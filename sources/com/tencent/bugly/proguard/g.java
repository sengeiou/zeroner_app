package com.tencent.bugly.proguard;

import android.support.v4.app.NotificationCompat;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.HashMap;
import java.util.Map;

/* compiled from: BUGLY */
public final class g extends m {
    static byte[] k = null;
    static Map<String, String> l = null;
    static final /* synthetic */ boolean m;
    public short a = 0;
    public byte b = 0;
    public int c = 0;
    public int d = 0;
    public String e = null;
    public String f = null;
    public byte[] g;
    public int h = 0;
    public Map<String, String> i;
    public Map<String, String> j;

    static {
        boolean z;
        if (!g.class.desiredAssertionStatus()) {
            z = true;
        } else {
            z = false;
        }
        m = z;
    }

    public boolean equals(Object o) {
        g gVar = (g) o;
        if (!n.a(1, (int) gVar.a) || !n.a(1, (int) gVar.b) || !n.a(1, gVar.c) || !n.a(1, gVar.d) || !n.a((Object) Integer.valueOf(1), (Object) gVar.e) || !n.a((Object) Integer.valueOf(1), (Object) gVar.f) || !n.a((Object) Integer.valueOf(1), (Object) gVar.g) || !n.a(1, gVar.h) || !n.a((Object) Integer.valueOf(1), (Object) gVar.i) || !n.a((Object) Integer.valueOf(1), (Object) gVar.j)) {
            return false;
        }
        return true;
    }

    public Object clone() {
        boolean z = false;
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            if (m) {
                return z;
            }
            throw new AssertionError();
        }
    }

    public void a(l lVar) {
        lVar.a(this.a, 1);
        lVar.b(this.b, 2);
        lVar.a(this.c, 3);
        lVar.a(this.d, 4);
        lVar.a(this.e, 5);
        lVar.a(this.f, 6);
        lVar.a(this.g, 7);
        lVar.a(this.h, 8);
        lVar.a(this.i, 9);
        lVar.a(this.j, 10);
    }

    public void a(k kVar) {
        try {
            this.a = kVar.a(this.a, 1, true);
            this.b = kVar.a(this.b, 2, true);
            this.c = kVar.a(this.c, 3, true);
            this.d = kVar.a(this.d, 4, true);
            this.e = kVar.a(5, true);
            this.f = kVar.a(6, true);
            if (k == null) {
                k = new byte[]{0};
            }
            this.g = kVar.a(k, 7, true);
            this.h = kVar.a(this.h, 8, true);
            if (l == null) {
                l = new HashMap();
                l.put("", "");
            }
            this.i = (Map) kVar.a(l, 9, true);
            if (l == null) {
                l = new HashMap();
                l.put("", "");
            }
            this.j = (Map) kVar.a(l, 10, true);
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
            System.out.println("RequestPacket decode error " + f.a(this.g));
            throw new RuntimeException(e2);
        }
    }

    public void a(StringBuilder sb, int i2) {
        i iVar = new i(sb, i2);
        iVar.a(this.a, "iVersion");
        iVar.a(this.b, "cPacketType");
        iVar.a(this.c, "iMessageType");
        iVar.a(this.d, "iRequestId");
        iVar.a(this.e, "sServantName");
        iVar.a(this.f, "sFuncName");
        iVar.a(this.g, "sBuffer");
        iVar.a(this.h, "iTimeout");
        iVar.a(this.i, "context");
        iVar.a(this.j, NotificationCompat.CATEGORY_STATUS);
    }
}
