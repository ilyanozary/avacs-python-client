package org.microemu.midp.media.audio;

import javax.microedition.media.control.ToneControl;

/* loaded from: avacs.jar:org/microemu/midp/media/audio/PCToneControl.class */
public class PCToneControl implements ToneControl {
    public byte[] sequence;

    @Override // javax.microedition.media.control.ToneControl
    public void setSequence(byte[] sequence) {
        this.sequence = sequence;
    }

    public byte[] getSequence() {
        return this.sequence;
    }
}
