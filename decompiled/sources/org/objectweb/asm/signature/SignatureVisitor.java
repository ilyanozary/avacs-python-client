package org.objectweb.asm.signature;

/* loaded from: avacs.jar:org/objectweb/asm/signature/SignatureVisitor.class */
public interface SignatureVisitor {
    public static final char EXTENDS = '+';
    public static final char SUPER = '-';
    public static final char INSTANCEOF = '=';

    void visitFormalTypeParameter(String str);

    SignatureVisitor visitClassBound();

    SignatureVisitor visitInterfaceBound();

    SignatureVisitor visitSuperclass();

    SignatureVisitor visitInterface();

    SignatureVisitor visitParameterType();

    SignatureVisitor visitReturnType();

    SignatureVisitor visitExceptionType();

    void visitBaseType(char c);

    void visitTypeVariable(String str);

    SignatureVisitor visitArrayType();

    void visitClassType(String str);

    void visitInnerClassType(String str);

    void visitTypeArgument();

    SignatureVisitor visitTypeArgument(char c);

    void visitEnd();
}
