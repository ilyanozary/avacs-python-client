package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;

/* loaded from: avacs.jar:javax/microedition/lcdui/game/Layer.class */
public abstract class Layer {
    private int width;
    private int height;
    private int x;
    private int y;
    private boolean visible;

    public abstract void paint(Graphics graphics);

    Layer(int x, int y, int width, int height, boolean visible) {
        setSize(width, height);
        setPosition(x, y);
        setVisible(visible);
    }

    void setSize(int width, int height) {
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final boolean isVisible() {
        return this.visible;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    public void move(int dx, int dy) {
        ?? r0 = this;
        synchronized (r0) {
            this.x += dx;
            this.y += dy;
            r0 = r0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    public void setPosition(int x, int y) {
        ?? r0 = this;
        synchronized (r0) {
            this.x = x;
            this.y = y;
            r0 = r0;
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
