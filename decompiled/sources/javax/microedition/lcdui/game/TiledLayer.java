package javax.microedition.lcdui.game;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/* loaded from: avacs.jar:javax/microedition/lcdui/game/TiledLayer.class */
public class TiledLayer extends Layer {
    private final int rows;
    private final int cols;
    Image img;
    private int tileHeight;
    private int tileWidth;
    private int numStaticTiles;
    private int[][] tiles;
    int[] animatedTiles;
    int numAnimatedTiles;

    public TiledLayer(int cols, int rows, Image img, int tileWidth, int tileHeight) {
        super(0, 0, cols * tileWidth, rows * tileHeight, true);
        if (img == null) {
            throw new NullPointerException();
        }
        if (cols <= 0 || rows <= 0 || tileHeight <= 0 || tileWidth <= 0) {
            throw new IllegalArgumentException();
        }
        if (img.getWidth() % tileWidth != 0 || img.getHeight() % tileHeight != 0) {
            throw new IllegalArgumentException();
        }
        this.img = img;
        this.cols = cols;
        this.rows = rows;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.numStaticTiles = (img.getWidth() / tileWidth) * (img.getHeight() / tileHeight);
        this.tiles = new int[rows][cols];
        this.animatedTiles = new int[5];
        this.numAnimatedTiles = 0;
    }

    public int createAnimatedTile(int i) {
        int i2;
        synchronized (this) {
            if (i >= 0) {
                if (i <= this.numStaticTiles) {
                    if (this.numAnimatedTiles == this.animatedTiles.length) {
                        int[] temp = new int[this.numAnimatedTiles + 6];
                        System.arraycopy(this.animatedTiles, 0, temp, 0, this.numAnimatedTiles);
                        this.animatedTiles = temp;
                    }
                    this.animatedTiles[this.numAnimatedTiles] = i;
                    this.numAnimatedTiles++;
                    i2 = -this.numAnimatedTiles;
                }
            }
            throw new IndexOutOfBoundsException();
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getAnimatedTile(int index) {
        int i;
        synchronized (this) {
            int index2 = (-index) - 1;
            if (index2 < 0 || index2 >= this.numAnimatedTiles) {
                throw new IndexOutOfBoundsException();
            }
            i = this.animatedTiles[index2];
        }
        return i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAnimatedTile(int index, int staticTileIndex) {
        synchronized (this) {
            int index2 = (-index) - 1;
            if (index2 < 0 || index2 >= this.numAnimatedTiles) {
                throw new IndexOutOfBoundsException();
            }
            if (staticTileIndex < 0 || staticTileIndex > this.numStaticTiles) {
                throw new IndexOutOfBoundsException();
            }
            this.animatedTiles[index2] = staticTileIndex;
        }
    }

    public int getCell(int col, int row) {
        return this.tiles[row][col];
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setCell(int col, int row, int index) {
        synchronized (this) {
            if ((-index) - 1 >= this.numAnimatedTiles || index > this.numStaticTiles) {
                throw new IndexOutOfBoundsException();
            }
            this.tiles[row][col] = index;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setStaticTileSet(Image image, int tileWidth, int tileHeight) {
        synchronized (this) {
            if (this.img == null) {
                throw new NullPointerException();
            }
            if (tileHeight <= 0 || tileWidth <= 0) {
                throw new IllegalArgumentException();
            }
            if (this.img.getWidth() % tileWidth != 0 || this.img.getHeight() % tileHeight != 0) {
                throw new IllegalArgumentException();
            }
            int newNumStaticTiles = (this.img.getWidth() / getCellWidth()) * (this.img.getHeight() / getCellHeight());
            int w = this.cols * tileWidth;
            int h = this.rows * tileHeight;
            setSize(w, h);
            this.img = this.img;
            this.tileWidth = tileWidth;
            this.tileHeight = tileHeight;
            if (newNumStaticTiles >= this.numStaticTiles) {
                this.numStaticTiles = newNumStaticTiles;
                return;
            }
            this.numStaticTiles = newNumStaticTiles;
            this.animatedTiles = new int[5];
            this.numAnimatedTiles = 0;
            fillCells(0, 0, getColumns(), getRows(), 0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void fillCells(int col, int row, int i, int numRows, int index) {
        synchronized (this) {
            if (i < 0 || numRows < 0) {
                throw new IllegalArgumentException();
            }
            if (row < 0 || col < 0 || col + i > this.cols || row + numRows > this.rows) {
                throw new IndexOutOfBoundsException();
            }
            if ((-index) - 1 >= this.numAnimatedTiles || index > this.numStaticTiles) {
                throw new IndexOutOfBoundsException();
            }
            int rMax = row + numRows;
            int cMax = col + i;
            for (int r = row; r < rMax; r++) {
                for (int c = col; c < cMax; c++) {
                    this.tiles[r][c] = index;
                }
            }
        }
    }

    public final int getColumns() {
        return this.cols;
    }

    public final int getRows() {
        return this.rows;
    }

    public final int getCellWidth() {
        return this.tileWidth;
    }

    public final int getCellHeight() {
        return this.tileHeight;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // javax.microedition.lcdui.game.Layer
    public final void paint(Graphics g) {
        synchronized (this) {
            if (isVisible()) {
                int x = getX();
                int y = getY();
                int cMax = getColumns();
                int rMax = getRows();
                int tW = getCellWidth();
                int tH = getCellHeight();
                g.getClipX();
                g.getClipY();
                g.getClipWidth();
                g.getClipHeight();
                int imgCols = this.img.getWidth() / tW;
                int height = this.img.getHeight() / tH;
                int r = 0;
                while (r < rMax) {
                    int x2 = x;
                    int c = 0;
                    while (c < cMax) {
                        int tile = getCell(c, r);
                        if (tile < 0) {
                            tile = getAnimatedTile(tile);
                        }
                        if (tile != 0) {
                            int tile2 = tile - 1;
                            int xSrc = tW * (tile2 % imgCols);
                            int ySrc = (tile2 / imgCols) * tH;
                            g.drawRegion(this.img, xSrc, ySrc, tW, tH, 0, x2, y, 20);
                        }
                        c++;
                        x2 += tW;
                    }
                    r++;
                    y += tH;
                }
            }
        }
    }
}
