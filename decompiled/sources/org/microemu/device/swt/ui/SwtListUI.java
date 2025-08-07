package org.microemu.device.swt.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import org.microemu.device.impl.ui.DisplayableImplUI;
import org.microemu.device.ui.ListUI;

/* loaded from: avacs.jar:org/microemu/device/swt/ui/SwtListUI.class */
public class SwtListUI extends DisplayableImplUI implements ListUI {
    public SwtListUI(List list) {
        super(list);
    }

    @Override // org.microemu.device.ui.ListUI
    public int append(String stringPart, Image imagePart) {
        return 0;
    }

    @Override // org.microemu.device.ui.ListUI
    public void setSelectCommand(Command command) {
    }

    @Override // org.microemu.device.ui.ListUI
    public int getSelectedIndex() {
        return 0;
    }

    @Override // org.microemu.device.ui.ListUI
    public String getString(int elementNum) {
        return null;
    }
}
