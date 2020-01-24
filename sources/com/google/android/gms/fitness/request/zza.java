package com.google.android.gms.fitness.request;

import android.os.RemoteException;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.BleDevice;

public final class zza extends zzaf {
    private final zzci<BleScanCallback> zzhfw;

    private zza(zzci<BleScanCallback> zzci) {
        this.zzhfw = (zzci) zzbq.checkNotNull(zzci);
    }

    /* synthetic */ zza(zzci zzci, zzb zzb) {
        this(zzci);
    }

    public final void onDeviceFound(BleDevice bleDevice) throws RemoteException {
        this.zzhfw.zza(new zzb(this, bleDevice));
    }

    public final void onScanStopped() throws RemoteException {
        this.zzhfw.zza(new zzc(this));
    }

    public final void release() {
        this.zzhfw.clear();
    }
}
