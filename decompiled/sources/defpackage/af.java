package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.microedition.lcdui.Graphics;

/* loaded from: avacs.jar:af.class */
public final class af extends ag implements Runnable {
    private String i;
    private String j;
    private String l;
    private String n;
    private int F;
    private int G;
    private int H;
    private int I;
    private n a;
    private String g = "..";
    private String h = "file:///";
    private int E = 0;

    /* renamed from: j, reason: collision with other field name */
    private boolean f34j = true;
    private boolean k = false;

    /* renamed from: k, reason: collision with other field name */
    private String f35k = this.h;
    private String m = "";

    /* renamed from: l, reason: collision with other field name */
    private boolean f36l = false;
    private int J = 0;

    @Override // defpackage.ag, defpackage.am
    public final void a() throws ClassNotFoundException {
        this.J = 0;
        Class<?> cls = null;
        if (b.m30a()) {
            if (this.J == 0) {
                try {
                    Class.forName("javax.microedition.io.file.FileConnection");
                    Class.forName("javax.microedition.io.file.FileSystemRegistry");
                    cls = Class.forName("k");
                    this.J = 1;
                } catch (Exception unused) {
                }
            }
            if (this.J == 0) {
                try {
                    Class.forName("com.siemens.mp.io.file.FileConnection");
                    Class.forName("com.siemens.mp.io.file.FileSystemRegistry");
                    cls = Class.forName("m");
                    this.J = 2;
                } catch (Exception unused2) {
                }
            }
            if (this.J == 0) {
                try {
                    Class.forName("com.motorola.io.FileConnection");
                    Class.forName("com.motorola.io.FileSystemRegistry");
                    cls = Class.forName("l");
                    this.J = 3;
                    this.h = "file://";
                    this.f35k = "file://";
                } catch (Exception unused3) {
                }
            }
            if (this.J > 0) {
                try {
                    this.a = (n) cls.newInstance();
                } catch (Exception unused4) {
                    this.J = 0;
                }
            }
        }
        if (this.J == 0) {
            this.E = -113;
            return;
        }
        this.F = this.C;
        this.G = this.D;
        this.H = this.z;
        this.I = this.A;
        this.l = this.f90a;
        if (this.b == 17) {
            this.i = this.f101d;
        } else {
            String[] strArrA = b.a(this.f101d, f119a);
            this.n = b.b(strArrA[0], f121c);
            this.i = strArrA[1];
        }
        new Thread(this).start();
    }

    @Override // java.lang.Runnable
    public final void run() throws IOException {
        this.f34j = false;
        this.k = false;
        String string = new StringBuffer().append(f120b).append(this.f101d).toString();
        int i = 0;
        if (this.f35k.compareTo(this.h) == 0) {
            this.n = 0;
            this.f91a[0] = 0;
            this.f90a = this.l;
            try {
                string = "";
                Enumeration enumerationA = this.a.a();
                while (enumerationA.hasMoreElements()) {
                    String str = (String) enumerationA.nextElement();
                    string = new StringBuffer().append(string).append(f120b).append(this.h).append(str).append(f119a).append(str).append(this.J > 1 ? "=        =" : "=").toString();
                    if (new StringBuffer().append(this.h).append(str).toString().compareTo(this.m) == 0) {
                        this.n = i;
                    }
                    i++;
                }
            } catch (Error e) {
                this.k = true;
                this.E = -116;
                this.j = new StringBuffer().append("").append(e).append(". ").append(e.getMessage()).toString();
            } catch (Exception e2) {
                this.k = true;
                this.E = -116;
                this.j = new StringBuffer().append("").append(e2).append(". ").append(e2.getMessage()).toString();
            }
        } else {
            try {
                if (this.f36l) {
                    this.a.a(new StringBuffer().append(this.f35k).append(this.n).toString());
                    if (!this.a.mo66a()) {
                        this.a.mo70a();
                    }
                    OutputStream outputStreamMo68a = this.a.mo68a();
                    int[] iArrA = b.a((byte[]) this.f97a);
                    outputStreamMo68a.write((byte[]) this.f97a, iArrA[0], iArrA[1]);
                    outputStreamMo68a.close();
                    this.a.mo71b();
                    this.a.a(this.f89a, this.i, new StringBuffer().append(b.m26a(this.n, f121c)).append(f119a).append(this.i).toString(), null, this);
                    this.f34j = true;
                    this.k = true;
                } else if (this.f35k.charAt(this.f35k.length() - 1) == '/') {
                    this.a.a(this.f35k);
                    this.n = 0;
                    this.f91a[0] = 0;
                    String str2 = this.f35k;
                    String strSubstring = str2.substring(0, str2.lastIndexOf(47, str2.length() - 2) + 1);
                    this.f90a = this.f35k.substring(this.h.length());
                    if (this.b == 18) {
                        this.f90a = new StringBuffer().append(this.f90a).append(this.n).toString();
                    }
                    Enumeration enumerationB = this.a.b();
                    this.a.mo71b();
                    string = new StringBuffer().append(f120b).append(strSubstring).append(f119a).append(this.g).append(this.J > 1 ? "=        =$dir$" : "=$dir$").toString();
                    while (enumerationB.hasMoreElements()) {
                        String str3 = (String) enumerationB.nextElement();
                        String strSubstring2 = str3.substring(str3.lastIndexOf(47, str3.length() - 2) + 1);
                        if (this.J > 1) {
                            try {
                                this.a.a(new StringBuffer().append(this.f35k).append(strSubstring2).toString());
                                string = new StringBuffer().append(string).append(f120b).append(this.a.mo65a()).append(f119a).append(b.m26a(strSubstring2, f121c)).append(f119a).append(strSubstring2.charAt(strSubstring2.length() - 1) == '/' ? "=$dir$" : new StringBuffer().append(this.a.mo67a()).append("=").append(a(strSubstring2)).toString()).toString();
                                i++;
                                if (this.a.mo65a().compareTo(this.m) == 0) {
                                    this.n = i;
                                }
                                this.a.mo71b();
                            } catch (Exception unused) {
                            }
                        } else {
                            string = new StringBuffer().append(string).append(f120b).append(this.f35k).append(strSubstring2).append(f119a).append(b.m26a(strSubstring2, f121c)).append(f119a).append(strSubstring2.charAt(strSubstring2.length() - 1) == '/' ? "$dir$" : a(strSubstring2)).toString();
                        }
                    }
                } else if (this.b == 17) {
                    this.a.a(this.f35k);
                    int iMo67a = (int) this.a.mo67a();
                    byte[] bArr = new byte[iMo67a];
                    InputStream inputStreamMo69a = this.a.mo69a();
                    int i2 = 0;
                    String strSubstring3 = this.f35k.substring(this.f35k.lastIndexOf(47, this.f35k.length() - 1) + 1);
                    while (i2 < iMo67a) {
                        int i3 = inputStreamMo69a.read(bArr, i2, iMo67a - i2 < iMo67a ? iMo67a - i2 : iMo67a);
                        if (i3 <= 0) {
                            break;
                        } else {
                            i2 += i3;
                        }
                    }
                    inputStreamMo69a.close();
                    this.a.mo71b();
                    this.a.a(this.f89a, this.h, new StringBuffer().append(b.m26a(strSubstring3, f121c)).append(f119a).append(this.i).toString(), bArr, this);
                    this.f34j = true;
                    this.k = true;
                } else {
                    this.f35k = this.f35k.substring(0, this.f35k.lastIndexOf(47, this.f35k.length() - 2) + 1);
                }
            } catch (Error e3) {
                this.E = -117;
                this.j = new StringBuffer().append("").append(e3).append(". ").append(e3.getMessage()).toString();
            } catch (Exception e4) {
                this.E = -117;
                this.j = new StringBuffer().append("").append(e4).append(". ").append(e4.getMessage()).toString();
            }
        }
        if (this.k || this.E != 0) {
            return;
        }
        this.C = this.F;
        this.D = this.G;
        this.z = this.H;
        this.A = this.I;
        this.f101d = string.substring(1);
        super.a();
        this.f34j = true;
        this.a.a(this.f89a, -200, null, null, this);
    }

    @Override // defpackage.ag, defpackage.am
    public final void a(Graphics graphics) {
        if (this.E == 0) {
            if (this.f34j) {
                super.a(graphics);
            }
        } else {
            this.a.a(this.f89a, this.E, this.j, null, this);
            this.E = 0;
            this.f34j = true;
        }
    }

    @Override // defpackage.am
    public final void a(int i) {
        this.f36l = false;
        if (i == 110 || (i == 105 && this.f105b[this.l] == 1)) {
            this.m = this.f35k;
            this.f35k = super.mo6a();
            new Thread(this).start();
        } else if (this.b != 18 || (i != 111 && (i != 105 || this.f105b[this.l] != 2))) {
            super.a(i);
        } else {
            this.f36l = true;
            new Thread(this).start();
        }
    }

    @Override // defpackage.ag, defpackage.am
    /* renamed from: a */
    public final String mo6a() {
        return new StringBuffer().append(b.m26a(super.mo6a(), f121c)).append(f119a).append(this.i).toString();
    }

    @Override // defpackage.ag, defpackage.am
    protected final void a(boolean z) {
        if (a(z)) {
            this.B = this.A;
        }
        int i = this.e;
        if (this.f93a != null && this.e <= this.f93a.b()) {
            this.e = this.f93a.b();
            this.A += this.e - i;
            this.D -= (this.e - i) / 2;
        }
        super.a();
    }

    private String a(String str) {
        String upperCase = str.toUpperCase();
        return a(upperCase, ".JPG.JPEG.PNG.GIF.BMP.TIFF.PCX.TIF.WBMP.EXIF.MBM.OTA.OTA.WMF") ? "$photo$" : a(upperCase, ".MP3.WAV.WAVE.MID.MIDI.PCM.AMR.WMA.OGG.MOD.AAC.AA.TTA.AIFF.SMF.MMF.M4A.RA.WVE.") ? "$sound$" : a(upperCase, ".MP4.3GP.3GPP.FLW.RM.WMV.AVI.VIC.MO.MPG.MPEG.3G2.3GP2.M4V.VOB.") ? "$video$" : a(upperCase, ".SWF.") ? "$flash$" : a(upperCase, ".TXT.TEXT.") ? "$text$" : "$file$";
    }

    private static boolean a(String str, String str2) {
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf >= 0 && str2.indexOf(new StringBuffer().append(str.substring(iLastIndexOf)).append(".").toString()) >= 0;
    }
}
