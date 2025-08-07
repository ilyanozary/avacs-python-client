package javax.microedition.io.file;

import java.util.Enumeration;
import org.microemu.cldc.file.FileSystemRegistryDelegate;
import org.microemu.microedition.ImplFactory;

/* loaded from: avacs.jar:javax/microedition/io/file/FileSystemRegistry.class */
public class FileSystemRegistry {
    private static FileSystemRegistryDelegate impl = (FileSystemRegistryDelegate) ImplFactory.getImplementation(FileSystemRegistry.class, FileSystemRegistryDelegate.class);

    private FileSystemRegistry() {
    }

    public static boolean addFileSystemListener(FileSystemListener listener) {
        return impl.addFileSystemListener(listener);
    }

    public static boolean removeFileSystemListener(FileSystemListener listener) {
        return impl.removeFileSystemListener(listener);
    }

    public static Enumeration listRoots() {
        return impl.listRoots();
    }
}
