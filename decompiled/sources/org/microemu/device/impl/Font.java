package org.microemu.device.impl;

/* loaded from: avacs.jar:org/microemu/device/impl/Font.class */
public interface Font {
    int charWidth(char c);

    int charsWidth(char[] cArr, int i, int i2);

    int getBaselinePosition();

    int getHeight();

    int stringWidth(String str);
}
