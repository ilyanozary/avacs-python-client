package org.microemu.app.ui.noui;

import java.awt.Graphics;
import javax.microedition.lcdui.Displayable;
import org.microemu.DisplayAccess;
import org.microemu.DisplayComponent;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.app.ui.DisplayRepaintListener;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.MutableImage;
import org.microemu.device.j2se.J2SEDeviceDisplay;
import org.microemu.device.j2se.J2SEMutableImage;

/* loaded from: avacs.jar:org/microemu/app/ui/noui/NoUiDisplayComponent.class */
public class NoUiDisplayComponent implements DisplayComponent {
    private J2SEMutableImage displayImage = null;
    private DisplayRepaintListener displayRepaintListener;

    @Override // org.microemu.DisplayComponent
    public void addDisplayRepaintListener(DisplayRepaintListener l) {
        this.displayRepaintListener = l;
    }

    @Override // org.microemu.DisplayComponent
    public void removeDisplayRepaintListener(DisplayRepaintListener l) {
        if (this.displayRepaintListener == l) {
            this.displayRepaintListener = null;
        }
    }

    @Override // org.microemu.DisplayComponent
    public MutableImage getDisplayImage() {
        return this.displayImage;
    }

    @Override // org.microemu.DisplayComponent
    public void repaintRequest(int x, int y, int width, int height) {
        DisplayAccess da;
        Device device;
        MIDletAccess ma = MIDletBridge.getMIDletAccess();
        if (ma == null || (da = ma.getDisplayAccess()) == null) {
            return;
        }
        Displayable current = da.getCurrent();
        if (current != null && (device = DeviceFactory.getDevice()) != null) {
            if (this.displayImage == null) {
                this.displayImage = new J2SEMutableImage(device.getDeviceDisplay().getFullWidth(), device.getDeviceDisplay().getFullHeight());
            }
            Graphics gc = this.displayImage.getImage().getGraphics();
            J2SEDeviceDisplay deviceDisplay = (J2SEDeviceDisplay) device.getDeviceDisplay();
            if (!deviceDisplay.isFullScreenMode()) {
                deviceDisplay.paintControls(gc);
            }
            deviceDisplay.paintDisplayable(gc, x, y, width, height);
            fireDisplayRepaint(this.displayImage);
        }
    }

    private void fireDisplayRepaint(MutableImage image) {
        if (this.displayRepaintListener != null) {
            this.displayRepaintListener.repaintInvoked(image);
        }
    }
}
