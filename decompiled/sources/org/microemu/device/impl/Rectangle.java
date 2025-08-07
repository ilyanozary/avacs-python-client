package org.microemu.device.impl;

/* loaded from: avacs.jar:org/microemu/device/impl/Rectangle.class */
public class Rectangle extends Shape {
    private boolean initialized = false;
    public int x;
    public int y;
    public int width;
    public int height;

    public Rectangle() {
    }

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Rectangle rect) {
        this.x = rect.x;
        this.y = rect.y;
        this.width = rect.width;
        this.height = rect.height;
    }

    public void add(int newx, int newy) {
        if (this.initialized) {
            if (newx < this.x) {
                this.width += this.x - newx;
                this.x = newx;
            } else if (newx > this.x + this.width) {
                this.width = newx - this.x;
            }
            if (newy < this.y) {
                this.height += this.y - newy;
                this.y = newy;
                return;
            } else {
                if (newy > this.y + this.height) {
                    this.height = newy - this.y;
                    return;
                }
                return;
            }
        }
        this.x = newx;
        this.y = newy;
        this.initialized = true;
    }

    @Override // org.microemu.device.impl.Shape
    public boolean contains(int x, int y) {
        if (x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height) {
            return true;
        }
        return false;
    }

    @Override // org.microemu.device.impl.Shape
    public Rectangle getBounds() {
        return this;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(this.x).append(",").append(this.y).append(" ").append(this.width).append("x").append(this.height);
        return buf.toString();
    }
}
