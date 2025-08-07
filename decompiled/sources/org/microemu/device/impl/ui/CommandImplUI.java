package org.microemu.device.impl.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Image;
import org.microemu.device.ui.CommandUI;

/* loaded from: avacs.jar:org/microemu/device/impl/ui/CommandImplUI.class */
public class CommandImplUI implements CommandUI {
    private Command command;

    public CommandImplUI(Command command) {
        this.command = command;
    }

    @Override // org.microemu.device.ui.CommandUI
    public Command getCommand() {
        return this.command;
    }

    @Override // org.microemu.device.ui.CommandUI
    public void setImage(Image image) {
    }
}
