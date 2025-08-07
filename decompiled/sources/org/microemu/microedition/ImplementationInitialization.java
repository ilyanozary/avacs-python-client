package org.microemu.microedition;

import java.util.Map;

/* loaded from: avacs.jar:org/microemu/microedition/ImplementationInitialization.class */
public interface ImplementationInitialization {
    public static final String PARAM_EMULATOR_ID = "emulatorID";

    void registerImplementation(Map map);

    void notifyMIDletStart();

    void notifyMIDletDestroyed();
}
