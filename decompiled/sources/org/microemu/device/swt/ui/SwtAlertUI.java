package org.microemu.device.swt.ui;

import javax.microedition.lcdui.Alert;
import org.microemu.device.impl.ui.DisplayableImplUI;
import org.microemu.device.ui.AlertUI;

/* loaded from: avacs.jar:org/microemu/device/swt/ui/SwtAlertUI.class */
public class SwtAlertUI extends DisplayableImplUI implements AlertUI {
    public SwtAlertUI(Alert alert) {
        super(alert);
    }

    @Override // org.microemu.device.ui.AlertUI
    public void setString(String str) {
    }
}
