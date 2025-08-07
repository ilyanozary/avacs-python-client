package org.microemu.microedition.io;

import java.io.IOException;
import javax.microedition.io.ConnectionNotFoundException;
import org.microemu.microedition.Implementation;

/* loaded from: avacs.jar:org/microemu/microedition/io/PushRegistryImpl.class */
public class PushRegistryImpl implements PushRegistryDelegate, Implementation {
    @Override // org.microemu.microedition.io.PushRegistryDelegate
    public String getFilter(String connection) {
        return null;
    }

    @Override // org.microemu.microedition.io.PushRegistryDelegate
    public String getMIDlet(String connection) {
        return null;
    }

    @Override // org.microemu.microedition.io.PushRegistryDelegate
    public String[] listConnections(boolean available) {
        return new String[0];
    }

    @Override // org.microemu.microedition.io.PushRegistryDelegate
    public long registerAlarm(String midlet, long time) throws ConnectionNotFoundException, ClassNotFoundException {
        throw new ConnectionNotFoundException();
    }

    @Override // org.microemu.microedition.io.PushRegistryDelegate
    public void registerConnection(String connection, String midlet, String filter) throws ClassNotFoundException, IOException {
    }

    @Override // org.microemu.microedition.io.PushRegistryDelegate
    public boolean unregisterConnection(String connection) {
        return false;
    }
}
