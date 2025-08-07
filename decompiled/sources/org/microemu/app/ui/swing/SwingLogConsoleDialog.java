package org.microemu.app.ui.swing;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import org.microemu.app.Config;
import org.microemu.app.ui.swing.logconsole.LogTextArea;
import org.microemu.app.util.RuntimeDetect;
import org.microemu.log.Logger;
import org.microemu.log.LoggerAppender;
import org.microemu.log.LoggingEvent;
import org.microemu.log.QueueAppender;
import org.microemu.log.StdOutAppender;

/* loaded from: avacs.jar:org/microemu/app/ui/swing/SwingLogConsoleDialog.class */
public class SwingLogConsoleDialog extends JFrame implements LoggerAppender {
    private static final long serialVersionUID = 1;
    private static final boolean tests = false;
    private boolean isShown;
    private LogTextArea logArea;
    private Vector logLinesQueue;
    private int testEventCounter;

    /* loaded from: avacs.jar:org/microemu/app/ui/swing/SwingLogConsoleDialog$SwingLogUpdater.class */
    private class SwingLogUpdater implements Runnable {
        private SwingLogUpdater() {
        }

        /* synthetic */ SwingLogUpdater(SwingLogConsoleDialog swingLogConsoleDialog, SwingLogUpdater swingLogUpdater) {
            this();
        }

        /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.Throwable, java.util.Vector] */
        private String getNextLine() {
            synchronized (SwingLogConsoleDialog.this.logLinesQueue) {
                if (SwingLogConsoleDialog.this.logLinesQueue.isEmpty()) {
                    return null;
                }
                String line = (String) SwingLogConsoleDialog.this.logLinesQueue.firstElement();
                SwingLogConsoleDialog.this.logLinesQueue.removeElementAt(0);
                return line;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                String line = getNextLine();
                if (line != null) {
                    SwingLogConsoleDialog.this.logArea.append(line);
                } else {
                    return;
                }
            }
        }
    }

    public SwingLogConsoleDialog(Frame owner, QueueAppender logQueueAppender) {
        super("Log console");
        this.logLinesQueue = new Vector();
        this.testEventCounter = 0;
        setIconImage(owner.getIconImage());
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Log");
        JMenuItem menuClear = new JMenuItem("Clear");
        menuClear.addActionListener(new ActionListener() { // from class: org.microemu.app.ui.swing.SwingLogConsoleDialog.1
            public void actionPerformed(ActionEvent e) {
                SwingLogConsoleDialog.this.logArea.setText("");
            }
        });
        menu.add(menuClear);
        menu.addSeparator();
        final JCheckBoxMenuItem menuRecordLocation = new JCheckBoxMenuItem("Show record location");
        menuRecordLocation.setState(Logger.isLocationEnabled());
        menuRecordLocation.addActionListener(new ActionListener() { // from class: org.microemu.app.ui.swing.SwingLogConsoleDialog.2
            public void actionPerformed(ActionEvent e) throws IOException {
                Logger.setLocationEnabled(menuRecordLocation.getState());
                Config.setLogConsoleLocationEnabled(menuRecordLocation.getState());
            }
        });
        menu.add(menuRecordLocation);
        final JCheckBoxMenuItem menuStdOut = new JCheckBoxMenuItem("Write to standard output");
        menuStdOut.setState(StdOutAppender.enabled);
        menuStdOut.addActionListener(new ActionListener() { // from class: org.microemu.app.ui.swing.SwingLogConsoleDialog.3
            public void actionPerformed(ActionEvent e) {
                StdOutAppender.enabled = menuStdOut.getState();
            }
        });
        menu.add(menuStdOut);
        menuBar.add(menu);
        if (RuntimeDetect.isJava15()) {
            JMenu j5Menu = new JMenu("Threads");
            JMenuItem menuThreadDump = new JMenuItem("ThreadDump to console");
            menuThreadDump.addActionListener(new ActionListener() { // from class: org.microemu.app.ui.swing.SwingLogConsoleDialog.4
                public void actionPerformed(ActionEvent e) {
                    Logger.threadDumpToConsole();
                }
            });
            j5Menu.add(menuThreadDump);
            JMenuItem menuThreadDumpFile = new JMenuItem("ThreadDump to file");
            menuThreadDumpFile.addActionListener(new ActionListener() { // from class: org.microemu.app.ui.swing.SwingLogConsoleDialog.5
                public void actionPerformed(ActionEvent e) throws IOException {
                    Logger.threadDumpToFile();
                }
            });
            j5Menu.add(menuThreadDumpFile);
            menuBar.add(j5Menu);
        }
        setJMenuBar(menuBar);
        this.logArea = new LogTextArea(20, 40, 1000);
        Font logFont = new Font("Monospaced", 0, 12);
        this.logArea.setFont(logFont);
        JScrollPane scrollPane = new JScrollPane(this.logArea);
        scrollPane.setAutoscrolls(false);
        getContentPane().add(scrollPane);
        Logger.addAppender(this);
        Logger.removeAppender(logQueueAppender);
        while (true) {
            LoggingEvent event = logQueueAppender.poll();
            if (event != null) {
                append(event);
            } else {
                return;
            }
        }
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
        this.isShown = true;
        if (this.isShown) {
            SwingUtilities.invokeLater(new SwingLogUpdater(this, null));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.util.Vector] */
    /* JADX WARN: Type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r0v9 */
    private void log(String message) {
        boolean createUpdater = false;
        ?? r0 = this.logLinesQueue;
        synchronized (r0) {
            if (this.logLinesQueue.isEmpty()) {
                createUpdater = true;
            }
            this.logLinesQueue.addElement(message);
            r0 = r0;
            if (createUpdater && this.isShown) {
                SwingUtilities.invokeLater(new SwingLogUpdater(this, null));
            }
        }
    }

    private String formatLocation(StackTraceElement ste) {
        if (ste == null) {
            return "";
        }
        return String.valueOf(ste.getClassName()) + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")";
    }

    private String formatEventTime(long eventTime) {
        DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS ");
        return format.format(new Date(eventTime));
    }

    @Override // org.microemu.log.LoggerAppender
    public void append(LoggingEvent event) {
        StringBuffer bug = new StringBuffer(formatEventTime(event.getEventTime()));
        if (event.getLevel() == 4) {
            bug.append("Error:");
        }
        bug.append(event.getMessage());
        if (event.hasData()) {
            bug.append(" [").append(event.getFormatedData()).append("]");
        }
        String location = formatLocation(event.getLocation());
        if (location.length() > 0) {
            bug.append("\n\t  ");
        }
        bug.append(location);
        if (event.getThrowable() != null) {
            OutputStream out = new ByteArrayOutputStream();
            PrintStream stream = new PrintStream(out);
            event.getThrowable().printStackTrace(stream);
            stream.flush();
            bug.append(out.toString());
        }
        bug.append("\n");
        log(bug.toString());
    }
}
