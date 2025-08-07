package org.microemu.device.impl;

import javax.microedition.lcdui.Image;

/* loaded from: avacs.jar:org/microemu/device/impl/PositionedImage.class */
public class PositionedImage {
    private Image image;
    private Rectangle rectangle;

    public PositionedImage(Image img, Rectangle arectangle) {
        this.image = img;
        this.rectangle = arectangle;
    }

    public Image getImage() {
        return this.image;
    }

    public Rectangle getRectangle() {
        return this.rectangle;
    }
}
