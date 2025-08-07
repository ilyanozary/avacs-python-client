package javax.microedition.lcdui;

import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.ListUI;

/* loaded from: avacs.jar:javax/microedition/lcdui/List.class */
public class List extends Screen implements Choice {
    public static final Command SELECT_COMMAND = new Command("", 1, 0);
    ChoiceGroup choiceGroup;
    private Command selCommand;
    private int initialPressedItem;

    public List(String title, int listType) {
        super(title);
        super.setUI(DeviceFactory.getDevice().getUIFactory().createListUI(this));
        if (listType != 3 && listType != 2 && listType != 1) {
            throw new IllegalArgumentException("Illegal list type");
        }
        if (listType == 3) {
            this.choiceGroup = new ChoiceGroup(null, 3, false);
        } else {
            this.choiceGroup = new ChoiceGroup(null, listType);
        }
        this.choiceGroup.setOwner(this);
        this.choiceGroup.setFocus(true);
        this.selCommand = SELECT_COMMAND;
        this.initialPressedItem = -1;
    }

    public List(String title, int listType, String[] stringElements, Image[] imageElements) {
        super(title);
        super.setUI(DeviceFactory.getDevice().getUIFactory().createListUI(this));
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
            for (int i = 0; i < stringElements.length; i++) {
                if (imageElements == null) {
                    append(stringElements[i], null);
                } else {
                    append(stringElements[i], imageElements[i]);
                }
            }
            this.choiceGroup = new ChoiceGroup(null, listType, stringElements, imageElements, false);
        } else if (listType == 3) {
            this.choiceGroup = new ChoiceGroup(null, 3, stringElements, imageElements, false);
            for (int i2 = 0; i2 < size(); i2++) {
                set(i2, getString(i2), null);
            }
        } else {
            this.choiceGroup = new ChoiceGroup(null, listType, stringElements, imageElements);
        }
        this.choiceGroup.setOwner(this);
        this.choiceGroup.setFocus(true);
        this.selCommand = SELECT_COMMAND;
        this.initialPressedItem = -1;
    }

    @Override // javax.microedition.lcdui.Choice
    public int append(String stringPart, Image imagePart) {
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
            return ((ListUI) this.ui).append(stringPart, imagePart);
        }
        return this.choiceGroup.append(stringPart, imagePart);
    }

    @Override // javax.microedition.lcdui.Choice
    public void delete(int elementNum) {
        this.choiceGroup.delete(elementNum);
    }

    @Override // javax.microedition.lcdui.Choice
    public void deleteAll() {
        this.choiceGroup.deleteAll();
    }

    @Override // javax.microedition.lcdui.Choice
    public int getFitPolicy() {
        return this.choiceGroup.getFitPolicy();
    }

    @Override // javax.microedition.lcdui.Choice
    public Font getFont(int elementNum) {
        return this.choiceGroup.getFont(elementNum);
    }

    @Override // javax.microedition.lcdui.Choice
    public Image getImage(int elementNum) {
        return this.choiceGroup.getImage(elementNum);
    }

    @Override // javax.microedition.lcdui.Choice
    public int getSelectedFlags(boolean[] selectedArray_return) {
        return this.choiceGroup.getSelectedFlags(selectedArray_return);
    }

    @Override // javax.microedition.lcdui.Choice
    public int getSelectedIndex() {
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
            return ((ListUI) this.ui).getSelectedIndex();
        }
        return this.choiceGroup.getSelectedIndex();
    }

    @Override // javax.microedition.lcdui.Choice
    public String getString(int elementNum) {
        if (this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
            return ((ListUI) this.ui).getString(elementNum);
        }
        return this.choiceGroup.getString(elementNum);
    }

    @Override // javax.microedition.lcdui.Choice
    public void insert(int elementNum, String stringPart, Image imagePart) {
        this.choiceGroup.insert(elementNum, stringPart, imagePart);
    }

    @Override // javax.microedition.lcdui.Choice
    public boolean isSelected(int elementNum) {
        return this.choiceGroup.isSelected(elementNum);
    }

    @Override // javax.microedition.lcdui.Displayable
    public void removeCommand(Command cmd) {
        super.removeCommand(cmd);
    }

    @Override // javax.microedition.lcdui.Choice
    public void set(int elementNum, String stringPart, Image imagePart) {
        this.choiceGroup.set(elementNum, stringPart, imagePart);
    }

    @Override // javax.microedition.lcdui.Choice
    public void setFitPolicy(int policy) {
        this.choiceGroup.setFitPolicy(policy);
    }

    @Override // javax.microedition.lcdui.Choice
    public void setFont(int elementNum, Font font) {
        this.choiceGroup.setFont(elementNum, font);
    }

    public void setSelectCommand(Command command) {
        this.selCommand = command;
        ((ListUI) this.ui).setSelectCommand(command);
    }

    @Override // javax.microedition.lcdui.Choice
    public void setSelectedFlags(boolean[] selectedArray) {
        this.choiceGroup.setSelectedFlags(selectedArray);
    }

    @Override // javax.microedition.lcdui.Choice
    public void setSelectedIndex(int elementNum, boolean selected) {
        this.choiceGroup.setSelectedIndex(elementNum, selected);
    }

    @Override // javax.microedition.lcdui.Displayable
    public void setTicker(Ticker ticker) {
        super.setTicker(ticker);
    }

    @Override // javax.microedition.lcdui.Displayable
    public void setTitle(String s) {
        super.setTitle(s);
    }

    @Override // javax.microedition.lcdui.Screen, javax.microedition.lcdui.Displayable
    void keyPressed(int keyCode) {
        if (Display.getGameAction(keyCode) == 8 && this.choiceGroup.select() && super.getCommandListener() != null && this.choiceGroup.choiceType == 3) {
            super.getCommandListener().commandAction(this.selCommand, this);
        } else {
            super.keyPressed(keyCode);
        }
    }

    @Override // javax.microedition.lcdui.Displayable
    void pointerPressed(int x, int y) {
        int pressedItem;
        Ticker ticker = getTicker();
        if (ticker != null) {
            y -= ticker.getHeight();
        }
        StringComponent title = new StringComponent(getTitle());
        int y2 = (y - title.getHeight()) - 1;
        if (y2 >= 0 && y2 < this.viewPortHeight && (pressedItem = this.choiceGroup.getItemIndexAt(x, y2 + this.viewPortY)) != -1) {
            if (this.choiceGroup.choiceType == 2) {
                setSelectedIndex(pressedItem, !isSelected(pressedItem));
            } else {
                setSelectedIndex(pressedItem, true);
            }
            this.initialPressedItem = pressedItem;
        }
    }

    @Override // javax.microedition.lcdui.Displayable
    void pointerReleased(int x, int y) {
        int releasedItem;
        Ticker ticker = getTicker();
        if (ticker != null) {
            y -= ticker.getHeight();
        }
        StringComponent title = new StringComponent(getTitle());
        int y2 = (y - title.getHeight()) - 1;
        if (y2 >= 0 && y2 < this.viewPortHeight && this.choiceGroup.choiceType == 3 && (releasedItem = this.choiceGroup.getItemIndexAt(x, y2 + this.viewPortY)) != -1 && releasedItem == this.initialPressedItem && super.getCommandListener() != null && this.choiceGroup.choiceType == 3) {
            super.getCommandListener().commandAction(SELECT_COMMAND, this);
        }
    }

    @Override // javax.microedition.lcdui.Screen
    int paintContent(Graphics g) {
        return this.choiceGroup.paint(g);
    }

    @Override // javax.microedition.lcdui.Choice
    public int size() {
        return this.choiceGroup.size();
    }

    @Override // javax.microedition.lcdui.Screen, javax.microedition.lcdui.Displayable
    void showNotify() {
        super.showNotify();
        if (!this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidListUI")) {
            int selectedItemIndex = getSelectedIndex();
            int heightToItem = this.choiceGroup.getHeightToItem(selectedItemIndex);
            int heightAfterItem = heightToItem;
            if (selectedItemIndex >= 0) {
                heightAfterItem += this.choiceGroup.getItemHeight(selectedItemIndex);
            }
            if (this.viewPortY > heightToItem) {
                this.viewPortY = heightToItem;
            } else if (this.viewPortY + this.viewPortHeight < heightAfterItem) {
                this.viewPortY = heightAfterItem - this.viewPortHeight;
            }
        }
    }

    @Override // javax.microedition.lcdui.Screen
    int traverse(int gameKeyCode, int top, int bottom) {
        int traverse = this.choiceGroup.traverse(gameKeyCode, top, bottom, true);
        if (traverse == Integer.MAX_VALUE) {
            return 0;
        }
        return traverse;
    }
}
