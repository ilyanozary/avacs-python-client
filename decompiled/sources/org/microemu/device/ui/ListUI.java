package org.microemu.device.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;

/* loaded from: avacs.jar:org/microemu/device/ui/ListUI.class */
public interface ListUI extends DisplayableUI {
    int append(String str, Image image);

    int getSelectedIndex();

    String getString(int i);

    void setSelectCommand(Command command);
}
