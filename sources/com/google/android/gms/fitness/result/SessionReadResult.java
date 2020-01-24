package com.google.android.gms.fitness.result;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Session;
import com.google.android.gms.fitness.data.zzae;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SessionReadResult extends zzbfm implements Result {
    public static final Creator<SessionReadResult> CREATOR = new zzh();
    private final Status mStatus;
    private final int zzeck;
    private final List<Session> zzhgg;
    private final List<zzae> zzhip;

    SessionReadResult(int i, List<Session> list, List<zzae> list2, Status status) {
        this.zzeck = i;
        this.zzhgg = list;
        this.zzhip = Collections.unmodifiableList(list2);
        this.mStatus = status;
    }

    private SessionReadResult(List<Session> list, List<zzae> list2, Status status) {
        this.zzeck = 3;
        this.zzhgg = list;
        this.zzhip = Collections.unmodifiableList(list2);
        this.mStatus = status;
    }

    public static SessionReadResult zzaf(Status status) {
        return new SessionReadResult(new ArrayList(), new ArrayList(), status);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof SessionReadResult)) {
                return false;
            }
            SessionReadResult sessionReadResult = (SessionReadResult) obj;
            if (!(this.mStatus.equals(sessionReadResult.mStatus) && zzbg.equal(this.zzhgg, sessionReadResult.zzhgg) && zzbg.equal(this.zzhip, sessionReadResult.zzhip))) {
                return false;
            }
        }
        return true;
    }

    public List<DataSet> getDataSet(Session session) {
        zzbq.zzb(this.zzhgg.contains(session), "Attempting to read data for session %s which was not returned", session);
        ArrayList arrayList = new ArrayList();
        for (zzae zzae : this.zzhip) {
            if (zzbg.equal(session, zzae.getSession())) {
                arrayList.add(zzae.getDataSet());
            }
        }
        return arrayList;
    }

    public List<DataSet> getDataSet(Session session, DataType dataType) {
        zzbq.zzb(this.zzhgg.contains(session), "Attempting to read data for session %s which was not returned", session);
        ArrayList arrayList = new ArrayList();
        for (zzae zzae : this.zzhip) {
            if (zzbg.equal(session, zzae.getSession()) && dataType.equals(zzae.getDataSet().getDataType())) {
                arrayList.add(zzae.getDataSet());
            }
        }
        return arrayList;
    }

    public List<Session> getSessions() {
        return this.zzhgg;
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mStatus, this.zzhgg, this.zzhip});
    }

    public String toString() {
        return zzbg.zzx(this).zzg(NotificationCompat.CATEGORY_STATUS, this.mStatus).zzg("sessions", this.zzhgg).zzg("sessionDataSets", this.zzhip).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zzc(parcel, 1, getSessions(), false);
        zzbfp.zzc(parcel, 2, this.zzhip, false);
        zzbfp.zza(parcel, 3, (Parcelable) getStatus(), i, false);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zzai(parcel, zze);
    }
}
