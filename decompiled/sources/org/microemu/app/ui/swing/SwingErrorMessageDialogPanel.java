package org.microemu.app.ui.swing;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import org.microemu.app.ui.MessageListener;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/SwingErrorMessageDialogPanel.class */
public class SwingErrorMessageDialogPanel extends SwingDialogPanel implements MessageListener {
    private static final long serialVersionUID = 1;
    private Frame parent;
    private JLabel iconLabel;
    private JLabel textLabel;
    private JTextArea stackTraceArea;
    private JScrollPane stackTracePane;

    public SwingErrorMessageDialogPanel(Frame parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 10;
        c.ipady = 10;
        c.gridx = 0;
        c.gridy = 0;
        this.iconLabel = new JLabel();
        add(this.iconLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0d;
        this.textLabel = new JLabel();
        add(this.textLabel, c);
        this.stackTraceArea = new JTextArea();
        this.stackTraceArea.setEditable(false);
        this.stackTracePane = new JScrollPane(this.stackTraceArea);
        this.stackTracePane.setPreferredSize(new Dimension(250, 250));
    }

    @Override // org.microemu.app.ui.MessageListener
    public void showMessage(int level, String title, String text, Throwable throwable) {
        switch (level) {
            case 0:
                this.iconLabel.setIcon(UIManager.getIcon("OptionPane.errorIcon"));
                break;
            case 1:
            default:
                this.iconLabel.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
                break;
            case 2:
                this.iconLabel.setIcon(UIManager.getIcon("OptionPane.warningIcon"));
                break;
        }
        this.textLabel.setText(text);
        if (throwable != null) {
            StringWriter writer = new StringWriter();
            throwable.printStackTrace(new PrintWriter(writer));
            this.stackTraceArea.setText(writer.toString());
            this.stackTraceArea.setCaretPosition(0);
            GridBagConstraints c = new GridBagConstraints();
            c.fill = 1;
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 2;
            c.weightx = 1.0d;
            c.weighty = 1.0d;
            add(this.stackTracePane, c);
        }
        SwingDialogWindow.show(this.parent, title, this, false);
        if (throwable != null) {
            remove(this.stackTracePane);
        }
    }
}
