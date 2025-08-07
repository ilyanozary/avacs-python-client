package org.objectweb.asm;

import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.TextField;
import javax.microedition.media.control.ToneControl;
import org.objectweb.asm.signature.SignatureVisitor;

/* loaded from: avacs.jar:org/objectweb/asm/Frame.class */
final class Frame {
    static final int[] a;
    Label b;
    int[] c;
    int[] d;
    private int[] e;
    private int[] f;
    private int g;
    private int h;
    private int[] i;

    Frame() {
    }

    private int a(int i) {
        if (this.e == null || i >= this.e.length) {
            return 33554432 | i;
        }
        int i2 = this.e[i];
        if (i2 == 0) {
            int i3 = 33554432 | i;
            this.e[i] = i3;
            i2 = i3;
        }
        return i2;
    }

    private void a(int i, int i2) {
        if (this.e == null) {
            this.e = new int[10];
        }
        int length = this.e.length;
        if (i >= length) {
            int[] iArr = new int[Math.max(i + 1, 2 * length)];
            System.arraycopy(this.e, 0, iArr, 0, length);
            this.e = iArr;
        }
        this.e[i] = i2;
    }

    private void b(int i) {
        if (this.f == null) {
            this.f = new int[10];
        }
        int length = this.f.length;
        if (this.g >= length) {
            int[] iArr = new int[Math.max(this.g + 1, 2 * length)];
            System.arraycopy(this.f, 0, iArr, 0, length);
            this.f = iArr;
        }
        int[] iArr2 = this.f;
        int i2 = this.g;
        this.g = i2 + 1;
        iArr2[i2] = i;
        int i3 = this.b.f + this.g;
        if (i3 > this.b.g) {
            this.b.g = i3;
        }
    }

    private void a(ClassWriter classWriter, String str) {
        int iB = b(classWriter, str);
        if (iB != 0) {
            b(iB);
            if (iB == 16777220 || iB == 16777219) {
                b(16777216);
            }
        }
    }

    private static int b(ClassWriter classWriter, String str) {
        int iC;
        int iIndexOf = str.charAt(0) == '(' ? str.indexOf(41) + 1 : 0;
        switch (str.charAt(iIndexOf)) {
            case 'B':
            case 'C':
            case 'I':
            case Opcodes.AASTORE /* 83 */:
            case Opcodes.DUP_X1 /* 90 */:
                return 16777217;
            case 'D':
                return 16777219;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case Opcodes.IASTORE /* 79 */:
            case Opcodes.LASTORE /* 80 */:
            case Opcodes.FASTORE /* 81 */:
            case Opcodes.DASTORE /* 82 */:
            case Opcodes.BASTORE /* 84 */:
            case Opcodes.CASTORE /* 85 */:
            case Opcodes.POP /* 87 */:
            case Opcodes.POP2 /* 88 */:
            case Opcodes.DUP /* 89 */:
            default:
                int i = iIndexOf + 1;
                while (str.charAt(i) == '[') {
                    i++;
                }
                switch (str.charAt(i)) {
                    case 'B':
                        iC = 16777226;
                        break;
                    case 'C':
                        iC = 16777227;
                        break;
                    case 'D':
                        iC = 16777219;
                        break;
                    case 'E':
                    case 'G':
                    case 'H':
                    case 'K':
                    case 'L':
                    case 'M':
                    case 'N':
                    case Opcodes.IASTORE /* 79 */:
                    case Opcodes.LASTORE /* 80 */:
                    case Opcodes.FASTORE /* 81 */:
                    case Opcodes.DASTORE /* 82 */:
                    case Opcodes.BASTORE /* 84 */:
                    case Opcodes.CASTORE /* 85 */:
                    case Opcodes.SASTORE /* 86 */:
                    case Opcodes.POP /* 87 */:
                    case Opcodes.POP2 /* 88 */:
                    case Opcodes.DUP /* 89 */:
                    default:
                        iC = 24117248 | classWriter.c(str.substring(i + 1, str.length() - 1));
                        break;
                    case 'F':
                        iC = 16777218;
                        break;
                    case 'I':
                        iC = 16777217;
                        break;
                    case 'J':
                        iC = 16777220;
                        break;
                    case Opcodes.AASTORE /* 83 */:
                        iC = 16777228;
                        break;
                    case Opcodes.DUP_X1 /* 90 */:
                        iC = 16777225;
                        break;
                }
                return ((i - iIndexOf) << 28) | iC;
            case 'F':
                return 16777218;
            case 'J':
                return 16777220;
            case 'L':
                return 24117248 | classWriter.c(str.substring(iIndexOf + 1, str.length() - 1));
            case Opcodes.SASTORE /* 86 */:
                return 0;
        }
    }

    private int a() {
        if (this.g > 0) {
            int[] iArr = this.f;
            int i = this.g - 1;
            this.g = i;
            return iArr[i];
        }
        Label label = this.b;
        int i2 = label.f - 1;
        label.f = i2;
        return 50331648 | (-i2);
    }

    private void c(int i) {
        if (this.g >= i) {
            this.g -= i;
            return;
        }
        this.b.f -= i - this.g;
        this.g = 0;
    }

    private void a(String str) {
        char cCharAt = str.charAt(0);
        if (cCharAt == '(') {
            c((MethodWriter.a(str) >> 2) - 1);
        } else if (cCharAt == 'J' || cCharAt == 'D') {
            c(2);
        } else {
            c(1);
        }
    }

    private void d(int i) {
        if (this.i == null) {
            this.i = new int[2];
        }
        int length = this.i.length;
        if (this.h >= length) {
            int[] iArr = new int[Math.max(this.h + 1, 2 * length)];
            System.arraycopy(this.i, 0, iArr, 0, length);
            this.i = iArr;
        }
        int[] iArr2 = this.i;
        int i2 = this.h;
        this.h = i2 + 1;
        iArr2[i2] = i;
    }

    private int a(ClassWriter classWriter, int i) {
        int iC;
        if (i == 16777222) {
            iC = 24117248 | classWriter.c(classWriter.F);
        } else {
            if ((i & (-1048576)) != 25165824) {
                return i;
            }
            iC = 24117248 | classWriter.c(classWriter.E[i & 1048575].g);
        }
        for (int i2 = 0; i2 < this.h; i2++) {
            int i3 = this.i[i2];
            int i4 = i3 & (-268435456);
            int i5 = i3 & 251658240;
            if (i5 == 33554432) {
                i3 = i4 + this.c[i3 & 16777215];
            } else if (i5 == 50331648) {
                i3 = i4 + this.d[this.d.length - (i3 & 16777215)];
            }
            if (i == i3) {
                return iC;
            }
        }
        return i;
    }

    void a(ClassWriter classWriter, int i, Type[] typeArr, int i2) {
        this.c = new int[i2];
        this.d = new int[0];
        int i3 = 0;
        if ((i & 8) == 0) {
            if ((i & TextField.SENSITIVE) == 0) {
                i3 = 0 + 1;
                this.c[0] = 24117248 | classWriter.c(classWriter.F);
            } else {
                i3 = 0 + 1;
                this.c[0] = 16777222;
            }
        }
        for (Type type : typeArr) {
            int iB = b(classWriter, type.getDescriptor());
            int i4 = i3;
            i3++;
            this.c[i4] = iB;
            if (iB == 16777220 || iB == 16777219) {
                i3++;
                this.c[i3] = 16777216;
            }
        }
        while (i3 < i2) {
            int i5 = i3;
            i3++;
            this.c[i5] = 16777216;
        }
    }

    void a(int i, int i2, ClassWriter classWriter, Item item) {
        switch (i) {
            case 0:
            case Opcodes.INEG /* 116 */:
            case Opcodes.LNEG /* 117 */:
            case Opcodes.FNEG /* 118 */:
            case Opcodes.DNEG /* 119 */:
            case Opcodes.I2B /* 145 */:
            case Opcodes.I2C /* 146 */:
            case Opcodes.I2S /* 147 */:
            case Opcodes.GOTO /* 167 */:
            case Opcodes.RETURN /* 177 */:
                return;
            case 1:
                b(16777221);
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 16:
            case Opcodes.SIPUSH /* 17 */:
            case Opcodes.ILOAD /* 21 */:
                b(16777217);
                return;
            case 9:
            case 10:
            case Opcodes.LLOAD /* 22 */:
                b(16777220);
                b(16777216);
                return;
            case 11:
            case 12:
            case 13:
            case Opcodes.FLOAD /* 23 */:
                b(16777218);
                return;
            case 14:
            case Opcodes.DCONST_1 /* 15 */:
            case Opcodes.DLOAD /* 24 */:
                b(16777219);
                b(16777216);
                return;
            case Opcodes.LDC /* 18 */:
                switch (item.b) {
                    case 3:
                        b(16777217);
                        return;
                    case 4:
                        b(16777218);
                        return;
                    case 5:
                        b(16777220);
                        b(16777216);
                        return;
                    case 6:
                        b(16777219);
                        b(16777216);
                        return;
                    case 7:
                        b(24117248 | classWriter.c("java/lang/Class"));
                        return;
                    default:
                        b(24117248 | classWriter.c("java/lang/String"));
                        return;
                }
            case 19:
            case 20:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case Canvas.KEY_POUND /* 35 */:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case Canvas.KEY_STAR /* 42 */:
            case SignatureVisitor.EXTENDS /* 43 */:
            case 44:
            case SignatureVisitor.SUPER /* 45 */:
            case 59:
            case ToneControl.C4 /* 60 */:
            case SignatureVisitor.INSTANCEOF /* 61 */:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 186:
            case 196:
            case Opcodes.MULTIANEWARRAY /* 197 */:
            default:
                c(i2);
                a(classWriter, item.g);
                return;
            case Opcodes.ALOAD /* 25 */:
                b(a(i2));
                return;
            case 46:
            case 51:
            case 52:
            case 53:
                c(2);
                b(16777217);
                return;
            case 47:
            case Opcodes.D2L /* 143 */:
                c(2);
                b(16777220);
                b(16777216);
                return;
            case 48:
                c(2);
                b(16777218);
                return;
            case 49:
            case Opcodes.L2D /* 138 */:
                c(2);
                b(16777219);
                b(16777216);
                return;
            case 50:
                c(1);
                b((-268435456) + a());
                return;
            case 54:
            case 56:
            case Opcodes.ASTORE /* 58 */:
                a(i2, a());
                if (i2 > 0) {
                    int iA = a(i2 - 1);
                    if (iA == 16777220 || iA == 16777219) {
                        a(i2 - 1, 16777216);
                        return;
                    }
                    return;
                }
                return;
            case 55:
            case 57:
                c(1);
                a(i2, a());
                a(i2 + 1, 16777216);
                if (i2 > 0) {
                    int iA2 = a(i2 - 1);
                    if (iA2 == 16777220 || iA2 == 16777219) {
                        a(i2 - 1, 16777216);
                        return;
                    }
                    return;
                }
                return;
            case Opcodes.IASTORE /* 79 */:
            case Opcodes.FASTORE /* 81 */:
            case Opcodes.AASTORE /* 83 */:
            case Opcodes.BASTORE /* 84 */:
            case Opcodes.CASTORE /* 85 */:
            case Opcodes.SASTORE /* 86 */:
                c(3);
                return;
            case Opcodes.LASTORE /* 80 */:
            case Opcodes.DASTORE /* 82 */:
                c(4);
                return;
            case Opcodes.POP /* 87 */:
            case Opcodes.IFEQ /* 153 */:
            case Opcodes.IFNE /* 154 */:
            case Opcodes.IFLT /* 155 */:
            case Opcodes.IFGE /* 156 */:
            case Opcodes.IFGT /* 157 */:
            case Opcodes.IFLE /* 158 */:
            case Opcodes.TABLESWITCH /* 170 */:
            case Opcodes.LOOKUPSWITCH /* 171 */:
            case Opcodes.IRETURN /* 172 */:
            case Opcodes.FRETURN /* 174 */:
            case 176:
            case Opcodes.ATHROW /* 191 */:
            case Opcodes.MONITORENTER /* 194 */:
            case Opcodes.MONITOREXIT /* 195 */:
            case Opcodes.IFNULL /* 198 */:
            case Opcodes.IFNONNULL /* 199 */:
                c(1);
                return;
            case Opcodes.POP2 /* 88 */:
            case Opcodes.IF_ICMPEQ /* 159 */:
            case Opcodes.IF_ICMPNE /* 160 */:
            case Opcodes.IF_ICMPLT /* 161 */:
            case Opcodes.IF_ICMPGE /* 162 */:
            case Opcodes.IF_ICMPGT /* 163 */:
            case Opcodes.IF_ICMPLE /* 164 */:
            case Opcodes.IF_ACMPEQ /* 165 */:
            case Opcodes.IF_ACMPNE /* 166 */:
            case Opcodes.LRETURN /* 173 */:
            case Opcodes.DRETURN /* 175 */:
                c(2);
                return;
            case Opcodes.DUP /* 89 */:
                int iA3 = a();
                b(iA3);
                b(iA3);
                return;
            case Opcodes.DUP_X1 /* 90 */:
                int iA4 = a();
                int iA5 = a();
                b(iA4);
                b(iA5);
                b(iA4);
                return;
            case Opcodes.DUP_X2 /* 91 */:
                int iA6 = a();
                int iA7 = a();
                int iA8 = a();
                b(iA6);
                b(iA8);
                b(iA7);
                b(iA6);
                return;
            case Opcodes.DUP2 /* 92 */:
                int iA9 = a();
                int iA10 = a();
                b(iA10);
                b(iA9);
                b(iA10);
                b(iA9);
                return;
            case Opcodes.DUP2_X1 /* 93 */:
                int iA11 = a();
                int iA12 = a();
                int iA13 = a();
                b(iA12);
                b(iA11);
                b(iA13);
                b(iA12);
                b(iA11);
                return;
            case Opcodes.DUP2_X2 /* 94 */:
                int iA14 = a();
                int iA15 = a();
                int iA16 = a();
                int iA17 = a();
                b(iA15);
                b(iA14);
                b(iA17);
                b(iA16);
                b(iA15);
                b(iA14);
                return;
            case Opcodes.SWAP /* 95 */:
                int iA18 = a();
                int iA19 = a();
                b(iA18);
                b(iA19);
                return;
            case Opcodes.IADD /* 96 */:
            case 100:
            case Opcodes.IMUL /* 104 */:
            case Opcodes.IDIV /* 108 */:
            case Opcodes.IREM /* 112 */:
            case Opcodes.ISHL /* 120 */:
            case Opcodes.ISHR /* 122 */:
            case Opcodes.IUSHR /* 124 */:
            case Opcodes.IAND /* 126 */:
            case 128:
            case Opcodes.IXOR /* 130 */:
            case Opcodes.L2I /* 136 */:
            case Opcodes.D2I /* 142 */:
            case Opcodes.FCMPL /* 149 */:
            case Opcodes.FCMPG /* 150 */:
                c(2);
                b(16777217);
                return;
            case Opcodes.LADD /* 97 */:
            case Opcodes.LSUB /* 101 */:
            case Opcodes.LMUL /* 105 */:
            case Opcodes.LDIV /* 109 */:
            case Opcodes.LREM /* 113 */:
            case Opcodes.LAND /* 127 */:
            case Opcodes.LOR /* 129 */:
            case Opcodes.LXOR /* 131 */:
                c(4);
                b(16777220);
                b(16777216);
                return;
            case Opcodes.FADD /* 98 */:
            case Opcodes.FSUB /* 102 */:
            case Opcodes.FMUL /* 106 */:
            case Opcodes.FDIV /* 110 */:
            case Opcodes.FREM /* 114 */:
            case Opcodes.L2F /* 137 */:
            case 144:
                c(2);
                b(16777218);
                return;
            case Opcodes.DADD /* 99 */:
            case Opcodes.DSUB /* 103 */:
            case Opcodes.DMUL /* 107 */:
            case Opcodes.DDIV /* 111 */:
            case Opcodes.DREM /* 115 */:
                c(4);
                b(16777219);
                b(16777216);
                return;
            case Opcodes.LSHL /* 121 */:
            case Opcodes.LSHR /* 123 */:
            case Opcodes.LUSHR /* 125 */:
                c(3);
                b(16777220);
                b(16777216);
                return;
            case Opcodes.IINC /* 132 */:
                a(i2, 16777217);
                return;
            case Opcodes.I2L /* 133 */:
            case Opcodes.F2L /* 140 */:
                c(1);
                b(16777220);
                b(16777216);
                return;
            case Opcodes.I2F /* 134 */:
                c(1);
                b(16777218);
                return;
            case Opcodes.I2D /* 135 */:
            case Opcodes.F2D /* 141 */:
                c(1);
                b(16777219);
                b(16777216);
                return;
            case Opcodes.F2I /* 139 */:
            case Opcodes.ARRAYLENGTH /* 190 */:
            case Opcodes.INSTANCEOF /* 193 */:
                c(1);
                b(16777217);
                return;
            case Opcodes.LCMP /* 148 */:
            case Opcodes.DCMPL /* 151 */:
            case Opcodes.DCMPG /* 152 */:
                c(4);
                b(16777217);
                return;
            case Opcodes.JSR /* 168 */:
            case Opcodes.RET /* 169 */:
                throw new RuntimeException("JSR/RET are not supported with computeFrames option");
            case Opcodes.GETSTATIC /* 178 */:
                a(classWriter, item.i);
                return;
            case Opcodes.PUTSTATIC /* 179 */:
                a(item.i);
                return;
            case Opcodes.GETFIELD /* 180 */:
                c(1);
                a(classWriter, item.i);
                return;
            case Opcodes.PUTFIELD /* 181 */:
                a(item.i);
                a();
                return;
            case Opcodes.INVOKEVIRTUAL /* 182 */:
            case Opcodes.INVOKESPECIAL /* 183 */:
            case Opcodes.INVOKESTATIC /* 184 */:
            case Opcodes.INVOKEINTERFACE /* 185 */:
                a(item.i);
                if (i != 184) {
                    int iA20 = a();
                    if (i == 183 && item.h.charAt(0) == '<') {
                        d(iA20);
                    }
                }
                a(classWriter, item.i);
                return;
            case Opcodes.NEW /* 187 */:
                b(25165824 | classWriter.a(item.g, i2));
                return;
            case Opcodes.NEWARRAY /* 188 */:
                a();
                switch (i2) {
                    case 4:
                        b(285212681);
                        return;
                    case 5:
                        b(285212683);
                        return;
                    case 6:
                        b(285212674);
                        return;
                    case 7:
                        b(285212675);
                        return;
                    case 8:
                        b(285212682);
                        return;
                    case 9:
                        b(285212684);
                        return;
                    case 10:
                        b(285212673);
                        return;
                    default:
                        b(285212676);
                        return;
                }
            case Opcodes.ANEWARRAY /* 189 */:
                String str = item.g;
                a();
                if (str.charAt(0) == '[') {
                    a(classWriter, new StringBuffer().append('[').append(str).toString());
                    return;
                } else {
                    b(292552704 | classWriter.c(str));
                    return;
                }
            case Opcodes.CHECKCAST /* 192 */:
                String str2 = item.g;
                a();
                if (str2.charAt(0) == '[') {
                    a(classWriter, str2);
                    return;
                } else {
                    b(24117248 | classWriter.c(str2));
                    return;
                }
        }
    }

    boolean a(ClassWriter classWriter, Frame frame, int i) {
        int iA;
        int i2;
        boolean zA = false;
        int length = this.c.length;
        int length2 = this.d.length;
        if (frame.c == null) {
            frame.c = new int[length];
            zA = true;
        }
        int i3 = 0;
        while (i3 < length) {
            if (this.e == null || i3 >= this.e.length || (i2 = this.e[i3]) == 0) {
                iA = this.c[i3];
            } else {
                int i4 = i2 & (-268435456);
                int i5 = i2 & 251658240;
                iA = i5 == 33554432 ? i4 + this.c[i2 & 16777215] : i5 == 50331648 ? i4 + this.d[length2 - (i2 & 16777215)] : i2;
            }
            if (this.i != null) {
                iA = a(classWriter, iA);
            }
            zA |= a(classWriter, iA, frame.c, i3);
            i3++;
        }
        if (i > 0) {
            for (int i6 = 0; i6 < length; i6++) {
                zA |= a(classWriter, this.c[i6], frame.c, i6);
            }
            if (frame.d == null) {
                frame.d = new int[1];
                zA = true;
            }
            return zA | a(classWriter, i, frame.d, 0);
        }
        int length3 = this.d.length + this.b.f;
        if (frame.d == null) {
            frame.d = new int[length3 + this.g];
            zA = true;
        }
        for (int i7 = 0; i7 < length3; i7++) {
            int iA2 = this.d[i7];
            if (this.i != null) {
                iA2 = a(classWriter, iA2);
            }
            zA |= a(classWriter, iA2, frame.d, i7);
        }
        for (int i8 = 0; i8 < this.g; i8++) {
            int i9 = this.f[i8];
            int i10 = i9 & (-268435456);
            int i11 = i9 & 251658240;
            int iA3 = i11 == 33554432 ? i10 + this.c[i9 & 16777215] : i11 == 50331648 ? i10 + this.d[length2 - (i9 & 16777215)] : i9;
            if (this.i != null) {
                iA3 = a(classWriter, iA3);
            }
            zA |= a(classWriter, iA3, frame.d, length3 + i8);
        }
        return zA;
    }

    private static boolean a(ClassWriter classWriter, int i, int[] iArr, int i2) {
        int iA;
        int i3 = iArr[i2];
        if (i3 == i) {
            return false;
        }
        if ((i & 268435455) == 16777221) {
            if (i3 == 16777221) {
                return false;
            }
            i = 16777221;
        }
        if (i3 == 0) {
            iArr[i2] = i;
            return true;
        }
        if ((i3 & 267386880) == 24117248 || (i3 & (-268435456)) != 0) {
            if (i == 16777221) {
                return false;
            }
            iA = (i & (-1048576)) == (i3 & (-1048576)) ? (i3 & 267386880) == 24117248 ? (i & (-268435456)) | 24117248 | classWriter.a(i & 1048575, i3 & 1048575) : 24117248 | classWriter.c("java/lang/Object") : ((i & 267386880) == 24117248 || (i & (-268435456)) != 0) ? 24117248 | classWriter.c("java/lang/Object") : 16777216;
        } else if (i3 == 16777221) {
            iA = ((i & 267386880) == 24117248 || (i & (-268435456)) != 0) ? i : 16777216;
        } else {
            iA = 16777216;
        }
        if (i3 == iA) {
            return false;
        }
        iArr[i2] = iA;
        return true;
    }

    static {
        int[] iArr = new int[HttpConnection.HTTP_ACCEPTED];
        for (int i = 0; i < iArr.length; i++) {
            iArr[i] = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE".charAt(i) - 'E';
        }
        a = iArr;
    }
}
