package org.microemu.device;

/* loaded from: avacs.jar:org/microemu/device/DeviceFactory.class */
public class DeviceFactory {
    private static Device device;

    public static Device getDevice() {
        return device;
    }

    public static void setDevice(Device device2) {
        if (device != null) {
            device.destroy();
        }
        device2.init();
        device = device2;
    }
}
