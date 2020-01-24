package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class zzctv {
    private static String[] zzhwc = {"key", "value"};
    private static final ConcurrentHashMap<Uri, zzctv> zzjwe = new ConcurrentHashMap<>();
    private final Uri uri;
    private final ContentResolver zzjwf;
    private final ContentObserver zzjwg;
    private final Object zzjwh = new Object();
    private volatile Map<String, String> zzjwi;

    private zzctv(ContentResolver contentResolver, Uri uri2) {
        this.zzjwf = contentResolver;
        this.uri = uri2;
        this.zzjwg = new zzctw(this, null);
    }

    public static zzctv zza(ContentResolver contentResolver, Uri uri2) {
        zzctv zzctv = (zzctv) zzjwe.get(uri2);
        if (zzctv != null) {
            return zzctv;
        }
        zzctv zzctv2 = new zzctv(contentResolver, uri2);
        zzctv zzctv3 = (zzctv) zzjwe.putIfAbsent(uri2, zzctv2);
        if (zzctv3 != null) {
            return zzctv3;
        }
        zzctv2.zzjwf.registerContentObserver(zzctv2.uri, false, zzctv2.zzjwg);
        return zzctv2;
    }

    private final Map<String, String> zzbco() {
        HashMap hashMap = new HashMap();
        Cursor query = this.zzjwf.query(this.uri, zzhwc, null, null, null);
        if (query != null) {
            while (query.moveToNext()) {
                try {
                    hashMap.put(query.getString(0), query.getString(1));
                } finally {
                    query.close();
                }
            }
        }
        return hashMap;
    }

    public final Map<String, String> zzbcm() {
        Map<String, String> zzbco = zzcui.zzg("gms:phenotype:phenotype_flag:debug_disable_caching", false) ? zzbco() : this.zzjwi;
        if (zzbco == null) {
            synchronized (this.zzjwh) {
                zzbco = this.zzjwi;
                if (zzbco == null) {
                    zzbco = zzbco();
                    this.zzjwi = zzbco;
                }
            }
        }
        return zzbco;
    }

    public final void zzbcn() {
        synchronized (this.zzjwh) {
            this.zzjwi = null;
        }
    }
}
