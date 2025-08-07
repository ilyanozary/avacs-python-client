package org.microemu.device;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Image;

/* loaded from: avacs.jar:org/microemu/device/DeviceDisplay.class */
public interface DeviceDisplay {
    MutableImage getDisplayImage();

    int getWidth();

    int getHeight();

    int getFullWidth();

    int getFullHeight();

    boolean isColor();

    boolean isFullScreenMode();

    int numAlphaLevels();

    int numColors();

    void repaint(int i, int i2, int i3, int i4);

    void setScrollDown(boolean z);

    void setScrollUp(boolean z);

    Image createImage(int i, int i2);

    Image createImage(String str) throws IOException;

    Image createImage(Image image);

    Image createImage(byte[] bArr, int i, int i2);

    Image createImage(InputStream inputStream) throws IOException;

    Image createRGBImage(int[] iArr, int i, int i2, boolean z);

    Image createImage(Image image, int i, int i2, int i3, int i4, int i5);
}
