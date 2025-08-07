package org.microemu.app.util;

import java.io.OutputStream;
import java.io.PrintStream;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/app/util/MIDletOutputStreamRedirector.class */
public class MIDletOutputStreamRedirector extends PrintStream {
    private static final boolean keepMultiLinePrint = true;
    public static final PrintStream out = outPrintStream();
    public static final PrintStream err = errPrintStream();
    private boolean isErrorStream;

    static {
        Logger.addLogOrigin(MIDletOutputStreamRedirector.class);
        Logger.addLogOrigin(OutputStream2Log.class);
    }

    /* loaded from: avacs.jar:org/microemu/app/util/MIDletOutputStreamRedirector$OutputStream2Log.class */
    private static class OutputStream2Log extends OutputStream {
        boolean isErrorStream;
        StringBuffer buffer = new StringBuffer();

        OutputStream2Log(boolean error) {
            this.isErrorStream = error;
        }

        @Override // java.io.OutputStream, java.io.Flushable
        public void flush() {
            if (this.buffer.length() > 0) {
                write(10);
            }
        }

        @Override // java.io.OutputStream
        public void write(int b) {
            if (b == 10 || b == 13) {
                if (this.buffer.length() > 0) {
                    if (this.isErrorStream) {
                        Logger.error(this.buffer.toString());
                    } else {
                        Logger.info(this.buffer.toString());
                    }
                    this.buffer = new StringBuffer();
                    return;
                }
                return;
            }
            this.buffer.append((char) b);
        }
    }

    private MIDletOutputStreamRedirector(boolean error) {
        super(new OutputStream2Log(error));
        this.isErrorStream = error;
    }

    private static PrintStream outPrintStream() {
        return new MIDletOutputStreamRedirector(false);
    }

    private static PrintStream errPrintStream() {
        return new MIDletOutputStreamRedirector(true);
    }

    @Override // java.io.PrintStream
    public void print(boolean b) {
        super.print(b);
    }

    @Override // java.io.PrintStream
    public void print(char c) {
        super.print(c);
    }

    @Override // java.io.PrintStream
    public void print(char[] s) {
        super.print(s);
    }

    @Override // java.io.PrintStream
    public void print(double d) {
        super.print(d);
    }

    @Override // java.io.PrintStream
    public void print(float f) {
        super.print(f);
    }

    @Override // java.io.PrintStream
    public void print(int i) {
        super.print(i);
    }

    @Override // java.io.PrintStream
    public void print(long l) {
        super.print(l);
    }

    @Override // java.io.PrintStream
    public void print(Object obj) {
        super.print(obj);
    }

    @Override // java.io.PrintStream
    public void print(String s) {
        super.print(s);
    }

    @Override // java.io.PrintStream
    public void println() {
        super.println();
    }

    @Override // java.io.PrintStream
    public void println(boolean x) {
        super.println(x);
    }

    @Override // java.io.PrintStream
    public void println(char x) {
        super.println(x);
    }

    @Override // java.io.PrintStream
    public void println(char[] x) {
        super.println(x);
    }

    @Override // java.io.PrintStream
    public void println(double x) {
        super.println(x);
    }

    @Override // java.io.PrintStream
    public void println(float x) {
        super.println(x);
    }

    @Override // java.io.PrintStream
    public void println(int x) {
        super.println(x);
    }

    @Override // java.io.PrintStream
    public void println(long x) {
        super.println(x);
    }

    @Override // java.io.PrintStream
    public void println(Object x) {
        super.flush();
        if (this.isErrorStream) {
            Logger.error(x);
        } else {
            Logger.info(x);
        }
    }

    @Override // java.io.PrintStream
    public void println(String x) {
        super.flush();
        if (this.isErrorStream) {
            Logger.error(x);
        } else {
            Logger.info(x);
        }
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);
    }

    @Override // java.io.PrintStream, java.io.FilterOutputStream, java.io.OutputStream
    public void write(int b) {
        super.write(b);
    }
}
