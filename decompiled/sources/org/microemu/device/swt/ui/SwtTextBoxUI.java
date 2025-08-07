package org.microemu.device.swt.ui;

import javax.microedition.lcdui.TextBox;
import org.microemu.device.impl.ui.DisplayableImplUI;
import org.microemu.device.ui.TextBoxUI;

/* loaded from: avacs.jar:org/microemu/device/swt/ui/SwtTextBoxUI.class */
public class SwtTextBoxUI extends DisplayableImplUI implements TextBoxUI {
    public SwtTextBoxUI(TextBox textBox) {
        super(textBox);
    }

    @Override // org.microemu.device.ui.TextBoxUI
    public int getCaretPosition() {
        return 0;
    }

    @Override // org.microemu.device.ui.TextBoxUI
    public String getString() {
        return null;
    }

    @Override // org.microemu.device.ui.TextBoxUI
    public void setString(String text) {
    }
}
