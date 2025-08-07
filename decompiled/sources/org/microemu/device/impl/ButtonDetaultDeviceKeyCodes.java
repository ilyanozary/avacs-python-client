package org.microemu.device.impl;

import java.util.HashMap;
import java.util.Map;

/* loaded from: avacs.jar:org/microemu/device/impl/ButtonDetaultDeviceKeyCodes.class */
public abstract class ButtonDetaultDeviceKeyCodes {
    private static Map codes = new HashMap();
    private static Map gameActions = new HashMap();

    static {
        code(ButtonName.SOFT1, -6);
        code(ButtonName.SOFT2, -7);
        code(ButtonName.SELECT, -5, 8);
        code(ButtonName.UP, -1, 1);
        code(ButtonName.DOWN, -2, 6);
        code(ButtonName.LEFT, -3, 2);
        code(ButtonName.RIGHT, -4, 5);
        code(ButtonName.BACK_SPACE, -8);
        code(ButtonName.KEY_NUM0, 48);
        code(ButtonName.KEY_NUM1, 49, 9);
        code(ButtonName.KEY_NUM2, 50);
        code(ButtonName.KEY_NUM3, 51, 10);
        code(ButtonName.KEY_NUM4, 52);
        code(ButtonName.KEY_NUM5, 53);
        code(ButtonName.KEY_NUM6, 54);
        code(ButtonName.KEY_NUM7, 55, 11);
        code(ButtonName.KEY_NUM8, 56);
        code(ButtonName.KEY_NUM9, 57, 12);
        code(ButtonName.KEY_STAR, 42);
        code(ButtonName.KEY_POUND, 35);
    }

    public static int getKeyCode(ButtonName name) {
        Integer code = (Integer) codes.get(name);
        if (code != null) {
            return code.intValue();
        }
        return 0;
    }

    public static int getGameAction(ButtonName name) {
        Integer code = (Integer) gameActions.get(name);
        if (code != null) {
            return code.intValue();
        }
        return 0;
    }

    public static ButtonName getButtonNameByGameAction(int gameAction) {
        Integer value = new Integer(gameAction);
        if (gameActions.containsValue(value)) {
            for (Map.Entry v : gameActions.entrySet()) {
                if (v.getValue().equals(value)) {
                    return (ButtonName) v.getKey();
                }
            }
        }
        throw new IllegalArgumentException("Illegal action " + gameAction);
    }

    private static void code(ButtonName name, int code) {
        codes.put(name, new Integer(code));
    }

    private static void code(ButtonName name, int code, int gameAction) {
        code(name, code);
        gameActions.put(name, new Integer(gameAction));
    }
}
