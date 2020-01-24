package com.tencent.bugly.crashreport.common.info;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.os.Process;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.b;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

/* compiled from: BUGLY */
public class a {
    private static a am = null;
    public String A = null;
    public String B = null;
    public String C = null;
    public boolean D = false;
    public boolean E = false;
    public Boolean F = null;
    public Boolean G = null;
    public HashMap<String, String> H = new HashMap<>();
    public boolean I = true;
    public List<String> J = new ArrayList();
    public boolean K = false;
    public com.tencent.bugly.crashreport.a L = null;
    public SharedPreferences M;
    private final Context N;
    private String O;
    private String P;
    private String Q = "unknown";
    private String R = "unknown";
    private String S = "";
    private String T = null;
    private String U = null;
    private String V = null;
    private String W = null;
    private long X = -1;
    private long Y = -1;
    private long Z = -1;
    public final long a = System.currentTimeMillis();
    private final Object aA = new Object();
    private final Object aB = new Object();
    private final Object aC = new Object();
    private final Object aD = new Object();
    private final Object aE = new Object();
    private String aa = null;
    private String ab = null;
    private Map<String, PlugInBean> ac = null;
    private boolean ad = true;
    private String ae = null;
    private String af = null;
    private Boolean ag = null;
    private String ah = null;
    private String ai = null;
    private String aj = null;
    private Map<String, PlugInBean> ak = null;
    private Map<String, PlugInBean> al = null;
    private int an = -1;
    private int ao = -1;
    private Map<String, String> ap = new HashMap();
    private Map<String, String> aq = new HashMap();
    private Map<String, String> ar = new HashMap();
    private boolean as;
    private String at = null;
    private String au = null;
    private String av = null;
    private String aw = null;
    private String ax = null;
    private final Object ay = new Object();
    private final Object az = new Object();
    public final String b;
    public final byte c;
    public String d;
    public final String e;
    public String f;
    public boolean g = true;
    public final String h = "com.tencent.bugly";
    public final String i = "2.6.5";
    public final String j = "";
    public final String k;
    public final String l;
    public final String m;
    public long n = 0;
    public String o = null;
    public String p = null;
    public String q = null;
    public String r = null;
    public String s = null;
    public List<String> t = null;
    public String u = "unknown";
    public long v = 0;
    public long w = 0;
    public long x = 0;
    public long y = 0;
    public boolean z = false;

    private a(Context context) {
        this.N = ap.a(context);
        this.c = 1;
        b(context);
        this.d = AppInfo.a(context);
        this.e = AppInfo.a(context, Process.myPid());
        this.k = b.m();
        this.l = b.a();
        this.p = AppInfo.c(context);
        this.m = "Android " + b.b() + ",level " + b.c();
        this.b = this.l + ";" + this.m;
        c(context);
        try {
            if (!context.getDatabasePath("bugly_db_").exists()) {
                this.E = true;
                an.c("App is first time to be installed on the device.", new Object[0]);
            }
        } catch (Throwable th) {
            if (b.c) {
                ThrowableExtension.printStackTrace(th);
            }
        }
        this.M = ap.a("BUGLY_COMMON_VALUES", context);
        an.c("com info create end", new Object[0]);
    }

    private void b(Context context) {
        PackageInfo b2 = AppInfo.b(context);
        if (b2 != null) {
            try {
                this.o = b2.versionName;
                this.A = this.o;
                this.B = Integer.toString(b2.versionCode);
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }

    private void c(Context context) {
        Map d2 = AppInfo.d(context);
        if (d2 != null) {
            try {
                this.t = AppInfo.a(d2);
                String str = (String) d2.get("BUGLY_APPID");
                if (str != null) {
                    this.af = str;
                }
                String str2 = (String) d2.get("BUGLY_APP_VERSION");
                if (str2 != null) {
                    this.o = str2;
                }
                String str3 = (String) d2.get("BUGLY_APP_CHANNEL");
                if (str3 != null) {
                    this.q = str3;
                }
                String str4 = (String) d2.get("BUGLY_ENABLE_DEBUG");
                if (str4 != null) {
                    this.z = str4.equalsIgnoreCase("true");
                }
                String str5 = (String) d2.get("com.tencent.rdm.uuid");
                if (str5 != null) {
                    this.C = str5;
                }
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }

    public boolean a() {
        return this.as;
    }

    public void a(boolean z2) {
        this.as = z2;
        if (this.L != null) {
            this.L.setNativeIsAppForeground(z2);
        }
    }

    public static synchronized a a(Context context) {
        a aVar;
        synchronized (a.class) {
            if (am == null) {
                am = new a(context);
            }
            aVar = am;
        }
        return aVar;
    }

    public static synchronized a b() {
        a aVar;
        synchronized (a.class) {
            aVar = am;
        }
        return aVar;
    }

    public String c() {
        return "2.6.5";
    }

    public void d() {
        synchronized (this.ay) {
            this.O = UUID.randomUUID().toString();
        }
    }

    public String e() {
        if (this.O == null) {
            synchronized (this.ay) {
                if (this.O == null) {
                    this.O = UUID.randomUUID().toString();
                }
            }
        }
        return this.O;
    }

    public String f() {
        if (!ap.a(this.f)) {
            return this.f;
        }
        return this.af;
    }

    public void a(String str) {
        this.af = str;
    }

    public String g() {
        String str;
        synchronized (this.aD) {
            str = this.Q;
        }
        return str;
    }

    public void b(String str) {
        synchronized (this.aD) {
            if (str == null) {
                str = "10000";
            }
            this.Q = "" + str;
        }
    }

    public void b(boolean z2) {
        this.ad = z2;
    }

    public String h() {
        if (this.P != null) {
            return this.P;
        }
        this.P = k() + "|" + m() + "|" + n();
        return this.P;
    }

    public void c(String str) {
        this.P = str;
        synchronized (this.aE) {
            this.aq.put("E8", str);
        }
    }

    public synchronized String i() {
        return this.R;
    }

    public synchronized void d(String str) {
        this.R = "" + str;
    }

    public synchronized String j() {
        return this.S;
    }

    public synchronized void e(String str) {
        this.S = "" + str;
    }

    public String k() {
        if (!this.ad) {
            return "";
        }
        if (this.T == null) {
            this.T = b.a(this.N);
        }
        return this.T;
    }

    public String l() {
        if (!this.ad) {
            return "";
        }
        if (this.U == null || !this.U.contains(":")) {
            this.U = b.d(this.N);
        }
        return this.U;
    }

    public String m() {
        if (!this.ad) {
            return "";
        }
        if (this.V == null) {
            this.V = b.b(this.N);
        }
        return this.V;
    }

    public String n() {
        if (!this.ad) {
            return "";
        }
        if (this.W == null) {
            this.W = b.c(this.N);
        }
        return this.W;
    }

    public long o() {
        if (this.X <= 0) {
            this.X = b.f();
        }
        return this.X;
    }

    public long p() {
        if (this.Y <= 0) {
            this.Y = b.h();
        }
        return this.Y;
    }

    public long q() {
        if (this.Z <= 0) {
            this.Z = b.j();
        }
        return this.Z;
    }

    public String r() {
        if (this.aa == null) {
            this.aa = b.a(this.N, true);
        }
        return this.aa;
    }

    public String s() {
        if (this.ab == null) {
            this.ab = b.h(this.N);
        }
        return this.ab;
    }

    public void a(String str, String str2) {
        if (str != null && str2 != null) {
            synchronized (this.az) {
                this.H.put(str, str2);
            }
        }
    }

    public String t() {
        try {
            Map all = this.N.getSharedPreferences("BuglySdkInfos", 0).getAll();
            if (!all.isEmpty()) {
                synchronized (this.az) {
                    for (Entry entry : all.entrySet()) {
                        try {
                            this.H.put(entry.getKey(), entry.getValue().toString());
                        } catch (Throwable th) {
                            an.a(th);
                        }
                    }
                }
            }
        } catch (Throwable th2) {
            an.a(th2);
        }
        if (this.H.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Entry entry2 : this.H.entrySet()) {
            sb.append("[");
            sb.append((String) entry2.getKey());
            sb.append(",");
            sb.append((String) entry2.getValue());
            sb.append("] ");
        }
        c("SDK_INFO", sb.toString());
        return sb.toString();
    }

    public String u() {
        if (this.ax == null) {
            this.ax = AppInfo.e(this.N);
        }
        return this.ax;
    }

    public synchronized Map<String, PlugInBean> v() {
        HashMap hashMap;
        if (this.ac == null || this.ac.size() <= 0) {
            hashMap = null;
        } else {
            hashMap = new HashMap(this.ac.size());
            hashMap.putAll(this.ac);
        }
        return hashMap;
    }

    public String w() {
        if (this.ae == null) {
            this.ae = b.l();
        }
        return this.ae;
    }

    public Boolean x() {
        if (this.ag == null) {
            this.ag = Boolean.valueOf(b.n());
        }
        return this.ag;
    }

    public String y() {
        if (this.ah == null) {
            this.ah = "" + b.g(this.N);
            an.a("ROM ID: %s", this.ah);
        }
        return this.ah;
    }

    public String z() {
        if (this.ai == null) {
            this.ai = "" + b.e(this.N);
            an.a("SIM serial number: %s", this.ai);
        }
        return this.ai;
    }

    public String A() {
        if (this.aj == null) {
            this.aj = "" + b.d();
            an.a("Hardware serial number: %s", this.aj);
        }
        return this.aj;
    }

    public Map<String, String> B() {
        HashMap hashMap;
        synchronized (this.aA) {
            if (this.ap.size() <= 0) {
                hashMap = null;
            } else {
                hashMap = new HashMap(this.ap);
            }
        }
        return hashMap;
    }

    public String f(String str) {
        String str2;
        if (ap.a(str)) {
            an.d("key should not be empty %s", "" + str);
            return null;
        }
        synchronized (this.aA) {
            str2 = (String) this.ap.remove(str);
        }
        return str2;
    }

    public void C() {
        synchronized (this.aA) {
            this.ap.clear();
        }
    }

    public String g(String str) {
        String str2;
        if (ap.a(str)) {
            an.d("key should not be empty %s", "" + str);
            return null;
        }
        synchronized (this.aA) {
            str2 = (String) this.ap.get(str);
        }
        return str2;
    }

    public void b(String str, String str2) {
        if (ap.a(str) || ap.a(str2)) {
            an.d("key&value should not be empty %s %s", "" + str, "" + str2);
            return;
        }
        synchronized (this.aA) {
            this.ap.put(str, str2);
        }
    }

    public int D() {
        int size;
        synchronized (this.aA) {
            size = this.ap.size();
        }
        return size;
    }

    public Set<String> E() {
        Set<String> keySet;
        synchronized (this.aA) {
            keySet = this.ap.keySet();
        }
        return keySet;
    }

    public Map<String, String> F() {
        HashMap hashMap;
        synchronized (this.aE) {
            if (this.aq.size() <= 0) {
                hashMap = null;
            } else {
                hashMap = new HashMap(this.aq);
            }
        }
        return hashMap;
    }

    public void c(String str, String str2) {
        if (ap.a(str) || ap.a(str2)) {
            an.d("server key&value should not be empty %s %s", "" + str, "" + str2);
            return;
        }
        synchronized (this.aB) {
            this.ar.put(str, str2);
        }
    }

    public Map<String, String> G() {
        HashMap hashMap;
        synchronized (this.aB) {
            if (this.ar.size() <= 0) {
                hashMap = null;
            } else {
                hashMap = new HashMap(this.ar);
            }
        }
        return hashMap;
    }

    public void a(int i2) {
        synchronized (this.aC) {
            int i3 = this.an;
            if (i3 != i2) {
                this.an = i2;
                an.a("user scene tag %d changed to tag %d", Integer.valueOf(i3), Integer.valueOf(this.an));
            }
        }
    }

    public int H() {
        int i2;
        synchronized (this.aC) {
            i2 = this.an;
        }
        return i2;
    }

    public void b(int i2) {
        int i3 = this.ao;
        if (i3 != i2) {
            this.ao = i2;
            an.a("server scene tag %d changed to tag %d", Integer.valueOf(i3), Integer.valueOf(this.ao));
        }
    }

    public int I() {
        return this.ao;
    }

    public synchronized Map<String, PlugInBean> J() {
        Map<String, PlugInBean> map;
        map = this.ak;
        if (this.al != null) {
            map.putAll(this.al);
        }
        return map;
    }

    public int K() {
        return b.c();
    }

    public String L() {
        if (this.at == null) {
            this.at = b.o();
        }
        return this.at;
    }

    public String M() {
        if (this.au == null) {
            this.au = b.i(this.N);
        }
        return this.au;
    }

    public String N() {
        if (this.av == null) {
            this.av = b.j(this.N);
        }
        return this.av;
    }

    public String O() {
        return b.k(this.N);
    }

    public String P() {
        if (this.aw == null) {
            this.aw = b.l(this.N);
        }
        return this.aw;
    }

    public long Q() {
        return b.m(this.N);
    }

    public boolean R() {
        if (this.F == null) {
            this.F = Boolean.valueOf(b.n(this.N));
            an.a("Is it a virtual machine? " + this.F, new Object[0]);
        }
        return this.F.booleanValue();
    }

    public boolean S() {
        if (this.G == null) {
            this.G = Boolean.valueOf(b.p(this.N));
            an.a("Does it has hook frame? " + this.G, new Object[0]);
        }
        return this.G.booleanValue();
    }
}
