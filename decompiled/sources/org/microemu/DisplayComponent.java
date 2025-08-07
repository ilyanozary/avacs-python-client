package org.microemu;

import org.microemu.app.ui.DisplayRepaintListener;
import org.microemu.device.MutableImage;

/* loaded from: avacs.jar:org/microemu/DisplayComponent.class */
public interface DisplayComponent {
    void addDisplayRepaintListener(DisplayRepaintListener displayRepaintListener);

    void removeDisplayRepaintListener(DisplayRepaintListener displayRepaintListener);

    MutableImage getDisplayImage();

    void repaintRequest(int i, int i2, int i3, int i4);
}
