package org.microemu.device.impl.ui;

import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import org.microemu.MIDletBridge;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.SoftButton;
import org.microemu.device.ui.CommandUI;

/* loaded from: avacs.jar:org/microemu/device/impl/ui/CommandManager.class */
public class CommandManager {
    public static final Command CMD_MENU = new Command("Menu", 7, 0);
    private static final Command CMD_BACK = new Command("Back", 2, 0);
    private static final Command CMD_SELECT = new Command("Select", 4, 0);
    private static CommandManager instance = new CommandManager();
    private Vector menuCommands;
    private Displayable previous;
    private List menuList = null;
    private CommandListener menuCommandListener = new CommandListener() { // from class: org.microemu.device.impl.ui.CommandManager.1
        @Override // javax.microedition.lcdui.CommandListener
        public void commandAction(Command c, Displayable d) {
            if (CommandManager.this.menuList == null) {
                CommandManager.this.lateInit();
            }
            MIDletBridge.getMIDletAccess().getDisplayAccess().setCurrent(CommandManager.this.previous);
            if (c == CommandManager.CMD_SELECT || c == List.SELECT_COMMAND) {
                MIDletBridge.getMIDletAccess().getDisplayAccess().commandAction((Command) CommandManager.this.menuCommands.elementAt(CommandManager.this.menuList.getSelectedIndex()), CommandManager.this.previous);
            }
        }
    };

    private CommandManager() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void lateInit() {
        this.menuList = new List("Menu", 3);
        this.menuList.addCommand(CMD_BACK);
        this.menuList.addCommand(CMD_SELECT);
        this.menuList.setCommandListener(this.menuCommandListener);
    }

    public static CommandManager getInstance() {
        return instance;
    }

    public void commandAction(Command command) {
        if (this.menuList == null) {
            lateInit();
        }
        this.previous = MIDletBridge.getMIDletAccess().getDisplayAccess().getCurrent();
        MIDletBridge.getMIDletAccess().getDisplayAccess().setCurrent(this.menuList);
    }

    public void updateCommands(Vector commands) {
        if (this.menuList == null) {
            lateInit();
        }
        Vector buttons = DeviceFactory.getDevice().getSoftButtons();
        int numOfButtons = 0;
        Enumeration en = buttons.elements();
        while (en.hasMoreElements()) {
            SoftButton button = (SoftButton) en.nextElement();
            if (button.getType() == 1) {
                button.setCommand(null);
                numOfButtons++;
            }
        }
        if (commands == null) {
            return;
        }
        Vector commandsTable = new Vector();
        for (int i = 0; i < commands.size(); i++) {
            commandsTable.addElement(null);
        }
        Enumeration en2 = commands.elements();
        while (en2.hasMoreElements()) {
            Command commandToSort = ((CommandUI) en2.nextElement()).getCommand();
            int i2 = 0;
            while (true) {
                if (i2 < commandsTable.size()) {
                    if (commandsTable.elementAt(i2) == null) {
                        commandsTable.setElementAt(commandToSort, i2);
                        break;
                    }
                    if (commandToSort.getPriority() < ((Command) commandsTable.elementAt(i2)).getPriority()) {
                        for (int j = commandsTable.size() - 1; j > i2; j--) {
                            if (commandsTable.elementAt(j - 1) != null) {
                                commandsTable.setElementAt(commandsTable.elementAt(j - 1), j);
                            }
                        }
                    }
                    i2++;
                }
            }
        }
        if (commandsTable.size() <= numOfButtons) {
            fillPossibleCommands(buttons, commandsTable);
            return;
        }
        commandsTable.insertElementAt(CMD_MENU, 0);
        fillPossibleCommands(buttons, commandsTable);
        while (this.menuList.size() > 0) {
            this.menuList.delete(0);
        }
        for (int i3 = 0; i3 < commandsTable.size(); i3++) {
            this.menuCommands = commandsTable;
            this.menuList.append(((Command) commandsTable.elementAt(i3)).getLabel(), null);
        }
    }

    private void fillPossibleCommands(Vector buttons, Vector commandsTable) {
        int i = 0;
        while (i < commandsTable.size()) {
            Enumeration en = buttons.elements();
            while (true) {
                if (!en.hasMoreElements()) {
                    break;
                }
                SoftButton button = (SoftButton) en.nextElement();
                if (button.getType() == 1 && button.getCommand() == null && button.preferredCommandType((Command) commandsTable.elementAt(i))) {
                    button.setCommand((Command) commandsTable.elementAt(i));
                    commandsTable.removeElementAt(i);
                    i--;
                    break;
                }
            }
            i++;
        }
        int i2 = 0;
        while (i2 < commandsTable.size()) {
            Enumeration en2 = buttons.elements();
            while (true) {
                if (!en2.hasMoreElements()) {
                    break;
                }
                SoftButton button2 = (SoftButton) en2.nextElement();
                if (button2.getType() == 1 && button2.getCommand() == null) {
                    button2.setCommand((Command) commandsTable.elementAt(i2));
                    commandsTable.removeElementAt(i2);
                    i2--;
                    break;
                }
            }
            i2++;
        }
        Enumeration hiddenEn = buttons.elements();
        while (hiddenEn.hasMoreElements()) {
            SoftButton hiddenButton = (SoftButton) hiddenEn.nextElement();
            if (hiddenButton.getType() == 1 && hiddenButton.getPaintable() == null && hiddenButton.getCommand() != null) {
                Enumeration en3 = buttons.elements();
                while (true) {
                    if (!en3.hasMoreElements()) {
                        break;
                    }
                    SoftButton button3 = (SoftButton) en3.nextElement();
                    if (button3.getType() == 1 && button3.getPaintable() != null && button3.getCommand() == null) {
                        button3.setCommand(hiddenButton.getCommand());
                        break;
                    }
                }
            }
        }
    }
}
