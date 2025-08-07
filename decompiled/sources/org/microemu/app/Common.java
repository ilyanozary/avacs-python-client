package org.microemu.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.microemu.EmulatorContext;
import org.microemu.Injected;
import org.microemu.MIDletAccess;
import org.microemu.MIDletBridge;
import org.microemu.MIDletContext;
import org.microemu.MIDletEntry;
import org.microemu.MicroEmulator;
import org.microemu.RecordStoreManager;
import org.microemu.app.classloader.ExtensionsClassLoader;
import org.microemu.app.classloader.MIDletClassLoader;
import org.microemu.app.classloader.MIDletClassLoaderConfig;
import org.microemu.app.launcher.Launcher;
import org.microemu.app.ui.Message;
import org.microemu.app.ui.ResponseInterfaceListener;
import org.microemu.app.ui.StatusBarListener;
import org.microemu.app.util.DeviceEntry;
import org.microemu.app.util.FileRecordStoreManager;
import org.microemu.app.util.IOUtils;
import org.microemu.app.util.MIDletResourceLoader;
import org.microemu.app.util.MIDletSystemProperties;
import org.microemu.app.util.MIDletThread;
import org.microemu.app.util.MIDletTimer;
import org.microemu.app.util.MIDletTimerTask;
import org.microemu.app.util.MidletURLReference;
import org.microemu.device.Device;
import org.microemu.device.DeviceFactory;
import org.microemu.device.impl.DeviceDisplayImpl;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.impl.Rectangle;
import org.microemu.log.Logger;
import org.microemu.log.StdOutAppender;
import org.microemu.microedition.ImplFactory;
import org.microemu.microedition.ImplementationInitialization;
import org.microemu.microedition.io.ConnectorImpl;
import org.microemu.util.Base64Coder;
import org.microemu.util.JadMidletEntry;
import org.microemu.util.JadProperties;
import org.microemu.util.MemoryRecordStoreManager;

/* loaded from: avacs.jar:org/microemu/app/Common.class */
public class Common implements MicroEmulator, CommonInterface {
    protected EmulatorContext emulatorContext;
    private static Common instance;
    private static Launcher launcher;
    private static StatusBarListener statusBarListener = null;
    private RecordStoreManager recordStoreManager;
    private ExtensionsClassLoader extensionsClassLoader;
    private MIDletClassLoaderConfig mIDletClassLoaderConfig;
    protected JadProperties jad = new JadProperties();
    private JadProperties manifest = new JadProperties();
    private ResponseInterfaceListener responseInterfaceListener = null;
    private Vector extensions = new Vector();
    private boolean useSystemClassLoader = false;
    private boolean autoTests = false;
    private String propertiesJad = null;
    private String midletClassOrUrl = null;
    private String jadURL = null;
    private Object destroyNotify = new Object();
    private boolean exitOnMIDletDestroy = false;

    public Common(EmulatorContext context) {
        instance = this;
        this.emulatorContext = context;
        ImplFactory.instance();
        MIDletSystemProperties.initContext();
        ImplFactory.registerGCF(ImplFactory.DEFAULT, new ConnectorImpl());
        MIDletBridge.setMicroEmulator(this);
    }

    @Override // org.microemu.MicroEmulator
    public RecordStoreManager getRecordStoreManager() {
        return this.recordStoreManager;
    }

    public void setRecordStoreManager(RecordStoreManager manager) {
        this.recordStoreManager = manager;
    }

    @Override // org.microemu.MicroEmulator
    public String getAppProperty(String key) {
        if (key.equals("microedition.platform")) {
            return "AVACSEmulator";
        }
        if (key.equals("microedition.profiles")) {
            return "MIDP-2.0";
        }
        if (key.equals("microedition.configuration")) {
            return "CLDC-1.0";
        }
        if (key.equals("microedition.locale")) {
            return Locale.getDefault().getLanguage();
        }
        if (key.equals("microedition.encoding")) {
            return System.getProperty("file.encoding");
        }
        String result = this.jad.getProperty(key);
        if (result == null) {
            result = this.manifest.getProperty(key);
        }
        return result;
    }

    @Override // org.microemu.MicroEmulator
    public InputStream getResourceAsStream(String name) {
        return this.emulatorContext.getResourceAsStream(name);
    }

    @Override // org.microemu.MicroEmulator
    public void notifyDestroyed(MIDletContext midletContext) {
        Logger.debug("notifyDestroyed");
        notifyImplementationMIDletDestroyed();
        startLauncher(midletContext);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v7 */
    @Override // org.microemu.MicroEmulator
    public void destroyMIDletContext(MIDletContext midletContext) {
        if (midletContext != null && MIDletBridge.getMIDletContext() == midletContext && !midletContext.isLauncher()) {
            Logger.debug("destroyMIDletContext");
        }
        MIDletThread.contextDestroyed(midletContext);
        ?? r0 = this.destroyNotify;
        synchronized (r0) {
            this.destroyNotify.notifyAll();
            r0 = r0;
        }
    }

    @Override // org.microemu.MicroEmulator
    public Launcher getLauncher() {
        return launcher;
    }

    public static void dispose() {
        try {
            MIDletAccess midletAccess = MIDletBridge.getMIDletAccess();
            if (midletAccess != null) {
                midletAccess.destroyApp(true);
            }
        } catch (MIDletStateChangeException ex) {
            Logger.error((Throwable) ex);
        }
        DeviceFactory.getDevice().getInputMethod().dispose();
    }

    public static boolean isMIDletUrlExtension(String nameString) {
        int s;
        if (nameString == null) {
            return false;
        }
        if ((nameString.startsWith("http://") || nameString.startsWith("https://")) && (s = nameString.lastIndexOf(63)) != -1) {
            nameString = nameString.substring(0, s);
        }
        int end = nameString.lastIndexOf(46);
        if (end == -1) {
            return false;
        }
        return nameString.substring(end + 1, nameString.length()).toLowerCase(Locale.ENGLISH).equals("jad") || nameString.substring(end + 1, nameString.length()).toLowerCase(Locale.ENGLISH).equals("jar");
    }

    public static void openMIDletUrlSafe(String urlString) {
        try {
            getInstance().openMIDletUrl(urlString);
        } catch (IOException e) {
        }
    }

    protected void openMIDletUrl(String urlString) throws IOException {
        this.midletClassOrUrl = urlString;
        if (!this.autoTests) {
            openMIDletUrl(urlString, createMIDletClassLoader(true));
        } else {
            runAutoTests(urlString, false);
        }
    }

    private void runAutoTests(final String urlString, final boolean exitAtTheEnd) {
        final Common common = getInstance();
        Thread t = new Thread("AutoTestsThread") { // from class: org.microemu.app.Common.1
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v53, types: [java.lang.Object] */
            /* JADX WARN: Type inference failed for: r0v54, types: [java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r0v55 */
            /* JADX WARN: Type inference failed for: r0v58, types: [java.lang.Object] */
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws InterruptedException, IOException {
                boolean firstJad = true;
                while (true) {
                    common.jad.clear();
                    Logger.debug("AutoTests open jad", urlString);
                    try {
                        common.jad = Common.loadJadProperties(urlString);
                        firstJad = false;
                        Iterator it = common.jad.getMidletEntries().iterator();
                        if (!it.hasNext()) {
                            break;
                        }
                        JadMidletEntry jadMidletEntry = (JadMidletEntry) it.next();
                        String midletClassName = jadMidletEntry.getClassName();
                        boolean firstJar = true;
                        while (true) {
                            MIDletClassLoader midletClassLoader = Common.this.createMIDletClassLoader(true);
                            String tmpURL = Common.this.saveJar2TmpFile(urlString, firstJar);
                            if (tmpURL == null) {
                                break;
                            }
                            firstJar = false;
                            try {
                                Common.this.loadJar(urlString, tmpURL, midletClassLoader);
                                Class midletClass = midletClassLoader.loadClass(midletClassName);
                                Logger.debug("AutoTests start class", midletClassName);
                                MIDletContext context = Common.this.startMidlet(midletClass, MIDletBridge.getMIDletAccess());
                                if (MIDletBridge.getMIDletContext() == context) {
                                    ?? r0 = Common.this.destroyNotify;
                                    synchronized (r0) {
                                        try {
                                            r0 = Common.this.destroyNotify;
                                            r0.wait();
                                        } catch (InterruptedException e) {
                                            r0 = r0;
                                            return;
                                        }
                                    }
                                }
                                while (MIDletThread.hasRunningThreads(context)) {
                                    try {
                                        Thread.sleep(100L);
                                    } catch (InterruptedException e2) {
                                    }
                                }
                                Logger.debug("AutoTests ends");
                            } catch (ClassNotFoundException e3) {
                                Logger.debug(e3);
                            }
                        }
                        Logger.debug("AutoTests no new jar");
                    } catch (IOException e4) {
                        if (firstJad) {
                            Logger.debug(e4);
                        } else {
                            Logger.debug("AutoTests no more tests");
                        }
                    }
                }
                Message.error("MIDlet Suite has no entries");
                if (exitAtTheEnd) {
                    System.exit(0);
                }
            }
        };
        t.start();
    }

    protected String saveJar2TmpFile(String jarUrl, boolean reportError) throws IOException {
        InputStream is = null;
        try {
            try {
                URL url = new URL(this.jad.getJarURL());
                URLConnection conn = url.openConnection();
                if (url.getUserInfo() != null) {
                    String userInfo = new String(Base64Coder.encode(url.getUserInfo().getBytes("UTF-8")));
                    conn.setRequestProperty("Authorization", "Basic " + userInfo);
                }
                is = conn.getInputStream();
                File tmpDir = null;
                String systemTmpDir = MIDletSystemProperties.getSystemProperty("java.io.tmpdir");
                if (systemTmpDir != null) {
                    tmpDir = new File(systemTmpDir, "microemulator-apps-" + MIDletSystemProperties.getSystemProperty("user.name"));
                    if (!tmpDir.exists() && !tmpDir.mkdirs()) {
                        tmpDir = null;
                    }
                }
                File tmp = File.createTempFile("me2-app-", ".jar", tmpDir);
                tmp.deleteOnExit();
                IOUtils.copyToFile(is, tmp);
                String canonicalFileClassLoaderURL = IOUtils.getCanonicalFileClassLoaderURL(tmp);
                IOUtils.closeQuietly(is);
                return canonicalFileClassLoaderURL;
            } catch (IOException e) {
                if (reportError) {
                    Message.error("Unable to open jar " + jarUrl, e);
                }
                IOUtils.closeQuietly(is);
                return null;
            }
        } catch (Throwable th) {
            IOUtils.closeQuietly(is);
            throw th;
        }
    }

    private void openMIDletUrl(String urlString, MIDletClassLoader midletClassLoader) throws IOException {
        try {
            setStatusBar("Loading...");
            this.jad.clear();
            if (urlString.toLowerCase().endsWith(".jad")) {
                Logger.debug("openJad", urlString);
                this.jad = loadJadProperties(urlString);
                loadJar(urlString, this.jad.getJarURL(), midletClassLoader);
            } else {
                this.jad.setCorrectedJarURL(urlString);
                loadJar(null, urlString, midletClassLoader);
            }
            Config.getUrlsMRU().push(new MidletURLReference(this.jad.getSuiteName(), urlString));
        } catch (FileNotFoundException ex) {
            Message.error("File Not found", urlString, ex);
        } catch (ClassNotFoundException ex2) {
            Logger.error((Throwable) ex2);
            throw new IOException(ex2.getMessage());
        } catch (IllegalArgumentException ex3) {
            Logger.error("Cannot open jad", urlString, ex3);
        } catch (NullPointerException ex4) {
            Logger.error("Cannot open jad", urlString, ex4);
        } catch (MalformedURLException ex5) {
            throw ex5;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MIDletContext startMidlet(Class midletClass, MIDletAccess previousMidletAccess) {
        if (previousMidletAccess != null) {
            try {
                previousMidletAccess.destroyApp(true);
            } catch (Throwable e) {
                Message.error("Unable to destroy MIDlet, " + Message.getCauseMessage(e), e);
            }
        }
        MIDletContext context = new MIDletContext();
        MIDletBridge.setThreadMIDletContext(context);
        MIDletBridge.getRecordStoreManager().init(MIDletBridge.getMicroEmulator());
        try {
            try {
                Object object = midletClass.newInstance();
                if (!(object instanceof MIDlet)) {
                    Message.error("Error starting MIDlet", "Class " + midletClass.getName() + " should extend MIDlet");
                    MIDletBridge.setThreadMIDletContext(null);
                    return null;
                }
                MIDlet m = (MIDlet) object;
                try {
                    if (context.getMIDlet() != m) {
                        throw new Error("MIDlet Context corrupted");
                    }
                    context.getMIDletAccess().startApp();
                    launcher.setCurrentMIDlet(m);
                    notifyImplementationMIDletStart();
                    MIDletBridge.setThreadMIDletContext(null);
                    return context;
                } catch (Throwable e2) {
                    Message.error("Error starting MIDlet", "Unable to start MIDlet, " + Message.getCauseMessage(e2), e2);
                    MIDletBridge.destroyMIDletContext(context);
                    MIDletBridge.setThreadMIDletContext(null);
                    return null;
                }
            } catch (Throwable th) {
                MIDletBridge.setThreadMIDletContext(null);
                throw th;
            }
        } catch (Throwable e3) {
            Message.error("Error starting MIDlet", "Unable to create MIDlet, " + Message.getCauseMessage(e3), e3);
            MIDletBridge.destroyMIDletContext(context);
            MIDletBridge.setThreadMIDletContext(null);
            return null;
        }
    }

    protected void startLauncher(MIDletContext midletContext) {
        if (midletContext != null && midletContext.isLauncher()) {
            return;
        }
        if (midletContext != null) {
            try {
                MIDletAccess previousMidletAccess = midletContext.getMIDletAccess();
                if (previousMidletAccess != null) {
                    previousMidletAccess.destroyApp(true);
                }
            } catch (Throwable e) {
                Logger.error("destroyApp error", e);
            }
            if (this.exitOnMIDletDestroy) {
                System.exit(0);
            }
        }
        try {
            try {
                launcher = new Launcher(this);
                MIDletBridge.getMIDletAccess(launcher).startApp();
                launcher.setCurrentMIDlet(launcher);
            } catch (Throwable e2) {
                Message.error("Unable to start launcher MIDlet, " + Message.getCauseMessage(e2), e2);
                handleStartMidletException(e2);
                MIDletBridge.setThreadMIDletContext(null);
            }
        } finally {
            MIDletBridge.setThreadMIDletContext(null);
        }
    }

    public void setStatusBarListener(StatusBarListener listener) {
        statusBarListener = listener;
    }

    @Override // org.microemu.MicroEmulator
    public int checkPermission(String permission) {
        return MIDletSystemProperties.getPermission(permission);
    }

    @Override // org.microemu.MicroEmulator
    public boolean platformRequest(String URL) {
        return this.emulatorContext.platformRequest(URL);
    }

    public void setResponseInterfaceListener(ResponseInterfaceListener listener) {
        this.responseInterfaceListener = listener;
    }

    protected void handleStartMidletException(Throwable e) {
    }

    protected boolean describeJarProblem(URL jarUrl, MIDletClassLoader midletClassLoader) throws IOException {
        InputStream is = null;
        JarInputStream jis = null;
        try {
            String message = "Unable to open jar " + jarUrl;
            try {
                URLConnection conn = jarUrl.openConnection();
                try {
                    is = conn.getInputStream();
                    try {
                        jis = new JarInputStream(is);
                        try {
                            JarEntry entry = jis.getNextJarEntry();
                            if (entry == null) {
                                Message.error("Empty jar " + jarUrl);
                                IOUtils.closeQuietly(jis);
                                IOUtils.closeQuietly(is);
                                return true;
                            }
                            while (jis.getNextJarEntry() != null) {
                            }
                            IOUtils.closeQuietly(jis);
                            IOUtils.closeQuietly(is);
                            return false;
                        } catch (ZipException e) {
                            Message.error("Problem reading jar " + jarUrl, e);
                            IOUtils.closeQuietly(jis);
                            IOUtils.closeQuietly(is);
                            return true;
                        } catch (IOException e2) {
                            Message.error("Problem reading jar " + jarUrl, e2);
                            IOUtils.closeQuietly(jis);
                            IOUtils.closeQuietly(is);
                            return true;
                        }
                    } catch (IOException e3) {
                        Message.error(message, e3);
                        IOUtils.closeQuietly(jis);
                        IOUtils.closeQuietly(is);
                        return true;
                    }
                } catch (FileNotFoundException e4) {
                    Message.error("The system cannot find the jar file " + jarUrl, e4);
                    IOUtils.closeQuietly((InputStream) null);
                    IOUtils.closeQuietly(is);
                    return true;
                } catch (IOException e5) {
                    Message.error(message, e5);
                    IOUtils.closeQuietly((InputStream) null);
                    IOUtils.closeQuietly(is);
                    return true;
                }
            } catch (IOException e6) {
                Message.error(message, e6);
                IOUtils.closeQuietly((InputStream) null);
                IOUtils.closeQuietly((InputStream) null);
                return true;
            }
        } catch (Throwable th) {
            IOUtils.closeQuietly(jis);
            IOUtils.closeQuietly(is);
            throw th;
        }
    }

    protected void loadJar(String jadUrl, String jarUrl, MIDletClassLoader midletClassLoader) throws ClassNotFoundException {
        URL url;
        if (jarUrl == null) {
            throw new ClassNotFoundException("Cannot find MIDlet-Jar-URL property in jad");
        }
        Logger.debug("openJar", jarUrl);
        dispose();
        MIDletBridge.clear();
        setResponseInterface(false);
        try {
            try {
                url = new URL(jarUrl);
            } catch (MalformedURLException ex) {
                if (jadUrl == null) {
                    Logger.error("Unable to find jar url", ex);
                    setResponseInterface(true);
                    setResponseInterface(true);
                    return;
                }
                try {
                    url = new URL(String.valueOf(jadUrl.substring(0, jadUrl.lastIndexOf(47) + 1)) + jarUrl);
                    this.jad.setCorrectedJarURL(url.toExternalForm());
                    Logger.debug("openJar url", url);
                } catch (MalformedURLException ex1) {
                    Logger.error("Unable to find jar url", ex1);
                    setResponseInterface(true);
                    setResponseInterface(true);
                    return;
                }
            }
            if (url.getUserInfo() != null) {
                String tmpURL = saveJar2TmpFile(jarUrl, true);
                if (tmpURL == null) {
                    setResponseInterface(true);
                    return;
                } else {
                    try {
                        url = new URL(tmpURL);
                    } catch (MalformedURLException e) {
                        Logger.error("Unable to open tmporary jar url", e);
                    }
                }
            }
            midletClassLoader.addURL(url);
            Launcher.removeMIDletEntries();
            this.manifest.clear();
            InputStream is = null;
            try {
                try {
                    is = midletClassLoader.getResourceAsStream("META-INF/MANIFEST.MF");
                } finally {
                    IOUtils.closeQuietly(is);
                }
            } catch (IOException e2) {
                Message.error("Unable to read MANIFEST", e2);
                IOUtils.closeQuietly(is);
            }
            if (is == null) {
                if (!describeJarProblem(url, midletClassLoader)) {
                    Message.error("Unable to find MANIFEST in MIDlet jar");
                }
                setResponseInterface(true);
                return;
            }
            this.manifest.read(is);
            Attributes attributes = this.manifest.getMainAttributes();
            Iterator it = attributes.keySet().iterator();
            while (it.hasNext()) {
                Attributes.Name key = (Attributes.Name) it.next();
                String value = (String) attributes.get(key);
                this.jad.getMainAttributes().put(key, value);
            }
            Launcher.setSuiteName(this.jad.getSuiteName());
            Enumeration e3 = this.jad.getMidletEntries().elements();
            while (e3.hasMoreElements()) {
                JadMidletEntry jadEntry = (JadMidletEntry) e3.nextElement();
                Class midletClass = midletClassLoader.loadClass(jadEntry.getClassName());
                Launcher.addMIDletEntry(new MIDletEntry(jadEntry.getName(), midletClass));
            }
            startLauncher(MIDletBridge.getMIDletContext());
            setStatusBar("");
            setResponseInterface(true);
        } catch (Throwable th) {
            setResponseInterface(true);
            throw th;
        }
    }

    public Device getDevice() {
        return DeviceFactory.getDevice();
    }

    public void setDevice(Device device) throws IOException, NumberFormatException {
        MIDletSystemProperties.setDevice(device);
        DeviceFactory.setDevice(device);
    }

    private static Common getInstance() {
        return instance;
    }

    public static void setStatusBar(String text) {
        if (statusBarListener != null) {
            statusBarListener.statusBarChanged(text);
        }
    }

    private void setResponseInterface(boolean state) {
        if (this.responseInterfaceListener != null) {
            this.responseInterfaceListener.stateChanged(state);
        }
    }

    public void registerImplementation(String implClassName, Map properties, boolean notFoundError) throws IllegalAccessException, NoSuchMethodException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Class implClass = getExtensionsClassLoader().loadClass(implClassName);
            if (ImplementationInitialization.class.isAssignableFrom(implClass)) {
                Object inst = implClass.newInstance();
                Map parameters = new HashMap();
                parameters.put(ImplementationInitialization.PARAM_EMULATOR_ID, Config.getEmulatorID());
                if (properties != null) {
                    parameters.putAll(properties);
                } else {
                    Map extensions = Config.getExtensions();
                    Map prop = (Map) extensions.get(implClassName);
                    if (prop != null) {
                        parameters.putAll(prop);
                    }
                }
                ((ImplementationInitialization) inst).registerImplementation(parameters);
                Logger.debug("implementation registered", implClassName);
                this.extensions.add(inst);
                return;
            }
            Logger.debug("initialize implementation", implClassName);
            boolean isStatic = true;
            try {
                Constructor c = implClass.getConstructor(null);
                if (Modifier.isPublic(c.getModifiers())) {
                    isStatic = false;
                    implClass.newInstance();
                }
            } catch (NoSuchMethodException e) {
            }
            if (isStatic) {
                try {
                    Method getinst = implClass.getMethod("instance", null);
                    if (Modifier.isStatic(getinst.getModifiers())) {
                        getinst.invoke(implClass, null);
                    } else {
                        Logger.debug("No known way to initialize implementation class");
                    }
                } catch (NoSuchMethodException e2) {
                    Logger.debug("No known way to initialize implementation class");
                } catch (InvocationTargetException e3) {
                    Logger.debug("Unable to initialize Implementation", e3.getCause());
                }
            }
        } catch (ClassNotFoundException e4) {
            if (notFoundError) {
                Logger.error("Implementation initialization", e4);
            } else {
                Logger.warn("Implementation initialization " + e4);
            }
        } catch (IllegalAccessException e5) {
            Logger.error("Implementation initialization", e5);
        } catch (InstantiationException e6) {
            Logger.error("Implementation initialization", e6);
        }
    }

    public void loadImplementationsFromConfig() throws IllegalAccessException, NoSuchMethodException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Map extensions = Config.getExtensions();
        for (Map.Entry entry : extensions.entrySet()) {
            registerImplementation((String) entry.getKey(), (Map) entry.getValue(), false);
        }
    }

    public void notifyImplementationMIDletStart() {
        Iterator iterator = this.extensions.iterator();
        while (iterator.hasNext()) {
            ImplementationInitialization impl = (ImplementationInitialization) iterator.next();
            impl.notifyMIDletStart();
        }
    }

    public void notifyImplementationMIDletDestroyed() {
        Iterator iterator = this.extensions.iterator();
        while (iterator.hasNext()) {
            ImplementationInitialization impl = (ImplementationInitialization) iterator.next();
            impl.notifyMIDletDestroyed();
        }
    }

    public boolean initParams(List params, DeviceEntry defaultDevice, Class defaultDeviceClass) throws IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, SecurityException, ConfigurationException, IllegalArgumentException, InvocationTargetException {
        boolean defaultDeviceSelected = false;
        MIDletClassLoaderConfig clConfig = new MIDletClassLoaderConfig();
        Class deviceClass = null;
        String deviceDescriptorLocation = null;
        int overrideDeviceWidth = -1;
        int overrideDeviceHeight = -1;
        RecordStoreManager paramRecordStoreManager = null;
        Iterator argsIterator = params.iterator();
        while (true) {
            try {
                if (!argsIterator.hasNext()) {
                    break;
                }
                String arg = (String) argsIterator.next();
                argsIterator.remove();
                if (arg.equals("--help") || arg.equals("-help")) {
                    System.out.println(usage());
                    System.exit(0);
                } else if (arg.equals("--id")) {
                    Config.setEmulatorID((String) argsIterator.next());
                    argsIterator.remove();
                } else if (arg.equals("--appclasspath") || arg.equals("-appclasspath") || arg.equals("-appcp")) {
                    if (clConfig == null) {
                        throw new ConfigurationException("Wrong command line argument order");
                    }
                    clConfig.addAppClassPath((String) argsIterator.next());
                    argsIterator.remove();
                } else if (arg.equals("--appclass")) {
                    if (clConfig == null) {
                        throw new ConfigurationException("Wrong command line argument order");
                    }
                    clConfig.addAppClass((String) argsIterator.next());
                    argsIterator.remove();
                } else if (arg.startsWith("-Xautotest:")) {
                    this.autoTests = true;
                    this.jadURL = arg.substring("-Xautotest:".length());
                } else if (arg.equals("-Xautotest")) {
                    this.autoTests = true;
                } else if (arg.equals("--propertiesjad")) {
                    File file = new File((String) argsIterator.next());
                    argsIterator.remove();
                    this.propertiesJad = file.exists() ? IOUtils.getCanonicalFileURL(file) : arg;
                } else if (arg.equals("--appclassloader")) {
                    if (clConfig == null) {
                        Message.error("Error", "Wrong command line argument order");
                        break;
                    }
                    clConfig.setDelegationType((String) argsIterator.next());
                    argsIterator.remove();
                } else if (arg.equals("--usesystemclassloader")) {
                    this.useSystemClassLoader = true;
                    clConfig.setDelegationType("system");
                } else if (arg.equals("-d") || arg.equals("--device")) {
                    if (argsIterator.hasNext()) {
                        String tmpDevice = (String) argsIterator.next();
                        argsIterator.remove();
                        if (!tmpDevice.toLowerCase().endsWith(".xml")) {
                            try {
                                deviceClass = Class.forName(tmpDevice);
                            } catch (ClassNotFoundException e) {
                            }
                        }
                        if (deviceClass == null) {
                            deviceDescriptorLocation = tmpDevice;
                        }
                    }
                } else if (arg.equals("--resizableDevice")) {
                    overrideDeviceWidth = Integer.parseInt((String) argsIterator.next());
                    argsIterator.remove();
                    overrideDeviceHeight = Integer.parseInt((String) argsIterator.next());
                    argsIterator.remove();
                    deviceDescriptorLocation = DeviceImpl.RESIZABLE_LOCATION;
                } else if (arg.equals("--rms")) {
                    if (argsIterator.hasNext()) {
                        String tmpRms = (String) argsIterator.next();
                        argsIterator.remove();
                        if (tmpRms.equals("file")) {
                            paramRecordStoreManager = new FileRecordStoreManager();
                        } else if (tmpRms.equals("memory")) {
                            paramRecordStoreManager = new MemoryRecordStoreManager();
                        }
                    }
                } else if (arg.equals("--classpath") || arg.equals("-classpath") || arg.equals("-cp")) {
                    getExtensionsClassLoader().addClasspath((String) argsIterator.next());
                    argsIterator.remove();
                } else if (arg.equals("--impl")) {
                    registerImplementation((String) argsIterator.next(), null, true);
                    argsIterator.remove();
                } else if (arg.equals("--quit")) {
                    this.exitOnMIDletDestroy = true;
                } else if (arg.equals("--logCallLocation")) {
                    Logger.setLocationEnabled(Boolean.valueOf((String) argsIterator.next()).booleanValue());
                } else if (arg.equals("--traceClassLoading")) {
                    MIDletClassLoader.traceClassLoading = true;
                } else if (arg.equals("--traceSystemClassLoading")) {
                    MIDletClassLoader.traceSystemClassLoading = true;
                } else if (arg.equals("--enhanceCatchBlock")) {
                    MIDletClassLoader.enhanceCatchBlock = true;
                } else if (arg.equals("--quiet")) {
                    StdOutAppender.enabled = false;
                } else if (!arg.equals("--headless")) {
                    if (arg.startsWith("--")) {
                        Logger.warn("Unknown argument " + arg);
                    } else {
                        this.midletClassOrUrl = arg;
                    }
                }
            } catch (ConfigurationException e2) {
                Message.error("Error", e2.getMessage(), e2);
                return false;
            }
        }
        this.mIDletClassLoaderConfig = clConfig;
        ClassLoader classLoader = getExtensionsClassLoader();
        if (deviceDescriptorLocation != null) {
            try {
                setDevice(DeviceImpl.create(this.emulatorContext, classLoader, deviceDescriptorLocation, defaultDeviceClass));
                DeviceDisplayImpl deviceDisplay = (DeviceDisplayImpl) DeviceFactory.getDevice().getDeviceDisplay();
                if (overrideDeviceWidth != -1 && overrideDeviceHeight != -1) {
                    deviceDisplay.setDisplayRectangle(new Rectangle(0, 0, overrideDeviceWidth, overrideDeviceHeight));
                }
            } catch (IOException ex) {
                Logger.error((Throwable) ex);
            }
        }
        if (DeviceFactory.getDevice() == null) {
            try {
                if (deviceClass == null) {
                    if (defaultDevice.getFileName() != null) {
                        URL[] urls = {new File(Config.getConfigPath(), defaultDevice.getFileName()).toURI().toURL()};
                        classLoader = createExtensionsClassLoader(urls);
                    }
                    setDevice(DeviceImpl.create(this.emulatorContext, classLoader, defaultDevice.getDescriptorLocation(), defaultDeviceClass));
                    defaultDeviceSelected = true;
                } else {
                    DeviceImpl device = (DeviceImpl) deviceClass.newInstance();
                    device.init(this.emulatorContext);
                    setDevice(device);
                }
            } catch (IOException ex2) {
                Logger.error((Throwable) ex2);
            } catch (IllegalAccessException ex3) {
                Logger.error((Throwable) ex3);
            } catch (InstantiationException ex4) {
                Logger.error((Throwable) ex4);
            }
        }
        try {
            launcher = new Launcher(this);
            launcher.setCurrentMIDlet(launcher);
            MIDletBridge.setThreadMIDletContext(null);
            if (getRecordStoreManager() == null) {
                if (paramRecordStoreManager == null) {
                    String className = Config.getRecordStoreManagerClassName();
                    if (className != null) {
                        try {
                            Class clazz = Class.forName(className);
                            setRecordStoreManager((RecordStoreManager) clazz.newInstance());
                        } catch (ClassNotFoundException ex5) {
                            Logger.error((Throwable) ex5);
                        } catch (IllegalAccessException ex6) {
                            Logger.error((Throwable) ex6);
                        } catch (InstantiationException ex7) {
                            Logger.error((Throwable) ex7);
                        }
                    }
                    if (getRecordStoreManager() == null) {
                        setRecordStoreManager(new FileRecordStoreManager());
                    }
                } else {
                    setRecordStoreManager(paramRecordStoreManager);
                }
            }
            return defaultDeviceSelected;
        } catch (Throwable th) {
            MIDletBridge.setThreadMIDletContext(null);
            throw th;
        }
    }

    private static ExtensionsClassLoader getExtensionsClassLoader() {
        if (instance.extensionsClassLoader == null) {
            instance.extensionsClassLoader = new ExtensionsClassLoader(new URL[0], instance.getClass().getClassLoader());
        }
        return instance.extensionsClassLoader;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public MIDletClassLoader createMIDletClassLoader(boolean forJad) {
        MIDletClassLoader mcl = new MIDletClassLoader(getExtensionsClassLoader());
        if (!Serializable.class.isAssignableFrom(Injected.class)) {
            Logger.error("classpath configuration error, Wrong Injected class detected. microemu-injected module should be after microemu-javase in eclipse");
        }
        if (this.mIDletClassLoaderConfig != null) {
            try {
                mcl.configure(this.mIDletClassLoaderConfig, forJad);
            } catch (MalformedURLException e) {
                Message.error("Error", "Unable to find MIDlet classes, " + Message.getCauseMessage(e), e);
            }
        }
        mcl.disableClassPreporcessing(Injected.class);
        mcl.disableClassPreporcessing(MIDletThread.class);
        mcl.disableClassPreporcessing(MIDletTimer.class);
        mcl.disableClassPreporcessing(MIDletTimerTask.class);
        MIDletResourceLoader.classLoader = mcl;
        return mcl;
    }

    public static ClassLoader createExtensionsClassLoader(URL[] urls) {
        return new ExtensionsClassLoader(urls, getExtensionsClassLoader());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static JadProperties loadJadProperties(String urlString) throws IOException {
        JadProperties properties = new JadProperties();
        URL url = new URL(urlString);
        if (url.getUserInfo() == null) {
            properties.read(url.openStream());
        } else {
            URLConnection cn = url.openConnection();
            String userInfo = new String(Base64Coder.encode(url.getUserInfo().getBytes("UTF-8")));
            cn.setRequestProperty("Authorization", "Basic " + userInfo);
            properties.read(cn.getInputStream());
        }
        return properties;
    }

    @Override // org.microemu.app.CommonInterface
    public void initMIDlet(boolean startMidlet) throws ClassNotFoundException {
        Class midletClass = null;
        if (this.midletClassOrUrl != null && isMIDletUrlExtension(this.midletClassOrUrl)) {
            try {
                File file = new File(this.midletClassOrUrl);
                String url = file.exists() ? IOUtils.getCanonicalFileURL(file) : this.midletClassOrUrl;
                openMIDletUrl(url);
            } catch (IOException exception) {
                Logger.error("Cannot load " + this.midletClassOrUrl + " URL", exception);
            }
        } else if (this.midletClassOrUrl != null) {
            this.useSystemClassLoader = this.mIDletClassLoaderConfig.isClassLoaderDisabled();
            if (!this.useSystemClassLoader) {
                MIDletClassLoader classLoader = createMIDletClassLoader(false);
                try {
                    classLoader.addClassURL(this.midletClassOrUrl);
                    midletClass = classLoader.loadClass(this.midletClassOrUrl);
                } catch (ClassNotFoundException e) {
                    Message.error("Error", "Unable to find MIDlet class, " + Message.getCauseMessage(e), e);
                    return;
                } catch (NoClassDefFoundError e2) {
                    Message.error("Error", "Unable to find MIDlet class, " + Message.getCauseMessage(e2), e2);
                    return;
                } catch (MalformedURLException e3) {
                    Message.error("Error", "Unable to find MIDlet class, " + Message.getCauseMessage(e3), e3);
                    return;
                }
            } else {
                try {
                    midletClass = instance.getClass().getClassLoader().loadClass(this.midletClassOrUrl);
                } catch (ClassNotFoundException e4) {
                    Message.error("Error", "Unable to find MIDlet class, " + Message.getCauseMessage(e4), e4);
                    return;
                }
            }
        }
        if (this.autoTests) {
            if (this.jadURL != null) {
                runAutoTests(this.jadURL, true);
                return;
            }
            return;
        }
        if (midletClass != null && this.propertiesJad != null) {
            try {
                this.jad = loadJadProperties(this.propertiesJad);
            } catch (IOException e5) {
                Logger.error("Cannot load " + this.propertiesJad + " URL", e5);
            }
        }
        boolean started = false;
        if (midletClass == null) {
            MIDletEntry entry = launcher.getSelectedMidletEntry();
            if (startMidlet && entry != null) {
                started = startMidlet(entry.getMIDletClass(), MIDletBridge.getMIDletAccess()) != null;
            }
        } else {
            started = startMidlet(midletClass, MIDletBridge.getMIDletAccess()) != null;
        }
        if (!started) {
            startLauncher(MIDletBridge.getMIDletContext());
        }
    }

    public static String usage() {
        return "[(-d | --device) ({device descriptor} | {device class name}) ] \n[--rms (file | memory)] \n[--id EmulatorID ] \n[--impl {JSR implementation class name}]\n[(--classpath|-cp) <JSR CLASSPATH>]\n[(--appclasspath|--appcp) <MIDlet CLASSPATH>]\n[--appclass <library class name>]\n[--appclassloader strict|relaxed|delegating|system] \n[-Xautotest:<JAD file url>\n[--quit]\n[--logCallLocation true|false]\n[--traceClassLoading\n[--traceSystemClassLoading]\n[--enhanceCatchBlock]\n][--resizableDevice {width} {height}]\n(({MIDlet class name} [--propertiesjad {jad file location}]) | {jad file location} | {jar file location})";
    }
}
