package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzbfo;
import java.util.ArrayList;
import java.util.HashSet;

public final class zzn implements Creator<zzm> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        zzp zzp = null;
        int i = 0;
        int zzd = zzbfn.zzd(parcel);
        HashSet hashSet = new HashSet();
        ArrayList arrayList = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i2 = zzbfn.zzg(parcel, readInt);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case 2:
                    arrayList = zzbfn.zzc(parcel, readInt, zzs.CREATOR);
                    hashSet.add(Integer.valueOf(2));
                    break;
                case 3:
                    i = zzbfn.zzg(parcel, readInt);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case 4:
                    zzp zzp2 = (zzp) zzbfn.zza(parcel, readInt, zzp.CREATOR);
                    hashSet.add(Integer.valueOf(4));
                    zzp = zzp2;
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        if (parcel.dataPosition() == zzd) {
            return new zzm(hashSet, i2, arrayList, i, zzp);
        }
        throw new zzbfo("Overread allowed size end=" + zzd, parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzm[i];
    }
}
