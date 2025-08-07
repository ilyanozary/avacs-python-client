package org.microemu.cldc;

import javax.microedition.io.SecurityInfo;
import javax.microedition.pki.Certificate;
import org.microemu.log.Logger;

/* loaded from: avacs.jar:org/microemu/cldc/SecurityInfoImpl.class */
public class SecurityInfoImpl implements SecurityInfo {
    private String cipherSuite;
    private String protocolName;
    private Certificate certificate;

    public SecurityInfoImpl(String cipherSuite, String protocolName, Certificate certificate) {
        this.cipherSuite = cipherSuite;
        this.protocolName = protocolName;
        this.certificate = certificate;
    }

    @Override // javax.microedition.io.SecurityInfo
    public String getCipherSuite() {
        return this.cipherSuite;
    }

    @Override // javax.microedition.io.SecurityInfo
    public String getProtocolName() {
        if (this.protocolName.startsWith("TLS")) {
            return "TLS";
        }
        if (this.protocolName.startsWith("SSL")) {
            return "SSL";
        }
        try {
            throw new RuntimeException();
        } catch (RuntimeException ex) {
            Logger.error((Throwable) ex);
            throw ex;
        }
    }

    @Override // javax.microedition.io.SecurityInfo
    public String getProtocolVersion() {
        if (this.protocolName.startsWith("TLS")) {
            return "3.1";
        }
        if (getProtocolName().equals("SSL")) {
            return "3.0";
        }
        try {
            throw new RuntimeException();
        } catch (RuntimeException ex) {
            Logger.error((Throwable) ex);
            throw ex;
        }
    }

    @Override // javax.microedition.io.SecurityInfo
    public Certificate getServerCertificate() {
        return this.certificate;
    }
}
