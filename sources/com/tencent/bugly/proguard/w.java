package com.tencent.bugly.proguard;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class w extends m implements Cloneable {
    static v i;
    static Map<String, String> j;
    static final /* synthetic */ boolean k = (!w.class.desiredAssertionStatus());
    public String a = "";
    public long b = 0;
    public byte c = 0;
    public long d = 0;
    public v e = null;
    public String f = "";
    public int g = 0;
    public Map<String, String> h = null;

    public w() {
    }

    public w(String str, long j2, byte b2, long j3, v vVar, String str2, int i2, Map<String, String> map) {
        this.a = str;
        this.b = j2;
        this.c = b2;
        this.d = j3;
        this.e = vVar;
        this.f = str2;
        this.g = i2;
        this.h = map;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        w wVar = (w) o;
        if (!n.a((Object) this.a, (Object) wVar.a) || !n.a(this.b, wVar.b) || !n.a(this.c, wVar.c) || !n.a(this.d, wVar.d) || !n.a((Object) this.e, (Object) wVar.e) || !n.a((Object) this.f, (Object) wVar.f) || !n.a(this.g, wVar.g) || !n.a((Object) this.h, (Object) wVar.h)) {
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
            if (k) {
                return z;
            }
            throw new AssertionError();
        }
    }

    public void a(l lVar) {
        lVar.a(this.a, 0);
        lVar.a(this.b, 1);
        lVar.b(this.c, 2);
        lVar.a(this.d, 3);
        if (this.e != null) {
            lVar.a((m) this.e, 4);
        }
        if (this.f != null) {
            lVar.a(this.f, 5);
        }
        lVar.a(this.g, 6);
        if (this.h != null) {
            lVar.a(this.h, 7);
        }
    }

    public void a(k kVar) {
        this.a = kVar.a(0, true);
        this.b = kVar.a(this.b, 1, true);
        this.c = kVar.a(this.c, 2, true);
        this.d = kVar.a(this.d, 3, false);
        if (i == null) {
            i = new v();
        }
        this.e = (v) kVar.a((m) i, 4, false);
        this.f = kVar.a(5, false);
        this.g = kVar.a(this.g, 6, false);
        if (j == null) {
            j = new HashMap();
            j.put("", "");
        }
        this.h = (Map) kVar.a(j, 7, false);
    }

    public void a(StringBuilder sb, int i2) {
        i iVar = new i(sb, i2);
        iVar.a(this.a, "eventType");
        iVar.a(this.b, "eventTime");
        iVar.a(this.c, "eventResult");
        iVar.a(this.d, "eventElapse");
        iVar.a((m) this.e, "destAppInfo");
        iVar.a(this.f, "strategyId");
        iVar.a(this.g, "updateType");
        iVar.a(this.h, "reserved");
    }
}
