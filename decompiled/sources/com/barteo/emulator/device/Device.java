package com.barteo.emulator.device;

import com.barteo.emulator.EmulatorContext;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.ui.UIFactory;

/* loaded from: avacs.jar:com/barteo/emulator/device/Device.class */
public class Device extends DeviceImpl {
    public void init(EmulatorContext context) {
        super.init((org.microemu.EmulatorContext) context);
    }

    public void init(EmulatorContext context, String config) {
        super.init((org.microemu.EmulatorContext) context, config);
    }

    @Override // org.microemu.device.Device
    public UIFactory getUIFactory() {
        return null;
    }
}
