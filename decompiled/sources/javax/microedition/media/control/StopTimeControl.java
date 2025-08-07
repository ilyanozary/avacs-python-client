package javax.microedition.media.control;

import javax.microedition.media.Control;

/* loaded from: avacs.jar:javax/microedition/media/control/StopTimeControl.class */
public interface StopTimeControl extends Control {
    public static final long RESET = Long.MAX_VALUE;

    void setStopTime(long j);

    long getStopTime();
}
