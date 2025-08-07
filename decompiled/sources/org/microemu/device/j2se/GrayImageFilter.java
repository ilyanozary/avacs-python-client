package org.microemu.device.j2se;

import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.Color;

/* loaded from: avacs.jar:org/microemu/device/j2se/GrayImageFilter.class */
public class GrayImageFilter extends java.awt.image.RGBImageFilter {
    private double Yr;
    private double Yg;
    private double Yb;
    private double Rr;
    private double Rg;
    private double Rb;

    public GrayImageFilter() {
        this(0.2126d, 0.7152d, 0.0722d);
    }

    public GrayImageFilter(double Yr, double Yg, double Yb) {
        this.Yr = Yr;
        this.Yg = Yg;
        this.Yb = Yb;
        this.canFilterIndexColorModel = true;
        Color backgroundColor = ((J2SEDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor();
        Color foregroundColor = ((J2SEDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor();
        this.Rr = (backgroundColor.getRed() - foregroundColor.getRed()) / 256.0d;
        this.Rg = (backgroundColor.getGreen() - foregroundColor.getGreen()) / 256.0d;
        this.Rb = (backgroundColor.getBlue() - foregroundColor.getBlue()) / 256.0d;
    }

    public int filterRGB(int x, int y, int rgb) {
        int a = rgb & (-16777216);
        int r = (rgb & 16711680) >>> 16;
        int g = (rgb & 65280) >>> 8;
        int b = rgb & 255;
        int Y = ((int) (((this.Yr * r) + (this.Yg * g)) + (this.Yb * b))) % 256;
        if (Y > 255) {
            Y = 255;
        }
        Color foregroundColor = ((J2SEDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor();
        int r2 = ((int) (this.Rr * Y)) + foregroundColor.getRed();
        int g2 = ((int) (this.Rg * Y)) + foregroundColor.getGreen();
        int b2 = ((int) (this.Rb * Y)) + foregroundColor.getBlue();
        return a | (r2 << 16) | (g2 << 8) | b2;
    }
}
