package org.microemu.microedition.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connection;
import org.microemu.microedition.Implementation;

/* loaded from: avacs.jar:org/microemu/microedition/io/ConnectorDelegate.class */
public interface ConnectorDelegate extends Implementation {
    Connection open(String str) throws IOException;

    Connection open(String str, int i) throws IOException;

    Connection open(String str, int i, boolean z) throws IOException;

    DataInputStream openDataInputStream(String str) throws IOException;

    DataOutputStream openDataOutputStream(String str) throws IOException;

    InputStream openInputStream(String str) throws IOException;

    OutputStream openOutputStream(String str) throws IOException;
}
