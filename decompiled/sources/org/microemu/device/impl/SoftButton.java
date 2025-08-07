package org.microemu.device.impl;

import javax.microedition.lcdui.Command;

/* loaded from: avacs.jar:org/microemu/device/impl/SoftButton.class */
public interface SoftButton {
    public static final int TYPE_COMMAND = 1;
    public static final int TYPE_ICON = 2;

    String getName();

    int getType();

    Command getCommand();

    void setCommand(Command command);

    boolean isVisible();

    void setVisible(boolean z);

    boolean isPressed();

    void setPressed(boolean z);

    Rectangle getPaintable();

    boolean preferredCommandType(Command command);
}
