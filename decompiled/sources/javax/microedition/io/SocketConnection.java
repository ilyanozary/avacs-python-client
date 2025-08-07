package javax.microedition.io;

import java.io.IOException;

/* loaded from: avacs.jar:javax/microedition/io/SocketConnection.class */
public interface SocketConnection extends StreamConnection {
    public static final byte DELAY = 0;
    public static final byte LINGER = 1;
    public static final byte KEEPALIVE = 2;
    public static final byte RCVBUF = 3;
    public static final byte SNDBUF = 4;

    void setSocketOption(byte b, int i) throws IOException, IllegalArgumentException;

    int getSocketOption(byte b) throws IOException, IllegalArgumentException;

    String getLocalAddress() throws IOException;

    int getLocalPort() throws IOException;

    String getAddress() throws IOException;

    int getPort() throws IOException;
}
