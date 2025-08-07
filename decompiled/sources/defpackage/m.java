package defpackage;

import com.siemens.mp.io.file.FileConnection;
import com.siemens.mp.io.file.FileSystemRegistry;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.microedition.io.Connector;

/* loaded from: avacs.jar:m.class */
public class m extends n {
    private FileConnection a;

    @Override // defpackage.n
    public final Enumeration a() {
        return FileSystemRegistry.listRoots();
    }

    @Override // defpackage.n
    public final void a(String str) {
        this.a = Connector.open(str);
    }

    @Override // defpackage.n
    /* renamed from: a */
    public final String mo65a() {
        return this.a.getURL();
    }

    @Override // defpackage.n
    public final Enumeration b() {
        return this.a.list();
    }

    @Override // defpackage.n
    /* renamed from: a */
    public final boolean mo66a() {
        return this.a.exists();
    }

    @Override // defpackage.n
    /* renamed from: a */
    public final long mo67a() {
        return this.a.fileSize();
    }

    @Override // defpackage.n
    /* renamed from: a */
    public final OutputStream mo68a() {
        return this.a.openOutputStream();
    }

    @Override // defpackage.n
    /* renamed from: a */
    public final InputStream mo69a() {
        return this.a.openInputStream();
    }

    @Override // defpackage.n
    /* renamed from: a */
    public final void mo70a() {
        this.a.create();
    }

    @Override // defpackage.n
    /* renamed from: b */
    public final void mo71b() {
        this.a.close();
    }
}
