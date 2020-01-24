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

public final class zzbve extends zzbvc<zzbxi> {
    public static final Api<NoOptions> API = new Api<>("Fitness.BLE_API", new zzbvg(), zzebf);
    private static zzf<zzbve> zzebf = new zzf<>();
    public static final Api<FitnessOptions> zzhen = new Api<>("Fitness.BLE_CLIENT", new zzbvi(), zzebf);

    private zzbve(Context context, Looper looper, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 59, connectionCallbacks, onConnectionFailedListener, zzr);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitBleApi");
        return queryLocalInterface instanceof zzbxi ? (zzbxi) queryLocalInterface : new zzbxj(iBinder);
    }

    public final String zzhi() {
        return "com.google.android.gms.fitness.BleApi";
    }

    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitBleApi";
    }
}
