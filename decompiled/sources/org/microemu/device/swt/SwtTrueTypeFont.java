package org.microemu.device.swt;

import java.net.URL;
import org.eclipse.swt.graphics.Font;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtTrueTypeFont.class */
public class SwtTrueTypeFont implements SwtFont {
    private URL url;
    private String style;
    private int size;
    private boolean antialiasing;
    private boolean initialized = false;
    private Font font;

    public SwtTrueTypeFont(URL url, String style, int size, boolean antialiasing) {
        this.url = url;
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
                int i = swtStyle | 2;
            }
            this.style.indexOf("underlined");
            this.initialized = true;
            try {
                throw new RuntimeException("not implemented");
            } catch (RuntimeException ex) {
                ex.printStackTrace();
                throw ex;
            }
        }
    }

    @Override // org.microemu.device.impl.Font
    public int charWidth(char ch) {
        try {
            throw new RuntimeException("not implemented");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override // org.microemu.device.impl.Font
    public int charsWidth(char[] ch, int offset, int length) {
        try {
            throw new RuntimeException("not implemented");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override // org.microemu.device.impl.Font
    public int getBaselinePosition() {
        try {
            throw new RuntimeException("not implemented");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override // org.microemu.device.impl.Font
    public int getHeight() {
        try {
            throw new RuntimeException("not implemented");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override // org.microemu.device.impl.Font
    public int stringWidth(String str) {
        try {
            throw new RuntimeException("not implemented");
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
