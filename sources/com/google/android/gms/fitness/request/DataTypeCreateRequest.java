package com.google.android.gms.fitness.request;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbxc;
import com.google.android.gms.internal.zzbxd;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.litepal.util.Const.TableSchema;

public class DataTypeCreateRequest extends zzbfm {
    public static final Creator<DataTypeCreateRequest> CREATOR = new zzr();
    private final String mName;
    private final int zzeck;
    private final List<Field> zzhgz;
    private final zzbxc zzhha;

    public static class Builder {
        /* access modifiers changed from: private */
        public String mName;
        /* access modifiers changed from: private */
        public List<Field> zzhgz = new ArrayList();

        public Builder addField(Field field) {
            if (!this.zzhgz.contains(field)) {
                this.zzhgz.add(field);
            }
            return this;
        }

        public Builder addField(String str, int i) {
            zzbq.checkArgument(str != null && !str.isEmpty(), "Invalid name specified");
            return addField(Field.zzo(str, i));
        }

        public DataTypeCreateRequest build() {
            boolean z = true;
            zzbq.zza(this.mName != null, (Object) "Must set the name");
            if (this.zzhgz.isEmpty()) {
                z = false;
            }
            zzbq.zza(z, (Object) "Must specify the data fields");
            return new DataTypeCreateRequest(this);
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }
    }

    DataTypeCreateRequest(int i, String str, List<Field> list, IBinder iBinder) {
        this.zzeck = i;
        this.mName = str;
        this.zzhgz = Collections.unmodifiableList(list);
        this.zzhha = zzbxd.zzav(iBinder);
    }

    private DataTypeCreateRequest(Builder builder) {
        this(builder.mName, builder.zzhgz, null);
    }

    public DataTypeCreateRequest(DataTypeCreateRequest dataTypeCreateRequest, zzbxc zzbxc) {
        this(dataTypeCreateRequest.mName, dataTypeCreateRequest.zzhgz, zzbxc);
    }

    private DataTypeCreateRequest(String str, List<Field> list, zzbxc zzbxc) {
        this.zzeck = 3;
        this.mName = str;
        this.zzhgz = Collections.unmodifiableList(list);
        this.zzhha = zzbxc;
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof DataTypeCreateRequest)) {
                return false;
            }
            DataTypeCreateRequest dataTypeCreateRequest = (DataTypeCreateRequest) obj;
            if (!(zzbg.equal(this.mName, dataTypeCreateRequest.mName) && zzbg.equal(this.zzhgz, dataTypeCreateRequest.zzhgz))) {
                return false;
            }
        }
        return true;
    }

    public List<Field> getFields() {
        return this.zzhgz;
    }

    public String getName() {
        return this.mName;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mName, this.zzhgz});
    }

    public String toString() {
        return zzbg.zzx(this).zzg(TableSchema.COLUMN_NAME, this.mName).zzg("fields", this.zzhgz).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, getName(), false);
        zzbfp.zzc(parcel, 2, getFields(), false);
        zzbfp.zza(parcel, 3, this.zzhha == null ? null : this.zzhha.asBinder(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
