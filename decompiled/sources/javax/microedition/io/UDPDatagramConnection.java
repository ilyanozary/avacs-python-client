package javax.microedition.io;

import java.io.IOException;

/* loaded from: avacs.jar:javax/microedition/io/UDPDatagramConnection.class */
public interface UDPDatagramConnection extends DatagramConnection {
    String getLocalAddress() throws IOException;

    int getLocalPort() throws IOException;
}
