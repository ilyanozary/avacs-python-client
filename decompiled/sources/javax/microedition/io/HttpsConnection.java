package javax.microedition.io;

import java.io.IOException;

/* loaded from: avacs.jar:javax/microedition/io/HttpsConnection.class */
public interface HttpsConnection extends HttpConnection {
    SecurityInfo getSecurityInfo() throws IOException;

    @Override // javax.microedition.io.HttpConnection
    int getPort();
}
