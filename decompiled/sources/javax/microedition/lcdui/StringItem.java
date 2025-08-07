package javax.microedition.lcdui;

/* loaded from: avacs.jar:javax/microedition/lcdui/StringItem.class */
public class StringItem extends Item {
    StringComponent stringComponent;

    public StringItem(String label, String text) {
        this(label, text, 0);
    }

    public StringItem(String label, String text, int appearanceMode) {
        super(label);
        this.stringComponent = new StringComponent(text);
    }

    public int getAppearanceMode() {
        return 0;
    }

    public Font getFont() {
        return Font.getDefaultFont();
    }

    public void setFont(Font font) {
    }

    @Override // javax.microedition.lcdui.Item
    public void setPreferredSize(int width, int height) {
    }

    public String getText() {
        return this.stringComponent.getText();
    }

    public void setText(String text) {
        this.stringComponent.setText(text);
        repaint();
    }

    @Override // javax.microedition.lcdui.Item
    int getHeight() {
        return super.getHeight() + this.stringComponent.getHeight();
    }

    @Override // javax.microedition.lcdui.Item
    int paint(Graphics g) {
        super.paintContent(g);
        g.translate(0, super.getHeight());
        this.stringComponent.paint(g);
        g.translate(0, -super.getHeight());
        return getHeight();
    }

    @Override // javax.microedition.lcdui.Item
    int traverse(int gameKeyCode, int top, int bottom, boolean action) {
        Font f = Font.getDefaultFont();
        if (gameKeyCode == 1) {
            if (top > 0) {
                if (top % f.getHeight() == 0) {
                    return -f.getHeight();
                }
                return -(top % f.getHeight());
            }
            return Integer.MAX_VALUE;
        }
        if (gameKeyCode == 6) {
            if (bottom < getHeight()) {
                if (getHeight() - bottom < f.getHeight()) {
                    return getHeight() - bottom;
                }
                return f.getHeight();
            }
            return Integer.MAX_VALUE;
        }
        return 0;
    }
}
