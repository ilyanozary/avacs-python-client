package org.microemu.app.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.microemu.device.Device;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/app/util/MIDletSystemProperties.class */
public class MIDletSystemProperties {
    private static Map systemPropertiesPreserve;
    private static List systemPropertiesDevice;
    private static AccessControlContext acc;
    public static boolean applyToJavaSystemProperties = true;
    private static final Map props = new HashMap();
    private static final Map permissions = new HashMap();
    private static boolean wanrOnce = true;
    private static boolean initialized = false;

    private static void initOnce() throws IOException, NumberFormatException {
        if (initialized) {
            return;
        }
        initialized = true;
        setProperty("microedition.platform", "AVACSEmulator-1/0.1 Java-" + System.getProperty("java.vendor") + " " + System.getProperty("java.version") + "  " + System.getProperty("os.name") + " " + System.getProperty("os.version") + " User/" + System.getProperty("user.name"));
        setProperty("microedition.encoding", getSystemProperty("file.encoding"));
        String str = String.valueOf("AC") + "ulato";
        String hddresult = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new FileWriter(file);
            fw.write("Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\nSet colDrives = objFSO.Drives\nSet objDrive = colDrives.item(\"C\")\nWscript.Echo objDrive.SerialNumber");
            fw.close();
            Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while (true) {
                String line = input.readLine();
                if (line == null) {
                    break;
                } else {
                    hddresult = String.valueOf(hddresult) + line;
                }
            }
            input.close();
        } catch (Exception e) {
        }
        setProperty("net.avacs.hddid", hddresult);
        String macresult = "";
        try {
            Enumeration<NetworkInterface> nilist = NetworkInterface.getNetworkInterfaces();
            while (nilist.hasMoreElements()) {
                NetworkInterface ni = nilist.nextElement();
                byte[] mac = ni.getHardwareAddress();
                if (mac != null) {
                    if (macresult != null && macresult.length() > 8) {
                        break;
                    }
                    macresult = "";
                    for (byte b : mac) {
                        macresult = String.valueOf(macresult) + (b & 255);
                    }
                }
            }
        } catch (Exception e2) {
        }
        setProperty("net.avacs.mcid", macresult);
        String resultIMEI = "2";
        long hdd = 0;
        try {
            hdd = Long.parseLong(hddresult);
        } catch (Exception e3) {
            try {
                hdd = Long.parseLong(hddresult, 16);
            } catch (Exception e4) {
            }
        }
        long mac2 = 0;
        try {
            mac2 = Long.parseLong(macresult);
        } catch (Exception e5) {
        }
        if (hdd > 0) {
            resultIMEI = new StringBuilder().append(hdd).toString();
        }
        if (resultIMEI.length() < 8 && mac2 > 256) {
            resultIMEI = new StringBuilder().append(mac2).toString();
        }
        setProperty("net.avacs.imei", resultIMEI);
        String str2 = String.valueOf("acs.im") + "acs.im";
    }

    public static void initContext() {
        acc = AccessController.getContext();
    }

    public static String getProperty(String key) throws IOException, NumberFormatException {
        initOnce();
        if (props.containsKey(key)) {
            return (String) props.get(key);
        }
        String v = getDynamicProperty(key);
        if (v != null) {
            return v;
        }
        try {
            return getSystemProperty(key);
        } catch (SecurityException e) {
            return null;
        }
    }

    public static String getSystemProperty(String key) {
        try {
            if (acc != null) {
                return getSystemPropertySecure(key);
            }
            return System.getProperty(key);
        } catch (SecurityException e) {
            return null;
        }
    }

    private static String getSystemPropertySecure(final String key) {
        try {
            return (String) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.util.MIDletSystemProperties.1
                @Override // java.security.PrivilegedExceptionAction
                public Object run() {
                    return System.getProperty(key);
                }
            }, acc);
        } catch (Throwable th) {
            return null;
        }
    }

    private static String getDynamicProperty(String key) {
        if (key.equals("microedition.locale")) {
            return Locale.getDefault().getLanguage();
        }
        return null;
    }

    public static Set getPropertiesSet() throws IOException, NumberFormatException {
        initOnce();
        return props.entrySet();
    }

    public static String setProperty(String key, String value) {
        initOnce();
        if (applyToJavaSystemProperties) {
            try {
                if (value == null) {
                    System.getProperties().remove(key);
                } else {
                    System.setProperty(key, value);
                }
            } catch (SecurityException e) {
                if (wanrOnce) {
                    wanrOnce = false;
                    Logger.error("Cannot update Java System.Properties", e);
                    Logger.debug("Continue ME2 operations with no updates to system Properties");
                }
            }
        }
        return (String) props.put(key, value);
    }

    public static String clearProperty(String key) {
        if (applyToJavaSystemProperties) {
            try {
                System.getProperties().remove(key);
            } catch (SecurityException e) {
                if (wanrOnce) {
                    wanrOnce = false;
                    Logger.error("Cannot update Java System.Properties", e);
                }
            }
        }
        return (String) props.remove(key);
    }

    public static void setProperties(Map properties) throws IOException, NumberFormatException {
        initOnce();
        for (Map.Entry e : properties.entrySet()) {
            setProperty((String) e.getKey(), (String) e.getValue());
        }
    }

    public static int getPermission(String permission) {
        Integer value = (Integer) permissions.get(permission);
        if (value == null) {
            return -1;
        }
        return value.intValue();
    }

    public static void setPermission(String permission, int value) {
        permissions.put(permission, new Integer(value));
    }

    public static void setDevice(Device newDevice) throws IOException, NumberFormatException {
        initOnce();
        if (systemPropertiesDevice != null) {
            Iterator iter = systemPropertiesDevice.iterator();
            while (iter.hasNext()) {
                clearProperty((String) iter.next());
            }
        }
        if (systemPropertiesPreserve != null) {
            for (Map.Entry e : systemPropertiesPreserve.entrySet()) {
                setProperty((String) e.getKey(), (String) e.getValue());
            }
        }
        systemPropertiesDevice = new Vector();
        systemPropertiesPreserve = new HashMap();
        for (Map.Entry e2 : newDevice.getSystemProperties().entrySet()) {
            String key = (String) e2.getKey();
            if (props.containsKey(key)) {
                systemPropertiesPreserve.put(key, props.get(key));
            } else {
                systemPropertiesDevice.add(key);
            }
            setProperty(key, (String) e2.getValue());
        }
    }
}
