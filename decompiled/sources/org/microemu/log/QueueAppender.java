package org.microemu.log;

import java.util.LinkedList;
import java.util.List;

/* loaded from: avacs.jar:org/microemu/log/QueueAppender.class */
public class QueueAppender implements LoggerAppender {
    private int buferSize;
    private List queue = new LinkedList();

    public QueueAppender(int buferSize) {
        this.buferSize = buferSize;
    }

    @Override // org.microemu.log.LoggerAppender
    public void append(LoggingEvent event) {
        this.queue.add(event);
        if (this.queue.size() > this.buferSize) {
            this.queue.remove(0);
        }
    }

    public LoggingEvent poll() {
        if (this.queue.size() == 0) {
            return null;
        }
        return (LoggingEvent) this.queue.remove(0);
    }
}
