package javax.microedition.io;

import java.io.IOException;

/* loaded from: avacs.jar:javax/microedition/io/DatagramConnection.class */
public interface DatagramConnection extends Connection {
    int getMaximumLength() throws IOException;

    int getNominalLength() throws IOException;

    void send(Datagram datagram) throws IOException;

    void receive(Datagram datagram) throws IOException;

    Datagram newDatagram(int i) throws IOException;

    Datagram newDatagram(int i, String str) throws IOException;

    Datagram newDatagram(byte[] bArr, int i) throws IOException;

    Datagram newDatagram(byte[] bArr, int i, String str) throws IOException;
}
