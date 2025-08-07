package org.microemu.app.ui.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.microedition.media.PlayerListener;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreNotOpenException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.microemu.RecordStoreManager;
import org.microemu.app.Common;
import org.microemu.app.Config;
import org.microemu.app.util.FileRecordStoreManager;
import org.microemu.log.Logger;
import org.microemu.util.ExtendedRecordListener;
import org.microemu.util.MemoryRecordStoreManager;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/RecordStoreManagerDialog.class */
public class RecordStoreManagerDialog extends JFrame {
    private static final long serialVersionUID = 1;
    private Common common;
    private SimpleDateFormat dateFormat;
    private JLabel recordStoreTypeLabel;
    private JLabel suiteNameLabel;
    private RecordStoreChangePanel recordStoreChangePanel;
    private DefaultTableModel modelTable;
    private JTable logTable;
    private JScrollPane logScrollPane;
    private ActionListener recordStoreTypeChangeListener;

    public RecordStoreManagerDialog(Frame owner, Common common) {
        super("Record Store Manager");
        this.dateFormat = new SimpleDateFormat("HH:mm:ss.S");
        this.recordStoreTypeLabel = new JLabel();
        this.suiteNameLabel = new JLabel();
        this.recordStoreChangePanel = null;
        this.modelTable = new DefaultTableModel();
        this.logTable = new JTable(this.modelTable);
        this.logScrollPane = new JScrollPane(this.logTable);
        this.recordStoreTypeChangeListener = new ActionListener() { // from class: org.microemu.app.ui.swing.RecordStoreManagerDialog.1
            public void actionPerformed(ActionEvent e) throws IOException {
                RecordStoreManager manager;
                if (RecordStoreManagerDialog.this.recordStoreChangePanel == null) {
                    RecordStoreManagerDialog.this.recordStoreChangePanel = new RecordStoreChangePanel(RecordStoreManagerDialog.this.common);
                }
                if (SwingDialogWindow.show(RecordStoreManagerDialog.this, "Change Record Store...", RecordStoreManagerDialog.this.recordStoreChangePanel, true)) {
                    String recordStoreName = RecordStoreManagerDialog.this.recordStoreChangePanel.getSelectedRecordStoreName();
                    if (!recordStoreName.equals(RecordStoreManagerDialog.this.common.getRecordStoreManager().getName()) && JOptionPane.showConfirmDialog(RecordStoreManagerDialog.this, "Changing record store type requires MIDlet restart. \nDo you want to proceed? All MIDlet data will be lost.", "Question?", 0, 3) == 0) {
                        for (int i = RecordStoreManagerDialog.this.modelTable.getRowCount() - 1; i >= 0; i--) {
                            RecordStoreManagerDialog.this.modelTable.removeRow(i);
                        }
                        if (recordStoreName.equals("File record store")) {
                            manager = new FileRecordStoreManager();
                        } else {
                            manager = new MemoryRecordStoreManager();
                        }
                        RecordStoreManagerDialog.this.common.setRecordStoreManager(manager);
                        Config.setRecordStoreManagerClassName(manager.getClass().getName());
                        RecordStoreManagerDialog.this.refresh();
                        try {
                            RecordStoreManagerDialog.this.common.initMIDlet(true);
                        } catch (Exception ex) {
                            Logger.error((Throwable) ex);
                        }
                    }
                }
            }
        };
        this.common = common;
        setIconImage(owner.getIconImage());
        setFocusableWindowState(false);
        setLayout(new BorderLayout());
        refresh();
        JButton recordStoreTypeChangeButton = new JButton("Change...");
        recordStoreTypeChangeButton.addActionListener(this.recordStoreTypeChangeListener);
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 3, 3, 3);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = 0;
        c.weightx = 0.0d;
        c.weighty = 0.0d;
        headerPanel.add(new JLabel("Record store type:"), c);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = 2;
        c.weightx = 1.0d;
        c.weighty = 0.0d;
        headerPanel.add(this.recordStoreTypeLabel, c);
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = 0;
        c.weightx = 0.0d;
        c.weighty = 0.0d;
        headerPanel.add(recordStoreTypeChangeButton, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = 0;
        c.weightx = 0.0d;
        c.weighty = 0.0d;
        headerPanel.add(new JLabel("Suite name:"), c);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = 2;
        c.weightx = 1.0d;
        c.weighty = 0.0d;
        headerPanel.add(this.suiteNameLabel, c);
        this.modelTable.addColumn("Timestamp");
        this.modelTable.addColumn("Action type");
        this.modelTable.addColumn("Record store name");
        this.modelTable.addColumn("Details");
        this.logTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() { // from class: org.microemu.app.ui.swing.RecordStoreManagerDialog.2
            private Color SUPER_LIGHT_GRAY = new Color(240, 240, 240);
            private static final long serialVersionUID = 1;

            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (row % 2 == 0) {
                    setBackground(Color.WHITE);
                } else {
                    setBackground(this.SUPER_LIGHT_GRAY);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
        this.logTable.setShowGrid(false);
        this.logScrollPane.setAutoscrolls(true);
        JTabbedPane viewPanel = new JTabbedPane();
        viewPanel.addTab("Log view", this.logScrollPane);
        getContentPane().add(headerPanel, "North");
        getContentPane().add(viewPanel, "Center");
    }

    public void refresh() {
        this.recordStoreTypeLabel.setText(this.common.getRecordStoreManager().getName());
        this.suiteNameLabel.setText(this.common.getLauncher().getSuiteName());
        this.common.getRecordStoreManager().setRecordListener(new ExtendedRecordListener() { // from class: org.microemu.app.ui.swing.RecordStoreManagerDialog.3
            @Override // org.microemu.util.ExtendedRecordListener
            public void recordEvent(int type, long timestamp, RecordStore recordStore, int recordId) {
                String eventMessageType = null;
                switch (type) {
                    case 1:
                        eventMessageType = "added";
                        break;
                    case 2:
                        eventMessageType = "read";
                        break;
                    case 3:
                        eventMessageType = "changed";
                        break;
                    case 4:
                        eventMessageType = "deleted";
                        break;
                }
                try {
                    RecordStoreManagerDialog.this.modelTable.addRow(new Object[]{RecordStoreManagerDialog.this.dateFormat.format(new Date(timestamp)), "record " + eventMessageType, recordStore.getName(), "recordId = " + recordId});
                } catch (RecordStoreNotOpenException e) {
                    e.printStackTrace();
                }
                RecordStoreManagerDialog.this.logTable.scrollRectToVisible(RecordStoreManagerDialog.this.logTable.getCellRect(RecordStoreManagerDialog.this.modelTable.getRowCount() - 1, 0, true));
            }

            @Override // org.microemu.util.ExtendedRecordListener
            public void recordStoreEvent(int type, long timestamp, String recordStoreName) {
                String eventMessageType = null;
                switch (type) {
                    case 8:
                        eventMessageType = "opened";
                        break;
                    case 9:
                        eventMessageType = PlayerListener.CLOSED;
                        break;
                    case 10:
                        eventMessageType = "deleted";
                        break;
                }
                DefaultTableModel defaultTableModel = RecordStoreManagerDialog.this.modelTable;
                Object[] objArr = new Object[4];
                objArr[0] = RecordStoreManagerDialog.this.dateFormat.format(new Date(timestamp));
                objArr[1] = "store " + eventMessageType;
                objArr[2] = recordStoreName;
                defaultTableModel.addRow(objArr);
                RecordStoreManagerDialog.this.logTable.scrollRectToVisible(RecordStoreManagerDialog.this.logTable.getCellRect(RecordStoreManagerDialog.this.modelTable.getRowCount() - 1, 0, true));
            }

            @Override // javax.microedition.rms.RecordListener
            public void recordAdded(RecordStore recordStore, int recordId) {
            }

            @Override // javax.microedition.rms.RecordListener
            public void recordChanged(RecordStore recordStore, int recordId) {
            }

            @Override // javax.microedition.rms.RecordListener
            public void recordDeleted(RecordStore recordStore, int recordId) {
            }
        });
    }
}
