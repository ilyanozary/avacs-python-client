package defpackage;

import javax.microedition.media.Player;

/* loaded from: avacs.jar:v.class */
public class v extends u {
    private static Player a;

    @Override // defpackage.u
    public final boolean a() {
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00c2 A[Catch: Exception -> 0x00eb, TryCatch #0 {Exception -> 0x00eb, blocks: (B:5:0x0008, B:6:0x0010, B:7:0x002c, B:9:0x003a, B:16:0x00aa, B:18:0x00b0, B:20:0x00c2, B:22:0x00c8, B:23:0x00d0, B:10:0x0053, B:11:0x0067, B:13:0x008b), top: B:30:0x0005 }] */
    @Override // defpackage.u
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void a(java.lang.String r8, java.lang.String r9, int r10, byte[] r11) throws java.io.IOException {
        /*
            r7 = this;
            r0 = 0
            r12 = r0
            r0 = r11
            if (r0 != 0) goto L8b
            r0 = r8
            r1 = 0
            char r0 = r0.charAt(r1)     // Catch: java.lang.Exception -> Leb
            r1 = r0
            r13 = r1
            switch(r0) {
                case 36: goto L2c;
                case 126: goto L53;
                default: goto L67;
            }     // Catch: java.lang.Exception -> Leb
        L2c:
            r0 = r8
            java.lang.String r0 = defpackage.g.m46a(r0)     // Catch: java.lang.Exception -> Leb
            r1 = r0
            r8 = r1
            f r0 = defpackage.g.a(r0)     // Catch: java.lang.Exception -> Leb
            r1 = r0
            r8 = r1
            if (r0 == 0) goto L88
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream     // Catch: java.lang.Exception -> Leb
            r1 = r0
            r2 = r8
            r3 = r2
            r9 = r3
            byte[] r2 = r2.a     // Catch: java.lang.Exception -> Leb
            r1.<init>(r2)     // Catch: java.lang.Exception -> Leb
            r12 = r0
            r0 = r8
            r1 = r0
            r9 = r1
            java.lang.String r0 = r0.f161a     // Catch: java.lang.Exception -> Leb
            r9 = r0
            goto La5
        L53:
            r0 = r8
            r1 = 126(0x7e, float:1.77E-43)
            byte[] r0 = defpackage.b.m27a(r0, r1)     // Catch: java.lang.Exception -> Leb
            r8 = r0
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream     // Catch: java.lang.Exception -> Leb
            r1 = r0
            r2 = r8
            r1.<init>(r2)     // Catch: java.lang.Exception -> Leb
            r12 = r0
            goto La5
        L67:
            r0 = r7
            java.lang.Class r0 = r0.getClass()     // Catch: java.lang.Exception -> Leb
            java.lang.StringBuffer r1 = new java.lang.StringBuffer     // Catch: java.lang.Exception -> Leb
            r2 = r1
            r2.<init>()     // Catch: java.lang.Exception -> Leb
            java.lang.String r2 = "/"
            java.lang.StringBuffer r1 = r1.append(r2)     // Catch: java.lang.Exception -> Leb
            r2 = r8
            java.lang.StringBuffer r1 = r1.append(r2)     // Catch: java.lang.Exception -> Leb
            java.lang.String r2 = ".mid"
            java.lang.StringBuffer r1 = r1.append(r2)     // Catch: java.lang.Exception -> Leb
            java.lang.String r1 = r1.toString()     // Catch: java.lang.Exception -> Leb
            java.io.InputStream r0 = r0.getResourceAsStream(r1)     // Catch: java.lang.Exception -> Leb
            r12 = r0
        L88:
            goto La5
        L8b:
            r0 = r11
            int[] r0 = defpackage.b.a(r0)     // Catch: java.lang.Exception -> Leb
            r13 = r0
            java.io.ByteArrayInputStream r0 = new java.io.ByteArrayInputStream     // Catch: java.lang.Exception -> Leb
            r1 = r0
            r2 = r11
            r3 = r13
            r4 = 0
            r3 = r3[r4]     // Catch: java.lang.Exception -> Leb
            r4 = r13
            r5 = 1
            r4 = r4[r5]     // Catch: java.lang.Exception -> Leb
            r1.<init>(r2, r3, r4)     // Catch: java.lang.Exception -> Leb
            r12 = r0
        La5:
            r0 = r12
            if (r0 == 0) goto Le8
            javax.microedition.media.Player r0 = defpackage.v.a     // Catch: java.lang.Exception -> Leb
            if (r0 == 0) goto Lc2
            javax.microedition.media.Player r0 = defpackage.v.a     // Catch: java.lang.Exception -> Leb
            int r0 = r0.getState()     // Catch: java.lang.Exception -> Leb
            javax.microedition.media.Player r1 = defpackage.v.a     // Catch: java.lang.Exception -> Leb
            r1 = 400(0x190, float:5.6E-43)
            if (r0 == r1) goto Le8
        Lc2:
            javax.microedition.media.Player r0 = defpackage.v.a     // Catch: java.lang.Exception -> Leb
            if (r0 == 0) goto Ld0
            javax.microedition.media.Player r0 = defpackage.v.a     // Catch: java.lang.Exception -> Leb
            r0.close()     // Catch: java.lang.Exception -> Leb
        Ld0:
            r0 = r12
            r1 = r9
            javax.microedition.media.Player r0 = javax.microedition.media.Manager.createPlayer(r0, r1)     // Catch: java.lang.Exception -> Leb
            r1 = r0
            defpackage.v.a = r1     // Catch: java.lang.Exception -> Leb
            r1 = r10
            r0.setLoopCount(r1)     // Catch: java.lang.Exception -> Leb
            javax.microedition.media.Player r0 = defpackage.v.a     // Catch: java.lang.Exception -> Leb
            r0.start()     // Catch: java.lang.Exception -> Leb
        Le8:
            goto Lec
        Leb:
        Lec:
            r0 = r12
            r0.close()     // Catch: java.lang.Exception -> Lf2
            return
        Lf2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.v.a(java.lang.String, java.lang.String, int, byte[]):void");
    }
}
