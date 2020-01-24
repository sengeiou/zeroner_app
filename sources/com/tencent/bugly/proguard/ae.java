package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.util.List;
import java.util.Map;

/* compiled from: BUGLY */
public class ae {
    public static boolean a = false;
    private static ae b = null;
    private static af c = null;

    /* compiled from: BUGLY */
    class a extends Thread {
        private int b;
        private ad c;
        private String d;
        private ContentValues e;
        private boolean f;
        private String[] g;
        private String h;
        private String[] i;
        private String j;
        private String k;
        private String l;
        private String m;
        private String n;
        private String[] o;
        private int p;
        private String q;
        private byte[] r;

        public a(int i2, ad adVar) {
            this.b = i2;
            this.c = adVar;
        }

        public void a(String str, ContentValues contentValues) {
            this.d = str;
            this.e = contentValues;
        }

        public void a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
            this.f = z;
            this.d = str;
            this.g = strArr;
            this.h = str2;
            this.i = strArr2;
            this.j = str3;
            this.k = str4;
            this.l = str5;
            this.m = str6;
        }

        public void a(String str, String str2, String[] strArr) {
            this.d = str;
            this.n = str2;
            this.o = strArr;
        }

        public void a(int i2, String str, byte[] bArr) {
            this.p = i2;
            this.q = str;
            this.r = bArr;
        }

        public void a(int i2) {
            this.p = i2;
        }

        public void a(int i2, String str) {
            this.p = i2;
            this.q = str;
        }

        public void run() {
            switch (this.b) {
                case 1:
                    ae.this.a(this.d, this.e, this.c);
                    return;
                case 2:
                    ae.this.a(this.d, this.n, this.o, this.c);
                    return;
                case 3:
                    ae.this.a(this.f, this.d, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.c);
                    return;
                case 4:
                    ae.this.a(this.p, this.q, this.r, this.c);
                    return;
                case 5:
                    ae.this.a(this.p, this.c);
                    return;
                case 6:
                    ae.this.a(this.p, this.q, this.c);
                    return;
                default:
                    return;
            }
        }
    }

    private ae(Context context, List<com.tencent.bugly.a> list) {
        c = new af(context, list);
    }

    public static synchronized ae a(Context context, List<com.tencent.bugly.a> list) {
        ae aeVar;
        synchronized (ae.class) {
            if (b == null) {
                b = new ae(context, list);
            }
            aeVar = b;
        }
        return aeVar;
    }

    public static synchronized ae a() {
        ae aeVar;
        synchronized (ae.class) {
            aeVar = b;
        }
        return aeVar;
    }

    public long a(String str, ContentValues contentValues, ad adVar, boolean z) {
        if (z) {
            return a(str, contentValues, adVar);
        }
        a aVar = new a(1, adVar);
        aVar.a(str, contentValues);
        am.a().a(aVar);
        return 0;
    }

    public Cursor a(String str, String[] strArr, String str2, String[] strArr2, ad adVar, boolean z) {
        return a(false, str, strArr, str2, strArr2, (String) null, (String) null, (String) null, (String) null, adVar, z);
    }

    public Cursor a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, ad adVar, boolean z2) {
        if (z2) {
            return a(z, str, strArr, str2, strArr2, str3, str4, str5, str6, adVar);
        }
        a aVar = new a(3, adVar);
        aVar.a(z, str, strArr, str2, strArr2, str3, str4, str5, str6);
        am.a().a(aVar);
        return null;
    }

    public int a(String str, String str2, String[] strArr, ad adVar, boolean z) {
        if (z) {
            return a(str, str2, strArr, adVar);
        }
        a aVar = new a(2, adVar);
        aVar.a(str, str2, strArr);
        am.a().a(aVar);
        return 0;
    }

    /* access modifiers changed from: private */
    public synchronized long a(String str, ContentValues contentValues, ad adVar) {
        long j = 0;
        synchronized (this) {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = c.getWritableDatabase();
                if (!(sQLiteDatabase == null || contentValues == null)) {
                    long replace = sQLiteDatabase.replace(str, "_id", contentValues);
                    if (replace >= 0) {
                        an.c("[Database] insert %s success.", str);
                    } else {
                        an.d("[Database] replace %s error.", str);
                    }
                    j = replace;
                }
                if (adVar != null) {
                    adVar.a(Long.valueOf(j));
                }
                if (a && sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (adVar != null) {
                    adVar.a(Long.valueOf(0));
                }
                if (a && sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        }
        return j;
    }

    /* access modifiers changed from: private */
    public synchronized Cursor a(boolean z, String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6, ad adVar) {
        Cursor cursor;
        try {
            SQLiteDatabase writableDatabase = c.getWritableDatabase();
            if (writableDatabase != null) {
                cursor = writableDatabase.query(z, str, strArr, str2, strArr2, str3, str4, str5, str6);
            } else {
                cursor = null;
            }
            if (adVar != null) {
                adVar.a(cursor);
            }
        } catch (Throwable th) {
            if (adVar != null) {
                adVar.a(null);
            }
            throw th;
        }
        return cursor;
    }

    /* access modifiers changed from: private */
    public synchronized int a(String str, String str2, String[] strArr, ad adVar) {
        int i;
        i = 0;
        SQLiteDatabase sQLiteDatabase = null;
        try {
            SQLiteDatabase writableDatabase = c.getWritableDatabase();
            if (writableDatabase != null) {
                i = writableDatabase.delete(str, str2, strArr);
            }
            if (adVar != null) {
                adVar.a(Integer.valueOf(i));
            }
            if (a && writableDatabase != null) {
                writableDatabase.close();
            }
        } catch (Throwable th) {
            if (adVar != null) {
                adVar.a(Integer.valueOf(0));
            }
            if (a && sQLiteDatabase != null) {
                sQLiteDatabase.close();
            }
            throw th;
        }
        return i;
    }

    public boolean a(int i, String str, byte[] bArr, ad adVar, boolean z) {
        if (z) {
            return a(i, str, bArr, adVar);
        }
        a aVar = new a(4, adVar);
        aVar.a(i, str, bArr);
        am.a().a(aVar);
        return true;
    }

    public Map<String, byte[]> a(int i, ad adVar, boolean z) {
        if (z) {
            return a(i, adVar);
        }
        a aVar = new a(5, adVar);
        aVar.a(i);
        am.a().a(aVar);
        return null;
    }

    public boolean a(int i, String str, ad adVar, boolean z) {
        if (z) {
            return a(i, str, adVar);
        }
        a aVar = new a(6, adVar);
        aVar.a(i, str);
        am.a().a(aVar);
        return false;
    }

    /* access modifiers changed from: private */
    public boolean a(int i, String str, byte[] bArr, ad adVar) {
        boolean z = false;
        try {
            ag agVar = new ag();
            agVar.a = (long) i;
            agVar.f = str;
            agVar.e = System.currentTimeMillis();
            agVar.g = bArr;
            z = d(agVar);
            if (adVar != null) {
                adVar.a(Boolean.valueOf(z));
            }
        } catch (Throwable th) {
            if (adVar != null) {
                adVar.a(Boolean.valueOf(z));
            }
            throw th;
        }
        return z;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0030 A[Catch:{ all -> 0x004a }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0035  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0044  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.Map<java.lang.String, byte[]> a(int r6, com.tencent.bugly.proguard.ad r7) {
        /*
            r5 = this;
            r2 = 0
            java.util.List r0 = r5.c(r6)     // Catch:{ Throwable -> 0x004f, all -> 0x0040 }
            if (r0 == 0) goto L_0x0053
            java.util.HashMap r1 = new java.util.HashMap     // Catch:{ Throwable -> 0x004f, all -> 0x0040 }
            r1.<init>()     // Catch:{ Throwable -> 0x004f, all -> 0x0040 }
            java.util.Iterator r2 = r0.iterator()     // Catch:{ Throwable -> 0x0026, all -> 0x0048 }
        L_0x0010:
            boolean r0 = r2.hasNext()     // Catch:{ Throwable -> 0x0026, all -> 0x0048 }
            if (r0 == 0) goto L_0x0039
            java.lang.Object r0 = r2.next()     // Catch:{ Throwable -> 0x0026, all -> 0x0048 }
            com.tencent.bugly.proguard.ag r0 = (com.tencent.bugly.proguard.ag) r0     // Catch:{ Throwable -> 0x0026, all -> 0x0048 }
            byte[] r3 = r0.g     // Catch:{ Throwable -> 0x0026, all -> 0x0048 }
            if (r3 == 0) goto L_0x0010
            java.lang.String r0 = r0.f     // Catch:{ Throwable -> 0x0026, all -> 0x0048 }
            r1.put(r0, r3)     // Catch:{ Throwable -> 0x0026, all -> 0x0048 }
            goto L_0x0010
        L_0x0026:
            r0 = move-exception
            r4 = r0
            r0 = r1
            r1 = r4
        L_0x002a:
            boolean r2 = com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x004a }
            if (r2 != 0) goto L_0x0033
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x004a }
        L_0x0033:
            if (r7 == 0) goto L_0x0038
            r7.a(r0)
        L_0x0038:
            return r0
        L_0x0039:
            r0 = r1
        L_0x003a:
            if (r7 == 0) goto L_0x0038
            r7.a(r0)
            goto L_0x0038
        L_0x0040:
            r0 = move-exception
            r1 = r2
        L_0x0042:
            if (r7 == 0) goto L_0x0047
            r7.a(r1)
        L_0x0047:
            throw r0
        L_0x0048:
            r0 = move-exception
            goto L_0x0042
        L_0x004a:
            r1 = move-exception
            r4 = r1
            r1 = r0
            r0 = r4
            goto L_0x0042
        L_0x004f:
            r0 = move-exception
            r1 = r0
            r0 = r2
            goto L_0x002a
        L_0x0053:
            r0 = r2
            goto L_0x003a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ae.a(int, com.tencent.bugly.proguard.ad):java.util.Map");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:37:0x0063=Splitter:B:37:0x0063, B:27:0x004f=Splitter:B:27:0x004f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean a(com.tencent.bugly.proguard.ag r10) {
        /*
            r9 = this;
            r1 = 1
            r0 = 0
            monitor-enter(r9)
            if (r10 != 0) goto L_0x0007
        L_0x0005:
            monitor-exit(r9)
            return r0
        L_0x0007:
            r2 = 0
            com.tencent.bugly.proguard.af r3 = c     // Catch:{ Throwable -> 0x0059 }
            android.database.sqlite.SQLiteDatabase r2 = r3.getWritableDatabase()     // Catch:{ Throwable -> 0x0059 }
            if (r2 == 0) goto L_0x004f
            android.content.ContentValues r3 = r9.b(r10)     // Catch:{ Throwable -> 0x0059 }
            if (r3 == 0) goto L_0x004f
            java.lang.String r4 = "t_lr"
            java.lang.String r5 = "_id"
            long r4 = r2.replace(r4, r5, r3)     // Catch:{ Throwable -> 0x0059 }
            r6 = 0
            int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r3 < 0) goto L_0x0042
            java.lang.String r3 = "[Database] insert %s success."
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x0059 }
            r7 = 0
            java.lang.String r8 = "t_lr"
            r6[r7] = r8     // Catch:{ Throwable -> 0x0059 }
            com.tencent.bugly.proguard.an.c(r3, r6)     // Catch:{ Throwable -> 0x0059 }
            r10.a = r4     // Catch:{ Throwable -> 0x0059 }
            boolean r0 = a     // Catch:{ all -> 0x004c }
            if (r0 == 0) goto L_0x0040
            if (r2 == 0) goto L_0x0040
            r2.close()     // Catch:{ all -> 0x004c }
        L_0x0040:
            r0 = r1
            goto L_0x0005
        L_0x0042:
            boolean r1 = a     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x0005
            if (r2 == 0) goto L_0x0005
            r2.close()     // Catch:{ all -> 0x004c }
            goto L_0x0005
        L_0x004c:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        L_0x004f:
            boolean r1 = a     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x0005
            if (r2 == 0) goto L_0x0005
            r2.close()     // Catch:{ all -> 0x004c }
            goto L_0x0005
        L_0x0059:
            r1 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x006d }
            if (r3 != 0) goto L_0x0063
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x006d }
        L_0x0063:
            boolean r1 = a     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x0005
            if (r2 == 0) goto L_0x0005
            r2.close()     // Catch:{ all -> 0x004c }
            goto L_0x0005
        L_0x006d:
            r0 = move-exception
            boolean r1 = a     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x0077
            if (r2 == 0) goto L_0x0077
            r2.close()     // Catch:{ all -> 0x004c }
        L_0x0077:
            throw r0     // Catch:{ all -> 0x004c }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ae.a(com.tencent.bugly.proguard.ag):boolean");
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:37:0x0063=Splitter:B:37:0x0063, B:27:0x004f=Splitter:B:27:0x004f} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean d(com.tencent.bugly.proguard.ag r10) {
        /*
            r9 = this;
            r1 = 1
            r0 = 0
            monitor-enter(r9)
            if (r10 != 0) goto L_0x0007
        L_0x0005:
            monitor-exit(r9)
            return r0
        L_0x0007:
            r2 = 0
            com.tencent.bugly.proguard.af r3 = c     // Catch:{ Throwable -> 0x0059 }
            android.database.sqlite.SQLiteDatabase r2 = r3.getWritableDatabase()     // Catch:{ Throwable -> 0x0059 }
            if (r2 == 0) goto L_0x004f
            android.content.ContentValues r3 = r9.c(r10)     // Catch:{ Throwable -> 0x0059 }
            if (r3 == 0) goto L_0x004f
            java.lang.String r4 = "t_pf"
            java.lang.String r5 = "_id"
            long r4 = r2.replace(r4, r5, r3)     // Catch:{ Throwable -> 0x0059 }
            r6 = 0
            int r3 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r3 < 0) goto L_0x0042
            java.lang.String r3 = "[Database] insert %s success."
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x0059 }
            r7 = 0
            java.lang.String r8 = "t_pf"
            r6[r7] = r8     // Catch:{ Throwable -> 0x0059 }
            com.tencent.bugly.proguard.an.c(r3, r6)     // Catch:{ Throwable -> 0x0059 }
            r10.a = r4     // Catch:{ Throwable -> 0x0059 }
            boolean r0 = a     // Catch:{ all -> 0x004c }
            if (r0 == 0) goto L_0x0040
            if (r2 == 0) goto L_0x0040
            r2.close()     // Catch:{ all -> 0x004c }
        L_0x0040:
            r0 = r1
            goto L_0x0005
        L_0x0042:
            boolean r1 = a     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x0005
            if (r2 == 0) goto L_0x0005
            r2.close()     // Catch:{ all -> 0x004c }
            goto L_0x0005
        L_0x004c:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        L_0x004f:
            boolean r1 = a     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x0005
            if (r2 == 0) goto L_0x0005
            r2.close()     // Catch:{ all -> 0x004c }
            goto L_0x0005
        L_0x0059:
            r1 = move-exception
            boolean r3 = com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x006d }
            if (r3 != 0) goto L_0x0063
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x006d }
        L_0x0063:
            boolean r1 = a     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x0005
            if (r2 == 0) goto L_0x0005
            r2.close()     // Catch:{ all -> 0x004c }
            goto L_0x0005
        L_0x006d:
            r0 = move-exception
            boolean r1 = a     // Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x0077
            if (r2 == 0) goto L_0x0077
            r2.close()     // Catch:{ all -> 0x004c }
        L_0x0077:
            throw r0     // Catch:{ all -> 0x004c }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ae.d(com.tencent.bugly.proguard.ag):boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:50:0x00a7 A[SYNTHETIC, Splitter:B:50:0x00a7] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.List<com.tencent.bugly.proguard.ag> a(int r10) {
        /*
            r9 = this;
            r8 = 0
            monitor-enter(r9)
            com.tencent.bugly.proguard.af r0 = c     // Catch:{ all -> 0x00b4 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ all -> 0x00b4 }
            if (r0 == 0) goto L_0x0073
            if (r10 < 0) goto L_0x003f
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00fe, all -> 0x00fb }
            r1.<init>()     // Catch:{ Throwable -> 0x00fe, all -> 0x00fb }
            java.lang.String r2 = "_tp = "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x00fe, all -> 0x00fb }
            java.lang.StringBuilder r1 = r1.append(r10)     // Catch:{ Throwable -> 0x00fe, all -> 0x00fb }
            java.lang.String r3 = r1.toString()     // Catch:{ Throwable -> 0x00fe, all -> 0x00fb }
        L_0x0020:
            java.lang.String r1 = "t_lr"
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r2 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x00fe, all -> 0x00fb }
            if (r2 != 0) goto L_0x0041
            if (r2 == 0) goto L_0x0033
            r2.close()     // Catch:{ all -> 0x00b4 }
        L_0x0033:
            boolean r1 = a     // Catch:{ all -> 0x00b4 }
            if (r1 == 0) goto L_0x003c
            if (r0 == 0) goto L_0x003c
            r0.close()     // Catch:{ all -> 0x00b4 }
        L_0x003c:
            r0 = r8
        L_0x003d:
            monitor-exit(r9)
            return r0
        L_0x003f:
            r3 = r8
            goto L_0x0020
        L_0x0041:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x005b }
            r3.<init>()     // Catch:{ Throwable -> 0x005b }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Throwable -> 0x005b }
            r1.<init>()     // Catch:{ Throwable -> 0x005b }
        L_0x004b:
            boolean r4 = r2.moveToNext()     // Catch:{ Throwable -> 0x005b }
            if (r4 == 0) goto L_0x00b7
            com.tencent.bugly.proguard.ag r4 = r9.a(r2)     // Catch:{ Throwable -> 0x005b }
            if (r4 == 0) goto L_0x0075
            r1.add(r4)     // Catch:{ Throwable -> 0x005b }
            goto L_0x004b
        L_0x005b:
            r1 = move-exception
        L_0x005c:
            boolean r3 = com.tencent.bugly.proguard.an.a(r1)     // Catch:{ all -> 0x00a4 }
            if (r3 != 0) goto L_0x0065
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x00a4 }
        L_0x0065:
            if (r2 == 0) goto L_0x006a
            r2.close()     // Catch:{ all -> 0x00b4 }
        L_0x006a:
            boolean r1 = a     // Catch:{ all -> 0x00b4 }
            if (r1 == 0) goto L_0x0073
            if (r0 == 0) goto L_0x0073
            r0.close()     // Catch:{ all -> 0x00b4 }
        L_0x0073:
            r0 = r8
            goto L_0x003d
        L_0x0075:
            java.lang.String r4 = "_id"
            int r4 = r2.getColumnIndex(r4)     // Catch:{ Throwable -> 0x0099 }
            long r4 = r2.getLong(r4)     // Catch:{ Throwable -> 0x0099 }
            java.lang.String r6 = " or "
            java.lang.StringBuilder r6 = r3.append(r6)     // Catch:{ Throwable -> 0x0099 }
            java.lang.String r7 = "_id"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x0099 }
            java.lang.String r7 = " = "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x0099 }
            r6.append(r4)     // Catch:{ Throwable -> 0x0099 }
            goto L_0x004b
        L_0x0099:
            r4 = move-exception
            java.lang.String r4 = "[Database] unknown id."
            r5 = 0
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x005b }
            com.tencent.bugly.proguard.an.d(r4, r5)     // Catch:{ Throwable -> 0x005b }
            goto L_0x004b
        L_0x00a4:
            r1 = move-exception
        L_0x00a5:
            if (r2 == 0) goto L_0x00aa
            r2.close()     // Catch:{ all -> 0x00b4 }
        L_0x00aa:
            boolean r2 = a     // Catch:{ all -> 0x00b4 }
            if (r2 == 0) goto L_0x00b3
            if (r0 == 0) goto L_0x00b3
            r0.close()     // Catch:{ all -> 0x00b4 }
        L_0x00b3:
            throw r1     // Catch:{ all -> 0x00b4 }
        L_0x00b4:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        L_0x00b7:
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x005b }
            int r4 = r3.length()     // Catch:{ Throwable -> 0x005b }
            if (r4 <= 0) goto L_0x00ea
            java.lang.String r4 = " or "
            int r4 = r4.length()     // Catch:{ Throwable -> 0x005b }
            java.lang.String r3 = r3.substring(r4)     // Catch:{ Throwable -> 0x005b }
            java.lang.String r4 = "t_lr"
            r5 = 0
            int r3 = r0.delete(r4, r3, r5)     // Catch:{ Throwable -> 0x005b }
            java.lang.String r4 = "[Database] deleted %s illegal data %d"
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x005b }
            r6 = 0
            java.lang.String r7 = "t_lr"
            r5[r6] = r7     // Catch:{ Throwable -> 0x005b }
            r6 = 1
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x005b }
            r5[r6] = r3     // Catch:{ Throwable -> 0x005b }
            com.tencent.bugly.proguard.an.d(r4, r5)     // Catch:{ Throwable -> 0x005b }
        L_0x00ea:
            if (r2 == 0) goto L_0x00ef
            r2.close()     // Catch:{ all -> 0x00b4 }
        L_0x00ef:
            boolean r2 = a     // Catch:{ all -> 0x00b4 }
            if (r2 == 0) goto L_0x00f8
            if (r0 == 0) goto L_0x00f8
            r0.close()     // Catch:{ all -> 0x00b4 }
        L_0x00f8:
            r0 = r1
            goto L_0x003d
        L_0x00fb:
            r1 = move-exception
            r2 = r8
            goto L_0x00a5
        L_0x00fe:
            r1 = move-exception
            r2 = r8
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ae.a(int):java.util.List");
    }

    public synchronized void a(List<ag> list) {
        if (list != null) {
            if (list.size() != 0) {
                SQLiteDatabase writableDatabase = c.getWritableDatabase();
                if (writableDatabase != null) {
                    StringBuilder sb = new StringBuilder();
                    for (ag agVar : list) {
                        sb.append(" or ").append("_id").append(" = ").append(agVar.a);
                    }
                    String sb2 = sb.toString();
                    if (sb2.length() > 0) {
                        sb2 = sb2.substring(" or ".length());
                    }
                    sb.setLength(0);
                    try {
                        an.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", sb2, null)));
                        if (a) {
                            writableDatabase.close();
                        }
                    } catch (Throwable th) {
                        if (a) {
                            writableDatabase.close();
                        }
                        throw th;
                    }
                }
            }
        }
    }

    public synchronized void b(int i) {
        String str = null;
        synchronized (this) {
            SQLiteDatabase writableDatabase = c.getWritableDatabase();
            if (writableDatabase != null) {
                if (i >= 0) {
                    try {
                        str = "_tp = " + i;
                    } catch (Throwable th) {
                        if (a && writableDatabase != null) {
                            writableDatabase.close();
                        }
                        throw th;
                    }
                }
                an.c("[Database] deleted %s data %d", "t_lr", Integer.valueOf(writableDatabase.delete("t_lr", str, null)));
                if (a && writableDatabase != null) {
                    writableDatabase.close();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public ContentValues b(ag agVar) {
        if (agVar == null) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (agVar.a > 0) {
                contentValues.put("_id", Long.valueOf(agVar.a));
            }
            contentValues.put("_tp", Integer.valueOf(agVar.b));
            contentValues.put("_pc", agVar.c);
            contentValues.put("_th", agVar.d);
            contentValues.put("_tm", Long.valueOf(agVar.e));
            if (agVar.g != null) {
                contentValues.put("_dt", agVar.g);
            }
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
    public ag a(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            ag agVar = new ag();
            agVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            agVar.b = cursor.getInt(cursor.getColumnIndex("_tp"));
            agVar.c = cursor.getString(cursor.getColumnIndex("_pc"));
            agVar.d = cursor.getString(cursor.getColumnIndex("_th"));
            agVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            agVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return agVar;
        } catch (Throwable th) {
            if (an.a(th)) {
                return null;
            }
            ThrowableExtension.printStackTrace(th);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        com.tencent.bugly.proguard.an.d("[Database] unknown id.", new java.lang.Object[0]);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00a4, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00a5, code lost:
        r8 = r0;
        r0 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00a4 A[ExcHandler: all (r1v12 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:19:0x003e] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00a9 A[SYNTHETIC, Splitter:B:50:0x00a9] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.util.List<com.tencent.bugly.proguard.ag> c(int r11) {
        /*
            r10 = this;
            r8 = 0
            monitor-enter(r10)
            r1 = 0
            com.tencent.bugly.proguard.af r0 = c     // Catch:{ Throwable -> 0x012e, all -> 0x0121 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x012e, all -> 0x0121 }
            if (r0 == 0) goto L_0x0111
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0133, all -> 0x0124 }
            r1.<init>()     // Catch:{ Throwable -> 0x0133, all -> 0x0124 }
            java.lang.String r2 = "_id = "
            java.lang.StringBuilder r1 = r1.append(r2)     // Catch:{ Throwable -> 0x0133, all -> 0x0124 }
            java.lang.StringBuilder r1 = r1.append(r11)     // Catch:{ Throwable -> 0x0133, all -> 0x0124 }
            java.lang.String r3 = r1.toString()     // Catch:{ Throwable -> 0x0133, all -> 0x0124 }
            java.lang.String r1 = "t_pf"
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r2 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0133, all -> 0x0124 }
            if (r2 != 0) goto L_0x003e
            if (r2 == 0) goto L_0x0032
            r2.close()     // Catch:{ all -> 0x00b6 }
        L_0x0032:
            boolean r1 = a     // Catch:{ all -> 0x00b6 }
            if (r1 == 0) goto L_0x003b
            if (r0 == 0) goto L_0x003b
            r0.close()     // Catch:{ all -> 0x00b6 }
        L_0x003b:
            r0 = r8
        L_0x003c:
            monitor-exit(r10)
            return r0
        L_0x003e:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            r4.<init>()     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            r1.<init>()     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
        L_0x0048:
            boolean r5 = r2.moveToNext()     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            if (r5 == 0) goto L_0x00b9
            com.tencent.bugly.proguard.ag r5 = r10.b(r2)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            if (r5 == 0) goto L_0x0075
            r1.add(r5)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            goto L_0x0048
        L_0x0058:
            r1 = move-exception
            r9 = r1
            r1 = r0
            r0 = r9
        L_0x005c:
            boolean r3 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x012a }
            if (r3 != 0) goto L_0x0065
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x012a }
        L_0x0065:
            if (r2 == 0) goto L_0x006a
            r2.close()     // Catch:{ all -> 0x00b6 }
        L_0x006a:
            boolean r0 = a     // Catch:{ all -> 0x00b6 }
            if (r0 == 0) goto L_0x0073
            if (r1 == 0) goto L_0x0073
            r1.close()     // Catch:{ all -> 0x00b6 }
        L_0x0073:
            r0 = r8
            goto L_0x003c
        L_0x0075:
            java.lang.String r5 = "_tp"
            int r5 = r2.getColumnIndex(r5)     // Catch:{ Throwable -> 0x0099, all -> 0x00a4 }
            java.lang.String r5 = r2.getString(r5)     // Catch:{ Throwable -> 0x0099, all -> 0x00a4 }
            java.lang.String r6 = " or "
            java.lang.StringBuilder r6 = r4.append(r6)     // Catch:{ Throwable -> 0x0099, all -> 0x00a4 }
            java.lang.String r7 = "_tp"
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x0099, all -> 0x00a4 }
            java.lang.String r7 = " = "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ Throwable -> 0x0099, all -> 0x00a4 }
            r6.append(r5)     // Catch:{ Throwable -> 0x0099, all -> 0x00a4 }
            goto L_0x0048
        L_0x0099:
            r5 = move-exception
            java.lang.String r5 = "[Database] unknown id."
            r6 = 0
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            com.tencent.bugly.proguard.an.d(r5, r6)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            goto L_0x0048
        L_0x00a4:
            r1 = move-exception
            r8 = r0
            r0 = r1
        L_0x00a7:
            if (r2 == 0) goto L_0x00ac
            r2.close()     // Catch:{ all -> 0x00b6 }
        L_0x00ac:
            boolean r1 = a     // Catch:{ all -> 0x00b6 }
            if (r1 == 0) goto L_0x00b5
            if (r8 == 0) goto L_0x00b5
            r8.close()     // Catch:{ all -> 0x00b6 }
        L_0x00b5:
            throw r0     // Catch:{ all -> 0x00b6 }
        L_0x00b6:
            r0 = move-exception
            monitor-exit(r10)
            throw r0
        L_0x00b9:
            int r5 = r4.length()     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            if (r5 <= 0) goto L_0x0100
            java.lang.String r5 = " and "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            java.lang.String r5 = "_id"
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            java.lang.String r5 = " = "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            r4.append(r11)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            java.lang.String r4 = " or "
            int r4 = r4.length()     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            java.lang.String r3 = r3.substring(r4)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            java.lang.String r4 = "t_pf"
            r5 = 0
            int r3 = r0.delete(r4, r3, r5)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            java.lang.String r4 = "[Database] deleted %s illegal data %d."
            r5 = 2
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            r6 = 0
            java.lang.String r7 = "t_pf"
            r5[r6] = r7     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            r6 = 1
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            r5[r6] = r3     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
            com.tencent.bugly.proguard.an.d(r4, r5)     // Catch:{ Throwable -> 0x0058, all -> 0x00a4 }
        L_0x0100:
            if (r2 == 0) goto L_0x0105
            r2.close()     // Catch:{ all -> 0x00b6 }
        L_0x0105:
            boolean r2 = a     // Catch:{ all -> 0x00b6 }
            if (r2 == 0) goto L_0x010e
            if (r0 == 0) goto L_0x010e
            r0.close()     // Catch:{ all -> 0x00b6 }
        L_0x010e:
            r0 = r1
            goto L_0x003c
        L_0x0111:
            if (r8 == 0) goto L_0x0116
            r1.close()     // Catch:{ all -> 0x00b6 }
        L_0x0116:
            boolean r1 = a     // Catch:{ all -> 0x00b6 }
            if (r1 == 0) goto L_0x0073
            if (r0 == 0) goto L_0x0073
            r0.close()     // Catch:{ all -> 0x00b6 }
            goto L_0x0073
        L_0x0121:
            r0 = move-exception
            r2 = r8
            goto L_0x00a7
        L_0x0124:
            r1 = move-exception
            r2 = r8
            r8 = r0
            r0 = r1
            goto L_0x00a7
        L_0x012a:
            r0 = move-exception
            r8 = r1
            goto L_0x00a7
        L_0x012e:
            r0 = move-exception
            r1 = r8
            r2 = r8
            goto L_0x005c
        L_0x0133:
            r1 = move-exception
            r2 = r8
            r9 = r0
            r0 = r1
            r1 = r9
            goto L_0x005c
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.ae.c(int):java.util.List");
    }

    /* access modifiers changed from: private */
    public synchronized boolean a(int i, String str, ad adVar) {
        String str2;
        SQLiteDatabase sQLiteDatabase = null;
        boolean z = true;
        boolean z2 = false;
        synchronized (this) {
            try {
                sQLiteDatabase = c.getWritableDatabase();
                if (sQLiteDatabase != null) {
                    if (ap.a(str)) {
                        str2 = "_id = " + i;
                    } else {
                        str2 = "_id = " + i + " and " + "_tp" + " = \"" + str + "\"";
                    }
                    int delete = sQLiteDatabase.delete("t_pf", str2, null);
                    an.c("[Database] deleted %s data %d", "t_pf", Integer.valueOf(delete));
                    if (delete <= 0) {
                        z = false;
                    }
                    z2 = z;
                }
                if (adVar != null) {
                    adVar.a(Boolean.valueOf(z2));
                }
                if (a && sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
            } catch (Throwable th) {
                if (adVar != null) {
                    adVar.a(Boolean.valueOf(false));
                }
                if (a && sQLiteDatabase != null) {
                    sQLiteDatabase.close();
                }
                throw th;
            }
        }
        return z2;
    }

    /* access modifiers changed from: protected */
    public ContentValues c(ag agVar) {
        if (agVar == null || ap.a(agVar.f)) {
            return null;
        }
        try {
            ContentValues contentValues = new ContentValues();
            if (agVar.a > 0) {
                contentValues.put("_id", Long.valueOf(agVar.a));
            }
            contentValues.put("_tp", agVar.f);
            contentValues.put("_tm", Long.valueOf(agVar.e));
            if (agVar.g == null) {
                return contentValues;
            }
            contentValues.put("_dt", agVar.g);
            return contentValues;
        } catch (Throwable th) {
            if (!an.a(th)) {
                ThrowableExtension.printStackTrace(th);
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public ag b(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        try {
            ag agVar = new ag();
            agVar.a = cursor.getLong(cursor.getColumnIndex("_id"));
            agVar.e = cursor.getLong(cursor.getColumnIndex("_tm"));
            agVar.f = cursor.getString(cursor.getColumnIndex("_tp"));
            agVar.g = cursor.getBlob(cursor.getColumnIndex("_dt"));
            return agVar;
        } catch (Throwable th) {
            if (an.a(th)) {
                return null;
            }
            ThrowableExtension.printStackTrace(th);
            return null;
        }
    }
}
