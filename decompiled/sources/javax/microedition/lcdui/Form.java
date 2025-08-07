package javax.microedition.lcdui;

import java.util.Vector;
import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.FormUI;

/* loaded from: avacs.jar:javax/microedition/lcdui/Form.class */
public class Form extends Screen {
    Item[] items;
    int numOfItems;
    int focusItemIndex;
    ItemStateListener itemStateListener;

    public Form(String title) {
        super(title);
        this.items = new Item[4];
        this.numOfItems = 0;
        this.itemStateListener = null;
        super.setUI(DeviceFactory.getDevice().getUIFactory().createFormUI(this));
        this.focusItemIndex = -2;
    }

    public Form(String title, Item[] items) {
        this(title);
        if (items != null) {
            this.items = new Item[items.length];
            System.arraycopy(items, 0, this.items, 0, items.length);
            this.numOfItems = this.items.length;
            for (int i = 0; i < this.numOfItems; i++) {
                verifyItem(this.items[i]);
            }
        }
    }

    public int append(Item item) {
        verifyItem(item);
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
            ((FormUI) this.ui).append(item);
        }
        if (this.numOfItems + 1 >= this.items.length) {
            Item[] newitems = new Item[this.numOfItems + 4];
            System.arraycopy(this.items, 0, newitems, 0, this.numOfItems);
            this.items = newitems;
        }
        this.items[this.numOfItems] = item;
        this.numOfItems++;
        repaint();
        return this.numOfItems - 1;
    }

    public int append(Image img) {
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
            ((FormUI) this.ui).append(new ImageItem(null, img, 0, null));
        }
        return append(new ImageItem(null, img, 0, null));
    }

    public int append(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
            ((FormUI) this.ui).append(new StringItem(null, str));
        }
        return append(new StringItem(null, str));
    }

    public void delete(int itemNum) {
        verifyItemNum(itemNum);
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
            ((FormUI) this.ui).delete(itemNum);
        }
        this.items[itemNum].setOwner(null);
        System.arraycopy(this.items, itemNum + 1, this.items, itemNum, (this.numOfItems - itemNum) - 1);
        this.numOfItems--;
        repaint();
    }

    public void deleteAll() {
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
            ((FormUI) this.ui).deleteAll();
        }
        for (int i = 0; i < this.numOfItems; i++) {
            this.items[i].setOwner(null);
        }
        this.numOfItems = 0;
        repaint();
    }

    public Item get(int itemNum) {
        verifyItemNum(itemNum);
        return this.items[itemNum];
    }

    @Override // javax.microedition.lcdui.Displayable
    public int getHeight() {
        return super.getHeight();
    }

    @Override // javax.microedition.lcdui.Displayable
    public int getWidth() {
        return super.getWidth();
    }

    public void insert(int itemNum, Item item) {
        if (itemNum != this.numOfItems) {
            verifyItemNum(itemNum);
        }
        verifyItem(item);
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
            ((FormUI) this.ui).insert(itemNum, item);
        }
        if (this.numOfItems + 1 == this.items.length) {
            Item[] newitems = new Item[this.numOfItems + 4];
            System.arraycopy(this.items, 0, newitems, 0, this.numOfItems);
            this.items = newitems;
        }
        System.arraycopy(this.items, itemNum, this.items, itemNum + 1, this.numOfItems - itemNum);
        this.items[itemNum] = item;
        this.items[itemNum].setOwner(this);
        this.numOfItems++;
        repaint();
    }

    public void set(int itemNum, Item item) {
        verifyItemNum(itemNum);
        verifyItem(item);
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidFormUI")) {
            ((FormUI) this.ui).set(itemNum, item);
        }
        this.items[itemNum].setOwner(null);
        this.items[itemNum] = item;
        this.items[itemNum].setOwner(this);
        repaint();
    }

    public void setItemStateListener(ItemStateListener iListener) {
        this.itemStateListener = iListener;
    }

    public int size() {
        return this.numOfItems;
    }

    @Override // javax.microedition.lcdui.Screen
    int paintContent(Graphics g) {
        int contentHeight = 0;
        for (int i = 0; i < this.numOfItems; i++) {
            int translateY = this.items[i].paint(g);
            g.translate(0, translateY);
            contentHeight += translateY;
        }
        g.translate(0, -contentHeight);
        return contentHeight;
    }

    void fireItemStateListener(Item item) {
        if (this.itemStateListener != null) {
            this.itemStateListener.itemStateChanged(item);
        }
    }

    void fireItemStateListener() {
        if (this.focusItemIndex >= 0 && this.focusItemIndex < this.items.length) {
            fireItemStateListener(this.items[this.focusItemIndex]);
        }
    }

    @Override // javax.microedition.lcdui.Screen, javax.microedition.lcdui.Displayable
    void hideNotify() {
        super.hideNotify();
        for (int i = 0; i < this.numOfItems; i++) {
            if (this.items[i].isFocusable() && this.items[i].hasFocus()) {
                this.items[i].setFocus(false);
                this.focusItemIndex = -2;
                return;
            }
        }
    }

    @Override // javax.microedition.lcdui.Screen, javax.microedition.lcdui.Displayable
    void keyPressed(int keyCode) {
        if (this.focusItemIndex != -1) {
            if (Display.getGameAction(keyCode) == 8) {
                this.items[this.focusItemIndex].select();
                fireItemStateListener();
            } else {
                this.items[this.focusItemIndex].keyPressed(keyCode);
            }
        }
        super.keyPressed(keyCode);
    }

    @Override // javax.microedition.lcdui.Screen, javax.microedition.lcdui.Displayable
    void showNotify() {
        super.showNotify();
        if (this.focusItemIndex == -2) {
            this.focusItemIndex = -1;
            int i = 0;
            while (true) {
                if (i >= this.numOfItems) {
                    break;
                }
                if (!this.items[i].isFocusable()) {
                    i++;
                } else {
                    this.items[i].setFocus(true);
                    this.focusItemIndex = i;
                    break;
                }
            }
        }
        if (this.focusItemIndex < 0) {
            return;
        }
        int heightToItem = getHeightToItem(this.focusItemIndex);
        int heightAfterItem = heightToItem + this.items[this.focusItemIndex].getHeight();
        if (this.viewPortY > heightToItem) {
            this.viewPortY = heightToItem;
        } else if (this.viewPortY + this.viewPortHeight < heightAfterItem) {
            this.viewPortY = heightAfterItem - this.viewPortHeight;
        }
    }

    @Override // javax.microedition.lcdui.Screen
    int traverse(int gameKeyCode, int top, int bottom) {
        int testItemIndex;
        int traverse;
        int testItemIndex2;
        int traverse2;
        if (this.numOfItems == 0) {
            return 0;
        }
        if (gameKeyCode == 1) {
            int topItemIndex = getTopVisibleIndex(top);
            if (this.focusItemIndex == -1) {
                testItemIndex2 = topItemIndex;
                int height = getHeightToItem(testItemIndex2);
                traverse2 = this.items[testItemIndex2].traverse(gameKeyCode, top - height, bottom - height, false);
            } else {
                testItemIndex2 = this.focusItemIndex;
                int height2 = getHeightToItem(testItemIndex2);
                traverse2 = this.items[testItemIndex2].traverse(gameKeyCode, top - height2, bottom - height2, true);
            }
            if (traverse2 != Integer.MAX_VALUE) {
                if (this.focusItemIndex == -1 && this.items[testItemIndex2].isFocusable()) {
                    this.items[testItemIndex2].setFocus(true);
                    this.focusItemIndex = testItemIndex2;
                }
                return traverse2;
            }
            if (testItemIndex2 > 0) {
                for (int i = testItemIndex2 - 1; i >= topItemIndex; i--) {
                    if (this.items[i].isFocusable()) {
                        if (this.focusItemIndex != -1) {
                            this.items[this.focusItemIndex].setFocus(false);
                        }
                        this.items[i].setFocus(true);
                        this.focusItemIndex = i;
                        int height3 = getHeightToItem(i);
                        int traverse3 = this.items[i].traverse(gameKeyCode, top - height3, bottom - height3, false);
                        if (traverse3 == Integer.MAX_VALUE) {
                            return 0;
                        }
                        return traverse3;
                    }
                }
                int height4 = getHeightToItem(topItemIndex);
                int traverse4 = this.items[topItemIndex].traverse(gameKeyCode, top - height4, bottom - height4, false);
                if (traverse4 != Integer.MAX_VALUE) {
                    int bottomItemIndex = getTopVisibleIndex(bottom + traverse4);
                    if (this.focusItemIndex != -1 && this.focusItemIndex > bottomItemIndex) {
                        this.items[this.focusItemIndex].setFocus(false);
                        this.focusItemIndex = -1;
                    }
                    return traverse4;
                }
            }
        }
        if (gameKeyCode == 6) {
            int bottomItemIndex2 = getBottomVisibleIndex(bottom);
            if (this.focusItemIndex == -1) {
                testItemIndex = bottomItemIndex2;
                int height5 = getHeightToItem(testItemIndex);
                traverse = this.items[testItemIndex].traverse(gameKeyCode, top - height5, bottom - height5, false);
            } else {
                testItemIndex = this.focusItemIndex;
                int height6 = getHeightToItem(testItemIndex);
                traverse = this.items[testItemIndex].traverse(gameKeyCode, top - height6, bottom - height6, true);
            }
            if (traverse != Integer.MAX_VALUE) {
                if (this.focusItemIndex == -1 && this.items[testItemIndex].isFocusable()) {
                    this.items[testItemIndex].setFocus(true);
                    this.focusItemIndex = testItemIndex;
                }
                return traverse;
            }
            if (testItemIndex < this.numOfItems - 1) {
                for (int i2 = testItemIndex + 1; i2 <= bottomItemIndex2; i2++) {
                    if (this.items[i2].isFocusable()) {
                        if (this.focusItemIndex != -1) {
                            this.items[this.focusItemIndex].setFocus(false);
                        }
                        this.items[i2].setFocus(true);
                        this.focusItemIndex = i2;
                        int height7 = getHeightToItem(i2);
                        int traverse5 = this.items[i2].traverse(gameKeyCode, top - height7, bottom - height7, false);
                        if (traverse5 == Integer.MAX_VALUE) {
                            return 0;
                        }
                        return traverse5;
                    }
                }
                int height8 = getHeightToItem(bottomItemIndex2);
                int traverse6 = this.items[bottomItemIndex2].traverse(gameKeyCode, top - height8, bottom - height8, false);
                if (traverse6 != Integer.MAX_VALUE) {
                    int topItemIndex2 = getTopVisibleIndex(top + traverse6);
                    if (this.focusItemIndex != -1 && this.focusItemIndex < topItemIndex2) {
                        this.items[this.focusItemIndex].setFocus(false);
                        this.focusItemIndex = -1;
                    }
                    return traverse6;
                }
                return 0;
            }
            return 0;
        }
        return 0;
    }

    private int getTopVisibleIndex(int top) {
        int height = 0;
        for (int i = 0; i < this.numOfItems; i++) {
            height += this.items[i].getHeight();
            if (height >= top) {
                return i;
            }
        }
        return this.numOfItems - 1;
    }

    private int getBottomVisibleIndex(int bottom) {
        int height = 0;
        for (int i = 0; i < this.numOfItems; i++) {
            height += this.items[i].getHeight();
            if (height > bottom) {
                return i;
            }
        }
        return this.numOfItems - 1;
    }

    private int getHeightToItem(int itemIndex) {
        int height = 0;
        for (int i = 0; i < itemIndex; i++) {
            height += this.items[i].getHeight();
        }
        return height;
    }

    private void verifyItem(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }
        if (item.getOwner() != null) {
            throw new IllegalStateException("item is already owned");
        }
        item.setOwner(this);
    }

    private void verifyItemNum(int itemNum) {
        if (itemNum < 0 || itemNum >= this.numOfItems) {
            throw new IndexOutOfBoundsException("item number is outside range of Form");
        }
    }

    @Override // javax.microedition.lcdui.Displayable
    Vector getCommands() {
        Vector formCommands = super.getCommands();
        if (this.focusItemIndex < 0) {
            return formCommands;
        }
        Item item = this.items[this.focusItemIndex];
        Vector itemCommands = item.commands;
        if (itemCommands.isEmpty()) {
            return formCommands;
        }
        Vector allCommands = new Vector();
        for (int i = 0; i < formCommands.size(); i++) {
            allCommands.add(formCommands.elementAt(i));
        }
        for (int i2 = 0; i2 < itemCommands.size(); i2++) {
            Command itemCommand = (Command) itemCommands.elementAt(i2);
            allCommands.add(itemCommand.getItemCommand(item));
        }
        return allCommands;
    }
}
