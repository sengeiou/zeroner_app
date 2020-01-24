package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.location.LocationStatusCodes;

final class zzcfm extends zzcey {
    private zzn<Status> zzilv;

    public zzcfm(zzn<Status> zzn) {
        this.zzilv = zzn;
    }

    private final void zzdx(int i) {
        if (this.zzilv == null) {
            Log.wtf("LocationClientImpl", "onRemoveGeofencesResult called multiple times");
            return;
        }
        this.zzilv.setResult(LocationStatusCodes.zzdw(LocationStatusCodes.zzdv(i)));
        this.zzilv = null;
    }

    public final void zza(int i, PendingIntent pendingIntent) {
        zzdx(i);
    }

    public final void zza(int i, String[] strArr) {
        Log.wtf("LocationClientImpl", "Unexpected call to onAddGeofencesResult");
    }

    public final void zzb(int i, String[] strArr) {
        zzdx(i);
    }
}
