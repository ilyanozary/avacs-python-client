package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/* loaded from: avacs.jar:h.class */
public final class h extends i {
    private HttpConnection a = null;
    private static boolean b = false;

    /* renamed from: a, reason: collision with other field name */
    private StringBuffer f169a;

    /* renamed from: b, reason: collision with other field name */
    private StringBuffer f170b;
    private StringBuffer c;

    @Override // defpackage.i
    public final int a(int i, StringBuffer stringBuffer, byte[] bArr) throws InterruptedException, NumberFormatException {
        this.f187a = bArr;
        int iA = 0;
        int i2 = 0;
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        }
        while (true) {
            if (i2 >= this.f184d) {
                break;
            }
            while (b) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException unused) {
                }
            }
            if (i2 > 0) {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException unused2) {
                }
            }
            StringBuffer stringBuffer2 = new StringBuffer(stringBuffer.toString());
            iA = a(i, stringBuffer2);
            i2++;
            if (iA > 0) {
                stringBuffer.delete(0, stringBuffer.length());
                stringBuffer.append(stringBuffer2.toString());
                break;
            }
            c();
        }
        if (iA <= 0) {
            stringBuffer.delete(0, stringBuffer.length());
        }
        return iA;
    }

    private int a(int i, StringBuffer stringBuffer) throws InterruptedException, NumberFormatException {
        boolean z = false;
        if (this.f187a != null) {
            z = true;
        }
        b = true;
        StringBuffer stringBuffer2 = new StringBuffer("");
        StringBuffer stringBuffer3 = new StringBuffer(new StringBuffer().append("").append(i).toString());
        this.f180a = a();
        int i2 = 0;
        long jCurrentTimeMillis = 0;
        this.f170b = stringBuffer3;
        this.f169a = stringBuffer;
        this.c = stringBuffer2;
        new Thread(this).start();
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        while (stringBuffer2.length() == 0 && jCurrentTimeMillis < this.f182b) {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException unused) {
            }
            jCurrentTimeMillis = System.currentTimeMillis() - jCurrentTimeMillis2;
        }
        if (jCurrentTimeMillis < this.f182b) {
            i2 = Integer.parseInt(stringBuffer3.toString());
        }
        if (i2 <= 0) {
            jCurrentTimeMillis = this.f182b;
        }
        if (this.f187a != null) {
            z = true;
        }
        if (!z) {
            this.f181a.a(this.f180a, jCurrentTimeMillis);
        }
        b = false;
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // defpackage.i, java.lang.Runnable
    public final void run() throws IOException {
        int iA;
        StringBuffer stringBuffer = this.f170b;
        StringBuffer stringBuffer2 = this.f169a;
        String strB = b();
        String string = stringBuffer.toString();
        String strA = a();
        byte[] bArrA = a(string, stringBuffer2.toString());
        stringBuffer.setLength(0);
        stringBuffer.append("-3");
        c();
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            this.a = (HttpConnection) Connector.open(strB);
            this.a.setRequestMethod(HttpConnection.POST);
            this.a.setRequestProperty("User-Agent", this.f179b);
            this.a.setRequestProperty("Connection", "close");
            String str = "";
            String str2 = "";
            if (this.f187a != null) {
                this.f182b = 150000;
            } else {
                this.f182b = 60000;
            }
            this.a.setRequestProperty("Content-Type", "multipart/form-data; boundary=NNNNNAVACSNNNN");
            String string2 = new StringBuffer().append("--NNNNNAVACSNNNN\r\nContent-Disposition: form-data; name='D'\r\n\r\n").append(strA).toString();
            int length = string2.length() + "\r\n--NNNNNAVACSNNNN\r\n".length() + bArrA.length;
            byte[] bArrM29a = null;
            if (this.f187a != null) {
                str = "Content-Disposition: form-data; name='attached_file'; filename='att_name'\r\nContent-Type: application/octet-stream\r\nContent-Transfer-Encoding: binary\r\n\r\n";
                str2 = "\r\n--NNNNNAVACSNNNN--\r\n";
                bArrM29a = b.m29a(this.f187a, (byte) 1);
                length += bArrM29a.length + str.length() + str2.length();
            }
            this.a.setRequestProperty("Content-Length", new StringBuffer().append("").append(length).toString());
            OutputStream outputStreamOpenOutputStream = this.a.openOutputStream();
            outputStreamOpenOutputStream.write(string2.getBytes());
            outputStreamOpenOutputStream.write(bArrA);
            outputStreamOpenOutputStream.write("\r\n--NNNNNAVACSNNNN\r\n".getBytes());
            if (this.f187a != null) {
                outputStreamOpenOutputStream.write(str.getBytes());
                outputStreamOpenOutputStream.write(bArrM29a);
                outputStreamOpenOutputStream.write(str2.getBytes());
            }
            outputStreamOpenOutputStream.close();
            outputStream = null;
            int responseCode = this.a.getResponseCode();
            if (responseCode == 200) {
                InputStream inputStreamOpenInputStream = this.a.openInputStream();
                int i = 0;
                int i2 = 0;
                byte[] bArr = new byte[8192];
                int[] iArr = new int[8192];
                byte[] bArr2 = new byte[1024];
                while (true) {
                    int i3 = inputStreamOpenInputStream.read(bArr2);
                    if (i3 < 0) {
                        break;
                    }
                    bArr[i2] = bArr2;
                    iArr[i2] = i3;
                    i += i3;
                    i2++;
                    bArr2 = new byte[1024];
                }
                inputStreamOpenInputStream.close();
                inputStream = null;
                if (i > 5) {
                    int i4 = i;
                    if (i4 > 2) {
                        byte[] bArr3 = new byte[i4];
                        int i5 = 0;
                        for (int i6 = 0; i6 < i2 && i5 < i4; i6++) {
                            System.arraycopy(bArr[i6], 0, bArr3, i5, iArr[i6]);
                            i5 += iArr[i6];
                            bArr[i6] = 0;
                        }
                        iA = a(bArr3, bArr3.length, stringBuffer2, 1);
                    } else {
                        iA = -12;
                    }
                } else {
                    iA = -13;
                }
            } else {
                iA = -4;
                if (responseCode == 404) {
                    iA = -7;
                } else {
                    this.f176a = new StringBuffer().append("").append(responseCode).append("-").append(this.a.getResponseMessage()).toString();
                }
            }
        } catch (IOException e) {
            iA = -3;
        } catch (Error e2) {
            iA = -4;
            this.f176a = new StringBuffer().append("").append(e2).append(". ").append(e2.getMessage()).toString();
        } catch (NumberFormatException e3) {
            iA = -11;
        } catch (IllegalArgumentException e4) {
            iA = -5;
        } catch (SecurityException e5) {
            iA = -6;
        } catch (Exception e6) {
            iA = -3;
        }
        stringBuffer.delete(0, stringBuffer.length());
        stringBuffer.append(iA);
        if (iA <= 0) {
            try {
                outputStream.close();
            } catch (Exception unused) {
            }
            try {
                inputStream.close();
            } catch (Exception unused2) {
            }
            System.gc();
        }
        this.c.append("1");
    }

    private void c() {
        try {
            this.a.close();
        } catch (Exception unused) {
        }
        this.a = null;
    }
}
