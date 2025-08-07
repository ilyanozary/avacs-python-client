package javax.microedition.lcdui;

import org.microemu.device.DeviceFactory;
import org.microemu.device.InputMethod;
import org.microemu.device.InputMethodEvent;
import org.microemu.device.InputMethodListener;
import org.microemu.device.ui.TextBoxUI;

/* loaded from: avacs.jar:javax/microedition/lcdui/TextBox.class */
public class TextBox extends Screen {
    TextField tf;
    InputMethodListener inputMethodListener;

    public TextBox(String title, String text, int maxSize, int constraints) {
        super(title);
        this.inputMethodListener = new InputMethodListener() { // from class: javax.microedition.lcdui.TextBox.1
            @Override // org.microemu.device.InputMethodListener
            public void caretPositionChanged(InputMethodEvent event) {
                TextBox.this.setCaretPosition(event.getCaret());
                TextBox.this.tf.setCaretVisible(true);
                TextBox.this.repaint();
            }

            @Override // org.microemu.device.InputMethodListener
            public void inputMethodTextChanged(InputMethodEvent event) {
                TextBox.this.tf.setCaretVisible(false);
                TextBox.this.tf.setString(event.getText(), event.getCaret());
                TextBox.this.repaint();
            }

            @Override // org.microemu.device.InputMethodListener
            public int getCaretPosition() {
                return TextBox.this.getCaretPosition();
            }

            @Override // org.microemu.device.InputMethodListener
            public String getText() {
                return TextBox.this.getString();
            }

            @Override // org.microemu.device.InputMethodListener
            public int getConstraints() {
                return TextBox.this.getConstraints();
            }
        };
        this.tf = new TextField(null, text, maxSize, constraints);
        super.setUI(DeviceFactory.getDevice().getUIFactory().createTextBoxUI(this));
    }

    public void delete(int offset, int length) {
        this.tf.delete(offset, length);
    }

    public int getCaretPosition() {
        if (this.ui != null && this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidTextBoxUI")) {
            return ((TextBoxUI) this.ui).getCaretPosition();
        }
        return this.tf.getCaretPosition();
    }

    public int getChars(char[] data) {
        return this.tf.getChars(data);
    }

    public int getConstraints() {
        return this.tf.getConstraints();
    }

    public int getMaxSize() {
        return this.tf.getMaxSize();
    }

    public String getString() {
        if (this.ui != null && this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidTextBoxUI")) {
            return ((TextBoxUI) this.ui).getString();
        }
        return this.tf.getString();
    }

    public void insert(char[] data, int offset, int length, int position) {
        this.tf.insert(data, offset, length, position);
    }

    public void insert(String src, int position) {
        this.tf.insert(src, position);
    }

    public void setChars(char[] data, int offset, int length) {
        this.tf.setChars(data, offset, length);
    }

    public void setConstraints(int constraints) {
        this.tf.setConstraints(constraints);
    }

    public void setInitialInputMode(String characterSubset) {
    }

    public int setMaxSize(int maxSize) {
        return this.tf.setMaxSize(maxSize);
    }

    public void setString(String text) {
        if (this.ui != null && this.ui.getClass().getName().equals("org.microemu.android.device.ui.AndroidTextBoxUI")) {
            ((TextBoxUI) this.ui).setString(text);
        } else {
            this.tf.setString(text);
        }
    }

    @Override // javax.microedition.lcdui.Displayable
    public void setTicker(Ticker ticker) {
    }

    @Override // javax.microedition.lcdui.Displayable
    public void setTitle(String s) {
        super.setTitle(s);
    }

    public int size() {
        return this.tf.size();
    }

    @Override // javax.microedition.lcdui.Screen, javax.microedition.lcdui.Displayable
    void hideNotify() {
        DeviceFactory.getDevice().getInputMethod().removeInputMethodListener(this.inputMethodListener);
        super.hideNotify();
    }

    @Override // javax.microedition.lcdui.Screen
    int paintContent(Graphics g) {
        this.tf.paintContent(g);
        return this.tf.stringComponent.getHeight() + 6;
    }

    void setCaretPosition(int position) {
        this.tf.setCaretPosition(position);
        StringComponent tmp = this.tf.stringComponent;
        if (tmp.getCharPositionY(position) < this.viewPortY) {
            this.viewPortY = tmp.getCharPositionY(position);
        } else if (tmp.getCharPositionY(position) + tmp.getCharHeight() > (this.viewPortY + this.viewPortHeight) - 6) {
            this.viewPortY = (tmp.getCharPositionY(position) + tmp.getCharHeight()) - (this.viewPortHeight - 6);
        }
    }

    @Override // javax.microedition.lcdui.Screen, javax.microedition.lcdui.Displayable
    void showNotify() {
        super.showNotify();
        InputMethod inputMethod = DeviceFactory.getDevice().getInputMethod();
        inputMethod.setInputMethodListener(this.inputMethodListener);
        inputMethod.setMaxSize(getMaxSize());
        setCaretPosition(getString().length());
        this.tf.setCaretVisible(true);
    }

    @Override // javax.microedition.lcdui.Screen
    int traverse(int gameKeyCode, int top, int bottom) {
        int traverse = this.tf.traverse(gameKeyCode, top, bottom, true);
        if (traverse == Integer.MAX_VALUE) {
            return 0;
        }
        return traverse;
    }
}
