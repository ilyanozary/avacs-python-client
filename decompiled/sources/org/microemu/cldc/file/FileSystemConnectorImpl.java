package org.microemu.cldc.file;

import java.io.IOException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.List;
import java.util.Vector;
import org.microemu.log.Logger;
import org.microemu.microedition.ImplementationUnloadable;
import org.microemu.microedition.io.ConnectorAdapter;

/* loaded from: avacs.jar:org/microemu/cldc/file/FileSystemConnectorImpl.class */
public class FileSystemConnectorImpl extends ConnectorAdapter implements ImplementationUnloadable {
    public static final String PROTOCOL = "file://";
    private String fsRoot;
    private List openConnection = new Vector();
    private AccessControlContext acc = AccessController.getContext();

    FileSystemConnectorImpl(String fsRoot) {
        this.fsRoot = fsRoot;
    }

    @Override // org.microemu.microedition.io.ConnectorAdapter, org.microemu.microedition.io.ConnectorDelegate
    public javax.microedition.io.Connection open(final String name, int mode, boolean timeouts) throws IOException {
        if (!name.startsWith("file://")) {
            throw new IOException("Invalid Protocol " + name);
        }
        javax.microedition.io.Connection con = (javax.microedition.io.Connection) doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemConnectorImpl.1
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                return new FileSystemFileConnection(FileSystemConnectorImpl.this.fsRoot, name.substring("file://".length()), FileSystemConnectorImpl.this);
            }
        }, this.acc);
        this.openConnection.add(con);
        return con;
    }

    static Object doPrivilegedIO(PrivilegedExceptionAction action, AccessControlContext context) throws IOException {
        try {
            return AccessController.doPrivileged(action, context);
        } catch (PrivilegedActionException e) {
            if (e.getCause() instanceof IOException) {
                throw ((IOException) e.getCause());
            }
            throw new IOException(e.toString());
        }
    }

    void notifyMIDletDestroyed() {
        if (this.openConnection.size() > 0) {
            Logger.warn("Still has " + this.openConnection.size() + " open file connections");
        }
    }

    @Override // org.microemu.microedition.ImplementationUnloadable
    public void unregisterImplementation() {
        FileSystem.unregisterImplementation(this);
    }

    void notifyClosed(FileSystemFileConnection con) {
        this.openConnection.remove(con);
    }
}
