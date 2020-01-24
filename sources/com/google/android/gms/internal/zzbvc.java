package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.fitness.zzh;
import java.util.Set;

public abstract class zzbvc<T extends IInterface> extends zzab<T> {
    protected zzbvc(Context context, Looper looper, int i, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, zzr zzr) {
        super(context, looper, i, zzr, connectionCallbacks, onConnectionFailedListener);
    }

    public final boolean zzaay() {
        return !zzi.zzcs(getContext());
    }

    public final boolean zzako() {
        return true;
    }

    /* access modifiers changed from: protected */
    public final Set<Scope> zzb(Set<Scope> set) {
        return zzh.zzi(set);
    }

    public abstract T zzd(IBinder iBinder);

    public abstract String zzhi();

    public abstract String zzhj();
}
