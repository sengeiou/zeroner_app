package com.tencent.wxop.stat;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;
import com.tencent.wxop.stat.common.StatLogger;
import com.tencent.wxop.stat.common.a;
import com.tencent.wxop.stat.common.e;
import com.tencent.wxop.stat.common.l;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class au {
    /* access modifiers changed from: private */
    public static StatLogger h = l.b();
    private static Context i = null;
    private static au j = null;
    volatile int a = 0;
    a b = null;
    private bc c = null;
    private bc d = null;
    private e e = null;
    private String f = "";
    private String g = "";
    private int k = 0;
    private ConcurrentHashMap<com.tencent.wxop.stat.event.e, String> l = null;
    private boolean m = false;
    private HashMap<String, String> n = new HashMap<>();

    private au(Context context) {
        try {
            this.e = new e();
            i = context.getApplicationContext();
            this.l = new ConcurrentHashMap<>();
            this.f = l.p(context);
            this.g = "pri_" + l.p(context);
            this.c = new bc(i, this.f);
            this.d = new bc(i, this.g);
            a(true);
            a(false);
            f();
            b(i);
            d();
            j();
        } catch (Throwable th) {
            h.e(th);
        }
    }

    public static au a(Context context) {
        if (j == null) {
            synchronized (au.class) {
                if (j == null) {
                    j = new au(context);
                }
            }
        }
        return j;
    }

    private String a(List<bd> list) {
        StringBuilder sb = new StringBuilder(list.size() * 3);
        sb.append("event_id in (");
        int i2 = 0;
        int size = list.size();
        Iterator it = list.iterator();
        while (true) {
            int i3 = i2;
            if (it.hasNext()) {
                sb.append(((bd) it.next()).a);
                if (i3 != size - 1) {
                    sb.append(",");
                }
                i2 = i3 + 1;
            } else {
                sb.append(")");
                return sb.toString();
            }
        }
    }

    private synchronized void a(int i2, boolean z) {
        try {
            if (this.a > 0 && i2 > 0 && !StatServiceImpl.a()) {
                if (StatConfig.isDebugEnable()) {
                    h.i("Load " + this.a + " unsent events");
                }
                ArrayList arrayList = new ArrayList(i2);
                b(arrayList, i2, z);
                if (arrayList.size() > 0) {
                    if (StatConfig.isDebugEnable()) {
                        h.i("Peek " + arrayList.size() + " unsent events.");
                    }
                    a((List<bd>) arrayList, 2, z);
                    i.b(i).b(arrayList, new ba(this, arrayList, z));
                }
            }
        } catch (Throwable th) {
            h.e(th);
        }
        return;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00ef  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(com.tencent.wxop.stat.event.e r7, com.tencent.wxop.stat.h r8, boolean r9) {
        /*
            r6 = this;
            r1 = 0
            android.database.sqlite.SQLiteDatabase r1 = r6.c(r9)     // Catch:{ Throwable -> 0x00ca }
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00ca }
            if (r9 != 0) goto L_0x0030
            int r0 = r6.a     // Catch:{ Throwable -> 0x00ca }
            int r2 = com.tencent.wxop.stat.StatConfig.getMaxStoreEventCount()     // Catch:{ Throwable -> 0x00ca }
            if (r0 <= r2) goto L_0x0030
            com.tencent.wxop.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r2 = "Too many events stored in db."
            r0.warn(r2)     // Catch:{ Throwable -> 0x00ca }
            int r0 = r6.a     // Catch:{ Throwable -> 0x00ca }
            com.tencent.wxop.stat.bc r2 = r6.c     // Catch:{ Throwable -> 0x00ca }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r3 = "events"
            java.lang.String r4 = "event_id in (select event_id from events where timestamp in (select min(timestamp) from events) limit 1)"
            r5 = 0
            int r2 = r2.delete(r3, r4, r5)     // Catch:{ Throwable -> 0x00ca }
            int r0 = r0 - r2
            r6.a = r0     // Catch:{ Throwable -> 0x00ca }
        L_0x0030:
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch:{ Throwable -> 0x00ca }
            r0.<init>()     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r2 = r7.g()     // Catch:{ Throwable -> 0x00ca }
            boolean r3 = com.tencent.wxop.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x00ca }
            if (r3 == 0) goto L_0x0054
            com.tencent.wxop.stat.common.StatLogger r3 = h     // Catch:{ Throwable -> 0x00ca }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r5 = "insert 1 event, content:"
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00ca }
            java.lang.StringBuilder r4 = r4.append(r2)     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x00ca }
            r3.i(r4)     // Catch:{ Throwable -> 0x00ca }
        L_0x0054:
            java.lang.String r2 = com.tencent.wxop.stat.common.r.b(r2)     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r3 = "content"
            r0.put(r3, r2)     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r2 = "send_count"
            java.lang.String r3 = "0"
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r2 = "status"
            r3 = 1
            java.lang.String r3 = java.lang.Integer.toString(r3)     // Catch:{ Throwable -> 0x00ca }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r2 = "timestamp"
            long r4 = r7.c()     // Catch:{ Throwable -> 0x00ca }
            java.lang.Long r3 = java.lang.Long.valueOf(r4)     // Catch:{ Throwable -> 0x00ca }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x00ca }
            java.lang.String r2 = "events"
            r3 = 0
            long r2 = r1.insert(r2, r3, r0)     // Catch:{ Throwable -> 0x00ca }
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00ca }
            if (r1 == 0) goto L_0x0109
            r1.endTransaction()     // Catch:{ Throwable -> 0x00c2 }
            r0 = r2
        L_0x0091:
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x00ef
            int r0 = r6.a
            int r0 = r0 + 1
            r6.a = r0
            boolean r0 = com.tencent.wxop.stat.StatConfig.isDebugEnable()
            if (r0 == 0) goto L_0x00bc
            com.tencent.wxop.stat.common.StatLogger r0 = h
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "directStoreEvent insert event to db, event:"
            r1.<init>(r2)
            java.lang.String r2 = r7.g()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.d(r1)
        L_0x00bc:
            if (r8 == 0) goto L_0x00c1
            r8.a()
        L_0x00c1:
            return
        L_0x00c2:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r1 = h
            r1.e(r0)
            r0 = r2
            goto L_0x0091
        L_0x00ca:
            r0 = move-exception
            r2 = -1
            com.tencent.wxop.stat.common.StatLogger r4 = h     // Catch:{ all -> 0x00e1 }
            r4.e(r0)     // Catch:{ all -> 0x00e1 }
            if (r1 == 0) goto L_0x0109
            r1.endTransaction()     // Catch:{ Throwable -> 0x00d9 }
            r0 = r2
            goto L_0x0091
        L_0x00d9:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r1 = h
            r1.e(r0)
            r0 = r2
            goto L_0x0091
        L_0x00e1:
            r0 = move-exception
            if (r1 == 0) goto L_0x00e7
            r1.endTransaction()     // Catch:{ Throwable -> 0x00e8 }
        L_0x00e7:
            throw r0
        L_0x00e8:
            r1 = move-exception
            com.tencent.wxop.stat.common.StatLogger r2 = h
            r2.e(r1)
            goto L_0x00e7
        L_0x00ef:
            com.tencent.wxop.stat.common.StatLogger r0 = h
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Failed to store event:"
            r1.<init>(r2)
            java.lang.String r2 = r7.g()
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.error(r1)
            goto L_0x00c1
        L_0x0109:
            r0 = r2
            goto L_0x0091
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.a(com.tencent.wxop.stat.event.e, com.tencent.wxop.stat.h, boolean):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00ca A[SYNTHETIC, Splitter:B:40:0x00ca] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00db A[SYNTHETIC, Splitter:B:48:0x00db] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.util.List<com.tencent.wxop.stat.bd> r7, int r8, boolean r9) {
        /*
            r6 = this;
            r2 = 0
            monitor-enter(r6)
            int r0 = r7.size()     // Catch:{ all -> 0x0082 }
            if (r0 != 0) goto L_0x000a
        L_0x0008:
            monitor-exit(r6)
            return
        L_0x000a:
            int r3 = r6.b(r9)     // Catch:{ all -> 0x0082 }
            android.database.sqlite.SQLiteDatabase r1 = r6.c(r9)     // Catch:{ Throwable -> 0x00e8, all -> 0x00d7 }
            r0 = 2
            if (r8 != r0) goto L_0x0085
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r3 = "update events set status="
            r0.<init>(r3)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r3 = ", send_count=send_count+1  where "
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r3 = r6.a(r7)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00c2 }
        L_0x0034:
            boolean r3 = com.tencent.wxop.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x00c2 }
            if (r3 == 0) goto L_0x004f
            com.tencent.wxop.stat.common.StatLogger r3 = h     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r5 = "update sql:"
            r4.<init>(r5)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r4 = r4.append(r0)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x00c2 }
            r3.i(r4)     // Catch:{ Throwable -> 0x00c2 }
        L_0x004f:
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00c2 }
            r1.execSQL(r0)     // Catch:{ Throwable -> 0x00c2 }
            if (r2 == 0) goto L_0x0072
            com.tencent.wxop.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r4 = "update for delete sql:"
            r3.<init>(r4)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r3 = r3.append(r2)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00c2 }
            r0.i(r3)     // Catch:{ Throwable -> 0x00c2 }
            r1.execSQL(r2)     // Catch:{ Throwable -> 0x00c2 }
            r6.f()     // Catch:{ Throwable -> 0x00c2 }
        L_0x0072:
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00c2 }
            if (r1 == 0) goto L_0x0008
            r1.endTransaction()     // Catch:{ Throwable -> 0x007b }
            goto L_0x0008
        L_0x007b:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x0082 }
            r1.e(r0)     // Catch:{ all -> 0x0082 }
            goto L_0x0008
        L_0x0082:
            r0 = move-exception
            monitor-exit(r6)
            throw r0
        L_0x0085:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r4 = "update events set status="
            r0.<init>(r4)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r0 = r0.append(r8)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r4 = " where "
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r4 = r6.a(r7)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r0 = r0.append(r4)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x00c2 }
            int r4 = r6.k     // Catch:{ Throwable -> 0x00c2 }
            int r4 = r4 % 3
            if (r4 != 0) goto L_0x00ba
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r4 = "delete from events where send_count>"
            r2.<init>(r4)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x00c2 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00c2 }
        L_0x00ba:
            int r3 = r6.k     // Catch:{ Throwable -> 0x00c2 }
            int r3 = r3 + 1
            r6.k = r3     // Catch:{ Throwable -> 0x00c2 }
            goto L_0x0034
        L_0x00c2:
            r0 = move-exception
        L_0x00c3:
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00e6 }
            r2.e(r0)     // Catch:{ all -> 0x00e6 }
            if (r1 == 0) goto L_0x0008
            r1.endTransaction()     // Catch:{ Throwable -> 0x00cf }
            goto L_0x0008
        L_0x00cf:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x0082 }
            r1.e(r0)     // Catch:{ all -> 0x0082 }
            goto L_0x0008
        L_0x00d7:
            r0 = move-exception
            r1 = r2
        L_0x00d9:
            if (r1 == 0) goto L_0x00de
            r1.endTransaction()     // Catch:{ Throwable -> 0x00df }
        L_0x00de:
            throw r0     // Catch:{ all -> 0x0082 }
        L_0x00df:
            r1 = move-exception
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x0082 }
            r2.e(r1)     // Catch:{ all -> 0x0082 }
            goto L_0x00de
        L_0x00e6:
            r0 = move-exception
            goto L_0x00d9
        L_0x00e8:
            r0 = move-exception
            r1 = r2
            goto L_0x00c3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.a(java.util.List, int, boolean):void");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00cf, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        h.e(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00f6, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00f7, code lost:
        h.e(r1);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:25:0x00ca, B:44:0x00f2] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.util.List<com.tencent.wxop.stat.bd> r9, boolean r10) {
        /*
            r8 = this;
            r1 = 0
            monitor-enter(r8)
            int r0 = r9.size()     // Catch:{ all -> 0x00d7 }
            if (r0 != 0) goto L_0x000a
        L_0x0008:
            monitor-exit(r8)
            return
        L_0x000a:
            boolean r0 = com.tencent.wxop.stat.StatConfig.isDebugEnable()     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x0034
            com.tencent.wxop.stat.common.StatLogger r0 = h     // Catch:{ all -> 0x00d7 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d7 }
            java.lang.String r3 = "Delete "
            r2.<init>(r3)     // Catch:{ all -> 0x00d7 }
            int r3 = r9.size()     // Catch:{ all -> 0x00d7 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00d7 }
            java.lang.String r3 = " events, important:"
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x00d7 }
            java.lang.StringBuilder r2 = r2.append(r10)     // Catch:{ all -> 0x00d7 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00d7 }
            r0.i(r2)     // Catch:{ all -> 0x00d7 }
        L_0x0034:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d7 }
            int r0 = r9.size()     // Catch:{ all -> 0x00d7 }
            int r0 = r0 * 3
            r3.<init>(r0)     // Catch:{ all -> 0x00d7 }
            java.lang.String r0 = "event_id in ("
            r3.append(r0)     // Catch:{ all -> 0x00d7 }
            r0 = 0
            int r4 = r9.size()     // Catch:{ all -> 0x00d7 }
            java.util.Iterator r5 = r9.iterator()     // Catch:{ all -> 0x00d7 }
            r2 = r0
        L_0x004f:
            boolean r0 = r5.hasNext()     // Catch:{ all -> 0x00d7 }
            if (r0 == 0) goto L_0x006e
            java.lang.Object r0 = r5.next()     // Catch:{ all -> 0x00d7 }
            com.tencent.wxop.stat.bd r0 = (com.tencent.wxop.stat.bd) r0     // Catch:{ all -> 0x00d7 }
            long r6 = r0.a     // Catch:{ all -> 0x00d7 }
            r3.append(r6)     // Catch:{ all -> 0x00d7 }
            int r0 = r4 + -1
            if (r2 == r0) goto L_0x006a
            java.lang.String r0 = ","
            r3.append(r0)     // Catch:{ all -> 0x00d7 }
        L_0x006a:
            int r0 = r2 + 1
            r2 = r0
            goto L_0x004f
        L_0x006e:
            java.lang.String r0 = ")"
            r3.append(r0)     // Catch:{ all -> 0x00d7 }
            android.database.sqlite.SQLiteDatabase r1 = r8.c(r10)     // Catch:{ Throwable -> 0x00da }
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00da }
            java.lang.String r0 = "events"
            java.lang.String r2 = r3.toString()     // Catch:{ Throwable -> 0x00da }
            r5 = 0
            int r0 = r1.delete(r0, r2, r5)     // Catch:{ Throwable -> 0x00da }
            boolean r2 = com.tencent.wxop.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x00da }
            if (r2 == 0) goto L_0x00bc
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ Throwable -> 0x00da }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00da }
            java.lang.String r6 = "delete "
            r5.<init>(r6)     // Catch:{ Throwable -> 0x00da }
            java.lang.StringBuilder r4 = r5.append(r4)     // Catch:{ Throwable -> 0x00da }
            java.lang.String r5 = " event "
            java.lang.StringBuilder r4 = r4.append(r5)     // Catch:{ Throwable -> 0x00da }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00da }
            java.lang.StringBuilder r3 = r4.append(r3)     // Catch:{ Throwable -> 0x00da }
            java.lang.String r4 = ", success delete:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ Throwable -> 0x00da }
            java.lang.StringBuilder r3 = r3.append(r0)     // Catch:{ Throwable -> 0x00da }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x00da }
            r2.i(r3)     // Catch:{ Throwable -> 0x00da }
        L_0x00bc:
            int r2 = r8.a     // Catch:{ Throwable -> 0x00da }
            int r0 = r2 - r0
            r8.a = r0     // Catch:{ Throwable -> 0x00da }
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00da }
            r8.f()     // Catch:{ Throwable -> 0x00da }
            if (r1 == 0) goto L_0x0008
            r1.endTransaction()     // Catch:{ Throwable -> 0x00cf }
            goto L_0x0008
        L_0x00cf:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x00d7 }
            r1.e(r0)     // Catch:{ all -> 0x00d7 }
            goto L_0x0008
        L_0x00d7:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        L_0x00da:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00ef }
            r2.e(r0)     // Catch:{ all -> 0x00ef }
            if (r1 == 0) goto L_0x0008
            r1.endTransaction()     // Catch:{ Throwable -> 0x00e7 }
            goto L_0x0008
        L_0x00e7:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x00d7 }
            r1.e(r0)     // Catch:{ all -> 0x00d7 }
            goto L_0x0008
        L_0x00ef:
            r0 = move-exception
            if (r1 == 0) goto L_0x00f5
            r1.endTransaction()     // Catch:{ Throwable -> 0x00f6 }
        L_0x00f5:
            throw r0     // Catch:{ all -> 0x00d7 }
        L_0x00f6:
            r1 = move-exception
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00d7 }
            r2.e(r1)     // Catch:{ all -> 0x00d7 }
            goto L_0x00f5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.a(java.util.List, boolean):void");
    }

    private void a(boolean z) {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            SQLiteDatabase c2 = c(z);
            c2.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotificationCompat.CATEGORY_STATUS, Integer.valueOf(1));
            int update = c2.update("events", contentValues, "status=?", new String[]{Long.toString(2)});
            if (StatConfig.isDebugEnable()) {
                h.i("update " + update + " unsent events.");
            }
            c2.setTransactionSuccessful();
            if (c2 != null) {
                try {
                    c2.endTransaction();
                } catch (Throwable th) {
                    h.e(th);
                }
            }
        } catch (Throwable th2) {
            h.e(th2);
        }
    }

    private int b(boolean z) {
        return !z ? StatConfig.getMaxSendRetryCount() : StatConfig.getMaxImportantDataSendRetryCount();
    }

    public static au b() {
        return j;
    }

    /* access modifiers changed from: private */
    public void b(int i2, boolean z) {
        int i3 = i2 == -1 ? !z ? g() : h() : i2;
        if (i3 > 0) {
            int sendPeriodMinutes = StatConfig.getSendPeriodMinutes() * 60 * StatConfig.getNumEventsCommitPerSec();
            if (i3 > sendPeriodMinutes && sendPeriodMinutes > 0) {
                i3 = sendPeriodMinutes;
            }
            int a2 = StatConfig.a();
            int i4 = i3 / a2;
            int i5 = i3 % a2;
            if (StatConfig.isDebugEnable()) {
                h.i("sentStoreEventsByDb sendNumbers=" + i3 + ",important=" + z + ",maxSendNumPerFor1Period=" + sendPeriodMinutes + ",maxCount=" + i4 + ",restNumbers=" + i5);
            }
            for (int i6 = 0; i6 < i4; i6++) {
                a(a2, z);
            }
            if (i5 > 0) {
                a(i5, z);
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void b(com.tencent.wxop.stat.event.e eVar, h hVar, boolean z, boolean z2) {
        if (StatConfig.getMaxStoreEventCount() > 0) {
            if (StatConfig.m <= 0 || z || z2) {
                a(eVar, hVar, z);
            } else if (StatConfig.m > 0) {
                if (StatConfig.isDebugEnable()) {
                    h.i("cacheEventsInMemory.size():" + this.l.size() + ",numEventsCachedInMemory:" + StatConfig.m + ",numStoredEvents:" + this.a);
                    h.i("cache event:" + eVar.g());
                }
                this.l.put(eVar, "");
                if (this.l.size() >= StatConfig.m) {
                    i();
                }
                if (hVar != null) {
                    if (this.l.size() > 0) {
                        i();
                    }
                    hVar.a();
                }
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00fc A[SYNTHETIC, Splitter:B:40:0x00fc] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void b(com.tencent.wxop.stat.f r14) {
        /*
            r13 = this;
            r9 = 1
            r10 = 0
            r8 = 0
            monitor-enter(r13)
            java.lang.String r11 = r14.a()     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            java.lang.String r0 = com.tencent.wxop.stat.common.l.a(r11)     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            android.content.ContentValues r12 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            r12.<init>()     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            java.lang.String r1 = "content"
            org.json.JSONObject r2 = r14.b     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            r12.put(r1, r2)     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            java.lang.String r1 = "md5sum"
            r12.put(r1, r0)     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            r14.c = r0     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            java.lang.String r0 = "version"
            int r1 = r14.d     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            r12.put(r0, r1)     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            com.tencent.wxop.stat.bc r0 = r13.c     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
            java.lang.String r1 = "config"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0110, all -> 0x00f8 }
        L_0x0044:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x00e1 }
            if (r0 == 0) goto L_0x0115
            r0 = 0
            int r0 = r1.getInt(r0)     // Catch:{ Throwable -> 0x00e1 }
            int r2 = r14.a     // Catch:{ Throwable -> 0x00e1 }
            if (r0 != r2) goto L_0x0044
            r0 = r9
        L_0x0054:
            com.tencent.wxop.stat.bc r2 = r13.c     // Catch:{ Throwable -> 0x00e1 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x00e1 }
            r2.beginTransaction()     // Catch:{ Throwable -> 0x00e1 }
            if (r9 != r0) goto L_0x00b0
            com.tencent.wxop.stat.bc r0 = r13.c     // Catch:{ Throwable -> 0x00e1 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "config"
            java.lang.String r3 = "type=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x00e1 }
            r5 = 0
            int r6 = r14.a     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ Throwable -> 0x00e1 }
            r4[r5] = r6     // Catch:{ Throwable -> 0x00e1 }
            int r0 = r0.update(r2, r12, r3, r4)     // Catch:{ Throwable -> 0x00e1 }
            long r2 = (long) r0     // Catch:{ Throwable -> 0x00e1 }
        L_0x007c:
            r4 = -1
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x00cb
            com.tencent.wxop.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x00e1 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r3 = "Failed to store cfg:"
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.StringBuilder r2 = r2.append(r11)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00e1 }
            r0.e(r2)     // Catch:{ Throwable -> 0x00e1 }
        L_0x0097:
            com.tencent.wxop.stat.bc r0 = r13.c     // Catch:{ Throwable -> 0x00e1 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00e1 }
            r0.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00e1 }
            if (r1 == 0) goto L_0x00a5
            r1.close()     // Catch:{ all -> 0x0109 }
        L_0x00a5:
            com.tencent.wxop.stat.bc r0 = r13.c     // Catch:{ Exception -> 0x0113 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Exception -> 0x0113 }
            r0.endTransaction()     // Catch:{ Exception -> 0x0113 }
        L_0x00ae:
            monitor-exit(r13)
            return
        L_0x00b0:
            java.lang.String r0 = "type"
            int r2 = r14.a     // Catch:{ Throwable -> 0x00e1 }
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)     // Catch:{ Throwable -> 0x00e1 }
            r12.put(r0, r2)     // Catch:{ Throwable -> 0x00e1 }
            com.tencent.wxop.stat.bc r0 = r13.c     // Catch:{ Throwable -> 0x00e1 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = "config"
            r3 = 0
            long r2 = r0.insert(r2, r3, r12)     // Catch:{ Throwable -> 0x00e1 }
            goto L_0x007c
        L_0x00cb:
            com.tencent.wxop.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x00e1 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r3 = "Sucessed to store cfg:"
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.StringBuilder r2 = r2.append(r11)     // Catch:{ Throwable -> 0x00e1 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00e1 }
            r0.d(r2)     // Catch:{ Throwable -> 0x00e1 }
            goto L_0x0097
        L_0x00e1:
            r0 = move-exception
        L_0x00e2:
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x010e }
            r2.e(r0)     // Catch:{ all -> 0x010e }
            if (r1 == 0) goto L_0x00ec
            r1.close()     // Catch:{ all -> 0x0109 }
        L_0x00ec:
            com.tencent.wxop.stat.bc r0 = r13.c     // Catch:{ Exception -> 0x00f6 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getWritableDatabase()     // Catch:{ Exception -> 0x00f6 }
            r0.endTransaction()     // Catch:{ Exception -> 0x00f6 }
            goto L_0x00ae
        L_0x00f6:
            r0 = move-exception
            goto L_0x00ae
        L_0x00f8:
            r0 = move-exception
            r1 = r8
        L_0x00fa:
            if (r1 == 0) goto L_0x00ff
            r1.close()     // Catch:{ all -> 0x0109 }
        L_0x00ff:
            com.tencent.wxop.stat.bc r1 = r13.c     // Catch:{ Exception -> 0x010c }
            android.database.sqlite.SQLiteDatabase r1 = r1.getWritableDatabase()     // Catch:{ Exception -> 0x010c }
            r1.endTransaction()     // Catch:{ Exception -> 0x010c }
        L_0x0108:
            throw r0     // Catch:{ all -> 0x0109 }
        L_0x0109:
            r0 = move-exception
            monitor-exit(r13)
            throw r0
        L_0x010c:
            r1 = move-exception
            goto L_0x0108
        L_0x010e:
            r0 = move-exception
            goto L_0x00fa
        L_0x0110:
            r0 = move-exception
            r1 = r8
            goto L_0x00e2
        L_0x0113:
            r0 = move-exception
            goto L_0x00ae
        L_0x0115:
            r0 = r10
            goto L_0x0054
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.b(com.tencent.wxop.stat.f):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x009a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(java.util.List<com.tencent.wxop.stat.bd> r11, int r12, boolean r13) {
        /*
            r10 = this;
            r9 = 0
            android.database.sqlite.SQLiteDatabase r0 = r10.d(r13)     // Catch:{ Throwable -> 0x00a3, all -> 0x0096 }
            java.lang.String r1 = "events"
            r2 = 0
            java.lang.String r3 = "status=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x00a3, all -> 0x0096 }
            r5 = 0
            r6 = 1
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ Throwable -> 0x00a3, all -> 0x0096 }
            r4[r5] = r6     // Catch:{ Throwable -> 0x00a3, all -> 0x0096 }
            r5 = 0
            r6 = 0
            r7 = 0
            java.lang.String r8 = java.lang.Integer.toString(r12)     // Catch:{ Throwable -> 0x00a3, all -> 0x0096 }
            android.database.Cursor r7 = r0.query(r1, r2, r3, r4, r5, r6, r7, r8)     // Catch:{ Throwable -> 0x00a3, all -> 0x0096 }
        L_0x0022:
            boolean r0 = r7.moveToNext()     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            if (r0 == 0) goto L_0x0090
            r0 = 0
            long r2 = r7.getLong(r0)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            r0 = 1
            java.lang.String r4 = r7.getString(r0)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            boolean r0 = com.tencent.wxop.stat.StatConfig.g     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            if (r0 != 0) goto L_0x003a
            java.lang.String r4 = com.tencent.wxop.stat.common.r.a(r4)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
        L_0x003a:
            r0 = 2
            int r5 = r7.getInt(r0)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            r0 = 3
            int r6 = r7.getInt(r0)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            com.tencent.wxop.stat.bd r1 = new com.tencent.wxop.stat.bd     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            r1.<init>(r2, r4, r5, r6)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            boolean r0 = com.tencent.wxop.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            if (r0 == 0) goto L_0x007f
            com.tencent.wxop.stat.common.StatLogger r0 = h     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            java.lang.String r5 = "peek event, id="
            r4.<init>(r5)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            java.lang.StringBuilder r2 = r4.append(r2)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            java.lang.String r3 = ",send_count="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            java.lang.String r3 = ",timestamp="
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            r3 = 4
            long r4 = r7.getLong(r3)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            r0.i(r2)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
        L_0x007f:
            r11.add(r1)     // Catch:{ Throwable -> 0x0083, all -> 0x009e }
            goto L_0x0022
        L_0x0083:
            r0 = move-exception
            r1 = r7
        L_0x0085:
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x00a0 }
            r2.e(r0)     // Catch:{ all -> 0x00a0 }
            if (r1 == 0) goto L_0x008f
            r1.close()
        L_0x008f:
            return
        L_0x0090:
            if (r7 == 0) goto L_0x008f
            r7.close()
            goto L_0x008f
        L_0x0096:
            r0 = move-exception
            r7 = r9
        L_0x0098:
            if (r7 == 0) goto L_0x009d
            r7.close()
        L_0x009d:
            throw r0
        L_0x009e:
            r0 = move-exception
            goto L_0x0098
        L_0x00a0:
            r0 = move-exception
            r7 = r1
            goto L_0x0098
        L_0x00a3:
            r0 = move-exception
            r1 = r9
            goto L_0x0085
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.b(java.util.List, int, boolean):void");
    }

    private SQLiteDatabase c(boolean z) {
        return !z ? this.c.getWritableDatabase() : this.d.getWritableDatabase();
    }

    private SQLiteDatabase d(boolean z) {
        return !z ? this.c.getReadableDatabase() : this.d.getReadableDatabase();
    }

    private void f() {
        this.a = g() + h();
    }

    private int g() {
        return (int) DatabaseUtils.queryNumEntries(this.c.getReadableDatabase(), "events");
    }

    private int h() {
        return (int) DatabaseUtils.queryNumEntries(this.d.getReadableDatabase(), "events");
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x012f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:?, code lost:
        h.e(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0147, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x0148, code lost:
        h.e(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:?, code lost:
        return;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:40:0x0128, B:49:0x0140] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void i() {
        /*
            r9 = this;
            r1 = 0
            boolean r0 = r9.m
            if (r0 == 0) goto L_0x0006
        L_0x0005:
            return
        L_0x0006:
            java.util.concurrent.ConcurrentHashMap<com.tencent.wxop.stat.event.e, java.lang.String> r2 = r9.l
            monitor-enter(r2)
            java.util.concurrent.ConcurrentHashMap<com.tencent.wxop.stat.event.e, java.lang.String> r0 = r9.l     // Catch:{ all -> 0x0013 }
            int r0 = r0.size()     // Catch:{ all -> 0x0013 }
            if (r0 != 0) goto L_0x0016
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            goto L_0x0005
        L_0x0013:
            r0 = move-exception
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            throw r0
        L_0x0016:
            r0 = 1
            r9.m = r0     // Catch:{ all -> 0x0013 }
            boolean r0 = com.tencent.wxop.stat.StatConfig.isDebugEnable()     // Catch:{ all -> 0x0013 }
            if (r0 == 0) goto L_0x0054
            com.tencent.wxop.stat.common.StatLogger r0 = h     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0013 }
            java.lang.String r4 = "insert "
            r3.<init>(r4)     // Catch:{ all -> 0x0013 }
            java.util.concurrent.ConcurrentHashMap<com.tencent.wxop.stat.event.e, java.lang.String> r4 = r9.l     // Catch:{ all -> 0x0013 }
            int r4 = r4.size()     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            java.lang.String r4 = " events ,numEventsCachedInMemory:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            int r4 = com.tencent.wxop.stat.StatConfig.m     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            java.lang.String r4 = ",numStoredEvents:"
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            int r4 = r9.a     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0013 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0013 }
            r0.i(r3)     // Catch:{ all -> 0x0013 }
        L_0x0054:
            com.tencent.wxop.stat.bc r0 = r9.c     // Catch:{ Throwable -> 0x00d4 }
            android.database.sqlite.SQLiteDatabase r1 = r0.getWritableDatabase()     // Catch:{ Throwable -> 0x00d4 }
            r1.beginTransaction()     // Catch:{ Throwable -> 0x00d4 }
            java.util.concurrent.ConcurrentHashMap<com.tencent.wxop.stat.event.e, java.lang.String> r0 = r9.l     // Catch:{ Throwable -> 0x00d4 }
            java.util.Set r0 = r0.entrySet()     // Catch:{ Throwable -> 0x00d4 }
            java.util.Iterator r3 = r0.iterator()     // Catch:{ Throwable -> 0x00d4 }
        L_0x0067:
            boolean r0 = r3.hasNext()     // Catch:{ Throwable -> 0x00d4 }
            if (r0 == 0) goto L_0x0123
            java.lang.Object r0 = r3.next()     // Catch:{ Throwable -> 0x00d4 }
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0     // Catch:{ Throwable -> 0x00d4 }
            java.lang.Object r0 = r0.getKey()     // Catch:{ Throwable -> 0x00d4 }
            com.tencent.wxop.stat.event.e r0 = (com.tencent.wxop.stat.event.e) r0     // Catch:{ Throwable -> 0x00d4 }
            android.content.ContentValues r4 = new android.content.ContentValues     // Catch:{ Throwable -> 0x00d4 }
            r4.<init>()     // Catch:{ Throwable -> 0x00d4 }
            java.lang.String r5 = r0.g()     // Catch:{ Throwable -> 0x00d4 }
            boolean r6 = com.tencent.wxop.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x00d4 }
            if (r6 == 0) goto L_0x009d
            com.tencent.wxop.stat.common.StatLogger r6 = h     // Catch:{ Throwable -> 0x00d4 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00d4 }
            java.lang.String r8 = "insert content:"
            r7.<init>(r8)     // Catch:{ Throwable -> 0x00d4 }
            java.lang.StringBuilder r7 = r7.append(r5)     // Catch:{ Throwable -> 0x00d4 }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00d4 }
            r6.i(r7)     // Catch:{ Throwable -> 0x00d4 }
        L_0x009d:
            java.lang.String r5 = com.tencent.wxop.stat.common.r.b(r5)     // Catch:{ Throwable -> 0x00d4 }
            java.lang.String r6 = "content"
            r4.put(r6, r5)     // Catch:{ Throwable -> 0x00d4 }
            java.lang.String r5 = "send_count"
            java.lang.String r6 = "0"
            r4.put(r5, r6)     // Catch:{ Throwable -> 0x00d4 }
            java.lang.String r5 = "status"
            r6 = 1
            java.lang.String r6 = java.lang.Integer.toString(r6)     // Catch:{ Throwable -> 0x00d4 }
            r4.put(r5, r6)     // Catch:{ Throwable -> 0x00d4 }
            java.lang.String r5 = "timestamp"
            long r6 = r0.c()     // Catch:{ Throwable -> 0x00d4 }
            java.lang.Long r0 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x00d4 }
            r4.put(r5, r0)     // Catch:{ Throwable -> 0x00d4 }
            java.lang.String r0 = "events"
            r5 = 0
            r1.insert(r0, r5, r4)     // Catch:{ Throwable -> 0x00d4 }
            r3.remove()     // Catch:{ Throwable -> 0x00d4 }
            goto L_0x0067
        L_0x00d4:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r3 = h     // Catch:{ all -> 0x013d }
            r3.e(r0)     // Catch:{ all -> 0x013d }
            if (r1 == 0) goto L_0x00e2
            r1.endTransaction()     // Catch:{ Throwable -> 0x0136 }
            r9.f()     // Catch:{ Throwable -> 0x0136 }
        L_0x00e2:
            r0 = 0
            r9.m = r0     // Catch:{ all -> 0x0013 }
            boolean r0 = com.tencent.wxop.stat.StatConfig.isDebugEnable()     // Catch:{ all -> 0x0013 }
            if (r0 == 0) goto L_0x0120
            com.tencent.wxop.stat.common.StatLogger r0 = h     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0013 }
            java.lang.String r3 = "after insert, cacheEventsInMemory.size():"
            r1.<init>(r3)     // Catch:{ all -> 0x0013 }
            java.util.concurrent.ConcurrentHashMap<com.tencent.wxop.stat.event.e, java.lang.String> r3 = r9.l     // Catch:{ all -> 0x0013 }
            int r3 = r3.size()     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            java.lang.String r3 = ",numEventsCachedInMemory:"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            int r3 = com.tencent.wxop.stat.StatConfig.m     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            java.lang.String r3 = ",numStoredEvents:"
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            int r3 = r9.a     // Catch:{ all -> 0x0013 }
            java.lang.StringBuilder r1 = r1.append(r3)     // Catch:{ all -> 0x0013 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0013 }
            r0.i(r1)     // Catch:{ all -> 0x0013 }
        L_0x0120:
            monitor-exit(r2)     // Catch:{ all -> 0x0013 }
            goto L_0x0005
        L_0x0123:
            r1.setTransactionSuccessful()     // Catch:{ Throwable -> 0x00d4 }
            if (r1 == 0) goto L_0x00e2
            r1.endTransaction()     // Catch:{ Throwable -> 0x012f }
            r9.f()     // Catch:{ Throwable -> 0x012f }
            goto L_0x00e2
        L_0x012f:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x0013 }
            r1.e(r0)     // Catch:{ all -> 0x0013 }
            goto L_0x00e2
        L_0x0136:
            r0 = move-exception
            com.tencent.wxop.stat.common.StatLogger r1 = h     // Catch:{ all -> 0x0013 }
            r1.e(r0)     // Catch:{ all -> 0x0013 }
            goto L_0x00e2
        L_0x013d:
            r0 = move-exception
            if (r1 == 0) goto L_0x0146
            r1.endTransaction()     // Catch:{ Throwable -> 0x0147 }
            r9.f()     // Catch:{ Throwable -> 0x0147 }
        L_0x0146:
            throw r0     // Catch:{ all -> 0x0013 }
        L_0x0147:
            r1 = move-exception
            com.tencent.wxop.stat.common.StatLogger r3 = h     // Catch:{ all -> 0x0013 }
            r3.e(r1)     // Catch:{ all -> 0x0013 }
            goto L_0x0146
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.i():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0040  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void j() {
        /*
            r9 = this;
            r8 = 0
            com.tencent.wxop.stat.bc r0 = r9.c     // Catch:{ Throwable -> 0x0046, all -> 0x003c }
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
            java.util.HashMap<java.lang.String, java.lang.String> r0 = r9.n     // Catch:{ Throwable -> 0x002a }
            r2 = 0
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Throwable -> 0x002a }
            r3 = 1
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Throwable -> 0x002a }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x002a }
            goto L_0x0014
        L_0x002a:
            r0 = move-exception
        L_0x002b:
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x0044 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.j():void");
    }

    public int a() {
        return this.a;
    }

    /* access modifiers changed from: 0000 */
    public void a(int i2) {
        this.e.a(new bb(this, i2));
    }

    /* access modifiers changed from: 0000 */
    public void a(com.tencent.wxop.stat.event.e eVar, h hVar, boolean z, boolean z2) {
        if (this.e != null) {
            this.e.a(new ay(this, eVar, hVar, z, z2));
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(f fVar) {
        if (fVar != null) {
            this.e.a(new az(this, fVar));
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(List<bd> list, int i2, boolean z, boolean z2) {
        if (this.e != null) {
            this.e.a(new av(this, list, i2, z, z2));
        }
    }

    /* access modifiers changed from: 0000 */
    public void a(List<bd> list, boolean z, boolean z2) {
        if (this.e != null) {
            this.e.a(new aw(this, list, z, z2));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:84:0x0213 A[SYNTHETIC, Splitter:B:84:0x0213] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x022d A[SYNTHETIC, Splitter:B:93:0x022d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized com.tencent.wxop.stat.common.a b(android.content.Context r20) {
        /*
            r19 = this;
            monitor-enter(r19)
            r0 = r19
            com.tencent.wxop.stat.common.a r2 = r0.b     // Catch:{ all -> 0x0207 }
            if (r2 == 0) goto L_0x000d
            r0 = r19
            com.tencent.wxop.stat.common.a r2 = r0.b     // Catch:{ all -> 0x0207 }
        L_0x000b:
            monitor-exit(r19)
            return r2
        L_0x000d:
            r11 = 0
            r0 = r19
            com.tencent.wxop.stat.bc r2 = r0.c     // Catch:{ Throwable -> 0x020a, all -> 0x0229 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x020a, all -> 0x0229 }
            r2.beginTransaction()     // Catch:{ Throwable -> 0x020a, all -> 0x0229 }
            boolean r2 = com.tencent.wxop.stat.StatConfig.isDebugEnable()     // Catch:{ Throwable -> 0x020a, all -> 0x0229 }
            if (r2 == 0) goto L_0x0027
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ Throwable -> 0x020a, all -> 0x0229 }
            java.lang.String r3 = "try to load user info from db."
            r2.i(r3)     // Catch:{ Throwable -> 0x020a, all -> 0x0229 }
        L_0x0027:
            r0 = r19
            com.tencent.wxop.stat.bc r2 = r0.c     // Catch:{ Throwable -> 0x020a, all -> 0x0229 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getReadableDatabase()     // Catch:{ Throwable -> 0x020a, all -> 0x0229 }
            java.lang.String r3 = "user"
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            android.database.Cursor r5 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ Throwable -> 0x020a, all -> 0x0229 }
            r2 = 0
            boolean r3 = r5.moveToNext()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r3 == 0) goto L_0x0138
            r2 = 0
            java.lang.String r10 = r5.getString(r2)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r7 = com.tencent.wxop.stat.common.r.a(r10)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2 = 1
            int r9 = r5.getInt(r2)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2 = 2
            java.lang.String r3 = r5.getString(r2)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2 = 3
            long r12 = r5.getLong(r2)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r6 = 1
            long r14 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r16 = 1000(0x3e8, double:4.94E-321)
            long r14 = r14 / r16
            r2 = 1
            if (r9 == r2) goto L_0x025b
            r16 = 1000(0x3e8, double:4.94E-321)
            long r12 = r12 * r16
            java.lang.String r2 = com.tencent.wxop.stat.common.l.a(r12)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r12 = 1000(0x3e8, double:4.94E-321)
            long r12 = r12 * r14
            java.lang.String r4 = com.tencent.wxop.stat.common.l.a(r12)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            boolean r2 = r2.equals(r4)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r2 != 0) goto L_0x025b
            r2 = 1
        L_0x007e:
            java.lang.String r4 = com.tencent.wxop.stat.common.l.l(r20)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            boolean r3 = r3.equals(r4)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r3 != 0) goto L_0x0258
            r2 = r2 | 2
            r8 = r2
        L_0x008b:
            java.lang.String r2 = ","
            java.lang.String[] r11 = r7.split(r2)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2 = 0
            if (r11 == 0) goto L_0x01d1
            int r3 = r11.length     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r3 <= 0) goto L_0x01d1
            r3 = 0
            r4 = r11[r3]     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r4 == 0) goto L_0x00a5
            int r3 = r4.length()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r12 = 11
            if (r3 >= r12) goto L_0x0251
        L_0x00a5:
            java.lang.String r3 = com.tencent.wxop.stat.common.r.a(r20)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r3 == 0) goto L_0x024e
            int r12 = r3.length()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r13 = 10
            if (r12 <= r13) goto L_0x024e
            r2 = 1
        L_0x00b4:
            r4 = r7
            r7 = r3
        L_0x00b6:
            if (r11 == 0) goto L_0x01d9
            int r3 = r11.length     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r12 = 2
            if (r3 < r12) goto L_0x01d9
            r3 = 1
            r3 = r11[r3]     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r4.<init>()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.StringBuilder r4 = r4.append(r7)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r11 = ","
            java.lang.StringBuilder r4 = r4.append(r11)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.StringBuilder r4 = r4.append(r3)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
        L_0x00d7:
            com.tencent.wxop.stat.common.a r11 = new com.tencent.wxop.stat.common.a     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r11.<init>(r7, r3, r8)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r0 = r19
            r0.b = r11     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            android.content.ContentValues r3 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r3.<init>()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = com.tencent.wxop.stat.common.r.b(r4)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r7 = "uid"
            r3.put(r7, r4)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = "user_type"
            java.lang.Integer r7 = java.lang.Integer.valueOf(r8)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r3.put(r4, r7)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = "app_ver"
            java.lang.String r7 = com.tencent.wxop.stat.common.l.l(r20)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r3.put(r4, r7)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = "ts"
            java.lang.Long r7 = java.lang.Long.valueOf(r14)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r3.put(r4, r7)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r2 == 0) goto L_0x0126
            r0 = r19
            com.tencent.wxop.stat.bc r2 = r0.c     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = "user"
            java.lang.String r7 = "uid=?"
            r11 = 1
            java.lang.String[] r11 = new java.lang.String[r11]     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r12 = 0
            r11[r12] = r10     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2.update(r4, r3, r7, r11)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
        L_0x0126:
            if (r8 == r9) goto L_0x0137
            r0 = r19
            com.tencent.wxop.stat.bc r2 = r0.c     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = "user"
            r7 = 0
            r2.replace(r4, r7, r3)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
        L_0x0137:
            r2 = r6
        L_0x0138:
            if (r2 != 0) goto L_0x01b0
            java.lang.String r3 = com.tencent.wxop.stat.common.l.b(r20)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = com.tencent.wxop.stat.common.l.c(r20)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r4 == 0) goto L_0x024b
            int r2 = r4.length()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r2 <= 0) goto L_0x024b
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2.<init>()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r6 = ","
            java.lang.StringBuilder r2 = r2.append(r6)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
        L_0x0162:
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r8 = 1000(0x3e8, double:4.94E-321)
            long r6 = r6 / r8
            java.lang.String r8 = com.tencent.wxop.stat.common.l.l(r20)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            android.content.ContentValues r9 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r9.<init>()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r2 = com.tencent.wxop.stat.common.r.b(r2)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r10 = "uid"
            r9.put(r10, r2)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r2 = "user_type"
            r10 = 0
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r9.put(r2, r10)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r2 = "app_ver"
            r9.put(r2, r8)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r2 = "ts"
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r9.put(r2, r6)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r0 = r19
            com.tencent.wxop.stat.bc r2 = r0.c     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r6 = "user"
            r7 = 0
            r2.insert(r6, r7, r9)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            com.tencent.wxop.stat.common.a r2 = new com.tencent.wxop.stat.common.a     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r6 = 0
            r2.<init>(r3, r4, r6)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r0 = r19
            r0.b = r2     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
        L_0x01b0:
            r0 = r19
            com.tencent.wxop.stat.bc r2 = r0.c     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2.setTransactionSuccessful()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r5 == 0) goto L_0x01c0
            r5.close()     // Catch:{ Throwable -> 0x0200 }
        L_0x01c0:
            r0 = r19
            com.tencent.wxop.stat.bc r2 = r0.c     // Catch:{ Throwable -> 0x0200 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x0200 }
            r2.endTransaction()     // Catch:{ Throwable -> 0x0200 }
        L_0x01cb:
            r0 = r19
            com.tencent.wxop.stat.common.a r2 = r0.b     // Catch:{ all -> 0x0207 }
            goto L_0x000b
        L_0x01d1:
            java.lang.String r4 = com.tencent.wxop.stat.common.l.b(r20)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2 = 1
            r7 = r4
            goto L_0x00b6
        L_0x01d9:
            java.lang.String r3 = com.tencent.wxop.stat.common.l.c(r20)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r3 == 0) goto L_0x00d7
            int r11 = r3.length()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            if (r11 <= 0) goto L_0x00d7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2.<init>()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.StringBuilder r2 = r2.append(r7)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = ","
            java.lang.StringBuilder r2 = r2.append(r4)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            java.lang.String r4 = r2.toString()     // Catch:{ Throwable -> 0x0248, all -> 0x0243 }
            r2 = 1
            goto L_0x00d7
        L_0x0200:
            r2 = move-exception
            com.tencent.wxop.stat.common.StatLogger r3 = h     // Catch:{ all -> 0x0207 }
            r3.e(r2)     // Catch:{ all -> 0x0207 }
            goto L_0x01cb
        L_0x0207:
            r2 = move-exception
            monitor-exit(r19)
            throw r2
        L_0x020a:
            r2 = move-exception
            r3 = r11
        L_0x020c:
            com.tencent.wxop.stat.common.StatLogger r4 = h     // Catch:{ all -> 0x0245 }
            r4.e(r2)     // Catch:{ all -> 0x0245 }
            if (r3 == 0) goto L_0x0216
            r3.close()     // Catch:{ Throwable -> 0x0222 }
        L_0x0216:
            r0 = r19
            com.tencent.wxop.stat.bc r2 = r0.c     // Catch:{ Throwable -> 0x0222 }
            android.database.sqlite.SQLiteDatabase r2 = r2.getWritableDatabase()     // Catch:{ Throwable -> 0x0222 }
            r2.endTransaction()     // Catch:{ Throwable -> 0x0222 }
            goto L_0x01cb
        L_0x0222:
            r2 = move-exception
            com.tencent.wxop.stat.common.StatLogger r3 = h     // Catch:{ all -> 0x0207 }
            r3.e(r2)     // Catch:{ all -> 0x0207 }
            goto L_0x01cb
        L_0x0229:
            r2 = move-exception
            r5 = r11
        L_0x022b:
            if (r5 == 0) goto L_0x0230
            r5.close()     // Catch:{ Throwable -> 0x023c }
        L_0x0230:
            r0 = r19
            com.tencent.wxop.stat.bc r3 = r0.c     // Catch:{ Throwable -> 0x023c }
            android.database.sqlite.SQLiteDatabase r3 = r3.getWritableDatabase()     // Catch:{ Throwable -> 0x023c }
            r3.endTransaction()     // Catch:{ Throwable -> 0x023c }
        L_0x023b:
            throw r2     // Catch:{ all -> 0x0207 }
        L_0x023c:
            r3 = move-exception
            com.tencent.wxop.stat.common.StatLogger r4 = h     // Catch:{ all -> 0x0207 }
            r4.e(r3)     // Catch:{ all -> 0x0207 }
            goto L_0x023b
        L_0x0243:
            r2 = move-exception
            goto L_0x022b
        L_0x0245:
            r2 = move-exception
            r5 = r3
            goto L_0x022b
        L_0x0248:
            r2 = move-exception
            r3 = r5
            goto L_0x020c
        L_0x024b:
            r2 = r3
            goto L_0x0162
        L_0x024e:
            r3 = r4
            goto L_0x00b4
        L_0x0251:
            r18 = r4
            r4 = r7
            r7 = r18
            goto L_0x00b6
        L_0x0258:
            r8 = r2
            goto L_0x008b
        L_0x025b:
            r2 = r9
            goto L_0x007e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.b(android.content.Context):com.tencent.wxop.stat.common.a");
    }

    /* access modifiers changed from: 0000 */
    public void c() {
        if (StatConfig.isEnableStatService()) {
            try {
                this.e.a(new ax(this));
            } catch (Throwable th) {
                h.e(th);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void d() {
        /*
            r9 = this;
            r8 = 0
            com.tencent.wxop.stat.bc r0 = r9.c     // Catch:{ Throwable -> 0x0062, all -> 0x0058 }
            android.database.sqlite.SQLiteDatabase r0 = r0.getReadableDatabase()     // Catch:{ Throwable -> 0x0062, all -> 0x0058 }
            java.lang.String r1 = "config"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0062, all -> 0x0058 }
        L_0x0014:
            boolean r0 = r1.moveToNext()     // Catch:{ Throwable -> 0x0046 }
            if (r0 == 0) goto L_0x0052
            r0 = 0
            int r0 = r1.getInt(r0)     // Catch:{ Throwable -> 0x0046 }
            r2 = 1
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Throwable -> 0x0046 }
            r3 = 2
            java.lang.String r3 = r1.getString(r3)     // Catch:{ Throwable -> 0x0046 }
            r4 = 3
            int r4 = r1.getInt(r4)     // Catch:{ Throwable -> 0x0046 }
            com.tencent.wxop.stat.f r5 = new com.tencent.wxop.stat.f     // Catch:{ Throwable -> 0x0046 }
            r5.<init>(r0)     // Catch:{ Throwable -> 0x0046 }
            r5.a = r0     // Catch:{ Throwable -> 0x0046 }
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ Throwable -> 0x0046 }
            r0.<init>(r2)     // Catch:{ Throwable -> 0x0046 }
            r5.b = r0     // Catch:{ Throwable -> 0x0046 }
            r5.c = r3     // Catch:{ Throwable -> 0x0046 }
            r5.d = r4     // Catch:{ Throwable -> 0x0046 }
            android.content.Context r0 = i     // Catch:{ Throwable -> 0x0046 }
            com.tencent.wxop.stat.StatConfig.a(r0, r5)     // Catch:{ Throwable -> 0x0046 }
            goto L_0x0014
        L_0x0046:
            r0 = move-exception
        L_0x0047:
            com.tencent.wxop.stat.common.StatLogger r2 = h     // Catch:{ all -> 0x0060 }
            r2.e(r0)     // Catch:{ all -> 0x0060 }
            if (r1 == 0) goto L_0x0051
            r1.close()
        L_0x0051:
            return
        L_0x0052:
            if (r1 == 0) goto L_0x0051
            r1.close()
            goto L_0x0051
        L_0x0058:
            r0 = move-exception
            r1 = r8
        L_0x005a:
            if (r1 == 0) goto L_0x005f
            r1.close()
        L_0x005f:
            throw r0
        L_0x0060:
            r0 = move-exception
            goto L_0x005a
        L_0x0062:
            r0 = move-exception
            r1 = r8
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.au.d():void");
    }
}
