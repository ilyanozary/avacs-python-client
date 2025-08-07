package javax.microedition.io.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.microedition.io.StreamConnection;

/* loaded from: avacs.jar:javax/microedition/io/file/FileConnection.class */
public interface FileConnection extends StreamConnection {
    boolean isOpen();

    @Override // javax.microedition.io.InputConnection
    InputStream openInputStream() throws IOException;

    @Override // javax.microedition.io.InputConnection
    DataInputStream openDataInputStream() throws IOException;

    @Override // javax.microedition.io.OutputConnection
    OutputStream openOutputStream() throws IOException;

    @Override // javax.microedition.io.OutputConnection
    DataOutputStream openDataOutputStream() throws IOException;

    OutputStream openOutputStream(long j) throws IOException;

    long totalSize();

    long availableSize();

    long usedSize();

    long directorySize(boolean z) throws IOException;

    long fileSize() throws IOException;

    boolean canRead();

    boolean canWrite();

    boolean isHidden();

    void setReadable(boolean z) throws IOException;

    void setWritable(boolean z) throws IOException;

    void setHidden(boolean z) throws IOException;

    Enumeration list() throws IOException;

    Enumeration list(String str, boolean z) throws IOException;

    void create() throws IOException;

    void mkdir() throws IOException;

    boolean exists();

    boolean isDirectory();

    void delete() throws IOException;

    void rename(String str) throws IOException;

    void truncate(long j) throws IOException;

    void setFileConnection(String str) throws IOException;

    String getName();

    String getPath();

    String getURL();

    long lastModified();
}
