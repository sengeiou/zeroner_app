package com.google.android.gms.internal;

import android.net.Uri;

public final class zzcup {
    /* access modifiers changed from: private */
    public final String zzjxi;
    /* access modifiers changed from: private */
    public final Uri zzjxj;
    /* access modifiers changed from: private */
    public final String zzjxk;
    /* access modifiers changed from: private */
    public final String zzjxl;
    /* access modifiers changed from: private */
    public final boolean zzjxm;
    /* access modifiers changed from: private */
    public final boolean zzjxn;

    public zzcup(Uri uri) {
        this(null, uri, "", "", false, false);
    }

    private zzcup(String str, Uri uri, String str2, String str3, boolean z, boolean z2) {
        this.zzjxi = str;
        this.zzjxj = uri;
        this.zzjxk = str2;
        this.zzjxl = str3;
        this.zzjxm = z;
        this.zzjxn = z2;
    }

    public final zzcui<String> zzaw(String str, String str2) {
        return zzcui.zza(this, str, null);
    }

    public final zzcup zzku(String str) {
        if (this.zzjxm) {
            throw new IllegalStateException("Cannot set GServices prefix and skip GServices");
        }
        return new zzcup(this.zzjxi, this.zzjxj, str, this.zzjxl, this.zzjxm, this.zzjxn);
    }

    public final zzcup zzkv(String str) {
        return new zzcup(this.zzjxi, this.zzjxj, this.zzjxk, str, this.zzjxm, this.zzjxn);
    }
}
