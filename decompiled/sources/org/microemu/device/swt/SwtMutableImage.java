package org.microemu.device.swt;

import javax.microedition.lcdui.Graphics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.microemu.app.ui.swt.SwtDeviceComponent;
import org.microemu.app.ui.swt.SwtGraphics;
import org.microemu.device.MutableImage;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtMutableImage.class */
public class SwtMutableImage extends MutableImage {
    public Image img;
    private GC gc;

    public SwtMutableImage(int width, int height) {
        this.img = SwtDeviceComponent.createImage(width, height);
        this.gc = new GC(this.img);
        SwtDisplayGraphics displayGraphics = new SwtDisplayGraphics(new SwtGraphics(this.gc), this);
        displayGraphics.setColor(16777215);
        displayGraphics.fillRect(0, 0, width, height);
    }

    @Override // javax.microedition.lcdui.Image
    public Graphics getGraphics() {
        SwtDisplayGraphics displayGraphics = new SwtDisplayGraphics(new SwtGraphics(this.gc), this);
        displayGraphics.setColor(0);
        displayGraphics.setClip(0, 0, getWidth(), getHeight());
        displayGraphics.translate(-displayGraphics.getTranslateX(), -displayGraphics.getTranslateY());
        return displayGraphics;
    }

    @Override // javax.microedition.lcdui.Image
    public boolean isMutable() {
        return true;
    }

    @Override // javax.microedition.lcdui.Image
    public int getHeight() {
        return this.img.getBounds().height;
    }

    public Image getImage() {
        return this.img;
    }

    @Override // javax.microedition.lcdui.Image
    public int getWidth() {
        return this.img.getBounds().width;
    }

    @Override // org.microemu.device.MutableImage
    public int[] getData() {
        byte[] tmp = this.img.getImageData().data;
        int[] result = new int[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            result[i] = tmp[i];
        }
        return result;
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
