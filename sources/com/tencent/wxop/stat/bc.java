package com.tencent.wxop.stat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class bc extends SQLiteOpenHelper {
    private String a = "";
    private Context b = null;

    public bc(Context context, String str) {
        super(context, str, null, 3);
        this.a = str;
        this.b = context.getApplicationContext();
        if (StatConfig.isDebugEnable()) {
            au.h.i("SQLiteOpenHelper " + this.a);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void a(android.database.sqlite.SQLiteDatabase r10) {
        /*
            r9 = this;
            r8 = 0
            java.lang.String r1 = "user"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r10
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x004c, all -> 0x005b }
            android.content.ContentValues r0 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0065 }
            r0.<init>()     // Catch:{ Throwable -> 0x0065 }
            boolean r2 = r1.moveToNext()     // Catch:{ Throwable -> 0x0065 }
            if (r2 == 0) goto L_0x0035
            r2 = 0
            java.lang.String r8 = r1.getString(r2)     // Catch:{ Throwable -> 0x0065 }
            r2 = 1
            r1.getInt(r2)     // Catch:{ Throwable -> 0x0065 }
            r2 = 2
            r1.getString(r2)     // Catch:{ Throwable -> 0x0065 }
            r2 = 3
            r1.getLong(r2)     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r2 = com.tencent.wxop.stat.common.r.b(r8)     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r3 = "uid"
            r0.put(r3, r2)     // Catch:{ Throwable -> 0x0065 }
        L_0x0035:
            if (r8 == 0) goto L_0x0046
            java.lang.String r2 = "user"
            java.lang.String r3 = "uid=?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Throwable -> 0x0065 }
            r5 = 0
            r4[r5] = r8     // Catch:{ Throwable -> 0x0065 }
            r10.update(r2, r0, r3, r4)     // Catch:{ Throwable -> 0x0065 }
        L_0x0046:
            if (r1 == 0) goto L_0x004b
            r1.close()
        L_0x004b:
            return
        L_0x004c:
            r0 = move-exception
            r1 = r8
        L_0x004e:
            com.tencent.wxop.stat.common.StatLogger r2 = com.tencent.wxop.stat.au.h     // Catch:{ all -> 0x0063 }
            r2.e(r0)     // Catch:{ all -> 0x0063 }
            if (r1 == 0) goto L_0x004b
            r1.close()
            goto L_0x004b
        L_0x005b:
            r0 = move-exception
            r1 = r8
        L_0x005d:
            if (r1 == 0) goto L_0x0062
            r1.close()
        L_0x0062:
            throw r0
        L_0x0063:
            r0 = move-exception
            goto L_0x005d
        L_0x0065:
            r0 = move-exception
            goto L_0x004e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.bc.a(android.database.sqlite.SQLiteDatabase):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void b(android.database.sqlite.SQLiteDatabase r11) {
        /*
            r10 = this;
            r8 = 0
            java.lang.String r1 = "events"
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            r0 = r11
            android.database.Cursor r7 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x0090, all -> 0x008a }
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r0.<init>()     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
        L_0x0014:
            boolean r1 = r7.moveToNext()     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            if (r1 == 0) goto L_0x0046
            r1 = 0
            long r2 = r7.getLong(r1)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r1 = 1
            java.lang.String r4 = r7.getString(r1)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r1 = 2
            int r5 = r7.getInt(r1)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r1 = 3
            int r6 = r7.getInt(r1)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            com.tencent.wxop.stat.bd r1 = new com.tencent.wxop.stat.bd     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r1.<init>(r2, r4, r5, r6)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r0.add(r1)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            goto L_0x0014
        L_0x0037:
            r0 = move-exception
            r1 = r7
        L_0x0039:
            com.tencent.wxop.stat.common.StatLogger r2 = com.tencent.wxop.stat.au.h     // Catch:{ all -> 0x008d }
            r2.e(r0)     // Catch:{ all -> 0x008d }
            if (r1 == 0) goto L_0x0045
            r1.close()
        L_0x0045:
            return
        L_0x0046:
            android.content.ContentValues r1 = new android.content.ContentValues     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r1.<init>()     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            java.util.Iterator r2 = r0.iterator()     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
        L_0x004f:
            boolean r0 = r2.hasNext()     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            if (r0 == 0) goto L_0x0084
            java.lang.Object r0 = r2.next()     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            com.tencent.wxop.stat.bd r0 = (com.tencent.wxop.stat.bd) r0     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            java.lang.String r3 = "content"
            java.lang.String r4 = r0.b     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            java.lang.String r4 = com.tencent.wxop.stat.common.r.b(r4)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r1.put(r3, r4)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            java.lang.String r3 = "events"
            java.lang.String r4 = "event_id=?"
            r5 = 1
            java.lang.String[] r5 = new java.lang.String[r5]     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r6 = 0
            long r8 = r0.a     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            java.lang.String r0 = java.lang.Long.toString(r8)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r5[r6] = r0     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            r11.update(r3, r1, r4, r5)     // Catch:{ Throwable -> 0x0037, all -> 0x007d }
            goto L_0x004f
        L_0x007d:
            r0 = move-exception
        L_0x007e:
            if (r7 == 0) goto L_0x0083
            r7.close()
        L_0x0083:
            throw r0
        L_0x0084:
            if (r7 == 0) goto L_0x0045
            r7.close()
            goto L_0x0045
        L_0x008a:
            r0 = move-exception
            r7 = r8
            goto L_0x007e
        L_0x008d:
            r0 = move-exception
            r7 = r1
            goto L_0x007e
        L_0x0090:
            r0 = move-exception
            r1 = r8
            goto L_0x0039
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.wxop.stat.bc.b(android.database.sqlite.SQLiteDatabase):void");
    }

    public synchronized void close() {
        super.close();
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists events(event_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, content TEXT, status INTEGER, send_count INTEGER, timestamp LONG)");
        sQLiteDatabase.execSQL("create table if not exists user(uid TEXT PRIMARY KEY, user_type INTEGER, app_ver TEXT, ts INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists config(type INTEGER PRIMARY KEY NOT NULL, content TEXT, md5sum TEXT, version INTEGER)");
        sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
        sQLiteDatabase.execSQL("CREATE INDEX if not exists status_idx ON events(status)");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        au.h.debug("upgrade DB from oldVersion " + i + " to newVersion " + i2);
        if (i == 1) {
            sQLiteDatabase.execSQL("create table if not exists keyvalues(key TEXT PRIMARY KEY NOT NULL, value TEXT)");
            a(sQLiteDatabase);
            b(sQLiteDatabase);
        }
        if (i == 2) {
            a(sQLiteDatabase);
            b(sQLiteDatabase);
        }
    }
}
