package org.microemu.device.impl;

/* loaded from: avacs.jar:org/microemu/device/impl/Color.class */
public class Color {
    private int value;

    public Color(int value) {
        this.value = value;
    }

    public int getRed() {
        return (this.value >> 16) & 255;
    }

    public int getGreen() {
        return (this.value >> 8) & 255;
    }

    public int getBlue() {
        return this.value & 255;
    }

    public int getRGB() {
        return this.value;
    }
}
