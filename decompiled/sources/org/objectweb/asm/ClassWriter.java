package org.objectweb.asm;

import javax.microedition.lcdui.TextField;

/* loaded from: avacs.jar:org/objectweb/asm/ClassWriter.class */
public class ClassWriter implements ClassVisitor {
    public static final int COMPUTE_MAXS = 1;
    public static final int COMPUTE_FRAMES = 2;
    static final byte[] a;
    ClassReader J;
    int b;
    int c;
    final ByteVector d;
    Item[] e;
    int f;
    final Item g;
    final Item h;
    final Item i;
    Item[] E;
    private short D;
    private int j;
    private int k;
    String F;
    private int l;
    private int m;
    private int n;
    private int[] o;
    private int p;
    private ByteVector q;
    private int r;
    private int s;
    private AnnotationWriter t;
    private AnnotationWriter u;
    private Attribute v;
    private int w;
    private ByteVector x;
    FieldWriter y;
    FieldWriter z;
    MethodWriter A;
    MethodWriter B;
    private final boolean H;
    private final boolean G;
    boolean I;

    public ClassWriter(int i) {
        this.c = 1;
        this.d = new ByteVector();
        this.e = new Item[256];
        this.f = (int) (0.75d * this.e.length);
        this.g = new Item();
        this.h = new Item();
        this.i = new Item();
        this.H = (i & 1) != 0;
        this.G = (i & 2) != 0;
    }

    public ClassWriter(ClassReader classReader, int i) {
        this(i);
        classReader.a(this);
        this.J = classReader;
    }

    @Override // org.objectweb.asm.ClassVisitor
    public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
        this.b = i;
        this.j = i2;
        this.k = newClass(str);
        this.F = str;
        if (str2 != null) {
            this.l = newUTF8(str2);
        }
        this.m = str3 == null ? 0 : newClass(str3);
        if (strArr == null || strArr.length <= 0) {
            return;
        }
        this.n = strArr.length;
        this.o = new int[this.n];
        for (int i3 = 0; i3 < this.n; i3++) {
            this.o[i3] = newClass(strArr[i3]);
        }
    }

    @Override // org.objectweb.asm.ClassVisitor
    public void visitSource(String str, String str2) {
        if (str != null) {
            this.p = newUTF8(str);
        }
        if (str2 != null) {
            this.q = new ByteVector().putUTF8(str2);
        }
    }

    @Override // org.objectweb.asm.ClassVisitor
    public void visitOuterClass(String str, String str2, String str3) {
        this.r = newClass(str);
        if (str2 == null || str3 == null) {
            return;
        }
        this.s = newNameType(str2, str3);
    }

    @Override // org.objectweb.asm.ClassVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.g = this.t;
            this.t = annotationWriter;
        } else {
            annotationWriter.g = this.u;
            this.u = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // org.objectweb.asm.ClassVisitor
    public void visitAttribute(Attribute attribute) {
        attribute.a = this.v;
        this.v = attribute;
    }

    @Override // org.objectweb.asm.ClassVisitor
    public void visitInnerClass(String str, String str2, String str3, int i) {
        if (this.x == null) {
            this.x = new ByteVector();
        }
        this.w++;
        this.x.putShort(str == null ? 0 : newClass(str));
        this.x.putShort(str2 == null ? 0 : newClass(str2));
        this.x.putShort(str3 == null ? 0 : newUTF8(str3));
        this.x.putShort(i);
    }

    @Override // org.objectweb.asm.ClassVisitor
    public FieldVisitor visitField(int i, String str, String str2, String str3, Object obj) {
        return new FieldWriter(this, i, str, str2, str3, obj);
    }

    @Override // org.objectweb.asm.ClassVisitor
    public MethodVisitor visitMethod(int i, String str, String str2, String str3, String[] strArr) {
        return new MethodWriter(this, i, str, str2, str3, strArr, this.H, this.G);
    }

    @Override // org.objectweb.asm.ClassVisitor
    public void visitEnd() {
    }

    public byte[] toByteArray() {
        int iA = 24 + (2 * this.n);
        int i = 0;
        FieldWriter fieldWriter = this.y;
        while (true) {
            FieldWriter fieldWriter2 = fieldWriter;
            if (fieldWriter2 == null) {
                break;
            }
            i++;
            iA += fieldWriter2.a();
            fieldWriter = fieldWriter2.a;
        }
        int i2 = 0;
        MethodWriter methodWriter = this.A;
        while (true) {
            MethodWriter methodWriter2 = methodWriter;
            if (methodWriter2 == null) {
                break;
            }
            i2++;
            iA += methodWriter2.a();
            methodWriter = methodWriter2.a;
        }
        int iA2 = 0;
        if (this.l != 0) {
            iA2 = 0 + 1;
            iA += 8;
            newUTF8("Signature");
        }
        if (this.p != 0) {
            iA2++;
            iA += 8;
            newUTF8("SourceFile");
        }
        if (this.q != null) {
            iA2++;
            iA += this.q.b + 4;
            newUTF8("SourceDebugExtension");
        }
        if (this.r != 0) {
            iA2++;
            iA += 10;
            newUTF8("EnclosingMethod");
        }
        if ((this.j & 131072) != 0) {
            iA2++;
            iA += 6;
            newUTF8("Deprecated");
        }
        if ((this.j & 4096) != 0 && (this.b & TextField.CONSTRAINT_MASK) < 49) {
            iA2++;
            iA += 6;
            newUTF8("Synthetic");
        }
        if (this.x != null) {
            iA2++;
            iA += 8 + this.x.b;
            newUTF8("InnerClasses");
        }
        if (this.t != null) {
            iA2++;
            iA += 8 + this.t.a();
            newUTF8("RuntimeVisibleAnnotations");
        }
        if (this.u != null) {
            iA2++;
            iA += 8 + this.u.a();
            newUTF8("RuntimeInvisibleAnnotations");
        }
        if (this.v != null) {
            iA2 += this.v.a();
            iA += this.v.a(this, null, 0, -1, -1);
        }
        ByteVector byteVector = new ByteVector(iA + this.d.b);
        byteVector.putInt(-889275714).putInt(this.b);
        byteVector.putShort(this.c).putByteArray(this.d.a, 0, this.d.b);
        byteVector.putShort(this.j).putShort(this.k).putShort(this.m);
        byteVector.putShort(this.n);
        for (int i3 = 0; i3 < this.n; i3++) {
            byteVector.putShort(this.o[i3]);
        }
        byteVector.putShort(i);
        FieldWriter fieldWriter3 = this.y;
        while (true) {
            FieldWriter fieldWriter4 = fieldWriter3;
            if (fieldWriter4 == null) {
                break;
            }
            fieldWriter4.a(byteVector);
            fieldWriter3 = fieldWriter4.a;
        }
        byteVector.putShort(i2);
        MethodWriter methodWriter3 = this.A;
        while (true) {
            MethodWriter methodWriter4 = methodWriter3;
            if (methodWriter4 == null) {
                break;
            }
            methodWriter4.a(byteVector);
            methodWriter3 = methodWriter4.a;
        }
        byteVector.putShort(iA2);
        if (this.l != 0) {
            byteVector.putShort(newUTF8("Signature")).putInt(2).putShort(this.l);
        }
        if (this.p != 0) {
            byteVector.putShort(newUTF8("SourceFile")).putInt(2).putShort(this.p);
        }
        if (this.q != null) {
            int i4 = this.q.b - 2;
            byteVector.putShort(newUTF8("SourceDebugExtension")).putInt(i4);
            byteVector.putByteArray(this.q.a, 2, i4);
        }
        if (this.r != 0) {
            byteVector.putShort(newUTF8("EnclosingMethod")).putInt(4);
            byteVector.putShort(this.r).putShort(this.s);
        }
        if ((this.j & 131072) != 0) {
            byteVector.putShort(newUTF8("Deprecated")).putInt(0);
        }
        if ((this.j & 4096) != 0 && (this.b & TextField.CONSTRAINT_MASK) < 49) {
            byteVector.putShort(newUTF8("Synthetic")).putInt(0);
        }
        if (this.x != null) {
            byteVector.putShort(newUTF8("InnerClasses"));
            byteVector.putInt(this.x.b + 2).putShort(this.w);
            byteVector.putByteArray(this.x.a, 0, this.x.b);
        }
        if (this.t != null) {
            byteVector.putShort(newUTF8("RuntimeVisibleAnnotations"));
            this.t.a(byteVector);
        }
        if (this.u != null) {
            byteVector.putShort(newUTF8("RuntimeInvisibleAnnotations"));
            this.u.a(byteVector);
        }
        if (this.v != null) {
            this.v.a(this, null, 0, -1, -1, byteVector);
        }
        if (!this.I) {
            return byteVector.a;
        }
        ClassWriter classWriter = new ClassWriter(2);
        new ClassReader(byteVector.a).accept(classWriter, 4);
        return classWriter.toByteArray();
    }

    Item a(Object obj) {
        if (obj instanceof Integer) {
            return a(((Integer) obj).intValue());
        }
        if (obj instanceof Byte) {
            return a(((Byte) obj).intValue());
        }
        if (obj instanceof Character) {
            return a((int) ((Character) obj).charValue());
        }
        if (obj instanceof Short) {
            return a(((Short) obj).intValue());
        }
        if (obj instanceof Boolean) {
            return a(((Boolean) obj).booleanValue() ? 1 : 0);
        }
        if (obj instanceof Float) {
            return a(((Float) obj).floatValue());
        }
        if (obj instanceof Long) {
            return a(((Long) obj).longValue());
        }
        if (obj instanceof Double) {
            return a(((Double) obj).doubleValue());
        }
        if (obj instanceof String) {
            return b((String) obj);
        }
        if (!(obj instanceof Type)) {
            throw new IllegalArgumentException(new StringBuffer().append("value ").append(obj).toString());
        }
        Type type = (Type) obj;
        return a(type.getSort() == 10 ? type.getInternalName() : type.getDescriptor());
    }

    public int newConst(Object obj) {
        return a(obj).a;
    }

    public int newUTF8(String str) {
        this.g.a(1, str, null, null);
        Item itemA = a(this.g);
        if (itemA == null) {
            this.d.putByte(1).putUTF8(str);
            int i = this.c;
            this.c = i + 1;
            itemA = new Item(i, this.g);
            b(itemA);
        }
        return itemA.a;
    }

    Item a(String str) {
        this.h.a(7, str, null, null);
        Item itemA = a(this.h);
        if (itemA == null) {
            this.d.b(7, newUTF8(str));
            int i = this.c;
            this.c = i + 1;
            itemA = new Item(i, this.h);
            b(itemA);
        }
        return itemA;
    }

    public int newClass(String str) {
        return a(str).a;
    }

    Item a(String str, String str2, String str3) {
        this.i.a(9, str, str2, str3);
        Item itemA = a(this.i);
        if (itemA == null) {
            a(9, newClass(str), newNameType(str2, str3));
            int i = this.c;
            this.c = i + 1;
            itemA = new Item(i, this.i);
            b(itemA);
        }
        return itemA;
    }

    public int newField(String str, String str2, String str3) {
        return a(str, str2, str3).a;
    }

    Item a(String str, String str2, String str3, boolean z) {
        int i = z ? 11 : 10;
        this.i.a(i, str, str2, str3);
        Item itemA = a(this.i);
        if (itemA == null) {
            a(i, newClass(str), newNameType(str2, str3));
            int i2 = this.c;
            this.c = i2 + 1;
            itemA = new Item(i2, this.i);
            b(itemA);
        }
        return itemA;
    }

    public int newMethod(String str, String str2, String str3, boolean z) {
        return a(str, str2, str3, z).a;
    }

    Item a(int i) {
        this.g.a(i);
        Item itemA = a(this.g);
        if (itemA == null) {
            this.d.putByte(3).putInt(i);
            int i2 = this.c;
            this.c = i2 + 1;
            itemA = new Item(i2, this.g);
            b(itemA);
        }
        return itemA;
    }

    Item a(float f) {
        this.g.a(f);
        Item itemA = a(this.g);
        if (itemA == null) {
            this.d.putByte(4).putInt(this.g.c);
            int i = this.c;
            this.c = i + 1;
            itemA = new Item(i, this.g);
            b(itemA);
        }
        return itemA;
    }

    Item a(long j) {
        this.g.a(j);
        Item itemA = a(this.g);
        if (itemA == null) {
            this.d.putByte(5).putLong(j);
            itemA = new Item(this.c, this.g);
            b(itemA);
            this.c += 2;
        }
        return itemA;
    }

    Item a(double d) {
        this.g.a(d);
        Item itemA = a(this.g);
        if (itemA == null) {
            this.d.putByte(6).putLong(this.g.d);
            itemA = new Item(this.c, this.g);
            b(itemA);
            this.c += 2;
        }
        return itemA;
    }

    private Item b(String str) {
        this.h.a(8, str, null, null);
        Item itemA = a(this.h);
        if (itemA == null) {
            this.d.b(8, newUTF8(str));
            int i = this.c;
            this.c = i + 1;
            itemA = new Item(i, this.h);
            b(itemA);
        }
        return itemA;
    }

    public int newNameType(String str, String str2) {
        this.h.a(12, str, str2, null);
        Item itemA = a(this.h);
        if (itemA == null) {
            a(12, newUTF8(str), newUTF8(str2));
            int i = this.c;
            this.c = i + 1;
            itemA = new Item(i, this.h);
            b(itemA);
        }
        return itemA.a;
    }

    int c(String str) {
        this.g.a(13, str, null, null);
        Item itemA = a(this.g);
        if (itemA == null) {
            itemA = c(this.g);
        }
        return itemA.a;
    }

    int a(String str, int i) {
        this.g.b = 14;
        this.g.c = i;
        this.g.g = str;
        this.g.j = Integer.MAX_VALUE & (14 + str.hashCode() + i);
        Item itemA = a(this.g);
        if (itemA == null) {
            itemA = c(this.g);
        }
        return itemA.a;
    }

    private Item c(Item item) {
        this.D = (short) (this.D + 1);
        Item item2 = new Item(this.D, this.g);
        b(item2);
        if (this.E == null) {
            this.E = new Item[16];
        }
        if (this.D == this.E.length) {
            Item[] itemArr = new Item[2 * this.E.length];
            System.arraycopy(this.E, 0, itemArr, 0, this.E.length);
            this.E = itemArr;
        }
        this.E[this.D] = item2;
        return item2;
    }

    int a(int i, int i2) {
        this.h.b = 15;
        this.h.d = i | (i2 << 32);
        this.h.j = Integer.MAX_VALUE & (15 + i + i2);
        Item itemA = a(this.h);
        if (itemA == null) {
            this.h.c = c(getCommonSuperClass(this.E[i].g, this.E[i2].g));
            itemA = new Item(0, this.h);
            b(itemA);
        }
        return itemA.c;
    }

    protected String getCommonSuperClass(String str, String str2) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName(str.replace('/', '.'));
            Class<?> cls2 = Class.forName(str2.replace('/', '.'));
            if (cls.isAssignableFrom(cls2)) {
                return str;
            }
            if (cls2.isAssignableFrom(cls)) {
                return str2;
            }
            if (cls.isInterface() || cls2.isInterface()) {
                return "java/lang/Object";
            }
            do {
                cls = cls.getSuperclass();
            } while (!cls.isAssignableFrom(cls2));
            return cls.getName().replace('.', '/');
        } catch (Exception e) {
            throw new RuntimeException(e.toString());
        }
    }

    private Item a(Item item) {
        Item item2;
        Item item3 = this.e[item.j % this.e.length];
        while (true) {
            item2 = item3;
            if (item2 == null || item.a(item2)) {
                break;
            }
            item3 = item2.k;
        }
        return item2;
    }

    private void b(Item item) {
        if (this.c > this.f) {
            int length = this.e.length;
            int i = (length * 2) + 1;
            Item[] itemArr = new Item[i];
            for (int i2 = length - 1; i2 >= 0; i2--) {
                Item item2 = this.e[i2];
                while (true) {
                    Item item3 = item2;
                    if (item3 != null) {
                        int length2 = item3.j % itemArr.length;
                        Item item4 = item3.k;
                        item3.k = itemArr[length2];
                        itemArr[length2] = item3;
                        item2 = item4;
                    }
                }
            }
            this.e = itemArr;
            this.f = (int) (i * 0.75d);
        }
        int length3 = item.j % this.e.length;
        item.k = this.e[length3];
        this.e[length3] = item;
    }

    private void a(int i, int i2, int i3) {
        this.d.b(i, i2).putShort(i3);
    }

    static {
        byte[] bArr = new byte[220];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ("AAAAAAAAAAAAAAAABCKLLDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAMAAAAAAAAAAAAAAAAAAAAIIIIIIIIIIIIIIIIDNOAAAAAAGGGGGGGHAFBFAAFFAAQPIIJJIIIIIIIIIIIIIIIIII".charAt(i) - 'A');
        }
        a = bArr;
    }
}
