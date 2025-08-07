package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import org.microemu.GameCanvasKeyAccess;
import org.microemu.MIDletBridge;

/* loaded from: avacs.jar:javax/microedition/lcdui/game/GameCanvas.class */
public abstract class GameCanvas extends Canvas {
    public static final int UP_PRESSED = 2;
    public static final int DOWN_PRESSED = 64;
    public static final int LEFT_PRESSED = 4;
    public static final int RIGHT_PRESSED = 32;
    public static final int FIRE_PRESSED = 256;
    public static final int GAME_A_PRESSED = 512;
    public static final int GAME_B_PRESSED = 1024;
    public static final int GAME_C_PRESSED = 2048;
    public static final int GAME_D_PRESSED = 4096;
    private boolean suppressKeyEvents;
    private int latchedKeyState;
    private int actualKeyState;
    Image offscreenBuffer;

    /* loaded from: avacs.jar:javax/microedition/lcdui/game/GameCanvas$KeyAccess.class */
    private class KeyAccess implements GameCanvasKeyAccess {
        private KeyAccess() {
        }

        /* synthetic */ KeyAccess(GameCanvas gameCanvas, KeyAccess keyAccess) {
            this();
        }

        @Override // org.microemu.GameCanvasKeyAccess
        public boolean suppressedKeyEvents(GameCanvas canvas) {
            return canvas.suppressKeyEvents;
        }

        @Override // org.microemu.GameCanvasKeyAccess
        public void recordKeyPressed(GameCanvas canvas, int gameCode) {
            int bit = 1 << gameCode;
            GameCanvas.this.latchedKeyState |= bit;
            GameCanvas.this.actualKeyState |= bit;
        }

        @Override // org.microemu.GameCanvasKeyAccess
        public void recordKeyReleased(GameCanvas canvas, int gameCode) {
            int bit = 1 << gameCode;
            GameCanvas.this.actualKeyState &= bit ^ (-1);
        }

        @Override // org.microemu.GameCanvasKeyAccess
        public void setActualKeyState(GameCanvas canvas, int keyState) {
            GameCanvas.this.actualKeyState = keyState;
        }

        @Override // org.microemu.GameCanvasKeyAccess
        public void initBuffer() {
            GameCanvas.this.offscreenBuffer = Image.createImage(GameCanvas.this.getWidth(), GameCanvas.this.getHeight());
        }
    }

    protected GameCanvas(boolean suppressKeyEvents) {
        MIDletBridge.registerGameCanvasKeyAccess(this, new KeyAccess(this, null));
        this.suppressKeyEvents = suppressKeyEvents;
        this.offscreenBuffer = Image.createImage(getWidth(), getHeight());
    }

    protected Graphics getGraphics() {
        return this.offscreenBuffer.getGraphics();
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    public void paint(Graphics g) {
        g.drawImage(this.offscreenBuffer, 0, 0, 20);
    }

    public void flushGraphics(int x, int y, int width, int height) {
        repaint(x, y, width, height);
        serviceRepaints();
    }

    public void flushGraphics() {
        repaint();
        serviceRepaints();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5, types: [int] */
    public int getKeyStates() {
        ?? r0 = this;
        synchronized (r0) {
            int ret = this.latchedKeyState;
            this.latchedKeyState = this.actualKeyState;
            r0 = ret;
        }
        return r0;
    }
}
