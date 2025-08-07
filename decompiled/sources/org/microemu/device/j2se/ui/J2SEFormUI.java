package org.microemu.device.j2se.ui;

import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;
import org.microemu.device.impl.ui.DisplayableImplUI;
import org.microemu.device.ui.FormUI;

/* loaded from: avacs.jar:org/microemu/device/j2se/ui/J2SEFormUI.class */
public class J2SEFormUI extends DisplayableImplUI implements FormUI {
    public J2SEFormUI(Form form) {
        super(form);
    }

    @Override // org.microemu.device.ui.FormUI
    public int append(Image img) {
        return 0;
    }

    @Override // org.microemu.device.ui.FormUI
    public int append(Item item) {
        return 0;
    }

    @Override // org.microemu.device.ui.FormUI
    public int append(String str) {
        return 0;
    }

    @Override // org.microemu.device.ui.FormUI
    public void delete(int itemNum) {
    }

    @Override // org.microemu.device.ui.FormUI
    public void deleteAll() {
    }

    @Override // org.microemu.device.ui.FormUI
    public void insert(int itemNum, Item item) {
    }

    @Override // org.microemu.device.ui.FormUI
    public void set(int itemNum, Item item) {
    }
}
