package org.microemu.app;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import nanoxml.XMLElement;
import nanoxml.XMLParseException;
import org.microemu.EmulatorContext;
import org.microemu.app.util.DeviceEntry;
import org.microemu.app.util.IOUtils;
import org.microemu.app.util.MIDletSystemProperties;
import org.microemu.app.util.MRUList;
import org.microemu.app.util.MidletURLReference;
import org.microemu.device.impl.DeviceImpl;
import org.microemu.device.impl.Rectangle;
import org.microemu.log.Logger;
import org.microemu.microedition.ImplementationInitialization;

/* loaded from: avacs.jar:org/microemu/app/Config.class */
public class Config {
    private static File meHome;
    private static String emulatorID;
    private static DeviceEntry defaultDevice;
    private static DeviceEntry resizableDevice;
    private static EmulatorContext emulatorContext;
    private static XMLElement configXml = new XMLElement();
    private static MRUList urlsMRU = new MRUList(MidletURLReference.class, "midlet");

    private static File initMEHomePath() {
        try {
            File meHome2 = new File(String.valueOf(System.getProperty("user.home")) + "/.AVACSLiveChat/");
            if (emulatorID != null) {
                return new File(meHome2, emulatorID);
            }
            return meHome2;
        } catch (SecurityException e) {
            Logger.error("Cannot access user.home", e);
            return null;
        }
    }

    public static void loadConfig(DeviceEntry defaultDevice2, EmulatorContext emulatorContext2) throws IOException, NumberFormatException {
        defaultDevice = defaultDevice2;
        emulatorContext = emulatorContext2;
        File configFile = new File(getConfigPath(), "config2.xml");
        try {
            try {
                if (configFile.exists()) {
                    loadConfigFile("config2.xml");
                } else {
                    File configFile2 = new File(getConfigPath(), "config.xml");
                    if (configFile2.exists()) {
                        loadConfigFile("config.xml");
                        Enumeration e = getDeviceEntries().elements();
                        while (e.hasMoreElements()) {
                            DeviceEntry entry = (DeviceEntry) e.nextElement();
                            if (entry.canRemove()) {
                                removeDeviceEntry(entry);
                                File src = new File(getConfigPath(), entry.getFileName());
                                File dst = File.createTempFile("dev", ".jar", getConfigPath());
                                IOUtils.copyFile(src, dst);
                                entry.setFileName(dst.getName());
                                addDeviceEntry(entry);
                            }
                        }
                    } else {
                        createDefaultConfigXml();
                    }
                    saveConfig();
                }
                if (configXml == null) {
                    createDefaultConfigXml();
                }
            } catch (IOException ex) {
                Logger.error((Throwable) ex);
                createDefaultConfigXml();
                if (configXml == null) {
                    createDefaultConfigXml();
                }
            }
            urlsMRU.read(configXml.getChildOrNew("files").getChildOrNew("recent"));
            initSystemProperties();
        } catch (Throwable th) {
            if (configXml == null) {
                createDefaultConfigXml();
            }
            throw th;
        }
    }

    private static void loadConfigFile(String configFileName) throws IOException {
        File configFile = new File(getConfigPath(), configFileName);
        InputStream is = null;
        String xml = "";
        try {
            try {
                InputStream fileInputStream = new FileInputStream(configFile);
                is = fileInputStream;
                InputStream dis = new BufferedInputStream(fileInputStream);
                while (dis.available() > 0) {
                    byte[] b = new byte[dis.available()];
                    dis.read(b);
                    xml = String.valueOf(xml) + new String(b);
                }
                configXml = new XMLElement();
                configXml.parseString(xml);
                IOUtils.closeQuietly(is);
            } catch (XMLParseException e) {
                Logger.error((Throwable) e);
                createDefaultConfigXml();
                IOUtils.closeQuietly(is);
            }
        } catch (Throwable th) {
            IOUtils.closeQuietly(is);
            throw th;
        }
    }

    private static void createDefaultConfigXml() {
        configXml = new XMLElement();
        configXml.setName("config");
    }

    public static void saveConfig() throws IOException {
        urlsMRU.save(configXml.getChildOrNew("files").getChildOrNew("recent"));
        File configFile = new File(getConfigPath(), "config2.xml");
        getConfigPath().mkdirs();
        FileWriter fw = null;
        try {
            try {
                fw = new FileWriter(configFile);
                configXml.write(fw);
                fw.close();
                IOUtils.closeQuietly(fw);
            } catch (IOException ex) {
                Logger.error((Throwable) ex);
                IOUtils.closeQuietly(fw);
            }
        } catch (Throwable th) {
            IOUtils.closeQuietly(fw);
            throw th;
        }
    }

    static Map getExtensions() {
        String className;
        Map extensions = new HashMap();
        XMLElement extensionsXml = configXml.getChild("extensions");
        if (extensionsXml == null) {
            return extensions;
        }
        Enumeration en = extensionsXml.enumerateChildren();
        while (en.hasMoreElements()) {
            XMLElement extension = (XMLElement) en.nextElement();
            if (extension.getName().equals("extension") && (className = extension.getChildString("className", null)) != null) {
                HashMap map = new HashMap();
                map.put(ImplementationInitialization.PARAM_EMULATOR_ID, getEmulatorID());
                Enumeration een = extension.enumerateChildren();
                while (een.hasMoreElements()) {
                    XMLElement propXml = (XMLElement) een.nextElement();
                    if (propXml.getName().equals("properties")) {
                        Enumeration e_prop = propXml.enumerateChildren();
                        while (e_prop.hasMoreElements()) {
                            XMLElement tmp_prop = (XMLElement) e_prop.nextElement();
                            if (tmp_prop.getName().equals("property")) {
                                map.put(tmp_prop.getStringAttribute("name"), tmp_prop.getStringAttribute("value"));
                            }
                        }
                    }
                }
                extensions.put(className, map);
            }
        }
        return extensions;
    }

    private static void initSystemProperties() throws IOException, NumberFormatException {
        Map systemProperties = null;
        Enumeration e = configXml.enumerateChildren();
        while (e.hasMoreElements()) {
            XMLElement tmp = (XMLElement) e.nextElement();
            if (tmp.getName().equals("system-properties")) {
                systemProperties = new HashMap();
                Enumeration e_prop = tmp.enumerateChildren();
                while (e_prop.hasMoreElements()) {
                    XMLElement tmp_prop = (XMLElement) e_prop.nextElement();
                    if (tmp_prop.getName().equals("system-property")) {
                        systemProperties.put(tmp_prop.getStringAttribute("name"), tmp_prop.getStringAttribute("value"));
                    }
                }
            }
        }
        if (systemProperties == null) {
            systemProperties = new Properties();
            systemProperties.put("microedition.configuration", "CLDC-1.0");
            systemProperties.put("microedition.profiles", "MIDP-2.0");
            systemProperties.put("avetana.forceNativeLibrary", Boolean.TRUE.toString());
            XMLElement propertiesXml = configXml.getChildOrNew("system-properties");
            for (Map.Entry e2 : systemProperties.entrySet()) {
                XMLElement xmlProperty = propertiesXml.addChild("system-property");
                xmlProperty.setAttribute("value", (String) e2.getValue());
                xmlProperty.setAttribute("name", (String) e2.getKey());
            }
            saveConfig();
        }
        MIDletSystemProperties.setProperties(systemProperties);
    }

    public static File getConfigPath() {
        if (meHome == null) {
            meHome = initMEHomePath();
        }
        return meHome;
    }

    public static Vector getDeviceEntries() throws IOException {
        Vector result = new Vector();
        if (defaultDevice == null) {
            defaultDevice = new DeviceEntry("Default device", (String) null, DeviceImpl.DEFAULT_LOCATION, true, false);
        }
        defaultDevice.setDefaultDevice(true);
        result.add(defaultDevice);
        if (resizableDevice == null) {
            resizableDevice = new DeviceEntry("Resizable device", (String) null, DeviceImpl.RESIZABLE_LOCATION, false, false);
            addDeviceEntry(resizableDevice);
        }
        XMLElement devicesXml = configXml.getChild("devices");
        if (devicesXml == null) {
            return result;
        }
        Enumeration e_device = devicesXml.enumerateChildren();
        while (e_device.hasMoreElements()) {
            XMLElement tmp_device = (XMLElement) e_device.nextElement();
            if (tmp_device.getName().equals("device")) {
                defaultDevice.setDefaultDevice(false);
                String devName = tmp_device.getChildString("name", null);
                String devFile = tmp_device.getChildString("filename", null);
                String devClass = tmp_device.getChildString("class", null);
                String devDescriptor = tmp_device.getChildString("descriptor", null);
                if (devDescriptor == null) {
                    result.add(new DeviceEntry(devName, devFile, true, devClass, emulatorContext));
                } else {
                    result.add(new DeviceEntry(devName, devFile, devDescriptor, true));
                }
            }
        }
        return result;
    }

    public static void addDeviceEntry(DeviceEntry entry) throws IOException {
        Enumeration en = getDeviceEntries().elements();
        while (en.hasMoreElements()) {
            DeviceEntry test = (DeviceEntry) en.nextElement();
            if (test.getDescriptorLocation().equals(entry.getDescriptorLocation())) {
                return;
            }
        }
        XMLElement devicesXml = configXml.getChildOrNew("devices");
        XMLElement deviceXml = devicesXml.addChild("device");
        if (entry.isDefaultDevice()) {
            deviceXml.setAttribute("default", "true");
        }
        deviceXml.addChild("name", entry.getName());
        deviceXml.addChild("filename", entry.getFileName());
        deviceXml.addChild("descriptor", entry.getDescriptorLocation());
        saveConfig();
    }

    public static void removeDeviceEntry(DeviceEntry entry) throws IOException {
        XMLElement devicesXml = configXml.getChild("devices");
        if (devicesXml == null) {
            return;
        }
        Enumeration e_device = devicesXml.enumerateChildren();
        while (e_device.hasMoreElements()) {
            XMLElement tmp_device = (XMLElement) e_device.nextElement();
            if (tmp_device.getName().equals("device")) {
                String testDescriptor = tmp_device.getChildString("descriptor", null);
                if (testDescriptor == null) {
                    devicesXml.removeChild(tmp_device);
                    saveConfig();
                } else if (testDescriptor.equals(entry.getDescriptorLocation())) {
                    devicesXml.removeChild(tmp_device);
                    saveConfig();
                    return;
                }
            }
        }
    }

    public static void changeDeviceEntry(DeviceEntry entry) throws IOException {
        XMLElement devicesXml = configXml.getChild("devices");
        if (devicesXml == null) {
            return;
        }
        Enumeration e_device = devicesXml.enumerateChildren();
        while (e_device.hasMoreElements()) {
            XMLElement tmp_device = (XMLElement) e_device.nextElement();
            if (tmp_device.getName().equals("device")) {
                String testDescriptor = tmp_device.getChildString("descriptor", null);
                if (testDescriptor.equals(entry.getDescriptorLocation())) {
                    if (entry.isDefaultDevice()) {
                        tmp_device.setAttribute("default", "true");
                    } else {
                        tmp_device.removeAttribute("default");
                    }
                    saveConfig();
                    return;
                }
            }
        }
    }

    public static Rectangle getDeviceEntryDisplaySize(DeviceEntry entry) {
        XMLElement rectangleXml;
        XMLElement devicesXml = configXml.getChild("devices");
        if (devicesXml != null) {
            Enumeration e_device = devicesXml.enumerateChildren();
            while (e_device.hasMoreElements()) {
                XMLElement tmp_device = (XMLElement) e_device.nextElement();
                if (tmp_device.getName().equals("device")) {
                    String testDescriptor = tmp_device.getChildString("descriptor", null);
                    if (testDescriptor.equals(entry.getDescriptorLocation()) && (rectangleXml = tmp_device.getChild("rectangle")) != null) {
                        Rectangle result = new Rectangle();
                        result.x = rectangleXml.getChildInteger("x", -1);
                        result.y = rectangleXml.getChildInteger("y", -1);
                        result.width = rectangleXml.getChildInteger("width", -1);
                        result.height = rectangleXml.getChildInteger("height", -1);
                        return result;
                    }
                }
            }
            return null;
        }
        return null;
    }

    public static void setDeviceEntryDisplaySize(DeviceEntry entry, Rectangle rect) throws IOException {
        XMLElement devicesXml;
        if (entry == null || (devicesXml = configXml.getChild("devices")) == null) {
            return;
        }
        Enumeration e_device = devicesXml.enumerateChildren();
        while (e_device.hasMoreElements()) {
            XMLElement tmp_device = (XMLElement) e_device.nextElement();
            if (tmp_device.getName().equals("device")) {
                String testDescriptor = tmp_device.getChildString("descriptor", null);
                if (testDescriptor.equals(entry.getDescriptorLocation())) {
                    XMLElement mainXml = tmp_device.getChildOrNew("rectangle");
                    XMLElement xml = mainXml.getChildOrNew("x");
                    xml.setContent(String.valueOf(rect.x));
                    XMLElement xml2 = mainXml.getChildOrNew("y");
                    xml2.setContent(String.valueOf(rect.y));
                    XMLElement xml3 = mainXml.getChildOrNew("width");
                    xml3.setContent(String.valueOf(rect.width));
                    XMLElement xml4 = mainXml.getChildOrNew("height");
                    xml4.setContent(String.valueOf(rect.height));
                    saveConfig();
                    return;
                }
            }
        }
    }

    public static String getRecordStoreManagerClassName() {
        XMLElement recordStoreManagerXml = configXml.getChild("recordStoreManager");
        if (recordStoreManagerXml == null) {
            return null;
        }
        return recordStoreManagerXml.getStringAttribute("class");
    }

    public static void setRecordStoreManagerClassName(String className) throws IOException {
        XMLElement recordStoreManagerXml = configXml.getChildOrNew("recordStoreManager");
        recordStoreManagerXml.setAttribute("class", className);
        saveConfig();
    }

    public static boolean isLogConsoleLocationEnabled() {
        XMLElement logConsoleXml = configXml.getChild("logConsole");
        if (logConsoleXml == null) {
            return true;
        }
        return logConsoleXml.getBooleanAttribute("locationEnabled", true);
    }

    public static void setLogConsoleLocationEnabled(boolean state) throws IOException {
        XMLElement logConsoleXml = configXml.getChildOrNew("logConsole");
        if (state) {
            logConsoleXml.setAttribute("locationEnabled", "true");
        } else {
            logConsoleXml.setAttribute("locationEnabled", "false");
        }
        saveConfig();
    }

    public static boolean isWindowOnStart(String name) {
        XMLElement mainXml;
        XMLElement windowsXml = configXml.getChild("windows");
        if (windowsXml == null || (mainXml = windowsXml.getChild(name)) == null) {
            return false;
        }
        String attr = mainXml.getStringAttribute("onstart", "false");
        if (attr.trim().toLowerCase().equals("true")) {
            return true;
        }
        return false;
    }

    public static Rectangle getWindow(String name, Rectangle defaultWindow) {
        XMLElement windowsXml = configXml.getChild("windows");
        if (windowsXml == null) {
            return defaultWindow;
        }
        XMLElement mainXml = windowsXml.getChild(name);
        if (mainXml == null) {
            return defaultWindow;
        }
        Rectangle window = new Rectangle();
        window.x = mainXml.getChildInteger("x", defaultWindow.x);
        window.y = mainXml.getChildInteger("y", defaultWindow.y);
        window.width = mainXml.getChildInteger("width", defaultWindow.width);
        window.height = mainXml.getChildInteger("height", defaultWindow.height);
        return window;
    }

    public static void setWindow(String name, Rectangle window, boolean onStart) throws IOException {
        XMLElement windowsXml = configXml.getChildOrNew("windows");
        XMLElement mainXml = windowsXml.getChildOrNew(name);
        if (onStart) {
            mainXml.setAttribute("onstart", "true");
        } else {
            mainXml.removeAttribute("onstart");
        }
        XMLElement xml = mainXml.getChildOrNew("x");
        xml.setContent(String.valueOf(window.x));
        XMLElement xml2 = mainXml.getChildOrNew("y");
        xml2.setContent(String.valueOf(window.y));
        XMLElement xml3 = mainXml.getChildOrNew("width");
        xml3.setContent(String.valueOf(window.width));
        XMLElement xml4 = mainXml.getChildOrNew("height");
        xml4.setContent(String.valueOf(window.height));
        saveConfig();
    }

    public static String getRecentDirectory(String key) {
        XMLElement filesXml = configXml.getChild("files");
        if (filesXml == null) {
            return ".";
        }
        return filesXml.getChildString(key, ".");
    }

    public static void setRecentDirectory(String key, String recentJadDirectory) throws IOException {
        XMLElement filesXml = configXml.getChildOrNew("files");
        XMLElement recentJadDirectoryXml = filesXml.getChildOrNew(key);
        recentJadDirectoryXml.setContent(recentJadDirectory);
        saveConfig();
    }

    public static MRUList getUrlsMRU() {
        return urlsMRU;
    }

    public static String getEmulatorID() {
        return emulatorID;
    }

    public static void setEmulatorID(String emulatorID2) {
        emulatorID = emulatorID2;
    }
}
