package org.objectweb.asm;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: avacs.jar:org/objectweb/asm/ClassReader.class */
public class ClassReader {
    public static final int SKIP_CODE = 1;
    public static final int SKIP_DEBUG = 2;
    public static final int SKIP_FRAMES = 4;
    public static final int EXPAND_FRAMES = 8;
    public final byte[] b;
    private final int[] a;
    private final String[] c;
    private final int d;
    public final int header;

    public ClassReader(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public ClassReader(byte[] bArr, int i, int i2) {
        int unsignedShort;
        this.b = bArr;
        this.a = new int[readUnsignedShort(i + 8)];
        int length = this.a.length;
        this.c = new String[length];
        int i3 = 0;
        int i4 = i + 10;
        int i5 = 1;
        while (i5 < length) {
            this.a[i5] = i4 + 1;
            switch (bArr[i4]) {
                case 1:
                    unsignedShort = 3 + readUnsignedShort(i4 + 1);
                    if (unsignedShort <= i3) {
                        break;
                    } else {
                        i3 = unsignedShort;
                        break;
                    }
                case 2:
                case 7:
                case 8:
                default:
                    unsignedShort = 3;
                    break;
                case 3:
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                    unsignedShort = 5;
                    break;
                case 5:
                case 6:
                    unsignedShort = 9;
                    i5++;
                    break;
            }
            i4 += unsignedShort;
            i5++;
        }
        this.d = i3;
        this.header = i4;
    }

    public int getAccess() {
        return readUnsignedShort(this.header);
    }

    public String getClassName() {
        return readClass(this.header + 2, new char[this.d]);
    }

    public String getSuperName() {
        int i = this.a[readUnsignedShort(this.header + 4)];
        if (i == 0) {
            return null;
        }
        return readUTF8(i, new char[this.d]);
    }

    public String[] getInterfaces() {
        int i = this.header + 6;
        int unsignedShort = readUnsignedShort(i);
        String[] strArr = new String[unsignedShort];
        if (unsignedShort > 0) {
            char[] cArr = new char[this.d];
            for (int i2 = 0; i2 < unsignedShort; i2++) {
                i += 2;
                strArr[i2] = readClass(i, cArr);
            }
        }
        return strArr;
    }

    void a(ClassWriter classWriter) {
        char[] cArr = new char[this.d];
        int length = this.a.length;
        Item[] itemArr = new Item[length];
        int i = 1;
        while (i < length) {
            int i2 = this.a[i];
            byte b = this.b[i2 - 1];
            Item item = new Item(i);
            switch (b) {
                case 1:
                    String str = this.c[i];
                    if (str == null) {
                        int i3 = this.a[i];
                        String strA = a(i3 + 2, readUnsignedShort(i3), cArr);
                        this.c[i] = strA;
                        str = strA;
                    }
                    item.a(b, str, null, null);
                    break;
                case 2:
                case 7:
                case 8:
                default:
                    item.a(b, readUTF8(i2, cArr), null, null);
                    break;
                case 3:
                    item.a(readInt(i2));
                    break;
                case 4:
                    item.a(Float.intBitsToFloat(readInt(i2)));
                    break;
                case 5:
                    item.a(readLong(i2));
                    i++;
                    break;
                case 6:
                    item.a(Double.longBitsToDouble(readLong(i2)));
                    i++;
                    break;
                case 9:
                case 10:
                case 11:
                    int i4 = this.a[readUnsignedShort(i2 + 2)];
                    item.a(b, readClass(i2, cArr), readUTF8(i4, cArr), readUTF8(i4 + 2, cArr));
                    break;
                case 12:
                    item.a(b, readUTF8(i2, cArr), readUTF8(i2 + 2, cArr), null);
                    break;
            }
            int length2 = item.j % itemArr.length;
            item.k = itemArr[length2];
            itemArr[length2] = item;
            i++;
        }
        int i5 = this.a[1] - 1;
        classWriter.d.putByteArray(this.b, i5, this.header - i5);
        classWriter.e = itemArr;
        classWriter.f = (int) (0.75d * length);
        classWriter.c = length;
    }

    public ClassReader(InputStream inputStream) throws IOException {
        this(a(inputStream));
    }

    public ClassReader(String str) throws IOException {
        this(ClassLoader.getSystemResourceAsStream(new StringBuffer().append(str.replace('.', '/')).append(".class").toString()));
    }

    private static byte[] a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException("Class not found");
        }
        byte[] bArr = new byte[inputStream.available()];
        int i = 0;
        while (true) {
            int i2 = inputStream.read(bArr, i, bArr.length - i);
            if (i2 == -1) {
                break;
            }
            i += i2;
            if (i == bArr.length) {
                byte[] bArr2 = new byte[bArr.length + 1000];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                bArr = bArr2;
            }
        }
        if (i < bArr.length) {
            byte[] bArr3 = new byte[i];
            System.arraycopy(bArr, 0, bArr3, 0, i);
            bArr = bArr3;
        }
        return bArr;
    }

    public void accept(ClassVisitor classVisitor, int i) {
        accept(classVisitor, new Attribute[0], i);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:389:0x0d51. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0916  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0a99  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0b42  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x0cf6  */
    /* JADX WARN: Removed duplicated region for block: B:414:0x0e8a  */
    /* JADX WARN: Removed duplicated region for block: B:522:0x1420  */
    /* JADX WARN: Removed duplicated region for block: B:529:0x143b  */
    /* JADX WARN: Removed duplicated region for block: B:536:0x149b  */
    /* JADX WARN: Removed duplicated region for block: B:550:0x1534 A[LOOP:43: B:548:0x152f->B:550:0x1534, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:554:0x1561  */
    /* JADX WARN: Removed duplicated region for block: B:606:0x1568 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void accept(org.objectweb.asm.ClassVisitor r10, org.objectweb.asm.Attribute[] r11, int r12) {
        /*
            Method dump skipped, instructions count: 5493
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.objectweb.asm.ClassReader.accept(org.objectweb.asm.ClassVisitor, org.objectweb.asm.Attribute[], int):void");
    }

    private void a(int i, String str, char[] cArr, boolean z, MethodVisitor methodVisitor) {
        int iA = i + 1;
        int i2 = this.b[i] & 255;
        int length = Type.getArgumentTypes(str).length - i2;
        int i3 = 0;
        while (i3 < length) {
            AnnotationVisitor annotationVisitorVisitParameterAnnotation = methodVisitor.visitParameterAnnotation(i3, "Ljava/lang/Synthetic;", false);
            if (annotationVisitorVisitParameterAnnotation != null) {
                annotationVisitorVisitParameterAnnotation.visitEnd();
            }
            i3++;
        }
        while (i3 < i2 + length) {
            iA += 2;
            for (int unsignedShort = readUnsignedShort(iA); unsignedShort > 0; unsignedShort--) {
                iA = a(iA + 2, cArr, true, methodVisitor.visitParameterAnnotation(i3, readUTF8(iA, cArr), z));
            }
            i3++;
        }
    }

    private int a(int i, char[] cArr, boolean z, AnnotationVisitor annotationVisitor) {
        int unsignedShort = readUnsignedShort(i);
        int iA = i + 2;
        if (z) {
            while (unsignedShort > 0) {
                iA = a(iA + 2, cArr, readUTF8(iA, cArr), annotationVisitor);
                unsignedShort--;
            }
        } else {
            while (unsignedShort > 0) {
                iA = a(iA, cArr, (String) null, annotationVisitor);
                unsignedShort--;
            }
        }
        if (annotationVisitor != null) {
            annotationVisitor.visitEnd();
        }
        return iA;
    }

    private int a(int i, char[] cArr, String str, AnnotationVisitor annotationVisitor) {
        if (annotationVisitor == null) {
            switch (this.b[i] & 255) {
                case 64:
                    return a(i + 3, cArr, true, (AnnotationVisitor) null);
                case Opcodes.DUP_X2 /* 91 */:
                    return a(i + 1, cArr, false, (AnnotationVisitor) null);
                case Opcodes.LSUB /* 101 */:
                    return i + 5;
                default:
                    return i + 3;
            }
        }
        int iA = i + 1;
        switch (this.b[i] & 255) {
            case 64:
                iA = a(iA + 2, cArr, true, annotationVisitor.visitAnnotation(str, readUTF8(iA, cArr)));
                break;
            case 66:
                annotationVisitor.visit(str, new Byte((byte) readInt(this.a[readUnsignedShort(iA)])));
                iA += 2;
                break;
            case 67:
                annotationVisitor.visit(str, new Character((char) readInt(this.a[readUnsignedShort(iA)])));
                iA += 2;
                break;
            case 68:
            case 70:
            case 73:
            case 74:
                annotationVisitor.visit(str, readConst(readUnsignedShort(iA), cArr));
                iA += 2;
                break;
            case Opcodes.AASTORE /* 83 */:
                annotationVisitor.visit(str, new Short((short) readInt(this.a[readUnsignedShort(iA)])));
                iA += 2;
                break;
            case Opcodes.DUP_X1 /* 90 */:
                annotationVisitor.visit(str, readInt(this.a[readUnsignedShort(iA)]) == 0 ? Boolean.FALSE : Boolean.TRUE);
                iA += 2;
                break;
            case Opcodes.DUP_X2 /* 91 */:
                int unsignedShort = readUnsignedShort(iA);
                int i2 = iA + 2;
                if (unsignedShort == 0) {
                    return a(i2 - 2, cArr, false, annotationVisitor.visitArray(str));
                }
                int i3 = i2 + 1;
                switch (this.b[i2] & 255) {
                    case 66:
                        byte[] bArr = new byte[unsignedShort];
                        for (int i4 = 0; i4 < unsignedShort; i4++) {
                            bArr[i4] = (byte) readInt(this.a[readUnsignedShort(i3)]);
                            i3 += 3;
                        }
                        annotationVisitor.visit(str, bArr);
                        iA = i3 - 1;
                        break;
                    case 67:
                        char[] cArr2 = new char[unsignedShort];
                        for (int i5 = 0; i5 < unsignedShort; i5++) {
                            cArr2[i5] = (char) readInt(this.a[readUnsignedShort(i3)]);
                            i3 += 3;
                        }
                        annotationVisitor.visit(str, cArr2);
                        iA = i3 - 1;
                        break;
                    case 68:
                        double[] dArr = new double[unsignedShort];
                        for (int i6 = 0; i6 < unsignedShort; i6++) {
                            dArr[i6] = Double.longBitsToDouble(readLong(this.a[readUnsignedShort(i3)]));
                            i3 += 3;
                        }
                        annotationVisitor.visit(str, dArr);
                        iA = i3 - 1;
                        break;
                    case 69:
                    case 71:
                    case 72:
                    case 75:
                    case 76:
                    case 77:
                    case 78:
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
                        iA = a(i3 - 3, cArr, false, annotationVisitor.visitArray(str));
                        break;
                    case 70:
                        float[] fArr = new float[unsignedShort];
                        for (int i7 = 0; i7 < unsignedShort; i7++) {
                            fArr[i7] = Float.intBitsToFloat(readInt(this.a[readUnsignedShort(i3)]));
                            i3 += 3;
                        }
                        annotationVisitor.visit(str, fArr);
                        iA = i3 - 1;
                        break;
                    case 73:
                        int[] iArr = new int[unsignedShort];
                        for (int i8 = 0; i8 < unsignedShort; i8++) {
                            iArr[i8] = readInt(this.a[readUnsignedShort(i3)]);
                            i3 += 3;
                        }
                        annotationVisitor.visit(str, iArr);
                        iA = i3 - 1;
                        break;
                    case 74:
                        long[] jArr = new long[unsignedShort];
                        for (int i9 = 0; i9 < unsignedShort; i9++) {
                            jArr[i9] = readLong(this.a[readUnsignedShort(i3)]);
                            i3 += 3;
                        }
                        annotationVisitor.visit(str, jArr);
                        iA = i3 - 1;
                        break;
                    case Opcodes.AASTORE /* 83 */:
                        short[] sArr = new short[unsignedShort];
                        for (int i10 = 0; i10 < unsignedShort; i10++) {
                            sArr[i10] = (short) readInt(this.a[readUnsignedShort(i3)]);
                            i3 += 3;
                        }
                        annotationVisitor.visit(str, sArr);
                        iA = i3 - 1;
                        break;
                    case Opcodes.DUP_X1 /* 90 */:
                        boolean[] zArr = new boolean[unsignedShort];
                        for (int i11 = 0; i11 < unsignedShort; i11++) {
                            zArr[i11] = readInt(this.a[readUnsignedShort(i3)]) != 0;
                            i3 += 3;
                        }
                        annotationVisitor.visit(str, zArr);
                        iA = i3 - 1;
                        break;
                }
            case Opcodes.DADD /* 99 */:
                annotationVisitor.visit(str, Type.getType(readUTF8(iA, cArr)));
                iA += 2;
                break;
            case Opcodes.LSUB /* 101 */:
                annotationVisitor.visitEnum(str, readUTF8(iA, cArr), readUTF8(iA + 2, cArr));
                iA += 4;
                break;
            case Opcodes.DREM /* 115 */:
                annotationVisitor.visit(str, readUTF8(iA, cArr));
                iA += 2;
                break;
        }
        return iA;
    }

    private int a(Object[] objArr, int i, int i2, char[] cArr, Label[] labelArr) {
        int i3 = i2 + 1;
        switch (this.b[i2] & 255) {
            case 0:
                objArr[i] = Opcodes.TOP;
                break;
            case 1:
                objArr[i] = Opcodes.INTEGER;
                break;
            case 2:
                objArr[i] = Opcodes.FLOAT;
                break;
            case 3:
                objArr[i] = Opcodes.DOUBLE;
                break;
            case 4:
                objArr[i] = Opcodes.LONG;
                break;
            case 5:
                objArr[i] = Opcodes.NULL;
                break;
            case 6:
                objArr[i] = Opcodes.UNINITIALIZED_THIS;
                break;
            case 7:
                objArr[i] = readClass(i3, cArr);
                i3 += 2;
                break;
            default:
                objArr[i] = readLabel(readUnsignedShort(i3), labelArr);
                i3 += 2;
                break;
        }
        return i3;
    }

    protected Label readLabel(int i, Label[] labelArr) {
        if (labelArr[i] == null) {
            labelArr[i] = new Label();
        }
        return labelArr[i];
    }

    private Attribute a(Attribute[] attributeArr, String str, int i, int i2, char[] cArr, int i3, Label[] labelArr) {
        for (int i4 = 0; i4 < attributeArr.length; i4++) {
            if (attributeArr[i4].type.equals(str)) {
                return attributeArr[i4].read(this, i, i2, cArr, i3, labelArr);
            }
        }
        return new Attribute(str).read(this, i, i2, null, -1, null);
    }

    public int getItem(int i) {
        return this.a[i];
    }

    public int readByte(int i) {
        return this.b[i] & 255;
    }

    public int readUnsignedShort(int i) {
        byte[] bArr = this.b;
        return ((bArr[i] & 255) << 8) | (bArr[i + 1] & 255);
    }

    public short readShort(int i) {
        byte[] bArr = this.b;
        return (short) (((bArr[i] & 255) << 8) | (bArr[i + 1] & 255));
    }

    public int readInt(int i) {
        byte[] bArr = this.b;
        return ((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16) | ((bArr[i + 2] & 255) << 8) | (bArr[i + 3] & 255);
    }

    public long readLong(int i) {
        return (readInt(i) << 32) | (readInt(i + 4) & 4294967295L);
    }

    public String readUTF8(int i, char[] cArr) {
        int unsignedShort = readUnsignedShort(i);
        String str = this.c[unsignedShort];
        if (str != null) {
            return str;
        }
        int i2 = this.a[unsignedShort];
        String[] strArr = this.c;
        String strA = a(i2 + 2, readUnsignedShort(i2), cArr);
        strArr[unsignedShort] = strA;
        return strA;
    }

    private String a(int i, int i2, char[] cArr) {
        int i3 = i + i2;
        byte[] bArr = this.b;
        int i4 = 0;
        while (i < i3) {
            int i5 = i;
            i++;
            int i6 = bArr[i5] & 255;
            switch (i6 >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    int i7 = i4;
                    i4++;
                    cArr[i7] = (char) i6;
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    int i8 = i + 1;
                    byte b = bArr[i];
                    i = i8 + 1;
                    int i9 = i4;
                    i4++;
                    cArr[i9] = (char) (((i6 & 15) << 12) | ((b & 63) << 6) | (bArr[i8] & 63));
                    break;
                case 12:
                case 13:
                    i++;
                    int i10 = i4;
                    i4++;
                    cArr[i10] = (char) (((i6 & 31) << 6) | (bArr[i] & 63));
                    break;
            }
        }
        return new String(cArr, 0, i4);
    }

    public String readClass(int i, char[] cArr) {
        return readUTF8(this.a[readUnsignedShort(i)], cArr);
    }

    public Object readConst(int i, char[] cArr) {
        int i2 = this.a[i];
        switch (this.b[i2 - 1]) {
            case 3:
                return new Integer(readInt(i2));
            case 4:
                return new Float(Float.intBitsToFloat(readInt(i2)));
            case 5:
                return new Long(readLong(i2));
            case 6:
                return new Double(Double.longBitsToDouble(readLong(i2)));
            case 7:
                return Type.getObjectType(readUTF8(i2, cArr));
            default:
                return readUTF8(i2, cArr);
        }
    }
}
