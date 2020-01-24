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
import com.tencent.tinker.android.dx.instruction.Opcodes;

public final class zzbvq extends zzbvc<zzbxm> {
    public static final Api<NoOptions> API = new Api<>("Fitness.GOALS_API", new zzbvs(), zzebf);
    private static zzf<zzbvq> zzebf = new zzf<>();
    public static final Api<FitnessOptions> zzhen = new Api<>("Fitness.GOALS_CLIENT", new zzbvu(), zzebf);

    private zzbvq(Context context, Looper looper, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, Opcodes.NEG_LONG, connectionCallbacks, onConnectionFailedListener, zzr);
    }

    public final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.internal.IGoogleFitGoalsApi");
        return queryLocalInterface instanceof zzbxm ? (zzbxm) queryLocalInterface : new zzbxn(iBinder);
    }

    public final String zzhi() {
        return "com.google.android.gms.fitness.GoalsApi";
    }

    public final String zzhj() {
        return "com.google.android.gms.fitness.internal.IGoogleFitGoalsApi";
    }
}
