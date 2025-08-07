package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:a.class */
public final class a implements Runnable {

    /* renamed from: a, reason: collision with other field name */
    private int f3a;
    private int b;
    private int d;
    private int e;

    /* renamed from: b, reason: collision with other field name */
    private Image f4b;

    /* renamed from: b, reason: collision with other field name */
    private long f6b;
    private static Hashtable a = new Hashtable(Opcodes.FCMPG);

    /* renamed from: a, reason: collision with other field name */
    private static long f0a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static Image f1a = null;

    /* renamed from: a, reason: collision with other field name */
    private static Object f7a = null;
    private static int h = 0;

    /* renamed from: a, reason: collision with other field name */
    private String f2a = null;
    private int c = -1;
    private int f = 1;

    /* renamed from: a, reason: collision with other field name */
    private int[] f5a = {0};
    private int g = 100;

    public a(Image image, String str, String str2, int i) {
        a(image, str, str2, i);
    }

    private void a(Image image, String str, String str2, int i) {
        this.f = 1;
        if (i > 0) {
            this.g = i;
        }
        this.f5a = new int[1];
        this.f5a[0] = 0;
        this.f4b = image;
        this.f6b = System.currentTimeMillis();
        try {
            this.e = Integer.parseInt(str2);
        } catch (Exception unused) {
        }
        if (str.length() > 0) {
            String[] strArrA = b.a(str, ',');
            this.f5a = new int[strArrA.length];
            for (int i2 = 0; i2 < strArrA.length; i2++) {
                try {
                    this.f5a[i2] = Integer.parseInt(strArrA[i2]);
                } catch (Exception unused2) {
                    this.f5a[i2] = 0;
                }
                if (this.f <= this.f5a[i2]) {
                    this.f = this.f5a[i2] + 1;
                }
            }
        }
        this.f3a = this.f4b.getWidth();
        this.b = this.f4b.getHeight() / this.f;
    }

    public static a a(String str) {
        return a(str, 100);
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0190, code lost:
    
        r0 = b(r7);
        r8 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0196, code lost:
    
        if (r0 != null) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0199, code lost:
    
        r8 = new defpackage.a(a(javax.microedition.lcdui.Image.createImage(new java.lang.StringBuffer().append("/").append(r7).append(".png").toString()), 1, 100), "0", "0", 100);
        a(r7, r8);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static defpackage.a a(java.lang.String r7, int r8) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.a.a(java.lang.String, int):a");
    }

    public static void a(a aVar, boolean z, int i, Graphics graphics, int i2, int i3, int i4) {
        if (aVar != null) {
            if (!z) {
                aVar.c = 0;
                aVar.d = 0;
            } else if (aVar.e == 0) {
                aVar.c = i - ((i / aVar.f5a.length) * aVar.f5a.length);
            } else {
                aVar.c++;
                if (aVar.c >= aVar.f5a.length) {
                    aVar.c = 0;
                    aVar.d++;
                }
                if (aVar.e > 0 && aVar.d >= aVar.e) {
                    aVar.c = 0;
                }
            }
            aVar.f6b = System.currentTimeMillis();
            int clipY = graphics.getClipY();
            int clipHeight = graphics.getClipHeight();
            int clipX = graphics.getClipX();
            int clipWidth = graphics.getClipWidth();
            int i5 = aVar.f5a[aVar.c];
            graphics.clipRect(i2, i3, aVar.f3a, aVar.b);
            graphics.drawImage(aVar.f4b, i2, i3 - (i5 * aVar.b), 20);
            graphics.setClip(clipX, clipY, clipWidth, clipHeight);
        }
    }

    public final int a() {
        return this.f3a;
    }

    public final int b() {
        return this.b;
    }

    /* renamed from: a, reason: collision with other method in class */
    public final boolean m1a() {
        return this.f4b == f1a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m2a(String str) {
        a aVarB = b(str);
        if (aVarB != null) {
            a.remove(str);
            a aVarA = a("$sound$", 100);
            Image image = aVarA.f4b;
            aVarB.f4b = aVarA.f4b;
            aVarB.f = aVarA.f;
            aVarB.f5a = aVarA.f5a;
            aVarB.c = aVarA.c;
            aVarB.b = aVarA.b;
            aVarB.f3a = aVarA.f3a;
            aVarB.d = aVarA.d;
            aVarB.e = aVarA.e;
            aVarB.f6b = System.currentTimeMillis();
        }
    }

    public static void a(String str, String str2, String str3, byte[] bArr, long j) {
        a aVarB = b(str);
        try {
            if (aVarB != null) {
                aVarB.a(a(bArr, 0, bArr.length, m4a(str2), aVarB.g), str2, str3, 0);
                if (j == 0) {
                    a.remove(str);
                }
            } else if (j == 1) {
                a(str, new a(a(bArr, 0, bArr.length, m4a(str2), aVarB.g), str2, str3, 0));
            }
        } catch (Exception unused) {
        }
    }

    private static void a(String str, a aVar) {
        try {
            aVar.f2a = str;
            a.put(str, aVar);
        } catch (Exception unused) {
        }
    }

    private static a b(String str) {
        try {
            return (a) a.get(str);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.lang.Runnable
    public final void run() throws IOException {
        HttpConnection httpConnection = null;
        InputStream inputStreamOpenInputStream = null;
        try {
            HttpConnection httpConnection2 = (HttpConnection) Connector.open(this.f2a);
            httpConnection = httpConnection2;
            inputStreamOpenInputStream = httpConnection2.openInputStream();
            int i = 0;
            int i2 = 0;
            byte[] bArr = new byte[512];
            int[] iArr = new int[512];
            byte[] bArr2 = new byte[1024];
            while (true) {
                int i3 = inputStreamOpenInputStream.read(bArr2);
                if (i3 < 0) {
                    break;
                }
                bArr[i2] = bArr2;
                iArr[i2] = i3;
                i += i3;
                i2++;
                bArr2 = new byte[1024];
            }
            byte[] bArr3 = new byte[i];
            int i4 = 0;
            for (int i5 = 0; i5 < i2 && i4 < i; i5++) {
                System.arraycopy(bArr[i5], 0, bArr3, i4, iArr[i5]);
                i4 += iArr[i5];
                bArr[i5] = 0;
            }
            a(Image.createImage(bArr3, 0, bArr3.length), "0", "0", 0);
            aj.e();
            try {
                inputStreamOpenInputStream.close();
            } catch (Exception unused) {
            }
            try {
                httpConnection.close();
            } catch (Exception unused2) {
            }
        } catch (Exception unused3) {
            try {
                inputStreamOpenInputStream.close();
            } catch (Exception unused4) {
            }
            try {
                httpConnection.close();
            } catch (Exception unused5) {
            }
        } catch (Throwable th) {
            try {
                inputStreamOpenInputStream.close();
            } catch (Exception unused6) {
            }
            try {
                httpConnection.close();
            } catch (Exception unused7) {
            }
            throw th;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m3a() {
        try {
            a.clear();
            System.gc();
        } catch (Exception unused) {
        }
    }

    public static void a(int i) {
        h = i + 3;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static int m4a(String str) throws NumberFormatException {
        int i = 1;
        if (str.length() > 0) {
            int i2 = 0;
            for (String str2 : b.a(str, ',')) {
                try {
                    i2 = Integer.parseInt(str2);
                } catch (Exception unused) {
                }
                if (i <= i2) {
                    i = i2 + 1;
                }
            }
        }
        return i;
    }

    public static Image a(Image image, int i, int i2) throws ClassNotFoundException {
        int i3 = h;
        int height = image.getHeight() / i;
        int width = image.getWidth();
        int i4 = (i3 * i2) / height;
        int i5 = i4;
        if (i4 < 40) {
            i5 = 100;
        }
        if (i5 < 100 && width > height * 3) {
            return image;
        }
        if (i5 > 84 && i5 < 116) {
            return image;
        }
        if (height >= width + (width / 4)) {
            i5 = (i5 * 100) / Opcodes.ISHL;
        }
        int i6 = ((height * i5) / 100) * i;
        int i7 = (width * i5) / 100;
        if (f7a == null) {
            try {
                Class.forName("javax.microedition.lcdui.game.GameCanvas");
                f7a = Class.forName("d").newInstance();
            } catch (Exception unused) {
                f7a = new c();
            }
        }
        return ((c) f7a).a(image, i7, i6);
    }

    private static Image a(byte[] bArr, int i, int i2, int i3, int i4) {
        return a(Image.createImage(bArr, 0, i2), i3, i4);
    }

    public static Image a(Image image, int i) throws ClassNotFoundException {
        if (f7a == null) {
            try {
                Class.forName("javax.microedition.lcdui.game.GameCanvas");
                f7a = Class.forName("d").newInstance();
            } catch (Exception unused) {
                f7a = new c();
            }
        }
        return ((c) f7a).a(image, i);
    }
}
