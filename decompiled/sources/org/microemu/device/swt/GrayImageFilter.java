package org.microemu.device.swt;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.microemu.app.ui.swt.ImageFilter;
import org.microemu.app.ui.swt.SwtDeviceComponent;
import org.microemu.device.DeviceFactory;

/* loaded from: avacs.jar:org/microemu/device/swt/GrayImageFilter.class */
public final class GrayImageFilter implements ImageFilter {
    private double Yr;
    private double Yg;
    private double Yb;
    private double Rr;
    private double Rg;
    private double Rb;
    private Color foregroundColor;

    public GrayImageFilter() {
        this(0.2126d, 0.7152d, 0.0722d);
    }

    public GrayImageFilter(double Yr, double Yg, double Yb) {
        this.Yr = Yr;
        this.Yg = Yg;
        this.Yb = Yb;
        Color backgroundColor = SwtDeviceComponent.getColor(new RGB(((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getRed(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getGreen(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getBlue()));
        this.foregroundColor = SwtDeviceComponent.getColor(new RGB(((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getRed(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getGreen(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getBlue()));
        this.Rr = (backgroundColor.getRed() - this.foregroundColor.getRed()) / 256.0d;
        this.Rg = (backgroundColor.getGreen() - this.foregroundColor.getGreen()) / 256.0d;
        this.Rb = (backgroundColor.getBlue() - this.foregroundColor.getBlue()) / 256.0d;
    }

    @Override // org.microemu.app.ui.swt.ImageFilter
    public RGB filterRGB(int x, int y, RGB rgb) {
        int Y = ((int) (((this.Yr * rgb.red) + (this.Yg * rgb.green)) + (this.Yb * rgb.blue))) % 256;
        if (Y > 255) {
            Y = 255;
        }
        return new RGB(((int) (this.Rr * Y)) + this.foregroundColor.getRed(), ((int) (this.Rg * Y)) + this.foregroundColor.getGreen(), ((int) (this.Rb * Y)) + this.foregroundColor.getBlue());
    }
}
