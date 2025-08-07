package org.microemu.cldc;

import java.security.cert.X509Certificate;
import javax.microedition.pki.Certificate;

/* loaded from: avacs.jar:org/microemu/cldc/CertificateImpl.class */
public class CertificateImpl implements Certificate {
    private X509Certificate cert;

    public CertificateImpl(X509Certificate cert) {
        this.cert = cert;
    }

    @Override // javax.microedition.pki.Certificate
    public String getIssuer() {
        return this.cert.getIssuerDN().getName();
    }

    @Override // javax.microedition.pki.Certificate
    public long getNotAfter() {
        return this.cert.getNotAfter().getTime();
    }

    @Override // javax.microedition.pki.Certificate
    public long getNotBefore() {
        return this.cert.getNotBefore().getTime();
    }

    @Override // javax.microedition.pki.Certificate
    public String getSerialNumber() {
        return this.cert.getSerialNumber().toString();
    }

    @Override // javax.microedition.pki.Certificate
    public String getSigAlgName() {
        return this.cert.getSigAlgName();
    }

    @Override // javax.microedition.pki.Certificate
    public String getSubject() {
        return this.cert.getSubjectDN().getName();
    }

    @Override // javax.microedition.pki.Certificate
    public String getType() {
        return this.cert.getType();
    }

    @Override // javax.microedition.pki.Certificate
    public String getVersion() {
        return Integer.toString(this.cert.getVersion());
    }
}
