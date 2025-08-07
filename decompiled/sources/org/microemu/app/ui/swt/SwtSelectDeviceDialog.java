package org.microemu.app.ui.swt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.microemu.EmulatorContext;
import org.microemu.app.Common;
import org.microemu.app.Config;
import org.microemu.app.ui.Message;
import org.microemu.app.util.DeviceEntry;
import org.microemu.app.util.IOUtils;
import org.microemu.device.Device;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.swt.SwtDevice;

/* loaded from: avacs.jar:org/microemu/app/ui/swt/SwtSelectDeviceDialog.class */
public class SwtSelectDeviceDialog extends SwtDialog {
    private EmulatorContext emulatorContext;
    private Button btAdd;
    private Button btRemove;
    private Button btDefault;
    private List lsDevices;
    private Vector deviceModel;
    private DeviceEntry selectedEntry;
    private Listener btAddListener;
    private Listener btRemoveListener;
    private Listener btDefaultListener;
    SelectionAdapter lsDevicesListener;

    public SwtSelectDeviceDialog(Shell parent, EmulatorContext emulatorContext) throws IOException {
        super(parent);
        this.btAddListener = new Listener() { // from class: org.microemu.app.ui.swt.SwtSelectDeviceDialog.1
            private FileDialog fileDialog = null;

            public void handleEvent(Event event) throws IOException {
                DeviceEntry entry;
                if (this.fileDialog == null) {
                    this.fileDialog = new FileDialog(SwtSelectDeviceDialog.this.getShell(), 4096);
                    this.fileDialog.setText("Open device profile file...");
                    this.fileDialog.setFilterNames(new String[]{"Device profile (*.jar)"});
                    this.fileDialog.setFilterExtensions(new String[]{"*.jar"});
                }
                this.fileDialog.open();
                if (this.fileDialog.getFileName() != null) {
                    String manifestDeviceName = null;
                    URL[] urls = new URL[1];
                    ArrayList descriptorEntries = new ArrayList();
                    try {
                        File file = new File(this.fileDialog.getFilterPath(), this.fileDialog.getFileName());
                        JarFile jar = new JarFile(file);
                        Manifest manifest = jar.getManifest();
                        if (manifest != null) {
                            Attributes attrs = manifest.getMainAttributes();
                            manifestDeviceName = attrs.getValue("Device-Name");
                        }
                        Enumeration en = jar.entries();
                        while (en.hasMoreElements()) {
                            String entry2 = en.nextElement().getName();
                            if (entry2.toLowerCase().endsWith(".xml") || entry2.toLowerCase().endsWith("device.txt")) {
                                if (!entry2.toLowerCase().startsWith("meta-inf")) {
                                    descriptorEntries.add(entry2);
                                }
                            }
                        }
                        jar.close();
                        urls[0] = file.toURL();
                        if (descriptorEntries.size() == 0) {
                            Message.error("Cannot find any device profile in file: " + this.fileDialog.getFileName());
                            return;
                        }
                        if (descriptorEntries.size() > 1) {
                            manifestDeviceName = null;
                        }
                        ClassLoader classLoader = Common.createExtensionsClassLoader(urls);
                        HashMap devices = new HashMap();
                        Iterator it = descriptorEntries.iterator();
                        while (it.hasNext()) {
                            JarEntry entry3 = (JarEntry) it.next();
                            try {
                                devices.put(entry3.getName(), DeviceImpl.create(SwtSelectDeviceDialog.this.emulatorContext, classLoader, entry3.getName(), SwtDevice.class));
                            } catch (IOException ex) {
                                Message.error("Error parsing device profile, " + Message.getCauseMessage(ex), ex);
                                return;
                            }
                        }
                        for (int i = 0; i < SwtSelectDeviceDialog.this.deviceModel.size(); i++) {
                            DeviceEntry entry4 = (DeviceEntry) SwtSelectDeviceDialog.this.deviceModel.elementAt(i);
                            if (devices.containsKey(entry4.getDescriptorLocation())) {
                                devices.remove(entry4.getDescriptorLocation());
                            }
                        }
                        if (devices.size() == 0) {
                            Message.info("Device profile already added");
                            return;
                        }
                        try {
                            File deviceFile = new File(Config.getConfigPath(), file.getName());
                            if (deviceFile.exists()) {
                                deviceFile = File.createTempFile("device", ".jar", Config.getConfigPath());
                            }
                            IOUtils.copyFile(file, deviceFile);
                            for (String descriptorLocation : devices.keySet()) {
                                Device device = (Device) devices.get(descriptorLocation);
                                if (manifestDeviceName != null) {
                                    entry = new DeviceEntry(manifestDeviceName, deviceFile.getName(), descriptorLocation, false);
                                } else {
                                    entry = new DeviceEntry(device.getName(), deviceFile.getName(), descriptorLocation, false);
                                }
                                SwtSelectDeviceDialog.this.deviceModel.addElement(entry);
                                for (int i2 = 0; i2 < SwtSelectDeviceDialog.this.deviceModel.size(); i2++) {
                                    if (SwtSelectDeviceDialog.this.deviceModel.elementAt(i2) == entry) {
                                        SwtSelectDeviceDialog.this.lsDevices.add(entry.getName());
                                        SwtSelectDeviceDialog.this.lsDevices.select(i2);
                                    }
                                }
                                Config.addDeviceEntry(entry);
                            }
                            SwtSelectDeviceDialog.this.lsDevicesListener.widgetSelected((SelectionEvent) null);
                        } catch (IOException ex2) {
                            Message.error("Error adding device profile, " + Message.getCauseMessage(ex2), ex2);
                        }
                    } catch (IOException ex3) {
                        Message.error("Error reading file: " + this.fileDialog.getFileName() + ", " + Message.getCauseMessage(ex3), ex3);
                    }
                }
            }
        };
        this.btRemoveListener = new Listener() { // from class: org.microemu.app.ui.swt.SwtSelectDeviceDialog.2
            public void handleEvent(Event event) throws IOException {
                DeviceEntry entry = (DeviceEntry) SwtSelectDeviceDialog.this.deviceModel.elementAt(SwtSelectDeviceDialog.this.lsDevices.getSelectionIndex());
                boolean canDeleteFile = true;
                int i = 0;
                while (true) {
                    if (i >= SwtSelectDeviceDialog.this.deviceModel.size()) {
                        break;
                    }
                    DeviceEntry test = (DeviceEntry) SwtSelectDeviceDialog.this.deviceModel.elementAt(i);
                    if (test == entry || test.getFileName() == null || !test.getFileName().equals(entry.getFileName())) {
                        i++;
                    } else {
                        canDeleteFile = false;
                        break;
                    }
                }
                if (canDeleteFile) {
                    File deviceFile = new File(Config.getConfigPath(), entry.getFileName());
                    deviceFile.delete();
                }
                if (entry.isDefaultDevice()) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= SwtSelectDeviceDialog.this.deviceModel.size()) {
                            break;
                        }
                        DeviceEntry tmp = (DeviceEntry) SwtSelectDeviceDialog.this.deviceModel.elementAt(i2);
                        if (tmp.canRemove()) {
                            i2++;
                        } else {
                            tmp.setDefaultDevice(true);
                            SwtSelectDeviceDialog.this.lsDevices.setItem(i2, String.valueOf(tmp.getName()) + " (default)");
                            break;
                        }
                    }
                }
                int i3 = 0;
                while (true) {
                    if (i3 >= SwtSelectDeviceDialog.this.deviceModel.size()) {
                        break;
                    }
                    if (SwtSelectDeviceDialog.this.deviceModel.elementAt(i3) == entry) {
                        SwtSelectDeviceDialog.this.deviceModel.removeElementAt(i3);
                        SwtSelectDeviceDialog.this.lsDevices.remove(i3);
                        break;
                    }
                    i3++;
                }
                SwtSelectDeviceDialog.this.lsDevicesListener.widgetSelected((SelectionEvent) null);
                Config.removeDeviceEntry(entry);
            }
        };
        this.btDefaultListener = new Listener() { // from class: org.microemu.app.ui.swt.SwtSelectDeviceDialog.3
            public void handleEvent(Event event) throws IOException {
                DeviceEntry entry = (DeviceEntry) SwtSelectDeviceDialog.this.deviceModel.elementAt(SwtSelectDeviceDialog.this.lsDevices.getSelectionIndex());
                for (int i = 0; i < SwtSelectDeviceDialog.this.deviceModel.size(); i++) {
                    DeviceEntry tmp = (DeviceEntry) SwtSelectDeviceDialog.this.deviceModel.elementAt(i);
                    if (tmp == entry) {
                        tmp.setDefaultDevice(true);
                        SwtSelectDeviceDialog.this.lsDevices.setItem(i, String.valueOf(tmp.getName()) + " (default)");
                    } else {
                        tmp.setDefaultDevice(false);
                        SwtSelectDeviceDialog.this.lsDevices.setItem(i, tmp.getName());
                    }
                    Config.changeDeviceEntry(tmp);
                }
                SwtSelectDeviceDialog.this.btDefault.setEnabled(false);
            }
        };
        this.lsDevicesListener = new SelectionAdapter() { // from class: org.microemu.app.ui.swt.SwtSelectDeviceDialog.4
            public void widgetSelected(SelectionEvent e) {
                int index = SwtSelectDeviceDialog.this.lsDevices.getSelectionIndex();
                if (index == -1) {
                    SwtSelectDeviceDialog.this.selectedEntry = null;
                    SwtSelectDeviceDialog.this.btDefault.setEnabled(false);
                    SwtSelectDeviceDialog.this.btRemove.setEnabled(false);
                    SwtSelectDeviceDialog.this.btOk.setEnabled(false);
                    return;
                }
                SwtSelectDeviceDialog.this.selectedEntry = (DeviceEntry) SwtSelectDeviceDialog.this.deviceModel.elementAt(index);
                if (SwtSelectDeviceDialog.this.selectedEntry.isDefaultDevice()) {
                    SwtSelectDeviceDialog.this.btDefault.setEnabled(false);
                } else {
                    SwtSelectDeviceDialog.this.btDefault.setEnabled(true);
                }
                if (SwtSelectDeviceDialog.this.selectedEntry.canRemove()) {
                    SwtSelectDeviceDialog.this.btRemove.setEnabled(true);
                } else {
                    SwtSelectDeviceDialog.this.btRemove.setEnabled(false);
                }
                SwtSelectDeviceDialog.this.btOk.setEnabled(true);
            }
        };
        this.emulatorContext = emulatorContext;
        Vector devs = Config.getDeviceEntries();
        for (int i = 0; i < devs.size(); i++) {
            DeviceEntry entry = (DeviceEntry) devs.elementAt(i);
            if (entry.isDefaultDevice()) {
                this.selectedEntry = entry;
            }
        }
    }

    @Override // org.microemu.app.ui.swt.SwtDialog
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("Select device...");
    }

    @Override // org.microemu.app.ui.swt.SwtDialog
    protected Control createDialogArea(Composite composite) throws IOException {
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 1;
        composite.setLayout(gridLayout);
        Group gpDevices = new Group(composite, 0);
        gpDevices.setText("Installed devices");
        GridLayout gridLayout2 = new GridLayout();
        gridLayout2.numColumns = 1;
        gpDevices.setLayout(gridLayout2);
        gpDevices.setLayoutData(new GridData(1808));
        this.lsDevices = new List(gpDevices, 516);
        GridData gridData = new GridData(1808);
        gridData.horizontalSpan = 3;
        gridData.grabExcessVerticalSpace = true;
        Rectangle trim = this.lsDevices.computeTrim(0, 0, 0, this.lsDevices.getItemHeight() * 5);
        gridData.heightHint = trim.height;
        this.lsDevices.setLayoutData(gridData);
        this.lsDevices.addSelectionListener(this.lsDevicesListener);
        Composite btDevices = new Composite(gpDevices, 0);
        GridLayout gridLayout3 = new GridLayout();
        gridLayout3.numColumns = 3;
        btDevices.setLayout(gridLayout3);
        btDevices.setLayoutData(new GridData(64));
        this.btAdd = new Button(btDevices, 8);
        this.btAdd.setText("Add...");
        this.btAdd.setLayoutData(new GridData(64));
        this.btAdd.addListener(13, this.btAddListener);
        this.btRemove = new Button(btDevices, 8);
        this.btRemove.setText("Remove");
        this.btRemove.setLayoutData(new GridData(64));
        this.btRemove.addListener(13, this.btRemoveListener);
        this.btDefault = new Button(btDevices, 8);
        this.btDefault.setText("Set as default");
        this.btDefault.setLayoutData(new GridData(64));
        this.btDefault.addListener(13, this.btDefaultListener);
        Vector devs = Config.getDeviceEntries();
        this.deviceModel = new Vector();
        for (int i = 0; i < devs.size(); i++) {
            DeviceEntry entry = (DeviceEntry) devs.elementAt(i);
            this.deviceModel.addElement(entry);
            if (entry.isDefaultDevice()) {
                this.lsDevices.add(String.valueOf(entry.getName()) + " (default)");
                this.lsDevices.select(i);
            } else {
                this.lsDevices.add(entry.getName());
            }
        }
        return composite;
    }

    @Override // org.microemu.app.ui.swt.SwtDialog
    protected Control createButtonBar(Composite parent) {
        Control control = super.createButtonBar(parent);
        this.lsDevicesListener.widgetSelected((SelectionEvent) null);
        return control;
    }

    public DeviceEntry getSelectedDeviceEntry() {
        return this.selectedEntry;
    }
}
