package org.microemu.app.util;

import java.io.IOException;
import java.io.InputStream;

/* loaded from: avacs.jar:org/microemu/app/util/MIDletResourceInputStream.class */
public class MIDletResourceInputStream extends InputStream {
    private InputStream is;

    public MIDletResourceInputStream(InputStream is) {
        this.is = is;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.is.available();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        return this.is.read();
    }

    @Override // java.io.InputStream
    public int read(byte[] b) throws IOException {
        int count;
        int result = 0;
        do {
            count = this.is.read(b, result, b.length - result);
            if (count != -1) {
                result += count;
                if (result == b.length) {
                    return result;
                }
            } else if (result != 0) {
                return result;
            }
        } while (count != -1);
        return -1;
    }
}
