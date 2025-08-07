package javax.microedition.media.control;

import javax.microedition.media.Control;

/* loaded from: avacs.jar:javax/microedition/media/control/VolumeControl.class */
public interface VolumeControl extends Control {
    void setMute(boolean z);

    boolean isMuted();

    int setLevel(int i);

    int getLevel();
}
