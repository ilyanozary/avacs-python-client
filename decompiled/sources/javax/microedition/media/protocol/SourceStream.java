package javax.microedition.media.protocol;

import java.io.IOException;
import javax.microedition.media.Controllable;

/* loaded from: avacs.jar:javax/microedition/media/protocol/SourceStream.class */
public interface SourceStream extends Controllable {
    public static final int NOT_SEEKABLE = 0;
    public static final int SEEKABLE_TO_START = 1;
    public static final int RANDOM_ACCESSIBLE = 2;

    ContentDescriptor getContentDescriptor();

    long getContentLength();

    int read(byte[] bArr, int i, int i2) throws IOException;

    int getTransferSize();

    long seek(long j) throws IOException;

    long tell();

    int getSeekType();
}
