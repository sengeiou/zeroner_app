package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import java.util.Set;

public abstract class zzab<T extends IInterface> extends zzd<T> implements zze, zzaf {
    private final Account zzebz;
    private final Set<Scope> zzehs;
    private final zzr zzfpx;

    protected zzab(Context context, Looper looper, int i, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzag.zzco(context), GoogleApiAvailability.getInstance(), i, zzr, (ConnectionCallbacks) zzbq.checkNotNull(connectionCallbacks), (OnConnectionFailedListener) zzbq.checkNotNull(onConnectionFailedListener));
    }

    private zzab(Context context, Looper looper, zzag zzag, GoogleApiAvailability googleApiAvailability, int i, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, zzag, googleApiAvailability, i, connectionCallbacks == null ? null : new zzac(connectionCallbacks), onConnectionFailedListener == null ? null : new zzad(onConnectionFailedListener), zzr.zzakz());
        this.zzfpx = zzr;
        this.zzebz = zzr.getAccount();
        Set zzakw = zzr.zzakw();
        Set<Scope> zzb = zzb(zzakw);
        for (Scope contains : zzb) {
            if (!zzakw.contains(contains)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        this.zzehs = zzb;
    }

    public final Account getAccount() {
        return this.zzebz;
    }

    public zzc[] zzakl() {
        return new zzc[0];
    }

    /* access modifiers changed from: protected */
    public final Set<Scope> zzakp() {
        return this.zzehs;
    }

    /* access modifiers changed from: protected */
    public final zzr zzalh() {
        return this.zzfpx;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public Set<Scope> zzb(@NonNull Set<Scope> set) {
        return set;
    }
}
