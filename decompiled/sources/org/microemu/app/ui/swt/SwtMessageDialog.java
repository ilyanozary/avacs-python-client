package org.microemu.app.ui.swt;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtMessageDialog.class */
public class SwtMessageDialog extends SwtDialog {
    public static final int ERROR = 1;
    public static final int WARNING = 2;
    public static final int INFORMATION = 3;
    public static final int QUESTION = 4;
    private String title;
    private String message;
    private String[] buttonLabels;
    private int defaultIndex;

    public SwtMessageDialog(Shell parentShell, String title, String message, int imageType, String[] buttonLabels, int defaultIndex) {
        super(parentShell);
        this.title = title;
        this.message = message;
        this.buttonLabels = buttonLabels;
        this.defaultIndex = defaultIndex;
    }

    public static void openMessageDialog(Shell parent, String title, String message, int messageType) {
        SwtMessageDialog dialog = new SwtMessageDialog(parent, title, message, messageType, new String[]{"OK"}, 0);
        dialog.open();
    }

    public static boolean openQuestion(Shell parent, String title, String message) {
        SwtMessageDialog dialog = new SwtMessageDialog(parent, title, message, 4, new String[]{"Yes", "No"}, 0);
        return dialog.open() == 0;
    }

    @Override // org.microemu.app.ui.swt.SwtDialog
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        if (this.title != null) {
            shell.setText(this.title);
        }
    }

    @Override // org.microemu.app.ui.swt.SwtDialog
    protected Control createDialogArea(Composite composite) {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        composite.setLayout(gridLayout);
        Label lbMessage = new Label(composite, 0);
        lbMessage.setText(this.message);
        lbMessage.setLayoutData(new GridData(1808));
        return composite;
    }

    @Override // org.microemu.app.ui.swt.SwtDialog
    protected Control createButtonBar(Composite parent) {
        Shell shell;
        Composite composite = new Composite(parent, 0);
        composite.setLayout(new GridLayout(this.buttonLabels.length, false));
        composite.setLayoutData(new GridData(64));
        composite.setFont(parent.getFont());
        for (int i = 0; i < this.buttonLabels.length; i++) {
            Button button = new Button(composite, 8);
            button.setText(this.buttonLabels[i]);
            button.setData(new Integer(i));
            button.addSelectionListener(new SelectionAdapter() { // from class: org.microemu.app.ui.swt.SwtMessageDialog.1
                public void widgetSelected(SelectionEvent event) {
                    SwtMessageDialog.this.buttonPressed(((Integer) event.widget.getData()).intValue());
                }
            });
            if (i == this.defaultIndex && (shell = parent.getShell()) != null) {
                shell.setDefaultButton(button);
            }
        }
        return composite;
    }

    @Override // org.microemu.app.ui.swt.SwtDialog
    protected void buttonPressed(int buttonId) {
        setReturnCode(buttonId);
        close();
    }
}
