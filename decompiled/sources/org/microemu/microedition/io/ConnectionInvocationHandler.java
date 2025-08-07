package org.microemu.microedition.io;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.microedition.io.Connection;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/microedition/io/ConnectionInvocationHandler.class */
public class ConnectionInvocationHandler implements InvocationHandler {
    private Connection originalConnection;
    private AccessControlContext acc;

    static {
        Logger.addLogOrigin(ConnectionInvocationHandler.class);
    }

    public ConnectionInvocationHandler(Connection con, boolean needPrivilegedCalls) {
        this.originalConnection = con;
        if (needPrivilegedCalls) {
            this.acc = AccessController.getContext();
        }
    }

    @Override // java.lang.reflect.InvocationHandler
    public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
        if (ConnectorImpl.debugConnectionInvocations) {
            Logger.debug("invoke", method);
        }
        try {
            if (this.acc != null) {
                return AccessController.doPrivileged(new PrivilegedExceptionAction() { // from class: org.microemu.microedition.io.ConnectionInvocationHandler.1
                    @Override // java.security.PrivilegedExceptionAction
                    public Object run() throws IllegalAccessException, InvocationTargetException {
                        return method.invoke(ConnectionInvocationHandler.this.originalConnection, args);
                    }
                }, this.acc);
            }
            return method.invoke(this.originalConnection, args);
        } catch (InvocationTargetException e) {
            if (ConnectorImpl.debugConnectionInvocations) {
                Logger.error("Connection." + method.getName(), e.getCause());
            }
            throw e.getCause();
        } catch (PrivilegedActionException e2) {
            if (e2.getCause() instanceof InvocationTargetException) {
                if (ConnectorImpl.debugConnectionInvocations) {
                    Logger.error("Connection." + method.getName(), e2.getCause().getCause());
                }
                throw e2.getCause().getCause();
            }
            if (ConnectorImpl.debugConnectionInvocations) {
                Logger.error("Connection." + method.getName(), e2.getCause());
            }
            throw e2.getCause();
        }
    }
}
