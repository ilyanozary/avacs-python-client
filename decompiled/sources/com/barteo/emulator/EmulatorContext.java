package com.barteo.emulator;

import java.io.InputStream;
import org.microemu.DisplayComponent;
import org.microemu.MIDletBridge;
import org.microemu.app.ui.Message;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;

/* loaded from: avacs.jar:com/barteo/emulator/EmulatorContext.class */
public class EmulatorContext implements org.microemu.EmulatorContext {
    private org.microemu.EmulatorContext context;

    public EmulatorContext(org.microemu.EmulatorContext context) {
        this.context = context;
    }

    @Override // org.microemu.EmulatorContext
    public DeviceDisplay getDeviceDisplay() {
        return this.context.getDeviceDisplay();
    }

    @Override // org.microemu.EmulatorContext
    public FontManager getDeviceFontManager() {
        return this.context.getDeviceFontManager();
    }

    @Override // org.microemu.EmulatorContext
    public InputMethod getDeviceInputMethod() {
        return this.context.getDeviceInputMethod();
    }

    @Override // org.microemu.EmulatorContext
    public DisplayComponent getDisplayComponent() {
        return this.context.getDisplayComponent();
    }

    @Override // org.microemu.EmulatorContext
    public InputStream getResourceAsStream(String name) {
        return MIDletBridge.getCurrentMIDlet().getClass().getResourceAsStream(name);
    }

    @Override // org.microemu.EmulatorContext
    public boolean platformRequest(final String URL) {
        new Thread(new Runnable() { // from class: com.barteo.emulator.EmulatorContext.1
            @Override // java.lang.Runnable
            public void run() {
                Message.info("MIDlet requests that the device handle the following URL: " + URL);
            }
        }).start();
        return false;
    }
}
