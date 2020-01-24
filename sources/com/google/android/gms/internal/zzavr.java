package com.google.android.gms.internal;

import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.api.Status;

final class zzavr extends zzavn {
    private /* synthetic */ zzavq zzege;

    zzavr(zzavq zzavq) {
        this.zzege = zzavq;
    }

    public final void zza(Status status, Credential credential) {
        this.zzege.setResult(new zzavo(status, credential));
    }

    public final void zze(Status status) {
        this.zzege.setResult(zzavo.zzf(status));
    }
}
