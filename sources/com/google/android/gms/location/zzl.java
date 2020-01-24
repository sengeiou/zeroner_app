package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.internal.zzcfs;
import java.util.ArrayList;

public final class zzl implements Creator<GeofencingRequest> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        ArrayList arrayList = null;
        int i = 0;
        String str = "";
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    arrayList = zzbfn.zzc(parcel, readInt, zzcfs.CREATOR);
                    break;
                case 2:
                    i = zzbfn.zzg(parcel, readInt);
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
        return new GeofencingRequest(arrayList, i, str);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new GeofencingRequest[i];
    }
}
