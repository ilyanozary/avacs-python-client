package javax.microedition.rms;

/* loaded from: avacs.jar:javax/microedition/rms/RecordComparator.class */
public interface RecordComparator {
    public static final int EQUIVALENT = 0;
    public static final int FOLLOWS = 1;
    public static final int PRECEDES = -1;

    int compare(byte[] bArr, byte[] bArr2);
}
