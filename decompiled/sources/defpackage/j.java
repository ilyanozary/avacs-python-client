package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

/* loaded from: avacs.jar:j.class */
public class j extends i {
    private boolean b = true;

    /* renamed from: b, reason: collision with other field name */
    private byte[] f189b = null;
    private int e = 0;
    private boolean d = true;
    private int f = 0;
    private int g = 0;

    /* renamed from: c, reason: collision with other field name */
    private String f190c = "";

    /* renamed from: a, reason: collision with other field name */
    private SocketConnection f195a = null;

    /* renamed from: a, reason: collision with other field name */
    private InputStream f196a = null;

    /* renamed from: a, reason: collision with other field name */
    private OutputStream f197a = null;

    /* renamed from: e, reason: collision with other field name */
    private boolean f198e = false;
    private static boolean c = false;
    private static byte a = 1;

    /* renamed from: c, reason: collision with other field name */
    private static byte[] f191c = {1, 2, 3, 7, 7};

    /* renamed from: d, reason: collision with other field name */
    private static byte[] f192d = {1, 5, 6, 8, 8};

    /* renamed from: e, reason: collision with other field name */
    private static byte[] f193e = {1, 7, 8, 9, 9};

    /* renamed from: b, reason: collision with other field name */
    private static byte f194b = 5;

    @Override // defpackage.i
    public final int a(String str, String str2, StringBuffer stringBuffer, String str3, boolean z) throws InterruptedException {
        b();
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (long jCurrentTimeMillis2 = 0; a && jCurrentTimeMillis2 < this.f182b; jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException unused) {
            }
        }
        return this.f198e ? super.a(str, str2, stringBuffer, str3, z) : this.g;
    }

    @Override // defpackage.i
    public final int a(int i, String str, byte[] bArr) throws InterruptedException, IOException {
        boolean z = false;
        if (bArr != null) {
            z = true;
        }
        int i2 = 0;
        while (a) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException unused) {
            }
        }
        b();
        while (a) {
            try {
                Thread.sleep(7L);
            } catch (InterruptedException unused2) {
            }
        }
        a = true;
        if (this.f198e) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            try {
                a(a(new StringBuffer().append("").append(11).toString(), str), bArr);
                i2 = 111;
            } catch (Exception unused3) {
                c();
            }
            if (!z) {
                this.f181a.a(this.f180a, System.currentTimeMillis() - jCurrentTimeMillis);
            }
        }
        a = false;
        return i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00db  */
    @Override // defpackage.i
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int a(int r8, java.lang.StringBuffer r9, byte[] r10) throws java.lang.InterruptedException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 257
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.j.a(int, java.lang.StringBuffer, byte[]):int");
    }

    @Override // defpackage.i
    public final void b() {
        if (this.f198e) {
            return;
        }
        Thread thread = new Thread(this);
        a = true;
        thread.start();
    }

    private void c() throws IOException {
        if (this.f196a != null) {
            try {
                this.f196a.close();
            } catch (Exception unused) {
            }
        }
        if (this.f197a != null) {
            try {
                this.f197a.close();
            } catch (Exception unused2) {
            }
        }
        if (this.f195a != null) {
            try {
                this.f195a.close();
            } catch (Exception unused3) {
            }
        }
        if (!this.f198e && this.d) {
            this.f181a.a(this.f180a, this.f182b / 5);
            if (this.f < this.f184d) {
                b();
                this.f198e = false;
                return;
            }
        }
        this.f198e = false;
        a = false;
    }

    @Override // defpackage.i, java.lang.Runnable
    public void run() throws IOException {
        try {
            this.f180a = mo49a();
            if (this.f >= 2) {
                this.f180a = -1;
            }
            this.f++;
            this.g = -3;
            try {
                this.f195a = (SocketConnection) Connector.open(b());
                this.f195a.setSocketOption((byte) 0, 0);
                this.f196a = this.f195a.openInputStream();
                this.f197a = this.f195a.openOutputStream();
                this.f = 0;
                this.g = 0;
                this.f188a = System.currentTimeMillis();
                SocketConnection socketConnection = this.f195a;
                try {
                    d();
                    this.f198e = true;
                } catch (IOException unused) {
                }
                a = false;
                byte[] bArr = new byte[4096];
                int i = 0;
                new int[1][0] = 0;
                boolean z = false;
                while (true) {
                    int i2 = this.f196a.read();
                    if (i2 < 0) {
                        return;
                    }
                    this.f188a = System.currentTimeMillis();
                    int i3 = i;
                    i++;
                    bArr[i3] = (byte) i2;
                    if (i > 4) {
                        if (i >= bArr.length - 1) {
                            byte[] bArr2 = new byte[bArr.length << 1];
                            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
                            bArr = bArr2;
                        }
                        if (bArr[i - 5] == a) {
                            if (bArr[i - 4] == f191c[1]) {
                                if (bArr[i - 3] == f191c[2] && bArr[i - 2] == f191c[3] && bArr[i - 1] == f191c[4]) {
                                    c = true;
                                    i = 0;
                                    z = true;
                                }
                            } else if (z && bArr[i - 4] == f193e[1] && bArr[i - 3] == f193e[2] && bArr[i - 2] == f193e[3] && bArr[i - 1] == f193e[4]) {
                                c = false;
                                int i4 = i - 5;
                                byte[] bArr3 = bArr;
                                bArr = new byte[4096];
                                if (this.b) {
                                    this.f189b = bArr3;
                                    this.e = i4;
                                } else {
                                    StringBuffer stringBuffer = new StringBuffer();
                                    int iA = a(bArr3, i4, stringBuffer, 1);
                                    if (iA > 0) {
                                        this.f181a.a(stringBuffer.toString(), this.f187a);
                                    } else {
                                        this.f181a.a(iA, "");
                                        c();
                                    }
                                }
                                a = false;
                                i = 0;
                                z = false;
                            }
                        }
                    }
                }
            } catch (SecurityException e) {
                this.g = -6;
                throw new Exception();
            }
        } catch (Error e2) {
        } catch (Exception e3) {
        } finally {
            c();
        }
    }

    private void a(byte[] bArr, byte[] bArr2) throws InterruptedException, IOException {
        d();
        b(bArr, bArr2);
    }

    private void b(byte[] bArr, byte[] bArr2) throws InterruptedException, IOException {
        int length = bArr.length;
        byte[] bArr3 = new byte[length + f194b + f194b];
        System.arraycopy(f191c, 0, bArr3, 0, f194b);
        System.arraycopy(bArr, 0, bArr3, f194b, length);
        if (bArr2 != null) {
            System.arraycopy(f192d, 0, bArr3, f194b + length, f194b);
        } else {
            System.arraycopy(f193e, 0, bArr3, f194b + length, f194b);
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        for (long jCurrentTimeMillis2 = 0; c && jCurrentTimeMillis2 < this.f183c; jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis) {
            try {
                Thread.sleep(5L);
            } catch (InterruptedException unused) {
            }
        }
        c = true;
        this.f197a.write(bArr3);
        if (bArr2 != null) {
            byte[][] bArrA = b.a(bArr2, (byte) 1);
            this.f197a.write(bArrA[0]);
            this.f197a.write(bArrA[1]);
            this.f197a.write(bArrA[2]);
            this.f197a.write(f193e);
        }
        this.f197a.flush();
        c = false;
    }

    private void d() throws InterruptedException, IOException {
        String string = new StringBuffer().append(mo49a()).append(this.f181a.f137a).append(f171a).append(this.f179b).toString();
        if (this.f198e && string.compareTo(this.f190c) == 0) {
            return;
        }
        b(b.a(string, (byte) -127), null);
        this.f190c = string;
    }

    @Override // defpackage.i
    /* renamed from: a */
    public final void mo49a() throws IOException {
        this.d = false;
        c();
    }

    @Override // defpackage.i
    public final void a(long j) throws IOException {
        if (!this.f198e || System.currentTimeMillis() - this.f188a <= j) {
            return;
        }
        c();
    }
}
