package defpackage;

import javax.microedition.io.Connector;
import javax.microedition.lcdui.Graphics;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/* loaded from: avacs.jar:ao.class */
public class ao extends am implements Runnable {
    private String[] a;

    @Override // defpackage.am
    public final void a() {
        this.a = b.a(this.f101d, f119a);
        this.a[1] = b.b(this.a[1], f121c);
    }

    @Override // defpackage.am
    public final void a(Graphics graphics) {
        if (this.r == 0) {
            new Thread(this).start();
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        MessageConnection messageConnection = null;
        try {
            this.a.a(this.f89a, 0, null, null, this);
            MessageConnection messageConnection2 = (MessageConnection) Connector.open(new StringBuffer().append("sms://").append(this.a[0]).toString());
            messageConnection = messageConnection2;
            TextMessage textMessageNewMessage = messageConnection2.newMessage("text", new StringBuffer().append("sms://").append(this.a[0]).toString());
            textMessageNewMessage.setPayloadText(this.a[1]);
            messageConnection.send(textMessageNewMessage);
            this.a.a(this.f89a, this.h, this.a[2], null, this);
        } catch (Exception e) {
            this.a.a(this.f89a, -117, b.m26a(new StringBuffer().append("").append(e).append(". ").append(e.getMessage()).toString(), f121c), null, this);
            this.a.a(this.f89a, this.i, this.a[2], null, this);
        }
        if (messageConnection != null) {
            try {
                messageConnection.close();
            } catch (Exception unused) {
            }
        }
    }
}
