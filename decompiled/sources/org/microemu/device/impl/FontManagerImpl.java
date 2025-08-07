package org.microemu.device.impl;

import java.net.URL;
import org.microemu.device.FontManager;

/* loaded from: avacs.jar:org/microemu/device/impl/FontManagerImpl.class */
public interface FontManagerImpl extends FontManager {
    void setAntialiasing(boolean z);

    void setFont(String str, String str2, String str3, Font font);

    Font createSystemFont(String str, String str2, int i, boolean z);

    Font createTrueTypeFont(URL url, String str, int i, boolean z);
}
