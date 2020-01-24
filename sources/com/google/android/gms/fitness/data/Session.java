package com.google.android.gms.fitness.data;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.zza;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.google.android.gms.internal.zzbfr;
import com.tencent.open.SocialConstants;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.litepal.util.Const.TableSchema;

public class Session extends zzbfm {
    public static final Creator<Session> CREATOR = new zzad();
    public static final String EXTRA_SESSION = "vnd.google.fitness.session";
    public static final String MIME_TYPE_PREFIX = "vnd.google.fitness.session/";
    private final String mName;
    private final String zzdrs;
    private final long zzdvq;
    private final int zzeck;
    private final long zzgzz;
    private final int zzhaa;
    private final String zzhdu;
    private final zzb zzhdv;
    private final Long zzhdw;

    public static class Builder {
        /* access modifiers changed from: private */
        public String mName = null;
        /* access modifiers changed from: private */
        public String zzdrs;
        /* access modifiers changed from: private */
        public long zzdvq = 0;
        /* access modifiers changed from: private */
        public long zzgzz = 0;
        /* access modifiers changed from: private */
        public int zzhaa = 4;
        /* access modifiers changed from: private */
        public String zzhdu;
        /* access modifiers changed from: private */
        public Long zzhdw;

        public Session build() {
            boolean z = false;
            zzbq.zza(this.zzdvq > 0, (Object) "Start time should be specified.");
            if (this.zzgzz == 0 || this.zzgzz > this.zzdvq) {
                z = true;
            }
            zzbq.zza(z, (Object) "End time should be later than start time.");
            if (this.zzhdu == null) {
                String str = this.mName == null ? "" : this.mName;
                this.zzhdu = new StringBuilder(String.valueOf(str).length() + 20).append(str).append(this.zzdvq).toString();
            }
            if (this.zzdrs == null) {
                this.zzdrs = "";
            }
            return new Session(this);
        }

        public Builder setActiveTime(long j, TimeUnit timeUnit) {
            this.zzhdw = Long.valueOf(timeUnit.toMillis(j));
            return this;
        }

        public Builder setActivity(String str) {
            this.zzhaa = zza.zzhc(str);
            return this;
        }

        public Builder setDescription(String str) {
            zzbq.zzb(str.length() <= 1000, "Session description cannot exceed %d characters", Integer.valueOf(1000));
            this.zzdrs = str;
            return this;
        }

        public Builder setEndTime(long j, TimeUnit timeUnit) {
            zzbq.zza(j >= 0, (Object) "End time should be positive.");
            this.zzgzz = timeUnit.toMillis(j);
            return this;
        }

        public Builder setIdentifier(String str) {
            zzbq.checkArgument(str != null && TextUtils.getTrimmedLength(str) > 0);
            this.zzhdu = str;
            return this;
        }

        public Builder setName(String str) {
            zzbq.zzb(str.length() <= 100, "Session name cannot exceed %d characters", Integer.valueOf(100));
            this.mName = str;
            return this;
        }

        public Builder setStartTime(long j, TimeUnit timeUnit) {
            zzbq.zza(j > 0, (Object) "Start time should be positive.");
            this.zzdvq = timeUnit.toMillis(j);
            return this;
        }
    }

    Session(int i, long j, long j2, String str, String str2, String str3, int i2, zzb zzb, Long l) {
        this.zzeck = i;
        this.zzdvq = j;
        this.zzgzz = j2;
        this.mName = str;
        this.zzhdu = str2;
        this.zzdrs = str3;
        this.zzhaa = i2;
        this.zzhdv = zzb;
        this.zzhdw = l;
    }

    private Session(long j, long j2, String str, String str2, String str3, int i, zzb zzb, Long l) {
        this(3, j, j2, str, str2, str3, i, null, l);
    }

    private Session(Builder builder) {
        this(builder.zzdvq, builder.zzgzz, builder.mName, builder.zzhdu, builder.zzdrs, builder.zzhaa, null, builder.zzhdw);
    }

    public static Session extract(Intent intent) {
        if (intent == null) {
            return null;
        }
        return (Session) zzbfr.zza(intent, EXTRA_SESSION, CREATOR);
    }

    public static String getMimeType(String str) {
        String valueOf = String.valueOf(MIME_TYPE_PREFIX);
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof Session)) {
                return false;
            }
            Session session = (Session) obj;
            if (!(this.zzdvq == session.zzdvq && this.zzgzz == session.zzgzz && zzbg.equal(this.mName, session.mName) && zzbg.equal(this.zzhdu, session.zzhdu) && zzbg.equal(this.zzdrs, session.zzdrs) && zzbg.equal(this.zzhdv, session.zzhdv) && this.zzhaa == session.zzhaa)) {
                return false;
            }
        }
        return true;
    }

    public long getActiveTime(TimeUnit timeUnit) {
        zzbq.zza(this.zzhdw != null, (Object) "Active time is not set");
        return timeUnit.convert(this.zzhdw.longValue(), TimeUnit.MILLISECONDS);
    }

    public String getActivity() {
        return zza.getName(this.zzhaa);
    }

    public String getAppPackageName() {
        if (this.zzhdv == null) {
            return null;
        }
        return this.zzhdv.getPackageName();
    }

    public String getDescription() {
        return this.zzdrs;
    }

    public long getEndTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzgzz, TimeUnit.MILLISECONDS);
    }

    public String getIdentifier() {
        return this.zzhdu;
    }

    public String getName() {
        return this.mName;
    }

    public long getStartTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzdvq, TimeUnit.MILLISECONDS);
    }

    public boolean hasActiveTime() {
        return this.zzhdw != null;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Long.valueOf(this.zzdvq), Long.valueOf(this.zzgzz), this.zzhdu});
    }

    public boolean isOngoing() {
        return this.zzgzz == 0;
    }

    public String toString() {
        return zzbg.zzx(this).zzg("startTime", Long.valueOf(this.zzdvq)).zzg("endTime", Long.valueOf(this.zzgzz)).zzg(TableSchema.COLUMN_NAME, this.mName).zzg("identifier", this.zzhdu).zzg(SocialConstants.PARAM_COMMENT, this.zzdrs).zzg(EnvConsts.ACTIVITY_MANAGER_SRVNAME, Integer.valueOf(this.zzhaa)).zzg("application", this.zzhdv).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzdvq);
        zzbfp.zza(parcel, 2, this.zzgzz);
        zzbfp.zza(parcel, 3, getName(), false);
        zzbfp.zza(parcel, 4, getIdentifier(), false);
        zzbfp.zza(parcel, 5, getDescription(), false);
        zzbfp.zzc(parcel, 7, this.zzhaa);
        zzbfp.zzc(parcel, 1000, this.zzeck);
        zzbfp.zza(parcel, 8, (Parcelable) this.zzhdv, i, false);
        zzbfp.zza(parcel, 9, this.zzhdw, false);
        zzbfp.zzai(parcel, zze);
    }
}
