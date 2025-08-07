package javax.microedition.lcdui;

import javax.microedition.io.HttpConnection;

/* loaded from: avacs.jar:javax/microedition/lcdui/Gauge.class */
public class Gauge extends Item {
    int value;
    int maxValue;
    boolean interactive;
    public static final int INDEFINITE = -1;
    public static final int CONTINUOUS_IDLE = 0;
    public static final int INCREMENTAL_IDLE = 1;
    public static final int CONTINUOUS_RUNNING = 2;
    public static final int INCREMENTAL_UPDATING = 3;
    private int indefiniteFrame;
    private static final int IDEFINITE_FRAMES = 4;
    int prefWidth;
    int prefHeight;
    static int HEIGHT = 15;
    static int PAINT_TIMEOUT = HttpConnection.HTTP_INTERNAL_ERROR;

    public Gauge(String label, boolean interactive, int maxValue, int initialValue) {
        super(label);
        this.interactive = interactive;
        setMaxValue(maxValue);
        setValue(initialValue);
    }

    public void setValue(int value) {
        if (hasIndefiniteRange()) {
            if (value != 0 && value != 2 && value != 1 && value != 3) {
                throw new IllegalArgumentException();
            }
            if (value == 3 && this.value == 3) {
                updateIndefiniteFrame();
                return;
            } else {
                this.value = value;
                repaint();
                return;
            }
        }
        if (value < 0) {
            value = 0;
        }
        if (value > this.maxValue) {
            value = this.maxValue;
        }
        this.value = value;
        repaint();
    }

    public int getValue() {
        return this.value;
    }

    public void setMaxValue(int maxValue) {
        if (maxValue > 0) {
            this.maxValue = maxValue;
            setValue(getValue());
        } else {
            if (isInteractive()) {
                throw new IllegalArgumentException();
            }
            if (maxValue != -1) {
                throw new IllegalArgumentException();
            }
            if (this.maxValue == -1) {
                return;
            }
            this.maxValue = -1;
            this.value = 1;
            repaint();
        }
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public boolean isInteractive() {
        return this.interactive;
    }

    boolean hasIndefiniteRange() {
        return !isInteractive() && getMaxValue() == -1;
    }

    void updateIndefiniteFrame() {
        if (hasIndefiniteRange()) {
            if (getValue() == 2 || getValue() == 3) {
                if (this.indefiniteFrame + 1 < 4) {
                    this.indefiniteFrame++;
                } else {
                    this.indefiniteFrame = 0;
                }
                repaint();
            }
        }
    }

    @Override // javax.microedition.lcdui.Item
    int getHeight() {
        return super.getHeight() + HEIGHT;
    }

    @Override // javax.microedition.lcdui.Item
    boolean isFocusable() {
        return this.interactive;
    }

    @Override // javax.microedition.lcdui.Item
    void keyPressed(int keyCode) {
        if (Display.getGameAction(keyCode) == 2 && this.value > 0) {
            this.value--;
            repaint();
        } else if (Display.getGameAction(keyCode) == 5 && this.value < this.maxValue) {
            this.value++;
            repaint();
        }
    }

    @Override // javax.microedition.lcdui.Item
    int paint(Graphics g) {
        super.paintContent(g);
        g.translate(0, super.getHeight());
        if (hasFocus()) {
            g.drawRect(2, 2, this.owner.getWidth() - 5, HEIGHT - 5);
        }
        if (hasIndefiniteRange()) {
            if (getValue() == 0 || getValue() == 1) {
                int width = this.owner.getWidth() - 9;
                g.drawRect(4, 4, width, HEIGHT - 9);
            } else {
                int width2 = ((this.owner.getWidth() - 8) << 1) / 4;
                int offset = (width2 >>> 1) * this.indefiniteFrame;
                int width22 = 0;
                if (offset + width2 > this.owner.getWidth() - 8) {
                    width22 = (offset + width2) - (this.owner.getWidth() - 8);
                    width2 -= width22;
                }
                g.fillRect(4 + offset, 4, width2, HEIGHT - 8);
                if (width22 != 0) {
                    g.fillRect(4, 4, width22, HEIGHT - 8);
                }
            }
        } else {
            int width3 = ((this.owner.getWidth() - 8) * this.value) / this.maxValue;
            g.fillRect(4, 4, width3, HEIGHT - 8);
        }
        g.translate(0, -super.getHeight());
        return getHeight();
    }

    @Override // javax.microedition.lcdui.Item
    int traverse(int gameKeyCode, int top, int bottom, boolean action) {
        if (gameKeyCode == 1) {
            if (top > 0) {
                return -top;
            }
            return Integer.MAX_VALUE;
        }
        if (gameKeyCode == 6) {
            if (getHeight() > bottom) {
                return getHeight() - bottom;
            }
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override // javax.microedition.lcdui.Item
    public void setPreferredSize(int w, int h) {
        Screen owner = getOwner();
        if (owner != null && (owner instanceof Alert)) {
            return;
        }
        super.setPreferredSize(w, h);
    }

    @Override // javax.microedition.lcdui.Item
    public void setLayout(int layout) {
        if (this.owner != null && (this.owner instanceof Alert)) {
            return;
        }
        super.setLayout(layout);
    }

    @Override // javax.microedition.lcdui.Item
    public void setLabel(String label) {
        if (this.owner != null && (this.owner instanceof Alert)) {
            return;
        }
        super.setLabel(label);
    }

    @Override // javax.microedition.lcdui.Item
    public void addCommand(Command cmd) {
        if (this.owner != null && (this.owner instanceof Alert)) {
            return;
        }
        super.addCommand(cmd);
    }

    @Override // javax.microedition.lcdui.Item
    public void setDefaultCommand(Command cmd) {
        if (this.owner != null && (this.owner instanceof Alert)) {
            return;
        }
        super.setDefaultCommand(cmd);
    }

    @Override // javax.microedition.lcdui.Item
    public void setItemCommandListener(ItemCommandListener l) {
        if (this.owner != null && (this.owner instanceof Alert)) {
            return;
        }
        super.setItemCommandListener(l);
    }
}
