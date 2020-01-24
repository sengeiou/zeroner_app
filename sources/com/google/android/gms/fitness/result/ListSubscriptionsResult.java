package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Subscription;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListSubscriptionsResult extends zzbfm implements Result {
    public static final Creator<ListSubscriptionsResult> CREATOR = new zzg();
    private final Status mStatus;
    private final int zzeck;
    private final List<Subscription> zzhio;

    ListSubscriptionsResult(int i, List<Subscription> list, Status status) {
        this.zzeck = i;
        this.zzhio = list;
        this.mStatus = status;
    }

    private ListSubscriptionsResult(List<Subscription> list, Status status) {
        this.zzeck = 3;
        this.zzhio = Collections.unmodifiableList(list);
        this.mStatus = (Status) zzbq.checkNotNull(status, NotificationCompat.CATEGORY_STATUS);
    }

    public static ListSubscriptionsResult zzae(Status status) {
        return new ListSubscriptionsResult(Collections.emptyList(), status);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof ListSubscriptionsResult)) {
                return false;
            }
            ListSubscriptionsResult listSubscriptionsResult = (ListSubscriptionsResult) obj;
            if (!(this.mStatus.equals(listSubscriptionsResult.mStatus) && zzbg.equal(this.zzhio, listSubscriptionsResult.zzhio))) {
                return false;
            }
        }
        return true;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public List<Subscription> getSubscriptions() {
        return this.zzhio;
    }

    public List<Subscription> getSubscriptions(DataType dataType) {
        ArrayList arrayList = new ArrayList();
        for (Subscription subscription : this.zzhio) {
            if (dataType.equals(subscription.zzaqq())) {
                arrayList.add(subscription);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhio});
    }

    public String toString() {
        return zzbg.zzx(this).zzg(NotificationCompat.CATEGORY_STATUS, this.mStatus).zzg("subscriptions", this.zzhio).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, getSubscriptions(), false);
        zzbfp.zza(parcel, 2, (Parcelable) getStatus(), i, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
