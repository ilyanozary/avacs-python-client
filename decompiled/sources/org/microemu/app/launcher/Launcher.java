package org.microemu.app.launcher;

import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;
import org.microemu.MIDletEntry;
import org.microemu.app.CommonInterface;

/* loaded from: avacs.jar:org/microemu/app/launcher/Launcher.class */
public class Launcher extends MIDlet implements CommandListener {
    protected static final String NOMIDLETS = "[no midlets]";
    protected CommonInterface common;
    protected List menuList;
    protected MIDlet currentMIDlet = null;
    protected static final Command CMD_LAUNCH = new Command("Start", 8, 0);
    protected static String midletSuiteName = null;
    protected static Vector midletEntries = new Vector();

    public Launcher(CommonInterface common) {
        this.common = common;
    }

    public String getSuiteName() {
        return midletSuiteName;
    }

    public static void setSuiteName(String midletSuiteName2) {
        midletSuiteName = midletSuiteName2;
    }

    public static void addMIDletEntry(MIDletEntry entry) {
        midletEntries.addElement(entry);
    }

    public static void removeMIDletEntries() {
        midletEntries.removeAllElements();
    }

    public MIDletEntry getSelectedMidletEntry() {
        if (this.menuList != null) {
            int idx = this.menuList.getSelectedIndex();
            if (!this.menuList.getString(idx).equals(NOMIDLETS)) {
                return (MIDletEntry) midletEntries.elementAt(idx);
            }
            return null;
        }
        return null;
    }

    public MIDlet getCurrentMIDlet() {
        return this.currentMIDlet;
    }

    public void setCurrentMIDlet(MIDlet midlet) {
        this.currentMIDlet = midlet;
    }

    @Override // javax.microedition.midlet.MIDlet
    public void destroyApp(boolean unconditional) {
    }

    @Override // javax.microedition.midlet.MIDlet
    public void pauseApp() {
    }

    @Override // javax.microedition.midlet.MIDlet
    public void startApp() {
        this.menuList = new List("Launcher", 3);
        this.menuList.addCommand(CMD_LAUNCH);
        this.menuList.setCommandListener(this);
        if (midletEntries.size() == 0) {
            System.exit(0);
            this.menuList.append(NOMIDLETS, null);
        } else {
            for (int i = 0; i < midletEntries.size(); i++) {
                this.menuList.append(((MIDletEntry) midletEntries.elementAt(i)).getName(), null);
            }
        }
        Display.getDisplay(this).setCurrent(this.menuList);
    }

    @Override // javax.microedition.lcdui.CommandListener
    public void commandAction(Command c, Displayable d) {
        if (d == this.menuList) {
            if (c == List.SELECT_COMMAND || c == CMD_LAUNCH) {
                MIDletEntry entry = getSelectedMidletEntry();
                if (entry != null) {
                    this.common.initMIDlet(true);
                }
            }
        }
    }
}
