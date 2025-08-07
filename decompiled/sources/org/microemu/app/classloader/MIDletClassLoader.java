package org.microemu.app.classloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import org.microemu.app.util.IOUtils;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/app/classloader/MIDletClassLoader.class */
public class MIDletClassLoader extends URLClassLoader {
    public static boolean instrumentMIDletClasses = true;
    public static boolean traceClassLoading = false;
    public static boolean traceSystemClassLoading = false;
    public static boolean enhanceCatchBlock = false;
    public static final boolean debug = false;
    private boolean delegatingToParent;
    private boolean findPathInParent;
    private InstrumentationConfig config;
    private Set noPreporcessingNames;
    private AccessControlContext acc;

    /* loaded from: avacs.jar:org/microemu/app/classloader/MIDletClassLoader$LoadClassByParentException.class */
    private static class LoadClassByParentException extends ClassNotFoundException {
        private static final long serialVersionUID = 1;

        public LoadClassByParentException(String name) {
            super(name);
        }
    }

    public MIDletClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
        this.delegatingToParent = false;
        this.findPathInParent = false;
        this.noPreporcessingNames = new HashSet();
        this.acc = AccessController.getContext();
        this.config = new InstrumentationConfig();
        this.config.setEnhanceCatchBlock(enhanceCatchBlock);
        this.config.setEnhanceThreadCreation(true);
    }

    public void configure(MIDletClassLoaderConfig clConfig, boolean forJad) throws MalformedURLException {
        for (String path : clConfig.appclasspath) {
            StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
            while (st.hasMoreTokens()) {
                addURL(new URL(IOUtils.getCanonicalFileClassLoaderURL(new File(st.nextToken()))));
            }
        }
        Iterator iter = clConfig.appclasses.iterator();
        while (iter.hasNext()) {
            addClassURL((String) iter.next());
        }
        int delegationType = clConfig.getDelegationType(forJad);
        this.delegatingToParent = delegationType == 2;
        this.findPathInParent = delegationType == 1;
    }

    public void addClassURL(String className) throws MalformedURLException {
        String resource = getClassResourceName(className);
        URL url = getParent().getResource(resource);
        if (url == null) {
            url = getResource(resource);
        }
        if (url == null) {
            throw new MalformedURLException("Unable to find class " + className + " URL");
        }
        String path = url.toExternalForm();
        addURL(new URL(path.substring(0, path.length() - resource.length())));
    }

    static URL getClassURL(ClassLoader parent, String className) throws MalformedURLException {
        String resource = getClassResourceName(className);
        URL url = parent.getResource(resource);
        if (url == null) {
            throw new MalformedURLException("Unable to find class " + className + " URL");
        }
        String path = url.toExternalForm();
        return new URL(path.substring(0, path.length() - resource.length()));
    }

    @Override // java.net.URLClassLoader
    public void addURL(URL url) {
        super.addURL(url);
    }

    @Override // java.lang.ClassLoader
    protected synchronized Class loadClass(String name, boolean resolve) throws IOException, ClassNotFoundException {
        Class result = findLoadedClass(name);
        if (result == null) {
            try {
                result = findClass(name);
            } catch (ClassNotFoundException e) {
                if ((e instanceof LoadClassByParentException) || this.delegatingToParent) {
                    if (traceSystemClassLoading) {
                        Logger.info("Load system class", name);
                    }
                    result = super.loadClass(name, false);
                    if (result == null) {
                        throw new ClassNotFoundException(name);
                    }
                }
            }
        }
        if (resolve) {
            resolveClass(result);
        }
        return result;
    }

    @Override // java.lang.ClassLoader
    public URL getResource(final String name) {
        try {
            return (URL) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.classloader.MIDletClassLoader.1
                @Override // java.security.PrivilegedExceptionAction
                public Object run() {
                    URL url = MIDletClassLoader.this.findResource(name);
                    if (url == null && MIDletClassLoader.this.delegatingToParent && MIDletClassLoader.this.getParent() != null) {
                        url = MIDletClassLoader.this.getParent().getResource(name);
                    }
                    return url;
                }
            }, this.acc);
        } catch (PrivilegedActionException e) {
            return null;
        }
    }

    @Override // java.net.URLClassLoader, java.lang.ClassLoader
    public InputStream getResourceAsStream(String name) {
        final URL url = getResource(name);
        if (url == null) {
            return null;
        }
        try {
            return (InputStream) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.classloader.MIDletClassLoader.2
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IOException {
                    return url.openStream();
                }
            }, this.acc);
        } catch (PrivilegedActionException e) {
            return null;
        }
    }

    public boolean classLoadByParent(String className) {
        if (className.startsWith("java.") || className.startsWith("sun.reflect.") || className.startsWith("javax.microedition.") || className.startsWith("javax.") || this.noPreporcessingNames.contains(className)) {
            return true;
        }
        return false;
    }

    public void disableClassPreporcessing(Class klass) {
        disableClassPreporcessing(klass.getName());
    }

    public void disableClassPreporcessing(String className) {
        this.noPreporcessingNames.add(className);
    }

    public static String getClassResourceName(String className) {
        return className.replace('.', '/').concat(".class");
    }

    @Override // java.net.URLClassLoader, java.lang.ClassLoader
    protected Class findClass(final String name) throws ClassNotFoundException, IOException {
        byte[] byteCode;
        int byteCodeLength;
        boolean classFound;
        if (classLoadByParent(name)) {
            throw new LoadClassByParentException(name);
        }
        try {
            InputStream is = (InputStream) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.classloader.MIDletClassLoader.3
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws ClassNotFoundException {
                    return MIDletClassLoader.this.getResourceAsStream(MIDletClassLoader.getClassResourceName(name));
                }
            }, this.acc);
            if (is == null && this.findPathInParent) {
                try {
                    addClassURL(name);
                    classFound = true;
                } catch (MalformedURLException e) {
                    classFound = false;
                }
                if (classFound) {
                    is = (InputStream) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.app.classloader.MIDletClassLoader.4
                        @Override // java.security.PrivilegedExceptionAction
                        public Object run() throws ClassNotFoundException {
                            return MIDletClassLoader.this.getResourceAsStream(MIDletClassLoader.getClassResourceName(name));
                        }
                    }, this.acc);
                }
            }
            if (is == null) {
                throw new ClassNotFoundException(name);
            }
            try {
                if (traceClassLoading) {
                    Logger.info("Load MIDlet class", name);
                }
                if (instrumentMIDletClasses) {
                    byteCode = ClassPreprocessor.instrument(is, this.config);
                    byteCodeLength = byteCode.length;
                } else {
                    byteCode = new byte[2048];
                    byteCodeLength = 0;
                    while (true) {
                        try {
                            int retrived = is.read(byteCode, byteCodeLength, byteCode.length - byteCodeLength);
                            if (retrived == -1) {
                                break;
                            }
                            if (byteCode.length + 2048 > 16384) {
                                throw new ClassNotFoundException(name, new ClassFormatError("Class object is bigger than 16 Kilobyte"));
                            }
                            byteCodeLength += retrived;
                            if (byteCode.length == byteCodeLength) {
                                byte[] newData = new byte[byteCode.length + 2048];
                                System.arraycopy(byteCode, 0, newData, 0, byteCode.length);
                                byteCode = newData;
                            } else if (byteCode.length < byteCodeLength) {
                                throw new ClassNotFoundException(name, new ClassFormatError("Internal read error"));
                            }
                        } catch (IOException e2) {
                            throw new ClassNotFoundException(name, e2);
                        }
                    }
                }
                return defineClass(name, byteCode, 0, byteCodeLength);
            } finally {
                try {
                    is.close();
                } catch (IOException e3) {
                }
            }
        } catch (PrivilegedActionException e4) {
            throw new ClassNotFoundException(name, e4.getCause());
        }
    }
}
