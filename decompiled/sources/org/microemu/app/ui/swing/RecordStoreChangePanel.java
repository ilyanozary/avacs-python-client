package org.microemu.app.ui.swing;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import org.microemu.app.Common;
import org.microemu.app.util.FileRecordStoreManager;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/RecordStoreChangePanel.class */
public class RecordStoreChangePanel extends SwingDialogPanel {
    private static final long serialVersionUID = 1;
    private Common common;
    private JComboBox selectStoreCombo = new JComboBox(new String[]{"File record store", "Memory record store"});

    public RecordStoreChangePanel(Common common) {
        this.common = common;
        add(new JLabel("Record store type:"));
        add(this.selectStoreCombo);
    }

    @Override // org.microemu.app.ui.swing.SwingDialogPanel
    protected void showNotify() {
        if (this.common.getRecordStoreManager() instanceof FileRecordStoreManager) {
            this.selectStoreCombo.setSelectedIndex(0);
        } else {
            this.selectStoreCombo.setSelectedIndex(1);
        }
    }

    public String getSelectedRecordStoreName() {
        return (String) this.selectStoreCombo.getSelectedItem();
    }
}
