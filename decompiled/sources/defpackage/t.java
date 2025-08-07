package defpackage;

import java.util.Random;

/* loaded from: avacs.jar:t.class */
public final class t extends Random {
    private static final aa a = new aa(new w());
    private aa b;

    public t() {
        this(a);
        setSeed(System.currentTimeMillis());
    }

    private t(aa aaVar) {
        super(0L);
        this.b = aaVar;
    }

    public final void a(byte[] bArr) {
        this.b.a(bArr);
    }

    @Override // java.util.Random
    public final void setSeed(long j) {
        if (j != 0) {
            this.b.a(j);
        }
    }

    @Override // java.util.Random
    public final int nextInt() {
        byte[] bArr = new byte[4];
        a(bArr);
        int i = 0;
        for (int i2 = 0; i2 < 4; i2++) {
            i = (i << 8) + (bArr[i2] & 255);
        }
        return i;
    }

    @Override // java.util.Random
    protected final int next(int i) {
        int i2 = (i + 7) / 8;
        byte[] bArr = new byte[i2];
        a(bArr);
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 = (i3 << 8) + (bArr[i4] & 255);
        }
        return i3 & ((1 << i) - 1);
    }
}
