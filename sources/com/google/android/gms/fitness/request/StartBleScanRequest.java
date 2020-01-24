package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.util.zza;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbyf;
import com.google.android.gms.internal.zzbyg;
import java.util.Collections;
import java.util.List;

public class StartBleScanRequest extends zzbfm {
    public static final Creator<StartBleScanRequest> CREATOR = new zzbg();
    private final int zzeck;
    private final List<DataType> zzgzy;
    private final zzbyf zzhgc;
    private final zzae zzhid;
    private final int zzhie;

    public static class Builder {
        /* access modifiers changed from: private */
        public DataType[] zzhgx = new DataType[0];
        /* access modifiers changed from: private */
        public zzae zzhid;
        /* access modifiers changed from: private */
        public int zzhie = 10;

        public StartBleScanRequest build() {
            zzbq.zza(this.zzhid != null, (Object) "Must set BleScanCallback");
            return new StartBleScanRequest(this);
        }

        public Builder setBleScanCallback(BleScanCallback bleScanCallback) {
            this.zzhid = zzd.zzaqt().zza(bleScanCallback);
            return this;
        }

        public Builder setDataTypes(DataType... dataTypeArr) {
            this.zzhgx = dataTypeArr;
            return this;
        }

        public Builder setTimeoutSecs(int i) {
            boolean z = true;
            zzbq.checkArgument(i > 0, "Stop time must be greater than zero");
            if (i > 60) {
                z = false;
            }
            zzbq.checkArgument(z, "Stop time must be less than 1 minute");
            this.zzhie = i;
            return this;
        }
    }

    StartBleScanRequest(int i, List<DataType> list, IBinder iBinder, int i2, IBinder iBinder2) {
        zzae zzag;
        this.zzeck = i;
        this.zzgzy = list;
        if (iBinder == null) {
            zzag = null;
        } else {
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.fitness.request.IBleScanCallback");
            zzag = queryLocalInterface instanceof zzae ? (zzae) queryLocalInterface : new zzag(iBinder);
        }
        this.zzhid = zzag;
        this.zzhie = i2;
        this.zzhgc = zzbyg.zzba(iBinder2);
    }

    private StartBleScanRequest(Builder builder) {
        this(zza.zza((T[]) builder.zzhgx), builder.zzhid, builder.zzhie, null);
    }

    public StartBleScanRequest(StartBleScanRequest startBleScanRequest, zzbyf zzbyf) {
        this(startBleScanRequest.zzgzy, startBleScanRequest.zzhid, startBleScanRequest.zzhie, zzbyf);
    }

    public StartBleScanRequest(List<DataType> list, zzae zzae, int i, zzbyf zzbyf) {
        this.zzeck = 4;
        this.zzgzy = list;
        this.zzhid = zzae;
        this.zzhie = i;
        this.zzhgc = zzbyf;
    }

    public List<DataType> getDataTypes() {
        return Collections.unmodifiableList(this.zzgzy);
    }

    public int getTimeoutSecs() {
        return this.zzhie;
    }

    public String toString() {
        return zzbg.zzx(this).zzg("dataTypes", this.zzgzy).zzg("timeoutSecs", Integer.valueOf(this.zzhie)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, getDataTypes(), false);
        zzbfp.zza(parcel, 2, this.zzhid.asBinder(), false);
        zzbfp.zzc(parcel, 3, getTimeoutSecs());
        zzbfp.zza(parcel, 4, this.zzhgc == null ? null : this.zzhgc.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
