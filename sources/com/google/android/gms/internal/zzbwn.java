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

public final class zzbwn extends zzbvc<zzbxu> {
    public static final Api<NoOptions> API = new Api<>("Fitness.SESSIONS_API", new zzbwp(), zzebf);
    private static zzf<zzbwn> zzebf = new zzf<>();
    public static final Api<FitnessOptions> zzhen = new Api<>("Fitness.SESSIONS_CLIENT", new zzbwr(), zzebf);

    private zzbwn(Context context, Looper looper, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 58, connectionCallbacks, onConnectionFailedListener, zzr);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitSessionsApi");
        return queryLocalInterface instanceof zzbxu ? (zzbxu) queryLocalInterface : new zzbxv(iBinder);
    }

    public final String zzhi() {
        return "com.google.android.gms.fitness.SessionsApi";
    }

    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitSessionsApi";
    }
}
