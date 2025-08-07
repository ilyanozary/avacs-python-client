package org.microemu.app.ui.swing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.microemu.EmulatorContext;
import org.microemu.app.Common;
import org.microemu.app.Config;
import org.microemu.app.ui.Message;
import org.microemu.app.util.DeviceEntry;
import org.microemu.app.util.IOUtils;
import org.microemu.device.Device;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.j2se.J2SEDevice;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/SwingSelectDevicePanel.class */
public class SwingSelectDevicePanel extends SwingDialogPanel {
    private static final long serialVersionUID = 1;
    private EmulatorContext emulatorContext;
    private JScrollPane spDevices;
    private JButton btAdd;
    private JButton btRemove;
    private JButton btDefault;
    private DefaultListModel lsDevicesModel;
    private JList lsDevices;
    private ActionListener btAddListener = new ActionListener() { // from class: org.microemu.app.ui.swing.SwingSelectDevicePanel.1
        private JFileChooser fileChooser = null;

        /* JADX WARN: Finally extract failed */
        public void actionPerformed(ActionEvent ev) throws IOException {
            if (this.fileChooser == null) {
                this.fileChooser = new JFileChooser();
                ExtensionFileFilter fileFilter = new ExtensionFileFilter("Device profile (*.jar, *.zip)");
                fileFilter.addExtension("jar");
                fileFilter.addExtension("zip");
                this.fileChooser.setFileFilter(fileFilter);
            }
            if (this.fileChooser.showOpenDialog(SwingSelectDevicePanel.this) == 0) {
                String manifestDeviceName = null;
                URL[] urls = new URL[1];
                ArrayList descriptorEntries = new ArrayList();
                JarFile jar = null;
                try {
                    try {
                        jar = new JarFile(this.fileChooser.getSelectedFile());
                        Manifest manifest = jar.getManifest();
                        if (manifest != null) {
                            Attributes attrs = manifest.getMainAttributes();
                            manifestDeviceName = attrs.getValue("Device-Name");
                        }
                        Enumeration en = jar.entries();
                        while (en.hasMoreElements()) {
                            String entry = en.nextElement().getName();
                            if (entry.toLowerCase().endsWith(".xml") || entry.toLowerCase().endsWith("device.txt")) {
                                if (!entry.toLowerCase().startsWith("meta-inf")) {
                                    descriptorEntries.add(entry);
                                }
                            }
                        }
                        urls[0] = this.fileChooser.getSelectedFile().toURL();
                        if (jar != null) {
                            try {
                                jar.close();
                            } catch (IOException e) {
                            }
                        }
                        if (descriptorEntries.size() == 0) {
                            Message.error("Cannot find any device profile in file: " + this.fileChooser.getSelectedFile().getName());
                            return;
                        }
                        if (descriptorEntries.size() > 1) {
                            manifestDeviceName = null;
                        }
                        ClassLoader classLoader = Common.createExtensionsClassLoader(urls);
                        HashMap devices = new HashMap();
                        Iterator it = descriptorEntries.iterator();
                        while (it.hasNext()) {
                            String entryName = (String) it.next();
                            try {
                                devices.put(entryName, DeviceImpl.create(SwingSelectDevicePanel.this.emulatorContext, classLoader, entryName, J2SEDevice.class));
                            } catch (IOException e2) {
                                Message.error("Error parsing device profile, " + Message.getCauseMessage(e2), e2);
                                return;
                            }
                        }
                        Enumeration en2 = SwingSelectDevicePanel.this.lsDevicesModel.elements();
                        while (en2.hasMoreElements()) {
                            DeviceEntry entry2 = (DeviceEntry) en2.nextElement();
                            if (devices.containsKey(entry2.getDescriptorLocation())) {
                                devices.remove(entry2.getDescriptorLocation());
                            }
                        }
                        if (devices.size() == 0) {
                            Message.info("Device profile already added");
                            return;
                        }
                        try {
                            File deviceFile = new File(Config.getConfigPath(), this.fileChooser.getSelectedFile().getName());
                            if (deviceFile.exists()) {
                                deviceFile = File.createTempFile("device", ".jar", Config.getConfigPath());
                            }
                            IOUtils.copyFile(this.fileChooser.getSelectedFile(), deviceFile);
                            DeviceEntry entry3 = null;
                            for (String descriptorLocation : devices.keySet()) {
                                Device device = (Device) devices.get(descriptorLocation);
                                if (manifestDeviceName != null) {
                                    entry3 = new DeviceEntry(manifestDeviceName, deviceFile.getName(), descriptorLocation, false);
                                } else {
                                    entry3 = new DeviceEntry(device.getName(), deviceFile.getName(), descriptorLocation, false);
                                }
                                SwingSelectDevicePanel.this.lsDevicesModel.addElement(entry3);
                                Config.addDeviceEntry(entry3);
                            }
                            SwingSelectDevicePanel.this.lsDevices.setSelectedValue(entry3, true);
                        } catch (IOException e3) {
                            Message.error("Error adding device profile, " + Message.getCauseMessage(e3), e3);
                        }
                    } catch (Throwable th) {
                        if (jar != null) {
                            try {
                                jar.close();
                            } catch (IOException e4) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    Message.error("Error reading file: " + this.fileChooser.getSelectedFile().getName() + ", " + Message.getCauseMessage(e5), e5);
                    if (jar == null) {
                        return;
                    }
                    try {
                        jar.close();
                    } catch (IOException e6) {
                    }
                }
            }
        }
    };
    private ActionListener btRemoveListener = new ActionListener() { // from class: org.microemu.app.ui.swing.SwingSelectDevicePanel.2
        public void actionPerformed(ActionEvent ev) throws IOException {
            DeviceEntry entry = (DeviceEntry) SwingSelectDevicePanel.this.lsDevices.getSelectedValue();
            boolean canDeleteFile = true;
            Enumeration en = SwingSelectDevicePanel.this.lsDevicesModel.elements();
            while (true) {
                if (!en.hasMoreElements()) {
                    break;
                }
                DeviceEntry test = (DeviceEntry) en.nextElement();
                if (test != entry && test.getFileName() != null && test.getFileName().equals(entry.getFileName())) {
                    canDeleteFile = false;
                    break;
                }
            }
            if (canDeleteFile) {
                File deviceFile = new File(Config.getConfigPath(), entry.getFileName());
                deviceFile.delete();
            }
            if (entry.isDefaultDevice()) {
                Enumeration en2 = SwingSelectDevicePanel.this.lsDevicesModel.elements();
                while (true) {
                    if (!en2.hasMoreElements()) {
                        break;
                    }
                    DeviceEntry tmp = (DeviceEntry) en2.nextElement();
                    if (!tmp.canRemove()) {
                        tmp.setDefaultDevice(true);
                        break;
                    }
                }
            }
            SwingSelectDevicePanel.this.lsDevicesModel.removeElement(entry);
            Config.removeDeviceEntry(entry);
        }
    };
    private ActionListener btDefaultListener = new ActionListener() { // from class: org.microemu.app.ui.swing.SwingSelectDevicePanel.3
        public void actionPerformed(ActionEvent ev) throws IOException {
            DeviceEntry entry = (DeviceEntry) SwingSelectDevicePanel.this.lsDevices.getSelectedValue();
            Enumeration en = SwingSelectDevicePanel.this.lsDevicesModel.elements();
            while (en.hasMoreElements()) {
                DeviceEntry tmp = (DeviceEntry) en.nextElement();
                if (tmp == entry) {
                    tmp.setDefaultDevice(true);
                } else {
                    tmp.setDefaultDevice(false);
                }
                Config.changeDeviceEntry(tmp);
            }
            SwingSelectDevicePanel.this.lsDevices.repaint();
            SwingSelectDevicePanel.this.btDefault.setEnabled(false);
        }
    };
    ListSelectionListener listSelectionListener = new ListSelectionListener() { // from class: org.microemu.app.ui.swing.SwingSelectDevicePanel.4
        public void valueChanged(ListSelectionEvent ev) {
            DeviceEntry entry = (DeviceEntry) SwingSelectDevicePanel.this.lsDevices.getSelectedValue();
            if (entry == null) {
                SwingSelectDevicePanel.this.btDefault.setEnabled(false);
                SwingSelectDevicePanel.this.btRemove.setEnabled(false);
                SwingSelectDevicePanel.this.btOk.setEnabled(false);
                return;
            }
            if (entry.isDefaultDevice()) {
                SwingSelectDevicePanel.this.btDefault.setEnabled(false);
            } else {
                SwingSelectDevicePanel.this.btDefault.setEnabled(true);
            }
            if (entry.canRemove()) {
                SwingSelectDevicePanel.this.btRemove.setEnabled(true);
            } else {
                SwingSelectDevicePanel.this.btRemove.setEnabled(false);
            }
            SwingSelectDevicePanel.this.btOk.setEnabled(true);
        }
    };

    public SwingSelectDevicePanel(EmulatorContext emulatorContext) {
        this.emulatorContext = emulatorContext;
        setLayout(new BorderLayout());
        setBorder(new TitledBorder(new EtchedBorder(), "Installed devices"));
        this.lsDevicesModel = new DefaultListModel();
        this.lsDevices = new JList(this.lsDevicesModel);
        this.lsDevices.setSelectionMode(0);
        this.lsDevices.addListSelectionListener(this.listSelectionListener);
        this.spDevices = new JScrollPane(this.lsDevices);
        add(this.spDevices, "Center");
        JPanel panel = new JPanel();
        this.btAdd = new JButton("Add...");
        this.btAdd.addActionListener(this.btAddListener);
        this.btRemove = new JButton("Remove");
        this.btRemove.addActionListener(this.btRemoveListener);
        this.btDefault = new JButton("Set as default");
        this.btDefault.addActionListener(this.btDefaultListener);
        panel.add(this.btAdd);
        panel.add(this.btRemove);
        panel.add(this.btDefault);
        add(panel, "South");
        Enumeration e = Config.getDeviceEntries().elements();
        while (e.hasMoreElements()) {
            DeviceEntry entry = (DeviceEntry) e.nextElement();
            this.lsDevicesModel.addElement(entry);
            if (entry.isDefaultDevice()) {
                this.lsDevices.setSelectedValue(entry, true);
            }
        }
    }

    public DeviceEntry getSelectedDeviceEntry() {
        return (DeviceEntry) this.lsDevices.getSelectedValue();
    }
}
