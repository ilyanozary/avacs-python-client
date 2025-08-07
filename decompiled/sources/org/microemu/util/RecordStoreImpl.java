package org.microemu.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordListener;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotOpenException;
import org.microemu.RecordStoreManager;

/* loaded from: avacs.jar:org/microemu/util/RecordStoreImpl.class */
public class RecordStoreImpl extends RecordStore {
    protected Hashtable records;
    private String recordStoreName;
    private int version;
    private long lastModified;
    private int nextRecordID;
    private transient boolean open;
    private transient RecordStoreManager recordStoreManager;
    private transient Vector recordListeners;

    public RecordStoreImpl(RecordStoreManager recordStoreManager, String recordStoreName) {
        this.records = new Hashtable();
        this.version = 0;
        this.lastModified = 0L;
        this.nextRecordID = 1;
        this.recordListeners = new Vector();
        this.recordStoreManager = recordStoreManager;
        if (recordStoreName.length() <= 32) {
            this.recordStoreName = recordStoreName;
        } else {
            this.recordStoreName = recordStoreName.substring(0, 32);
        }
        this.open = false;
    }

    public RecordStoreImpl(RecordStoreManager recordStoreManager, DataInputStream dis) throws IOException {
        this.records = new Hashtable();
        this.version = 0;
        this.lastModified = 0L;
        this.nextRecordID = 1;
        this.recordListeners = new Vector();
        this.recordStoreManager = recordStoreManager;
        this.recordStoreName = dis.readUTF();
        this.version = dis.readInt();
        this.lastModified = dis.readLong();
        this.nextRecordID = dis.readInt();
        while (true) {
            try {
                int recordId = dis.readInt();
                byte[] data = new byte[dis.readInt()];
                dis.read(data, 0, data.length);
                this.records.put(new Integer(recordId), data);
            } catch (EOFException e) {
                return;
            }
        }
    }

    public void write(DataOutputStream dos) throws IOException {
        dos.writeUTF(this.recordStoreName);
        dos.writeInt(this.version);
        dos.writeLong(this.lastModified);
        dos.writeInt(this.nextRecordID);
        Enumeration en = this.records.keys();
        while (en.hasMoreElements()) {
            Integer key = (Integer) en.nextElement();
            dos.writeInt(key.intValue());
            byte[] data = (byte[]) this.records.get(key);
            dos.writeInt(data.length);
            dos.write(data);
        }
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override // javax.microedition.rms.RecordStore
    public void closeRecordStore() throws RecordStoreException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        if (this.recordListeners != null) {
            this.recordListeners.removeAllElements();
        }
        this.recordStoreManager.fireRecordStoreListener(9, getName());
        this.open = false;
    }

    @Override // javax.microedition.rms.RecordStore
    public String getName() throws RecordStoreNotOpenException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        return this.recordStoreName;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5, types: [int] */
    @Override // javax.microedition.rms.RecordStore
    public int getVersion() throws RecordStoreNotOpenException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        ?? r0 = this;
        synchronized (r0) {
            r0 = this.version;
        }
        return r0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6, types: [int] */
    @Override // javax.microedition.rms.RecordStore
    public int getNumRecords() throws RecordStoreNotOpenException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        ?? size = this;
        synchronized (size) {
            size = this.records.size();
        }
        return size;
    }

    @Override // javax.microedition.rms.RecordStore
    public int getSize() throws RecordStoreNotOpenException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        int size = 0;
        Enumeration keys = this.records.keys();
        while (keys.hasMoreElements()) {
            size += ((byte[]) this.records.get(keys.nextElement())).length;
        }
        return size;
    }

    @Override // javax.microedition.rms.RecordStore
    public int getSizeAvailable() throws RecordStoreNotOpenException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        return this.recordStoreManager.getSizeAvailable(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5, types: [long] */
    @Override // javax.microedition.rms.RecordStore
    public long getLastModified() throws RecordStoreNotOpenException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        ?? r0 = this;
        synchronized (r0) {
            r0 = this.lastModified;
        }
        return r0;
    }

    @Override // javax.microedition.rms.RecordStore
    public void addRecordListener(RecordListener listener) {
        if (!this.recordListeners.contains(listener)) {
            this.recordListeners.addElement(listener);
        }
    }

    @Override // javax.microedition.rms.RecordStore
    public void removeRecordListener(RecordListener listener) {
        this.recordListeners.removeElement(listener);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5, types: [int] */
    @Override // javax.microedition.rms.RecordStore
    public int getNextRecordID() throws RecordStoreException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        ?? r0 = this;
        synchronized (r0) {
            r0 = this.nextRecordID;
        }
        return r0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.Throwable] */
    @Override // javax.microedition.rms.RecordStore
    public int addRecord(byte[] data, int offset, int numBytes) throws RecordStoreException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        if (data == null && numBytes > 0) {
            throw new NullPointerException();
        }
        if (numBytes > this.recordStoreManager.getSizeAvailable(this)) {
            throw new RecordStoreFullException();
        }
        byte[] recordData = new byte[numBytes];
        if (data != null) {
            System.arraycopy(data, offset, recordData, 0, numBytes);
        }
        ?? r0 = this;
        synchronized (r0) {
            this.records.put(new Integer(this.nextRecordID), recordData);
            this.version++;
            int curRecordID = this.nextRecordID;
            this.nextRecordID++;
            this.lastModified = System.currentTimeMillis();
            r0 = r0;
            this.recordStoreManager.saveChanges(this);
            fireRecordListener(1, curRecordID);
            return curRecordID;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.microedition.rms.RecordStore
    public void deleteRecord(int recordId) throws RecordStoreException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        synchronized (this) {
            if (this.records.remove(new Integer(recordId)) == null) {
                throw new InvalidRecordIDException();
            }
            this.version++;
            this.lastModified = System.currentTimeMillis();
        }
        this.recordStoreManager.saveChanges(this);
        fireRecordListener(4, recordId);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.microedition.rms.RecordStore
    public int getRecordSize(int recordId) throws RecordStoreException {
        int length;
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        synchronized (this) {
            byte[] data = (byte[]) this.records.get(new Integer(recordId));
            if (data == null) {
                throw new InvalidRecordIDException();
            }
            length = data.length;
        }
        return length;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    @Override // javax.microedition.rms.RecordStore
    public int getRecord(int recordId, byte[] buffer, int offset) throws RecordStoreException {
        ?? r0 = this;
        synchronized (r0) {
            int recordSize = getRecordSize(recordId);
            System.arraycopy(this.records.get(new Integer(recordId)), 0, buffer, offset, recordSize);
            r0 = r0;
            fireRecordListener(2, recordId);
            return recordSize;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    @Override // javax.microedition.rms.RecordStore
    public byte[] getRecord(int recordId) throws RecordStoreException {
        ?? r0 = this;
        synchronized (r0) {
            byte[] data = new byte[getRecordSize(recordId)];
            getRecord(recordId, data, 0);
            r0 = r0;
            if (data.length < 1) {
                return null;
            }
            return data;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.microedition.rms.RecordStore
    public void setRecord(int recordId, byte[] newData, int offset, int numBytes) throws RecordStoreException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        if (numBytes > this.recordStoreManager.getSizeAvailable(this)) {
            throw new RecordStoreFullException();
        }
        byte[] recordData = new byte[numBytes];
        System.arraycopy(newData, offset, recordData, 0, numBytes);
        synchronized (this) {
            Integer id = new Integer(recordId);
            if (this.records.remove(id) == null) {
                throw new InvalidRecordIDException();
            }
            this.records.put(id, recordData);
            this.version++;
            this.lastModified = System.currentTimeMillis();
        }
        this.recordStoreManager.saveChanges(this);
        fireRecordListener(3, recordId);
    }

    @Override // javax.microedition.rms.RecordStore
    public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated) throws RecordStoreNotOpenException {
        if (!this.open) {
            throw new RecordStoreNotOpenException();
        }
        return new RecordEnumerationImpl(this, filter, comparator, keepUpdated);
    }

    public int getHeaderSize() {
        return this.recordStoreName.length() + 4 + 8 + 4;
    }

    public int getRecordHeaderSize() {
        return 8;
    }

    private void fireRecordListener(int type, int recordId) {
        long timestamp = System.currentTimeMillis();
        if (this.recordListeners != null) {
            Enumeration e = this.recordListeners.elements();
            while (e.hasMoreElements()) {
                RecordListener l = (RecordListener) e.nextElement();
                if (l instanceof ExtendedRecordListener) {
                    ((ExtendedRecordListener) l).recordEvent(type, timestamp, this, recordId);
                } else {
                    switch (type) {
                        case 1:
                            l.recordAdded(this, recordId);
                            break;
                        case 3:
                            l.recordChanged(this, recordId);
                            break;
                        case 4:
                            l.recordDeleted(this, recordId);
                            break;
                    }
                }
            }
        }
    }
}
