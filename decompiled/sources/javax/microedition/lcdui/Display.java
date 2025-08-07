package javax.microedition.lcdui;

import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;
import org.microemu.DisplayAccess;
import org.microemu.GameCanvasKeyAccess;
import org.microemu.MIDletBridge;
import org.microemu.device.DeviceFactory;
import org.microemu.device.ui.DisplayableUI;
import org.microemu.device.ui.EventDispatcher;
import org.microemu.device.ui.EventDispatcher.HideNotifyEvent;
import org.microemu.device.ui.EventDispatcher.PaintEvent;
import org.microemu.device.ui.EventDispatcher.PointerEvent;
import org.microemu.device.ui.EventDispatcher.ShowNotifyEvent;

/* loaded from: avacs.jar:javax/microedition/lcdui/Display.class */
public class Display {
    public static final int LIST_ELEMENT = 1;
    public static final int CHOICE_GROUP_ELEMENT = 2;
    public static final int ALERT = 3;
    public static final int COLOR_BACKGROUND = 0;
    public static final int COLOR_FOREGROUND = 1;
    public static final int COLOR_HIGHLIGHTED_BACKGROUND = 2;
    public static final int COLOR_HIGHLIGHTED_FOREGROUND = 3;
    public static final int COLOR_BORDER = 4;
    public static final int COLOR_HIGHLIGHTED_BORDER = 5;
    private DisplayAccessor accessor;
    private Displayable current = null;
    private final Timer timer = new Timer();
    private EventDispatcher eventDispatcher = DeviceFactory.getDevice().getUIFactory().createEventDispatcher(this);

    /* loaded from: avacs.jar:javax/microedition/lcdui/Display$GaugePaintTask.class */
    private final class GaugePaintTask implements Runnable {
        private GaugePaintTask() {
        }

        /* synthetic */ GaugePaintTask(Display display, GaugePaintTask gaugePaintTask) {
            this();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (Display.this.current != null) {
                if (Display.this.current instanceof Alert) {
                    Gauge gauge = ((Alert) Display.this.current).indicator;
                    if (gauge != null && gauge.hasIndefiniteRange() && gauge.getValue() == 2) {
                        gauge.updateIndefiniteFrame();
                        return;
                    }
                    return;
                }
                if (Display.this.current instanceof Form) {
                    Item[] items = ((Form) Display.this.current).items;
                    for (Item it : items) {
                        if (it != null && (it instanceof Gauge)) {
                            Gauge gauge2 = (Gauge) it;
                            if (gauge2.hasIndefiniteRange() && gauge2.getValue() == 2) {
                                gauge2.updateIndefiniteFrame();
                            }
                        }
                    }
                }
            }
        }
    }

    /* loaded from: avacs.jar:javax/microedition/lcdui/Display$TickerPaintTask.class */
    private final class TickerPaintTask implements Runnable {
        private TickerPaintTask() {
        }

        /* synthetic */ TickerPaintTask(Display display, TickerPaintTask tickerPaintTask) {
            this();
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v13 */
        /* JADX WARN: Type inference failed for: r0v8 */
        /* JADX WARN: Type inference failed for: r0v9, types: [java.lang.Throwable] */
        @Override // java.lang.Runnable
        public void run() {
            if (Display.this.current != null) {
                Ticker ticker = Display.this.current.getTicker();
                if (ticker != null) {
                    ?? r0 = ticker;
                    synchronized (r0) {
                        if (ticker.resetTextPosTo != -1) {
                            ticker.textPos = ticker.resetTextPosTo;
                            ticker.resetTextPosTo = -1;
                        }
                        ticker.textPos -= Ticker.PAINT_MOVE;
                        r0 = r0;
                        Display.this.repaint(Display.this.current, 0, 0, Display.this.current.getWidth(), Display.this.current.getHeight());
                    }
                }
            }
        }
    }

    /* loaded from: avacs.jar:javax/microedition/lcdui/Display$KeyEvent.class */
    private final class KeyEvent extends EventDispatcher.Event {
        static final short KEY_PRESSED = 0;
        static final short KEY_RELEASED = 1;
        static final short KEY_REPEATED = 2;
        private short type;
        private int keyCode;

        /* JADX WARN: Illegal instructions before constructor call */
        KeyEvent(short type, int keyCode) {
            EventDispatcher eventDispatcher = Display.this.eventDispatcher;
            eventDispatcher.getClass();
            super();
            this.type = type;
            this.keyCode = keyCode;
        }

        @Override // java.lang.Runnable
        public void run() {
            switch (this.type) {
                case 0:
                    if (Display.this.current != null) {
                        Display.this.current.keyPressed(this.keyCode);
                        break;
                    }
                    break;
                case 1:
                    if (Display.this.current != null) {
                        Display.this.current.keyReleased(this.keyCode);
                        break;
                    }
                    break;
                case 2:
                    if (Display.this.current != null) {
                        Display.this.current.keyRepeated(this.keyCode);
                        break;
                    }
                    break;
            }
        }
    }

    /* loaded from: avacs.jar:javax/microedition/lcdui/Display$DisplayAccessor.class */
    private class DisplayAccessor implements DisplayAccess {
        Display display;

        DisplayAccessor(Display d) {
            this.display = d;
        }

        @Override // org.microemu.DisplayAccess
        public void commandAction(Command c, Displayable d) {
            CommandListener listener;
            if (c.isRegularCommand()) {
                if (d == null || (listener = d.getCommandListener()) == null) {
                    return;
                }
                listener.commandAction(c, d);
                return;
            }
            Item item = c.getFocusedItem();
            ItemCommandListener listener2 = item.getItemCommandListener();
            if (listener2 == null) {
                return;
            }
            listener2.commandAction(c.getOriginalCommand(), item);
        }

        @Override // org.microemu.DisplayAccess
        public Display getDisplay() {
            return this.display;
        }

        private void processGameCanvasKeyEvent(GameCanvas c, int k, boolean press) {
            GameCanvasKeyAccess access = MIDletBridge.getGameCanvasKeyAccess(c);
            int gameCode = c.getGameAction(k);
            boolean suppress = false;
            if (gameCode != 0) {
                if (press) {
                    access.recordKeyPressed(c, gameCode);
                } else {
                    access.recordKeyReleased(c, gameCode);
                }
                suppress = access.suppressedKeyEvents(c);
            }
            if (!suppress) {
                if (press) {
                    Display.this.eventDispatcher.put((EventDispatcher.Event) Display.this.new KeyEvent((short) 0, k));
                } else {
                    Display.this.eventDispatcher.put((EventDispatcher.Event) Display.this.new KeyEvent((short) 1, k));
                }
            }
        }

        @Override // org.microemu.DisplayAccess
        public void keyPressed(int keyCode) {
            if (Display.this.current == null || !(Display.this.current instanceof GameCanvas)) {
                Display.this.eventDispatcher.put((EventDispatcher.Event) Display.this.new KeyEvent((short) 0, keyCode));
            } else {
                processGameCanvasKeyEvent((GameCanvas) Display.this.current, keyCode, true);
            }
        }

        @Override // org.microemu.DisplayAccess
        public void keyRepeated(int keyCode) {
            Display.this.eventDispatcher.put((EventDispatcher.Event) Display.this.new KeyEvent((short) 2, keyCode));
        }

        @Override // org.microemu.DisplayAccess
        public void keyReleased(int keyCode) {
            if (Display.this.current == null || !(Display.this.current instanceof GameCanvas)) {
                Display.this.eventDispatcher.put((EventDispatcher.Event) Display.this.new KeyEvent((short) 1, keyCode));
            } else {
                processGameCanvasKeyEvent((GameCanvas) Display.this.current, keyCode, false);
            }
        }

        @Override // org.microemu.DisplayAccess
        public void pointerPressed(final int x, final int y) {
            if (Display.this.current != null) {
                EventDispatcher eventDispatcher = Display.this.eventDispatcher;
                EventDispatcher eventDispatcher2 = Display.this.eventDispatcher;
                eventDispatcher2.getClass();
                eventDispatcher.put((EventDispatcher.Event) eventDispatcher2.new PointerEvent(new Runnable() { // from class: javax.microedition.lcdui.Display.DisplayAccessor.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Display.this.current.pointerPressed(x, y);
                    }
                }, (short) 0, x, y));
            }
        }

        @Override // org.microemu.DisplayAccess
        public void pointerReleased(final int x, final int y) {
            if (Display.this.current != null) {
                EventDispatcher eventDispatcher = Display.this.eventDispatcher;
                EventDispatcher eventDispatcher2 = Display.this.eventDispatcher;
                eventDispatcher2.getClass();
                eventDispatcher.put((EventDispatcher.Event) eventDispatcher2.new PointerEvent(new Runnable() { // from class: javax.microedition.lcdui.Display.DisplayAccessor.2
                    @Override // java.lang.Runnable
                    public void run() {
                        Display.this.current.pointerReleased(x, y);
                    }
                }, (short) 1, x, y));
            }
        }

        @Override // org.microemu.DisplayAccess
        public void pointerDragged(final int x, final int y) {
            if (Display.this.current != null) {
                EventDispatcher eventDispatcher = Display.this.eventDispatcher;
                EventDispatcher eventDispatcher2 = Display.this.eventDispatcher;
                eventDispatcher2.getClass();
                eventDispatcher.put((EventDispatcher.Event) eventDispatcher2.new PointerEvent(new Runnable() { // from class: javax.microedition.lcdui.Display.DisplayAccessor.3
                    @Override // java.lang.Runnable
                    public void run() {
                        Display.this.current.pointerDragged(x, y);
                    }
                }, (short) 2, x, y));
            }
        }

        @Override // org.microemu.DisplayAccess
        public void paint(Graphics g) {
            if (Display.this.current != null) {
                try {
                    Display.this.current.paint(g);
                } catch (Throwable th) {
                    th.printStackTrace();
                }
                g.translate(-g.getTranslateX(), -g.getTranslateY());
            }
        }

        @Override // org.microemu.DisplayAccess
        public Displayable getCurrent() {
            return getDisplay().getCurrent();
        }

        @Override // org.microemu.DisplayAccess
        public DisplayableUI getCurrentUI() {
            Displayable current = getCurrent();
            if (current == null) {
                return null;
            }
            return current.ui;
        }

        @Override // org.microemu.DisplayAccess
        public boolean isFullScreenMode() {
            Displayable current = getCurrent();
            if (current instanceof Canvas) {
                return ((Canvas) current).fullScreenMode;
            }
            return false;
        }

        @Override // org.microemu.DisplayAccess
        public void serviceRepaints() {
            getDisplay().serviceRepaints();
        }

        @Override // org.microemu.DisplayAccess
        public void setCurrent(Displayable d) {
            getDisplay().setCurrent(d);
        }

        @Override // org.microemu.DisplayAccess
        public void sizeChanged() {
            if (Display.this.current != null) {
                if (Display.this.current instanceof GameCanvas) {
                    Display.this.current.width = -1;
                    Display.this.current.height = -1;
                    GameCanvasKeyAccess access = MIDletBridge.getGameCanvasKeyAccess((GameCanvas) Display.this.current);
                    access.initBuffer();
                }
                Display.this.current.sizeChanged(Display.this);
            }
        }

        @Override // org.microemu.DisplayAccess
        public void repaint() {
            Displayable d = getCurrent();
            if (d != null) {
                getDisplay().repaint(d, 0, 0, d.getWidth(), d.getHeight());
            }
        }

        @Override // org.microemu.DisplayAccess
        public void clean() {
            if (Display.this.current != null) {
                Display.this.current.hideNotify();
            }
            Display.this.eventDispatcher.cancel();
            Display.this.timer.cancel();
        }
    }

    /* loaded from: avacs.jar:javax/microedition/lcdui/Display$AlertTimeout.class */
    private class AlertTimeout implements Runnable {
        int time;

        AlertTimeout(int time) {
            this.time = time;
        }

        @Override // java.lang.Runnable
        public void run() throws InterruptedException {
            try {
                Thread.sleep(this.time);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            Displayable d = Display.this.current;
            if (d != null && (d instanceof Alert)) {
                Alert alert = (Alert) d;
                if (alert.time != -2) {
                    alert.getCommandListener().commandAction((Command) alert.getCommands().get(0), alert);
                }
            }
        }
    }

    /* loaded from: avacs.jar:javax/microedition/lcdui/Display$RunnableWrapper.class */
    private final class RunnableWrapper extends TimerTask {
        private final Runnable runnable;

        RunnableWrapper(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() {
            Display.this.eventDispatcher.put(this.runnable);
        }
    }

    Display() {
        this.accessor = null;
        this.accessor = new DisplayAccessor(this);
        this.timer.scheduleAtFixedRate(new RunnableWrapper(new TickerPaintTask(this, null)), 0L, Ticker.PAINT_TIMEOUT);
        this.timer.scheduleAtFixedRate(new RunnableWrapper(new GaugePaintTask(this, null)), 0L, Ticker.PAINT_TIMEOUT);
    }

    public void callSerially(Runnable runnable) {
        this.eventDispatcher.put(runnable);
    }

    public int numAlphaLevels() {
        return DeviceFactory.getDevice().getDeviceDisplay().numAlphaLevels();
    }

    public int numColors() {
        return DeviceFactory.getDevice().getDeviceDisplay().numColors();
    }

    public boolean flashBacklight(int duration) {
        return false;
    }

    public static Display getDisplay(MIDlet m) {
        Display result;
        if (MIDletBridge.getMIDletAccess(m).getDisplayAccess() == null) {
            result = new Display();
            MIDletBridge.getMIDletAccess(m).setDisplayAccess(result.accessor);
        } else {
            result = MIDletBridge.getMIDletAccess(m).getDisplayAccess().getDisplay();
        }
        return result;
    }

    public int getColor(int colorSpecifier) {
        switch (colorSpecifier) {
            case 0:
            case 3:
            case 5:
                return 16777215;
            case 1:
            case 2:
            case 4:
            default:
                return 0;
        }
    }

    public int getBorderStyle(boolean highlighted) {
        return highlighted ? 1 : 0;
    }

    public int getBestImageWidth(int imageType) {
        return 0;
    }

    public int getBestImageHeight(int imageType) {
        return 0;
    }

    public Displayable getCurrent() {
        return this.current;
    }

    public boolean isColor() {
        return DeviceFactory.getDevice().getDeviceDisplay().isColor();
    }

    public void setCurrent(final Displayable nextDisplayable) {
        if (nextDisplayable != null) {
            EventDispatcher eventDispatcher = this.eventDispatcher;
            EventDispatcher eventDispatcher2 = this.eventDispatcher;
            eventDispatcher2.getClass();
            eventDispatcher.put((EventDispatcher.Event) eventDispatcher2.new ShowNotifyEvent(new Runnable() { // from class: javax.microedition.lcdui.Display.1
                @Override // java.lang.Runnable
                public void run() {
                    if (Display.this.current != null) {
                        EventDispatcher eventDispatcher3 = Display.this.eventDispatcher;
                        EventDispatcher eventDispatcher4 = Display.this.eventDispatcher;
                        eventDispatcher4.getClass();
                        eventDispatcher3.put((EventDispatcher.Event) eventDispatcher4.new HideNotifyEvent(new Runnable() { // from class: javax.microedition.lcdui.Display.1.1
                            private Displayable displayable;

                            {
                                this.displayable = Display.this.current;
                            }

                            @Override // java.lang.Runnable
                            public void run() {
                                this.displayable.hideNotify(Display.this);
                            }
                        }));
                    }
                    if (nextDisplayable instanceof Alert) {
                        Display.this.setCurrent((Alert) nextDisplayable, Display.this.current);
                        return;
                    }
                    nextDisplayable.showNotify(Display.this);
                    Display.this.current = nextDisplayable;
                    Display.this.setScrollUp(false);
                    Display.this.setScrollDown(false);
                    nextDisplayable.repaint();
                }
            }));
        }
    }

    public void setCurrent(Alert alert, Displayable nextDisplayable) {
        Alert.nextDisplayable = nextDisplayable;
        this.current = alert;
        this.current.showNotify(this);
        this.current.repaint();
        if (alert.getTimeout() != -2) {
            AlertTimeout at = new AlertTimeout(alert.getTimeout());
            Thread t = new Thread(at);
            t.start();
        }
    }

    public void setCurrentItem(Item item) {
    }

    public boolean vibrate(int duration) {
        return DeviceFactory.getDevice().vibrate(duration);
    }

    void clearAlert() {
        setCurrent(Alert.nextDisplayable);
    }

    static int getGameAction(int keyCode) {
        return DeviceFactory.getDevice().getInputMethod().getGameAction(keyCode);
    }

    static int getKeyCode(int gameAction) {
        return DeviceFactory.getDevice().getInputMethod().getKeyCode(gameAction);
    }

    static String getKeyName(int keyCode) throws IllegalArgumentException {
        return DeviceFactory.getDevice().getInputMethod().getKeyName(keyCode);
    }

    boolean isShown(Displayable d) {
        if (this.current == null || this.current != d) {
            return false;
        }
        return true;
    }

    void repaint(Displayable d, int x, int y, int width, int height) {
        if (this.current == d) {
            EventDispatcher eventDispatcher = this.eventDispatcher;
            EventDispatcher eventDispatcher2 = this.eventDispatcher;
            eventDispatcher2.getClass();
            eventDispatcher.put((EventDispatcher.Event) eventDispatcher2.new PaintEvent(x, y, width, height));
        }
    }

    void serviceRepaints() {
        if (EventDispatcher.EVENT_DISPATCHER_NAME.equals(Thread.currentThread().getName())) {
            DeviceFactory.getDevice().getDeviceDisplay().repaint(0, 0, this.current.getWidth(), this.current.getHeight());
        } else {
            this.eventDispatcher.serviceRepaints();
        }
    }

    void setScrollDown(boolean state) {
        DeviceFactory.getDevice().getDeviceDisplay().setScrollDown(state);
    }

    void setScrollUp(boolean state) {
        DeviceFactory.getDevice().getDeviceDisplay().setScrollUp(state);
    }
}
