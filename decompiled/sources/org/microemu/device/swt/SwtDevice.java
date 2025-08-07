package org.microemu.device.swt;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.TextBox;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.impl.ui.CommandImplUI;
import org.microemu.device.swt.ui.SwtAlertUI;
import org.microemu.device.swt.ui.SwtCanvasUI;
import org.microemu.device.swt.ui.SwtFormUI;
import org.microemu.device.swt.ui.SwtListUI;
import org.microemu.device.swt.ui.SwtTextBoxUI;
import org.microemu.device.ui.AlertUI;
import org.microemu.device.ui.CanvasUI;
import org.microemu.device.ui.CommandUI;
import org.microemu.device.ui.EventDispatcher;
import org.microemu.device.ui.FormUI;
import org.microemu.device.ui.ListUI;
import org.microemu.device.ui.TextBoxUI;
import org.microemu.device.ui.UIFactory;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtDevice.class */
public class SwtDevice extends DeviceImpl {
    private UIFactory ui = new UIFactory() { // from class: org.microemu.device.swt.SwtDevice.1
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
            return new SwtAlertUI(alert);
        }

        @Override // org.microemu.device.ui.UIFactory
        public CanvasUI createCanvasUI(Canvas canvas) {
            return new SwtCanvasUI(canvas);
        }

        @Override // org.microemu.device.ui.UIFactory
        public CommandUI createCommandUI(Command command) {
            return new CommandImplUI(command);
        }

        @Override // org.microemu.device.ui.UIFactory
        public FormUI createFormUI(Form form) {
            return new SwtFormUI(form);
        }

        @Override // org.microemu.device.ui.UIFactory
        public ListUI createListUI(List list) {
            return new SwtListUI(list);
        }

        @Override // org.microemu.device.ui.UIFactory
        public TextBoxUI createTextBoxUI(TextBox textBox) {
            return new SwtTextBoxUI(textBox);
        }
    };

    @Override // org.microemu.device.Device
    public UIFactory getUIFactory() {
        return this.ui;
    }
}
