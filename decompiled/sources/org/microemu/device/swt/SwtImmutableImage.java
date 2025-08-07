package org.microemu.device.swt;

import javax.microedition.lcdui.Image;
import org.eclipse.swt.graphics.ImageData;
import org.microemu.app.ui.swt.SwtDeviceComponent;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtImmutableImage.class */
public class SwtImmutableImage extends Image {
    org.eclipse.swt.graphics.Image img;

    public SwtImmutableImage(org.eclipse.swt.graphics.Image image) {
        this.img = image;
    }

    public SwtImmutableImage(SwtMutableImage image) {
        this.img = SwtDeviceComponent.createImage(image.getImage());
    }

    @Override // javax.microedition.lcdui.Image
    public int getHeight() {
        return this.img.getBounds().height;
    }

    public org.eclipse.swt.graphics.Image getImage() {
        return this.img;
    }

    @Override // javax.microedition.lcdui.Image
    public int getWidth() {
        return this.img.getBounds().width;
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
        ImageData imageData = this.img.getImageData();
        for (int i = 0; i < height; i++) {
            imageData.getPixels(x, y + i, width, argb, offset + (i * scanlength));
        }
    }
}
