package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzr;

final class zzcxb extends zza<zzcxn, zzcxe> {
    zzcxb() {
    }

    public final /* synthetic */ zze zza(Context context, Looper looper, zzr zzr, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        zzcxe zzcxe = (zzcxe) obj;
        return new zzcxn(context, looper, true, zzr, zzcxe == null ? zzcxe.zzkbs : zzcxe, connectionCallbacks, onConnectionFailedListener);
    }
}
