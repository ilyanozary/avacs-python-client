package org.microemu.device.impl;

import javax.microedition.lcdui.TextField;
import org.microemu.MIDletBridge;
import org.microemu.device.DeviceFactory;
import org.microemu.device.InputMethod;
import org.microemu.device.InputMethodEvent;
import org.microemu.device.InputMethodListener;

/* loaded from: avacs.jar:org/microemu/device/impl/InputMethodImpl.class */
public abstract class InputMethodImpl extends InputMethod implements Runnable {
    protected boolean resetKey;
    protected Button lastButton = null;
    protected int lastButtonCharIndex = -1;
    private boolean cancel = false;
    private Thread t = new Thread(this, "InputMethodThread");

    public InputMethodImpl() {
        this.t.setDaemon(true);
        this.t.start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    @Override // org.microemu.device.InputMethod
    public void dispose() {
        this.cancel = true;
        ?? r0 = this;
        synchronized (r0) {
            notify();
            r0 = r0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v5, types: [boolean] */
    @Override // java.lang.Runnable
    public void run() {
        int caret;
        ?? r0;
        while (!this.cancel) {
            try {
                this.resetKey = true;
                r0 = this;
            } catch (InterruptedException e) {
            }
            synchronized (r0) {
                wait(1500L);
                r0 = r0;
                ?? r02 = this;
                synchronized (r02) {
                    r02 = this.resetKey;
                    if (r02 != 0 && this.lastButton != null && this.inputMethodListener != null && (caret = this.inputMethodListener.getCaretPosition() + 1) <= this.inputMethodListener.getText().length()) {
                        this.lastButton = null;
                        this.lastButtonCharIndex = -1;
                        InputMethodEvent event = new InputMethodEvent(1, caret, this.inputMethodListener.getText());
                        this.inputMethodListener.caretPositionChanged(event);
                    }
                }
            }
        }
    }

    @Override // org.microemu.device.InputMethod
    public void setInputMethodListener(InputMethodListener l) {
        super.setInputMethodListener(l);
        this.lastButton = null;
        this.lastButtonCharIndex = -1;
    }

    public void pointerPressed(int x, int y) {
        if (DeviceFactory.getDevice().hasPointerEvents()) {
            MIDletBridge.getMIDletAccess().getDisplayAccess().pointerPressed(x, y);
        }
    }

    public void pointerReleased(int x, int y) {
        if (DeviceFactory.getDevice().hasPointerEvents()) {
            MIDletBridge.getMIDletAccess().getDisplayAccess().pointerReleased(x, y);
        }
    }

    public void pointerDragged(int x, int y) {
        if (DeviceFactory.getDevice().hasPointerMotionEvents()) {
            MIDletBridge.getMIDletAccess().getDisplayAccess().pointerDragged(x, y);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.Throwable] */
    protected void insertText(String str) {
        if (str.length() > 0) {
            int caret = this.inputMethodListener.getCaretPosition();
            String tmp = "";
            ?? r0 = this;
            synchronized (r0) {
                if (this.lastButton != null) {
                    caret++;
                    this.lastButton = null;
                    this.lastButtonCharIndex = -1;
                }
                if (caret > 0) {
                    tmp = String.valueOf(tmp) + this.inputMethodListener.getText().substring(0, caret);
                }
                String tmp2 = String.valueOf(tmp) + str;
                if (caret < this.inputMethodListener.getText().length()) {
                    tmp2 = String.valueOf(tmp2) + this.inputMethodListener.getText().substring(caret);
                }
                int caret2 = caret + str.length();
                r0 = r0;
                if (!validate(tmp2, this.inputMethodListener.getConstraints())) {
                    return;
                }
                InputMethodEvent event = new InputMethodEvent(2, caret2, tmp2);
                this.inputMethodListener.inputMethodTextChanged(event);
                InputMethodEvent event2 = new InputMethodEvent(1, caret2, tmp2);
                this.inputMethodListener.caretPositionChanged(event2);
            }
        }
    }

    protected char[] filterConstraints(char[] chars) {
        char[] result = new char[chars.length];
        int i = 0;
        int j = 0;
        while (i < chars.length) {
            switch (this.inputMethodListener.getConstraints() & TextField.CONSTRAINT_MASK) {
                case 0:
                    result[j] = chars[i];
                    j++;
                    break;
                case 2:
                    if (!Character.isDigit(chars[i]) && chars[i] != '-') {
                        break;
                    } else {
                        result[j] = chars[i];
                        j++;
                        break;
                    }
                case 4:
                    if (chars[i] == '\n') {
                        break;
                    } else {
                        result[j] = chars[i];
                        j++;
                        break;
                    }
                case 5:
                    if (!Character.isDigit(chars[i]) && chars[i] != '-' && chars[i] != '.') {
                        break;
                    } else {
                        result[j] = chars[i];
                        j++;
                        break;
                    }
                    break;
            }
            i++;
        }
        if (i != j) {
            char[] newresult = new char[j];
            System.arraycopy(result, 0, newresult, 0, j);
            result = newresult;
        }
        return result;
    }

    protected char[] filterInputMode(char[] chars) {
        if (chars == null) {
            return new char[0];
        }
        int inputMode = getInputMode();
        char[] result = new char[chars.length];
        int i = 0;
        int j = 0;
        while (i < chars.length) {
            if (inputMode == 2) {
                result[j] = Character.toUpperCase(chars[i]);
                j++;
            } else if (inputMode == 3) {
                result[j] = Character.toLowerCase(chars[i]);
                j++;
            } else if (inputMode == 1 && (Character.isDigit(chars[i]) || chars[i] == '-' || chars[i] == '.')) {
                result[j] = chars[i];
                j++;
            }
            i++;
        }
        if (i != j) {
            char[] newresult = new char[j];
            System.arraycopy(result, 0, newresult, 0, j);
            result = newresult;
        }
        return result;
    }
}
