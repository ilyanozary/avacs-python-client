package org.microemu.microedition.io;

import com.sun.cdc.io.ConnectionBaseInterface;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Vector;
import javax.microedition.io.Connection;
import javax.microedition.io.ConnectionNotFoundException;
import org.microemu.cldc.ClosedConnection;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/microedition/io/ConnectorImpl.class */
public class ConnectorImpl extends ConnectorAdapter {
    public static boolean debugConnectionInvocations = false;
    private final boolean needPrivilegedCalls = isWebstart();
    private AccessControlContext acc = AccessController.getContext();

    private static boolean isWebstart() {
        try {
            return System.getProperty("javawebstart.version") != null;
        } catch (SecurityException e) {
            return false;
        }
    }

    @Override // org.microemu.microedition.io.ConnectorAdapter, org.microemu.microedition.io.ConnectorDelegate
    public Connection open(final String name, final int mode, final boolean timeouts) throws IOException {
        try {
            return (Connection) AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.microedition.io.ConnectorImpl.1
                @Override // java.security.PrivilegedExceptionAction
                public Object run() throws IOException {
                    if (ConnectorImpl.debugConnectionInvocations || ConnectorImpl.this.needPrivilegedCalls) {
                        return ConnectorImpl.this.openSecureProxy(name, mode, timeouts, ConnectorImpl.this.needPrivilegedCalls);
                    }
                    return ConnectorImpl.this.openSecure(name, mode, timeouts);
                }
            }, this.acc);
        } catch (PrivilegedActionException e) {
            if (e.getCause() instanceof IOException) {
                throw ((IOException) e.getCause());
            }
            throw new IOException(e.toString());
        }
    }

    private static Class[] getAllInterfaces(Class klass) {
        Vector allInterfaces = new Vector();
        Class superclass = klass;
        while (true) {
            Class parent = superclass;
            if (parent != null) {
                Class[] interfaces = parent.getInterfaces();
                for (Class cls : interfaces) {
                    allInterfaces.add(cls);
                }
                superclass = parent.getSuperclass();
            } else {
                return (Class[]) allInterfaces.toArray(new Class[allInterfaces.size()]);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Connection openSecureProxy(String name, int mode, boolean timeouts, boolean needPrivilegedCalls) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        Connection origConnection = openSecure(name, mode, timeouts);
        Class connectionClass = null;
        Class[] interfaces = getAllInterfaces(origConnection.getClass());
        int i = 0;
        while (true) {
            if (i >= interfaces.length) {
                break;
            }
            if (Connection.class.isAssignableFrom(interfaces[i])) {
                connectionClass = interfaces[i];
                break;
            }
            if (interfaces[i].getClass().getName().equals(Connection.class.getName())) {
                Logger.debugClassLoader("ME2 Connection.class", Connection.class);
                Logger.debugClassLoader(String.valueOf(name) + " Connection.class", interfaces[i]);
                Logger.error("Connection interface loaded by different ClassLoader");
            }
            i++;
        }
        if (connectionClass == null) {
            throw new ClassCastException(String.valueOf(origConnection.getClass().getName()) + " Connection expected");
        }
        return (Connection) Proxy.newProxyInstance(ConnectorImpl.class.getClassLoader(), interfaces, new ConnectionInvocationHandler(origConnection, needPrivilegedCalls));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Connection openSecure(String name, int mode, boolean timeouts) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IOException {
        try {
            try {
                String protocol = name.substring(0, name.indexOf(58));
                String className = "org.microemu.cldc." + protocol + ".Connection";
                Class cl = Class.forName(className);
                Object inst = cl.newInstance();
                if (inst instanceof ConnectionImplementation) {
                    return ((ConnectionImplementation) inst).openConnection(name, mode, timeouts);
                }
                return ((ClosedConnection) inst).open(name);
            } catch (ClassNotFoundException e) {
                try {
                    String className2 = "com.sun.cdc.io.j2me." + ((String) null) + ".Protocol";
                    Class cl2 = Class.forName(className2);
                    ConnectionBaseInterface base = (ConnectionBaseInterface) cl2.newInstance();
                    return base.openPrim(name.substring(name.indexOf(58) + 1), mode, timeouts);
                } catch (ClassNotFoundException ex) {
                    Logger.debug("connection [" + ((String) null) + "] class not found", (Throwable) e);
                    Logger.debug("connection [" + ((String) null) + "] class not found", (Throwable) ex);
                    throw new ConnectionNotFoundException("connection [" + ((String) null) + "] class not found");
                }
            }
        } catch (IllegalAccessException e2) {
            Logger.error("Unable to create", null, e2);
            throw new ConnectionNotFoundException();
        } catch (InstantiationException e3) {
            Logger.error("Unable to create", null, e3);
            throw new ConnectionNotFoundException();
        }
    }
}
