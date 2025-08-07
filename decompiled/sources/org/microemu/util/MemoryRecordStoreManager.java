package org.microemu.util;

import java.util.Enumeration;
import java.util.Hashtable;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;
import org.microemu.MicroEmulator;
import org.microemu.RecordStoreManager;

/* loaded from: avacs.jar:org/microemu/util/MemoryRecordStoreManager.class */
public class MemoryRecordStoreManager implements RecordStoreManager {
    private Hashtable recordStores = new Hashtable();
    private ExtendedRecordListener recordListener = null;

    @Override // org.microemu.RecordStoreManager
    public void init(MicroEmulator emulator) {
    }

    @Override // org.microemu.RecordStoreManager
    public String getName() {
        return "Memory record store";
    }

    @Override // org.microemu.RecordStoreManager
    public void deleteRecordStore(String recordStoreName) throws RecordStoreException {
        RecordStoreImpl recordStoreImpl = (RecordStoreImpl) this.recordStores.get(recordStoreName);
        if (recordStoreImpl == null) {
            throw new RecordStoreNotFoundException(recordStoreName);
        }
        if (recordStoreImpl.isOpen()) {
            throw new RecordStoreException();
        }
        this.recordStores.remove(recordStoreName);
        fireRecordStoreListener(10, recordStoreName);
    }

    @Override // org.microemu.RecordStoreManager
    public RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) throws RecordStoreNotFoundException {
        RecordStoreImpl recordStoreImpl = (RecordStoreImpl) this.recordStores.get(recordStoreName);
        if (recordStoreImpl == null) {
            if (!createIfNecessary) {
                throw new RecordStoreNotFoundException(recordStoreName);
            }
            recordStoreImpl = new RecordStoreImpl(this, recordStoreName);
            this.recordStores.put(recordStoreName, recordStoreImpl);
        }
        recordStoreImpl.setOpen(true);
        if (this.recordListener != null) {
            recordStoreImpl.addRecordListener(this.recordListener);
        }
        fireRecordStoreListener(8, recordStoreName);
        return recordStoreImpl;
    }

    @Override // org.microemu.RecordStoreManager
    public String[] listRecordStores() {
        String[] result = (String[]) null;
        int i = 0;
        Enumeration e = this.recordStores.keys();
        while (e.hasMoreElements()) {
            if (result == null) {
                result = new String[this.recordStores.size()];
            }
            result[i] = (String) e.nextElement();
            i++;
        }
        return result;
    }

    @Override // org.microemu.RecordStoreManager
    public void saveChanges(RecordStoreImpl recordStoreImpl) {
    }

    public void init() {
        deleteStores();
    }

    @Override // org.microemu.RecordStoreManager
    public void deleteStores() {
        if (this.recordStores != null) {
            this.recordStores.clear();
        }
    }

    @Override // org.microemu.RecordStoreManager
    public int getSizeAvailable(RecordStoreImpl recordStoreImpl) {
        return (int) Runtime.getRuntime().freeMemory();
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
