package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;
import java.util.ArrayList;
import java.util.List;

public final class zzk implements Creator<PolygonOptions> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        ArrayList arrayList = null;
        float f = 0.0f;
        int i = 0;
        int zzd = zzbfn.zzd(parcel);
        ArrayList arrayList2 = new ArrayList();
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        int i2 = 0;
        int i3 = 0;
        float f2 = 0.0f;
        List list = null;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 2:
                    list = zzbfn.zzc(parcel, readInt, LatLng.CREATOR);
                    break;
                case 3:
                    zzbfn.zza(parcel, readInt, (List) arrayList2, getClass().getClassLoader());
                    break;
                case 4:
                    f2 = zzbfn.zzl(parcel, readInt);
                    break;
                case 5:
                    i3 = zzbfn.zzg(parcel, readInt);
                    break;
                case 6:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                case 7:
                    f = zzbfn.zzl(parcel, readInt);
                    break;
                case 8:
                    z3 = zzbfn.zzc(parcel, readInt);
                    break;
                case 9:
                    z2 = zzbfn.zzc(parcel, readInt);
                    break;
                case 10:
                    z = zzbfn.zzc(parcel, readInt);
                    break;
                case 11:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 12:
                    arrayList = zzbfn.zzc(parcel, readInt, PatternItem.CREATOR);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new PolygonOptions(list, arrayList2, f2, i3, i2, f, z3, z2, z, i, arrayList);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new PolygonOptions[i];
    }
}
