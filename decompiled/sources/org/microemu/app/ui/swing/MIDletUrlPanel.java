package org.microemu.app.ui.swing;

import javax.swing.JTextField;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/MIDletUrlPanel.class */
public class MIDletUrlPanel extends SwingDialogPanel {
    private static final long serialVersionUID = 1;
    private JTextField jadUrlField = new JTextField(50);

    public MIDletUrlPanel() {
        add(this.jadUrlField);
    }

    public String getText() {
        return this.jadUrlField.getText();
    }

    @Override // org.microemu.app.ui.swing.SwingDialogPanel
    protected void showNotify() {
        this.jadUrlField.setText("");
    }
}
