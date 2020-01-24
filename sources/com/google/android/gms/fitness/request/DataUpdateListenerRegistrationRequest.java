package com.google.android.gms.fitness.request;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.Arrays;

public class DataUpdateListenerRegistrationRequest extends zzbfm {
    public static final Creator<DataUpdateListenerRegistrationRequest> CREATOR = new zzv();
    private final int zzeck;
    private final PendingIntent zzeeo;
    private DataSource zzhbr;
    private DataType zzhbs;
    private final zzbyf zzhgc;

    public static class Builder {
        /* access modifiers changed from: private */
        public PendingIntent zzeeo;
        /* access modifiers changed from: private */
        public DataSource zzhbr;
        /* access modifiers changed from: private */
        public DataType zzhbs;

        public DataUpdateListenerRegistrationRequest build() {
            zzbq.zza((this.zzhbr == null && this.zzhbs == null) ? false : true, (Object) "Set either dataSource or dataTYpe");
            zzbq.checkNotNull(this.zzeeo, "pendingIntent must be set");
            return new DataUpdateListenerRegistrationRequest(this);
        }

        public Builder setDataSource(DataSource dataSource) throws NullPointerException {
            zzbq.checkNotNull(dataSource);
            this.zzhbr = dataSource;
            return this;
        }

        public Builder setDataType(DataType dataType) {
            zzbq.checkNotNull(dataType);
            this.zzhbs = dataType;
            return this;
        }

        public Builder setPendingIntent(PendingIntent pendingIntent) {
            zzbq.checkNotNull(pendingIntent);
            this.zzeeo = pendingIntent;
            return this;
        }
    }

    DataUpdateListenerRegistrationRequest(int i, DataSource dataSource, DataType dataType, PendingIntent pendingIntent, IBinder iBinder) {
        this.zzeck = i;
        this.zzhbr = dataSource;
        this.zzhbs = dataType;
        this.zzeeo = pendingIntent;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    private DataUpdateListenerRegistrationRequest(DataSource dataSource, DataType dataType, PendingIntent pendingIntent, IBinder iBinder) {
        this.zzeck = 1;
        this.zzhbr = dataSource;
        this.zzhbs = dataType;
        this.zzeeo = pendingIntent;
        this.zzhgc = zzbyg.zzba(iBinder);
    }

    private DataUpdateListenerRegistrationRequest(Builder builder) {
        this(builder.zzhbr, builder.zzhbs, builder.zzeeo, null);
    }

    public DataUpdateListenerRegistrationRequest(DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest, IBinder iBinder) {
        this(dataUpdateListenerRegistrationRequest.zzhbr, dataUpdateListenerRegistrationRequest.zzhbs, dataUpdateListenerRegistrationRequest.zzeeo, iBinder);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataUpdateListenerRegistrationRequest)) {
                return false;
            }
            DataUpdateListenerRegistrationRequest dataUpdateListenerRegistrationRequest = (DataUpdateListenerRegistrationRequest) obj;
            if (!(zzbg.equal(this.zzhbr, dataUpdateListenerRegistrationRequest.zzhbr) && zzbg.equal(this.zzhbs, dataUpdateListenerRegistrationRequest.zzhbs) && zzbg.equal(this.zzeeo, dataUpdateListenerRegistrationRequest.zzeeo))) {
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

    public PendingIntent getIntent() {
        return this.zzeeo;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhbr, this.zzhbs, this.zzeeo});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("dataSource", this.zzhbr).zzg("dataType", this.zzhbs).zzg("pendingIntent", this.zzeeo).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getDataSource(), i, false);
        zzbfp.zza(parcel, 2, (Parcelable) getDataType(), i, false);
        zzbfp.zza(parcel, 3, (Parcelable) getIntent(), i, false);
        zzbfp.zza(parcel, 4, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
