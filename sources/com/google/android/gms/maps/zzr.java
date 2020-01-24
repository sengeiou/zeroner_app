package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.internal.zzbt;

final class zzr extends zzbt {
    private /* synthetic */ SnapshotReadyCallback zzirr;

    zzr(GoogleMap googleMap, SnapshotReadyCallback snapshotReadyCallback) {
        this.zzirr = snapshotReadyCallback;
    }

    public final void onSnapshotReady(Bitmap bitmap) throws RemoteException {
        this.zzirr.onSnapshotReady(bitmap);
    }

    public final void zzz(IObjectWrapper iObjectWrapper) throws RemoteException {
        this.zzirr.onSnapshotReady((Bitmap) zzn.zzx(iObjectWrapper));
    }
}
