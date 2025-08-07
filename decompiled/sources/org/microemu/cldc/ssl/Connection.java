package org.microemu.cldc.ssl;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.microedition.io.SecureConnection;
import javax.microedition.io.SecurityInfo;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.microemu.cldc.CertificateImpl;
import org.microemu.cldc.ClosedConnection;
import org.microemu.cldc.SecurityInfoImpl;
import org.microemu.cldc.socket.SocketConnection;

/* loaded from: avacs.jar:org/microemu/cldc/ssl/Connection.class */
public class Connection extends SocketConnection implements SecureConnection, ClosedConnection {
    private SecurityInfo securityInfo = null;

    @Override // org.microemu.cldc.ClosedConnection
    public javax.microedition.io.Connection open(String name) throws NoSuchAlgorithmException, IOException, NumberFormatException, KeyManagementException {
        if (!org.microemu.cldc.http.Connection.isAllowNetworkConnection()) {
            throw new IOException("No network");
        }
        int portSepIndex = name.lastIndexOf(58);
        int port = Integer.parseInt(name.substring(portSepIndex + 1));
        String host = name.substring("ssl://".length(), portSepIndex);
        TrustManager[] trustAllCerts = {new X509TrustManager() { // from class: org.microemu.cldc.ssl.Connection.1
            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            SSLSocketFactory factory = sc.getSocketFactory();
            this.socket = factory.createSocket(host, port);
            return this;
        } catch (KeyManagementException ex) {
            throw new IOException(ex.toString());
        } catch (NoSuchAlgorithmException ex2) {
            throw new IOException(ex2.toString());
        }
    }

    @Override // org.microemu.cldc.socket.SocketConnection, javax.microedition.io.Connection
    public void close() throws IOException {
        this.socket.close();
    }

    @Override // javax.microedition.io.SecureConnection
    public SecurityInfo getSecurityInfo() throws IOException {
        if (this.securityInfo == null) {
            SSLSession session = ((SSLSocket) this.socket).getSession();
            Certificate[] certs = session.getPeerCertificates();
            if (certs.length == 0) {
                throw new IOException();
            }
            this.securityInfo = new SecurityInfoImpl(session.getCipherSuite(), session.getProtocol(), new CertificateImpl((X509Certificate) certs[0]));
        }
        return this.securityInfo;
    }
}
