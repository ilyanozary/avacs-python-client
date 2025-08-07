package org.microemu.device.swt;

import java.util.Hashtable;
import org.microemu.device.impl.Button;
import org.microemu.device.impl.Shape;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtButton.class */
public class SwtButton implements Button {
    String name;
    Shape shape;
    private int keyboardKey;
    private int keyCode;
    private Hashtable inputToChars;

    public SwtButton(String name, Shape shape, int keyCode, String keyName, Hashtable inputToChars) {
        this.name = name;
        this.shape = shape;
        this.keyboardKey = parseKeyboardKey(keyName);
        if (keyCode == Integer.MIN_VALUE) {
            if (keyName != null) {
                this.keyCode = this.keyboardKey;
            } else {
                this.keyCode = -1;
            }
        } else {
            this.keyCode = keyCode;
        }
        this.inputToChars = inputToChars;
    }

    public int getKeyboardKey() {
        return this.keyboardKey;
    }

    public int getKeyCode() {
        return this.keyCode;
    }

    public char[] getChars(int inputMode) {
        char[] result = (char[]) null;
        switch (inputMode) {
            case 1:
                result = (char[]) this.inputToChars.get("123");
                break;
            case 2:
                result = (char[]) this.inputToChars.get("ABC");
                break;
            case 3:
                result = (char[]) this.inputToChars.get("abc");
                break;
        }
        if (result == null) {
            result = (char[]) this.inputToChars.get("common");
        }
        if (result == null) {
            result = new char[0];
        }
        return result;
    }

    public boolean isChar(char c, int inputMode) {
        char c2 = Character.toLowerCase(c);
        char[] chars = getChars(inputMode);
        if (chars != null) {
            for (char c3 : chars) {
                if (c2 == Character.toLowerCase(c3)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    public Shape getShape() {
        return this.shape;
    }

    private int parseKeyboardKey(String keyName) {
        if (keyName == null) {
            return -1;
        }
        if (keyName.equals("VK_LEFT")) {
            return 16777219;
        }
        if (keyName.equals("VK_RIGHT")) {
            return 16777220;
        }
        if (keyName.equals("VK_UP")) {
            return 16777217;
        }
        if (keyName.equals("VK_DOWN")) {
            return 16777218;
        }
        if (keyName.equals("VK_ENTER")) {
            return 13;
        }
        if (keyName.equals("VK_F1")) {
            return 16777226;
        }
        if (keyName.equals("VK_F2")) {
            return 16777227;
        }
        if (keyName.equals("VK_0")) {
            return 48;
        }
        if (keyName.equals("VK_1")) {
            return 49;
        }
        if (keyName.equals("VK_2")) {
            return 50;
        }
        if (keyName.equals("VK_3")) {
            return 51;
        }
        if (keyName.equals("VK_4")) {
            return 52;
        }
        if (keyName.equals("VK_5")) {
            return 53;
        }
        if (keyName.equals("VK_6")) {
            return 54;
        }
        if (keyName.equals("VK_7")) {
            return 55;
        }
        if (keyName.equals("VK_8")) {
            return 56;
        }
        if (keyName.equals("VK_9")) {
            return 57;
        }
        if (keyName.equals("VK_MULTIPLY")) {
            return 42;
        }
        if (keyName.equals("VK_MODECHANGE")) {
            return 35;
        }
        try {
            return Integer.parseInt(keyName);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
