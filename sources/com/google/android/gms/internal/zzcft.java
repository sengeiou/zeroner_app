package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.github.mikephil.charting.utils.Utils;

public final class zzcft implements Creator<zzcfs> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        int zzd = zzbfn.zzd(parcel);
        String str = null;
        int i = 0;
        short s = 0;
        double d = Utils.DOUBLE_EPSILON;
        double d2 = Utils.DOUBLE_EPSILON;
        float f = 0.0f;
        long j = 0;
        int i2 = 0;
        int i3 = -1;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    str = zzbfn.zzq(parcel, readInt);
                    break;
                case 2:
                    j = zzbfn.zzi(parcel, readInt);
                    break;
                case 3:
                    s = zzbfn.zzf(parcel, readInt);
                    break;
                case 4:
                    d = zzbfn.zzn(parcel, readInt);
                    break;
                case 5:
                    d2 = zzbfn.zzn(parcel, readInt);
                    break;
                case 6:
                    f = zzbfn.zzl(parcel, readInt);
                    break;
                case 7:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 8:
                    i2 = zzbfn.zzg(parcel, readInt);
                    break;
                case 9:
                    i3 = zzbfn.zzg(parcel, readInt);
                    break;
                default:
                    zzbfn.zzb(parcel, readInt);
                    break;
            }
        }
        zzbfn.zzaf(parcel, zzd);
        return new zzcfs(str, i, s, d, d2, f, j, i2, i3);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new zzcfs[i];
    }
}
