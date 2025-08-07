package org.microemu.cldc.datagram;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import javax.microedition.io.UDPDatagramConnection;
import org.microemu.microedition.io.ConnectionImplementation;

/* loaded from: avacs.jar:org/microemu/cldc/datagram/Connection.class */
public class Connection implements DatagramConnection, UDPDatagramConnection, ConnectionImplementation {
    public static final String PROTOCOL = "datagram://";
    private DatagramSocket socket;
    private String address;

    @Override // javax.microedition.io.Connection
    public void close() throws IOException {
        this.socket.close();
    }

    @Override // javax.microedition.io.DatagramConnection
    public int getMaximumLength() throws IOException {
        return Math.min(this.socket.getReceiveBufferSize(), this.socket.getSendBufferSize());
    }

    @Override // javax.microedition.io.DatagramConnection
    public int getNominalLength() throws IOException {
        return getMaximumLength();
    }

    @Override // javax.microedition.io.DatagramConnection
    public void send(Datagram dgram) throws IOException {
        this.socket.send(((DatagramImpl) dgram).getDatagramPacket());
    }

    @Override // javax.microedition.io.DatagramConnection
    public void receive(Datagram dgram) throws IOException {
        this.socket.receive(((DatagramImpl) dgram).getDatagramPacket());
    }

    @Override // javax.microedition.io.DatagramConnection
    public Datagram newDatagram(int size) throws IOException {
        return newDatagram(size, this.address);
    }

    @Override // javax.microedition.io.DatagramConnection
    public Datagram newDatagram(int size, String addr) throws IOException {
        Datagram datagram = new DatagramImpl(size);
        datagram.setAddress(addr);
        return datagram;
    }

    @Override // javax.microedition.io.DatagramConnection
    public Datagram newDatagram(byte[] buf, int size) throws IOException {
        return newDatagram(buf, size, this.address);
    }

    @Override // javax.microedition.io.DatagramConnection
    public Datagram newDatagram(byte[] buf, int size, String addr) throws IOException {
        Datagram datagram = new DatagramImpl(buf, size);
        datagram.setAddress(addr);
        return datagram;
    }

    @Override // javax.microedition.io.UDPDatagramConnection
    public String getLocalAddress() throws IOException {
        InetAddress address;
        InetAddress address2 = this.socket.getInetAddress();
        if (address2 == null) {
            address = InetAddress.getLocalHost();
        } else {
            address = this.socket.getLocalAddress();
        }
        return address.getHostAddress();
    }

    @Override // javax.microedition.io.UDPDatagramConnection
    public int getLocalPort() throws IOException {
        return this.socket.getLocalPort();
    }

    @Override // org.microemu.microedition.io.ConnectionImplementation
    public javax.microedition.io.Connection openConnection(String name, int mode, boolean timeouts) throws IOException, NumberFormatException {
        if (!org.microemu.cldc.http.Connection.isAllowNetworkConnection()) {
            throw new IOException("No network");
        }
        if (!name.startsWith(PROTOCOL)) {
            throw new IOException("Invalid Protocol " + name);
        }
        this.address = name.substring(PROTOCOL.length());
        int index = this.address.indexOf(58);
        if (index == -1) {
            throw new IOException("port missing");
        }
        int port = Integer.parseInt(this.address.substring(index + 1));
        if (index == 0) {
            this.socket = new DatagramSocket(port);
        } else {
            String host = this.address.substring(0, index);
            this.socket = new DatagramSocket();
            this.socket.connect(InetAddress.getByName(host), port);
        }
        return this;
    }
}
