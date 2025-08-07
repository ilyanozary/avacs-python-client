package javax.microedition.lcdui;

/* loaded from: avacs.jar:javax/microedition/lcdui/CustomItem.class */
public abstract class CustomItem extends Item {
    protected static final int TRAVERSE_HORIZONTAL = 1;
    protected static final int TRAVERSE_VERTICAL = 2;
    protected static final int KEY_PRESS = 4;
    protected static final int KEY_RELEASE = 8;
    protected static final int KEY_REPEAT = 16;
    protected static final int POINTER_PRESS = 32;
    protected static final int POINTER_RELEASE = 64;
    protected static final int POINTER_DRAG = 128;
    protected static final int NONE = 0;
    int width;
    int height;

    protected abstract int getMinContentHeight();

    protected abstract int getMinContentWidth();

    protected abstract int getPrefContentHeight(int i);

    protected abstract int getPrefContentWidth(int i);

    protected abstract void paint(Graphics graphics, int i, int i2);

    protected CustomItem(String label) {
        super(label);
        this.width = 0;
        this.height = 0;
    }

    public int getGameAction(int keycode) {
        return 0;
    }

    protected final int getInteractionModes() {
        return 0;
    }

    protected void hideNotify() {
    }

    protected final void invalidate() {
        repaintOwner();
    }

    @Override // javax.microedition.lcdui.Item
    protected void keyPressed(int keyCode) {
    }

    protected void keyReleased(int keyCode) {
    }

    protected void keyRepeated(int keyCode) {
    }

    protected void pointerDragged(int x, int y) {
    }

    protected void pointerPressed(int x, int y) {
    }

    protected void pointerReleased(int x, int y) {
    }

    @Override // javax.microedition.lcdui.Item
    protected final void repaint() {
        super.repaint();
    }

    protected final void repaint(int x, int y, int w, int h) {
        repaint();
    }

    protected void showNotify() {
    }

    protected void sizeChanged(int w, int h) {
    }

    protected boolean traverse(int dir, int viewportWidth, int viewportHeight, int[] visRect_inout) {
        return false;
    }

    protected void traverseOut() {
    }

    @Override // javax.microedition.lcdui.Item
    int paint(Graphics g) {
        this.width = getPrefContentWidth(-1);
        this.height = getPrefContentHeight(-1);
        super.paintContent(g);
        g.translate(0, super.getHeight());
        paint(g, this.width, this.height);
        return this.height;
    }

    @Override // javax.microedition.lcdui.Item
    int getHeight() {
        return super.getHeight() + this.height;
    }

    @Override // javax.microedition.lcdui.Item
    int traverse(int gameKeyCode, int top, int bottom, boolean action) {
        Font f = Font.getDefaultFont();
        if (gameKeyCode == 1) {
            if (top > 0) {
                if (top % f.getHeight() == 0) {
                    return -f.getHeight();
                }
                return -(top % f.getHeight());
            }
            return Integer.MAX_VALUE;
        }
        if (gameKeyCode == 6) {
            if (bottom < getHeight()) {
                if (getHeight() - bottom < f.getHeight()) {
                    return getHeight() - bottom;
                }
                return f.getHeight();
            }
            return Integer.MAX_VALUE;
        }
        return 0;
    }
}
