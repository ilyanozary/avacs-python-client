package javax.microedition.lcdui;

/* loaded from: avacs.jar:javax/microedition/lcdui/Spacer.class */
public class Spacer extends Item {
    public Spacer(int minWidth, int minHeight) {
        super(null);
        setMinimumSize(minWidth, minHeight);
    }

    @Override // javax.microedition.lcdui.Item
    public void setLabel(String label) {
        throw new IllegalStateException("Spacer items can't have labels");
    }

    @Override // javax.microedition.lcdui.Item
    public void addCommand(Command cmd) {
        throw new IllegalStateException("Spacer items can't have commands");
    }

    @Override // javax.microedition.lcdui.Item
    public void setDefaultCommand(Command cmd) {
        throw new IllegalStateException("Spacer items can't have commands");
    }

    public void setMinimumSize(int minWidth, int minHeight) {
        if (minWidth < 0 || minHeight < 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override // javax.microedition.lcdui.Item
    int paint(Graphics g) {
        return 0;
    }
}
