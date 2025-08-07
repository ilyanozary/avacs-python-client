package org.microemu.app.ui;

import java.util.List;
import java.util.Vector;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/app/ui/Message.class */
public class Message {
    public static final int ERROR = 0;
    public static final int INFO = 1;
    public static final int WARN = 2;
    private static List listeners = new Vector();

    static {
        Logger.addLogOrigin(Message.class);
    }

    public static void error(String title, String text) {
        Logger.error("Message: " + title + ": " + text);
        callListeners(0, title, text, null);
    }

    public static void error(String text) {
        Logger.error("Message: Error: " + text);
        callListeners(0, "Error", text, null);
    }

    public static void error(String title, String text, Throwable throwable) {
        Logger.error("Message: " + title + ": " + text, throwable);
        callListeners(0, title, text, throwable);
    }

    public static void error(String text, Throwable throwable) {
        Logger.error("Message: Error : " + text, throwable);
        callListeners(0, "Error", text, throwable);
    }

    public static void info(String text) {
        Logger.info("Message: info: " + text);
        callListeners(1, "Info", text, null);
    }

    public static void warn(String text) {
        Logger.warn("Message: warn: " + text);
        callListeners(1, "Warning", text, null);
    }

    public static String getCauseMessage(Throwable throwable) {
        if (throwable.getCause() == null) {
            return throwable.toString();
        }
        return getCauseMessage(throwable.getCause());
    }

    private static void callListeners(int level, String title, String text, Throwable throwable) {
        for (MessageListener a : listeners) {
            a.showMessage(level, title, text, throwable);
        }
    }

    public static void addListener(MessageListener newListener) {
        listeners.add(newListener);
    }
}
