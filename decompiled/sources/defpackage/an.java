package defpackage;

import java.util.Date;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.DateField;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.TextBox;

/* loaded from: avacs.jar:an.class */
public final class an extends am implements CommandListener {
    private TextBox a;

    /* renamed from: a, reason: collision with other field name */
    private Command f123a;
    private Command b;

    /* renamed from: a, reason: collision with other field name */
    private Displayable f124a;
    private String g;

    /* renamed from: a, reason: collision with other field name */
    private Form f125a;

    /* renamed from: a, reason: collision with other field name */
    private DateField f126a;
    private int E = 0;

    @Override // defpackage.am
    public final void a() {
        String[] strArrA = b.a(this.f101d, f119a);
        this.g = strArrA[2];
        this.f123a = new Command(this.f99b, 4, 1);
        this.b = new Command(this.f100c, 7, 1);
        this.f124a = this.f87a.getCurrent();
        switch (this.b) {
            case 12:
                this.a = new TextBox(this.f90a, b.b(strArrA[0], f121c), Integer.parseInt(strArrA[1]), 0);
                this.a.setCommandListener(this);
                if (this.h != 0) {
                    this.a.addCommand(this.f123a);
                }
                if (this.i != 0) {
                    this.a.addCommand(this.b);
                    break;
                }
                break;
            case 13:
                this.f126a = new DateField("", Integer.parseInt(strArrA[1]));
                this.f126a.setDate(new Date(Long.parseLong(strArrA[0])));
                this.f125a = new Form(this.f90a);
                this.f125a.append(this.f126a);
                this.f125a.setCommandListener(this);
                if (this.h != 0) {
                    this.f125a.addCommand(this.f123a);
                }
                if (this.i != 0) {
                    this.f125a.addCommand(this.b);
                    break;
                }
                break;
        }
    }

    public final void d(String str) {
        String strB = b.b(str, f121c);
        if (this.a.getString().length() + strB.length() <= this.a.getMaxSize()) {
            this.a.insert(strB, this.E);
        }
        this.r = 0;
    }

    @Override // defpackage.am
    public final void a(Graphics graphics) {
        if (this.r == 0) {
            switch (this.b) {
                case 12:
                    this.f87a.setCurrent(this.a);
                    break;
                case 13:
                    this.f87a.setCurrent(this.f125a);
                    break;
            }
        }
    }

    @Override // javax.microedition.lcdui.CommandListener
    public final void commandAction(Command command, Displayable displayable) {
        if (this.a != null) {
            this.E = this.a.getCaretPosition();
        }
        int i = this.h;
        if (command == this.f123a) {
            i = this.h;
        }
        if (command == this.b) {
            i = this.i;
        }
        switch (this.b) {
            case 12:
                this.a.a(this.f89a, i, new StringBuffer().append(b.m26a(this.a.getString(), f121c)).append(f119a).append(this.g).toString(), null, this);
                break;
            case 13:
                this.a.a(this.f89a, i, new StringBuffer().append("").append(this.f126a.getDate().getTime()).append(f119a).append(this.g).toString(), null, this);
                break;
        }
        this.f87a.setCurrent(this.f124a);
    }
}
