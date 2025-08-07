package org.microemu.cldc.file;

import java.io.IOException;
import org.microemu.microedition.io.ConnectionImplementation;

/* loaded from: avacs.jar:org/microemu/cldc/file/Connection.class */
public class Connection implements ConnectionImplementation {
    public static final String PROTOCOL = "file://";
    public static final int CONNECTIONTYPE_SYSTEM_FS = 0;
    private static int connectionType = 0;

    @Override // org.microemu.microedition.io.ConnectionImplementation
    public javax.microedition.io.Connection openConnection(String name, int mode, boolean timeouts) throws IOException {
        if (!name.startsWith("file://")) {
            throw new IOException("Invalid Protocol " + name);
        }
        switch (connectionType) {
            case 0:
                return new FileSystemFileConnection(null, name.substring("file://".length()), null);
            default:
                throw new IOException("Invalid connectionType configuration");
        }
    }

    static int getConnectionType() {
        return connectionType;
    }

    static void setConnectionType(int connectionType2) {
        connectionType = connectionType2;
    }
}
