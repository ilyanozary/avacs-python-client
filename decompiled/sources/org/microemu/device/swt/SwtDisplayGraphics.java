package org.microemu.device.swt;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.microemu.DisplayAccess;
import org.microemu.MIDletBridge;
import org.microemu.app.ui.swt.ImageFilter;
import org.microemu.app.ui.swt.SwtGraphics;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.DisplayGraphics;
import org.microemu.device.MutableImage;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtDisplayGraphics.class */
public class SwtDisplayGraphics extends Graphics implements DisplayGraphics {
    public SwtGraphics g;
    private MutableImage image;
    private int color = 0;
    private Font currentFont = Font.getDefaultFont();
    private ImageFilter filter;

    public SwtDisplayGraphics(SwtGraphics a_g, MutableImage a_image) {
        this.g = a_g;
        this.image = a_image;
        Device device = DeviceFactory.getDevice();
        this.g.setBackground(this.g.getColor(new RGB(((SwtDeviceDisplay) device.getDeviceDisplay()).getBackgroundColor().getRed(), ((SwtDeviceDisplay) device.getDeviceDisplay()).getBackgroundColor().getGreen(), ((SwtDeviceDisplay) device.getDeviceDisplay()).getBackgroundColor().getBlue())));
        SwtFont tmpFont = (SwtFont) ((SwtFontManager) device.getFontManager()).getFont(this.currentFont);
        this.g.setFont(tmpFont.getFont());
        if (device.getDeviceDisplay().isColor()) {
            this.filter = new RGBImageFilter();
        } else if (device.getDeviceDisplay().numColors() == 2) {
            this.filter = new BWImageFilter();
        } else {
            this.filter = new GrayImageFilter();
        }
    }

    @Override // org.microemu.device.DisplayGraphics
    public MutableImage getImage() {
        return this.image;
    }

    @Override // javax.microedition.lcdui.Graphics
    public int getColor() {
        return this.color;
    }

    @Override // javax.microedition.lcdui.Graphics
    public void setColor(int RGB) {
        this.color = RGB;
        this.g.setForeground(this.g.getColor(this.filter.filterRGB(0, 0, new RGB((this.color >> 16) & 255, (this.color >> 8) & 255, this.color & 255))));
    }

    @Override // javax.microedition.lcdui.Graphics
    public Font getFont() {
        return this.currentFont;
    }

    @Override // javax.microedition.lcdui.Graphics
    public void setFont(Font font) {
        this.currentFont = font;
        SwtFont tmpFont = (SwtFont) ((SwtFontManager) DeviceFactory.getDevice().getFontManager()).getFont(this.currentFont);
        this.g.setFont(tmpFont.getFont());
    }

    @Override // javax.microedition.lcdui.Graphics
    public void clipRect(int x, int y, int width, int height) {
        Rectangle rect = new Rectangle(x, y, width, height);
        if (rect.x < getClipX()) {
            rect.x = getClipX();
        }
        if (rect.y < getClipY()) {
            rect.y = getClipY();
        }
        if (x + width > getClipX() + getClipWidth()) {
            rect.width = (getClipX() + getClipWidth()) - rect.x;
        } else {
            rect.width = (x + width) - rect.x;
        }
        if (y + height > getClipY() + getClipHeight()) {
            rect.height = (getClipY() + getClipHeight()) - rect.y;
        } else {
            rect.height = (y + height) - rect.y;
        }
        setClip(rect.x, rect.y, rect.width, rect.height);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void setClip(int x, int y, int width, int height) {
        this.g.setClipping(x, y, width, height);
    }

    @Override // javax.microedition.lcdui.Graphics
    public int getClipX() {
        Rectangle rect = this.g.getClipping();
        if (rect == null) {
            return 0;
        }
        return rect.x;
    }

    @Override // javax.microedition.lcdui.Graphics
    public int getClipY() {
        Rectangle rect = this.g.getClipping();
        if (rect == null) {
            return 0;
        }
        return rect.y;
    }

    @Override // javax.microedition.lcdui.Graphics
    public int getClipHeight() {
        Rectangle rect = this.g.getClipping();
        if (rect == null) {
            DisplayAccess da = MIDletBridge.getMIDletAccess().getDisplayAccess();
            return da.getCurrent().getHeight();
        }
        return rect.height;
    }

    @Override // javax.microedition.lcdui.Graphics
    public int getClipWidth() {
        Rectangle rect = this.g.getClipping();
        if (rect == null) {
            DisplayAccess da = MIDletBridge.getMIDletAccess().getDisplayAccess();
            return da.getCurrent().getWidth();
        }
        return rect.width;
    }

    @Override // javax.microedition.lcdui.Graphics
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        this.g.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void drawImage(Image img, int x, int y, int anchor) {
        int newx = x;
        int newy = y;
        if (anchor == 0) {
            anchor = 20;
        }
        if ((anchor & 8) != 0) {
            newx -= img.getWidth();
        } else if ((anchor & 1) != 0) {
            newx -= img.getWidth() / 2;
        }
        if ((anchor & 32) != 0) {
            newy -= img.getHeight();
        } else if ((anchor & 2) != 0) {
            newy -= img.getHeight() / 2;
        }
        if (img.isMutable()) {
            this.g.drawImage(((SwtMutableImage) img).getImage(), newx, newy);
        } else {
            this.g.drawImage(((SwtImmutableImage) img).getImage(), newx, newy);
        }
    }

    @Override // javax.microedition.lcdui.Graphics
    public void drawLine(int x1, int y1, int x2, int y2) {
        this.g.drawLine(x1, y1, x2, y2);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void drawRect(int x, int y, int width, int height) {
        drawLine(x, y, x + width, y);
        drawLine(x + width, y, x + width, y + height);
        drawLine(x + width, y + height, x, y + height);
        drawLine(x, y + height, x, y);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dst, int y_dst, int anchor) {
        super.drawRegion(src, x_src, y_src, width, height, transform, x_dst, y_dst, anchor);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void drawRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height, boolean processAlpha) {
        super.drawRGB(rgbData, offset, scanlength, x, y, width, height, processAlpha);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void fillTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        int[] points = {x1, y1, x2, y2, x3, y3};
        this.g.fillPolygon(points);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        this.g.drawRoundRectangle(x, y, width, height, arcWidth, arcHeight);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void drawString(String str, int x, int y, int anchor) {
        int newx = x;
        int newy = y;
        if (anchor == 0) {
            anchor = 20;
        }
        if ((anchor & 2) != 0) {
            newy -= this.g.getFontMetrics().getAscent();
        } else if ((anchor & 32) != 0) {
            newy -= this.g.getFontMetrics().getHeight();
        }
        if ((anchor & 1) != 0) {
            newx -= this.g.stringWidth(str) / 2;
        } else if ((anchor & 8) != 0) {
            newx -= this.g.stringWidth(str);
        }
        boolean textAntialiasing = ((SwtFontManager) DeviceFactory.getDevice().getFontManager()).getAntialiasing();
        boolean graphicsAntialiasing = this.g.getAntialias();
        if (textAntialiasing != graphicsAntialiasing) {
            this.g.setAntialias(textAntialiasing);
        }
        this.g.drawString(str, newx, newy, true);
        if (textAntialiasing != graphicsAntialiasing) {
            this.g.setAntialias(graphicsAntialiasing);
        }
        if ((this.currentFont.getStyle() & 4) != 0) {
            this.g.drawLine(newx, newy + 1, newx + this.g.stringWidth(str), newy + 1);
        }
    }

    @Override // javax.microedition.lcdui.Graphics
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        Color tmp = this.g.getBackground();
        this.g.setBackground(this.g.getForeground());
        this.g.fillArc(x, y, width, height, startAngle, arcAngle);
        this.g.setBackground(tmp);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void fillRect(int x, int y, int width, int height) {
        Color tmp = this.g.getBackground();
        this.g.setBackground(this.g.getForeground());
        this.g.fillRectangle(x, y, width, height);
        this.g.setBackground(tmp);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        this.g.fillRoundRectangle(x, y, width, height, arcWidth, arcHeight);
    }

    @Override // javax.microedition.lcdui.Graphics
    public void translate(int x, int y) {
        super.translate(x, y);
        this.g.translate(x, y);
    }
}
