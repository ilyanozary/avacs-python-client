package org.microemu.app.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;
import javax.microedition.media.control.StopTimeControl;
import org.microemu.MIDletBridge;
import org.microemu.MIDletContext;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/app/util/MIDletTimer.class */
public class MIDletTimer extends Timer implements Runnable {
    private static Map midlets = new WeakHashMap();
    private String name;
    private MIDletContext midletContext;
    List tasks;
    private boolean cancelled;
    private MIDletThread thread;

    public MIDletTimer() {
        StackTraceElement[] ste = new Throwable().getStackTrace();
        this.name = String.valueOf(ste[1].getClassName()) + "." + ste[1].getMethodName();
        this.tasks = new ArrayList();
        this.cancelled = false;
        this.thread = new MIDletThread(this);
        this.thread.start();
    }

    @Override // java.util.Timer
    public void schedule(TimerTask task, Date time) {
        register(this);
        schedule(task, time.getTime(), -1L, false);
    }

    @Override // java.util.Timer
    public void schedule(TimerTask task, Date firstTime, long period) {
        register(this);
        schedule(task, firstTime.getTime(), period, false);
    }

    @Override // java.util.Timer
    public void schedule(TimerTask task, long delay) {
        register(this);
        schedule(task, System.currentTimeMillis() + delay, -1L, false);
    }

    @Override // java.util.Timer
    public void schedule(TimerTask task, long delay, long period) {
        register(this);
        schedule(task, System.currentTimeMillis() + delay, period, false);
    }

    @Override // java.util.Timer
    public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
        register(this);
        schedule(task, firstTime.getTime(), period, true);
    }

    @Override // java.util.Timer
    public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
        register(this);
        schedule(task, System.currentTimeMillis() + delay, period, true);
    }

    @Override // java.util.Timer
    public void cancel() {
        unregister(this);
        terminate();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11, types: [boolean] */
    /* JADX WARN: Type inference failed for: r0v18, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v19, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v22, types: [int] */
    /* JADX WARN: Type inference failed for: r0v36, types: [int] */
    /* JADX WARN: Type inference failed for: r0v37, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v39, types: [java.lang.Object, java.util.List] */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v6, types: [java.lang.Throwable] */
    @Override // java.lang.Runnable
    public void run() {
        while (!this.cancelled) {
            MIDletTimerTask task = null;
            long nextTimeTask = Long.MAX_VALUE;
            ?? HasNext = this.tasks;
            synchronized (HasNext) {
                Iterator it = this.tasks.iterator();
                while (true) {
                    HasNext = it.hasNext();
                    if (HasNext == 0) {
                        break;
                    }
                    MIDletTimerTask candidate = (MIDletTimerTask) it.next();
                    if (candidate.time > System.currentTimeMillis()) {
                        if (candidate.time < nextTimeTask) {
                            nextTimeTask = candidate.time;
                        }
                    } else if (task == null) {
                        task = candidate;
                    } else if (candidate.time < task.time) {
                        if (task.time < nextTimeTask) {
                            nextTimeTask = task.time;
                        }
                        task = candidate;
                    } else if (candidate.time < nextTimeTask) {
                        nextTimeTask = candidate.time;
                    }
                }
                if (task != null) {
                    if (task.period > 0) {
                        task.oneTimeTaskExcecuted = true;
                    }
                    this.tasks.remove(task);
                }
            }
            if (task != null) {
                try {
                    task.run();
                    ?? r0 = this.tasks;
                    synchronized (r0) {
                        r0 = (task.period > 0L ? 1 : (task.period == 0L ? 0 : -1));
                        if (r0 > 0) {
                            task.time = System.currentTimeMillis() + task.period;
                            this.tasks.add(task);
                            if (task.time < nextTimeTask) {
                                nextTimeTask = task.time;
                            }
                        }
                    }
                } catch (Throwable t) {
                    Logger.debug("MIDletTimerTask throws", t);
                }
            }
            synchronized (this.tasks) {
                ?? r02 = (nextTimeTask > StopTimeControl.RESET ? 1 : (nextTimeTask == StopTimeControl.RESET ? 0 : -1));
                if (r02 == 0) {
                    try {
                        r02 = this.tasks;
                        r02.wait();
                    } catch (InterruptedException e) {
                    }
                } else {
                    long timeout = nextTimeTask - System.currentTimeMillis();
                    if (timeout > 0) {
                        this.tasks.wait(timeout);
                    }
                }
            }
        }
    }

    private void terminate() {
        this.cancelled = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    private void schedule(TimerTask task, long time, long period, boolean fixedRate) {
        ?? r0 = this.tasks;
        synchronized (r0) {
            ((MIDletTimerTask) task).timer = this;
            ((MIDletTimerTask) task).time = time;
            ((MIDletTimerTask) task).period = period;
            this.tasks.add(task);
            this.tasks.notify();
            r0 = r0;
        }
    }

    private static void register(MIDletTimer timer) {
        if (timer.midletContext == null) {
            timer.midletContext = MIDletBridge.getMIDletContext();
        }
        if (timer.midletContext == null) {
            Logger.error("Creating Timer with no MIDlet context", new Throwable());
            return;
        }
        Map timers = (Map) midlets.get(timer.midletContext);
        if (timers == null) {
            timers = new HashMap();
            midlets.put(timer.midletContext, timers);
        }
        timers.put(timer, timer.midletContext);
    }

    private static void unregister(MIDletTimer timer) {
        Map timers;
        if (timer.midletContext == null || (timers = (Map) midlets.get(timer.midletContext)) == null) {
            return;
        }
        timers.remove(timer);
    }

    public static void contextDestroyed(MIDletContext midletContext) {
        Map timers;
        if (midletContext != null && (timers = (Map) midlets.get(midletContext)) != null) {
            terminateTimers(timers);
            midlets.remove(midletContext);
        }
    }

    private static void terminateTimers(Map timers) {
        for (Object o : timers.keySet()) {
            if (o != null) {
                if (o instanceof MIDletTimer) {
                    MIDletTimer tm = (MIDletTimer) o;
                    Logger.warn("MIDlet timer created from [" + tm.name + "] still running");
                    tm.terminate();
                } else {
                    Logger.debug("unrecognized Object [" + o.getClass().getName() + "]");
                }
            }
        }
    }
}
