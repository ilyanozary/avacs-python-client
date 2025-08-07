package org.microemu.app.ui.swing;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.microemu.app.Main;
import org.microemu.app.util.BuildVersion;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/SwingAboutDialog.class */
public class SwingAboutDialog extends SwingDialogPanel {
    private static final long serialVersionUID = 1;
    private JLabel iconLabel;
    private JLabel textLabel;

    public SwingAboutDialog() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 10;
        c.ipady = 10;
        c.gridx = 0;
        c.gridy = 0;
        this.iconLabel = new JLabel();
        add(this.iconLabel, c);
        this.iconLabel.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/org/microemu/icon.png"))));
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0d;
        c.gridwidth = 0;
        this.textLabel = new JLabel("AVACSEmulator");
        this.textLabel.setFont(new Font("Default", 1, 18));
        add(this.textLabel, c);
        c.gridy = 1;
        c.weightx = 0.0d;
        c.gridwidth = 0;
        c.anchor = 17;
        add(new JLabel("version: " + BuildVersion.getVersion()), c);
        c.gridy = 2;
        c.weightx = 0.0d;
        c.gridwidth = 0;
        add(new JLabel("Copyright (C) 2001-2008 Bartek Teodorczyk & co"), c);
    }
}
