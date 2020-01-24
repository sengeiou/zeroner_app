package com.google.android.gms.fitness.service;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.internal.zzbfn;

public final class zzb implements Creator<FitnessSensorServiceRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long j = 0;
        IBinder iBinder = null;
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        long j2 = 0;
        DataSource dataSource = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    dataSource = (DataSource) zzbfn.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 2:
                    iBinder = zzbfn.zzr(parcel, readInt);
                    break;
                case 3:
                    j2 = zzbfn.zzi(parcel, readInt);
                    break;
                case 4:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 1000:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new FitnessSensorServiceRequest(i, dataSource, iBinder, j2, j);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new FitnessSensorServiceRequest[i];
    }
}
