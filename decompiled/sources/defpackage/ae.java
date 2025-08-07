package defpackage;

import avacs.c;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:ae.class */
public final class ae extends am {
    private int F;
    private int G;
    private String g;
    private String h;
    private long b;

    /* renamed from: a, reason: collision with other field name */
    private String[][] f25a;
    private int[][] d;

    /* renamed from: c, reason: collision with other field name */
    private int[] f26c;

    /* renamed from: e, reason: collision with other field name */
    private int[][] f28e;
    private int[][] f;
    private int L;
    private int M;
    private int N;
    private int O;

    /* renamed from: a, reason: collision with other field name */
    private String[] f29a;

    /* renamed from: e, reason: collision with other field name */
    private int[] f30e;

    /* renamed from: f, reason: collision with other field name */
    private int[] f31f;
    private int P;
    private int Q;
    private int R;
    private String k;
    private a c = null;
    private int E = 0;
    private Vector a = new Vector(30, 10);
    private int H = 0;
    private int I = 0;
    private int J = 12;

    /* renamed from: a, reason: collision with other field name */
    private Image f23a = null;

    /* renamed from: b, reason: collision with other field name */
    private Image f24b = null;
    private int K = 0;
    private String i = "";
    private boolean j = false;
    private char e = '*';

    /* renamed from: d, reason: collision with other field name */
    private int[] f27d = {16, 8, 0};

    /* renamed from: j, reason: collision with other field name */
    private String f32j = "";

    /* renamed from: g, reason: collision with other field name */
    private int[] f33g = new int[1];

    @Override // defpackage.am
    public final void a() {
        int i = 0;
        switch (this.b) {
            case 2:
                this.f29a = b.a(this.f101d, f119a);
                if (this.f29a.length > 1) {
                    this.f32j = this.f29a[1];
                }
                Object[] objArrM133a = new Object[0];
                if (this.f29a.length > 0) {
                    this.f29a[0] = b.b(this.f29a[0], f121c);
                    objArrM133a = x.m133a(this.f29a[0]);
                }
                Object[][] objArrA = x.a(objArrM133a, 0, this.z - 10, this.f102a, c.f && (this.c == -2 || this.c == 1));
                for (Object[] objArr : objArrA) {
                    this.a.addElement(objArr);
                }
                this.f30e = new int[objArrA.length];
                this.f31f = new int[objArrA.length];
                this.m = this.a.size();
                if (this.f88a) {
                    for (int i2 = 0; i2 < this.a.size(); i2++) {
                        int[] iArrA = x.a(this.a.elementAt(i2), this.f102a, this.f103j);
                        this.f30e[i2] = iArrA[0];
                        this.f31f[i2] = iArrA[1];
                        this.P += iArrA[1];
                        if (iArrA[0] > i) {
                            i = iArrA[0];
                        }
                    }
                    int i3 = i + 10;
                    int i4 = this.P + this.e + 13;
                    if (this.h != 0) {
                        i4 += this.f103j + 4;
                    }
                    if (i4 > this.A) {
                        this.D -= (this.B - this.A) / 2;
                        this.A = this.B;
                    }
                    if (this.A > i4) {
                        this.D += (this.A - i4) / 2;
                        this.A = i4;
                    }
                    int iA = x.a(this.f102a, this.i, this.f99b, this.f100c);
                    if (iA > i3) {
                        i3 = iA;
                    }
                    if (this.z > i3) {
                        this.C += (this.z - i3) / 2;
                        this.z = i3;
                    }
                }
                int i5 = 0;
                int i6 = this.m;
                this.m = 0;
                for (int i7 = i6; i7 > 0; i7--) {
                    int i8 = i5 + this.f31f[i7 - 1];
                    i5 = i8;
                    if (i8 > (this.h == 0 ? (this.A - this.f103j) - 13 : ((this.A - this.f103j) - this.f103j) - 17)) {
                        this.m = i7 + 1;
                        break;
                    }
                }
                break;
            case 5:
                String[] strArrA = b.a(this.f101d, f120b);
                this.k = strArrA[0];
                this.n = x.a(strArrA, this.a, this.E, this.z - 10, f119a, f121c, this.f102a, this.f103j, this.f92b);
                this.E = this.n;
                this.m = this.a.size();
                this.P = 0;
                for (int i9 = 0; i9 < this.m; i9++) {
                    this.P += ((int[]) ((Object[]) this.a.elementAt(i9))[3])[1];
                }
                int i10 = this.n;
                this.R = ((this.A - this.D) - this.e) - 8;
                if (this.P > this.R) {
                    int[] iArr = this.f33g;
                    int[] iArr2 = this.f91a;
                    int i11 = this.P - this.R;
                    iArr2[0] = i11;
                    iArr[0] = i11;
                }
                this.Q = x.a(this.a, this.n);
                break;
            case 6:
                this.f29a = b.a(this.f101d, f119a);
                if (this.f29a.length > 1) {
                    this.f32j = this.f29a[1];
                }
                this.f26c = x.a(this.f29a[0]);
                this.m = 3;
                this.f105b[this.k] = -2;
                this.k++;
                this.f105b[this.k] = -3;
                this.k++;
                this.f105b[this.k] = -4;
                this.k++;
                this.f28e = new int[this.m][4];
                this.f28e[0][3] = this.f26c[3] >> this.f27d[0];
                this.f28e[1][3] = (this.f26c[3] - (this.f28e[0][3] << this.f27d[0])) >> this.f27d[1];
                this.f28e[2][3] = (this.f26c[3] - (this.f28e[0][3] << this.f27d[0])) - (this.f28e[1][3] << this.f27d[1]);
                this.f28e[0][2] = x.b(this.f28e[0][3], -12);
                this.f28e[0][1] = x.b(this.f28e[0][2], -12);
                this.f28e[0][0] = x.b(this.f28e[0][1], -12);
                this.f28e[1][2] = x.b(this.f28e[1][3], -12);
                this.f28e[1][1] = x.b(this.f28e[1][2], -12);
                this.f28e[1][0] = x.b(this.f28e[1][1], -12);
                this.f28e[2][2] = x.b(this.f28e[2][3], -12);
                this.f28e[2][1] = x.b(this.f28e[2][2], -12);
                this.f28e[2][0] = x.b(this.f28e[2][1], -12);
                int i12 = 60 + this.e + this.f103j + 17;
                if (this.A > i12) {
                    this.D += (this.A - i12) / 2;
                    this.A = i12;
                }
                int iA2 = x.a(this.f102a, this.i, this.f99b, this.f100c);
                int i13 = iA2 > 60 ? iA2 : 60;
                if (i12 > this.A) {
                    this.D -= (this.B - this.A) / 2;
                    this.A = this.B;
                }
                if (this.z > i13) {
                    this.C += (this.z - i13) / 2;
                    this.z = i13;
                }
                this.L = ((this.A - this.e) - this.f103j) - 17;
                this.N = 10;
                this.M = 4;
                this.O = this.L / 2;
                this.f = new int[this.O][2];
                for (int i14 = 0; i14 < this.O; i14++) {
                    this.f[i14][1] = ((this.O - i14) * 255) / this.O;
                    this.f[i14][0] = (this.f[i14][1] << 1) / 3;
                }
                break;
            case 11:
                String[] strArrA2 = b.a(this.f101d, f120b);
                this.m = strArrA2.length;
                this.f25a = new String[this.m][3];
                this.d = new int[this.m][2];
                for (int i15 = 0; i15 < this.m; i15++) {
                    String[] strArrA3 = b.a(strArrA2[i15], f119a);
                    this.f25a[i15][0] = strArrA3[0];
                    this.f25a[i15][1] = b.b(strArrA3[1], f121c);
                    this.f25a[i15][2] = b.b(strArrA3[2], f121c);
                    if (this.f25a[i15][1].charAt(0) == this.e) {
                        this.d[i15] = x.a(this.f25a[i15][2]);
                        this.f25a[i15][1] = this.f25a[i15][1].substring(1);
                    } else {
                        this.d[i15][1] = -1;
                    }
                }
                this.E = this.f103j + this.f103j + 7;
                if (this.f88a) {
                    int i16 = this.e + (this.E * this.m) + this.f103j + 8;
                    if (this.A > i16) {
                        this.D += (this.A - i16) / 2;
                        this.A = i16;
                    }
                    for (int i17 = 0; i17 < this.m; i17++) {
                        int iStringWidth = this.f102a.stringWidth(this.f25a[i17][1]);
                        if (iStringWidth > i) {
                            i = iStringWidth;
                        }
                        int iStringWidth2 = this.f102a.stringWidth(this.f25a[i17][2]);
                        if (iStringWidth2 > i) {
                            i = iStringWidth2;
                        }
                    }
                    int i18 = i + 10;
                    int iA3 = x.a(this.f102a, this.i, this.f99b, this.f100c);
                    if (iA3 > i18) {
                        i18 = iA3;
                    }
                    if (i16 > this.A) {
                        this.D -= (this.B - this.A) / 2;
                        this.A = this.B;
                    }
                    if (this.z > i18) {
                        this.C += (this.z - i18) / 2;
                        this.z = i18;
                        break;
                    }
                }
                break;
            case Opcodes.DCONST_1 /* 15 */:
                if (this.c == null) {
                    try {
                        int[] iArrA2 = b.a((byte[]) this.f97a);
                        if (((byte[]) this.f97a)[iArrA2[0]] == 104 && ((byte[]) this.f97a)[iArrA2[0] + 1] == 116) {
                            this.c = a.a(new String((byte[]) this.f97a, iArrA2[0], iArrA2[1]));
                        } else {
                            this.c = new a(Image.createImage((byte[]) this.f97a, iArrA2[0], iArrA2[1]), "0", "0", 100);
                        }
                        this.f97a = null;
                    } catch (Error e) {
                        this.K = -116;
                        this.i = new StringBuffer().append("").append(e).append(". ").append(e.getMessage()).toString();
                    } catch (Exception unused) {
                        this.K = -115;
                    }
                }
                if (this.c != null) {
                    int iB = this.c.b() + this.e + 9;
                    if (this.h != 0) {
                        iB += this.f103j + 4;
                    }
                    int iA4 = this.c.a() + 8;
                    if (iB > this.A) {
                        this.D -= (this.B - this.A) / 2;
                        this.A = this.B;
                    }
                    if (iA4 > this.z || iB > this.A) {
                        this.j = true;
                        break;
                    } else {
                        if (iA4 < this.z) {
                            this.C += (this.z - iA4) / 2;
                            this.z = iA4;
                        }
                        if (iB < this.A) {
                            this.D += (this.A - iB) / 2;
                            this.A = iB;
                            break;
                        }
                    }
                }
                break;
            case Opcodes.ALOAD /* 25 */:
                this.w = 2;
                this.k = 1;
                this.l = 0;
                String[] strArrA4 = b.a(this.f101d, f119a);
                if (this.z <= 128 && this.A <= 128) {
                    strArrA4[0] = new StringBuffer().append("s").append(strArrA4[0]).toString();
                    strArrA4[2] = new StringBuffer().append("s").append(strArrA4[2]).toString();
                }
                this.f23a = b.a(strArrA4[0]);
                this.f32j = strArrA4[2];
                if (this.f24b == null) {
                    this.f24b = b.a(this.f32j);
                }
                this.f118c[0] = new int[3];
                this.f118c[0][2] = Integer.parseInt(strArrA4[1], 16);
                this.f118c[0][1] = Integer.parseInt(strArrA4[3], 16);
                this.g = b.b(strArrA4[4], f121c);
                this.H = Integer.parseInt(strArrA4[5], 16);
                this.h = b.b(strArrA4[6], f121c);
                this.I = Integer.parseInt(strArrA4[7], 16);
                this.F = this.z / this.J;
                this.G = this.A / this.J;
                this.b = System.currentTimeMillis();
                break;
        }
    }

    @Override // defpackage.am
    protected final void a(boolean z) {
        if (this.b == 2 || this.b == 5 || this.b == 25) {
            a(z);
        } else if (this.b == 6 || this.b == 11) {
            this.C = (this.x - this.z) / 2;
            this.D = (this.y - this.A) / 2;
        } else if (this.b == 15 && (z || this.x - 4 < this.z || this.y - 4 < this.A)) {
            this.C = (this.x - this.z) / 2;
            this.D = (this.y - this.A) / 2;
            if (this.z > this.x || this.A > this.y) {
                this.j = true;
            } else {
                this.j = false;
            }
        }
        int i = this.e;
        if (this.f93a != null && this.e <= this.f93a.b()) {
            this.e = this.f93a.b();
            this.A += this.e - i;
            this.D -= (this.e - i) / 2;
        }
        this.P = 0;
        switch (this.b) {
            case 2:
                this.a = new Vector(30, 10);
                break;
            case 5:
                for (int i2 = 0; i2 < this.a.size(); i2++) {
                    Object[] objArr = (Object[]) this.a.elementAt(i2);
                    objArr[3] = x.a(objArr[2], this.f102a, this.f103j);
                    this.P += ((int[]) objArr[3])[1];
                }
                a();
                return;
            case Opcodes.DCONST_1 /* 15 */:
                break;
            default:
                return;
        }
        a();
    }

    /* JADX WARN: Type inference failed for: r1v78, types: [int[], int[][]] */
    @Override // defpackage.am
    public final void a(Graphics graphics) {
        int i;
        int i2;
        if (this.b != 25) {
            if (this.j) {
                graphics.setColor(0);
                graphics.fillRect(0, 0, this.x, this.y);
            } else {
                this.f110a = x.a(this.f93a, graphics, this.C, this.D, this.z, this.A, this.f102a, this.e, this.f90a, this.f118c[7][this.g], this.f118c[6][this.g], this.f118c[0][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.l, this.f118c[4][this.g], this.f118c[5][this.g], this.f118c[16][this.g], this.f118c[15][this.g], this.f94c, this.f105b, this.f95d, true, this.f118c[1][this.g], this.r, 20, this.d, this.f109g, this.f106e, this.f107f);
                this.f111b = x.a(this.h, this.i, this.l, this.f105b, this.f99b, this.f100c, this.f102a, graphics, this.C + 2, (this.D + this.A) - 2, this.z - 6, this.f118c[14][this.g], this.f118c[15][this.g], this.f118c[12][this.g], this.f118c[13][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.f118c[0][this.g], 0, this.t, this.d, this.f95d, this.f109g);
                graphics.setClip(this.C, this.D, this.z, this.A);
            }
        }
        switch (this.b) {
            case 2:
                x.a(graphics, this.C + 2, this.D + this.e + 3, this.z - 6, ((this.f111b[0][1] - this.D) - this.e) - 4, this.f118c[2][this.g], this.f118c[3][this.g], true, this.f118c[9][this.g], this.f116b, this.q, this.r);
                if (x.a(this.a, this.f30e, this.f31f, this.f102a, this.f103j, graphics, this.C + 3, this.D + this.e + 4, this.z - 8, ((this.f111b[0][1] - this.D) - this.e) - 6, this.f118c[8][this.g], this.f118c[30][this.g], this.r, this.n, this.m, this.P, this.f91a, this.v, this.c)) {
                    this.a.a((am) this, this.v, true);
                    break;
                }
                break;
            case 5:
                x.a(graphics, this.C + 2, this.D + this.e + 3, this.z - 6, ((this.f111b[0][1] - this.D) - this.e) - 4, this.f118c[2][this.g], this.f118c[3][this.g], true, this.f118c[9][this.g], this.f116b, this.q, this.r);
                this.R = ((this.f111b[0][1] - this.D) - this.e) - 7;
                if (x.a(graphics, this.C + 3, this.D + this.e + 5, this.z - 8, this.R, this.f116b, this.q, this.Q, this.f33g, this.v, this.f91a, this.f102a, this.f103j, this.g, this.m, this.n, this.P, this.a, this.f118c[30][this.g], this.f118c[10][this.g], this.t, this.c)) {
                    this.a.a((am) this, this.v, true);
                    break;
                }
                break;
            case 6:
                x.a(graphics, this.C + 2, this.D + this.e + 3, this.z - 6, ((this.f111b[0][1] - this.D) - this.e) - 4, this.f118c[2][this.g], this.f118c[3][this.g], true, this.f118c[9][this.g], this.f116b, this.q, this.r);
                graphics.setClip(this.C, this.D, this.z, this.A);
                int i3 = 0;
                int i4 = this.D + this.e + 6;
                int i5 = 0;
                while (i5 < this.m) {
                    int i6 = this.C + 2 + (this.N * (i5 + 1));
                    i3 = i6;
                    int i7 = i6 - (this.M / 2);
                    for (int i8 = 0; i8 < this.O; i8++) {
                        graphics.setColor(this.f[i8][this.g == 0 ? (char) 0 : (char) 1] << this.f27d[i5]);
                        graphics.drawRect(i7, i4 + (i8 << 1), this.M, 1);
                    }
                    x.a(graphics, i3 - this.M, ((i4 + this.L) - ((this.f28e[i5][3] * this.L) / 256)) - 2, (this.M << 1) + 1, 4, this.f118c[2][this.g], this.f118c[3][this.g], (-this.f105b[this.l]) - 2 == i5 ? this.f118c[15][this.g] : this.f118c[12][this.g]);
                    i5++;
                }
                graphics.setColor(this.f26c[this.g]);
                graphics.fillRect(i3 + this.N + 1, i4 + 1, this.N - 1, this.L - 1);
                graphics.setColor(this.f26c[0]);
                graphics.drawRect(i3 + this.N, i4, this.N, this.L);
                break;
            case 11:
                int i9 = (this.f111b[0][1] - this.D) - 1;
                int i10 = this.D + this.e;
                if ((this.n + 1) * this.E > i9 - 4) {
                    i10 -= this.E - 4;
                }
                graphics.setColor(this.f118c[0][this.g]);
                graphics.fillRect(this.C + 2, this.D + this.e + 4, this.z - 6, ((this.A - this.e) - this.f103j) - 10);
                for (int i11 = 0; i11 < this.m; i11++) {
                    graphics.setClip(this.C, this.D + this.e + 1, this.z, (i9 - this.f103j) - 1);
                    int i12 = this.f118c[8][this.g];
                    int i13 = this.f118c[0][this.g];
                    if (this.n == i11) {
                        i12 = this.f118c[11][this.g];
                        i13 = this.f118c[10][this.g];
                    }
                    x.a(graphics, this.C + 2, i10 + 3 + (this.E * i11), this.z - 6, this.f103j + this.f103j + 4, i13, 60, true, true, false, false, this.f109g);
                    x.a((a) null, this.f25a[i11][1], this.f102a, this.f103j, graphics, this.C + 3, i10 + 4 + (this.E * i11), this.z - 8, this.f103j + 1, i12, -1, this.s, 20, this.c, this.c, true, true, false, this.f109g);
                    graphics.setClip(this.C, this.D + this.f103j + 1, this.z, (i9 - this.f103j) - 1);
                    if (this.d[i11][1] >= 0) {
                        graphics.setColor(this.d[i11][this.g == 0 ? (char) 0 : (char) 1]);
                        graphics.fillRect(this.C + 4, i10 + this.f103j + 6 + (this.E * i11), this.z - 10, this.f103j - 1);
                    } else {
                        if (this.n == i11) {
                            x.a(graphics, this.C + 3, i10 + this.f103j + 5 + (this.E * i11), this.z - 8, this.f103j + 1, this.f118c[2][this.g], this.f118c[3][this.g], true, this.f118c[9][this.g], (a) null, 0, 0);
                        }
                        x.a((a) null, this.f25a[i11][2], this.f102a, this.f103j, graphics, this.C + 4, i10 + this.f103j + 5 + (this.E * i11), this.z - 9, this.f103j + 1, this.f118c[8][this.g], -1, this.s, 20, this.c, this.c, true, true, false, this.f109g);
                    }
                }
                break;
            case Opcodes.DCONST_1 /* 15 */:
                if (this.K == 0) {
                    if (this.j) {
                        a.a(this.c, false, 0, graphics, (this.x - this.c.a()) >> 1, (this.y - this.c.b()) >> 1, 20);
                        this.f110a = new int[3];
                        int i14 = 1;
                        if (this.f106e != null) {
                            this.f110a[1] = x.a(graphics, this.C + 1 + 1, this.D + 1, this.f103j + 1, this.f103j + 1, this.f118c[16][this.g], this.f118c[4][this.g], false, "L", this.f109g);
                            i14 = 1 + this.f103j + 2;
                        }
                        if (this.f107f != null) {
                            this.f110a[2] = x.a(graphics, this.C + 1 + i14, this.D + 1, this.f103j + 1, this.f103j + 1, this.f118c[16][this.g], this.f118c[4][this.g], false, "R", this.f109g);
                            break;
                        }
                    } else {
                        a.a(this.c, false, 0, graphics, this.C + (((this.z - 8) - this.c.a()) / 2) + 3, this.D + ((this.h == 0 ? 0 : ((((-this.f103j) - 13) + this.A) - this.e) - this.c.b()) / 2) + this.e + 4, 20);
                        x.a(graphics, this.C + 2, this.D + this.e + 3, this.z - 6, ((this.f111b[0][1] - this.D) - this.e) - 4, this.f118c[2][this.g], this.f118c[3][this.g], false, this.f118c[9][this.g], this.f116b, this.q, this.r);
                        break;
                    }
                } else {
                    this.a.a(this.f89a, this.K, this.i, null, this);
                    break;
                }
                break;
            case Opcodes.ALOAD /* 25 */:
                if (this.f23a != null) {
                    graphics.setColor(this.f118c[0][2]);
                    graphics.fillRect(this.C, this.D, this.z, this.A);
                    graphics.drawImage(this.f23a, this.C + (this.z / 2), this.D + (this.A / 2), 3);
                    if (System.currentTimeMillis() - this.b >= 3000) {
                        this.f23a = null;
                        this.s = 0;
                        this.f24b = b.a(this.f32j);
                        break;
                    }
                } else {
                    graphics.setColor(this.f118c[0][1]);
                    graphics.fillRect(this.C, this.D, this.z, this.A);
                    int i15 = this.s;
                    int i16 = this.F;
                    int i17 = this.s;
                    int i18 = this.G;
                    if (this.s >= this.J + 5) {
                        i = (((2 * this.J) - this.s) + 5) * this.F;
                        i2 = (((2 * this.J) - this.s) + 5) * this.G;
                    } else if (this.s <= this.J) {
                        i = this.s * this.F;
                        i2 = this.s * this.G;
                    } else {
                        i = this.J * this.F;
                        i2 = this.J * this.G;
                    }
                    if (this.s > (2 * this.J) + 5) {
                        i = 0;
                        i2 = 0;
                        a(0);
                    }
                    graphics.setClip(this.C + ((this.z - i) / 2), this.D + ((this.A - i2) / 2), i, i2);
                    if (this.f24b != null) {
                        graphics.drawImage(this.f24b, this.C + (this.z / 2), this.D + (this.A / 2), 3);
                    }
                    graphics.setClip(this.C, this.D, this.z, this.A);
                    graphics.setColor(this.H);
                    graphics.drawString(this.g, this.C + (this.z / 2), 1, 17);
                    graphics.setColor(this.I);
                    graphics.drawString(this.h, this.C + 2, (this.D + this.A) - 2, 36);
                    this.a.a((am) this, this.v, true);
                    break;
                }
                break;
        }
    }

    @Override // defpackage.am
    public final void a(int i) {
        if (this.f96e) {
            switch (this.b) {
                case 5:
                    switch (i) {
                        case Opcodes.DSUB /* 103 */:
                            break;
                        case Opcodes.IMUL /* 104 */:
                            int i2 = this.n;
                            while (true) {
                                this.n++;
                                if (this.n >= this.m) {
                                    this.n = i2;
                                } else if (((Object[]) this.a.elementAt(this.n))[4] != null) {
                                }
                            }
                            this.Q = x.a(this.a, this.n);
                            break;
                        default:
                            super.a(i);
                            break;
                    }
                    while (true) {
                        this.n--;
                        if (this.n <= 0) {
                            this.n = 0;
                        } else if (((Object[]) this.a.elementAt(this.n))[4] != null) {
                        }
                    }
                    this.Q = x.a(this.a, this.n);
                    break;
                case 6:
                    switch (i) {
                        case Opcodes.DSUB /* 103 */:
                            if (this.f105b[this.l] < -1) {
                                int i3 = (-this.f105b[this.l]) - 2;
                                int[] iArr = this.f28e[i3];
                                iArr[3] = iArr[3] + 8;
                                if (this.f28e[i3][3] > 255) {
                                    this.f28e[i3][3] = 255;
                                }
                                this.f28e[i3][2] = x.b(this.f26c[3], -12);
                                this.f28e[i3][1] = x.b(this.f26c[2], -12);
                                this.f28e[i3][0] = x.b(this.f26c[1], -12);
                                this.f26c[3] = (this.f28e[0][3] << this.f27d[0]) + (this.f28e[1][3] << this.f27d[1]) + this.f28e[2][3];
                                this.f26c[2] = x.b(this.f26c[3], -12);
                                this.f26c[1] = x.b(this.f26c[2], -12);
                                this.f26c[0] = x.b(this.f26c[1], -12);
                                break;
                            }
                            break;
                        case Opcodes.IMUL /* 104 */:
                            if (this.f105b[this.l] < -1) {
                                int i4 = (-this.f105b[this.l]) - 2;
                                int[] iArr2 = this.f28e[i4];
                                iArr2[3] = iArr2[3] - 8;
                                if (this.f28e[i4][3] < 0) {
                                    this.f28e[i4][3] = 0;
                                }
                                this.f28e[i4][2] = x.b(this.f26c[3], -12);
                                this.f28e[i4][1] = x.b(this.f26c[2], -12);
                                this.f28e[i4][0] = x.b(this.f26c[1], -12);
                                this.f26c[3] = (this.f28e[0][3] << this.f27d[0]) + (this.f28e[1][3] << this.f27d[1]) + this.f28e[2][3];
                                this.f26c[2] = x.b(this.f26c[3], -12);
                                this.f26c[1] = x.b(this.f26c[2], -12);
                                this.f26c[0] = x.b(this.f26c[1], -12);
                                break;
                            }
                            break;
                        case Opcodes.LMUL /* 105 */:
                            if (this.f105b[this.l] < -1) {
                                this.l = 0;
                            }
                            super.a(i);
                            break;
                        default:
                            super.a(i);
                            break;
                    }
                case Opcodes.ALOAD /* 25 */:
                    if (this.f23a == null) {
                        this.a.a(this.f89a, this.h, "", null, this);
                        break;
                    }
                    break;
                default:
                    super.a(i);
                    break;
            }
        }
    }

    @Override // defpackage.am
    public final boolean a(int i, int i2) {
        switch (this.b) {
            case 2:
                int i3 = this.D + this.e + 6;
                int i4 = this.f111b[0][1] - 2;
                if (i2 >= i3 && i2 <= i4 && i > this.C && i < this.C + this.z + 3) {
                    if (i > ((this.C + this.z) - this.f103j) - 10 && this.P > i4 - i3) {
                        this.n = ((i2 - i3) * this.m) / (i4 - i3);
                        return true;
                    }
                    if (i2 > i3 + ((i4 - i3) / 2)) {
                        a(Opcodes.IMUL);
                        return true;
                    }
                    a(Opcodes.DSUB);
                    return true;
                }
                break;
            case 5:
                int i5 = this.C + this.z + 3;
                if (i > this.C + 4 && i < i5) {
                    int i6 = this.D + this.e + 5;
                    int i7 = ((this.D + this.A) - this.f103j) - 9;
                    if (i2 > i6 && i2 < i7) {
                        if (i > (i5 - this.f103j) - 10 && this.P > ((this.A - this.e) - this.f103j) - 14) {
                            this.n = ((i2 - i6) * this.m) / (i7 - i6);
                            while (true) {
                                if (this.n <= 0) {
                                    this.n = 0;
                                } else if (((Object[]) this.a.elementAt(this.n))[4] == null) {
                                    this.n--;
                                }
                            }
                            this.Q = x.a(this.a, this.n);
                            return true;
                        }
                        int i8 = 0;
                        for (int i9 = 0; i9 < this.m; i9++) {
                            int i10 = (((i8 + this.D) + this.e) + 5) - this.f33g[0];
                            int i11 = ((int[]) ((Object[]) this.a.elementAt(i9))[3])[1];
                            i8 += i11;
                            int i12 = i10 + i11;
                            if (i2 > i10 && i2 < i12) {
                                this.n = i9;
                                while (true) {
                                    if (this.n <= 0) {
                                        this.n = 0;
                                    } else if (((Object[]) this.a.elementAt(this.n))[4] == null) {
                                        this.n--;
                                    }
                                }
                                this.Q = x.a(this.a, this.n);
                                this.o = i;
                                this.p = i2;
                                this.f115i = true;
                                return true;
                            }
                        }
                        break;
                    }
                }
                break;
            case 6:
                int i13 = this.D + this.e + 6;
                if (i2 >= i13 && i2 <= (i13 + this.L) - 1) {
                    for (int i14 = 0; i14 < this.m; i14++) {
                        int i15 = (this.C + (this.N * (i14 + 1))) - (this.M / 2);
                        int i16 = i15 + this.M + 4;
                        if (i >= i15 && i <= i16) {
                            for (int i17 = 0; i17 < this.k; i17++) {
                                if (this.f105b[i17] == (-((this.m + i14) - 1))) {
                                    this.l = i17;
                                }
                            }
                            this.t = 0;
                            this.f28e[i14][3] = (((this.L - (i2 - i13)) - 1) << 8) / this.L;
                            this.f28e[i14][2] = x.b(this.f26c[3], -12);
                            this.f28e[i14][1] = x.b(this.f26c[2], -12);
                            this.f28e[i14][0] = x.b(this.f26c[1], -12);
                            this.f26c[3] = (this.f28e[0][3] << this.f27d[0]) + (this.f28e[1][3] << this.f27d[1]) + this.f28e[2][3];
                            this.f26c[2] = x.b(this.f26c[3], -12);
                            this.f26c[1] = x.b(this.f26c[2], -12);
                            this.f26c[0] = x.b(this.f26c[1], -12);
                            return true;
                        }
                    }
                    break;
                }
                break;
            case 11:
                if (i > this.C && i < this.C + this.z) {
                    int i18 = this.D + this.e + 3;
                    int i19 = i18 + (this.E * this.m);
                    if (i2 > i18 && i2 < i19) {
                        int i20 = (i2 - i18) / this.E;
                        if (this.n == i20) {
                            super.a(Opcodes.LMUL);
                            return true;
                        }
                        this.n = i20;
                        this.s = 0;
                        return true;
                    }
                }
                break;
            case Opcodes.DCONST_1 /* 15 */:
                if (this.j && i2 > this.f103j + 2) {
                    if (i < this.C + this.f103j + 2) {
                        a(Opcodes.INEG);
                        break;
                    } else if (i > ((this.C + this.z) - this.f103j) - 2) {
                        a(Opcodes.LNEG);
                        break;
                    } else if (i < this.C + (this.z / 2)) {
                        a(Opcodes.FDIV);
                        break;
                    } else if (i > this.C + (this.z / 2)) {
                        a(Opcodes.DDIV);
                        break;
                    } else {
                        a(Opcodes.LMUL);
                        break;
                    }
                }
                break;
            case Opcodes.ALOAD /* 25 */:
                a(Opcodes.LMUL);
                break;
            default:
                super.a(Opcodes.LMUL);
                break;
        }
        return super.a(i, i2);
    }

    @Override // defpackage.am
    /* renamed from: a */
    public final String mo6a() {
        String string = "";
        switch (this.b) {
            case 2:
                string = this.f32j;
                break;
            case 5:
                string = this.k;
                break;
            case 6:
                string = new StringBuffer().append(Integer.toHexString(this.f26c[3])).append((this.f32j == null || this.f32j.length() <= 0) ? "" : new StringBuffer().append(f119a).append(this.f32j).toString()).toString();
                break;
            case 11:
                string = this.m > 0 ? this.f25a[this.n][0] : "";
                break;
            case Opcodes.DCONST_1 /* 15 */:
                string = this.f101d;
                break;
            case Opcodes.ALOAD /* 25 */:
                this.f32j = "";
                break;
        }
        return string;
    }
}
