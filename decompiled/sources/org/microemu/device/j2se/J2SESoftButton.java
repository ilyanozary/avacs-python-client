package org.microemu.device.j2se;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.impl.Shape;
import org.microemu.device.impl.SoftButton;

/* loaded from: avacs.jar:org/microemu/device/j2se/J2SESoftButton.class */
public class J2SESoftButton extends J2SEButton implements SoftButton {
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

    public J2SESoftButton(int skinVersion, String name, Shape shape, int keyCode, String keyboardKeys, Rectangle paintable, String alignmentName, Vector commands, Font font) {
        super(skinVersion, name, shape, keyCode, keyboardKeys, null, new Hashtable(), false);
        this.commandTypes = new Vector();
        this.command = null;
        this.type = 1;
        this.paintable = paintable;
        this.visible = true;
        this.pressed = false;
        this.font = font;
        if (alignmentName != null) {
            try {
                this.alignment = J2SESoftButton.class.getField(alignmentName).getInt(null);
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

    public J2SESoftButton(int skinVersion, String name, Rectangle paintable, Image normalImage, Image pressedImage) {
        super(skinVersion, name, null, Integer.MIN_VALUE, null, null, null, false);
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
    public Rectangle getPaintable() {
        return this.paintable;
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v27 */
    /* JADX WARN: Type inference failed for: r0v28, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v31 */
    public void paint(Graphics g) {
        if (!this.visible || this.paintable == null) {
            return;
        }
        java.awt.Shape clip = g.getClip();
        g.setClip(this.paintable.x, this.paintable.y, this.paintable.width, this.paintable.height);
        if (this.type == 1) {
            int xoffset = 0;
            Device device = DeviceFactory.getDevice();
            J2SEDeviceDisplay deviceDisplay = (J2SEDeviceDisplay) device.getDeviceDisplay();
            if (this.pressed) {
                g.setColor(deviceDisplay.foregroundColor);
            } else {
                g.setColor(deviceDisplay.backgroundColor);
            }
            g.fillRect(this.paintable.x, this.paintable.y, this.paintable.width, this.paintable.height);
            ?? r0 = this;
            synchronized (r0) {
                if (this.command != null) {
                    if (this.font != null) {
                        J2SEFontManager fontManager = (J2SEFontManager) device.getFontManager();
                        J2SEFont buttonFont = (J2SEFont) fontManager.getFont(this.font);
                        g.setFont(buttonFont.getFont());
                    }
                    FontMetrics metrics = g.getFontMetrics();
                    if (this.alignment == RIGHT) {
                        xoffset = (this.paintable.width - metrics.stringWidth(this.command.getLabel())) - 1;
                    }
                    if (this.pressed) {
                        g.setColor(deviceDisplay.backgroundColor);
                    } else {
                        g.setColor(deviceDisplay.foregroundColor);
                    }
                    g.drawString(this.command.getLabel(), this.paintable.x + xoffset, (this.paintable.y + this.paintable.height) - metrics.getDescent());
                }
                r0 = r0;
            }
        } else if (this.type == 2) {
            if (this.pressed) {
                g.drawImage(((J2SEImmutableImage) this.pressedImage).getImage(), this.paintable.x, this.paintable.y, (ImageObserver) null);
            } else {
                g.drawImage(((J2SEImmutableImage) this.normalImage).getImage(), this.paintable.x, this.paintable.y, (ImageObserver) null);
            }
        }
        g.setClip(clip);
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
