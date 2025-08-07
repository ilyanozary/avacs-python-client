package org.microemu.app.ui.swt;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtInputDialog.class */
public class SwtInputDialog extends SwtDialog {
    private String title;
    private String message;
    private String value;

    public SwtInputDialog(Shell parentShell, String title, String message) {
        super(parentShell);
        this.title = title;
        this.message = message;
    }

    public String getValue() {
        return this.value;
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
        final Text txInput = new Text(composite, 2052);
        GridData gridData = new GridData(768);
        gridData.widthHint = txInput.getLineHeight() * 15;
        txInput.setLayoutData(gridData);
        txInput.addModifyListener(new ModifyListener() { // from class: org.microemu.app.ui.swt.SwtInputDialog.1
            public void modifyText(ModifyEvent e) {
                SwtInputDialog.this.value = txInput.getText();
            }
        });
        return composite;
    }
}
