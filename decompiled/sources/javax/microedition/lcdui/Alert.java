package javax.microedition.lcdui;

import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.AlertUI;

/* loaded from: avacs.jar:javax/microedition/lcdui/Alert.class */
public class Alert extends Screen {
    public static final int FOREVER = -2;
    ImageStringItem alertContent;
    AlertType type;
    int time;
    Gauge indicator;
    static Displayable nextDisplayable;
    public static final Command DISMISS_COMMAND = new Command("OK", 4, 0);
    static CommandListener defaultListener = new CommandListener() { // from class: javax.microedition.lcdui.Alert.1
        @Override // javax.microedition.lcdui.CommandListener
        public void commandAction(Command cmd, Displayable d) {
            ((Alert) d).currentDisplay.setCurrent(Alert.nextDisplayable);
        }
    };

    public Alert(String title) {
        this(title, null, null, null);
    }

    public Alert(String title, String alertText, Image alertImage, AlertType alertType) {
        super(title);
        super.setUI(DeviceFactory.getDevice().getUIFactory().createAlertUI(this));
        setTimeout(getDefaultTimeout());
        setString(alertText);
        setImage(alertImage);
        setType(alertType);
        super.addCommand(DISMISS_COMMAND);
        super.setCommandListener(defaultListener);
    }

    @Override // javax.microedition.lcdui.Displayable
    public void addCommand(Command cmd) {
        if (cmd == DISMISS_COMMAND) {
            return;
        }
        super.addCommand(cmd);
        super.removeCommand(DISMISS_COMMAND);
    }

    @Override // javax.microedition.lcdui.Displayable
    public void removeCommand(Command cmd) {
        if (cmd == DISMISS_COMMAND) {
            return;
        }
        super.removeCommand(cmd);
        if (getCommands().size() == 0) {
            super.addCommand(DISMISS_COMMAND);
        }
    }

    public int getDefaultTimeout() {
        return -2;
    }

    public String getString() {
        return this.alertContent.getText();
    }

    public int getTimeout() {
        return this.time;
    }

    public AlertType getType() {
        return this.type;
    }

    public void setType(AlertType type) {
        this.type = type;
        repaint();
    }

    @Override // javax.microedition.lcdui.Displayable
    public void setCommandListener(CommandListener l) {
        if (l == null) {
            l = defaultListener;
        }
        super.setCommandListener(l);
    }

    public Image getImage() {
        return this.alertContent.getImage();
    }

    public void setImage(Image img) {
        if (this.alertContent == null) {
            this.alertContent = new ImageStringItem(null, img, null);
        }
        if (img != null && img.isMutable()) {
            img = Image.createImage(img);
        }
        this.alertContent.setImage(img);
        repaint();
    }

    public Gauge getIndicator() {
        return this.indicator;
    }

    public void setIndicator(Gauge indicator) {
        if (indicator == null) {
            if (this.indicator != null) {
                this.indicator.setOwner(null);
            }
            this.indicator = null;
            repaint();
            return;
        }
        if (indicator.getLayout() != 0 || indicator.getLabel() != null || indicator.prefHeight != -1 || indicator.prefWidth != -1 || indicator.commandListener != null || indicator.isInteractive() || indicator.getOwner() != null || !indicator.commands.isEmpty()) {
            throw new IllegalArgumentException("This gauge cannot be added to an Alert");
        }
        indicator.setOwner(this);
        this.indicator = indicator;
        repaint();
    }

    public void setString(String str) {
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidAlertUI")) {
            ((AlertUI) this.ui).setString(str);
        }
        if (this.alertContent == null) {
            this.alertContent = new ImageStringItem(null, null, str);
        }
        this.alertContent.setText(str);
        repaint();
    }

    public void setTimeout(int time) {
        if (time != -2 && time <= 0) {
            throw new IllegalArgumentException();
        }
        if (time != -2 && getCommands().size() > 1) {
            time = -2;
        }
        this.time = time;
    }

    private int getContentHeight() {
        return this.alertContent.getHeight();
    }

    @Override // javax.microedition.lcdui.Screen
    int paintContent(Graphics g) {
        return this.alertContent.paint(g);
    }

    @Override // javax.microedition.lcdui.Screen, javax.microedition.lcdui.Displayable
    void showNotify() {
        super.showNotify();
        this.viewPortY = 0;
    }

    @Override // javax.microedition.lcdui.Screen
    int traverse(int gameKeyCode, int top, int bottom) {
        Font f = Font.getDefaultFont();
        if (gameKeyCode == 1 && top != 0) {
            return -f.getHeight();
        }
        if (gameKeyCode == 6 && bottom < getContentHeight()) {
            return f.getHeight();
        }
        return 0;
    }
}
