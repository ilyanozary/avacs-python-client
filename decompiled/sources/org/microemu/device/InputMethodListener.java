package org.microemu.device;

/* loaded from: avacs.jar:org/microemu/device/InputMethodListener.class */
public interface InputMethodListener {
    void caretPositionChanged(InputMethodEvent inputMethodEvent);

    void inputMethodTextChanged(InputMethodEvent inputMethodEvent);

    int getCaretPosition();

    String getText();

    int getConstraints();
}
