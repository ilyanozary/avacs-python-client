package org.objectweb.asm;

/* loaded from: avacs.jar:org/objectweb/asm/FieldVisitor.class */
public interface FieldVisitor {
    AnnotationVisitor visitAnnotation(String str, boolean z);

    void visitAttribute(Attribute attribute);

    void visitEnd();
}
