package org.objectweb.asm;

/* loaded from: avacs.jar:org/objectweb/asm/Label.class */
public class Label {
    public Object info;
    int a;
    int b;
    int c;
    private int d;
    private int[] e;
    int f;
    int g;
    Frame h;
    Label i;
    Edge j;
    Label k;

    public int getOffset() {
        if ((this.a & 2) == 0) {
            throw new IllegalStateException("Label offset position has not been resolved yet");
        }
        return this.c;
    }

    void a(MethodWriter methodWriter, ByteVector byteVector, int i, boolean z) {
        if ((this.a & 2) != 0) {
            if (z) {
                byteVector.putInt(this.c - i);
                return;
            } else {
                byteVector.putShort(this.c - i);
                return;
            }
        }
        if (z) {
            a((-1) - i, byteVector.b);
            byteVector.putInt(-1);
        } else {
            a(i, byteVector.b);
            byteVector.putShort(-1);
        }
    }

    private void a(int i, int i2) {
        if (this.e == null) {
            this.e = new int[6];
        }
        if (this.d >= this.e.length) {
            int[] iArr = new int[this.e.length + 6];
            System.arraycopy(this.e, 0, iArr, 0, this.e.length);
            this.e = iArr;
        }
        int[] iArr2 = this.e;
        int i3 = this.d;
        this.d = i3 + 1;
        iArr2[i3] = i;
        int[] iArr3 = this.e;
        int i4 = this.d;
        this.d = i4 + 1;
        iArr3[i4] = i2;
    }

    boolean a(MethodWriter methodWriter, int i, byte[] bArr) {
        boolean z = false;
        this.a |= 2;
        this.c = i;
        int i2 = 0;
        while (i2 < this.d) {
            int i3 = i2;
            int i4 = i2 + 1;
            int i5 = this.e[i3];
            i2 = i4 + 1;
            int i6 = this.e[i4];
            if (i5 >= 0) {
                int i7 = i - i5;
                if (i7 < -32768 || i7 > 32767) {
                    int i8 = bArr[i6 - 1] & 255;
                    if (i8 <= 168) {
                        bArr[i6 - 1] = (byte) (i8 + 49);
                    } else {
                        bArr[i6 - 1] = (byte) (i8 + 20);
                    }
                    z = true;
                }
                bArr[i6] = (byte) (i7 >>> 8);
                bArr[i6 + 1] = (byte) i7;
            } else {
                int i9 = i + i5 + 1;
                int i10 = i6 + 1;
                bArr[i6] = (byte) (i9 >>> 24);
                int i11 = i10 + 1;
                bArr[i10] = (byte) (i9 >>> 16);
                bArr[i11] = (byte) (i9 >>> 8);
                bArr[i11 + 1] = (byte) i9;
            }
        }
        return z;
    }

    Label a() {
        return this.h == null ? this : this.h.b;
    }

    boolean a(long j) {
        return ((this.a & 1024) == 0 || (this.e[(int) (j >>> 32)] & ((int) j)) == 0) ? false : true;
    }

    boolean a(Label label) {
        for (int i = 0; i < this.e.length; i++) {
            if ((this.e[i] & label.e[i]) != 0) {
                return true;
            }
        }
        return false;
    }

    void a(long j, int i) {
        if ((this.a & 1024) == 0) {
            this.a |= 1024;
            this.e = new int[((i - 1) / 32) + 1];
        }
        int[] iArr = this.e;
        int i2 = (int) (j >>> 32);
        iArr[i2] = iArr[i2] | ((int) j);
    }

    void b(Label label, long j, int i) {
        if (label != null) {
            if ((this.a & 1024) != 0) {
                return;
            }
            this.a |= 1024;
            if ((this.a & 256) != 0 && !a(label)) {
                Edge edge = new Edge();
                edge.a = this.f;
                edge.b = label.j.b;
                edge.c = this.j;
                this.j = edge;
            }
        } else if (a(j)) {
            return;
        } else {
            a(j, i);
        }
        Edge edge2 = this.j;
        while (true) {
            Edge edge3 = edge2;
            if (edge3 == null) {
                return;
            }
            if ((this.a & 128) == 0 || edge3 != this.j.c) {
                edge3.b.b(label, j, i);
            }
            edge2 = edge3.c;
        }
    }

    public String toString() {
        return new StringBuffer().append("L").append(System.identityHashCode(this)).toString();
    }
}
