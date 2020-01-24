package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzj implements Creator<PasswordSpecification> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int i = 0;
        ArrayList arrayList = null;
        int zzd = zzbfn.zzd(parcel);
        int i2 = 0;
        List list = null;
        String str = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzbfn.zzq(parcel, readInt);
                    break;
                case 2:
                    list = zzbfn.zzac(parcel, readInt);
                    break;
                case 3:
                    arrayList = zzbfn.zzab(parcel, readInt);
                    break;
                case 4:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                case 5:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new PasswordSpecification(str, list, arrayList, i2, i);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PasswordSpecification[i];
    }
}
