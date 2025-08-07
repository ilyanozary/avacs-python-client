package javax.microedition.io;

import java.io.IOException;

/* loaded from: avacs.jar:javax/microedition/io/StreamConnectionNotifier.class */
public interface StreamConnectionNotifier extends Connection {
    StreamConnection acceptAndOpen() throws IOException;
}
