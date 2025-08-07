package org.objectweb.asm;

/* loaded from: avacs.jar:org/objectweb/asm/MethodVisitor.class */
public interface MethodVisitor {
    AnnotationVisitor visitAnnotationDefault();

    AnnotationVisitor visitAnnotation(String str, boolean z);

    AnnotationVisitor visitParameterAnnotation(int i, String str, boolean z);

    void visitAttribute(Attribute attribute);

    void visitCode();

    void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2);

    void visitInsn(int i);

    void visitIntInsn(int i, int i2);

    void visitVarInsn(int i, int i2);

    void visitTypeInsn(int i, String str);

    void visitFieldInsn(int i, String str, String str2, String str3);

    void visitMethodInsn(int i, String str, String str2, String str3);

    void visitJumpInsn(int i, Label label);

    void visitLabel(Label label);

    void visitLdcInsn(Object obj);

    void visitIincInsn(int i, int i2);

    void visitTableSwitchInsn(int i, int i2, Label label, Label[] labelArr);

    void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr);

    void visitMultiANewArrayInsn(String str, int i);

    void visitTryCatchBlock(Label label, Label label2, Label label3, String str);

    void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i);

    void visitLineNumber(int i, Label label);

    void visitMaxs(int i, int i2);

    void visitEnd();
}
