package org.microemu;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import org.microemu.app.util.MIDletOutputStreamRedirector;
import org.microemu.app.util.MIDletResourceLoader;
import org.microemu.app.util.MIDletSystemProperties;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/Injected.class */
public final class Injected implements Serializable {
    private static final long serialVersionUID = -1;
    public static final PrintStream out = outPrintStream();
    public static final PrintStream err = errPrintStream();

    static {
        Logger.addLogOrigin(Injected.class);
    }

    private Injected() {
    }

    private static PrintStream outPrintStream() {
        return MIDletOutputStreamRedirector.out;
    }

    private static PrintStream errPrintStream() {
        return MIDletOutputStreamRedirector.err;
    }

    public static void printStackTrace(Throwable t) {
        Logger.error("MIDlet caught", t);
    }

    public static String getProperty(String key) {
        return MIDletSystemProperties.getProperty(key);
    }

    public static InputStream getResourceAsStream(Class origClass, String name) {
        return MIDletResourceLoader.getResourceAsStream(origClass, name);
    }

    public static Throwable handleCatchThrowable(Throwable t) {
        Logger.error("MIDlet caught", t);
        return t;
    }
}
