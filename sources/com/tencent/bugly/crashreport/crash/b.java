package com.tencent.bugly.crashreport.crash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.os.Parcelable;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.info.PlugInBean;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.crashreport.common.strategy.a;
import com.tencent.bugly.proguard.ad;
import com.tencent.bugly.proguard.ae;
import com.tencent.bugly.proguard.ag;
import com.tencent.bugly.proguard.ah;
import com.tencent.bugly.proguard.aj;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.ay;
import com.tencent.bugly.proguard.ba;
import com.tencent.bugly.proguard.bb;
import com.tencent.bugly.proguard.bc;
import com.tencent.bugly.proguard.bd;
import com.tencent.bugly.proguard.be;
import com.tencent.bugly.proguard.m;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/* compiled from: BUGLY */
public class b {
    public static int a = 0;
    protected final Context b;
    protected final ak c;
    protected final ae d;
    protected final a e;
    protected f f;
    protected BuglyStrategy.a g;

    public b(int i, Context context, ak akVar, ae aeVar, a aVar, BuglyStrategy.a aVar2, f fVar) {
        a = i;
        this.b = context;
        this.c = akVar;
        this.d = aeVar;
        this.e = aVar;
        this.g = aVar2;
        this.f = fVar;
    }

    /* access modifiers changed from: protected */
    public List<a> a(List<a> list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        long currentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        for (a aVar : list) {
            if (aVar.d && aVar.b <= currentTimeMillis - 86400000) {
                arrayList.add(aVar);
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0163  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.tencent.bugly.crashreport.crash.CrashDetailBean a(java.util.List<com.tencent.bugly.crashreport.crash.a> r12, com.tencent.bugly.crashreport.crash.CrashDetailBean r13) {
        /*
            r11 = this;
            r3 = 0
            if (r12 == 0) goto L_0x0009
            int r0 = r12.size()
            if (r0 != 0) goto L_0x000b
        L_0x0009:
            r1 = r13
        L_0x000a:
            return r1
        L_0x000b:
            r1 = 0
            java.util.ArrayList r2 = new java.util.ArrayList
            r0 = 10
            r2.<init>(r0)
            java.util.Iterator r4 = r12.iterator()
        L_0x0017:
            boolean r0 = r4.hasNext()
            if (r0 == 0) goto L_0x002b
            java.lang.Object r0 = r4.next()
            com.tencent.bugly.crashreport.crash.a r0 = (com.tencent.bugly.crashreport.crash.a) r0
            boolean r5 = r0.e
            if (r5 == 0) goto L_0x0017
            r2.add(r0)
            goto L_0x0017
        L_0x002b:
            int r0 = r2.size()
            if (r0 <= 0) goto L_0x0169
            java.util.List r4 = r11.b(r2)
            if (r4 == 0) goto L_0x0169
            int r0 = r4.size()
            if (r0 <= 0) goto L_0x0169
            java.util.Collections.sort(r4)
            r2 = r3
        L_0x0041:
            int r0 = r4.size()
            if (r2 >= r0) goto L_0x00ae
            java.lang.Object r0 = r4.get(r2)
            com.tencent.bugly.crashreport.crash.CrashDetailBean r0 = (com.tencent.bugly.crashreport.crash.CrashDetailBean) r0
            if (r2 != 0) goto L_0x0054
        L_0x004f:
            int r1 = r2 + 1
            r2 = r1
            r1 = r0
            goto L_0x0041
        L_0x0054:
            java.lang.String r5 = r0.s
            if (r5 != 0) goto L_0x005a
            r0 = r1
            goto L_0x004f
        L_0x005a:
            java.lang.String r0 = r0.s
            java.lang.String r5 = "\n"
            java.lang.String[] r5 = r0.split(r5)
            if (r5 != 0) goto L_0x0067
            r0 = r1
            goto L_0x004f
        L_0x0067:
            int r6 = r5.length
            r0 = r3
        L_0x0069:
            if (r0 >= r6) goto L_0x0166
            r7 = r5[r0]
            java.lang.String r8 = r1.s
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = ""
            java.lang.StringBuilder r9 = r9.append(r10)
            java.lang.StringBuilder r9 = r9.append(r7)
            java.lang.String r9 = r9.toString()
            boolean r8 = r8.contains(r9)
            if (r8 != 0) goto L_0x00ab
            int r8 = r1.t
            int r8 = r8 + 1
            r1.t = r8
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = r1.s
            java.lang.StringBuilder r8 = r8.append(r9)
            java.lang.StringBuilder r7 = r8.append(r7)
            java.lang.String r8 = "\n"
            java.lang.StringBuilder r7 = r7.append(r8)
            java.lang.String r7 = r7.toString()
            r1.s = r7
        L_0x00ab:
            int r0 = r0 + 1
            goto L_0x0069
        L_0x00ae:
            r0 = r1
        L_0x00af:
            if (r0 != 0) goto L_0x0163
            r0 = 1
            r13.j = r0
            r13.t = r3
            java.lang.String r0 = ""
            r13.s = r0
            r1 = r13
        L_0x00bc:
            java.util.Iterator r2 = r12.iterator()
        L_0x00c0:
            boolean r0 = r2.hasNext()
            if (r0 == 0) goto L_0x0117
            java.lang.Object r0 = r2.next()
            com.tencent.bugly.crashreport.crash.a r0 = (com.tencent.bugly.crashreport.crash.a) r0
            boolean r3 = r0.e
            if (r3 != 0) goto L_0x00c0
            boolean r3 = r0.d
            if (r3 != 0) goto L_0x00c0
            java.lang.String r3 = r1.s
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = ""
            java.lang.StringBuilder r4 = r4.append(r5)
            long r6 = r0.b
            java.lang.StringBuilder r4 = r4.append(r6)
            java.lang.String r4 = r4.toString()
            boolean r3 = r3.contains(r4)
            if (r3 != 0) goto L_0x00c0
            int r3 = r1.t
            int r3 = r3 + 1
            r1.t = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = r1.s
            java.lang.StringBuilder r3 = r3.append(r4)
            long r4 = r0.b
            java.lang.StringBuilder r0 = r3.append(r4)
            java.lang.String r3 = "\n"
            java.lang.StringBuilder r0 = r0.append(r3)
            java.lang.String r0 = r0.toString()
            r1.s = r0
            goto L_0x00c0
        L_0x0117:
            long r2 = r1.r
            long r4 = r13.r
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 == 0) goto L_0x000a
            java.lang.String r0 = r1.s
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = ""
            java.lang.StringBuilder r2 = r2.append(r3)
            long r4 = r13.r
            java.lang.StringBuilder r2 = r2.append(r4)
            java.lang.String r2 = r2.toString()
            boolean r0 = r0.contains(r2)
            if (r0 != 0) goto L_0x000a
            int r0 = r1.t
            int r0 = r0 + 1
            r1.t = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = r1.s
            java.lang.StringBuilder r0 = r0.append(r2)
            long r2 = r13.r
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r2 = "\n"
            java.lang.StringBuilder r0 = r0.append(r2)
            java.lang.String r0 = r0.toString()
            r1.s = r0
            goto L_0x000a
        L_0x0163:
            r1 = r0
            goto L_0x00bc
        L_0x0166:
            r0 = r1
            goto L_0x004f
        L_0x0169:
            r0 = r1
            goto L_0x00af
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.a(java.util.List, com.tencent.bugly.crashreport.crash.CrashDetailBean):com.tencent.bugly.crashreport.crash.CrashDetailBean");
    }

    public boolean a(CrashDetailBean crashDetailBean) {
        return a(crashDetailBean, -123456789);
    }

    public boolean a(CrashDetailBean crashDetailBean, int i) {
        boolean z;
        if (crashDetailBean == null) {
            return true;
        }
        if (c.n != null && !c.n.isEmpty()) {
            an.c("Crash filter for crash stack is: %s", c.n);
            if (crashDetailBean.q.contains(c.n)) {
                an.d("This crash contains the filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        if (c.o != null && !c.o.isEmpty()) {
            an.c("Crash regular filter for crash stack is: %s", c.o);
            if (Pattern.compile(c.o).matcher(crashDetailBean.q).find()) {
                an.d("This crash matches the regular filter string set. It will not be record and upload.", new Object[0]);
                return true;
            }
        }
        boolean z2 = crashDetailBean.b == 1;
        String str = crashDetailBean.n;
        String str2 = crashDetailBean.p;
        String str3 = crashDetailBean.q;
        long j = crashDetailBean.r;
        String str4 = crashDetailBean.m;
        String str5 = crashDetailBean.e;
        String str6 = crashDetailBean.c;
        if (this.f != null) {
            an.c("Calling 'onCrashSaving' of RQD crash listener.", new Object[0]);
            if (!this.f.a(z2, str, str2, str3, i, j, str4, str5, str6, crashDetailBean.A)) {
                an.d("Crash listener 'onCrashSaving' return 'false' thus will not handle this crash.", new Object[0]);
                return true;
            }
        }
        if (crashDetailBean.b != 2) {
            ag agVar = new ag();
            agVar.b = 1;
            agVar.c = crashDetailBean.A;
            agVar.d = crashDetailBean.B;
            agVar.e = crashDetailBean.r;
            this.d.b(1);
            this.d.a(agVar);
            an.b("[crash] a crash occur, handling...", new Object[0]);
        } else {
            an.b("[crash] a caught exception occur, handling...", new Object[0]);
        }
        List b2 = b();
        List list = null;
        if (b2 != null && b2.size() > 0) {
            ArrayList arrayList = new ArrayList(10);
            ArrayList<a> arrayList2 = new ArrayList<>(10);
            arrayList.addAll(a(b2));
            b2.removeAll(arrayList);
            if (!com.tencent.bugly.b.c && c.d) {
                boolean z3 = false;
                Iterator it = b2.iterator();
                while (true) {
                    z = z3;
                    if (!it.hasNext()) {
                        break;
                    }
                    a aVar = (a) it.next();
                    if (crashDetailBean.u.equals(aVar.c)) {
                        if (aVar.e) {
                            z = true;
                        }
                        arrayList2.add(aVar);
                    }
                    z3 = z;
                }
                if (z || arrayList2.size() >= c.c) {
                    an.a("same crash occur too much do merged!", new Object[0]);
                    CrashDetailBean a2 = a((List<a>) arrayList2, crashDetailBean);
                    for (a aVar2 : arrayList2) {
                        if (aVar2.a != a2.a) {
                            arrayList.add(aVar2);
                        }
                    }
                    e(a2);
                    c((List<a>) arrayList);
                    an.b("[crash] save crash success. For this device crash many times, it will not upload crashes immediately", new Object[0]);
                    return true;
                }
            }
            list = arrayList;
        }
        e(crashDetailBean);
        if (list != null && !list.isEmpty()) {
            c(list);
        }
        an.b("[crash] save crash success", new Object[0]);
        return false;
    }

    public List<CrashDetailBean> a() {
        StrategyBean c2 = a.a().c();
        if (c2 == null) {
            an.d("have not synced remote!", new Object[0]);
            return null;
        } else if (!c2.g) {
            an.d("Crashreport remote closed, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            an.b("[init] WARNING! Crashreport closed by server, please check your APP ID correct and Version available, then uninstall and reinstall your app.", new Object[0]);
            return null;
        } else {
            long currentTimeMillis = System.currentTimeMillis();
            long b2 = ap.b();
            List b3 = b();
            if (b3 == null || b3.size() <= 0) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = b3.iterator();
            while (it.hasNext()) {
                a aVar = (a) it.next();
                if (aVar.b < b2 - c.g) {
                    it.remove();
                    arrayList.add(aVar);
                } else if (aVar.d) {
                    if (aVar.b >= currentTimeMillis - 86400000) {
                        it.remove();
                    } else if (!aVar.e) {
                        it.remove();
                        arrayList.add(aVar);
                    }
                } else if (((long) aVar.f) >= 3 && aVar.b < currentTimeMillis - 86400000) {
                    it.remove();
                    arrayList.add(aVar);
                }
            }
            if (arrayList.size() > 0) {
                c((List<a>) arrayList);
            }
            ArrayList arrayList2 = new ArrayList();
            List b4 = b(b3);
            if (b4 != null && b4.size() > 0) {
                String str = com.tencent.bugly.crashreport.common.info.a.b().o;
                Iterator it2 = b4.iterator();
                while (it2.hasNext()) {
                    CrashDetailBean crashDetailBean = (CrashDetailBean) it2.next();
                    if (!str.equals(crashDetailBean.f)) {
                        it2.remove();
                        arrayList2.add(crashDetailBean);
                    }
                }
            }
            if (arrayList2.size() > 0) {
                d((List<CrashDetailBean>) arrayList2);
            }
            return b4;
        }
    }

    public void b(CrashDetailBean crashDetailBean) {
        boolean z = true;
        if (this.f != null) {
            an.c("Calling 'onCrashHandleEnd' of RQD crash listener.", new Object[0]);
            f fVar = this.f;
            if (crashDetailBean.b != 1) {
                z = false;
            }
            fVar.b(z);
        }
    }

    public void a(CrashDetailBean crashDetailBean, long j, boolean z) {
        boolean z2 = false;
        if (c.l) {
            an.a("try to upload right now", new Object[0]);
            ArrayList arrayList = new ArrayList();
            arrayList.add(crashDetailBean);
            if (crashDetailBean.b == 7) {
                z2 = true;
            }
            a(arrayList, j, z, z2, z);
        }
    }

    public void a(final List<CrashDetailBean> list, long j, boolean z, boolean z2, boolean z3) {
        if (!com.tencent.bugly.crashreport.common.info.a.a(this.b).g || this.c == null) {
            return;
        }
        if (z3 || this.c.b(c.a)) {
            StrategyBean c2 = this.e.c();
            if (!c2.g) {
                an.d("remote report is disable!", new Object[0]);
                an.b("[crash] server closed bugly in this app. please check your appid if is correct, and re-install it", new Object[0]);
            } else if (list != null && list.size() != 0) {
                try {
                    String str = this.c.b ? c2.s : c2.t;
                    String str2 = this.c.b ? StrategyBean.c : StrategyBean.a;
                    int i = this.c.b ? 830 : 630;
                    bc a2 = a(this.b, list, com.tencent.bugly.crashreport.common.info.a.b());
                    if (a2 == null) {
                        an.d("create eupPkg fail!", new Object[0]);
                        return;
                    }
                    byte[] a3 = ah.a((m) a2);
                    if (a3 == null) {
                        an.d("send encode fail!", new Object[0]);
                        return;
                    }
                    bd a4 = ah.a(this.b, i, a3);
                    if (a4 == null) {
                        an.d("request package is null.", new Object[0]);
                        return;
                    }
                    AnonymousClass1 r5 = new aj() {
                        public void a(int i) {
                        }

                        public void a(int i, be beVar, long j, long j2, boolean z, String str) {
                            b.this.a(z, list);
                        }
                    };
                    if (z) {
                        this.c.a(a, a4, str, str2, r5, j, z2);
                    } else {
                        this.c.a(a, a4, str, str2, r5, false);
                    }
                } catch (Throwable th) {
                    an.e("req cr error %s", th.toString());
                    if (!an.b(th)) {
                        ThrowableExtension.printStackTrace(th);
                    }
                }
            }
        }
    }

    public void a(boolean z, List<CrashDetailBean> list) {
        if (list != null && list.size() > 0) {
            an.c("up finish update state %b", Boolean.valueOf(z));
            for (CrashDetailBean crashDetailBean : list) {
                an.c("pre uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
                crashDetailBean.l++;
                crashDetailBean.d = z;
                an.c("set uid:%s uc:%d re:%b me:%b", crashDetailBean.c, Integer.valueOf(crashDetailBean.l), Boolean.valueOf(crashDetailBean.d), Boolean.valueOf(crashDetailBean.j));
            }
            for (CrashDetailBean a2 : list) {
                c.a().a(a2);
            }
            an.c("update state size %d", Integer.valueOf(list.size()));
        }
        if (!z) {
            an.b("[crash] upload fail.", new Object[0]);
        }
    }

    public void c(CrashDetailBean crashDetailBean) {
        int i;
        Map map;
        String str;
        if (crashDetailBean != null) {
            if (this.g != null || this.f != null) {
                try {
                    an.a("[crash callback] start user's callback:onCrashHandleStart()", new Object[0]);
                    switch (crashDetailBean.b) {
                        case 0:
                            i = 0;
                            break;
                        case 1:
                            i = 2;
                            break;
                        case 2:
                            i = 1;
                            break;
                        case 3:
                            i = 4;
                            break;
                        case 4:
                            i = 3;
                            break;
                        case 5:
                            i = 5;
                            break;
                        case 6:
                            i = 6;
                            break;
                        case 7:
                            i = 7;
                            break;
                        default:
                            return;
                    }
                    boolean z = crashDetailBean.b == 1;
                    String str2 = crashDetailBean.n;
                    String str3 = crashDetailBean.p;
                    String str4 = crashDetailBean.q;
                    long j = crashDetailBean.r;
                    if (this.f != null) {
                        an.c("Calling 'onCrashHandleStart' of RQD crash listener.", new Object[0]);
                        this.f.a(z);
                        an.c("Calling 'getCrashExtraMessage' of RQD crash listener.", new Object[0]);
                        String b2 = this.f.b(z, str2, str3, str4, -1234567890, j);
                        if (b2 != null) {
                            map = new HashMap(1);
                            map.put("userData", b2);
                        } else {
                            map = null;
                        }
                    } else if (this.g != null) {
                        an.c("Calling 'onCrashHandleStart' of Bugly crash listener.", new Object[0]);
                        map = this.g.onCrashHandleStart(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    } else {
                        map = null;
                    }
                    if (map != null && map.size() > 0) {
                        crashDetailBean.P = new LinkedHashMap(map.size());
                        for (Entry entry : map.entrySet()) {
                            if (!ap.a((String) entry.getKey())) {
                                String str5 = (String) entry.getKey();
                                if (str5.length() > 100) {
                                    str5 = str5.substring(0, 100);
                                    an.d("setted key length is over limit %d substring to %s", Integer.valueOf(100), str5);
                                }
                                String str6 = str5;
                                if (ap.a((String) entry.getValue()) || ((String) entry.getValue()).length() <= 30000) {
                                    str = "" + ((String) entry.getValue());
                                } else {
                                    str = ((String) entry.getValue()).substring(((String) entry.getValue()).length() - 30000);
                                    an.d("setted %s value length is over limit %d substring", str6, Integer.valueOf(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH));
                                }
                                crashDetailBean.P.put(str6, str);
                                an.a("add setted key %s value size:%d", str6, Integer.valueOf(str.length()));
                            }
                        }
                    }
                    an.a("[crash callback] start user's callback:onCrashHandleStart2GetExtraDatas()", new Object[0]);
                    byte[] bArr = null;
                    if (this.f != null) {
                        an.c("Calling 'getCrashExtraData' of RQD crash listener.", new Object[0]);
                        bArr = this.f.a(z, str2, str3, str4, -1234567890, j);
                    } else if (this.g != null) {
                        an.c("Calling 'onCrashHandleStart2GetExtraDatas' of Bugly crash listener.", new Object[0]);
                        bArr = this.g.onCrashHandleStart2GetExtraDatas(i, crashDetailBean.n, crashDetailBean.o, crashDetailBean.q);
                    }
                    crashDetailBean.U = bArr;
                    if (bArr != null) {
                        if (bArr.length > 30000) {
                            an.d("extra bytes size %d is over limit %d will drop over part", Integer.valueOf(bArr.length), Integer.valueOf(BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH));
                            crashDetailBean.U = Arrays.copyOf(bArr, BuglyStrategy.a.MAX_USERDATA_VALUE_LENGTH);
                        }
                        an.a("add extra bytes %d ", Integer.valueOf(bArr.length));
                    }
                } catch (Throwable th) {
                    an.d("crash handle callback something wrong! %s", th.getClass().getName());
                    if (!an.a(th)) {
                        ThrowableExtension.printStackTrace(th);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public ContentValues d(CrashDetailBean crashDetailBean) {
        int i;
        int i2 = 1;
        if (crashDetailBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (crashDetailBean.a > 0) {
                contentValues.put("_id", Long.valueOf(crashDetailBean.a));
            }
            contentValues.put("_tm", Long.valueOf(crashDetailBean.r));
            contentValues.put("_s1", crashDetailBean.u);
            String str = "_up";
            if (crashDetailBean.d) {
                i = 1;
            } else {
                i = 0;
            }
            contentValues.put(str, Integer.valueOf(i));
            String str2 = "_me";
            if (!crashDetailBean.j) {
                i2 = 0;
            }
            contentValues.put(str2, Integer.valueOf(i2));
            contentValues.put("_uc", Integer.valueOf(crashDetailBean.l));
            contentValues.put("_dt", ap.a((Parcelable) crashDetailBean));
            return contentValues;
        } catch (Throwable th) {
            if (an.a(th)) {
                return null;
            }
            ThrowableExtension.printStackTrace(th);
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public CrashDetailBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            CrashDetailBean crashDetailBean = (CrashDetailBean) ap.a(blob, CrashDetailBean.CREATOR);
            if (crashDetailBean == null) {
                return crashDetailBean;
            }
            crashDetailBean.a = j;
            return crashDetailBean;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    public void e(CrashDetailBean crashDetailBean) {
        if (crashDetailBean != null) {
            ContentValues d2 = d(crashDetailBean);
            if (d2 != null) {
                long a2 = ae.a().a("t_cr", d2, (ad) null, true);
                if (a2 >= 0) {
                    an.c("insert %s success!", "t_cr");
                    crashDetailBean.a = a2;
                }
            }
            if (c.i) {
                f(crashDetailBean);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:?, code lost:
        com.tencent.bugly.proguard.an.d("unknown id!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c3, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00c6, code lost:
        r8.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00c3 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:19:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.tencent.bugly.crashreport.crash.CrashDetailBean> b(java.util.List<com.tencent.bugly.crashreport.crash.a> r11) {
        /*
            r10 = this;
            r6 = 0
            r7 = 0
            if (r11 == 0) goto L_0x000a
            int r0 = r11.size()
            if (r0 != 0) goto L_0x000c
        L_0x000a:
            r0 = r7
        L_0x000b:
            return r0
        L_0x000c:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.util.Iterator r1 = r11.iterator()
        L_0x0015:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x003c
            java.lang.Object r0 = r1.next()
            com.tencent.bugly.crashreport.crash.a r0 = (com.tencent.bugly.crashreport.crash.a) r0
            java.lang.String r2 = " or "
            java.lang.StringBuilder r2 = r9.append(r2)
            java.lang.String r3 = "_id"
            java.lang.StringBuilder r2 = r2.append(r3)
            java.lang.String r3 = " = "
            java.lang.StringBuilder r2 = r2.append(r3)
            long r4 = r0.a
            r2.append(r4)
            goto L_0x0015
        L_0x003c:
            java.lang.String r3 = r9.toString()
            int r0 = r3.length()
            if (r0 <= 0) goto L_0x0051
            java.lang.String r0 = " or "
            int r0 = r0.length()
            java.lang.String r3 = r3.substring(r0)
        L_0x0051:
            r9.setLength(r6)
            com.tencent.bugly.proguard.ae r0 = com.tencent.bugly.proguard.ae.a()     // Catch:{ Throwable -> 0x0111, all -> 0x010b }
            java.lang.String r1 = "t_cr"
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 1
            android.database.Cursor r8 = r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x0111, all -> 0x010b }
            if (r8 != 0) goto L_0x006c
            if (r8 == 0) goto L_0x006a
            r8.close()
        L_0x006a:
            r0 = r7
            goto L_0x000b
        L_0x006c:
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            r6.<init>()     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
        L_0x0071:
            boolean r0 = r8.moveToNext()     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            if (r0 == 0) goto L_0x00ca
            com.tencent.bugly.crashreport.crash.CrashDetailBean r0 = r10.a(r8)     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            if (r0 == 0) goto L_0x0094
            r6.add(r0)     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            goto L_0x0071
        L_0x0081:
            r0 = move-exception
            r1 = r8
        L_0x0083:
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x010e }
            if (r2 != 0) goto L_0x008c
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x010e }
        L_0x008c:
            if (r1 == 0) goto L_0x0091
            r1.close()
        L_0x0091:
            r0 = r7
            goto L_0x000b
        L_0x0094:
            java.lang.String r0 = "_id"
            int r0 = r8.getColumnIndex(r0)     // Catch:{ Throwable -> 0x00b8, all -> 0x00c3 }
            long r0 = r8.getLong(r0)     // Catch:{ Throwable -> 0x00b8, all -> 0x00c3 }
            java.lang.String r2 = " or "
            java.lang.StringBuilder r2 = r9.append(r2)     // Catch:{ Throwable -> 0x00b8, all -> 0x00c3 }
            java.lang.String r3 = "_id"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x00b8, all -> 0x00c3 }
            java.lang.String r3 = " = "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x00b8, all -> 0x00c3 }
            r2.append(r0)     // Catch:{ Throwable -> 0x00b8, all -> 0x00c3 }
            goto L_0x0071
        L_0x00b8:
            r0 = move-exception
            java.lang.String r0 = "unknown id!"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            com.tencent.bugly.proguard.an.d(r0, r1)     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            goto L_0x0071
        L_0x00c3:
            r0 = move-exception
        L_0x00c4:
            if (r8 == 0) goto L_0x00c9
            r8.close()
        L_0x00c9:
            throw r0
        L_0x00ca:
            java.lang.String r0 = r9.toString()     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            int r1 = r0.length()     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            if (r1 <= 0) goto L_0x0103
            java.lang.String r1 = " or "
            int r1 = r1.length()     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            java.lang.String r2 = r0.substring(r1)     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            com.tencent.bugly.proguard.ae r0 = com.tencent.bugly.proguard.ae.a()     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            java.lang.String r1 = "t_cr"
            r3 = 0
            r4 = 0
            r5 = 1
            int r0 = r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            java.lang.String r1 = "deleted %s illegle data %d"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            r3 = 0
            java.lang.String r4 = "t_cr"
            r2[r3] = r4     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            r3 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            r2[r3] = r0     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
            com.tencent.bugly.proguard.an.d(r1, r2)     // Catch:{ Throwable -> 0x0081, all -> 0x00c3 }
        L_0x0103:
            if (r8 == 0) goto L_0x0108
            r8.close()
        L_0x0108:
            r0 = r6
            goto L_0x000b
        L_0x010b:
            r0 = move-exception
            r8 = r7
            goto L_0x00c4
        L_0x010e:
            r0 = move-exception
            r8 = r1
            goto L_0x00c4
        L_0x0111:
            r0 = move-exception
            r1 = r7
            goto L_0x0083
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.b(java.util.List):java.util.List");
    }

    /* access modifiers changed from: protected */
    public a b(Cursor cursor) {
        boolean z;
        boolean z2 = true;
        if (cursor == null) {
            return null;
        }
        try {
            a aVar = new a();
            aVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            aVar.b = cursor.getLong(cursor.getColumnIndex("_tm"));
            aVar.c = cursor.getString(cursor.getColumnIndex("_s1"));
            if (cursor.getInt(cursor.getColumnIndex("_up")) == 1) {
                z = true;
            } else {
                z = false;
            }
            aVar.d = z;
            if (cursor.getInt(cursor.getColumnIndex("_me")) != 1) {
                z2 = false;
            }
            aVar.e = z2;
            aVar.f = cursor.getInt(cursor.getColumnIndex("_uc"));
            return aVar;
        } catch (Throwable th) {
            if (an.a(th)) {
                return null;
            }
            ThrowableExtension.printStackTrace(th);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        com.tencent.bugly.proguard.an.d("unknown id!", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x009b, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x009e, code lost:
        r6.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x009b A[ExcHandler: all (th java.lang.Throwable), Splitter:B:7:0x0045] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x009e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.tencent.bugly.crashreport.crash.a> b() {
        /*
            r9 = this;
            r7 = 0
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            r0 = 6
            java.lang.String[] r2 = new java.lang.String[r0]     // Catch:{ Throwable -> 0x00e9, all -> 0x00e3 }
            r0 = 0
            java.lang.String r1 = "_id"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00e9, all -> 0x00e3 }
            r0 = 1
            java.lang.String r1 = "_tm"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00e9, all -> 0x00e3 }
            r0 = 2
            java.lang.String r1 = "_s1"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00e9, all -> 0x00e3 }
            r0 = 3
            java.lang.String r1 = "_up"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00e9, all -> 0x00e3 }
            r0 = 4
            java.lang.String r1 = "_me"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00e9, all -> 0x00e3 }
            r0 = 5
            java.lang.String r1 = "_uc"
            r2[r0] = r1     // Catch:{ Throwable -> 0x00e9, all -> 0x00e3 }
            com.tencent.bugly.proguard.ae r0 = com.tencent.bugly.proguard.ae.a()     // Catch:{ Throwable -> 0x00e9, all -> 0x00e3 }
            java.lang.String r1 = "t_cr"
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 1
            android.database.Cursor r6 = r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x00e9, all -> 0x00e3 }
            if (r6 != 0) goto L_0x0045
            if (r6 == 0) goto L_0x0043
            r6.close()
        L_0x0043:
            r0 = r7
        L_0x0044:
            return r0
        L_0x0045:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            r0.<init>()     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
        L_0x004a:
            boolean r1 = r6.moveToNext()     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            if (r1 == 0) goto L_0x00a2
            com.tencent.bugly.crashreport.crash.a r1 = r9.b(r6)     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            if (r1 == 0) goto L_0x006c
            r8.add(r1)     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            goto L_0x004a
        L_0x005a:
            r0 = move-exception
            r7 = r6
        L_0x005c:
            boolean r1 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x00e6 }
            if (r1 != 0) goto L_0x0065
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x00e6 }
        L_0x0065:
            if (r7 == 0) goto L_0x006a
            r7.close()
        L_0x006a:
            r0 = r8
            goto L_0x0044
        L_0x006c:
            java.lang.String r1 = "_id"
            int r1 = r6.getColumnIndex(r1)     // Catch:{ Throwable -> 0x0090, all -> 0x009b }
            long r2 = r6.getLong(r1)     // Catch:{ Throwable -> 0x0090, all -> 0x009b }
            java.lang.String r1 = " or "
            java.lang.StringBuilder r1 = r0.append(r1)     // Catch:{ Throwable -> 0x0090, all -> 0x009b }
            java.lang.String r4 = "_id"
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x0090, all -> 0x009b }
            java.lang.String r4 = " = "
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x0090, all -> 0x009b }
            r1.append(r2)     // Catch:{ Throwable -> 0x0090, all -> 0x009b }
            goto L_0x004a
        L_0x0090:
            r1 = move-exception
            java.lang.String r1 = "unknown id!"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            com.tencent.bugly.proguard.an.d(r1, r2)     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            goto L_0x004a
        L_0x009b:
            r0 = move-exception
        L_0x009c:
            if (r6 == 0) goto L_0x00a1
            r6.close()
        L_0x00a1:
            throw r0
        L_0x00a2:
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            int r1 = r0.length()     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            if (r1 <= 0) goto L_0x00db
            java.lang.String r1 = " or "
            int r1 = r1.length()     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            java.lang.String r2 = r0.substring(r1)     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            com.tencent.bugly.proguard.ae r0 = com.tencent.bugly.proguard.ae.a()     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            java.lang.String r1 = "t_cr"
            r3 = 0
            r4 = 0
            r5 = 1
            int r0 = r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            java.lang.String r1 = "deleted %s illegle data %d"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            r3 = 0
            java.lang.String r4 = "t_cr"
            r2[r3] = r4     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            r3 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            r2[r3] = r0     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
            com.tencent.bugly.proguard.an.d(r1, r2)     // Catch:{ Throwable -> 0x005a, all -> 0x009b }
        L_0x00db:
            if (r6 == 0) goto L_0x00e0
            r6.close()
        L_0x00e0:
            r0 = r8
            goto L_0x0044
        L_0x00e3:
            r0 = move-exception
            r6 = r7
            goto L_0x009c
        L_0x00e6:
            r0 = move-exception
            r6 = r7
            goto L_0x009c
        L_0x00e9:
            r0 = move-exception
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.b():java.util.List");
    }

    public void c(List<a> list) {
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (a aVar : list) {
                sb.append(" or ").append("_id").append(" = ").append(aVar.a);
            }
            String sb2 = sb.toString();
            if (sb2.length() > 0) {
                sb2 = sb2.substring(" or ".length());
            }
            sb.setLength(0);
            try {
                an.c("deleted %s data %d", "t_cr", Integer.valueOf(ae.a().a("t_cr", sb2, (String[]) null, (ad) null, true)));
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }

    public void d(List<CrashDetailBean> list) {
        if (list != null) {
            try {
                if (list.size() != 0) {
                    StringBuilder sb = new StringBuilder();
                    for (CrashDetailBean crashDetailBean : list) {
                        sb.append(" or ").append("_id").append(" = ").append(crashDetailBean.a);
                    }
                    String sb2 = sb.toString();
                    if (sb2.length() > 0) {
                        sb2 = sb2.substring(" or ".length());
                    }
                    sb.setLength(0);
                    an.c("deleted %s data %d", "t_cr", Integer.valueOf(ae.a().a("t_cr", sb2, (String[]) null, (ad) null, true)));
                }
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }

    public static bb a(Context context, CrashDetailBean crashDetailBean, com.tencent.bugly.crashreport.common.info.a aVar) {
        ba baVar;
        int i;
        boolean z = true;
        if (context == null || crashDetailBean == null || aVar == null) {
            an.d("enExp args == null", new Object[0]);
            return null;
        }
        bb bbVar = new bb();
        switch (crashDetailBean.b) {
            case 0:
                bbVar.a = crashDetailBean.j ? "200" : "100";
                break;
            case 1:
                bbVar.a = crashDetailBean.j ? "201" : "101";
                break;
            case 2:
                bbVar.a = crashDetailBean.j ? "202" : "102";
                break;
            case 3:
                bbVar.a = crashDetailBean.j ? "203" : "103";
                break;
            case 4:
                bbVar.a = crashDetailBean.j ? "204" : "104";
                break;
            case 5:
                bbVar.a = crashDetailBean.j ? "207" : "107";
                break;
            case 6:
                bbVar.a = crashDetailBean.j ? "206" : "106";
                break;
            case 7:
                bbVar.a = crashDetailBean.j ? "208" : "108";
                break;
            default:
                an.e("crash type error! %d", Integer.valueOf(crashDetailBean.b));
                break;
        }
        bbVar.b = crashDetailBean.r;
        bbVar.c = crashDetailBean.n;
        bbVar.d = crashDetailBean.o;
        bbVar.e = crashDetailBean.p;
        bbVar.g = crashDetailBean.q;
        bbVar.h = crashDetailBean.z;
        bbVar.i = crashDetailBean.c;
        bbVar.j = null;
        bbVar.l = crashDetailBean.m;
        bbVar.m = crashDetailBean.e;
        bbVar.f = crashDetailBean.B;
        bbVar.t = com.tencent.bugly.crashreport.common.info.a.b().i();
        bbVar.n = null;
        if (crashDetailBean.i != null && crashDetailBean.i.size() > 0) {
            bbVar.o = new ArrayList<>();
            for (Entry entry : crashDetailBean.i.entrySet()) {
                ay ayVar = new ay();
                ayVar.a = ((PlugInBean) entry.getValue()).a;
                ayVar.c = ((PlugInBean) entry.getValue()).c;
                ayVar.e = ((PlugInBean) entry.getValue()).b;
                ayVar.b = aVar.r();
                bbVar.o.add(ayVar);
            }
        }
        if (crashDetailBean.h != null && crashDetailBean.h.size() > 0) {
            bbVar.p = new ArrayList<>();
            for (Entry entry2 : crashDetailBean.h.entrySet()) {
                ay ayVar2 = new ay();
                ayVar2.a = ((PlugInBean) entry2.getValue()).a;
                ayVar2.c = ((PlugInBean) entry2.getValue()).c;
                ayVar2.e = ((PlugInBean) entry2.getValue()).b;
                bbVar.p.add(ayVar2);
            }
        }
        if (crashDetailBean.j) {
            bbVar.k = crashDetailBean.t;
            if (crashDetailBean.s != null && crashDetailBean.s.length() > 0) {
                if (bbVar.q == null) {
                    bbVar.q = new ArrayList<>();
                }
                try {
                    bbVar.q.add(new ba(1, "alltimes.txt", crashDetailBean.s.getBytes("utf-8")));
                } catch (UnsupportedEncodingException e2) {
                    ThrowableExtension.printStackTrace(e2);
                    bbVar.q = null;
                }
            }
            String str = "crashcount:%d sz:%d";
            Object[] objArr = new Object[2];
            objArr[0] = Integer.valueOf(bbVar.k);
            if (bbVar.q != null) {
                i = bbVar.q.size();
            } else {
                i = 0;
            }
            objArr[1] = Integer.valueOf(i);
            an.c(str, objArr);
        }
        if (crashDetailBean.w != null) {
            if (bbVar.q == null) {
                bbVar.q = new ArrayList<>();
            }
            try {
                bbVar.q.add(new ba(1, "log.txt", crashDetailBean.w.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e3) {
                ThrowableExtension.printStackTrace(e3);
                bbVar.q = null;
            }
        }
        if (crashDetailBean.x != null) {
            if (bbVar.q == null) {
                bbVar.q = new ArrayList<>();
            }
            try {
                bbVar.q.add(new ba(1, "jniLog.txt", crashDetailBean.x.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e4) {
                ThrowableExtension.printStackTrace(e4);
                bbVar.q = null;
            }
        }
        if (!ap.a(crashDetailBean.V)) {
            if (bbVar.q == null) {
                bbVar.q = new ArrayList<>();
            }
            try {
                baVar = new ba(1, "crashInfos.txt", crashDetailBean.V.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e5) {
                ThrowableExtension.printStackTrace(e5);
                baVar = null;
            }
            if (baVar != null) {
                an.c("attach crash infos", new Object[0]);
                bbVar.q.add(baVar);
            }
        }
        if (crashDetailBean.W != null) {
            if (bbVar.q == null) {
                bbVar.q = new ArrayList<>();
            }
            ba a2 = a("backupRecord.zip", context, crashDetailBean.W);
            if (a2 != null) {
                an.c("attach backup record", new Object[0]);
                bbVar.q.add(a2);
            }
        }
        if (crashDetailBean.y != null && crashDetailBean.y.length > 0) {
            ba baVar2 = new ba(2, "buglylog.zip", crashDetailBean.y);
            if (baVar2 != null) {
                an.c("attach user log", new Object[0]);
                if (bbVar.q == null) {
                    bbVar.q = new ArrayList<>();
                }
                bbVar.q.add(baVar2);
            }
        }
        if (crashDetailBean.b == 3) {
            if (bbVar.q == null) {
                bbVar.q = new ArrayList<>();
            }
            if (crashDetailBean.P != null && crashDetailBean.P.containsKey("BUGLY_CR_01")) {
                try {
                    bbVar.q.add(new ba(1, "anrMessage.txt", ((String) crashDetailBean.P.get("BUGLY_CR_01")).getBytes("utf-8")));
                    an.c("attach anr message", new Object[0]);
                } catch (UnsupportedEncodingException e6) {
                    ThrowableExtension.printStackTrace(e6);
                    bbVar.q = null;
                }
                crashDetailBean.P.remove("BUGLY_CR_01");
            }
            if (crashDetailBean.v != null) {
                ba a3 = a("trace.zip", context, crashDetailBean.v);
                if (a3 != null) {
                    an.c("attach traces", new Object[0]);
                    bbVar.q.add(a3);
                }
            }
        }
        if (crashDetailBean.b == 1) {
            if (bbVar.q == null) {
                bbVar.q = new ArrayList<>();
            }
            if (crashDetailBean.v != null) {
                ba a4 = a("tomb.zip", context, crashDetailBean.v);
                if (a4 != null) {
                    an.c("attach tombs", new Object[0]);
                    bbVar.q.add(a4);
                }
            }
        }
        if (aVar.J != null && !aVar.J.isEmpty()) {
            if (bbVar.q == null) {
                bbVar.q = new ArrayList<>();
            }
            StringBuilder sb = new StringBuilder();
            for (String append : aVar.J) {
                sb.append(append);
            }
            try {
                bbVar.q.add(new ba(1, "martianlog.txt", sb.toString().getBytes("utf-8")));
                an.c("attach pageTracingList", new Object[0]);
            } catch (UnsupportedEncodingException e7) {
                ThrowableExtension.printStackTrace(e7);
            }
        }
        if (crashDetailBean.U != null && crashDetailBean.U.length > 0) {
            if (bbVar.q == null) {
                bbVar.q = new ArrayList<>();
            }
            bbVar.q.add(new ba(1, "userExtraByteData", crashDetailBean.U));
            an.c("attach extraData", new Object[0]);
        }
        bbVar.r = new HashMap();
        bbVar.r.put("A9", "" + crashDetailBean.C);
        bbVar.r.put("A11", "" + crashDetailBean.D);
        bbVar.r.put("A10", "" + crashDetailBean.E);
        bbVar.r.put("A23", "" + crashDetailBean.f);
        bbVar.r.put("A7", "" + aVar.k);
        bbVar.r.put("A6", "" + aVar.s());
        bbVar.r.put("A5", "" + aVar.r());
        bbVar.r.put("A22", "" + aVar.h());
        bbVar.r.put("A2", "" + crashDetailBean.G);
        bbVar.r.put("A1", "" + crashDetailBean.F);
        bbVar.r.put("A24", "" + aVar.m);
        bbVar.r.put("A17", "" + crashDetailBean.H);
        bbVar.r.put("A3", "" + aVar.k());
        bbVar.r.put("A16", "" + aVar.m());
        bbVar.r.put("A25", "" + aVar.n());
        bbVar.r.put("A14", "" + aVar.l());
        bbVar.r.put("A15", "" + aVar.w());
        bbVar.r.put("A13", "" + aVar.x());
        bbVar.r.put("A34", "" + crashDetailBean.A);
        if (aVar.C != null) {
            bbVar.r.put("productIdentify", "" + aVar.C);
        }
        try {
            bbVar.r.put("A26", "" + URLEncoder.encode(crashDetailBean.I, "utf-8"));
        } catch (UnsupportedEncodingException e8) {
            ThrowableExtension.printStackTrace(e8);
        }
        if (crashDetailBean.b == 1) {
            bbVar.r.put("A27", "" + crashDetailBean.L);
            bbVar.r.put("A28", "" + crashDetailBean.K);
            bbVar.r.put("A29", "" + crashDetailBean.k);
        }
        bbVar.r.put("A30", "" + crashDetailBean.M);
        bbVar.r.put("A18", "" + crashDetailBean.N);
        bbVar.r.put("A36", "" + (!crashDetailBean.O));
        bbVar.r.put("F02", "" + aVar.v);
        bbVar.r.put("F03", "" + aVar.w);
        bbVar.r.put("F04", "" + aVar.e());
        bbVar.r.put("F05", "" + aVar.x);
        bbVar.r.put("F06", "" + aVar.u);
        bbVar.r.put("F08", "" + aVar.A);
        bbVar.r.put("F09", "" + aVar.B);
        bbVar.r.put("F10", "" + aVar.y);
        if (crashDetailBean.Q >= 0) {
            bbVar.r.put("C01", "" + crashDetailBean.Q);
        }
        if (crashDetailBean.R >= 0) {
            bbVar.r.put("C02", "" + crashDetailBean.R);
        }
        if (crashDetailBean.S != null && crashDetailBean.S.size() > 0) {
            for (Entry entry3 : crashDetailBean.S.entrySet()) {
                bbVar.r.put("C03_" + ((String) entry3.getKey()), entry3.getValue());
            }
        }
        if (crashDetailBean.T != null && crashDetailBean.T.size() > 0) {
            for (Entry entry4 : crashDetailBean.T.entrySet()) {
                bbVar.r.put("C04_" + ((String) entry4.getKey()), entry4.getValue());
            }
        }
        bbVar.s = null;
        if (crashDetailBean.P != null && crashDetailBean.P.size() > 0) {
            bbVar.s = crashDetailBean.P;
            an.a("setted message size %d", Integer.valueOf(bbVar.s.size()));
        }
        String str2 = "%s rid:%s sess:%s ls:%ds isR:%b isF:%b isM:%b isN:%b mc:%d ,%s ,isUp:%b ,vm:%d";
        Object[] objArr2 = new Object[12];
        objArr2[0] = crashDetailBean.n;
        objArr2[1] = crashDetailBean.c;
        objArr2[2] = aVar.e();
        objArr2[3] = Long.valueOf((crashDetailBean.r - crashDetailBean.N) / 1000);
        objArr2[4] = Boolean.valueOf(crashDetailBean.k);
        objArr2[5] = Boolean.valueOf(crashDetailBean.O);
        objArr2[6] = Boolean.valueOf(crashDetailBean.j);
        if (crashDetailBean.b != 1) {
            z = false;
        }
        objArr2[7] = Boolean.valueOf(z);
        objArr2[8] = Integer.valueOf(crashDetailBean.t);
        objArr2[9] = crashDetailBean.s;
        objArr2[10] = Boolean.valueOf(crashDetailBean.d);
        objArr2[11] = Integer.valueOf(bbVar.r.size());
        an.c(str2, objArr2);
        return bbVar;
    }

    public static bc a(Context context, List<CrashDetailBean> list, com.tencent.bugly.crashreport.common.info.a aVar) {
        if (context == null || list == null || list.size() == 0 || aVar == null) {
            an.d("enEXPPkg args == null!", new Object[0]);
            return null;
        }
        bc bcVar = new bc();
        bcVar.a = new ArrayList<>();
        for (CrashDetailBean a2 : list) {
            bcVar.a.add(a(context, a2, aVar));
        }
        return bcVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x005c A[Catch:{ all -> 0x00ef }] */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0061 A[SYNTHETIC, Splitter:B:22:0x0061] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00cd A[SYNTHETIC, Splitter:B:49:0x00cd] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.tencent.bugly.proguard.ba a(java.lang.String r9, android.content.Context r10, java.lang.String r11) {
        /*
            r2 = 1
            r0 = 0
            r8 = 0
            if (r11 == 0) goto L_0x0007
            if (r10 != 0) goto L_0x0010
        L_0x0007:
            java.lang.String r1 = "rqdp{  createZipAttachment sourcePath == null || context == null ,pls check}"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.d(r1, r2)
        L_0x000f:
            return r0
        L_0x0010:
            java.lang.String r1 = "zip %s"
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r2[r8] = r11
            com.tencent.bugly.proguard.an.c(r1, r2)
            java.io.File r1 = new java.io.File
            r1.<init>(r11)
            java.io.File r3 = new java.io.File
            java.io.File r2 = r10.getCacheDir()
            r3.<init>(r2, r9)
            r2 = 5000(0x1388, float:7.006E-42)
            boolean r1 = com.tencent.bugly.proguard.ap.a(r1, r3, r2)
            if (r1 != 0) goto L_0x0039
            java.lang.String r1 = "zip fail!"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.d(r1, r2)
            goto L_0x000f
        L_0x0039:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00f1, all -> 0x00c8 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00f1, all -> 0x00c8 }
            r4 = 4096(0x1000, float:5.74E-42)
            byte[] r4 = new byte[r4]     // Catch:{ Throwable -> 0x0055 }
        L_0x0047:
            int r5 = r2.read(r4)     // Catch:{ Throwable -> 0x0055 }
            if (r5 <= 0) goto L_0x0078
            r6 = 0
            r1.write(r4, r6, r5)     // Catch:{ Throwable -> 0x0055 }
            r1.flush()     // Catch:{ Throwable -> 0x0055 }
            goto L_0x0047
        L_0x0055:
            r1 = move-exception
        L_0x0056:
            boolean r4 = com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x00ef }
            if (r4 != 0) goto L_0x005f
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x00ef }
        L_0x005f:
            if (r2 == 0) goto L_0x0064
            r2.close()     // Catch:{ IOException -> 0x00bd }
        L_0x0064:
            if (r3 == 0) goto L_0x000f
            boolean r1 = r3.exists()
            if (r1 == 0) goto L_0x000f
            java.lang.String r1 = "del tmp"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.c(r1, r2)
            r3.delete()
            goto L_0x000f
        L_0x0078:
            byte[] r4 = r1.toByteArray()     // Catch:{ Throwable -> 0x0055 }
            java.lang.String r1 = "read bytes :%d"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0055 }
            r6 = 0
            int r7 = r4.length     // Catch:{ Throwable -> 0x0055 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)     // Catch:{ Throwable -> 0x0055 }
            r5[r6] = r7     // Catch:{ Throwable -> 0x0055 }
            com.tencent.bugly.proguard.an.c(r1, r5)     // Catch:{ Throwable -> 0x0055 }
            com.tencent.bugly.proguard.ba r1 = new com.tencent.bugly.proguard.ba     // Catch:{ Throwable -> 0x0055 }
            r5 = 2
            java.lang.String r6 = r3.getName()     // Catch:{ Throwable -> 0x0055 }
            r1.<init>(r5, r6, r4)     // Catch:{ Throwable -> 0x0055 }
            if (r2 == 0) goto L_0x009c
            r2.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x009c:
            if (r3 == 0) goto L_0x00af
            boolean r0 = r3.exists()
            if (r0 == 0) goto L_0x00af
            java.lang.String r0 = "del tmp"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.c(r0, r2)
            r3.delete()
        L_0x00af:
            r0 = r1
            goto L_0x000f
        L_0x00b2:
            r0 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)
            if (r2 != 0) goto L_0x009c
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
            goto L_0x009c
        L_0x00bd:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r1)
            if (r2 != 0) goto L_0x0064
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x0064
        L_0x00c8:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L_0x00cb:
            if (r2 == 0) goto L_0x00d0
            r2.close()     // Catch:{ IOException -> 0x00e4 }
        L_0x00d0:
            if (r3 == 0) goto L_0x00e3
            boolean r1 = r3.exists()
            if (r1 == 0) goto L_0x00e3
            java.lang.String r1 = "del tmp"
            java.lang.Object[] r2 = new java.lang.Object[r8]
            com.tencent.bugly.proguard.an.c(r1, r2)
            r3.delete()
        L_0x00e3:
            throw r0
        L_0x00e4:
            r1 = move-exception
            boolean r2 = com.tencent.bugly.proguard.an.a(r1)
            if (r2 != 0) goto L_0x00d0
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)
            goto L_0x00d0
        L_0x00ef:
            r0 = move-exception
            goto L_0x00cb
        L_0x00f1:
            r1 = move-exception
            r2 = r0
            goto L_0x0056
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.crash.b.a(java.lang.String, android.content.Context, java.lang.String):com.tencent.bugly.proguard.ba");
    }

    private boolean f(CrashDetailBean crashDetailBean) {
        try {
            an.c("save eup logs", new Object[0]);
            com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
            String f2 = b2.f();
            String str = b2.o;
            String str2 = crashDetailBean.A;
            b2.getClass();
            String format = String.format(Locale.US, "#--------\npackage:%s\nversion:%s\nsdk:%s\nprocess:%s\ndate:%s\ntype:%s\nmessage:%s\nstack:\n%s\neupID:%s\n", new Object[]{f2, str, "2.6.5", str2, ap.a(new Date(crashDetailBean.r)), crashDetailBean.n, crashDetailBean.o, crashDetailBean.q, crashDetailBean.c});
            String str3 = null;
            if (c.j != null) {
                File file = new File(c.j);
                if (file.isFile()) {
                    file = file.getParentFile();
                }
                str3 = file.getAbsolutePath();
            } else if (Environment.getExternalStorageState().equals("mounted")) {
                str3 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Tencent/" + this.b.getPackageName();
            }
            ap.a(this.b, str3 + "/euplog.txt", format, c.k);
            return true;
        } catch (Throwable th) {
            an.d("rqdp{  save error} %s", th.toString());
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return false;
        }
    }

    public static void a(String str, String str2, String str3, String str4, String str5, CrashDetailBean crashDetailBean) {
        String str6;
        com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
        if (b2 != null) {
            an.e("#++++++++++Record By Bugly++++++++++#", new Object[0]);
            an.e("# You can use Bugly(http:\\\\bugly.qq.com) to get more Crash Detail!", new Object[0]);
            an.e("# PKG NAME: %s", b2.d);
            an.e("# APP VER: %s", b2.o);
            an.e("# LAUNCH TIME: %s", ap.a(new Date(com.tencent.bugly.crashreport.common.info.a.b().a)));
            an.e("# CRASH TYPE: %s", str);
            an.e("# CRASH TIME: %s", str2);
            an.e("# CRASH PROCESS: %s", str3);
            an.e("# CRASH THREAD: %s", str4);
            if (crashDetailBean != null) {
                an.e("# REPORT ID: %s", crashDetailBean.c);
                String str7 = "# CRASH DEVICE: %s %s";
                Object[] objArr = new Object[2];
                objArr[0] = b2.l;
                objArr[1] = b2.x().booleanValue() ? "ROOTED" : "UNROOT";
                an.e(str7, objArr);
                an.e("# RUNTIME AVAIL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.C), Long.valueOf(crashDetailBean.D), Long.valueOf(crashDetailBean.E));
                an.e("# RUNTIME TOTAL RAM:%d ROM:%d SD:%d", Long.valueOf(crashDetailBean.F), Long.valueOf(crashDetailBean.G), Long.valueOf(crashDetailBean.H));
                if (!ap.a(crashDetailBean.L)) {
                    an.e("# EXCEPTION FIRED BY %s %s", crashDetailBean.L, crashDetailBean.K);
                } else if (crashDetailBean.b == 3) {
                    String str8 = "# EXCEPTION ANR MESSAGE:\n %s";
                    Object[] objArr2 = new Object[1];
                    if (crashDetailBean.P == null) {
                        str6 = "null";
                    } else {
                        str6 = "" + ((String) crashDetailBean.P.get("BUGLY_CR_01"));
                    }
                    objArr2[0] = str6;
                    an.e(str8, objArr2);
                }
            }
            if (!ap.a(str5)) {
                an.e("# CRASH STACK: ", new Object[0]);
                an.e(str5, new Object[0]);
            }
            an.e("#++++++++++++++++++++++++++++++++++++++++++#", new Object[0]);
        }
    }
}
