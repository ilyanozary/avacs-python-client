package avacs;

import defpackage.aj;
import defpackage.am;
import defpackage.b;
import defpackage.e;
import defpackage.g;
import defpackage.h;
import defpackage.i;
import defpackage.u;
import java.io.IOException;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:avacs/c.class */
public class c extends MIDlet implements Runnable {
    public static boolean a;

    /* renamed from: a, reason: collision with other field name */
    private long[] f130a;

    /* renamed from: a, reason: collision with other field name */
    private Display f132a;

    /* renamed from: a, reason: collision with other field name */
    private i f133a;

    /* renamed from: a, reason: collision with other field name */
    public StringBuffer f134a;

    /* renamed from: b, reason: collision with other field name */
    public StringBuffer f135b;

    /* renamed from: a, reason: collision with other field name */
    private String[] f136a;

    /* renamed from: a, reason: collision with other field name */
    private static u f138a;

    /* renamed from: a, reason: collision with other field name */
    private aj f139a;

    /* renamed from: a, reason: collision with other field name */
    public int f140a;

    /* renamed from: b, reason: collision with other field name */
    public int f141b;

    /* renamed from: b, reason: collision with other field name */
    private String f142b;

    /* renamed from: c, reason: collision with other field name */
    private int f143c;

    /* renamed from: d, reason: collision with other field name */
    private int f144d;

    /* renamed from: a, reason: collision with other field name */
    private byte[] f145a;

    /* renamed from: a, reason: collision with other field name */
    private Object f146a;

    /* renamed from: c, reason: collision with other field name */
    private String f147c;
    public static boolean b = true;
    public static boolean c = true;
    public static boolean f = false;
    public static boolean g = true;
    public boolean d = false;
    public boolean e = false;
    private boolean h = false;
    private boolean i = true;
    private boolean j = false;

    /* renamed from: b, reason: collision with other field name */
    private String[][] f127b = new String[2];

    /* renamed from: a, reason: collision with other field name */
    public String[][] f128a = new String[2];

    /* renamed from: a, reason: collision with other field name */
    public boolean[][] f129a = new boolean[2];

    /* renamed from: a, reason: collision with other field name */
    private long f131a = 8000;

    /* renamed from: a, reason: collision with other field name */
    public String f137a = "2.3.2";

    private void c() throws NumberFormatException {
        a(e.d(), false);
        String[] strArrA = b.a(e.e(), '|');
        this.f130a = new long[strArrA.length];
        for (int i = 0; i < strArrA.length; i++) {
            this.f130a[i] = Long.parseLong(strArrA[i]);
        }
        String[] strArrA2 = b.a(e.c(), '|');
        this.d = strArrA2[0].compareTo("true") == 0;
        a = strArrA2[1].compareTo("true") == 0;
        this.e = strArrA2[4].compareTo("true") == 0;
        f = strArrA2[5].compareTo("true") == 0;
        if (!g) {
            a = false;
        }
        this.f140a = Integer.parseInt(strArrA2[2]);
        this.f141b = Integer.parseInt(strArrA2[3]);
        if (strArrA2.length > 6) {
            b = strArrA2[6].compareTo("true") == 0;
        }
    }

    @Override // javax.microedition.midlet.MIDlet
    public void startApp() throws NumberFormatException {
        if (this.j) {
            return;
        }
        this.f132a = Display.getDisplay(this);
        e.m41b();
        this.f134a = new StringBuffer();
        this.f135b = new StringBuffer();
        this.f130a = new long[0];
        this.f136a = b.a(getClass(), this.f134a, this.f135b, Opcodes.FCMPG);
        c();
        this.f139a = new aj(this.f132a, this.f136a, this);
        this.j = true;
    }

    @Override // javax.microedition.midlet.MIDlet
    public void pauseApp() {
    }

    @Override // javax.microedition.midlet.MIDlet
    public void destroyApp(boolean z) throws IOException {
        this.f139a.f64b = false;
        e.b(b());
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.f130a.length; i++) {
            stringBuffer.append('|').append(this.f130a[i]);
        }
        stringBuffer.deleteCharAt(0);
        e.c(stringBuffer.toString());
        e.a(new StringBuffer().append(this.d ? "true" : "false").append('|').append(a ? "true" : "false").append('|').append(this.f140a).append('|').append(this.f141b).append('|').append(this.e ? "true" : "false").append('|').append(f ? "true" : "false").append('|').append(b ? "true" : "false").toString());
        e.m40a();
        g.a();
        this.j = false;
        notifyDestroyed();
    }

    public final void a(int i, long j) {
        if (i >= 0) {
            int length = this.f130a.length;
            if (i >= length) {
                long[] jArr = new long[i + 1];
                System.arraycopy(this.f130a, 0, jArr, 0, length);
                this.f130a = jArr;
            }
            this.f130a[i] = ((4 * this.f130a[i]) + j) / 5;
            if (this.f130a[i] > 9000) {
                for (int i2 = 0; i2 < length; i2++) {
                    this.f130a[i2] = 0;
                }
                this.f130a[i] = 10;
            }
            this.f131a = this.f130a[i];
        }
    }

    public final void a() {
        this.f130a = new long[3];
    }

    public final long a(int i) {
        if (i >= this.f130a.length) {
            return 0L;
        }
        return this.f130a[i];
    }

    /* renamed from: a, reason: collision with other method in class */
    private long m20a() {
        return this.f131a + 100;
    }

    public final void a(String str, int i) throws InterruptedException {
        if (i == 1 && this.f133a != null) {
            this.f133a.mo49a();
            this.f133a = null;
        }
        if (this.f133a == null) {
            if (!this.e) {
                this.f133a = b.m33a();
            }
            if (this.f133a == null) {
                this.e = true;
                this.f133a = new h();
            }
            this.f133a.a(this, this.f136a);
            this.f133a.a(this.f127b, this.f129a, this.e);
        }
        a(str, (byte[]) null, i == 6 ? 400 : 10, i);
    }

    public final synchronized void a(String str, byte[] bArr, int i, int i2) throws InterruptedException {
        this.f142b = str;
        this.f143c = i;
        this.f144d = i2;
        this.f145a = bArr;
        new Thread(this).start();
        try {
            Thread.sleep(5L);
        } catch (InterruptedException unused) {
        }
    }

    public final void a(int i, String str, byte[] bArr) throws InterruptedException {
        this.f139a.f66c = true;
        this.f146a = null;
        this.f147c = null;
        if (this.f133a != null) {
            a(new StringBuffer().append("").append(i).append('|').append(str).toString(), bArr, this.e ? 100 : Opcodes.ISHL, 0);
        }
    }

    @Override // java.lang.Runnable
    public void run() throws InterruptedException {
        try {
            String str = this.f142b;
            int i = this.f143c;
            int i2 = this.f144d;
            boolean z = false;
            if (i == 110) {
                this.f139a.f68e = true;
                z = true;
            } else {
                this.f139a.f66c = true;
            }
            int iA = 0;
            StringBuffer stringBuffer = new StringBuffer();
            boolean z2 = true;
            boolean z3 = true;
            while (z3) {
                if (i != 500) {
                    stringBuffer.delete(0, stringBuffer.length());
                }
                switch (i) {
                    case 10:
                        if (e.a().length() > 2 && e.b().length() > 1) {
                            z2 = false;
                        }
                        i = 15;
                        break;
                    case Opcodes.DCONST_1 /* 15 */:
                        if (z2) {
                            this.f139a.a(m20a(), this.f136a[32]);
                        }
                        iA = this.f133a.a("", this.f137a, stringBuffer, this.f134a.toString(), z2);
                        i = 17;
                        if (z2) {
                            this.f139a.d();
                        }
                        i iVar = this.f133a;
                        if (iA == 111) {
                            this.f139a.a(true, (am) null, stringBuffer.toString());
                            break;
                        } else {
                            if (z2) {
                                if (this.i) {
                                    this.h = this.e;
                                    this.i = false;
                                    this.e = !this.e;
                                    z3 = false;
                                    a(str, i2);
                                } else {
                                    this.i = true;
                                    this.e = this.h;
                                }
                            }
                            i = 500;
                            break;
                        }
                    case Opcodes.SIPUSH /* 17 */:
                        if (i2 == 1) {
                            i = 40;
                        }
                        if (i2 == 7) {
                            this.f139a.a(-41, 511, "", null, null);
                        }
                        if (i2 == 5) {
                            this.f139a.a(-41, 533, "", null, null);
                        }
                        if (i2 >= 5) {
                            z3 = false;
                            break;
                        } else {
                            this.f139a.a(m20a(), this.f136a[32]);
                            break;
                        }
                    case 40:
                        stringBuffer.append(new StringBuffer().append("1|").append(str).toString());
                        i iVar2 = this.f133a;
                        i iVar3 = this.f133a;
                        iA = iVar2.a(11, stringBuffer, (byte[]) null);
                        this.f139a.d();
                        i iVar4 = this.f133a;
                        if (iA != 111) {
                            i = 500;
                            break;
                        } else {
                            this.f139a.c();
                            this.f139a.a(true, (am) null, stringBuffer.toString());
                            z3 = false;
                            break;
                        }
                    case 100:
                        i = 102;
                        this.f139a.a(this.f145a == null ? m20a() : this.f133a.f182b, this.f136a[35]);
                        break;
                    case Opcodes.FSUB /* 102 */:
                        if (!this.f139a.f68e && !this.f139a.f51a) {
                            i = 105;
                            break;
                        } else {
                            i = 102;
                            break;
                        }
                    case Opcodes.LMUL /* 105 */:
                        stringBuffer.append(new StringBuffer().append("").append(str).toString());
                        i iVar5 = this.f133a;
                        i iVar6 = this.f133a;
                        iA = iVar5.a(11, stringBuffer, this.f145a);
                        this.f139a.d();
                        i iVar7 = this.f133a;
                        if (iA != 111) {
                            i = 500;
                            break;
                        } else {
                            this.f139a.f52a = this.f133a.a();
                            this.f133a.a((byte[]) null);
                            this.f139a.a(true, (am) null, stringBuffer.toString());
                            if (this.f147c != null) {
                                this.f139a.f52a = this.f146a;
                                this.f146a = null;
                                this.f139a.a(true, (am) null, this.f147c);
                                this.f147c = null;
                            }
                            z3 = false;
                            break;
                        }
                    case Opcodes.FDIV /* 110 */:
                        stringBuffer.append(new StringBuffer().append("").append(str).toString());
                        i iVar8 = this.f133a;
                        i iVar9 = this.f133a;
                        int iA2 = iVar8.a(11, stringBuffer, (byte[]) null);
                        iA = iA2;
                        i iVar10 = this.f133a;
                        if (iA2 != 111) {
                            i = 500;
                            break;
                        } else {
                            if (this.f139a.f66c) {
                                this.f146a = this.f133a.a();
                                this.f147c = stringBuffer.toString();
                            } else {
                                this.f146a = null;
                                this.f147c = null;
                                this.f139a.f52a = this.f133a.a();
                                this.f139a.a(true, (am) null, stringBuffer.toString());
                            }
                            this.f133a.a((byte[]) null);
                            z3 = false;
                            break;
                        }
                    case Opcodes.ISHL /* 120 */:
                        i iVar11 = this.f133a;
                        i iVar12 = this.f133a;
                        int iA3 = iVar11.a(11, str, this.f145a);
                        iA = iA3;
                        i iVar13 = this.f133a;
                        if (iA3 != 111) {
                            i = 500;
                            break;
                        } else {
                            if (this.f147c != null) {
                                this.f139a.f52a = this.f146a;
                                this.f146a = null;
                                this.f139a.a(true, (am) null, this.f147c);
                                this.f147c = null;
                            }
                            z3 = false;
                            break;
                        }
                    case HttpConnection.HTTP_INTERNAL_ERROR /* 500 */:
                        this.f139a.a(-1, "0");
                        this.f139a.f67d = true;
                        z3 = false;
                        a(iA, stringBuffer.toString());
                        break;
                }
                try {
                    Thread.sleep(25L);
                } catch (InterruptedException unused) {
                }
            }
            if (z) {
                this.f139a.f68e = false;
            } else {
                this.f139a.f66c = false;
            }
        } catch (Exception e) {
        }
    }

    public final void a(String str, byte[] bArr) throws NumberFormatException {
        this.f139a.f52a = bArr;
        this.f139a.a(true, (am) null, str);
    }

    public final void a(int i, String str) throws InterruptedException, NumberFormatException, ClassNotFoundException, IOException {
        this.f139a.a(-1, "0");
        this.f139a.f67d = true;
        this.f133a.a(i, str);
    }

    public static void a(String str, String str2, int i, byte[] bArr) {
        if (a) {
            if (f138a == null) {
                u uVarM31a = b.m31a();
                f138a = uVarM31a;
                g = uVarM31a.a();
            }
            if (g) {
                f138a.a(str, str2, 1, bArr);
            } else {
                a = false;
            }
        }
    }

    public final void a(String str) throws NumberFormatException, IOException {
        a(str, true);
        e.b(b());
    }

    /* renamed from: a, reason: collision with other method in class */
    public final i m21a() {
        return this.f133a;
    }

    private void a(String str, boolean z) throws NumberFormatException {
        String[] strArrA = b.a(str, '^');
        if (strArrA.length == 2) {
            for (int i = 0; i < 2; i++) {
                String[] strArrA2 = b.a(strArrA[i], ',');
                int length = strArrA2.length;
                int i2 = z ? 1 : 0;
                String[] strArr = new String[length + i2];
                String[] strArr2 = new String[length + i2];
                boolean[] zArr = new boolean[length + i2];
                if (z) {
                    strArr[0] = this.f127b[i][0];
                    strArr2[0] = this.f128a[i][0];
                    zArr[0] = this.f129a[i][0];
                }
                if (length > 0) {
                    for (int i3 = 0; i3 < length; i3++) {
                        String[] strArrA3 = b.a(strArrA2[i3], '=');
                        strArr[i3 + i2] = strArrA3[0];
                        strArr2[i3 + i2] = strArrA3[2];
                        int i4 = Integer.parseInt(strArrA3[1]);
                        if (i4 == 1) {
                            zArr[i3 + i2] = true;
                        } else if (i4 == 0) {
                            zArr[i3 + i2] = false;
                        } else {
                            zArr[i3 + i2] = true;
                            if (i3 + i2 < this.f127b[i].length && this.f127b[i][i3 + i2] != null && strArr[i3 + i2] != null && this.f127b[i][i3 + i2].compareTo(strArr[i3 + i2]) == 0) {
                                zArr[i3 + i2] = this.f129a[i][i3 + i2];
                            } else if (i3 + i2 < this.f130a.length) {
                                this.f130a[0] = 0;
                            }
                        }
                    }
                }
                this.f127b[i] = strArr;
                this.f128a[i] = strArr2;
                this.f129a[i] = zArr;
                if (this.f130a.length < strArr.length) {
                    this.f130a = new long[strArr.length];
                }
            }
            if (this.f133a != null) {
                this.f133a.a(this.f127b, this.f129a, this.e);
            }
            b();
        }
    }

    private String b() {
        StringBuffer stringBuffer = new StringBuffer(128);
        for (int i = 0; i < 2; i++) {
            if (i == 1) {
                stringBuffer.append('^');
            }
            int length = this.f127b[i].length;
            for (int i2 = 0; i2 < length; i2++) {
                if (i2 > 0) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(this.f127b[i][i2]);
                stringBuffer.append('=');
                stringBuffer.append(this.f129a[i][i2] ? "1" : "0");
                stringBuffer.append('=');
                stringBuffer.append(this.f128a[i][i2]);
            }
        }
        return stringBuffer.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public final String m22a() {
        return this.f128a[this.e ? (char) 0 : (char) 1][0];
    }

    public final void b(String str) {
        if (this.e) {
            if (str.length() < 11 || str.substring(0, 7).toUpperCase().compareTo("HTTP://") != 0) {
                return;
            }
        } else if (str.length() < 13 || str.substring(0, 9).toUpperCase().compareTo("SOCKET://") != 0) {
            return;
        }
        this.f127b[this.e ? (char) 0 : (char) 1][0] = str;
        this.f128a[this.e ? (char) 0 : (char) 1][0] = str;
        this.f129a[this.e ? (char) 0 : (char) 1][0] = true;
        this.f130a[0] = 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public final boolean m23a() {
        return this.e;
    }

    /* renamed from: b, reason: collision with other method in class */
    public final void m24b() throws IOException {
        e.b(b());
    }
}
