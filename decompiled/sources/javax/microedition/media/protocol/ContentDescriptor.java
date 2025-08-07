package javax.microedition.media.protocol;

/* loaded from: avacs.jar:javax/microedition/media/protocol/ContentDescriptor.class */
public class ContentDescriptor {
    private String encoding;

    public String getContentType() {
        return this.encoding;
    }

    public ContentDescriptor(String contentType) {
        this.encoding = contentType;
    }
}
