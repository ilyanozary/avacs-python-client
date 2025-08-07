package org.microemu.device.j2se;

import java.util.HashMap;
import java.util.Map;
import org.microemu.device.impl.ButtonName;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:org/microemu/device/j2se/J2SEButtonDefaultKeyCodes.class */
public class J2SEButtonDefaultKeyCodes {
    private static Map codes = new HashMap();
    private static Map backwardCompatibleNames = new HashMap();

    static {
        code(ButtonName.SOFT1, Opcodes.IREM);
        code(ButtonName.SOFT2, Opcodes.LREM);
        code(ButtonName.SELECT, 10);
        code(ButtonName.UP, 38, 224);
        code(ButtonName.DOWN, 40, 225);
        code(ButtonName.LEFT, 37, 226);
        code(ButtonName.RIGHT, 39, 227);
        code(ButtonName.BACK_SPACE, 8);
        code(ButtonName.DELETE, 12, Opcodes.LAND);
        code(ButtonName.KEY_NUM0, 48, 96).charCodes = "0";
        code(ButtonName.KEY_NUM1, 49, 97).charCodes = "1";
        code(ButtonName.KEY_NUM2, 50, 98).charCodes = "2";
        code(ButtonName.KEY_NUM3, 51, 99).charCodes = "3";
        code(ButtonName.KEY_NUM4, 52, 100).charCodes = "4";
        code(ButtonName.KEY_NUM5, 53, Opcodes.LSUB).charCodes = "5";
        code(ButtonName.KEY_NUM6, 54, Opcodes.FSUB).charCodes = "6";
        code(ButtonName.KEY_NUM7, 55, Opcodes.DSUB).charCodes = "7";
        code(ButtonName.KEY_NUM8, 56, Opcodes.IMUL).charCodes = "8";
        code(ButtonName.KEY_NUM9, 57, Opcodes.LMUL).charCodes = "9";
        code(ButtonName.KEY_STAR, Opcodes.FMUL, Opcodes.DCMPL).charCodes = "*";
        code(ButtonName.KEY_POUND, 31, Opcodes.LDIV).charCodes = "#";
    }

    /* loaded from: avacs.jar:org/microemu/device/j2se/J2SEButtonDefaultKeyCodes$KeyInformation.class */
    private static class KeyInformation {
        int[] keyCodes;
        String charCodes;

        private KeyInformation() {
            this.charCodes = "";
        }

        /* synthetic */ KeyInformation(KeyInformation keyInformation) {
            this();
        }
    }

    public static int[] getKeyCodes(ButtonName name) {
        KeyInformation info = (KeyInformation) codes.get(name);
        if (info == null) {
            return new int[0];
        }
        return info.keyCodes;
    }

    public static String getCharCodes(ButtonName name) {
        KeyInformation info = (KeyInformation) codes.get(name);
        if (info == null) {
            return "";
        }
        return info.charCodes;
    }

    private static KeyInformation code(ButtonName name, int code) {
        KeyInformation info = new KeyInformation(null);
        info.keyCodes = new int[]{code};
        codes.put(name, info);
        backwardCompatibleNames.put(new Integer(code), name);
        return info;
    }

    private static KeyInformation code(ButtonName name, int code1, int code2) {
        KeyInformation info = new KeyInformation(null);
        info.keyCodes = new int[]{code1, code2};
        codes.put(name, info);
        backwardCompatibleNames.put(new Integer(code1), name);
        return info;
    }

    public static ButtonName getBackwardCompatibleName(int keyboardKey) {
        return (ButtonName) backwardCompatibleNames.get(new Integer(keyboardKey));
    }
}
