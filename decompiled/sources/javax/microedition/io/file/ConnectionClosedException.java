package javax.microedition.io.file;

/* loaded from: avacs.jar:javax/microedition/io/file/ConnectionClosedException.class */
public class ConnectionClosedException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ConnectionClosedException() {
    }

    public ConnectionClosedException(String detailMessage) {
        super(detailMessage);
    }
}
