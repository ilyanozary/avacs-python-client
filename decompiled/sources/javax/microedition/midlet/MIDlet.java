package javax.microedition.midlet;

import javax.microedition.io.ConnectionNotFoundException;
import org.microemu.DisplayAccess;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;

/* loaded from: avacs.jar:javax/microedition/midlet/MIDlet.class */
public abstract class MIDlet {
    private boolean destroyed;

    protected abstract void startApp() throws MIDletStateChangeException;

    protected abstract void pauseApp();

    protected abstract void destroyApp(boolean z) throws MIDletStateChangeException;

    /* loaded from: avacs.jar:javax/microedition/midlet/MIDlet$MIDletAccessor.class */
    class MIDletAccessor extends MIDletAccess {
        public MIDletAccessor() {
            super(MIDlet.this);
            MIDlet.this.destroyed = false;
        }

        @Override // org.microemu.MIDletAccess
        public void startApp() throws MIDletStateChangeException {
            MIDletBridge.setCurrentMIDlet(this.midlet);
            this.midlet.startApp();
        }

        @Override // org.microemu.MIDletAccess
        public void pauseApp() {
            this.midlet.pauseApp();
        }

        @Override // org.microemu.MIDletAccess
        public void destroyApp(boolean unconditional) throws MIDletStateChangeException {
            if (!this.midlet.destroyed) {
                this.midlet.destroyApp(unconditional);
            }
            DisplayAccess da = getDisplayAccess();
            if (da != null) {
                da.clean();
                setDisplayAccess(null);
            }
            MIDletBridge.destroyMIDletContext(MIDletBridge.getMIDletContext(this.midlet));
        }
    }

    protected MIDlet() {
        MIDletBridge.registerMIDletAccess(new MIDletAccessor());
    }

    public final int checkPermission(String permission) {
        return MIDletBridge.checkPermission(permission);
    }

    public final String getAppProperty(String key) {
        return MIDletBridge.getAppProperty(key);
    }

    public final void notifyDestroyed() {
        this.destroyed = true;
        MIDletBridge.notifyDestroyed();
    }

    public final void notifyPaused() {
    }

    public final boolean platformRequest(String URL) throws ConnectionNotFoundException {
        return MIDletBridge.platformRequest(URL);
    }

    public final void resumeRequest() {
    }
}
