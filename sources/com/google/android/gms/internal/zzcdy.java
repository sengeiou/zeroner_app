package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.location.zze;
import java.util.List;

public final class zzcdy implements Creator<zzcdx> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        zze zze = zzcdx.zzikw;
        List<zzcdv> list = zzcdx.zzikv;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    zze = (zze) zzbfn.zza(parcel, readInt, zze.CREATOR);
                    break;
                case 2:
                    list = zzbfn.zzc(parcel, readInt, zzcdv.CREATOR);
                    break;
                case 3:
                    str = zzbfn.zzq(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new zzcdx(zze, list, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcdx[i];
    }
}
