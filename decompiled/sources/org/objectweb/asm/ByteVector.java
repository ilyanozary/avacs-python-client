package org.objectweb.asm;

/* loaded from: avacs.jar:org/objectweb/asm/ByteVector.class */
public class ByteVector {
    byte[] a;
    int b;

    public ByteVector() {
        this.a = new byte[64];
    }

    public ByteVector(int i) {
        this.a = new byte[i];
    }

    public ByteVector putByte(int i) {
        int i2 = this.b;
        if (i2 + 1 > this.a.length) {
            a(1);
        }
        this.a[i2] = (byte) i;
        this.b = i2 + 1;
        return this;
    }

    ByteVector a(int i, int i2) {
        int i3 = this.b;
        if (i3 + 2 > this.a.length) {
            a(2);
        }
        byte[] bArr = this.a;
        int i4 = i3 + 1;
        bArr[i3] = (byte) i;
        bArr[i4] = (byte) i2;
        this.b = i4 + 1;
        return this;
    }

    public ByteVector putShort(int i) {
        int i2 = this.b;
        if (i2 + 2 > this.a.length) {
            a(2);
        }
        byte[] bArr = this.a;
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i >>> 8);
        bArr[i3] = (byte) i;
        this.b = i3 + 1;
        return this;
    }

    ByteVector b(int i, int i2) {
        int i3 = this.b;
        if (i3 + 3 > this.a.length) {
            a(3);
        }
        byte[] bArr = this.a;
        int i4 = i3 + 1;
        bArr[i3] = (byte) i;
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        bArr[i5] = (byte) i2;
        this.b = i5 + 1;
        return this;
    }

    public ByteVector putInt(int i) {
        int i2 = this.b;
        if (i2 + 4 > this.a.length) {
            a(4);
        }
        byte[] bArr = this.a;
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i >>> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i >>> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i >>> 8);
        bArr[i5] = (byte) i;
        this.b = i5 + 1;
        return this;
    }

    public ByteVector putLong(long j) {
        int i = this.b;
        if (i + 8 > this.a.length) {
            a(8);
        }
        byte[] bArr = this.a;
        int i2 = (int) (j >>> 32);
        int i3 = i + 1;
        bArr[i] = (byte) (i2 >>> 24);
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i2 >>> 16);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 8);
        int i6 = i5 + 1;
        bArr[i5] = (byte) i2;
        int i7 = (int) j;
        int i8 = i6 + 1;
        bArr[i6] = (byte) (i7 >>> 24);
        int i9 = i8 + 1;
        bArr[i8] = (byte) (i7 >>> 16);
        int i10 = i9 + 1;
        bArr[i9] = (byte) (i7 >>> 8);
        bArr[i10] = (byte) i7;
        this.b = i10 + 1;
        return this;
    }

    public ByteVector putUTF8(String str) {
        int length = str.length();
        if (this.b + 2 + length > this.a.length) {
            a(2 + length);
        }
        int i = this.b;
        byte[] bArr = this.a;
        int i2 = i + 1;
        bArr[i] = (byte) (length >>> 8);
        int i3 = i2 + 1;
        bArr[i2] = (byte) length;
        for (int i4 = 0; i4 < length; i4++) {
            char cCharAt = str.charAt(i4);
            if (cCharAt < 1 || cCharAt > 127) {
                int i5 = i4;
                for (int i6 = i4; i6 < length; i6++) {
                    char cCharAt2 = str.charAt(i6);
                    i5 = (cCharAt2 < 1 || cCharAt2 > 127) ? cCharAt2 > 2047 ? i5 + 3 : i5 + 2 : i5 + 1;
                }
                bArr[this.b] = (byte) (i5 >>> 8);
                bArr[this.b + 1] = (byte) i5;
                if (this.b + 2 + i5 > bArr.length) {
                    this.b = i3;
                    a(2 + i5);
                    bArr = this.a;
                }
                for (int i7 = i4; i7 < length; i7++) {
                    char cCharAt3 = str.charAt(i7);
                    if (cCharAt3 >= 1 && cCharAt3 <= 127) {
                        int i8 = i3;
                        i3++;
                        bArr[i8] = (byte) cCharAt3;
                    } else if (cCharAt3 > 2047) {
                        int i9 = i3;
                        int i10 = i3 + 1;
                        bArr[i9] = (byte) (224 | ((cCharAt3 >> '\f') & 15));
                        int i11 = i10 + 1;
                        bArr[i10] = (byte) (128 | ((cCharAt3 >> 6) & 63));
                        i3 = i11 + 1;
                        bArr[i11] = (byte) (128 | (cCharAt3 & '?'));
                    } else {
                        int i12 = i3;
                        int i13 = i3 + 1;
                        bArr[i12] = (byte) (192 | ((cCharAt3 >> 6) & 31));
                        i3 = i13 + 1;
                        bArr[i13] = (byte) (128 | (cCharAt3 & '?'));
                    }
                }
                this.b = i3;
                return this;
            }
            int i14 = i3;
            i3++;
            bArr[i14] = (byte) cCharAt;
        }
        this.b = i3;
        return this;
    }

    public ByteVector putByteArray(byte[] bArr, int i, int i2) {
        if (this.b + i2 > this.a.length) {
            a(i2);
        }
        if (bArr != null) {
            System.arraycopy(bArr, i, this.a, this.b, i2);
        }
        this.b += i2;
        return this;
    }

    private void a(int i) {
        int length = 2 * this.a.length;
        int i2 = this.b + i;
        byte[] bArr = new byte[length > i2 ? length : i2];
        System.arraycopy(this.a, 0, bArr, 0, this.b);
        this.a = bArr;
    }
}
