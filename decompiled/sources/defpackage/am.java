package defpackage;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:am.class */
public class am {
    o a;

    /* renamed from: a, reason: collision with other field name */
    Display f87a;

    /* renamed from: a, reason: collision with other field name */
    public int f89a;

    /* renamed from: a, reason: collision with other field name */
    public String f90a;
    public int b;
    public int c;
    public int d;

    /* renamed from: b, reason: collision with other field name */
    boolean f92b;
    private int E;

    /* renamed from: c, reason: collision with other field name */
    public boolean f94c;

    /* renamed from: a, reason: collision with other field name */
    public Object f97a;

    /* renamed from: f, reason: collision with other field name */
    public int f98f;
    int g;

    /* renamed from: b, reason: collision with other field name */
    String f99b;
    int h;

    /* renamed from: c, reason: collision with other field name */
    String f100c;
    int i;

    /* renamed from: d, reason: collision with other field name */
    String f101d;

    /* renamed from: j, reason: collision with other field name */
    int f103j;

    /* renamed from: g, reason: collision with other field name */
    boolean f109g;
    int o;
    int p;
    public int x;
    public int y;
    public int z;
    public int A;
    public int B;
    public int C;
    public int D;

    /* renamed from: d, reason: collision with other field name */
    private static int[][] f117d = {new int[]{13684944, 12047601, 16499544, 15066596, 9539986, 11369781, 4276545, 14877446, 15633786, 10223698, 16740863, 16765186, 16742672, 16363114, 4882944, 35377, 1486, 25498, 7837943, 7471247, 10320090}, new int[]{0}, new int[]{16777215, 16777215, 16777215, 16777214, 13421773, 12951657, 9474192, 16159378, 16370634, 13631594, 16759039, 16777077, 13523456, 16762760, 6529544, 568906, 6448895, 37335, 9942521, 10621123, 12032241}, new int[]{10526880, 10526880, 10526880, 8223612, 2105377, 4864545, 5592405, 7344145, 12330247, 7012409, 7016538, 9143296, 7681280, 14773510, 3229960, 22817, 2192, 17516, 2167692, 4982108, 4068236}, new int[]{0, 0, 0, 0, 1, 16777214, 15790320, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, new int[]{1, 1, 1, 1, 16777214, 16777215, 16777215, 16777214, 16777215, 16777214, 1, 1, 16777214, 1, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214}, new int[]{11579647, 9474303, 13864715, 15066596, 7171437, 7490052, 1315860, 14942223, 15618869, 8388678, 15073776, 15514880, 8993280, 16488515, 3429127, 550185, 1697, 22414, 7772663, 6226039, 9000428}, new int[]{16777215, 16777215, 16777215, 1, 16777215, 16777214, 16777215, 16777214, 16777214, 16777214, 1, 1, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214}, new int[]{0, 0, 0, 0, 1, 16777214, 16777215, 16777214, 1, 16777214, 1, 1, 1, 1, 1, 1, 16777214, 1, 1, 16777214, 1}, new int[]{14737632, 14677247, 16766339, 16777215, 11250346, 10515514, 3158064, 16401488, 15569276, 14559372, 16749311, 15918953, 16742672, 16361828, 8102474, 4893299, 5724625, 565501, 10927615, 10308782, 11899647}, new int[]{8421631, 4752895, 4752895, 13224393, 7697782, 8082447, 4210752, 16060956, 16206667, 6488121, 16738807, 15055872, 7286272, 15561739, 2705672, 20769, 1434, 16741, 2839754, 4785496, 7428794}, new int[]{16777215, 16777215, 16777215, 1, 16777214, 16777214, 16777215, 16777214, 1, 16777214, 1, 1, 16777214, 1, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214}, new int[]{1, 1, 1, 1, 1, 16777215, 15790320, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, new int[]{1, 1, 1, 1, 16777214, 16777215, 16777215, 16777214, 16777215, 16777214, 1, 1, 16777214, 1, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214}, new int[]{12632256, 10666722, 15977539, 13948115, 9474193, 9267749, 5592406, 13765889, 15427685, 11862123, 16734719, 15716624, 12866304, 16295516, 5278224, 32049, 3488492, 30394, 6590455, 8329113, 10519002}, new int[]{10526880, 7312311, 13872427, 11053223, 5329489, 6373380, 3487029, 11208714, 14044208, 7143477, 14943920, 13675036, 9978880, 16289071, 3301632, 1087050, 1053816, 17775, 2771962, 4983392, 7685580}, new int[]{12632256, 10666722, 15977539, 13948115, 9474193, 9267749, 5592406, 13765889, 15427685, 11862123, 16734719, 15716624, 12866304, 16295516, 6523408, 32049, 3488511, 30394, 6590455, 8329113, 10519002}, new int[]{0, 0, 0, 1, 0, 16777214, 16777215, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, new int[]{14737632, 14677247, 16048783, 16777215, 11250346, 10515514, 2105376, 16401488, 15569276, 14559372, 16749311, 15918953, 16742672, 16361828, 8102474, 4893299, 5724625, 565501, 10927615, 10308782, 11899647}, new int[]{16777215, 16777215, 16777215, 1, 16777214, 16777214, 16777215, 16777214, 1, 16777214, 1, 1, 16777214, 1, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214}, new int[]{8421631, 6316287, 6316287, 13224392, 7697780, 7689515, 4210752, 14357020, 16206667, 6488121, 15623664, 15055872, 7286272, 15561739, 2705672, 20769, 1434, 16741, 2839754, 4785496, 7424443}, new int[]{0, 0, 0, 1, 0, 16777214, 16777215, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, new int[]{16777184, 46542, 16092962, 15066596, 13158600, 13280094, 5592405, 16413819, 16566461, 16732589, 16753407, 16773310, 16752984, 16498052, 11393882, 6544276, 8422128, 5160191, 13687801, 13002715, 13219839}, new int[]{16777215, 16777215, 16777215, 1, 16777214, 16777214, 16777215, 16777214, 0, 16777214, 1, 1, 16777214, 1, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214}, new int[]{11579647, 5263535, 11409133, 12171704, 10197916, 11435562, 7895160, 15284806, 16289422, 10813539, 15766256, 15786020, 11749632, 16289071, 5931528, 561721, 3036, 28073, 7772663, 7932054, 10646256}, new int[]{0, 0, 0, 1, 0, 16777214, 16777215, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, new int[]{14737632, 14677247, 16048783, 16777215, 11250346, 10515514, 2105376, 16401488, 15569276, 14559372, 15766256, 15918953, 16742672, 16361828, 8102474, 4893299, 5724625, 565501, 10927615, 10308782, 11899647}, new int[]{16777215, 16777215, 16777215, 1, 16777214, 16777214, 16777215, 16777214, 0, 16777214, 1, 1, 16777214, 1, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214, 16777214}, new int[]{8421631, 6316287, 6316287, 13224392, 7697780, 7689515, 4210752, 14357020, 16206667, 6488121, 15623664, 15055872, 7286272, 15561739, 2705672, 20769, 1434, 16741, 2839754, 4785496, 7424443}, new int[]{9655296, 9655296, 9655296, 5592405, 1052688, 2105376, 12632256, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, new int[]{0, 29352, 0, 1, 0, 16777214, 16777215, 16777214, 1, 16777214, 1, 1, 16777214, 1, 1, 1, 16777214, 1, 1, 16777214, 1}, new int[]{9494255, 9494255, 15717763}, new int[]{16777215, 12773631, 12773631}, new int[]{3520764, 2536938, 2536938}, new int[]{15686316, 16756912, 16756912}, new int[]{0}, new int[]{0}};

    /* renamed from: a, reason: collision with other field name */
    static char f119a = '=';

    /* renamed from: b, reason: collision with other field name */
    static char f120b = '^';

    /* renamed from: c, reason: collision with other field name */
    static char f121c = '~';

    /* renamed from: d, reason: collision with other field name */
    static char f122d = '|';

    /* renamed from: a, reason: collision with other field name */
    boolean f88a = true;

    /* renamed from: a, reason: collision with other field name */
    int[] f91a = {0};

    /* renamed from: a, reason: collision with other field name */
    a f93a = null;
    int e = 0;

    /* renamed from: d, reason: collision with other field name */
    public boolean f95d = false;

    /* renamed from: e, reason: collision with other field name */
    boolean f96e = false;
    private boolean j = false;
    public boolean f = false;
    private int F = 0;

    /* renamed from: a, reason: collision with other field name */
    Font f102a = null;
    int k = 0;
    int l = 0;
    int m = 0;
    int n = 0;

    /* renamed from: a, reason: collision with other field name */
    long f104a = 0;

    /* renamed from: b, reason: collision with other field name */
    int[] f105b = new int[8];

    /* renamed from: e, reason: collision with other field name */
    String f106e = null;

    /* renamed from: f, reason: collision with other field name */
    String f107f = null;

    /* renamed from: g, reason: collision with other field name */
    private String f108g = null;

    /* renamed from: a, reason: collision with other field name */
    int[][] f110a = null;

    /* renamed from: b, reason: collision with other field name */
    int[][] f111b = null;

    /* renamed from: h, reason: collision with other field name */
    boolean f112h = true;

    /* renamed from: a, reason: collision with other field name */
    private am f113a = null;

    /* renamed from: k, reason: collision with other field name */
    private boolean f114k = false;

    /* renamed from: i, reason: collision with other field name */
    boolean f115i = false;

    /* renamed from: b, reason: collision with other field name */
    a f116b = null;
    int q = 4;

    /* renamed from: c, reason: collision with other field name */
    int[][] f118c = new int[f117d.length][4];
    int r = 0;
    int s = 0;
    int t = 0;
    private int G = 0;
    int u = 0;
    int v = 1;
    int w = 3;

    public final void a(Display display, o oVar, int i, String str, int i2, int i3, String str2, String str3, int i4, String str4, int i5, String str5, String str6, String str7, int i6, int i7, boolean z, int i8, int i9, int i10, int i11, int i12, int i13, Font font, boolean z2, int i14, Object obj, am amVar, boolean z3) {
        this.f113a = amVar;
        this.f87a = display;
        this.a = oVar;
        this.f88a = z2;
        this.f89a = i;
        this.f90a = b.b(str, f121c);
        this.b = i2;
        this.E = i3;
        this.f99b = b.b(str3, f121c);
        this.h = i4;
        this.f100c = b.b(str4, f121c);
        this.i = i5;
        this.f101d = str5;
        this.f102a = font;
        this.f94c = z3;
        this.f97a = obj;
        this.x = i8;
        this.y = i9;
        this.z = i10;
        this.A = i11;
        this.B = i11;
        this.C = i12;
        this.D = i13;
        if (this.k == 0) {
            if (i4 == 0 && i5 != 0) {
                this.h = i5;
                this.i = 0;
                this.f99b = this.f100c;
                this.f100c = "";
            }
            if (this.h != 0) {
                this.f105b[this.k] = 1;
                this.k++;
            }
            if (this.i != 0) {
                this.f105b[this.k] = 2;
                this.k++;
            }
            if ((i3 & 1) == 1) {
                this.f105b[this.k] = 10;
                this.k++;
            } else {
                this.f106e = null;
            }
            if ((i3 & 2) == 2) {
                this.f105b[this.k] = 11;
                this.k++;
            } else {
                this.f107f = null;
            }
            if (z3) {
                this.f105b[this.k] = -1;
                this.k++;
            }
        }
        this.c = i6;
        this.d = i7;
        this.f92b = z;
        this.f103j = font.getHeight();
        font.stringWidth(str);
        this.f93a = a.a(str2);
        this.e = this.f103j;
        if (this.f93a != null && this.e <= this.f93a.b()) {
            this.e = this.f93a.b();
        }
        if (i2 != 30) {
            this.n = i14;
        } else if (System.currentTimeMillis() - this.f104a > 2000) {
            this.n = 200;
        }
        String[] strArrA = b.a(str6, f120b);
        this.f98f = Integer.parseInt(strArrA[0]);
        int i15 = this.f98f - 1;
        for (int i16 = 0; i16 < f117d.length; i16++) {
            int[][] iArr = this.f118c;
            int i17 = i16;
            int[] iArr2 = f117d[i16];
            int i18 = i15;
            int[] iArr3 = new int[4];
            if (i18 < 0 || i18 >= iArr2.length) {
                i18 = 0;
            }
            iArr3[3] = iArr2[i18];
            iArr3[2] = x.b(iArr3[3], -12);
            iArr3[1] = x.b(iArr3[2], -12);
            iArr3[0] = x.b(iArr3[1], -12);
            iArr[i17] = iArr3;
        }
        if (strArrA.length > 1) {
            for (int i19 = 1; i19 < strArrA.length; i19++) {
                String[] strArrA2 = b.a(strArrA[i19], f119a);
                if (strArrA2.length >= 2) {
                    this.f118c[Integer.parseInt(strArrA2[0])] = x.a(strArrA2[1]);
                }
            }
        }
        this.f98f = 3;
        if (str7.length() > 0) {
            String[] strArrA3 = b.a(str7, f120b);
            this.f116b = a.a(strArrA3[0]);
            if (strArrA3.length > 1) {
                String[] strArrA4 = b.a(strArrA3[1], f119a);
                if (strArrA4.length > 0) {
                    this.q = Integer.parseInt(strArrA4[0]);
                }
            }
        } else {
            this.f116b = null;
            this.q = 4;
        }
        a();
    }

    public void a() {
    }

    public void a(Graphics graphics) {
    }

    public final void a(Graphics graphics, boolean z, boolean z2) {
        try {
            this.f109g = z;
            if (z2) {
                this.f112h = true;
            }
            graphics.setClip(this.C, this.D, this.z, this.A);
            graphics.setFont(this.f102a);
            if (this.f96e) {
                if (z) {
                    this.g = this.f98f;
                } else {
                    this.g++;
                }
            } else if (z) {
                this.g = 0;
            } else {
                this.g--;
            }
            if (this.g > this.f98f) {
                this.g = this.f98f;
            } else if (this.g < 0) {
                this.g = 0;
            } else if (!this.f96e && !z) {
                this.a.a(this);
            }
            if (z) {
                this.v = this.w;
            } else {
                this.v = 1;
            }
            a(graphics);
            if (this.f96e) {
                this.r++;
                this.s++;
                this.t++;
                this.G++;
                this.u++;
                if (this.G > 5) {
                    this.f95d = false;
                }
            }
        } catch (Exception unused) {
        }
    }

    public final void c(int i) {
        if (this.f96e) {
            this.v = i;
            this.r += i;
            this.s += i;
            this.t += i;
            this.G += i;
        }
    }

    public final void b(boolean z) {
        a(z);
    }

    protected void a(boolean z) {
    }

    public void a(int i) {
        if (this.f96e) {
            switch (i) {
                case Opcodes.LSUB /* 101 */:
                    this.l--;
                    if (this.l < 0) {
                        this.l = this.k - 1;
                    }
                    this.t = 0;
                    break;
                case Opcodes.FSUB /* 102 */:
                    this.l++;
                    if (this.l >= this.k) {
                        this.l = 0;
                    }
                    this.t = 0;
                    break;
                case Opcodes.DSUB /* 103 */:
                    this.n--;
                    if (this.n < 0) {
                        this.n = this.m - 1;
                    }
                    this.s = 0;
                    break;
                case Opcodes.IMUL /* 104 */:
                    this.n++;
                    if (this.n >= this.m) {
                        this.n = 0;
                    }
                    this.s = 0;
                    break;
                case Opcodes.LMUL /* 105 */:
                    this.f95d = true;
                    this.G = 0;
                    String str = null;
                    int i2 = 0;
                    switch (this.f105b[this.l]) {
                        case 1:
                            i2 = this.h;
                            break;
                        case 2:
                            i2 = this.i;
                            break;
                        case 10:
                            i2 = -60;
                            str = this.f106e;
                            break;
                        case 11:
                            i2 = -70;
                            str = this.f107f;
                            break;
                    }
                    o oVar = this.a;
                    int i3 = this.b;
                    oVar.a(str, this.f89a, i2, mo6a(), (Object) null, this);
                    break;
                case Opcodes.FDIV /* 110 */:
                    this.l = 0;
                    this.s = 0;
                    this.a.a(this.b, this.f89a, this.h, mo6a(), (Object) null, this);
                    break;
                case Opcodes.DDIV /* 111 */:
                    if (this.i != 0) {
                        this.l = 1;
                        this.s = 0;
                        this.a.a(this.b, this.f89a, this.i, mo6a(), (Object) null, this);
                        break;
                    }
                    break;
                case Opcodes.FREM /* 114 */:
                    if (this.f94c) {
                        this.f95d = true;
                        this.G = 0;
                        for (int i4 = 0; i4 < this.k; i4++) {
                            if (this.f105b[i4] == -1) {
                                this.l = i4;
                            }
                        }
                        this.s = 0;
                        this.a.a(this.b, this.f89a, 0, mo6a(), (Object) null, this);
                        break;
                    }
                    break;
                case Opcodes.DREM /* 115 */:
                    this.a.a(this.b, this.f89a, -1, mo6a(), (Object) null, this);
                    break;
                case Opcodes.INEG /* 116 */:
                    if (this.f106e != null) {
                        o oVar2 = this.a;
                        int i5 = this.b;
                        oVar2.a(this.f106e, this.f89a, -60, mo6a(), (Object) null, this);
                        break;
                    }
                    break;
                case Opcodes.LNEG /* 117 */:
                    if (this.f107f != null) {
                        o oVar3 = this.a;
                        int i6 = this.b;
                        oVar3.a(this.f107f, this.f89a, -70, mo6a(), (Object) null, this);
                        break;
                    }
                    break;
                case Opcodes.ISHL /* 120 */:
                    this.a.a(this.f89a, -65, "", null, this);
                    break;
            }
        }
    }

    public boolean a(int i, int i2) {
        if (i2 > this.D - 2 && i2 < this.D + this.e + 2 && i > this.C - 2 && i < this.C + this.z + 2) {
            this.f114k = true;
            this.o = i;
            this.p = i2;
        }
        if (this.f110a != null) {
            if (this.f110a[0] != null && i > this.f110a[0][0] - 1 && i < this.f110a[0][0] + this.f110a[0][2] + 1 && i2 > this.f110a[0][1] - 2 && i2 < this.f110a[0][1] + this.f110a[0][3] + 2) {
                for (int i3 = 0; i3 < this.k; i3++) {
                    if (this.f105b[i3] == -1) {
                        this.l = i3;
                    }
                }
                a(Opcodes.FREM);
                return true;
            }
            if (this.f110a[1] != null && i > this.f110a[1][0] - 1 && i < this.f110a[1][0] + this.f110a[1][2] + 1 && i2 > this.f110a[1][1] - 2 && i2 < this.f110a[1][1] + this.f110a[1][3] + 2) {
                this.f95d = true;
                this.G = 0;
                for (int i4 = 0; i4 < this.k; i4++) {
                    if (this.f105b[i4] == 10) {
                        this.l = i4;
                    }
                }
                a(Opcodes.INEG);
                return true;
            }
            if (this.f110a[2] != null && i > this.f110a[2][0] - 1 && i < this.f110a[2][0] + this.f110a[2][2] + 1 && i2 > this.f110a[2][1] - 2 && i2 < this.f110a[2][1] + this.f110a[2][3] + 2) {
                this.f95d = true;
                this.G = 0;
                for (int i5 = 0; i5 < this.k; i5++) {
                    if (this.f105b[i5] == 11) {
                        this.l = i5;
                    }
                }
                a(Opcodes.LNEG);
                return true;
            }
        }
        if (this.f111b != null) {
            int[] iArr = this.f111b[0];
            if (iArr != null && i > iArr[0] - 2 && i < iArr[0] + iArr[2] + 2 && i2 > iArr[1] - 2 && i2 < iArr[1] + iArr[3] + 2) {
                this.l = 0;
                a(Opcodes.FDIV);
                return true;
            }
            int[] iArr2 = this.f111b[1];
            if (iArr2 != null && i > iArr2[0] - 2 && i < iArr2[0] + iArr2[2] + 2 && i2 > iArr2[1] - 2 && i2 < iArr2[1] + iArr2[3] + 2) {
                this.l = 1;
                a(Opcodes.DDIV);
                return true;
            }
        }
        if (i >= this.C - 5 && i <= this.C + this.z + 5 && i2 >= this.D - 5 && i2 <= this.D + this.A + 5) {
            return false;
        }
        this.a.d(i, i2);
        return true;
    }

    /* renamed from: a, reason: collision with other method in class */
    public final void m13a(int i, int i2) {
        if (this.f114k && this.o != 0 && this.p != 0) {
            int i3 = i - this.o;
            int i4 = i2 - this.p;
            this.C += i3;
            this.D += i4;
            if (i3 != 0 || i4 != 0) {
                this.f112h = true;
            }
        }
        this.o = 0;
        this.p = 0;
        this.f114k = false;
        this.f115i = false;
    }

    public final void b(int i, int i2) {
        if (this.o == 0 || this.p == 0) {
            return;
        }
        int i3 = i - this.o;
        int i4 = i2 - this.p;
        if (this.f114k) {
            this.C += i3;
            this.D += i4;
        }
        if (this.f115i) {
            if (this.b == 7) {
                int[] iArr = this.f91a;
                iArr[0] = iArr[0] + i4;
                b(this.f91a[0]);
            } else {
                int[] iArr2 = this.f91a;
                iArr2[0] = iArr2[0] - i4;
            }
            this.f112h = true;
        }
        if (i3 == 0 && i4 == 0) {
            return;
        }
        this.o = i;
        this.p = i2;
        if (this.f114k) {
            aj.f();
        }
        this.f112h = true;
    }

    public void b(int i) {
    }

    /* renamed from: b, reason: collision with other method in class */
    public final boolean m14b(int i, int i2) {
        if (i <= this.C - 5 || i >= this.C + this.z + 5 || i2 <= this.D - 5 || i2 >= this.D + this.A + 5) {
            return false;
        }
        a(i, i2);
        return true;
    }

    /* renamed from: a */
    public String mo6a() {
        return "";
    }

    /* renamed from: a, reason: collision with other method in class */
    public final boolean m15a() {
        return this.f96e;
    }

    public final void c(boolean z) {
        this.f96e = z;
        if (z) {
            return;
        }
        this.o = 0;
        this.p = 0;
        this.u = 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public final boolean m16a(boolean z) {
        if (this.x - 4 >= this.z && this.y - 4 >= this.A && !z) {
            return false;
        }
        this.z = (this.x << 2) / 5;
        this.A = (this.y << 2) / 5;
        this.C = (this.x - ((this.x << 2) / 5)) / 2;
        this.D = (this.y - ((this.y << 2) / 5)) / 2;
        return true;
    }

    /* renamed from: a, reason: collision with other method in class */
    public final synchronized int m17a() {
        return this.E;
    }

    public final void a(String str) {
        this.f106e = str;
    }

    public final void b(String str) {
        this.f107f = str;
    }

    public final void c(String str) {
        this.f108g = str;
    }

    public final String b() {
        return this.f108g;
    }

    /* renamed from: a, reason: collision with other method in class */
    public final synchronized am m18a() {
        return this.f113a;
    }
}
