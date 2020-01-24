package com.loc;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.HashMap;
import java.util.Map;

@ap(a = "a")
/* compiled from: SDKInfo */
public class v {
    @aq(a = "a1", b = 6)
    private String a;
    @aq(a = "a2", b = 6)
    private String b;
    @aq(a = "a6", b = 2)
    private int c;
    @aq(a = "a3", b = 6)
    private String d;
    @aq(a = "a4", b = 6)
    private String e;
    @aq(a = "a5", b = 6)
    private String f;
    private String g;
    private String h;
    private String i;
    private String j;
    private String k;
    private String[] l;

    /* compiled from: SDKInfo */
    public static class a {
        /* access modifiers changed from: private */
        public String a;
        /* access modifiers changed from: private */
        public String b;
        /* access modifiers changed from: private */
        public String c;
        /* access modifiers changed from: private */
        public String d;
        /* access modifiers changed from: private */
        public boolean e = true;
        /* access modifiers changed from: private */
        public String f = "standard";
        /* access modifiers changed from: private */
        public String[] g = null;

        public a(String str, String str2, String str3) {
            this.a = str2;
            this.b = str2;
            this.d = str3;
            this.c = str;
        }

        public final a a(String str) {
            this.b = str;
            return this;
        }

        public final a a(String[] strArr) {
            if (strArr != null) {
                this.g = (String[]) strArr.clone();
            }
            return this;
        }

        public final v a() throws k {
            if (this.g != null) {
                return new v(this, 0);
            }
            throw new k("sdk packages is null");
        }
    }

    private v() {
        this.c = 1;
        this.l = null;
    }

    private v(a aVar) {
        int i2 = 1;
        this.c = 1;
        this.l = null;
        this.g = aVar.a;
        this.h = aVar.b;
        this.j = aVar.c;
        this.i = aVar.d;
        if (!aVar.e) {
            i2 = 0;
        }
        this.c = i2;
        this.k = aVar.f;
        this.l = aVar.g;
        this.b = w.b(this.h);
        this.a = w.b(this.j);
        this.d = w.b(this.i);
        this.e = w.b(a(this.l));
        this.f = w.b(this.k);
    }

    /* synthetic */ v(a aVar, byte b2) {
        this(aVar);
    }

    public static String a(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("a1", w.b(str));
        return ao.a((Map<String, String>) hashMap);
    }

    private static String a(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (String append : strArr) {
                sb.append(append).append(";");
            }
            return sb.toString();
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return null;
        }
    }

    private static String[] b(String str) {
        try {
            return str.split(";");
        } catch (Throwable th) {
            ThrowableExtension.printStackTrace(th);
            return null;
        }
    }

    public static String g() {
        return "a6=1";
    }

    public final String a() {
        if (TextUtils.isEmpty(this.j) && !TextUtils.isEmpty(this.a)) {
            this.j = w.c(this.a);
        }
        return this.j;
    }

    public final void a(boolean z) {
        this.c = z ? 1 : 0;
    }

    public final String b() {
        return this.g;
    }

    public final String c() {
        if (TextUtils.isEmpty(this.h) && !TextUtils.isEmpty(this.b)) {
            this.h = w.c(this.b);
        }
        return this.h;
    }

    public final String d() {
        if (TextUtils.isEmpty(this.k) && !TextUtils.isEmpty(this.f)) {
            this.k = w.c(this.f);
        }
        if (TextUtils.isEmpty(this.k)) {
            this.k = "standard";
        }
        return this.k;
    }

    public final boolean e() {
        return this.c == 1;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return hashCode() == ((v) obj).hashCode();
    }

    public final String[] f() {
        if ((this.l == null || this.l.length == 0) && !TextUtils.isEmpty(this.e)) {
            this.l = b(w.c(this.e));
        }
        return (String[]) this.l.clone();
    }

    public int hashCode() {
        af afVar = new af();
        afVar.a((Object) this.j).a((Object) this.g).a((Object) this.h).a((Object[]) this.l);
        return afVar.a();
    }
}
