package javax.microedition.media.control;

import javax.microedition.media.Control;

/* loaded from: avacs.jar:javax/microedition/media/control/RateControl.class */
public interface RateControl extends Control {
    int setRate(int i);

    int getRate();

    int getMaxRate();

    int getMinRate();
}
