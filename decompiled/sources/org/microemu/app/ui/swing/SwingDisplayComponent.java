package org.microemu.app.ui.swing;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.ImageObserver;
import java.util.Enumeration;
import java.util.Iterator;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Screen;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import org.microemu.DisplayAccess;
import org.microemu.DisplayComponent;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.app.Common;
import org.microemu.app.ui.DisplayRepaintListener;
import org.microemu.device.Device;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.DeviceFactory;
import org.microemu.device.MutableImage;
import org.microemu.device.impl.ButtonName;
import org.microemu.device.impl.InputMethodImpl;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.impl.SoftButton;
import org.microemu.device.impl.ui.CommandManager;
import org.microemu.device.j2se.J2SEButton;
import org.microemu.device.j2se.J2SEDeviceDisplay;
import org.microemu.device.j2se.J2SEInputMethod;
import org.microemu.device.j2se.J2SEMutableImage;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/SwingDisplayComponent.class */
public class SwingDisplayComponent extends JComponent implements DisplayComponent {
    private static final long serialVersionUID = 1;
    private SwingDeviceComponent deviceComponent;
    private Graphics displayGraphics;
    private SoftButton initialPressedSoftButton;
    private DisplayRepaintListener displayRepaintListener;
    private J2SEMutableImage displayImage = null;
    private boolean showMouseCoordinates = false;
    private Point pressedPoint = new Point();
    private MouseAdapter mouseListener = new MouseAdapter() { // from class: org.microemu.app.ui.swing.SwingDisplayComponent.1
        public void mousePressed(MouseEvent e) {
            Rectangle pb;
            SwingDisplayComponent.this.deviceComponent.requestFocus();
            SwingDisplayComponent.this.pressedPoint = e.getPoint();
            if (MIDletBridge.getCurrentMIDlet() == null) {
                return;
            }
            if (SwingUtilities.isMiddleMouseButton(e)) {
                KeyEvent event = new KeyEvent(SwingDisplayComponent.this.deviceComponent, 0, System.currentTimeMillis(), 0, 10, (char) 65535);
                SwingDisplayComponent.this.deviceComponent.keyPressed(event);
                SwingDisplayComponent.this.deviceComponent.keyReleased(event);
                return;
            }
            Device device = DeviceFactory.getDevice();
            J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();
            boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();
            if (device.hasPointerEvents()) {
                if (!fullScreenMode) {
                    Iterator it = device.getSoftButtons().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        SoftButton button = (SoftButton) it.next();
                        if (button.isVisible() && (pb = button.getPaintable()) != null && pb.contains(e.getX(), e.getY())) {
                            SwingDisplayComponent.this.initialPressedSoftButton = button;
                            button.setPressed(true);
                            SwingDisplayComponent.this.repaintRequest(pb.x, pb.y, pb.width, pb.height);
                            break;
                        }
                    }
                }
                Point p = SwingDisplayComponent.this.deviceCoordinate(device.getDeviceDisplay(), e.getPoint());
                inputMethod.pointerPressed(p.x, p.y);
            }
        }

        public void mouseReleased(MouseEvent e) {
            DisplayAccess da;
            if (MIDletBridge.getCurrentMIDlet() == null) {
                return;
            }
            Device device = DeviceFactory.getDevice();
            J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();
            boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();
            if (device.hasPointerEvents()) {
                if (!fullScreenMode) {
                    if (SwingDisplayComponent.this.initialPressedSoftButton != null && SwingDisplayComponent.this.initialPressedSoftButton.isPressed()) {
                        SwingDisplayComponent.this.initialPressedSoftButton.setPressed(false);
                        Rectangle pb = SwingDisplayComponent.this.initialPressedSoftButton.getPaintable();
                        if (pb != null) {
                            SwingDisplayComponent.this.repaintRequest(pb.x, pb.y, pb.width, pb.height);
                            if (pb.contains(e.getX(), e.getY())) {
                                MIDletAccess ma = MIDletBridge.getMIDletAccess();
                                if (ma == null || (da = ma.getDisplayAccess()) == null) {
                                    return;
                                }
                                Displayable d = da.getCurrent();
                                Command cmd = SwingDisplayComponent.this.initialPressedSoftButton.getCommand();
                                if (cmd != null) {
                                    if (cmd.equals(CommandManager.CMD_MENU)) {
                                        CommandManager.getInstance().commandAction(cmd);
                                    } else {
                                        da.commandAction(cmd, d);
                                    }
                                } else if (d != null && (d instanceof Screen)) {
                                    if (!SwingDisplayComponent.this.initialPressedSoftButton.getName().equals("up")) {
                                        if (SwingDisplayComponent.this.initialPressedSoftButton.getName().equals("down")) {
                                            da.keyPressed(SwingDisplayComponent.this.getButtonByButtonName(ButtonName.DOWN).getKeyCode());
                                        }
                                    } else {
                                        da.keyPressed(SwingDisplayComponent.this.getButtonByButtonName(ButtonName.UP).getKeyCode());
                                    }
                                }
                            }
                        }
                    }
                    SwingDisplayComponent.this.initialPressedSoftButton = null;
                }
                Point p = SwingDisplayComponent.this.deviceCoordinate(device.getDeviceDisplay(), e.getPoint());
                inputMethod.pointerReleased(p.x, p.y);
            }
        }
    };
    private MouseMotionListener mouseMotionListener = new MouseMotionListener() { // from class: org.microemu.app.ui.swing.SwingDisplayComponent.2
        public void mouseDragged(MouseEvent e) {
            Rectangle pb;
            if (SwingDisplayComponent.this.showMouseCoordinates) {
                StringBuffer buf = new StringBuffer();
                int width = e.getX() - SwingDisplayComponent.this.pressedPoint.x;
                int height = e.getY() - SwingDisplayComponent.this.pressedPoint.y;
                Point p = SwingDisplayComponent.this.deviceCoordinate(DeviceFactory.getDevice().getDeviceDisplay(), SwingDisplayComponent.this.pressedPoint);
                buf.append(p.x).append(",").append(p.y).append(" ").append(width).append("x").append(height);
                Common.setStatusBar(buf.toString());
            }
            Device device = DeviceFactory.getDevice();
            InputMethodImpl inputMethod = (InputMethodImpl) device.getInputMethod();
            boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();
            if (device.hasPointerMotionEvents()) {
                if (!fullScreenMode && SwingDisplayComponent.this.initialPressedSoftButton != null && (pb = SwingDisplayComponent.this.initialPressedSoftButton.getPaintable()) != null) {
                    if (pb.contains(e.getX(), e.getY())) {
                        if (!SwingDisplayComponent.this.initialPressedSoftButton.isPressed()) {
                            SwingDisplayComponent.this.initialPressedSoftButton.setPressed(true);
                            SwingDisplayComponent.this.repaintRequest(pb.x, pb.y, pb.width, pb.height);
                        }
                    } else if (SwingDisplayComponent.this.initialPressedSoftButton.isPressed()) {
                        SwingDisplayComponent.this.initialPressedSoftButton.setPressed(false);
                        SwingDisplayComponent.this.repaintRequest(pb.x, pb.y, pb.width, pb.height);
                    }
                }
                Point p2 = SwingDisplayComponent.this.deviceCoordinate(device.getDeviceDisplay(), e.getPoint());
                inputMethod.pointerDragged(p2.x, p2.y);
            }
        }

        public void mouseMoved(MouseEvent e) {
            if (SwingDisplayComponent.this.showMouseCoordinates) {
                StringBuffer buf = new StringBuffer();
                Point p = SwingDisplayComponent.this.deviceCoordinate(DeviceFactory.getDevice().getDeviceDisplay(), e.getPoint());
                buf.append(p.x).append(",").append(p.y);
                Common.setStatusBar(buf.toString());
            }
        }
    };
    private MouseWheelListener mouseWheelListener = new MouseWheelListener() { // from class: org.microemu.app.ui.swing.SwingDisplayComponent.3
        public void mouseWheelMoved(MouseWheelEvent ev) {
            if (ev.getWheelRotation() > 0) {
                KeyEvent event = new KeyEvent(SwingDisplayComponent.this.deviceComponent, 0, System.currentTimeMillis(), 0, 40, (char) 65535);
                SwingDisplayComponent.this.deviceComponent.keyPressed(event);
                SwingDisplayComponent.this.deviceComponent.keyReleased(event);
            } else {
                KeyEvent event2 = new KeyEvent(SwingDisplayComponent.this.deviceComponent, 0, System.currentTimeMillis(), 0, 38, (char) 65535);
                SwingDisplayComponent.this.deviceComponent.keyPressed(event2);
                SwingDisplayComponent.this.deviceComponent.keyReleased(event2);
            }
        }
    };

    SwingDisplayComponent(SwingDeviceComponent deviceComponent) {
        this.deviceComponent = deviceComponent;
        setFocusable(false);
        addMouseListener(this.mouseListener);
        addMouseMotionListener(this.mouseMotionListener);
        addMouseWheelListener(this.mouseWheelListener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    public void init() {
        ?? r0 = this;
        synchronized (r0) {
            this.displayImage = null;
            this.initialPressedSoftButton = null;
            r0 = r0;
        }
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

    public Dimension getPreferredSize() {
        Device device = DeviceFactory.getDevice();
        if (device == null) {
            return new Dimension(0, 0);
        }
        return new Dimension(device.getDeviceDisplay().getFullWidth(), device.getDeviceDisplay().getFullHeight());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [org.microemu.device.j2se.J2SEMutableImage] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    protected void paintComponent(Graphics g) {
        if (this.displayImage != null) {
            ?? r0 = this.displayImage;
            synchronized (r0) {
                g.drawImage(this.displayImage.getImage(), 0, 0, (ImageObserver) null);
                r0 = r0;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17, types: [org.microemu.device.j2se.J2SEMutableImage] */
    /* JADX WARN: Type inference failed for: r0v18, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.Throwable, org.microemu.app.ui.swing.SwingDisplayComponent] */
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
            J2SEDeviceDisplay deviceDisplay = (J2SEDeviceDisplay) device.getDeviceDisplay();
            synchronized (this) {
                if (this.displayImage == null) {
                    this.displayImage = new J2SEMutableImage(device.getDeviceDisplay().getFullWidth(), device.getDeviceDisplay().getFullHeight());
                    this.displayGraphics = this.displayImage.getImage().getGraphics();
                }
                ?? r0 = this.displayImage;
                synchronized (r0) {
                    deviceDisplay.paintDisplayable(this.displayGraphics, x, y, width, height);
                    if (!deviceDisplay.isFullScreenMode()) {
                        deviceDisplay.paintControls(this.displayGraphics);
                    }
                    r0 = r0;
                    fireDisplayRepaint(this.displayImage);
                }
            }
            if (deviceDisplay.isFullScreenMode()) {
                repaint(x, y, width, height);
            } else {
                repaint(0, 0, this.displayImage.getWidth(), this.displayImage.getHeight());
            }
        }
    }

    private void fireDisplayRepaint(MutableImage image) {
        if (this.displayRepaintListener != null) {
            this.displayRepaintListener.repaintInvoked(image);
        }
    }

    Point deviceCoordinate(DeviceDisplay deviceDisplay, Point p) {
        if (deviceDisplay.isFullScreenMode()) {
            return p;
        }
        Rectangle pb = ((J2SEDeviceDisplay) deviceDisplay).getDisplayPaintable();
        return new Point(p.x - pb.x, p.y - pb.y);
    }

    void switchShowMouseCoordinates() {
        this.showMouseCoordinates = !this.showMouseCoordinates;
    }

    public MutableImage getScaledDisplayImage(int zoom) {
        return this.displayImage.scale(zoom);
    }

    public MouseAdapter getMouseListener() {
        return this.mouseListener;
    }

    public MouseMotionListener getMouseMotionListener() {
        return this.mouseMotionListener;
    }

    public MouseWheelListener getMouseWheelListener() {
        return this.mouseWheelListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public J2SEButton getButtonByButtonName(ButtonName buttonName) {
        Enumeration e = DeviceFactory.getDevice().getButtons().elements();
        while (e.hasMoreElements()) {
            J2SEButton result = (J2SEButton) e.nextElement();
            if (result.getFunctionalName() == buttonName) {
                return result;
            }
        }
        return null;
    }
}
