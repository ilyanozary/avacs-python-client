package org.microemu.util;

/* loaded from: avacs.jar:org/microemu/util/Base64Coder.class */
public class Base64Coder {
    private static char[] map1 = new char[64];
    private static byte[] map2;

    static {
        int i = 0;
        char c = 'A';
        while (true) {
            char c2 = c;
            if (c2 > 'Z') {
                break;
            }
            int i2 = i;
            i++;
            map1[i2] = c2;
            c = (char) (c2 + 1);
        }
        char c3 = 'a';
        while (true) {
            char c4 = c3;
            if (c4 > 'z') {
                break;
            }
            int i3 = i;
            i++;
            map1[i3] = c4;
            c3 = (char) (c4 + 1);
        }
        char c5 = '0';
        while (true) {
            char c6 = c5;
            if (c6 > '9') {
                break;
            }
            int i4 = i;
            i++;
            map1[i4] = c6;
            c5 = (char) (c6 + 1);
        }
        int i5 = i;
        int i6 = i + 1;
        map1[i5] = '+';
        int i7 = i6 + 1;
        map1[i6] = '/';
        map2 = new byte[128];
        for (int i8 = 0; i8 < map2.length; i8++) {
            map2[i8] = -1;
        }
        for (int i9 = 0; i9 < 64; i9++) {
            map2[map1[i9]] = (byte) i9;
        }
    }

    public static String encode(String s) {
        return new String(encode(s.getBytes()));
    }

    public static char[] encode(byte[] in) {
        int i;
        int i2;
        int iLen = in.length;
        int oDataLen = ((iLen * 4) + 2) / 3;
        int oLen = ((iLen + 2) / 3) * 4;
        char[] out = new char[oLen];
        int ip = 0;
        int op = 0;
        while (ip < iLen) {
            int i3 = ip;
            ip++;
            int i0 = in[i3] & 255;
            if (ip < iLen) {
                ip++;
                i = in[ip] & 255;
            } else {
                i = 0;
            }
            int i1 = i;
            if (ip < iLen) {
                int i4 = ip;
                ip++;
                i2 = in[i4] & 255;
            } else {
                i2 = 0;
            }
            int i22 = i2;
            int o0 = i0 >>> 2;
            int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
            int o2 = ((i1 & 15) << 2) | (i22 >>> 6);
            int o3 = i22 & 63;
            int i5 = op;
            int op2 = op + 1;
            out[i5] = map1[o0];
            int op3 = op2 + 1;
            out[op2] = map1[o1];
            out[op3] = op3 < oDataLen ? map1[o2] : '=';
            int op4 = op3 + 1;
            out[op4] = op4 < oDataLen ? map1[o3] : '=';
            op = op4 + 1;
        }
        return out;
    }

    public static String decode(String s) {
        return new String(decode(s.toCharArray()));
    }

    public static byte[] decode(char[] in) {
        char c;
        char c2;
        int iLen = in.length;
        if (iLen % 4 != 0) {
            throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
        }
        while (iLen > 0 && in[iLen - 1] == '=') {
            iLen--;
        }
        int oLen = (iLen * 3) / 4;
        byte[] out = new byte[oLen];
        int ip = 0;
        int op = 0;
        while (ip < iLen) {
            int i = ip;
            int ip2 = ip + 1;
            char c3 = in[i];
            ip = ip2 + 1;
            char c4 = in[ip2];
            if (ip < iLen) {
                ip++;
                c = in[ip];
            } else {
                c = 'A';
            }
            int i2 = c;
            if (ip < iLen) {
                int i3 = ip;
                ip++;
                c2 = in[i3];
            } else {
                c2 = 'A';
            }
            int i32 = c2;
            if (c3 > 127 || c4 > 127 || i2 > 127 || i32 > 127) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            byte b = map2[c3];
            byte b2 = map2[c4];
            byte b3 = map2[i2];
            byte b4 = map2[i32];
            if (b < 0 || b2 < 0 || b3 < 0 || b4 < 0) {
                throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
            }
            int o0 = (b << 2) | (b2 >>> 4);
            int o1 = ((b2 & 15) << 4) | (b3 >>> 2);
            int o2 = ((b3 & 3) << 6) | b4;
            int i4 = op;
            op++;
            out[i4] = (byte) o0;
            if (op < oLen) {
                op++;
                out[op] = (byte) o1;
            }
            if (op < oLen) {
                int i5 = op;
                op++;
                out[i5] = (byte) o2;
            }
        }
        return out;
    }
}
