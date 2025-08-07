package javax.microedition.rms;

import org.microemu.MIDletBridge;

/* loaded from: avacs.jar:javax/microedition/rms/RecordStore.class */
public class RecordStore {
    public static final int AUTHMODE_PRIVATE = 0;
    public static final int AUTHMODE_ANY = 1;

    public static void deleteRecordStore(String recordStoreName) throws RecordStoreException {
        MIDletBridge.getRecordStoreManager().deleteRecordStore(recordStoreName);
    }

    public static String[] listRecordStores() {
        return MIDletBridge.getRecordStoreManager().listRecordStores();
    }

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary) throws RecordStoreException {
        return MIDletBridge.getRecordStoreManager().openRecordStore(recordStoreName, createIfNecessary);
    }

    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary, int authmode, boolean writable) throws RecordStoreException {
        return openRecordStore(recordStoreName, createIfNecessary);
    }

    public static RecordStore openRecordStore(String recordStoreName, String vendorName, String suiteName) throws RecordStoreException {
        return openRecordStore(recordStoreName, false);
    }

    public void closeRecordStore() throws RecordStoreException {
    }

    public String getName() throws RecordStoreNotOpenException {
        return null;
    }

    public int getVersion() throws RecordStoreNotOpenException {
        return -1;
    }

    public int getNumRecords() throws RecordStoreNotOpenException {
        return -1;
    }

    public int getSize() throws RecordStoreNotOpenException {
        return -1;
    }

    public int getSizeAvailable() throws RecordStoreNotOpenException {
        return -1;
    }

    public long getLastModified() throws RecordStoreNotOpenException {
        return -1L;
    }

    public void addRecordListener(RecordListener listener) {
    }

    public void removeRecordListener(RecordListener listener) {
    }

    public int getNextRecordID() throws RecordStoreException {
        return -1;
    }

    public int addRecord(byte[] data, int offset, int numBytes) throws RecordStoreException {
        return -1;
    }

    public void deleteRecord(int recordId) throws RecordStoreException {
    }

    public int getRecordSize(int recordId) throws RecordStoreException {
        return -1;
    }

    public int getRecord(int recordId, byte[] buffer, int offset) throws RecordStoreException {
        return -1;
    }

    public byte[] getRecord(int recordId) throws RecordStoreException {
        return null;
    }

    public void setMode(int authmode, boolean writable) throws RecordStoreException {
    }

    public void setRecord(int recordId, byte[] newData, int offset, int numBytes) throws RecordStoreException {
    }

    public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated) throws RecordStoreNotOpenException {
        return null;
    }
}
