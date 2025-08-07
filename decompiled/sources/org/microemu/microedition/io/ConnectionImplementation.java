package org.microemu.microedition.io;

import java.io.IOException;
import javax.microedition.io.Connection;

/* loaded from: avacs.jar:org/microemu/microedition/io/ConnectionImplementation.class */
public interface ConnectionImplementation {
    Connection openConnection(String str, int i, boolean z) throws IOException;
}
