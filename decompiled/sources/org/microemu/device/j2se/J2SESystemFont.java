package org.microemu.device.j2se;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/* loaded from: avacs.jar:org/microemu/device/j2se/J2SESystemFont.class */
public class J2SESystemFont implements J2SEFont {
    private static final Graphics2D graphics = new BufferedImage(1, 1, 2).getGraphics();
    private String name;
    private String style;
    private int size;
    private boolean antialiasing;
    private boolean initialized = false;
    private FontMetrics fontMetrics;

    public J2SESystemFont(String name, String style, int size, boolean antialiasing) {
        this.name = name;
        this.style = style.toLowerCase();
        this.size = size;
        this.antialiasing = antialiasing;
    }

    @Override // org.microemu.device.j2se.J2SEFont
    public void setAntialiasing(boolean antialiasing) {
        if (this.antialiasing != antialiasing) {
            this.antialiasing = antialiasing;
            this.initialized = false;
        }
    }

    @Override // org.microemu.device.impl.Font
    public int charWidth(char ch) {
        checkInitialized();
        return this.fontMetrics.charWidth(ch);
    }

    @Override // org.microemu.device.impl.Font
    public int charsWidth(char[] ch, int offset, int length) {
        checkInitialized();
        return this.fontMetrics.charsWidth(ch, offset, length);
    }

    @Override // org.microemu.device.impl.Font
    public int getBaselinePosition() {
        checkInitialized();
        return this.fontMetrics.getAscent();
    }

    @Override // org.microemu.device.impl.Font
    public int getHeight() {
        checkInitialized();
        return this.fontMetrics.getHeight();
    }

    @Override // org.microemu.device.impl.Font
    public int stringWidth(String str) {
        checkInitialized();
        return this.fontMetrics.stringWidth(str);
    }

    @Override // org.microemu.device.j2se.J2SEFont
    public Font getFont() {
        checkInitialized();
        return this.fontMetrics.getFont();
    }

    private synchronized void checkInitialized() {
        if (!this.initialized) {
            int awtStyle = 0;
            if (this.style.indexOf("plain") != -1) {
                awtStyle = 0 | 0;
            }
            if (this.style.indexOf("bold") != -1) {
                awtStyle |= 1;
            }
            if (this.style.indexOf("italic") != -1) {
                awtStyle |= 2;
            }
            this.style.indexOf("underlined");
            if (this.antialiasing) {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            } else {
                graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            }
            this.fontMetrics = graphics.getFontMetrics(new Font(this.name, awtStyle, this.size));
            this.initialized = true;
        }
    }
}
