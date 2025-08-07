package org.microemu.device.impl;

/* loaded from: avacs.jar:org/microemu/device/impl/Shape.class */
public abstract class Shape implements Cloneable {
    public abstract Rectangle getBounds();

    public abstract boolean contains(int i, int i2);

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
