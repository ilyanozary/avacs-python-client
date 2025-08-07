package org.microemu.device;

import javax.microedition.lcdui.TextField;

/* loaded from: avacs.jar:org/microemu/device/InputMethod.class */
public abstract class InputMethod {
    public static final int INPUT_NONE = 0;
    public static final int INPUT_123 = 1;
    public static final int INPUT_ABC_UPPER = 2;
    public static final int INPUT_ABC_LOWER = 3;
    static InputMethod inputMethod = null;
    int inputMode = 0;
    protected InputMethodListener inputMethodListener = null;
    protected int maxSize;

    public abstract void dispose();

    public abstract int getGameAction(int i);

    public abstract int getKeyCode(int i);

    public abstract String getKeyName(int i) throws IllegalArgumentException;

    public void removeInputMethodListener(InputMethodListener l) {
        if (l == this.inputMethodListener) {
            this.inputMethodListener = null;
            setInputMode(0);
        }
    }

    public void setInputMethodListener(InputMethodListener l) {
        this.inputMethodListener = l;
        switch (l.getConstraints() & TextField.CONSTRAINT_MASK) {
            case 0:
            case 1:
            case 4:
                setInputMode(3);
                break;
            case 2:
            case 3:
            case 5:
                setInputMode(1);
                break;
        }
    }

    public int getInputMode() {
        return this.inputMode;
    }

    public void setInputMode(int mode) {
        this.inputMode = mode;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public static boolean validate(String text, int constraints) throws NumberFormatException {
        switch (constraints & TextField.CONSTRAINT_MASK) {
            case 2:
                if (text != null && text.length() > 0 && !text.equals("-")) {
                    try {
                        Integer.parseInt(text);
                        break;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
                break;
            case 5:
                if (text != null && text.length() > 0 && !text.equals("-")) {
                    try {
                        Double.valueOf(text);
                        break;
                    } catch (NumberFormatException e2) {
                        return false;
                    }
                }
                break;
        }
        return false;
    }
}
