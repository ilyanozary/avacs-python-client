package javax.microedition.rms;

/* loaded from: avacs.jar:javax/microedition/rms/RecordListener.class */
public interface RecordListener {
    void recordAdded(RecordStore recordStore, int i);

    void recordChanged(RecordStore recordStore, int i);

    void recordDeleted(RecordStore recordStore, int i);
}
