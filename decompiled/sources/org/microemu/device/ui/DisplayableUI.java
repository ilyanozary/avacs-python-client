package org.microemu.device.ui;

import java.util.Vector;
import javax.microedition.lcdui.CommandListener;

/* loaded from: avacs.jar:org/microemu/device/ui/DisplayableUI.class */
public interface DisplayableUI {
    void addCommandUI(CommandUI commandUI);

    void removeCommandUI(CommandUI commandUI);

    void setCommandListener(CommandListener commandListener);

    void hideNotify();

    void showNotify();

    void invalidate();

    Vector getCommandsUI();
}
