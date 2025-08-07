package defpackage;

/* loaded from: avacs.jar:aa.class */
public final class aa {
    private static long a = 10;

    /* renamed from: a, reason: collision with other field name */
    private w f8a;

    /* renamed from: b, reason: collision with other field name */
    private byte[] f10b = new byte[20];
    private long c = 1;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f9a = new byte[20];
    private long b = 1;

    public aa(w wVar) {
        this.f8a = wVar;
    }

    public final void a(long j) {
        synchronized (this) {
            b(j);
            b(this.f10b);
            c(this.f10b);
        }
    }

    public final void a(byte[] bArr) {
        int length = bArr.length;
        synchronized (this) {
            int i = 0;
            a();
            int i2 = length + 0;
            for (int i3 = 0; i3 != i2; i3++) {
                if (i == this.f9a.length) {
                    a();
                    i = 0;
                }
                int i4 = i;
                i++;
                bArr[i3] = this.f9a[i4];
            }
        }
    }

    private void a() {
        long j = this.b;
        this.b = j + 1;
        b(j);
        b(this.f9a);
        b(this.f10b);
        c(this.f9a);
        if (this.b % a == 0) {
            b(this.f10b);
            long j2 = this.c;
            this.c = j2 + 1;
            b(j2);
            c(this.f10b);
        }
    }

    private void b(long j) {
        for (int i = 0; i != 8; i++) {
            this.f8a.a((byte) j);
            j >>>= 8;
        }
    }

    private void b(byte[] bArr) {
        this.f8a.a(bArr, 0, bArr.length);
    }

    private void c(byte[] bArr) {
        this.f8a.m132a(bArr, 0);
    }
}
