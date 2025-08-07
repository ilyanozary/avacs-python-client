package org.microemu.cldc.file;

import java.util.Map;
import org.microemu.app.util.MIDletSystemProperties;
import org.microemu.microedition.ImplFactory;
import org.microemu.microedition.ImplementationInitialization;

/* loaded from: avacs.jar:org/microemu/cldc/file/FileSystem.class */
public class FileSystem implements ImplementationInitialization {
    public static final String detectionProperty = "microedition.io.file.FileConnection.version";
    public static final String fsRootConfigProperty = "fsRoot";
    public static final String fsSingleConfigProperty = "fsSingle";
    private FileSystemConnectorImpl impl;

    @Override // org.microemu.microedition.ImplementationInitialization
    public void registerImplementation(Map parameters) {
        String fsRoot = (String) parameters.get(fsRootConfigProperty);
        String fsSingle = (String) parameters.get(fsSingleConfigProperty);
        this.impl = new FileSystemConnectorImpl(fsRoot);
        ImplFactory.registerGCF("file", this.impl);
        ImplFactory.register(FileSystemRegistryDelegate.class, new FileSystemRegistryImpl(fsRoot, fsSingle));
        MIDletSystemProperties.setProperty(detectionProperty, "1.0");
    }

    protected static void unregisterImplementation(FileSystemConnectorImpl impl) {
        MIDletSystemProperties.clearProperty(detectionProperty);
        ImplFactory.unregistedGCF("file", impl);
        ImplFactory.unregister(FileSystemRegistryDelegate.class, FileSystemRegistryImpl.class);
    }

    @Override // org.microemu.microedition.ImplementationInitialization
    public void notifyMIDletStart() {
    }

    @Override // org.microemu.microedition.ImplementationInitialization
    public void notifyMIDletDestroyed() {
        this.impl.notifyMIDletDestroyed();
    }
}
