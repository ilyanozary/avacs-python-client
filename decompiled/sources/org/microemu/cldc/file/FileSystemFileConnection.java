package org.microemu.cldc.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.file.ConnectionClosedException;
import javax.microedition.io.file.FileConnection;

/* loaded from: avacs.jar:org/microemu/cldc/file/FileSystemFileConnection.class */
public class FileSystemFileConnection implements FileConnection {
    private String fsRootConfig;
    private File fsRoot;
    private String host;
    private String fullPath;
    private File file;
    private boolean isRoot;
    private boolean isDirectory;
    private Throwable locationClosedFrom = null;
    private FileSystemConnectorImpl notifyClosed;
    private InputStream opendInputStream;
    private OutputStream opendOutputStream;
    private static final char DIR_SEP = '/';
    private static final String DIR_SEP_STR = "/";
    private AccessControlContext acc;
    private static boolean java15 = false;

    FileSystemFileConnection(String fsRootConfig, String name, FileSystemConnectorImpl notifyClosed) throws IOException {
        int hostEnd = name.indexOf(47);
        if (hostEnd == -1) {
            throw new IOException("Invalid path " + name);
        }
        this.fsRootConfig = fsRootConfig;
        this.notifyClosed = notifyClosed;
        this.host = name.substring(0, hostEnd);
        this.fullPath = name.substring(hostEnd + 1);
        if (this.fullPath.length() == 0) {
            throw new IOException("Invalid path " + name);
        }
        int rootEnd = this.fullPath.indexOf(47);
        this.isRoot = rootEnd == -1 || rootEnd == this.fullPath.length() - 1;
        if (this.fullPath.charAt(this.fullPath.length() - 1) == '/') {
            this.fullPath = this.fullPath.substring(0, this.fullPath.length() - 1);
        }
        this.acc = AccessController.getContext();
        AccessController.doPrivileged(new PrivilegedAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.1
            @Override // java.security.PrivilegedAction
            public Object run() {
                FileSystemFileConnection.this.fsRoot = FileSystemFileConnection.getRoot(FileSystemFileConnection.this.fsRootConfig);
                FileSystemFileConnection.this.file = new File(FileSystemFileConnection.this.fsRoot, FileSystemFileConnection.this.fullPath);
                FileSystemFileConnection.this.isDirectory = FileSystemFileConnection.this.file.isDirectory();
                return null;
            }
        }, this.acc);
    }

    private Object doPrivilegedIO(PrivilegedExceptionAction action) throws IOException {
        return FileSystemConnectorImpl.doPrivilegedIO(action, this.acc);
    }

    /* loaded from: avacs.jar:org/microemu/cldc/file/FileSystemFileConnection$PrivilegedBooleanAction.class */
    private abstract class PrivilegedBooleanAction implements PrivilegedAction {
        abstract boolean getBoolean();

        private PrivilegedBooleanAction() {
        }

        /* synthetic */ PrivilegedBooleanAction(FileSystemFileConnection fileSystemFileConnection, PrivilegedBooleanAction privilegedBooleanAction) {
            this();
        }

        @Override // java.security.PrivilegedAction
        public Object run() {
            return new Boolean(getBoolean());
        }
    }

    private boolean doPrivilegedBoolean(PrivilegedBooleanAction action) {
        return ((Boolean) AccessController.doPrivileged(action)).booleanValue();
    }

    public static File getRoot(String fsRootConfig) {
        try {
            if (fsRootConfig == null) {
                File fsRoot = new File(DIR_SEP_STR);
                if (!fsRoot.exists()) {
                    if (!fsRoot.mkdirs()) {
                        throw new RuntimeException("Can't create filesystem root " + fsRoot.getAbsolutePath());
                    }
                    File rootC = new File(fsRoot, "c");
                    if (!rootC.exists()) {
                        rootC.mkdirs();
                    }
                    File rootE = new File(fsRoot, "e");
                    if (!rootE.exists()) {
                        rootE.mkdirs();
                    }
                }
                return fsRoot;
            }
            File fsRoot2 = new File(fsRootConfig);
            if (!fsRoot2.isDirectory()) {
                throw new RuntimeException("Can't find filesystem root " + fsRoot2.getAbsolutePath());
            }
            return fsRoot2;
        } catch (SecurityException e) {
            return null;
        }
    }

    static Enumeration listRoots(String fsRootConfig, String fsSingleConfig) {
        File[] files;
        if (fsSingleConfig != null) {
            files = new File[]{getRoot(String.valueOf(fsRootConfig) + fsSingleConfig)};
        } else {
            files = getRoot(fsRootConfig).listFiles();
            if (files == null) {
                return new Vector().elements();
            }
        }
        Vector list = new Vector();
        for (File file : files) {
            if (!file.isHidden() && file.isDirectory()) {
                list.add(String.valueOf(file.getName()) + '/');
            }
        }
        return list.elements();
    }

    @Override // javax.microedition.io.file.FileConnection
    public long availableSize() throws ConnectionClosedException {
        throwClosed();
        if (this.fsRoot == null) {
            return -1L;
        }
        return getFileValueJava6("getFreeSpace");
    }

    @Override // javax.microedition.io.file.FileConnection
    public long totalSize() throws ConnectionClosedException {
        throwClosed();
        if (this.fsRoot == null) {
            return -1L;
        }
        return getFileValueJava6("getTotalSpace");
    }

    @Override // javax.microedition.io.file.FileConnection
    public boolean canRead() throws ConnectionClosedException {
        throwClosed();
        return doPrivilegedBoolean(new PrivilegedBooleanAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.2
            @Override // org.microemu.cldc.file.FileSystemFileConnection.PrivilegedBooleanAction
            public boolean getBoolean() {
                return FileSystemFileConnection.this.file.canRead();
            }
        });
    }

    @Override // javax.microedition.io.file.FileConnection
    public boolean canWrite() throws ConnectionClosedException {
        throwClosed();
        return doPrivilegedBoolean(new PrivilegedBooleanAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.3
            @Override // org.microemu.cldc.file.FileSystemFileConnection.PrivilegedBooleanAction
            public boolean getBoolean() {
                return FileSystemFileConnection.this.file.canWrite();
            }
        });
    }

    @Override // javax.microedition.io.file.FileConnection
    public void create() throws IOException, ConnectionClosedException {
        throwClosed();
        doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.4
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                if (!FileSystemFileConnection.this.file.createNewFile()) {
                    throw new IOException("File already exists  " + FileSystemFileConnection.this.file.getAbsolutePath());
                }
                return null;
            }
        });
    }

    @Override // javax.microedition.io.file.FileConnection
    public void delete() throws IOException, ConnectionClosedException {
        throwClosed();
        doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.5
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                if (!FileSystemFileConnection.this.file.delete()) {
                    throw new IOException("Unable to delete " + FileSystemFileConnection.this.file.getAbsolutePath());
                }
                return null;
            }
        });
    }

    @Override // javax.microedition.io.file.FileConnection
    public long directorySize(final boolean includeSubDirs) throws IOException, ConnectionClosedException {
        throwClosed();
        return ((Long) doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.6
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                if (!FileSystemFileConnection.this.file.isDirectory()) {
                    throw new IOException("Not a directory " + FileSystemFileConnection.this.file.getAbsolutePath());
                }
                return new Long(FileSystemFileConnection.directorySize(FileSystemFileConnection.this.file, includeSubDirs));
            }
        })).longValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long directorySize(File dir, boolean includeSubDirs) throws IOException {
        long j;
        long length;
        long size = 0;
        File[] files = dir.listFiles();
        if (files == null) {
            return 0L;
        }
        for (File child : files) {
            if (includeSubDirs && child.isDirectory()) {
                j = size;
                length = directorySize(child, true);
            } else {
                j = size;
                length = child.length();
            }
            size = j + length;
        }
        return size;
    }

    @Override // javax.microedition.io.file.FileConnection
    public boolean exists() throws ConnectionClosedException {
        throwClosed();
        return doPrivilegedBoolean(new PrivilegedBooleanAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.7
            @Override // org.microemu.cldc.file.FileSystemFileConnection.PrivilegedBooleanAction
            public boolean getBoolean() {
                return FileSystemFileConnection.this.file.exists();
            }
        });
    }

    @Override // javax.microedition.io.file.FileConnection
    public long fileSize() throws IOException, ConnectionClosedException {
        throwClosed();
        return ((Long) doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.8
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                return new Long(FileSystemFileConnection.this.file.length());
            }
        })).longValue();
    }

    @Override // javax.microedition.io.file.FileConnection
    public String getName() throws ConnectionClosedException {
        throwClosed();
        if (this.isRoot) {
            return "";
        }
        if (this.isDirectory) {
            return String.valueOf(this.file.getName()) + '/';
        }
        return this.file.getName();
    }

    @Override // javax.microedition.io.file.FileConnection
    public String getPath() throws ConnectionClosedException {
        throwClosed();
        if (this.isRoot) {
            return String.valueOf('/') + this.fullPath + '/';
        }
        int pathEnd = this.fullPath.lastIndexOf(47);
        if (pathEnd == -1) {
            return DIR_SEP_STR;
        }
        return String.valueOf('/') + this.fullPath.substring(0, pathEnd + 1);
    }

    @Override // javax.microedition.io.file.FileConnection
    public String getURL() throws ConnectionClosedException {
        throwClosed();
        return "file://" + this.host + '/' + this.fullPath + (this.isDirectory ? DIR_SEP_STR : "");
    }

    @Override // javax.microedition.io.file.FileConnection
    public boolean isDirectory() throws ConnectionClosedException {
        throwClosed();
        return this.isDirectory;
    }

    @Override // javax.microedition.io.file.FileConnection
    public boolean isHidden() throws ConnectionClosedException {
        throwClosed();
        return doPrivilegedBoolean(new PrivilegedBooleanAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.9
            @Override // org.microemu.cldc.file.FileSystemFileConnection.PrivilegedBooleanAction
            public boolean getBoolean() {
                return FileSystemFileConnection.this.file.isHidden();
            }
        });
    }

    @Override // javax.microedition.io.file.FileConnection
    public long lastModified() throws ConnectionClosedException {
        throwClosed();
        return ((Long) AccessController.doPrivileged(new PrivilegedAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.10
            @Override // java.security.PrivilegedAction
            public Object run() {
                return new Long(FileSystemFileConnection.this.file.lastModified());
            }
        }, this.acc)).longValue();
    }

    @Override // javax.microedition.io.file.FileConnection
    public void mkdir() throws IOException, ConnectionClosedException {
        throwClosed();
        doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.11
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                if (!FileSystemFileConnection.this.file.mkdir()) {
                    throw new IOException("Can't create directory " + FileSystemFileConnection.this.file.getAbsolutePath());
                }
                return null;
            }
        });
    }

    @Override // javax.microedition.io.file.FileConnection
    public Enumeration list() throws IOException {
        return list(null, false);
    }

    @Override // javax.microedition.io.file.FileConnection
    public Enumeration list(final String filter, final boolean includeHidden) throws IOException, ConnectionClosedException {
        throwClosed();
        return (Enumeration) doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.12
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                return FileSystemFileConnection.this.listPrivileged(filter, includeHidden);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Enumeration listPrivileged(String filter, boolean includeHidden) throws IOException {
        if (!this.file.isDirectory()) {
            throw new IOException("Not a directory " + this.file.getAbsolutePath());
        }
        File[] files = this.file.listFiles((FilenameFilter) null);
        if (files == null) {
            return new Vector().elements();
        }
        Vector list = new Vector();
        for (File child : files) {
            if (includeHidden || !child.isHidden()) {
                if (child.isDirectory()) {
                    list.add(String.valueOf(child.getName()) + '/');
                } else {
                    list.add(child.getName());
                }
            }
        }
        return list.elements();
    }

    @Override // javax.microedition.io.file.FileConnection
    public boolean isOpen() {
        return this.file != null;
    }

    private void throwOpenDirectory() throws IOException {
        if (this.isDirectory) {
            throw new IOException("Unable to open Stream on directory");
        }
    }

    @Override // javax.microedition.io.file.FileConnection, javax.microedition.io.InputConnection
    public InputStream openInputStream() throws IOException, ConnectionClosedException {
        throwClosed();
        throwOpenDirectory();
        if (this.opendInputStream != null) {
            throw new IOException("InputStream already opened");
        }
        this.opendInputStream = (InputStream) doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.13
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                return new FileInputStream(FileSystemFileConnection.this.file) { // from class: org.microemu.cldc.file.FileSystemFileConnection.13.1
                    @Override // java.io.FileInputStream, java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws IOException {
                        FileSystemFileConnection.this.opendInputStream = null;
                        super.close();
                    }
                };
            }
        });
        return this.opendInputStream;
    }

    @Override // javax.microedition.io.file.FileConnection, javax.microedition.io.InputConnection
    public DataInputStream openDataInputStream() throws IOException {
        return new DataInputStream(openInputStream());
    }

    @Override // javax.microedition.io.file.FileConnection, javax.microedition.io.OutputConnection
    public OutputStream openOutputStream() throws IOException {
        return openOutputStream(false);
    }

    private OutputStream openOutputStream(final boolean append) throws IOException, ConnectionClosedException {
        throwClosed();
        throwOpenDirectory();
        if (this.opendOutputStream != null) {
            throw new IOException("OutputStream already opened");
        }
        this.opendOutputStream = (OutputStream) doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.14
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                return new FileOutputStream(FileSystemFileConnection.this.file, append) { // from class: org.microemu.cldc.file.FileSystemFileConnection.14.1
                    @Override // java.io.FileOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
                    public void close() throws IOException {
                        FileSystemFileConnection.this.opendOutputStream = null;
                        super.close();
                    }
                };
            }
        });
        return this.opendOutputStream;
    }

    @Override // javax.microedition.io.file.FileConnection, javax.microedition.io.OutputConnection
    public DataOutputStream openDataOutputStream() throws IOException {
        return new DataOutputStream(openOutputStream());
    }

    @Override // javax.microedition.io.file.FileConnection
    public OutputStream openOutputStream(long byteOffset) throws IOException, ConnectionClosedException {
        throwClosed();
        throwOpenDirectory();
        if (this.opendOutputStream != null) {
            throw new IOException("OutputStream already opened");
        }
        truncate(byteOffset);
        return openOutputStream(true);
    }

    @Override // javax.microedition.io.file.FileConnection
    public void rename(final String newName) throws IOException, ConnectionClosedException {
        throwClosed();
        if (newName.indexOf(47) != -1) {
            throw new IllegalArgumentException("Name contains path specification " + newName);
        }
        doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.15
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                File newFile = new File(FileSystemFileConnection.this.file.getParentFile(), newName);
                if (!FileSystemFileConnection.this.file.renameTo(newFile)) {
                    throw new IOException("Unable to rename " + FileSystemFileConnection.this.file.getAbsolutePath() + " to " + newFile.getAbsolutePath());
                }
                return null;
            }
        });
        this.fullPath = String.valueOf(getPath()) + newName;
    }

    @Override // javax.microedition.io.file.FileConnection
    public void setFileConnection(String s) throws IOException, ConnectionClosedException {
        throwClosed();
    }

    @Override // javax.microedition.io.file.FileConnection
    public void setHidden(boolean hidden) throws IOException, ConnectionClosedException {
        throwClosed();
    }

    private void fileSetJava16(String mehtodName, final Boolean param) throws NoSuchMethodException, IOException, SecurityException {
        if (java15) {
            throw new IOException("Not supported on Java version < 6");
        }
        try {
            final Method setWritable = this.file.getClass().getMethod(mehtodName, Boolean.TYPE);
            doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.16
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IllegalAccessException, IOException, IllegalArgumentException, InvocationTargetException {
                    try {
                        setWritable.invoke(FileSystemFileConnection.this.file, param);
                        FileSystemFileConnection.this.file.setReadOnly();
                        return null;
                    } catch (Exception e) {
                        throw new IOException(e.getCause().getMessage());
                    }
                }
            });
        } catch (NoSuchMethodException e) {
            java15 = true;
            throw new IOException("Not supported on Java version < 6");
        }
    }

    private long getFileValueJava6(String mehtodName) throws NoSuchMethodException, SecurityException {
        if (java15) {
            throw new SecurityException("Not supported on Java version < 6");
        }
        try {
            final Method getter = this.file.getClass().getMethod(mehtodName, new Class[0]);
            Long rc = (Long) doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.17
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IOException {
                    try {
                        return getter.invoke(FileSystemFileConnection.this.file, new Object[0]);
                    } catch (Exception e) {
                        throw new IOException(e.getCause().getMessage());
                    }
                }
            });
            return rc.longValue();
        } catch (IOException e) {
            throw new SecurityException(e.getMessage());
        } catch (NoSuchMethodException e2) {
            java15 = true;
            throw new SecurityException("Not supported on Java version < 6");
        }
    }

    @Override // javax.microedition.io.file.FileConnection
    public void setReadable(boolean readable) throws NoSuchMethodException, IOException, SecurityException, ConnectionClosedException {
        throwClosed();
        fileSetJava16("setReadable", new Boolean(readable));
    }

    @Override // javax.microedition.io.file.FileConnection
    public void setWritable(boolean writable) throws NoSuchMethodException, IOException, SecurityException, ConnectionClosedException {
        throwClosed();
        if (!writable) {
            doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.18
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IOException {
                    FileSystemFileConnection.this.file.setReadOnly();
                    return null;
                }
            });
        } else {
            fileSetJava16("setWritable", new Boolean(writable));
        }
    }

    @Override // javax.microedition.io.file.FileConnection
    public void truncate(final long byteOffset) throws IOException, ConnectionClosedException {
        throwClosed();
        doPrivilegedIO(new PrivilegedExceptionAction() { // from class: org.microemu.cldc.file.FileSystemFileConnection.19
            @Override // java.security.PrivilegedExceptionAction
            public Object run() throws IOException {
                RandomAccessFile raf = new RandomAccessFile(FileSystemFileConnection.this.file, "rw");
                try {
                    raf.setLength(byteOffset);
                    raf.close();
                    return null;
                } catch (Throwable th) {
                    raf.close();
                    throw th;
                }
            }
        });
    }

    @Override // javax.microedition.io.file.FileConnection
    public long usedSize() {
        try {
            return fileSize();
        } catch (IOException e) {
            return -1L;
        }
    }

    @Override // javax.microedition.io.Connection
    public void close() throws IOException {
        if (this.file != null) {
            if (this.notifyClosed != null) {
                this.notifyClosed.notifyClosed(this);
            }
            this.locationClosedFrom = new Throwable();
            this.locationClosedFrom.fillInStackTrace();
            this.file = null;
        }
    }

    private void throwClosed() throws ConnectionClosedException {
        if (this.file == null) {
            if (this.locationClosedFrom != null) {
                this.locationClosedFrom.printStackTrace();
            }
            throw new ConnectionClosedException("Connection already closed");
        }
    }
}
