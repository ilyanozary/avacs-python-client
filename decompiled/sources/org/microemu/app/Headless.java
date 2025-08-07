package org.microemu.app;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.microemu.DisplayComponent;
import org.microemu.EmulatorContext;
import org.microemu.MIDletBridge;
import org.microemu.app.ui.Message;
import org.microemu.app.ui.noui.NoUiDisplayComponent;
import org.microemu.app.util.DeviceEntry;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.j2se.J2SEDevice;
import org.microemu.device.j2se.J2SEDeviceDisplay;
import org.microemu.device.j2se.J2SEFontManager;
import org.microemu.device.j2se.J2SEInputMethod;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/app/Headless.class */
public class Headless {
    private EmulatorContext context = new EmulatorContext() { // from class: org.microemu.app.Headless.1
        private DisplayComponent displayComponent = new NoUiDisplayComponent();
        private InputMethod inputMethod = new J2SEInputMethod();
        private DeviceDisplay deviceDisplay = new J2SEDeviceDisplay(this);
        private FontManager fontManager = new J2SEFontManager();

        @Override // org.microemu.EmulatorContext
        public DisplayComponent getDisplayComponent() {
            return this.displayComponent;
        }

        @Override // org.microemu.EmulatorContext
        public InputMethod getDeviceInputMethod() {
            return this.inputMethod;
        }

        @Override // org.microemu.EmulatorContext
        public DeviceDisplay getDeviceDisplay() {
            return this.deviceDisplay;
        }

        @Override // org.microemu.EmulatorContext
        public FontManager getDeviceFontManager() {
            return this.fontManager;
        }

        @Override // org.microemu.EmulatorContext
        public InputStream getResourceAsStream(String name) {
            return MIDletBridge.getCurrentMIDlet().getClass().getResourceAsStream(name);
        }

        @Override // org.microemu.EmulatorContext
        public boolean platformRequest(final String URL) {
            new Thread(new Runnable() { // from class: org.microemu.app.Headless.1.1
                @Override // java.lang.Runnable
                public void run() {
                    Message.info("MIDlet requests that the device handle the following URL: " + URL);
                }
            }).start();
            return false;
        }
    };
    private Common emulator = new Common(this.context);

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, SecurityException, ConfigurationException, IllegalArgumentException, InvocationTargetException {
        StringBuffer debugArgs = new StringBuffer();
        ArrayList params = new ArrayList();
        params.add("--rms");
        params.add("memory");
        for (int i = 0; i < args.length; i++) {
            params.add(args[i]);
            if (debugArgs.length() != 0) {
                debugArgs.append(", ");
            }
            debugArgs.append("[").append(args[i]).append("]");
        }
        if (args.length > 0) {
            Logger.debug("headless arguments", debugArgs.toString());
        }
        Headless app = new Headless();
        DeviceEntry defaultDevice = new DeviceEntry("Default device", (String) null, DeviceImpl.DEFAULT_LOCATION, true, false);
        app.emulator.initParams(params, defaultDevice, J2SEDevice.class);
        app.emulator.initMIDlet(true);
    }
}
