package org.microemu.app.ui.swt;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/* loaded from: avacs.jar:org/microemu/app/ui/swt/OptionPane.class */
public class OptionPane {
    public static final int ERROR_MESSAGE = 1;
    public static final int INFORMATION_MESSAGE = 2;
    private static WindowAdapter windowListener = new WindowAdapter() { // from class: org.microemu.app.ui.swt.OptionPane.1
        public void windowClosing(WindowEvent ev) {
            ((Dialog) ev.getSource()).hide();
        }
    };

    public static String showInputDialog(Component parentComponent, String message) {
        final StringBuffer result = new StringBuffer();
        final Dialog dialog = new Dialog(getFrame(parentComponent), "Input");
        final TextField tfInput = new TextField(40);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new Label(message), "West");
        dialog.add(tfInput, "Center");
        Panel panel = new Panel();
        Button btOk = new Button("OK");
        panel.add(btOk);
        Button btCancel = new Button("Cancel");
        panel.add(btCancel);
        dialog.add(panel, "South");
        dialog.pack();
        dialog.addWindowListener(windowListener);
        btOk.addActionListener(new ActionListener() { // from class: org.microemu.app.ui.swt.OptionPane.2
            public void actionPerformed(ActionEvent ev) {
                result.append(tfInput.getText());
                dialog.hide();
            }
        });
        btCancel.addActionListener(new ActionListener() { // from class: org.microemu.app.ui.swt.OptionPane.3
            public void actionPerformed(ActionEvent ev) {
                dialog.hide();
            }
        });
        dialog.show();
        if (result.length() > 0) {
            return result.toString();
        }
        return null;
    }

    public static void showMessageDialog(Component parentComponent, String message, String title, int messageType) {
        final Dialog dialog = new Dialog(getFrame(parentComponent), title);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.add(new Label(message), "Center");
        Panel panel = new Panel();
        Button btOk = new Button("OK");
        panel.add(btOk);
        dialog.add(panel, "South");
        dialog.pack();
        dialog.addWindowListener(windowListener);
        btOk.addActionListener(new ActionListener() { // from class: org.microemu.app.ui.swt.OptionPane.4
            public void actionPerformed(ActionEvent ev) {
                dialog.hide();
            }
        });
        dialog.show();
    }

    private static Frame getFrame(Component parentComponent) {
        Component parent = parentComponent;
        while (true) {
            Component tmp = parent;
            if (tmp instanceof Frame) {
                return (Frame) tmp;
            }
            parent = tmp.getParent();
        }
    }
}
