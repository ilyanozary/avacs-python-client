package defpackage;

import java.util.Stack;
import javax.microedition.io.HttpConnection;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:s.class */
public final class s {

    /* renamed from: a, reason: collision with other field name */
    private static int[] f199a;

    /* renamed from: a, reason: collision with other field name */
    private int f202a;

    /* renamed from: c, reason: collision with other field name */
    private int[] f203c;

    /* renamed from: b, reason: collision with other field name */
    private int f204b;

    /* renamed from: c, reason: collision with other field name */
    private int f205c;

    /* renamed from: a, reason: collision with other field name */
    private long f206a;

    /* renamed from: a, reason: collision with other field name */
    private static final byte[] f207a;
    private static final int[][] a = {new int[]{3, 5, 7, 11, 13, 17, 19, 23}, new int[]{29, 31, 37, 41, 43}, new int[]{47, 53, 59, 61, 67}, new int[]{71, 73, 79, 83}, new int[]{89, 97, Opcodes.LSUB, Opcodes.DSUB}, new int[]{Opcodes.DMUL, Opcodes.LDIV, Opcodes.LREM, Opcodes.LAND}, new int[]{Opcodes.LXOR, Opcodes.L2F, Opcodes.F2I, Opcodes.FCMPL}, new int[]{Opcodes.DCMPL, Opcodes.IFGT, Opcodes.IF_ICMPGT, Opcodes.GOTO}, new int[]{Opcodes.LRETURN, Opcodes.PUTSTATIC, Opcodes.PUTFIELD, Opcodes.ATHROW}, new int[]{Opcodes.INSTANCEOF, Opcodes.MULTIANEWARRAY, Opcodes.IFNONNULL, 211}, new int[]{223, 227, 229}, new int[]{233, 239, 241}, new int[]{251, 257, 263}, new int[]{269, 271, 277}, new int[]{281, 283, 293}, new int[]{HttpConnection.HTTP_TEMP_REDIRECT, 311, 313}, new int[]{317, 331, 337}, new int[]{347, 349, 353}, new int[]{359, 367, 373}, new int[]{379, 383, 389}, new int[]{397, HttpConnection.HTTP_UNAUTHORIZED, HttpConnection.HTTP_CONFLICT}, new int[]{419, 421, 431}, new int[]{433, 439, 443}, new int[]{449, 457, 461}, new int[]{463, 467, 479}, new int[]{487, 491, 499}, new int[]{HttpConnection.HTTP_UNAVAILABLE, 509, 521}, new int[]{523, 541, 547}, new int[]{557, 563, 569}, new int[]{571, 577, 587}, new int[]{593, 599, 601}, new int[]{607, 613, 617}, new int[]{619, 631, 641}, new int[]{643, 647, 653}, new int[]{659, 661, 673}, new int[]{677, 683, 691}, new int[]{701, 709, 719}, new int[]{727, 733, 739}, new int[]{743, 751, 757}, new int[]{761, 769, 773}, new int[]{787, 797, 809}, new int[]{811, 821, 823}, new int[]{827, 829, 839}, new int[]{853, 857, 859}, new int[]{863, 877, 881}, new int[]{883, 887, 907}, new int[]{911, 919, 929}, new int[]{937, 941, 947}, new int[]{953, 967, 971}, new int[]{977, 983, 991}, new int[]{997, 1009, 1013}, new int[]{1019, 1021, 1031}};
    private static final int[] b = new int[0];

    /* renamed from: a, reason: collision with other field name */
    private static s f200a = new s(0, b);

    /* renamed from: b, reason: collision with other field name */
    private static s f201b = a(1L);
    private static final s c = a(2L);

    private s() {
        this.f204b = -1;
        this.f205c = -1;
        this.f206a = -1L;
    }

    private s(int i, int[] iArr) {
        this.f204b = -1;
        this.f205c = -1;
        this.f206a = -1L;
        if (iArr.length <= 0) {
            this.f203c = iArr;
            this.f202a = 0;
            return;
        }
        this.f202a = i;
        int i2 = 0;
        while (i2 < iArr.length && iArr[i2] == 0) {
            i2++;
        }
        if (i2 == 0) {
            this.f203c = iArr;
            return;
        }
        int[] iArr2 = new int[iArr.length - i2];
        System.arraycopy(iArr, i2, iArr2, 0, iArr2.length);
        this.f203c = iArr2;
        if (iArr2.length == 0) {
            this.f202a = 0;
        }
    }

    public s(String str, int i) {
        this.f204b = -1;
        this.f205c = -1;
        this.f206a = -1L;
        if (str.length() == 0) {
            throw new NumberFormatException("Zero length BigInteger");
        }
        if (i < 2 || i > 36) {
            throw new NumberFormatException("Radix out of range");
        }
        int i2 = 0;
        this.f202a = 1;
        if (str.charAt(0) == '-') {
            if (str.length() == 1) {
                throw new NumberFormatException("Zero length BigInteger");
            }
            this.f202a = -1;
            i2 = 1;
        }
        while (i2 < str.length() && Character.digit(str.charAt(i2), i) == 0) {
            i2++;
        }
        if (i2 >= str.length()) {
            this.f202a = 0;
            this.f203c = new int[0];
            return;
        }
        s sVarA = f200a;
        s sVarA2 = a(i);
        while (i2 < str.length()) {
            sVarA = sVarA.c(sVarA2).a(a(Character.digit(str.charAt(i2), i)));
            i2++;
        }
        this.f203c = sVarA.f203c;
    }

    private s(byte[] bArr) {
        this.f204b = -1;
        this.f205c = -1;
        this.f206a = -1L;
        if (bArr.length == 0) {
            throw new NumberFormatException("Zero length BigInteger");
        }
        this.f202a = 1;
        if (bArr[0] < 0) {
            this.f202a = -1;
        }
        this.f203c = a(bArr, this.f202a);
        if (this.f203c.length == 0) {
            this.f202a = 0;
        }
    }

    private int[] a(byte[] bArr, int i) {
        if (i >= 0) {
            int i2 = 0;
            while (i2 < bArr.length && bArr[i2] == 0) {
                i2++;
            }
            if (i2 >= bArr.length) {
                return new int[0];
            }
            int length = ((bArr.length - i2) + 3) / 4;
            int length2 = (bArr.length - i2) % 4;
            int i3 = length2;
            if (length2 == 0) {
                i3 = 4;
            }
            int[] iArr = new int[length];
            int i4 = 0;
            int i5 = 0;
            for (int i6 = i2; i6 < bArr.length; i6++) {
                i4 = (i4 << 8) | (bArr[i6] & 255);
                i3--;
                if (i3 <= 0) {
                    iArr[i5] = i4;
                    i5++;
                    i3 = 4;
                    i4 = 0;
                }
            }
            return iArr;
        }
        int i7 = 0;
        while (i7 < bArr.length - 1 && bArr[i7] == 255) {
            i7++;
        }
        int length3 = bArr.length;
        boolean z = false;
        if (bArr[i7] == 128) {
            int i8 = i7 + 1;
            while (i8 < bArr.length && bArr[i8] == 0) {
                i8++;
            }
            if (i8 == bArr.length) {
                length3++;
                z = true;
            }
        }
        int i9 = ((length3 - i7) + 3) / 4;
        int i10 = (length3 - i7) % 4;
        int i11 = i10;
        if (i10 == 0) {
            i11 = 4;
        }
        int[] iArr2 = new int[i9];
        int i12 = 0;
        int i13 = 0;
        if (z) {
            i11--;
            if (i11 <= 0) {
                i13 = 0 + 1;
                i11 = 4;
            }
        }
        for (int i14 = i7; i14 < bArr.length; i14++) {
            i12 = (i12 << 8) | ((bArr[i14] ^ (-1)) & 255);
            i11--;
            if (i11 <= 0) {
                iArr2[i13] = i12;
                i13++;
                i11 = 4;
                i12 = 0;
            }
        }
        int length4 = iArr2.length - 1;
        long j = (iArr2[length4] & 4294967295L) + 1;
        int i15 = length4 - 1;
        iArr2[length4] = (int) j;
        while (true) {
            long j2 = j >>> 32;
            if (i15 < 0 || j2 == 0) {
                break;
            }
            j = j2 + (iArr2[i15] & 4294967295L);
            int i16 = i15;
            i15--;
            iArr2[i16] = (int) j;
        }
        int[] iArr3 = iArr2;
        if (iArr2[0] == 0) {
            int[] iArr4 = new int[iArr3.length - 1];
            System.arraycopy(iArr3, 1, iArr4, 0, iArr4.length);
            iArr3 = iArr4;
        }
        return iArr3;
    }

    public s(int i, byte[] bArr) {
        this.f204b = -1;
        this.f205c = -1;
        this.f206a = -1L;
        this.f203c = a(bArr, 1);
        this.f202a = 1;
    }

    private s a() {
        return this.f202a >= 0 ? this : b();
    }

    private static int[] a(int[] iArr, int[] iArr2) {
        long j;
        int length = iArr.length - 1;
        int length2 = iArr2.length - 1;
        long j2 = 0;
        while (true) {
            j = j2;
            if (length2 < 0) {
                break;
            }
            int i = length2;
            length2--;
            long j3 = j + (iArr[length] & 4294967295L) + (iArr2[i] & 4294967295L);
            int i2 = length;
            length--;
            iArr[i2] = (int) j3;
            j2 = j3 >>> 32;
        }
        while (length >= 0 && j != 0) {
            long j4 = j + (iArr[length] & 4294967295L);
            int i3 = length;
            length--;
            iArr[i3] = (int) j4;
            j = j4 >>> 32;
        }
        return iArr;
    }

    private s a(s sVar) {
        int[] iArr;
        int[] iArr2;
        if (sVar.f202a == 0 || sVar.f203c.length == 0) {
            return this;
        }
        if (this.f202a == 0 || this.f203c.length == 0) {
            return sVar;
        }
        if (sVar.f202a < 0) {
            if (this.f202a > 0) {
                return e(sVar.b());
            }
        } else if (this.f202a < 0) {
            return sVar.e(b());
        }
        int[] iArr3 = sVar.f203c;
        if (this.f203c.length < iArr3.length) {
            iArr = iArr3;
            iArr2 = this.f203c;
        } else {
            iArr = this.f203c;
            iArr2 = iArr3;
        }
        int i = Integer.MAX_VALUE;
        if (iArr.length == iArr2.length) {
            i = Integer.MAX_VALUE - iArr2[0];
        }
        int i2 = (iArr[0] ^ Integer.MIN_VALUE) >= i ? 1 : 0;
        int[] iArr4 = new int[iArr.length + i2];
        System.arraycopy(iArr, 0, iArr4, i2, iArr.length);
        return new s(this.f202a, a(iArr4, iArr2));
    }

    private int a(int i, int[] iArr) {
        if (iArr.length == 0) {
            return 0;
        }
        while (i != iArr.length && iArr[i] == 0) {
            i++;
        }
        if (i == iArr.length) {
            return 0;
        }
        int length = (32 * ((iArr.length - i) - 1)) + a(iArr[i]);
        if (this.f202a < 0) {
            boolean z = ((f207a[iArr[i] & 255] + f207a[(iArr[i] >> 8) & 255]) + f207a[(iArr[i] >> 16) & 255]) + f207a[iArr[i] >>> 24] == 1;
            for (int i2 = i + 1; i2 < iArr.length && z; i2++) {
                z = iArr[i2] == 0;
            }
            length -= z ? 1 : 0;
        }
        return length;
    }

    /* renamed from: a, reason: collision with other method in class */
    public final int m126a() {
        if (this.f205c == -1) {
            if (this.f202a == 0) {
                this.f205c = 0;
            } else {
                this.f205c = a(0, this.f203c);
            }
        }
        return this.f205c;
    }

    private static int a(int i) {
        if (i >= 32768) {
            return i < 8388608 ? i < 524288 ? i < 131072 ? i < 65536 ? 16 : 17 : i < 262144 ? 18 : 19 : i < 2097152 ? i < 1048576 ? 20 : 21 : i < 4194304 ? 22 : 23 : i < 134217728 ? i < 33554432 ? i < 16777216 ? 24 : 25 : i < 67108864 ? 26 : 27 : i < 536870912 ? i < 268435456 ? 28 : 29 : i < 1073741824 ? 30 : 31;
        }
        if (i >= 128) {
            return i < 2048 ? i < 512 ? i < 256 ? 8 : 9 : i < 1024 ? 10 : 11 : i < 8192 ? i < 4096 ? 12 : 13 : i < 16384 ? 14 : 15;
        }
        if (i >= 8) {
            return i < 32 ? i < 16 ? 4 : 5 : i < 64 ? 6 : 7;
        }
        if (i >= 2) {
            return i < 4 ? 2 : 3;
        }
        if (i <= 0) {
            return i < 0 ? 32 : 0;
        }
        return 1;
    }

    private int a(int i, int[] iArr, int i2, int[] iArr2) {
        while (i != iArr.length && iArr[i] == 0) {
            i++;
        }
        while (i2 != iArr2.length && iArr2[i2] == 0) {
            i2++;
        }
        return b(i, iArr, i2, iArr2);
    }

    private static int b(int i, int[] iArr, int i2, int[] iArr2) {
        int length = (iArr.length - iArr2.length) - (i - i2);
        if (length != 0) {
            return length < 0 ? -1 : 1;
        }
        while (i < iArr.length) {
            int i3 = i;
            i++;
            int i4 = iArr[i3];
            int i5 = i2;
            i2++;
            int i6 = iArr2[i5];
            if (i4 != i6) {
                return (i4 ^ Integer.MIN_VALUE) < (i6 ^ Integer.MIN_VALUE) ? -1 : 1;
            }
        }
        return 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    private int m127a(s sVar) {
        if (this.f202a < sVar.f202a) {
            return -1;
        }
        if (this.f202a > sVar.f202a) {
            return 1;
        }
        if (this.f202a == 0) {
            return 0;
        }
        return this.f202a * a(0, this.f203c, 0, sVar.f203c);
    }

    private int[] b(int[] iArr, int[] iArr2) {
        int[] iArrA;
        int[] iArrA2;
        int iA;
        int iA2 = a(0, iArr, 0, iArr2);
        if (iA2 > 0) {
            int iA3 = a(0, iArr) - a(0, iArr2);
            if (iA3 > 1) {
                iArrA2 = a(iArr2, iA3 - 1);
                iArrA = a(f201b.f203c, iA3 - 1);
                if (iA3 % 32 == 0) {
                    int[] iArr3 = new int[(iA3 / 32) + 1];
                    System.arraycopy(iArrA, 0, iArr3, 1, iArr3.length - 1);
                    iArr3[0] = 0;
                    iArrA = iArr3;
                }
            } else {
                iArrA2 = new int[iArr.length];
                System.arraycopy(iArr2, 0, iArrA2, iArrA2.length - iArr2.length, iArr2.length);
                iArrA = new int[]{1};
            }
            int[] iArr4 = new int[iArrA.length];
            a(iArr, 0, iArrA2);
            System.arraycopy(iArrA, 0, iArr4, 0, iArrA.length);
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (a(i, iArr, i2, iArrA2) < 0) {
                    iA = a(i, iArr, 0, iArr2);
                    if (iA <= 0) {
                        break;
                    }
                    if (iArr[i] == 0) {
                        i++;
                    }
                    int iA4 = a(i2, iArrA2) - a(i, iArr);
                    if (iA4 == 0) {
                        m129a(i2, iArrA2);
                        m129a(i3, iArr4);
                    } else {
                        a(i2, iArrA2, iA4);
                        a(i3, iArr4, iA4);
                    }
                    if (iArrA2[i2] == 0) {
                        i2++;
                    }
                    if (iArr4[i3] == 0) {
                        i3++;
                    }
                } else {
                    a(iArr, i2, iArrA2);
                    a(iArrA, iArr4);
                }
            }
            if (iA == 0) {
                a(iArrA, f201b.f203c);
                for (int i4 = i; i4 != iArr.length; i4++) {
                    iArr[i4] = 0;
                }
            }
        } else {
            iArrA = iA2 == 0 ? new int[]{1} : new int[]{0};
        }
        return iArrA;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof s)) {
            return false;
        }
        s sVar = (s) obj;
        if (sVar.f202a != this.f202a || sVar.f203c.length != this.f203c.length) {
            return false;
        }
        for (int i = 0; i < this.f203c.length; i++) {
            if (sVar.f203c[i] != this.f203c[i]) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int length = this.f203c.length;
        if (this.f203c.length > 0) {
            length ^= this.f203c[0];
            if (this.f203c.length > 1) {
                length ^= this.f203c[this.f203c.length - 1];
            }
        }
        return this.f202a < 0 ? length ^ (-1) : length;
    }

    private s b(s sVar) {
        if (sVar.f202a <= 0) {
            throw new ArithmeticException("BigInteger: modulus is not positive");
        }
        s sVarD = d(sVar);
        return sVarD.f202a >= 0 ? sVarD : sVarD.a(sVar);
    }

    private static s a(s sVar, s sVar2, s sVar3, s sVar4) {
        s sVar5 = f201b;
        s sVar6 = sVar;
        s sVar7 = f200a;
        s sVar8 = sVar2;
        while (true) {
            s sVar9 = sVar8;
            if (sVar9.f202a <= 0) {
                if (sVar3 != null) {
                    sVar3.f202a = sVar5.f202a;
                    sVar3.f203c = sVar5.f203c;
                }
                return sVar6;
            }
            s sVar10 = sVar6;
            if (sVar9.f202a == 0) {
                throw new ArithmeticException("Divide by zero");
            }
            s[] sVarArr = new s[2];
            if (sVar10.f202a == 0) {
                s sVar11 = f200a;
                sVarArr[1] = sVar11;
                sVarArr[0] = sVar11;
            } else if (sVar9.m127a(f201b) == 0) {
                sVarArr[0] = sVar10;
                sVarArr[1] = f200a;
            } else {
                int[] iArr = new int[sVar10.f203c.length];
                System.arraycopy(sVar10.f203c, 0, iArr, 0, iArr.length);
                sVarArr[0] = new s(sVar10.f202a * sVar9.f202a, sVar10.b(iArr, sVar9.f203c));
                sVarArr[1] = new s(sVar10.f202a, iArr);
            }
            s sVarE = sVar5.e(sVar7.c(sVarArr[0]));
            sVar5 = sVar7;
            sVar7 = sVarE;
            sVar6 = sVar9;
            sVar8 = sVarArr[1];
        }
    }

    private static void a(int[] iArr) {
        for (int i = 0; i != iArr.length; i++) {
            iArr[i] = 0;
        }
    }

    public final s a(s sVar, s sVar2) {
        long j;
        if (sVar2.f202a <= 0) {
            throw new ArithmeticException("Modulus must be positive");
        }
        if (sVar2.equals(f201b)) {
            return f200a;
        }
        if (sVar.f202a == 0) {
            return f201b;
        }
        if (this.f202a == 0) {
            return f200a;
        }
        int[] iArr = null;
        int[] iArr2 = null;
        boolean z = (sVar2.f203c[sVar2.f203c.length - 1] & 1) == 1;
        long j2 = 0;
        if (z) {
            if (sVar2.f206a != -1) {
                j = sVar2.f206a;
            } else if ((sVar2.f203c[sVar2.f203c.length - 1] & 1) == 0) {
                j = -1;
            } else {
                long j3 = ((sVar2.f203c[sVar2.f203c.length - 1] ^ (-1)) | 1) & 4294967295L;
                if (4294967296L < 0) {
                    throw new ArithmeticException("Modulus must be positive");
                }
                long[] jArr = new long[2];
                if (a(j3, 4294967296L, jArr) != 1) {
                    throw new ArithmeticException("Numbers not relatively prime.");
                }
                if (jArr[0] < 0) {
                    jArr[0] = jArr[0] + 4294967296L;
                }
                sVar2.f206a = jArr[0];
                j = sVar2.f206a;
            }
            j2 = j;
            int[] iArr3 = m128a(32 * sVar2.f203c.length).b(sVar2).f203c;
            iArr = iArr3;
            boolean z2 = iArr3.length <= sVar2.f203c.length;
            z = z2;
            if (z2) {
                iArr2 = new int[sVar2.f203c.length + 1];
                if (iArr.length < sVar2.f203c.length) {
                    int[] iArr4 = new int[sVar2.f203c.length];
                    System.arraycopy(iArr, 0, iArr4, iArr4.length - iArr.length, iArr.length);
                    iArr = iArr4;
                }
            }
        }
        if (!z) {
            if (this.f203c.length <= sVar2.f203c.length) {
                iArr = new int[sVar2.f203c.length];
                System.arraycopy(this.f203c, 0, iArr, iArr.length - this.f203c.length, this.f203c.length);
            } else {
                s sVarD = d(sVar2);
                iArr = new int[sVar2.f203c.length];
                System.arraycopy(sVarD.f203c, 0, iArr, iArr.length - sVarD.f203c.length, sVarD.f203c.length);
            }
            iArr2 = new int[sVar2.f203c.length << 1];
        }
        int[] iArr5 = new int[sVar2.f203c.length];
        for (int i = 0; i < sVar.f203c.length; i++) {
            int i2 = sVar.f203c[i];
            int i3 = 0;
            if (i == 0) {
                while (i2 > 0) {
                    i2 <<= 1;
                    i3++;
                }
                System.arraycopy(iArr, 0, iArr5, 0, iArr.length);
                i2 <<= 1;
                i3++;
            }
            while (i2 != 0) {
                if (z) {
                    a(iArr2, iArr5, iArr5, sVar2.f203c, j2);
                } else {
                    c(iArr2, iArr5);
                    d(iArr2, sVar2.f203c);
                    System.arraycopy(iArr2, iArr2.length - iArr5.length, iArr5, 0, iArr5.length);
                    a(iArr2);
                }
                i3++;
                if (i2 < 0) {
                    if (z) {
                        a(iArr2, iArr5, iArr, sVar2.f203c, j2);
                    } else {
                        a(iArr2, iArr5, iArr);
                        d(iArr2, sVar2.f203c);
                        System.arraycopy(iArr2, iArr2.length - iArr5.length, iArr5, 0, iArr5.length);
                        a(iArr2);
                    }
                }
                i2 <<= 1;
            }
            while (i3 < 32) {
                if (z) {
                    a(iArr2, iArr5, iArr5, sVar2.f203c, j2);
                } else {
                    c(iArr2, iArr5);
                    d(iArr2, sVar2.f203c);
                    System.arraycopy(iArr2, iArr2.length - iArr5.length, iArr5, 0, iArr5.length);
                    a(iArr2);
                }
                i3++;
            }
        }
        if (z) {
            a(iArr);
            iArr[iArr.length - 1] = 1;
            a(iArr2, iArr5, iArr, sVar2.f203c, j2);
        }
        s sVar3 = new s(1, iArr5);
        if (sVar.f202a > 0) {
            return sVar3;
        }
        if (sVar2.f202a != 1) {
            throw new ArithmeticException("Modulus must be positive");
        }
        s sVar4 = new s();
        if (!a(sVar3, sVar2, sVar4, (s) null).equals(f201b)) {
            throw new ArithmeticException("Numbers not relatively prime.");
        }
        if (sVar4.m127a(f200a) < 0) {
            sVar4 = sVar4.a(sVar2);
        }
        return sVar4;
    }

    private static int[] c(int[] iArr, int[] iArr2) {
        int length = iArr.length - 1;
        for (int length2 = iArr2.length - 1; length2 != 0; length2--) {
            long j = iArr2[length2] & 4294967295L;
            long j2 = j * j;
            long j3 = j2 >>> 32;
            long j4 = (j2 & 4294967295L) + (iArr[length] & 4294967295L);
            iArr[length] = (int) j4;
            long j5 = j3 + (j4 >> 32);
            for (int i = length2 - 1; i >= 0; i--) {
                length--;
                long j6 = (iArr2[i] & 4294967295L) * j;
                long j7 = j6 >>> 31;
                long j8 = ((j6 & 2147483647L) << 1) + (iArr[length] & 4294967295L) + j5;
                iArr[length] = (int) j8;
                j5 = j7 + (j8 >>> 32);
            }
            int i2 = length - 1;
            long j9 = j5 + (iArr[i2] & 4294967295L);
            iArr[i2] = (int) j9;
            int i3 = i2 - 1;
            if (i3 >= 0) {
                iArr[i3] = (int) (j9 >> 32);
            }
            length = i3 + length2;
        }
        long j10 = iArr2[0] & 4294967295L;
        long j11 = j10 * j10;
        long j12 = j11 >>> 32;
        long j13 = (j11 & 4294967295L) + (iArr[length] & 4294967295L);
        iArr[length] = (int) j13;
        int i4 = length - 1;
        if (i4 >= 0) {
            iArr[i4] = (int) (j12 + (j13 >> 32) + iArr[i4]);
        }
        return iArr;
    }

    private static int[] a(int[] iArr, int[] iArr2, int[] iArr3) {
        long j;
        int length = iArr3.length;
        int i = length;
        if (length <= 0) {
            return iArr;
        }
        int length2 = iArr.length - iArr2.length;
        while (true) {
            i--;
            long j2 = iArr3[i] & 4294967295L;
            j = 0;
            for (int length3 = iArr2.length - 1; length3 >= 0; length3--) {
                long j3 = j + (j2 * (iArr2[length3] & 4294967295L)) + (iArr[length2 + length3] & 4294967295L);
                iArr[length2 + length3] = (int) j3;
                j = j3 >>> 32;
            }
            length2--;
            if (i <= 0) {
                break;
            }
            iArr[length2] = (int) j;
        }
        if (length2 >= 0) {
            iArr[length2] = (int) j;
        }
        return iArr;
    }

    private static long a(long j, long j2, long[] jArr) {
        long j3 = 1;
        long j4 = j;
        long j5 = 0;
        long j6 = j2;
        while (true) {
            long j7 = j6;
            if (j7 <= 0) {
                jArr[0] = j3;
                jArr[1] = (j4 - (j3 * j)) / j2;
                return j4;
            }
            long j8 = j4 / j7;
            long j9 = j3 - (j5 * j8);
            j3 = j5;
            j5 = j9;
            long j10 = j4 - (j7 * j8);
            j4 = j7;
            j6 = j10;
        }
    }

    private void a(int[] iArr, int[] iArr2, int[] iArr3, int[] iArr4, long j) {
        int length = iArr4.length;
        int i = length - 1;
        long j2 = iArr3[i] & 4294967295L;
        for (int i2 = 0; i2 <= length; i2++) {
            iArr[i2] = 0;
        }
        for (int i3 = length; i3 > 0; i3--) {
            long j3 = iArr2[i3 - 1] & 4294967295L;
            long j4 = ((((iArr[length] & 4294967295L) + ((j3 * j2) & 4294967295L)) & 4294967295L) * j) & 4294967295L;
            long j5 = j3 * j2;
            long j6 = j4 * (iArr4[i] & 4294967295L);
            long j7 = (j5 >>> 32) + (j6 >>> 32) + ((((iArr[length] & 4294967295L) + (j5 & 4294967295L)) + (j6 & 4294967295L)) >>> 32);
            for (int i4 = i; i4 > 0; i4--) {
                long j8 = j3 * (iArr3[i4 - 1] & 4294967295L);
                long j9 = j4 * (iArr4[i4 - 1] & 4294967295L);
                long j10 = (iArr[i4] & 4294967295L) + (j8 & 4294967295L) + (j9 & 4294967295L) + (j7 & 4294967295L);
                j7 = (j7 >>> 32) + (j8 >>> 32) + (j9 >>> 32) + (j10 >>> 32);
                iArr[i4 + 1] = (int) j10;
            }
            long j11 = j7 + (iArr[0] & 4294967295L);
            iArr[1] = (int) j11;
            iArr[0] = (int) (j11 >>> 32);
        }
        if (a(0, iArr, 0, iArr4) >= 0) {
            a(iArr, 0, iArr4);
        }
        System.arraycopy(iArr, 1, iArr2, 0, length);
    }

    private s c(s sVar) {
        if (this.f202a == 0 || sVar.f202a == 0) {
            return f200a;
        }
        int[] iArr = new int[((m126a() + sVar.m126a()) / 32) + 1];
        if (sVar == this) {
            c(iArr, this.f203c);
        } else {
            a(iArr, this.f203c, sVar.f203c);
        }
        return new s(this.f202a * sVar.f202a, iArr);
    }

    private s b() {
        return this.f202a == 0 ? this : new s(-this.f202a, this.f203c);
    }

    private s c() {
        return a(f201b).b();
    }

    private int b(int i) {
        long j = 0;
        for (int i2 = 0; i2 < this.f203c.length; i2++) {
            j = ((j << 32) | (this.f203c[i2] & 4294967295L)) % i;
        }
        return (int) j;
    }

    private int[] d(int[] iArr, int[] iArr2) {
        int[] iArrA;
        int i = 0;
        while (i < iArr.length && iArr[i] == 0) {
            i++;
        }
        int i2 = 0;
        while (i2 < iArr2.length && iArr2[i2] == 0) {
            i2++;
        }
        int iB = b(i, iArr, i2, iArr2);
        int i3 = iB;
        if (iB > 0) {
            int iA = a(i2, iArr2);
            int iA2 = a(i, iArr);
            int length = iA2;
            int i4 = iA2 - iA;
            int i5 = 0;
            int i6 = iA;
            if (i4 > 0) {
                iArrA = a(iArr2, i4);
                i6 += i4;
            } else {
                int length2 = iArr2.length - i2;
                iArrA = new int[length2];
                System.arraycopy(iArr2, i2, iArrA, 0, length2);
            }
            while (true) {
                if (i6 < length || b(i, iArr, i5, iArrA) >= 0) {
                    a(iArr, i5, iArrA);
                    while (iArr[i] == 0) {
                        i++;
                        if (i == iArr.length) {
                            return iArr;
                        }
                    }
                    int iB2 = b(i, iArr, i2, iArr2);
                    i3 = iB2;
                    if (iB2 <= 0) {
                        break;
                    }
                    length = (32 * ((iArr.length - i) - 1)) + a(iArr[i]);
                }
                int i7 = i6 - length;
                if (i7 < 2) {
                    m129a(i5, iArrA);
                    i6--;
                } else {
                    a(i5, iArrA, i7);
                    i6 -= i7;
                }
                while (iArrA[i5] == 0) {
                    i5++;
                }
            }
        }
        if (i3 == 0) {
            for (int i8 = i; i8 < iArr.length; i8++) {
                iArr[i8] = 0;
            }
        }
        return iArr;
    }

    private s d(s sVar) {
        int[] iArrD;
        int[] iArr;
        int i;
        int iB;
        if (sVar.f202a == 0) {
            throw new ArithmeticException("BigInteger: Divide by zero");
        }
        if (this.f202a == 0) {
            return f200a;
        }
        if (sVar.f203c.length == 1 && (i = sVar.f203c[0]) > 0) {
            if (i != 1 && (iB = b(i)) != 0) {
                return new s(this.f202a, new int[]{iB});
            }
            return f200a;
        }
        if (a(0, this.f203c, 0, sVar.f203c) < 0) {
            return this;
        }
        if (sVar.f202a > 0 && sVar.f204b == 1) {
            int iM126a = sVar.a().m126a() - 1;
            if (iM126a <= 0) {
                iArr = b;
            } else {
                int iMin = Math.min((iM126a + 31) / 32, this.f203c.length);
                int[] iArr2 = new int[iMin];
                System.arraycopy(this.f203c, this.f203c.length - iMin, iArr2, 0, iMin);
                int i2 = iM126a % 32;
                if (i2 != 0) {
                    iArr2[0] = iArr2[0] & (((-1) << i2) ^ (-1));
                }
                iArr = iArr2;
            }
            iArrD = iArr;
        } else {
            int[] iArr3 = new int[this.f203c.length];
            System.arraycopy(this.f203c, 0, iArr3, 0, iArr3.length);
            iArrD = d(iArr3, sVar.f203c);
        }
        return new s(this.f202a, iArrD);
    }

    private static int[] a(int[] iArr, int i) {
        int[] iArr2;
        int i2 = i >>> 5;
        int i3 = i & 31;
        int length = iArr.length;
        if (i3 == 0) {
            iArr2 = new int[length + i2];
            System.arraycopy(iArr, 0, iArr2, 0, length);
        } else {
            int i4 = 0;
            int i5 = 32 - i3;
            int i6 = iArr[0] >>> i5;
            if (i6 != 0) {
                int[] iArr3 = new int[length + i2 + 1];
                iArr2 = iArr3;
                i4 = 0 + 1;
                iArr3[0] = i6;
            } else {
                iArr2 = new int[length + i2];
            }
            int i7 = iArr[0];
            for (int i8 = 0; i8 < length - 1; i8++) {
                int i9 = iArr[i8 + 1];
                int i10 = i4;
                i4++;
                iArr2[i10] = (i7 << i3) | (i9 >>> i5);
                i7 = i9;
            }
            iArr2[i4] = iArr[length - 1] << i3;
        }
        return iArr2;
    }

    /* renamed from: a, reason: collision with other method in class */
    private s m128a(int i) {
        if (this.f202a == 0 || this.f203c.length == 0) {
            return f200a;
        }
        if (i == 0) {
            return this;
        }
        if (i >= 0) {
            s sVar = new s(this.f202a, a(this.f203c, i));
            if (this.f204b != -1) {
                sVar.f204b = this.f202a > 0 ? this.f204b : this.f204b + i;
            }
            if (this.f205c != -1) {
                sVar.f205c = this.f205c + i;
            }
            return sVar;
        }
        int i2 = -i;
        if (i2 == 0) {
            return this;
        }
        if (i2 < 0) {
            return m128a(-i2);
        }
        if (i2 >= m126a()) {
            return this.f202a < 0 ? a(-1L) : f200a;
        }
        int[] iArr = new int[this.f203c.length];
        System.arraycopy(this.f203c, 0, iArr, 0, iArr.length);
        a(0, iArr, i2);
        return new s(this.f202a, iArr);
    }

    private static void a(int i, int[] iArr, int i2) {
        int i3 = (i2 >>> 5) + i;
        int i4 = i2 & 31;
        int length = iArr.length - 1;
        if (i3 != i) {
            int i5 = i3 - i;
            for (int i6 = length; i6 >= i3; i6--) {
                iArr[i6] = iArr[i6 - i5];
            }
            for (int i7 = i3 - 1; i7 >= i; i7--) {
                iArr[i7] = 0;
            }
        }
        if (i4 != 0) {
            int i8 = 32 - i4;
            int i9 = iArr[length];
            for (int i10 = length; i10 >= i3 + 1; i10--) {
                int i11 = iArr[i10 - 1];
                iArr[i10] = (i9 >>> i4) | (i11 << i8);
                i9 = i11;
            }
            iArr[i3] = iArr[i3] >>> i4;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static void m129a(int i, int[] iArr) {
        int length = iArr.length - 1;
        int i2 = iArr[length];
        for (int i3 = length; i3 > i; i3--) {
            int i4 = iArr[i3 - 1];
            iArr[i3] = (i2 >>> 1) | (i4 << 31);
            i2 = i4;
        }
        iArr[i] = iArr[i] >>> 1;
    }

    private static int[] a(int[] iArr, int i, int[] iArr2) {
        int i2;
        int length = iArr.length;
        int length2 = iArr2.length;
        int i3 = 0;
        do {
            length--;
            length2--;
            long j = ((iArr[length] & 4294967295L) - (iArr2[length2] & 4294967295L)) + i3;
            iArr[length] = (int) j;
            i3 = (int) (j >> 63);
        } while (length2 > i);
        if (i3 != 0) {
            do {
                length--;
                i2 = iArr[length] - 1;
                iArr[length] = i2;
            } while (i2 == -1);
        }
        return iArr;
    }

    private s e(s sVar) {
        s sVar2;
        s sVar3;
        if (sVar.f202a == 0 || sVar.f203c.length == 0) {
            return this;
        }
        if (this.f202a == 0 || this.f203c.length == 0) {
            return sVar.b();
        }
        if (this.f202a != sVar.f202a) {
            return a(sVar.b());
        }
        int iA = a(0, this.f203c, 0, sVar.f203c);
        if (iA == 0) {
            return f200a;
        }
        if (iA < 0) {
            sVar2 = sVar;
            sVar3 = this;
        } else {
            sVar2 = this;
            sVar3 = sVar;
        }
        int[] iArr = new int[sVar2.f203c.length];
        System.arraycopy(sVar2.f203c, 0, iArr, 0, iArr.length);
        return new s(this.f202a * iA, a(iArr, 0, sVar3.f203c));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v16, types: [int] */
    /* JADX WARN: Type inference failed for: r2v30, types: [int] */
    /* renamed from: a, reason: collision with other method in class */
    public final byte[] m130a() {
        int i;
        if (this.f202a == 0) {
            return new byte[1];
        }
        byte[] bArr = new byte[(m126a() / 8) + 1];
        int length = this.f203c.length;
        int length2 = bArr.length;
        if (this.f202a > 0) {
            while (length > 1) {
                length--;
                int i2 = this.f203c[length];
                int i3 = length2 - 1;
                bArr[i3] = (byte) i2;
                int i4 = i3 - 1;
                bArr[i4] = (byte) (i2 >>> 8);
                int i5 = i4 - 1;
                bArr[i5] = (byte) (i2 >>> 16);
                length2 = i5 - 1;
                bArr[length2] = i2 >> 24;
            }
            int i6 = this.f203c[0];
            while (true) {
                i = i6;
                if ((i & (-256)) == 0) {
                    break;
                }
                length2--;
                bArr[length2] = (byte) i;
                i6 = i >>> 8;
            }
            bArr[length2 - 1] = (byte) i;
        } else {
            boolean z = true;
            while (length > 1) {
                length--;
                int i7 = this.f203c[length] ^ (-1);
                if (z) {
                    i7++;
                    z = i7 == 0;
                }
                int i8 = length2 - 1;
                bArr[i8] = (byte) i7;
                int i9 = i8 - 1;
                bArr[i9] = (byte) (i7 >>> 8);
                int i10 = i9 - 1;
                bArr[i10] = (byte) (i7 >>> 16);
                length2 = i10 - 1;
                bArr[length2] = i7 >> 24;
            }
            int i11 = this.f203c[0];
            if (z) {
                i11--;
            }
            while ((i11 & (-256)) != 0) {
                length2--;
                bArr[length2] = (byte) (i11 ^ (-1));
                i11 >>>= 8;
            }
            int i12 = length2 - 1;
            bArr[i12] = (byte) (i11 ^ (-1));
            if (i12 > 0) {
                bArr[i12 - 1] = -1;
            }
        }
        return bArr;
    }

    public final String toString() {
        String string;
        if (this.f203c == null) {
            return "null";
        }
        if (this.f202a == 0) {
            return "0";
        }
        StringBuffer stringBuffer = new StringBuffer();
        Stack stack = new Stack();
        s sVar = new s(Integer.toString(10, 10), 10);
        s sVarA = a();
        while (true) {
            s sVar2 = sVarA;
            if (sVar2.equals(f200a)) {
                while (!stack.empty()) {
                    stringBuffer.append((String) stack.pop());
                }
                String string2 = stringBuffer.toString();
                while (true) {
                    string = string2;
                    if (string.length() <= 1 || string.charAt(0) != '0') {
                        break;
                    }
                    string2 = string.substring(1);
                }
                if (string.length() == 0) {
                    string = "0";
                } else if (this.f202a == -1) {
                    string = new StringBuffer().append("-").append(string).toString();
                }
                return string;
            }
            s sVarB = sVar2.b(sVar);
            if (sVarB.equals(f200a)) {
                stack.push("0");
            } else {
                stack.push(Integer.toString(sVarB.f203c[0], 10));
            }
            if (sVar.f202a == 0) {
                throw new ArithmeticException("Divide by zero");
            }
            if (sVar2.f202a == 0) {
                sVarA = f200a;
            } else if (sVar.m127a(f201b) == 0) {
                sVarA = sVar2;
            } else {
                int[] iArr = new int[sVar2.f203c.length];
                System.arraycopy(sVar2.f203c, 0, iArr, 0, iArr.length);
                sVarA = new s(sVar2.f202a * sVar.f202a, sVar2.b(iArr, sVar.f203c));
            }
        }
    }

    private static s a(long j) {
        if (j == 0) {
            return f200a;
        }
        if (j < 0) {
            return j == Long.MIN_VALUE ? a(j ^ (-1)).c() : a(-j).b();
        }
        byte[] bArr = new byte[8];
        for (int i = 0; i < 8; i++) {
            bArr[7 - i] = (byte) j;
            j >>= 8;
        }
        return new s(bArr);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [int[], int[][]] */
    static {
        a(3L);
        f200a.f204b = 0;
        f200a.f205c = 0;
        f201b.f204b = 1;
        f201b.f205c = 1;
        c.f204b = 1;
        c.f205c = 2;
        f199a = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            int i2 = 1;
            for (int i3 : a[i]) {
                i2 *= i3;
            }
            f199a[i] = i2;
        }
        byte[] bArr = {-1, Byte.MAX_VALUE, 63, 31, 15, 7, 3, 1};
        f207a = new byte[]{0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8};
    }
}
