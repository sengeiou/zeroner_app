package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.fitness.FitnessOptions;

public final class zzbwb extends zzbvc<zzbxq> {
    public static final Api<NoOptions> API = new Api<>("Fitness.RECORDING_API", new zzbwd(), zzebf);
    private static zzf<zzbwb> zzebf = new zzf<>();
    public static final Api<FitnessOptions> zzhen = new Api<>("Fitness.RECORDING_CLIENT", new zzbwf(), zzebf);

    private zzbwb(Context context, Looper looper, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 56, connectionCallbacks, onConnectionFailedListener, zzr);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitRecordingApi");
        return queryLocalInterface instanceof zzbxq ? (zzbxq) queryLocalInterface : new zzbxr(iBinder);
    }

    public final String zzhi() {
        return "com.google.android.gms.fitness.RecordingApi";
    }

    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitRecordingApi";
    }
}
