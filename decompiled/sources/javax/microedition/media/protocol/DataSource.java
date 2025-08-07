package javax.microedition.media.protocol;

import java.io.IOException;
import javax.microedition.media.Control;
import javax.microedition.media.Controllable;

/* loaded from: avacs.jar:javax/microedition/media/protocol/DataSource.class */
public abstract class DataSource implements Controllable {
    private String sourceLocator;

    public abstract String getContentType();

    public abstract void connect() throws IOException;

    public abstract void disconnect();

    public abstract void start() throws IOException;

    public abstract void stop() throws IOException;

    public abstract SourceStream[] getStreams();

    @Override // javax.microedition.media.Controllable
    public abstract Control[] getControls();

    @Override // javax.microedition.media.Controllable
    public abstract Control getControl(String str);

    public DataSource(String locator) {
        this.sourceLocator = locator;
    }

    public String getLocator() {
        return this.sourceLocator;
    }
}
