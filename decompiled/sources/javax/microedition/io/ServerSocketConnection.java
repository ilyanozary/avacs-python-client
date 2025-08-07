package javax.microedition.io;

import java.io.IOException;

/* loaded from: avacs.jar:javax/microedition/io/ServerSocketConnection.class */
public interface ServerSocketConnection extends StreamConnectionNotifier {
    String getLocalAddress() throws IOException;

    int getLocalPort() throws IOException;
}
