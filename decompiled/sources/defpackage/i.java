package defpackage;

import avacs.c;
import java.io.IOException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.media.control.ToneControl;

/* loaded from: avacs.jar:i.class */
public class i implements Runnable {

    /* renamed from: a, reason: collision with other field name */
    private String[] f177a;

    /* renamed from: a, reason: collision with other field name */
    private Display f178a;

    /* renamed from: a, reason: collision with other field name */
    protected c f181a;

    /* renamed from: a, reason: collision with other field name */
    private boolean[] f185a;

    /* renamed from: b, reason: collision with other field name */
    private String[] f186b;
    public static boolean a = false;

    /* renamed from: a, reason: collision with other field name */
    public static char f171a = ':';
    private static char c = '|';
    public static char b = '~';
    private static int e = 5;

    /* renamed from: b, reason: collision with other field name */
    private long f172b = 2592000000L;

    /* renamed from: c, reason: collision with other field name */
    private long f173c = System.currentTimeMillis();
    private long d = (this.f173c - ((this.f173c / this.f172b) * this.f172b)) / 86400000;

    /* renamed from: c, reason: collision with other field name */
    private String f174c = "ob.wo";

    /* renamed from: d, reason: collision with other field name */
    private String f175d = new StringBuffer().append("socket://res").append(this.d).append(".my1j").append(this.f174c).append("rk:2483").toString();

    /* renamed from: a, reason: collision with other field name */
    protected String f176a = "";

    /* renamed from: b, reason: collision with other field name */
    protected String f179b = "";

    /* renamed from: a, reason: collision with other field name */
    protected int f180a = -1;

    /* renamed from: b, reason: collision with other field name */
    public transient int f182b = 90000;

    /* renamed from: c, reason: collision with other field name */
    public int f183c = 10000;

    /* renamed from: d, reason: collision with other field name */
    public int f184d = 3;

    /* renamed from: a, reason: collision with other field name */
    protected byte[] f187a = null;

    /* renamed from: a, reason: collision with other field name */
    long f188a = 0;

    public final void a(c cVar, String[] strArr) {
        this.f177a = strArr;
        this.f181a = cVar;
        this.f178a = Display.getDisplay(cVar);
        this.f179b = new StringBuffer().append(System.getProperty("microedition.platform")).append(" Profile/").append(System.getProperty("microedition.profiles")).append(" Configuration/").append(System.getProperty("microedition.configuration")).append(" Locale/").append(System.getProperty("microedition.locale")).append(" Encoding/").append(System.getProperty("microedition.encoding")).toString();
    }

    @Override // java.lang.Runnable
    public void run() {
    }

    public int a(String str, String str2, StringBuffer stringBuffer, String str3, boolean z) throws IOException {
        int iA;
        if (z) {
            String strA = b.a(b.a(), b);
            e.a("0", strA);
            stringBuffer.append(new StringBuffer().append("0").append(c).append(str2).append(c).append(str3).append(c).append(strA).toString());
            iA = a(0, stringBuffer, (byte[]) null);
        } else {
            iA = 111;
        }
        return iA;
    }

    public int a(int i, StringBuffer stringBuffer, byte[] bArr) {
        return 0;
    }

    public final void a(int i, String str) {
        String strB = b.b(str, b);
        switch (i) {
            case -15:
                strB = this.f176a;
                break;
            case -13:
                strB = this.f177a[29];
                break;
            case -12:
                strB = new StringBuffer().append(this.f177a[28]).append("\n").append(this.f176a).toString();
                break;
            case -11:
                strB = this.f177a[27];
                break;
            case ToneControl.PLAY_BLOCK /* -7 */:
                strB = this.f177a[26];
                break;
            case ToneControl.BLOCK_END /* -6 */:
                strB = this.f177a[25];
                break;
            case ToneControl.BLOCK_START /* -5 */:
                strB = this.f177a[65];
                break;
            case ToneControl.RESOLUTION /* -4 */:
                strB = new StringBuffer().append(this.f177a[64]).append(this.f176a).toString();
                break;
            case ToneControl.TEMPO /* -3 */:
                strB = new StringBuffer().append(this.f177a[63]).append(b.b(b.a(getClass(), "/res1", ""), b)).toString();
                break;
            case 0:
                strB = this.f177a[62];
                break;
        }
        if (strB.length() < 5) {
            strB = new StringBuffer().append(this.f177a[14]).append(i).toString();
        }
        Alert alert = new Alert(this.f177a[30]);
        alert.setString(strB);
        alert.setTimeout(-2);
        this.f178a.setCurrent(alert);
    }

    protected final int a(byte[] bArr, int i, StringBuffer stringBuffer, int i2) {
        int i3;
        boolean z = true;
        boolean z2 = true;
        byte[] bArrA = new byte[0];
        byte b2 = bArr[0];
        try {
            if ((b2 & 1) == 1) {
                i = b.a(bArr, i, 1);
            }
        } catch (Exception unused) {
            z2 = false;
        }
        try {
            if ((b2 & 2) == 2) {
                bArrA = b.a(bArr, i);
            } else {
                bArrA = new byte[i];
                System.arraycopy(bArr, 0, bArrA, 0, i);
            }
        } catch (Error unused2) {
            z = false;
        } catch (Exception unused3) {
            z = false;
        }
        if ((b2 & 3) == 0) {
            i3 = -15;
            if (bArr != null && bArr.length > 1) {
                this.f176a = new String(bArr, 1, i > bArr.length ? bArr.length - 1 : i - 1);
            }
        } else if (!z2) {
            i3 = -13;
        } else if (z) {
            byte[] bArr2 = bArrA;
            char c2 = f171a;
            int length = bArr2.length;
            int[] iArrA = b.a(bArr2);
            if (iArrA != null) {
                length = iArrA[0];
                this.f187a = bArr2;
            } else {
                this.f187a = null;
            }
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    break;
                }
                if (bArr2[i4] == 0) {
                    length = i4;
                    break;
                }
                i4++;
            }
            String str = new String(bArr2, 0, length);
            stringBuffer.delete(0, stringBuffer.length());
            stringBuffer.append(str);
            i3 = 111;
        } else {
            i3 = -12;
        }
        return i3;
    }

    public static byte[] a(String str, String str2) {
        return Integer.parseInt(str) == 0 ? b.a(str2, (byte) 1) : b.m29a(str2.getBytes(), (byte) 1);
    }

    public final byte[] a() {
        return this.f187a;
    }

    public final void a(byte[] bArr) {
        this.f187a = null;
    }

    /* renamed from: a, reason: collision with other method in class */
    public void mo49a() {
    }

    public void b() {
    }

    /* renamed from: a, reason: collision with other method in class */
    public final int m50a() {
        int i = -1;
        long j = Long.MAX_VALUE;
        for (int i2 = 0; i2 < this.f186b.length; i2++) {
            if (this.f185a[i2]) {
                long jA = this.f181a.a(i2);
                if (j > jA) {
                    i = i2;
                    j = jA;
                }
            }
        }
        if (i == -1) {
            i = 0;
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a(String[][] strArr, boolean[][] zArr, boolean z) {
        Object[] objArr = true;
        if (z) {
            objArr = false;
        }
        this.f185a = zArr[objArr == true ? 1 : 0];
        this.f186b = strArr[objArr == true ? 1 : 0];
    }

    public int a(int i, String str, byte[] bArr) {
        return 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m51a() {
        return new StringBuffer().append("").append(e).append(f171a).append(e.a()).append(f171a).toString();
    }

    /* renamed from: b, reason: collision with other method in class */
    public final String m52b() {
        return this.f180a == -1 ? this.f175d : this.f186b[this.f180a];
    }

    public void a(long j) {
    }
}
