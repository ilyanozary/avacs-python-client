package javax.microedition.lcdui;

/* loaded from: avacs.jar:javax/microedition/lcdui/ImageStringItem.class */
class ImageStringItem extends Item {
    Image img;
    StringComponent stringComponent;

    public ImageStringItem(String label, Image img, String text) {
        super(label);
        this.stringComponent = new StringComponent(text);
        setImage(img);
    }

    public Image getImage() {
        return this.img;
    }

    public void setImage(Image img) {
        this.img = img;
        if (this.img != null) {
            this.stringComponent.setWidthDecreaser(img.getWidth() + 2);
        }
    }

    public String getText() {
        return this.stringComponent.getText();
    }

    public void setText(String text) {
        this.stringComponent.setText(text);
    }

    @Override // javax.microedition.lcdui.Item
    int getHeight() {
        if (this.img != null && this.img.getHeight() > this.stringComponent.getHeight()) {
            return this.img.getHeight();
        }
        return this.stringComponent.getHeight();
    }

    void invertPaint(boolean state) {
        this.stringComponent.invertPaint(state);
    }

    @Override // javax.microedition.lcdui.Item
    int paint(Graphics g) {
        if (this.stringComponent == null) {
            return 0;
        }
        if (this.img != null) {
            g.drawImage(this.img, 0, 0, 20);
            g.translate(this.img.getWidth() + 2, 0);
        }
        int y = this.stringComponent.paint(g);
        if (this.img != null) {
            g.translate((-this.img.getWidth()) - 2, 0);
        }
        return y;
    }
}
