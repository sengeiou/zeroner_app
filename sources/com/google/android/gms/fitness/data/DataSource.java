package com.google.android.gms.fitness.data;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbfr;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class DataSource extends zzbfm {
    public static final Creator<DataSource> CREATOR = new zzk();
    public static final int DATA_QUALITY_BLOOD_GLUCOSE_ISO151972003 = 8;
    public static final int DATA_QUALITY_BLOOD_GLUCOSE_ISO151972013 = 9;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_AAMI = 3;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_BHS_A_A = 4;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_BHS_A_B = 5;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_BHS_B_A = 6;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_BHS_B_B = 7;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_ESH2002 = 1;
    public static final int DATA_QUALITY_BLOOD_PRESSURE_ESH2010 = 2;
    public static final String EXTRA_DATA_SOURCE = "vnd.google.fitness.data_source";
    public static final int TYPE_DERIVED = 1;
    public static final int TYPE_RAW = 0;
    private static final int[] zzhap = new int[0];
    private final String name;
    private final int type;
    private final int versionCode;
    private final DataType zzgzg;
    private final Device zzhaq;
    private final zzb zzhar;
    private final String zzhas;
    private final int[] zzhat;
    private final String zzhau;

    public static final class Builder {
        /* access modifiers changed from: private */
        public String name;
        /* access modifiers changed from: private */
        public int type = -1;
        /* access modifiers changed from: private */
        public DataType zzgzg;
        /* access modifiers changed from: private */
        public Device zzhaq;
        /* access modifiers changed from: private */
        public zzb zzhar;
        /* access modifiers changed from: private */
        public String zzhas = "";
        /* access modifiers changed from: private */
        public int[] zzhat;

        public final DataSource build() {
            boolean z = true;
            zzbq.zza(this.zzgzg != null, (Object) "Must set data type");
            if (this.type < 0) {
                z = false;
            }
            zzbq.zza(z, (Object) "Must set data source type");
            return new DataSource(this);
        }

        public final Builder setAppPackageName(Context context) {
            return setAppPackageName(context.getPackageName());
        }

        public final Builder setAppPackageName(String str) {
            this.zzhar = zzb.zzhd(str);
            return this;
        }

        public final Builder setDataQualityStandards(int... iArr) {
            this.zzhat = iArr;
            return this;
        }

        public final Builder setDataType(DataType dataType) {
            this.zzgzg = dataType;
            return this;
        }

        public final Builder setDevice(Device device) {
            this.zzhaq = device;
            return this;
        }

        public final Builder setName(String str) {
            this.name = str;
            return this;
        }

        public final Builder setStreamName(String str) {
            zzbq.checkArgument(str != null, "Must specify a valid stream name");
            this.zzhas = str;
            return this;
        }

        public final Builder setType(int i) {
            this.type = i;
            return this;
        }
    }

    DataSource(int i, DataType dataType, String str, int i2, Device device, zzb zzb, String str2, int[] iArr) {
        this.versionCode = i;
        this.zzgzg = dataType;
        this.type = i2;
        this.name = str;
        this.zzhaq = device;
        this.zzhar = zzb;
        this.zzhas = str2;
        this.zzhau = zzaqm();
        if (iArr == null) {
            iArr = zzhap;
        }
        this.zzhat = iArr;
    }

    private DataSource(Builder builder) {
        this.versionCode = 3;
        this.zzgzg = builder.zzgzg;
        this.type = builder.type;
        this.name = builder.name;
        this.zzhaq = builder.zzhaq;
        this.zzhar = builder.zzhar;
        this.zzhas = builder.zzhas;
        this.zzhau = zzaqm();
        this.zzhat = builder.zzhat;
    }

    public static DataSource extract(Intent intent) {
        if (intent == null) {
            return null;
        }
        return (DataSource) zzbfr.zza(intent, EXTRA_DATA_SOURCE, CREATOR);
    }

    private final String getTypeString() {
        switch (this.type) {
            case 0:
                return ShareConstants.DEXMODE_RAW;
            case 1:
                return "derived";
            case 2:
                return "cleaned";
            case 3:
                return "converted";
            default:
                return "derived";
        }
    }

    private final String zzaqm() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTypeString());
        sb.append(":").append(this.zzgzg.getName());
        if (this.zzhar != null) {
            sb.append(":").append(this.zzhar.getPackageName());
        }
        if (this.zzhaq != null) {
            sb.append(":").append(this.zzhaq.getStreamIdentifier());
        }
        if (this.zzhas != null) {
            sb.append(":").append(this.zzhas);
        }
        return sb.toString();
    }

    public static String zzdd(int i) {
        switch (i) {
            case 1:
                return "blood_pressure_esh2002";
            case 2:
                return "blood_pressure_esh2010";
            case 3:
                return "blood_pressure_aami";
            case 4:
                return "blood_pressure_bhs_a_a";
            case 5:
                return "blood_pressure_bhs_a_b";
            case 6:
                return "blood_pressure_bhs_b_a";
            case 7:
                return "blood_pressure_bhs_b_b";
            case 8:
                return "blood_glucose_iso151972003";
            case 9:
                return "blood_glucose_iso151972013";
            default:
                return "unknown";
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0010, code lost:
        if (r2.zzhau.equals(((com.google.android.gms.fitness.data.DataSource) r3).zzhau) != false) goto L_0x0012;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r3) {
        /*
            r2 = this;
            if (r2 == r3) goto L_0x0012
            boolean r0 = r3 instanceof com.google.android.gms.fitness.data.DataSource
            if (r0 == 0) goto L_0x0014
            com.google.android.gms.fitness.data.DataSource r3 = (com.google.android.gms.fitness.data.DataSource) r3
            java.lang.String r0 = r2.zzhau
            java.lang.String r1 = r3.zzhau
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0014
        L_0x0012:
            r0 = 1
        L_0x0013:
            return r0
        L_0x0014:
            r0 = 0
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.data.DataSource.equals(java.lang.Object):boolean");
    }

    public String getAppPackageName() {
        if (this.zzhar == null) {
            return null;
        }
        return this.zzhar.getPackageName();
    }

    public int[] getDataQualityStandards() {
        return this.zzhat;
    }

    public DataType getDataType() {
        return this.zzgzg;
    }

    public Device getDevice() {
        return this.zzhaq;
    }

    public String getName() {
        return this.name;
    }

    public String getStreamIdentifier() {
        return this.zzhau;
    }

    public String getStreamName() {
        return this.zzhas;
    }

    public int getType() {
        return this.type;
    }

    public int hashCode() {
        return this.zzhau.hashCode();
    }

    public final String toDebugString() {
        String str;
        String str2;
        String str3;
        String str4;
        switch (this.type) {
            case 0:
                str = "r";
                break;
            case 1:
                str = "d";
                break;
            case 2:
                str = "c";
                break;
            case 3:
                str = "v";
                break;
            default:
                str = "?";
                break;
        }
        String zzaqp = this.zzgzg.zzaqp();
        if (this.zzhar == null) {
            str2 = "";
        } else if (this.zzhar.equals(zzb.zzgzu)) {
            str2 = ":gms";
        } else {
            String str5 = ":";
            String valueOf = String.valueOf(this.zzhar.getPackageName());
            str2 = valueOf.length() != 0 ? str5.concat(valueOf) : new String(str5);
        }
        if (this.zzhaq != null) {
            String model = this.zzhaq.getModel();
            String uid = this.zzhaq.getUid();
            str3 = new StringBuilder(String.valueOf(model).length() + 2 + String.valueOf(uid).length()).append(":").append(model).append(":").append(uid).toString();
        } else {
            str3 = "";
        }
        if (this.zzhas != null) {
            String str6 = ":";
            String valueOf2 = String.valueOf(this.zzhas);
            str4 = valueOf2.length() != 0 ? str6.concat(valueOf2) : new String(str6);
        } else {
            str4 = "";
        }
        return new StringBuilder(String.valueOf(str).length() + 1 + String.valueOf(zzaqp).length() + String.valueOf(str2).length() + String.valueOf(str3).length() + String.valueOf(str4).length()).append(str).append(":").append(zzaqp).append(str2).append(str3).append(str4).toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DataSource{");
        sb.append(getTypeString());
        if (this.name != null) {
            sb.append(":").append(this.name);
        }
        if (this.zzhar != null) {
            sb.append(":").append(this.zzhar);
        }
        if (this.zzhaq != null) {
            sb.append(":").append(this.zzhaq);
        }
        if (this.zzhas != null) {
            sb.append(":").append(this.zzhas);
        }
        sb.append(":").append(this.zzgzg);
        return sb.append("}").toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, (Parcelable) getDataType(), i, false);
        zzbfp.zza(parcel, 2, getName(), false);
        zzbfp.zzc(parcel, 3, getType());
        zzbfp.zza(parcel, 4, (Parcelable) getDevice(), i, false);
        zzbfp.zza(parcel, 5, (Parcelable) this.zzhar, i, false);
        zzbfp.zza(parcel, 6, getStreamName(), false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zza(parcel, 8, getDataQualityStandards(), false);
        zzbfp.zzai(parcel, zze);
    }

    public final zzb zzaql() {
        return this.zzhar;
    }
}
