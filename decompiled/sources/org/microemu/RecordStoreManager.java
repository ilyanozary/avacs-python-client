package org.microemu;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import org.microemu.util.ExtendedRecordListener;
import org.microemu.util.RecordStoreImpl;

/* loaded from: avacs.jar:org/microemu/RecordStoreManager.class */
public interface RecordStoreManager {
    String getName();

    void deleteRecordStore(String str) throws RecordStoreException;

    RecordStore openRecordStore(String str, boolean z) throws RecordStoreException;

    String[] listRecordStores();

    void saveChanges(RecordStoreImpl recordStoreImpl) throws RecordStoreException;

    int getSizeAvailable(RecordStoreImpl recordStoreImpl);

    void init(MicroEmulator microEmulator);

    void deleteStores();

    void setRecordListener(ExtendedRecordListener extendedRecordListener);

    void fireRecordStoreListener(int i, String str);
}
