package org.microemu.app.classloader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.StringTokenizer;
import org.microemu.app.util.IOUtils;

/* loaded from: avacs.jar:org/microemu/app/classloader/ExtensionsClassLoader.class */
public class ExtensionsClassLoader extends URLClassLoader {
    private static final boolean debug = false;
    private AccessControlContext acc;

    public ExtensionsClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
        this.acc = AccessController.getContext();
    }

    @Override // java.net.URLClassLoader
    public void addURL(URL url) {
        super.addURL(url);
    }

    public void addClasspath(String classpath) {
        StringTokenizer st = new StringTokenizer(classpath, ";");
        while (st.hasMoreTokens()) {
            try {
                String path = st.nextToken();
                if (path.startsWith("file:")) {
                    addURL(new URL(path));
                } else {
                    addURL(new URL(IOUtils.getCanonicalFileURL(new File(path))));
                }
            } catch (MalformedURLException e) {
                throw new Error(e);
            }
        }
    }

    @Override // java.lang.ClassLoader
    public URL getResource(final String name) {
        try {
            URL url = (URL) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.classloader.ExtensionsClassLoader.1
                @Override // java.security.PrivilegedExceptionAction
                public Object run() {
                    return ExtensionsClassLoader.this.findResource(name);
                }
            }, this.acc);
            if (url != null) {
                return url;
            }
        } catch (PrivilegedActionException e) {
        }
        return super.getResource(name);
    }
}
