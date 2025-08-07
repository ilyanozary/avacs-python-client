package defpackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;

/* loaded from: avacs.jar:k.class */
public class k extends n {
    private FileConnection a;

    @Override // defpackage.n
    public final Enumeration a() {
        return FileSystemRegistry.listRoots();
    }

    @Override // defpackage.n
    public final void a(String str) {
        this.a = (FileConnection) Connector.open(str);
    }

    @Override // defpackage.n
    /* renamed from: a, reason: collision with other method in class */
    public final String mo65a() {
        return this.a.getURL();
    }

    @Override // defpackage.n
    public final Enumeration b() {
        return this.a.list();
    }

    @Override // defpackage.n
    /* renamed from: a, reason: collision with other method in class */
    public final boolean mo66a() {
        return this.a.exists();
    }

    @Override // defpackage.n
    /* renamed from: a, reason: collision with other method in class */
    public final long mo67a() {
        return this.a.fileSize();
    }

    @Override // defpackage.n
    /* renamed from: a, reason: collision with other method in class */
    public final OutputStream mo68a() {
        return this.a.openOutputStream();
    }

    @Override // defpackage.n
    /* renamed from: a, reason: collision with other method in class */
    public final InputStream mo69a() {
        return this.a.openInputStream();
    }

    @Override // defpackage.n
    /* renamed from: a, reason: collision with other method in class */
    public final void mo70a() throws IOException {
        this.a.create();
    }

    @Override // defpackage.n
    /* renamed from: b, reason: collision with other method in class */
    public final void mo71b() {
        this.a.close();
    }
}
