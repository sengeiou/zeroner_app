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

public final class zzbwh extends zzbvc<zzbxs> {
    public static final Api<NoOptions> API = new Api<>("Fitness.SENSORS_API", new zzbwj(), zzebf);
    private static zzf<zzbwh> zzebf = new zzf<>();
    public static final Api<FitnessOptions> zzhen = new Api<>("Fitness.SENSORS_CLIENT", new zzbwl(), zzebf);

    private zzbwh(Context context, Looper looper, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 55, connectionCallbacks, onConnectionFailedListener, zzr);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitSensorsApi");
        return queryLocalInterface instanceof zzbxs ? (zzbxs) queryLocalInterface : new zzbxt(iBinder);
    }

    public final String zzhi() {
        return "com.google.android.gms.fitness.SensorsApi";
    }

    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitSensorsApi";
    }
}
