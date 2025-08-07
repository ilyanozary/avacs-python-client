package javax.microedition.lcdui;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/* loaded from: avacs.jar:javax/microedition/lcdui/DateField.class */
public class DateField extends Item {
    public static final int DATE = 1;
    public static final int TIME = 2;
    public static final int DATE_TIME = 3;
    Date date;
    Date time;
    String label;
    int mode;
    ChoiceGroup dateTime;
    DateCanvas dateCanvas;
    TimeCanvas timeCanvas;
    static Command saveCommand = new Command("Save", 4, 0);
    static Command backCommand = new Command("Back", 2, 0);
    CommandListener dateTimeListener;

    public DateField(String label, int mode) {
        this(label, mode, null);
    }

    public DateField(String label, int mode, TimeZone timeZone) {
        super(null);
        this.dateTimeListener = new CommandListener() { // from class: javax.microedition.lcdui.DateField.1
            @Override // javax.microedition.lcdui.CommandListener
            public void commandAction(Command c, Displayable d) {
                if (c == DateField.backCommand) {
                    DateField.this.getOwner().currentDisplay.setCurrent(DateField.this.owner);
                    return;
                }
                if (c == DateField.saveCommand) {
                    Calendar from = Calendar.getInstance();
                    Calendar to = Calendar.getInstance();
                    to.setTime(new Date(0L));
                    if (d == DateField.this.dateCanvas) {
                        from.setTime(DateField.this.dateCanvas.getTime());
                        to.set(5, from.get(5));
                        to.set(2, from.get(2));
                        to.set(1, from.get(1));
                        DateField.this.date = to.getTime();
                    } else {
                        from.setTime(DateField.this.timeCanvas.getTime());
                        to.set(11, from.get(11));
                        to.set(12, from.get(12));
                        DateField.this.time = to.getTime();
                    }
                    DateField.this.updateDateTimeString();
                    DateField.this.getOwner().currentDisplay.setCurrent(DateField.this.owner);
                }
            }
        };
        this.label = label;
        setInputMode(mode);
        this.dateCanvas = new DateCanvas();
        this.dateCanvas.addCommand(saveCommand);
        this.dateCanvas.addCommand(backCommand);
        this.dateCanvas.setCommandListener(this.dateTimeListener);
        this.timeCanvas = new TimeCanvas();
        this.timeCanvas.addCommand(saveCommand);
        this.timeCanvas.addCommand(backCommand);
        this.timeCanvas.setCommandListener(this.dateTimeListener);
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
        updateDateTimeString();
    }

    public int getInputMode() {
        return this.mode;
    }

    public void setInputMode(int mode) {
        if (mode < 1 || mode > 3) {
            throw new IllegalArgumentException();
        }
        this.mode = mode;
        this.dateTime = new ChoiceGroup(this.label, 3, false);
        if ((mode & 1) != 0) {
            this.dateTime.append("[date]", null);
        }
        if ((mode & 2) != 0) {
            this.dateTime.append("[time]", null);
        }
    }

    @Override // javax.microedition.lcdui.Item
    boolean isFocusable() {
        return true;
    }

    @Override // javax.microedition.lcdui.Item
    int getHeight() {
        return super.getHeight() + this.dateTime.getHeight();
    }

    @Override // javax.microedition.lcdui.Item
    int paint(Graphics g) {
        super.paintContent(g);
        g.translate(0, super.getHeight());
        this.dateTime.paint(g);
        g.translate(0, -super.getHeight());
        return getHeight();
    }

    @Override // javax.microedition.lcdui.Item
    void setFocus(boolean state) {
        super.setFocus(state);
        this.dateTime.setFocus(state);
    }

    @Override // javax.microedition.lcdui.Item
    boolean select() {
        this.dateTime.select();
        if (this.dateTime.getSelectedIndex() == 0 && (this.mode & 1) != 0) {
            if (this.date != null) {
                this.dateCanvas.setTime(this.date);
            } else {
                this.dateCanvas.setTime(new Date());
            }
            getOwner().currentDisplay.setCurrent(this.dateCanvas);
            return true;
        }
        if (this.time != null) {
            this.timeCanvas.setTime(this.time);
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(1, 1970);
            cal.set(2, 0);
            cal.set(5, 1);
            cal.set(11, 12);
            cal.set(12, 0);
            cal.set(13, 0);
            this.timeCanvas.setTime(cal.getTime());
        }
        getOwner().currentDisplay.setCurrent(this.timeCanvas);
        return true;
    }

    @Override // javax.microedition.lcdui.Item
    int traverse(int gameKeyCode, int top, int bottom, boolean action) {
        return this.dateTime.traverse(gameKeyCode, top, bottom, action);
    }

    private String formatDate() {
        if (this.date == null) {
            return "[date]";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        int day = cal.get(5);
        int month = cal.get(2) + 1;
        int year = cal.get(1);
        return String.valueOf(Integer.toString(day)) + "-" + month + "-" + year;
    }

    private String formatTime() {
        if (this.time == null) {
            return "[time]";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.time);
        int hours = cal.get(11);
        int minutes = cal.get(12);
        return String.valueOf(Integer.toString(hours)) + ":" + (minutes < 10 ? "0" : "") + minutes;
    }

    void updateDateTimeString() {
        if ((this.mode & 1) != 0) {
            this.dateTime.set(0, formatDate(), null);
        }
        if ((this.mode & 2) != 0) {
            this.dateTime.set((this.mode & 1) != 0 ? 1 : 0, formatTime(), null);
        }
    }
}
