package com.tencent.bugly.proguard;

import android.support.v4.app.NotificationCompat;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class y extends m implements Cloneable {
    static v q;
    static u r;
    static u s;
    static Map<String, String> t;
    static final /* synthetic */ boolean u = (!y.class.desiredAssertionStatus());
    public String a = "";
    public String b = "";
    public long c = 0;
    public int d = 0;
    public v e = null;
    public u f = null;
    public byte g = 0;
    public int h = 0;
    public long i = 0;
    public u j = null;
    public String k = "";
    public Map<String, String> l = null;
    public String m = "";
    public int n = 0;
    public long o = 0;
    public int p = 0;

    public long a() {
        return this.c;
    }

    public u b() {
        return this.f;
    }

    public y() {
    }

    public y(String str, String str2, long j2, int i2, v vVar, u uVar, byte b2, int i3, long j3, u uVar2, String str3, Map<String, String> map, String str4, int i4, long j4, int i5) {
        this.a = str;
        this.b = str2;
        this.c = j2;
        this.d = i2;
        this.e = vVar;
        this.f = uVar;
        this.g = b2;
        this.h = i3;
        this.i = j3;
        this.j = uVar2;
        this.k = str3;
        this.l = map;
        this.m = str4;
        this.n = i4;
        this.o = j4;
        this.p = i5;
    }

    public boolean equals(Object o2) {
        if (o2 == null) {
            return false;
        }
        y yVar = (y) o2;
        if (!n.a((Object) this.a, (Object) yVar.a) || !n.a((Object) this.b, (Object) yVar.b) || !n.a(this.c, yVar.c) || !n.a(this.d, yVar.d) || !n.a((Object) this.e, (Object) yVar.e) || !n.a((Object) this.f, (Object) yVar.f) || !n.a(this.g, yVar.g) || !n.a(this.h, yVar.h) || !n.a(this.i, yVar.i) || !n.a((Object) this.j, (Object) yVar.j) || !n.a((Object) this.k, (Object) yVar.k) || !n.a((Object) this.l, (Object) yVar.l) || !n.a((Object) this.m, (Object) yVar.m) || !n.a(this.n, yVar.n) || !n.a(this.o, yVar.o) || !n.a(this.p, yVar.p)) {
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
            if (u) {
                return z;
            }
            throw new AssertionError();
        }
    }

    public void a(l lVar) {
        lVar.a(this.a, 0);
        lVar.a(this.b, 1);
        lVar.a(this.c, 2);
        lVar.a(this.d, 3);
        lVar.a((m) this.e, 4);
        lVar.a((m) this.f, 5);
        lVar.b(this.g, 6);
        lVar.a(this.h, 7);
        lVar.a(this.i, 8);
        if (this.j != null) {
            lVar.a((m) this.j, 9);
        }
        if (this.k != null) {
            lVar.a(this.k, 10);
        }
        if (this.l != null) {
            lVar.a(this.l, 11);
        }
        if (this.m != null) {
            lVar.a(this.m, 12);
        }
        lVar.a(this.n, 13);
        lVar.a(this.o, 14);
        lVar.a(this.p, 15);
    }

    public void a(k kVar) {
        this.a = kVar.a(0, true);
        this.b = kVar.a(1, true);
        this.c = kVar.a(this.c, 2, true);
        this.d = kVar.a(this.d, 3, true);
        if (q == null) {
            q = new v();
        }
        this.e = (v) kVar.a((m) q, 4, true);
        if (r == null) {
            r = new u();
        }
        this.f = (u) kVar.a((m) r, 5, true);
        this.g = kVar.a(this.g, 6, true);
        this.h = kVar.a(this.h, 7, false);
        this.i = kVar.a(this.i, 8, false);
        if (s == null) {
            s = new u();
        }
        this.j = (u) kVar.a((m) s, 9, false);
        this.k = kVar.a(10, false);
        if (t == null) {
            t = new HashMap();
            t.put("", "");
        }
        this.l = (Map) kVar.a(t, 11, false);
        this.m = kVar.a(12, false);
        this.n = kVar.a(this.n, 13, false);
        this.o = kVar.a(this.o, 14, false);
        this.p = kVar.a(this.p, 15, false);
    }

    public void a(StringBuilder sb, int i2) {
        i iVar = new i(sb, i2);
        iVar.a(this.a, "title");
        iVar.a(this.b, "newFeature");
        iVar.a(this.c, "publishTime");
        iVar.a(this.d, "publishType");
        iVar.a((m) this.e, "appBasicInfo");
        iVar.a((m) this.f, "apkBaseInfo");
        iVar.a(this.g, "updateStrategy");
        iVar.a(this.h, "popTimes");
        iVar.a(this.i, "popInterval");
        iVar.a((m) this.j, "diffApkInfo");
        iVar.a(this.k, "netType");
        iVar.a(this.l, "reserved");
        iVar.a(this.m, "strategyId");
        iVar.a(this.n, NotificationCompat.CATEGORY_STATUS);
        iVar.a(this.o, "updateTime");
        iVar.a(this.p, "updateType");
    }
}
