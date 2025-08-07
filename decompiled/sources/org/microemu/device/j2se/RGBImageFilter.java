package org.microemu.device.j2se;

import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.Color;

/* loaded from: avacs.jar:org/microemu/device/j2se/RGBImageFilter.class */
public class RGBImageFilter extends java.awt.image.RGBImageFilter {
    private double Rr;
    private double Rg;
    private double Rb;
    private Color backgroundColor;
    private Color foregroundColor;

    public RGBImageFilter() {
        this.canFilterIndexColorModel = true;
        this.backgroundColor = ((J2SEDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor();
        this.foregroundColor = ((J2SEDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor();
        this.Rr = this.foregroundColor.getRed() - this.backgroundColor.getRed();
        this.Rg = this.foregroundColor.getGreen() - this.backgroundColor.getGreen();
        this.Rb = this.foregroundColor.getBlue() - this.backgroundColor.getBlue();
    }

    public int filterRGB(int x, int y, int rgb) {
        int r;
        int g;
        int b;
        int a = rgb & (-16777216);
        int r2 = (rgb & 16711680) >>> 16;
        int g2 = (rgb & 65280) >>> 8;
        int b2 = rgb & 255;
        if (this.Rr > 0.0d) {
            r = (((int) (r2 * this.Rr)) / 255) + this.backgroundColor.getRed();
        } else {
            r = (((int) (r2 * (-this.Rr))) / 255) + this.foregroundColor.getRed();
        }
        if (this.Rr > 0.0d) {
            g = (((int) (g2 * this.Rg)) / 255) + this.backgroundColor.getGreen();
        } else {
            g = (((int) (g2 * (-this.Rg))) / 255) + this.foregroundColor.getGreen();
        }
        if (this.Rr > 0.0d) {
            b = (((int) (b2 * this.Rb)) / 255) + this.backgroundColor.getBlue();
        } else {
            b = (((int) (b2 * (-this.Rb))) / 255) + this.foregroundColor.getBlue();
        }
        return a | (r << 16) | (g << 8) | b;
    }
}
