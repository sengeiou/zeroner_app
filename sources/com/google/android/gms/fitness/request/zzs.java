package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbxc;
import com.google.android.gms.internal.zzbxd;
import java.util.Arrays;
import org.litepal.util.Const.TableSchema;

public final class zzs extends zzbfm {
    public static final Creator<zzs> CREATOR = new zzt();
    private final String mName;
    private final int zzeck;
    private final zzbxc zzhha;

    zzs(int i, String str, IBinder iBinder) {
        this.zzeck = i;
        this.mName = str;
        this.zzhha = zzbxd.zzav(iBinder);
    }

    public zzs(String str, zzbxc zzbxc) {
        this.zzeck = 3;
        this.mName = str;
        this.zzhha = zzbxc;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0010, code lost:
        if (com.google.android.gms.common.internal.zzbg.equal(r2.mName, ((com.google.android.gms.fitness.request.zzs) r3).mName) != false) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r3 == r2) goto L_0x0012
            boolean r0 = r3 instanceof com.google.android.gms.fitness.request.zzs
            if (r0 == 0) goto L_0x0014
            com.google.android.gms.fitness.request.zzs r3 = (com.google.android.gms.fitness.request.zzs) r3
            java.lang.String r0 = r2.mName
            java.lang.String r1 = r3.mName
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.request.zzs.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.mName});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg(TableSchema.COLUMN_NAME, this.mName).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.mName, false);
        zzbfp.zza(parcel, 3, this.zzhha.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
