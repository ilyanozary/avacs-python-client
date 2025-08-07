package org.microemu.cldc.file;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;
import javax.microedition.io.file.FileSystemListener;
import org.microemu.microedition.Implementation;

/* loaded from: avacs.jar:org/microemu/cldc/file/FileSystemRegistryImpl.class */
public class FileSystemRegistryImpl implements FileSystemRegistryDelegate, Implementation {
    private AccessControlContext acc;
    private String fsRoot;
    private String fsSingle;

    public FileSystemRegistryImpl() {
        this.acc = AccessController.getContext();
    }

    public FileSystemRegistryImpl(String fsRoot, String fsSingle) {
        this();
        this.fsRoot = fsRoot;
        this.fsSingle = fsSingle;
    }

    @Override // org.microemu.cldc.file.FileSystemRegistryDelegate
    public Enumeration listRoots() {
        switch (Connection.getConnectionType()) {
            case 0:
                return (Enumeration) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.microemu.cldc.file.FileSystemRegistryImpl.1
                    @Override // java.security.PrivilegedAction
                    public Object run() {
                        return FileSystemFileConnection.listRoots(FileSystemRegistryImpl.this.fsRoot, FileSystemRegistryImpl.this.fsSingle);
                    }
                }, this.acc);
            default:
                throw new RuntimeException("Invalid connectionType configuration");
        }
    }

    @Override // org.microemu.cldc.file.FileSystemRegistryDelegate
    public boolean addFileSystemListener(FileSystemListener listener) {
        return false;
    }

    @Override // org.microemu.cldc.file.FileSystemRegistryDelegate
    public boolean removeFileSystemListener(FileSystemListener listener) {
        return false;
    }
}
