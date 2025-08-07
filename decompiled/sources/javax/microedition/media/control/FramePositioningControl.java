package javax.microedition.media.control;

import javax.microedition.media.Control;

/* loaded from: avacs.jar:javax/microedition/media/control/FramePositioningControl.class */
public interface FramePositioningControl extends Control {
    int seek(int i);

    int skip(int i);

    long mapFrameToTime(int i);

    int mapTimeToFrame(long j);
}
