package javax.microedition.lcdui;

/* loaded from: avacs.jar:javax/microedition/lcdui/ImageItem.class */
public class ImageItem extends Item {
    public static final int LAYOUT_DEFAULT = 0;
    public static final int LAYOUT_LEFT = 1;
    public static final int LAYOUT_RIGHT = 2;
    public static final int LAYOUT_CENTER = 3;
    public static final int LAYOUT_NEWLINE_BEFORE = 256;
    public static final int LAYOUT_NEWLINE_AFTER = 512;
    Image img;
    String altText;
    private int appearanceMode;

    public ImageItem(String label, Image img, int layout, String altText) {
        this(label, img, layout, altText, 0);
    }

    public ImageItem(String label, Image img, int layout, String altText, int appearanceMode) {
        super(label);
        setLayout(layout);
        if (appearanceMode != 0 && appearanceMode != 1 && appearanceMode != 2) {
            throw new IllegalArgumentException();
        }
        setImage(img);
        this.altText = altText;
        this.appearanceMode = appearanceMode;
    }

    public String getAltText() {
        return this.altText;
    }

    public int getAppearanceMode() {
        return this.appearanceMode;
    }

    public Image getImage() {
        return this.img;
    }

    @Override // javax.microedition.lcdui.Item
    public int getLayout() {
        return super.getLayout();
    }

    public void setAltText(String text) {
        this.altText = text;
    }

    public void setImage(Image img) {
        if (img != null && img.isMutable()) {
            img = Image.createImage(img);
        }
        this.img = img;
        repaint();
    }

    @Override // javax.microedition.lcdui.Item
    public void setLayout(int layout) {
        super.setLayout(layout);
    }

    @Override // javax.microedition.lcdui.Item
    int getHeight() {
        if (this.img == null) {
            return super.getHeight();
        }
        return super.getHeight() + this.img.getHeight();
    }

    @Override // javax.microedition.lcdui.Item
    int paint(Graphics g) {
        super.paintContent(g);
        if (this.img != null) {
            g.translate(0, super.getHeight());
            if (this.layout == 0 || this.layout == 1) {
                g.drawImage(this.img, 0, 0, 20);
            } else if (this.layout == 2) {
                g.drawImage(this.img, this.owner.getWidth(), 0, 24);
            } else if (this.layout == 3) {
                g.drawImage(this.img, this.owner.getWidth() / 2, 0, 17);
            } else {
                g.drawImage(this.img, 0, 0, 20);
            }
            g.translate(0, -super.getHeight());
        }
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
