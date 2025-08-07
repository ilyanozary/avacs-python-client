package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/* loaded from: avacs.jar:javax/microedition/lcdui/game/Sprite.class */
public class Sprite extends Layer {
    public static final int TRANS_NONE = 0;
    public static final int TRANS_ROT90 = 5;
    public static final int TRANS_ROT180 = 3;
    public static final int TRANS_ROT270 = 6;
    public static final int TRANS_MIRROR = 2;
    public static final int TRANS_MIRROR_ROT90 = 7;
    public static final int TRANS_MIRROR_ROT180 = 1;
    public static final int TRANS_MIRROR_ROT270 = 4;
    private int frame;
    private int[] sequence;
    private int refX;
    private int refY;
    private int cols;
    private int rows;
    private int transform;
    private Image img;
    private int collX;
    private int collY;
    private int collWidth;
    private int collHeight;
    private int[] rgbData;
    private int[] rgbDataAux;

    public Sprite(Image img) {
        this(img, img.getWidth(), img.getHeight());
    }

    public Sprite(Image img, int frameWidth, int frameHeight) {
        super(0, 0, frameWidth, frameHeight, true);
        if (img.getWidth() % frameWidth != 0 || img.getHeight() % frameHeight != 0) {
            throw new IllegalArgumentException();
        }
        this.img = img;
        this.cols = img.getWidth() / frameWidth;
        this.rows = img.getHeight() / frameHeight;
        this.collY = 0;
        this.collX = 0;
        this.collWidth = frameWidth;
        this.collHeight = frameHeight;
    }

    public Sprite(Sprite otherSprite) {
        super(otherSprite.getX(), otherSprite.getY(), otherSprite.getWidth(), otherSprite.getHeight(), otherSprite.isVisible());
        this.frame = otherSprite.frame;
        this.sequence = otherSprite.sequence;
        this.refX = otherSprite.refX;
        this.refY = otherSprite.refY;
        this.cols = otherSprite.cols;
        this.rows = otherSprite.rows;
        this.transform = otherSprite.transform;
        this.img = otherSprite.img;
        this.collX = otherSprite.collX;
        this.collY = otherSprite.collY;
        this.collWidth = otherSprite.collWidth;
        this.collHeight = otherSprite.collHeight;
    }

    public final boolean collidesWith(Image image, int iX, int iY, boolean pixelLevel) {
        if (image == null) {
            throw new IllegalArgumentException();
        }
        if (!isVisible()) {
            return false;
        }
        if (pixelLevel) {
            return collidesWithPixelLevel(image, iX, iY);
        }
        return collidesWith(image, iX, iY);
    }

    public final boolean collidesWith(TiledLayer layer, boolean pixelLevel) {
        if (layer == null) {
            throw new NullPointerException();
        }
        if (!isVisible() || !layer.isVisible() || !isVisible()) {
            return false;
        }
        if (pixelLevel) {
            return collidesWithPixelLevel(layer, 0, 0);
        }
        return collidesWith(layer, 0, 0);
    }

    public final boolean collidesWith(Sprite otherSprite, boolean pixelLevel) {
        if (otherSprite == null) {
            throw new NullPointerException();
        }
        if (!otherSprite.isVisible() || !isVisible()) {
            return false;
        }
        if (pixelLevel) {
            return collidesWithPixelLevel(otherSprite, 0, 0);
        }
        return collidesWith(otherSprite, 0, 0);
    }

    public void defineReferencePixel(int x, int y) {
        this.refX = x;
        this.refY = y;
    }

    public int getRefPixelX() {
        return getX() + this.refX;
    }

    public int getRefPixelY() {
        return getY() + this.refY;
    }

    public void setRefPixelPosition(int x, int y) {
        int curRefX;
        int curRefY;
        int width = getWidth();
        int height = getHeight();
        switch (this.transform) {
            case 0:
                curRefX = this.refX;
                curRefY = this.refY;
                break;
            case 1:
                curRefX = width - this.refX;
                curRefY = height - this.refY;
                break;
            case 2:
                curRefX = width - this.refX;
                curRefY = this.refY;
                break;
            case 3:
                curRefX = this.refX;
                curRefY = height - this.refY;
                break;
            case 4:
                curRefX = height - this.refY;
                curRefY = this.refX;
                break;
            case 5:
                curRefX = height - this.refY;
                curRefY = width - this.refX;
                break;
            case 6:
                curRefX = this.refY;
                curRefY = this.refX;
                break;
            case 7:
                curRefX = this.refY;
                curRefY = width - this.refX;
                break;
            default:
                return;
        }
        setPosition(x - curRefX, y - curRefY);
    }

    public void defineCollisionRectangle(int x, int y, int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException();
        }
        this.collX = x;
        this.collY = y;
        this.collWidth = width;
        this.collHeight = height;
    }

    public void setFrameSequence(int[] sequence) {
        if (sequence == null) {
            this.sequence = null;
            return;
        }
        int max = (this.rows * this.cols) - 1;
        int l = sequence.length;
        if (l == 0) {
            throw new IllegalArgumentException();
        }
        for (int value : sequence) {
            if (value > max || value < 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
        }
        this.sequence = sequence;
        this.frame = 0;
    }

    public final int getFrame() {
        return this.frame;
    }

    public int getFrameSequenceLength() {
        return this.sequence == null ? this.rows * this.cols : this.sequence.length;
    }

    public void setFrame(int frame) {
        int l = this.sequence == null ? this.rows * this.cols : this.sequence.length;
        if (frame < 0 || frame >= l) {
            throw new IndexOutOfBoundsException();
        }
        this.frame = frame;
    }

    public void nextFrame() {
        if (this.frame == (this.sequence == null ? this.rows * this.cols : this.sequence.length) - 1) {
            this.frame = 0;
        } else {
            this.frame++;
        }
    }

    public void prevFrame() {
        if (this.frame == 0) {
            this.frame = (this.sequence == null ? this.rows * this.cols : this.sequence.length) - 1;
        } else {
            this.frame--;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setImage(Image img, int frameWidth, int frameHeight) {
        int dx;
        int dy;
        synchronized (this) {
            int oldW = getWidth();
            int oldH = getHeight();
            int newW = img.getWidth();
            int newH = img.getHeight();
            setSize(frameWidth, frameHeight);
            if (img.getWidth() % frameWidth != 0 || img.getHeight() % frameHeight != 0) {
                throw new IllegalArgumentException();
            }
            this.img = img;
            int oldFrames = this.cols * this.rows;
            this.cols = img.getWidth() / frameWidth;
            this.rows = img.getHeight() / frameHeight;
            if (this.rows * this.cols < oldFrames) {
                this.sequence = null;
                this.frame = 0;
            }
            if (frameWidth != getWidth() || frameHeight != getHeight()) {
                defineCollisionRectangle(0, 0, frameWidth, frameHeight);
                this.rgbDataAux = null;
                this.rgbData = null;
                if (this.transform != 0) {
                    switch (this.transform) {
                        case 1:
                            dx = newW - oldW;
                            dy = newH - oldH;
                            break;
                        case 2:
                            dx = newW - oldW;
                            dy = 0;
                            break;
                        case 3:
                            dx = 0;
                            dy = newH - oldH;
                            break;
                        case 4:
                            dx = newH - oldH;
                            dy = 0;
                            break;
                        case 5:
                            dx = newH - oldH;
                            dy = newW - oldW;
                            break;
                        case 6:
                            dx = 0;
                            dy = 0;
                            break;
                        case 7:
                            dx = 0;
                            dy = newW - oldW;
                            break;
                        default:
                            return;
                    }
                    move(dx, dy);
                }
            }
        }
    }

    @Override // javax.microedition.lcdui.game.Layer
    public final void paint(Graphics g) {
        if (!isVisible()) {
            return;
        }
        int f = this.sequence == null ? this.frame : this.sequence[this.frame];
        int w = getWidth();
        int h = getHeight();
        int fx = w * (f % this.cols);
        int fy = h * (f / this.cols);
        g.drawRegion(this.img, fx, fy, w, h, this.transform, getX(), getY(), 20);
    }

    public int getRawFrameCount() {
        return this.cols * this.rows;
    }

    public void setTransform(int transform) {
        int newRefX;
        int newRefY;
        int curRefX;
        int curRefY;
        if (this.transform == transform) {
            return;
        }
        int width = getWidth();
        int height = getHeight();
        int currentTransform = this.transform;
        switch (transform) {
            case 0:
                newRefX = this.refX;
                newRefY = this.refY;
                break;
            case 1:
                newRefX = width - this.refX;
                newRefY = height - this.refY;
                break;
            case 2:
                newRefX = width - this.refX;
                newRefY = this.refY;
                break;
            case 3:
                newRefX = this.refX;
                newRefY = height - this.refY;
                break;
            case 4:
                newRefX = height - this.refY;
                newRefY = this.refX;
                break;
            case 5:
                newRefX = height - this.refY;
                newRefY = width - this.refX;
                break;
            case 6:
                newRefX = this.refY;
                newRefY = this.refX;
                break;
            case 7:
                newRefX = this.refY;
                newRefY = width - this.refX;
                break;
            default:
                throw new IllegalArgumentException();
        }
        switch (currentTransform) {
            case 0:
                curRefX = this.refX;
                curRefY = this.refY;
                break;
            case 1:
                curRefX = width - this.refX;
                curRefY = height - this.refY;
                break;
            case 2:
                curRefX = width - this.refX;
                curRefY = this.refY;
                break;
            case 3:
                curRefX = this.refX;
                curRefY = height - this.refY;
                break;
            case 4:
                curRefX = height - this.refY;
                curRefY = this.refX;
                break;
            case 5:
                curRefX = height - this.refY;
                curRefY = width - this.refX;
                break;
            case 6:
                curRefX = this.refY;
                curRefY = this.refX;
                break;
            case 7:
                curRefX = this.refY;
                curRefY = width - this.refX;
                break;
            default:
                return;
        }
        move(curRefX - newRefX, curRefY - newRefY);
        this.transform = transform;
    }

    private synchronized boolean collidesWith(Object o, int oX, int oY) {
        int rX;
        int rW;
        int rY;
        int rH;
        int sX;
        int sY;
        int sW;
        int sH;
        int tX = 0;
        int tY = 0;
        int tW = 0;
        int tH = 0;
        int oW = 0;
        int oH = 0;
        Sprite t = this;
        boolean another = true;
        while (another) {
            int cX = t.collX;
            int cY = t.collY;
            int cW = t.collWidth;
            int cH = t.collHeight;
            if (cW == 0 || cH == 0) {
                return false;
            }
            switch (t.transform) {
                case 0:
                    sX = t.getX() + cX;
                    sY = t.getY() + cY;
                    sW = cW;
                    sH = cH;
                    break;
                case 1:
                    sX = t.getX() + cX;
                    sY = (t.getY() + ((t.getHeight() - cY) - 1)) - cH;
                    sW = cW;
                    sH = cH;
                    break;
                case 2:
                    sX = (t.getX() + ((t.getWidth() - cX) - 1)) - cW;
                    sY = t.getY() + cY;
                    sW = cW;
                    sH = cH;
                    break;
                case 3:
                    sX = (t.getX() + ((t.getWidth() - cX) - 1)) - cW;
                    sY = (t.getY() + ((t.getHeight() - cY) - 1)) - cH;
                    sW = cW;
                    sH = cH;
                    break;
                case 4:
                    sX = t.getX() + cY;
                    sY = t.getY() + cX;
                    sW = cH;
                    sH = cW;
                    break;
                case 5:
                    sX = (t.getX() + ((t.getHeight() - cY) - 1)) - cH;
                    sY = t.getY() + cX;
                    sW = cH;
                    sH = cW;
                    break;
                case 6:
                    sX = t.getX() + cY;
                    sY = (t.getY() + ((t.getWidth() - cX) - 1)) - cW;
                    sW = cH;
                    sH = cW;
                    break;
                case 7:
                    sX = (t.getX() + ((t.getHeight() - cY) - 1)) - cH;
                    sY = (t.getY() + ((t.getWidth() - cX) - 1)) - cW;
                    sW = cH;
                    sH = cW;
                    break;
                default:
                    return false;
            }
            if (o != t) {
                tX = sX;
                tY = sY;
                tW = sW;
                tH = sH;
                if (o instanceof Sprite) {
                    t = (Sprite) o;
                } else if (o instanceof TiledLayer) {
                    another = false;
                    TiledLayer layer = (TiledLayer) o;
                    oX = layer.getX();
                    oY = layer.getY();
                    oW = layer.getWidth();
                    oH = layer.getHeight();
                } else {
                    another = false;
                    Image img = (Image) o;
                    oW = img.getWidth();
                    oH = img.getHeight();
                }
            } else {
                another = false;
                oX = sX;
                oY = sY;
                oW = sW;
                oH = sH;
            }
        }
        if (tX > oX && tX >= oX + oW) {
            return false;
        }
        if (tX < oX && tX + tW <= oX) {
            return false;
        }
        if (tY > oY && tY >= oY + oH) {
            return false;
        }
        if (tY < oY && tY + tH <= oY) {
            return false;
        }
        if (o instanceof TiledLayer) {
            TiledLayer layer2 = (TiledLayer) o;
            if (oX > tX) {
                rX = oX;
                rW = (oX + oW < tX + tW ? oX + oW : tX + tW) - rX;
            } else {
                rX = tX;
                rW = (tX + tW < oX + oW ? tX + tW : oX + oW) - rX;
            }
            if (oY > tY) {
                rY = oY;
                rH = (oY + oH < tY + tH ? oY + oH : tY + tH) - rY;
            } else {
                rY = tY;
                rH = (tY + tH < oY + oH ? tY + tH : oY + oH) - rY;
            }
            Image image = layer2.img;
            int lW = layer2.getCellWidth();
            int lH = layer2.getCellHeight();
            int minC = (rX - oX) / lW;
            int minR = (rY - oY) / lH;
            int maxC = (((rX - oX) + rW) - 1) / lW;
            int maxR = (((rY - oY) + rH) - 1) / lH;
            for (int row = minR; row <= maxR; row++) {
                for (int col = minC; col <= maxC; col++) {
                    int cell = layer2.getCell(col, row);
                    if (cell < 0) {
                        cell = layer2.getAnimatedTile(cell);
                    }
                    if (cell != 0) {
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    private synchronized boolean collidesWithPixelLevel(Object o, int oX, int oY) {
        int rX;
        int rW;
        int rY;
        int rH;
        int sOffset;
        int sColIncr;
        int sRowIncr;
        int sX;
        int sY;
        int sW;
        int sH;
        boolean another = true;
        Sprite t = this;
        int tX = 0;
        int tY = 0;
        int tW = 0;
        int tH = 0;
        int oW = 0;
        int oH = 0;
        while (another) {
            if (t.collX >= t.getWidth() || t.collX + t.collWidth <= 0 || t.collY >= t.getHeight() || t.collY + t.collHeight <= 0) {
                return false;
            }
            int cX = t.collX >= 0 ? t.collX : 0;
            int cY = t.collY >= 0 ? t.collY : 0;
            int cW = t.collX + t.collWidth < t.getWidth() ? (t.collX + t.collWidth) - cX : t.getWidth() - cX;
            int cH = t.collY + t.collHeight < t.getHeight() ? (t.collY + t.collHeight) - cY : t.getHeight() - cY;
            switch (t.transform) {
                case 0:
                    sX = t.getX() + cX;
                    sY = t.getY() + cY;
                    sW = cW;
                    sH = cH;
                    break;
                case 1:
                    sX = t.getX() + cX;
                    sY = (t.getY() + ((t.getHeight() - cY) - 1)) - cH;
                    sW = cW;
                    sH = cH;
                    break;
                case 2:
                    sX = (t.getX() + ((t.getWidth() - cX) - 1)) - cW;
                    sY = t.getY() + cY;
                    sW = cW;
                    sH = cH;
                    break;
                case 3:
                    sX = (t.getX() + ((t.getWidth() - cX) - 1)) - cW;
                    sY = (t.getY() + ((t.getHeight() - cY) - 1)) - cH;
                    sW = cW;
                    sH = cH;
                    break;
                case 4:
                    sX = t.getX() + cY;
                    sY = t.getY() + cX;
                    sW = cH;
                    sH = cW;
                    break;
                case 5:
                    sX = (t.getX() + (t.getHeight() - cY)) - cH;
                    sY = t.getY() + cX;
                    sW = cH;
                    sH = cW;
                    break;
                case 6:
                    sX = t.getX() + cY;
                    sY = (t.getY() + (t.getWidth() - cX)) - cW;
                    sW = cH;
                    sH = cW;
                    break;
                case 7:
                    sX = (t.getX() + (t.getHeight() - cY)) - cH;
                    sY = (t.getY() + (t.getWidth() - cX)) - cW;
                    sW = cH;
                    sH = cW;
                    break;
                default:
                    return false;
            }
            if (o != t) {
                tX = sX;
                tY = sY;
                tW = sW;
                tH = sH;
                if (o instanceof Sprite) {
                    t = (Sprite) o;
                } else if (o instanceof TiledLayer) {
                    another = false;
                    TiledLayer layer = (TiledLayer) o;
                    oX = layer.getX();
                    oY = layer.getY();
                    oW = layer.getWidth();
                    oH = layer.getHeight();
                } else {
                    another = false;
                    Image img = (Image) o;
                    oW = img.getWidth();
                    oH = img.getHeight();
                }
            } else {
                another = false;
                oX = sX;
                oY = sY;
                oW = sW;
                oH = sH;
            }
        }
        if (tX > oX && tX >= oX + oW) {
            return false;
        }
        if (tX < oX && tX + tW <= oX) {
            return false;
        }
        if (tY > oY && tY >= oY + oH) {
            return false;
        }
        if (tY < oY && tY + tH <= oY) {
            return false;
        }
        if (oX > tX) {
            rX = oX;
            rW = (oX + oW < tX + tW ? oX + oW : tX + tW) - rX;
        } else {
            rX = tX;
            rW = (tX + tW < oX + oW ? tX + tW : oX + oW) - rX;
        }
        if (oY > tY) {
            rY = oY;
            rH = (oY + oH < tY + tH ? oY + oH : tY + tH) - rY;
        } else {
            rY = tY;
            rH = (tY + tH < oY + oH ? tY + tH : oY + oH) - rY;
        }
        int tColIncr = 0;
        int tRowIncr = 0;
        int tOffset = 0;
        int oColIncr = 0;
        int oRowIncr = 0;
        int oOffset = 0;
        int f = this.sequence == null ? this.frame : this.sequence[this.frame];
        int fW = getWidth();
        int fH = getHeight();
        int fX = fW * (f % this.rows);
        int fY = fH * (f / this.rows);
        if (this.rgbData == null) {
            this.rgbData = new int[fW * fH];
            this.rgbDataAux = new int[fW * fH];
        }
        Sprite t2 = this;
        boolean another2 = true;
        int[] tRgbData = this.rgbData;
        while (another2) {
            switch (t2.transform) {
                case 0:
                    t2.img.getRGB(tRgbData, 0, rW, (fX + rX) - t2.getX(), (fY + rY) - t2.getY(), rW, rH);
                    sOffset = 0;
                    sColIncr = 1;
                    sRowIncr = 0;
                    break;
                case 1:
                    t2.img.getRGB(tRgbData, 0, rW, (fX + rX) - t2.getX(), (((fY + fH) - (rY - t2.getY())) - rH) - 1, rW, rH);
                    sOffset = (rH - 1) * rW;
                    sColIncr = 1;
                    sRowIncr = -(rW << 1);
                    break;
                case 2:
                    t2.img.getRGB(tRgbData, 0, rW, (((fX + fW) - (rX - t2.getX())) - rW) - 1, (fY + rY) - t2.getY(), rW, rH);
                    sOffset = rW - 1;
                    sColIncr = -1;
                    sRowIncr = rW << 1;
                    break;
                case 3:
                    t2.img.getRGB(tRgbData, 0, rW, (((fX + fW) - (rX - t2.getX())) - rW) - 1, (((fY + fH) - (rY - t2.getY())) - rH) - 1, rW, rH);
                    sOffset = (rH * rW) - 1;
                    sColIncr = -1;
                    sRowIncr = 0;
                    break;
                case 4:
                    t2.img.getRGB(tRgbData, 0, rH, (fX + rY) - t2.getY(), (fY + rX) - t2.getX(), rH, rW);
                    sOffset = 0;
                    sColIncr = rH;
                    sRowIncr = (-(rH * rW)) + 1;
                    break;
                case 5:
                    t2.img.getRGB(tRgbData, 0, rH, (fX + rY) - t2.getY(), ((fY + fH) - (rX - t2.getX())) - rW, rH, rW);
                    sOffset = (rW - 1) * rH;
                    sColIncr = -rH;
                    sRowIncr = (rH * rW) + 1;
                    break;
                case 6:
                    t2.img.getRGB(tRgbData, 0, rH, ((fX + fW) - (rY - t2.getY())) - rH, (fY + rX) - t2.getX(), rH, rW);
                    sOffset = rH - 1;
                    sColIncr = rH;
                    sRowIncr = (-(rH * rW)) - 1;
                    break;
                case 7:
                    t2.img.getRGB(tRgbData, 0, rH, ((fX + fW) - (rY - t2.getY())) - rH, ((fY + fH) - (rX - t2.getX())) - rW, rH, rW);
                    sOffset = (rH * rW) - 1;
                    sColIncr = -rH;
                    sRowIncr = (rH * rW) - 1;
                    break;
                default:
                    return false;
            }
            if (o != t2) {
                tOffset = sOffset;
                tRowIncr = sRowIncr;
                tColIncr = sColIncr;
                if (o instanceof Sprite) {
                    t2 = (Sprite) o;
                    tRgbData = this.rgbDataAux;
                    int f2 = t2.sequence == null ? t2.frame : t2.sequence[t2.frame];
                    fW = t2.getWidth();
                    fH = t2.getHeight();
                    fX = fW * (f2 % t2.rows);
                    fY = fH * (f2 / t2.rows);
                } else if (o instanceof TiledLayer) {
                    another2 = false;
                    TiledLayer layer2 = (TiledLayer) o;
                    Image img2 = layer2.img;
                    oOffset = 0;
                    oColIncr = 1;
                    oRowIncr = 0;
                    int lW = layer2.getCellWidth();
                    int lH = layer2.getCellHeight();
                    int minC = (rX - oX) / lW;
                    int minR = (rY - oY) / lH;
                    int maxC = (((rX - oX) + rW) - 1) / lW;
                    int maxR = (((rY - oY) + rH) - 1) / lH;
                    int row = minR;
                    while (row <= maxR) {
                        int col = minC;
                        while (col <= maxC) {
                            int cell = layer2.getCell(col, row);
                            if (cell < 0) {
                                cell = layer2.getAnimatedTile(cell);
                            }
                            int minX = col == minC ? (rX - oX) % lW : 0;
                            int minY = row == minR ? (rY - oY) % lH : 0;
                            int maxX = col == maxC ? (((rX + rW) - oX) - 1) % lW : lW - 1;
                            int maxY = row == maxR ? (((rY + rH) - oY) - 1) % lH : lH - 1;
                            int c = (((((row - minR) * lH) * rW) + ((col - minC) * lW)) - (col == minC ? 0 : (rX - oX) % lW)) - ((row == minR ? 0 : (rY - oY) % lH) * rW);
                            if (cell == 0) {
                                int y = minY;
                                while (y <= maxY) {
                                    int x = minX;
                                    while (x <= maxX) {
                                        this.rgbDataAux[c] = 0;
                                        x++;
                                        c++;
                                    }
                                    y++;
                                    c += rW - ((maxX - minX) + 1);
                                }
                            } else {
                                int cell2 = cell - 1;
                                int imgCols = img2.getWidth() / layer2.getCellWidth();
                                int xSrc = lW * (cell2 % imgCols);
                                int ySrc = (cell2 / imgCols) * lH;
                                img2.getRGB(this.rgbDataAux, c, rW, xSrc + minX, ySrc + minY, (maxX - minX) + 1, (maxY - minY) + 1);
                            }
                            col++;
                        }
                        row++;
                    }
                } else {
                    another2 = false;
                    ((Image) o).getRGB(this.rgbDataAux, 0, rW, rX - oX, rY - oY, rW, rH);
                    oOffset = 0;
                    oColIncr = 1;
                    oRowIncr = 0;
                }
            } else {
                another2 = false;
                oOffset = sOffset;
                oRowIncr = sRowIncr;
                oColIncr = sColIncr;
            }
        }
        int row2 = 0;
        while (row2 < rH) {
            int col2 = 0;
            while (col2 < rW) {
                int rgb = this.rgbData[tOffset];
                int rgbA = this.rgbDataAux[oOffset];
                if (((rgb & rgbA) >> 24) != -1) {
                    col2++;
                    tOffset += tColIncr;
                    oOffset += oColIncr;
                } else {
                    return true;
                }
            }
            row2++;
            tOffset += tRowIncr;
            oOffset += oRowIncr;
        }
        return false;
    }
}
