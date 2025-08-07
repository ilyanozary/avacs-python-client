package org.microemu.device.impl;

/* loaded from: avacs.jar:org/microemu/device/impl/Polygon.class */
public class Polygon extends Shape {
    public int npoints;
    public int[] xpoints;
    public int[] ypoints;
    private Rectangle bounds;

    public Polygon() {
        this.bounds = new Rectangle();
    }

    public Polygon(int[] xpoints, int[] ypoints, int npoints) {
        this.bounds = new Rectangle();
        this.xpoints = new int[npoints];
        this.ypoints = new int[npoints];
        this.npoints = npoints;
        System.arraycopy(xpoints, 0, this.xpoints, 0, npoints);
        System.arraycopy(ypoints, 0, this.ypoints, 0, npoints);
        for (int i = 0; i < npoints; i++) {
            this.bounds.add(xpoints[i], ypoints[i]);
        }
    }

    public Polygon(Polygon poly) {
        this(poly.xpoints, poly.ypoints, poly.npoints);
    }

    public void addPoint(int x, int y) {
        if (this.npoints > 0) {
            int[] xtemp = this.xpoints;
            int[] ytemp = this.ypoints;
            this.xpoints = new int[this.npoints + 1];
            System.arraycopy(xtemp, 0, this.xpoints, 0, this.npoints);
            this.ypoints = new int[this.npoints + 1];
            System.arraycopy(ytemp, 0, this.ypoints, 0, this.npoints);
        } else {
            this.xpoints = new int[1];
            this.ypoints = new int[1];
        }
        this.npoints++;
        this.xpoints[this.npoints - 1] = x;
        this.ypoints[this.npoints - 1] = y;
        this.bounds.add(x, y);
    }

    @Override // org.microemu.device.impl.Shape
    public Rectangle getBounds() {
        return this.bounds;
    }

    @Override // org.microemu.device.impl.Shape
    public boolean contains(int x, int y) {
        if (getBounds().contains(x, y)) {
            boolean c = false;
            int i = 0;
            int i2 = this.npoints - 1;
            while (true) {
                int j = i2;
                if (i < this.npoints) {
                    if (((this.ypoints[i] <= y && y < this.ypoints[j]) || (this.ypoints[j] <= y && y < this.ypoints[i])) && x < (((this.xpoints[j] - this.xpoints[i]) * (y - this.ypoints[i])) / (this.ypoints[j] - this.ypoints[i])) + this.xpoints[i]) {
                        c = !c;
                    }
                    i2 = i;
                    i++;
                } else {
                    return c;
                }
            }
        } else {
            return false;
        }
    }
}
