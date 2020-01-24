package com.google.android.gms.internal;

import android.os.DeadObjectException;
import android.os.IInterface;

final class zzcdu implements zzcfu<zzcez> {
    private /* synthetic */ zzcdt zziku;

    zzcdu(zzcdt zzcdt) {
        this.zziku = zzcdt;
    }

    public final void zzakm() {
        this.zziku.zzakm();
    }

    public final /* synthetic */ IInterface zzakn() throws DeadObjectException {
        return (zzcez) this.zziku.zzakn();
    }
}
