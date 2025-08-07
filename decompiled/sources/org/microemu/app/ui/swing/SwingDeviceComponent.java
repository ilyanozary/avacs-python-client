package org.microemu.app.ui.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Command;
import javax.swing.JPanel;
import javax.swing.UIManager;
import org.microemu.DisplayAccess;
import org.microemu.DisplayComponent;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.app.Common;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.DeviceDisplayImpl;
import org.microemu.device.impl.Polygon;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.impl.Shape;
import org.microemu.device.impl.SoftButton;
import org.microemu.device.impl.ui.CommandManager;
import org.microemu.device.j2se.J2SEButton;
import org.microemu.device.j2se.J2SEDeviceButtonsHelper;
import org.microemu.device.j2se.J2SEDeviceDisplay;
import org.microemu.device.j2se.J2SEImmutableImage;
import org.microemu.device.j2se.J2SEInputMethod;
import org.microemu.device.j2se.J2SEMutableImage;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/SwingDeviceComponent.class */
public class SwingDeviceComponent extends JPanel implements KeyListener {
    private static final long serialVersionUID = 1;
    J2SEButton prevOverButton;
    J2SEButton overButton;
    J2SEButton pressedButton;
    Image offi;
    Graphics offg;
    private int pressedX;
    private int pressedY;
    private boolean mouseButtonDown = false;
    private boolean showMouseCoordinates = false;
    private MouseAdapter mouseListener = new MouseAdapter() { // from class: org.microemu.app.ui.swing.SwingDeviceComponent.1
        public void mousePressed(MouseEvent e) {
            DisplayAccess da;
            SwingDeviceComponent.this.requestFocus();
            SwingDeviceComponent.this.mouseButtonDown = true;
            SwingDeviceComponent.this.pressedX = e.getX();
            SwingDeviceComponent.this.pressedY = e.getY();
            MouseRepeatedTimerTask.stop();
            if (MIDletBridge.getCurrentMIDlet() == null) {
                return;
            }
            Device device = DeviceFactory.getDevice();
            J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();
            boolean fullScreenMode = device.getDeviceDisplay().isFullScreenMode();
            SwingDeviceComponent.this.pressedButton = J2SEDeviceButtonsHelper.getSkinButton(e);
            if (SwingDeviceComponent.this.pressedButton != null) {
                if ((SwingDeviceComponent.this.pressedButton instanceof SoftButton) && !fullScreenMode) {
                    Command cmd = ((SoftButton) SwingDeviceComponent.this.pressedButton).getCommand();
                    if (cmd != null) {
                        MIDletAccess ma = MIDletBridge.getMIDletAccess();
                        if (ma == null || (da = ma.getDisplayAccess()) == null) {
                            return;
                        }
                        if (cmd.equals(CommandManager.CMD_MENU)) {
                            CommandManager.getInstance().commandAction(cmd);
                        } else {
                            da.commandAction(cmd, da.getCurrent());
                        }
                    }
                } else {
                    inputMethod.buttonPressed(SwingDeviceComponent.this.pressedButton, (char) 0);
                    MouseRepeatedTimerTask.schedule(SwingDeviceComponent.this, SwingDeviceComponent.this.pressedButton, inputMethod);
                }
                SwingDeviceComponent.this.repaint(SwingDeviceComponent.this.pressedButton.getShape().getBounds());
            }
        }

        public void mouseReleased(MouseEvent e) {
            SwingDeviceComponent.this.mouseButtonDown = false;
            MouseRepeatedTimerTask.stop();
            if (SwingDeviceComponent.this.pressedButton == null || MIDletBridge.getCurrentMIDlet() == null) {
                return;
            }
            Device device = DeviceFactory.getDevice();
            J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();
            J2SEButton prevOverButton = J2SEDeviceButtonsHelper.getSkinButton(e);
            if (prevOverButton != null) {
                inputMethod.buttonReleased(prevOverButton, (char) 0);
            }
            SwingDeviceComponent.this.pressedButton = null;
            if (prevOverButton == null) {
                SwingDeviceComponent.this.repaint();
            } else {
                SwingDeviceComponent.this.repaint(prevOverButton.getShape().getBounds());
            }
        }
    };
    private MouseMotionListener mouseMotionListener = new MouseMotionListener() { // from class: org.microemu.app.ui.swing.SwingDeviceComponent.2
        public void mouseDragged(MouseEvent e) {
            mouseMoved(e);
        }

        public void mouseMoved(MouseEvent e) {
            if (SwingDeviceComponent.this.showMouseCoordinates) {
                StringBuffer buf = new StringBuffer();
                if (SwingDeviceComponent.this.mouseButtonDown) {
                    int width = e.getX() - SwingDeviceComponent.this.pressedX;
                    int height = e.getY() - SwingDeviceComponent.this.pressedY;
                    buf.append(SwingDeviceComponent.this.pressedX).append(",").append(SwingDeviceComponent.this.pressedY).append(" ").append(width).append("x").append(height);
                } else {
                    buf.append(e.getX()).append(",").append(e.getY());
                }
                Common.setStatusBar(buf.toString());
            }
            if (SwingDeviceComponent.this.mouseButtonDown && SwingDeviceComponent.this.pressedButton == null) {
                return;
            }
            SwingDeviceComponent.this.prevOverButton = SwingDeviceComponent.this.overButton;
            SwingDeviceComponent.this.overButton = J2SEDeviceButtonsHelper.getSkinButton(e);
            if (SwingDeviceComponent.this.overButton != SwingDeviceComponent.this.prevOverButton) {
                if (SwingDeviceComponent.this.prevOverButton != null) {
                    MouseRepeatedTimerTask.mouseReleased();
                    SwingDeviceComponent.this.pressedButton = null;
                    SwingDeviceComponent.this.repaint(SwingDeviceComponent.this.prevOverButton.getShape().getBounds());
                }
                if (SwingDeviceComponent.this.overButton == null) {
                    return;
                }
                SwingDeviceComponent.this.repaint(SwingDeviceComponent.this.overButton.getShape().getBounds());
                return;
            }
            if (SwingDeviceComponent.this.overButton == null) {
                MouseRepeatedTimerTask.mouseReleased();
                SwingDeviceComponent.this.pressedButton = null;
                if (SwingDeviceComponent.this.prevOverButton == null) {
                    return;
                }
                SwingDeviceComponent.this.repaint(SwingDeviceComponent.this.prevOverButton.getShape().getBounds());
            }
        }
    };
    SwingDisplayComponent dc = new SwingDisplayComponent(this);

    /* loaded from: avacs.jar:org/microemu/app/ui/swing/SwingDeviceComponent$MouseRepeatedTimerTask.class */
    private static class MouseRepeatedTimerTask extends TimerTask {
        private static final int DELAY = 100;
        Timer timer;
        Component source;
        J2SEButton button;
        J2SEInputMethod inputMethod;
        static MouseRepeatedTimerTask task;

        private MouseRepeatedTimerTask() {
        }

        static void schedule(Component source, J2SEButton button, J2SEInputMethod inputMethod) {
            if (task != null) {
                task.cancel();
            }
            task = new MouseRepeatedTimerTask();
            task.source = source;
            task.button = button;
            task.inputMethod = inputMethod;
            task.timer = new Timer();
            task.timer.scheduleAtFixedRate(task, 500L, 100L);
        }

        static void stop() {
            if (task != null) {
                task.inputMethod = null;
                if (task.timer != null) {
                    task.timer.cancel();
                }
                task.cancel();
                task = null;
            }
        }

        public static void mouseReleased() {
            if (task != null && task.inputMethod != null) {
                task.inputMethod.buttonReleased(task.button, (char) 0);
                stop();
            }
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            if (this.inputMethod != null) {
                this.inputMethod.buttonPressed(this.button, (char) 0);
            }
        }
    }

    public SwingDeviceComponent() {
        setLayout(new XYLayout());
        addMouseListener(this.mouseListener);
        addMouseMotionListener(this.mouseMotionListener);
    }

    public DisplayComponent getDisplayComponent() {
        return this.dc;
    }

    public void init() {
        this.dc.init();
        remove(this.dc);
        Rectangle r = ((J2SEDeviceDisplay) DeviceFactory.getDevice().getDeviceDisplay()).getDisplayRectangle();
        add(this.dc, new XYConstraints(r.x, r.y, -1, -1));
        revalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void repaint(Rectangle r) {
        repaint(r.x, r.y, r.width, r.height);
    }

    public void switchShowMouseCoordinates() {
        this.dc.switchShowMouseCoordinates();
    }

    public void keyTyped(KeyEvent ev) {
        J2SEInputMethod inputMethod;
        J2SEButton button;
        if (MIDletBridge.getCurrentMIDlet() != null && (button = (inputMethod = (J2SEInputMethod) DeviceFactory.getDevice().getInputMethod()).getButton(ev)) != null) {
            inputMethod.buttonTyped(button);
        }
    }

    public void keyPressed(KeyEvent ev) {
        if (MIDletBridge.getCurrentMIDlet() == null) {
            return;
        }
        Device device = DeviceFactory.getDevice();
        J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();
        if (ev.getKeyCode() == 86 && (ev.getModifiers() & 2) != 0) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable transferable = clipboard.getContents((Object) null);
            if (transferable != null) {
                try {
                    Object data = transferable.getTransferData(DataFlavor.stringFlavor);
                    if (data instanceof String) {
                        inputMethod.clipboardPaste((String) data);
                        return;
                    }
                    return;
                } catch (UnsupportedFlavorException ex) {
                    Logger.error((Throwable) ex);
                    return;
                } catch (IOException ex2) {
                    Logger.error((Throwable) ex2);
                    return;
                }
            }
            return;
        }
        switch (ev.getKeyCode()) {
            case 0:
                if (ev.getKeyChar() == 0) {
                    return;
                }
                break;
        }
        char keyChar = 0;
        if (ev.getKeyChar() >= ' ' && ev.getKeyChar() != 65535) {
            keyChar = ev.getKeyChar();
        }
        J2SEButton button = inputMethod.getButton(ev);
        if (button != null) {
            this.pressedButton = button;
        }
        inputMethod.buttonPressed(button, keyChar);
    }

    public void keyReleased(KeyEvent ev) {
        Shape shape;
        if (MIDletBridge.getCurrentMIDlet() == null) {
            return;
        }
        switch (ev.getKeyCode()) {
            case 0:
                if (ev.getKeyChar() == 0) {
                    return;
                }
                break;
        }
        Device device = DeviceFactory.getDevice();
        J2SEInputMethod inputMethod = (J2SEInputMethod) device.getInputMethod();
        char keyChar = 0;
        if (ev.getKeyChar() >= ' ' && ev.getKeyChar() != 65535) {
            keyChar = ev.getKeyChar();
        }
        inputMethod.buttonReleased(inputMethod.getButton(ev), keyChar);
        this.prevOverButton = this.pressedButton;
        this.pressedButton = null;
        if (this.prevOverButton != null && (shape = this.prevOverButton.getShape()) != null) {
            repaint(shape.getBounds());
        }
    }

    public MouseListener getDefaultMouseListener() {
        return this.mouseListener;
    }

    public MouseMotionListener getDefaultMouseMotionListener() {
        return this.mouseMotionListener;
    }

    protected void paintComponent(Graphics g) {
        Shape shape;
        Shape shape2;
        if (this.offg == null || this.offi.getWidth((ImageObserver) null) != getSize().width || this.offi.getHeight((ImageObserver) null) != getSize().height) {
            this.offi = new J2SEMutableImage(getSize().width, getSize().height).getImage();
            this.offg = this.offi.getGraphics();
        }
        Dimension size = getSize();
        this.offg.setColor(UIManager.getColor("text"));
        try {
            this.offg.fillRect(0, 0, size.width, size.height);
        } catch (NullPointerException e) {
        }
        Device device = DeviceFactory.getDevice();
        if (device == null) {
            g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            return;
        }
        if (((DeviceDisplayImpl) device.getDeviceDisplay()).isResizable()) {
            return;
        }
        this.offg.drawImage(((J2SEImmutableImage) device.getNormalImage()).getImage(), 0, 0, this);
        if (this.prevOverButton != null) {
            Shape shape3 = this.prevOverButton.getShape();
            if (shape3 != null) {
                drawImageInShape(this.offg, ((J2SEImmutableImage) device.getNormalImage()).getImage(), shape3);
            }
            this.prevOverButton = null;
        }
        if (this.overButton != null && (shape2 = this.overButton.getShape()) != null) {
            drawImageInShape(this.offg, ((J2SEImmutableImage) device.getOverImage()).getImage(), shape2);
        }
        if (this.pressedButton != null && (shape = this.pressedButton.getShape()) != null) {
            drawImageInShape(this.offg, ((J2SEImmutableImage) device.getPressedImage()).getImage(), shape);
        }
        g.drawImage(this.offi, 0, 0, (ImageObserver) null);
    }

    private void drawImageInShape(Graphics g, Image image, Shape shape) {
        java.awt.Shape clipSave = g.getClip();
        if (shape instanceof Polygon) {
            java.awt.Polygon poly = new java.awt.Polygon(((Polygon) shape).xpoints, ((Polygon) shape).ypoints, ((Polygon) shape).npoints);
            g.setClip(poly);
        }
        Rectangle r = shape.getBounds();
        g.drawImage(image, r.x, r.y, r.x + r.width, r.y + r.height, r.x, r.y, r.x + r.width, r.y + r.height, (ImageObserver) null);
        g.setClip(clipSave);
    }

    public Dimension getPreferredSize() {
        Device device = DeviceFactory.getDevice();
        if (device == null) {
            return new Dimension(0, 0);
        }
        DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) DeviceFactory.getDevice().getDeviceDisplay();
        if (deviceDisplay.isResizable()) {
            return new Dimension(deviceDisplay.getFullWidth(), deviceDisplay.getFullHeight());
        }
        javax.microedition.lcdui.Image img = device.getNormalImage();
        return new Dimension(img.getWidth(), img.getHeight());
    }
}
