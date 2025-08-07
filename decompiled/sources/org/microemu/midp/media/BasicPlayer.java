package org.microemu.midp.media;

import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;

/* loaded from: avacs.jar:org/microemu/midp/media/BasicPlayer.class */
public abstract class BasicPlayer implements Player {
    public static String CONTROL_TYPE = "ToneControl";
    private int state;
    private int loopCount;
    private TimeBase timeBase;
    private Vector listenersVector;

    public BasicPlayer() {
        setListenersVector(new Vector());
        setLoopCount(0);
        setState(100);
    }

    @Override // javax.microedition.media.Player
    public synchronized void addPlayerListener(PlayerListener playerListener) {
        getListenersVector().add(playerListener);
    }

    @Override // javax.microedition.media.Player
    public void removePlayerListener(PlayerListener playerListener) {
        Enumeration enumeration = getListenersVector().elements();
        while (enumeration.hasMoreElements()) {
            PlayerListener listener = (PlayerListener) enumeration.nextElement();
            if (listener == playerListener) {
                getListenersVector().remove(listener);
                return;
            }
        }
    }

    @Override // javax.microedition.media.Player
    public int getState() {
        return this.state;
    }

    public synchronized void setState(int state) {
        this.state = state;
    }

    @Override // javax.microedition.media.Player
    public long getDuration() {
        return 0L;
    }

    @Override // javax.microedition.media.Player
    public long getMediaTime() {
        return 0L;
    }

    public TimeBase getTimeBase() {
        return this.timeBase;
    }

    public synchronized void setTimeBase(TimeBase timeBase) {
        this.timeBase = timeBase;
    }

    @Override // javax.microedition.media.Player
    public void deallocate() {
    }

    @Override // javax.microedition.media.Player
    public void prefetch() throws MediaException {
    }

    @Override // javax.microedition.media.Player
    public void realize() throws MediaException {
    }

    @Override // javax.microedition.media.Player
    public synchronized void setLoopCount(int count) {
        this.loopCount = count;
    }

    protected int getLoopCount() {
        return this.loopCount;
    }

    @Override // javax.microedition.media.Player
    public synchronized long setMediaTime(long now) throws MediaException {
        return 0L;
    }

    protected Vector getListenersVector() {
        return this.listenersVector;
    }

    protected synchronized void setListenersVector(Vector listenersVector) {
        this.listenersVector = listenersVector;
    }

    @Override // javax.microedition.media.Player
    public synchronized void start() throws MediaException {
        setState(400);
    }

    @Override // javax.microedition.media.Player
    public synchronized void stop() throws MediaException {
        setState(300);
    }
}
