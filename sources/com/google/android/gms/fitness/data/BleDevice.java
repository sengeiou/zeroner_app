package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbus;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.litepal.util.Const.TableSchema;

public class BleDevice extends zzbfm implements ReflectedParcelable {
    public static final Creator<BleDevice> CREATOR = new zzd();
    private final String mName;
    private final int zzeck;
    private final String zzgzw;
    private final List<String> zzgzx;
    private final List<DataType> zzgzy;

    BleDevice(int i, String str, String str2, List<String> list, List<DataType> list2) {
        this.zzeck = i;
        this.zzgzw = str;
        this.mName = str2;
        this.zzgzx = Collections.unmodifiableList(list);
        this.zzgzy = Collections.unmodifiableList(list2);
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof BleDevice)) {
                return false;
            }
            BleDevice bleDevice = (BleDevice) obj;
            if (!(this.mName.equals(bleDevice.mName) && this.zzgzw.equals(bleDevice.zzgzw) && zzbus.zza(bleDevice.zzgzx, this.zzgzx) && zzbus.zza(this.zzgzy, bleDevice.zzgzy))) {
                return false;
            }
        }
        return true;
    }

    public String getAddress() {
        return this.zzgzw;
    }

    public List<DataType> getDataTypes() {
        return this.zzgzy;
    }

    public String getName() {
        return this.mName;
    }

    public List<String> getSupportedProfiles() {
        return this.zzgzx;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mName, this.zzgzw, this.zzgzx, this.zzgzy});
    }

    public String toString() {
        return zzbg.zzx(this).zzg(TableSchema.COLUMN_NAME, this.mName).zzg("address", this.zzgzw).zzg("dataTypes", this.zzgzy).zzg("supportedProfiles", this.zzgzx).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, getAddress(), false);
        zzbfp.zza(parcel, 2, getName(), false);
        zzbfp.zzb(parcel, 3, getSupportedProfiles(), false);
        zzbfp.zzc(parcel, 4, getDataTypes(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
