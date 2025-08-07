package javax.microedition.io;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: avacs.jar:javax/microedition/io/InputConnection.class */
public interface InputConnection extends Connection {
    InputStream openInputStream() throws IOException;

    DataInputStream openDataInputStream() throws IOException;
}
