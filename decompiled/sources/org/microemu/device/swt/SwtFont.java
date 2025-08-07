package org.microemu.device.swt;

import org.microemu.device.impl.Font;

/* loaded from: avacs.jar:org/microemu/device/swt/SwtFont.class */
public interface SwtFont extends Font {
    org.eclipse.swt.graphics.Font getFont();

    void setAntialiasing(boolean z);
}
