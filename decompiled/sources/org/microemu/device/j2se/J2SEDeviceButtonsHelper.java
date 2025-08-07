package org.microemu.device.j2se;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.ButtonName;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.impl.SoftButton;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/device/j2se/J2SEDeviceButtonsHelper.class */
public class J2SEDeviceButtonsHelper {
    private static Map devices = new WeakHashMap();

    /* loaded from: avacs.jar:org/microemu/device/j2se/J2SEDeviceButtonsHelper$DeviceInformation.class */
    private static class DeviceInformation {
        Map keyboardKeyCodes;
        Map keyboardCharCodes;
        Map functions;

        private DeviceInformation() {
            this.keyboardKeyCodes = new HashMap();
            this.keyboardCharCodes = new HashMap();
            this.functions = new HashMap();
        }

        /* synthetic */ DeviceInformation(DeviceInformation deviceInformation) {
            this();
        }
    }

    public static SoftButton getSoftButton(MouseEvent ev) {
        Rectangle pb;
        Iterator it = DeviceFactory.getDevice().getSoftButtons().iterator();
        while (it.hasNext()) {
            SoftButton button = (SoftButton) it.next();
            if (button.isVisible() && (pb = button.getPaintable()) != null && pb.contains(ev.getX(), ev.getY())) {
                return button;
            }
        }
        return null;
    }

    public static J2SEButton getSkinButton(MouseEvent ev) {
        Enumeration en = DeviceFactory.getDevice().getButtons().elements();
        while (en.hasMoreElements()) {
            J2SEButton button = (J2SEButton) en.nextElement();
            if (button.getShape() != null && button.getShape().contains(ev.getX(), ev.getY())) {
                return button;
            }
        }
        return null;
    }

    public static J2SEButton getButton(KeyEvent ev) {
        DeviceInformation inf = getDeviceInformation();
        J2SEButton button = (J2SEButton) inf.keyboardCharCodes.get(new Integer(ev.getKeyChar()));
        if (button != null) {
            return button;
        }
        return (J2SEButton) inf.keyboardKeyCodes.get(new Integer(ev.getKeyCode()));
    }

    public static J2SEButton getButton(ButtonName functionalName) {
        DeviceInformation inf = getDeviceInformation();
        return (J2SEButton) inf.functions.get(functionalName);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Class<org.microemu.device.j2se.J2SEDeviceButtonsHelper>] */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    private static DeviceInformation getDeviceInformation() {
        Device dev = DeviceFactory.getDevice();
        ?? r0 = J2SEDeviceButtonsHelper.class;
        synchronized (r0) {
            DeviceInformation inf = (DeviceInformation) devices.get(dev);
            if (inf == null) {
                inf = createDeviceInformation(dev);
            }
            r0 = r0;
            return inf;
        }
    }

    private static DeviceInformation createDeviceInformation(Device dev) {
        DeviceInformation inf = new DeviceInformation(null);
        boolean hasModeChange = false;
        Enumeration en = dev.getButtons().elements();
        while (en.hasMoreElements()) {
            J2SEButton button = (J2SEButton) en.nextElement();
            int[] keyCodes = button.getKeyboardKeyCodes();
            for (int i : keyCodes) {
                inf.keyboardKeyCodes.put(new Integer(i), button);
            }
            char[] charCodes = button.getKeyboardCharCodes();
            for (char c : charCodes) {
                inf.keyboardCharCodes.put(new Integer(c), button);
            }
            inf.functions.put(button.getFunctionalName(), button);
            if (button.isModeChange()) {
                hasModeChange = true;
            }
        }
        if (!hasModeChange) {
            J2SEButton button2 = (J2SEButton) inf.functions.get(ButtonName.KEY_POUND);
            if (button2 != null) {
                button2.setModeChange();
            } else {
                Logger.warn("Device has no ModeChange and POUND buttons");
            }
        }
        if (inf.functions.get(ButtonName.DELETE) == null) {
            dev.getButtons().add(new J2SEButton(ButtonName.DELETE));
        }
        if (inf.functions.get(ButtonName.BACK_SPACE) == null) {
            dev.getButtons().add(new J2SEButton(ButtonName.BACK_SPACE));
        }
        return inf;
    }
}
