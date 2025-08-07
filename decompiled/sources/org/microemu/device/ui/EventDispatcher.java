package org.microemu.device.ui;

import org.microemu.device.DeviceFactory;

/* loaded from: avacs.jar:org/microemu/device/ui/EventDispatcher.class */
public class EventDispatcher implements Runnable {
    public static final String EVENT_DISPATCHER_NAME = "event-thread";
    public static int maxFps = -1;
    private volatile boolean cancelled = false;
    private Event head = null;
    private Event tail = null;
    private PaintEvent scheduledPaintEvent = null;
    private PointerEvent scheduledPointerDraggedEvent = null;
    private Object serviceRepaintsLock = new Object();
    private long lastPaintEventTime = 0;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r0v14, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v20 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v6, types: [org.microemu.device.ui.EventDispatcher$Event] */
    @Override // java.lang.Runnable
    public void run() {
        while (!this.cancelled) {
            Event event = null;
            ?? r0 = this;
            synchronized (r0) {
                r0 = this.head;
                if (r0 != 0) {
                    event = this.head;
                    if (maxFps > 0 && (event instanceof PaintEvent)) {
                        long difference = System.currentTimeMillis() - this.lastPaintEventTime;
                        if (difference < 1000 / maxFps) {
                            event = null;
                            try {
                                wait((1000 / maxFps) - difference);
                            } catch (InterruptedException e) {
                            }
                        }
                    }
                    if (event != null) {
                        this.head = event.next;
                        if (this.head == null) {
                            this.tail = null;
                        }
                        if ((event instanceof PointerEvent) && ((PointerEvent) event).type == 2) {
                            this.scheduledPointerDraggedEvent = null;
                        }
                    }
                } else {
                    try {
                        wait();
                    } catch (InterruptedException e2) {
                    }
                }
            }
            if (event != null) {
                if (event instanceof PaintEvent) {
                    ?? r02 = this.serviceRepaintsLock;
                    synchronized (r02) {
                        this.scheduledPaintEvent = null;
                        this.lastPaintEventTime = System.currentTimeMillis();
                        post(event);
                        this.serviceRepaintsLock.notifyAll();
                        r02 = r02;
                    }
                } else {
                    post(event);
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v4 */
    public final void cancel() {
        this.cancelled = true;
        ?? r0 = this;
        synchronized (r0) {
            notify();
            r0 = r0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v1, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v33 */
    public void put(Event event) {
        ?? r0 = this;
        synchronized (r0) {
            if ((event instanceof PaintEvent) && this.scheduledPaintEvent != null) {
                this.scheduledPaintEvent.merge((PaintEvent) event);
            } else if ((event instanceof PointerEvent) && this.scheduledPointerDraggedEvent != null && ((PointerEvent) event).type == 2) {
                this.scheduledPointerDraggedEvent.x = ((PointerEvent) event).x;
                this.scheduledPointerDraggedEvent.y = ((PointerEvent) event).y;
            } else {
                if (event instanceof PaintEvent) {
                    this.scheduledPaintEvent = (PaintEvent) event;
                }
                if ((event instanceof PointerEvent) && ((PointerEvent) event).type == 2) {
                    this.scheduledPointerDraggedEvent = (PointerEvent) event;
                }
                if (this.tail != null) {
                    this.tail.next = event;
                }
                this.tail = event;
                if (this.head == null) {
                    this.head = event;
                }
                notify();
            }
            r0 = r0;
        }
    }

    public void put(Runnable runnable) {
        put((Event) new RunnableEvent(runnable));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void serviceRepaints() {
        synchronized (this.serviceRepaintsLock) {
            synchronized (this) {
                if (this.scheduledPaintEvent == null) {
                    return;
                }
                try {
                    this.serviceRepaintsLock.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    protected void post(Event event) {
        event.run();
    }

    /* loaded from: avacs.jar:org/microemu/device/ui/EventDispatcher$Event.class */
    public abstract class Event implements Runnable {
        Event next = null;

        public Event() {
        }
    }

    /* loaded from: avacs.jar:org/microemu/device/ui/EventDispatcher$PaintEvent.class */
    public final class PaintEvent extends Event {
        private int x;
        private int y;
        private int width;
        private int height;

        public PaintEvent(int x, int y, int width, int height) {
            super();
            this.x = -1;
            this.y = -1;
            this.width = -1;
            this.height = -1;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override // java.lang.Runnable
        public void run() {
            DeviceFactory.getDevice().getDeviceDisplay().repaint(this.x, this.y, this.width, this.height);
        }

        public final void merge(PaintEvent event) {
            int xMax = this.x + this.width;
            int yMax = this.y + this.height;
            this.x = Math.min(this.x, event.x);
            int xMax2 = Math.max(xMax, event.x + event.width);
            this.y = Math.min(this.y, event.y);
            int yMax2 = Math.max(yMax, event.y + event.height);
            this.width = xMax2 - this.x;
            this.height = yMax2 - this.y;
        }
    }

    /* loaded from: avacs.jar:org/microemu/device/ui/EventDispatcher$PointerEvent.class */
    public final class PointerEvent extends Event {
        public static final short POINTER_PRESSED = 0;
        public static final short POINTER_RELEASED = 1;
        public static final short POINTER_DRAGGED = 2;
        private Runnable runnable;
        private short type;
        private int x;
        private int y;

        public PointerEvent(Runnable runnable, short type, int x, int y) {
            super();
            this.runnable = runnable;
            this.type = type;
            this.x = x;
            this.y = y;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.runnable.run();
        }
    }

    /* loaded from: avacs.jar:org/microemu/device/ui/EventDispatcher$ShowNotifyEvent.class */
    public final class ShowNotifyEvent extends Event {
        private Runnable runnable;

        public ShowNotifyEvent(Runnable runnable) {
            super();
            this.runnable = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.runnable.run();
        }
    }

    /* loaded from: avacs.jar:org/microemu/device/ui/EventDispatcher$HideNotifyEvent.class */
    public final class HideNotifyEvent extends Event {
        private Runnable runnable;

        public HideNotifyEvent(Runnable runnable) {
            super();
            this.runnable = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.runnable.run();
        }
    }

    /* loaded from: avacs.jar:org/microemu/device/ui/EventDispatcher$RunnableEvent.class */
    private class RunnableEvent extends Event {
        private Runnable runnable;

        public RunnableEvent(Runnable runnable) {
            super();
            this.runnable = runnable;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.runnable.run();
        }
    }
}
