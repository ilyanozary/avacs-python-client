package org.microemu.device.j2se;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.impl.ui.CommandImplUI;
import org.microemu.device.j2se.ui.J2SEAlertUI;
import org.microemu.device.j2se.ui.J2SECanvasUI;
import org.microemu.device.j2se.ui.J2SEFormUI;
import org.microemu.device.j2se.ui.J2SEListUI;
import org.microemu.device.j2se.ui.J2SETextBoxUI;
import org.microemu.device.ui.AlertUI;
import org.microemu.device.ui.CanvasUI;
import org.microemu.device.ui.CommandUI;
import org.microemu.device.ui.EventDispatcher;
import org.microemu.device.ui.FormUI;
import org.microemu.device.ui.ListUI;
import org.microemu.device.ui.TextBoxUI;
import org.microemu.device.ui.UIFactory;

/* loaded from: avacs.jar:org/microemu/device/j2se/J2SEDevice.class */
public class J2SEDevice extends DeviceImpl {
    private UIFactory ui = new UIFactory() { // from class: org.microemu.device.j2se.J2SEDevice.1
        @Override // org.microemu.device.ui.UIFactory
        public EventDispatcher createEventDispatcher(Display display) {
            EventDispatcher eventDispatcher = new EventDispatcher();
            Thread thread = new Thread(eventDispatcher, EventDispatcher.EVENT_DISPATCHER_NAME);
            thread.setDaemon(true);
            thread.start();
            return eventDispatcher;
        }

        @Override // org.microemu.device.ui.UIFactory
        public AlertUI createAlertUI(Alert alert) {
            return new J2SEAlertUI(alert);
        }

        @Override // org.microemu.device.ui.UIFactory
        public CanvasUI createCanvasUI(Canvas canvas) {
            return new J2SECanvasUI(canvas);
        }

        @Override // org.microemu.device.ui.UIFactory
        public CommandUI createCommandUI(Command command) {
            return new CommandImplUI(command);
        }

        @Override // org.microemu.device.ui.UIFactory
        public FormUI createFormUI(Form form) {
            return new J2SEFormUI(form);
        }

        @Override // org.microemu.device.ui.UIFactory
        public ListUI createListUI(List list) {
            return new J2SEListUI(list);
        }

        @Override // org.microemu.device.ui.UIFactory
        public TextBoxUI createTextBoxUI(TextBox textBox) {
            return new J2SETextBoxUI(textBox);
        }
    };

    @Override // org.microemu.device.Device
    public UIFactory getUIFactory() {
        return this.ui;
    }
}
