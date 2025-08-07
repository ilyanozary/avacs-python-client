package org.microemu.midp.media.audio;

import org.microemu.midp.media.TimeBase;

/* loaded from: avacs.jar:org/microemu/midp/media/audio/PCTimeBase.class */
class PCTimeBase implements TimeBase {
    private static long timeBase = System.currentTimeMillis();

    PCTimeBase() {
    }

    @Override // org.microemu.midp.media.TimeBase
    public long getTime() {
        return System.currentTimeMillis() - timeBase;
    }
}
