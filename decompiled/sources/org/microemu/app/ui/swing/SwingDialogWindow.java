package org.microemu.app.ui.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/SwingDialogWindow.class */
public class SwingDialogWindow {
    public static boolean show(Frame parent, String title, final SwingDialogPanel panel, boolean hasCancel) {
        final JDialog dialog = new JDialog(parent, title, true);
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(panel, "Center");
        JPanel actionPanel = new JPanel();
        actionPanel.add(panel.btOk);
        if (hasCancel) {
            actionPanel.add(panel.btCancel);
        }
        final JButton extraButton = panel.getExtraButton();
        if (extraButton != null) {
            actionPanel.add(extraButton);
        }
        dialog.getContentPane().add(actionPanel, "South");
        dialog.pack();
        Dimension frameSize = dialog.getSize();
        int x = parent.getLocation().x + ((parent.getWidth() - frameSize.width) / 2);
        if (x < 0) {
            x = 0;
        }
        int y = parent.getLocation().y + ((parent.getHeight() - frameSize.height) / 2);
        if (y < 0) {
            y = 0;
        }
        dialog.setLocation(x, y);
        ActionListener closeListener = new ActionListener() { // from class: org.microemu.app.ui.swing.SwingDialogWindow.1
            public void actionPerformed(ActionEvent event) {
                Object source = event.getSource();
                panel.extra = false;
                if (source == panel.btOk || source == extraButton) {
                    if (panel.check(true)) {
                        if (source == extraButton) {
                            panel.extra = true;
                        }
                        panel.state = true;
                        dialog.setVisible(false);
                        panel.hideNotify();
                        return;
                    }
                    return;
                }
                panel.state = false;
                dialog.setVisible(false);
                panel.hideNotify();
            }
        };
        WindowAdapter windowAdapter = new WindowAdapter() { // from class: org.microemu.app.ui.swing.SwingDialogWindow.2
            public void windowClosing(WindowEvent e) {
                panel.state = false;
                panel.hideNotify();
            }
        };
        dialog.addWindowListener(windowAdapter);
        panel.btOk.addActionListener(closeListener);
        panel.btCancel.addActionListener(closeListener);
        if (extraButton != null) {
            extraButton.addActionListener(closeListener);
        }
        panel.showNotify();
        dialog.setVisible(true);
        panel.btOk.removeActionListener(closeListener);
        panel.btCancel.removeActionListener(closeListener);
        if (extraButton != null) {
            extraButton.removeActionListener(closeListener);
        }
        return panel.state;
    }
}
