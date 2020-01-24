package com.tencent.open.b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.tencent.open.utils.e;

/* compiled from: ProGuard */
public class f extends SQLiteOpenHelper {
    protected static final String[] a = {"key"};
    protected static f b;

    public static synchronized f a() {
        f fVar;
        synchronized (f.class) {
            if (b == null) {
                b = new f(e.a());
            }
            fVar = b;
        }
        return fVar;
    }

    public f(Context context) {
        super(context, "sdk_report.db", null, 2);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS via_cgi_report( _id INTEGER PRIMARY KEY,key TEXT,type TEXT,blob BLOB);");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS via_cgi_report");
        onCreate(sQLiteDatabase);
    }

    /* JADX WARNING: Removed duplicated region for block: B:112:0x006a A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0061 A[SYNTHETIC, Splitter:B:31:0x0061] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006c A[SYNTHETIC, Splitter:B:36:0x006c] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0071 A[SYNTHETIC, Splitter:B:39:0x0071] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0076 A[SYNTHETIC, Splitter:B:42:0x0076] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x007f A[SYNTHETIC, Splitter:B:48:0x007f] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x008e A[SYNTHETIC, Splitter:B:58:0x008e] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00ac A[SYNTHETIC, Splitter:B:76:0x00ac] */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x00c1 A[Catch:{ IOException -> 0x00b3 }] */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x00c6 A[SYNTHETIC, Splitter:B:91:0x00c6] */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x00cb A[SYNTHETIC, Splitter:B:94:0x00cb] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.List<java.io.Serializable> a(java.lang.String r12) {
        /*
            r11 = this;
            r9 = 0
            monitor-enter(r11)
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x00b0 }
            r0.<init>()     // Catch:{ all -> 0x00b0 }
            java.util.List r8 = java.util.Collections.synchronizedList(r0)     // Catch:{ all -> 0x00b0 }
            boolean r0 = android.text.TextUtils.isEmpty(r12)     // Catch:{ all -> 0x00b0 }
            if (r0 == 0) goto L_0x0014
            r0 = r8
        L_0x0012:
            monitor-exit(r11)
            return r0
        L_0x0014:
            android.database.sqlite.SQLiteDatabase r0 = r11.getReadableDatabase()     // Catch:{ all -> 0x00b0 }
            if (r0 != 0) goto L_0x001c
            r0 = r8
            goto L_0x0012
        L_0x001c:
            r10 = 0
            java.lang.String r1 = "via_cgi_report"
            r2 = 0
            java.lang.String r3 = "type = ?"
            r4 = 1
            java.lang.String[] r4 = new java.lang.String[r4]     // Catch:{ Exception -> 0x00e3, all -> 0x00bd }
            r5 = 0
            r4[r5] = r12     // Catch:{ Exception -> 0x00e3, all -> 0x00bd }
            r5 = 0
            r6 = 0
            r7 = 0
            android.database.Cursor r3 = r0.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x00e3, all -> 0x00bd }
            if (r3 == 0) goto L_0x006a
            int r1 = r3.getCount()     // Catch:{ Exception -> 0x0095, all -> 0x00de }
            if (r1 <= 0) goto L_0x006a
            r3.moveToFirst()     // Catch:{ Exception -> 0x0095, all -> 0x00de }
        L_0x003c:
            java.lang.String r1 = "blob"
            int r1 = r3.getColumnIndex(r1)     // Catch:{ Exception -> 0x0095, all -> 0x00de }
            byte[] r1 = r3.getBlob(r1)     // Catch:{ Exception -> 0x0095, all -> 0x00de }
            java.io.ByteArrayInputStream r4 = new java.io.ByteArrayInputStream     // Catch:{ Exception -> 0x0095, all -> 0x00de }
            r4.<init>(r1)     // Catch:{ Exception -> 0x0095, all -> 0x00de }
            java.io.ObjectInputStream r2 = new java.io.ObjectInputStream     // Catch:{ Exception -> 0x007b, all -> 0x008a }
            r2.<init>(r4)     // Catch:{ Exception -> 0x007b, all -> 0x008a }
            java.lang.Object r1 = r2.readObject()     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            java.io.Serializable r1 = (java.io.Serializable) r1     // Catch:{ Exception -> 0x00e8, all -> 0x00e6 }
            if (r2 == 0) goto L_0x005c
            r2.close()     // Catch:{ IOException -> 0x00d4 }
        L_0x005c:
            r4.close()     // Catch:{ IOException -> 0x00d6 }
        L_0x005f:
            if (r1 == 0) goto L_0x0064
            r8.add(r1)     // Catch:{ Exception -> 0x0095, all -> 0x00de }
        L_0x0064:
            boolean r1 = r3.moveToNext()     // Catch:{ Exception -> 0x0095, all -> 0x00de }
            if (r1 != 0) goto L_0x003c
        L_0x006a:
            if (r3 == 0) goto L_0x006f
            r3.close()     // Catch:{ all -> 0x00b0 }
        L_0x006f:
            if (r9 == 0) goto L_0x0074
            r10.close()     // Catch:{ IOException -> 0x00b3 }
        L_0x0074:
            if (r0 == 0) goto L_0x0079
            r0.close()     // Catch:{ all -> 0x00b0 }
        L_0x0079:
            r0 = r8
            goto L_0x0012
        L_0x007b:
            r1 = move-exception
            r1 = r9
        L_0x007d:
            if (r1 == 0) goto L_0x0082
            r1.close()     // Catch:{ IOException -> 0x00d8 }
        L_0x0082:
            r4.close()     // Catch:{ IOException -> 0x0087 }
            r1 = r9
            goto L_0x005f
        L_0x0087:
            r1 = move-exception
            r1 = r9
            goto L_0x005f
        L_0x008a:
            r1 = move-exception
            r2 = r9
        L_0x008c:
            if (r2 == 0) goto L_0x0091
            r2.close()     // Catch:{ IOException -> 0x00da }
        L_0x0091:
            r4.close()     // Catch:{ IOException -> 0x00dc }
        L_0x0094:
            throw r1     // Catch:{ Exception -> 0x0095, all -> 0x00de }
        L_0x0095:
            r1 = move-exception
            r2 = r3
        L_0x0097:
            java.lang.String r3 = "openSDK_LOG.ReportDatabaseHelper"
            java.lang.String r4 = "getReportItemFromDB has exception."
            com.tencent.open.a.f.b(r3, r4, r1)     // Catch:{ all -> 0x00e0 }
            if (r2 == 0) goto L_0x00a5
            r2.close()     // Catch:{ all -> 0x00b0 }
        L_0x00a5:
            if (r9 == 0) goto L_0x00aa
            r10.close()     // Catch:{ IOException -> 0x00b8 }
        L_0x00aa:
            if (r0 == 0) goto L_0x0079
            r0.close()     // Catch:{ all -> 0x00b0 }
            goto L_0x0079
        L_0x00b0:
            r0 = move-exception
            monitor-exit(r11)
            throw r0
        L_0x00b3:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x00b0 }
            goto L_0x0074
        L_0x00b8:
            r1 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r1)     // Catch:{ all -> 0x00b0 }
            goto L_0x00aa
        L_0x00bd:
            r1 = move-exception
            r3 = r9
        L_0x00bf:
            if (r3 == 0) goto L_0x00c4
            r3.close()     // Catch:{ all -> 0x00b0 }
        L_0x00c4:
            if (r9 == 0) goto L_0x00c9
            r10.close()     // Catch:{ IOException -> 0x00cf }
        L_0x00c9:
            if (r0 == 0) goto L_0x00ce
            r0.close()     // Catch:{ all -> 0x00b0 }
        L_0x00ce:
            throw r1     // Catch:{ all -> 0x00b0 }
        L_0x00cf:
            r2 = move-exception
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r2)     // Catch:{ all -> 0x00b0 }
            goto L_0x00c9
        L_0x00d4:
            r2 = move-exception
            goto L_0x005c
        L_0x00d6:
            r2 = move-exception
            goto L_0x005f
        L_0x00d8:
            r1 = move-exception
            goto L_0x0082
        L_0x00da:
            r2 = move-exception
            goto L_0x0091
        L_0x00dc:
            r2 = move-exception
            goto L_0x0094
        L_0x00de:
            r1 = move-exception
            goto L_0x00bf
        L_0x00e0:
            r1 = move-exception
            r3 = r2
            goto L_0x00bf
        L_0x00e3:
            r1 = move-exception
            r2 = r9
            goto L_0x0097
        L_0x00e6:
            r1 = move-exception
            goto L_0x008c
        L_0x00e8:
            r1 = move-exception
            r1 = r2
            goto L_0x007d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.f.a(java.lang.String):java.util.List");
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x006d A[SYNTHETIC, Splitter:B:39:0x006d] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0079 A[SYNTHETIC, Splitter:B:46:0x0079] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(java.lang.String r9, java.util.List<java.io.Serializable> r10) {
        /*
            r8 = this;
            r2 = 0
            r1 = 20
            monitor-enter(r8)
            int r0 = r10.size()     // Catch:{ all -> 0x0094 }
            if (r0 != 0) goto L_0x000c
        L_0x000a:
            monitor-exit(r8)
            return
        L_0x000c:
            if (r0 > r1) goto L_0x0067
            r4 = r0
        L_0x000f:
            boolean r0 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x0094 }
            if (r0 != 0) goto L_0x000a
            r8.b(r9)     // Catch:{ all -> 0x0094 }
            android.database.sqlite.SQLiteDatabase r5 = r8.getWritableDatabase()     // Catch:{ all -> 0x0094 }
            if (r5 == 0) goto L_0x000a
            r5.beginTransaction()     // Catch:{ all -> 0x0094 }
            android.content.ContentValues r6 = new android.content.ContentValues     // Catch:{ Exception -> 0x0080 }
            r6.<init>()     // Catch:{ Exception -> 0x0080 }
            r0 = 0
            r3 = r0
        L_0x0028:
            if (r3 >= r4) goto L_0x0097
            java.lang.Object r0 = r10.get(r3)     // Catch:{ Exception -> 0x0080 }
            java.io.Serializable r0 = (java.io.Serializable) r0     // Catch:{ Exception -> 0x0080 }
            if (r0 == 0) goto L_0x0060
            java.lang.String r1 = "type"
            r6.put(r1, r9)     // Catch:{ Exception -> 0x0080 }
            java.io.ByteArrayOutputStream r7 = new java.io.ByteArrayOutputStream     // Catch:{ Exception -> 0x0080 }
            r1 = 512(0x200, float:7.175E-43)
            r7.<init>(r1)     // Catch:{ Exception -> 0x0080 }
            java.io.ObjectOutputStream r1 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0069, all -> 0x0076 }
            r1.<init>(r7)     // Catch:{ IOException -> 0x0069, all -> 0x0076 }
            r1.writeObject(r0)     // Catch:{ IOException -> 0x00bb, all -> 0x00b8 }
            if (r1 == 0) goto L_0x004c
            r1.close()     // Catch:{ IOException -> 0x00ae }
        L_0x004c:
            r7.close()     // Catch:{ IOException -> 0x00b0 }
        L_0x004f:
            java.lang.String r0 = "blob"
            byte[] r1 = r7.toByteArray()     // Catch:{ Exception -> 0x0080 }
            r6.put(r0, r1)     // Catch:{ Exception -> 0x0080 }
            java.lang.String r0 = "via_cgi_report"
            r1 = 0
            r5.insert(r0, r1, r6)     // Catch:{ Exception -> 0x0080 }
        L_0x0060:
            r6.clear()     // Catch:{ Exception -> 0x0080 }
            int r0 = r3 + 1
            r3 = r0
            goto L_0x0028
        L_0x0067:
            r4 = r1
            goto L_0x000f
        L_0x0069:
            r0 = move-exception
            r0 = r2
        L_0x006b:
            if (r0 == 0) goto L_0x0070
            r0.close()     // Catch:{ IOException -> 0x00b2 }
        L_0x0070:
            r7.close()     // Catch:{ IOException -> 0x0074 }
            goto L_0x004f
        L_0x0074:
            r0 = move-exception
            goto L_0x004f
        L_0x0076:
            r0 = move-exception
        L_0x0077:
            if (r2 == 0) goto L_0x007c
            r2.close()     // Catch:{ IOException -> 0x00b4 }
        L_0x007c:
            r7.close()     // Catch:{ IOException -> 0x00b6 }
        L_0x007f:
            throw r0     // Catch:{ Exception -> 0x0080 }
        L_0x0080:
            r0 = move-exception
            java.lang.String r0 = "openSDK_LOG.ReportDatabaseHelper"
            java.lang.String r1 = "saveReportItemToDB has exception."
            com.tencent.open.a.f.e(r0, r1)     // Catch:{ all -> 0x00a4 }
            r5.endTransaction()     // Catch:{ all -> 0x0094 }
            if (r5 == 0) goto L_0x000a
            r5.close()     // Catch:{ all -> 0x0094 }
            goto L_0x000a
        L_0x0094:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        L_0x0097:
            r5.setTransactionSuccessful()     // Catch:{ Exception -> 0x0080 }
            r5.endTransaction()     // Catch:{ all -> 0x0094 }
            if (r5 == 0) goto L_0x000a
            r5.close()     // Catch:{ all -> 0x0094 }
            goto L_0x000a
        L_0x00a4:
            r0 = move-exception
            r5.endTransaction()     // Catch:{ all -> 0x0094 }
            if (r5 == 0) goto L_0x00ad
            r5.close()     // Catch:{ all -> 0x0094 }
        L_0x00ad:
            throw r0     // Catch:{ all -> 0x0094 }
        L_0x00ae:
            r0 = move-exception
            goto L_0x004c
        L_0x00b0:
            r0 = move-exception
            goto L_0x004f
        L_0x00b2:
            r0 = move-exception
            goto L_0x0070
        L_0x00b4:
            r1 = move-exception
            goto L_0x007c
        L_0x00b6:
            r1 = move-exception
            goto L_0x007f
        L_0x00b8:
            r0 = move-exception
            r2 = r1
            goto L_0x0077
        L_0x00bb:
            r0 = move-exception
            r0 = r1
            goto L_0x006b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.open.b.f.a(java.lang.String, java.util.List):void");
    }

    public synchronized void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase != null) {
                try {
                    writableDatabase.delete("via_cgi_report", "type = ?", new String[]{str});
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                } catch (Exception e) {
                    com.tencent.open.a.f.b("openSDK_LOG.ReportDatabaseHelper", "clearReportItem has exception.", e);
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                } catch (Throwable th) {
                    if (writableDatabase != null) {
                        writableDatabase.close();
                    }
                    throw th;
                }
            }
        }
    }
}
