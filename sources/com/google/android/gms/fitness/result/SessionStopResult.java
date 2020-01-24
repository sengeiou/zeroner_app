package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SessionStopResult extends zzbfm implements Result {
    public static final Creator<SessionStopResult> CREATOR = new zzi();
    private final Status mStatus;
    private final int zzeck;
    private final List<Session> zzhgg;

    SessionStopResult(int i, Status status, List<Session> list) {
        this.zzeck = i;
        this.mStatus = status;
        this.zzhgg = Collections.unmodifiableList(list);
    }

    public SessionStopResult(Status status, List<Session> list) {
        this.zzeck = 3;
        this.mStatus = status;
        this.zzhgg = Collections.unmodifiableList(list);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof SessionStopResult)) {
                return false;
            }
            SessionStopResult sessionStopResult = (SessionStopResult) obj;
            if (!(this.mStatus.equals(sessionStopResult.mStatus) && zzbg.equal(this.zzhgg, sessionStopResult.zzhgg))) {
                return false;
            }
        }
        return true;
    }

    public List<Session> getSessions() {
        return this.zzhgg;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhgg});
    }

    public String toString() {
        return zzbg.zzx(this).zzg(NotificationCompat.CATEGORY_STATUS, this.mStatus).zzg("sessions", this.zzhgg).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, (Parcelable) getStatus(), i, false);
        zzbfp.zzc(parcel, 3, getSessions(), false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
