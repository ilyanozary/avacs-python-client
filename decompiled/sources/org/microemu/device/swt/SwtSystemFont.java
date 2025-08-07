package org.microemu.device.swt;

import org.eclipse.swt.graphics.Font;
import org.microemu.app.ui.swt.SwtDeviceComponent;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtSystemFont.class */
public class SwtSystemFont implements SwtFont {
    private String name;
    private String style;
    private int size;
    private boolean antialiasing;
    private boolean initialized = false;
    private Font font;

    public SwtSystemFont(String name, String style, int size, boolean antialiasing) {
        this.name = name;
        this.style = style.toLowerCase();
        this.size = size;
        this.antialiasing = antialiasing;
    }

    @Override // org.microemu.device.swt.SwtFont
    public void setAntialiasing(boolean antialiasing) {
        if (this.antialiasing != antialiasing) {
            this.antialiasing = antialiasing;
            this.initialized = false;
        }
    }

    @Override // org.microemu.device.swt.SwtFont
    public Font getFont() {
        checkInitialized();
        return this.font;
    }

    private synchronized void checkInitialized() {
        if (!this.initialized) {
            int swtStyle = 0;
            if (this.style.indexOf("plain") != -1) {
                swtStyle = 0 | 0;
            }
            if (this.style.indexOf("bold") != -1) {
                swtStyle |= 1;
            }
            if (this.style.indexOf("italic") != -1) {
                swtStyle |= 2;
            }
            this.style.indexOf("underlined");
            this.font = SwtDeviceComponent.getFont(this.name, this.size, swtStyle, this.antialiasing);
            this.initialized = true;
        }
    }

    @Override // org.microemu.device.impl.Font
    public int charWidth(char ch) {
        return charsWidth(new char[]{ch}, 0, 1);
    }

    @Override // org.microemu.device.impl.Font
    public int charsWidth(char[] ch, int offset, int length) {
        checkInitialized();
        return SwtDeviceComponent.stringWidth(this.font, new String(ch, offset, length));
    }

    @Override // org.microemu.device.impl.Font
    public int getBaselinePosition() {
        checkInitialized();
        return SwtDeviceComponent.getFontMetrics(this.font).getAscent();
    }

    @Override // org.microemu.device.impl.Font
    public int getHeight() {
        checkInitialized();
        return SwtDeviceComponent.getFontMetrics(this.font).getHeight();
    }

    @Override // org.microemu.device.impl.Font
    public int stringWidth(String str) {
        checkInitialized();
        return SwtDeviceComponent.stringWidth(this.font, str);
    }
}
