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

public final class zzax extends zzbfm {
    public static final Creator<zzax> CREATOR = new zzay();
    private final int zzeck;
    private final PendingIntent zzeeo;
    private final zzbyf zzhgc;
    private final int zzhib;

    zzax(int i, PendingIntent pendingIntent, IBinder iBinder, int i2) {
        this.zzeck = i;
        this.zzeeo = pendingIntent;
        this.zzhgc = iBinder == null ? null : zzbyg.zzba(iBinder);
        this.zzhib = i2;
    }

    public zzax(PendingIntent pendingIntent, zzbyf zzbyf, int i) {
        this.zzeck = 6;
        this.zzeeo = pendingIntent;
        this.zzhgc = zzbyf;
        this.zzhib = i;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof zzax)) {
                return false;
            }
            zzax zzax = (zzax) obj;
            if (!(this.zzhib == zzax.zzhib && zzbg.equal(this.zzeeo, zzax.zzeeo))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzeeo, Integer.valueOf(this.zzhib)});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("pendingIntent", this.zzeeo).zzg("sessionRegistrationOption", Integer.valueOf(this.zzhib)).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) this.zzeeo, i, false);
        zzbfp.zza(parcel, 2, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 4, this.zzhib);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
