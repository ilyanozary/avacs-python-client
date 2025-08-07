package org.microemu.app.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;
import org.microemu.MicroEmulator;
import org.microemu.RecordStoreManager;
import org.microemu.app.Config;
import org.microemu.log.Logger;
import org.microemu.util.ExtendedRecordListener;
import org.microemu.util.RecordStoreImpl;

/* loaded from: avacs.jar:org/microemu/app/util/FileRecordStoreManager.class */
public class FileRecordStoreManager implements RecordStoreManager {
    private static final String RECORD_STORE_SUFFIX = ".rs";
    private static final List replaceChars = new Vector();
    private MicroEmulator emulator;
    private AccessControlContext acc;
    private Hashtable testOpenRecordStores = new Hashtable();
    private ExtendedRecordListener recordListener = null;
    private FilenameFilter filter = new FilenameFilter() { // from class: org.microemu.app.util.FileRecordStoreManager.1
        @Override // java.io.FilenameFilter
        public boolean accept(File dir, String name) {
            if (name.endsWith(FileRecordStoreManager.RECORD_STORE_SUFFIX)) {
                return true;
            }
            return false;
        }
    };

    static {
        replaceChars.add(":");
        replaceChars.add("*");
        replaceChars.add("?");
        replaceChars.add("=");
        replaceChars.add("|");
        replaceChars.add("/");
        replaceChars.add("\\");
        replaceChars.add("\"");
    }

    @Override // org.microemu.RecordStoreManager
    public void init(MicroEmulator emulator) {
        this.emulator = emulator;
        this.acc = AccessController.getContext();
    }

    @Override // org.microemu.RecordStoreManager
    public String getName() {
        return "File record store";
    }

    protected File getSuiteFolder() {
        return new File(Config.getConfigPath(), "suite-" + this.emulator.getLauncher().getSuiteName());
    }

    private static String escapeCharacter(String charcter) {
        return "_%%" + ((int) charcter.charAt(0)) + "%%_";
    }

    static String recordStoreName2FileName(String recordStoreName) {
        for (String c : replaceChars) {
            String newValue = escapeCharacter(c);
            if (c.equals("\\")) {
                c = "\\\\";
            }
            recordStoreName = recordStoreName.replaceAll("[" + c + "]", newValue);
        }
        return String.valueOf(recordStoreName) + RECORD_STORE_SUFFIX;
    }

    static String fileName2RecordStoreName(String fileName) {
        for (String c : replaceChars) {
            String newValue = escapeCharacter(c);
            if (c.equals("\\")) {
                c = "\\\\";
            }
            fileName = fileName.replaceAll(newValue, c);
        }
        return fileName.substring(0, fileName.length() - RECORD_STORE_SUFFIX.length());
    }

    @Override // org.microemu.RecordStoreManager
    public void deleteRecordStore(final String recordStoreName) throws PrivilegedActionException, RecordStoreException {
        final File storeFile = new File(getSuiteFolder(), recordStoreName2FileName(recordStoreName));
        RecordStoreImpl recordStoreImpl = (RecordStoreImpl) this.testOpenRecordStores.get(storeFile.getName());
        if (recordStoreImpl != null && recordStoreImpl.isOpen()) {
            throw new RecordStoreException();
        }
        try {
            loadFromDisk(storeFile);
            try {
                AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.util.FileRecordStoreManager.2
                    @Override // java.security.PrivilegedExceptionAction
                    public Object run() throws FileNotFoundException {
                        storeFile.delete();
                        FileRecordStoreManager.this.fireRecordStoreListener(10, recordStoreName);
                        return null;
                    }
                }, this.acc);
            } catch (PrivilegedActionException e) {
                Logger.error("Unable remove file " + storeFile, e);
                throw new RecordStoreException();
            }
        } catch (FileNotFoundException e2) {
            throw new RecordStoreNotFoundException(recordStoreName);
        }
    }

    @Override // org.microemu.RecordStoreManager
    public RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) throws PrivilegedActionException, RecordStoreException {
        RecordStoreImpl recordStoreImpl;
        File storeFile = new File(getSuiteFolder(), recordStoreName2FileName(recordStoreName));
        try {
            recordStoreImpl = loadFromDisk(storeFile);
        } catch (FileNotFoundException e) {
            if (!createIfNecessary) {
                throw new RecordStoreNotFoundException(recordStoreName);
            }
            recordStoreImpl = new RecordStoreImpl(this, recordStoreName);
            saveToDisk(storeFile, recordStoreImpl);
        }
        recordStoreImpl.setOpen(true);
        if (this.recordListener != null) {
            recordStoreImpl.addRecordListener(this.recordListener);
        }
        this.testOpenRecordStores.put(storeFile.getName(), recordStoreImpl);
        fireRecordStoreListener(8, recordStoreName);
        return recordStoreImpl;
    }

    @Override // org.microemu.RecordStoreManager
    public String[] listRecordStores() {
        try {
            String[] result = (String[]) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.util.FileRecordStoreManager.3
                @Override // java.security.PrivilegedExceptionAction
                public Object run() {
                    return FileRecordStoreManager.this.getSuiteFolder().list(FileRecordStoreManager.this.filter);
                }
            }, this.acc);
            if (result != null) {
                if (result.length == 0) {
                    result = (String[]) null;
                } else {
                    for (int i = 0; i < result.length; i++) {
                        result[i] = fileName2RecordStoreName(result[i]);
                    }
                }
            }
            return result;
        } catch (PrivilegedActionException e) {
            Logger.error("Unable to access storeFiles", e);
            return null;
        }
    }

    @Override // org.microemu.RecordStoreManager
    public void saveChanges(RecordStoreImpl recordStoreImpl) throws PrivilegedActionException, RecordStoreException {
        File storeFile = new File(getSuiteFolder(), recordStoreName2FileName(recordStoreImpl.getName()));
        saveToDisk(storeFile, recordStoreImpl);
    }

    public void init() {
    }

    @Override // org.microemu.RecordStoreManager
    public void deleteStores() throws PrivilegedActionException {
        String[] stores = listRecordStores();
        for (String store : stores) {
            try {
                deleteRecordStore(store);
            } catch (RecordStoreException e) {
                Logger.debug("deleteRecordStore", (Throwable) e);
            }
        }
    }

    private RecordStoreImpl loadFromDisk(final File recordStoreFile) throws FileNotFoundException {
        try {
            return (RecordStoreImpl) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.util.FileRecordStoreManager.4
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws FileNotFoundException {
                    return FileRecordStoreManager.this.loadFromDiskSecure(recordStoreFile);
                }
            }, this.acc);
        } catch (PrivilegedActionException e) {
            if (e.getCause() instanceof FileNotFoundException) {
                throw ((FileNotFoundException) e.getCause());
            }
            Logger.error("Unable access file " + recordStoreFile, e);
            throw new FileNotFoundException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RecordStoreImpl loadFromDiskSecure(File recordStoreFile) throws FileNotFoundException {
        RecordStoreImpl store = null;
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(recordStoreFile)));
            store = new RecordStoreImpl(this, dis);
            dis.close();
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e2) {
            Logger.error("RecordStore.loadFromDisk: ERROR reading " + recordStoreFile.getName(), e2);
        }
        return store;
    }

    private void saveToDisk(final File recordStoreFile, final RecordStoreImpl recordStore) throws PrivilegedActionException, RecordStoreException {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.util.FileRecordStoreManager.5
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws RecordStoreException {
                    FileRecordStoreManager.this.saveToDiskSecure(recordStoreFile, recordStore);
                    return null;
                }
            }, this.acc);
        } catch (PrivilegedActionException e) {
            if (e.getCause() instanceof RecordStoreException) {
                throw ((RecordStoreException) e.getCause());
            }
            Logger.error("Unable access file " + recordStoreFile, e);
            throw new RecordStoreException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void saveToDiskSecure(File recordStoreFile, RecordStoreImpl recordStore) throws RecordStoreException {
        if (!recordStoreFile.getParentFile().exists() && !recordStoreFile.getParentFile().mkdirs()) {
            throw new RecordStoreException("Unable to create recordStore directory");
        }
        try {
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(recordStoreFile)));
            recordStore.write(dos);
            dos.close();
        } catch (IOException e) {
            Logger.error("RecordStore.saveToDisk: ERROR writting object to " + recordStoreFile.getName(), e);
            throw new RecordStoreException(e.getMessage());
        }
    }

    @Override // org.microemu.RecordStoreManager
    public int getSizeAvailable(RecordStoreImpl recordStoreImpl) {
        return TextField.INITIAL_CAPS_WORD;
    }

    @Override // org.microemu.RecordStoreManager
    public void setRecordListener(ExtendedRecordListener recordListener) {
        this.recordListener = recordListener;
    }

    @Override // org.microemu.RecordStoreManager
    public void fireRecordStoreListener(int type, String recordStoreName) {
        if (this.recordListener != null) {
            this.recordListener.recordStoreEvent(type, System.currentTimeMillis(), recordStoreName);
        }
    }
}
