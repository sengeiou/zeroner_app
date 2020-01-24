package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.location.LocationRequest;
import java.util.ArrayList;

public final class zzap implements Creator<zzao> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        int i = 0;
        DataSource dataSource = null;
        DataType dataType = null;
        IBinder iBinder = null;
        int i2 = 0;
        int i3 = 0;
        long j = 0;
        long j2 = 0;
        PendingIntent pendingIntent = null;
        long j3 = 0;
        int i4 = 0;
        ArrayList arrayList = null;
        long j4 = 0;
        IBinder iBinder2 = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    dataSource = (DataSource) zzbfn.zza(parcel, readInt, DataSource.CREATOR);
                    break;
                case 2:
                    dataType = (DataType) zzbfn.zza(parcel, readInt, DataType.CREATOR);
                    break;
                case 3:
                    iBinder = zzbfn.zzr(parcel, readInt);
                    break;
                case 4:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                case 5:
                    i3 = zzbfn.zzg(parcel, readInt);
                    break;
                case 6:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 7:
                    j2 = zzbfn.zzi(parcel, readInt);
                    break;
                case 8:
                    pendingIntent = (PendingIntent) zzbfn.zza(parcel, readInt, PendingIntent.CREATOR);
                    break;
                case 9:
                    j3 = zzbfn.zzi(parcel, readInt);
                    break;
                case 10:
                    i4 = zzbfn.zzg(parcel, readInt);
                    break;
                case 11:
                    arrayList = zzbfn.zzc(parcel, readInt, LocationRequest.CREATOR);
                    break;
                case 12:
                    j4 = zzbfn.zzi(parcel, readInt);
                    break;
                case 13:
                    iBinder2 = zzbfn.zzr(parcel, readInt);
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
        return new zzao(i, dataSource, dataType, iBinder, i2, i3, j, j2, pendingIntent, j3, i4, arrayList, j4, iBinder2);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzao[i];
    }
}
