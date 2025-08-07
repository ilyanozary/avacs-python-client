package javax.microedition.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/* loaded from: avacs.jar:javax/microedition/io/Datagram.class */
public interface Datagram extends DataInput, DataOutput {
    String getAddress();

    byte[] getData();

    int getLength();

    int getOffset();

    void setAddress(String str) throws IOException;

    void setAddress(Datagram datagram);

    void setLength(int i);

    void setData(byte[] bArr, int i, int i2);

    void reset();
}
