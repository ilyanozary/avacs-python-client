package javax.microedition.lcdui;

import org.microemu.device.DeviceFactory;

/* loaded from: avacs.jar:javax/microedition/lcdui/StringComponent.class */
class StringComponent {
    private String text;
    private int[] breaks;
    private boolean invertPaint;
    private int numOfBreaks;
    private int width;
    private int widthDecreaser;

    public StringComponent() {
        this(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8 */
    public StringComponent(String text) {
        this.breaks = new int[4];
        this.invertPaint = false;
        ?? r0 = this;
        synchronized (r0) {
            this.width = -1;
            this.widthDecreaser = 0;
            setText(text);
            r0 = r0;
        }
    }

    public int getCharHeight() {
        return Font.getDefaultFont().getHeight();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v9, types: [int] */
    public int getCharPositionX(int num) {
        ?? SubstringWidth = this;
        synchronized (SubstringWidth) {
            if (this.numOfBreaks == -1) {
                updateBreaks();
            }
            int prevIndex = 0;
            Font f = Font.getDefaultFont();
            for (int i = 0; i < this.numOfBreaks && num >= this.breaks[i]; i++) {
                prevIndex = this.breaks[i];
            }
            SubstringWidth = f.substringWidth(this.text, prevIndex, num - prevIndex);
        }
        return SubstringWidth;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v8 */
    public int getCharPositionY(int num) {
        int y = 0;
        ?? r0 = this;
        synchronized (r0) {
            if (this.numOfBreaks == -1) {
                updateBreaks();
            }
            Font f = Font.getDefaultFont();
            for (int i = 0; i < this.numOfBreaks && num >= this.breaks[i]; i++) {
                y += f.getHeight();
            }
            r0 = r0;
            return y;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getHeight() {
        synchronized (this) {
            if (this.numOfBreaks == -1) {
                updateBreaks();
            }
            Font f = Font.getDefaultFont();
            if (this.text == null) {
                return 0;
            }
            if (this.numOfBreaks == 0) {
                return f.getHeight();
            }
            int height = this.numOfBreaks * f.getHeight();
            if (this.breaks[this.numOfBreaks - 1] != this.text.length() - 1 || this.text.charAt(this.text.length() - 1) != '\n') {
                height += f.getHeight();
            }
            return height;
        }
    }

    public String getText() {
        return this.text;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v3 */
    public void invertPaint(boolean state) {
        ?? r0 = this;
        synchronized (r0) {
            this.invertPaint = state;
            r0 = r0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    public int paint(Graphics g) {
        if (this.text == null) {
            return 0;
        }
        ?? r0 = this;
        synchronized (r0) {
            if (this.numOfBreaks == -1) {
                updateBreaks();
            }
            Font f = Font.getDefaultFont();
            int y = 0;
            int prevIndex = 0;
            for (int i = 0; i < this.numOfBreaks; i++) {
                if (this.invertPaint) {
                    g.setGrayScale(0);
                } else {
                    g.setGrayScale(255);
                }
                g.fillRect(0, y, this.width, f.getHeight());
                if (this.invertPaint) {
                    g.setGrayScale(255);
                } else {
                    g.setGrayScale(0);
                }
                g.drawSubstring(this.text, prevIndex, this.breaks[i] - prevIndex, 0, y, 0);
                prevIndex = this.breaks[i];
                y += f.getHeight();
            }
            if (prevIndex != this.text.length() || this.text.length() == 0) {
                if (this.invertPaint) {
                    g.setGrayScale(0);
                } else {
                    g.setGrayScale(255);
                }
                g.fillRect(0, y, this.width, f.getHeight());
                if (this.invertPaint) {
                    g.setGrayScale(255);
                } else {
                    g.setGrayScale(0);
                }
                g.drawSubstring(this.text, prevIndex, this.text.length() - prevIndex, 0, y, 0);
                y += f.getHeight();
            }
            r0 = r0;
            return y;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    public void setText(String text) {
        ?? r0 = this;
        synchronized (r0) {
            this.text = text;
            this.numOfBreaks = -1;
            r0 = r0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    public void setWidthDecreaser(int widthDecreaser) {
        ?? r0 = this;
        synchronized (r0) {
            this.widthDecreaser = widthDecreaser;
            this.numOfBreaks = -1;
            r0 = r0;
        }
    }

    private void insertBreak(int pos) {
        int i = 0;
        while (i < this.numOfBreaks && pos >= this.breaks[i]) {
            i++;
        }
        if (this.numOfBreaks + 1 == this.breaks.length) {
            int[] newbreaks = new int[this.breaks.length + 4];
            System.arraycopy(this.breaks, 0, newbreaks, 0, this.numOfBreaks);
            this.breaks = newbreaks;
        }
        System.arraycopy(this.breaks, i, this.breaks, i + 1, this.numOfBreaks - i);
        this.breaks[i] = pos;
        this.numOfBreaks++;
    }

    private void updateBreaks() {
        if (this.text == null) {
            return;
        }
        this.width = DeviceFactory.getDevice().getDeviceDisplay().getWidth() - this.widthDecreaser;
        int prevIndex = 0;
        int canBreak = 0;
        this.numOfBreaks = 0;
        Font f = Font.getDefaultFont();
        int i = 0;
        while (i < this.text.length()) {
            if (this.text.charAt(i) == ' ') {
                canBreak = i + 1;
            }
            if (this.text.charAt(i) == '\n') {
                insertBreak(i);
                canBreak = 0;
                prevIndex = i + 1;
            } else if (f.substringWidth(this.text, prevIndex, (i - prevIndex) + 1) > this.width) {
                if (canBreak != 0) {
                    insertBreak(canBreak);
                    i = canBreak;
                    prevIndex = i;
                } else {
                    insertBreak(i);
                    prevIndex = i + 1;
                }
                canBreak = 0;
            }
            i++;
        }
    }
}
