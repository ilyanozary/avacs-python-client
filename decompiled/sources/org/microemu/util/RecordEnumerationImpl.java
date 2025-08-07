package org.microemu.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordComparator;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordFilter;
import javax.microedition.rms.RecordListener;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;

/* loaded from: avacs.jar:org/microemu/util/RecordEnumerationImpl.class */
public class RecordEnumerationImpl implements RecordEnumeration {
    private RecordStoreImpl recordStoreImpl;
    private RecordFilter filter;
    private RecordComparator comparator;
    private boolean keepUpdated;
    private int currentRecord;
    private Vector enumerationRecords = new Vector();
    private RecordListener recordListener = new RecordListener() { // from class: org.microemu.util.RecordEnumerationImpl.1
        @Override // javax.microedition.rms.RecordListener
        public void recordAdded(RecordStore recordStore, int recordId) {
            RecordEnumerationImpl.this.rebuild();
        }

        @Override // javax.microedition.rms.RecordListener
        public void recordChanged(RecordStore recordStore, int recordId) {
            RecordEnumerationImpl.this.rebuild();
        }

        @Override // javax.microedition.rms.RecordListener
        public void recordDeleted(RecordStore recordStore, int recordId) {
            RecordEnumerationImpl.this.rebuild();
        }
    };

    public RecordEnumerationImpl(RecordStoreImpl recordStoreImpl, RecordFilter filter, RecordComparator comparator, boolean keepUpdated) {
        this.recordStoreImpl = recordStoreImpl;
        this.filter = filter;
        this.comparator = comparator;
        this.keepUpdated = keepUpdated;
        rebuild();
        if (keepUpdated) {
            recordStoreImpl.addRecordListener(this.recordListener);
        }
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public int numRecords() {
        return this.enumerationRecords.size();
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public byte[] nextRecord() throws RecordStoreException {
        if (!this.recordStoreImpl.isOpen()) {
            throw new RecordStoreNotOpenException();
        }
        if (this.currentRecord >= numRecords()) {
            throw new InvalidRecordIDException();
        }
        byte[] result = ((EnumerationRecord) this.enumerationRecords.elementAt(this.currentRecord)).value;
        this.currentRecord++;
        return result;
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public int nextRecordId() throws InvalidRecordIDException {
        if (this.currentRecord >= numRecords()) {
            throw new InvalidRecordIDException();
        }
        int result = ((EnumerationRecord) this.enumerationRecords.elementAt(this.currentRecord)).recordId;
        this.currentRecord++;
        return result;
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public byte[] previousRecord() throws RecordStoreException {
        if (!this.recordStoreImpl.isOpen()) {
            throw new RecordStoreNotOpenException();
        }
        if (this.currentRecord < 0) {
            throw new InvalidRecordIDException();
        }
        byte[] result = ((EnumerationRecord) this.enumerationRecords.elementAt(this.currentRecord)).value;
        this.currentRecord--;
        return result;
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public int previousRecordId() throws InvalidRecordIDException {
        if (this.currentRecord < 0) {
            throw new InvalidRecordIDException();
        }
        int result = ((EnumerationRecord) this.enumerationRecords.elementAt(this.currentRecord)).recordId;
        this.currentRecord--;
        return result;
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public boolean hasNextElement() {
        if (this.currentRecord == numRecords()) {
            return false;
        }
        return true;
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public boolean hasPreviousElement() {
        if (this.currentRecord == 0) {
            return false;
        }
        return true;
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public void reset() {
        this.currentRecord = 0;
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public void rebuild() {
        this.enumerationRecords.removeAllElements();
        Enumeration e = this.recordStoreImpl.records.keys();
        while (e.hasMoreElements()) {
            Object key = e.nextElement();
            byte[] data = (byte[]) this.recordStoreImpl.records.get(key);
            if (this.filter == null || this.filter.matches(data)) {
                this.enumerationRecords.add(new EnumerationRecord(((Integer) key).intValue(), data));
            }
        }
        if (this.comparator != null) {
            Collections.sort(this.enumerationRecords, new Comparator() { // from class: org.microemu.util.RecordEnumerationImpl.2
                @Override // java.util.Comparator
                public int compare(Object lhs, Object rhs) {
                    int compare = RecordEnumerationImpl.this.comparator.compare(((EnumerationRecord) lhs).value, ((EnumerationRecord) rhs).value);
                    if (compare == 0) {
                        return 0;
                    }
                    if (compare == 1) {
                        return 1;
                    }
                    return -1;
                }
            });
        }
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public void keepUpdated(boolean keepUpdated) {
        if (keepUpdated) {
            if (!this.keepUpdated) {
                rebuild();
                this.recordStoreImpl.addRecordListener(this.recordListener);
            }
        } else {
            this.recordStoreImpl.removeRecordListener(this.recordListener);
        }
        this.keepUpdated = keepUpdated;
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public boolean isKeptUpdated() {
        return this.keepUpdated;
    }

    @Override // javax.microedition.rms.RecordEnumeration
    public void destroy() {
    }

    /* loaded from: avacs.jar:org/microemu/util/RecordEnumerationImpl$EnumerationRecord.class */
    class EnumerationRecord {
        int recordId;
        byte[] value;

        EnumerationRecord(int recordId, byte[] value) {
            this.recordId = recordId;
            this.value = value;
        }
    }
}
