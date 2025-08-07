package org.microemu.cldc.file;

import java.util.Enumeration;
import javax.microedition.io.file.FileSystemListener;

/* loaded from: avacs.jar:org/microemu/cldc/file/FileSystemRegistryDelegate.class */
public interface FileSystemRegistryDelegate {
    boolean addFileSystemListener(FileSystemListener fileSystemListener);

    boolean removeFileSystemListener(FileSystemListener fileSystemListener);

    Enumeration listRoots();
}
