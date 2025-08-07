package org.microemu.device.swt;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import org.eclipse.swt.graphics.RGB;
import org.microemu.app.ui.swt.SwtGraphics;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.impl.Shape;
import org.microemu.device.impl.SoftButton;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtSoftButton.class */
public class SwtSoftButton extends SwtButton implements SoftButton {
    public static int LEFT = 1;
    public static int RIGHT = 2;
    private int type;
    private Image normalImage;
    private Image pressedImage;
    private Vector commandTypes;
    private Command command;
    private Rectangle paintable;
    private int alignment;
    private boolean visible;
    private boolean pressed;
    private Font font;

    public SwtSoftButton(String name, Shape shape, int keyCode, String keyName, Rectangle paintable, String alignmentName, Vector commands, Font font) {
        super(name, shape, keyCode, keyName, new Hashtable());
        this.commandTypes = new Vector();
        this.command = null;
        this.type = 1;
        this.paintable = paintable;
        this.visible = true;
        this.pressed = false;
        this.font = font;
        if (alignmentName != null) {
            try {
                this.alignment = SwtSoftButton.class.getField(alignmentName).getInt(null);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
        Enumeration e = commands.elements();
        while (e.hasMoreElements()) {
            String tmp = (String) e.nextElement();
            try {
                addCommandType(Command.class.getField(tmp).getInt(null));
            } catch (Exception ex2) {
                System.err.println("a3" + ex2);
            }
        }
    }

    public SwtSoftButton(String name, Rectangle paintable, Image normalImage, Image pressedImage) {
        super(name, null, Integer.MIN_VALUE, null, null);
        this.commandTypes = new Vector();
        this.command = null;
        this.type = 2;
        this.paintable = paintable;
        this.normalImage = normalImage;
        this.pressedImage = pressedImage;
        this.visible = true;
        this.pressed = false;
    }

    @Override // org.microemu.device.impl.SoftButton
    public int getType() {
        return this.type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v3 */
    @Override // org.microemu.device.impl.SoftButton
    public void setCommand(Command cmd) {
        ?? r0 = this;
        synchronized (r0) {
            this.command = cmd;
            r0 = r0;
        }
    }

    @Override // org.microemu.device.impl.SoftButton
    public Command getCommand() {
        return this.command;
    }

    @Override // org.microemu.device.impl.SoftButton
    public boolean isVisible() {
        return this.visible;
    }

    @Override // org.microemu.device.impl.SoftButton
    public void setVisible(boolean state) {
        this.visible = state;
    }

    @Override // org.microemu.device.impl.SoftButton
    public boolean isPressed() {
        return this.pressed;
    }

    @Override // org.microemu.device.impl.SoftButton
    public void setPressed(boolean state) {
        this.pressed = state;
    }

    @Override // org.microemu.device.impl.SoftButton
    public Rectangle getPaintable() {
        return this.paintable;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v26, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v29 */
    public void paint(SwtGraphics g) {
        if (!this.visible || this.paintable == null) {
            return;
        }
        org.eclipse.swt.graphics.Rectangle clip = g.getClipping();
        g.setClipping(this.paintable.x, this.paintable.y, this.paintable.width, this.paintable.height);
        if (this.type == 1) {
            int xoffset = 0;
            Device device = DeviceFactory.getDevice();
            SwtDeviceDisplay deviceDisplay = (SwtDeviceDisplay) device.getDeviceDisplay();
            if (this.pressed) {
                g.setForeground(g.getColor(new RGB(deviceDisplay.getForegroundColor().getRed(), deviceDisplay.getForegroundColor().getGreen(), deviceDisplay.getForegroundColor().getBlue())));
            } else {
                g.setBackground(g.getColor(new RGB(deviceDisplay.getBackgroundColor().getRed(), deviceDisplay.getBackgroundColor().getGreen(), deviceDisplay.getBackgroundColor().getBlue())));
            }
            g.fillRectangle(this.paintable.x, this.paintable.y, this.paintable.width, this.paintable.height);
            ?? r0 = this;
            synchronized (r0) {
                if (this.command != null) {
                    if (this.font != null) {
                        SwtFontManager fontManager = (SwtFontManager) device.getFontManager();
                        SwtFont buttonFont = (SwtFont) fontManager.getFont(this.font);
                        g.setFont(buttonFont.getFont());
                    }
                    if (this.alignment == RIGHT) {
                        xoffset = this.paintable.width - g.stringWidth(this.command.getLabel());
                    }
                    if (this.pressed) {
                        g.setBackground(g.getColor(new RGB(deviceDisplay.getBackgroundColor().getRed(), deviceDisplay.getBackgroundColor().getGreen(), deviceDisplay.getBackgroundColor().getBlue())));
                    } else {
                        g.setForeground(g.getColor(new RGB(deviceDisplay.getForegroundColor().getRed(), deviceDisplay.getForegroundColor().getGreen(), deviceDisplay.getForegroundColor().getBlue())));
                    }
                    g.drawString(this.command.getLabel(), this.paintable.x + xoffset, this.paintable.y + (this.paintable.height - g.getFontMetrics().getHeight()), true);
                }
                r0 = r0;
            }
        } else if (this.type == 2) {
            if (this.pressed) {
                g.drawImage(((SwtImmutableImage) this.pressedImage).getImage(), this.paintable.x, this.paintable.y);
            } else {
                g.drawImage(((SwtImmutableImage) this.normalImage).getImage(), this.paintable.x, this.paintable.y);
            }
        }
        g.setClipping(clip);
    }

    @Override // org.microemu.device.impl.SoftButton
    public boolean preferredCommandType(Command cmd) {
        Enumeration ct = this.commandTypes.elements();
        while (ct.hasMoreElements()) {
            if (cmd.getCommandType() == ((Integer) ct.nextElement()).intValue()) {
                return true;
            }
        }
        return false;
    }

    public void addCommandType(int commandType) {
        this.commandTypes.addElement(new Integer(commandType));
    }
}
