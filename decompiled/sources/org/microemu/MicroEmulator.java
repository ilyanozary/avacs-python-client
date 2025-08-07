package org.microemu;

import java.io.InputStream;
import org.microemu.app.launcher.Launcher;

/* loaded from: avacs.jar:org/microemu/MicroEmulator.class */
public interface MicroEmulator {
    RecordStoreManager getRecordStoreManager();

    Launcher getLauncher();

    String getAppProperty(String str);

    InputStream getResourceAsStream(String str);

    void notifyDestroyed(MIDletContext mIDletContext);

    void destroyMIDletContext(MIDletContext mIDletContext);

    int checkPermission(String str);

    boolean platformRequest(String str);
}
