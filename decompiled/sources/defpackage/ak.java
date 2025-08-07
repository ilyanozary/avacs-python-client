package defpackage;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:ak.class */
public final class ak extends am {
    private a[] a;

    /* renamed from: a, reason: collision with other field name */
    private String[] f84a;
    private String[] b;
    private String g = "";
    private boolean j = false;
    private int E = 0;
    private int F = 128;

    /* renamed from: a, reason: collision with other field name */
    private Object[] f85a = null;

    /* renamed from: a, reason: collision with other field name */
    private Integer[] f86a = null;
    private int G = 0;
    private int H = 0;
    private int I = 0;

    @Override // defpackage.am
    public final void a() {
        int i;
        String[] strArrA = b.a(this.f101d, f120b);
        this.g = strArrA[0];
        this.m = strArrA.length - 1;
        this.a = new a[this.m];
        this.f84a = new String[this.m];
        this.b = new String[this.m];
        this.I = 0;
        for (int i2 = 1; i2 <= this.m; i2++) {
            String[] strArrA2 = b.a(strArrA[i2], f119a);
            int i3 = i2 - 1;
            this.b[i3] = b.b(strArrA2[0], f121c);
            a aVarA = a.a(strArrA2[1]);
            this.a[i3] = aVarA;
            this.f84a[i3] = b.b(strArrA2[2], f121c);
            this.I += (((aVarA.b() + 2) * (aVarA.a() + 2)) * Opcodes.TABLESWITCH) / 100;
        }
        if (this.f88a && (i = (((this.A - this.e) - this.f103j) - 8) * (this.z - 6)) > this.I) {
            int i4 = this.z;
            int i5 = (i * 10) / this.I;
            if (i5 > 50) {
                this.z = ((this.z - 6) / 2) + 6;
            } else if (i5 > 20) {
                this.z = (((this.z - 6) * 10) / 14) + 6;
            }
            int i6 = (((this.A - this.e) - this.f103j) - 8) * (this.z - 6);
            if (i6 > this.I) {
                this.A = (((((this.A - this.e) - this.f103j) - 8) * 10) / ((i6 * 10) / this.I)) + this.e + this.f103j + 11;
            }
            if (this.A < this.e + this.f103j + 34) {
                this.A = this.e + this.f103j + 34;
            }
            this.C += (i4 - this.z) / 2;
            this.D += (this.B - this.A) / 2;
        }
        a(false);
    }

    @Override // defpackage.am
    protected final void a(boolean z) {
        if (a(z)) {
            a();
            return;
        }
        Vector vector = new Vector(16);
        Vector vector2 = new Vector(16);
        Vector vector3 = new Vector(16);
        int i = 3;
        int i2 = 0;
        int i3 = this.z - 6;
        this.H = 0;
        for (int i4 = 0; i4 < this.m; i4++) {
            a aVar = this.a[i4];
            int iA = aVar.a();
            int iB = aVar.b() + 2;
            if (i + iA <= i3) {
                vector2.addElement(new Object[]{aVar, this.f84a[i4], this.b[i4], new Integer(i4)});
                if (i2 < iB) {
                    i2 = iB;
                }
            } else {
                Object[] objArr = new Object[vector2.size()];
                vector2.copyInto(objArr);
                vector.addElement(objArr);
                vector2 = new Vector(16);
                vector3.addElement(new Integer(i2));
                this.H += i2;
                i = 2;
                this.E++;
                i2 = iB;
                vector2.addElement(new Object[]{aVar, this.f84a[i4], this.b[i4], new Integer(i4)});
            }
            i += iA + 2;
        }
        Object[] objArr2 = new Object[vector2.size()];
        vector2.copyInto(objArr2);
        vector.addElement(objArr2);
        vector3.addElement(new Integer(i2));
        this.H += i2;
        this.f85a = new Object[vector.size()];
        vector.copyInto(this.f85a);
        this.f86a = new Integer[vector3.size()];
        vector3.copyInto(this.f86a);
        this.f112h = true;
    }

    @Override // defpackage.am
    public final void a(Graphics graphics) {
        if (this.u < 4) {
            this.f112h = true;
        }
        int i = this.f91a[0] - this.G;
        int i2 = 5 * this.v;
        if (i != 0) {
            if (i <= i2 - 1 && i >= (-i2) + 1) {
                this.f91a[0] = this.G;
            } else if (i > 0) {
                int[] iArr = this.f91a;
                iArr[0] = iArr[0] - i2;
            } else {
                int[] iArr2 = this.f91a;
                iArr2[0] = iArr2[0] + i2;
            }
            this.f112h = true;
        }
        this.f110a = x.a(this.f93a, graphics, this.C, this.D, this.z, this.A, this.f102a, this.e, this.f90a, this.f118c[7][this.g], this.f118c[6][this.g], this.f118c[0][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.l, this.f118c[4][this.g], this.f118c[5][this.g], this.f118c[16][this.g], this.f118c[15][this.g], this.f94c, this.f105b, this.f95d, true, this.f118c[1][this.g], this.r, 15, this.d, this.f109g, this.f106e, this.f107f);
        graphics.setClip(this.C, this.D, this.z, this.A);
        this.f111b = x.a(this.h, this.i, this.l, this.f105b, this.f99b, this.f100c, this.f102a, graphics, this.C + 2, (this.D + this.A) - 2, this.z - 6, this.f118c[14][this.g], this.f118c[15][this.g], this.f118c[12][this.g], this.f118c[13][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.f118c[0][this.g], 0, this.t, this.d, this.f95d, this.f109g);
        this.F = ((this.f111b[0][1] - this.D) - this.e) - 4;
        if (this.f112h) {
            graphics.setClip(this.C, this.D, this.z, this.A);
            x.a(graphics, this.C + 2, this.D + this.e + 3, this.z - 6, this.F, this.f118c[2][this.g], this.f118c[3][this.g], true, this.f118c[9][this.g], this.f116b, this.q, this.r);
        }
        graphics.setClip(this.C + 3, this.D + this.e + 5, this.z - 8, this.F - 3);
        int length = this.f85a.length;
        int i3 = this.D + this.e + 4 + this.f91a[0];
        int i4 = 0;
        int i5 = 0;
        String str = "";
        a aVar = null;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < length; i8++) {
            Object[] objArr = (Object[]) this.f85a[i8];
            int length2 = objArr.length;
            int iA = this.C + 3;
            int iIntValue = this.f86a[i8].intValue();
            int i9 = i4 + iIntValue;
            i4 = i9;
            if (i9 <= (-this.f91a[0]) || i4 - iIntValue >= ((-this.f91a[0]) + this.F) - 2) {
                i5 += length2;
            } else {
                for (Object obj : objArr) {
                    Object[] objArr2 = (Object[]) obj;
                    a aVar2 = (a) objArr2[0];
                    boolean z = this.n == i5;
                    boolean z2 = z;
                    if (z || this.f112h) {
                        if (z2) {
                            x.a(graphics, iA, i3, aVar2.a() + 2, aVar2.b() + 2, this.f118c[2][this.g], this.f118c[3][this.g], this.f112h ? -1 : this.f118c[9][this.g]);
                            str = (String) objArr2[1];
                            aVar = aVar2;
                            i6 = iA;
                            i7 = i3;
                        }
                        a.a(aVar2, z2, this.r, graphics, iA + 1, i3 + 1, 20);
                    }
                    iA += aVar2.a() + 2;
                    i5++;
                }
            }
            i3 += iIntValue;
        }
        if (this.f96e && str != null && str.length() > 0 && (this.s > 7 || this.f109g)) {
            int iStringWidth = this.f102a.stringWidth(str);
            int iA2 = i6 + 1 + ((aVar.a() - iStringWidth) / 2);
            int iB = i7 + aVar.b();
            if (iA2 < this.C + 2) {
                iA2 = this.C + 3;
            }
            if (iA2 + iStringWidth + 9 > this.C + this.z) {
                iA2 = ((this.C + this.z) - iStringWidth) - 9;
            }
            if (iB + this.f103j > this.D + this.F + this.e + 4) {
                iB = i7 - this.f103j;
            }
            if (this.f95d) {
                graphics.setColor(11711154);
            } else if (this.s > 10 || this.f109g) {
                graphics.setColor(16777215);
            } else if (this.s == 8) {
                graphics.setColor(9211020);
            } else if (this.s == 9) {
                graphics.setColor(11711154);
            } else if (this.s == 10) {
                graphics.setColor(14211288);
            }
            graphics.fillRect(iA2 + 1, iB, iStringWidth + 2, this.f103j);
            graphics.setColor(0);
            graphics.drawString(str, iA2 + 2, iB, 20);
            graphics.drawRect(iA2, iB - 1, iStringWidth + 3, this.f103j + 1);
        }
        this.f112h = false;
    }

    @Override // defpackage.am
    public final void a(int i) {
        int i2 = this.n;
        if (this.f96e) {
            switch (i) {
                case Opcodes.LSUB /* 101 */:
                    this.n--;
                    if (this.n < 0) {
                        this.n = this.m - 1;
                    }
                    this.s = 0;
                    break;
                case Opcodes.FSUB /* 102 */:
                    this.n++;
                    if (this.n >= this.m) {
                        this.n = 0;
                    }
                    this.s = 0;
                    break;
                case Opcodes.DSUB /* 103 */:
                    d(false);
                    this.s = 0;
                    break;
                case Opcodes.IMUL /* 104 */:
                    d(true);
                    this.s = 0;
                    break;
                default:
                    super.a(i);
                    break;
            }
        }
        if (i2 != this.n) {
            b();
        }
    }

    private void b() {
        int i = 0;
        int i2 = 0;
        int length = this.f85a.length;
        int iIntValue = 0;
        for (int i3 = 0; i3 < length; i3++) {
            int length2 = ((Object[]) this.f85a[i3]).length;
            iIntValue = this.f86a[i3].intValue();
            if ((i + length2) - 1 >= this.n) {
                break;
            }
            i += length2;
            i2 += iIntValue;
        }
        if (this.f91a[0] + i2 <= 0) {
            this.G = -i2;
        } else if (this.f91a[0] + i2 > (this.F - 2) - iIntValue) {
            this.G = (((-i2) + this.F) - 2) - iIntValue;
        }
        if (this.G > 0) {
            this.G = 0;
        }
        if (this.H > this.F - 2 && this.G + this.H < this.F - 2) {
            this.G = (this.F - 2) - this.H;
        }
        this.f112h = true;
    }

    private void d(boolean z) {
        int i;
        int i2 = this.n;
        int length = this.f85a.length;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i4 >= length) {
                i = 0;
                break;
            }
            int length2 = ((Object[]) this.f85a[i4]).length;
            if (i3 + length2 > i2) {
                i = i4;
                break;
            } else {
                i3 += length2;
                i4++;
            }
        }
        int i5 = i;
        Object[] objArr = (Object[]) this.f85a[i5];
        int length3 = objArr.length;
        int iA = 0;
        int i6 = 0;
        while (true) {
            if (i6 >= length3) {
                break;
            }
            Object[] objArr2 = (Object[]) objArr[i6];
            a aVar = (a) objArr2[0];
            int iIntValue = ((Integer) objArr2[3]).intValue();
            iA += aVar.a();
            if (iIntValue == this.n) {
                iA -= aVar.a() / 2;
                break;
            }
            i6++;
        }
        Object[] objArr3 = (Object[]) this.f85a[z ? this.f85a.length > i5 + 1 ? i5 + 1 : 0 : i5 - 1 < 0 ? this.f85a.length - 1 : i5 - 1];
        int i7 = 0;
        this.n = ((Integer) ((Object[]) objArr3[objArr3.length - 1])[3]).intValue();
        for (Object obj : objArr3) {
            Object[] objArr4 = (Object[]) obj;
            int iA2 = ((a) objArr4[0]).a();
            if (i7 + iA2 >= iA) {
                this.n = ((Integer) objArr4[3]).intValue();
                return;
            }
            i7 += iA2;
        }
    }

    @Override // defpackage.am
    public final boolean a(int i, int i2) {
        int i3 = this.D + this.e + 6;
        int i4 = this.f111b[0][1] - 2;
        if (i2 >= i3 && i2 <= i4) {
            this.o = i;
            this.p = i2;
            this.f115i = true;
            int length = this.f85a.length;
            int i5 = this.D + this.e + 4 + this.f91a[0];
            for (int i6 = 0; i6 < length; i6++) {
                int iIntValue = this.f86a[i6].intValue();
                i5 += iIntValue;
                if (i2 < i5 && i2 > i5 - iIntValue) {
                    Object[] objArr = (Object[]) this.f85a[i6];
                    int iA = this.C + 3;
                    for (Object obj : objArr) {
                        Object[] objArr2 = (Object[]) obj;
                        a aVar = (a) objArr2[0];
                        iA += aVar.a() + 2;
                        if (i < iA && i > (iA - aVar.a()) - 2) {
                            int iIntValue2 = ((Integer) objArr2[3]).intValue();
                            if (this.n == iIntValue2) {
                                a(Opcodes.LMUL);
                                return true;
                            }
                            this.n = iIntValue2;
                            b();
                            this.s = 0;
                            this.f112h = true;
                            return true;
                        }
                    }
                }
            }
        }
        this.f112h = true;
        return super.a(i, i2);
    }

    @Override // defpackage.am
    public final void b(int i) {
        this.G = i;
        b();
    }

    @Override // defpackage.am
    /* renamed from: a */
    public final String mo6a() {
        return new StringBuffer().append(this.m > 0 ? b.m26a(this.b[this.n], f121c) : "").append((this.g == null || this.g.length() <= 0) ? "" : new StringBuffer().append("=").append(this.g).toString()).toString();
    }
}
