package com.tencent.bugly.proguard;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;
import com.tencent.bugly.beta.download.DownloadTask;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BUGLY */
public class p {
    public static p a = new p();

    public synchronized ContentValues a(String str) {
        ContentValues contentValues;
        if (TextUtils.isEmpty(str)) {
            contentValues = null;
        } else {
            Cursor a2 = ae.a().a("dl_1002", null, "_dUrl=?", new String[]{str}, null, true);
            if (a2 == null || !a2.moveToFirst()) {
                contentValues = null;
            } else {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("_dUrl", a2.getString(a2.getColumnIndex("_dUrl")));
                contentValues2.put("_sFile", a2.getString(a2.getColumnIndex("_sFile")));
                contentValues2.put("_sLen", Long.valueOf(a2.getLong(a2.getColumnIndex("_sLen"))));
                contentValues2.put("_tLen", Long.valueOf(a2.getLong(a2.getColumnIndex("_tLen"))));
                contentValues2.put("_MD5", a2.getString(a2.getColumnIndex("_MD5")));
                contentValues2.put("_DLTIME", Long.valueOf(a2.getLong(a2.getColumnIndex("_DLTIME"))));
                contentValues = contentValues2;
            }
            if (a2 != null) {
                a2.close();
            }
        }
        return contentValues;
    }

    public synchronized boolean a(DownloadTask downloadTask) {
        boolean z = true;
        synchronized (this) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_dUrl", downloadTask.getDownloadUrl());
            contentValues.put("_sFile", downloadTask.getSaveFile().getAbsolutePath());
            contentValues.put("_sLen", Long.valueOf(downloadTask.getSavedLength()));
            contentValues.put("_tLen", Long.valueOf(downloadTask.getTotalLength()));
            contentValues.put("_MD5", downloadTask.getMD5());
            contentValues.put("_DLTIME", Long.valueOf(downloadTask.getCostTime()));
            if (ae.a().a("dl_1002", contentValues, (ad) null, true) < 0) {
                z = false;
            }
        }
        return z;
    }

    public synchronized int b(DownloadTask downloadTask) {
        return ae.a().a("dl_1002", "_dUrl = ?", new String[]{downloadTask.getDownloadUrl()}, (ad) null, true);
    }

    public synchronized int b(String str) {
        return ae.a().a("dl_1002", "_sFile = ?", new String[]{str}, (ad) null, true);
    }

    public synchronized List<w> a() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Cursor a2 = ae.a().a("ge_1002", null, null, null, null, true);
        while (a2 != null && a2.moveToNext()) {
            w wVar = (w) ah.a(a2.getBlob(a2.getColumnIndex("_datas")), w.class);
            if (wVar != null) {
                arrayList.add(wVar);
            }
        }
        if (a2 != null) {
            a2.close();
        }
        return arrayList.isEmpty() ? null : arrayList;
    }

    public synchronized boolean a(w wVar) {
        boolean z = true;
        boolean z2 = false;
        synchronized (this) {
            if (wVar != null) {
                byte[] a2 = ah.a((m) wVar);
                if (a2 != null && a2.length > 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("_time", Long.valueOf(wVar.b));
                    contentValues.put("_datas", a2);
                    if (ae.a().a("ge_1002", contentValues, (ad) null, true) < 0) {
                        z = false;
                    }
                    z2 = z;
                }
            }
        }
        return z2;
    }

    public synchronized int b() {
        return ae.a().a("ge_1002", (String) null, (String[]) null, (ad) null, true);
    }

    public synchronized boolean a(int i, String str, byte[] bArr) {
        boolean z = true;
        synchronized (this) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", Integer.valueOf(i));
            contentValues.put("_tm", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("_tp", str);
            contentValues.put("_dt", bArr);
            if (ae.a().a("st_1002", contentValues, (ad) null, true) < 0) {
                z = false;
            }
        }
        return z;
    }

    public synchronized boolean c(String str) {
        return ae.a().a("st_1002", "_id = ? and _tp = ? ", new String[]{"1002", str}, (ad) null, true) > 0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x0061 A[SYNTHETIC, Splitter:B:34:0x0061] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.Map<java.lang.String, byte[]> c() {
        /*
            r8 = this;
            r7 = 0
            monitor-enter(r8)
            java.lang.String r3 = "_id = 1002"
            com.tencent.bugly.proguard.ae r0 = com.tencent.bugly.proguard.ae.a()     // Catch:{ Throwable -> 0x0067, all -> 0x005d }
            java.lang.String r1 = "st_1002"
            r2 = 0
            r4 = 0
            r5 = 0
            r6 = 1
            android.database.Cursor r1 = r0.a(r1, r2, r3, r4, r5, r6)     // Catch:{ Throwable -> 0x0067, all -> 0x005d }
            if (r1 != 0) goto L_0x001e
            if (r1 == 0) goto L_0x001b
            r1.close()     // Catch:{ all -> 0x005a }
        L_0x001b:
            r0 = r7
        L_0x001c:
            monitor-exit(r8)
            return r0
        L_0x001e:
            java.util.HashMap r0 = new java.util.HashMap     // Catch:{ Throwable -> 0x0043 }
            r0.<init>()     // Catch:{ Throwable -> 0x0043 }
        L_0x0023:
            boolean r2 = r1.moveToNext()     // Catch:{ Throwable -> 0x0043 }
            if (r2 == 0) goto L_0x0054
            java.lang.String r2 = "_tp"
            int r2 = r1.getColumnIndex(r2)     // Catch:{ Throwable -> 0x0043 }
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Throwable -> 0x0043 }
            java.lang.String r3 = "_dt"
            int r3 = r1.getColumnIndex(r3)     // Catch:{ Throwable -> 0x0043 }
            byte[] r3 = r1.getBlob(r3)     // Catch:{ Throwable -> 0x0043 }
            r0.put(r2, r3)     // Catch:{ Throwable -> 0x0043 }
            goto L_0x0023
        L_0x0043:
            r0 = move-exception
        L_0x0044:
            boolean r2 = com.tencent.bugly.proguard.an.a(r0)     // Catch:{ all -> 0x0065 }
            if (r2 != 0) goto L_0x004d
            com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)     // Catch:{ all -> 0x0065 }
        L_0x004d:
            if (r1 == 0) goto L_0x0052
            r1.close()     // Catch:{ all -> 0x005a }
        L_0x0052:
            r0 = r7
            goto L_0x001c
        L_0x0054:
            if (r1 == 0) goto L_0x001c
            r1.close()     // Catch:{ all -> 0x005a }
            goto L_0x001c
        L_0x005a:
            r0 = move-exception
            monitor-exit(r8)
            throw r0
        L_0x005d:
            r0 = move-exception
            r1 = r7
        L_0x005f:
            if (r1 == 0) goto L_0x0064
            r1.close()     // Catch:{ all -> 0x005a }
        L_0x0064:
            throw r0     // Catch:{ all -> 0x005a }
        L_0x0065:
            r0 = move-exception
            goto L_0x005f
        L_0x0067:
            r0 = move-exception
            r1 = r7
            goto L_0x0044
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.p.c():java.util.Map");
    }
}
