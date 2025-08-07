package javax.microedition.pki;

/* loaded from: avacs.jar:javax/microedition/pki/Certificate.class */
public interface Certificate {
    String getSubject();

    String getIssuer();

    String getType();

    String getVersion();

    String getSigAlgName();

    long getNotBefore();

    long getNotAfter();

    String getSerialNumber();
}
