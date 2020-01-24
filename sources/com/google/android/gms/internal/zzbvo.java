package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.fitness.FitnessOptions;

final class zzbvo extends zza<zzbvk, FitnessOptions> {
    private zzbvo() {
    }

    public final /* synthetic */ zze zza(Context context, Looper looper, zzr zzr, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        return new zzbvk(context, looper, zzr, connectionCallbacks, onConnectionFailedListener);
    }
}
