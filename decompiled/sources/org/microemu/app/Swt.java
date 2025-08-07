package org.microemu.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.microemu.DisplayComponent;
import org.microemu.EmulatorContext;
import org.microemu.MIDletBridge;
import org.microemu.app.ui.Message;
import org.microemu.app.ui.ResponseInterfaceListener;
import org.microemu.app.ui.StatusBarListener;
import org.microemu.app.ui.swt.SwtDeviceComponent;
import org.microemu.app.ui.swt.SwtErrorMessageDialogPanel;
import org.microemu.app.ui.swt.SwtInputDialog;
import org.microemu.app.ui.swt.SwtMessageDialog;
import org.microemu.app.ui.swt.SwtSelectDeviceDialog;
import org.microemu.app.util.DeviceEntry;
import org.microemu.app.util.IOUtils;
import org.microemu.device.Device;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.DeviceFactory;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.swt.SwtDevice;
import org.microemu.device.swt.SwtDeviceDisplay;
import org.microemu.device.swt.SwtFontManager;
import org.microemu.device.swt.SwtInputMethod;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:org/microemu/app/Swt.class */
public class Swt extends Common {
    public static Shell shell;
    protected static SwtDeviceComponent devicePanel;
    protected MenuItem menuOpenJADFile;
    protected MenuItem menuOpenJADURL;
    private SwtSelectDeviceDialog selectDeviceDialog;
    private FileDialog fileDialog;
    private MenuItem menuSelectDevice;
    private DeviceEntry deviceEntry;
    private Label statusBar;
    private KeyListener keyListener;
    protected Listener menuOpenJADFileListener;
    protected Listener menuOpenJADURLListener;
    protected Listener menuExitListener;
    private Listener menuSelectDeviceListener;
    private StatusBarListener statusBarListener;
    private ResponseInterfaceListener responseInterfaceListener;

    protected Swt(Shell shell2) {
        this(shell2, null);
    }

    protected Swt(Shell shell2, DeviceEntry defaultDevice) throws IOException, NumberFormatException {
        super(new EmulatorContext() { // from class: org.microemu.app.Swt.8
            private InputMethod inputMethod = new SwtInputMethod();
            private DeviceDisplay deviceDisplay = new SwtDeviceDisplay(this);
            private FontManager fontManager = new SwtFontManager();

            @Override // org.microemu.EmulatorContext
            public DisplayComponent getDisplayComponent() {
                return Swt.devicePanel.getDisplayComponent();
            }

            @Override // org.microemu.EmulatorContext
            public InputMethod getDeviceInputMethod() {
                return this.inputMethod;
            }

            @Override // org.microemu.EmulatorContext
            public DeviceDisplay getDeviceDisplay() {
                return this.deviceDisplay;
            }

            @Override // org.microemu.EmulatorContext
            public FontManager getDeviceFontManager() {
                return this.fontManager;
            }

            @Override // org.microemu.EmulatorContext
            public InputStream getResourceAsStream(String name) {
                return MIDletBridge.getCurrentMIDlet().getClass().getResourceAsStream(name);
            }

            @Override // org.microemu.EmulatorContext
            public boolean platformRequest(final String URL) {
                new Thread(new Runnable() { // from class: org.microemu.app.Swt.8.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Message.info("MIDlet requests that the device handle the following URL: " + URL);
                    }
                }).start();
                return false;
            }
        });
        this.fileDialog = null;
        this.keyListener = new KeyListener() { // from class: org.microemu.app.Swt.1
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }
        };
        this.menuOpenJADFileListener = new Listener() { // from class: org.microemu.app.Swt.2
            public void handleEvent(Event ev) throws IOException {
                File selectedFile;
                if (Swt.this.fileDialog == null) {
                    Swt.this.fileDialog = new FileDialog(Swt.shell, 4096);
                    Swt.this.fileDialog.setText("Open JAD File...");
                    Swt.this.fileDialog.setFilterNames(new String[]{"JAD files"});
                    Swt.this.fileDialog.setFilterExtensions(new String[]{"*.jad"});
                    Swt.this.fileDialog.setFilterPath(Config.getRecentDirectory("recentJadDirectory"));
                }
                Swt.this.fileDialog.open();
                if (Swt.this.fileDialog.getFileName().length() > 0) {
                    if (Swt.this.fileDialog.getFilterPath() == null) {
                        selectedFile = new File(Swt.this.fileDialog.getFileName());
                    } else {
                        selectedFile = new File(Swt.this.fileDialog.getFilterPath(), Swt.this.fileDialog.getFileName());
                        Config.setRecentDirectory("recentJadDirectory", Swt.this.fileDialog.getFilterPath());
                    }
                    IOUtils.getCanonicalFileURL(selectedFile);
                }
            }
        };
        this.menuOpenJADURLListener = new Listener() { // from class: org.microemu.app.Swt.3
            public void handleEvent(Event ev) {
                SwtInputDialog inputDialog = new SwtInputDialog(Swt.shell, "Open...", "Enter JAD URL:");
                if (inputDialog.open() == 0) {
                    System.err.println("Cannot load " + inputDialog.getValue());
                }
            }
        };
        this.menuExitListener = new Listener() { // from class: org.microemu.app.Swt.4
            public void handleEvent(Event e) throws IOException {
                Config.setWindow("main", new Rectangle(Swt.shell.getLocation().x, Swt.shell.getLocation().y, Swt.shell.getSize().x, Swt.shell.getSize().y), true);
                System.exit(0);
            }
        };
        this.menuSelectDeviceListener = new Listener() { // from class: org.microemu.app.Swt.5
            public void handleEvent(Event e) throws ClassNotFoundException, NumberFormatException {
                if (Swt.this.selectDeviceDialog.open() != 0 || Swt.this.selectDeviceDialog.getSelectedDeviceEntry().equals(Swt.this.getDevice())) {
                    return;
                }
                if (MIDletBridge.getCurrentMIDlet() == Swt.this.getLauncher() || SwtMessageDialog.openQuestion(Swt.shell, "Question?", "Changing device needs MIDlet to be restarted. All MIDlet data will be lost. Are you sure?")) {
                    Swt.this.setDevice(Swt.this.selectDeviceDialog.getSelectedDeviceEntry());
                    if (MIDletBridge.getCurrentMIDlet() != Swt.this.getLauncher()) {
                        try {
                            Swt.this.initMIDlet(true);
                            return;
                        } catch (Exception ex) {
                            System.err.println(ex);
                            return;
                        }
                    }
                    Swt.this.startLauncher(MIDletBridge.getMIDletContext());
                }
            }
        };
        this.statusBarListener = new StatusBarListener() { // from class: org.microemu.app.Swt.6
            @Override // org.microemu.app.ui.StatusBarListener
            public void statusBarChanged(final String text) {
                Swt.shell.getDisplay().asyncExec(new Runnable() { // from class: org.microemu.app.Swt.6.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Swt.this.statusBar.setText(text);
                    }
                });
            }
        };
        this.responseInterfaceListener = new ResponseInterfaceListener() { // from class: org.microemu.app.Swt.7
            @Override // org.microemu.app.ui.ResponseInterfaceListener
            public void stateChanged(final boolean state) {
                Swt.shell.getDisplay().asyncExec(new Runnable() { // from class: org.microemu.app.Swt.7.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Swt.this.menuOpenJADFile.setEnabled(state);
                        Swt.this.menuOpenJADURL.setEnabled(state);
                        Swt.this.menuSelectDevice.setEnabled(state);
                    }
                });
            }
        };
        initInterface(shell2);
        Config.loadConfig(null, this.emulatorContext);
        loadImplementationsFromConfig();
        Rectangle window = Config.getWindow("main", new Rectangle(0, 0, Opcodes.IF_ICMPNE, Opcodes.ISHL));
        shell2.setLocation(window.x, window.y);
        shell2.addKeyListener(this.keyListener);
        this.selectDeviceDialog = new SwtSelectDeviceDialog(shell2, this.emulatorContext);
        setStatusBarListener(this.statusBarListener);
        setResponseInterfaceListener(this.responseInterfaceListener);
        Message.addListener(new SwtErrorMessageDialogPanel(shell2));
    }

    protected void initInterface(Shell shell2) {
        GridLayout layout = new GridLayout(1, false);
        shell2.setLayout(layout);
        shell2.setLayoutData(new GridData(1808));
        Menu bar = new Menu(shell2, 2);
        shell2.setMenuBar(bar);
        MenuItem menuFile = new MenuItem(bar, 64);
        menuFile.setText("File");
        Menu fileSubmenu = new Menu(shell2, 4);
        menuFile.setMenu(fileSubmenu);
        this.menuOpenJADFile = new MenuItem(fileSubmenu, 8);
        this.menuOpenJADFile.setText("Open JAD File...");
        this.menuOpenJADFile.addListener(13, this.menuOpenJADFileListener);
        this.menuOpenJADURL = new MenuItem(fileSubmenu, 0);
        this.menuOpenJADURL.setText("Open JAD URL...");
        this.menuOpenJADURL.addListener(13, this.menuOpenJADURLListener);
        new MenuItem(fileSubmenu, 2);
        MenuItem menuExit = new MenuItem(fileSubmenu, 8);
        menuExit.setText("Exit");
        menuExit.addListener(13, this.menuExitListener);
        MenuItem menuOptions = new MenuItem(bar, 64);
        menuOptions.setText("Options");
        Menu optionsSubmenu = new Menu(shell2, 4);
        menuOptions.setMenu(optionsSubmenu);
        this.menuSelectDevice = new MenuItem(optionsSubmenu, 8);
        this.menuSelectDevice.setText("Select device...");
        this.menuSelectDevice.addListener(13, this.menuSelectDeviceListener);
        shell2.setText("AVACSEmulator");
        devicePanel = new SwtDeviceComponent(shell2);
        devicePanel.setLayoutData(new GridData(1808));
        this.statusBar = new Label(shell2, 256);
        this.statusBar.setText("Status");
        this.statusBar.setLayoutData(new GridData(768));
    }

    public void setDevice(DeviceEntry entry) throws ClassNotFoundException, NumberFormatException {
        DeviceFactory.getDevice();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            if (entry.getFileName() != null) {
                URL[] urls = {new File(Config.getConfigPath(), entry.getFileName()).toURI().toURL()};
                classLoader = Common.createExtensionsClassLoader(urls);
            }
            this.emulatorContext.getDeviceFontManager().init();
            Device device = DeviceImpl.create(this.emulatorContext, classLoader, entry.getDescriptorLocation(), SwtDevice.class);
            this.deviceEntry = entry;
            setDevice(device);
            updateDevice();
        } catch (MalformedURLException ex) {
            System.err.println(ex);
        } catch (IOException ex2) {
            System.err.println(ex2);
        }
    }

    protected void updateDevice() {
        shell.setSize(shell.computeSize(-1, -1, true));
    }

    public static void main(String[] args) {
        Display display = new Display();
        shell = new Shell(display, 224);
        List params = new ArrayList();
        for (String str : args) {
            params.add(str);
        }
        Swt app = new Swt(shell);
        app.initParams(params, app.selectDeviceDialog.getSelectedDeviceEntry(), SwtDevice.class);
        app.updateDevice();
        shell.pack();
        shell.open();
        try {
        } catch (NoSuchElementException e) {
        }
        app.initMIDlet(false);
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}
