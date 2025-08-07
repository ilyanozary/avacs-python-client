package org.objectweb.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* loaded from: avacs.jar:org/objectweb/asm/Type.class */
public class Type {
    public static final int VOID = 0;
    public static final int BOOLEAN = 1;
    public static final int CHAR = 2;
    public static final int BYTE = 3;
    public static final int SHORT = 4;
    public static final int INT = 5;
    public static final int FLOAT = 6;
    public static final int LONG = 7;
    public static final int DOUBLE = 8;
    public static final int ARRAY = 9;
    public static final int OBJECT = 10;
    public static final Type VOID_TYPE = new Type(0);
    public static final Type BOOLEAN_TYPE = new Type(1);
    public static final Type CHAR_TYPE = new Type(2);
    public static final Type BYTE_TYPE = new Type(3);
    public static final Type SHORT_TYPE = new Type(4);
    public static final Type INT_TYPE = new Type(5);
    public static final Type FLOAT_TYPE = new Type(6);
    public static final Type LONG_TYPE = new Type(7);
    public static final Type DOUBLE_TYPE = new Type(8);
    private final int a;
    private final char[] b;
    private final int c;
    private final int d;

    private Type(int i) {
        this(i, null, 0, 1);
    }

    private Type(int i, char[] cArr, int i2, int i3) {
        this.a = i;
        this.b = cArr;
        this.c = i2;
        this.d = i3;
    }

    public static Type getType(String str) {
        return a(str.toCharArray(), 0);
    }

    public static Type getObjectType(String str) {
        char[] charArray = str.toCharArray();
        return new Type(charArray[0] == '[' ? 9 : 10, charArray, 0, charArray.length);
    }

    public static Type getType(Class cls) {
        return cls.isPrimitive() ? cls == Integer.TYPE ? INT_TYPE : cls == Void.TYPE ? VOID_TYPE : cls == Boolean.TYPE ? BOOLEAN_TYPE : cls == Byte.TYPE ? BYTE_TYPE : cls == Character.TYPE ? CHAR_TYPE : cls == Short.TYPE ? SHORT_TYPE : cls == Double.TYPE ? DOUBLE_TYPE : cls == Float.TYPE ? FLOAT_TYPE : LONG_TYPE : getType(getDescriptor(cls));
    }

    public static Type[] getArgumentTypes(String str) {
        int i;
        char[] charArray = str.toCharArray();
        int i2 = 1;
        int i3 = 0;
        while (true) {
            int i4 = i2;
            i2++;
            char c = charArray[i4];
            if (c == ')') {
                break;
            }
            if (c == 'L') {
                do {
                    i = i2;
                    i2++;
                } while (charArray[i] != ';');
                i3++;
            } else if (c != '[') {
                i3++;
            }
        }
        Type[] typeArr = new Type[i3];
        int i5 = 1;
        int i6 = 0;
        while (charArray[i5] != ')') {
            typeArr[i6] = a(charArray, i5);
            i5 += typeArr[i6].d + (typeArr[i6].a == 10 ? 2 : 0);
            i6++;
        }
        return typeArr;
    }

    public static Type[] getArgumentTypes(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Type[] typeArr = new Type[parameterTypes.length];
        for (int length = parameterTypes.length - 1; length >= 0; length--) {
            typeArr[length] = getType(parameterTypes[length]);
        }
        return typeArr;
    }

    public static Type getReturnType(String str) {
        return a(str.toCharArray(), str.indexOf(41) + 1);
    }

    public static Type getReturnType(Method method) {
        return getType(method.getReturnType());
    }

    private static Type a(char[] cArr, int i) {
        switch (cArr[i]) {
            case 'B':
                return BYTE_TYPE;
            case 'C':
                return CHAR_TYPE;
            case 'D':
                return DOUBLE_TYPE;
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
            case Opcodes.POP /* 87 */:
            case Opcodes.POP2 /* 88 */:
            case Opcodes.DUP /* 89 */:
            default:
                int i2 = 1;
                while (cArr[i + i2] != ';') {
                    i2++;
                }
                return new Type(10, cArr, i + 1, i2 - 1);
            case 'F':
                return FLOAT_TYPE;
            case 'I':
                return INT_TYPE;
            case 'J':
                return LONG_TYPE;
            case Opcodes.AASTORE /* 83 */:
                return SHORT_TYPE;
            case Opcodes.SASTORE /* 86 */:
                return VOID_TYPE;
            case Opcodes.DUP_X1 /* 90 */:
                return BOOLEAN_TYPE;
            case Opcodes.DUP_X2 /* 91 */:
                int i3 = 1;
                while (cArr[i + i3] == '[') {
                    i3++;
                }
                if (cArr[i + i3] == 'L') {
                    do {
                        i3++;
                    } while (cArr[i + i3] != ';');
                }
                return new Type(9, cArr, i, i3 + 1);
        }
    }

    public int getSort() {
        return this.a;
    }

    public int getDimensions() {
        int i = 1;
        while (this.b[this.c + i] == '[') {
            i++;
        }
        return i;
    }

    public Type getElementType() {
        return a(this.b, this.c + getDimensions());
    }

    public String getClassName() {
        switch (this.a) {
            case 0:
                return "void";
            case 1:
                return "boolean";
            case 2:
                return "char";
            case 3:
                return "byte";
            case 4:
                return "short";
            case 5:
                return "int";
            case 6:
                return "float";
            case 7:
                return "long";
            case 8:
                return "double";
            case 9:
                StringBuffer stringBuffer = new StringBuffer(getElementType().getClassName());
                for (int dimensions = getDimensions(); dimensions > 0; dimensions--) {
                    stringBuffer.append("[]");
                }
                return stringBuffer.toString();
            default:
                return new String(this.b, this.c, this.d).replace('/', '.');
        }
    }

    public String getInternalName() {
        return new String(this.b, this.c, this.d);
    }

    public String getDescriptor() {
        StringBuffer stringBuffer = new StringBuffer();
        a(stringBuffer);
        return stringBuffer.toString();
    }

    public static String getMethodDescriptor(Type type, Type[] typeArr) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        for (Type type2 : typeArr) {
            type2.a(stringBuffer);
        }
        stringBuffer.append(')');
        type.a(stringBuffer);
        return stringBuffer.toString();
    }

    private void a(StringBuffer stringBuffer) {
        switch (this.a) {
            case 0:
                stringBuffer.append('V');
                break;
            case 1:
                stringBuffer.append('Z');
                break;
            case 2:
                stringBuffer.append('C');
                break;
            case 3:
                stringBuffer.append('B');
                break;
            case 4:
                stringBuffer.append('S');
                break;
            case 5:
                stringBuffer.append('I');
                break;
            case 6:
                stringBuffer.append('F');
                break;
            case 7:
                stringBuffer.append('J');
                break;
            case 8:
                stringBuffer.append('D');
                break;
            case 9:
                stringBuffer.append(this.b, this.c, this.d);
                break;
            default:
                stringBuffer.append('L');
                stringBuffer.append(this.b, this.c, this.d);
                stringBuffer.append(';');
                break;
        }
    }

    public static String getInternalName(Class cls) {
        return cls.getName().replace('.', '/');
    }

    public static String getDescriptor(Class cls) {
        StringBuffer stringBuffer = new StringBuffer();
        a(stringBuffer, cls);
        return stringBuffer.toString();
    }

    public static String getConstructorDescriptor(Constructor constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        for (Class<?> cls : parameterTypes) {
            a(stringBuffer, cls);
        }
        return stringBuffer.append(")V").toString();
    }

    public static String getMethodDescriptor(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('(');
        for (Class<?> cls : parameterTypes) {
            a(stringBuffer, cls);
        }
        stringBuffer.append(')');
        a(stringBuffer, method.getReturnType());
        return stringBuffer.toString();
    }

    private static void a(StringBuffer stringBuffer, Class cls) {
        Class componentType = cls;
        while (true) {
            Class cls2 = componentType;
            if (cls2.isPrimitive()) {
                stringBuffer.append(cls2 == Integer.TYPE ? 'I' : cls2 == Void.TYPE ? 'V' : cls2 == Boolean.TYPE ? 'Z' : cls2 == Byte.TYPE ? 'B' : cls2 == Character.TYPE ? 'C' : cls2 == Short.TYPE ? 'S' : cls2 == Double.TYPE ? 'D' : cls2 == Float.TYPE ? 'F' : 'J');
                return;
            }
            if (!cls2.isArray()) {
                stringBuffer.append('L');
                String name = cls2.getName();
                int length = name.length();
                for (int i = 0; i < length; i++) {
                    char cCharAt = name.charAt(i);
                    stringBuffer.append(cCharAt == '.' ? '/' : cCharAt);
                }
                stringBuffer.append(';');
                return;
            }
            stringBuffer.append('[');
            componentType = cls2.getComponentType();
        }
    }

    public int getSize() {
        return (this.a == 7 || this.a == 8) ? 2 : 1;
    }

    public int getOpcode(int i) {
        if (i != 46 && i != 79) {
            switch (this.a) {
                case 0:
                    return i + 5;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                    return i;
                case 6:
                    return i + 2;
                case 7:
                    return i + 1;
                case 8:
                    return i + 3;
                default:
                    return i + 4;
            }
        }
        switch (this.a) {
            case 1:
            case 3:
                return i + 5;
            case 2:
                return i + 6;
            case 4:
                return i + 7;
            case 5:
                return i;
            case 6:
                return i + 2;
            case 7:
                return i + 1;
            case 8:
                return i + 3;
            default:
                return i + 4;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Type)) {
            return false;
        }
        Type type = (Type) obj;
        if (this.a != type.a) {
            return false;
        }
        if (this.a != 10 && this.a != 9) {
            return true;
        }
        if (this.d != type.d) {
            return false;
        }
        int i = this.c;
        int i2 = type.c;
        int i3 = i + this.d;
        while (i < i3) {
            if (this.b[i] != type.b[i2]) {
                return false;
            }
            i++;
            i2++;
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v10, types: [int] */
    public int hashCode() {
        char c = 13 * this.a;
        if (this.a == 10 || this.a == 9) {
            int i = this.c;
            int i2 = i + this.d;
            while (i < i2) {
                c = 17 * (c + this.b[i]);
                i++;
            }
        }
        return c;
    }

    public String toString() {
        return getDescriptor();
    }
}
