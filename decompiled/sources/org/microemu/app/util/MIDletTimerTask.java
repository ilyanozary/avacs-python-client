package org.microemu.app.util;

import java.util.TimerTask;

/* loaded from: avacs.jar:org/microemu/app/util/MIDletTimerTask.class */
public abstract class MIDletTimerTask extends TimerTask {
    MIDletTimer timer;
    long period;
    long time = -1;
    boolean oneTimeTaskExcecuted = false;

    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable, java.util.List] */
    @Override // java.util.TimerTask
    public boolean cancel() {
        if (this.timer == null) {
            return false;
        }
        synchronized (this.timer.tasks) {
            if (this.time == -1) {
                return false;
            }
            if (this.oneTimeTaskExcecuted) {
                return false;
            }
            this.timer.tasks.remove(this);
            return true;
        }
    }

    @Override // java.util.TimerTask
    public long scheduledExecutionTime() {
        return this.time;
    }
}
