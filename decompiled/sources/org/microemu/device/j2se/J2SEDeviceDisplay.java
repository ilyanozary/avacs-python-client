package org.microemu.device.j2se;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MediaTracker;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import org.microemu.DisplayAccess;
import org.microemu.EmulatorContext;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.app.util.IOUtils;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.MutableImage;
import org.microemu.device.impl.Button;
import org.microemu.device.impl.DeviceDisplayImpl;
import org.microemu.device.impl.PositionedImage;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.impl.SoftButton;

/* loaded from: avacs.jar:org/microemu/device/j2se/J2SEDeviceDisplay.class */
public class J2SEDeviceDisplay implements DeviceDisplayImpl {
    EmulatorContext context;
    Rectangle displayRectangle;
    Rectangle displayPaintable;
    boolean isColor;
    int numColors;
    int numAlphaLevels;
    Color backgroundColor;
    Color foregroundColor;
    PositionedImage mode123Image;
    PositionedImage modeAbcUpperImage;
    PositionedImage modeAbcLowerImage;
    boolean resizable;

    public J2SEDeviceDisplay(EmulatorContext context) {
        this.context = context;
    }

    @Override // org.microemu.device.DeviceDisplay
    public MutableImage getDisplayImage() {
        return this.context.getDisplayComponent().getDisplayImage();
    }

    @Override // org.microemu.device.DeviceDisplay
    public int getHeight() {
        return this.displayPaintable.height;
    }

    @Override // org.microemu.device.DeviceDisplay
    public int getWidth() {
        return this.displayPaintable.width;
    }

    @Override // org.microemu.device.DeviceDisplay
    public int getFullHeight() {
        return this.displayRectangle.height;
    }

    @Override // org.microemu.device.DeviceDisplay
    public int getFullWidth() {
        return this.displayRectangle.width;
    }

    @Override // org.microemu.device.DeviceDisplay
    public boolean isColor() {
        return this.isColor;
    }

    @Override // org.microemu.device.DeviceDisplay
    public boolean isFullScreenMode() {
        DisplayAccess da;
        MIDletAccess ma = MIDletBridge.getMIDletAccess();
        if (ma == null || (da = ma.getDisplayAccess()) == null) {
            return false;
        }
        return da.isFullScreenMode();
    }

    @Override // org.microemu.device.DeviceDisplay
    public int numAlphaLevels() {
        return this.numAlphaLevels;
    }

    @Override // org.microemu.device.DeviceDisplay
    public int numColors() {
        return this.numColors;
    }

    public void paintControls(Graphics g) {
        Device device = DeviceFactory.getDevice();
        g.setColor(this.backgroundColor);
        g.fillRect(0, 0, this.displayRectangle.width, this.displayPaintable.y);
        g.fillRect(0, this.displayPaintable.y, this.displayPaintable.x, this.displayPaintable.height);
        g.fillRect(this.displayPaintable.x + this.displayPaintable.width, this.displayPaintable.y, (this.displayRectangle.width - this.displayPaintable.x) - this.displayPaintable.width, this.displayPaintable.height);
        g.fillRect(0, this.displayPaintable.y + this.displayPaintable.height, this.displayRectangle.width, (this.displayRectangle.height - this.displayPaintable.y) - this.displayPaintable.height);
        g.setColor(this.foregroundColor);
        Enumeration s = device.getSoftButtons().elements();
        while (s.hasMoreElements()) {
            ((J2SESoftButton) s.nextElement()).paint(g);
        }
        int inputMode = device.getInputMethod().getInputMode();
        if (inputMode == 1) {
            g.drawImage(((J2SEImmutableImage) this.mode123Image.getImage()).getImage(), this.mode123Image.getRectangle().x, this.mode123Image.getRectangle().y, (ImageObserver) null);
        } else if (inputMode == 2) {
            g.drawImage(((J2SEImmutableImage) this.modeAbcUpperImage.getImage()).getImage(), this.modeAbcUpperImage.getRectangle().x, this.modeAbcUpperImage.getRectangle().y, (ImageObserver) null);
        } else if (inputMode == 3) {
            g.drawImage(((J2SEImmutableImage) this.modeAbcLowerImage.getImage()).getImage(), this.modeAbcLowerImage.getRectangle().x, this.modeAbcLowerImage.getRectangle().y, (ImageObserver) null);
        }
    }

    public void paintDisplayable(Graphics g, int x, int y, int width, int height) {
        DisplayAccess da;
        Displayable current;
        MIDletAccess ma = MIDletBridge.getMIDletAccess();
        if (ma == null || (da = ma.getDisplayAccess()) == null || (current = da.getCurrent()) == null) {
            return;
        }
        g.setColor(this.foregroundColor);
        Shape oldclip = g.getClip();
        if (!(current instanceof Canvas) || ((Canvas) current).getWidth() != this.displayRectangle.width || ((Canvas) current).getHeight() != this.displayRectangle.height) {
            g.translate(this.displayPaintable.x, this.displayPaintable.y);
        }
        g.setClip(x, y, width, height);
        Font oldf = g.getFont();
        ma.getDisplayAccess().paint(new J2SEDisplayGraphics((Graphics2D) g, getDisplayImage()));
        g.setFont(oldf);
        if (!(current instanceof Canvas) || ((Canvas) current).getWidth() != this.displayRectangle.width || ((Canvas) current).getHeight() != this.displayRectangle.height) {
            g.translate(-this.displayPaintable.x, -this.displayPaintable.y);
        }
        g.setClip(oldclip);
    }

    @Override // org.microemu.device.DeviceDisplay
    public void repaint(int x, int y, int width, int height) {
        this.context.getDisplayComponent().repaintRequest(x, y, width, height);
    }

    @Override // org.microemu.device.DeviceDisplay
    public void setScrollDown(boolean state) {
        Enumeration en = DeviceFactory.getDevice().getSoftButtons().elements();
        while (en.hasMoreElements()) {
            SoftButton button = (SoftButton) en.nextElement();
            if (button.getType() == 2 && button.getName().equals("down")) {
                button.setVisible(state);
            }
        }
    }

    @Override // org.microemu.device.DeviceDisplay
    public void setScrollUp(boolean state) {
        Enumeration en = DeviceFactory.getDevice().getSoftButtons().elements();
        while (en.hasMoreElements()) {
            SoftButton button = (SoftButton) en.nextElement();
            if (button.getType() == 2 && button.getName().equals("up")) {
                button.setVisible(state);
            }
        }
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public boolean isResizable() {
        return this.resizable;
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setResizable(boolean state) {
        this.resizable = state;
    }

    public Rectangle getDisplayRectangle() {
        return this.displayRectangle;
    }

    public Rectangle getDisplayPaintable() {
        return this.displayPaintable;
    }

    public org.microemu.device.impl.Color getBackgroundColor() {
        return new org.microemu.device.impl.Color(this.backgroundColor.getRGB());
    }

    public org.microemu.device.impl.Color getForegroundColor() {
        return new org.microemu.device.impl.Color(this.foregroundColor.getRGB());
    }

    @Override // org.microemu.device.DeviceDisplay
    public Image createImage(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        return new J2SEMutableImage(width, height);
    }

    @Override // org.microemu.device.DeviceDisplay
    public Image createImage(String name) throws IOException {
        return getImage(name);
    }

    @Override // org.microemu.device.DeviceDisplay
    public Image createImage(Image source) {
        if (source.isMutable()) {
            return new J2SEImmutableImage((J2SEMutableImage) source);
        }
        return source;
    }

    @Override // org.microemu.device.DeviceDisplay
    public Image createImage(InputStream is) throws IOException {
        if (is == null) {
            throw new IOException();
        }
        return getImage(is);
    }

    @Override // org.microemu.device.DeviceDisplay
    public Image createRGBImage(int[] rgb, int width, int height, boolean processAlpha) {
        if (rgb == null) {
            throw new NullPointerException();
        }
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException();
        }
        BufferedImage img = new BufferedImage(width, height, 2);
        if (!processAlpha) {
            int l = rgb.length;
            int[] rgbAux = new int[l];
            for (int i = 0; i < l; i++) {
                rgbAux[i] = rgb[i] | (-16777216);
            }
            rgb = rgbAux;
        }
        img.setRGB(0, 0, width, height, rgb, 0, width);
        ImageFilter filter = null;
        if (isColor()) {
            if (this.backgroundColor.getRed() != 255 || this.backgroundColor.getGreen() != 255 || this.backgroundColor.getBlue() != 255 || this.foregroundColor.getRed() != 0 || this.foregroundColor.getGreen() != 0 || this.foregroundColor.getBlue() != 0) {
                filter = new RGBImageFilter();
            }
        } else if (numColors() == 2) {
            filter = new BWImageFilter();
        } else {
            filter = new GrayImageFilter();
        }
        if (filter != null) {
            FilteredImageSource imageSource = new FilteredImageSource(img.getSource(), filter);
            return new J2SEImmutableImage(Toolkit.getDefaultToolkit().createImage(imageSource));
        }
        return new J2SEImmutableImage((java.awt.Image) img);
    }

    @Override // org.microemu.device.DeviceDisplay
    public Image createImage(Image image, int x, int y, int width, int height, int transform) {
        int offset;
        int colIncr;
        int rowIncr;
        if (image == null) {
            throw new NullPointerException();
        }
        if (x + width > image.getWidth() || y + height > image.getHeight() || width <= 0 || height <= 0 || x < 0 || y < 0) {
            throw new IllegalArgumentException("Area out of Image");
        }
        int[] rgbData = new int[height * width];
        int[] rgbTransformedData = new int[height * width];
        if (image instanceof J2SEImmutableImage) {
            ((J2SEImmutableImage) image).getRGB(rgbData, 0, width, x, y, width, height);
        } else {
            ((J2SEMutableImage) image).getRGB(rgbData, 0, width, x, y, width, height);
        }
        switch (transform) {
            case 0:
                offset = 0;
                colIncr = 1;
                rowIncr = 0;
                break;
            case 1:
                offset = (height - 1) * width;
                colIncr = 1;
                rowIncr = -(width << 1);
                break;
            case 2:
                offset = width - 1;
                colIncr = -1;
                rowIncr = width << 1;
                break;
            case 3:
                offset = (height * width) - 1;
                colIncr = -1;
                rowIncr = 0;
                break;
            case 4:
                offset = 0;
                colIncr = width;
                rowIncr = (-(height * width)) + 1;
                width = height;
                height = width;
                break;
            case 5:
                offset = (height - 1) * width;
                colIncr = -width;
                rowIncr = (height * width) + 1;
                width = height;
                height = width;
                break;
            case 6:
                offset = width - 1;
                colIncr = width;
                rowIncr = (-(height * width)) - 1;
                width = height;
                height = width;
                break;
            case 7:
                offset = (height * width) - 1;
                colIncr = -width;
                rowIncr = (height * width) - 1;
                width = height;
                height = width;
                break;
            default:
                throw new IllegalArgumentException("Bad transform");
        }
        int row = 0;
        int i = 0;
        while (row < height) {
            int col = 0;
            while (col < width) {
                rgbTransformedData[i] = rgbData[offset];
                col++;
                offset += colIncr;
                i++;
            }
            row++;
            offset += rowIncr;
        }
        return createRGBImage(rgbTransformedData, width, height, true);
    }

    @Override // org.microemu.device.DeviceDisplay
    public Image createImage(byte[] imageData, int imageOffset, int imageLength) {
        ByteArrayInputStream is = new ByteArrayInputStream(imageData, imageOffset, imageLength);
        try {
            return getImage(is);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex.toString());
        }
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setNumAlphaLevels(int i) {
        this.numAlphaLevels = i;
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setNumColors(int i) {
        this.numColors = i;
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setIsColor(boolean b) {
        this.isColor = b;
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setBackgroundColor(org.microemu.device.impl.Color color) {
        this.backgroundColor = new Color(color.getRGB());
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setForegroundColor(org.microemu.device.impl.Color color) {
        this.foregroundColor = new Color(color.getRGB());
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setDisplayRectangle(Rectangle rectangle) {
        this.displayRectangle = rectangle;
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setDisplayPaintable(Rectangle rectangle) {
        this.displayPaintable = rectangle;
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setMode123Image(PositionedImage object) {
        this.mode123Image = object;
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setModeAbcLowerImage(PositionedImage object) {
        this.modeAbcLowerImage = object;
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public void setModeAbcUpperImage(PositionedImage object) {
        this.modeAbcUpperImage = object;
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public Image createSystemImage(URL url) throws IOException {
        java.awt.Image resultImage = Toolkit.getDefaultToolkit().createImage(url);
        MediaTracker mediaTracker = new MediaTracker(new java.awt.Canvas());
        mediaTracker.addImage(resultImage, 0);
        try {
            mediaTracker.waitForID(0);
        } catch (InterruptedException e) {
        }
        if (mediaTracker.isErrorID(0)) {
            throw new IOException();
        }
        return new J2SEImmutableImage(resultImage);
    }

    private Image getImage(String str) throws IOException {
        Object midlet = MIDletBridge.getCurrentMIDlet();
        if (midlet == null) {
            midlet = getClass();
        }
        InputStream is = midlet.getClass().getResourceAsStream(str);
        if (is == null) {
            throw new IOException(String.valueOf(str) + " could not be found.");
        }
        try {
            return getImage(is);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private Image getImage(InputStream is) throws IOException {
        java.awt.Image resultImage;
        byte[] imageBytes = new byte[1024];
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        while (true) {
            int num = is.read(imageBytes);
            if (num == -1) {
                break;
            }
            ba.write(imageBytes, 0, num);
        }
        java.awt.Image image = Toolkit.getDefaultToolkit().createImage(ba.toByteArray());
        ImageFilter filter = null;
        if (isColor()) {
            if (this.backgroundColor.getRed() != 255 || this.backgroundColor.getGreen() != 255 || this.backgroundColor.getBlue() != 255 || this.foregroundColor.getRed() != 0 || this.foregroundColor.getGreen() != 0 || this.foregroundColor.getBlue() != 0) {
                filter = new RGBImageFilter();
            }
        } else if (numColors() == 2) {
            filter = new BWImageFilter();
        } else {
            filter = new GrayImageFilter();
        }
        if (filter != null) {
            FilteredImageSource imageSource = new FilteredImageSource(image.getSource(), filter);
            resultImage = Toolkit.getDefaultToolkit().createImage(imageSource);
        } else {
            resultImage = image;
        }
        MediaTracker mediaTracker = new MediaTracker(new java.awt.Canvas());
        mediaTracker.addImage(resultImage, 0);
        try {
            mediaTracker.waitForID(0);
        } catch (InterruptedException e) {
        }
        if (mediaTracker.isErrorID(0)) {
            throw new IOException();
        }
        return new J2SEImmutableImage(resultImage);
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public Button createButton(int skinVersion, String name, org.microemu.device.impl.Shape shape, int keyCode, String keyboardKeys, String keyboardChars, Hashtable inputToChars, boolean modeChange) {
        return new J2SEButton(skinVersion, name, shape, keyCode, keyboardKeys, keyboardChars, inputToChars, modeChange);
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public SoftButton createSoftButton(int skinVersion, String name, org.microemu.device.impl.Shape shape, int keyCode, String keyboardKeys, Rectangle paintable, String alignmentName, Vector commands, javax.microedition.lcdui.Font font) {
        return new J2SESoftButton(skinVersion, name, shape, keyCode, keyboardKeys, paintable, alignmentName, commands, font);
    }

    @Override // org.microemu.device.impl.DeviceDisplayImpl
    public SoftButton createSoftButton(int skinVersion, String name, Rectangle paintable, Image normalImage, Image pressedImage) {
        return new J2SESoftButton(skinVersion, name, paintable, normalImage, pressedImage);
    }
}
