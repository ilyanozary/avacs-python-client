package org.microemu.device.ui;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Item;

/* loaded from: avacs.jar:org/microemu/device/ui/FormUI.class */
public interface FormUI extends DisplayableUI {
    int append(Image image);

    int append(Item item);

    int append(String str);

    void delete(int i);

    void deleteAll();

    void insert(int i, Item item);

    void set(int i, Item item);
}
