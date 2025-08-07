package org.microemu.cldc.socket;

import java.io.IOException;
import org.microemu.cldc.ClosedConnection;

/* loaded from: avacs.jar:org/microemu/cldc/socket/Connection.class */
public class Connection implements ClosedConnection {
    @Override // org.microemu.cldc.ClosedConnection
    public javax.microedition.io.Connection open(String name) throws IOException, NumberFormatException {
        if (!org.microemu.cldc.http.Connection.isAllowNetworkConnection()) {
            throw new IOException("No network");
        }
        int portSepIndex = name.lastIndexOf(58);
        int port = Integer.parseInt(name.substring(portSepIndex + 1));
        String host = name.substring("socket://".length(), portSepIndex);
        if (host.length() > 0) {
            return new SocketConnection(host, port);
        }
        return new ServerSocketConnection(port);
    }

    public void close() throws IOException {
    }
}
