package org.microemu.device.swt;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.microemu.app.ui.swt.ImageFilter;
import org.microemu.app.ui.swt.SwtDeviceComponent;
import org.microemu.device.DeviceFactory;

/* loaded from: avacs.jar:org/microemu/device/swt/BWImageFilter.class */
public final class BWImageFilter implements ImageFilter {
    private double Yr;
    private double Yg;
    private double Yb;
    private Color backgroundColor;
    private Color foregroundColor;

    public BWImageFilter() {
        this(0.2126d, 0.7152d, 0.0722d);
    }

    public BWImageFilter(double Yr, double Yg, double Yb) {
        this.Yr = Yr;
        this.Yg = Yg;
        this.Yb = Yb;
        this.backgroundColor = SwtDeviceComponent.getColor(new RGB(((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getRed(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getGreen(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getBlue()));
        this.foregroundColor = SwtDeviceComponent.getColor(new RGB(((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getRed(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getGreen(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getBlue()));
    }

    @Override // org.microemu.app.ui.swt.ImageFilter
    public RGB filterRGB(int x, int y, RGB rgb) {
        int Y = (int) ((this.Yr * rgb.red) + (this.Yg * rgb.green) + (this.Yb * rgb.blue));
        if (Y > 127) {
            return this.backgroundColor.getRGB();
        }
        return this.foregroundColor.getRGB();
    }
}
