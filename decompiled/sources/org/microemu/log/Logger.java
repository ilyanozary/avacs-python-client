package org.microemu.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.microedition.media.PlayerListener;
import org.microemu.app.util.IOUtils;

/* loaded from: avacs.jar:org/microemu/log/Logger.class */
public class Logger {
    private static final String FQCN = Logger.class.getName();
    private static final Set fqcnSet = new HashSet();
    private static final Set logFunctionsSet = new HashSet();
    private static boolean java13 = false;
    private static boolean locationEnabled = true;
    private static List loggerAppenders = new Vector();

    static {
        fqcnSet.add(FQCN);
        addAppender(new StdOutAppender());
        logFunctionsSet.add("debug");
        logFunctionsSet.add("log");
        logFunctionsSet.add(PlayerListener.ERROR);
        logFunctionsSet.add("fatal");
        logFunctionsSet.add("info");
        logFunctionsSet.add("warn");
    }

    public static boolean isDebugEnabled() {
        return false;
    }

    public static boolean isErrorEnabled() {
        return false;
    }

    public static boolean isLocationEnabled() {
        return locationEnabled;
    }

    public static void setLocationEnabled(boolean state) {
        locationEnabled = state;
    }

    private static StackTraceElement getLocation() {
        if (java13 || !locationEnabled) {
            return null;
        }
        try {
            StackTraceElement[] ste = new Throwable().getStackTrace();
            boolean wrapperFound = false;
            for (int i = 0; i < ste.length - 1; i++) {
                if (fqcnSet.contains(ste[i].getClassName())) {
                    wrapperFound = false;
                    String nextClassName = ste[i + 1].getClassName();
                    if (!nextClassName.startsWith("java.") && !nextClassName.startsWith("sun.") && !fqcnSet.contains(nextClassName)) {
                        if (logFunctionsSet.contains(ste[i + 1].getMethodName())) {
                            wrapperFound = true;
                        } else {
                            if (nextClassName.startsWith("$Proxy")) {
                                return ste[i + 2];
                            }
                            return ste[i + 1];
                        }
                    }
                } else if (wrapperFound && !logFunctionsSet.contains(ste[i].getMethodName())) {
                    return ste[i];
                }
            }
            return ste[ste.length - 1];
        } catch (Throwable th) {
            java13 = true;
            return null;
        }
    }

    private static void write(int level, String message, Throwable throwable) {
        while (message != null && message.endsWith("\n")) {
            message = message.substring(0, message.length() - 1);
        }
        callAppenders(new LoggingEvent(level, message, getLocation(), throwable));
    }

    private static void write(int level, String message, Throwable throwable, Object data) {
        callAppenders(new LoggingEvent(level, message, getLocation(), throwable, data));
    }

    public static void debug(String message) {
        if (isDebugEnabled()) {
            write(1, message, null);
        }
    }

    public static void debug(String message, Throwable t) {
        if (isDebugEnabled()) {
            write(1, message, t);
        }
    }

    public static void debug(Throwable t) {
        if (isDebugEnabled()) {
            write(1, PlayerListener.ERROR, t);
        }
    }

    public static void debug(String message, String v) {
        if (isDebugEnabled()) {
            write(1, message, null, v);
        }
    }

    public static void debug(String message, Object o) {
        if (isDebugEnabled()) {
            write(1, message, null, new LoggerDataWrapper(o));
        }
    }

    public static void debug(String message, String v1, String v2) {
        if (isDebugEnabled()) {
            write(1, message, null, new LoggerDataWrapper(v1, v2));
        }
    }

    public static void debug(String message, long v) {
        if (isDebugEnabled()) {
            write(1, message, null, new LoggerDataWrapper(v));
        }
    }

    public static void debug0x(String message, long v) {
        if (isDebugEnabled()) {
            write(1, message, null, new LoggerDataWrapper("0x" + Long.toHexString(v)));
        }
    }

    public static void debug(String message, long v1, long v2) {
        if (isDebugEnabled()) {
            write(1, message, null, new LoggerDataWrapper(v1, v2));
        }
    }

    public static void debug(String message, boolean v) {
        if (isDebugEnabled()) {
            write(1, message, null, new LoggerDataWrapper(v));
        }
    }

    public static void debugClassLoader(String message, Object obj) {
        Class klass;
        if (obj == null) {
            write(1, String.valueOf(message) + " no class, no object", null, null);
            return;
        }
        StringBuffer buf = new StringBuffer();
        buf.append(message).append(" ");
        if (obj instanceof Class) {
            klass = (Class) obj;
            buf.append("class ");
        } else {
            klass = obj.getClass();
            buf.append("instance ");
        }
        buf.append(String.valueOf(klass.getName()) + " loaded by ");
        if (klass.getClassLoader() != null) {
            buf.append(klass.getClassLoader().hashCode());
            buf.append(" ");
            buf.append(klass.getClassLoader().getClass().getName());
        } else {
            buf.append("system");
        }
        write(1, buf.toString(), null, null);
    }

    public static void info(String message) {
        if (isErrorEnabled()) {
            write(2, message, null);
        }
    }

    public static void info(Object message) {
        if (isErrorEnabled()) {
            write(2, new StringBuilder().append(message).toString(), null);
        }
    }

    public static void info(String message, String data) {
        if (isErrorEnabled()) {
            write(2, message, null, data);
        }
    }

    public static void warn(String message) {
        if (isErrorEnabled()) {
            write(3, message, null);
        }
    }

    public static void error(String message) {
        if (isErrorEnabled()) {
            write(4, "error " + message, null);
        }
    }

    public static void error(Object message) {
        if (isErrorEnabled()) {
            write(4, "error " + message, null);
        }
    }

    public static void error(String message, long v) {
        if (isErrorEnabled()) {
            write(4, "error " + message, null, new LoggerDataWrapper(v));
        }
    }

    public static void error(String message, String v) {
        if (isErrorEnabled()) {
            write(4, "error " + message, null, v);
        }
    }

    public static void error(String message, String v, Throwable t) {
        if (isErrorEnabled()) {
            write(4, "error " + message, t, v);
        }
    }

    public static void error(Throwable t) {
        if (isErrorEnabled()) {
            write(4, "error " + t.toString(), t);
        }
    }

    public static void error(String message, Throwable t) {
        if (isErrorEnabled()) {
            write(4, "error " + message + " " + t.toString(), t);
        }
    }

    private static void callAppenders(LoggingEvent event) {
        for (LoggerAppender a : loggerAppenders) {
            a.append(event);
        }
    }

    public static void addLogOrigin(Class origin) {
        fqcnSet.add(origin.getName());
    }

    public static void addAppender(LoggerAppender newAppender) {
        loggerAppenders.add(newAppender);
    }

    public static void removeAppender(LoggerAppender appender) {
        loggerAppenders.remove(appender);
    }

    public static void removeAllAppenders() {
        loggerAppenders.clear();
    }

    public static void threadDumpToConsole() {
        try {
            StringBuffer out = new StringBuffer("Full ThreadDump\n");
            Map traces = Thread.getAllStackTraces();
            for (Map.Entry<Thread, StackTraceElement[]> entry : traces.entrySet()) {
                Thread thread = entry.getKey();
                out.append("Thread= " + thread.getName() + " " + (thread.isDaemon() ? "daemon" : "") + " prio=" + thread.getPriority() + "id=" + thread.getId() + " " + thread.getState());
                out.append("\n");
                StackTraceElement[] ste = entry.getValue();
                for (StackTraceElement stackTraceElement : ste) {
                    out.append("\t");
                    out.append(stackTraceElement.toString());
                    out.append("\n");
                }
                out.append("---------------------------------\n");
            }
            info(out.toString());
        } catch (Throwable th) {
        }
    }

    public static void threadDumpToFile() throws IOException {
        SimpleDateFormat fmt = new SimpleDateFormat("MM-dd_HH-mm-ss");
        OutputStreamWriter out = null;
        try {
            File file = new File("ThreadDump-" + fmt.format(new Date()) + ".log");
            OutputStreamWriter out2 = new FileWriter(file);
            Map traces = Thread.getAllStackTraces();
            for (Map.Entry<Thread, StackTraceElement[]> entry : traces.entrySet()) {
                Thread thread = entry.getKey();
                out2.write("Thread= " + thread.getName() + " " + (thread.isDaemon() ? "daemon" : "") + " prio=" + thread.getPriority() + "id=" + thread.getId() + " " + thread.getState());
                out2.write("\n");
                StackTraceElement[] ste = entry.getValue();
                for (StackTraceElement stackTraceElement : ste) {
                    out2.write("\t");
                    out2.write(stackTraceElement.toString());
                    out2.write("\n");
                }
                out2.write("---------------------------------\n");
            }
            out2.close();
            out = null;
            info("Full ThreadDump created " + file.getAbsolutePath());
            IOUtils.closeQuietly((Writer) null);
        } catch (Throwable th) {
            IOUtils.closeQuietly(out);
            throw th;
        }
    }
}
