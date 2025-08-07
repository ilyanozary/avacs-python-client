package org.microemu.device.j2se.ui;

import javax.microedition.lcdui.TextBox;
import org.microemu.device.impl.ui.DisplayableImplUI;
import org.microemu.device.ui.TextBoxUI;

/* loaded from: avacs.jar:org/microemu/device/j2se/ui/J2SETextBoxUI.class */
public class J2SETextBoxUI extends DisplayableImplUI implements TextBoxUI {
    private String text;

    public J2SETextBoxUI(TextBox textBox) {
        super(textBox);
    }

    @Override // org.microemu.device.ui.TextBoxUI
    public int getCaretPosition() {
        return -1;
    }

    @Override // org.microemu.device.ui.TextBoxUI
    public String getString() {
        return this.text;
    }

    @Override // org.microemu.device.ui.TextBoxUI
    public void setString(String text) {
        this.text = text;
    }
}
