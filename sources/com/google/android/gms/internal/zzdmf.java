package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class zzdmf {
    private static Uri CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
    private static Uri zzlnb = Uri.parse("content://com.google.android.gsf.gservices/prefix");
    private static Pattern zzlnc = Pattern.compile("^(1|true|t|on|yes|y)$", 2);
    private static Pattern zzlnd = Pattern.compile("^(0|false|f|off|no|n)$", 2);
    /* access modifiers changed from: private */
    public static final AtomicBoolean zzlne = new AtomicBoolean();
    private static HashMap<String, String> zzlnf;
    private static HashMap<String, Boolean> zzlng = new HashMap<>();
    private static HashMap<String, Integer> zzlnh = new HashMap<>();
    private static HashMap<String, Long> zzlni = new HashMap<>();
    private static HashMap<String, Float> zzlnj = new HashMap<>();
    private static Object zzlnk;
    private static boolean zzlnl;
    private static String[] zzlnm = new String[0];

    public static long getLong(ContentResolver contentResolver, String str, long j) {
        Long l;
        long j2;
        Object zzb = zzb(contentResolver);
        Long l2 = (Long) zza(zzlni, str, (T) Long.valueOf(0));
        if (l2 != null) {
            return l2.longValue();
        }
        String zza = zza(contentResolver, str, (String) null);
        if (zza == null) {
            l = l2;
            j2 = 0;
        } else {
            try {
                long parseLong = Long.parseLong(zza);
                l = Long.valueOf(parseLong);
                j2 = parseLong;
            } catch (NumberFormatException e) {
                l = l2;
                j2 = 0;
            }
        }
        zza(zzb, zzlni, str, l);
        return j2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        return r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static <T> T zza(java.util.HashMap<java.lang.String, T> r2, java.lang.String r3, T r4) {
        /*
            java.lang.Class<com.google.android.gms.internal.zzdmf> r1 = com.google.android.gms.internal.zzdmf.class
            monitor-enter(r1)
            boolean r0 = r2.containsKey(r3)     // Catch:{ all -> 0x0016 }
            if (r0 == 0) goto L_0x0013
            java.lang.Object r0 = r2.get(r3)     // Catch:{ all -> 0x0016 }
            if (r0 == 0) goto L_0x0011
        L_0x000f:
            monitor-exit(r1)     // Catch:{ all -> 0x0016 }
        L_0x0010:
            return r0
        L_0x0011:
            r0 = r4
            goto L_0x000f
        L_0x0013:
            monitor-exit(r1)     // Catch:{ all -> 0x0016 }
            r0 = 0
            goto L_0x0010
        L_0x0016:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0016 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdmf.zza(java.util.HashMap, java.lang.String, java.lang.Object):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0030, code lost:
        if (zzlnl == false) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0038, code lost:
        if (zzlnf.isEmpty() == false) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003a, code lost:
        zzlnf.putAll(zza(r9, zzlnm));
        zzlnl = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004e, code lost:
        if (zzlnf.containsKey(r10) == false) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0050, code lost:
        r0 = (java.lang.String) zzlnf.get(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0058, code lost:
        if (r0 == null) goto L_0x005b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x005a, code lost:
        r2 = r0;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zza(android.content.ContentResolver r9, java.lang.String r10, java.lang.String r11) {
        /*
            r3 = 0
            r8 = 1
            r2 = 0
            java.lang.Class<com.google.android.gms.internal.zzdmf> r1 = com.google.android.gms.internal.zzdmf.class
            monitor-enter(r1)
            zza(r9)     // Catch:{ all -> 0x005d }
            java.lang.Object r6 = zzlnk     // Catch:{ all -> 0x005d }
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzlnf     // Catch:{ all -> 0x005d }
            boolean r0 = r0.containsKey(r10)     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0020
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzlnf     // Catch:{ all -> 0x005d }
            java.lang.Object r0 = r0.get(r10)     // Catch:{ all -> 0x005d }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x001e
            r2 = r0
        L_0x001e:
            monitor-exit(r1)     // Catch:{ all -> 0x005d }
        L_0x001f:
            return r2
        L_0x0020:
            java.lang.String[] r4 = zzlnm     // Catch:{ all -> 0x005d }
            int r5 = r4.length     // Catch:{ all -> 0x005d }
            r0 = r3
        L_0x0024:
            if (r0 >= r5) goto L_0x0065
            r7 = r4[r0]     // Catch:{ all -> 0x005d }
            boolean r7 = r10.startsWith(r7)     // Catch:{ all -> 0x005d }
            if (r7 == 0) goto L_0x0062
            boolean r0 = zzlnl     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x003a
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzlnf     // Catch:{ all -> 0x005d }
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0060
        L_0x003a:
            java.lang.String[] r0 = zzlnm     // Catch:{ all -> 0x005d }
            java.util.HashMap<java.lang.String, java.lang.String> r3 = zzlnf     // Catch:{ all -> 0x005d }
            java.util.Map r0 = zza(r9, r0)     // Catch:{ all -> 0x005d }
            r3.putAll(r0)     // Catch:{ all -> 0x005d }
            r0 = 1
            zzlnl = r0     // Catch:{ all -> 0x005d }
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzlnf     // Catch:{ all -> 0x005d }
            boolean r0 = r0.containsKey(r10)     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0060
            java.util.HashMap<java.lang.String, java.lang.String> r0 = zzlnf     // Catch:{ all -> 0x005d }
            java.lang.Object r0 = r0.get(r10)     // Catch:{ all -> 0x005d }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x005b
            r2 = r0
        L_0x005b:
            monitor-exit(r1)     // Catch:{ all -> 0x005d }
            goto L_0x001f
        L_0x005d:
            r0 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x005d }
            throw r0
        L_0x0060:
            monitor-exit(r1)     // Catch:{ all -> 0x005d }
            goto L_0x001f
        L_0x0062:
            int r0 = r0 + 1
            goto L_0x0024
        L_0x0065:
            monitor-exit(r1)     // Catch:{ all -> 0x005d }
            android.net.Uri r1 = CONTENT_URI
            java.lang.String[] r4 = new java.lang.String[r8]
            r4[r3] = r10
            r0 = r9
            r3 = r2
            r5 = r2
            android.database.Cursor r1 = r0.query(r1, r2, r3, r4, r5)
            if (r1 == 0) goto L_0x007b
            boolean r0 = r1.moveToFirst()     // Catch:{ all -> 0x00a0 }
            if (r0 != 0) goto L_0x0085
        L_0x007b:
            r0 = 0
            zza(r6, r10, r0)     // Catch:{ all -> 0x00a0 }
            if (r1 == 0) goto L_0x001f
            r1.close()
            goto L_0x001f
        L_0x0085:
            r0 = 1
            java.lang.String r0 = r1.getString(r0)     // Catch:{ all -> 0x00a0 }
            if (r0 == 0) goto L_0x0094
            r3 = 0
            boolean r3 = r0.equals(r3)     // Catch:{ all -> 0x00a0 }
            if (r3 == 0) goto L_0x0094
            r0 = r2
        L_0x0094:
            zza(r6, r10, r0)     // Catch:{ all -> 0x00a0 }
            if (r0 == 0) goto L_0x009a
            r2 = r0
        L_0x009a:
            if (r1 == 0) goto L_0x001f
            r1.close()
            goto L_0x001f
        L_0x00a0:
            r0 = move-exception
            if (r1 == 0) goto L_0x00a6
            r1.close()
        L_0x00a6:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdmf.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(zzlnb, null, null, strArr, null);
        TreeMap treeMap = new TreeMap();
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    treeMap.put(query.getString(0), query.getString(1));
                } finally {
                    query.close();
                }
            }
        }
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzlnf == null) {
            zzlne.set(false);
            zzlnf = new HashMap<>();
            zzlnk = new Object();
            zzlnl = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new zzdmg(null));
        } else if (zzlne.getAndSet(false)) {
            zzlnf.clear();
            zzlng.clear();
            zzlnh.clear();
            zzlni.clear();
            zzlnj.clear();
            zzlnk = new Object();
            zzlnl = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (zzdmf.class) {
            if (obj == zzlnk) {
                zzlnf.put(str, str2);
            }
        }
    }

    private static <T> void zza(Object obj, HashMap<String, T> hashMap, String str, T t) {
        synchronized (zzdmf.class) {
            if (obj == zzlnk) {
                hashMap.put(str, t);
                zzlnf.remove(str);
            }
        }
    }

    public static boolean zza(ContentResolver contentResolver, String str, boolean z) {
        Object zzb = zzb(contentResolver);
        Boolean bool = (Boolean) zza(zzlng, str, (T) Boolean.valueOf(z));
        if (bool != null) {
            return bool.booleanValue();
        }
        String zza = zza(contentResolver, str, (String) null);
        if (zza != null && !zza.equals("")) {
            if (zzlnc.matcher(zza).matches()) {
                bool = Boolean.valueOf(true);
                z = true;
            } else if (zzlnd.matcher(zza).matches()) {
                bool = Boolean.valueOf(false);
                z = false;
            } else {
                Log.w("Gservices", "attempt to read gservices key " + str + " (value \"" + zza + "\") as boolean");
            }
        }
        zza(zzb, zzlng, str, bool);
        return z;
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (zzdmf.class) {
            zza(contentResolver);
            obj = zzlnk;
        }
        return obj;
    }
}
