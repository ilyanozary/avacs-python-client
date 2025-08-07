package org.microemu.cldc;

import java.io.IOException;
import javax.microedition.io.Connection;

/* loaded from: avacs.jar:org/microemu/cldc/ClosedConnection.class */
public interface ClosedConnection {
    Connection open(String str) throws IOException;
}
