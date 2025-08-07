package javax.microedition.io;

/* loaded from: avacs.jar:javax/microedition/io/ContentConnection.class */
public interface ContentConnection extends StreamConnection {
    String getType();

    String getEncoding();

    long getLength();
}
