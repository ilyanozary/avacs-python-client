package org.microemu.util;

import java.util.Iterator;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/* loaded from: avacs.jar:org/microemu/util/JadProperties.class */
public class JadProperties extends Manifest {
    private static final long serialVersionUID = 1;
    static String MIDLET_PREFIX = "MIDlet-";
    Vector midletEntries = null;
    String correctedJarURL = null;

    @Override // java.util.jar.Manifest
    public void clear() {
        super.clear();
        this.midletEntries = null;
        this.correctedJarURL = null;
    }

    public String getSuiteName() {
        return getProperty("MIDlet-Name");
    }

    public String getVersion() {
        return getProperty("MIDlet-Version");
    }

    public String getVendor() {
        return getProperty("MIDlet-Vendor");
    }

    public String getProfile() {
        return getProperty("MicroEdition-Profile");
    }

    public String getConfiguration() {
        return getProperty("MicroEdition-Configuration");
    }

    public String getJarURL() {
        if (this.correctedJarURL != null) {
            return this.correctedJarURL;
        }
        return getProperty("MIDlet-Jar-URL");
    }

    public void setCorrectedJarURL(String correctedJarURL) {
        this.correctedJarURL = correctedJarURL;
    }

    public int getJarSize() {
        return Integer.parseInt(getProperty("MIDlet-Jar-Size"));
    }

    public Vector getMidletEntries() throws NumberFormatException {
        if (this.midletEntries == null) {
            this.midletEntries = new Vector();
            Attributes attributes = super.getMainAttributes();
            Iterator it = attributes.keySet().iterator();
            while (it.hasNext()) {
                Attributes.Name key = (Attributes.Name) it.next();
                if (key.toString().startsWith(MIDLET_PREFIX)) {
                    try {
                        Integer.parseInt(key.toString().substring(MIDLET_PREFIX.length()));
                        String test = getProperty(key.toString());
                        int pos = test.indexOf(44);
                        String name = test.substring(0, pos).trim();
                        String icon = test.substring(pos + 1, test.indexOf(44, pos + 1)).trim();
                        String className = test.substring(test.indexOf(44, pos + 1) + 1).trim();
                        this.midletEntries.addElement(new JadMidletEntry(name, icon, className));
                    } catch (NumberFormatException e) {
                    }
                }
            }
        }
        return this.midletEntries;
    }

    public String getProperty(String key, String defaultValue) {
        Attributes attributes = super.getMainAttributes();
        String result = null;
        try {
            result = attributes.getValue(key);
        } catch (IllegalArgumentException e) {
        }
        if (result != null) {
            return result.trim();
        }
        return defaultValue;
    }

    public String getProperty(String key) {
        return getProperty(key, null);
    }
}
