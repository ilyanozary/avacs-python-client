package javax.microedition.media.control;

import javax.microedition.media.MediaException;

/* loaded from: avacs.jar:javax/microedition/media/control/VideoControl.class */
public interface VideoControl extends GUIControl {
    public static final int USE_DIRECT_VIDEO = 1;

    @Override // javax.microedition.media.control.GUIControl
    Object initDisplayMode(int i, Object obj);

    void setDisplayLocation(int i, int i2);

    int getDisplayX();

    int getDisplayY();

    void setVisible(boolean z);

    void setDisplaySize(int i, int i2) throws MediaException;

    void setDisplayFullScreen(boolean z) throws MediaException;

    int getSourceWidth();

    int getSourceHeight();

    int getDisplayWidth();

    int getDisplayHeight();

    byte[] getSnapshot(String str) throws MediaException;
}
