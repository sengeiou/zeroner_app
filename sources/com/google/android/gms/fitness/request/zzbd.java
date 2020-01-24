package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.Arrays;

public final class zzbd extends zzbfm {
    public static final Creator<zzbd> CREATOR = new zzbe();
    private final int zzeck;
    private final PendingIntent zzeeo;
    private final zzbyf zzhgc;

    zzbd(int i, PendingIntent pendingIntent, IBinder iBinder) {
        this.zzeck = i;
        this.zzeeo = pendingIntent;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    public zzbd(PendingIntent pendingIntent, zzbyf zzbyf) {
        this.zzeck = 5;
        this.zzeeo = pendingIntent;
        this.zzhgc = zzbyf;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0010, code lost:
        if (com.google.android.gms.common.internal.zzbg.equal(r2.zzeeo, ((com.google.android.gms.fitness.request.zzbd) r3).zzeeo) != false) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r2 == r3) goto L_0x0012
            boolean r0 = r3 instanceof com.google.android.gms.fitness.request.zzbd
            if (r0 == 0) goto L_0x0014
            com.google.android.gms.fitness.request.zzbd r3 = (com.google.android.gms.fitness.request.zzbd) r3
            android.app.PendingIntent r0 = r2.zzeeo
            android.app.PendingIntent r1 = r3.zzeeo
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.request.zzbd.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzeeo});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("pendingIntent", this.zzeeo).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzeeo, i, false);
        zzbfp.zza(parcel, 2, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
