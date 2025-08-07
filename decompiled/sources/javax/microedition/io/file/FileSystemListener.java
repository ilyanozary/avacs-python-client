package javax.microedition.io.file;

/* loaded from: avacs.jar:javax/microedition/io/file/FileSystemListener.class */
public interface FileSystemListener {
    public static final int ROOT_ADDED = 0;
    public static final int ROOT_REMOVED = 1;

    void rootChanged(int i, String str);
}
