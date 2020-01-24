package com.tencent.bugly.beta.upgrade;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.beta.global.e;
import com.tencent.bugly.crashreport.common.info.a;
import com.tencent.bugly.proguard.ah;
import com.tencent.bugly.proguard.aj;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.bd;
import com.tencent.bugly.proguard.m;
import com.tencent.bugly.proguard.x;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: BUGLY */
public class b {
    public static b a = new b();

    /* access modifiers changed from: protected */
    public synchronized void a(int i, byte[] bArr, a aVar, boolean z, String str) {
        a b = a.b();
        try {
            bd a2 = ah.a(e.E.s, i, bArr);
            if (a2 != null) {
                e eVar = e.E;
                a2.b = eVar.u;
                if (!TextUtils.isEmpty(eVar.P)) {
                    a2.e = eVar.P;
                }
                if (a2.k != null) {
                    a2.k.put("G6", eVar.v);
                    a2.k.put("G10", "1.3.5");
                    a2.k.put("G3", "" + eVar.x);
                    a2.k.put("G11", String.valueOf(eVar.w));
                    a2.k.put("G7", "" + b.m());
                    a2.k.put("G8", "" + b.k());
                    a2.k.put("G9", "" + b.l());
                    a2.k.put("G2", String.valueOf(com.tencent.bugly.beta.global.a.a(e.E.s)));
                    a2.k.put("G12", String.valueOf(eVar.o));
                    a2.k.put("A21", "" + b.g());
                    a2.k.put("A22", "" + b.h());
                    a2.k.put("G13", "" + eVar.J);
                    a2.k.put("G14", "" + eVar.K);
                    a2.k.put("G15", "" + eVar.M);
                    a2.k.put("G17", "" + b.x());
                    a2.k.put("C01", "" + b.H());
                    a2.k.put("G18", "" + b.g());
                    Map B = b.B();
                    if (B != null && B.size() > 0) {
                        for (Entry entry : B.entrySet()) {
                            a2.k.put("C03_" + ((String) entry.getKey()), entry.getValue());
                        }
                    }
                    an.c("app version is: [%s.%s], [deviceId:%s], channel: [%s], base tinkerId:[%s], patch tinkerId:[%s], patch version:[%s]", eVar.x, Integer.valueOf(eVar.w), b.h(), a2.e, eVar.J, eVar.K, eVar.M);
                    HashMap hashMap = new HashMap();
                    hashMap.put("grayStrategyUpdateTime", e.E.F.b + "");
                    if (z) {
                        ak.a().a(1002, a2.g, ah.a((Object) a2), str, str, aVar, 0, 1, true, hashMap);
                    } else {
                        ak.a().a(1002, a2.g, ah.a((Object) a2), str, str, (aj) aVar, false, (Map<String, String>) hashMap);
                    }
                }
            }
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            aVar.a(i, null, 0, 0, false, "sendRequest failed");
        }
    }

    public synchronized void a(x xVar, boolean z) {
        if (xVar != null) {
            try {
                a(803, ah.a((m) xVar), new a(2, 803, xVar, Boolean.valueOf(z)), false, e.E.F.a.d);
            } catch (Throwable th) {
                if (!an.b(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }
}
