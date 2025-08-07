package org.microemu.device;

import javax.microedition.lcdui.Font;

/* loaded from: avacs.jar:org/microemu/device/FontManager.class */
public interface FontManager {
    void init();

    int charWidth(Font font, char c);

    int charsWidth(Font font, char[] cArr, int i, int i2);

    int getBaselinePosition(Font font);

    int getHeight(Font font);

    int stringWidth(Font font, String str);
}
