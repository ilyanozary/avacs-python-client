package javax.microedition.lcdui;

/* loaded from: avacs.jar:javax/microedition/lcdui/Screen.class */
public abstract class Screen extends Displayable {
    abstract int traverse(int i, int i2, int i3);

    abstract int paintContent(Graphics graphics);

    Screen(String title) {
        super(title);
    }

    void scroll(int gameKeyCode) {
        this.viewPortY += traverse(gameKeyCode, this.viewPortY, this.viewPortY + this.viewPortHeight);
        repaint();
    }

    @Override // javax.microedition.lcdui.Displayable
    void keyPressed(int keyCode) {
        int gameKeyCode = Display.getGameAction(keyCode);
        if (gameKeyCode == 1 || gameKeyCode == 6) {
            this.viewPortY += traverse(gameKeyCode, this.viewPortY, this.viewPortY + this.viewPortHeight);
            repaint();
        }
    }

    @Override // javax.microedition.lcdui.Displayable
    void hideNotify() {
        super.hideNotify();
    }

    @Override // javax.microedition.lcdui.Displayable
    void keyRepeated(int keyCode) {
        keyPressed(keyCode);
    }

    @Override // javax.microedition.lcdui.Displayable
    final void paint(Graphics g) {
        int contentHeight = 0;
        if (this.viewPortY == 0) {
            this.currentDisplay.setScrollUp(false);
        } else {
            this.currentDisplay.setScrollUp(true);
        }
        g.setGrayScale(255);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setGrayScale(0);
        if (getTicker() != null) {
            contentHeight = 0 + getTicker().paintContent(g);
        }
        g.translate(0, contentHeight);
        int translatedY = contentHeight;
        StringComponent title = new StringComponent(getTitle());
        int contentHeight2 = contentHeight + title.paint(g);
        g.drawLine(0, title.getHeight(), getWidth(), title.getHeight());
        int contentHeight3 = contentHeight2 + 1;
        g.translate(0, contentHeight3 - translatedY);
        g.setClip(0, 0, getWidth(), getHeight() - contentHeight3);
        g.translate(0, -this.viewPortY);
        int contentHeight4 = contentHeight3 + paintContent(g);
        g.translate(0, this.viewPortY);
        if (contentHeight4 - this.viewPortY > getHeight()) {
            this.currentDisplay.setScrollDown(true);
        } else {
            this.currentDisplay.setScrollDown(false);
        }
        g.translate(0, -contentHeight3);
    }

    @Override // javax.microedition.lcdui.Displayable
    void repaint() {
        super.repaint();
    }

    @Override // javax.microedition.lcdui.Displayable
    void showNotify() {
        this.viewPortY = 0;
        super.showNotify();
    }
}
