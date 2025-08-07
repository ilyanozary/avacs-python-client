package javax.microedition.lcdui;

import javax.microedition.lcdui.game.GameCanvas;
import org.microemu.GameCanvasKeyAccess;
import org.microemu.MIDletBridge;
import org.microemu.device.DeviceFactory;

/* loaded from: avacs.jar:javax/microedition/lcdui/Canvas.class */
public abstract class Canvas extends Displayable {
    public static final int UP = 1;
    public static final int DOWN = 6;
    public static final int LEFT = 2;
    public static final int RIGHT = 5;
    public static final int FIRE = 8;
    public static final int GAME_A = 9;
    public static final int GAME_B = 10;
    public static final int GAME_C = 11;
    public static final int GAME_D = 12;
    public static final int KEY_NUM0 = 48;
    public static final int KEY_NUM1 = 49;
    public static final int KEY_NUM2 = 50;
    public static final int KEY_NUM3 = 51;
    public static final int KEY_NUM4 = 52;
    public static final int KEY_NUM5 = 53;
    public static final int KEY_NUM6 = 54;
    public static final int KEY_NUM7 = 55;
    public static final int KEY_NUM8 = 56;
    public static final int KEY_NUM9 = 57;
    public static final int KEY_STAR = 42;
    public static final int KEY_POUND = 35;

    @Override // javax.microedition.lcdui.Displayable
    protected abstract void paint(Graphics graphics);

    protected Canvas() {
        super(null);
        super.setUI(DeviceFactory.getDevice().getUIFactory().createCanvasUI(this));
    }

    public int getGameAction(int keyCode) {
        return Display.getGameAction(keyCode);
    }

    public int getKeyCode(int gameAction) {
        return Display.getKeyCode(gameAction);
    }

    public String getKeyName(int keyCode) throws IllegalArgumentException {
        return Display.getKeyName(keyCode);
    }

    public boolean hasPointerEvents() {
        return this.device.hasPointerEvents();
    }

    public boolean hasPointerMotionEvents() {
        return this.device.hasPointerMotionEvents();
    }

    public boolean hasRepeatEvents() {
        return this.device.hasRepeatEvents();
    }

    @Override // javax.microedition.lcdui.Displayable
    protected void hideNotify() {
    }

    public boolean isDoubleBuffered() {
        return true;
    }

    @Override // javax.microedition.lcdui.Displayable
    protected void keyPressed(int keyCode) {
    }

    @Override // javax.microedition.lcdui.Displayable
    protected void keyRepeated(int keyCode) {
    }

    @Override // javax.microedition.lcdui.Displayable
    protected void keyReleased(int keyCode) {
    }

    @Override // javax.microedition.lcdui.Displayable
    protected void pointerPressed(int x, int y) {
    }

    @Override // javax.microedition.lcdui.Displayable
    protected void pointerReleased(int x, int y) {
    }

    @Override // javax.microedition.lcdui.Displayable
    protected void pointerDragged(int x, int y) {
    }

    @Override // javax.microedition.lcdui.Displayable
    public final void repaint() {
        super.repaint();
    }

    @Override // javax.microedition.lcdui.Displayable
    public final void repaint(int x, int y, int width, int height) {
        super.repaint(x, y, width, height);
    }

    public final void serviceRepaints() {
        if (this.currentDisplay != null) {
            this.currentDisplay.serviceRepaints();
        }
    }

    public void setFullScreenMode(boolean mode) {
        if (this.fullScreenMode != mode) {
            this.fullScreenMode = mode;
            if (this instanceof GameCanvas) {
                this.width = -1;
                this.height = -1;
                GameCanvasKeyAccess access = MIDletBridge.getGameCanvasKeyAccess((GameCanvas) this);
                access.initBuffer();
            }
            if (this.currentDisplay != null) {
                sizeChanged(this.currentDisplay);
            }
        }
    }

    @Override // javax.microedition.lcdui.Displayable
    protected void sizeChanged(int w, int h) {
    }

    @Override // javax.microedition.lcdui.Displayable
    protected void showNotify() {
    }
}
