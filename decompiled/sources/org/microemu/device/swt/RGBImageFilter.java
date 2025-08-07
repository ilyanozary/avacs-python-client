package org.microemu.device.swt;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.microemu.app.ui.swt.ImageFilter;
import org.microemu.app.ui.swt.SwtDeviceComponent;
import org.microemu.device.DeviceFactory;

/* loaded from: avacs.jar:org/microemu/device/swt/RGBImageFilter.class */
public final class RGBImageFilter implements ImageFilter {
    private Color backgroundColor = SwtDeviceComponent.getColor(new RGB(((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getRed(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getGreen(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getBackgroundColor().getBlue()));
    private Color foregroundColor = SwtDeviceComponent.getColor(new RGB(((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getRed(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getGreen(), ((SwtDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getForegroundColor().getBlue()));
    private double Rr = this.foregroundColor.getRed() - this.backgroundColor.getRed();
    private double Rg = this.foregroundColor.getGreen() - this.backgroundColor.getGreen();
    private double Rb = this.foregroundColor.getBlue() - this.backgroundColor.getBlue();

    @Override // org.microemu.app.ui.swt.ImageFilter
    public RGB filterRGB(int x, int y, RGB rgb) {
        int r;
        int g;
        int b;
        if (this.Rr > 0.0d) {
            r = (((int) (rgb.red * this.Rr)) / 255) + this.backgroundColor.getRed();
        } else {
            r = (((int) (rgb.red * (-this.Rr))) / 255) + this.foregroundColor.getRed();
        }
        if (this.Rr > 0.0d) {
            g = (((int) (rgb.green * this.Rg)) / 255) + this.backgroundColor.getGreen();
        } else {
            g = (((int) (rgb.green * (-this.Rg))) / 255) + this.foregroundColor.getGreen();
        }
        if (this.Rr > 0.0d) {
            b = (((int) (rgb.blue * this.Rb)) / 255) + this.backgroundColor.getBlue();
        } else {
            b = (((int) (rgb.blue * (-this.Rb))) / 255) + this.foregroundColor.getBlue();
        }
        return new RGB(r, g, b);
    }
}
