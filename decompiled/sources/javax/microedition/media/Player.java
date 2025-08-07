package javax.microedition.media;

/* loaded from: avacs.jar:javax/microedition/media/Player.class */
public interface Player extends Controllable {
    public static final int UNREALIZED = 100;
    public static final int REALIZED = 200;
    public static final int PREFETCHED = 300;
    public static final int STARTED = 400;
    public static final int CLOSED = 0;
    public static final long TIME_UNKNOWN = -1;

    void realize() throws MediaException;

    void prefetch() throws MediaException;

    void start() throws MediaException;

    void stop() throws MediaException;

    void deallocate();

    void close();

    long setMediaTime(long j) throws MediaException;

    long getMediaTime();

    int getState();

    long getDuration();

    String getContentType();

    void setLoopCount(int i);

    void addPlayerListener(PlayerListener playerListener);

    void removePlayerListener(PlayerListener playerListener);
}
