package org.microemu.app.ui.swt;

import javax.microedition.lcdui.Displayable;
import org.eclipse.swt.widgets.Canvas;
import org.microemu.DisplayAccess;
import org.microemu.DisplayComponent;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.app.ui.DisplayRepaintListener;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.MutableImage;
import org.microemu.device.swt.SwtDeviceDisplay;
import org.microemu.device.swt.SwtDisplayGraphics;
import org.microemu.device.swt.SwtMutableImage;

/* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtDisplayComponent.class */
public class SwtDisplayComponent implements DisplayComponent {
    private Canvas deviceCanvas;
    private DisplayRepaintListener displayRepaintListener;
    private SwtMutableImage displayImage = null;
    private Runnable redrawRunnable = new Runnable() { // from class: org.microemu.app.ui.swt.SwtDisplayComponent.1
        @Override // java.lang.Runnable
        public void run() {
            if (!SwtDisplayComponent.this.deviceCanvas.isDisposed()) {
                SwtDisplayComponent.this.deviceCanvas.redraw();
            }
        }
    };

    SwtDisplayComponent(Canvas deviceCanvas) {
        this.deviceCanvas = deviceCanvas;
    }

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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    public void paint(SwtGraphics gc) {
        ?? r0 = this;
        synchronized (r0) {
            if (this.displayImage != null) {
                gc.drawImage(this.displayImage.img, 0, 0);
            }
            r0 = r0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v27, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v31 */
    @Override // org.microemu.DisplayComponent
    public void repaintRequest(int x, int y, int width, int height) {
        MIDletAccess ma;
        DisplayAccess da;
        if (this.deviceCanvas.isDisposed() || (ma = MIDletBridge.getMIDletAccess()) == null || (da = ma.getDisplayAccess()) == null) {
            return;
        }
        Displayable current = da.getCurrent();
        if (current == null) {
            return;
        }
        Device device = DeviceFactory.getDevice();
        SwtMutableImage image = new SwtMutableImage(device.getDeviceDisplay().getFullWidth(), device.getDeviceDisplay().getFullHeight());
        SwtGraphics gc = ((SwtDisplayGraphics) image.getGraphics()).g;
        try {
            SwtDeviceDisplay deviceDisplay = (SwtDeviceDisplay) device.getDeviceDisplay();
            deviceDisplay.paintDisplayable(gc, x, y, width, height);
            if (!deviceDisplay.isFullScreenMode()) {
                deviceDisplay.paintControls(gc);
            }
            gc.dispose();
            ?? r0 = this;
            synchronized (r0) {
                if (this.displayImage != null) {
                    this.displayImage.img.dispose();
                }
                this.displayImage = image;
                r0 = r0;
                fireDisplayRepaint(this.displayImage);
                this.deviceCanvas.getDisplay().asyncExec(this.redrawRunnable);
            }
        } catch (Throwable th) {
            gc.dispose();
            throw th;
        }
    }

    private void fireDisplayRepaint(MutableImage image) {
        if (this.displayRepaintListener != null) {
            this.displayRepaintListener.repaintInvoked(image);
        }
    }
}
