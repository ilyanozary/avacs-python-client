package defpackage;

import avacs.c;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.media.control.ToneControl;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.signature.SignatureVisitor;

/* loaded from: avacs.jar:aj.class */
public final class aj implements Runnable, o {
    private static aj a = null;

    /* renamed from: a, reason: collision with other field name */
    private Canvas f53a;

    /* renamed from: a, reason: collision with other field name */
    private Display f54a;

    /* renamed from: a, reason: collision with other field name */
    private String[] f57a;

    /* renamed from: a, reason: collision with other field name */
    private int f58a;

    /* renamed from: b, reason: collision with other field name */
    private int f59b;

    /* renamed from: b, reason: collision with other field name */
    public boolean f64b;

    /* renamed from: a, reason: collision with other field name */
    private c f65a;

    /* renamed from: b, reason: collision with other field name */
    private long f71b;

    /* renamed from: a, reason: collision with other field name */
    private String f73a;

    /* renamed from: b, reason: collision with other field name */
    private String f74b;

    /* renamed from: c, reason: collision with other field name */
    private String f75c;

    /* renamed from: d, reason: collision with other field name */
    private String f76d;

    /* renamed from: a, reason: collision with other field name */
    public boolean f51a = true;
    private boolean f = true;
    private boolean g = false;

    /* renamed from: a, reason: collision with other field name */
    public Object f52a = null;

    /* renamed from: a, reason: collision with other field name */
    private char f55a = '|';
    private char b = '&';
    private char c = '~';
    private char d = '^';
    private char e = '=';

    /* renamed from: f, reason: collision with other field name */
    private char f56f = '\r';

    /* renamed from: a, reason: collision with other field name */
    private Font f60a = null;

    /* renamed from: a, reason: collision with other field name */
    private Vector f61a = new Vector(6, 1);

    /* renamed from: b, reason: collision with other field name */
    private Vector f62b = new Vector(3, 1);

    /* renamed from: a, reason: collision with other field name */
    private am f63a = null;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;

    /* renamed from: c, reason: collision with other field name */
    public boolean f66c = false;

    /* renamed from: d, reason: collision with other field name */
    public boolean f67d = false;

    /* renamed from: e, reason: collision with other field name */
    public boolean f68e = false;
    private boolean k = false;
    private boolean l = false;

    /* renamed from: a, reason: collision with other field name */
    private int[][] f69a = new int[4][2];

    /* renamed from: a, reason: collision with other field name */
    private long f70a = 0;

    /* renamed from: c, reason: collision with other field name */
    private long f72c = 4000;

    /* renamed from: c, reason: collision with other field name */
    private int f77c = 0;

    /* renamed from: d, reason: collision with other field name */
    private int f78d = 0;

    /* renamed from: e, reason: collision with other field name */
    private int f79e = 0;

    /* renamed from: f, reason: collision with other field name */
    private int f80f = 45000;

    /* renamed from: d, reason: collision with other field name */
    private long f81d = 62000;

    /* renamed from: e, reason: collision with other field name */
    private String f82e = "";

    /* renamed from: g, reason: collision with other field name */
    private int f83g = 0;

    public aj(Display display, String[] strArr, c cVar) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        a = this;
        this.f57a = strArr;
        this.f54a = display;
        this.f65a = cVar;
        Object objM32a = b.m32a();
        this.f53a = (Canvas) objM32a;
        ((o) objM32a).a(this);
        display.setCurrent(this.f53a);
    }

    @Override // defpackage.o
    public final void a(Graphics graphics) {
        int width = this.f53a.getWidth();
        int height = this.f53a.getHeight();
        if (width != this.f58a || height != this.f59b) {
            if (width == this.f59b && height == this.f58a) {
                this.g = true;
            }
            this.f58a = width;
            this.f59b = height;
            this.f = true;
            this.f51a = true;
            if (this.l && !this.f67d && !this.i) {
                a((String) null, 1, 536, new StringBuffer().append("").append(this.f58a).append(this.e).append(this.f59b).toString(), (Object) null, (am) null);
            }
        }
        boolean z = this.f51a;
        boolean z2 = this.f;
        am amVar = this.f63a;
        this.f63a = null;
        if (amVar != null && amVar.f) {
            z = true;
            amVar = null;
        }
        boolean z3 = this.k;
        if (this.k) {
            this.k = false;
        }
        boolean z4 = this.f65a.d;
        int size = this.f61a.size();
        for (int i = 0; i < this.f62b.size(); i++) {
            am amVarC = c(this.f62b, i);
            if (amVarC != null) {
                int i2 = amVarC.b;
                if (size <= 0 || !(i2 == 30 || i2 == 32 || i2 == 23)) {
                    amVarC.c(this.l);
                } else {
                    amVarC.c(false);
                }
                if (z2) {
                    amVarC.x = this.f58a;
                    amVarC.y = this.f59b;
                    amVarC.b(this.g);
                }
                if ((z || ((i2 == 30 || i2 == 32 || i2 == 23) && amVarC.m15a())) && (!z4 || z || i2 == 23 || i2 == 32 || z3)) {
                    amVarC.a(graphics, z4, z);
                }
            }
        }
        if (z) {
            for (int i3 = 0; i3 < this.f61a.size() - 1; i3++) {
                am amVarC2 = c(this.f61a, i3);
                if (amVarC2 != null) {
                    amVarC2.c(false);
                    if (z2) {
                        amVarC2.x = this.f58a;
                        amVarC2.y = this.f59b;
                        amVarC2.b(this.g);
                    }
                    amVarC2.a(graphics, z4, z);
                    if (amVar != null && amVar == amVarC2) {
                        amVar = null;
                    }
                }
            }
        }
        am amVarA = a(this.f61a);
        if (amVarA != null) {
            if (amVar != null && amVar != amVarA) {
                amVar.a(graphics, z4, z);
            }
            amVarA.c(true);
            if (amVarA.m15a()) {
                switch (amVarA.b) {
                    case 31:
                        this.f77c = amVarA.n;
                        break;
                    case 34:
                        this.f78d = amVarA.n;
                        break;
                    case Canvas.KEY_POUND /* 35 */:
                        this.f79e = amVarA.n;
                        break;
                }
            }
            if (z2) {
                amVarA.x = this.f58a;
                amVarA.y = this.f59b;
                amVarA.b(this.g);
            }
            if ((!z4 || z || z3) && (z || amVarA.m15a() || amVarA.g != 0)) {
                amVarA.a(graphics, z4, z);
            }
        }
        if (z) {
            this.f51a = false;
        }
        if (z2) {
            this.f = false;
        }
    }

    @Override // defpackage.o
    public final void a(int i) {
        am amVar;
        int i2;
        if (this.j || this.f66c) {
            return;
        }
        int i3 = 0;
        switch (i) {
            case Canvas.KEY_POUND /* 35 */:
                j();
                break;
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case SignatureVisitor.EXTENDS /* 43 */:
            case 44:
            case SignatureVisitor.SUPER /* 45 */:
            case 46:
            case 47:
            case Opcodes.ASTORE /* 58 */:
            case 59:
            case ToneControl.C4 /* 60 */:
            case SignatureVisitor.INSTANCEOF /* 61 */:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case Opcodes.IASTORE /* 79 */:
            case Opcodes.LASTORE /* 80 */:
            case Opcodes.FASTORE /* 81 */:
            case Opcodes.DASTORE /* 82 */:
            case Opcodes.AASTORE /* 83 */:
            case Opcodes.BASTORE /* 84 */:
            case Opcodes.CASTORE /* 85 */:
            case Opcodes.SASTORE /* 86 */:
            case Opcodes.POP /* 87 */:
            case Opcodes.POP2 /* 88 */:
            case Opcodes.DUP /* 89 */:
            case Opcodes.DUP_X1 /* 90 */:
            case Opcodes.DUP_X2 /* 91 */:
            case Opcodes.DUP2 /* 92 */:
            case Opcodes.DUP2_X1 /* 93 */:
            case Opcodes.DUP2_X2 /* 94 */:
            case Opcodes.SWAP /* 95 */:
            case Opcodes.IADD /* 96 */:
            case Opcodes.FADD /* 98 */:
            case Opcodes.DSUB /* 103 */:
            case Opcodes.IMUL /* 104 */:
            case Opcodes.FMUL /* 106 */:
            case Opcodes.DMUL /* 107 */:
            case Opcodes.IDIV /* 108 */:
            case Opcodes.LDIV /* 109 */:
            case Opcodes.FDIV /* 110 */:
            case Opcodes.INEG /* 116 */:
            case Opcodes.FNEG /* 118 */:
            case Opcodes.LSHL /* 121 */:
            default:
                switch (this.f53a.getGameAction(i)) {
                    case 1:
                        i3 = 103;
                        break;
                    case 2:
                    case 9:
                        i3 = 101;
                        break;
                    case 3:
                    case 4:
                    case 7:
                    default:
                        if (i == 42) {
                            i3 = 115;
                        }
                        if (i == 48 || i == -11 || i == 8 || i == -8) {
                            i3 = 114;
                        }
                        if (i == 10) {
                            i3 = 105;
                        }
                        if (i == 35) {
                            j();
                        }
                        if (i == -6 || i == 21 || i == -21 || i == 57345 || i == -1 || i == -202 || i == 105 || i == 116) {
                            i3 = 110;
                        }
                        if (i == -7 || i == 22 || i == -22 || i == 57346 || i == -20 || i == -4 || i == -203 || i == 106 || i == 121) {
                            i3 = 111;
                        }
                        if (i == 128) {
                            i3 = 102;
                            break;
                        }
                        break;
                    case 5:
                    case 10:
                        i3 = 102;
                        break;
                    case 6:
                        i3 = 104;
                        break;
                    case 8:
                        i3 = 105;
                        break;
                }
            case Canvas.KEY_STAR /* 42 */:
            case Opcodes.LADD /* 97 */:
                i3 = 115;
                break;
            case 48:
                i3 = 114;
                break;
            case 49:
            case Opcodes.LNEG /* 117 */:
            case Opcodes.DNEG /* 119 */:
                i3 = 116;
                break;
            case 50:
            case Opcodes.LSUB /* 101 */:
                i3 = 103;
                break;
            case 51:
            case Opcodes.IREM /* 112 */:
            case Opcodes.FREM /* 114 */:
                i3 = 117;
                break;
            case 52:
            case Opcodes.DREM /* 115 */:
                i3 = 101;
                break;
            case 53:
            case 100:
                i3 = 105;
                break;
            case 54:
            case Opcodes.FSUB /* 102 */:
                i3 = 102;
                break;
            case 55:
            case 57:
            case Opcodes.DADD /* 99 */:
            case Opcodes.LMUL /* 105 */:
            case Opcodes.DDIV /* 111 */:
            case Opcodes.ISHR /* 122 */:
                try {
                    int size = this.f61a.size();
                    if (size <= 1 || (i2 = (amVar = (am) this.f61a.elementAt(size - 1)).b) == 31 || i2 == 34 || i2 == 35 || i2 == 24 || i2 == 11 || i2 == 14 || i2 == 26 || i2 == 27) {
                        break;
                    } else {
                        amVar.c(false);
                        this.f63a = amVar;
                        if (i == 57 || i == 99 || i == 105) {
                            this.f61a.removeElementAt(size - 1);
                            this.f61a.insertElementAt(amVar, 0);
                            break;
                        } else {
                            am amVar2 = (am) this.f61a.elementAt(0);
                            this.f61a.removeElementAt(0);
                            this.f61a.addElement(amVar2);
                        }
                    }
                } catch (Exception e) {
                    break;
                }
                break;
            case 56:
            case Opcodes.ISHL /* 120 */:
                i3 = 104;
                break;
            case Opcodes.LREM /* 113 */:
                i3 = 105;
                break;
        }
        try {
            am amVarA = a();
            if (amVarA != null) {
                amVarA.a(i3);
            }
        } catch (Exception e2) {
        }
        this.k = true;
    }

    private am a() {
        am amVar = null;
        if (this.f61a.size() > 0) {
            amVar = (am) this.f61a.lastElement();
        } else {
            for (int i = 0; i < this.f62b.size(); i++) {
                if (((am) this.f62b.elementAt(i)).b == 30) {
                    amVar = (am) this.f62b.elementAt(i);
                }
            }
        }
        return amVar;
    }

    @Override // defpackage.o
    /* renamed from: a, reason: collision with other method in class */
    public final void mo10a() {
        this.f53a.getWidth();
        this.f53a.getHeight();
        b();
        this.f51a = true;
        this.f67d = false;
    }

    @Override // defpackage.o
    public final void a(int i, int i2) {
        try {
            am amVarA = a();
            if (amVarA != null) {
                amVarA.a(i, i2);
            }
        } catch (Exception e) {
        }
        this.k = true;
    }

    @Override // defpackage.o
    public final void c(int i, int i2) {
        try {
            am amVarA = a();
            if (amVarA != null) {
                amVarA.m13a(i, i2);
            }
        } catch (Exception e) {
        }
        this.k = true;
    }

    @Override // defpackage.o
    public final void b(int i, int i2) {
        try {
            am amVarA = a();
            if (amVarA != null) {
                amVarA.b(i, i2);
            }
        } catch (Exception e) {
        }
        this.k = true;
    }

    @Override // defpackage.o
    public final void b() {
        if (this.h) {
            return;
        }
        this.h = true;
        this.f64b = true;
        new Thread(this).start();
    }

    @Override // java.lang.Runnable
    public final void run() throws InterruptedException {
        int i;
        Thread.currentThread().setPriority(6);
        int i2 = 0;
        while (this.f64b) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (this.f54a.getCurrent() == this.f53a && !this.j) {
                    if (i2 == 2) {
                        this.f60a = Font.getFont(this.f65a.f141b << 5, 0, this.f65a.f140a << 3);
                        i();
                        i = 25;
                        a(-25, "", 25, 0, "", false, "", -48, "", 0, "first0=0=first=ff6600==0==0", "1", "", 0, 0, false, 0, 0, 0, 0, false, 0, false, null);
                    }
                    i2++;
                    if (this.f70a < jCurrentTimeMillis) {
                        this.f70a = jCurrentTimeMillis + this.f80f;
                        if (!this.f66c && !this.f68e && this.l && !this.f67d && !this.i) {
                            c cVar = this.f65a;
                            i = this.f65a.e ? Opcodes.FDIV : Opcodes.ISHL;
                            cVar.a("15|", (byte[]) null, i, 0);
                        }
                    }
                    this.f53a.repaint();
                    this.f53a.serviceRepaints();
                }
                i iVarM21a = this.f65a.m21a();
                if (this.l && iVarM21a != null) {
                    iVarM21a.a(this.f81d);
                }
                int i3 = i;
                try {
                    Thread.sleep(System.currentTimeMillis() - jCurrentTimeMillis < 68 ? 83 - i3 : i3 > 120 ? 5L : 15L);
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
            }
        }
    }

    @Override // defpackage.o
    public final void a(int i, int i2, String str, Object obj, am amVar) {
        a((String) null, i, i2, str, obj, amVar);
    }

    @Override // defpackage.o
    public final void a(int i, int i2, int i3, String str, Object obj, am amVar) {
        a((String) null, i2, i3, str, (Object) null, amVar);
    }

    /* JADX WARN: Type inference failed for: r0v17, types: [avacs.c, java.lang.Exception] */
    @Override // defpackage.o
    public final void a(String str, int i, int i2, String str2, Object obj, am amVar) {
        ?? r0;
        try {
            this.i = false;
            this.f67d = false;
            if (amVar == null || !(amVar.b == 12 || amVar.b == 13)) {
                this.f83g = 0;
            } else {
                this.f61a.removeElement(amVar);
                this.f83g = 1;
            }
            if (amVar != null && (amVar.b == 1 || amVar.b == 11)) {
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    if (i4 >= this.f69a.length) {
                        break;
                    }
                    if (this.f69a[i4][0] == i) {
                        i3 = i4;
                        break;
                    }
                    i4++;
                }
                if (i3 == 0) {
                    for (int length = this.f69a.length - 1; length > 0; length--) {
                        this.f69a[length][0] = this.f69a[length - 1][0];
                        this.f69a[length][1] = this.f69a[length - 1][1];
                    }
                }
                this.f69a[i3][0] = i;
                this.f69a[i3][1] = amVar.n;
            }
            if ((i2 == 1 || i2 == 0) && (this.f73a != null || i != -1)) {
                b(1 - this.f83g);
                if (i == -1) {
                    this.l = true;
                }
            }
            if (i2 < 0) {
                a(i2, str2, amVar != null ? amVar : null, str);
            }
            if (i2 > 1) {
                if (amVar != null && amVar.b() != null) {
                    str2 = new StringBuffer().append(str2).append(this.f55a).append(amVar.b()).toString();
                }
                r0 = this.f65a;
                r0.a(i2, str2, (byte[]) obj);
            }
        } catch (Exception e) {
            r0.printStackTrace();
        }
    }

    private am a(int i, String str, int i2, int i3, String str2, boolean z, String str3, int i4, String str4, int i5, String str5, String str6, String str7, int i6, int i7, boolean z2, int i8, int i9, int i10, int i11, boolean z3, int i12, boolean z4, am amVar) throws InterruptedException, ClassNotFoundException, NumberFormatException, IOException {
        am aeVar = null;
        boolean z5 = true;
        int height = this.f60a.getHeight();
        switch (i2) {
            case 1:
                aeVar = new ag();
                i12 = m11a(i);
                break;
            case 2:
                aeVar = new ae();
                break;
            case 5:
                am amVarB = b(this.f61a, i2);
                aeVar = amVarB;
                if (amVarB == null) {
                    aeVar = new ae();
                    break;
                } else {
                    z5 = false;
                    break;
                }
            case 6:
                aeVar = new ae();
                break;
            case 7:
                aeVar = new ak();
                i12 = m11a(i);
                break;
            case 11:
                aeVar = new ae();
                i12 = m11a(i);
                break;
            case 12:
                this.i = true;
                aeVar = new an();
                break;
            case 13:
                this.i = true;
                aeVar = new an();
                break;
            case 14:
                this.i = true;
                boolean z6 = false;
                try {
                    if (b.m30a()) {
                        Class.forName("javax.microedition.media.control.VideoControl");
                        aeVar = (am) Class.forName("ac").newInstance();
                        z6 = true;
                    }
                } catch (Exception unused) {
                }
                if (!z6) {
                    a(-114, (String) null);
                    break;
                }
                break;
            case Opcodes.DCONST_1 /* 15 */:
                aeVar = new ae();
                i8 = this.f58a;
                i9 = this.f59b;
                i10 = 0;
                i11 = 0;
                break;
            case 16:
                boolean z7 = false;
                try {
                    if (b.m30a()) {
                        aeVar = (am) Class.forName("al").newInstance();
                        z7 = true;
                        this.f52a = this.f65a;
                    }
                } catch (Exception unused2) {
                }
                if (!z7) {
                    str5 = b.a(str5, this.e)[1];
                    i2 = 2;
                    aeVar = new ae();
                    break;
                }
                break;
            case Opcodes.SIPUSH /* 17 */:
                aeVar = new af();
                break;
            case Opcodes.LDC /* 18 */:
                aeVar = new af();
                break;
            case 19:
                boolean z8 = false;
                try {
                    if (b.m30a()) {
                        Class.forName("javax.wireless.messaging.MessageConnection");
                        Class.forName("javax.wireless.messaging.TextMessage");
                        aeVar = (am) Class.forName("ao").newInstance();
                        z8 = true;
                    }
                } catch (Exception unused3) {
                }
                if (!z8) {
                    a(i, i5, b.a(str5, this.e)[2], null, aeVar);
                    break;
                }
                break;
            case Opcodes.FLOAD /* 23 */:
                am amVarB2 = b(this.f62b, i2);
                aeVar = amVarB2;
                if (amVarB2 == null) {
                    aeVar = new ai();
                }
                i8 = this.f58a;
                i9 = height;
                i10 = 0;
                i11 = 0;
                break;
            case Opcodes.DLOAD /* 24 */:
                aeVar = new ai();
                i8 = this.f58a;
                i9 = height;
                i10 = 0;
                i11 = this.f59b - i9;
                z3 = false;
                break;
            case Opcodes.ALOAD /* 25 */:
                aeVar = new ae();
                i8 = this.f58a;
                i9 = this.f59b;
                i10 = 0;
                i11 = 0;
                break;
            case 26:
            case 27:
                this.i = true;
                boolean z9 = false;
                try {
                    if (b.m30a()) {
                        Class.forName("javax.microedition.media.Player");
                        aeVar = (am) Class.forName("ab").newInstance();
                        z9 = true;
                    }
                } catch (Exception unused4) {
                }
                if (!z9) {
                    a(-114, (String) null);
                    break;
                }
                break;
            case 30:
                am amVarB3 = b(this.f62b, i2);
                aeVar = amVarB3;
                if (amVarB3 == null) {
                    aeVar = new ai();
                }
                i8 = this.f58a;
                i9 = this.f59b;
                i10 = 0;
                i11 = 0;
                break;
            case 31:
                aeVar = new ai();
                i9 = this.f59b;
                i8 = this.f58a;
                i10 = 0;
                i11 = 0;
                i12 = this.f77c;
                break;
            case 32:
                aeVar = new ai();
                i8 = this.f58a;
                i9 = height;
                i10 = 0;
                i11 = i9 + 1;
                break;
            case 33:
                i2 = 1;
                aeVar = new ag();
                i12 = m11a(i);
                break;
            case 34:
                aeVar = new ai();
                i9 = this.f59b;
                i8 = this.f58a;
                i10 = 0;
                i11 = 0;
                i12 = this.f78d;
                break;
            case Canvas.KEY_POUND /* 35 */:
                aeVar = new ai();
                i9 = this.f59b;
                i8 = this.f58a;
                i10 = 0;
                i11 = 0;
                i12 = this.f79e;
                break;
            case Opcodes.LSUB /* 101 */:
                b(Integer.parseInt(str5) - this.f83g);
                break;
            case Opcodes.FSUB /* 102 */:
                c cVar = this.f65a;
                c.a(str5, "audio/midi", 1, (byte[]) null);
                break;
            case Opcodes.DMUL /* 107 */:
                c cVar2 = this.f65a;
                c.a("", str5, 1, (byte[]) this.f52a);
                break;
            case Opcodes.IDIV /* 108 */:
                c cVar3 = this.f65a;
                if (c.b) {
                    String[] strArrA = b.a(str5, this.e);
                    c cVar4 = this.f65a;
                    c.c = ((o) this.f53a).a(this.f54a, Integer.parseInt(strArrA[1]), Integer.parseInt(strArrA[0]));
                    c cVar5 = this.f65a;
                    if (!c.c) {
                        c cVar6 = this.f65a;
                        c.b = false;
                        break;
                    }
                }
                break;
            case Opcodes.LDIV /* 109 */:
                this.f81d = Integer.parseInt(str5) * 1000;
                break;
            case Opcodes.FDIV /* 110 */:
                this.f80f = Integer.parseInt(str5) * 1000;
                break;
            case Opcodes.DDIV /* 111 */:
                this.f72c = Integer.parseInt(str5) * 1000;
                break;
            case Opcodes.IREM /* 112 */:
                String[] strArrA2 = b.a(str5, this.d);
                String strSubstring = "";
                for (int i13 = 0; i13 < strArrA2.length; i13++) {
                    String[] strArrA3 = b.a(strArrA2[i13], this.e);
                    if (strArrA3.length == 2) {
                        String strB = b.b(strArrA3[1], this.c);
                        switch (Integer.parseInt(strArrA3[0])) {
                            case 0:
                                strB = b.a(this.f65a, strB, "");
                                break;
                            case 1:
                                strB = new StringBuffer().append("").append(System.getProperty(strB)).toString();
                                break;
                        }
                        strSubstring = new StringBuffer().append(strSubstring).append(this.f55a).append(strArrA2[i13]).append(this.e).append(b.m26a(strB, this.c)).toString();
                    }
                }
                if (strSubstring.length() > 0) {
                    strSubstring = strSubstring.substring(1);
                }
                this.f65a.a(i4, strSubstring, null);
                break;
            case Opcodes.LREM /* 113 */:
                this.f65a.a(str5);
                break;
            case Opcodes.FREM /* 114 */:
                g.m44a(str5);
                this.f = true;
                this.f51a = true;
                break;
            case Opcodes.DREM /* 115 */:
                String[] strArrA4 = b.a(str5, this.e);
                String str8 = strArrA4[2];
                a(Integer.parseInt(strArrA4[0]), strArrA4[1], null, str8.length() < 2 ? null : b.b(str8, this.c));
                break;
            case Opcodes.INEG /* 116 */:
                boolean z10 = false;
                try {
                    if (b.m30a()) {
                        Class.forName("javax.microedition.pim.Contact");
                        aeVar = (am) Class.forName("ad").newInstance();
                        z10 = true;
                    }
                } catch (Exception unused5) {
                }
                if (!z10) {
                    a(-114, (String) null);
                    break;
                }
                break;
            case Opcodes.LNEG /* 117 */:
                boolean z11 = false;
                try {
                    if (b.m30a()) {
                        Class.forName("javax.microedition.location.LocationProvider");
                        aeVar = (am) Class.forName("ah").newInstance();
                        z11 = true;
                        z5 = false;
                    }
                } catch (Exception unused6) {
                }
                if (!z11) {
                    a(-114, (String) null);
                    break;
                }
                break;
            case Opcodes.FNEG /* 118 */:
                String[] strArrA5 = b.a(str5, this.d);
                this.f65a.f135b.delete(0, this.f65a.f135b.length());
                this.f65a.f135b.append(b.b(strArrA5[1], this.c));
                this.f65a.f134a.delete(0, this.f65a.f134a.length());
                this.f65a.f134a.append(strArrA5[0]);
                String[] strArrA6 = b.a(strArrA5[2], Opcodes.FCMPG, this.f55a, this.e, this.c);
                try {
                    Integer.parseInt(strArrA6[0]);
                } catch (Exception unused7) {
                    strArrA6[0] = "0";
                }
                String string = "";
                for (int i14 = 0; i14 < this.f57a.length; i14++) {
                    if (strArrA6[i14] != null) {
                        string = new StringBuffer().append(string).append(i14).append("=").append(strArrA6[i14]).append("|").toString();
                    }
                    this.f57a[i14] = strArrA6[i14];
                }
                e.a(this.f65a.f134a.toString(), this.f65a.f135b.toString(), string);
                a(-332, "");
                break;
            case Opcodes.ISHL /* 120 */:
                String[] strArrA7 = b.a(str5, this.e);
                String strB2 = e.b();
                e.a(strArrA7[0], strArrA7[1]);
                if (strB2.compareTo(strArrA7[1]) != 0) {
                    b.m28a();
                    break;
                }
                break;
            case Opcodes.LSHL /* 121 */:
                String[] strArrA8 = b.a(str5, this.e);
                String string2 = new StringBuffer().append(strArrA8[0]).append(this.f55a).append(b.m26a(this.f65a.m21a().m52b(), this.c)).toString();
                strArrA8[0] = string2;
                this.f65a.a(new StringBuffer().append("").append(i4).append(this.f55a).append(string2).toString(), (byte[]) null, this.f65a.e ? Opcodes.FDIV : Opcodes.ISHL, 0);
                break;
        }
        if (aeVar != null) {
            aeVar.a(this.f54a, this, i, str, i2, i3, str2, str3, i4, str4, i5, str5, str6, str7, i6, i7, z2, this.f58a, this.f59b, i8, i9, i10, i11, this.f60a, z3, i12, this.f52a, amVar, z4);
            if (z) {
                b(1);
            }
            if (aeVar.f) {
                a(aeVar, i2);
            } else if (z5) {
                this.f61a.addElement(aeVar);
            }
        }
        this.f51a = true;
        return aeVar;
    }

    /* renamed from: a, reason: collision with other method in class */
    private int m11a(int i) {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            if (i3 >= this.f69a.length) {
                break;
            }
            if (this.f69a[i3][0] == i) {
                i2 = this.f69a[i3][1];
                break;
            }
            i3++;
        }
        return i2;
    }

    private void g() {
        String strM45a = g.m45a();
        if (strM45a == null || this.f82e.compareTo(strM45a) == 0) {
            this.f82e = "";
        } else {
            a(530, 530, strM45a, null, null);
            this.f82e = strM45a;
        }
    }

    public final void a(int i, String str) throws InterruptedException, NumberFormatException, ClassNotFoundException, IOException {
        a(i, str, null, null);
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
        */
    /* JADX WARN: Not initialized variable reg: 3, insn: MOVE (r2 I:??) = (r3 I:??), block:B:187:0x0c26 */
    private void a(int r27, java.lang.String r28, defpackage.am r29, java.lang.String r30) throws java.lang.InterruptedException, java.lang.NumberFormatException, java.lang.ClassNotFoundException, java.io.IOException {
        /*
            Method dump skipped, instructions count: 3157
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.aj.a(int, java.lang.String, am, java.lang.String):void");
    }

    private am a(Vector vector, int i) {
        am amVar = null;
        int i2 = 0;
        while (true) {
            if (i2 >= vector.size()) {
                break;
            }
            am amVarC = c(vector, i2);
            if (amVarC.f89a == i) {
                amVar = amVarC;
                break;
            }
            i2++;
        }
        return amVar;
    }

    private am b(Vector vector, int i) {
        am amVar = null;
        int i2 = 0;
        while (true) {
            if (i2 >= vector.size()) {
                break;
            }
            am amVarC = c(vector, i2);
            if (amVarC.b == i) {
                amVar = amVarC;
                break;
            }
            i2++;
        }
        return amVar;
    }

    private void b(int i) {
        for (int i2 = 0; i2 < i; i2++) {
            this.f61a.removeElement(a(this.f61a));
        }
        this.f51a = true;
    }

    private void a(am amVar, int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.f62b.size(); i3++) {
            am amVarC = c(this.f62b, i3);
            if (amVarC.b == i) {
                this.f62b.removeElement(amVarC);
                i2 = i3;
            }
        }
        try {
            this.f62b.insertElementAt(amVar, i2);
        } catch (Exception unused) {
        }
    }

    private static am c(Vector vector, int i) {
        am amVar = null;
        try {
            amVar = (am) vector.elementAt(i);
        } catch (Exception unused) {
        }
        return amVar;
    }

    private static am a(Vector vector) {
        am amVar = null;
        try {
            amVar = (am) vector.lastElement();
        } catch (Exception unused) {
        }
        return amVar;
    }

    public final void c() {
        b(2);
        this.l = true;
    }

    public final am a(boolean z, am amVar, String str) throws NumberFormatException {
        this.j = true;
        am amVarA = null;
        if (z) {
            try {
                this.f71b = System.currentTimeMillis();
                this.f70a = this.f71b + this.f80f;
            } catch (Error e) {
            } catch (Exception e2) {
            }
        }
        String[] strArrA = b.a(str, this.b);
        for (int i = 0; i < strArrA.length; i++) {
            try {
                String[] strArrA2 = b.a(strArrA[i], this.f55a, 16);
                if (strArrA2.length > 1) {
                    int i2 = Integer.parseInt(strArrA2[1]);
                    int i3 = Integer.parseInt(strArrA2[0]);
                    if (amVarA == null || !(((amVarA.m17a() & 1) == 1 && i2 == 34) || ((amVarA.m17a() & 2) == 2 && i2 == 35))) {
                        if (strArrA2.length == 12) {
                            int i4 = i2 == 2 ? -1 : 0;
                            if (!z || (i2 != 33 && i2 != 31 && i3 != 85)) {
                                amVarA = a(i3, strArrA2[3], i2, Integer.parseInt(strArrA2[4]), strArrA2[10], Integer.parseInt(strArrA2[5]) == 1, strArrA2[7], Integer.parseInt(strArrA2[6]), strArrA2[9], Integer.parseInt(strArrA2[8]), strArrA2[11], new StringBuffer().append("").append(Integer.parseInt(strArrA2[2]) & 3).toString(), "", i4, 0, (Integer.parseInt(strArrA2[2]) & 8) == 8, (this.f58a << 2) / 5, (this.f59b << 2) / 5, (this.f58a - ((this.f58a << 2) / 5)) / 2, (this.f59b - ((this.f59b << 2) / 5)) / 2, true, 0, true, amVar);
                            }
                        } else if (!z || (i2 != 33 && i2 != 31 && i3 != 85 && i3 != 91)) {
                            amVarA = a(i3, strArrA2[7], i2, Integer.parseInt(strArrA2[8]), strArrA2[14], Integer.parseInt(strArrA2[9]) == 1, strArrA2[11], Integer.parseInt(strArrA2[10]), strArrA2[13], Integer.parseInt(strArrA2[12]), strArrA2[15], strArrA2[2], strArrA2[3], Integer.parseInt(strArrA2[4]), Integer.parseInt(strArrA2[5]), Integer.parseInt(strArrA2[6]) == 1, (this.f58a << 2) / 5, (this.f59b << 2) / 5, (this.f58a - ((this.f58a << 2) / 5)) / 2, (this.f59b - ((this.f59b << 2) / 5)) / 2, true, 0, true, amVar);
                        }
                    } else if ((amVarA.m17a() & 1) == 1 && i2 == 34) {
                        amVarA.a(strArrA[i]);
                    } else {
                        amVarA.b(strArrA[i]);
                    }
                    switch (i2) {
                        case 31:
                            this.f74b = strArrA[i];
                            break;
                        case 33:
                            this.f73a = strArrA[i];
                            break;
                    }
                    if (i3 == 85) {
                        this.f75c = strArrA[i];
                    } else if (i3 == 91) {
                        this.f76d = strArrA[i];
                    }
                }
            } catch (Exception e3) {
            }
        }
        if (z) {
            this.f70a = System.currentTimeMillis() + this.f80f;
        }
        this.j = false;
        if (z) {
            this.f52a = null;
        }
        if (this.l) {
            g();
        }
        return amVarA;
    }

    public final void a(long j, String str) throws InterruptedException, ClassNotFoundException, NumberFormatException, IOException {
        a(-24, str, 24, 0, "", false, "", 0, "", 0, new StringBuffer().append("").append(j).toString(), "1", "", 0, 0, false, 0, 0, 0, 0, true, 0, false, null);
    }

    public final void d() {
        b(1);
    }

    @Override // defpackage.o
    public final void a(am amVar, int i, boolean z) {
        if (this.f65a.d) {
            amVar.c(i);
            this.k = z;
        }
    }

    @Override // defpackage.o
    public final void a(am amVar) {
        this.f63a = amVar;
    }

    private void h() {
        am amVarA = a(this.f61a);
        if (amVarA != null) {
            amVarA.g = amVarA.f98f;
        }
    }

    public static void e() {
        a.f = true;
        a.f51a = true;
    }

    public static void f() {
        a.f51a = true;
    }

    @Override // defpackage.o
    public final void d(int i, int i2) {
        am amVar;
        int i3;
        am amVarB;
        try {
            int size = this.f61a.size();
            if (size <= 0 || (i3 = (amVar = (am) this.f61a.elementAt(size - 1)).b) == 31 || i3 == 34 || i3 == 35 || i3 == 24 || i3 == 11 || i3 == 14 || i3 == 26 || i3 == 27) {
                return;
            }
            amVar.c(false);
            this.f63a = amVar;
            boolean z = false;
            int i4 = size - 2;
            while (true) {
                if (i4 < 0) {
                    break;
                }
                am amVar2 = (am) this.f61a.elementAt(i4);
                if (amVar2.m14b(i, i2)) {
                    this.f61a.removeElementAt(i4);
                    this.f61a.addElement(amVar2);
                    z = true;
                    break;
                }
                i4--;
            }
            if (z || (amVarB = b(this.f62b, 30)) == null) {
                return;
            }
            amVarB.c(true);
            amVarB.a(i, i2);
        } catch (Exception unused) {
        }
    }

    private void i() {
        a.m3a();
        a.a(this.f60a.getHeight());
    }

    private void j() {
        am amVarB = b(this.f62b, 30);
        if (amVarB != null) {
            amVarB.c(true);
            amVarB.a(Opcodes.LMUL);
        }
    }
}
