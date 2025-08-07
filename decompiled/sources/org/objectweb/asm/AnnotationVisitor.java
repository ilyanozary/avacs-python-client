package org.objectweb.asm;

/* loaded from: avacs.jar:org/objectweb/asm/AnnotationVisitor.class */
public interface AnnotationVisitor {
    void visit(String str, Object obj);

    void visitEnum(String str, String str2, String str3);

    AnnotationVisitor visitAnnotation(String str, String str2);

    AnnotationVisitor visitArray(String str);

    void visitEnd();
}
