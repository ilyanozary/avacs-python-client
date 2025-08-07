package org.microemu.app.util;

import java.io.InputStream;
import org.microemu.Injected;
import org.microemu.log.Logger;
import org.microemu.util.ThreadUtils;

/* loaded from: avacs.jar:org/microemu/app/util/MIDletResourceLoader.class */
public class MIDletResourceLoader {
    public static ClassLoader classLoader;
    public static boolean traceResourceLoading = false;
    private static final String FQCN = Injected.class.getName();

    public static InputStream getResourceAsStream(Class origClass, String resourceName) {
        if (traceResourceLoading) {
            Logger.debug("Loading MIDlet resource", resourceName);
        }
        if (classLoader != origClass.getClassLoader()) {
            ThreadUtils.getCallLocation(FQCN);
        }
        String resourceName2 = resolveName(origClass, resourceName);
        InputStream is = classLoader.getResourceAsStream(resourceName2);
        if (is == null) {
            Logger.debug("Resource not found ", resourceName2);
            return null;
        }
        return new MIDletResourceInputStream(is);
    }

    private static String resolveName(Class origClass, String name) {
        if (name == null) {
            return name;
        }
        if (!name.startsWith("/")) {
            while (origClass.isArray()) {
                origClass = origClass.getComponentType();
            }
            String baseName = origClass.getName();
            int index = baseName.lastIndexOf(46);
            if (index != -1) {
                name = String.valueOf(baseName.substring(0, index).replace('.', '/')) + "/" + name;
            }
        } else {
            name = name.substring(1);
        }
        return name;
    }
}
