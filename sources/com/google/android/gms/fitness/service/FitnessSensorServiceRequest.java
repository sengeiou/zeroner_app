package com.google.android.gms.fitness.service;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.zzt;
import com.google.android.gms.fitness.data.zzu;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class FitnessSensorServiceRequest extends zzbfm {
    public static final Creator<FitnessSensorServiceRequest> CREATOR = new zzb();
    public static final int UNSPECIFIED = -1;
    private final int zzeck;
    private final DataSource zzhbr;
    private final zzt zzhhj;
    private final long zzhis;
    private final long zzhit;

    FitnessSensorServiceRequest(int i, DataSource dataSource, IBinder iBinder, long j, long j2) {
        this.zzeck = i;
        this.zzhbr = dataSource;
        this.zzhhj = zzu.zzar(iBinder);
        this.zzhis = j;
        this.zzhit = j2;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof FitnessSensorServiceRequest)) {
                return false;
            }
            FitnessSensorServiceRequest fitnessSensorServiceRequest = (FitnessSensorServiceRequest) obj;
            if (!(zzbg.equal(this.zzhbr, fitnessSensorServiceRequest.zzhbr) && this.zzhis == fitnessSensorServiceRequest.zzhis && this.zzhit == fitnessSensorServiceRequest.zzhit)) {
                return false;
            }
        }
        return true;
    }

    public long getBatchInterval(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhit, TimeUnit.MICROSECONDS);
    }

    public DataSource getDataSource() {
        return this.zzhbr;
    }

    public SensorEventDispatcher getDispatcher() {
        return new zzc(this.zzhhj);
    }

    public long getSamplingRate(TimeUnit timeUnit) {
        if (this.zzhis == -1) {
            return -1;
        }
        return timeUnit.convert(this.zzhis, TimeUnit.MICROSECONDS);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhbr, Long.valueOf(this.zzhis), Long.valueOf(this.zzhit)});
    }

    public String toString() {
        return String.format("FitnessSensorServiceRequest{%s}", new Object[]{this.zzhbr});
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getDataSource(), i, false);
        zzbfp.zza(parcel, 2, this.zzhhj.asBinder(), false);
        zzbfp.zza(parcel, 3, this.zzhis);
        zzbfp.zza(parcel, 4, this.zzhit);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
