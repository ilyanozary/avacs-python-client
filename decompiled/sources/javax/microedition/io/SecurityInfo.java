package javax.microedition.io;

import javax.microedition.pki.Certificate;

/* loaded from: avacs.jar:javax/microedition/io/SecurityInfo.class */
public interface SecurityInfo {
    Certificate getServerCertificate();

    String getProtocolVersion();

    String getProtocolName();

    String getCipherSuite();
}
