package defpackage;

import avacs.c;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/* loaded from: avacs.jar:x.class */
public class x {
    public byte[] a;

    /* renamed from: a, reason: collision with other field name */
    private w f211a;
    private w b;

    /* renamed from: a, reason: collision with other field name */
    public z f212a;

    /* renamed from: a, reason: collision with other field name */
    public t f213a;

    /* renamed from: a, reason: collision with other field name */
    public boolean f214a;

    public x(z zVar) {
        this(zVar, new w(), null);
    }

    private x(z zVar, w wVar, byte[] bArr) {
        this(zVar, wVar, wVar, null);
    }

    private x(z zVar, w wVar, w wVar2, byte[] bArr) {
        this.f212a = zVar;
        this.f211a = wVar;
        this.b = wVar2;
        this.a = new byte[20];
        if (bArr != null) {
            wVar.a(bArr, 0, bArr.length);
        }
        wVar.m132a(this.a, 0);
    }

    public final int a() {
        z zVar = this.f212a;
        int iM126a = zVar.a[0].m126a();
        int i = zVar.f218a ? ((iM126a + 7) / 8) - 1 : (iM126a + 7) / 8;
        return this.f214a ? (i - 1) - (2 * this.a.length) : i;
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [int] */
    private static void a(int i, byte[] bArr) {
        bArr[0] = i >> 24;
        bArr[1] = (byte) (i >>> 16);
        bArr[2] = (byte) (i >>> 8);
        bArr[3] = (byte) i;
    }

    public byte[] a(byte[] bArr, int i, int i2, int i3) {
        byte[] bArr2 = new byte[i3];
        w wVar = this.b;
        byte[] bArr3 = new byte[20];
        byte[] bArr4 = new byte[4];
        int i4 = 0;
        this.f211a.a();
        do {
            a(i4, bArr4);
            this.b.a(bArr, i, i2);
            this.b.a(bArr4, 0, bArr4.length);
            this.b.m132a(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, i4 * bArr3.length, bArr3.length);
            i4++;
        } while (i4 < i3 / bArr3.length);
        if (i4 * bArr3.length < i3) {
            a(i4, bArr4);
            this.b.a(bArr, i, i2);
            this.b.a(bArr4, 0, bArr4.length);
            this.b.m132a(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, i4 * bArr3.length, bArr2.length - (i4 * bArr3.length));
        }
        return bArr2;
    }

    public x() {
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [int[], int[][]] */
    public static int[][] a(a aVar, Graphics graphics, int i, int i2, int i3, int i4, Font font, int i5, String str, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, boolean z, int[] iArr, boolean z2, boolean z3, int i16, int i17, int i18, int i19, boolean z4, String str2, String str3) {
        int height = font.getHeight();
        ?? r0 = new int[3];
        int i20 = i5 + 1;
        a(graphics, i + 1, i2 + 1, (i3 - 2) - 2, i20, i7, -1, false, false, false, false, z4);
        int i21 = 0;
        if (1 != 0) {
            graphics.setColor(i16);
            graphics.drawRect(i + 2, (i2 + i4) - 2, i3 - 4, 0);
            graphics.drawRect((i + i3) - 2, i2 + 2, 0, i4 - 4);
            graphics.drawRect(i + 3, (i2 + i4) - 1, i3 - 6, 0);
            graphics.drawRect((i + i3) - 1, i2 + 3, 0, i4 - 6);
            graphics.drawRect((i + i3) - 4, (i2 + i4) - 4, 1, 1);
            i21 = 2;
        }
        a(graphics, i, i2, i3 - i21, i4 - i21, i9, i10, -1);
        graphics.setColor(i8);
        graphics.drawRect(i + 1, i2 + i20 + 1, i3 - 5, (i4 - i20) - 6);
        graphics.drawRect(i + 2, i2 + i20 + 2, 1, 1);
        graphics.drawRect((i + i3) - 6, i2 + i20 + 2, 1, 1);
        graphics.drawRect(i + 2, ((i2 + i4) - height) - 10, 1, 1);
        graphics.drawRect((i + i3) - 6, ((i2 + i4) - height) - 10, 1, 1);
        int i22 = 0;
        if (z) {
            i22 = height + 1;
            int i23 = i19 == 0 ? (((i + i3) - height) - 2) - 2 : i + 1;
            int i24 = i2 + 1;
            int i25 = iArr[i11] == -1 ? i15 : i14;
            int i26 = iArr[i11] == -1 ? i13 : i12;
            int i27 = i23;
            a(graphics, i27, i24, i22, i22, i25, -1, true, true, iArr[i11] == -1 ? z2 : false, false, z4);
            graphics.setColor(i26);
            graphics.drawLine(i27 + 3, i24 + 3, (i27 + i22) - 4, (i24 + i22) - 4);
            graphics.drawLine(i27 + 3, (i24 + i22) - 4, (i27 + i22) - 4, i24 + 3);
            r0[0] = new int[]{i27, i24, i22, i22};
        }
        if (i19 == 0) {
            if (str3 != null) {
                r0[2] = a(graphics, ((((i + i3) - height) - 3) - 2) - i22, i2 + 1, height + 1, height + 1, iArr[i11] == 11 ? i15 : i14, iArr[i11] == 11 ? i13 : i12, iArr[i11] == 11 ? z2 : false, "R", z4);
                i22 += height + 2;
            }
            if (str2 != null) {
                r0[1] = a(graphics, ((((i + i3) - height) - 3) - 2) - i22, i2 + 1, height + 1, height + 1, iArr[i11] == 10 ? i15 : i14, iArr[i11] == 10 ? i13 : i12, iArr[i11] == 10 ? z2 : false, "L", z4);
                i22 += height + 1;
            }
        } else {
            if (str2 != null) {
                r0[1] = a(graphics, i + i22 + 2, i2 + 1, height + 1, height + 1, iArr[i11] == 10 ? i15 : i14, iArr[i11] == 10 ? i13 : i12, iArr[i11] == 10 ? z2 : false, "L", z4);
                i22 += height + 2;
            }
            if (str3 != null) {
                r0[2] = a(graphics, i + i22 + 2, i2 + 1, height + 1, height + 1, iArr[i11] == 11 ? i15 : i14, iArr[i11] == 11 ? i13 : i12, iArr[i11] == 10 ? z2 : false, "R", z4);
                i22 += height + 1;
            }
        }
        a(aVar, str, font, height, graphics, i19 == 0 ? i + 1 : i + i22 + 2, i2 + 1, ((i3 - 3) - 2) - i22, i20, i6, -1, i17, i18, i19, i19, false, false, false, z4);
        graphics.setClip(i, i2, i3, i4);
        return r0;
    }

    public static void a(Graphics graphics, int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (z5) {
            graphics.setColor(i5);
            graphics.fillRect(i + 1, i2 + 1, i3 - 2, i4 - 2);
            graphics.setColor(b(i5, -20));
            graphics.drawRect(i, i2, i3 - 1, i4 - 1);
            return;
        }
        if (z4) {
            i = i2;
            i2 = i;
            i3 = i4;
            i4 = i3;
        }
        if (i6 <= 0) {
            i6 = 50;
        }
        int i7 = i6 << 4;
        int i8 = i7 / i4;
        int i9 = i7 / 2;
        int i10 = i;
        int i11 = i;
        int i12 = i3 - 1;
        int i13 = i12;
        int i14 = i12;
        if (z) {
            i10++;
            i13--;
            i11 += 2;
            i14 -= 2;
        }
        if (z2) {
            i13--;
            i14 -= 2;
        }
        for (int i15 = 0; i15 < i4; i15++) {
            int i16 = z3 ? ((i2 + i4) - i15) - 1 : i2 + i15;
            graphics.setColor(b(i5, i9 >> 4));
            if (z4) {
                if (i16 == i2 || i16 == (i2 + i4) - 1) {
                    graphics.drawRect(i16, i11, 0, i14);
                } else if (i16 == i2 + 1 || i16 == (i2 + i4) - 2) {
                    graphics.drawRect(i16, i10, 0, i13);
                } else {
                    graphics.drawRect(i16, i, 0, i3 - 1);
                }
            } else if (i16 == i2 || i16 == (i2 + i4) - 1) {
                graphics.drawRect(i11, i16, i14, 0);
            } else if (i16 == i2 + 1 || i16 == (i2 + i4) - 2) {
                graphics.drawRect(i10, i16, i13, 0);
            } else {
                graphics.drawRect(i, i16, i3 - 1, 0);
            }
            i9 -= i8;
        }
    }

    public static void a(a aVar, String str, Font font, int i, Graphics graphics, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, boolean z, boolean z2, boolean z3, boolean z4) {
        if (i7 > 0) {
            a(graphics, i2, i3, i4, i5, i7, -1, z, z2, z3, false, z4);
        }
        if (aVar != null) {
            int iA = aVar.a();
            int iB = aVar.b();
            if (i11 > 0 || i11 <= -2) {
                a.a(aVar, true, i8, graphics, ((i2 + i4) - iA) - 1, i3 + ((i5 - iB) >> 1), 20);
            } else {
                a.a(aVar, true, i8, graphics, i2, i3 + ((i5 - iB) >> 1), 20);
                i2 += iA;
            }
            i4 -= iA;
        }
        int i12 = i8 - i9;
        graphics.setColor(i6);
        int i13 = i3 + ((i5 - i) >> 1) + 1;
        int i14 = i4 - 2;
        if (i11 > 0 || i11 <= -2) {
            graphics.clipRect(i2 + 1, i13, i14 + 1, i5 - 2);
        } else {
            graphics.clipRect(i2, i13, i14 + 1, i5 - 1);
        }
        int iStringWidth = font.stringWidth(str);
        int i15 = i2 + 1;
        if (i11 > 0 || i11 < -1) {
            i15 = ((i2 + i4) - 1) - iStringWidth;
        }
        if (i10 < 0 && iStringWidth < i14) {
            i15 = (i11 > 0 || i11 <= -2) ? i15 - ((i14 - iStringWidth) / 2) : i15 + ((i14 - iStringWidth) / 2);
        }
        if (i12 > 0 && iStringWidth > i14) {
            int i16 = iStringWidth + (i14 / 2);
            int i17 = i12 / i16;
            if (i11 > 0 || i11 <= -2) {
                i15 += i12 - (i16 * i17);
                graphics.drawString(str, i15 - i16, i13, 20);
            } else {
                i15 -= i12 - (i16 * i17);
                graphics.drawString(str, i15 + i16, i13, 20);
            }
        }
        graphics.drawString(str, i15, i13, 20);
    }

    public static void a(Graphics graphics, int i, int i2, int i3, int i4, int i5, int i6, boolean z, int i7, a aVar, int i8, int i9) {
        if (z) {
            graphics.setColor(i7);
            graphics.fillRect(i + 1, i2 + 1, i3 - 2, i4 - 2);
        }
        a(graphics, i, i2, i3, i4, aVar, i8, i9);
        graphics.setColor(i5);
        graphics.drawRect((i + i3) - 1, i2 + 2, 0, i4 - 5);
        graphics.drawRect(i + 2, (i2 + i4) - 1, i3 - 5, 0);
        graphics.drawRect((i + i3) - 2, (i2 + i4) - 2, 0, 0);
        graphics.setColor(i6);
        graphics.drawRect(i + 2, i2, i3 - 5, 0);
        graphics.drawRect(i, i2 + 2, 0, i4 - 5);
        graphics.drawRect(i + 1, i2 + 1, 0, 0);
        graphics.setColor(a(i5, i6));
        graphics.drawRect(i + 1, (i2 + i4) - 2, 0, 0);
        graphics.drawRect((i + i3) - 2, i2 + 1, 0, 0);
    }

    public static void a(Graphics graphics, int i, int i2, int i3, int i4, a aVar, int i5, int i6) {
        if (aVar == null || aVar.m1a()) {
            return;
        }
        int iA = aVar.a();
        int iB = aVar.b();
        graphics.setClip(i, i2, i3, i4);
        switch (i5) {
            case 0:
                break;
            case 1:
                try {
                    int i7 = i;
                    int i8 = i2;
                    int i9 = i4 + i2;
                    int i10 = i3 + i;
                    while (i8 < i9) {
                        while (i7 < i10) {
                            a.a(aVar, true, i6, graphics, i7, i8, 20);
                            i7 += iA;
                        }
                        i8 += iB;
                        i7 = i;
                    }
                    break;
                } catch (Exception unused) {
                    return;
                }
            case 2:
                int i11 = i4 + i2;
                for (int i12 = i2; i12 < i11; i12 += iB) {
                    a.a(aVar, true, i6, graphics, i, i12, 20);
                }
                break;
            case 3:
                int i13 = i3 + i;
                for (int i14 = i; i14 < i13; i14 += iA) {
                    a.a(aVar, true, i6, graphics, i14, i2, 20);
                }
                break;
            case 4:
            default:
                a.a(aVar, true, i6, graphics, i + ((i3 - iA) >> 1), i2 + ((i4 - iB) >> 1), 20);
                break;
            case 5:
                a.a(aVar, true, i6, graphics, i, i2, 20);
                break;
            case 6:
                a.a(aVar, true, i6, graphics, i + ((i3 - iA) >> 1), i2, 20);
                break;
            case 7:
                a.a(aVar, true, i6, graphics, (i + i3) - iA, i2, 20);
                break;
            case 8:
                a.a(aVar, true, i6, graphics, (i + i3) - iA, i2 + ((i4 - iB) >> 1), 20);
                break;
            case 9:
                a.a(aVar, true, i6, graphics, (i + i3) - iA, (i2 + i4) - iB, 20);
                break;
            case 10:
                a.a(aVar, true, i6, graphics, i + ((i3 - iA) >> 1), (i2 + i4) - iB, 20);
                break;
            case 11:
                a.a(aVar, true, i6, graphics, i, (i2 + i4) - iB, 20);
                break;
            case 12:
                a.a(aVar, true, i6, graphics, i, i2 + ((i4 - iB) >> 1), 20);
                break;
        }
    }

    public static void a(Graphics graphics, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        if (i7 >= 0) {
            graphics.setColor(i7);
            graphics.fillRect(i + 1, i2 + 1, i3 - 2, i4 - 2);
        }
        graphics.setColor(i6);
        graphics.drawRect(i + 2, (i2 + i4) - 1, i3 - 5, 0);
        graphics.drawRect((i + i3) - 1, i2 + 2, 0, i4 - 5);
        graphics.drawRect((i + i3) - 2, (i2 + i4) - 2, 0, 0);
        graphics.setColor(i5);
        graphics.drawRect(i + 2, i2, i3 - 5, 0);
        graphics.drawRect(i, i2 + 2, 0, i4 - 5);
        graphics.drawRect(i + 1, i2 + 1, 0, 0);
        graphics.setColor(a(i5, i6));
        graphics.drawRect(i + 1, (i2 + i4) - 2, 0, 0);
        graphics.drawRect((i + i3) - 2, i2 + 1, 0, 0);
    }

    public static int[] a(Graphics graphics, int i, int i2, int i3, int i4, int i5, int i6, String str, Font font, int i7, int i8, int i9, boolean z, boolean z2) {
        a((a) null, str, font, font.getHeight(), graphics, i, i2, i3, i4, i6, i5, i7, 20, -1, i9, true, true, z, z2);
        return new int[]{i, i2, i3, i4};
    }

    public static int[] a(Graphics graphics, int i, int i2, int i3, int i4, int i5, int i6, boolean z, String str, boolean z2) {
        a(graphics, i, i2, i3, i4, i5, -1, true, true, z, false, z2);
        graphics.setColor(i6);
        graphics.drawString(str, i + (i3 / 2), i2, 17);
        return new int[]{i, i2, i3, i4};
    }

    public static boolean a(Vector vector, int[] iArr, int[] iArr2, Font font, int i, Graphics graphics, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int[] iArr3, int i12, int i13) {
        boolean z = false;
        graphics.setClip(i2, i3, i4, i5);
        int i14 = 0;
        for (int i15 = 0; i15 < i9; i15++) {
            i14 += iArr2[i15];
        }
        int i16 = iArr3[0] - i14;
        int i17 = i12 * 3;
        if (i16 > i || i16 < (-i)) {
            i17 = i * i12;
        }
        if (i16 != 0) {
            if (i16 > i17 - 1 || i16 < (-i17) + 1) {
                if (i16 > 0) {
                    iArr3[0] = iArr3[0] - i17;
                } else {
                    iArr3[0] = iArr3[0] + i17;
                }
                z = true;
            } else {
                iArr3[0] = i14;
                z = false;
            }
        }
        int i18 = 0;
        int size = 0;
        int i19 = 0;
        int i20 = 0;
        for (int i21 = 0; i21 < iArr2.length; i21++) {
            i19 += iArr2[i21];
            if (iArr3[0] > i19) {
                i18 = i21;
                i20 = i19 - iArr2[i21];
            }
            if (i5 + iArr3[0] <= i19) {
                break;
            }
            size = i21 + 2;
        }
        if (size > vector.size()) {
            size = vector.size();
        }
        graphics.setColor(i6);
        int i22 = i20 + i3;
        for (int i23 = i18; i23 < size; i23++) {
            int i24 = i22 - iArr3[0];
            int i25 = iArr2[i23];
            i22 += i25;
            Object[] objArr = (Object[]) vector.elementAt(i23);
            int iA = i2 + 1;
            if (i13 < 0) {
                iA += (i4 - iArr[i23]) / 2;
            } else if (i13 > 0) {
                iA += i4 - iArr[i23];
            }
            for (int i26 = 0; i26 < objArr.length; i26++) {
                Object obj = objArr[i26];
                if (obj instanceof String) {
                    String str = (String) obj;
                    graphics.drawString(str, iA, i24 + ((i25 - i) >> 1), 20);
                    iA += font.stringWidth(str);
                } else if (obj instanceof a) {
                    a aVar = (a) obj;
                    int i27 = iA + 1;
                    a.a(aVar, true, i8 + i23 + i26, graphics, i27, i24 + ((i25 - aVar.b()) >> 1), 20);
                    iA = i27 + aVar.a() + 1;
                }
            }
        }
        if (i11 > i5) {
            a(graphics, i2 + i4, i3, 1, i5, i, i10, i9, i7);
        }
        return z;
    }

    public static boolean a(String[][] strArr, a[] aVarArr, boolean[] zArr, int i, int i2, int[] iArr, Font font, int i3, Graphics graphics, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19, int i20, int i21, int i22, int i23, int i24, int i25, int[] iArr2, int i26, int i27, a aVar, int i28, boolean z) {
        int iA;
        int iA2;
        int iStringWidth;
        boolean z2 = false;
        if (i <= 0) {
            return false;
        }
        int i29 = iArr2[0];
        int i30 = 0;
        int i31 = 0;
        while (i31 < i2) {
            i30 += iArr[i31];
            i31++;
        }
        int i32 = i30;
        while (i31 < iArr.length) {
            i32 += iArr[i31];
            i31++;
        }
        if (i29 > 0 && i32 < (i7 + i29) - 2) {
            i29 = ((-i7) - i29) + 2 + i32;
        }
        int i33 = i30 + iArr[i2];
        if (i30 < i29) {
            i29 = i30;
        }
        int i34 = i33 > (i7 + i29) - 2 ? (i33 - i7) + 2 : 0;
        if (iArr2[0] < i34) {
            i29 = i34;
        }
        int i35 = iArr2[0] - i29;
        int i36 = i26 * 5;
        if (i35 != 0) {
            if (i35 > i36 - 1 || i35 < (-i36) + 1) {
                if (i35 > 0) {
                    iArr2[0] = iArr2[0] - i36;
                } else {
                    iArr2[0] = iArr2[0] + i36;
                }
                z2 = true;
            } else {
                iArr2[0] = i29;
                z2 = false;
            }
        }
        int i37 = i4 + i8 + i9;
        int i38 = i4 + 1;
        int i39 = i6 - 1;
        int i40 = i8 - 2;
        int i41 = i9 - 2;
        int i42 = i38 + i8;
        int i43 = i10 - 2;
        int i44 = 0;
        int i45 = 0;
        int i46 = 0;
        int i47 = 0;
        for (int i48 = 0; i48 < i; i48++) {
            i46 += iArr[i48];
            if (iArr2[0] > i46) {
                i44 = i48;
                i47 = i46 - iArr[i48];
            }
            if ((i7 - 1) + iArr2[0] <= i46) {
                break;
            }
            i45 = i48 + 2;
        }
        if (i45 > i) {
            i45 = i;
        }
        graphics.setColor(i12);
        graphics.fillRect(i4, i5, i6, i7);
        if (i16 > 0) {
            graphics.setClip(i4, i5, i6, i7);
            graphics.setColor(i16);
            if (i27 > 0 || i27 <= -2) {
                graphics.fillRect((((i4 + i6) + 1) - i8) - i9, i5, i9, i7);
            } else {
                graphics.fillRect(i4 + i8, i5, i9, i7);
            }
            if (i20 > 0) {
                graphics.setColor(i20);
                if (i27 > 0 || i27 <= -2) {
                    graphics.fillRect(i4, i5 - iArr2[0], i10, i7);
                } else {
                    graphics.fillRect(i37, i5 - iArr2[0], i10, i7);
                }
            }
        }
        a(graphics, i4, i5, i6, i7, aVar, i28, i24);
        int i49 = i5 + 1;
        int i50 = i47 + i49;
        for (int i51 = i44; i51 < i45; i51++) {
            int i52 = i50 - iArr2[0];
            int i53 = iArr[i51];
            i50 += i53;
            int i54 = i52 + ((i53 - i3) >> 1) + 1;
            graphics.setClip(i38, i49, i39, i7 - 2);
            if (i27 > 0 || i27 <= -2) {
                if (i2 == i51) {
                    int i55 = ((i4 + i6) + 1) - i8;
                    a(aVarArr[i51], strArr[i51][1], font, i3, graphics, i55, i52, i8 - 1, i53, i13, i14, i24, 10, i27, i27, i9 <= 0, true, false, z);
                    if (i9 > 0) {
                        int i56 = i55 - i9;
                        graphics.setClip(i38, i49, i39, i7);
                        a((a) null, strArr[i51][2], font, i3, graphics, i56, i52, i9, i53, i17, i18, i24, 10, -1, i27, i10 <= 0, false, false, z);
                        if (i10 > 0) {
                            graphics.setClip(i38, i49, i39, i7);
                            a((a) null, strArr[i51][3], font, i3, graphics, i38, i52, i10, i53, i21, i22, i24, 10, -1, i27, true, false, false, z);
                        }
                    }
                } else {
                    if (aVarArr[i51] != null) {
                        iA = aVarArr[i51].a();
                        a.a(aVarArr[i51], false, i24 + i51, graphics, ((i4 + i6) - iA) - 1, i52 + ((i53 - aVarArr[i51].b()) >> 1), 20);
                    } else {
                        iA = 0;
                    }
                    int iStringWidth2 = font.stringWidth(strArr[i51][1]);
                    if (i27 < 0 && iStringWidth2 < i40 - iA) {
                        iStringWidth2 += (((i40 - iA) - 1) - iStringWidth2) / 2;
                    }
                    int i57 = (i4 + i6) - i8;
                    graphics.setColor(i11);
                    graphics.clipRect((i57 + 2) - iA, i54, i8, i53);
                    graphics.drawString(strArr[i51][1], (((i4 + i6) - iA) - iStringWidth2) - 1, i54, 20);
                    if (i9 > 0) {
                        int iStringWidth3 = font.stringWidth(strArr[i51][2]);
                        int i58 = iStringWidth3;
                        if (iStringWidth3 < i41) {
                            i58 += (i41 - i58) / 2;
                        }
                        int i59 = i57 - i9;
                        graphics.setColor(i15);
                        graphics.setClip(i59 + 2, i49, i9, i7);
                        graphics.drawString(strArr[i51][2], i57 - i58, i54, 20);
                        if (i10 > 0) {
                            int iStringWidth4 = font.stringWidth(strArr[i51][3]);
                            int i60 = iStringWidth4;
                            if (iStringWidth4 < i43) {
                                i60 += (i43 - i60) / 2;
                            }
                            graphics.setColor(i19);
                            graphics.setClip(i4 + 2, i49, i10, i7);
                            graphics.drawString(strArr[i51][3], i59 - i60, i54, 20);
                        }
                    }
                }
            } else if (i2 == i51) {
                a(aVarArr[i51], strArr[i51][1], font, i3, graphics, i38, i52, i8 - 1, i53, i13, i14, i24, 10, i27, i27, true, i9 <= 0, false, z);
                if (i9 > 0) {
                    graphics.setClip(i38, i49, i39, i7 - 2);
                    a((a) null, strArr[i51][2], font, i3, graphics, i4 + i8, i52, i9, i53, i17, i18, i24, 10, -1, i27, false, i10 <= 0, false, z);
                    if (i10 > 0) {
                        graphics.setClip(i38, i49, i39, i7 - 2);
                        a((a) null, strArr[i51][3], font, i3, graphics, i37, i52, i10, i53, i21, i22, i24, 10, -1, i27, false, true, false, z);
                    }
                }
            } else {
                if (aVarArr[i51] != null) {
                    a.a(aVarArr[i51], false, i24 + i51, graphics, i38, i52 + ((i53 - aVarArr[i51].b()) >> 1), 20);
                    iA2 = aVarArr[i51].a();
                } else {
                    iA2 = 0;
                }
                if (i27 < 0 && (iStringWidth = font.stringWidth(strArr[i51][1])) < i40 - iA2) {
                    iA2 += (((i40 - 1) - iStringWidth) - iA2) / 2;
                }
                graphics.setColor(i11);
                graphics.clipRect(i38, i54, i40, i53);
                graphics.drawString(strArr[i51][1], i38 + iA2 + 1, i54, 20);
                if (i9 > 0) {
                    int iStringWidth5 = font.stringWidth(strArr[i51][2]);
                    int i61 = iStringWidth5 < i41 ? (i41 - iStringWidth5) / 2 : 0;
                    graphics.setColor(i15);
                    graphics.setClip(i38, i49, (i39 - i10) - 1, i7);
                    graphics.drawString(strArr[i51][2], i42 + i61, i54, 20);
                    if (i10 > 0) {
                        int iStringWidth6 = font.stringWidth(strArr[i51][3]);
                        int i62 = iStringWidth6 < i43 ? (i43 - iStringWidth6) / 2 : 0;
                        graphics.setColor(i19);
                        graphics.setClip(i38, i49, i39 - 1, i7);
                        graphics.drawString(strArr[i51][3], i42 + i9 + i62, i54, 20);
                    }
                }
            }
            if (zArr[i51]) {
                graphics.setColor(i23);
                graphics.setClip(i38, i49, i39, i7);
                graphics.drawRect(i38, i52 - 1, i39, 0);
            }
        }
        return z2;
    }

    /* JADX WARN: Type inference failed for: r0v23, types: [int[], int[][]] */
    public static int[][] a(int i, int i2, int i3, int[] iArr, String str, String str2, Font font, Graphics graphics, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, boolean z, boolean z2) {
        int[] iArrA;
        int i17;
        int[] iArrA2 = null;
        int height = font.getHeight();
        boolean z3 = false;
        int iStringWidth = font.stringWidth(str) + 4;
        int iStringWidth2 = font.stringWidth(str2) + 4;
        if (iStringWidth2 > iStringWidth) {
            iStringWidth = iStringWidth2;
        }
        int i18 = height + 3;
        int i19 = (i5 - i18) - 2;
        if (i != 0) {
            if (i13 >= 0) {
                graphics.setColor(i13);
                graphics.fillRect(i4, i19 - 1, i6, i18 + 2);
            }
            if (i2 == 0) {
                if (iStringWidth > i6) {
                    iStringWidth = i6;
                }
                i17 = i4 + ((i6 - iStringWidth) / 2);
            } else {
                int i20 = (i6 - 2) / 2;
                if (iStringWidth > i20) {
                    iStringWidth = i20;
                }
                i17 = i4 + ((i20 - iStringWidth) / 2);
                int i21 = i4 + ((i20 - iStringWidth) / 2) + 2 + i20;
                int i22 = i7;
                int i23 = i9;
                if (iArr[i3] == 2) {
                    i22 = i8;
                    i23 = i10;
                    if (z) {
                        z3 = true;
                    }
                }
                iArrA2 = a(graphics, i21, i19, iStringWidth, i18, i22, i23, str2, font, iArr[i3] == 2 ? i15 : 0, 20, i16, z3, z2);
            }
            graphics.setClip(i4, i19, i6, i18);
            int i24 = i7;
            int i25 = i9;
            boolean z4 = false;
            if (iArr[i3] == 1) {
                i24 = i8;
                i25 = i10;
                if (z) {
                    z4 = true;
                }
            }
            iArrA = a(graphics, i17, i19, iStringWidth, i18, i24, i25, str, font, iArr[i3] == 1 ? i15 : 0, 20, i16, z4, z2);
        } else {
            iArrA = new int[]{i4, i5, 0, 0};
            iArrA2 = new int[]{i4, i5, 0, 0};
        }
        return new int[]{iArrA, iArrA2};
    }

    public static int a(int i, int i2) {
        return ((((i & 16711680) + (i2 & 16711680)) >> 1) & 16711680) | ((((i & 65280) + (i2 & 65280)) >> 1) & 65280) | (((i & 255) + (i2 & 255)) >> 1);
    }

    public static int b(int i, int i2) {
        int i3 = (i >> 8) & 255;
        int i4 = i & 255;
        int i5 = (((i >> 16) & 255) * (i2 + 100)) / 100;
        int i6 = i5;
        if (i5 > 255) {
            i6 = 255;
        }
        if (i6 < 0) {
            i6 = 0;
        }
        int i7 = (i3 * (i2 + 100)) / 100;
        int i8 = i7;
        if (i7 > 255) {
            i8 = 255;
        }
        if (i8 < 0) {
            i8 = 0;
        }
        int i9 = (i4 * (i2 + 100)) / 100;
        int i10 = i9;
        if (i9 > 255) {
            i10 = 255;
        }
        if (i10 < 0) {
            i10 = 0;
        }
        return (i6 << 16) | (i8 << 8) | i10;
    }

    public static int a(Font font, int i, String str, String str2) {
        int i2;
        int iStringWidth = font.stringWidth(str);
        if (i != 0) {
            int iStringWidth2 = font.stringWidth(str2);
            if (iStringWidth2 > iStringWidth) {
                iStringWidth = iStringWidth2;
            }
            i2 = iStringWidth + iStringWidth + 16;
        } else {
            i2 = iStringWidth + 10;
        }
        return i2;
    }

    public static void a(Graphics graphics, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (i6 > 1) {
            graphics.setClip(i, i2, 1, i4);
            graphics.setColor(i8);
            graphics.fillRect(i, i2 + (((i4 - i5) * i7) / (i6 - 1)), 1, i5);
        }
    }

    public static int[] a(String str) {
        int[] iArr = new int[4];
        iArr[3] = Integer.parseInt(str, 16);
        iArr[2] = b(iArr[3], -12);
        iArr[1] = b(iArr[2], -12);
        iArr[0] = b(iArr[1], -12);
        return iArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0209  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0222 A[SYNTHETIC] */
    /* renamed from: a, reason: collision with other method in class */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Object[] m133a(java.lang.String r6) {
        /*
            Method dump skipped, instructions count: 586
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.x.m133a(java.lang.String):java.lang.Object[]");
    }

    public static boolean a(Graphics graphics, int i, int i2, int i3, int i4, a aVar, int i5, int i6, int[] iArr, int i7, int[] iArr2, Font font, int i8, int i9, int i10, int i11, int i12, Vector vector, int i13, int i14, int i15, int i16) {
        int i17 = (i8 << 1) / 3;
        graphics.setClip(i, i2, i3, i4);
        a(graphics, i, i2, i3, i4, aVar, i5, i15);
        int i18 = 0;
        for (int i19 = 0; i19 < i11; i19++) {
            i18 += ((int[]) ((Object[]) vector.elementAt(i19))[3])[1];
        }
        int i20 = 0;
        for (int i21 = i11; i21 < i11 + i6; i21++) {
            if (i21 < vector.size()) {
                i20 += ((int[]) ((Object[]) vector.elementAt(i21))[3])[1];
            }
        }
        if (i18 < iArr[0]) {
            iArr2[0] = i18;
        } else if (i18 + i20 > iArr[0] + i4) {
            iArr2[0] = (i18 + i20) - i4;
        }
        if (i4 <= i20) {
            iArr2[0] = i18;
        }
        if (iArr2[0] < 0) {
            iArr2[0] = 0;
        }
        if (i12 - iArr2[0] < i4 && i12 > i4) {
            iArr2[0] = i12 - i4;
        }
        int i22 = iArr[0] - iArr2[0];
        if (i7 == 1) {
            i22 /= 2;
        }
        iArr[0] = iArr[0] - i22;
        boolean z = iArr[0] != iArr2[0];
        int i23 = 0;
        int i24 = 0;
        int i25 = 0;
        int i26 = 0;
        for (int i27 = 0; i27 < vector.size(); i27++) {
            int i28 = ((int[]) ((Object[]) vector.elementAt(i27))[3])[1];
            i25 += i28;
            if (iArr[0] > i25) {
                i23 = i27;
                i26 = i25 - i28;
            }
            if (i4 + iArr[0] <= i25) {
                break;
            }
            i24 = i27 + 2;
        }
        if (i24 > i10) {
            i24 = i10;
        }
        a(graphics, i, (((i2 + i18) - iArr[0]) + i17) - 2, i3 - 3, (i20 - i17) + 10, i14 + 1052688, i14 - 1052688, i14);
        int i29 = i26 + i2;
        for (int i30 = i23; i30 < i24; i30++) {
            int i31 = i29 - iArr[0];
            try {
                Object[] objArr = (Object[]) vector.elementAt(i30);
                int i32 = ((int[]) objArr[3])[1];
                i29 += i32;
                if (i16 > 0 || i16 <= -2) {
                    int i33 = (i + i3) - 1;
                    if (i16 < 0) {
                        int iStringWidth = ((int[]) objArr[3])[0];
                        if (objArr[4] != null) {
                            iStringWidth += font.stringWidth((String) objArr[4]) + 1;
                            a aVar2 = (a) objArr[5];
                            if (aVar2 != null) {
                                iStringWidth += aVar2.a() + 1;
                            }
                        }
                        i33 -= (i3 - iStringWidth) / 2;
                    }
                    if (objArr[4] != null) {
                        a aVar3 = (a) objArr[5];
                        if (aVar3 != null) {
                            int iA = i33 - aVar3.a();
                            a.a(aVar3, true, i15 + i30, graphics, iA, i31 + (((i32 - i17) - aVar3.b()) >> 1) + i17, 20);
                            i33 = iA - 1;
                        }
                        String str = (String) objArr[4];
                        int iStringWidth2 = i33 - font.stringWidth(str);
                        graphics.setColor(((int[]) objArr[6])[i9 == 0 ? (char) 0 : (char) 1]);
                        graphics.drawString(str, iStringWidth2, i31 + (((i32 - i17) - i8) >> 1) + i17, 20);
                        a(null, (Object[]) objArr[2], (int[]) objArr[1], null, i9, false, font, i8, graphics, (iStringWidth2 - 1) - ((int[]) objArr[3])[0], i31 + i17, i32 - i17, i30, i15, i16);
                    } else {
                        a(null, (Object[]) objArr[2], (int[]) objArr[1], null, i9, false, font, i8, graphics, i33 - ((int[]) objArr[3])[0], i31, i32, i30, i15, i16);
                    }
                } else {
                    int iA2 = i + 1;
                    if (i16 < 0) {
                        int iStringWidth3 = ((int[]) objArr[3])[0];
                        if (objArr[4] != null) {
                            iStringWidth3 += font.stringWidth((String) objArr[4]) + 1;
                            a aVar4 = (a) objArr[5];
                            if (aVar4 != null) {
                                iStringWidth3 += aVar4.a() + 1;
                            }
                        }
                        iA2 += (i3 - iStringWidth3) / 2;
                    }
                    if (objArr[4] != null) {
                        a aVar5 = (a) objArr[5];
                        if (aVar5 != null) {
                            a.a(aVar5, true, i15 + i30, graphics, iA2, i31 + (((i32 - i17) - aVar5.b()) >> 1) + i17, 20);
                            iA2 += aVar5.a() + 1;
                        }
                        String str2 = (String) objArr[4];
                        graphics.setColor(((int[]) objArr[6])[i9 == 0 ? (char) 0 : (char) 1]);
                        graphics.drawString(str2, iA2, i31 + (((i32 - i17) - i8) >> 1) + i17, 20);
                        a(null, (Object[]) objArr[2], (int[]) objArr[1], null, i9, false, font, i8, graphics, iA2 + font.stringWidth(str2) + 1, i31 + i17, i32 - i17, i30, i15, i16);
                    } else {
                        a(null, (Object[]) objArr[2], (int[]) objArr[1], null, i9, false, font, i8, graphics, iA2, i31, i32, i30, i15, i16);
                    }
                }
            } catch (Exception e) {
            }
        }
        if (i12 > i4) {
            a(graphics, i + i3, i2, 1, i4, i8, i10, i11, i13);
        }
        return z;
    }

    public static void a(a aVar, Object[] objArr, int[] iArr, int[] iArr2, int i, boolean z, Font font, int i2, Graphics graphics, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (objArr != null) {
            if (aVar != null && i8 <= 0) {
                int iA = aVar.a() + 1;
                if (z) {
                    graphics.setColor(iArr2[i]);
                    graphics.fillRect(i3, i4, iA, i5);
                }
                a.a(aVar, true, i7 + i6, graphics, i3, i4 + ((i5 - aVar.b()) >> 1), 20);
                i3 += iA;
            }
            graphics.setColor(iArr[i]);
            for (int i9 = 0; i9 < objArr.length; i9++) {
                Object obj = objArr[i9];
                if (obj instanceof String) {
                    String str = (String) obj;
                    int iStringWidth = font.stringWidth(str);
                    if (z) {
                        graphics.setColor(iArr2[i]);
                        graphics.fillRect(i3, i4, iStringWidth, i5);
                        graphics.setColor(iArr[i]);
                    }
                    graphics.drawString(str, i3, i4 + ((i5 - i2) >> 1), 20);
                    i3 += iStringWidth;
                } else if (obj instanceof a) {
                    int i10 = i7 + i6 + i9;
                    a aVar2 = (a) obj;
                    int iA2 = aVar2.a() + 2;
                    if (z) {
                        graphics.setColor(iArr2[i]);
                        graphics.fillRect(i3, i4, iA2, i5);
                    }
                    a.a(aVar2, true, i10, graphics, i3 + 1, i4 + ((i5 - aVar2.b()) >> 1), 20);
                    i3 += iA2;
                }
            }
            if (aVar == null || i8 <= 0) {
                return;
            }
            int iA3 = aVar.a() + 1;
            if (z) {
                graphics.setColor(iArr2[i]);
                graphics.fillRect(i3, i4, iA3, i5);
            }
            a.a(aVar, true, i7 + i6, graphics, i3, i4 + ((i5 - aVar.b()) >> 1), 20);
        }
    }

    public static int a(Vector vector, int i) {
        int i2 = 0;
        int size = vector.size();
        do {
            i2++;
            i++;
            if (i >= size) {
                break;
            }
        } while (((Object[]) vector.elementAt(i))[4] == null);
        return i2;
    }

    public static int a(String[] strArr, Vector vector, int i, int i2, char c, char c2, Font font, int i3, boolean z) {
        int[] iArrA;
        int length = strArr.length;
        if (z) {
            vector.removeAllElements();
        }
        String str = "";
        String str2 = "";
        for (int i4 = 1; i4 < length; i4++) {
            try {
                String[] strArrA = b.a(strArr[i4], c);
                if (strArrA.length >= 5) {
                    int[] iArrA2 = a(strArrA[1]);
                    strArrA[3] = b.b(strArrA[3], c2);
                    strArrA[4] = b.b(strArrA[4], c2);
                    if (strArrA[3].compareTo(str) != 0 || strArrA[4].compareTo(str2) != 0) {
                        str = strArrA[3];
                        str2 = strArrA[4];
                        a aVarA = a.a(strArrA[2]);
                        boolean z2 = false;
                        if (strArrA.length >= 6) {
                            iArrA = a(strArrA[5]);
                            if (Integer.parseInt(strArrA[6]) == 1 && c.f) {
                                z2 = true;
                            }
                        } else {
                            iArrA = a("0");
                        }
                        Object[][] objArrA = a(m133a(strArrA[4]), (aVarA != null ? 0 + aVarA.a() : 0) + font.stringWidth(strArrA[3]), i2 - 4, font, z2);
                        for (int i5 = 0; i5 < objArrA.length; i5++) {
                            Object[] objArr = new Object[7];
                            objArr[0] = strArrA[0];
                            objArr[1] = iArrA2;
                            objArr[2] = objArrA[i5];
                            objArr[3] = a(objArr[2], font, i3);
                            if (i5 == 0) {
                                if (aVarA != null && aVarA.b() > ((int[]) objArr[3])[1]) {
                                    ((int[]) objArr[3])[1] = aVarA.b();
                                }
                                ((int[]) objArr[3])[1] = ((int[]) objArr[3])[1] + ((i3 << 1) / 3);
                                objArr[4] = strArrA[3];
                                objArr[5] = aVarA;
                                objArr[6] = iArrA;
                            }
                            vector.addElement(objArr);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }
        int size = vector.size();
        if (size > 50) {
            int i6 = size - 50;
            for (int i7 = 0; i7 < i6; i7++) {
                vector.removeElementAt(0);
            }
            i -= i6;
        }
        if (i < 0) {
            i = 0;
        }
        if (i >= vector.size() || z) {
            i = vector.size() - 1;
        }
        if (vector != null && vector.size() > 0) {
            i++;
            while (true) {
                i--;
                if (i <= 0) {
                    i = 0;
                    break;
                }
                if (((Object[]) vector.elementAt(i))[4] != null) {
                    break;
                }
            }
        }
        return i;
    }

    public static int[] a(Object obj, Font font, int i) {
        a aVar;
        int[] iArr = {0, i};
        if (obj != null) {
            for (Object obj2 : (Object[]) obj) {
                if (obj2 instanceof String) {
                    iArr[0] = iArr[0] + font.stringWidth((String) obj2);
                    if (iArr[1] < i) {
                        iArr[1] = i;
                    }
                } else if ((obj2 instanceof a) && (aVar = (a) obj2) != null) {
                    iArr[0] = iArr[0] + aVar.a() + 2;
                    if (iArr[1] < aVar.b()) {
                        iArr[1] = aVar.b();
                    }
                }
            }
        }
        return iArr;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.lang.Object[], java.lang.Object[][]] */
    public static Object[][] a(Object[] objArr, int i, int i2, Font font, boolean z) {
        Vector vector = new Vector(8, 4);
        int i3 = i;
        Vector vector2 = null;
        boolean z2 = true;
        int i4 = 0;
        while (i4 < objArr.length) {
            if (z2) {
                if (vector2 != null) {
                    int size = vector2.size();
                    Object[] objArr2 = new Object[size];
                    if (z) {
                        for (int i5 = 1; i5 <= size; i5++) {
                            objArr2[size - i5] = vector2.elementAt(i5 - 1);
                        }
                    } else {
                        vector2.copyInto(objArr2);
                    }
                    vector.addElement(objArr2);
                    i3 = 0;
                }
                vector2 = new Vector(6, 7);
                z2 = false;
            } else {
                Object obj = objArr[i4];
                if (obj instanceof String) {
                    int iStringWidth = font.stringWidth((String) obj);
                    if (i3 + iStringWidth <= i2) {
                        vector2.addElement(obj);
                        i3 += iStringWidth;
                        i4++;
                    } else if (iStringWidth > (i2 << 1) / 3) {
                        int i6 = i2 - i3;
                        String str = (String) obj;
                        String string = "";
                        int i7 = 0;
                        while (true) {
                            if (i7 >= str.length()) {
                                break;
                            }
                            string = new StringBuffer().append(string).append(str.charAt(i7)).toString();
                            if (font.stringWidth(string) > i6) {
                                vector2.addElement(string.substring(0, string.length() - 1));
                                objArr[i4] = str.substring(string.length() - 1);
                                break;
                            }
                            i7++;
                        }
                        z2 = true;
                    } else {
                        z2 = true;
                    }
                } else if (obj instanceof a) {
                    int iA = ((a) obj).a();
                    if (i3 + iA + 1 <= i2) {
                        vector2.addElement(obj);
                        i3 += iA + 2;
                        i4++;
                    } else {
                        z2 = true;
                    }
                } else if (obj instanceof Integer) {
                    z2 = true;
                    i4++;
                } else {
                    i4++;
                }
            }
        }
        if (vector2 != null) {
            int size2 = vector2.size();
            Object[] objArr3 = new Object[size2];
            if (z) {
                for (int i8 = 1; i8 <= size2; i8++) {
                    objArr3[size2 - i8] = vector2.elementAt(i8 - 1);
                }
            } else {
                vector2.copyInto(objArr3);
            }
            vector.addElement(objArr3);
        }
        ?? r0 = new Object[vector.size()];
        vector.copyInto(r0);
        return r0;
    }
}
