package defpackage;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Item;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:ac.class */
public class ac extends am implements Runnable, CommandListener {
    private Command a;
    private Command b;

    /* renamed from: a, reason: collision with other field name */
    private Form f19a;

    /* renamed from: a, reason: collision with other field name */
    private Player f20a;

    /* renamed from: a, reason: collision with other field name */
    private VideoControl f21a;

    /* renamed from: a, reason: collision with other field name */
    private Displayable f22a;
    private String g;
    private String h;
    private boolean j = true;
    private int E = 0;

    @Override // defpackage.am
    public final void a() {
        boolean z = true;
        this.f22a = this.f87a.getCurrent();
        try {
            try {
                this.f20a = Manager.createPlayer("capture://image");
            } catch (Exception unused) {
                try {
                    this.f20a = Manager.createPlayer("capture://video");
                } catch (Exception unused2) {
                    this.f20a = Manager.createPlayer("capture://devcam0");
                }
            }
            this.f20a.realize();
            this.f21a = (VideoControl) this.f20a.getControl("VideoControl");
            if (this.f21a == null) {
                z = false;
            }
        } catch (Exception unused3) {
            z = false;
        }
        this.g = new StringBuffer().append("").append(System.getProperty("microedition.platform")).toString();
        this.g = this.g.toUpperCase();
        if (this.g.indexOf("SONYERICSSON") >= 0) {
            this.j = !b.a(this.g, new String[]{"K700", "K50", "Z500", "S70", "K30", "J30"});
        }
        if (z) {
            if (this.j) {
                try {
                    this.f21a.initDisplayMode(1, this.f87a.getCurrent());
                    this.f20a.start();
                } catch (Exception unused4) {
                    z = false;
                    this.j = false;
                }
            }
            if (!this.j) {
                try {
                    this.f19a = new Form(this.f90a);
                    Form form = this.f19a;
                    VideoControl videoControl = this.f21a;
                    VideoControl videoControl2 = this.f21a;
                    form.append((Item) videoControl.initDisplayMode(0, null));
                    try {
                        this.f21a.setDisplayFullScreen(true);
                    } catch (Exception unused5) {
                    }
                    this.f20a.start();
                    this.a = new Command(this.f99b, 4, 1);
                    this.b = new Command(this.f100c, 7, 1);
                    this.f19a.setCommandListener(this);
                    if (this.h != 0) {
                        this.f19a.addCommand(this.a);
                    }
                    if (this.i != 0) {
                        this.f19a.addCommand(this.b);
                    }
                    this.f87a.setCurrent(this.f19a);
                    z = true;
                } catch (Exception unused6) {
                    z = false;
                }
            }
        }
        if (!z) {
            this.E = -113;
            this.h = "";
            return;
        }
        if (this.j) {
            int displayWidth = this.f21a.getDisplayWidth();
            int displayHeight = this.f21a.getDisplayHeight() + this.f103j + this.f103j + 13;
            int i = displayWidth + 8;
            this.D += (this.A - displayHeight) / 2;
            this.A = displayHeight;
            this.C += (this.z - i) / 2;
            this.z = i;
            try {
                this.f21a.setDisplayLocation(this.C + 3, this.D + this.f103j + 4);
            } catch (Exception unused7) {
            }
        }
    }

    @Override // defpackage.am
    protected final void a(boolean z) {
        if (this.j && a(z)) {
            int displayWidth = this.f21a.getDisplayWidth();
            int displayHeight = this.f21a.getDisplayHeight() + this.f103j + this.f103j + 13;
            int i = displayWidth + 8;
            this.D += (this.A - displayHeight) / 2;
            this.A = displayHeight;
            this.C += (this.z - i) / 2;
            this.z = i;
            try {
                this.f21a.setDisplayLocation(this.C + 3, this.D + this.f103j + 4);
            } catch (Exception unused) {
            }
        }
    }

    @Override // defpackage.am
    public final void a(Graphics graphics) {
        if (this.E != 0) {
            this.a.a(this.f89a, this.E, this.h, null, this);
            this.E = 0;
        } else if (this.j) {
            if (this.r == 0) {
                this.f110a = x.a(this.f93a, graphics, this.C, this.D, this.z, this.A, this.f102a, this.e, this.f90a, this.f118c[7][this.g], this.f118c[6][this.g], this.f118c[0][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.l, this.f118c[4][this.g], this.f118c[5][this.g], this.f118c[16][this.g], this.f118c[15][this.g], this.f94c, this.f105b, this.f95d, true, this.f118c[1][this.g], this.r, 20, this.d, this.f109g, this.f106e, this.f107f);
            }
            this.f111b = x.a(this.h, this.i, this.l, this.f105b, this.f99b, this.f100c, this.f102a, graphics, this.C + 2, (this.D + this.A) - 2, this.z - 6, this.f118c[14][this.g], this.f118c[15][this.g], this.f118c[12][this.g], this.f118c[13][this.g], this.f118c[2][this.g], this.f118c[3][this.g], this.f118c[0][this.g], 0, this.t, this.c, this.f95d, this.f109g);
            graphics.setClip(this.C, this.D, this.z, this.A);
            if (this.r == 0) {
                x.a(graphics, this.C + 2, this.D + this.f103j + 3, this.z - 6, ((this.f111b[0][1] - this.D) - this.f103j) - 4, this.f118c[2][this.g], this.f118c[3][this.g], false, this.f118c[9][this.g], this.f116b, this.q, this.r);
                this.f21a.setVisible(true);
            }
        }
    }

    @Override // javax.microedition.lcdui.CommandListener
    public void commandAction(Command command, Displayable displayable) {
        if (command == this.a) {
            this.l = 0;
            a(Opcodes.LMUL);
        }
        if (command == this.b) {
            this.l = 1;
            a(Opcodes.LMUL);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v36, types: [java.lang.StringBuffer] */
    /* JADX WARN: Type inference failed for: r2v11, types: [java.lang.Exception] */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.lang.Object] */
    @Override // java.lang.Runnable
    public void run() {
        int i = 0;
        byte[] snapshot = null;
        int i2 = 0;
        try {
            try {
                if (this.j) {
                    this.f21a.setVisible(false);
                }
            } catch (Exception unused) {
            }
            String string = new StringBuffer().append("").append(System.getProperty("video.snapshot.encodings")).toString();
            int iIndexOf = this.g.indexOf("SONYERICSSON");
            if (iIndexOf >= 0) {
                try {
                    int iIndexOf2 = string.indexOf(" ");
                    int length = iIndexOf2;
                    if (iIndexOf2 == -1) {
                        length = string.length();
                    }
                    snapshot = this.f21a.getSnapshot(string.substring(0, length));
                    iIndexOf = 7;
                    i2 = 7;
                } catch (Exception e) {
                    i = iIndexOf;
                }
                int i3 = i2;
                if (i3 == 0) {
                    try {
                        snapshot = this.f21a.getSnapshot(null);
                        i3 = 8;
                        i2 = 8;
                    } catch (Exception e2) {
                        i = i3;
                    }
                }
            }
            int iIndexOf3 = string.toUpperCase().indexOf("JPEG");
            if (iIndexOf3 >= 0) {
                try {
                    snapshot = this.f21a.getSnapshot("encoding=jpeg&width=640&height=480");
                    iIndexOf3 = 1;
                    i2 = 1;
                } catch (Exception e3) {
                    i = iIndexOf3;
                }
                int i4 = i2;
                if (i4 == 0) {
                    try {
                        snapshot = this.f21a.getSnapshot("encoding=jpeg&width=320&height=240");
                        i4 = 2;
                        i2 = 2;
                    } catch (Exception e4) {
                        i = i4;
                    }
                }
                int i5 = i2;
                if (i5 == 0) {
                    try {
                        snapshot = this.f21a.getSnapshot("encoding=jpeg&width=160&height=120");
                        i5 = 3;
                        i2 = 3;
                    } catch (Exception e5) {
                        i = i5;
                    }
                }
                int i6 = i2;
                if (i6 == 0) {
                    try {
                        snapshot = this.f21a.getSnapshot("encoding=jpeg");
                        i6 = 4;
                        i2 = 4;
                    } catch (Exception e6) {
                        i = i6;
                    }
                }
            }
            int i7 = i2;
            if (i7 == 0) {
                try {
                    snapshot = this.f21a.getSnapshot("encoding=png&width=160&height=120");
                    i7 = 5;
                    i2 = 5;
                } catch (Exception e7) {
                    i = i7;
                }
            }
            int length2 = i2;
            if (length2 == 0 || (i2 > 0 && (length2 = snapshot.length) <= 1500)) {
                try {
                    snapshot = this.f21a.getSnapshot(null);
                    length2 = 6;
                    i2 = 6;
                } catch (Exception e8) {
                    i = length2;
                }
            }
            b();
            if (i2 > 0) {
                this.a.a(this.f89a, this.h, this.f101d, snapshot, this);
                this.a.a(this.f89a, 1, null, null, this);
            } else {
                this.E = -116;
                this.h = new StringBuffer().append("").append(i).append(". ").append(i.getMessage()).toString();
            }
        } catch (Error e9) {
            this.E = -116;
            this.h = new StringBuffer().append("").append(e9).append(". ").append(e9.getMessage()).toString();
        }
    }

    @Override // defpackage.am
    public final void a(int i) {
        switch (i) {
            case Opcodes.LMUL /* 105 */:
            case Opcodes.FDIV /* 110 */:
                if (this.f105b[this.l] != 1) {
                    super.a(i);
                    break;
                } else {
                    new Thread(this).start();
                    break;
                }
            default:
                super.a(i);
                break;
        }
    }

    @Override // defpackage.am
    public final boolean a(int i, int i2) {
        a(Opcodes.LMUL);
        return true;
    }

    @Override // defpackage.am
    /* renamed from: a */
    public final String mo6a() {
        b();
        return this.f101d;
    }

    private void b() {
        try {
            this.f20a.stop();
            this.f20a.close();
            this.f20a = null;
        } catch (Exception unused) {
        }
        if (this.j) {
            return;
        }
        this.f87a.setCurrent(this.f22a);
    }
}
