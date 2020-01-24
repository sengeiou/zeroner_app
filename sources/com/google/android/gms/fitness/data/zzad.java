package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;

public final class zzad implements Creator<Session> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        long j = 0;
        int i = 0;
        Long l = null;
        int zzd = zzbfn.zzd(parcel);
        zzb zzb = null;
        String str = null;
        String str2 = null;
        String str3 = null;
        long j2 = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    j2 = zzbfn.zzi(parcel, readInt);
                    break;
                case 2:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 3:
                    str3 = zzbfn.zzq(parcel, readInt);
                    break;
                case 4:
                    str2 = zzbfn.zzq(parcel, readInt);
                    break;
                case 5:
                    str = zzbfn.zzq(parcel, readInt);
                    break;
                case 7:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 8:
                    zzb = (zzb) zzbfn.zza(parcel, readInt, zzb.CREATOR);
                    break;
                case 9:
                    l = zzbfn.zzj(parcel, readInt);
                    break;
                case 1000:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new Session(i2, j2, j, str3, str2, str, i, zzb, l);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Session[i];
    }
}
