package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.fitness.zza;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import com.tencent.tinker.loader.hotplug.EnvConsts;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Goal extends zzbfm {
    public static final Creator<Goal> CREATOR = new zzs();
    public static final int OBJECTIVE_TYPE_DURATION = 2;
    public static final int OBJECTIVE_TYPE_FREQUENCY = 3;
    public static final int OBJECTIVE_TYPE_METRIC = 1;
    private final int versionCode;
    private final long zzhcw;
    private final long zzhcx;
    private final List<Integer> zzhcy;
    private final Recurrence zzhcz;
    private final int zzhda;
    private final MetricObjective zzhdb;
    private final DurationObjective zzhdc;
    private final FrequencyObjective zzhdd;

    public static class DurationObjective extends zzbfm {
        public static final Creator<DurationObjective> CREATOR = new zzp();
        private final int versionCode;
        private final long zzhde;

        DurationObjective(int i, long j) {
            this.versionCode = i;
            this.zzhde = j;
        }

        public DurationObjective(long j, TimeUnit timeUnit) {
            this(1, timeUnit.toNanos(j));
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (!(obj instanceof DurationObjective)) {
                    return false;
                }
                if (!(this.zzhde == ((DurationObjective) obj).zzhde)) {
                    return false;
                }
            }
            return true;
        }

        public long getDuration(TimeUnit timeUnit) {
            return timeUnit.convert(this.zzhde, TimeUnit.NANOSECONDS);
        }

        public int hashCode() {
            return (int) this.zzhde;
        }

        public String toString() {
            return zzbg.zzx(this).zzg("duration", Long.valueOf(this.zzhde)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbfp.zze(parcel);
            zzbfp.zza(parcel, 1, this.zzhde);
            zzbfp.zzc(parcel, 1000, this.versionCode);
            zzbfp.zzai(parcel, zze);
        }
    }

    public static class FrequencyObjective extends zzbfm {
        public static final Creator<FrequencyObjective> CREATOR = new zzr();
        private final int frequency;
        private final int versionCode;

        public FrequencyObjective(int i) {
            this(1, i);
        }

        FrequencyObjective(int i, int i2) {
            this.versionCode = i;
            this.frequency = i2;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:4:0x000c, code lost:
            if (r2.frequency == ((com.google.android.gms.fitness.data.Goal.FrequencyObjective) r3).frequency) goto L_0x000e;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean equals(java.lang.Object r3) {
            /*
                r2 = this;
                if (r2 == r3) goto L_0x000e
                boolean r0 = r3 instanceof com.google.android.gms.fitness.data.Goal.FrequencyObjective
                if (r0 == 0) goto L_0x0010
                com.google.android.gms.fitness.data.Goal$FrequencyObjective r3 = (com.google.android.gms.fitness.data.Goal.FrequencyObjective) r3
                int r0 = r2.frequency
                int r1 = r3.frequency
                if (r0 != r1) goto L_0x0010
            L_0x000e:
                r0 = 1
            L_0x000f:
                return r0
            L_0x0010:
                r0 = 0
                goto L_0x000f
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.fitness.data.Goal.FrequencyObjective.equals(java.lang.Object):boolean");
        }

        public int getFrequency() {
            return this.frequency;
        }

        public int hashCode() {
            return this.frequency;
        }

        public String toString() {
            return zzbg.zzx(this).zzg("frequency", Integer.valueOf(this.frequency)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbfp.zze(parcel);
            zzbfp.zzc(parcel, 1, getFrequency());
            zzbfp.zzc(parcel, 1000, this.versionCode);
            zzbfp.zzai(parcel, zze);
        }
    }

    public static class MetricObjective extends zzbfm {
        public static final Creator<MetricObjective> CREATOR = new zzx();
        private final double value;
        private final int versionCode;
        private final String zzhdf;
        private final double zzhdg;

        MetricObjective(int i, String str, double d, double d2) {
            this.versionCode = i;
            this.zzhdf = str;
            this.value = d;
            this.zzhdg = d2;
        }

        public MetricObjective(String str, double d) {
            this(1, str, d, Utils.DOUBLE_EPSILON);
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (!(obj instanceof MetricObjective)) {
                    return false;
                }
                MetricObjective metricObjective = (MetricObjective) obj;
                if (!(zzbg.equal(this.zzhdf, metricObjective.zzhdf) && this.value == metricObjective.value && this.zzhdg == metricObjective.zzhdg)) {
                    return false;
                }
            }
            return true;
        }

        public String getDataTypeName() {
            return this.zzhdf;
        }

        public double getValue() {
            return this.value;
        }

        public int hashCode() {
            return this.zzhdf.hashCode();
        }

        public String toString() {
            return zzbg.zzx(this).zzg("dataTypeName", this.zzhdf).zzg("value", Double.valueOf(this.value)).zzg("initialValue", Double.valueOf(this.zzhdg)).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbfp.zze(parcel);
            zzbfp.zza(parcel, 1, getDataTypeName(), false);
            zzbfp.zza(parcel, 2, getValue());
            zzbfp.zza(parcel, 3, this.zzhdg);
            zzbfp.zzc(parcel, 1000, this.versionCode);
            zzbfp.zzai(parcel, zze);
        }
    }

    public static class MismatchedGoalException extends IllegalStateException {
        public MismatchedGoalException(String str) {
            super(str);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ObjectiveType {
    }

    public static class Recurrence extends zzbfm {
        public static final Creator<Recurrence> CREATOR = new zzab();
        public static final int UNIT_DAY = 1;
        public static final int UNIT_MONTH = 3;
        public static final int UNIT_WEEK = 2;
        private final int count;
        private final int versionCode;
        /* access modifiers changed from: private */
        public final int zzhdh;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RecurrenceUnit {
        }

        public Recurrence(int i, int i2) {
            this(1, i, i2);
        }

        Recurrence(int i, int i2, int i3) {
            this.versionCode = i;
            this.count = i2;
            zzbq.checkState(i3 > 0 && i3 <= 3);
            this.zzhdh = i3;
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (!(obj instanceof Recurrence)) {
                    return false;
                }
                Recurrence recurrence = (Recurrence) obj;
                if (!(this.count == recurrence.count && this.zzhdh == recurrence.zzhdh)) {
                    return false;
                }
            }
            return true;
        }

        public int getCount() {
            return this.count;
        }

        public int getUnit() {
            return this.zzhdh;
        }

        public int hashCode() {
            return this.zzhdh;
        }

        public String toString() {
            String str;
            zzbi zzg = zzbg.zzx(this).zzg("count", Integer.valueOf(this.count));
            String str2 = "unit";
            switch (this.zzhdh) {
                case 1:
                    str = "day";
                    break;
                case 2:
                    str = "week";
                    break;
                case 3:
                    str = "month";
                    break;
                default:
                    throw new IllegalArgumentException("invalid unit value");
            }
            return zzg.zzg(str2, str).toString();
        }

        public void writeToParcel(Parcel parcel, int i) {
            int zze = zzbfp.zze(parcel);
            zzbfp.zzc(parcel, 1, getCount());
            zzbfp.zzc(parcel, 2, getUnit());
            zzbfp.zzc(parcel, 1000, this.versionCode);
            zzbfp.zzai(parcel, zze);
        }
    }

    Goal(int i, long j, long j2, List<Integer> list, Recurrence recurrence, int i2, MetricObjective metricObjective, DurationObjective durationObjective, FrequencyObjective frequencyObjective) {
        this.versionCode = i;
        this.zzhcw = j;
        this.zzhcx = j2;
        this.zzhcy = list;
        this.zzhcz = recurrence;
        this.zzhda = i2;
        this.zzhdb = metricObjective;
        this.zzhdc = durationObjective;
        this.zzhdd = frequencyObjective;
    }

    private static String zzde(int i) {
        switch (i) {
            case 0:
                return "unknown";
            case 1:
                return "metric";
            case 2:
                return "duration";
            case 3:
                return "frequency";
            default:
                throw new IllegalArgumentException("invalid objective type value");
        }
    }

    private final void zzdf(int i) throws MismatchedGoalException {
        if (i != this.zzhda) {
            throw new MismatchedGoalException(String.format("%s goal does not have %s objective", new Object[]{zzde(this.zzhda), zzde(i)}));
        }
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof Goal)) {
                return false;
            }
            Goal goal = (Goal) obj;
            if (!(this.zzhcw == goal.zzhcw && this.zzhcx == goal.zzhcx && zzbg.equal(this.zzhcy, goal.zzhcy) && zzbg.equal(this.zzhcz, goal.zzhcz) && this.zzhda == goal.zzhda && zzbg.equal(this.zzhdb, goal.zzhdb) && zzbg.equal(this.zzhdc, goal.zzhdc) && zzbg.equal(this.zzhdd, goal.zzhdd))) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    public String getActivityName() {
        if (this.zzhcy.isEmpty() || this.zzhcy.size() > 1) {
            return null;
        }
        return zza.getName(((Integer) this.zzhcy.get(0)).intValue());
    }

    public long getCreateTime(TimeUnit timeUnit) {
        return timeUnit.convert(this.zzhcw, TimeUnit.NANOSECONDS);
    }

    public DurationObjective getDurationObjective() {
        zzdf(2);
        return this.zzhdc;
    }

    public long getEndTime(Calendar calendar, TimeUnit timeUnit) {
        if (this.zzhcz == null) {
            return timeUnit.convert(this.zzhcx, TimeUnit.NANOSECONDS);
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        switch (this.zzhcz.zzhdh) {
            case 1:
                instance.add(5, 1);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 2:
                instance.add(4, 1);
                instance.set(7, 2);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 3:
                instance.add(2, 1);
                instance.set(5, 1);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            default:
                throw new IllegalArgumentException("Invalid unit " + this.zzhcz.zzhdh);
        }
    }

    public FrequencyObjective getFrequencyObjective() {
        zzdf(3);
        return this.zzhdd;
    }

    public MetricObjective getMetricObjective() {
        zzdf(1);
        return this.zzhdb;
    }

    public int getObjectiveType() {
        return this.zzhda;
    }

    public Recurrence getRecurrence() {
        return this.zzhcz;
    }

    public long getStartTime(Calendar calendar, TimeUnit timeUnit) {
        if (this.zzhcz == null) {
            return timeUnit.convert(this.zzhcw, TimeUnit.NANOSECONDS);
        }
        Calendar instance = Calendar.getInstance();
        instance.setTime(calendar.getTime());
        switch (this.zzhcz.zzhdh) {
            case 1:
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 2:
                instance.set(7, 2);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            case 3:
                instance.set(5, 1);
                instance.set(11, 0);
                return timeUnit.convert(instance.getTimeInMillis(), TimeUnit.MILLISECONDS);
            default:
                throw new IllegalArgumentException("Invalid unit " + this.zzhcz.zzhdh);
        }
    }

    public int hashCode() {
        return this.zzhda;
    }

    public String toString() {
        return zzbg.zzx(this).zzg(EnvConsts.ACTIVITY_MANAGER_SRVNAME, getActivityName()).zzg("recurrence", this.zzhcz).zzg("metricObjective", this.zzhdb).zzg("durationObjective", this.zzhdc).zzg("frequencyObjective", this.zzhdd).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, this.zzhcw);
        zzbfp.zza(parcel, 2, this.zzhcx);
        zzbfp.zzd(parcel, 3, this.zzhcy, false);
        zzbfp.zza(parcel, 4, (Parcelable) getRecurrence(), i, false);
        zzbfp.zzc(parcel, 5, getObjectiveType());
        zzbfp.zza(parcel, 6, (Parcelable) this.zzhdb, i, false);
        zzbfp.zza(parcel, 7, (Parcelable) this.zzhdc, i, false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zza(parcel, 8, (Parcelable) this.zzhdd, i, false);
        zzbfp.zzai(parcel, zze);
    }
}
