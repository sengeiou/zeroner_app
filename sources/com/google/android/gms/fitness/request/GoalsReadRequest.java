package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.zza;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbuu;
import com.google.android.gms.internal.zzbxf;
import com.google.android.gms.internal.zzbxg;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoalsReadRequest extends zzbfm {
    public static final Creator<GoalsReadRequest> CREATOR = new zzad();
    private final int versionCode;
    private final List<Integer> zzhcy;
    private final zzbxf zzhhb;
    private final List<DataType> zzhhc;
    private final List<Integer> zzhhd;

    public static class Builder {
        /* access modifiers changed from: private */
        public final List<Integer> zzhcy = new ArrayList();
        /* access modifiers changed from: private */
        public final List<DataType> zzhhc = new ArrayList();
        /* access modifiers changed from: private */
        public final List<Integer> zzhhd = new ArrayList();

        public Builder addActivity(String str) {
            int zzhc = zza.zzhc(str);
            zzbq.zza(zzhc != 4, (Object) "Attempting to add an unknown activity");
            zzbuu.zza(Integer.valueOf(zzhc), this.zzhcy);
            return this;
        }

        public Builder addDataType(DataType dataType) {
            zzbq.checkNotNull(dataType, "Attempting to use a null data type");
            if (!this.zzhhc.contains(dataType)) {
                this.zzhhc.add(dataType);
            }
            return this;
        }

        public Builder addObjectiveType(int i) {
            boolean z = true;
            if (!(i == 1 || i == 2 || i == 3)) {
                z = false;
            }
            zzbq.zza(z, (Object) "Attempting to add an invalid objective type");
            if (!this.zzhhd.contains(Integer.valueOf(i))) {
                this.zzhhd.add(Integer.valueOf(i));
            }
            return this;
        }

        public GoalsReadRequest build() {
            zzbq.zza(!this.zzhhc.isEmpty(), (Object) "At least one data type should be specified.");
            return new GoalsReadRequest(this);
        }
    }

    GoalsReadRequest(int i, IBinder iBinder, List<DataType> list, List<Integer> list2, List<Integer> list3) {
        this.versionCode = i;
        this.zzhhb = iBinder == null ? null : zzbxg.zzaw(iBinder);
        this.zzhhc = list;
        this.zzhhd = list2;
        this.zzhcy = list3;
    }

    private GoalsReadRequest(Builder builder) {
        this(null, builder.zzhhc, builder.zzhhd, builder.zzhcy);
    }

    public GoalsReadRequest(GoalsReadRequest goalsReadRequest, zzbxf zzbxf) {
        this(zzbxf, goalsReadRequest.getDataTypes(), goalsReadRequest.zzhhd, goalsReadRequest.zzhcy);
    }

    private GoalsReadRequest(zzbxf zzbxf, List<DataType> list, List<Integer> list2, List<Integer> list3) {
        this(1, zzbxf == null ? null : zzbxf.asBinder(), list, list2, list3);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof GoalsReadRequest)) {
                return false;
            }
            GoalsReadRequest goalsReadRequest = (GoalsReadRequest) obj;
            if (!(zzbg.equal(this.zzhhc, goalsReadRequest.zzhhc) && zzbg.equal(this.zzhhd, goalsReadRequest.zzhhd) && zzbg.equal(this.zzhcy, goalsReadRequest.zzhcy))) {
                return false;
            }
        }
        return true;
    }

    public List<String> getActivityNames() {
        if (this.zzhcy.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Integer intValue : this.zzhcy) {
            arrayList.add(zza.getName(intValue.intValue()));
        }
        return arrayList;
    }

    public List<DataType> getDataTypes() {
        return this.zzhhc;
    }

    public List<Integer> getObjectiveTypes() {
        if (this.zzhhd.isEmpty()) {
            return null;
        }
        return this.zzhhd;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzhhc, this.zzhhd, getActivityNames()});
    }

    public String toString() {
        return zzbg.zzx(this).zzg("dataTypes", this.zzhhc).zzg("objectiveTypes", this.zzhhd).zzg("activities", getActivityNames()).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhhb.asBinder(), false);
        zzbfp.zzd(parcel, 2, getDataTypes(), false);
        zzbfp.zzd(parcel, 3, this.zzhhd, false);
        zzbfp.zzd(parcel, 4, this.zzhcy, false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }
}
