package defpackage;

import avacs.c;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:ai.class */
public final class ai extends am {
    private int I;
    private int J;
    private int K;
    private int L;
    private int M;
    private int N;

    /* renamed from: a, reason: collision with other field name */
    private String[] f40a;
    private int[] d;

    /* renamed from: a, reason: collision with other field name */
    private a[] f41a;

    /* renamed from: a, reason: collision with other field name */
    private String[][] f42a;

    /* renamed from: a, reason: collision with other field name */
    private Object[] f45a;

    /* renamed from: b, reason: collision with other field name */
    private Object[] f46b;

    /* renamed from: c, reason: collision with other field name */
    private a f47c;

    /* renamed from: d, reason: collision with other field name */
    private a f48d;
    private int U;
    private int V;
    private static int F = 0;
    private static int G = 0;
    private static Image[] a = null;
    private static int H = 0;

    /* renamed from: a, reason: collision with other field name */
    private static Vector f39a = new Vector(60, 10);
    private static Vector b = new Vector(7, 2);

    /* renamed from: c, reason: collision with other field name */
    private static Vector f43c = new Vector(7, 2);

    /* renamed from: d, reason: collision with other field name */
    private static Vector f44d = new Vector(7, 2);
    private static Vector e = new Vector(7, 2);
    private static Vector f = new Vector(7, 2);
    private int E = 0;
    private int[] c = new int[1];
    private int O = 8;
    private int P = 0;
    private int Q = 16;
    private int R = 4;
    private int S = 0;
    private int T = 0;

    /* renamed from: e, reason: collision with other field name */
    private int[] f49e = {0, 0};

    /* renamed from: f, reason: collision with other field name */
    private int[] f50f = {0, 0};
    private int[] g = {0, 0};
    private int[] h = {0, 0};
    private int W = 0;

    @Override // defpackage.am
    public final void a() throws NumberFormatException {
        switch (this.b) {
            case Opcodes.FLOAD /* 23 */:
                this.f = true;
                String[] strArrA = b.a(this.f101d, f120b);
                String str = this.v > 1 ? "    " : " ";
                for (String str2 : strArrA) {
                    String[] strArrA2 = b.a(str2, f119a);
                    if (strArrA2.length >= 3) {
                        String string = new StringBuffer().append(str).append(b.b(strArrA2[0], f121c)).append(str).toString();
                        if (b.size() == 0 || ((String) b.lastElement()).compareTo(string) != 0) {
                            int i = strArrA2.length >= 4 ? Integer.parseInt(strArrA2[3]) : 0;
                            f44d.addElement(new Integer(i));
                            b.addElement(string);
                            Object[] objArrM133a = x.m133a(string);
                            if (i == 1 && c.f) {
                                int length = objArrM133a.length;
                                Object[] objArr = new Object[length];
                                for (int i2 = 1; i2 <= length; i2++) {
                                    objArr[length - i2] = objArrM133a[i2 - 1];
                                }
                                objArrM133a = objArr;
                            }
                            e.addElement(objArrM133a);
                            f43c.addElement(new int[][]{x.a(strArrA2[1]), x.a(strArrA2[2])});
                            a aVarA = null;
                            if (strArrA2.length >= 4 && strArrA2.length >= 5 && strArrA2[4].length() > 0) {
                                aVarA = a.a(strArrA2[4]);
                            }
                            f.addElement(aVarA);
                        }
                    }
                }
                break;
            case Opcodes.DLOAD /* 24 */:
                this.E = Integer.parseInt(this.f101d);
                this.P = this.E / (1000 / this.O);
                this.N = ((this.z * 1000) * 1000) / (this.E * this.O);
                break;
            case 30:
                this.f = true;
                a = new Image[4];
                if (this.f103j > 18) {
                    a[0] = b.a("msg");
                } else {
                    a[0] = b.a("smsg");
                }
                a[0] = a.a(a[0], 1, 2);
                int length2 = 100 / a.length;
                for (int i3 = 1; i3 < a.length; i3++) {
                    a[i3] = a.a(a[0], 100 - (length2 * i3));
                }
                if (2 * this.f103j > a[0].getWidth()) {
                    H = 2 * this.f103j;
                } else {
                    H = a[0].getWidth();
                }
                String[] strArrA3 = b.a(this.f101d, f120b);
                this.f101d = "";
                if (this.f92b) {
                    this.c[0] = 0;
                    this.f91a[0] = 0;
                }
                this.n = x.a(strArrA3, f39a, this.n, this.z - 10, f119a, f121c, this.f102a, this.f103j, this.f92b);
                this.m = f39a.size();
                this.I = 0;
                for (int i4 = 0; i4 < this.m; i4++) {
                    this.I += ((int[]) ((Object[]) f39a.elementAt(i4))[3])[1];
                }
                this.L = this.D + 1;
                this.M = this.C + 1;
                this.K = (this.A - this.L) - 1;
                this.J = x.a(f39a, this.n);
                break;
            case 31:
            case 34:
            case Canvas.KEY_POUND /* 35 */:
                this.w = 4;
                String[] strArrA4 = b.a(this.f101d, f120b);
                this.m = strArrA4.length;
                this.f42a = new String[this.m][2];
                this.f41a = new a[this.m];
                for (int i5 = 0; i5 < this.m; i5++) {
                    String[] strArrA5 = b.a(strArrA4[i5], f119a);
                    this.f42a[i5][0] = strArrA5[0];
                    this.f42a[i5][1] = b.b(strArrA5[1], f121c);
                    this.f41a[i5] = a.a(strArrA5[2]);
                }
                this.r = -20;
                break;
            case 32:
                this.f = true;
                this.d = new int[2];
                this.f40a = b.a(this.f101d, f120b);
                this.d[0] = this.f102a.stringWidth(this.f40a[0]);
                this.d[1] = this.f102a.stringWidth(this.f40a[1]);
                this.C = ((this.z - this.d[0]) - this.d[1]) - 2;
                break;
        }
    }

    /* JADX WARN: Type inference failed for: r1v277, types: [int[], int[][]] */
    @Override // defpackage.am
    public final void a(Graphics graphics) {
        switch (this.b) {
            case Opcodes.FLOAD /* 23 */:
                if (this.S > 0 || this.T > 0) {
                    if (this.W == 0) {
                        x.a(this.f47c, this.f45a, this.f49e, this.g, this.g, true, this.f102a, this.f103j, graphics, this.C + this.U, this.D, this.f103j, 0, this.t, this.W);
                        x.a(this.f48d, this.f46b, this.f50f, this.h, this.g, true, this.f102a, this.f103j, graphics, this.C + this.V, this.D, this.f103j, 0, this.t, this.W);
                        if (this.f96e) {
                            if (this.v == 1) {
                                this.U -= 5;
                                this.V -= 5;
                            } else {
                                this.U -= 4 * this.v;
                                this.V -= 4 * this.v;
                            }
                            if (this.U + this.S < this.C) {
                                if (this.T > 0) {
                                    b();
                                    this.U = this.V;
                                    this.V = this.U + this.S + (this.z / 2);
                                } else {
                                    b();
                                    this.U = this.z;
                                    this.V = this.U + this.S + (this.z / 2);
                                }
                            }
                        }
                    } else {
                        x.a(this.f47c, this.f45a, this.f49e, this.g, this.g, true, this.f102a, this.f103j, graphics, this.C + this.U, this.D, this.f103j, 0, this.t, this.W);
                        x.a(this.f48d, this.f46b, this.f50f, this.h, this.g, true, this.f102a, this.f103j, graphics, this.C + this.V, this.D, this.f103j, 0, this.t, this.W);
                        if (this.f96e) {
                            this.U += 4 * this.v;
                            this.V += 4 * this.v;
                            if (this.U > this.C + this.z) {
                                if (this.T > 0) {
                                    b();
                                    this.U = this.V;
                                    this.V = (this.U - this.T) - (this.z / 2);
                                } else {
                                    b();
                                    this.U = this.C - this.S;
                                    this.V = (this.U - this.T) - (this.z / 2);
                                }
                            }
                        }
                    }
                    this.a.a((am) this, 0, false);
                    break;
                } else if (b()) {
                    if (this.W == 0) {
                        this.U = this.z;
                        this.V = this.U + this.S + (this.z / 2);
                        break;
                    } else {
                        this.U = this.C - this.S;
                        this.V = (this.U - (this.z / 2)) - this.T;
                        break;
                    }
                }
                break;
            case Opcodes.DLOAD /* 24 */:
                if (this.r > this.P) {
                    this.r = 0;
                }
                graphics.setClip(this.C, this.D, (this.r * this.N) / 1000, this.f103j);
                graphics.setColor(this.f118c[10][this.g]);
                graphics.fillRect(this.C, this.D, (this.r * this.N) / 1000, this.f103j);
                graphics.setColor(this.f118c[11][this.g]);
                graphics.drawString(this.f90a, this.C + 1, this.D, 20);
                graphics.setClip(this.C + ((this.r * this.N) / 1000), this.D, this.z - ((this.r * this.N) / 1000), this.f103j);
                graphics.setColor(this.f118c[9][this.g]);
                graphics.fillRect(this.C + ((this.r * this.N) / 1000), this.D, this.z - ((this.r * this.N) / 1000), this.f103j);
                graphics.setColor(this.f118c[8][this.g]);
                graphics.drawString(this.f90a, this.C + 1, this.D, 20);
                this.a.a((am) this, 0, true);
                break;
            case 30:
                graphics.setColor(this.f118c[32][this.g]);
                graphics.setClip(0, 0, this.x, this.y);
                graphics.fillRect(0, 0, this.x, this.y);
                boolean zA = x.a(graphics, this.M, this.L, this.z - 2, this.K, this.f116b, this.q, this.J, this.c, this.v, this.f91a, this.f102a, this.f103j, this.g, this.m, this.n, this.I, f39a, this.f118c[30][this.g], this.f118c[31][this.g], this.r, this.c);
                graphics.setClip(this.C, this.D, this.z, this.A);
                this.f110a = new int[3];
                int i = 1;
                if (this.f106e != null) {
                    this.f110a[1] = x.a(graphics, this.C + 1 + 1, this.D + this.f103j + 1, this.f103j + 1, this.f103j + 1, this.f118c[16][this.g], this.f118c[4][this.g], false, "L", this.f109g);
                    i = 1 + this.f103j + 2;
                }
                if (this.f107f != null) {
                    this.f110a[2] = x.a(graphics, this.C + 1 + i, this.D + this.f103j + 1, this.f103j + 1, this.f103j + 1, this.f118c[16][this.g], this.f118c[4][this.g], false, "R", this.f109g);
                }
                if (this.f96e) {
                    if (G == 24) {
                        a(Opcodes.LSUB);
                    }
                    G++;
                    int length = a.length;
                    int i2 = F / 2;
                    F++;
                    if (i2 < (length << 1)) {
                        graphics.drawImage(a[i2 < length ? (length - i2) - 1 : i2 - length], ((this.z - H) - (this.f103j / 2)) - 2, ((this.A - H) - (this.f103j / 2)) - 2, 20);
                    }
                }
                if (zA) {
                    this.a.a((am) this, this.v, true);
                    break;
                }
                break;
            case 31:
            case 34:
            case Canvas.KEY_POUND /* 35 */:
                this.r += 2;
                if (this.r > 5) {
                    this.r = 5;
                } else {
                    this.s = 0;
                }
                this.Q = 16;
                if (this.f41a[0] != null && this.f41a[0].b() > this.Q) {
                    this.Q = this.f41a[0].b();
                }
                int i3 = 0;
                int i4 = 0;
                int i5 = ((this.Q + this.R) * this.m) - this.R;
                switch (this.b) {
                    case 31:
                        i3 = (this.z - i5) / 2;
                        i4 = this.D + this.r;
                        break;
                    case 34:
                        i3 = (this.C + this.r) - 1;
                        i4 = (this.A - i5) / 2;
                        break;
                    case Canvas.KEY_POUND /* 35 */:
                        i3 = ((((this.C + this.z) - this.r) - this.R) - this.Q) + 3;
                        i4 = (this.A - i5) / 2;
                        break;
                }
                if (this.b == 31) {
                    x.a(graphics, this.C, i4 - 4, this.z, this.Q + 10, this.f118c[0][this.g], -1, false, false, true, false, this.f109g);
                } else {
                    x.a(graphics, i3 - 4, (this.D + ((this.A - i5) / 2)) - 3, this.Q + 10, i5 + 6, this.f118c[0][this.g], -1, true, true, this.b == 34, true, this.f109g);
                }
                if (this.f96e && (this.s > 2 || this.f109g)) {
                    int iStringWidth = this.f102a.stringWidth(this.f42a[this.n][1]);
                    int i6 = 0;
                    int i7 = 0;
                    switch (this.b) {
                        case 31:
                            i6 = (((i3 + ((this.n + 1) * (this.Q + this.R))) - ((this.Q + iStringWidth) / 2)) - 2) - this.R;
                            i7 = i4 + this.Q + 5;
                            break;
                        case 34:
                            i6 = i3 + this.Q + 4;
                            i7 = i4 + (this.n * (this.Q + this.R)) + ((this.Q - this.f103j) / 2);
                            break;
                        case Canvas.KEY_POUND /* 35 */:
                            i6 = (i3 - 7) - iStringWidth;
                            i7 = i4 + (this.n * (this.Q + this.R)) + ((this.Q - this.f103j) / 2);
                            break;
                    }
                    if (i6 < 0) {
                        i6 = 0;
                    }
                    if (i6 + iStringWidth + 4 > this.C + this.z) {
                        i6 = ((this.C + this.z) - iStringWidth) - 4;
                    }
                    if (this.f95d) {
                        graphics.setColor(11711154);
                    } else if (this.s > 5 || this.f109g) {
                        graphics.setColor(16777215);
                    } else if (this.s == 3) {
                        graphics.setColor(9211020);
                    } else if (this.s == 4) {
                        graphics.setColor(11711154);
                    } else if (this.s == 5) {
                        graphics.setColor(14211288);
                    }
                    graphics.fillRect(i6 + 1, i7, iStringWidth + 2, this.f103j);
                    graphics.setColor(0);
                    graphics.drawString(this.f42a[this.n][1], i6 + 2, i7, 20);
                    graphics.drawRect(i6, i7 - 1, iStringWidth + 3, this.f103j + 1);
                }
                int i8 = i4 + 1;
                int i9 = i3 + 1;
                for (int i10 = 0; i10 < this.m; i10++) {
                    if (i10 != this.n) {
                        a.a(this.f41a[i10], false, this.s, graphics, i9, i8, 20);
                    } else if (this.f95d) {
                        a.a(this.f41a[i10], false, this.s, graphics, i9, i8, 20);
                        x.a(graphics, i9 - 2, i8 - 2, this.Q + 4, this.Q + 4, this.f118c[2][this.g], this.f118c[3][this.g], false, this.f118c[0][this.g], this.f116b, this.q, this.r);
                    } else {
                        a.a(this.f41a[i10], true, this.s, graphics, i9 - 1, i8 - 1, 20);
                        x.a(graphics, i9 - 3, i8 - 3, this.Q + 4, this.Q + 4, this.f118c[2][this.g], this.f118c[3][this.g], -1);
                    }
                    switch (this.b) {
                        case 31:
                            i9 += this.Q + this.R;
                            break;
                        case 34:
                        case Canvas.KEY_POUND /* 35 */:
                            i8 += this.Q + this.R;
                            break;
                    }
                }
                if (this.r < 5) {
                    this.a.a((am) this, this.v, true);
                    break;
                }
                break;
            case 32:
                graphics.setColor(this.f118c[33][this.g]);
                graphics.fillRect(this.C, this.D, this.d[0], this.f103j);
                graphics.setColor(this.f118c[34][this.g]);
                graphics.fillRect(this.C + this.d[0], this.D, this.d[1], this.f103j);
                graphics.setColor(this.f118c[35][this.g]);
                graphics.drawString(this.f40a[0], this.C, this.D, 20);
                graphics.setColor(this.f118c[36][this.g]);
                graphics.drawString(this.f40a[1], this.C + this.d[0], this.D, 20);
                break;
        }
    }

    @Override // defpackage.am
    protected final void a(boolean z) throws NumberFormatException {
        switch (this.b) {
            case Opcodes.FLOAD /* 23 */:
                this.z = this.x;
                break;
            case 30:
                this.A = this.y;
                this.z = this.x;
                this.f92b = false;
                this.I = 0;
                for (int i = 0; i < f39a.size(); i++) {
                    Object[] objArr = (Object[]) f39a.elementAt(i);
                    objArr[3] = x.a(objArr[2], this.f102a, this.f103j);
                    if (objArr[4] != null) {
                        a aVar = (a) objArr[5];
                        if (aVar != null && aVar.b() > ((int[]) objArr[3])[1]) {
                            ((int[]) objArr[3])[1] = aVar.b();
                        }
                        ((int[]) objArr[3])[1] = ((int[]) objArr[3])[1] + ((this.f103j << 1) / 3);
                    }
                    this.I += ((int[]) objArr[3])[1];
                }
                a();
                break;
            case 31:
            case 34:
            case Canvas.KEY_POUND /* 35 */:
                this.A = this.y;
                this.z = this.x;
                break;
            case 32:
                this.z = this.x;
                a();
                break;
        }
    }

    @Override // defpackage.am
    public final void a(int i) {
        if (this.f96e) {
            switch (this.b) {
                case 30:
                    switch (i) {
                        case Opcodes.LSUB /* 101 */:
                            this.l = 1;
                            super.a(Opcodes.LMUL);
                            break;
                        case Opcodes.FSUB /* 102 */:
                            this.f104a = System.currentTimeMillis();
                            String strMo6a = mo6a();
                            this.a.a(this.f89a, strMo6a.charAt(0) == '-' ? 320 : -20, strMo6a, null, this);
                            break;
                        case Opcodes.DSUB /* 103 */:
                            this.f104a = System.currentTimeMillis();
                            while (true) {
                                this.n--;
                                if (this.n <= 0) {
                                    this.n = 0;
                                } else if (((Object[]) f39a.elementAt(this.n))[4] != null) {
                                }
                            }
                            this.J = x.a(f39a, this.n);
                            break;
                        case Opcodes.IMUL /* 104 */:
                            this.f104a = System.currentTimeMillis();
                            int i2 = this.n;
                            while (true) {
                                this.n++;
                                if (this.n >= this.m) {
                                    this.n = i2;
                                } else if (((Object[]) f39a.elementAt(this.n))[4] != null) {
                                }
                            }
                            this.J = x.a(f39a, this.n);
                            break;
                        case Opcodes.LMUL /* 105 */:
                        case Opcodes.DDIV /* 111 */:
                            this.l = 0;
                            super.a(Opcodes.LMUL);
                            break;
                        case Opcodes.FMUL /* 106 */:
                        case Opcodes.DMUL /* 107 */:
                        case Opcodes.IDIV /* 108 */:
                        case Opcodes.LDIV /* 109 */:
                        case Opcodes.IREM /* 112 */:
                        case Opcodes.LREM /* 113 */:
                        default:
                            this.l = 0;
                            super.a(i);
                            break;
                        case Opcodes.FDIV /* 110 */:
                            super.a(Opcodes.DREM);
                            break;
                        case Opcodes.FREM /* 114 */:
                            this.a.a(this.f89a, -210, null, null, this);
                            break;
                    }
                case 31:
                    switch (i) {
                        case Opcodes.LSUB /* 101 */:
                            super.a(Opcodes.DSUB);
                            this.a.a(this.f89a, -200, null, null, this);
                            break;
                        case Opcodes.FSUB /* 102 */:
                            super.a(Opcodes.IMUL);
                            this.a.a(this.f89a, -200, null, null, this);
                            break;
                        case Opcodes.DSUB /* 103 */:
                        case Opcodes.IMUL /* 104 */:
                            super.a(Opcodes.FREM);
                            break;
                        default:
                            super.a(i);
                            break;
                    }
                case 34:
                case Canvas.KEY_POUND /* 35 */:
                    switch (i) {
                        case Opcodes.LSUB /* 101 */:
                        case Opcodes.FSUB /* 102 */:
                            super.a(Opcodes.FREM);
                            break;
                        case Opcodes.DSUB /* 103 */:
                        case Opcodes.IMUL /* 104 */:
                            super.a(i);
                            this.a.a(this.f89a, -200, null, null, this);
                            break;
                        default:
                            super.a(i);
                            break;
                    }
            }
        }
    }

    @Override // defpackage.am
    /* renamed from: a */
    public final String mo6a() {
        String str = "";
        switch (this.b) {
            case 30:
                if (this.m > 0) {
                    if (this.n >= f39a.size()) {
                        this.n = f39a.size() - 1;
                    }
                    str = (String) ((Object[]) f39a.elementAt(this.n))[0];
                    break;
                }
                break;
            case 31:
            case 34:
            case Canvas.KEY_POUND /* 35 */:
                str = this.f42a[this.n][0];
                break;
        }
        return str;
    }

    @Override // defpackage.am
    public final boolean a(int i, int i2) {
        switch (this.b) {
            case 30:
                if (F > (a.length << 2)) {
                    F = 0;
                }
                this.f104a = System.currentTimeMillis();
                if (!super.a(i, i2)) {
                    if (i <= (this.z - this.f103j) - 3 || this.I <= this.A) {
                        if (i2 < this.f103j + 1) {
                            if (i > (this.z * 3) / 4) {
                                a(Opcodes.DREM);
                                break;
                            } else {
                                a(Opcodes.LSUB);
                                break;
                            }
                        } else if ((i2 <= (this.A - this.f103j) - 5 || i <= this.z / 8 || i >= this.z - (this.z / 8)) && (i2 <= ((this.A - H) - (this.f103j / 2)) - 2 || i <= ((this.z - H) - (this.f103j / 2)) - 2)) {
                            int i3 = 0;
                            for (int i4 = 0; i4 < this.m; i4++) {
                                int i5 = i3 - this.f91a[0];
                                int i6 = ((int[]) ((Object[]) f39a.elementAt(i4))[3])[1];
                                i3 += i6;
                                int i7 = i5 + i6;
                                if (i2 > i5 && i2 < i7) {
                                    int i8 = i4;
                                    while (true) {
                                        if (i8 <= 0) {
                                            i8 = 0;
                                        } else if (((Object[]) f39a.elementAt(i8))[4] == null) {
                                            i8--;
                                        }
                                    }
                                    this.J = x.a(f39a, i8);
                                    this.o = i;
                                    this.p = i2;
                                    this.f115i = true;
                                    if (this.n == i8) {
                                        a(Opcodes.FSUB);
                                    } else {
                                        this.n = i8;
                                    }
                                }
                            }
                            break;
                        } else {
                            a(Opcodes.DDIV);
                            break;
                        }
                    } else {
                        this.n = (i2 * this.m) / this.A;
                        while (true) {
                            if (this.n <= 0) {
                                this.n = 0;
                            } else if (((Object[]) f39a.elementAt(this.n))[4] == null) {
                                this.n--;
                            }
                        }
                        this.J = x.a(f39a, this.n);
                        break;
                    }
                }
                break;
            case 31:
                if (i2 < this.Q + this.R + this.f103j) {
                    d(i - ((this.z - (((this.Q + this.R) * this.m) - this.R)) / 2));
                    break;
                } else {
                    super.a(Opcodes.FREM);
                    break;
                }
            case 34:
                if (i < this.Q + this.R + this.f103j) {
                    d(i2 - ((this.A - (((this.Q + this.R) * this.m) - this.R)) / 2));
                    break;
                } else {
                    super.a(Opcodes.FREM);
                    break;
                }
            case Canvas.KEY_POUND /* 35 */:
                if (i > ((this.z - this.Q) - this.R) - this.f103j) {
                    d(i2 - ((this.A - (((this.Q + this.R) * this.m) - this.R)) / 2));
                    break;
                } else {
                    super.a(Opcodes.FREM);
                    break;
                }
        }
        return true;
    }

    private void d(int i) {
        if (i >= (this.Q + this.R) * this.m) {
            super.a(Opcodes.FREM);
            return;
        }
        int i2 = i / (this.Q + this.R);
        int i3 = i2;
        if (i2 < 0) {
            super.a(Opcodes.FREM);
            i3 = 0;
        }
        if (this.n == i3) {
            super.a(Opcodes.LMUL);
            return;
        }
        this.n = i3;
        this.s = 0;
        this.a.a(this.f89a, -200, null, null, this);
    }

    private boolean b() {
        boolean z = false;
        boolean z2 = false;
        if (this.f45a == null || this.S == 0) {
            z2 = true;
        }
        this.f45a = null;
        this.f46b = null;
        try {
            if (b.size() > 0) {
                f44d.firstElement();
                int iM8b = m8b();
                if (z2 || this.W == iM8b) {
                    this.W = iM8b;
                    this.f45a = (Object[]) e.firstElement();
                    this.f49e = ((int[][]) f43c.firstElement())[0];
                    this.g = ((int[][]) f43c.firstElement())[1];
                    this.f47c = (a) f.firstElement();
                    b.removeElementAt(0);
                    f43c.removeElementAt(0);
                    f44d.removeElementAt(0);
                    e.removeElementAt(0);
                    f.removeElementAt(0);
                    z = true;
                    if (b.size() > 0) {
                        f44d.firstElement();
                        if (this.W == m8b()) {
                            this.f46b = (Object[]) e.firstElement();
                            this.f50f = ((int[][]) f43c.firstElement())[0];
                            this.h = ((int[][]) f43c.firstElement())[1];
                            this.f48d = (a) f.firstElement();
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        this.S = x.a(this.f45a, this.f102a, this.f103j)[0];
        this.T = x.a(this.f46b, this.f102a, this.f103j)[0];
        return z;
    }

    /* renamed from: b, reason: collision with other method in class */
    private static int m8b() {
        return ((Integer) f44d.firstElement()).intValue();
    }
}
