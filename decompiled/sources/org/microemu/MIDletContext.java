package org.microemu;

import javax.microedition.midlet.MIDlet;
import org.microemu.app.launcher.Launcher;

/* loaded from: avacs.jar:org/microemu/MIDletContext.class */
public class MIDletContext {
    private MIDletAccess midletAccess;

    public MIDletAccess getMIDletAccess() {
        return this.midletAccess;
    }

    protected void setMIDletAccess(MIDletAccess midletAccess) {
        this.midletAccess = midletAccess;
    }

    public MIDlet getMIDlet() {
        if (this.midletAccess == null) {
            return null;
        }
        return this.midletAccess.midlet;
    }

    public boolean isLauncher() {
        return getMIDlet() instanceof Launcher;
    }
}
