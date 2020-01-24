package com.tencent.bugly.crashreport.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.ad;
import com.tencent.bugly.proguard.ae;
import com.tencent.bugly.proguard.ah;
import com.tencent.bugly.proguard.aj;
import com.tencent.bugly.proguard.ak;
import com.tencent.bugly.proguard.am;
import com.tencent.bugly.proguard.an;
import com.tencent.bugly.proguard.ap;
import com.tencent.bugly.proguard.bd;
import com.tencent.bugly.proguard.be;
import com.tencent.bugly.proguard.bi;
import com.tencent.bugly.proguard.m;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import no.nordicsemi.android.dfu.internal.scanner.BootloaderScanner;

/* compiled from: BUGLY */
public class a {
    private Context a;
    /* access modifiers changed from: private */
    public long b;
    private int c;
    private boolean d = true;

    /* renamed from: com.tencent.bugly.crashreport.biz.a$a reason: collision with other inner class name */
    /* compiled from: BUGLY */
    class C0004a implements Runnable {
        private boolean b;
        private UserInfoBean c;

        public C0004a(UserInfoBean userInfoBean, boolean z) {
            this.c = userInfoBean;
            this.b = z;
        }

        private void a(UserInfoBean userInfoBean) {
            if (userInfoBean != null) {
                com.tencent.bugly.crashreport.common.info.a b2 = com.tencent.bugly.crashreport.common.info.a.b();
                if (b2 != null) {
                    userInfoBean.j = b2.e();
                }
            }
        }

        public void run() {
            try {
                if (this.c != null) {
                    a(this.c);
                    an.c("[UserInfo] Record user info.", new Object[0]);
                    a.this.a(this.c, false);
                }
                if (this.b) {
                    a.this.b();
                }
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }

    /* compiled from: BUGLY */
    class b implements Runnable {
        b() {
        }

        public void run() {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < a.this.b) {
                am.a().a(new b(), (a.this.b - currentTimeMillis) + BootloaderScanner.TIMEOUT);
                return;
            }
            a.this.a(3, false, 0);
            a.this.a();
        }
    }

    /* compiled from: BUGLY */
    class c implements Runnable {
        private long b = 21600000;

        public c(long j) {
            this.b = j;
        }

        public void run() {
            a.this.b();
            a.this.b(this.b);
        }
    }

    public a(Context context, boolean z) {
        this.a = context;
        this.d = z;
    }

    private static UserInfoBean a(Context context, int i) {
        int i2 = 1;
        com.tencent.bugly.crashreport.common.info.a a2 = com.tencent.bugly.crashreport.common.info.a.a(context);
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.b = i;
        userInfoBean.c = a2.e;
        userInfoBean.d = a2.g();
        userInfoBean.e = System.currentTimeMillis();
        userInfoBean.f = -1;
        userInfoBean.n = a2.o;
        if (i != 1) {
            i2 = 0;
        }
        userInfoBean.o = i2;
        userInfoBean.l = a2.a();
        userInfoBean.m = a2.u;
        userInfoBean.g = a2.v;
        userInfoBean.h = a2.w;
        userInfoBean.i = a2.x;
        userInfoBean.k = a2.y;
        userInfoBean.r = a2.B();
        userInfoBean.s = a2.G();
        userInfoBean.p = a2.H();
        userInfoBean.q = a2.I();
        return userInfoBean;
    }

    public void a(int i, boolean z, long j) {
        com.tencent.bugly.crashreport.common.strategy.a a2 = com.tencent.bugly.crashreport.common.strategy.a.a();
        if (a2 == null || a2.c().h || i == 1 || i == 3) {
            if (i == 1 || i == 3) {
                this.c++;
            }
            am.a().a(new C0004a(a(this.a, i), z), j);
            return;
        }
        an.e("UserInfo is disable", new Object[0]);
    }

    public void a(long j) {
        am.a().a(new C0004a(null, true), j);
    }

    public void b(long j) {
        am.a().a(new c(j), j);
    }

    public void a() {
        this.b = ap.b() + 86400000;
        am.a().a(new b(), (this.b - System.currentTimeMillis()) + BootloaderScanner.TIMEOUT);
    }

    /* access modifiers changed from: private */
    public synchronized void c() {
        final List arrayList;
        boolean z;
        int i;
        boolean z2 = false;
        synchronized (this) {
            if (this.d) {
                ak a2 = ak.a();
                if (a2 != null) {
                    com.tencent.bugly.crashreport.common.strategy.a a3 = com.tencent.bugly.crashreport.common.strategy.a.a();
                    if (a3 != null && (!a3.b() || a2.b(1001))) {
                        String str = com.tencent.bugly.crashreport.common.info.a.a(this.a).e;
                        ArrayList arrayList2 = new ArrayList();
                        List a4 = a(str);
                        if (a4 != null) {
                            int size = a4.size() - 20;
                            if (size > 0) {
                                for (int i2 = 0; i2 < a4.size() - 1; i2++) {
                                    for (int i3 = i2 + 1; i3 < a4.size(); i3++) {
                                        if (((UserInfoBean) a4.get(i2)).e > ((UserInfoBean) a4.get(i3)).e) {
                                            UserInfoBean userInfoBean = (UserInfoBean) a4.get(i2);
                                            a4.set(i2, a4.get(i3));
                                            a4.set(i3, userInfoBean);
                                        }
                                    }
                                }
                                for (int i4 = 0; i4 < size; i4++) {
                                    arrayList2.add(a4.get(i4));
                                }
                            }
                            Iterator it = a4.iterator();
                            int i5 = 0;
                            while (it.hasNext()) {
                                UserInfoBean userInfoBean2 = (UserInfoBean) it.next();
                                if (userInfoBean2.f != -1) {
                                    it.remove();
                                    if (userInfoBean2.e < ap.b()) {
                                        arrayList2.add(userInfoBean2);
                                    }
                                }
                                if (userInfoBean2.e <= System.currentTimeMillis() - 600000 || !(userInfoBean2.b == 1 || userInfoBean2.b == 4 || userInfoBean2.b == 3)) {
                                    i = i5;
                                } else {
                                    i = i5 + 1;
                                }
                                i5 = i;
                            }
                            if (i5 > 15) {
                                an.d("[UserInfo] Upload user info too many times in 10 min: %d", Integer.valueOf(i5));
                                z = false;
                            } else {
                                z = true;
                            }
                            arrayList = a4;
                        } else {
                            arrayList = new ArrayList();
                            z = true;
                        }
                        if (arrayList2.size() > 0) {
                            a((List<UserInfoBean>) arrayList2);
                        }
                        if (!z || arrayList.size() == 0) {
                            an.c("[UserInfo] There is no user info in local database.", new Object[0]);
                        } else {
                            an.c("[UserInfo] Upload user info(size: %d)", Integer.valueOf(arrayList.size()));
                            bi a5 = ah.a(arrayList, this.c == 1 ? 1 : 2);
                            if (a5 == null) {
                                an.d("[UserInfo] Failed to create UserInfoPackage.", new Object[0]);
                            } else {
                                byte[] a6 = ah.a((m) a5);
                                if (a6 == null) {
                                    an.d("[UserInfo] Failed to encode data.", new Object[0]);
                                } else {
                                    bd a7 = ah.a(this.a, a2.b ? 840 : 640, a6);
                                    if (a7 == null) {
                                        an.d("[UserInfo] Request package is null.", new Object[0]);
                                    } else {
                                        AnonymousClass1 r5 = new aj() {
                                            public void a(int i) {
                                            }

                                            public void a(int i, be beVar, long j, long j2, boolean z, String str) {
                                                if (z) {
                                                    an.c("[UserInfo] Successfully uploaded user info.", new Object[0]);
                                                    long currentTimeMillis = System.currentTimeMillis();
                                                    for (UserInfoBean userInfoBean : arrayList) {
                                                        userInfoBean.f = currentTimeMillis;
                                                        a.this.a(userInfoBean, true);
                                                    }
                                                }
                                            }
                                        };
                                        StrategyBean c2 = com.tencent.bugly.crashreport.common.strategy.a.a().c();
                                        String str2 = a2.b ? c2.r : c2.t;
                                        String str3 = a2.b ? StrategyBean.b : StrategyBean.a;
                                        ak a8 = ak.a();
                                        if (this.c == 1) {
                                            z2 = true;
                                        }
                                        a8.a(1001, a7, str2, str3, r5, z2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void b() {
        am a2 = am.a();
        if (a2 != null) {
            a2.a(new Runnable() {
                public void run() {
                    try {
                        a.this.c();
                    } catch (Throwable th) {
                        an.a(th);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void a(UserInfoBean userInfoBean, boolean z) {
        if (userInfoBean != null) {
            if (!z && userInfoBean.b != 1) {
                List a2 = a(com.tencent.bugly.crashreport.common.info.a.a(this.a).e);
                if (a2 != null && a2.size() >= 20) {
                    an.a("[UserInfo] There are too many user info in local: %d", Integer.valueOf(a2.size()));
                    return;
                }
            }
            long a3 = ae.a().a("t_ui", a(userInfoBean), (ad) null, true);
            if (a3 >= 0) {
                an.c("[Database] insert %s success with ID: %d", "t_ui", Long.valueOf(a3));
                userInfoBean.a = a3;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        com.tencent.bugly.proguard.an.d("[Database] unknown id.", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0097, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x009a, code lost:
        r8.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0097 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:12:0x003c] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x009a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.tencent.bugly.crashreport.biz.UserInfoBean> a(java.lang.String r10) {
        /*
            r9 = this;
            r7 = 0
            boolean r0 = com.tencent.bugly.proguard.ap.a(r10)     // Catch:{ Throwable -> 0x00e5, all -> 0x00df }
            if (r0 == 0) goto L_0x0020
            r3 = r7
        L_0x0008:
            com.tencent.bugly.proguard.ae r0 = com.tencent.bugly.proguard.ae.a()     // Catch:{ Throwable -> 0x00e5, all -> 0x00df }
            java.lang.String r1 = "t_ui"
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 1
            android.database.Cursor r8 = r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x00e5, all -> 0x00df }
            if (r8 != 0) goto L_0x003c
            if (r8 == 0) goto L_0x001e
            r8.close()
        L_0x001e:
            r0 = r7
        L_0x001f:
            return r0
        L_0x0020:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e5, all -> 0x00df }
            r0.<init>()     // Catch:{ Throwable -> 0x00e5, all -> 0x00df }
            java.lang.String r1 = "_pc = '"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x00e5, all -> 0x00df }
            java.lang.StringBuilder r0 = r0.append(r10)     // Catch:{ Throwable -> 0x00e5, all -> 0x00df }
            java.lang.String r1 = "'"
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Throwable -> 0x00e5, all -> 0x00df }
            java.lang.String r3 = r0.toString()     // Catch:{ Throwable -> 0x00e5, all -> 0x00df }
            goto L_0x0008
        L_0x003c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            r0.<init>()     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            r6.<init>()     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
        L_0x0046:
            boolean r1 = r8.moveToNext()     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            if (r1 == 0) goto L_0x009e
            com.tencent.bugly.crashreport.biz.UserInfoBean r1 = r9.a(r8)     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            if (r1 == 0) goto L_0x0068
            r6.add(r1)     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            goto L_0x0046
        L_0x0056:
            r0 = move-exception
            r1 = r8
        L_0x0058:
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x00e2 }
            if (r2 != 0) goto L_0x0061
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x00e2 }
        L_0x0061:
            if (r1 == 0) goto L_0x0066
            r1.close()
        L_0x0066:
            r0 = r7
            goto L_0x001f
        L_0x0068:
            java.lang.String r1 = "_id"
            int r1 = r8.getColumnIndex(r1)     // Catch:{ Throwable -> 0x008c, all -> 0x0097 }
            long r2 = r8.getLong(r1)     // Catch:{ Throwable -> 0x008c, all -> 0x0097 }
            java.lang.String r1 = " or "
            java.lang.StringBuilder r1 = r0.append(r1)     // Catch:{ Throwable -> 0x008c, all -> 0x0097 }
            java.lang.String r4 = "_id"
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x008c, all -> 0x0097 }
            java.lang.String r4 = " = "
            java.lang.StringBuilder r1 = r1.append(r4)     // Catch:{ Throwable -> 0x008c, all -> 0x0097 }
            r1.append(r2)     // Catch:{ Throwable -> 0x008c, all -> 0x0097 }
            goto L_0x0046
        L_0x008c:
            r1 = move-exception
            java.lang.String r1 = "[Database] unknown id."
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            com.tencent.bugly.proguard.an.d(r1, r2)     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            goto L_0x0046
        L_0x0097:
            r0 = move-exception
        L_0x0098:
            if (r8 == 0) goto L_0x009d
            r8.close()
        L_0x009d:
            throw r0
        L_0x009e:
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            int r1 = r0.length()     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            if (r1 <= 0) goto L_0x00d7
            java.lang.String r1 = " or "
            int r1 = r1.length()     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            java.lang.String r2 = r0.substring(r1)     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            com.tencent.bugly.proguard.ae r0 = com.tencent.bugly.proguard.ae.a()     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            java.lang.String r1 = "t_ui"
            r3 = 0
            r4 = 0
            r5 = 1
            int r0 = r0.a(r1, r2, r3, r4, r5)     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            java.lang.String r1 = "[Database] deleted %s error data %d"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            r3 = 0
            java.lang.String r4 = "t_ui"
            r2[r3] = r4     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            r3 = 1
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            r2[r3] = r0     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
            com.tencent.bugly.proguard.an.d(r1, r2)     // Catch:{ Throwable -> 0x0056, all -> 0x0097 }
        L_0x00d7:
            if (r8 == 0) goto L_0x00dc
            r8.close()
        L_0x00dc:
            r0 = r6
            goto L_0x001f
        L_0x00df:
            r0 = move-exception
            r8 = r7
            goto L_0x0098
        L_0x00e2:
            r0 = move-exception
            r8 = r1
            goto L_0x0098
        L_0x00e5:
            r0 = move-exception
            r1 = r7
            goto L_0x0058
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.crashreport.biz.a.a(java.lang.String):java.util.List");
    }

    public void a(List<UserInfoBean> list) {
        if (list != null && list.size() != 0) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < list.size() && i < 50) {
                sb.append(" or ").append("_id").append(" = ").append(((UserInfoBean) list.get(i)).a);
                i++;
            }
            String sb2 = sb.toString();
            if (sb2.length() > 0) {
                sb2 = sb2.substring(" or ".length());
            }
            sb.setLength(0);
            try {
                an.c("[Database] deleted %s data %d", "t_ui", Integer.valueOf(ae.a().a("t_ui", sb2, (String[]) null, (ad) null, true)));
            } catch (Throwable th) {
                if (!an.a(th)) {
                    ThrowableExtension.printStackTrace(th);
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public ContentValues a(UserInfoBean userInfoBean) {
        if (userInfoBean == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (userInfoBean.a > 0) {
                contentValues.put("_id", Long.valueOf(userInfoBean.a));
            }
            contentValues.put("_tm", Long.valueOf(userInfoBean.e));
            contentValues.put("_ut", Long.valueOf(userInfoBean.f));
            contentValues.put("_tp", Integer.valueOf(userInfoBean.b));
            contentValues.put("_pc", userInfoBean.c);
            contentValues.put("_dt", ap.a((Parcelable) userInfoBean));
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
    public UserInfoBean a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            byte[] blob = cursor.getBlob(cursor.getColumnIndex("_dt"));
            if (blob == null) {
                return null;
            }
            long j = cursor.getLong(cursor.getColumnIndex("_id"));
            UserInfoBean userInfoBean = (UserInfoBean) ap.a(blob, UserInfoBean.CREATOR);
            if (userInfoBean == null) {
                return userInfoBean;
            }
            userInfoBean.a = j;
            return userInfoBean;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }
}
