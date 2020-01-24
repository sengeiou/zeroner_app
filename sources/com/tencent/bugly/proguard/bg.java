package com.tencent.bugly.proguard;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class bg extends m implements Cloneable {
    static bf m = new bf();
    static Map<String, String> n = new HashMap();
    static final /* synthetic */ boolean o;
    public boolean a = true;
    public boolean b = true;
    public boolean c = true;
    public String d = "";
    public String e = "";
    public bf f = null;
    public Map<String, String> g = null;
    public long h = 0;
    public String i = "";
    public String j = "";
    public int k = 0;
    public int l = 0;

    static {
        boolean z;
        if (!bg.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        o = z;
        n.put("", "");
    }

    public boolean equals(Object o2) {
        if (o2 == null) {
            return false;
        }
        bg bgVar = (bg) o2;
        if (!n.a(this.a, bgVar.a) || !n.a(this.b, bgVar.b) || !n.a(this.c, bgVar.c) || !n.a((Object) this.d, (Object) bgVar.d) || !n.a((Object) this.e, (Object) bgVar.e) || !n.a((Object) this.f, (Object) bgVar.f) || !n.a((Object) this.g, (Object) bgVar.g) || !n.a(this.h, bgVar.h) || !n.a((Object) this.i, (Object) bgVar.i) || !n.a((Object) this.j, (Object) bgVar.j) || !n.a(this.k, bgVar.k) || !n.a(this.l, bgVar.l)) {
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
            if (o) {
                return z;
            }
            throw new AssertionError();
        }
    }

    public void a(l lVar) {
        lVar.a(this.a, 0);
        lVar.a(this.b, 1);
        lVar.a(this.c, 2);
        if (this.d != null) {
            lVar.a(this.d, 3);
        }
        if (this.e != null) {
            lVar.a(this.e, 4);
        }
        if (this.f != null) {
            lVar.a((m) this.f, 5);
        }
        if (this.g != null) {
            lVar.a(this.g, 6);
        }
        lVar.a(this.h, 7);
        if (this.i != null) {
            lVar.a(this.i, 8);
        }
        if (this.j != null) {
            lVar.a(this.j, 9);
        }
        lVar.a(this.k, 10);
        lVar.a(this.l, 11);
    }

    public void a(k kVar) {
        this.a = kVar.a(this.a, 0, true);
        this.b = kVar.a(this.b, 1, true);
        this.c = kVar.a(this.c, 2, true);
        this.d = kVar.a(3, false);
        this.e = kVar.a(4, false);
        this.f = (bf) kVar.a((m) m, 5, false);
        this.g = (Map) kVar.a(n, 6, false);
        this.h = kVar.a(this.h, 7, false);
        this.i = kVar.a(8, false);
        this.j = kVar.a(9, false);
        this.k = kVar.a(this.k, 10, false);
        this.l = kVar.a(this.l, 11, false);
    }

    public void a(StringBuilder sb, int i2) {
        i iVar = new i(sb, i2);
        iVar.a(this.a, "enable");
        iVar.a(this.b, "enableUserInfo");
        iVar.a(this.c, "enableQuery");
        iVar.a(this.d, "url");
        iVar.a(this.e, "expUrl");
        iVar.a((m) this.f, "security");
        iVar.a(this.g, "valueMap");
        iVar.a(this.h, "strategylastUpdateTime");
        iVar.a(this.i, "httpsUrl");
        iVar.a(this.j, "httpsExpUrl");
        iVar.a(this.k, "eventRecordCount");
        iVar.a(this.l, "eventTimeInterval");
    }
}
