package javax.microedition.lcdui.game;

import java.util.Vector;
import javax.microedition.lcdui.Graphics;

/* loaded from: avacs.jar:javax/microedition/lcdui/game/LayerManager.class */
public class LayerManager {
    private Vector layers = new Vector();
    private int viewY = 0;
    private int viewX = 0;
    private int viewH = Integer.MAX_VALUE;
    private int viewW = Integer.MAX_VALUE;

    /* JADX WARN: Multi-variable type inference failed */
    public void append(Layer layer) {
        synchronized (this) {
            if (layer == 0) {
                throw new NullPointerException();
            }
            this.layers.add(layer);
        }
    }

    public Layer getLayerAt(int i) {
        return (Layer) this.layers.get(i);
    }

    public int getSize() {
        return this.layers.size();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void insert(Layer layer, int i) {
        synchronized (this) {
            if (layer == 0) {
                throw new NullPointerException();
            }
            this.layers.insertElementAt(layer, i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void remove(Layer layer) {
        synchronized (this) {
            if (layer == 0) {
                throw new NullPointerException();
            }
            this.layers.remove(layer);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setViewWindow(int x, int y, int i, int height) {
        synchronized (this) {
            if (i < 0 || height < 0) {
                throw new IllegalArgumentException();
            }
            this.viewX = x;
            this.viewY = y;
            this.viewW = i;
            this.viewH = height;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void paint(Graphics graphics, int x, int y) {
        synchronized (this) {
            if (graphics == 0) {
                throw new NullPointerException();
            }
            int clipX = graphics.getClipX();
            int clipY = graphics.getClipY();
            int clipW = graphics.getClipWidth();
            int clipH = graphics.getClipHeight();
            graphics.translate(x - this.viewX, y - this.viewY);
            graphics.clipRect(this.viewX, this.viewY, this.viewW, this.viewH);
            int i = getSize();
            while (true) {
                i--;
                if (i >= 0) {
                    Layer comp = getLayerAt(i);
                    if (comp.isVisible()) {
                        comp.paint(graphics);
                    }
                } else {
                    graphics.translate((-x) + this.viewX, (-y) + this.viewY);
                    graphics.setClip(clipX, clipY, clipW, clipH);
                }
            }
        }
    }
}
