package javax.microedition.lcdui;

import java.util.Calendar;
import java.util.Date;

/* compiled from: DateField.java */
/* loaded from: avacs.jar:javax/microedition/lcdui/DateCanvas.class */
class DateCanvas extends Canvas {
    Calendar cal = Calendar.getInstance();
    private int month;
    private int day;
    private int year;
    private int selected;

    public Date getTime() {
        this.cal.set(1, this.year);
        this.cal.set(2, this.month);
        this.cal.set(5, this.day);
        return this.cal.getTime();
    }

    public void setTime(Date time) {
        this.cal.setTime(time);
        this.year = this.cal.get(1);
        this.month = this.cal.get(2);
        this.day = this.cal.get(5);
        repaint();
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    public void paint(Graphics g) {
        int colorR;
        int colorT;
        int colorR2;
        int colorT2;
        int colorR3;
        int colorT3;
        int w = getWidth();
        int h = getHeight();
        g.setColor(16777215);
        g.fillRect(0, 0, w, h);
        Font font = Font.getFont(32, 1, 0);
        String dayStr = Integer.toString(this.day);
        if (this.day < 10) {
            dayStr = "0" + dayStr;
        }
        String monthStr = Integer.toString(this.month + 1);
        if (this.month + 1 < 10) {
            monthStr = "0" + monthStr;
        }
        String yearStr = Integer.toString(this.year);
        int y = (h - font.getHeight()) >>> 1;
        int dayW = font.stringWidth(dayStr);
        int monthW = font.stringWidth(monthStr);
        int yearW = font.stringWidth(yearStr);
        int delimiterW = font.stringWidth("/");
        int stringWidth = dayW + monthW + yearW + (delimiterW << 1);
        int offset = (w - stringWidth) >>> 1;
        int del1Off = offset + dayW;
        int mOff = del1Off + delimiterW;
        int del2Off = mOff + monthW;
        int yOff = del2Off + delimiterW;
        g.setColor(0);
        g.setFont(font);
        g.drawString("/", del1Off, y, 20);
        g.drawString("/", del2Off, y, 20);
        if (this.selected == 0) {
            colorR = 0;
            colorT = 16777215;
        } else {
            colorR = 16777215;
            colorT = 0;
        }
        g.setColor(colorR);
        g.fillRect(offset, y, dayW, font.getHeight());
        g.setColor(colorT);
        g.drawString(dayStr, offset, y, 20);
        if (this.selected == 1) {
            colorR2 = 0;
            colorT2 = 16777215;
        } else {
            colorR2 = 16777215;
            colorT2 = 0;
        }
        g.setColor(colorR2);
        g.fillRect(mOff, y, monthW, font.getHeight());
        g.setColor(colorT2);
        g.drawString(monthStr, mOff, y, 20);
        if (this.selected == 2) {
            colorR3 = 0;
            colorT3 = 16777215;
        } else {
            colorR3 = 16777215;
            colorT3 = 0;
        }
        g.setColor(colorR3);
        g.fillRect(yOff, y, yearW, font.getHeight());
        g.setColor(colorT3);
        g.drawString(yearStr, yOff, y, 20);
    }

    @Override // javax.microedition.lcdui.Canvas, javax.microedition.lcdui.Displayable
    public synchronized void keyPressed(int keycode) {
        int k = getGameAction(keycode);
        if (k == 2 && this.selected > 0) {
            this.selected--;
            repaint();
            return;
        }
        if (k == 5 && this.selected < 2) {
            this.selected++;
            repaint();
            return;
        }
        if (k == 1) {
            Calendar cal = Calendar.getInstance();
            switch (this.selected) {
                case 0:
                    cal.set(1, this.year);
                    cal.set(2, this.month);
                    cal.set(5, this.day);
                    cal.set(11, 1);
                    cal.setTime(cal.getTime());
                    cal.add(5, 1);
                    if (cal.get(2) == this.month) {
                        this.day++;
                        break;
                    } else {
                        this.day = 1;
                        break;
                    }
                case 1:
                    if (this.month == 11) {
                        this.month = 0;
                    } else {
                        this.month++;
                    }
                    cal.set(1, this.year);
                    cal.set(2, this.month);
                    cal.set(5, 28);
                    cal.set(11, 1);
                    cal.setTime(cal.getTime());
                    cal.add(5, 4);
                    int daysInMonth = 28 + (4 - cal.get(5));
                    if (this.day > daysInMonth) {
                        this.day = daysInMonth;
                        break;
                    }
                    break;
                case 2:
                    if (this.year < 5000) {
                        this.year++;
                        if (this.day == 29 && this.month == 1) {
                            this.day = 28;
                            break;
                        }
                    }
                    break;
            }
            repaint();
            return;
        }
        if (k == 6) {
            Calendar cal2 = Calendar.getInstance();
            switch (this.selected) {
                case 0:
                    if (this.day > 1) {
                        this.day--;
                        break;
                    } else {
                        cal2.set(1, this.year);
                        cal2.set(2, this.month);
                        cal2.set(5, 28);
                        cal2.set(11, 1);
                        cal2.setTime(cal2.getTime());
                        cal2.add(5, 4);
                        this.day = 28 + (4 - cal2.get(5));
                        break;
                    }
                case 1:
                    if (this.month == 0) {
                        this.month = 11;
                    } else {
                        this.month--;
                    }
                    cal2.set(1, this.year);
                    cal2.set(2, this.month);
                    cal2.set(5, 28);
                    cal2.set(11, 1);
                    cal2.setTime(cal2.getTime());
                    cal2.add(5, 1);
                    int daysInMonth2 = 28 + (4 - cal2.get(5));
                    if (this.day > daysInMonth2) {
                        this.day = daysInMonth2;
                        break;
                    }
                    break;
                case 2:
                    if (this.year > 1000) {
                        this.year--;
                        if (this.day == 29 && this.month == 1) {
                            this.day = 28;
                            break;
                        }
                    }
                    break;
            }
            repaint();
        }
    }
}
