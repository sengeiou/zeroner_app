package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BleDevicesResult extends zzbfm implements Result {
    public static final Creator<BleDevicesResult> CREATOR = new zza();
    private final Status mStatus;
    private final int zzeck;
    private final List<BleDevice> zzhih;

    BleDevicesResult(int i, List<BleDevice> list, Status status) {
        this.zzeck = i;
        this.zzhih = Collections.unmodifiableList(list);
        this.mStatus = status;
    }

    private BleDevicesResult(List<BleDevice> list, Status status) {
        this.zzeck = 3;
        this.zzhih = Collections.unmodifiableList(list);
        this.mStatus = status;
    }

    public static BleDevicesResult zzac(Status status) {
        return new BleDevicesResult(Collections.emptyList(), status);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof BleDevicesResult)) {
                return false;
            }
            BleDevicesResult bleDevicesResult = (BleDevicesResult) obj;
            if (!(this.mStatus.equals(bleDevicesResult.mStatus) && zzbg.equal(this.zzhih, bleDevicesResult.zzhih))) {
                return false;
            }
        }
        return true;
    }

    public List<BleDevice> getClaimedBleDevices() {
        return this.zzhih;
    }

    public List<BleDevice> getClaimedBleDevices(DataType dataType) {
        ArrayList arrayList = new ArrayList();
        for (BleDevice bleDevice : this.zzhih) {
            if (bleDevice.getDataTypes().contains(dataType)) {
                arrayList.add(bleDevice);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhih});
    }

    public String toString() {
        return zzbg.zzx(this).zzg(NotificationCompat.CATEGORY_STATUS, this.mStatus).zzg("bleDevices", this.zzhih).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, getClaimedBleDevices(), false);
        zzbfp.zza(parcel, 2, (Parcelable) getStatus(), i, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
