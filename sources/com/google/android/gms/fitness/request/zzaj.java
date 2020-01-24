package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbxw;
import com.google.android.gms.internal.zzbxx;

public final class zzaj extends zzbfm {
    public static final Creator<zzaj> CREATOR = new zzak();
    private final int zzeck;
    private final DataType zzhbs;
    private final zzbxw zzhhf;

    zzaj(int i, DataType dataType, IBinder iBinder) {
        this.zzeck = i;
        this.zzhbs = dataType;
        this.zzhhf = zzbxx.zzax(iBinder);
    }

    public zzaj(DataType dataType, zzbxw zzbxw) {
        this.zzeck = 3;
        this.zzhbs = dataType;
        this.zzhhf = zzbxw;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzhbs, i, false);
        zzbfp.zza(parcel, 2, this.zzhhf == null ? null : this.zzhhf.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
