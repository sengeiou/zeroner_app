package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.Arrays;

public final class zzk extends zzbfm {
    public static final Creator<zzk> CREATOR = new zzl();
    private final int zzeck;
    private final DataSet zzhdx;
    private final zzbyf zzhgc;
    private final boolean zzhgj;

    zzk(int i, DataSet dataSet, IBinder iBinder, boolean z) {
        this.zzeck = i;
        this.zzhdx = dataSet;
        this.zzhgc = zzbyg.zzba(iBinder);
        this.zzhgj = z;
    }

    public zzk(DataSet dataSet, zzbyf zzbyf, boolean z) {
        this.zzeck = 4;
        this.zzhdx = dataSet;
        this.zzhgc = zzbyf;
        this.zzhgj = z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0010, code lost:
        if (com.google.android.gms.common.internal.zzbg.equal(r2.zzhdx, ((com.google.android.gms.fitness.request.zzk) r3).zzhdx) != false) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r3 == r2) goto L_0x0012
            boolean r0 = r3 instanceof com.google.android.gms.fitness.request.zzk
            if (r0 == 0) goto L_0x0014
            com.google.android.gms.fitness.request.zzk r3 = (com.google.android.gms.fitness.request.zzk) r3
            com.google.android.gms.fitness.data.DataSet r0 = r2.zzhdx
            com.google.android.gms.fitness.data.DataSet r1 = r3.zzhdx
            boolean r0 = com.google.android.gms.common.internal.zzbg.equal(r0, r1)
            if (r0 == 0) goto L_0x0014
        L_0x0012:
            r0 = 1
        L_0x0013:
            return r0
        L_0x0014:
            r0 = 0
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.request.zzk.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhdx});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("dataSet", this.zzhdx).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzhdx, i, false);
        zzbfp.zza(parcel, 2, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zza(parcel, 4, this.zzhgj);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
