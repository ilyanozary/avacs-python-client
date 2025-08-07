package org.objectweb.asm;

import javax.microedition.lcdui.TextField;

/* loaded from: avacs.jar:org/objectweb/asm/FieldWriter.class */
final class FieldWriter implements FieldVisitor {
    FieldWriter a;
    private final ClassWriter b;
    private final int c;
    private final int d;
    private final int e;
    private int f;
    private int g;
    private AnnotationWriter h;
    private AnnotationWriter i;
    private Attribute j;

    FieldWriter(ClassWriter classWriter, int i, String str, String str2, String str3, Object obj) {
        if (classWriter.y == null) {
            classWriter.y = this;
        } else {
            classWriter.z.a = this;
        }
        classWriter.z = this;
        this.b = classWriter;
        this.c = i;
        this.d = classWriter.newUTF8(str);
        this.e = classWriter.newUTF8(str2);
        if (str3 != null) {
            this.f = classWriter.newUTF8(str3);
        }
        if (obj != null) {
            this.g = classWriter.a(obj).a;
        }
    }

    @Override // org.objectweb.asm.FieldVisitor
    public AnnotationVisitor visitAnnotation(String str, boolean z) {
        ByteVector byteVector = new ByteVector();
        byteVector.putShort(this.b.newUTF8(str)).putShort(0);
        AnnotationWriter annotationWriter = new AnnotationWriter(this.b, true, byteVector, byteVector, 2);
        if (z) {
            annotationWriter.g = this.h;
            this.h = annotationWriter;
        } else {
            annotationWriter.g = this.i;
            this.i = annotationWriter;
        }
        return annotationWriter;
    }

    @Override // org.objectweb.asm.FieldVisitor
    public void visitAttribute(Attribute attribute) {
        attribute.a = this.j;
        this.j = attribute;
    }

    @Override // org.objectweb.asm.FieldVisitor
    public void visitEnd() {
    }

    int a() {
        int iA = 8;
        if (this.g != 0) {
            this.b.newUTF8("ConstantValue");
            iA = 8 + 8;
        }
        if ((this.c & 4096) != 0 && (this.b.b & TextField.CONSTRAINT_MASK) < 49) {
            this.b.newUTF8("Synthetic");
            iA += 6;
        }
        if ((this.c & 131072) != 0) {
            this.b.newUTF8("Deprecated");
            iA += 6;
        }
        if (this.f != 0) {
            this.b.newUTF8("Signature");
            iA += 8;
        }
        if (this.h != null) {
            this.b.newUTF8("RuntimeVisibleAnnotations");
            iA += 8 + this.h.a();
        }
        if (this.i != null) {
            this.b.newUTF8("RuntimeInvisibleAnnotations");
            iA += 8 + this.i.a();
        }
        if (this.j != null) {
            iA += this.j.a(this.b, null, 0, -1, -1);
        }
        return iA;
    }

    void a(ByteVector byteVector) {
        byteVector.putShort(this.c).putShort(this.d).putShort(this.e);
        int iA = 0;
        if (this.g != 0) {
            iA = 0 + 1;
        }
        if ((this.c & 4096) != 0 && (this.b.b & TextField.CONSTRAINT_MASK) < 49) {
            iA++;
        }
        if ((this.c & 131072) != 0) {
            iA++;
        }
        if (this.f != 0) {
            iA++;
        }
        if (this.h != null) {
            iA++;
        }
        if (this.i != null) {
            iA++;
        }
        if (this.j != null) {
            iA += this.j.a();
        }
        byteVector.putShort(iA);
        if (this.g != 0) {
            byteVector.putShort(this.b.newUTF8("ConstantValue"));
            byteVector.putInt(2).putShort(this.g);
        }
        if ((this.c & 4096) != 0 && (this.b.b & TextField.CONSTRAINT_MASK) < 49) {
            byteVector.putShort(this.b.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.c & 131072) != 0) {
            byteVector.putShort(this.b.newUTF8("Deprecated")).putInt(0);
        }
        if (this.f != 0) {
            byteVector.putShort(this.b.newUTF8("Signature"));
            byteVector.putInt(2).putShort(this.f);
        }
        if (this.h != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeVisibleAnnotations"));
            this.h.a(byteVector);
        }
        if (this.i != null) {
            byteVector.putShort(this.b.newUTF8("RuntimeInvisibleAnnotations"));
            this.i.a(byteVector);
        }
        if (this.j != null) {
            this.j.a(this.b, null, 0, -1, -1, byteVector);
        }
    }
}
