package javax.microedition.media.control;

import javax.microedition.media.Control;
import javax.microedition.media.MediaException;

/* loaded from: avacs.jar:javax/microedition/media/control/MIDIControl.class */
public interface MIDIControl extends Control {
    public static final int NOTE_ON = 144;
    public static final int CONTROL_CHANGE = 176;

    boolean isBankQuerySupported();

    int[] getProgram(int i) throws MediaException;

    int getChannelVolume(int i);

    void setProgram(int i, int i2, int i3);

    void setChannelVolume(int i, int i2);

    int[] getBankList(boolean z) throws MediaException;

    int[] getProgramList(int i) throws MediaException;

    String getProgramName(int i, int i2) throws MediaException;

    String getKeyName(int i, int i2, int i3) throws MediaException;

    void shortMidiEvent(int i, int i2, int i3);

    int longMidiEvent(byte[] bArr, int i, int i2);
}
