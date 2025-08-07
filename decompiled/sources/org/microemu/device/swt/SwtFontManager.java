package org.microemu.device.swt;

import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import org.microemu.device.impl.Font;
import org.microemu.device.impl.FontManagerImpl;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtFontManager.class */
public class SwtFontManager implements FontManagerImpl {
    static String FACE_SYSTEM_NAME = "SansSerif";
    static String FACE_MONOSPACE_NAME = "Monospaced";
    static String FACE_PROPORTIONAL_NAME = "SansSerif";
    static int SIZE_SMALL = 6;
    static int SIZE_MEDIUM = 8;
    static int SIZE_LARGE = 10;
    private Hashtable fonts = new Hashtable();
    private boolean antialiasing;

    Font getFont(javax.microedition.lcdui.Font meFont) {
        int key = 0 | meFont.getFace() | meFont.getStyle() | meFont.getSize();
        Font result = (Font) this.fonts.get(new Integer(key));
        if (result == null) {
            String name = null;
            if (meFont.getFace() == 0) {
                name = FACE_SYSTEM_NAME;
            } else if (meFont.getFace() == 32) {
                name = FACE_MONOSPACE_NAME;
            } else if (meFont.getFace() == 64) {
                name = FACE_PROPORTIONAL_NAME;
            }
            String style = ",";
            meFont.getStyle();
            if (0 != 0) {
                style = String.valueOf(style) + "plain,";
            }
            if ((meFont.getStyle() & 1) != 0) {
                style = String.valueOf(style) + "bold,";
            }
            if ((meFont.getStyle() & 2) != 0) {
                style = String.valueOf(style) + "italic,";
            }
            if ((meFont.getStyle() & 2) != 0) {
                style = String.valueOf(style) + "underlined,";
            }
            String style2 = style.substring(0, style.length() - 1);
            int size = 0;
            if (meFont.getSize() == 8) {
                size = SIZE_SMALL;
            } else if (meFont.getSize() == 0) {
                size = SIZE_MEDIUM;
            } else if (meFont.getSize() == 16) {
                size = SIZE_LARGE;
            }
            result = new SwtSystemFont(name, style2, size, this.antialiasing);
            this.fonts.put(new Integer(key), result);
        }
        return result;
    }

    @Override // org.microemu.device.FontManager
    public void init() {
        this.fonts.clear();
    }

    @Override // org.microemu.device.FontManager
    public int charWidth(javax.microedition.lcdui.Font f, char ch) {
        return getFont(f).charWidth(ch);
    }

    @Override // org.microemu.device.FontManager
    public int charsWidth(javax.microedition.lcdui.Font f, char[] ch, int offset, int length) {
        return getFont(f).charsWidth(ch, offset, length);
    }

    @Override // org.microemu.device.FontManager
    public int getBaselinePosition(javax.microedition.lcdui.Font f) {
        return getFont(f).getBaselinePosition();
    }

    @Override // org.microemu.device.FontManager
    public int getHeight(javax.microedition.lcdui.Font f) {
        return getFont(f).getHeight();
    }

    @Override // org.microemu.device.FontManager
    public int stringWidth(javax.microedition.lcdui.Font f, String str) {
        return getFont(f).stringWidth(str);
    }

    public boolean getAntialiasing() {
        return this.antialiasing;
    }

    @Override // org.microemu.device.impl.FontManagerImpl
    public void setAntialiasing(boolean antialiasing) {
        this.antialiasing = antialiasing;
        Enumeration en = this.fonts.elements();
        while (en.hasMoreElements()) {
            SwtFont font = (SwtFont) en.nextElement();
            font.setAntialiasing(antialiasing);
        }
    }

    @Override // org.microemu.device.impl.FontManagerImpl
    public void setFont(String face, String style, String size, Font font) {
        int key = 0;
        if (face.equalsIgnoreCase("system")) {
            key = 0 | 0;
        } else if (face.equalsIgnoreCase("monospace")) {
            key = 0 | 32;
        } else if (face.equalsIgnoreCase("proportional")) {
            key = 0 | 64;
        }
        String testStyle = style.toLowerCase();
        if (testStyle.indexOf("plain") != -1) {
            key |= 0;
        }
        if (testStyle.indexOf("bold") != -1) {
            key |= 1;
        }
        if (testStyle.indexOf("italic") != -1) {
            key |= 2;
        }
        if (testStyle.indexOf("underlined") != -1) {
            key |= 4;
        }
        if (size.equalsIgnoreCase("small")) {
            key |= 8;
        } else if (size.equalsIgnoreCase("medium")) {
            key |= 0;
        } else if (size.equalsIgnoreCase("large")) {
            key |= 16;
        }
        this.fonts.put(new Integer(key), font);
    }

    @Override // org.microemu.device.impl.FontManagerImpl
    public Font createSystemFont(String defName, String defStyle, int defSize, boolean antialiasing) {
        return new SwtSystemFont(defName, defStyle, defSize, antialiasing);
    }

    @Override // org.microemu.device.impl.FontManagerImpl
    public Font createTrueTypeFont(URL defUrl, String defStyle, int defSize, boolean antialiasing) {
        return new SwtTrueTypeFont(defUrl, defStyle, defSize, antialiasing);
    }
}
