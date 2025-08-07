package org.microemu;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/* loaded from: avacs.jar:org/microemu/MIDletAccess.class */
public abstract class MIDletAccess {
    public MIDlet midlet;
    private DisplayAccess displayAccess;

    public abstract void startApp() throws MIDletStateChangeException;

    public abstract void pauseApp();

    public abstract void destroyApp(boolean z) throws MIDletStateChangeException;

    public MIDletAccess(MIDlet amidlet) {
        this.midlet = amidlet;
    }

    public DisplayAccess getDisplayAccess() {
        return this.displayAccess;
    }

    public void setDisplayAccess(DisplayAccess adisplayAccess) {
        this.displayAccess = adisplayAccess;
    }
}
