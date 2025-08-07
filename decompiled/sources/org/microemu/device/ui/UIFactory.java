package org.microemu.device.ui;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;

/* loaded from: avacs.jar:org/microemu/device/ui/UIFactory.class */
public interface UIFactory {
    EventDispatcher createEventDispatcher(Display display);

    AlertUI createAlertUI(Alert alert);

    CanvasUI createCanvasUI(Canvas canvas);

    CommandUI createCommandUI(Command command);

    FormUI createFormUI(Form form);

    ListUI createListUI(List list);

    TextBoxUI createTextBoxUI(TextBox textBox);
}
