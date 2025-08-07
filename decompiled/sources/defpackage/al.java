package defpackage;

import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

/* loaded from: avacs.jar:al.class */
public class al extends am implements Runnable {
    private String[] a;

    @Override // defpackage.am
    public final void a() {
        this.a = b.a(this.f101d, f119a);
        this.f101d = b.b(this.a[0], f121c);
    }

    @Override // defpackage.am
    public final void a(Graphics graphics) {
        if (this.r == 0) {
            new Thread(this).start();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.a(this.f89a, 0, null, null, this);
        try {
            if (((MIDlet) this.f97a).platformRequest(this.f101d) || this.f101d.indexOf(".JAD") != -1 || this.f101d.indexOf(".JAR") != -1) {
                this.a.a(this.f89a, -500, null, null, this);
            }
            new Exception();
        } catch (Exception e) {
            this.a.a(this.f89a, -117, b.m26a(new StringBuffer().append("").append(e).append(". ").append(e.getMessage()).append(". ").append(b.b(this.a[1], f121c)).toString(), f121c), null, this);
        }
    }
}
