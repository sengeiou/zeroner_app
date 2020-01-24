package com.tencent.bugly.proguard;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.tencent.bugly.crashreport.common.info.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public class ac {
    public static final long a = System.currentTimeMillis();
    private static ac b = null;
    private Context c;
    /* access modifiers changed from: private */
    public String d = a.b().e;
    /* access modifiers changed from: private */
    public Map<Integer, Map<String, ab>> e = new HashMap();
    /* access modifiers changed from: private */
    public SharedPreferences f;

    public ac(Context context) {
        this.c = context;
        this.f = context.getSharedPreferences("crashrecord", 0);
    }

    public static synchronized ac a(Context context) {
        ac acVar;
        synchronized (ac.class) {
            if (b == null) {
                b = new ac(context);
            }
            acVar = b;
        }
        return acVar;
    }

    public static synchronized ac a() {
        ac acVar;
        synchronized (ac.class) {
            acVar = b;
        }
        return acVar;
    }

    /* access modifiers changed from: private */
    public synchronized boolean b(int i) {
        boolean z;
        try {
            List<ab> c2 = c(i);
            if (c2 == null) {
                z = false;
            } else {
                long currentTimeMillis = System.currentTimeMillis();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (ab abVar : c2) {
                    if (abVar.b != null && abVar.b.equalsIgnoreCase(this.d) && abVar.d > 0) {
                        arrayList.add(abVar);
                    }
                    if (abVar.c + 86400000 < currentTimeMillis) {
                        arrayList2.add(abVar);
                    }
                }
                Collections.sort(arrayList);
                if (arrayList.size() < 2) {
                    c2.removeAll(arrayList2);
                    a(i, (T) c2);
                    z = false;
                } else if (arrayList.size() <= 0 || ((ab) arrayList.get(arrayList.size() - 1)).c + 86400000 >= currentTimeMillis) {
                    z = true;
                } else {
                    c2.clear();
                    a(i, (T) c2);
                    z = false;
                }
            }
        } catch (Exception e2) {
            an.e("isFrequentCrash failed", new Object[0]);
            z = false;
        }
        return z;
    }

    public void a(final int i, final int i2) {
        am.a().a(new Runnable() {
            public void run() {
                List<ab> list;
                ab abVar;
                try {
                    if (!TextUtils.isEmpty(ac.this.d)) {
                        List a2 = ac.this.c(i);
                        if (a2 == null) {
                            list = new ArrayList<>();
                        } else {
                            list = a2;
                        }
                        if (ac.this.e.get(Integer.valueOf(i)) == null) {
                            ac.this.e.put(Integer.valueOf(i), new HashMap());
                        }
                        if (((Map) ac.this.e.get(Integer.valueOf(i))).get(ac.this.d) == null) {
                            ab abVar2 = new ab();
                            abVar2.a = (long) i;
                            abVar2.g = ac.a;
                            abVar2.b = ac.this.d;
                            abVar2.f = a.b().o;
                            a.b().getClass();
                            abVar2.e = "2.6.5";
                            abVar2.c = System.currentTimeMillis();
                            abVar2.d = i2;
                            ((Map) ac.this.e.get(Integer.valueOf(i))).put(ac.this.d, abVar2);
                            abVar = abVar2;
                        } else {
                            ab abVar3 = (ab) ((Map) ac.this.e.get(Integer.valueOf(i))).get(ac.this.d);
                            abVar3.d = i2;
                            abVar = abVar3;
                        }
                        ArrayList arrayList = new ArrayList();
                        boolean z = false;
                        for (ab abVar4 : list) {
                            if (abVar4.g == abVar.g && abVar4.b != null && abVar4.b.equalsIgnoreCase(abVar.b)) {
                                z = true;
                                abVar4.d = abVar.d;
                            }
                            if ((abVar4.e != null && !abVar4.e.equalsIgnoreCase(abVar.e)) || ((abVar4.f != null && !abVar4.f.equalsIgnoreCase(abVar.f)) || abVar4.d <= 0)) {
                                arrayList.add(abVar4);
                            }
                        }
                        list.removeAll(arrayList);
                        if (!z) {
                            list.add(abVar);
                        }
                        ac.this.a(i, list);
                    }
                } catch (Exception e) {
                    an.e("saveCrashRecord failed", new Object[0]);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005f A[SYNTHETIC, Splitter:B:28:0x005f] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0073 A[SYNTHETIC, Splitter:B:39:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x007b A[Catch:{ Exception -> 0x0046 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized <T extends java.util.List<?>> T c(int r7) {
        /*
            r6 = this;
            r1 = 0
            monitor-enter(r6)
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x0046 }
            android.content.Context r2 = r6.c     // Catch:{ Exception -> 0x0046 }
            java.lang.String r3 = "crashrecord"
            r4 = 0
            java.io.File r2 = r2.getDir(r3, r4)     // Catch:{ Exception -> 0x0046 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0046 }
            r3.<init>()     // Catch:{ Exception -> 0x0046 }
            java.lang.StringBuilder r3 = r3.append(r7)     // Catch:{ Exception -> 0x0046 }
            java.lang.String r4 = ""
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Exception -> 0x0046 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0046 }
            r0.<init>(r2, r3)     // Catch:{ Exception -> 0x0046 }
            if (r0 == 0) goto L_0x002d
            boolean r2 = r0.exists()     // Catch:{ Exception -> 0x0046 }
            if (r2 != 0) goto L_0x0030
        L_0x002d:
            r0 = r1
        L_0x002e:
            monitor-exit(r6)
            return r0
        L_0x0030:
            java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch:{ IOException -> 0x0052, ClassNotFoundException -> 0x0066, all -> 0x0077 }
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0052, ClassNotFoundException -> 0x0066, all -> 0x0077 }
            r3.<init>(r0)     // Catch:{ IOException -> 0x0052, ClassNotFoundException -> 0x0066, all -> 0x0077 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0052, ClassNotFoundException -> 0x0066, all -> 0x0077 }
            java.lang.Object r0 = r2.readObject()     // Catch:{ IOException -> 0x0088, ClassNotFoundException -> 0x0086 }
            java.util.List r0 = (java.util.List) r0     // Catch:{ IOException -> 0x0088, ClassNotFoundException -> 0x0086 }
            if (r2 == 0) goto L_0x002e
            r2.close()     // Catch:{ Exception -> 0x0046 }
            goto L_0x002e
        L_0x0046:
            r0 = move-exception
            java.lang.String r0 = "readCrashRecord error"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0063 }
            com.tencent.bugly.proguard.an.e(r0, r2)     // Catch:{ all -> 0x0063 }
        L_0x0050:
            r0 = r1
            goto L_0x002e
        L_0x0052:
            r0 = move-exception
            r0 = r1
        L_0x0054:
            java.lang.String r2 = "open record file error"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x0081 }
            com.tencent.bugly.proguard.an.a(r2, r3)     // Catch:{ all -> 0x0081 }
            if (r0 == 0) goto L_0x0050
            r0.close()     // Catch:{ Exception -> 0x0046 }
            goto L_0x0050
        L_0x0063:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        L_0x0066:
            r0 = move-exception
            r2 = r1
        L_0x0068:
            java.lang.String r0 = "get object error"
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ all -> 0x007f }
            com.tencent.bugly.proguard.an.a(r0, r3)     // Catch:{ all -> 0x007f }
            if (r2 == 0) goto L_0x0050
            r2.close()     // Catch:{ Exception -> 0x0046 }
            goto L_0x0050
        L_0x0077:
            r0 = move-exception
            r2 = r1
        L_0x0079:
            if (r2 == 0) goto L_0x007e
            r2.close()     // Catch:{ Exception -> 0x0046 }
        L_0x007e:
            throw r0     // Catch:{ Exception -> 0x0046 }
        L_0x007f:
            r0 = move-exception
            goto L_0x0079
        L_0x0081:
            r2 = move-exception
            r5 = r2
            r2 = r0
            r0 = r5
            goto L_0x0079
        L_0x0086:
            r0 = move-exception
            goto L_0x0068
        L_0x0088:
            r0 = move-exception
            r0 = r2
            goto L_0x0054
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ac.c(int):java.util.List");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005a A[SYNTHETIC, Splitter:B:26:0x005a] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0062 A[Catch:{ Exception -> 0x003c }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized <T extends java.util.List<?>> void a(int r5, T r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r6 != 0) goto L_0x0005
        L_0x0003:
            monitor-exit(r4)
            return
        L_0x0005:
            java.io.File r0 = new java.io.File     // Catch:{ Exception -> 0x003c }
            android.content.Context r1 = r4.c     // Catch:{ Exception -> 0x003c }
            java.lang.String r2 = "crashrecord"
            r3 = 0
            java.io.File r1 = r1.getDir(r2, r3)     // Catch:{ Exception -> 0x003c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x003c }
            r2.<init>()     // Catch:{ Exception -> 0x003c }
            java.lang.StringBuilder r2 = r2.append(r5)     // Catch:{ Exception -> 0x003c }
            java.lang.String r3 = ""
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Exception -> 0x003c }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x003c }
            r0.<init>(r1, r2)     // Catch:{ Exception -> 0x003c }
            r2 = 0
            java.io.ObjectOutputStream r1 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x004a, all -> 0x005e }
            java.io.FileOutputStream r3 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x004a, all -> 0x005e }
            r3.<init>(r0)     // Catch:{ IOException -> 0x004a, all -> 0x005e }
            r1.<init>(r3)     // Catch:{ IOException -> 0x004a, all -> 0x005e }
            r1.writeObject(r6)     // Catch:{ IOException -> 0x0068 }
            if (r1 == 0) goto L_0x0003
            r1.close()     // Catch:{ Exception -> 0x003c }
            goto L_0x0003
        L_0x003c:
            r0 = move-exception
            java.lang.String r0 = "writeCrashRecord error"
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ all -> 0x0047 }
            com.tencent.bugly.proguard.an.e(r0, r1)     // Catch:{ all -> 0x0047 }
            goto L_0x0003
        L_0x0047:
            r0 = move-exception
            monitor-exit(r4)
            throw r0
        L_0x004a:
            r0 = move-exception
            r1 = r2
        L_0x004c:
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0066 }
            java.lang.String r0 = "open record file error"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0066 }
            com.tencent.bugly.proguard.an.a(r0, r2)     // Catch:{ all -> 0x0066 }
            if (r1 == 0) goto L_0x0003
            r1.close()     // Catch:{ Exception -> 0x003c }
            goto L_0x0003
        L_0x005e:
            r0 = move-exception
            r1 = r2
        L_0x0060:
            if (r1 == 0) goto L_0x0065
            r1.close()     // Catch:{ Exception -> 0x003c }
        L_0x0065:
            throw r0     // Catch:{ Exception -> 0x003c }
        L_0x0066:
            r0 = move-exception
            goto L_0x0060
        L_0x0068:
            r0 = move-exception
            goto L_0x004c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ac.a(int, java.util.List):void");
    }

    public synchronized boolean a(final int i) {
        boolean z = true;
        synchronized (this) {
            try {
                z = this.f.getBoolean(i + "_" + this.d, true);
                am.a().a(new Runnable() {
                    public void run() {
                        ac.this.f.edit().putBoolean(i + "_" + ac.this.d, !ac.this.b(i)).commit();
                    }
                });
            } catch (Exception e2) {
                an.e("canInit error", new Object[0]);
            }
        }
        return z;
    }
}
