package org.microemu.device.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Image;
import org.microemu.device.DeviceDisplay;

/* loaded from: avacs.jar:org/microemu/device/impl/DeviceDisplayImpl.class */
public interface DeviceDisplayImpl extends DeviceDisplay {
    Image createSystemImage(URL url) throws IOException;

    Button createButton(int i, String str, Shape shape, int i2, String str2, String str3, Hashtable hashtable, boolean z);

    SoftButton createSoftButton(int i, String str, Shape shape, int i2, String str2, Rectangle rectangle, String str3, Vector vector, javax.microedition.lcdui.Font font);

    SoftButton createSoftButton(int i, String str, Rectangle rectangle, Image image, Image image2);

    void setNumColors(int i);

    void setIsColor(boolean z);

    void setNumAlphaLevels(int i);

    void setBackgroundColor(Color color);

    void setForegroundColor(Color color);

    void setDisplayRectangle(Rectangle rectangle);

    void setDisplayPaintable(Rectangle rectangle);

    void setMode123Image(PositionedImage positionedImage);

    void setModeAbcLowerImage(PositionedImage positionedImage);

    void setModeAbcUpperImage(PositionedImage positionedImage);

    boolean isResizable();

    void setResizable(boolean z);
}
