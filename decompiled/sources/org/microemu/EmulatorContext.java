package org.microemu;

import java.io.InputStream;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;

/* loaded from: avacs.jar:org/microemu/EmulatorContext.class */
public interface EmulatorContext {
    DisplayComponent getDisplayComponent();

    InputMethod getDeviceInputMethod();

    DeviceDisplay getDeviceDisplay();

    FontManager getDeviceFontManager();

    InputStream getResourceAsStream(String str);

    boolean platformRequest(String str);
}
