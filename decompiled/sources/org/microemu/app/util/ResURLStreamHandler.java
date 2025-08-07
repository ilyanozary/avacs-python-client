package org.microemu.app.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Hashtable;

/* loaded from: avacs.jar:org/microemu/app/util/ResURLStreamHandler.class */
public class ResURLStreamHandler extends URLStreamHandler {
    private Hashtable entries;

    public ResURLStreamHandler(Hashtable entries) {
        this.entries = entries;
    }

    @Override // java.net.URLStreamHandler
    protected URLConnection openConnection(URL url) throws IOException {
        return new ResURLConnection(url, this.entries);
    }
}
