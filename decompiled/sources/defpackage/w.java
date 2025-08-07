package defpackage;

/* loaded from: avacs.jar:w.class */
public final class w {
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;

    /* renamed from: a, reason: collision with other field name */
    private long f210a;

    /* renamed from: a, reason: collision with other field name */
    private int[] f208a = new int[80];

    /* renamed from: a, reason: collision with other field name */
    private byte[] f209a = new byte[4];
    private int g = 0;

    public w() {
        a();
    }

    private void a(byte[] bArr, int i) {
        int i2 = bArr[i] << 24;
        int i3 = i + 1;
        int i4 = i2 | ((bArr[i3] & 255) << 16);
        int i5 = i3 + 1;
        this.f208a[this.f] = i4 | ((bArr[i5] & 255) << 8) | (bArr[i5 + 1] & 255);
        int i6 = this.f + 1;
        this.f = i6;
        if (i6 == 16) {
            b();
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public final int m132a(byte[] bArr, int i) {
        long j = this.f210a << 3;
        a(Byte.MIN_VALUE);
        while (this.g != 0) {
            a((byte) 0);
        }
        if (this.f > 14) {
            b();
        }
        this.f208a[14] = (int) (j >>> 32);
        this.f208a[15] = (int) j;
        b();
        a(this.a, bArr, 0);
        a(this.b, bArr, 4);
        a(this.c, bArr, 8);
        a(this.d, bArr, 12);
        a(this.e, bArr, 16);
        a();
        return 20;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [int] */
    private static void a(int i, byte[] bArr, int i2) {
        bArr[i2] = i >> 24;
        int i3 = i2 + 1;
        bArr[i3] = (byte) (i >>> 16);
        int i4 = i3 + 1;
        bArr[i4] = (byte) (i >>> 8);
        bArr[i4 + 1] = (byte) i;
    }

    public final void a() {
        this.f210a = 0L;
        this.g = 0;
        for (int i = 0; i < this.f209a.length; i++) {
            this.f209a[i] = 0;
        }
        this.a = 1732584193;
        this.b = -271733879;
        this.c = -1732584194;
        this.d = 271733878;
        this.e = -1009589776;
        this.f = 0;
        for (int i2 = 0; i2 != this.f208a.length; i2++) {
            this.f208a[i2] = 0;
        }
    }

    private static int a(int i, int i2, int i3) {
        return (i & i2) | ((i ^ (-1)) & i3);
    }

    private static int b(int i, int i2, int i3) {
        return (i & i2) | (i & i3) | (i2 & i3);
    }

    private void b() {
        for (int i = 16; i < 80; i++) {
            int i2 = ((this.f208a[i - 3] ^ this.f208a[i - 8]) ^ this.f208a[i - 14]) ^ this.f208a[i - 16];
            this.f208a[i] = (i2 << 1) | (i2 >>> 31);
        }
        int iB = this.a;
        int iB2 = this.b;
        int i3 = this.c;
        int i4 = this.d;
        int i5 = this.e;
        int i6 = 0;
        for (int i7 = 0; i7 < 4; i7++) {
            int i8 = i6;
            int i9 = i6 + 1;
            int iA = i5 + ((iB << 5) | (iB >>> 27)) + a(iB2, i3, i4) + this.f208a[i8] + 1518500249;
            int i10 = (iB2 << 30) | (iB2 >>> 2);
            int i11 = i9 + 1;
            int iA2 = i4 + ((iA << 5) | (iA >>> 27)) + a(iB, i10, i3) + this.f208a[i9] + 1518500249;
            int i12 = (iB << 30) | (iB >>> 2);
            int i13 = i11 + 1;
            int iA3 = i3 + ((iA2 << 5) | (iA2 >>> 27)) + a(iA, i12, i10) + this.f208a[i11] + 1518500249;
            i5 = (iA << 30) | (iA >>> 2);
            int i14 = i13 + 1;
            iB2 = i10 + ((iA3 << 5) | (iA3 >>> 27)) + a(iA2, i5, i12) + this.f208a[i13] + 1518500249;
            i4 = (iA2 << 30) | (iA2 >>> 2);
            i6 = i14 + 1;
            iB = i12 + ((iB2 << 5) | (iB2 >>> 27)) + a(iA3, i4, i5) + this.f208a[i14] + 1518500249;
            i3 = (iA3 << 30) | (iA3 >>> 2);
        }
        for (int i15 = 0; i15 < 4; i15++) {
            int i16 = i6;
            int i17 = i6 + 1;
            int i18 = i5 + ((iB << 5) | (iB >>> 27)) + ((iB2 ^ i3) ^ i4) + this.f208a[i16] + 1859775393;
            int i19 = (iB2 << 30) | (iB2 >>> 2);
            int i20 = i17 + 1;
            int i21 = i4 + ((i18 << 5) | (i18 >>> 27)) + ((iB ^ i19) ^ i3) + this.f208a[i17] + 1859775393;
            int i22 = (iB << 30) | (iB >>> 2);
            int i23 = i20 + 1;
            int i24 = i3 + ((i21 << 5) | (i21 >>> 27)) + ((i18 ^ i22) ^ i19) + this.f208a[i20] + 1859775393;
            i5 = (i18 << 30) | (i18 >>> 2);
            int i25 = i23 + 1;
            iB2 = i19 + ((i24 << 5) | (i24 >>> 27)) + ((i21 ^ i5) ^ i22) + this.f208a[i23] + 1859775393;
            i4 = (i21 << 30) | (i21 >>> 2);
            i6 = i25 + 1;
            iB = i22 + ((iB2 << 5) | (iB2 >>> 27)) + ((i24 ^ i4) ^ i5) + this.f208a[i25] + 1859775393;
            i3 = (i24 << 30) | (i24 >>> 2);
        }
        for (int i26 = 0; i26 < 4; i26++) {
            int i27 = i6;
            int iB3 = i5 + (((((iB << 5) | (iB >>> 27)) + b(iB2, i3, i4)) + this.f208a[i27]) - 1894007588);
            int iB4 = i4 + (((((iB3 << 5) | (iB3 >>> 27)) + b(iB, r0, i3)) + this.f208a[r12]) - 1894007588);
            int iB5 = i3 + (((((iB4 << 5) | (iB4 >>> 27)) + b(iB3, r0, r0)) + this.f208a[r12]) - 1894007588);
            i5 = (iB3 << 30) | (iB3 >>> 2);
            iB2 = ((iB2 << 30) | (iB2 >>> 2)) + (((((iB5 << 5) | (iB5 >>> 27)) + b(iB4, i5, r0)) + this.f208a[r12]) - 1894007588);
            i4 = (iB4 << 30) | (iB4 >>> 2);
            i6 = i6 + 1 + 1 + 1 + 1 + 1;
            iB = ((iB << 30) | (iB >>> 2)) + (((((iB2 << 5) | (iB2 >>> 27)) + b(iB5, i4, i5)) + this.f208a[r12]) - 1894007588);
            i3 = (iB5 << 30) | (iB5 >>> 2);
        }
        for (int i28 = 0; i28 <= 3; i28++) {
            int i29 = i6;
            int i30 = i5 + (((((iB << 5) | (iB >>> 27)) + ((iB2 ^ i3) ^ i4)) + this.f208a[i29]) - 899497514);
            int i31 = i4 + (((((i30 << 5) | (i30 >>> 27)) + ((iB ^ r0) ^ i3)) + this.f208a[r12]) - 899497514);
            int i32 = i3 + (((((i31 << 5) | (i31 >>> 27)) + ((i30 ^ r0) ^ r0)) + this.f208a[r12]) - 899497514);
            i5 = (i30 << 30) | (i30 >>> 2);
            iB2 = ((iB2 << 30) | (iB2 >>> 2)) + (((((i32 << 5) | (i32 >>> 27)) + ((i31 ^ i5) ^ r0)) + this.f208a[r12]) - 899497514);
            i4 = (i31 << 30) | (i31 >>> 2);
            i6 = i6 + 1 + 1 + 1 + 1 + 1;
            iB = ((iB << 30) | (iB >>> 2)) + (((((iB2 << 5) | (iB2 >>> 27)) + ((i32 ^ i4) ^ i5)) + this.f208a[r12]) - 899497514);
            i3 = (i32 << 30) | (i32 >>> 2);
        }
        this.a += iB;
        this.b += iB2;
        this.c += i3;
        this.d += i4;
        this.e += i5;
        this.f = 0;
        for (int i33 = 0; i33 < 16; i33++) {
            this.f208a[i33] = 0;
        }
    }

    public final void a(byte b) {
        byte[] bArr = this.f209a;
        int i = this.g;
        this.g = i + 1;
        bArr[i] = b;
        if (this.g == this.f209a.length) {
            a(this.f209a, 0);
            this.g = 0;
        }
        this.f210a++;
    }

    public final void a(byte[] bArr, int i, int i2) {
        while (this.g != 0 && i2 > 0) {
            a(bArr[i]);
            i++;
            i2--;
        }
        while (i2 > this.f209a.length) {
            a(bArr, i);
            i += this.f209a.length;
            i2 -= this.f209a.length;
            this.f210a += this.f209a.length;
        }
        while (i2 > 0) {
            a(bArr[i]);
            i++;
            i2--;
        }
    }
}
