package javax.microedition.lcdui;

/* loaded from: avacs.jar:javax/microedition/lcdui/AlertType.class */
public class AlertType {
    public static final AlertType INFO = new AlertType();
    public static final AlertType WARNING = new AlertType();
    public static final AlertType ERROR = new AlertType();
    public static final AlertType ALARM = new AlertType();
    public static final AlertType CONFIRMATION = new AlertType();

    protected AlertType() {
    }

    public boolean playSound(Display display) {
        return true;
    }
}
