package com.tencent.bugly.proguard;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

/* compiled from: BUGLY */
public final class u extends m implements Cloneable {
    static final /* synthetic */ boolean f = (!u.class.desiredAssertionStatus());
    public String a = "";
    public String b = "";
    public String c = "";
    public long d = 0;
    public String e = "";

    public String a() {
        return this.b;
    }

    public u() {
    }

    public u(String str, String str2, String str3, long j, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = j;
        this.e = str4;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        u uVar = (u) o;
        if (!n.a((Object) this.a, (Object) uVar.a) || !n.a((Object) this.b, (Object) uVar.b) || !n.a((Object) this.c, (Object) uVar.c) || !n.a(this.d, uVar.d) || !n.a((Object) this.e, (Object) uVar.e)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        try {
            throw new Exception("Need define key first!");
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
            return 0;
        }
    }

    public Object clone() {
        boolean z = false;
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            if (f) {
                return z;
            }
            throw new AssertionError();
        }
    }

    public void a(l lVar) {
        lVar.a(this.a, 0);
        lVar.a(this.b, 1);
        if (this.c != null) {
            lVar.a(this.c, 2);
        }
        lVar.a(this.d, 3);
        if (this.e != null) {
            lVar.a(this.e, 4);
        }
    }

    public void a(k kVar) {
        this.a = kVar.a(0, true);
        this.b = kVar.a(1, true);
        this.c = kVar.a(2, false);
        this.d = kVar.a(this.d, 3, true);
        this.e = kVar.a(4, false);
    }

    public void a(StringBuilder sb, int i) {
        i iVar = new i(sb, i);
        iVar.a(this.a, "apkMd5");
        iVar.a(this.b, "apkUrl");
        iVar.a(this.c, "manifestMd5");
        iVar.a(this.d, "fileSize");
        iVar.a(this.e, "signatureMd5");
    }
}
