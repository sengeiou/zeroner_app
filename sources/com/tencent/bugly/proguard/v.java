package com.tencent.bugly.proguard;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.mm.opensdk.constants.ConstantsAPI.Token;

/* compiled from: BUGLY */
public final class v extends m implements Cloneable {
    static final /* synthetic */ boolean l = (!v.class.desiredAssertionStatus());
    public String a = "";
    public byte b = 0;
    public int c = 0;
    public String d = "";
    public int e = 0;
    public String f = "";
    public long g = 0;
    public String h = "";
    public String i = "";
    public String j = "";
    public String k = "";

    public v() {
    }

    public v(String str, byte b2, int i2, String str2, int i3, String str3, long j2, String str4, String str5, String str6, String str7) {
        this.a = str;
        this.b = b2;
        this.c = i2;
        this.d = str2;
        this.e = i3;
        this.f = str3;
        this.g = j2;
        this.h = str4;
        this.i = str5;
        this.j = str6;
        this.k = str7;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        v vVar = (v) o;
        if (!n.a((Object) this.a, (Object) vVar.a) || !n.a(this.b, vVar.b) || !n.a(this.c, vVar.c) || !n.a((Object) this.d, (Object) vVar.d) || !n.a(this.e, vVar.e) || !n.a((Object) this.f, (Object) vVar.f) || !n.a(this.g, vVar.g) || !n.a((Object) this.h, (Object) vVar.h) || !n.a((Object) this.i, (Object) vVar.i) || !n.a((Object) this.j, (Object) vVar.j) || !n.a((Object) this.k, (Object) vVar.k)) {
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
            if (l) {
                return z;
            }
            throw new AssertionError();
        }
    }

    public void a(l lVar) {
        lVar.a(this.a, 0);
        lVar.b(this.b, 1);
        lVar.a(this.c, 2);
        if (this.d != null) {
            lVar.a(this.d, 3);
        }
        lVar.a(this.e, 4);
        if (this.f != null) {
            lVar.a(this.f, 5);
        }
        lVar.a(this.g, 6);
        if (this.h != null) {
            lVar.a(this.h, 7);
        }
        if (this.i != null) {
            lVar.a(this.i, 8);
        }
        if (this.j != null) {
            lVar.a(this.j, 9);
        }
        if (this.k != null) {
            lVar.a(this.k, 10);
        }
    }

    public void a(k kVar) {
        this.a = kVar.a(0, true);
        this.b = kVar.a(this.b, 1, true);
        this.c = kVar.a(this.c, 2, true);
        this.d = kVar.a(3, false);
        this.e = kVar.a(this.e, 4, false);
        this.f = kVar.a(5, false);
        this.g = kVar.a(this.g, 6, false);
        this.h = kVar.a(7, false);
        this.i = kVar.a(8, false);
        this.j = kVar.a(9, false);
        this.k = kVar.a(10, false);
    }

    public void a(StringBuilder sb, int i2) {
        i iVar = new i(sb, i2);
        iVar.a(this.a, "appId");
        iVar.a(this.b, Token.WX_TOKEN_PLATFORMID_KEY);
        iVar.a(this.c, "versionCode");
        iVar.a(this.d, "versionName");
        iVar.a(this.e, "buildNo");
        iVar.a(this.f, "iconUrl");
        iVar.a(this.g, "apkId");
        iVar.a(this.h, "channelId");
        iVar.a(this.i, "md5");
        iVar.a(this.j, "sdkVer");
        iVar.a(this.k, "bundleId");
    }
}
