package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyc;
import com.google.android.gms.internal.zzbyd;
import java.util.Arrays;
import org.litepal.util.Const.TableSchema;

public final class zzbb extends zzbfm {
    public static final Creator<zzbb> CREATOR = new zzbc();
    private final String mName;
    private final int zzeck;
    private final String zzhdu;
    private final zzbyc zzhic;

    zzbb(int i, String str, String str2, IBinder iBinder) {
        this.zzeck = i;
        this.mName = str;
        this.zzhdu = str2;
        this.zzhic = zzbyd.zzaz(iBinder);
    }

    public zzbb(String str, String str2, zzbyc zzbyc) {
        this.zzeck = 3;
        this.mName = str;
        this.zzhdu = str2;
        this.zzhic = zzbyc;
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof zzbb)) {
                return false;
            }
            zzbb zzbb = (zzbb) obj;
            if (!(zzbg.equal(this.mName, zzbb.mName) && zzbg.equal(this.zzhdu, zzbb.zzhdu))) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.mName, this.zzhdu});
    }

    public final String toString() {
        return zzbg.zzx(this).zzg(TableSchema.COLUMN_NAME, this.mName).zzg("identifier", this.zzhdu).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.mName, false);
        zzbfp.zza(parcel, 2, this.zzhdu, false);
        zzbfp.zza(parcel, 3, this.zzhic == null ? null : this.zzhic.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
