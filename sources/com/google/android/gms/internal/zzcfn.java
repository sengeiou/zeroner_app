package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.location.LocationSettingsResult;

final class zzcfn extends zzcfc {
    private zzn<LocationSettingsResult> zzilv;

    public zzcfn(zzn<LocationSettingsResult> zzn) {
        zzbq.checkArgument(zzn != null, "listener can't be null.");
        this.zzilv = zzn;
    }

    public final void zza(LocationSettingsResult locationSettingsResult) throws RemoteException {
        this.zzilv.setResult(locationSettingsResult);
        this.zzilv = null;
    }
}
