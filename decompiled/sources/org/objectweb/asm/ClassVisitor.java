package org.objectweb.asm;

/* loaded from: avacs.jar:org/objectweb/asm/ClassVisitor.class */
public interface ClassVisitor {
    void visit(int i, int i2, String str, String str2, String str3, String[] strArr);

    void visitSource(String str, String str2);

    void visitOuterClass(String str, String str2, String str3);

    AnnotationVisitor visitAnnotation(String str, boolean z);

    void visitAttribute(Attribute attribute);

    void visitInnerClass(String str, String str2, String str3, int i);

    FieldVisitor visitField(int i, String str, String str2, String str3, Object obj);

    MethodVisitor visitMethod(int i, String str, String str2, String str3, String[] strArr);

    void visitEnd();
}
