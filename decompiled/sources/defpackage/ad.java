package defpackage;

import java.util.Enumeration;
import javax.microedition.lcdui.Graphics;
import javax.microedition.pim.Contact;
import javax.microedition.pim.ContactList;
import javax.microedition.pim.PIM;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:ad.class */
public class ad extends ag implements Runnable {
    private String[] a;
    private int E;
    private int F;
    private int G;
    private int H;
    private String g = "";
    private boolean j = true;
    private int I = 0;

    @Override // defpackage.ag, defpackage.am
    public final void a() {
        this.a = b.a(this.f101d, f119a);
        if (this.a.length > 1) {
            this.g = this.a[1];
        }
        this.I = Integer.parseInt(this.a[0]);
        this.E = this.C;
        this.F = this.D;
        this.G = this.z;
        this.H = this.A;
        String str = this.f90a;
        new Thread(this).start();
    }

    @Override // defpackage.ag, defpackage.am
    public final void a(Graphics graphics) {
        if (this.j) {
            super.a(graphics);
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        String str;
        String str2;
        String string;
        this.j = false;
        try {
            ContactList contactListOpenPIMList = PIM.getInstance().openPIMList(1, 1);
            Enumeration enumerationItems = contactListOpenPIMList.items();
            String string2 = this.I == 0 ? this.g : "";
            while (enumerationItems.hasMoreElements()) {
                Contact contact = (Contact) enumerationItems.nextElement();
                String string3 = "";
                if (contactListOpenPIMList.isSupportedField(Opcodes.LMUL) && (string = contact.getString(Opcodes.LMUL, 0)) != null) {
                    string3 = new StringBuffer().append(string3).append(string).toString();
                }
                String string4 = contactListOpenPIMList.isSupportedField(Opcodes.DREM) ? contact.getString(Opcodes.DREM, 0) : "";
                if (string3.length() < 3) {
                    if (contactListOpenPIMList.isSupportedArrayElement(Opcodes.FMUL, 1) && (str2 = contact.getStringArray(Opcodes.FMUL, 0)[1]) != null) {
                        string3 = new StringBuffer().append(string3).append(str2).toString();
                    }
                    if (contactListOpenPIMList.isSupportedArrayElement(Opcodes.FMUL, 0) && (str = contact.getStringArray(Opcodes.FMUL, 0)[0]) != null) {
                        string3 = new StringBuffer().append(string3).append("").append(str).toString();
                    }
                }
                string2 = this.I == 1 ? new StringBuffer().append(string2).append(f120b).append(b.m26a(string4, f121c)).append(f119a).append(b.m26a(string3, f121c)).append(f119a).append(b.m26a(string4, f121c)).append(f119a).toString() : new StringBuffer().append(string2).append(f122d).append(b.m26a(string4, f121c)).append(f119a).append(b.m26a(string3, f121c)).toString();
            }
            if (this.I != 1) {
                this.a.a(this.f89a, this.h, string2, null, this);
                this.a.a(this.f89a, 0, "", null, this);
                return;
            }
            this.C = this.E;
            this.D = this.F;
            this.z = this.G;
            this.A = this.H;
            this.f101d = "";
            if (string2 != null && string2.length() > 0) {
                this.f101d = string2.substring(1);
            }
            super.a();
            this.j = true;
            this.a.a(this.f89a, -200, null, null, this);
        } catch (Exception e) {
            this.a.a(this.f89a, -116, b.m26a(new StringBuffer().append("").append(e).append(". ").append(e.getMessage()).toString(), f121c), null, this);
        }
    }

    @Override // defpackage.ag, defpackage.am
    protected final void a(boolean z) {
        if (this.j && a(z)) {
            this.B = this.A;
        }
        int i = this.e;
        if (this.f93a != null && this.e <= this.f93a.b()) {
            this.e = this.f93a.b();
            this.A += this.e - i;
            this.D -= (this.e - i) / 2;
        }
        if (this.j) {
            super.a();
        }
    }

    @Override // defpackage.ag, defpackage.am
    /* renamed from: a */
    public final String mo6a() {
        return new StringBuffer().append(this.g).append(f122d).append(super.mo6a()).toString();
    }
}
