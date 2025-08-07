package javax.microedition.io;

import java.io.IOException;

/* loaded from: avacs.jar:javax/microedition/io/SecureConnection.class */
public interface SecureConnection extends SocketConnection {
    SecurityInfo getSecurityInfo() throws IOException;
}
