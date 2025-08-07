package org.microemu.device.j2se.ui;

import javax.microedition.lcdui.Alert;
import org.microemu.device.impl.ui.DisplayableImplUI;
import org.microemu.device.ui.AlertUI;

/* loaded from: avacs.jar:org/microemu/device/j2se/ui/J2SEAlertUI.class */
public class J2SEAlertUI extends DisplayableImplUI implements AlertUI {
    public J2SEAlertUI(Alert alert) {
        super(alert);
    }

    @Override // org.microemu.device.ui.AlertUI
    public void setString(String str) {
    }
}
