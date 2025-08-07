package org.microemu.device.impl.ui;

import java.util.Vector;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import org.microemu.DisplayAccess;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.device.ui.CommandUI;
import org.microemu.device.ui.DisplayableUI;

/* loaded from: avacs.jar:org/microemu/device/impl/ui/DisplayableImplUI.class */
public class DisplayableImplUI implements DisplayableUI {
    protected Displayable displayable;
    private Vector commands = new Vector();

    protected DisplayableImplUI(Displayable displayable) {
        this.displayable = displayable;
    }

    @Override // org.microemu.device.ui.DisplayableUI
    public void addCommandUI(CommandUI cmd) {
        for (int i = 0; i < this.commands.size(); i++) {
            if (cmd == ((CommandUI) this.commands.elementAt(i))) {
                return;
            }
        }
        boolean inserted = false;
        int i2 = 0;
        while (true) {
            if (i2 >= this.commands.size()) {
                break;
            }
            if (cmd.getCommand().getPriority() >= ((CommandUI) this.commands.elementAt(i2)).getCommand().getPriority()) {
                i2++;
            } else {
                this.commands.insertElementAt(cmd, i2);
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            this.commands.addElement(cmd);
        }
        if (this.displayable.isShown()) {
            updateCommands();
        }
    }

    @Override // org.microemu.device.ui.DisplayableUI
    public void removeCommandUI(CommandUI cmd) {
        this.commands.removeElement(cmd);
        if (this.displayable.isShown()) {
            updateCommands();
        }
    }

    @Override // org.microemu.device.ui.DisplayableUI
    public void setCommandListener(CommandListener l) {
    }

    @Override // org.microemu.device.ui.DisplayableUI
    public void hideNotify() {
    }

    @Override // org.microemu.device.ui.DisplayableUI
    public void showNotify() {
        updateCommands();
    }

    @Override // org.microemu.device.ui.DisplayableUI
    public void invalidate() {
    }

    @Override // org.microemu.device.ui.DisplayableUI
    public Vector getCommandsUI() {
        return this.commands;
    }

    private void updateCommands() {
        DisplayAccess da;
        CommandManager.getInstance().updateCommands(getCommandsUI());
        MIDletAccess ma = MIDletBridge.getMIDletAccess();
        if (ma == null || (da = ma.getDisplayAccess()) == null) {
            return;
        }
        da.repaint();
    }
}
