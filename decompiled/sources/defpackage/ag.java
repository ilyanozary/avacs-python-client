package defpackage;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:ag.class */
public class ag extends am {
    private String[][] a;

    /* renamed from: a, reason: collision with other field name */
    private a[] f37a;
    private int[] c;

    /* renamed from: a, reason: collision with other field name */
    private boolean[] f38a;
    private int E = 1;
    private int F = 0;
    private int G = 0;
    private int H = 0;
    private char e = '_';
    private int I;

    @Override // defpackage.am
    public void a() {
        int iStringWidth;
        int iStringWidth2;
        String[] strArrA = new String[0];
        if (this.f101d != null && this.f101d.length() > 0) {
            strArrA = b.a(this.f101d, f120b);
        }
        this.m = strArrA.length;
        if (this.n >= this.m) {
            this.n = 0;
            this.f91a[0] = 0;
        }
        this.a = new String[this.m][4];
        this.f37a = new a[this.m];
        this.c = new int[this.m];
        this.f38a = new boolean[this.m];
        int i = 0;
        for (int i2 = 0; i2 < this.m; i2++) {
            String[] strArrA2 = b.a(strArrA[i2], f119a);
            this.E = strArrA2.length;
            this.a[i2][0] = strArrA2[0];
            this.a[i2][1] = strArrA2[1];
            if (this.a[i2][1].length() <= 0 || this.a[i2][1].charAt(0) != this.e) {
                this.f38a[i2] = false;
            } else {
                this.f38a[i2] = true;
                this.a[i2][1] = this.a[i2][1].substring(1);
            }
            this.a[i2][1] = b.b(this.a[i2][1], f121c);
            if (this.E == 4) {
                this.a[i2][2] = b.b(strArrA2[2], f121c);
                this.f37a[i2] = a.a(strArrA2[3]);
            } else if (this.E == 5) {
                this.a[i2][2] = b.b(strArrA2[2], f121c);
                this.a[i2][3] = b.b(strArrA2[3], f121c);
                this.f37a[i2] = a.a(strArrA2[4]);
            } else {
                this.f37a[i2] = a.a(strArrA2[2]);
            }
            this.c[i2] = this.f103j + 1;
            if (this.f37a[i2] != null && this.f37a[i2].b() > this.c[i2]) {
                this.c[i2] = this.f37a[i2].b() + 1;
            }
            i += this.c[i2];
        }
        this.I = i;
        int i3 = this.z - 8;
        this.F = i3;
        if (this.E == 4) {
            this.F = (i3 << 1) / 3;
            this.G = i3 / 3;
        }
        if (this.E == 5) {
            this.F = i3 / 2;
            this.G = i3 / 4;
            this.H = i3 / 4;
        }
        if (this.f88a) {
            int i4 = i + this.e + 11;
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
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            for (int i8 = 0; i8 < this.m; i8++) {
                int iStringWidth3 = this.f102a.stringWidth(this.a[i8][1]) + 3;
                if (this.f37a[i8] != null) {
                    iStringWidth3 += this.f37a[i8].a();
                }
                if (iStringWidth3 > i5) {
                    i5 = iStringWidth3;
                }
                if (this.G > 0 && (iStringWidth2 = this.f102a.stringWidth(this.a[i8][2]) + 2) > i6) {
                    i6 = iStringWidth2;
                }
                if (this.H > 0 && (iStringWidth = this.f102a.stringWidth(this.a[i8][3]) + 2) > i7) {
                    i7 = iStringWidth;
                }
            }
            int i9 = i5 + i6 + i7 + 10;
            int iA = x.a(this.f102a, this.i, this.f99b, this.f100c);
            if (iA > i9) {
                i9 = iA;
            }
            if (this.z > i9) {
                this.C += (this.z - i9) / 2;
                this.z = i9;
            }
            int i10 = this.z - 8;
            this.F = i10;
            if (this.E == 4) {
                if (i5 + i6 <= i10) {
                    this.F = i5;
                    this.G = i6;
                } else if (i5 <= (i10 << 1) / 3) {
                    this.F = i5;
                    this.G = i10 - i5;
                } else if (i6 <= (i10 << 1) / 3) {
                    this.F = i10 - i6;
                    this.G = i6;
                } else {
                    this.F = i10 / 2;
                    this.G = i10 / 2;
                }
                if (this.G + this.F < i10) {
                    this.F = i10 - this.G;
                }
            }
            if (this.E == 5) {
                if (i5 + i6 + i7 > i10) {
                    if (i5 <= i10 / 2) {
                        this.F = i5;
                    } else {
                        this.F = i10 / 2;
                    }
                    if (i6 + i7 <= i10 - this.F) {
                        this.G = i6;
                        this.H = i7;
                    } else {
                        int i11 = i10 - this.F;
                        if (i6 <= (i11 << 1) / 3) {
                            this.G = i6;
                            this.H = i11 - i6;
                        } else if (i7 <= (i11 << 1) / 3) {
                            this.G = i11 - i7;
                            this.H = i7;
                        } else {
                            this.G = i11 / 2;
                            this.H = i11 / 2;
                        }
                    }
                } else {
                    this.F = i5;
                    this.G = i6;
                    this.H = i7;
                }
                if (this.H + this.G + this.F < i10) {
                    this.F = (i10 - this.G) - this.H;
                }
            }
        }
    }

    @Override // defpackage.am
    protected void a(boolean z) {
        if (a(z)) {
            this.B = this.A;
        }
        int i = this.e;
        if (this.f93a != null && this.e <= this.f93a.b()) {
            this.e = this.f93a.b();
            this.A += this.e - i;
            this.D -= (this.e - i) / 2;
        }
        a();
    }

    @Override // defpackage.am
    public void a(Graphics graphics) {
        this.f110a = x.a(this.f93a, graphics, this.C, this.D, this.z, this.A, this.f102a, this.e, this.f90a, this.f118c[7][this.g], this.f118c[6][this.g], this.f118c[0][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.l, this.f118c[4][this.g], this.f118c[5][this.g], this.f118c[16][this.g], this.f118c[15][this.g], this.f94c, this.f105b, this.f95d, true, this.f118c[1][this.g], this.r, 15, this.d, this.f109g, this.f106e, this.f107f);
        String[][] strArr = this.a;
        a[] aVarArr = this.f37a;
        boolean[] zArr = this.f38a;
        int i = this.m;
        int i2 = this.n;
        int[] iArr = this.c;
        Font font = this.f102a;
        int i3 = this.f103j;
        int i4 = this.C + 3;
        int i5 = this.D + this.e + 4;
        int i6 = this.z - 8;
        int i7 = this.h == 0 ? (this.A - this.e) - 9 : ((this.A - this.f103j) - this.e) - 13;
        int i8 = this.f103j;
        boolean zA = x.a(strArr, aVarArr, zArr, i, i2, iArr, font, i3, graphics, i4, i5, i6, i7, this.F, this.G, this.H, this.f118c[17][this.g], this.f118c[18][this.g], this.f118c[19][this.g], this.f118c[20][this.g], this.f118c[21][this.g], this.f118c[22][this.g], this.f118c[23][this.g], this.f118c[24][this.g], this.f118c[25][this.g], this.f118c[26][this.g], this.f118c[27][this.g], this.f118c[28][this.g], this.f118c[29][this.g], this.s, 10, this.f91a, this.v, this.c, this.f116b, this.q, this.f109g);
        graphics.setClip(this.C, this.D, this.z, this.A);
        this.f111b = x.a(this.h, this.i, this.l, this.f105b, this.f99b, this.f100c, this.f102a, graphics, this.C + 2, (this.D + this.A) - 2, this.z - 6, this.f118c[14][this.g], this.f118c[15][this.g], this.f118c[12][this.g], this.f118c[13][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.f118c[0][this.g], 0, this.t, this.d, this.f95d, this.f109g);
        graphics.setClip(this.C, this.D, this.z, this.A);
        x.a(graphics, this.C + 2, this.D + this.e + 3, this.z - 6, ((this.f111b[0][1] - this.D) - this.e) - 4, this.f118c[2][this.g], this.f118c[3][this.g], false, this.f118c[9][this.g], (a) null, 0, this.r);
        if (this.I > (this.h == 0 ? (this.A - this.f103j) - 10 : ((this.A - this.f103j) - this.f103j) - 14)) {
            x.a(graphics, (this.C + this.z) - 5, this.D + this.e + 5, 1, this.h == 0 ? (this.A - this.e) - 11 : ((this.A - this.f103j) - this.e) - 15, this.f103j + 1, this.m, this.n, this.f118c[30][this.g]);
        }
        if (zA) {
            this.a.a((am) this, this.w, true);
        }
    }

    @Override // defpackage.am
    public final boolean a(int i, int i2) {
        int i3 = this.C + this.z + 3;
        if (i > this.C + 4 && i < i3) {
            int i4 = this.D + this.e + 5;
            int i5 = ((this.D + this.A) - this.e) - 9;
            if (this.h == 0) {
                i5 += this.f103j;
            }
            if (i2 > i4 && i2 < i5) {
                if (i > (i3 - this.f103j) - 10) {
                    if (this.I > (this.h == 0 ? (this.A - this.e) - 11 : ((this.A - this.e) - this.f103j) - 15)) {
                        this.n = ((i2 - i4) * this.m) / (i5 - i4);
                        return true;
                    }
                }
                this.o = i;
                this.p = i2;
                this.f115i = true;
                int i6 = 0;
                for (int i7 = 0; i7 < this.m; i7++) {
                    int i8 = (((i6 + this.D) + this.e) + 5) - this.f91a[0];
                    i6 += this.c[i7];
                    int i9 = i8 + this.c[i7];
                    if (i2 > i8 && i2 < i9) {
                        int i10 = i7;
                        if (this.n == i10) {
                            a(Opcodes.LMUL);
                            return true;
                        }
                        this.n = i10;
                        this.s = 0;
                        return true;
                    }
                }
            }
        }
        return super.a(i, i2);
    }

    @Override // defpackage.am
    /* renamed from: a */
    public String mo6a() {
        return this.m > 0 ? this.a[this.n][0] : "";
    }
}
