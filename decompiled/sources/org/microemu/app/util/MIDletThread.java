package org.microemu.app.util;

import java.util.Map;
import java.util.WeakHashMap;
import org.microemu.MIDletBridge;
import org.microemu.MIDletContext;
import org.microemu.log.Logger;
import org.microemu.util.ThreadUtils;

/* loaded from: avacs.jar:org/microemu/app/util/MIDletThread.class */
public class MIDletThread extends Thread {
    private static final String THREAD_NAME_PREFIX = "MIDletThread-";
    private static int threadInitNumber;
    private String callLocation;
    public static int graceTerminationPeriod = 5000;
    private static boolean terminator = false;
    private static Map midlets = new WeakHashMap();

    private static synchronized int nextThreadNum() {
        int i = threadInitNumber;
        threadInitNumber = i + 1;
        return i;
    }

    public MIDletThread() {
        super(THREAD_NAME_PREFIX + nextThreadNum());
        register(this);
    }

    public MIDletThread(Runnable target) {
        super(target, THREAD_NAME_PREFIX + nextThreadNum());
        register(this);
    }

    public MIDletThread(Runnable target, String name) {
        super(target, THREAD_NAME_PREFIX + name);
        register(this);
    }

    public MIDletThread(String name) {
        super(THREAD_NAME_PREFIX + name);
        register(this);
    }

    private static void register(MIDletThread thread) {
        MIDletContext midletContext = MIDletBridge.getMIDletContext();
        if (midletContext == null) {
            Logger.error("Creating thread with no MIDlet context", new Throwable());
            return;
        }
        thread.callLocation = ThreadUtils.getCallLocation(MIDletThread.class.getName());
        Map threads = (Map) midlets.get(midletContext);
        if (threads == null) {
            threads = new WeakHashMap();
            midlets.put(midletContext, threads);
        }
        threads.put(thread, midletContext);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        try {
            super.run();
        } catch (Throwable e) {
            Logger.debug("MIDletThread throws", e);
        }
    }

    public static void contextDestroyed(MIDletContext midletContext) {
        if (midletContext == null) {
            return;
        }
        final Map threads = (Map) midlets.remove(midletContext);
        if (threads != null && threads.size() != 0) {
            terminator = true;
            Thread terminator2 = new Thread("MIDletThreadsTerminator") { // from class: org.microemu.app.util.MIDletThread.1
                @Override // java.lang.Thread, java.lang.Runnable
                public void run() {
                    MIDletThread.terminateThreads(threads);
                }
            };
            terminator2.start();
        }
        MIDletTimer.contextDestroyed(midletContext);
    }

    public static boolean hasRunningThreads(MIDletContext midletContext) {
        return terminator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void terminateThreads(Map threads) {
        long endTime = System.currentTimeMillis() + graceTerminationPeriod;
        for (Object o : threads.keySet()) {
            if (o != null) {
                if (o instanceof MIDletThread) {
                    MIDletThread t = (MIDletThread) o;
                    if (t.isAlive()) {
                        Logger.info("wait thread [" + t.getName() + "] end");
                        while (endTime > System.currentTimeMillis() && t.isAlive()) {
                            try {
                                t.join(700L);
                            } catch (InterruptedException e) {
                            }
                        }
                        if (t.isAlive()) {
                            Logger.warn("MIDlet thread [" + t.getName() + "] still running" + ThreadUtils.getTreadStackTrace(t));
                            if (t.callLocation != null) {
                                Logger.info("this thread [" + t.getName() + "] was created from " + t.callLocation);
                            }
                            t.interrupt();
                        }
                    }
                } else {
                    Logger.debug("unrecognized Object [" + o.getClass().getName() + "]");
                }
            }
        }
        Logger.debug("all " + threads.size() + " thread(s) finished");
        terminator = false;
    }
}
