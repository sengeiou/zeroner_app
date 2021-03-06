package com.tencent.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.app.NotificationCompat;
import com.tencent.stat.a.e;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.common.k;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class n {
    /* access modifiers changed from: private */
    public static StatLogger e = k.b();
    private static n f = null;
    Handler a = null;
    volatile int b = 0;
    DeviceInfo c = null;
    /* access modifiers changed from: private */
    public w d;
    private HashMap<String, String> g = new HashMap<>();

    private n(Context context) {
        try {
            HandlerThread handlerThread = new HandlerThread("StatStore");
            handlerThread.start();
            e.w("Launch store thread:" + handlerThread);
            this.a = new Handler(handlerThread.getLooper());
            Context applicationContext = context.getApplicationContext();
            this.d = new w(applicationContext);
            this.d.getWritableDatabase();
            this.d.getReadableDatabase();
            b(applicationContext);
            c();
            f();
            this.a.post(new o(this));
        } catch (Throwable th) {
            e.e((Object) th);
        }
    }

    public static synchronized n a(Context context) {
        n nVar;
        synchronized (n.class) {
            if (f == null) {
                f = new n(context);
            }
            nVar = f;
        }
        return nVar;
    }

    public static n b() {
        return f;
    }

    /* access modifiers changed from: private */
    public synchronized void b(int i) {
        try {
            if (this.b > 0 && i > 0) {
                e.i("Load " + Integer.toString(this.b) + " unsent events");
                ArrayList arrayList = new ArrayList();
                ArrayList<x> arrayList2 = new ArrayList<>();
                if (i == -1 || i > StatConfig.a()) {
                    i = StatConfig.a();
                }
                this.b -= i;
                c(arrayList2, i);
                e.i("Peek " + Integer.toString(arrayList2.size()) + " unsent events.");
                if (!arrayList2.isEmpty()) {
                    b((List<x>) arrayList2, 2);
                    for (x xVar : arrayList2) {
                        arrayList.add(xVar.b);
                    }
                    d.b().b(arrayList, new u(this, arrayList2, i));
                }
            }
        } catch (Throwable th) {
            e.e((Object) th);
        }
        return;
    }

    /* access modifiers changed from: private */
    public synchronized void b(e eVar, c cVar) {
        if (StatConfig.getMaxStoreEventCount() > 0) {
            try {
                this.d.getWritableDatabase().beginTransaction();
                if (this.b > StatConfig.getMaxStoreEventCount()) {
                    e.warn("Too many events stored in db.");
                    this.b -= this.d.getWritableDatabase().delete("events", "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)", null);
                }
                ContentValues contentValues = new ContentValues();
                String c2 = k.c(eVar.d());
                contentValues.put("content", c2);
                contentValues.put("send_count", "0");
                contentValues.put(NotificationCompat.CATEGORY_STATUS, Integer.toString(1));
                contentValues.put("timestamp", Long.valueOf(eVar.b()));
                if (this.d.getWritableDatabase().insert("events", null, contentValues) == -1) {
                    e.error((Object) "Failed to store event:" + c2);
                } else {
                    this.b++;
                    this.d.getWritableDatabase().setTransactionSuccessful();
                    if (cVar != null) {
                        cVar.a();
                    }
                }
                try {
                    this.d.getWritableDatabase().endTransaction();
                } catch (Throwable th) {
                }
            } catch (Throwable th2) {
            }
        }
        return;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00e1 A[SYNTHETIC, Splitter:B:36:0x00e1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(com.tencent.stat.b r14) {
        /*
            r13 = this;
            r9 = 1
            r10 = 0
            r8 = 0
            monitor-enter(r13)
            java.lang.String r11 = r14.a()     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            java.lang.String r0 = com.tencent.stat.common.k.a(r11)     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            android.content.ContentValues r12 = new android.content.ContentValues     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            r12.<init>()     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            java.lang.String r1 = "content"
            org.json.JSONObject r2 = r14.b     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            r12.put(r1, r2)     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            java.lang.String r1 = "md5sum"
            r12.put(r1, r0)     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            r14.c = r0     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            java.lang.String r0 = "version"
            int r1 = r14.d     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            r12.put(r0, r1)     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            com.tencent.stat.w r0 = r13.d     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
            java.lang.String r1 = "config"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x00e7, all -> 0x00dd }
        L_0x0044:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x00ce }
            if (r0 == 0) goto L_0x00ea
            r0 = 0
            int r0 = r1.getInt(r0)     // Catch:{ Throwable -> 0x00ce }
            int r2 = r14.a     // Catch:{ Throwable -> 0x00ce }
            if (r0 != r2) goto L_0x0044
            r0 = r9
        L_0x0054:
            if (r9 != r0) goto L_0x0099
            com.tencent.stat.w r0 = r13.d     // Catch:{ Throwable -> 0x00ce }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r2 = "config"
            java.lang.String r3 = "type=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x00ce }
            r5 = 0
            int r6 = r14.a     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ Throwable -> 0x00ce }
            r4[r5] = r6     // Catch:{ Throwable -> 0x00ce }
            int r0 = r0.update(r2, r12, r3, r4)     // Catch:{ Throwable -> 0x00ce }
            long r2 = (long) r0     // Catch:{ Throwable -> 0x00ce }
        L_0x0073:
            r4 = -1
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x00b4
            com.tencent.stat.common.StatLogger r0 = e     // Catch:{ Throwable -> 0x00ce }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ce }
            r2.<init>()     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r3 = "Failed to store cfg:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x00ce }
            java.lang.StringBuilder r2 = r2.append(r11)     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00ce }
            r0.e(r2)     // Catch:{ Throwable -> 0x00ce }
        L_0x0092:
            if (r1 == 0) goto L_0x0097
            r1.close()     // Catch:{ all -> 0x00da }
        L_0x0097:
            monitor-exit(r13)
            return
        L_0x0099:
            java.lang.String r0 = "type"
            int r2 = r14.a     // Catch:{ Throwable -> 0x00ce }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x00ce }
            r12.put(r0, r2)     // Catch:{ Throwable -> 0x00ce }
            com.tencent.stat.w r0 = r13.d     // Catch:{ Throwable -> 0x00ce }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r2 = "config"
            r3 = 0
            long r2 = r0.insert(r2, r3, r12)     // Catch:{ Throwable -> 0x00ce }
            goto L_0x0073
        L_0x00b4:
            com.tencent.stat.common.StatLogger r0 = e     // Catch:{ Throwable -> 0x00ce }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ce }
            r2.<init>()     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r3 = "Sucessed to store cfg:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x00ce }
            java.lang.StringBuilder r2 = r2.append(r11)     // Catch:{ Throwable -> 0x00ce }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00ce }
            r0.d(r2)     // Catch:{ Throwable -> 0x00ce }
            goto L_0x0092
        L_0x00ce:
            r0 = move-exception
        L_0x00cf:
            com.tencent.stat.common.StatLogger r2 = e     // Catch:{ all -> 0x00e5 }
            r2.e(r0)     // Catch:{ all -> 0x00e5 }
            if (r1 == 0) goto L_0x0097
            r1.close()     // Catch:{ all -> 0x00da }
            goto L_0x0097
        L_0x00da:
            r0 = move-exception
            monitor-exit(r13)
            throw r0
        L_0x00dd:
            r0 = move-exception
            r1 = r8
        L_0x00df:
            if (r1 == 0) goto L_0x00e4
            r1.close()     // Catch:{ all -> 0x00da }
        L_0x00e4:
            throw r0     // Catch:{ all -> 0x00da }
        L_0x00e5:
            r0 = move-exception
            goto L_0x00df
        L_0x00e7:
            r0 = move-exception
            r1 = r8
            goto L_0x00cf
        L_0x00ea:
            r0 = r10
            goto L_0x0054
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.b(com.tencent.stat.b):void");
    }

    /* access modifiers changed from: private */
    public synchronized void b(List<x> list) {
        e.i("Delete " + list.size() + " sent events in thread:" + Thread.currentThread());
        try {
            this.d.getWritableDatabase().beginTransaction();
            for (x xVar : list) {
                this.b -= this.d.getWritableDatabase().delete("events", "event_id = ?", new String[]{Long.toString(xVar.a)});
            }
            this.d.getWritableDatabase().setTransactionSuccessful();
            this.b = (int) DatabaseUtils.queryNumEntries(this.d.getReadableDatabase(), "events");
            try {
            } catch (SQLiteException e2) {
                e.e((Exception) e2);
            }
        } catch (Throwable th) {
            e.e((Object) th);
            try {
            } catch (SQLiteException e3) {
                e.e((Exception) e3);
            }
        } finally {
            try {
                this.d.getWritableDatabase().endTransaction();
            } catch (SQLiteException e4) {
                e.e((Exception) e4);
            }
        }
        return;
    }

    /* access modifiers changed from: private */
    public synchronized void b(List<x> list, int i) {
        e.i("Update " + list.size() + " sending events to status:" + i + " in thread:" + Thread.currentThread());
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotificationCompat.CATEGORY_STATUS, Integer.toString(i));
            this.d.getWritableDatabase().beginTransaction();
            for (x xVar : list) {
                if (xVar.d + 1 > StatConfig.getMaxSendRetryCount()) {
                    this.b -= this.d.getWritableDatabase().delete("events", "event_id=?", new String[]{Long.toString(xVar.a)});
                } else {
                    contentValues.put("send_count", Integer.valueOf(xVar.d + 1));
                    e.i("Update event:" + xVar.a + " for content:" + contentValues);
                    int update = this.d.getWritableDatabase().update("events", contentValues, "event_id=?", new String[]{Long.toString(xVar.a)});
                    if (update <= 0) {
                        e.e((Object) "Failed to update db, error code:" + Integer.toString(update));
                    }
                }
            }
            this.d.getWritableDatabase().setTransactionSuccessful();
            this.b = (int) DatabaseUtils.queryNumEntries(this.d.getReadableDatabase(), "events");
            try {
            } catch (SQLiteException e2) {
                e.e((Exception) e2);
            }
        } catch (Throwable th) {
            e.e((Object) th);
            try {
            } catch (SQLiteException e3) {
                e.e((Exception) e3);
            }
        } finally {
            try {
                this.d.getWritableDatabase().endTransaction();
            } catch (SQLiteException e4) {
                e.e((Exception) e4);
            }
        }
        return;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0063  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void c(java.util.List<com.tencent.stat.x> r11, int r12) {
        /*
            r10 = this;
            r9 = 0
            com.tencent.stat.w r0 = r10.d     // Catch:{ Throwable -> 0x006d, all -> 0x0060 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ Throwable -> 0x006d, all -> 0x0060 }
            java.lang.String r1 = "events"
            r2 = 0
            java.lang.String r3 = "status=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x006d, all -> 0x0060 }
            r5 = 0
            r6 = 1
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ Throwable -> 0x006d, all -> 0x0060 }
            r4[r5] = r6     // Catch:{ Throwable -> 0x006d, all -> 0x0060 }
            r5 = 0
            r6 = 0
            java.lang.String r7 = "event_id"
            java.lang.String r8 = java.lang.Integer.toString(r12)     // Catch:{ Throwable -> 0x006d, all -> 0x0060 }
            android.database.Cursor r7 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x006d, all -> 0x0060 }
        L_0x0026:
            boolean r0 = r7.moveToNext()     // Catch:{ Throwable -> 0x004d, all -> 0x0067 }
            if (r0 == 0) goto L_0x005a
            r0 = 0
            long r2 = r7.getLong(r0)     // Catch:{ Throwable -> 0x004d, all -> 0x0067 }
            r0 = 1
            java.lang.String r0 = r7.getString(r0)     // Catch:{ Throwable -> 0x004d, all -> 0x0067 }
            java.lang.String r4 = com.tencent.stat.common.k.d(r0)     // Catch:{ Throwable -> 0x004d, all -> 0x0067 }
            r0 = 2
            int r5 = r7.getInt(r0)     // Catch:{ Throwable -> 0x004d, all -> 0x0067 }
            r0 = 3
            int r6 = r7.getInt(r0)     // Catch:{ Throwable -> 0x004d, all -> 0x0067 }
            com.tencent.stat.x r1 = new com.tencent.stat.x     // Catch:{ Throwable -> 0x004d, all -> 0x0067 }
            r1.<init>(r2, r4, r5, r6)     // Catch:{ Throwable -> 0x004d, all -> 0x0067 }
            r11.add(r1)     // Catch:{ Throwable -> 0x004d, all -> 0x0067 }
            goto L_0x0026
        L_0x004d:
            r0 = move-exception
            r1 = r7
        L_0x004f:
            com.tencent.stat.common.StatLogger r2 = e     // Catch:{ all -> 0x006a }
            r2.e(r0)     // Catch:{ all -> 0x006a }
            if (r1 == 0) goto L_0x0059
            r1.close()
        L_0x0059:
            return
        L_0x005a:
            if (r7 == 0) goto L_0x0059
            r7.close()
            goto L_0x0059
        L_0x0060:
            r0 = move-exception
        L_0x0061:
            if (r9 == 0) goto L_0x0066
            r9.close()
        L_0x0066:
            throw r0
        L_0x0067:
            r0 = move-exception
            r9 = r7
            goto L_0x0061
        L_0x006a:
            r0 = move-exception
            r9 = r1
            goto L_0x0061
        L_0x006d:
            r0 = move-exception
            r1 = r9
            goto L_0x004f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.c(java.util.List, int):void");
    }

    /* access modifiers changed from: private */
    public void e() {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(1));
            this.d.getWritableDatabase().update("events", contentValues, "status=?", new String[]{Long.toString(2)});
            this.b = (int) DatabaseUtils.queryNumEntries(this.d.getReadableDatabase(), "events");
            e.i("Total " + this.b + " unsent events.");
        } catch (Throwable th) {
            e.e((Object) th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0040  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void f() {
        /*
            r9 = this;
            r8 = 0
            com.tencent.stat.w r0 = r9.d     // Catch:{ Throwable -> 0x0046, all -> 0x003c }
            android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ Throwable -> 0x0046, all -> 0x003c }
            java.lang.String r1 = "keyvalues"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0046, all -> 0x003c }
        L_0x0014:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x002a }
            if (r0 == 0) goto L_0x0036
            java.util.HashMap<java.lang.String, java.lang.String> r0 = r9.g     // Catch:{ Throwable -> 0x002a }
            r2 = 0
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Throwable -> 0x002a }
            r3 = 1
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Throwable -> 0x002a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x002a }
            goto L_0x0014
        L_0x002a:
            r0 = move-exception
        L_0x002b:
            com.tencent.stat.common.StatLogger r2 = e     // Catch:{ all -> 0x0044 }
            r2.e(r0)     // Catch:{ all -> 0x0044 }
            if (r1 == 0) goto L_0x0035
            r1.close()
        L_0x0035:
            return
        L_0x0036:
            if (r1 == 0) goto L_0x0035
            r1.close()
            goto L_0x0035
        L_0x003c:
            r0 = move-exception
            r1 = r8
        L_0x003e:
            if (r1 == 0) goto L_0x0043
            r1.close()
        L_0x0043:
            throw r0
        L_0x0044:
            r0 = move-exception
            goto L_0x003e
        L_0x0046:
            r0 = move-exception
            r1 = r8
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.f():void");
    }

    public int a() {
        return this.b;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i) {
        this.a.post(new v(this, i));
    }

    /* access modifiers changed from: 0000 */
    public void a(e eVar, c cVar) {
        if (StatConfig.isEnableStatService()) {
            try {
                if (Thread.currentThread().getId() == this.a.getLooper().getThread().getId()) {
                    b(eVar, cVar);
                } else {
                    this.a.post(new r(this, eVar, cVar));
                }
            } catch (Throwable th) {
                e.e((Object) th);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(b bVar) {
        if (bVar != null) {
            this.a.post(new s(this, bVar));
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(List<x> list) {
        try {
            if (Thread.currentThread().getId() == this.a.getLooper().getThread().getId()) {
                b(list);
            } else {
                this.a.post(new q(this, list));
            }
        } catch (SQLiteException e2) {
            e.e((Exception) e2);
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(List<x> list, int i) {
        try {
            if (Thread.currentThread().getId() == this.a.getLooper().getThread().getId()) {
                b(list, i);
            } else {
                this.a.post(new p(this, list, i));
            }
        } catch (Throwable th) {
            e.e((Object) th);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:70:0x01e1 A[SYNTHETIC, Splitter:B:70:0x01e1] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01ec A[SYNTHETIC, Splitter:B:78:0x01ec] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.tencent.stat.DeviceInfo b(android.content.Context r20) {
        /*
            r19 = this;
            monitor-enter(r19)
            r0 = r19
            com.tencent.stat.DeviceInfo r2 = r0.c     // Catch:{ all -> 0x01e5 }
            if (r2 == 0) goto L_0x000d
            r0 = r19
            com.tencent.stat.DeviceInfo r2 = r0.c     // Catch:{ all -> 0x01e5 }
        L_0x000b:
            monitor-exit(r19)
            return r2
        L_0x000d:
            r11 = 0
            r0 = r19
            com.tencent.stat.w r2 = r0.d     // Catch:{ Throwable -> 0x01d8, all -> 0x01e8 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getReadableDatabase()     // Catch:{ Throwable -> 0x01d8, all -> 0x01e8 }
            java.lang.String r3 = "user"
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            android.database.Cursor r5 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ Throwable -> 0x01d8, all -> 0x01e8 }
            r2 = 0
            java.lang.String r3 = ""
            boolean r3 = r5.moveToNext()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r3 == 0) goto L_0x0122
            r2 = 0
            java.lang.String r10 = r5.getString(r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r7 = com.tencent.stat.common.k.d(r10)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2 = 1
            int r9 = r5.getInt(r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2 = 2
            java.lang.String r3 = r5.getString(r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2 = 3
            long r12 = r5.getLong(r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r6 = 1
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r16 = 1000(0x3e8, double:4.94E-321)
            long r14 = r14 / r16
            r2 = 1
            if (r9 == r2) goto L_0x020d
            r16 = 1000(0x3e8, double:4.94E-321)
            long r12 = r12 * r16
            java.lang.String r2 = com.tencent.stat.common.k.a(r12)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r12 = 1000(0x3e8, double:4.94E-321)
            long r12 = r12 * r14
            java.lang.String r4 = com.tencent.stat.common.k.a(r12)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r2 != 0) goto L_0x020d
            r2 = 1
        L_0x0068:
            java.lang.String r4 = com.tencent.stat.common.k.r(r20)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            boolean r3 = r3.equals(r4)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r3 != 0) goto L_0x020a
            r2 = r2 | 2
            r8 = r2
        L_0x0075:
            java.lang.String r2 = ","
            java.lang.String[] r11 = r7.split(r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r4 = 0
            if (r11 == 0) goto L_0x01a4
            int r2 = r11.length     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r2 <= 0) goto L_0x01a4
            r2 = 0
            r3 = r11[r2]     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r3 == 0) goto L_0x008f
            int r2 = r3.length()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r12 = 11
            if (r2 >= r12) goto L_0x0202
        L_0x008f:
            java.lang.String r2 = com.tencent.stat.common.k.l(r20)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r2 == 0) goto L_0x01fe
            int r12 = r2.length()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r13 = 10
            if (r12 <= r13) goto L_0x01fe
            r3 = 1
        L_0x009e:
            r4 = r7
            r7 = r2
        L_0x00a0:
            if (r11 == 0) goto L_0x01b1
            int r2 = r11.length     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r12 = 2
            if (r2 < r12) goto L_0x01b1
            r2 = 1
            r2 = r11[r2]     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r4.<init>()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r11 = ","
            java.lang.StringBuilder r4 = r4.append(r11)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.StringBuilder r4 = r4.append(r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        L_0x00c1:
            com.tencent.stat.DeviceInfo r11 = new com.tencent.stat.DeviceInfo     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r11.<init>(r7, r2, r8)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r0 = r19
            r0.c = r11     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            android.content.ContentValues r2 = new android.content.ContentValues     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2.<init>()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = com.tencent.stat.common.k.c(r4)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r7 = "uid"
            r2.put(r7, r4)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = "user_type"
            java.lang.Integer r7 = java.lang.Integer.valueOf(r8)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2.put(r4, r7)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = "app_ver"
            java.lang.String r7 = com.tencent.stat.common.k.r(r20)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2.put(r4, r7)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = "ts"
            java.lang.Long r7 = java.lang.Long.valueOf(r14)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2.put(r4, r7)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r3 == 0) goto L_0x0110
            r0 = r19
            com.tencent.stat.w r3 = r0.d     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            android.database.sqlite.SQLiteDatabase r3 = r3.getWritableDatabase()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = "user"
            java.lang.String r7 = "uid=?"
            r11 = 1
            java.lang.String[] r11 = new java.lang.String[r11]     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r12 = 0
            r11[r12] = r10     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r3.update(r4, r2, r7, r11)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        L_0x0110:
            if (r8 == r9) goto L_0x01fb
            r0 = r19
            com.tencent.stat.w r3 = r0.d     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            android.database.sqlite.SQLiteDatabase r3 = r3.getWritableDatabase()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = "user"
            r7 = 0
            r3.replace(r4, r7, r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2 = r6
        L_0x0122:
            if (r2 != 0) goto L_0x0199
            java.lang.String r3 = com.tencent.stat.common.k.b(r20)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = com.tencent.stat.common.k.c(r20)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r4 == 0) goto L_0x01f8
            int r2 = r4.length()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r2 <= 0) goto L_0x01f8
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2.<init>()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r6 = ","
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        L_0x014c:
            r6 = 0
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r10 = 1000(0x3e8, double:4.94E-321)
            long r8 = r8 / r10
            java.lang.String r7 = com.tencent.stat.common.k.r(r20)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            android.content.ContentValues r10 = new android.content.ContentValues     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r10.<init>()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r2 = com.tencent.stat.common.k.c(r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r11 = "uid"
            r10.put(r11, r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r2 = "user_type"
            java.lang.Integer r11 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r10.put(r2, r11)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r2 = "app_ver"
            r10.put(r2, r7)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r2 = "ts"
            java.lang.Long r7 = java.lang.Long.valueOf(r8)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r10.put(r2, r7)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r0 = r19
            com.tencent.stat.w r2 = r0.d     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r7 = "user"
            r8 = 0
            r2.insert(r7, r8, r10)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            com.tencent.stat.DeviceInfo r2 = new com.tencent.stat.DeviceInfo     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r2.<init>(r3, r4, r6)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r0 = r19
            r0.c = r2     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
        L_0x0199:
            if (r5 == 0) goto L_0x019e
            r5.close()     // Catch:{ all -> 0x01e5 }
        L_0x019e:
            r0 = r19
            com.tencent.stat.DeviceInfo r2 = r0.c     // Catch:{ all -> 0x01e5 }
            goto L_0x000b
        L_0x01a4:
            java.lang.String r3 = com.tencent.stat.common.k.b(r20)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r4 = 1
            r7 = r3
            r18 = r4
            r4 = r3
            r3 = r18
            goto L_0x00a0
        L_0x01b1:
            java.lang.String r2 = com.tencent.stat.common.k.c(r20)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r2 == 0) goto L_0x00c1
            int r11 = r2.length()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            if (r11 <= 0) goto L_0x00c1
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r3.<init>()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.StringBuilder r3 = r3.append(r7)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = ","
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            java.lang.String r4 = r3.toString()     // Catch:{ Throwable -> 0x01f5, all -> 0x01f0 }
            r3 = 1
            goto L_0x00c1
        L_0x01d8:
            r2 = move-exception
            r3 = r11
        L_0x01da:
            com.tencent.stat.common.StatLogger r4 = e     // Catch:{ all -> 0x01f2 }
            r4.e(r2)     // Catch:{ all -> 0x01f2 }
            if (r3 == 0) goto L_0x019e
            r3.close()     // Catch:{ all -> 0x01e5 }
            goto L_0x019e
        L_0x01e5:
            r2 = move-exception
            monitor-exit(r19)
            throw r2
        L_0x01e8:
            r2 = move-exception
            r5 = r11
        L_0x01ea:
            if (r5 == 0) goto L_0x01ef
            r5.close()     // Catch:{ all -> 0x01e5 }
        L_0x01ef:
            throw r2     // Catch:{ all -> 0x01e5 }
        L_0x01f0:
            r2 = move-exception
            goto L_0x01ea
        L_0x01f2:
            r2 = move-exception
            r5 = r3
            goto L_0x01ea
        L_0x01f5:
            r2 = move-exception
            r3 = r5
            goto L_0x01da
        L_0x01f8:
            r2 = r3
            goto L_0x014c
        L_0x01fb:
            r2 = r6
            goto L_0x0122
        L_0x01fe:
            r2 = r3
            r3 = r4
            goto L_0x009e
        L_0x0202:
            r18 = r3
            r3 = r4
            r4 = r7
            r7 = r18
            goto L_0x00a0
        L_0x020a:
            r8 = r2
            goto L_0x0075
        L_0x020d:
            r2 = r9
            goto L_0x0068
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.stat.n.b(android.content.Context):com.tencent.stat.DeviceInfo");
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        this.a.post(new t(this));
    }
}
