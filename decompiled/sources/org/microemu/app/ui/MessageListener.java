package org.microemu.app.ui;

/* loaded from: avacs.jar:org/microemu/app/ui/MessageListener.class */
public interface MessageListener {
    public static final int ERROR = 0;
    public static final int INFO = 1;
    public static final int WARN = 2;

    void showMessage(int i, String str, String str2, Throwable th);
}
