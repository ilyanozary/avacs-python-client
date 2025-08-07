package defpackage;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

/* loaded from: avacs.jar:g.class */
public final class g {

    /* renamed from: a, reason: collision with other field name */
    private static long f167a;
    private static Hashtable a = new Hashtable(100);

    /* renamed from: a, reason: collision with other field name */
    private static Vector f163a = new Vector();

    /* renamed from: a, reason: collision with other field name */
    private static RecordStore f164a = null;

    /* renamed from: a, reason: collision with other field name */
    private static String f165a = "CachedObjects";

    /* renamed from: a, reason: collision with other field name */
    private static boolean f166a = false;

    /* renamed from: a, reason: collision with other field name */
    private static char f168a = '^';
    private static char b = '=';
    private static char c = '~';

    public static void a() {
        try {
            f164a.closeRecordStore();
            f164a = null;
        } catch (Exception unused) {
        }
    }

    private static void b() throws IOException {
        if (f164a == null) {
            try {
                f164a = RecordStore.openRecordStore(f165a, true);
            } catch (Exception unused) {
                c();
            }
            if (f164a != null) {
                try {
                    RecordEnumeration recordEnumerationEnumerateRecords = f164a.enumerateRecords(null, null, false);
                    while (recordEnumerationEnumerateRecords.hasNextElement()) {
                        int iNextRecordId = recordEnumerationEnumerateRecords.nextRecordId();
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(f164a.getRecord(iNextRecordId));
                        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                        String strM46a = m46a(dataInputStream.readUTF());
                        long j = dataInputStream.readLong();
                        dataInputStream.close();
                        byteArrayInputStream.close();
                        if (j < System.currentTimeMillis()) {
                            f164a.deleteRecord(iNextRecordId);
                        } else {
                            a.put(strM46a, new Integer(iNextRecordId));
                        }
                    }
                } catch (Error unused2) {
                    c();
                } catch (Exception unused3) {
                    c();
                }
            }
        }
    }

    public static f a(String str) {
        b();
        f fVar = null;
        Integer num = (Integer) a.get(str);
        if (num == null) {
            b(str);
        } else {
            try {
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(f164a.getRecord(num.intValue()));
                DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                dataInputStream.readUTF();
                dataInputStream.readLong();
                f fVar2 = new f();
                fVar = fVar2;
                fVar2.f162a = dataInputStream.readInt();
                fVar.f161a = dataInputStream.readUTF();
                fVar.b = dataInputStream.readUTF();
                int i = dataInputStream.readInt();
                byte[] bArr = new byte[i];
                for (int i2 = 0; i2 < i; i2++) {
                    bArr[i2] = (byte) dataInputStream.read();
                }
                fVar.a = bArr;
                dataInputStream.close();
                byteArrayInputStream.close();
            } catch (Exception unused) {
                b(str);
            }
        }
        return fVar;
    }

    private static void b(String str) {
        String strM46a = m46a(str);
        for (int i = 0; i < f163a.size(); i++) {
            if (strM46a.compareTo((String) f163a.elementAt(i)) == 0) {
                return;
            }
        }
        f163a.addElement(strM46a);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0154  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0171 A[SYNTHETIC] */
    /* renamed from: a, reason: collision with other method in class */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void m44a(java.lang.String r7) {
        /*
            Method dump skipped, instructions count: 380
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.g.m44a(java.lang.String):void");
    }

    private static void c() {
        try {
            a = new Hashtable(100);
            try {
                f164a.closeRecordStore();
            } catch (Exception unused) {
            }
            try {
                RecordStore.deleteRecordStore(f165a);
            } catch (Exception unused2) {
            }
            f164a = RecordStore.openRecordStore(f165a, true);
        } catch (Exception unused3) {
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m45a() {
        if (f163a.size() <= 0) {
            return null;
        }
        if (!f166a && System.currentTimeMillis() - f167a <= 20000) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        Enumeration enumerationElements = f163a.elements();
        while (enumerationElements.hasMoreElements()) {
            stringBuffer.append(new StringBuffer().append(",").append((String) enumerationElements.nextElement()).toString());
            i++;
            if (i > 11) {
                break;
            }
        }
        stringBuffer.deleteCharAt(0);
        f167a = System.currentTimeMillis();
        f166a = false;
        return stringBuffer.toString();
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m46a(String str) {
        String strSubstring = "";
        try {
            int iIndexOf = str.indexOf(36);
            strSubstring = str.substring(iIndexOf, str.indexOf(36, iIndexOf + 1) + 1);
        } catch (Exception unused) {
        }
        return strSubstring;
    }
}
