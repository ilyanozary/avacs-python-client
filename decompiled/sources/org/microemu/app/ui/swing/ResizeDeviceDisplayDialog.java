package org.microemu.app.ui.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/ResizeDeviceDisplayDialog.class */
public class ResizeDeviceDisplayDialog extends SwingDialogPanel {
    private static final long serialVersionUID = 1;
    private IntegerField widthField = new IntegerField(5, 1, 9999);
    private IntegerField heightField = new IntegerField(5, 1, 9999);

    /* loaded from: avacs.jar:org/microemu/app/ui/swing/ResizeDeviceDisplayDialog$IntegerField.class */
    private class IntegerField extends JTextField {
        private static final long serialVersionUID = 1;
        private int minValue;
        private int maxValue;

        public IntegerField(int cols, int minValue, int maxValue) {
            super(cols);
            this.minValue = minValue;
            this.maxValue = maxValue;
        }

        protected Document createDefaultModel() {
            return new IntegerDocument();
        }

        /* loaded from: avacs.jar:org/microemu/app/ui/swing/ResizeDeviceDisplayDialog$IntegerField$IntegerDocument.class */
        class IntegerDocument extends PlainDocument {
            private static final long serialVersionUID = 1;

            IntegerDocument() {
            }

            public void insertString(int offs, String str, AttributeSet a) throws NumberFormatException, BadLocationException {
                if (str == null) {
                    return;
                }
                char[] test = str.toCharArray();
                for (char c : test) {
                    if (!Character.isDigit(c)) {
                        return;
                    }
                }
                String prevText = getText(0, getLength());
                super.insertString(offs, str, a);
                int testValue = Integer.parseInt(getText(0, getLength()));
                if ((testValue < IntegerField.this.minValue) | (testValue > IntegerField.this.maxValue)) {
                    replace(0, getLength(), prevText, a);
                }
            }
        }
    }

    public ResizeDeviceDisplayDialog() {
        add(new JLabel("Width:"));
        add(this.widthField);
        add(new JLabel("Height:"));
        add(this.heightField);
        JButton swapButton = new JButton("Swap");
        swapButton.addActionListener(new ActionListener() { // from class: org.microemu.app.ui.swing.ResizeDeviceDisplayDialog.1
            public void actionPerformed(ActionEvent e) {
                String tmp = ResizeDeviceDisplayDialog.this.widthField.getText();
                ResizeDeviceDisplayDialog.this.widthField.setText(ResizeDeviceDisplayDialog.this.heightField.getText());
                ResizeDeviceDisplayDialog.this.heightField.setText(tmp);
            }
        });
        add(swapButton);
    }

    public void setDeviceDisplaySize(int width, int height) {
        this.widthField.setText(new StringBuilder().append(width).toString());
        this.heightField.setText(new StringBuilder().append(height).toString());
    }

    public int getDeviceDisplayWidth() {
        return Integer.parseInt(this.widthField.getText());
    }

    public int getDeviceDisplayHeight() {
        return Integer.parseInt(this.heightField.getText());
    }
}
