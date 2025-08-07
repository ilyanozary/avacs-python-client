package org.microemu.app.ui.swt;

import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

/* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtGraphics.class */
public class SwtGraphics {
    private Display display;
    private GC gc;
    private int transX = 0;
    private int transY = 0;
    private HashMap colors;

    public SwtGraphics(Display display) {
        this.display = display;
        this.gc = new GC(display);
    }

    public SwtGraphics(GC gc) {
        this.gc = gc;
    }

    public void dispose() {
        this.gc.dispose();
        if (this.colors != null) {
            Iterator it = this.colors.values().iterator();
            while (it.hasNext()) {
                ((Color) it.next()).dispose();
            }
        }
    }

    public void drawImage(Image image, int srcX, int srcY, int srcWidth, int srcHeight, int destX, int destY, int destWidth, int destHeight) {
        this.gc.drawImage(image, srcX, srcY, srcWidth, srcHeight, destX + this.transX, destY + this.transY, destWidth, destHeight);
    }

    public void drawImage(Image image, int x, int y) {
        this.gc.drawImage(image, x + this.transX, y + this.transY);
    }

    public void translate(int x, int y) {
        this.transX += x;
        this.transY += y;
    }

    public Color getColor(RGB rgb) {
        if (this.colors == null) {
            this.colors = new HashMap();
        }
        Color result = (Color) this.colors.get(rgb);
        if (result == null) {
            result = new Color(this.display, rgb);
            this.colors.put(rgb, result);
        }
        return result;
    }

    public FontMetrics getFontMetrics() {
        return this.gc.getFontMetrics();
    }

    public void setFont(Font font) {
        this.gc.setFont(font);
    }

    public Color getBackground() {
        return this.gc.getBackground();
    }

    public Color getForeground() {
        return this.gc.getForeground();
    }

    public void setBackground(Color color) {
        this.gc.setBackground(color);
    }

    public void setForeground(Color color) {
        this.gc.setForeground(color);
    }

    public Rectangle getClipping() {
        return this.gc.getClipping();
    }

    public void setClipping(int x, int y, int width, int height) {
        this.gc.setClipping(x + this.transX, y + this.transY, width, height);
    }

    public void drawArc(int x, int y, int width, int height, int startAngle, int endAngle) {
        this.gc.drawArc(x + this.transX, y + this.transY, width, height, startAngle, endAngle);
    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        this.gc.drawLine(x1 + this.transX, y1 + this.transY, x2 + this.transX, y2 + this.transY);
    }

    public void drawRoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        this.gc.drawRoundRectangle(x + this.transX, y + this.transY, width, height, arcWidth, arcHeight);
    }

    public void drawString(String string, int x, int y, boolean isTransparent) {
        this.gc.drawString(string, x + this.transX, y + this.transY, isTransparent);
    }

    public void fillArc(int x, int y, int width, int height, int startAngle, int endAngle) {
        this.gc.fillArc(x + this.transX, y + this.transY, width, height, startAngle, endAngle);
    }

    public void fillPolygon(int[] pointArray) {
        this.gc.fillPolygon(pointArray);
    }

    public void fillRectangle(int x, int y, int width, int height) {
        this.gc.fillRectangle(x + this.transX, y + this.transY, width, height);
    }

    public void fillRoundRectangle(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        this.gc.fillRoundRectangle(x + this.transX, y + this.transY, width, height, arcWidth, arcHeight);
    }

    public int stringWidth(String string) {
        return this.gc.stringExtent(string).x;
    }

    public Font getFont() {
        return this.gc.getFont();
    }

    public void setClipping(Rectangle rect) {
        Rectangle tmp = new Rectangle(rect.x + this.transX, rect.y + this.transY, rect.width, rect.height);
        this.gc.setClipping(tmp);
    }

    public boolean getAntialias() {
        if (this.gc.getAntialias() == 1) {
            return true;
        }
        return false;
    }

    public void setAntialias(boolean antialias) {
        if (antialias) {
            this.gc.setAntialias(1);
        } else {
            this.gc.setAntialias(0);
        }
    }
}
