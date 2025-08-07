package org.microemu.device.j2se;

import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import javax.microedition.lcdui.Image;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/device/j2se/J2SEImmutableImage.class */
public class J2SEImmutableImage extends Image {
    private java.awt.Image img;
    private int width = -1;
    private int height = -1;

    public J2SEImmutableImage(java.awt.Image image) {
        this.img = image;
    }

    public J2SEImmutableImage(J2SEMutableImage image) {
        this.img = Toolkit.getDefaultToolkit().createImage(image.getImage().getSource());
    }

    @Override // javax.microedition.lcdui.Image
    public int getHeight() {
        if (this.height == -1) {
            Throwable th = new ImageObserver() { // from class: org.microemu.device.j2se.J2SEImmutableImage.1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r0v10 */
                /* JADX WARN: Type inference failed for: r0v5 */
                /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Throwable] */
                public boolean imageUpdate(java.awt.Image img, int infoflags, int x, int y, int width, int height) {
                    if ((infoflags & 1) != 0) {
                        J2SEImmutableImage.this.width = width;
                    }
                    if ((infoflags & 2) != 0) {
                        ?? r0 = this;
                        synchronized (r0) {
                            J2SEImmutableImage.this.height = height;
                            notify();
                            r0 = r0;
                            return false;
                        }
                    }
                    return true;
                }
            };
            Throwable th2 = th;
            synchronized (th2) {
                try {
                    this.height = this.img.getHeight(th);
                } catch (NullPointerException e) {
                }
                if (this.height == -1) {
                    try {
                        th.wait();
                    } catch (InterruptedException e2) {
                    }
                }
                th2 = th2;
            }
        }
        return this.height;
    }

    public java.awt.Image getImage() {
        return this.img;
    }

    @Override // javax.microedition.lcdui.Image
    public int getWidth() {
        if (this.width == -1) {
            Throwable th = new ImageObserver() { // from class: org.microemu.device.j2se.J2SEImmutableImage.2
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r0v10 */
                /* JADX WARN: Type inference failed for: r0v5 */
                /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Throwable] */
                public boolean imageUpdate(java.awt.Image img, int infoflags, int x, int y, int width, int height) {
                    if ((infoflags & 2) != 0) {
                        J2SEImmutableImage.this.height = height;
                    }
                    if ((infoflags & 1) != 0) {
                        ?? r0 = this;
                        synchronized (r0) {
                            J2SEImmutableImage.this.width = width;
                            notify();
                            r0 = r0;
                            return false;
                        }
                    }
                    return true;
                }
            };
            Throwable th2 = th;
            synchronized (th2) {
                try {
                    this.width = this.img.getWidth(th);
                } catch (NullPointerException e) {
                }
                if (this.width == -1) {
                    try {
                        th.wait();
                    } catch (InterruptedException e2) {
                    }
                }
                th2 = th2;
            }
        }
        return this.width;
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
