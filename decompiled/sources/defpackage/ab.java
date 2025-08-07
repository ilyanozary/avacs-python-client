package defpackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Item;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.RecordControl;
import javax.microedition.media.control.VideoControl;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:ab.class */
public class ab extends am implements Runnable, CommandListener {
    private Command a;
    private Command b;

    /* renamed from: a, reason: collision with other field name */
    private Form f11a;

    /* renamed from: a, reason: collision with other field name */
    private Player f12a;

    /* renamed from: a, reason: collision with other field name */
    private RecordControl f13a;

    /* renamed from: a, reason: collision with other field name */
    private VideoControl f14a;

    /* renamed from: a, reason: collision with other field name */
    private Displayable f15a;
    private String g;
    private String h;

    /* renamed from: a, reason: collision with other field name */
    private ByteArrayOutputStream f16a;
    private String i;

    /* renamed from: j, reason: collision with other field name */
    private String f18j;
    private boolean j = true;
    private int E = 0;
    private boolean k = false;
    private boolean l = true;

    /* renamed from: b, reason: collision with other field name */
    private long f17b = 0;
    private long c = 0;
    private boolean m = false;
    private boolean n = false;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v103 */
    /* JADX WARN: Type inference failed for: r0v118 */
    /* JADX WARN: Type inference failed for: r0v120 */
    @Override // defpackage.am
    public final void a() throws Exception {
        int displayWidth;
        int displayHeight;
        String[] strArrA = b.a(this.f101d, f120b);
        int i = Integer.parseInt(strArrA[0]);
        if (i == 0 || i == 2) {
            this.l = false;
        }
        if (i == 2 || i == 3) {
            this.m = true;
        }
        String str = strArrA[1];
        this.f18j = strArrA[2];
        boolean z = false;
        this.f15a = this.f87a.getCurrent();
        Exception exc = null;
        switch (this.b) {
            case 26:
                try {
                    if (str.length() < 2) {
                        str = this.l ? "audio_video,video/3gpp,video/h264,video,devcam0" : "audio?encoding=mp3,audio?encoding=audio/mp3,audio?encoding=amr,audio?encoding=audio/amr,audio?encoding=pcm&rate=8000&bits=8,audio?encoding=pcm&rate=11025&bits=8,audio?encoding=pcm&rate=11025,audio?encoding=pcm&rate=22050,audio";
                    }
                    String[] strArrA2 = b.a(str, ',');
                    int i2 = 0;
                    while (true) {
                        ?? r0 = i2;
                        if (r0 < strArrA2.length) {
                            try {
                                this.f12a = Manager.createPlayer(new StringBuffer().append("capture://").append(strArrA2[i2]).toString());
                                r0 = 1;
                                z = true;
                            } catch (Exception e) {
                                exc = r0;
                                i2++;
                            }
                        }
                    }
                } catch (Exception e2) {
                    z = false;
                    exc = e2;
                }
                if (!z) {
                    throw exc;
                }
                this.f12a.realize();
                this.f13a = (RecordControl) this.f12a.getControl("RecordControl");
                if (this.l) {
                    this.f14a = (VideoControl) this.f12a.getControl("VideoControl");
                    if (this.f14a == null) {
                        z = false;
                    }
                }
                if (this.f13a == null) {
                    z = false;
                }
                if (this.l) {
                    this.g = new StringBuffer().append("").append(System.getProperty("microedition.platform")).toString();
                    this.g = this.g.toUpperCase();
                    if (this.g.indexOf("SONYERICSSON") >= 0) {
                        this.j = !b.a(this.g, new String[]{"K700", "K50", "Z500", "S70", "K30", "J30"});
                    }
                }
                if (z) {
                    if (this.j) {
                        try {
                            if (this.l) {
                                this.f14a.initDisplayMode(1, this.f87a.getCurrent());
                            }
                            this.f12a.start();
                        } catch (Exception unused) {
                            z = false;
                            this.j = false;
                        }
                    }
                    if (!this.j) {
                        try {
                            this.f11a = new Form(this.f90a);
                            if (this.l) {
                                this.f11a.append((Item) this.f14a.initDisplayMode(0, null));
                                try {
                                    this.f14a.setDisplayFullScreen(true);
                                } catch (Exception unused2) {
                                }
                            }
                            this.f12a.start();
                            this.a = new Command(this.f99b, 4, 1);
                            this.b = new Command(this.f100c, 7, 1);
                            this.f11a.setCommandListener(this);
                            if (this.h != 0) {
                                this.f11a.addCommand(this.a);
                            }
                            if (this.i != 0) {
                                this.f11a.addCommand(this.b);
                            }
                            this.f87a.setCurrent(this.f11a);
                            z = true;
                        } catch (Exception unused3) {
                            z = false;
                        }
                    }
                }
                if (!z) {
                    this.E = -113;
                    this.h = exc.getMessage();
                    return;
                }
                if (this.j) {
                    if (this.l) {
                        displayWidth = this.f14a.getDisplayWidth();
                        displayHeight = this.f14a.getDisplayHeight();
                    } else {
                        displayWidth = this.z;
                        displayHeight = -2;
                    }
                    int i3 = displayHeight + this.f103j + this.e + 13;
                    int i4 = displayWidth + 8;
                    this.D += (this.A - i3) / 2;
                    this.A = i3;
                    this.C += (this.z - i4) / 2;
                    this.z = i4;
                    if (this.l) {
                        try {
                            this.f14a.setDisplayLocation(this.C + 3, this.D + this.e + 4);
                            return;
                        } catch (Exception unused4) {
                            return;
                        }
                    }
                    return;
                }
                return;
            case 27:
                this.i = str;
                new Thread(this).start();
                return;
            default:
                return;
        }
    }

    @Override // javax.microedition.lcdui.CommandListener
    public void commandAction(Command command, Displayable displayable) throws Exception {
        if (command == this.a) {
            this.l = 0;
            a(Opcodes.LMUL);
        }
        if (command == this.b) {
            this.l = 1;
            a(Opcodes.LMUL);
        }
    }

    @Override // defpackage.am
    public final void a(int i) throws Exception {
        if (i == 111) {
            this.l = 1;
        } else if (i == 110) {
            this.l = 0;
        }
        switch (i) {
            case Opcodes.LMUL /* 105 */:
            case Opcodes.FDIV /* 110 */:
            case Opcodes.DDIV /* 111 */:
                if (this.f105b[this.l] != 1 && this.f105b[this.l] != 2) {
                    super.a(i);
                    break;
                } else if (this.b != 26) {
                    if (this.f12a != null) {
                        if (this.f105b[this.l] != 2) {
                            b();
                            this.k = false;
                            a();
                            break;
                        } else {
                            if (this.k) {
                                int state = this.f12a.getState();
                                Player player = this.f12a;
                                if (state == 400) {
                                    try {
                                        this.f12a.stop();
                                        this.c = System.currentTimeMillis();
                                        break;
                                    } catch (MediaException unused) {
                                        return;
                                    }
                                }
                            }
                            if (this.k) {
                                int state2 = this.f12a.getState();
                                Player player2 = this.f12a;
                                if (state2 == 300) {
                                    try {
                                        this.f12a.start();
                                        if (this.c > 0) {
                                            this.f17b = System.currentTimeMillis() - (this.c - this.f17b);
                                        } else {
                                            this.f17b = System.currentTimeMillis();
                                        }
                                        this.c = 0L;
                                        break;
                                    } catch (MediaException unused2) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    new Thread(this).start();
                    break;
                }
                break;
            default:
                super.a(i);
                break;
        }
    }

    @Override // defpackage.am
    protected final void a(boolean z) {
        int displayWidth;
        int displayHeight;
        int displayWidth2;
        int displayHeight2;
        switch (this.b) {
            case 26:
                if (!this.l) {
                    a(z);
                    break;
                } else if (a(z) && this.j) {
                    if (this.l) {
                        displayWidth2 = this.f14a.getDisplayWidth();
                        displayHeight2 = this.f14a.getDisplayHeight();
                    } else {
                        displayWidth2 = this.z;
                        displayHeight2 = -2;
                    }
                    int i = displayHeight2 + this.f103j + this.e + 13;
                    int i2 = displayWidth2 + 8;
                    this.D += (this.A - i) / 2;
                    this.A = i;
                    this.C += (this.z - i2) / 2;
                    this.z = i2;
                    if (this.l) {
                        try {
                            this.f14a.setDisplayLocation(this.C + 3, this.D + this.e + 4);
                            break;
                        } catch (Exception unused) {
                        }
                    }
                }
                break;
            case 27:
                if (this.j) {
                    a(z);
                    if (this.l) {
                        displayWidth = this.f14a.getDisplayWidth();
                        displayHeight = this.f14a.getDisplayHeight();
                    } else {
                        displayWidth = this.z;
                        displayHeight = 0;
                    }
                    int i3 = displayHeight + this.f103j + this.e + 11;
                    int i4 = displayWidth + 6;
                    this.D += (this.A - i3) / 2;
                    this.A = i3;
                    this.C += (this.z - i4) / 2;
                    this.z = i4;
                    if (this.l) {
                        try {
                            this.f14a.setDisplayLocation(this.C + 2, this.D + this.e + 3);
                        } catch (Exception unused2) {
                        }
                    }
                    this.a.a(this.f89a, -200, null, null, this);
                    break;
                }
                break;
        }
    }

    @Override // defpackage.am
    public final void a(Graphics graphics) {
        if (this.E != 0) {
            this.a.a(this.f89a, this.E, this.h, null, this);
            this.E = 0;
            return;
        }
        if (this.j) {
            if (this.b == 26 || this.k) {
                if (this.k) {
                    int state = this.f12a.getState();
                    Player player = this.f12a;
                    if (state == 400) {
                        this.f90a = new StringBuffer().append("").append(System.currentTimeMillis() - this.f17b).toString();
                    }
                }
                this.f110a = x.a(this.f93a, graphics, this.C, this.D, this.z, this.A, this.f102a, this.e, this.f90a, this.f118c[7][this.g], this.f118c[6][this.g], this.f118c[0][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.l, this.f118c[4][this.g], this.f118c[5][this.g], this.f118c[16][this.g], this.f118c[15][this.g], this.f94c, this.f105b, this.f95d, true, this.f118c[1][this.g], this.r, 20, this.d, this.f109g, this.f106e, this.f107f);
                this.f111b = x.a(this.h, this.i, this.l, this.f105b, this.f99b, this.f100c, this.f102a, graphics, this.C + 2, (this.D + this.A) - 2, this.z - 6, this.f118c[14][this.g], this.f118c[15][this.g], this.f118c[12][this.g], this.f118c[13][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.f118c[0][this.g], 0, this.t, this.c, this.f95d, this.f109g);
                graphics.setClip(this.C, this.D, this.z, this.A);
                x.a(graphics, this.C + 2, this.D + this.f103j + 3, this.z - 6, ((this.f111b[0][1] - this.D) - this.f103j) - 4, this.f118c[2][this.g], this.f118c[3][this.g], false, this.f118c[9][this.g], this.f116b, this.q, this.r);
                if (!this.l || this.f14a == null || this.n) {
                    return;
                }
                this.f14a.setVisible(true);
                this.n = true;
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        int displayWidth;
        int displayHeight;
        switch (this.b) {
            case 26:
                try {
                    if (!this.k) {
                        this.f16a = new ByteArrayOutputStream();
                        this.f13a.setRecordStream(this.f16a);
                        this.f13a.startRecord();
                        this.k = true;
                        this.f17b = System.currentTimeMillis();
                        break;
                    } else {
                        String contentType = this.f13a.getContentType();
                        this.f13a.commit();
                        byte[] byteArray = this.f16a.toByteArray();
                        b();
                        this.a.a(this.f89a, this.h, new StringBuffer().append(contentType).append("=").append(this.f18j).toString(), byteArray, this);
                        this.a.a(this.f89a, 1, null, null, this);
                        break;
                    }
                } catch (Error e) {
                    b();
                    this.E = -116;
                    this.h = new StringBuffer().append("").append(e).append(". ").append(e.getMessage()).toString();
                    return;
                } catch (Exception e2) {
                    b();
                    this.E = -116;
                    this.h = new StringBuffer().append("").append(e2).append(". ").append(e2.getMessage()).toString();
                    return;
                }
            case 27:
                try {
                    if (!this.k) {
                        int[] iArrA = b.a((byte[]) this.f97a);
                        if (this.m) {
                            this.f12a = Manager.createPlayer(new String((byte[]) this.f97a, iArrA[0], iArrA[1]));
                            this.f12a.realize();
                        } else {
                            this.f12a = Manager.createPlayer(new ByteArrayInputStream((byte[]) this.f97a, iArrA[0], iArrA[1]), this.i);
                            this.f12a.realize();
                        }
                        boolean z = true;
                        if (this.l) {
                            this.f14a = (VideoControl) this.f12a.getControl("VideoControl");
                            if (this.f14a == null) {
                                z = false;
                            }
                            this.g = new StringBuffer().append("").append(System.getProperty("microedition.platform")).toString();
                            this.g = this.g.toUpperCase();
                            if (this.g.indexOf("SONYERICSSON") >= 0) {
                                this.j = !b.a(this.g, new String[]{"K700", "K50", "Z500", "S70", "K30", "J30"});
                            }
                        }
                        if (z) {
                            if (this.j) {
                                try {
                                    if (this.l) {
                                        this.f14a.initDisplayMode(1, this.f87a.getCurrent());
                                    }
                                    this.f12a.start();
                                    this.k = true;
                                    this.f17b = System.currentTimeMillis();
                                } catch (Exception unused) {
                                    z = false;
                                    this.j = false;
                                }
                            }
                            if (!this.j) {
                                try {
                                    this.f11a = new Form(this.f90a);
                                    if (this.l) {
                                        this.f11a.append((Item) this.f14a.initDisplayMode(0, null));
                                        try {
                                            this.f14a.setDisplayFullScreen(true);
                                        } catch (Exception unused2) {
                                        }
                                    }
                                    this.f12a.start();
                                    this.k = true;
                                    this.f17b = System.currentTimeMillis();
                                    this.a = new Command(this.f99b, 4, 1);
                                    this.b = new Command(this.f100c, 7, 1);
                                    this.f11a.setCommandListener(this);
                                    if (this.h != 0) {
                                        this.f11a.addCommand(this.a);
                                    }
                                    if (this.i != 0) {
                                        this.f11a.addCommand(this.b);
                                    }
                                    this.f87a.setCurrent(this.f11a);
                                    z = true;
                                } catch (Exception unused3) {
                                    z = false;
                                }
                            }
                        }
                        if (!z) {
                            this.E = -113;
                            this.h = "";
                            break;
                        } else if (this.j) {
                            if (this.l) {
                                displayWidth = this.f14a.getDisplayWidth();
                                displayHeight = this.f14a.getDisplayHeight();
                            } else {
                                displayWidth = this.z;
                                displayHeight = 0;
                            }
                            int i = displayHeight + this.f103j + this.e + 11;
                            int i2 = displayWidth + 6;
                            this.D += (this.A - i) / 2;
                            this.A = i;
                            this.C += (this.z - i2) / 2;
                            this.z = i2;
                            if (this.l) {
                                try {
                                    this.f14a.setDisplayLocation(this.C + 2, this.D + this.e + 3);
                                } catch (Exception unused4) {
                                }
                            }
                            this.a.a(this.f89a, -200, null, null, this);
                            break;
                        }
                    }
                } catch (Error e3) {
                    b();
                    this.E = -116;
                    this.h = new StringBuffer().append("").append(e3).append(". ").append(e3.getMessage()).toString();
                } catch (Exception e4) {
                    b();
                    this.E = -116;
                    this.h = new StringBuffer().append("").append(e4).append(". ").append(e4.getMessage()).toString();
                    return;
                }
                break;
        }
    }

    @Override // defpackage.am
    public final boolean a(int i, int i2) throws Exception {
        if (this.j) {
            return super.a(i, i2);
        }
        a(Opcodes.LMUL);
        return true;
    }

    @Override // defpackage.am
    /* renamed from: a, reason: collision with other method in class */
    public final String mo6a() {
        b();
        return this.f18j;
    }

    private void b() {
        try {
            this.f12a.stop();
            this.f12a.close();
            this.f12a = null;
        } catch (Error unused) {
        } catch (Exception unused2) {
        }
        if (this.j) {
            return;
        }
        this.f87a.setCurrent(this.f15a);
    }
}
