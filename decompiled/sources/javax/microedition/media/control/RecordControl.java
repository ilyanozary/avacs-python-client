package javax.microedition.media.control;

import java.io.IOException;
import java.io.OutputStream;
import javax.microedition.media.Control;
import javax.microedition.media.MediaException;

/* loaded from: avacs.jar:javax/microedition/media/control/RecordControl.class */
public interface RecordControl extends Control {
    void setRecordStream(OutputStream outputStream);

    void setRecordLocation(String str) throws IOException, MediaException;

    String getContentType();

    void startRecord();

    void stopRecord();

    void commit() throws IOException;

    int setRecordSizeLimit(int i) throws MediaException;

    void reset() throws IOException;
}
