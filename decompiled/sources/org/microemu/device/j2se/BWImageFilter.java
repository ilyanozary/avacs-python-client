package org.microemu.device.j2se;

import org.microemu.device.DeviceFactory;

/* loaded from: avacs.jar:org/microemu/device/j2se/BWImageFilter.class */
public class BWImageFilter extends java.awt.image.RGBImageFilter {
    private double Yr;
    private double Yg;
    private double Yb;

    public BWImageFilter() {
        this(0.2126d, 0.7152d, 0.0722d);
    }

    public BWImageFilter(double Yr, double Yg, double Yb) {
        this.Yr = Yr;
        this.Yg = Yg;
        this.Yb = Yb;
        this.canFilterIndexColorModel = true;
    }

    public int filterRGB(int x, int y, int rgb) {
        int a = rgb & (-16777216);
        int r = (rgb & 16711680) >>> 16;
        int g = (rgb & 65280) >>> 8;
        int b = rgb & 255;
        int Y = (int) ((this.Yr * r) + (this.Yg * g) + (this.Yb * b));
        if (Y > 127) {
            return a | ((J2SEDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getRGB();
        }
        return a | ((J2SEDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getRGB();
    }
}
