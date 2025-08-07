package defpackage;

import com.nokia.mid.ui.DeviceControl;
import com.nokia.mid.ui.FullCanvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;

/* loaded from: avacs.jar:r.class */
public class r extends FullCanvas implements o {
    private o a;

    @Override // defpackage.o
    public final void a(o oVar) {
        this.a = oVar;
    }

    @Override // defpackage.o
    public final boolean a(Display display, int i, int i2) {
        try {
            DeviceControl.startVibra(i, i2);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public void paint(Graphics graphics) {
        if (this.a != null) {
            this.a.a(graphics);
        }
    }

    public void keyPressed(int i) {
        if (this.a != null) {
            this.a.a(i);
        }
    }

    public void keyRepeated(int i) {
        if (this.a != null) {
            int gameAction = getGameAction(i);
            if (i == 50 || i == 52 || i == 54 || i == 56 || gameAction == 2 || gameAction == 5 || gameAction == 1 || gameAction == 6) {
                this.a.a(i);
            }
        }
    }

    public void hideNotify() {
        if (this.a != null) {
            o oVar = this.a;
        }
    }

    public void showNotify() {
        if (this.a != null) {
            this.a.mo10a();
        }
    }

    public void pointerPressed(int i, int i2) {
        if (this.a != null) {
            this.a.a(i, i2);
        }
    }

    protected void sizeChanged(int i, int i2) {
        if (this.a != null) {
            this.a.b();
        }
    }

    protected void pointerDragged(int i, int i2) {
        if (this.a != null) {
            this.a.b(i, i2);
        }
    }

    protected void pointerReleased(int i, int i2) {
        if (this.a != null) {
            this.a.c(i, i2);
        }
    }
}
