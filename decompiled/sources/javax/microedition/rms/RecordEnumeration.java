package javax.microedition.rms;

/* loaded from: avacs.jar:javax/microedition/rms/RecordEnumeration.class */
public interface RecordEnumeration {
    int numRecords();

    byte[] nextRecord() throws RecordStoreException;

    int nextRecordId() throws InvalidRecordIDException;

    byte[] previousRecord() throws RecordStoreException;

    int previousRecordId() throws InvalidRecordIDException;

    boolean hasNextElement();

    boolean hasPreviousElement();

    void reset();

    void rebuild();

    void keepUpdated(boolean z);

    boolean isKeptUpdated();

    void destroy();
}
