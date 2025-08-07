package defpackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

/* loaded from: avacs.jar:e.class */
public final class e {
    private static RecordStore a = null;
    private static RecordStore b = null;

    /* renamed from: a, reason: collision with other field name */
    private static String f159a = "Store1";

    /* renamed from: b, reason: collision with other field name */
    private static String f160b = "Store2";
    private static String c = "0";
    private static String d = "0";

    public static String a() {
        return d;
    }

    public static void a(String str, String str2) throws IOException {
        d = str;
        c = str2;
        a(a, f159a, 1, m42a(str));
        a(a, f159a, 2, str);
        a(a, f159a, 3, str2);
    }

    public static String b() {
        return c;
    }

    public static String c() {
        return a(b, f160b, 2);
    }

    public static void a(String str) throws IOException {
        a(b, f160b, 2, str);
    }

    public static String d() {
        return a(b, f160b, 3);
    }

    public static void b(String str) throws IOException {
        a(b, f160b, 3, str);
    }

    public static String e() {
        return a(b, f160b, 4);
    }

    public static void c(String str) throws IOException {
        a(b, f160b, 4, str);
    }

    public static void a(String str, String str2, String str3) throws IOException {
        a(b, f160b, 6, str);
        a(b, f160b, 7, str2);
        int length = str3.length() / 3;
        a(b, f160b, 8, str3.substring(0, length));
        a(b, f160b, 9, str3.substring(length, length << 1));
        a(b, f160b, 10, str3.substring(length << 1));
    }

    public static String f() {
        return a(b, f160b, 6);
    }

    public static String g() {
        return a(b, f160b, 7);
    }

    public static String h() {
        StringBuffer stringBuffer = new StringBuffer(2048);
        stringBuffer.append(a(b, f160b, 8));
        stringBuffer.append(a(b, f160b, 9));
        stringBuffer.append(a(b, f160b, 10));
        return stringBuffer.toString();
    }

    private static String a(RecordStore recordStore, String str, int i) throws IOException {
        byte[] record = new byte[0];
        try {
            record = recordStore.getRecord(i);
        } catch (Exception unused) {
            try {
                record = b(recordStore, str).getRecord(i);
            } catch (Exception e) {
            }
        }
        String utf = "0";
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(record);
            DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
            utf = dataInputStream.readUTF();
            dataInputStream.close();
            byteArrayInputStream.close();
        } catch (Exception e2) {
        }
        return utf;
    }

    private static void a(RecordStore recordStore, String str, int i, String str2) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] byteArray = new byte[0];
        try {
            dataOutputStream.writeUTF(str2);
            byteArray = byteArrayOutputStream.toByteArray();
            dataOutputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException unused) {
        }
        try {
            recordStore.setRecord(i, byteArray, 0, byteArray.length);
        } catch (Exception unused2) {
            try {
                b(recordStore, str).setRecord(i, byteArray, 0, byteArray.length);
            } catch (Exception unused3) {
            }
        }
    }

    private static RecordStore a(RecordStore recordStore, String str) {
        if (recordStore == null) {
            try {
                recordStore = m38a(str);
            } catch (Exception unused) {
                recordStore = b(recordStore, str);
            }
        }
        return recordStore;
    }

    private static RecordStore b(RecordStore recordStore, String str) {
        try {
            try {
                recordStore.closeRecordStore();
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
        }
        try {
            RecordStore.deleteRecordStore(str);
        } catch (Exception unused3) {
        }
        recordStore = m38a(str);
        return recordStore;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static RecordStore m38a(String str) throws RecordStoreException, IOException {
        RecordStore recordStoreOpenRecordStore = RecordStore.openRecordStore(str, true);
        if (recordStoreOpenRecordStore.getNumRecords() == 0) {
            if (str == f159a) {
                m39a(recordStoreOpenRecordStore, m42a("0"));
                m39a(recordStoreOpenRecordStore, "0");
                m39a(recordStoreOpenRecordStore, "0");
            } else if (str == f160b) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                long j = jCurrentTimeMillis - ((jCurrentTimeMillis / 259200000) * 259200000);
                long j2 = jCurrentTimeMillis - ((jCurrentTimeMillis / 21800000) * 21800000);
                String str2 = j > 86400000 ? "summary.ad2.work" : "fast.ad4.work";
                if (j > 172800000) {
                    str2 = "resume.4you.work";
                }
                String str3 = j2 > 10900000 ? "my.ad4.click" : "add.2my.click";
                String str4 = j > 86400000 ? "summary.ad2.work" : "fast.ad4.work";
                if (j > 172800000) {
                    str4 = "resume.4you.work";
                }
                String str5 = j2 > 10900000 ? "my.ad4.click" : "add.2my.click";
                m39a(recordStoreOpenRecordStore, "0");
                m39a(recordStoreOpenRecordStore, new StringBuffer().append("false|true|0|0|false|").append(b.m34a()).toString());
                m39a(recordStoreOpenRecordStore, new StringBuffer().append("http://http.avacs.net=0=http://http.avacs.net,http://").append(str5).append("=1=http://http.avacschat.net").append(',').append("http://").append(str4).append("=1=http://http.avacschat.com").append('^').append("socket://").append("socket.avacs.net").append(":9001=0=socket://socket.avacs.net:9001").append(',').append("socket://").append(str3).append(":9001=1=socket://socket.avacschat.net:9001").append(',').append("socket://").append(str2).append(":9011=1=socket://socket.avacschat.com:9011").toString());
                m39a(recordStoreOpenRecordStore, "0|0");
                m39a(recordStoreOpenRecordStore, "0");
                m39a(recordStoreOpenRecordStore, "0");
                m39a(recordStoreOpenRecordStore, "");
                m39a(recordStoreOpenRecordStore, "");
                m39a(recordStoreOpenRecordStore, "");
                m39a(recordStoreOpenRecordStore, "");
            }
        }
        return recordStoreOpenRecordStore;
    }

    /* renamed from: a, reason: collision with other method in class */
    private static void m39a(RecordStore recordStore, String str) throws RecordStoreException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] byteArray = new byte[0];
        try {
            dataOutputStream.writeUTF(str);
            byteArray = byteArrayOutputStream.toByteArray();
            dataOutputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException unused) {
        }
        recordStore.addRecord(byteArray, 0, byteArray.length);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static void m40a() {
        try {
            a.closeRecordStore();
            a = null;
        } catch (Exception unused) {
        }
        try {
            b.closeRecordStore();
            b = null;
        } catch (Exception unused2) {
        }
    }

    /* renamed from: b, reason: collision with other method in class */
    public static void m41b() {
        RecordStore recordStoreA = a(a, f159a);
        a = recordStoreA;
        d = a(recordStoreA, f159a, 2);
        c = a(a, f159a, 3);
        if (a(a, f159a, 1).compareTo(m42a(d)) != 0) {
            d = "0";
            c = "0";
            a = b(a, f159a);
        }
        b = a(b, f160b);
    }

    /* renamed from: a, reason: collision with other method in class */
    private static String m42a(String str) {
        return new StringBuffer().append("9").append(Integer.toOctalString(b.a(str, 10, 3000))).toString();
    }
}
