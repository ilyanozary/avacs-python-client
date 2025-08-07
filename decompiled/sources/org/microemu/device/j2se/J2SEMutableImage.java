package org.microemu.device.j2se;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import org.microemu.device.MutableImage;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/device/j2se/J2SEMutableImage.class */
public class J2SEMutableImage extends MutableImage {
    private BufferedImage img;
    private PixelGrabber grabber = null;
    private int[] pixels;

    public J2SEMutableImage(int width, int height) {
        this.img = new BufferedImage(width, height, 1);
        Graphics g = this.img.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
    }

    @Override // javax.microedition.lcdui.Image
    public javax.microedition.lcdui.Graphics getGraphics() {
        Graphics2D g = this.img.getGraphics();
        g.setClip(0, 0, getWidth(), getHeight());
        J2SEDisplayGraphics displayGraphics = new J2SEDisplayGraphics(g, this);
        displayGraphics.setColor(0);
        displayGraphics.translate(-displayGraphics.getTranslateX(), -displayGraphics.getTranslateY());
        return displayGraphics;
    }

    @Override // javax.microedition.lcdui.Image
    public boolean isMutable() {
        return true;
    }

    @Override // javax.microedition.lcdui.Image
    public int getHeight() {
        return this.img.getHeight();
    }

    public Image getImage() {
        return this.img;
    }

    @Override // javax.microedition.lcdui.Image
    public int getWidth() {
        return this.img.getWidth();
    }

    @Override // org.microemu.device.MutableImage
    public int[] getData() {
        if (this.grabber == null) {
            this.pixels = new int[getWidth() * getHeight()];
            this.grabber = new PixelGrabber(this.img, 0, 0, getWidth(), getHeight(), this.pixels, 0, getWidth());
        }
        try {
            this.grabber.grabPixels();
        } catch (InterruptedException e) {
            Logger.error((Throwable) e);
        }
        return this.pixels;
    }

    public MutableImage scale(int zoom) {
        BufferedImage scaledImg = new BufferedImage(this.img.getWidth() * zoom, this.img.getHeight() * zoom, this.img.getType());
        Graphics2D imgGraphics = scaledImg.createGraphics();
        imgGraphics.scale(zoom, zoom);
        imgGraphics.drawImage(this.img, 0, 0, (ImageObserver) null);
        J2SEMutableImage scaledMutableImage = new J2SEMutableImage(scaledImg.getWidth(), scaledImg.getHeight());
        scaledMutableImage.img = scaledImg;
        return scaledMutableImage;
    }

    @Override // javax.microedition.lcdui.Image
    public void getRGB(int[] argb, int offset, int scanlength, int x, int y, int width, int height) {
        if (width <= 0 || height <= 0) {
            return;
        }
        if (x < 0 || y < 0 || x + width > getWidth() || y + height > getHeight()) {
            throw new IllegalArgumentException("Specified area exceeds bounds of image");
        }
        if ((scanlength < 0 ? -scanlength : scanlength) < width) {
            throw new IllegalArgumentException("abs value of scanlength is less than width");
        }
        if (argb == null) {
            throw new NullPointerException("null rgbData");
        }
        if (offset < 0 || offset + width > argb.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (scanlength < 0) {
            if (offset + (scanlength * (height - 1)) < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
        } else if (offset + (scanlength * (height - 1)) + width > argb.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        try {
            new PixelGrabber(this.img, x, y, width, height, argb, offset, scanlength).grabPixels();
        } catch (InterruptedException e) {
            Logger.error((Throwable) e);
        }
    }
}
