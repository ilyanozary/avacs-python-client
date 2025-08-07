package org.microemu.microedition;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Map;
import org.microemu.microedition.io.ConnectorDelegate;

/* loaded from: avacs.jar:org/microemu/microedition/ImplFactory.class */
public class ImplFactory {
    public static final String DEFAULT = "org.microemu.default";
    private static final String INTERFACE_NAME_SUFIX = "Delegate";
    private static final String IMPLEMENTATION_NAME_SUFIX = "Impl";
    private Map implementations;
    private Map implementationsGCF;
    private AccessControlContext acc;

    /* loaded from: avacs.jar:org/microemu/microedition/ImplFactory$SingletonHolder.class */
    private static class SingletonHolder {
        private static ImplFactory instance = new ImplFactory(null);

        private SingletonHolder() {
        }
    }

    private ImplFactory() {
        this.implementations = new HashMap();
        this.implementationsGCF = new HashMap();
        this.acc = AccessController.getContext();
    }

    /* synthetic */ ImplFactory(ImplFactory implFactory) {
        this();
    }

    public static ImplFactory instance() {
        return SingletonHolder.instance;
    }

    public static void register(Class delegate, Class implementationClass) {
        instance().implementations.put(delegate, implementationClass);
    }

    public static void register(Class delegate, Object implementationInstance) {
        instance().implementations.put(delegate, implementationInstance);
    }

    public static void unregister(Class delegate, Class implementation) {
    }

    public static void registerGCF(String scheme, Object implementation) {
        if (!ConnectorDelegate.class.isAssignableFrom(implementation.getClass())) {
            throw new IllegalArgumentException();
        }
        if (scheme == null) {
            scheme = DEFAULT;
        }
        Object impl = instance().implementationsGCF.get(scheme);
        if (impl instanceof ImplementationUnloadable) {
            ((ImplementationUnloadable) impl).unregisterImplementation();
        }
        instance().implementationsGCF.put(scheme, implementation);
    }

    public static void unregistedGCF(String scheme, Object implementation) {
        if (!ConnectorDelegate.class.isAssignableFrom(implementation.getClass())) {
            throw new IllegalArgumentException();
        }
        if (scheme == null) {
            scheme = DEFAULT;
        }
        Object impl = instance().implementationsGCF.get(scheme);
        if (impl == implementation) {
            instance().implementationsGCF.remove(scheme);
        }
    }

    private Object getDefaultImplementation(Class delegateInterface) {
        try {
            String name = delegateInterface.getName();
            if (name.endsWith(INTERFACE_NAME_SUFIX)) {
                name = name.substring(0, name.length() - INTERFACE_NAME_SUFIX.length());
            }
            final String implClassName = String.valueOf(name) + IMPLEMENTATION_NAME_SUFIX;
            return AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.microedition.ImplFactory.1
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, SecurityException {
                    Class implClass = ImplFactory.class.getClassLoader().loadClass(implClassName);
                    try {
                        implClass.getConstructor(null);
                        return implClass.newInstance();
                    } catch (NoSuchMethodException e) {
                        throw new InstantiationException("No default constructor in class " + implClassName);
                    }
                }
            }, this.acc);
        } catch (Throwable e) {
            throw new RuntimeException("Unable create " + delegateInterface.getName() + " implementation", e);
        }
    }

    private Object implementationNewInstance(final Class implClass) {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.microedition.ImplFactory.2
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
                    return implClass.newInstance();
                }
            }, this.acc);
        } catch (Throwable e) {
            throw new RuntimeException("Unable create " + implClass.getName() + " implementation", e);
        }
    }

    public static String getCGFScheme(String name) {
        return name.substring(0, name.indexOf(58));
    }

    public static ConnectorDelegate getCGFImplementation(String name) {
        String scheme = getCGFScheme(name);
        ConnectorDelegate impl = (ConnectorDelegate) instance().implementationsGCF.get(scheme);
        if (impl != null) {
            return impl;
        }
        ConnectorDelegate impl2 = (ConnectorDelegate) instance().implementationsGCF.get(DEFAULT);
        if (impl2 != null) {
            return impl2;
        }
        return (ConnectorDelegate) instance().getDefaultImplementation(ConnectorDelegate.class);
    }

    public static Implementation getImplementation(Class origClass, Class delegateInterface) {
        Object impl = instance().implementations.get(delegateInterface);
        if (impl != null) {
            if (impl instanceof Class) {
                return (Implementation) instance().implementationNewInstance((Class) impl);
            }
            return (Implementation) impl;
        }
        return (Implementation) instance().getDefaultImplementation(delegateInterface);
    }
}
