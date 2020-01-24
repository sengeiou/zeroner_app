package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzbfo;
import java.util.HashSet;

public final class zzt implements Creator<zzs> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        String str = null;
        int zzd = zzbfn.zzd(parcel);
        HashSet hashSet = new HashSet();
        int i = 0;
        String str2 = null;
        zzu zzu = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzbfn.zzg(parcel, readInt);
                    hashSet.add(Integer.valueOf(1));
                    break;
                case 2:
                    zzu zzu2 = (zzu) zzbfn.zza(parcel, readInt, zzu.CREATOR);
                    hashSet.add(Integer.valueOf(2));
                    zzu = zzu2;
                    break;
                case 3:
                    str2 = zzbfn.zzq(parcel, readInt);
                    hashSet.add(Integer.valueOf(3));
                    break;
                case 4:
                    str = zzbfn.zzq(parcel, readInt);
                    hashSet.add(Integer.valueOf(4));
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        if (parcel.dataPosition() == zzd) {
            return new zzs(hashSet, i, zzu, str2, str);
        }
        throw new zzbfo("Overread allowed size end=" + zzd, parcel);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzs[i];
    }
}
