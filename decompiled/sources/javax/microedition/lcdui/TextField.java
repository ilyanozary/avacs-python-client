package javax.microedition.lcdui;

import org.microemu.device.DeviceFactory;
import org.microemu.device.InputMethod;
import org.microemu.device.InputMethodEvent;
import org.microemu.device.InputMethodListener;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:javax/microedition/lcdui/TextField.class */
public class TextField extends Item {
    public static final int ANY = 0;
    public static final int EMAILADDR = 1;
    public static final int NUMERIC = 2;
    public static final int PHONENUMBER = 3;
    public static final int URL = 4;
    public static final int DECIMAL = 5;
    public static final int PASSWORD = 65536;
    public static final int UNEDITABLE = 131072;
    public static final int SENSITIVE = 262144;
    public static final int NON_PREDICTIVE = 524288;
    public static final int INITIAL_CAPS_WORD = 1048576;
    public static final int INITIAL_CAPS_SENTENCE = 2097152;
    public static final int CONSTRAINT_MASK = 65535;
    StringComponent stringComponent;
    private String field;
    private int caret;
    private boolean caretVisible;
    private int maxSize;
    private int constraints;
    private InputMethodListener inputMethodListener;

    public TextField(String label, String text, int maxSize, int constraints) {
        super(label);
        this.inputMethodListener = new InputMethodListener() { // from class: javax.microedition.lcdui.TextField.1
            @Override // org.microemu.device.InputMethodListener
            public void caretPositionChanged(InputMethodEvent event) {
                TextField.this.setCaretPosition(event.getCaret());
                TextField.this.setCaretVisible(true);
                TextField.this.repaint();
            }

            @Override // org.microemu.device.InputMethodListener
            public void inputMethodTextChanged(InputMethodEvent event) {
                TextField.this.setCaretVisible(false);
                TextField.this.setString(event.getText(), event.getCaret());
                TextField.this.repaint();
                if (TextField.this.owner instanceof Form) {
                    ((Form) TextField.this.owner).fireItemStateListener();
                }
            }

            @Override // org.microemu.device.InputMethodListener
            public int getCaretPosition() {
                return TextField.this.getCaretPosition();
            }

            @Override // org.microemu.device.InputMethodListener
            public String getText() {
                return TextField.this.getString();
            }

            @Override // org.microemu.device.InputMethodListener
            public int getConstraints() {
                return TextField.this.getConstraints();
            }
        };
        if (maxSize <= 0) {
            throw new IllegalArgumentException();
        }
        setConstraints(constraints);
        if (!InputMethod.validate(text, constraints)) {
            throw new IllegalArgumentException();
        }
        this.maxSize = maxSize;
        this.stringComponent = new StringComponent();
        if (text != null) {
            setString(text);
        } else {
            setString("");
        }
        this.stringComponent.setWidthDecreaser(8);
    }

    public String getString() {
        return this.field;
    }

    public void setString(String text) {
        setString(text, text.length());
    }

    void setString(String text, int caret) {
        if (!InputMethod.validate(text, this.constraints)) {
            throw new IllegalArgumentException();
        }
        if (text == null) {
            this.field = "";
            this.stringComponent.setText("");
        } else {
            if (text.length() > this.maxSize) {
                throw new IllegalArgumentException();
            }
            this.field = text;
            if ((this.constraints & PASSWORD) == 0) {
                this.stringComponent.setText(text);
            } else {
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < text.length(); i++) {
                    sb.append('*');
                }
                this.stringComponent.setText(sb.toString());
            }
        }
        setCaretPosition(caret);
        setCaretVisible(false);
        repaint();
    }

    public int getChars(char[] data) {
        if (data.length < this.field.length()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        getString().getChars(0, this.field.length(), data, 0);
        return this.field.length();
    }

    public void setChars(char[] data, int offset, int length) {
        if (data == null) {
            setString("");
        } else {
            if (length > this.maxSize) {
                throw new IllegalArgumentException();
            }
            String newtext = new String(data, offset, length);
            if (!InputMethod.validate(newtext, this.constraints)) {
                throw new IllegalArgumentException();
            }
            setString(newtext);
        }
        repaint();
    }

    public void insert(String src, int position) {
        if (!InputMethod.validate(src, this.constraints)) {
            throw new IllegalArgumentException();
        }
        if (this.field.length() + src.length() > this.maxSize) {
            throw new IllegalArgumentException();
        }
        String newtext = "";
        if (position > 0) {
            newtext = getString().substring(0, position);
        }
        String newtext2 = String.valueOf(newtext) + src;
        if (position < this.field.length()) {
            newtext2 = String.valueOf(newtext2) + getString().substring(position);
        }
        setString(newtext2);
        repaint();
    }

    public void insert(char[] data, int offset, int length, int position) {
        if (offset + length > data.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
        insert(new String(data, offset, length), position);
    }

    public void delete(int offset, int length) {
        if (offset + length > this.field.length()) {
            throw new StringIndexOutOfBoundsException();
        }
        String newtext = "";
        if (offset > 0) {
            newtext = getString().substring(0, offset);
        }
        if (offset + length < this.field.length()) {
            newtext = String.valueOf(newtext) + getString().substring(offset + length);
        }
        setString(newtext);
        repaint();
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public int setMaxSize(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException();
        }
        if (this.field.length() > maxSize) {
            setString(getString().substring(0, maxSize));
        }
        this.maxSize = maxSize;
        return maxSize;
    }

    public int size() {
        return this.field.length();
    }

    public int getCaretPosition() {
        return this.caret;
    }

    public void setConstraints(int constraints) {
        if ((constraints & CONSTRAINT_MASK) < 0 || (constraints & CONSTRAINT_MASK) > 5) {
            throw new IllegalArgumentException("constraints " + constraints + " is an illegal value");
        }
        this.constraints = constraints;
        if (!InputMethod.validate(this.field, constraints)) {
            setString("");
        }
    }

    public int getConstraints() {
        return this.constraints;
    }

    public void setInitialInputMode(String characterSubset) {
    }

    @Override // javax.microedition.lcdui.Item
    boolean isFocusable() {
        return true;
    }

    @Override // javax.microedition.lcdui.Item
    int getHeight() {
        return super.getHeight() + this.stringComponent.getHeight() + 8;
    }

    @Override // javax.microedition.lcdui.Item
    int paint(Graphics g) {
        super.paintContent(g);
        g.translate(0, super.getHeight());
        int savedColor = g.getColor();
        if (!hasFocus()) {
            g.setGrayScale(Opcodes.LAND);
        }
        g.drawRect(1, 1, this.owner.getWidth() - 3, this.stringComponent.getHeight() + 4);
        if (!hasFocus()) {
            g.setColor(savedColor);
        }
        g.translate(3, 3);
        paintContent(g);
        g.translate(-3, -3);
        g.translate(0, -super.getHeight());
        return getHeight();
    }

    @Override // javax.microedition.lcdui.Item
    void paintContent(Graphics g) {
        this.stringComponent.paint(g);
        if (this.caretVisible) {
            int x_pos = this.stringComponent.getCharPositionX(this.caret);
            int y_pos = this.stringComponent.getCharPositionY(this.caret);
            g.drawLine(x_pos, y_pos, x_pos, y_pos + Font.getDefaultFont().getHeight());
        }
    }

    void setCaretPosition(int position) {
        this.caret = position;
    }

    void setCaretVisible(boolean state) {
        this.caretVisible = true;
    }

    @Override // javax.microedition.lcdui.Item
    int traverse(int gameKeyCode, int top, int bottom, boolean action) {
        if (gameKeyCode == 1) {
            if (top > 0) {
                return -top;
            }
            return Integer.MAX_VALUE;
        }
        if (gameKeyCode == 6) {
            if (getHeight() > bottom) {
                return getHeight() - bottom;
            }
            return Integer.MAX_VALUE;
        }
        return 0;
    }

    @Override // javax.microedition.lcdui.Item
    void setFocus(boolean hasFocus) {
        super.setFocus(hasFocus);
        if (hasFocus) {
            InputMethod inputMethod = DeviceFactory.getDevice().getInputMethod();
            inputMethod.setInputMethodListener(this.inputMethodListener);
            inputMethod.setMaxSize(getMaxSize());
            setCaretVisible(true);
            return;
        }
        DeviceFactory.getDevice().getInputMethod().removeInputMethodListener(this.inputMethodListener);
        setCaretVisible(false);
    }
}
