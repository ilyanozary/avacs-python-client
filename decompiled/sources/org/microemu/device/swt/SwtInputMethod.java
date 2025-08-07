package org.microemu.device.swt;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import org.eclipse.swt.events.KeyEvent;
import org.microemu.DisplayAccess;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.device.DeviceFactory;
import org.microemu.device.InputMethodEvent;
import org.microemu.device.impl.InputMethodImpl;
import org.microemu.device.impl.SoftButton;
import org.microemu.device.impl.ui.CommandManager;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtInputMethod.class */
public class SwtInputMethod extends InputMethodImpl {
    private Timer keyRepeatTimer = new Timer();
    private int repeatModeKeyCode = Integer.MIN_VALUE;
    private boolean clearRepeatFlag = false;

    /* loaded from: avacs.jar:org/microemu/device/swt/SwtInputMethod$KeyRepeatTask.class */
    private class KeyRepeatTask extends TimerTask {
        private KeyRepeatTask() {
        }

        /* synthetic */ KeyRepeatTask(SwtInputMethod swtInputMethod, KeyRepeatTask keyRepeatTask) {
            this();
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            MIDletAccess ma;
            DisplayAccess da;
            if (SwtInputMethod.this.repeatModeKeyCode != Integer.MIN_VALUE && (ma = MIDletBridge.getMIDletAccess()) != null && (da = ma.getDisplayAccess()) != null && SwtInputMethod.this.clearRepeatFlag) {
                da.keyReleased(SwtInputMethod.this.repeatModeKeyCode);
                SwtInputMethod.this.repeatModeKeyCode = Integer.MIN_VALUE;
            }
        }
    }

    @Override // org.microemu.device.InputMethod
    public int getGameAction(int keyCode) {
        switch (keyCode) {
            case 13:
                return 8;
            case 16777217:
                return 1;
            case 16777218:
                return 6;
            case 16777219:
                return 2;
            case 16777220:
                return 5;
            default:
                return 0;
        }
    }

    @Override // org.microemu.device.InputMethod
    public int getKeyCode(int gameAction) {
        switch (gameAction) {
            case 1:
                return 16777217;
            case 2:
                return 16777219;
            case 3:
            case 4:
            case 7:
            default:
                throw new IllegalArgumentException();
            case 5:
                return 16777220;
            case 6:
                return 16777218;
            case 8:
                return 13;
        }
    }

    @Override // org.microemu.device.InputMethod
    public String getKeyName(int keyCode) throws IllegalArgumentException {
        Iterator it = DeviceFactory.getDevice().getButtons().iterator();
        while (it.hasNext()) {
            SwtButton button = (SwtButton) it.next();
            if (button.getKeyCode() == keyCode) {
                return button.getName();
            }
        }
        Iterator it2 = DeviceFactory.getDevice().getButtons().iterator();
        while (it2.hasNext()) {
            SwtButton button2 = (SwtButton) it2.next();
            if (button2.getKeyboardKey() == keyCode) {
                return button2.getName();
            }
        }
        throw new IllegalArgumentException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v17, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v45 */
    /* JADX WARN: Type inference failed for: r0v46, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v50 */
    /* JADX WARN: Type inference failed for: r0v69 */
    /* JADX WARN: Type inference failed for: r0v70, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v74 */
    private boolean commonKeyPressed(KeyEvent ev) {
        int midpKeyCode;
        int keyCode = ev.keyCode;
        if (this.inputMethodListener == null) {
            switch (ev.keyCode) {
                case 8:
                    return true;
                default:
                    switch (ev.character) {
                        case Canvas.KEY_POUND /* 35 */:
                            midpKeyCode = 35;
                            break;
                        case Canvas.KEY_STAR /* 42 */:
                            midpKeyCode = 42;
                            break;
                        default:
                            midpKeyCode = keyCode;
                            break;
                    }
                    MIDletBridge.getMIDletAccess().getDisplayAccess().keyPressed(midpKeyCode);
                    return true;
            }
        }
        if (getGameAction(keyCode) == 1 || getGameAction(keyCode) == 6) {
            MIDletBridge.getMIDletAccess().getDisplayAccess().keyPressed(keyCode);
            return true;
        }
        int caret = this.inputMethodListener.getCaretPosition();
        if (getGameAction(keyCode) == 2 || getGameAction(keyCode) == 5) {
            ?? r0 = this;
            synchronized (r0) {
                if (getGameAction(keyCode) == 2 && caret > 0) {
                    caret--;
                }
                if (getGameAction(keyCode) == 5 && caret < this.inputMethodListener.getText().length()) {
                    caret++;
                }
                this.lastButton = null;
                this.lastButtonCharIndex = -1;
                r0 = r0;
                InputMethodEvent event = new InputMethodEvent(1, caret, this.inputMethodListener.getText());
                this.inputMethodListener.caretPositionChanged(event);
                return true;
            }
        }
        if (keyCode == 8) {
            String tmp = this.inputMethodListener.getText();
            ?? r02 = this;
            synchronized (r02) {
                if (this.lastButton != null) {
                    caret++;
                    this.lastButton = null;
                    this.lastButtonCharIndex = -1;
                }
                if (caret > 0) {
                    caret--;
                    tmp = "";
                    if (caret > 0) {
                        tmp = String.valueOf(tmp) + this.inputMethodListener.getText().substring(0, caret);
                    }
                    if (caret < this.inputMethodListener.getText().length() - 1) {
                        tmp = String.valueOf(tmp) + this.inputMethodListener.getText().substring(caret + 1);
                    }
                }
                r02 = r02;
                if (!validate(tmp, this.inputMethodListener.getConstraints())) {
                    return true;
                }
                InputMethodEvent event2 = new InputMethodEvent(2, caret, tmp);
                this.inputMethodListener.inputMethodTextChanged(event2);
                InputMethodEvent event3 = new InputMethodEvent(1, caret, tmp);
                this.inputMethodListener.caretPositionChanged(event3);
                return true;
            }
        }
        if (keyCode == 127) {
            String tmp2 = this.inputMethodListener.getText();
            ?? r03 = this;
            synchronized (r03) {
                if (this.lastButton != null) {
                    this.lastButton = null;
                    this.lastButtonCharIndex = -1;
                }
                if (caret != this.inputMethodListener.getText().length()) {
                    tmp2 = String.valueOf(this.inputMethodListener.getText().substring(0, caret)) + this.inputMethodListener.getText().substring(caret + 1);
                }
                r03 = r03;
                if (!validate(tmp2, this.inputMethodListener.getConstraints())) {
                    return true;
                }
                InputMethodEvent event4 = new InputMethodEvent(2, caret, tmp2);
                this.inputMethodListener.inputMethodTextChanged(event4);
                InputMethodEvent event5 = new InputMethodEvent(1, caret, tmp2);
                this.inputMethodListener.caretPositionChanged(event5);
                return true;
            }
        }
        if (keyCode == 131072 || keyCode == 262144 || keyCode == 65536) {
            return true;
        }
        return false;
    }

    public void keyPressed(KeyEvent ev) {
        Command cmd;
        DisplayAccess da;
        DisplayAccess da2;
        if (DeviceFactory.getDevice().hasRepeatEvents() && this.inputMethodListener == null) {
            this.clearRepeatFlag = false;
            if (this.repeatModeKeyCode == ev.keyCode) {
                MIDletAccess ma = MIDletBridge.getMIDletAccess();
                if (ma == null || (da2 = ma.getDisplayAccess()) == null) {
                    return;
                }
                da2.keyRepeated(ev.keyCode);
                return;
            }
            this.repeatModeKeyCode = ev.keyCode;
        }
        boolean rawSoftKeys = DeviceFactory.getDevice().getDeviceDisplay().isFullScreenMode();
        Object button = getButton(ev);
        if (button != null && (button instanceof SoftButton) && !rawSoftKeys && (cmd = ((SoftButton) button).getCommand()) != null) {
            MIDletAccess ma2 = MIDletBridge.getMIDletAccess();
            if (ma2 == null || (da = ma2.getDisplayAccess()) == null) {
                return;
            }
            if (cmd.equals(CommandManager.CMD_MENU)) {
                CommandManager.getInstance().commandAction(cmd);
                return;
            } else {
                da.commandAction(cmd, da.getCurrent());
                return;
            }
        }
        if (!commonKeyPressed(ev) && this.inputMethodListener.getText().length() < this.maxSize && (ev.keyCode & 16777216) == 0) {
            insertText(new Character(ev.character).toString());
        }
    }

    public void keyReleased(KeyEvent ev) {
        DisplayAccess da;
        if (DeviceFactory.getDevice().hasRepeatEvents() && this.inputMethodListener == null) {
            this.clearRepeatFlag = true;
            this.keyRepeatTimer.schedule(new KeyRepeatTask(this, null), 50L);
            return;
        }
        MIDletAccess ma = MIDletBridge.getMIDletAccess();
        if (ma == null || (da = ma.getDisplayAccess()) == null) {
            return;
        }
        da.keyReleased(ev.keyCode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r0v23, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v33 */
    public void mousePressed(KeyEvent ev) {
        if (!commonKeyPressed(ev) && this.inputMethodListener.getText().length() < this.maxSize) {
            Enumeration e = DeviceFactory.getDevice().getButtons().elements();
            while (e.hasMoreElements()) {
                SwtButton button = (SwtButton) e.nextElement();
                if (ev.keyCode == button.getKeyCode()) {
                    int caret = this.inputMethodListener.getCaretPosition();
                    String tmp = this.inputMethodListener.getText();
                    ?? r0 = this;
                    synchronized (r0) {
                        this.lastButtonCharIndex++;
                        char[] buttonChars = filterConstraints(filterInputMode(button.getChars(getInputMode())));
                        if (buttonChars.length > 0) {
                            if (this.lastButtonCharIndex == buttonChars.length) {
                                if (buttonChars.length == 1) {
                                    if (this.lastButton != null) {
                                        caret++;
                                    }
                                    this.lastButton = null;
                                } else {
                                    this.lastButtonCharIndex = 0;
                                }
                            }
                            if (this.lastButton != button) {
                                if (this.lastButton != null) {
                                    caret++;
                                }
                                String tmp2 = "";
                                if (caret > 0) {
                                    tmp2 = String.valueOf(tmp2) + this.inputMethodListener.getText().substring(0, caret);
                                }
                                tmp = String.valueOf(tmp2) + buttonChars[0];
                                if (caret < this.inputMethodListener.getText().length()) {
                                    tmp = String.valueOf(tmp) + this.inputMethodListener.getText().substring(caret);
                                }
                                this.lastButton = button;
                                this.lastButtonCharIndex = 0;
                            } else {
                                String tmp3 = "";
                                if (caret > 0) {
                                    tmp3 = String.valueOf(tmp3) + this.inputMethodListener.getText().substring(0, caret);
                                }
                                tmp = String.valueOf(tmp3) + buttonChars[this.lastButtonCharIndex];
                                if (caret < this.inputMethodListener.getText().length() - 1) {
                                    tmp = String.valueOf(tmp) + this.inputMethodListener.getText().substring(caret + 1);
                                }
                                this.lastButton = button;
                            }
                        } else {
                            this.lastButton = null;
                            this.lastButtonCharIndex = -1;
                        }
                        this.resetKey = false;
                        notify();
                        r0 = r0;
                        if (!validate(tmp, this.inputMethodListener.getConstraints())) {
                            return;
                        }
                        InputMethodEvent event = new InputMethodEvent(2, caret, tmp);
                        this.inputMethodListener.inputMethodTextChanged(event);
                        return;
                    }
                }
            }
        }
    }

    public void mouseReleased(int keyCode) {
        DisplayAccess da;
        MIDletAccess ma = MIDletBridge.getMIDletAccess();
        if (ma == null || (da = ma.getDisplayAccess()) == null) {
            return;
        }
        da.keyReleased(keyCode);
    }

    public SwtButton getButton(KeyEvent ev) {
        Enumeration e = DeviceFactory.getDevice().getButtons().elements();
        while (e.hasMoreElements()) {
            SwtButton button = (SwtButton) e.nextElement();
            if (ev.keyCode == button.getKeyCode()) {
                return button;
            }
            if (button.isChar(ev.character, getInputMode())) {
                return button;
            }
        }
        return null;
    }
}
