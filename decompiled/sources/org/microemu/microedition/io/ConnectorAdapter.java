package org.microemu.microedition.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connection;
import javax.microedition.io.InputConnection;
import javax.microedition.io.OutputConnection;

/* loaded from: avacs.jar:org/microemu/microedition/io/ConnectorAdapter.class */
public abstract class ConnectorAdapter implements ConnectorDelegate {
    @Override // org.microemu.microedition.io.ConnectorDelegate
    public abstract Connection open(String str, int i, boolean z) throws IOException;

    @Override // org.microemu.microedition.io.ConnectorDelegate
    public Connection open(String name) throws IOException {
        return open(name, 3, false);
    }

    @Override // org.microemu.microedition.io.ConnectorDelegate
    public Connection open(String name, int mode) throws IOException {
        return open(name, mode, false);
    }

    @Override // org.microemu.microedition.io.ConnectorDelegate
    public DataInputStream openDataInputStream(String name) throws IOException {
        return ((InputConnection) open(name)).openDataInputStream();
    }

    @Override // org.microemu.microedition.io.ConnectorDelegate
    public DataOutputStream openDataOutputStream(String name) throws IOException {
        return ((OutputConnection) open(name)).openDataOutputStream();
    }

    @Override // org.microemu.microedition.io.ConnectorDelegate
    public InputStream openInputStream(String name) throws IOException {
        return ((InputConnection) open(name)).openInputStream();
    }

    @Override // org.microemu.microedition.io.ConnectorDelegate
    public OutputStream openOutputStream(String name) throws IOException {
        return ((OutputConnection) open(name)).openOutputStream();
    }
}
