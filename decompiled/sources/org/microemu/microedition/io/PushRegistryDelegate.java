package org.microemu.microedition.io;

import java.io.IOException;
import javax.microedition.io.ConnectionNotFoundException;

/* loaded from: avacs.jar:org/microemu/microedition/io/PushRegistryDelegate.class */
public interface PushRegistryDelegate {
    void registerConnection(String str, String str2, String str3) throws ClassNotFoundException, IOException;

    boolean unregisterConnection(String str);

    String[] listConnections(boolean z);

    String getMIDlet(String str);

    String getFilter(String str);

    long registerAlarm(String str, long j) throws ConnectionNotFoundException, ClassNotFoundException;
}
