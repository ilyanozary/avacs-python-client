package org.microemu.util;

import javax.microedition.rms.RecordListener;
import javax.microedition.rms.RecordStore;

/* loaded from: avacs.jar:org/microemu/util/ExtendedRecordListener.class */
public interface ExtendedRecordListener extends RecordListener {
    public static final int RECORD_ADD = 1;
    public static final int RECORD_READ = 2;
    public static final int RECORD_CHANGE = 3;
    public static final int RECORD_DELETE = 4;
    public static final int RECORDSTORE_OPEN = 8;
    public static final int RECORDSTORE_CLOSE = 9;
    public static final int RECORDSTORE_DELETE = 10;

    void recordEvent(int i, long j, RecordStore recordStore, int i2);

    void recordStoreEvent(int i, long j, String str);
}
