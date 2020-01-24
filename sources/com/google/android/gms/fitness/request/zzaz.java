package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public final class zzaz extends zzbfm {
    public static final Creator<zzaz> CREATOR = new zzba();
    private final int zzeck;
    private final Session zzgzp;
    private final zzbyf zzhgc;

    zzaz(int i, Session session, IBinder iBinder) {
        this.zzeck = i;
        this.zzgzp = session;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    public zzaz(Session session, zzbyf zzbyf) {
        zzbq.checkArgument(session.getStartTime(TimeUnit.MILLISECONDS) < System.currentTimeMillis(), "Cannot start a session in the future");
        zzbq.checkArgument(session.isOngoing(), "Cannot start a session which has already ended");
        this.zzeck = 3;
        this.zzgzp = session;
        this.zzhgc = zzbyf;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0010, code lost:
        if (com.google.android.gms.common.internal.zzbg.equal(r2.zzgzp, ((com.google.android.gms.fitness.request.zzaz) r3).zzgzp) != false) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r3 == r2) goto L_0x0012
            boolean r0 = r3 instanceof com.google.android.gms.fitness.request.zzaz
            if (r0 == 0) goto L_0x0014
            com.google.android.gms.fitness.request.zzaz r3 = (com.google.android.gms.fitness.request.zzaz) r3
            com.google.android.gms.fitness.data.Session r0 = r2.zzgzp
            com.google.android.gms.fitness.data.Session r1 = r3.zzgzp
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.request.zzaz.equals(java.lang.Object):boolean");
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzgzp});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("session", this.zzgzp).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzgzp, i, false);
        zzbfp.zza(parcel, 2, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
