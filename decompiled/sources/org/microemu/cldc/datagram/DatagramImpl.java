package org.microemu.cldc.datagram;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.nio.BufferOverflowException;
import javax.microedition.io.Datagram;

/* loaded from: avacs.jar:org/microemu/cldc/datagram/DatagramImpl.class */
public class DatagramImpl implements Datagram {
    private DatagramPacket packet;
    private BufferOutputStream os;
    private DataOutputStream dos;
    private DataInputStream dis;

    /* loaded from: avacs.jar:org/microemu/cldc/datagram/DatagramImpl$BufferOutputStream.class */
    class BufferOutputStream extends OutputStream {
        private int originalOffset;
        private int offset;

        public BufferOutputStream() {
            this.originalOffset = DatagramImpl.this.packet.getOffset();
            this.offset = this.originalOffset;
        }

        @Override // java.io.OutputStream
        public void write(int b) throws IOException {
            byte[] buffer = DatagramImpl.this.packet.getData();
            if (this.offset == buffer.length - 1) {
                throw new BufferOverflowException();
            }
            int i = this.offset;
            this.offset = i + 1;
            buffer[i] = (byte) b;
        }

        public void reset() {
            this.offset = this.originalOffset;
        }
    }

    DatagramImpl(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Invalid size: " + size);
        }
        this.packet = new DatagramPacket(new byte[size], size);
        initialiseInOut();
    }

    DatagramImpl(byte[] buff, int length) {
        this.packet = new DatagramPacket(buff, length);
        initialiseInOut();
    }

    private void initialiseInOut() {
        this.os = new BufferOutputStream();
        this.dos = new DataOutputStream(this.os);
        this.dis = new DataInputStream(new ByteArrayInputStream(this.packet.getData()));
    }

    @Override // javax.microedition.io.Datagram
    public String getAddress() {
        return String.valueOf(this.packet.getAddress().getCanonicalHostName()) + ":" + this.packet.getPort();
    }

    @Override // javax.microedition.io.Datagram
    public byte[] getData() {
        return this.packet.getData();
    }

    @Override // javax.microedition.io.Datagram
    public int getLength() {
        return this.packet.getLength();
    }

    @Override // javax.microedition.io.Datagram
    public int getOffset() {
        return this.packet.getOffset();
    }

    @Override // javax.microedition.io.Datagram
    public void reset() {
        try {
            this.os.reset();
            this.dis.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override // javax.microedition.io.Datagram
    public void setAddress(String address) throws IOException {
        if (address == null) {
            throw new NullPointerException("address cannot be null");
        }
        int index = address.indexOf(58);
        if (index == -1) {
            throw new IllegalArgumentException("Missing port in address: " + address);
        }
        String host = address.substring(0, index);
        String port = address.substring(index + 1);
        this.packet.setAddress(InetAddress.getByName(host));
        this.packet.setPort(Integer.parseInt(port));
    }

    @Override // javax.microedition.io.Datagram
    public void setAddress(Datagram reference) {
        this.packet.setAddress(((DatagramImpl) reference).getDatagramPacket().getAddress());
        this.packet.setPort(((DatagramImpl) reference).getDatagramPacket().getPort());
    }

    @Override // javax.microedition.io.Datagram
    public void setData(byte[] buffer, int offset, int len) {
        this.packet.setData(buffer, offset, len);
    }

    @Override // javax.microedition.io.Datagram
    public void setLength(int len) {
        this.packet.setLength(len);
    }

    @Override // java.io.DataInput
    public boolean readBoolean() throws IOException {
        return this.dis.readBoolean();
    }

    @Override // java.io.DataInput
    public byte readByte() throws IOException {
        return this.dis.readByte();
    }

    @Override // java.io.DataInput
    public char readChar() throws IOException {
        return this.dis.readChar();
    }

    @Override // java.io.DataInput
    public double readDouble() throws IOException {
        return this.dis.readDouble();
    }

    @Override // java.io.DataInput
    public float readFloat() throws IOException {
        return this.dis.readFloat();
    }

    @Override // java.io.DataInput
    public void readFully(byte[] b) throws IOException {
        this.dis.readFully(b);
    }

    @Override // java.io.DataInput
    public void readFully(byte[] b, int off, int len) throws IOException {
        this.dis.read(b, off, len);
    }

    @Override // java.io.DataInput
    public int readInt() throws IOException {
        return this.dis.readInt();
    }

    @Override // java.io.DataInput
    public String readLine() throws IOException {
        return this.dis.readLine();
    }

    @Override // java.io.DataInput
    public long readLong() throws IOException {
        return this.dis.readLong();
    }

    @Override // java.io.DataInput
    public short readShort() throws IOException {
        return this.dis.readShort();
    }

    @Override // java.io.DataInput
    public String readUTF() throws IOException {
        return this.dis.readUTF();
    }

    @Override // java.io.DataInput
    public int readUnsignedByte() throws IOException {
        return this.dis.readUnsignedByte();
    }

    @Override // java.io.DataInput
    public int readUnsignedShort() throws IOException {
        return this.dis.readUnsignedShort();
    }

    @Override // java.io.DataInput
    public int skipBytes(int n) throws IOException {
        return this.dis.skipBytes(n);
    }

    @Override // java.io.DataOutput
    public void write(int b) throws IOException {
        this.dos.write(b);
    }

    @Override // java.io.DataOutput
    public void write(byte[] b) throws IOException {
        this.dos.write(b);
    }

    @Override // java.io.DataOutput
    public void write(byte[] b, int off, int len) throws IOException {
        this.dos.write(b, off, len);
    }

    @Override // java.io.DataOutput
    public void writeBoolean(boolean v) throws IOException {
        this.dos.writeBoolean(v);
    }

    @Override // java.io.DataOutput
    public void writeByte(int v) throws IOException {
        this.dos.writeByte(v);
    }

    @Override // java.io.DataOutput
    public void writeBytes(String s) throws IOException {
        this.dos.writeBytes(s);
    }

    @Override // java.io.DataOutput
    public void writeChar(int v) throws IOException {
        this.dos.writeChar(v);
    }

    @Override // java.io.DataOutput
    public void writeChars(String v) throws IOException {
        this.dos.writeChars(v);
    }

    @Override // java.io.DataOutput
    public void writeDouble(double v) throws IOException {
        this.dos.writeDouble(v);
    }

    @Override // java.io.DataOutput
    public void writeFloat(float v) throws IOException {
        this.dos.writeFloat(v);
    }

    @Override // java.io.DataOutput
    public void writeInt(int v) throws IOException {
        this.dos.writeInt(v);
    }

    @Override // java.io.DataOutput
    public void writeLong(long v) throws IOException {
        this.dos.writeLong(v);
    }

    @Override // java.io.DataOutput
    public void writeShort(int v) throws IOException {
        this.dos.writeShort(v);
    }

    @Override // java.io.DataOutput
    public void writeUTF(String str) throws IOException {
        this.dos.writeUTF(str);
    }

    DatagramPacket getDatagramPacket() {
        return this.packet;
    }
}
