package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;

public class Subscription extends zzbfm {
    public static final Creator<Subscription> CREATOR = new zzah();
    private final int zzeck;
    private final DataSource zzhbr;
    private final DataType zzhbs;
    private final long zzhdy;
    private final int zzhdz;

    public static class zza {
        /* access modifiers changed from: private */
        public DataSource zzhbr;
        /* access modifiers changed from: private */
        public DataType zzhbs;
        /* access modifiers changed from: private */
        public long zzhdy = -1;
        /* access modifiers changed from: private */
        public int zzhdz = 2;

        public final zza zza(DataSource dataSource) {
            this.zzhbr = dataSource;
            return this;
        }

        public final zza zza(DataType dataType) {
            this.zzhbs = dataType;
            return this;
        }

        public final Subscription zzaqr() {
            boolean z = false;
            zzbq.zza((this.zzhbr == null && this.zzhbs == null) ? false : true, (Object) "Must call setDataSource() or setDataType()");
            if (this.zzhbs == null || this.zzhbr == null || this.zzhbs.equals(this.zzhbr.getDataType())) {
                z = true;
            }
            zzbq.zza(z, (Object) "Specified data type is incompatible with specified data source");
            return new Subscription(this);
        }
    }

    Subscription(int i, DataSource dataSource, DataType dataType, long j, int i2) {
        this.zzeck = i;
        this.zzhbr = dataSource;
        this.zzhbs = dataType;
        this.zzhdy = j;
        this.zzhdz = i2;
    }

    private Subscription(zza zza2) {
        this.zzeck = 1;
        this.zzhbs = zza2.zzhbs;
        this.zzhbr = zza2.zzhbr;
        this.zzhdy = zza2.zzhdy;
        this.zzhdz = zza2.zzhdz;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof Subscription)) {
                return false;
            }
            Subscription subscription = (Subscription) obj;
            if (!(zzbg.equal(this.zzhbr, subscription.zzhbr) && zzbg.equal(this.zzhbs, subscription.zzhbs) && this.zzhdy == subscription.zzhdy && this.zzhdz == subscription.zzhdz)) {
                return false;
            }
        }
        return true;
    }

    public DataSource getDataSource() {
        return this.zzhbr;
    }

    public DataType getDataType() {
        return this.zzhbs;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhbr, this.zzhbr, Long.valueOf(this.zzhdy), Integer.valueOf(this.zzhdz)});
    }

    public String toDebugString() {
        String str = "Subscription{%s}";
        Object[] objArr = new Object[1];
        objArr[0] = this.zzhbr == null ? this.zzhbs.getName() : this.zzhbr.toDebugString();
        return String.format(str, objArr);
    }

    public String toString() {
        return zzbg.zzx(this).zzg("dataSource", this.zzhbr).zzg("dataType", this.zzhbs).zzg("samplingIntervalMicros", Long.valueOf(this.zzhdy)).zzg("accuracyMode", Integer.valueOf(this.zzhdz)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getDataSource(), i, false);
        zzbfp.zza(parcel, 2, (Parcelable) getDataType(), i, false);
        zzbfp.zza(parcel, 3, this.zzhdy);
        zzbfp.zzc(parcel, 4, this.zzhdz);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }

    public final DataType zzaqq() {
        return this.zzhbs == null ? this.zzhbr.getDataType() : this.zzhbs;
    }
}
