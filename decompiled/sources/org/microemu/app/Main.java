package org.microemu.app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.io.HttpConnection;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import org.microemu.DisplayAccess;
import org.microemu.DisplayComponent;
import org.microemu.EmulatorContext;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.app.capture.AnimatedGifEncoder;
import org.microemu.app.classloader.MIDletClassLoader;
import org.microemu.app.ui.DisplayRepaintListener;
import org.microemu.app.ui.Message;
import org.microemu.app.ui.ResponseInterfaceListener;
import org.microemu.app.ui.StatusBarListener;
import org.microemu.app.ui.swing.DropTransferHandler;
import org.microemu.app.ui.swing.ExtensionFileFilter;
import org.microemu.app.ui.swing.JMRUMenu;
import org.microemu.app.ui.swing.MIDletUrlPanel;
import org.microemu.app.ui.swing.RecordStoreManagerDialog;
import org.microemu.app.ui.swing.ResizeDeviceDisplayDialog;
import org.microemu.app.ui.swing.SwingAboutDialog;
import org.microemu.app.ui.swing.SwingDeviceComponent;
import org.microemu.app.ui.swing.SwingDialogWindow;
import org.microemu.app.ui.swing.SwingDisplayComponent;
import org.microemu.app.ui.swing.SwingErrorMessageDialogPanel;
import org.microemu.app.ui.swing.SwingLogConsoleDialog;
import org.microemu.app.ui.swing.SwingSelectDevicePanel;
import org.microemu.app.util.AppletProducer;
import org.microemu.app.util.DeviceEntry;
import org.microemu.app.util.IOUtils;
import org.microemu.app.util.MidletURLReference;
import org.microemu.cldc.http.Connection;
import org.microemu.device.Device;
import org.microemu.device.DeviceDisplay;
import org.microemu.device.DeviceFactory;
import org.microemu.device.FontManager;
import org.microemu.device.InputMethod;
import org.microemu.device.MutableImage;
import org.microemu.device.impl.DeviceDisplayImpl;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.impl.Rectangle;
import org.microemu.device.j2se.J2SEDevice;
import org.microemu.device.j2se.J2SEDeviceDisplay;
import org.microemu.device.j2se.J2SEFontManager;
import org.microemu.device.j2se.J2SEInputMethod;
import org.microemu.device.j2se.J2SEMutableImage;
import org.microemu.log.Logger;
import org.microemu.log.QueueAppender;
import org.microemu.util.JadMidletEntry;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:org/microemu/app/Main.class */
public class Main extends JFrame {
    private static final long serialVersionUID = 1;
    protected Common common;
    protected SwingSelectDevicePanel selectDevicePanel;
    private MIDletUrlPanel midletUrlPanel;
    private JFileChooser saveForWebChooser;
    private JFileChooser fileChooser;
    private JFileChooser captureFileChooser;
    private JMenuItem menuOpenMIDletFile;
    private JMenuItem menuOpenMIDletURL;
    private JMenuItem menuSelectDevice;
    private JMenuItem menuSaveForWeb;
    private JMenuItem menuStartCapture;
    private JMenuItem menuStopCapture;
    private JCheckBoxMenuItem menuMIDletNetworkConnection;
    private JCheckBoxMenuItem menuLogConsole;
    private JCheckBoxMenuItem menuRecordStoreManager;
    private JFrame scaledDisplayFrame;
    private JCheckBoxMenuItem[] zoomLevels;
    private SwingDeviceComponent devicePanel;
    private SwingLogConsoleDialog logConsoleDialog;
    private RecordStoreManagerDialog recordStoreManagerDialog;
    private QueueAppender logQueueAppender;
    private DeviceEntry deviceEntry;
    private AnimatedGifEncoder encoder;
    private JLabel statusBar;
    private JButton resizeButton;
    private ResizeDeviceDisplayDialog resizeDeviceDisplayDialog;
    protected EmulatorContext emulatorContext;
    private ActionListener menuOpenMIDletFileListener;
    private ActionListener menuOpenMIDletURLListener;
    private ActionListener menuCloseMidletListener;
    private ActionListener menuSaveForWebListener;
    private ActionListener menuStartCaptureListener;
    private ActionListener menuStopCaptureListener;
    private ActionListener menuMIDletNetworkConnectionListener;
    private ActionListener menuRecordStoreManagerListener;
    private ActionListener menuLogConsoleListener;
    private ActionListener actionListenerUpMenu;
    private ActionListener actionListenerMainMenu;
    private ActionListener actionListenerWriteMenu;
    private ActionListener menuAboutListener;
    private ActionListener menuExitListener;
    private ActionListener menuSelectDeviceListener;
    private ActionListener menuScaledDisplayListener;
    private StatusBarListener statusBarListener;
    private ResponseInterfaceListener responseInterfaceListener;
    private ComponentListener componentListener;
    private WindowAdapter windowListener;

    /* renamed from: org.microemu.app.Main$20, reason: invalid class name */
    /* loaded from: avacs.jar:org/microemu/app/Main$20.class */
    class AnonymousClass20 extends ComponentAdapter {
        Timer timer;
        int count = 0;

        AnonymousClass20() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v25, types: [org.microemu.app.ui.StatusBarListener] */
        /* JADX WARN: Type inference failed for: r0v26, types: [java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r0v31 */
        public void componentResized(ComponentEvent e) {
            this.count++;
            DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) DeviceFactory.getDevice().getDeviceDisplay();
            if (deviceDisplay.isResizable()) {
                deviceDisplay.setDisplayRectangle(new Rectangle(0, 0, Main.this.devicePanel.getWidth(), Main.this.devicePanel.getHeight()));
                ((SwingDisplayComponent) Main.this.devicePanel.getDisplayComponent()).init();
                MIDletAccess ma = MIDletBridge.getMIDletAccess();
                if (ma == null) {
                    return;
                }
                DisplayAccess da = ma.getDisplayAccess();
                if (da != null) {
                    da.sizeChanged();
                    deviceDisplay.repaint(0, 0, deviceDisplay.getFullWidth(), deviceDisplay.getFullHeight());
                }
                Main.this.devicePanel.revalidate();
                Main.this.statusBarListener.statusBarChanged("New size: " + deviceDisplay.getFullWidth() + "x" + deviceDisplay.getFullHeight());
                ?? r0 = Main.this.statusBarListener;
                synchronized (r0) {
                    if (this.timer == null) {
                        this.timer = new Timer();
                    }
                    this.timer.schedule(new CountTimerTask(Main.this, this.count) { // from class: org.microemu.app.Main.20.1
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public void run() throws IOException {
                            if (this.counter == AnonymousClass20.this.count) {
                                Config.setDeviceEntryDisplaySize(Main.this.deviceEntry, new Rectangle(0, 0, Main.this.devicePanel.getWidth(), Main.this.devicePanel.getHeight()));
                                Main.this.statusBarListener.statusBarChanged("");
                                AnonymousClass20.this.timer.cancel();
                                AnonymousClass20.this.timer = null;
                            }
                        }
                    }, 2000L);
                    r0 = r0;
                }
            }
        }
    }

    public Main() {
        this(null);
    }

    public Main(DeviceEntry defaultDevice) throws IllegalAccessException, NoSuchMethodException, InstantiationException, IOException, SecurityException, IllegalArgumentException, InvocationTargetException {
        this.selectDevicePanel = null;
        this.midletUrlPanel = null;
        this.fileChooser = null;
        this.captureFileChooser = null;
        this.statusBar = new JLabel("Status");
        this.resizeButton = new JButton("Resize");
        this.resizeDeviceDisplayDialog = null;
        this.emulatorContext = new EmulatorContext() { // from class: org.microemu.app.Main.1
            private InputMethod inputMethod = new J2SEInputMethod();
            private DeviceDisplay deviceDisplay = new J2SEDeviceDisplay(this);
            private FontManager fontManager = new J2SEFontManager();

            @Override // org.microemu.EmulatorContext
            public DisplayComponent getDisplayComponent() {
                return Main.this.devicePanel.getDisplayComponent();
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
                new Thread(new Runnable() { // from class: org.microemu.app.Main.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        boolean ststus = true;
                        if (!Desktop.isDesktopSupported()) {
                            ststus = false;
                        }
                        if (ststus) {
                            Desktop desktop = Desktop.getDesktop();
                            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                                ststus = false;
                            } else {
                                try {
                                    URI uri = new URI(URL);
                                    desktop.browse(uri);
                                } catch (Exception e) {
                                    ststus = false;
                                }
                            }
                        }
                        if (!ststus) {
                            Message.info("Open please manually following URL: " + URL);
                        }
                    }
                }).start();
                return false;
            }
        };
        this.menuOpenMIDletFileListener = new ActionListener() { // from class: org.microemu.app.Main.2
            public void actionPerformed(ActionEvent ev) throws IOException {
                if (Main.this.fileChooser == null) {
                    ExtensionFileFilter fileFilter = new ExtensionFileFilter("MIDlet files");
                    fileFilter.addExtension("jad");
                    fileFilter.addExtension("jar");
                    Main.this.fileChooser = new JFileChooser();
                    Main.this.fileChooser.setFileFilter(fileFilter);
                    Main.this.fileChooser.setDialogTitle("Open MIDlet File...");
                    Main.this.fileChooser.setCurrentDirectory(new File(Config.getRecentDirectory("recentJadDirectory")));
                }
                int returnVal = Main.this.fileChooser.showOpenDialog(Main.this);
                if (returnVal == 0) {
                    Config.setRecentDirectory("recentJadDirectory", Main.this.fileChooser.getCurrentDirectory().getAbsolutePath());
                    String url = IOUtils.getCanonicalFileURL(Main.this.fileChooser.getSelectedFile());
                    Common.openMIDletUrlSafe(url);
                    if (Main.this.recordStoreManagerDialog != null) {
                        Main.this.recordStoreManagerDialog.refresh();
                    }
                }
            }
        };
        this.menuOpenMIDletURLListener = new ActionListener() { // from class: org.microemu.app.Main.3
            public void actionPerformed(ActionEvent ev) {
                if (Main.this.midletUrlPanel == null) {
                    Main.this.midletUrlPanel = new MIDletUrlPanel();
                }
                if (SwingDialogWindow.show(Main.this, "Enter MIDlet URL:", Main.this.midletUrlPanel, true)) {
                    Common.openMIDletUrlSafe(Main.this.midletUrlPanel.getText());
                    if (Main.this.recordStoreManagerDialog != null) {
                        Main.this.recordStoreManagerDialog.refresh();
                    }
                }
            }
        };
        this.menuCloseMidletListener = new ActionListener() { // from class: org.microemu.app.Main.4
            public void actionPerformed(ActionEvent e) {
                Main.this.common.startLauncher(MIDletBridge.getMIDletContext());
            }
        };
        this.menuSaveForWebListener = new ActionListener() { // from class: org.microemu.app.Main.5
            public void actionPerformed(ActionEvent e) throws IOException {
                if (Main.this.saveForWebChooser == null) {
                    ExtensionFileFilter fileFilter = new ExtensionFileFilter("HTML files");
                    fileFilter.addExtension("html");
                    Main.this.saveForWebChooser = new JFileChooser();
                    Main.this.saveForWebChooser.setFileFilter(fileFilter);
                    Main.this.saveForWebChooser.setDialogTitle("Save for Web...");
                    Main.this.saveForWebChooser.setCurrentDirectory(new File(Config.getRecentDirectory("recentSaveForWebDirectory")));
                }
                if (Main.this.saveForWebChooser.showSaveDialog(Main.this) == 0) {
                    Config.setRecentDirectory("recentSaveForWebDirectory", Main.this.saveForWebChooser.getCurrentDirectory().getAbsolutePath());
                    File pathFile = Main.this.saveForWebChooser.getSelectedFile().getParentFile();
                    String name = Main.this.saveForWebChooser.getSelectedFile().getName();
                    if (!name.toLowerCase().endsWith(".html") && name.indexOf(46) == -1) {
                        name = String.valueOf(name) + ".html";
                    }
                    String resource = MIDletClassLoader.getClassResourceName(getClass().getName());
                    URL url = getClass().getClassLoader().getResource(resource);
                    String path = url.getPath();
                    int prefix = path.indexOf(58);
                    String mainJarFileName = path.substring(prefix + 1, path.length() - resource.length());
                    File appletJarDir = new File(new File(mainJarFileName).getParent(), "lib");
                    File appletJarFile = new File(appletJarDir, "microemu-javase-applet.jar");
                    if (!appletJarFile.exists()) {
                        appletJarFile = null;
                    }
                    if (appletJarFile == null) {
                        ExtensionFileFilter fileFilter2 = new ExtensionFileFilter("JAR packages");
                        fileFilter2.addExtension("jar");
                        JFileChooser appletChooser = new JFileChooser();
                        appletChooser.setFileFilter(fileFilter2);
                        appletChooser.setDialogTitle("Select MicroEmulator applet jar package...");
                        appletChooser.setCurrentDirectory(new File(Config.getRecentDirectory("recentAppletJarDirectory")));
                        if (appletChooser.showOpenDialog(Main.this) == 0) {
                            Config.setRecentDirectory("recentAppletJarDirectory", appletChooser.getCurrentDirectory().getAbsolutePath());
                            appletJarFile = appletChooser.getSelectedFile();
                        } else {
                            return;
                        }
                    }
                    Iterator it = Main.this.common.jad.getMidletEntries().iterator();
                    if (it.hasNext()) {
                        JadMidletEntry jadMidletEntry = (JadMidletEntry) it.next();
                        String midletInput = Main.this.common.jad.getJarURL();
                        DeviceEntry deviceInput = Main.this.selectDevicePanel.getSelectedDeviceEntry();
                        if (deviceInput != null && deviceInput.getDescriptorLocation().equals(DeviceImpl.DEFAULT_LOCATION)) {
                            deviceInput = null;
                        }
                        File htmlOutputFile = new File(pathFile, name);
                        if (!allowOverride(htmlOutputFile)) {
                            return;
                        }
                        File appletPackageOutputFile = new File(pathFile, "microemu-javase-applet.jar");
                        if (!allowOverride(appletPackageOutputFile)) {
                            return;
                        }
                        File midletOutputFile = new File(pathFile, midletInput.substring(midletInput.lastIndexOf("/") + 1));
                        if (!allowOverride(midletOutputFile)) {
                            return;
                        }
                        File deviceOutputFile = null;
                        if (deviceInput != null && deviceInput.getFileName() != null) {
                            deviceOutputFile = new File(pathFile, deviceInput.getFileName());
                            if (!allowOverride(deviceOutputFile)) {
                                return;
                            }
                        }
                        try {
                            AppletProducer.createHtml(htmlOutputFile, (DeviceImpl) DeviceFactory.getDevice(), jadMidletEntry.getClassName(), midletOutputFile, appletPackageOutputFile, deviceOutputFile);
                            AppletProducer.createMidlet(new URL(midletInput), midletOutputFile);
                            IOUtils.copyFile(appletJarFile, appletPackageOutputFile);
                            if (deviceInput != null && deviceInput.getFileName() != null) {
                                IOUtils.copyFile(new File(Config.getConfigPath(), deviceInput.getFileName()), deviceOutputFile);
                                return;
                            }
                            return;
                        } catch (IOException ex) {
                            Logger.error((Throwable) ex);
                            return;
                        }
                    }
                    Message.error("MIDlet Suite has no entries");
                }
            }

            private boolean allowOverride(File file) {
                if (file.exists()) {
                    int answer = JOptionPane.showConfirmDialog(Main.this, "Override the file:" + file + "?", "Question?", 0, 3);
                    if (answer == 1) {
                        return false;
                    }
                    return true;
                }
                return true;
            }
        };
        this.menuStartCaptureListener = new ActionListener() { // from class: org.microemu.app.Main.6
            public void actionPerformed(ActionEvent e) throws IOException {
                if (Main.this.captureFileChooser == null) {
                    ExtensionFileFilter fileFilter = new ExtensionFileFilter("GIF files");
                    fileFilter.addExtension("gif");
                    Main.this.captureFileChooser = new JFileChooser();
                    Main.this.captureFileChooser.setFileFilter(fileFilter);
                    Main.this.captureFileChooser.setDialogTitle("Capture to GIF File...");
                    Main.this.captureFileChooser.setCurrentDirectory(new File(Config.getRecentDirectory("recentCaptureDirectory")));
                }
                if (Main.this.captureFileChooser.showSaveDialog(Main.this) == 0) {
                    Config.setRecentDirectory("recentCaptureDirectory", Main.this.captureFileChooser.getCurrentDirectory().getAbsolutePath());
                    String name = Main.this.captureFileChooser.getSelectedFile().getName();
                    if (!name.toLowerCase().endsWith(".gif") && name.indexOf(46) == -1) {
                        name = String.valueOf(name) + ".gif";
                    }
                    File captureFile = new File(Main.this.captureFileChooser.getSelectedFile().getParentFile(), name);
                    if (!allowOverride(captureFile)) {
                        return;
                    }
                    Main.this.encoder = new AnimatedGifEncoder();
                    Main.this.encoder.start(captureFile.getAbsolutePath());
                    Main.this.menuStartCapture.setEnabled(false);
                    Main.this.menuStopCapture.setEnabled(true);
                    Main.this.emulatorContext.getDisplayComponent().addDisplayRepaintListener(new DisplayRepaintListener() { // from class: org.microemu.app.Main.6.1
                        long start = 0;

                        @Override // org.microemu.app.ui.DisplayRepaintListener
                        public void repaintInvoked(MutableImage image) {
                            JFrame jFrame = Main.this;
                            synchronized (jFrame) {
                                if (Main.this.encoder != null) {
                                    if (this.start == 0) {
                                        this.start = System.currentTimeMillis();
                                    } else {
                                        long current = System.currentTimeMillis();
                                        Main.this.encoder.setDelay((int) (current - this.start));
                                        this.start = current;
                                    }
                                    Main.this.encoder.addFrame((BufferedImage) ((J2SEMutableImage) image).getImage());
                                }
                                jFrame = jFrame;
                            }
                        }
                    });
                }
            }

            private boolean allowOverride(File file) {
                if (file.exists()) {
                    int answer = JOptionPane.showConfirmDialog(Main.this, "Override the file:" + file + "?", "Question?", 0, 3);
                    if (answer == 1) {
                        return false;
                    }
                    return true;
                }
                return true;
            }
        };
        this.menuStopCaptureListener = new ActionListener() { // from class: org.microemu.app.Main.7
            public void actionPerformed(ActionEvent e) {
                Main.this.menuStopCapture.setEnabled(false);
                JFrame jFrame = Main.this;
                synchronized (jFrame) {
                    Main.this.encoder.finish();
                    Main.this.encoder = null;
                    jFrame = jFrame;
                    Main.this.menuStartCapture.setEnabled(true);
                }
            }
        };
        this.menuMIDletNetworkConnectionListener = new ActionListener() { // from class: org.microemu.app.Main.8
            public void actionPerformed(ActionEvent e) {
                Connection.setAllowNetworkConnection(Main.this.menuMIDletNetworkConnection.getState());
            }
        };
        this.menuRecordStoreManagerListener = new ActionListener() { // from class: org.microemu.app.Main.9
            public void actionPerformed(ActionEvent e) {
                if (Main.this.recordStoreManagerDialog == null) {
                    Main.this.recordStoreManagerDialog = new RecordStoreManagerDialog(Main.this, Main.this.common);
                    Main.this.recordStoreManagerDialog.addWindowListener(new WindowAdapter() { // from class: org.microemu.app.Main.9.1
                        public void windowClosing(WindowEvent e2) {
                            Main.this.menuRecordStoreManager.setState(false);
                        }
                    });
                    Main.this.recordStoreManagerDialog.pack();
                    Rectangle window = Config.getWindow("recordStoreManager", new Rectangle(0, 0, 640, 320));
                    Main.this.recordStoreManagerDialog.setBounds(window.x, window.y, window.width, window.height);
                }
                Main.this.recordStoreManagerDialog.setVisible(!Main.this.recordStoreManagerDialog.isVisible());
            }
        };
        this.menuLogConsoleListener = new ActionListener() { // from class: org.microemu.app.Main.10
            public void actionPerformed(ActionEvent e) {
                if (Main.this.logConsoleDialog == null) {
                    Main.this.logConsoleDialog = new SwingLogConsoleDialog(Main.this, Main.this.logQueueAppender);
                    Main.this.logConsoleDialog.addWindowListener(new WindowAdapter() { // from class: org.microemu.app.Main.10.1
                        public void windowClosing(WindowEvent e2) {
                            Main.this.menuLogConsole.setState(false);
                        }
                    });
                    Main.this.logConsoleDialog.pack();
                    Main.this.logConsoleDialog.setFocusableWindowState(false);
                    Rectangle window = Config.getWindow("logConsole", new Rectangle(0, 0, 640, 320));
                    Main.this.logConsoleDialog.setBounds(window.x, window.y, window.width, window.height);
                }
                Main.this.logConsoleDialog.setVisible(!Main.this.logConsoleDialog.isVisible());
            }
        };
        this.actionListenerUpMenu = new ActionListener() { // from class: org.microemu.app.Main.11
            public void actionPerformed(ActionEvent e) {
                SwingDisplayComponent sdc = (SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent();
                int xWidth = sdc.getDisplayImage().getWidth();
                MouseEvent me = new MouseEvent((SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent(), HttpConnection.HTTP_INTERNAL_ERROR, System.currentTimeMillis(), 0, 0 + (xWidth / 3), 2, 1, false);
                sdc.getMouseListener().mousePressed(me);
            }
        };
        this.actionListenerMainMenu = new ActionListener() { // from class: org.microemu.app.Main.12
            public void actionPerformed(ActionEvent e) {
                SwingDisplayComponent sdc = (SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent();
                int xWidth = sdc.getDisplayImage().getWidth();
                MouseEvent me = new MouseEvent((SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent(), HttpConnection.HTTP_INTERNAL_ERROR, System.currentTimeMillis(), 0, xWidth - (xWidth / 3), 2, 1, false);
                sdc.getMouseListener().mousePressed(me);
            }
        };
        this.actionListenerWriteMenu = new ActionListener() { // from class: org.microemu.app.Main.13
            public void actionPerformed(ActionEvent e) {
                SwingDisplayComponent sdc = (SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent();
                int yWidth = sdc.getDisplayImage().getHeight();
                int xWidth = sdc.getDisplayImage().getWidth();
                MouseEvent me = new MouseEvent((SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent(), HttpConnection.HTTP_INTERNAL_ERROR, System.currentTimeMillis(), 0, xWidth / 2, yWidth - 2, 1, false);
                sdc.getMouseListener().mousePressed(me);
            }
        };
        this.menuAboutListener = new ActionListener() { // from class: org.microemu.app.Main.14
            public void actionPerformed(ActionEvent e) {
                SwingDialogWindow.show(Main.this, "About", new SwingAboutDialog(), false);
            }
        };
        this.menuExitListener = new ActionListener() { // from class: org.microemu.app.Main.15
            public void actionPerformed(ActionEvent e) throws IOException {
                JFrame jFrame = Main.this;
                synchronized (jFrame) {
                    if (Main.this.encoder != null) {
                        Main.this.encoder.finish();
                        Main.this.encoder = null;
                    }
                    jFrame = jFrame;
                    if (Main.this.logConsoleDialog != null) {
                        Config.setWindow("logConsole", new Rectangle(Main.this.logConsoleDialog.getX(), Main.this.logConsoleDialog.getY(), Main.this.logConsoleDialog.getWidth(), Main.this.logConsoleDialog.getHeight()), Main.this.logConsoleDialog.isVisible());
                    }
                    if (Main.this.recordStoreManagerDialog != null) {
                        Config.setWindow("recordStoreManager", new Rectangle(Main.this.recordStoreManagerDialog.getX(), Main.this.recordStoreManagerDialog.getY(), Main.this.recordStoreManagerDialog.getWidth(), Main.this.recordStoreManagerDialog.getHeight()), Main.this.recordStoreManagerDialog.isVisible());
                    }
                    if (Main.this.scaledDisplayFrame != null) {
                        Config.setWindow("scaledDisplay", new Rectangle(Main.this.scaledDisplayFrame.getX(), Main.this.scaledDisplayFrame.getY(), 0, 0), false);
                    }
                    Config.setWindow("main", new Rectangle(Main.this.getX(), Main.this.getY(), Main.this.getWidth(), Main.this.getHeight()), true);
                    System.exit(0);
                }
            }
        };
        this.menuSelectDeviceListener = new ActionListener() { // from class: org.microemu.app.Main.16
            public void actionPerformed(ActionEvent e) {
                if (!SwingDialogWindow.show(Main.this, "Select device...", Main.this.selectDevicePanel, true) || Main.this.selectDevicePanel.getSelectedDeviceEntry().equals(Main.this.deviceEntry)) {
                    return;
                }
                int restartMidlet = 1;
                if (MIDletBridge.getCurrentMIDlet() != Main.this.common.getLauncher()) {
                    restartMidlet = JOptionPane.showConfirmDialog(Main.this, "Changing device may trigger MIDlet to the unpredictable state and restart of MIDlet is recommended. \nDo you want to restart the MIDlet? All MIDlet data will be lost.", "Question?", 0, 3);
                }
                if (!Main.this.setDevice(Main.this.selectDevicePanel.getSelectedDeviceEntry())) {
                    return;
                }
                if (restartMidlet == 0) {
                    try {
                        Main.this.common.initMIDlet(true);
                        return;
                    } catch (Exception ex) {
                        System.err.println(ex);
                        return;
                    }
                }
                DeviceDisplay deviceDisplay = DeviceFactory.getDevice().getDeviceDisplay();
                DisplayAccess da = MIDletBridge.getMIDletAccess().getDisplayAccess();
                if (da != null) {
                    da.sizeChanged();
                    deviceDisplay.repaint(0, 0, deviceDisplay.getFullWidth(), deviceDisplay.getFullHeight());
                }
            }
        };
        this.menuScaledDisplayListener = new ActionListener() { // from class: org.microemu.app.Main.17
            private DisplayRepaintListener updateScaledImageListener;

            public void actionPerformed(ActionEvent e) throws NumberFormatException, IOException {
                final JCheckBoxMenuItem selectedZoomLevelMenuItem = (JCheckBoxMenuItem) e.getSource();
                if (selectedZoomLevelMenuItem.isSelected()) {
                    for (int i = 0; i < Main.this.zoomLevels.length; i++) {
                        if (Main.this.zoomLevels[i] != e.getSource()) {
                            Main.this.zoomLevels[i].setSelected(false);
                        }
                    }
                    final int scale = Integer.parseInt(e.getActionCommand());
                    if (Main.this.scaledDisplayFrame != null) {
                        Main.this.emulatorContext.getDisplayComponent().removeDisplayRepaintListener(this.updateScaledImageListener);
                        Main.this.scaledDisplayFrame.dispose();
                    }
                    Main.this.scaledDisplayFrame = new JFrame(Main.this.getTitle());
                    Main.this.scaledDisplayFrame.setContentPane(new JLabel(new ImageIcon()));
                    this.updateScaledImageListener = new DisplayRepaintListener() { // from class: org.microemu.app.Main.17.1
                        @Override // org.microemu.app.ui.DisplayRepaintListener
                        public void repaintInvoked(MutableImage image) {
                            updateScaledImage(scale, Main.this.scaledDisplayFrame);
                            Main.this.scaledDisplayFrame.validate();
                        }
                    };
                    Main.this.scaledDisplayFrame.addWindowListener(new WindowAdapter() { // from class: org.microemu.app.Main.17.2
                        public void windowClosing(WindowEvent event) {
                            selectedZoomLevelMenuItem.setSelected(false);
                        }
                    });
                    Main.this.scaledDisplayFrame.getContentPane().addMouseListener(new MouseListener() { // from class: org.microemu.app.Main.17.3
                        private MouseListener receiver;

                        {
                            this.receiver = ((SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent()).getMouseListener();
                        }

                        public void mouseClicked(MouseEvent e2) {
                            this.receiver.mouseClicked(createAdaptedMouseEvent(e2, scale));
                        }

                        public void mousePressed(MouseEvent e2) {
                            this.receiver.mousePressed(createAdaptedMouseEvent(e2, scale));
                        }

                        public void mouseReleased(MouseEvent e2) {
                            this.receiver.mouseReleased(createAdaptedMouseEvent(e2, scale));
                        }

                        public void mouseEntered(MouseEvent e2) {
                            this.receiver.mouseEntered(createAdaptedMouseEvent(e2, scale));
                        }

                        public void mouseExited(MouseEvent e2) {
                            this.receiver.mouseExited(createAdaptedMouseEvent(e2, scale));
                        }
                    });
                    Main.this.scaledDisplayFrame.getContentPane().addMouseMotionListener(new MouseMotionListener() { // from class: org.microemu.app.Main.17.4
                        private MouseMotionListener receiver;

                        {
                            this.receiver = ((SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent()).getMouseMotionListener();
                        }

                        public void mouseDragged(MouseEvent e2) {
                            this.receiver.mouseDragged(createAdaptedMouseEvent(e2, scale));
                        }

                        public void mouseMoved(MouseEvent e2) {
                            this.receiver.mouseMoved(createAdaptedMouseEvent(e2, scale));
                        }
                    });
                    Main.this.scaledDisplayFrame.getContentPane().addMouseWheelListener(new MouseWheelListener() { // from class: org.microemu.app.Main.17.5
                        private MouseWheelListener receiver;

                        {
                            this.receiver = ((SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent()).getMouseWheelListener();
                        }

                        public void mouseWheelMoved(MouseWheelEvent e2) {
                            MouseWheelEvent adaptedEvent = createAdaptedMouseWheelEvent(e2, scale);
                            this.receiver.mouseWheelMoved(adaptedEvent);
                        }
                    });
                    Main.this.scaledDisplayFrame.addKeyListener(Main.this.devicePanel);
                    updateScaledImage(scale, Main.this.scaledDisplayFrame);
                    Main.this.emulatorContext.getDisplayComponent().addDisplayRepaintListener(this.updateScaledImageListener);
                    Main.this.scaledDisplayFrame.setIconImage(Main.this.getIconImage());
                    Main.this.scaledDisplayFrame.setResizable(false);
                    Point location = Main.this.getLocation();
                    Dimension size = Main.this.getSize();
                    Rectangle window = Config.getWindow("scaledDisplay", new Rectangle(location.x + size.width, location.y, 0, 0));
                    Main.this.scaledDisplayFrame.setLocation(window.x, window.y);
                    Config.setWindow("scaledDisplay", new Rectangle(Main.this.scaledDisplayFrame.getX(), Main.this.scaledDisplayFrame.getY(), 0, 0), false);
                    Main.this.scaledDisplayFrame.pack();
                    Main.this.scaledDisplayFrame.setVisible(true);
                    return;
                }
                Main.this.emulatorContext.getDisplayComponent().removeDisplayRepaintListener(this.updateScaledImageListener);
                Main.this.scaledDisplayFrame.dispose();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public MouseEvent createAdaptedMouseEvent(MouseEvent e, int scale) {
                return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(), e.getX() / scale, e.getY() / scale, e.getClickCount(), e.isPopupTrigger(), e.getButton());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public MouseWheelEvent createAdaptedMouseWheelEvent(MouseWheelEvent e, int scale) {
                return new MouseWheelEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(), e.getX() / scale, e.getY() / scale, e.getClickCount(), e.isPopupTrigger(), e.getScrollType(), e.getScrollAmount(), e.getWheelRotation());
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void updateScaledImage(int scale, JFrame scaledLCDFrame) {
                J2SEMutableImage scaledImage = (J2SEMutableImage) ((SwingDisplayComponent) Main.this.emulatorContext.getDisplayComponent()).getScaledDisplayImage(scale);
                scaledLCDFrame.getContentPane().getIcon().setImage(scaledImage.getImage());
                scaledLCDFrame.getContentPane().repaint();
            }
        };
        this.statusBarListener = new StatusBarListener() { // from class: org.microemu.app.Main.18
            @Override // org.microemu.app.ui.StatusBarListener
            public void statusBarChanged(String text) {
                FontMetrics metrics = Main.this.statusBar.getFontMetrics(Main.this.statusBar.getFont());
                Main.this.statusBar.setPreferredSize(new Dimension(metrics.stringWidth(text), metrics.getHeight()));
                Main.this.statusBar.setText(text);
            }
        };
        this.responseInterfaceListener = new ResponseInterfaceListener() { // from class: org.microemu.app.Main.19
            @Override // org.microemu.app.ui.ResponseInterfaceListener
            public void stateChanged(boolean state) {
                Main.this.menuOpenMIDletFile.setEnabled(state);
                Main.this.menuOpenMIDletURL.setEnabled(state);
                Main.this.menuSelectDevice.setEnabled(state);
                if (Main.this.common.jad.getJarURL() != null) {
                    Main.this.menuSaveForWeb.setEnabled(state);
                } else {
                    Main.this.menuSaveForWeb.setEnabled(false);
                }
            }
        };
        this.componentListener = new AnonymousClass20();
        this.windowListener = new WindowAdapter() { // from class: org.microemu.app.Main.21
            public void windowClosing(WindowEvent ev) {
                Main.this.menuExitListener.actionPerformed((ActionEvent) null);
            }

            public void windowIconified(WindowEvent ev) {
                MIDletBridge.getMIDletAccess(MIDletBridge.getCurrentMIDlet()).pauseApp();
            }

            public void windowDeiconified(WindowEvent ev) {
                try {
                    MIDletBridge.getMIDletAccess(MIDletBridge.getCurrentMIDlet()).startApp();
                } catch (MIDletStateChangeException ex) {
                    System.err.println(ex);
                }
            }
        };
        this.logQueueAppender = new QueueAppender(1024);
        Logger.addAppender(this.logQueueAppender);
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        this.menuOpenMIDletFile = new JMenuItem("Open MIDlet File...");
        this.menuOpenMIDletFile.addActionListener(this.menuOpenMIDletFileListener);
        menuFile.add(this.menuOpenMIDletFile);
        this.menuOpenMIDletURL = new JMenuItem("Open MIDlet URL...");
        this.menuOpenMIDletURL.addActionListener(this.menuOpenMIDletURLListener);
        menuFile.add(this.menuOpenMIDletURL);
        JMenuItem menuItemTmp = new JMenuItem("Close MIDlet");
        menuItemTmp.setAccelerator(KeyStroke.getKeyStroke(87, 2));
        menuItemTmp.addActionListener(this.menuCloseMidletListener);
        menuFile.add(menuItemTmp);
        menuFile.addSeparator();
        JMRUMenu urlsMRU = new JMRUMenu("Recent MIDlets...");
        urlsMRU.addActionListener(new ActionListener() { // from class: org.microemu.app.Main.22
            public void actionPerformed(ActionEvent event) {
                if (event instanceof JMRUMenu.MRUActionEvent) {
                    Common.openMIDletUrlSafe(((MidletURLReference) ((JMRUMenu.MRUActionEvent) event).getSourceMRU()).getUrl());
                    if (Main.this.recordStoreManagerDialog != null) {
                        Main.this.recordStoreManagerDialog.refresh();
                    }
                }
            }
        });
        Config.getUrlsMRU().setListener(urlsMRU);
        menuFile.add(urlsMRU);
        menuFile.addSeparator();
        this.menuSaveForWeb = new JMenuItem("Save for Web...");
        this.menuSaveForWeb.addActionListener(this.menuSaveForWebListener);
        menuFile.add(this.menuSaveForWeb);
        menuFile.addSeparator();
        JMenuItem menuItem = new JMenuItem("Exit");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(81, 2));
        menuItem.addActionListener(this.menuExitListener);
        menuFile.add(menuItem);
        JMenu menuOptions = new JMenu("Options");
        this.menuSelectDevice = new JMenuItem("Select device...");
        this.menuSelectDevice.addActionListener(this.menuSelectDeviceListener);
        menuOptions.add(this.menuSelectDevice);
        JMenu menuScaleLCD = new JMenu("Scaled display");
        menuOptions.add(menuScaleLCD);
        this.zoomLevels = new JCheckBoxMenuItem[3];
        for (int i = 0; i < this.zoomLevels.length; i++) {
            this.zoomLevels[i] = new JCheckBoxMenuItem("x " + (i + 2));
            this.zoomLevels[i].setActionCommand(new StringBuilder().append(i + 2).toString());
            this.zoomLevels[i].addActionListener(this.menuScaledDisplayListener);
            menuScaleLCD.add(this.zoomLevels[i]);
        }
        this.menuStartCapture = new JMenuItem("Start capture to GIF...");
        this.menuStartCapture.addActionListener(this.menuStartCaptureListener);
        menuOptions.add(this.menuStartCapture);
        this.menuStopCapture = new JMenuItem("Stop capture");
        this.menuStopCapture.setEnabled(false);
        this.menuStopCapture.addActionListener(this.menuStopCaptureListener);
        menuOptions.add(this.menuStopCapture);
        this.menuMIDletNetworkConnection = new JCheckBoxMenuItem("MIDlet Network access");
        this.menuMIDletNetworkConnection.setState(true);
        this.menuMIDletNetworkConnection.addActionListener(this.menuMIDletNetworkConnectionListener);
        menuOptions.add(this.menuMIDletNetworkConnection);
        this.menuRecordStoreManager = new JCheckBoxMenuItem("Record Store Manager");
        this.menuRecordStoreManager.setState(false);
        this.menuRecordStoreManager.addActionListener(this.menuRecordStoreManagerListener);
        menuOptions.add(this.menuRecordStoreManager);
        this.menuLogConsole = new JCheckBoxMenuItem("Log console");
        this.menuLogConsole.setState(false);
        this.menuLogConsole.addActionListener(this.menuLogConsoleListener);
        menuOptions.add(this.menuLogConsole);
        menuOptions.addSeparator();
        JCheckBoxMenuItem menuShowMouseCoordinates = new JCheckBoxMenuItem("Mouse coordinates");
        menuShowMouseCoordinates.setState(false);
        menuShowMouseCoordinates.addActionListener(new ActionListener() { // from class: org.microemu.app.Main.23
            public void actionPerformed(ActionEvent event) {
                Main.this.devicePanel.switchShowMouseCoordinates();
            }
        });
        menuOptions.add(menuShowMouseCoordinates);
        JMenu menuHelp = new JMenu("Help");
        JMenuItem menuAbout = new JMenuItem("About");
        menuAbout.addActionListener(this.menuAboutListener);
        menuHelp.add(menuAbout);
        menuBar.add(menuFile);
        menuBar.add(menuOptions);
        menuBar.add(menuHelp);
        setTitle("AVACS Live Chat");
        setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/org/microemu/icon.png")));
        addWindowListener(this.windowListener);
        Config.loadConfig(defaultDevice, this.emulatorContext);
        Logger.setLocationEnabled(Config.isLogConsoleLocationEnabled());
        Rectangle window = Config.getWindow("main", new Rectangle(0, 0, Opcodes.IF_ICMPNE, Opcodes.ISHL));
        setLocation(window.x, window.y);
        getContentPane().add(createContents(getContentPane()), "Center");
        this.selectDevicePanel = new SwingSelectDevicePanel(this.emulatorContext);
        this.common = new Common(this.emulatorContext);
        this.common.setStatusBarListener(this.statusBarListener);
        this.common.setResponseInterfaceListener(this.responseInterfaceListener);
        this.common.loadImplementationsFromConfig();
        this.resizeButton.addActionListener(new ActionListener() { // from class: org.microemu.app.Main.24
            public void actionPerformed(ActionEvent ev) {
                if (Main.this.resizeDeviceDisplayDialog == null) {
                    Main.this.resizeDeviceDisplayDialog = new ResizeDeviceDisplayDialog();
                }
                DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) DeviceFactory.getDevice().getDeviceDisplay();
                Main.this.resizeDeviceDisplayDialog.setDeviceDisplaySize(deviceDisplay.getFullWidth(), deviceDisplay.getFullHeight());
                if (SwingDialogWindow.show(Main.this, "Enter new size...", Main.this.resizeDeviceDisplayDialog, true)) {
                    deviceDisplay.setDisplayRectangle(new Rectangle(0, 0, Main.this.resizeDeviceDisplayDialog.getDeviceDisplayWidth(), Main.this.resizeDeviceDisplayDialog.getDeviceDisplayHeight()));
                    ((SwingDisplayComponent) Main.this.devicePanel.getDisplayComponent()).init();
                    MIDletAccess ma = MIDletBridge.getMIDletAccess();
                    if (ma == null) {
                        return;
                    }
                    DisplayAccess da = ma.getDisplayAccess();
                    if (da != null) {
                        da.sizeChanged();
                        deviceDisplay.repaint(0, 0, deviceDisplay.getFullWidth(), deviceDisplay.getFullHeight());
                    }
                    Main.this.pack();
                }
            }
        });
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BorderLayout());
        statusPanel.add(this.statusBar, "West");
        JMenuBar menuBarD = new JMenuBar();
        JMenuItem menuWrite = new JMenuItem("Write");
        menuWrite.addActionListener(this.actionListenerWriteMenu);
        JMenuItem menuUp = new JMenuItem(" Up ");
        menuUp.addActionListener(this.actionListenerUpMenu);
        JMenuItem menuMain = new JMenuItem("Main");
        menuMain.addActionListener(this.actionListenerMainMenu);
        menuBarD.add(menuWrite);
        menuBarD.add(menuUp);
        menuBarD.add(menuMain);
        setJMenuBar(menuBarD);
        Message.addListener(new SwingErrorMessageDialogPanel(this));
        this.devicePanel.setTransferHandler(new DropTransferHandler());
        Common.openMIDletUrlSafe("avacs.c");
    }

    protected Component createContents(Container parent) {
        this.devicePanel = new SwingDeviceComponent();
        this.devicePanel.addKeyListener(this.devicePanel);
        addKeyListener(this.devicePanel);
        return this.devicePanel;
    }

    public boolean setDevice(DeviceEntry entry) {
        Rectangle size;
        DeviceFactory.getDevice();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            if (entry.getFileName() != null) {
                URL[] urls = {new File(Config.getConfigPath(), entry.getFileName()).toURI().toURL()};
                classLoader = Common.createExtensionsClassLoader(urls);
            }
            this.emulatorContext.getDeviceFontManager().init();
            Device device = DeviceImpl.create(this.emulatorContext, classLoader, entry.getDescriptorLocation(), J2SEDevice.class);
            this.deviceEntry = entry;
            DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) device.getDeviceDisplay();
            if (deviceDisplay.isResizable() && (size = Config.getDeviceEntryDisplaySize(entry)) != null) {
                deviceDisplay.setDisplayRectangle(size);
            }
            this.common.setDevice(device);
            updateDevice();
            return true;
        } catch (MalformedURLException e) {
            Message.error("Error creating device", "Error creating device, " + Message.getCauseMessage(e), e);
            return false;
        } catch (IOException e2) {
            Message.error("Error creating device", "Error creating device, " + Message.getCauseMessage(e2), e2);
            return false;
        } catch (Throwable e3) {
            Message.error("Error creating device", "Error creating device, " + Message.getCauseMessage(e3), e3);
            return false;
        }
    }

    protected void updateDevice() {
        this.devicePanel.init();
        if (((DeviceDisplayImpl) DeviceFactory.getDevice().getDeviceDisplay()).isResizable()) {
            setResizable(true);
            this.resizeButton.setVisible(true);
        } else {
            setResizable(false);
            this.resizeButton.setVisible(false);
        }
        pack();
        this.devicePanel.requestFocus();
    }

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, SecurityException, ConfigurationException, IllegalArgumentException, InvocationTargetException {
        Rectangle size;
        String[] args2 = new String[0];
        List params = new ArrayList();
        StringBuffer debugArgs = new StringBuffer();
        for (int i = 0; i < args2.length; i++) {
            params.add(args2[i]);
            if (debugArgs.length() != 0) {
                debugArgs.append(", ");
            }
            debugArgs.append("[").append(args2[i]).append("]");
        }
        if (params.contains("--headless")) {
            Headless.main(args2);
            return;
        }
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            Logger.error((Throwable) ex);
        }
        Main app = new Main();
        if (args2.length > 0) {
            Logger.debug("arguments", debugArgs.toString());
        }
        if (app.common.initParams(params, app.selectDevicePanel.getSelectedDeviceEntry(), J2SEDevice.class)) {
            app.deviceEntry = app.selectDevicePanel.getSelectedDeviceEntry();
            DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) DeviceFactory.getDevice().getDeviceDisplay();
            if (deviceDisplay.isResizable() && (size = Config.getDeviceEntryDisplaySize(app.deviceEntry)) != null) {
                deviceDisplay.setDisplayRectangle(size);
            }
        }
        app.updateDevice();
        app.validate();
        app.setVisible(true);
        if (Config.isWindowOnStart("logConsole")) {
            app.menuLogConsoleListener.actionPerformed((ActionEvent) null);
            app.menuLogConsole.setSelected(true);
        }
        if (Config.isWindowOnStart("recordStoreManager")) {
            app.menuRecordStoreManagerListener.actionPerformed((ActionEvent) null);
            app.menuRecordStoreManager.setSelected(true);
        }
        try {
        } catch (NoSuchElementException e) {
        }
        app.common.initMIDlet(true);
        app.addComponentListener(app.componentListener);
        app.responseInterfaceListener.stateChanged(true);
    }

    /* loaded from: avacs.jar:org/microemu/app/Main$CountTimerTask.class */
    private abstract class CountTimerTask extends TimerTask {
        protected int counter;

        public CountTimerTask(int counter) {
            this.counter = counter;
        }
    }
}
