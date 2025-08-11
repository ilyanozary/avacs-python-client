#!/usr/bin/env zsh
# Capture AVACS framed payloads (HELLO/LOGIN) passing through a local proxy or directly.
# Logs both directions with timestamps and stream ids; extracts AVACS frames.
# Usage:
#   zsh tools/capture_avacs.sh [port] [iface]
# Examples:
#   zsh tools/capture_avacs.sh 1080 lo0   # SOCKS on loopback (default)
#   zsh tools/capture_avacs.sh 9001 en0   # Direct server port on Wi‑Fi

set -euo pipefail

PROXY_PORT=${1:-1080}
IFACE=${2:-lo0}
LOGDIR="logs"
mkdir -p "$LOGDIR"
LOGFILE="$LOGDIR/avacs_capture_$(date +%Y%m%d_%H%M%S).log"

if ! command -v tshark >/dev/null 2>&1; then
  echo "tshark is required. Install with: brew install wireshark" >&2
  exit 1
fi

# Prepare inline Python processor in a temp file (so stdin remains the tshark pipe)
PYPROC=$(mktemp -t avacs_capture_py)
cat > "$PYPROC" <<'PY'
import sys, os, binascii, datetime
START = bytes.fromhex('01 02 03 07 07')
END   = bytes.fromhex('01 07 08 09 09')
PROXY_PORT = int(os.environ.get('AVACS_PROXY_PORT', '1080'))

# buffers[(stream_id, dir)] -> bytearray
buffers = {}

def flush_frames(key, ts):
    buf = buffers.get(key)
    if not buf:
        return
    while True:
        s = buf.find(START)
        if s == -1:
            if len(buf) > (1<<20):
                del buf[:-8192]
            return
        e = buf.find(END, s + len(START))
        if e == -1:
            return
        payload = bytes(buf[s+len(START):e])
        direction = key[1]
        print(f"[FRAME {direction}] t={ts} stream={key[0]} len={len(payload)} hex={payload.hex()}")
        del buf[: e+len(END)]

def fmt_time(epoch):
    try:
        dt = datetime.datetime.fromtimestamp(float(epoch))
        return dt.strftime('%H:%M:%S.%f')[:-3]
    except Exception:
        return str(epoch)

for line in sys.stdin:
    line = line.rstrip('\n')
    if not line:
        continue
    # Expect TSV: time, stream, ip.src, ipv6.src, tcp.srcport, ip.dst, ipv6.dst, tcp.dstport, tcp.payload
    parts = line.split('\t')
    if len(parts) < 9:
        continue
    t, stream, ip4s, ip6s, srcp, ip4d, ip6d, dstp, hexdata = parts[:9]
    src = ip4s or ip6s or ''
    dst = ip4d or ip6d or ''
    ts = fmt_time(t)
    try:
        srcp_i = int(srcp)
        dstp_i = int(dstp)
    except Exception:
        continue
    if not hexdata:
        continue
    direction = 'OUT' if dstp_i == PROXY_PORT else ('IN' if srcp_i == PROXY_PORT else 'UNK')
    try:
        data = binascii.unhexlify(hexdata)
    except Exception:
        continue
    key = (stream, direction)
    buffers.setdefault(key, bytearray()).extend(data)
    print(f"[PKT  {direction}] t={ts} stream={stream} {src}:{srcp}->{dst}:{dstp} len={len(data)}")
    flush_frames(key, ts)
PY

# Capture TCP on the given port, reassemble streams, extract frames between markers, print as hex
# Use tcp.payload (works for IPv4/IPv6 and avoids dependence on 'segment_data').

echo "Capturing on $IFACE port $PROXY_PORT ... Press Ctrl+C to stop"
echo "Logging to: $LOGFILE"

AVACS_PROXY_PORT="$PROXY_PORT" tshark \
  -i "$IFACE" \
  -f "tcp port $PROXY_PORT" \
  -o tcp.desegment_tcp_streams:TRUE \
  -o tcp.relative_sequence_numbers:FALSE \
  -Y "tcp.payload" \
  -T fields \
  -e frame.time_epoch -e tcp.stream -e ip.src -e ipv6.src -e tcp.srcport -e ip.dst -e ipv6.dst -e tcp.dstport -e tcp.payload \
  -E separator=$'\t' \
  -l | python3 "$PYPROC" | tee -a "$LOGFILE"

# Cleanup
rm -f "$PYPROC"
