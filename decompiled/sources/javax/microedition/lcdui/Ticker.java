package javax.microedition.lcdui;

import org.microemu.DisplayAccess;
import org.microemu.MIDletBridge;

/* loaded from: avacs.jar:javax/microedition/lcdui/Ticker.class */
public class Ticker {
    static int PAINT_TIMEOUT = 250;
    static int PAINT_MOVE = 5;
    static int PAINT_GAP = 10;
    Ticker instance;
    String text;
    int textPos = 0;
    int resetTextPosTo = -1;

    public Ticker(String str) {
        this.instance = null;
        if (str == null) {
            throw new NullPointerException();
        }
        this.instance = this;
        this.text = str;
    }

    public String getString() {
        return this.text;
    }

    public void setString(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        this.text = str;
    }

    int getHeight() {
        return Font.getDefaultFont().getHeight();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v2, types: [javax.microedition.lcdui.Ticker] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    int paintContent(Graphics g) {
        Font f = Font.getDefaultFont();
        ?? r0 = this.instance;
        synchronized (r0) {
            int stringWidth = f.stringWidth(this.text) + PAINT_GAP;
            g.drawString(this.text, this.textPos, 0, 20);
            DisplayAccess da = MIDletBridge.getMIDletAccess().getDisplayAccess();
            for (int xPos = this.textPos + stringWidth; xPos < da.getCurrent().getWidth(); xPos += stringWidth) {
                g.drawString(this.text, xPos, 0, 20);
            }
            if (this.textPos + stringWidth < 0) {
                this.resetTextPosTo = this.textPos + stringWidth;
            }
            r0 = r0;
            return f.getHeight();
        }
    }
}
