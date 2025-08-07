package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.MIDlet;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:b.class */
public final class b {
    private static byte[] a;

    /* renamed from: a, reason: collision with other field name */
    private static int f148a;
    private static int b;
    private static int c;

    /* renamed from: b, reason: collision with other field name */
    private static byte[] f149b;
    private static int d;

    /* renamed from: c, reason: collision with other field name */
    private static byte[] f150c;

    /* renamed from: a, reason: collision with other field name */
    private static short[] f151a;

    /* renamed from: d, reason: collision with other field name */
    private static byte[] f152d;

    /* renamed from: b, reason: collision with other field name */
    private static short[] f153b;
    private static byte[] e;

    /* renamed from: a, reason: collision with other field name */
    private static y f154a;

    /* renamed from: a, reason: collision with other field name */
    private static boolean f155a;

    /* renamed from: a, reason: collision with other field name */
    private static z f156a;

    /* renamed from: b, reason: collision with other field name */
    private static boolean f157b;

    /* renamed from: a, reason: collision with other field name */
    private static x f158a;

    public static String a(MIDlet mIDlet, String str, String str2) {
        String appProperty = null;
        try {
            appProperty = mIDlet.getAppProperty(str);
        } catch (NullPointerException unused) {
        }
        if (appProperty == null) {
            appProperty = str2;
        }
        return appProperty;
    }

    public static String a(Class cls, String str, String str2) throws IOException {
        InputStream resourceAsStream = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            resourceAsStream = cls.getResourceAsStream(str);
            while (true) {
                int i = resourceAsStream.read();
                if (i < 0) {
                    break;
                }
                stringBuffer.append((char) i);
            }
        } catch (Exception unused) {
        }
        try {
            resourceAsStream.close();
        } catch (Exception unused2) {
        }
        if (stringBuffer.length() > 0) {
            str2 = stringBuffer.toString();
        }
        return str2;
    }

    public static String[] a(String str, char c2) {
        return a(str, c2, Integer.MAX_VALUE);
    }

    public static String[] a(String str, char c2, int i) {
        Vector vector = new Vector(22, 30);
        int i2 = 0;
        int i3 = 0;
        int i4 = i - 1;
        do {
            int iIndexOf = str.indexOf(c2, i2);
            if (0 > iIndexOf) {
                break;
            }
            vector.addElement(str.substring(i2, iIndexOf));
            i2 = iIndexOf + 1;
            i3++;
        } while (i3 < i4);
        vector.addElement(str.substring(i2));
        String[] strArr = new String[vector.size()];
        vector.copyInto(strArr);
        return strArr;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m26a(String str, char c2) {
        int length = str.length();
        String string = "";
        for (int i = 0; i < length; i++) {
            string = new StringBuffer().append(string).append(c2).append(Integer.toHexString(str.charAt(i))).toString();
        }
        return string;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static byte[] m27a(String str, char c2) {
        byte[] bArr = null;
        int length = str.length();
        if (length > 0 && str.charAt(0) == c2) {
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                if (str.charAt(i2) == c2) {
                    i++;
                }
            }
            bArr = new byte[i];
            int i3 = 1;
            int i4 = 0;
            while (true) {
                int iIndexOf = str.indexOf(c2, i3);
                if (0 > iIndexOf) {
                    break;
                }
                int i5 = i4;
                i4++;
                bArr[i5] = (byte) Integer.parseInt(str.substring(i3, iIndexOf), 16);
                i3 = iIndexOf + 1;
            }
            bArr[i4] = (byte) Integer.parseInt(str.substring(i3, length), 16);
        }
        return bArr;
    }

    public static String a(byte[] bArr, char c2) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b2 : bArr) {
            stringBuffer.append(c2).append(Integer.toHexString(128 + b2));
        }
        return stringBuffer.toString();
    }

    public static String b(String str, char c2) {
        StringBuffer stringBuffer = new StringBuffer(40);
        if (str.length() <= 0 || str.charAt(0) != c2) {
            return str;
        }
        int i = 1;
        while (true) {
            int iIndexOf = str.indexOf(c2, i);
            if (0 > iIndexOf) {
                stringBuffer.append((char) Integer.parseInt(str.substring(i), 16));
                return stringBuffer.toString();
            }
            stringBuffer.append((char) Integer.parseInt(str.substring(i, iIndexOf), 16));
            i = iIndexOf + 1;
        }
    }

    public static Image a(String str) {
        Image imageCreateImage = null;
        if (str.length() > 0) {
            try {
                imageCreateImage = Image.createImage(new StringBuffer().append("/").append(str).append(".png").toString());
            } catch (Exception unused) {
            }
        }
        return imageCreateImage;
    }

    private static byte[] a(int i) {
        byte[] bArr = new byte[8];
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        for (int i2 = 0; i2 < 8; i2++) {
            bArr[i2] = (byte) random.nextInt();
        }
        return bArr;
    }

    public static byte[] a() {
        byte[] bArr = new byte[16];
        new t().a(bArr);
        f155a = false;
        return bArr;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m28a() {
        f155a = false;
        b();
    }

    private static void b() {
        if (f155a) {
            return;
        }
        byte[] bArrM27a = m27a(e.b(), i.b);
        y yVar = new y();
        f154a = yVar;
        yVar.a(bArrM27a);
        f155a = true;
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [byte[], byte[][]] */
    public static synchronized byte[][] a(byte[] bArr, byte b2) {
        b();
        f154a.a();
        ?? r0 = new byte[3];
        byte[] bArr2 = new byte[9];
        bArr2[0] = 1;
        f154a.a(a(8), 0, bArr2, 1);
        r0[0] = bArr2;
        int length = (bArr.length / 8) << 3;
        for (int i = 0; i < length; i += 8) {
            f154a.a(bArr, i, bArr, i);
        }
        r0[1] = bArr;
        int length2 = bArr.length - length;
        byte[] bArr3 = new byte[8];
        System.arraycopy(bArr, length, bArr3, 0, length2);
        bArr3[7] = (byte) (8 - length2);
        f154a.a(bArr3, 0, bArr3, 0);
        System.arraycopy(bArr3, 0, bArr, length, length2);
        byte[] bArr4 = new byte[8 - length2];
        System.arraycopy(bArr3, length2, bArr4, 0, 8 - length2);
        r0[2] = bArr4;
        return r0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized byte[] m29a(byte[] bArr, byte b2) {
        b();
        int length = bArr.length;
        int i = length - ((length / 8) << 3) > 0 ? ((length / 8) << 3) + 8 : length + 8;
        f154a.a();
        byte[] bArr2 = new byte[i + 8 + 1];
        bArr2[0] = 1;
        bArr2[bArr2.length - 1] = (byte) (i - bArr.length);
        System.arraycopy(bArr, 0, bArr2, 9, bArr.length);
        System.arraycopy(a(8), 0, bArr2, 1, 8);
        for (int i2 = 1; i2 < bArr2.length; i2 += 8) {
            f154a.a(bArr2, i2, bArr2, i2);
        }
        return bArr2;
    }

    public static synchronized int a(byte[] bArr, int i, int i2) {
        b();
        f154a.a();
        int i3 = (i - 8) - i2;
        for (int i4 = 0; i4 < i - i2; i4 += 8) {
            f154a.b(bArr, i4 + i2, bArr, i4 - 8 < 0 ? 0 : i4 - 8);
        }
        return i3 - bArr[i3 - 1];
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x030c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static byte[] a(java.lang.String r8, byte r9) {
        /*
            Method dump skipped, instructions count: 817
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.b.a(java.lang.String, byte):byte[]");
    }

    public static int a(String str, int i, int i2) {
        long jCharAt = 1381;
        for (int i3 = 0; i3 < str.length(); i3++) {
            jCharAt += str.charAt(i3);
        }
        String string = new StringBuffer().append("").append((jCharAt * 9716) + 10).toString();
        int length = string.length();
        return Integer.parseInt(string.substring(length - 5, length - 1));
    }

    public static String[] a(String str, int i, char c2, char c3, char c4) throws NumberFormatException {
        String[] strArr = new String[i];
        int length = str.length();
        int i2 = 0;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == c2) {
                strArr[i2] = b(stringBuffer.toString(), c4);
                i2++;
                stringBuffer.setLength(0);
            } else if (cCharAt == c3) {
                i2 = Integer.parseInt(stringBuffer.toString());
                stringBuffer.setLength(0);
            } else {
                stringBuffer.append(cCharAt);
            }
        }
        return strArr;
    }

    public static String[] a(Class cls, StringBuffer stringBuffer, StringBuffer stringBuffer2, int i) throws NumberFormatException, IOException {
        String[] strArrA;
        stringBuffer.append(e.f());
        stringBuffer2.append(e.g());
        StringBuffer stringBuffer3 = new StringBuffer(e.h());
        if (stringBuffer3.length() < 5) {
            String[] strArrA2 = a(a(cls, "/langid", ""), '=');
            stringBuffer.delete(0, stringBuffer.length());
            stringBuffer.append(strArrA2[0]);
            stringBuffer2.append(b(strArrA2[1], '~'));
            strArrA = a(a(cls.getClass(), "/langvoc", ""), Opcodes.FCMPG, '|', '=', '~');
            for (int i2 = 0; i2 < strArrA.length; i2++) {
                if (strArrA[i2] != null) {
                    stringBuffer3.append(new StringBuffer().append(i2).append("=").append(strArrA[i2]).append("|").toString());
                }
            }
            e.a(stringBuffer.toString(), stringBuffer2.toString(), stringBuffer3.toString());
        } else {
            strArrA = a(stringBuffer3.toString(), Opcodes.FCMPG, '|', '=', '~');
        }
        try {
            Integer.parseInt(strArrA[0]);
        } catch (Exception unused) {
            strArrA[0] = "0";
        }
        return strArrA;
    }

    public static boolean a(String str, String[] strArr) {
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= strArr.length) {
                break;
            }
            if (str.indexOf(strArr[i]) >= 0) {
                z = true;
                break;
            }
            i++;
        }
        return z;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m30a() {
        boolean z = true;
        String upperCase = new StringBuffer().append("").append(System.getProperty("microedition.platform")).toString().toUpperCase();
        if (upperCase.indexOf("NOKIA") >= 0) {
            z = !a(upperCase, new String[]{"2650", "3100", "3105", "3108", "3120", "3125", "3200", "3205", "5100", "6108", "6200", "6220", "6225", "6585", "6610", "6650", "6800", "6810", "6820", "7200", "7210", "7250"});
        }
        return z;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static u m31a() throws ClassNotFoundException {
        u uVar = null;
        boolean z = false;
        if (m30a()) {
            try {
                Class.forName("javax.microedition.media.Manager");
                uVar = (u) Class.forName("v").newInstance();
                z = true;
            } catch (Exception unused) {
                z = false;
            }
        }
        if (!z) {
            uVar = new u();
        }
        return uVar;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: a, reason: collision with other method in class */
    public static Object m32a() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Object objNewInstance = null;
        Class<?> cls = null;
        boolean z = false;
        try {
            String upperCase = new StringBuffer().append("").append(System.getProperty("microedition.platform")).toString().toUpperCase();
            String upperCase2 = new StringBuffer().append("").append(System.getProperty("microedition.profiles")).toString().toUpperCase();
            if (upperCase.indexOf("NOKIA") >= 0) {
                z = true;
            } else if (upperCase2.indexOf("MIDP-2.") >= 0) {
                z = 2;
            }
            if (upperCase.indexOf("GX") >= 0) {
                z = false;
            }
            if (upperCase.indexOf("SONYERICSSON") >= 0 && a(upperCase, new String[]{"P800", "P900", "P910"})) {
                z = false;
            }
            if (z) {
                Class.forName("com.nokia.mid.ui.FullCanvas");
                Class<?> cls2 = Class.forName("r");
                cls = cls2;
                objNewInstance = cls2.newInstance();
            }
            if (z == 2) {
                Class.forName("javax.microedition.lcdui.game.GameCanvas");
                Class<?> cls3 = Class.forName("q");
                cls = cls3;
                objNewInstance = cls3.newInstance();
            }
        } catch (Exception e2) {
            z = false;
        }
        if (!z) {
            try {
                cls = Class.forName("p");
            } catch (Exception unused) {
            }
        }
        objNewInstance = cls.newInstance();
        return objNewInstance;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static i m33a() throws ClassNotFoundException {
        i iVar = null;
        if (m30a()) {
            try {
                Class.forName("javax.microedition.io.SocketConnection");
                iVar = (i) Class.forName("j").newInstance();
            } catch (Exception unused) {
            }
        }
        return iVar;
    }

    public static int[] a(byte[] bArr) {
        int[] iArr = null;
        int length = bArr.length;
        if (length > 15 && bArr[length - 1] == 93) {
            int i = length - 2;
            while (true) {
                if (i <= length - 12) {
                    break;
                }
                if (bArr[i] == 91) {
                    iArr = new int[]{0, Integer.parseInt(new String(bArr, i + 1, (length - i) - 2))};
                    iArr[0] = i - iArr[1];
                    break;
                }
                i--;
            }
        }
        return iArr;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m34a() {
        String upperCase = new StringBuffer().append("").append(System.getProperty("microedition.platform")).toString().toUpperCase();
        return (upperCase.indexOf("NOKIA") < 0 && upperCase.indexOf("AVACSEMULATOR") < 0) ? "false" : "true";
    }

    public static synchronized byte[] a(byte[] bArr, int i) {
        int iM35a;
        byte[] bArr2;
        int i2;
        byte[] bArr3;
        int i3;
        try {
            a = bArr;
            if (m35a(16) != 35615 || m35a(8) != 8) {
                throw new IOException();
            }
            int iM35a2 = m35a(8);
            f148a += 6;
            if ((iM35a2 & 4) != 0) {
                f148a += m35a(16);
            }
            if ((iM35a2 & 8) != 0) {
                do {
                    bArr3 = a;
                    i3 = f148a;
                    f148a = i3 + 1;
                } while (bArr3[i3] != 0);
            }
            if ((iM35a2 & 16) != 0) {
                do {
                    bArr2 = a;
                    i2 = f148a;
                    f148a = i2 + 1;
                } while (bArr2[i2] != 0);
            }
            if ((iM35a2 & 2) != 0) {
                f148a += 2;
            }
            int i4 = f148a;
            f148a = a.length - 4;
            if (i < a.length) {
                f148a = i - 4;
            }
            f149b = new byte[m35a(16) | (m35a(16) << 16)];
            f148a = i4;
            f150c = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0, 99, 99};
            f151a = new short[]{3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31, 35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258, 0, 0};
            f152d = new byte[]{0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13};
            f153b = new short[]{1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193, 257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145, 8193, 12289, 16385, 24577};
            e = new byte[]{16, 17, 18, 0, 8, 7, 9, 6, 10, 5, 11, 4, 12, 3, 13, 2, 14, 1, 15};
            do {
                iM35a = m35a(1);
                int iM35a3 = m35a(2);
                if (iM35a3 == 0) {
                    c = 0;
                    int iM35a4 = m35a(16);
                    m35a(16);
                    System.arraycopy(a, f148a, f149b, d, iM35a4);
                    f148a += iM35a4;
                    d += iM35a4;
                } else if (iM35a3 == 1) {
                    c();
                } else {
                    if (iM35a3 != 2) {
                        throw new IOException();
                    }
                    d();
                }
            } while (iM35a == 0);
            byte[] bArr4 = f149b;
            d = 0;
            c = 0;
            b = 0;
            f148a = 0;
            e = null;
            f152d = null;
            f150c = null;
            f149b = null;
            a = null;
            f153b = null;
            f151a = null;
            return bArr4;
        } catch (Throwable th) {
            d = 0;
            c = 0;
            b = 0;
            f148a = 0;
            e = null;
            f152d = null;
            f150c = null;
            f149b = null;
            a = null;
            f153b = null;
            f151a = null;
            throw th;
        }
    }

    private static void c() {
        byte[] bArr = new byte[288];
        for (int i = 0; i < 144; i++) {
            bArr[i] = 8;
        }
        for (int i2 = 144; i2 < 256; i2++) {
            bArr[i2] = 9;
        }
        for (int i3 = 256; i3 < 280; i3++) {
            bArr[i3] = 7;
        }
        for (int i4 = 280; i4 < 288; i4++) {
            bArr[i4] = 8;
        }
        int[] iArrM36a = m36a(bArr, 287);
        byte[] bArr2 = new byte[32];
        for (int i5 = 0; i5 < bArr2.length; i5++) {
            bArr2[i5] = 5;
        }
        a(iArrM36a, m36a(bArr2, 31));
    }

    private static void d() {
        int iM35a = m35a(5) + 257;
        int iM35a2 = m35a(5) + 1;
        int iM35a3 = m35a(4) + 4;
        byte[] bArr = new byte[19];
        for (int i = 0; i < iM35a3; i++) {
            bArr[e[i]] = (byte) m35a(3);
        }
        int[] iArrM36a = m36a(bArr, 18);
        a(m36a(a(iArrM36a, iM35a), iM35a - 1), m36a(a(iArrM36a, iM35a2), iM35a2 - 1));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v24, types: [int] */
    /* JADX WARN: Type inference failed for: r0v26, types: [int] */
    /* JADX WARN: Type inference failed for: r8v10, types: [int] */
    private static void a(int[] iArr, int[] iArr2) {
        while (true) {
            int iA = a(iArr);
            if (iA == 256) {
                return;
            }
            if (iA > 256) {
                int i = iA - 257;
                short sM35a = f151a[i];
                byte b2 = f150c[i];
                if (b2 > 0) {
                    sM35a += m35a((int) b2);
                }
                int iA2 = a(iArr2);
                short sM35a2 = f153b[iA2];
                byte b3 = f152d[iA2];
                if (b3 > 0) {
                    sM35a2 += m35a((int) b3);
                }
                int i2 = d - sM35a2;
                for (short s = 0; s < sM35a; s++) {
                    byte[] bArr = f149b;
                    int i3 = d;
                    d = i3 + 1;
                    bArr[i3] = f149b[i2 + s];
                }
            } else {
                byte[] bArr2 = f149b;
                int i4 = d;
                d = i4 + 1;
                bArr2[i4] = (byte) iA;
            }
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static int m35a(int i) {
        int i2;
        if (c == 0) {
            byte[] bArr = a;
            int i3 = f148a;
            f148a = i3 + 1;
            i2 = bArr[i3] & 255;
            b = i2;
        } else {
            i2 = b >> c;
        }
        int i4 = i2;
        for (int i5 = 8 - c; i5 < i; i5 += 8) {
            byte[] bArr2 = a;
            int i6 = f148a;
            f148a = i6 + 1;
            b = bArr2[i6] & 255;
            i4 |= b << i5;
        }
        c = (c + i) & 7;
        return i4 & ((1 << i) - 1);
    }

    private static int a(int[] iArr) {
        int i = iArr[0];
        while (i >= 0) {
            if (c == 0) {
                byte[] bArr = a;
                int i2 = f148a;
                f148a = i2 + 1;
                b = bArr[i2] & 255;
            }
            i = (b & (1 << c)) == 0 ? iArr[i >> 16] : iArr[i & TextField.CONSTRAINT_MASK];
            c = (c + 1) & 7;
        }
        return i & TextField.CONSTRAINT_MASK;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static int[] m36a(byte[] bArr, int i) {
        int i2;
        int[] iArr = new int[17];
        for (byte b2 : bArr) {
            iArr[b2] = iArr[b2] + 1;
        }
        int i3 = 0;
        iArr[0] = 0;
        int[] iArr2 = new int[17];
        for (int i4 = 1; i4 <= 16; i4++) {
            int i5 = (i3 + iArr[i4 - 1]) << 1;
            i3 = i5;
            iArr2[i4] = i5;
        }
        int[] iArr3 = new int[(i << 1) + 16];
        int i6 = 1;
        for (int i7 = 0; i7 <= i; i7++) {
            byte b3 = bArr[i7];
            if (b3 != 0) {
                int i8 = iArr2[b3];
                iArr2[b3] = i8 + 1;
                int i9 = 0;
                for (int i10 = b3 - 1; i10 >= 0; i10--) {
                    if ((i8 & (1 << i10)) == 0) {
                        int i11 = iArr3[i9] >> 16;
                        if (i11 == 0) {
                            int i12 = i9;
                            iArr3[i12] = iArr3[i12] | (i6 << 16);
                            i2 = i6;
                            i6++;
                        } else {
                            i2 = i11;
                        }
                    } else {
                        int i13 = iArr3[i9] & TextField.CONSTRAINT_MASK;
                        if (i13 == 0) {
                            int i14 = i9;
                            iArr3[i14] = iArr3[i14] | i6;
                            i2 = i6;
                            i6++;
                        } else {
                            i2 = i13;
                        }
                    }
                    i9 = i2;
                }
                iArr3[i9] = i7 | Integer.MIN_VALUE;
            }
        }
        return iArr3;
    }

    private static byte[] a(int[] iArr, int i) {
        int iM35a;
        byte[] bArr = new byte[i];
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = i3;
            if (i2 >= i) {
                return bArr;
            }
            int iA = a(iArr);
            int i5 = iA;
            if (iA >= 16) {
                if (i5 == 16) {
                    iM35a = 3 + m35a(2);
                    i5 = i4;
                } else {
                    iM35a = i5 == 17 ? 3 + m35a(3) : 11 + m35a(7);
                    i5 = 0;
                }
                while (true) {
                    int i6 = iM35a;
                    iM35a--;
                    if (i6 > 0) {
                        int i7 = i2;
                        i2++;
                        bArr[i7] = (byte) i5;
                    }
                }
            } else if (i5 != 0) {
                int i8 = i2;
                i2++;
                bArr[i8] = (byte) i5;
            } else {
                i2++;
            }
            i3 = i5;
        }
    }

    static {
        new Hashtable(100);
        f154a = null;
        f155a = false;
        f156a = null;
        f157b = false;
        f158a = null;
    }
}
