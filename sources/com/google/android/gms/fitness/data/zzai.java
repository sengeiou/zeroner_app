package com.google.android.gms.fitness.data;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbfn;

public final class zzai implements Creator<Value> {
    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        boolean z = false;
        byte[] bArr = null;
        int zzd = zzbfn.zzd(parcel);
        float f = 0.0f;
        float[] fArr = null;
        int[] iArr = null;
        Bundle bundle = null;
        String str = null;
        int i = 0;
        int i2 = 0;
        while (parcel.dataPosition() < zzd) {
            int readInt = parcel.readInt();
            switch (65535 & readInt) {
                case 1:
                    i = zzbfn.zzg(parcel, readInt);
                    break;
                case 2:
                    z = zzbfn.zzc(parcel, readInt);
                    break;
                case 3:
                    f = zzbfn.zzl(parcel, readInt);
                    break;
                case 4:
                    str = zzbfn.zzq(parcel, readInt);
                    break;
                case 5:
                    bundle = zzbfn.zzs(parcel, readInt);
                    break;
                case 6:
                    iArr = zzbfn.zzw(parcel, readInt);
                    break;
                case 7:
                    fArr = zzbfn.zzy(parcel, readInt);
                    break;
                case 8:
                    bArr = zzbfn.zzt(parcel, readInt);
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
        return new Value(i2, i, z, f, str, bundle, iArr, fArr, bArr);
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new Value[i];
    }
}
