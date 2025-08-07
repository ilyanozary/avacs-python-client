package org.microemu.cldc.http;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.microedition.io.HttpConnection;
import org.microemu.microedition.io.ConnectionImplementation;

/* loaded from: avacs.jar:org/microemu/cldc/http/Connection.class */
public class Connection implements HttpConnection, ConnectionImplementation {
    protected URLConnection cn;
    protected boolean connected = false;
    protected static boolean allowNetworkConnection = true;

    @Override // org.microemu.microedition.io.ConnectionImplementation
    public javax.microedition.io.Connection openConnection(String name, int mode, boolean timeouts) throws IOException {
        if (!isAllowNetworkConnection()) {
            throw new IOException("No network");
        }
        try {
            URL url = new URL(name);
            this.cn = url.openConnection();
            this.cn.setDoOutput(true);
            if (this.cn instanceof HttpURLConnection) {
                ((HttpURLConnection) this.cn).setInstanceFollowRedirects(false);
            }
            return this;
        } catch (MalformedURLException ex) {
            throw new IOException(ex.toString());
        }
    }

    @Override // javax.microedition.io.Connection
    public void close() throws IOException {
        if (this.cn == null) {
            return;
        }
        if (this.cn instanceof HttpURLConnection) {
            ((HttpURLConnection) this.cn).disconnect();
        }
        this.cn = null;
    }

    @Override // javax.microedition.io.HttpConnection
    public String getURL() {
        if (this.cn == null) {
            return null;
        }
        return this.cn.getURL().toString();
    }

    @Override // javax.microedition.io.HttpConnection
    public String getProtocol() {
        return "http";
    }

    @Override // javax.microedition.io.HttpConnection
    public String getHost() {
        if (this.cn == null) {
            return null;
        }
        return this.cn.getURL().getHost();
    }

    @Override // javax.microedition.io.HttpConnection
    public String getFile() {
        if (this.cn == null) {
            return null;
        }
        return this.cn.getURL().getFile();
    }

    @Override // javax.microedition.io.HttpConnection
    public String getRef() {
        if (this.cn == null) {
            return null;
        }
        return this.cn.getURL().getRef();
    }

    @Override // javax.microedition.io.HttpConnection
    public String getQuery() {
        if (this.cn == null) {
            return null;
        }
        return null;
    }

    @Override // javax.microedition.io.HttpConnection
    public int getPort() {
        if (this.cn == null) {
            return -1;
        }
        int port = this.cn.getURL().getPort();
        if (port == -1) {
            return 80;
        }
        return port;
    }

    @Override // javax.microedition.io.HttpConnection
    public String getRequestMethod() {
        if (this.cn != null && (this.cn instanceof HttpURLConnection)) {
            return ((HttpURLConnection) this.cn).getRequestMethod();
        }
        return null;
    }

    @Override // javax.microedition.io.HttpConnection
    public void setRequestMethod(String method) throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (method.equals(HttpConnection.POST)) {
            this.cn.setDoOutput(true);
        }
        if (this.cn instanceof HttpURLConnection) {
            ((HttpURLConnection) this.cn).setRequestMethod(method);
        }
    }

    @Override // javax.microedition.io.HttpConnection
    public String getRequestProperty(String key) {
        if (this.cn == null) {
            return null;
        }
        return this.cn.getRequestProperty(key);
    }

    @Override // javax.microedition.io.HttpConnection
    public void setRequestProperty(String key, String value) throws IOException {
        if (this.cn == null || this.connected) {
            throw new IOException();
        }
        this.cn.setRequestProperty(key, value);
    }

    @Override // javax.microedition.io.HttpConnection
    public int getResponseCode() throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        if (this.cn instanceof HttpURLConnection) {
            return ((HttpURLConnection) this.cn).getResponseCode();
        }
        return -1;
    }

    @Override // javax.microedition.io.HttpConnection
    public String getResponseMessage() throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        if (this.cn instanceof HttpURLConnection) {
            return ((HttpURLConnection) this.cn).getResponseMessage();
        }
        return null;
    }

    @Override // javax.microedition.io.HttpConnection
    public long getExpiration() throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        return this.cn.getExpiration();
    }

    @Override // javax.microedition.io.HttpConnection
    public long getDate() throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        return this.cn.getDate();
    }

    @Override // javax.microedition.io.HttpConnection
    public long getLastModified() throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        return this.cn.getLastModified();
    }

    @Override // javax.microedition.io.HttpConnection
    public String getHeaderField(String name) throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        return this.cn.getHeaderField(name);
    }

    @Override // javax.microedition.io.HttpConnection
    public int getHeaderFieldInt(String name, int def) throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        return this.cn.getHeaderFieldInt(name, def);
    }

    @Override // javax.microedition.io.HttpConnection
    public long getHeaderFieldDate(String name, long def) throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        return this.cn.getHeaderFieldDate(name, def);
    }

    @Override // javax.microedition.io.HttpConnection
    public String getHeaderField(int n) throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        return this.cn.getHeaderField(getImplIndex(n));
    }

    @Override // javax.microedition.io.HttpConnection
    public String getHeaderFieldKey(int n) throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        if (!this.connected) {
            this.cn.connect();
            this.connected = true;
        }
        return this.cn.getHeaderFieldKey(getImplIndex(n));
    }

    private int getImplIndex(int index) {
        if (this.cn.getHeaderFieldKey(0) == null && this.cn.getHeaderField(0) != null) {
            index++;
        }
        return index;
    }

    @Override // javax.microedition.io.InputConnection
    public InputStream openInputStream() throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        this.connected = true;
        return this.cn.getInputStream();
    }

    @Override // javax.microedition.io.InputConnection
    public DataInputStream openDataInputStream() throws IOException {
        return new DataInputStream(openInputStream());
    }

    @Override // javax.microedition.io.OutputConnection
    public OutputStream openOutputStream() throws IOException {
        if (this.cn == null) {
            throw new IOException();
        }
        this.connected = true;
        return this.cn.getOutputStream();
    }

    @Override // javax.microedition.io.OutputConnection
    public DataOutputStream openDataOutputStream() throws IOException {
        return new DataOutputStream(openOutputStream());
    }

    @Override // javax.microedition.io.ContentConnection
    public String getType() {
        try {
            return getHeaderField("content-type");
        } catch (IOException e) {
            return null;
        }
    }

    @Override // javax.microedition.io.ContentConnection
    public String getEncoding() {
        try {
            return getHeaderField("content-encoding");
        } catch (IOException e) {
            return null;
        }
    }

    @Override // javax.microedition.io.ContentConnection
    public long getLength() {
        try {
            return getHeaderFieldInt("content-length", -1);
        } catch (IOException e) {
            return -1L;
        }
    }

    public static boolean isAllowNetworkConnection() {
        return allowNetworkConnection;
    }

    public static void setAllowNetworkConnection(boolean allowNetworkConnection2) {
        allowNetworkConnection = allowNetworkConnection2;
    }
}
