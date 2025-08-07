package org.microemu.app.ui.swt;

import org.eclipse.swt.widgets.Shell;
import org.microemu.app.ui.MessageListener;

/* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtErrorMessageDialogPanel.class */
public class SwtErrorMessageDialogPanel implements MessageListener {
    private Shell shell;

    public SwtErrorMessageDialogPanel(Shell shell) {
        this.shell = shell;
    }

    @Override // org.microemu.app.ui.MessageListener
    public void showMessage(int level, String title, String text, Throwable throwable) {
        int messageType;
        switch (level) {
            case 0:
                messageType = 1;
                break;
            case 1:
            default:
                messageType = 3;
                break;
            case 2:
                messageType = 2;
                break;
        }
        SwtMessageDialog.openMessageDialog(this.shell, title, text, messageType);
    }
}
