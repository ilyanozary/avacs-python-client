package org.microemu.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Timer;

/* loaded from: avacs.jar:org/microemu/util/ThreadUtils.class */
public class ThreadUtils {
    private static boolean java13 = false;
    private static boolean java14 = false;

    public static Timer createTimer(String name) {
        try {
            Constructor c = Timer.class.getConstructor(String.class);
            return (Timer) c.newInstance(name);
        } catch (Throwable th) {
            return new Timer();
        }
    }

    public static String getCallLocation(String fqn) {
        if (!java13) {
            try {
                StackTraceElement[] ste = new Throwable().getStackTrace();
                for (int i = 0; i < ste.length - 1; i++) {
                    if (fqn.equals(ste[i].getClassName())) {
                        StackTraceElement callLocation = ste[i + 1];
                        String nextClassName = callLocation.getClassName();
                        if (!nextClassName.equals(fqn)) {
                            return callLocation.toString();
                        }
                    }
                }
                return null;
            } catch (Throwable th) {
                java13 = true;
                return null;
            }
        }
        return null;
    }

    public static String getTreadStackTrace(Thread t) {
        if (java14) {
            return "";
        }
        try {
            Method m = t.getClass().getMethod("getStackTrace", null);
            StackTraceElement[] trace = (StackTraceElement[]) m.invoke(t, null);
            StringBuffer b = new StringBuffer();
            for (StackTraceElement stackTraceElement : trace) {
                b.append("\n\tat ").append(stackTraceElement);
            }
            return b.toString();
        } catch (Throwable th) {
            java14 = true;
            return "";
        }
    }
}
