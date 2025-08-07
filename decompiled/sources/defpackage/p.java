package defpackage;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;

/* loaded from: avacs.jar:p.class */
public class p extends Canvas implements CommandListener, o {
    private o a;

    public p() {
        setCommandListener(this);
    }

    @Override // defpackage.o
    public final void a(o oVar) {
        this.a = oVar;
        o oVar2 = this.a;
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    public void paint(Graphics graphics) {
        if (this.a != null) {
            this.a.a(graphics);
        }
    }

    @Override // defpackage.o
    public final boolean a(Display display, int i, int i2) {
        return false;
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    public void keyPressed(int i) {
        if (this.a != null) {
            this.a.a(i);
        }
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    public void keyRepeated(int i) {
        if (this.a != null) {
            int gameAction = getGameAction(i);
            if (i == 50 || i == 52 || i == 54 || i == 56 || gameAction == 2 || gameAction == 5 || gameAction == 1 || gameAction == 6) {
                this.a.a(i);
            }
        }
    }

    @Override // javax.microedition.lcdui.CommandListener
    public void commandAction(Command command, Displayable displayable) {
        if (this.a != null) {
            o oVar = this.a;
        }
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    public void hideNotify() {
        if (this.a != null) {
            o oVar = this.a;
        }
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    public void showNotify() {
        if (this.a != null) {
            this.a.mo10a();
        }
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    public void pointerPressed(int i, int i2) {
        if (this.a != null) {
            this.a.a(i, i2);
        }
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    protected void pointerDragged(int i, int i2) {
        if (this.a != null) {
            this.a.b(i, i2);
        }
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    protected void pointerReleased(int i, int i2) {
        if (this.a != null) {
            this.a.c(i, i2);
        }
    }
}
