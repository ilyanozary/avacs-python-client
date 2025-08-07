package org.microemu.app.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Hashtable;

/* loaded from: avacs.jar:org/microemu/app/util/ResURLConnection.class */
public class ResURLConnection extends URLConnection {
    private static final String PREFIX = "res:";
    private Hashtable entries;

    protected ResURLConnection(URL url, Hashtable entries) {
        super(url);
        this.entries = entries;
    }

    @Override // java.net.URLConnection
    public void connect() throws IOException {
    }

    @Override // java.net.URLConnection
    public InputStream getInputStream() throws IOException {
        String location = this.url.toString();
        int idx = location.indexOf(PREFIX);
        if (idx == -1) {
            throw new IOException();
        }
        byte[] data = (byte[]) this.entries.get(location.substring(idx + PREFIX.length()));
        if (data == null) {
            throw new IOException();
        }
        return new ByteArrayInputStream(data);
    }
}
