package org.microemu;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import org.microemu.device.ui.DisplayableUI;

/* loaded from: avacs.jar:org/microemu/DisplayAccess.class */
public interface DisplayAccess {
    void commandAction(Command command, Displayable displayable);

    Display getDisplay();

    void keyPressed(int i);

    void keyRepeated(int i);

    void keyReleased(int i);

    void pointerPressed(int i, int i2);

    void pointerReleased(int i, int i2);

    void pointerDragged(int i, int i2);

    void paint(Graphics graphics);

    boolean isFullScreenMode();

    void serviceRepaints();

    Displayable getCurrent();

    DisplayableUI getCurrentUI();

    void setCurrent(Displayable displayable);

    void sizeChanged();

    void repaint();

    void clean();
}
