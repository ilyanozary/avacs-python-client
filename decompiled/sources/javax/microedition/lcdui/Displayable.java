package javax.microedition.lcdui;

import java.util.Vector;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.CommandUI;
import org.microemu.device.ui.DisplayableUI;

/* loaded from: avacs.jar:javax/microedition/lcdui/Displayable.class */
public abstract class Displayable {
    Ticker ticker;
    int viewPortY;
    int viewPortHeight;
    DisplayableUI ui;
    private String title;
    Display currentDisplay = null;
    private CommandListener listener = null;
    Device device = DeviceFactory.getDevice();
    int width = -1;
    int height = -1;
    boolean fullScreenMode = false;

    abstract void paint(Graphics graphics);

    Displayable(String title) {
        this.title = title;
    }

    void setUI(DisplayableUI ui) {
        this.ui = ui;
    }

    public void addCommand(Command cmd) {
        this.ui.addCommandUI(cmd.ui);
    }

    public void removeCommand(Command cmd) {
        this.ui.removeCommandUI(cmd.ui);
    }

    public int getWidth() {
        if (this.width == -1) {
            if (this.fullScreenMode) {
                this.width = this.device.getDeviceDisplay().getFullWidth();
            } else {
                this.width = this.device.getDeviceDisplay().getWidth();
            }
        }
        return this.width;
    }

    public int getHeight() {
        if (this.height == -1) {
            if (this.fullScreenMode) {
                this.height = this.device.getDeviceDisplay().getFullHeight();
            } else {
                this.height = this.device.getDeviceDisplay().getHeight();
            }
        }
        return this.height;
    }

    public boolean isShown() {
        if (this.currentDisplay == null) {
            return false;
        }
        return this.currentDisplay.isShown(this);
    }

    public Ticker getTicker() {
        return this.ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
        repaint();
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String s) {
        this.title = s;
        this.ui.invalidate();
    }

    public void setCommandListener(CommandListener l) {
        this.listener = l;
        this.ui.setCommandListener(l);
    }

    CommandListener getCommandListener() {
        return this.listener;
    }

    Vector getCommands() {
        Vector result = new Vector();
        Vector commandsUI = this.ui.getCommandsUI();
        for (int i = 0; i < commandsUI.size(); i++) {
            result.addElement(((CommandUI) commandsUI.elementAt(i)).getCommand());
        }
        return result;
    }

    void hideNotify() {
    }

    final void hideNotify(Display d) {
        this.ui.hideNotify();
        hideNotify();
    }

    void keyPressed(int keyCode) {
    }

    void keyRepeated(int keyCode) {
    }

    void keyReleased(int keyCode) {
    }

    void pointerPressed(int x, int y) {
    }

    void pointerReleased(int x, int y) {
    }

    void pointerDragged(int x, int y) {
    }

    void repaint() {
        if (this.currentDisplay != null) {
            repaint(0, 0, getWidth(), getHeight());
        }
    }

    void repaint(int x, int y, int width, int height) {
        if (this.currentDisplay != null) {
            this.currentDisplay.repaint(this, x, y, width, height);
        }
    }

    protected void sizeChanged(int w, int h) {
    }

    final void sizeChanged(Display d) {
        this.width = -1;
        this.height = -1;
        this.fullScreenMode = true;
        sizeChanged(getWidth(), getHeight());
    }

    void showNotify() {
    }

    final void showNotify(Display d) {
        int w;
        int h;
        this.currentDisplay = d;
        this.viewPortY = 0;
        StringComponent title = new StringComponent(getTitle());
        this.viewPortHeight = (getHeight() - title.getHeight()) - 1;
        if (this.ticker != null) {
            this.viewPortHeight -= this.ticker.getHeight();
        }
        if (this.fullScreenMode) {
            w = this.device.getDeviceDisplay().getFullWidth();
        } else {
            w = this.device.getDeviceDisplay().getWidth();
        }
        if (this.fullScreenMode) {
            h = this.device.getDeviceDisplay().getFullHeight();
        } else {
            h = this.device.getDeviceDisplay().getHeight();
        }
        if (this.width != w || this.height != h) {
            sizeChanged(d);
        }
        showNotify();
        this.ui.showNotify();
    }
}
