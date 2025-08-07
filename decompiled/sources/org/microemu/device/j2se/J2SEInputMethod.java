package org.microemu.device.j2se;

import java.awt.event.KeyEvent;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextField;
import org.microemu.DisplayAccess;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.device.DeviceFactory;
import org.microemu.device.InputMethodEvent;
import org.microemu.device.impl.ButtonDetaultDeviceKeyCodes;
import org.microemu.device.impl.ButtonName;
import org.microemu.device.impl.InputMethodImpl;
import org.microemu.device.impl.SoftButton;
import org.microemu.device.impl.ui.CommandManager;
import org.microemu.util.ThreadUtils;

/* loaded from: avacs.jar:org/microemu/device/j2se/J2SEInputMethod.class */
public class J2SEInputMethod extends InputMethodImpl {
    private boolean eventAlreadyConsumed;
    private List repeatModeKeyCodes = new Vector();
    private Timer keyReleasedDelayTimer = ThreadUtils.createTimer("InputKeyReleasedDelayTimer");

    /* loaded from: avacs.jar:org/microemu/device/j2se/J2SEInputMethod$KeyReleasedDelayTask.class */
    private class KeyReleasedDelayTask extends TimerTask {
        private int repeatModeKeyCode;

        KeyReleasedDelayTask(int repeatModeKeyCode) {
            this.repeatModeKeyCode = repeatModeKeyCode;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            MIDletAccess ma;
            DisplayAccess da;
            if (this.repeatModeKeyCode == Integer.MIN_VALUE || (ma = MIDletBridge.getMIDletAccess()) == null || (da = ma.getDisplayAccess()) == null) {
                return;
            }
            da.keyReleased(this.repeatModeKeyCode);
            J2SEInputMethod.this.eventAlreadyConsumed = false;
            this.repeatModeKeyCode = Integer.MIN_VALUE;
        }
    }

    @Override // org.microemu.device.InputMethod
    public int getGameAction(int keyCode) {
        Iterator it = DeviceFactory.getDevice().getButtons().iterator();
        while (it.hasNext()) {
            J2SEButton button = (J2SEButton) it.next();
            if (button.getKeyCode() == keyCode) {
                return ButtonDetaultDeviceKeyCodes.getGameAction(button.getFunctionalName());
            }
        }
        return 0;
    }

    @Override // org.microemu.device.InputMethod
    public int getKeyCode(int gameAction) {
        ButtonName name = ButtonDetaultDeviceKeyCodes.getButtonNameByGameAction(gameAction);
        return J2SEDeviceButtonsHelper.getButton(name).getKeyCode();
    }

    @Override // org.microemu.device.InputMethod
    public String getKeyName(int keyCode) throws IllegalArgumentException {
        Iterator it = DeviceFactory.getDevice().getButtons().iterator();
        while (it.hasNext()) {
            J2SEButton button = (J2SEButton) it.next();
            if (button.getKeyCode() == keyCode) {
                return button.getName();
            }
        }
        return Character.toString((char) keyCode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v102 */
    /* JADX WARN: Type inference failed for: r0v141 */
    /* JADX WARN: Type inference failed for: r0v142, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v145 */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r0v45 */
    /* JADX WARN: Type inference failed for: r0v46, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v55 */
    /* JADX WARN: Type inference failed for: r0v75 */
    /* JADX WARN: Type inference failed for: r0v76, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v80 */
    /* JADX WARN: Type inference failed for: r0v97 */
    /* JADX WARN: Type inference failed for: r0v98, types: [java.lang.Throwable] */
    protected boolean fireInputMethodListener(J2SEButton button, char keyChar) {
        DisplayAccess da;
        MIDletAccess ma = MIDletBridge.getMIDletAccess();
        if (ma == null || (da = ma.getDisplayAccess()) == null) {
            return false;
        }
        int keyCode = keyChar;
        if (button != null && keyChar == 0) {
            keyCode = button.getKeyCode();
        }
        if (this.inputMethodListener == null) {
            da.keyPressed(keyCode);
            return true;
        }
        if (button == null) {
            return true;
        }
        ButtonName functionalName = button.getFunctionalName();
        if (functionalName == ButtonName.UP || functionalName == ButtonName.DOWN) {
            da.keyPressed(button.getKeyCode());
            return true;
        }
        int caret = this.inputMethodListener.getCaretPosition();
        if (button.isModeChange()) {
            switch (this.inputMethodListener.getConstraints() & TextField.CONSTRAINT_MASK) {
                case 0:
                case 1:
                case 4:
                    if (getInputMode() == 1) {
                        setInputMode(2);
                    } else if (getInputMode() == 2) {
                        setInputMode(3);
                    } else if (getInputMode() == 3) {
                        setInputMode(1);
                    }
                    ?? r0 = this;
                    synchronized (r0) {
                        if (this.lastButton != null) {
                            caret++;
                            this.lastButton = null;
                            this.lastButtonCharIndex = -1;
                        }
                        r0 = r0;
                        InputMethodEvent event = new InputMethodEvent(1, caret, this.inputMethodListener.getText());
                        this.inputMethodListener.caretPositionChanged(event);
                        return true;
                    }
                case 2:
                case 3:
                default:
                    return true;
            }
        }
        if (functionalName == ButtonName.SELECT) {
            Displayable d = da.getCurrent();
            Command cmd = new Command("", "", 4, 1);
            da.commandAction(cmd, d);
            return true;
        }
        if (functionalName == ButtonName.LEFT || functionalName == ButtonName.RIGHT) {
            ?? r02 = this;
            synchronized (r02) {
                if (functionalName == ButtonName.LEFT && caret > 0) {
                    caret--;
                } else if (functionalName == ButtonName.RIGHT && caret < this.inputMethodListener.getText().length()) {
                    caret++;
                }
                this.lastButton = null;
                this.lastButtonCharIndex = -1;
                r02 = r02;
                InputMethodEvent event2 = new InputMethodEvent(1, caret, this.inputMethodListener.getText());
                this.inputMethodListener.caretPositionChanged(event2);
                return true;
            }
        }
        if (functionalName == ButtonName.BACK_SPACE) {
            String tmp = "";
            ?? r03 = this;
            synchronized (r03) {
                if (this.lastButton != null) {
                    caret++;
                    this.lastButton = null;
                    this.lastButtonCharIndex = -1;
                }
                if (caret > 0) {
                    caret--;
                    if (caret > 0) {
                        tmp = String.valueOf(tmp) + this.inputMethodListener.getText().substring(0, caret);
                    }
                    if (caret < this.inputMethodListener.getText().length() - 1) {
                        tmp = String.valueOf(tmp) + this.inputMethodListener.getText().substring(caret + 1);
                    }
                }
                r03 = r03;
                if (!validate(tmp, this.inputMethodListener.getConstraints())) {
                    return true;
                }
                InputMethodEvent event3 = new InputMethodEvent(2, caret, tmp);
                this.inputMethodListener.inputMethodTextChanged(event3);
                InputMethodEvent event4 = new InputMethodEvent(1, caret, tmp);
                this.inputMethodListener.caretPositionChanged(event4);
                return true;
            }
        }
        if (functionalName == ButtonName.DELETE) {
            String tmp2 = this.inputMethodListener.getText();
            ?? r04 = this;
            synchronized (r04) {
                if (this.lastButton != null) {
                    this.lastButton = null;
                    this.lastButtonCharIndex = -1;
                }
                if (caret != this.inputMethodListener.getText().length()) {
                    tmp2 = String.valueOf(this.inputMethodListener.getText().substring(0, caret)) + this.inputMethodListener.getText().substring(caret + 1);
                }
                r04 = r04;
                if (!validate(tmp2, this.inputMethodListener.getConstraints())) {
                    return true;
                }
                InputMethodEvent event5 = new InputMethodEvent(2, caret, tmp2);
                this.inputMethodListener.inputMethodTextChanged(event5);
                InputMethodEvent event6 = new InputMethodEvent(1, caret, tmp2);
                this.inputMethodListener.caretPositionChanged(event6);
                return true;
            }
        }
        if (this.inputMethodListener.getText().length() < this.maxSize) {
            StringBuffer editText = new StringBuffer(this.inputMethodListener.getText());
            ?? r05 = this;
            synchronized (r05) {
                this.lastButtonCharIndex++;
                char[] buttonChars = filterConstraints(filterInputMode(button.getChars(getInputMode())));
                if (keyChar != 0) {
                    if (editText.length() < caret) {
                        editText.append(buttonChars[0]);
                    } else {
                        editText.insert(caret, keyChar);
                    }
                    caret++;
                    this.lastButton = null;
                    this.lastButtonCharIndex = -1;
                } else {
                    this.lastButton = null;
                    this.lastButtonCharIndex = -1;
                }
                this.resetKey = false;
                notify();
                r05 = r05;
                if (!validate(editText.toString(), this.inputMethodListener.getConstraints())) {
                    return false;
                }
                InputMethodEvent event7 = new InputMethodEvent(2, caret, editText.toString());
                this.inputMethodListener.inputMethodTextChanged(event7);
                return false;
            }
        }
        return false;
    }

    public void buttonTyped(J2SEButton button) {
        if (this.eventAlreadyConsumed) {
        }
    }

    public void clipboardPaste(String str) {
        if (this.inputMethodListener != null && this.inputMethodListener.getText() != null && this.inputMethodListener.getText().length() + str.length() <= this.maxSize) {
            insertText(str);
        }
        this.eventAlreadyConsumed = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void buttonPressed(J2SEButton j2SEButton, char keyChar) {
        Command cmd;
        DisplayAccess da;
        if (j2SEButton != 0 && keyChar == 0) {
            j2SEButton.getKeyCode();
        }
        this.eventAlreadyConsumed = false;
        boolean rawSoftKeys = DeviceFactory.getDevice().getDeviceDisplay().isFullScreenMode();
        if ((j2SEButton instanceof SoftButton) && !rawSoftKeys && (cmd = ((SoftButton) j2SEButton).getCommand()) != null) {
            System.out.println("SoftButton=" + cmd.toString());
            MIDletAccess ma = MIDletBridge.getMIDletAccess();
            if (ma == null || (da = ma.getDisplayAccess()) == null) {
                return;
            }
            if (cmd.equals(CommandManager.CMD_MENU)) {
                CommandManager.getInstance().commandAction(cmd);
            } else {
                da.commandAction(cmd, da.getCurrent());
            }
            this.eventAlreadyConsumed = true;
            return;
        }
        if (fireInputMethodListener(j2SEButton, keyChar)) {
            this.eventAlreadyConsumed = true;
        }
    }

    public void buttonReleased(J2SEButton button, char keyChar) {
        DisplayAccess da;
        int keyCode = keyChar;
        if (button != null && keyChar == 0) {
            keyCode = button.getKeyCode();
        }
        if (DeviceFactory.getDevice().hasRepeatEvents()) {
            this.repeatModeKeyCodes.remove(new Integer(keyCode));
            this.keyReleasedDelayTimer.schedule(new KeyReleasedDelayTask(keyCode), 50L);
            return;
        }
        MIDletAccess ma = MIDletBridge.getMIDletAccess();
        if (ma == null || (da = ma.getDisplayAccess()) == null) {
            return;
        }
        da.keyReleased(keyCode);
        this.eventAlreadyConsumed = false;
    }

    public J2SEButton getButton(KeyEvent ev) {
        J2SEButton button = J2SEDeviceButtonsHelper.getButton(ev);
        if (button != null) {
            return button;
        }
        if (getInputMode() != 1) {
            Enumeration e = DeviceFactory.getDevice().getButtons().elements();
            while (e.hasMoreElements()) {
                J2SEButton button2 = (J2SEButton) e.nextElement();
                if (button2.isChar(ev.getKeyChar(), getInputMode())) {
                    return button2;
                }
            }
        }
        return (J2SEButton) DeviceFactory.getDevice().getButtons().get(10);
    }
}
