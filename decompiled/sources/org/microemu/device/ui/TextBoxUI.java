package org.microemu.device.ui;

/* loaded from: avacs.jar:org/microemu/device/ui/TextBoxUI.class */
public interface TextBoxUI extends DisplayableUI {
    int getCaretPosition();

    String getString();

    void setString(String str);
}
