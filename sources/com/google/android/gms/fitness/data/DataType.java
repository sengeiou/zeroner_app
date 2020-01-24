package com.google.android.gms.fitness.data;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.RequiresPermission;
import android.support.v4.util.ArraySet;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbfm;
import com.google.android.gms.internal.zzbfp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class DataType extends zzbfm implements ReflectedParcelable {
    public static final DataType AGGREGATE_ACTIVITY_SUMMARY = new DataType("com.google.activity.summary", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_ACTIVITY, Field.FIELD_DURATION, Field.FIELD_NUM_SEGMENTS);
    public static final DataType AGGREGATE_BASAL_METABOLIC_RATE_SUMMARY = new DataType("com.google.calories.bmr.summary", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
    public static final DataType AGGREGATE_BODY_FAT_PERCENTAGE_SUMMARY = new DataType("com.google.body.fat.percentage.summary", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
    @Deprecated
    public static final DataType AGGREGATE_CALORIES_CONSUMED = TYPE_CALORIES_CONSUMED;
    public static final DataType AGGREGATE_CALORIES_EXPENDED = TYPE_CALORIES_EXPENDED;
    public static final DataType AGGREGATE_DISTANCE_DELTA = TYPE_DISTANCE_DELTA;
    @RequiresPermission(conditional = true, value = "android.permission.BODY_SENSORS")
    public static final DataType AGGREGATE_HEART_RATE_SUMMARY = new DataType("com.google.heart_rate.summary", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
    public static final DataType AGGREGATE_HEIGHT_SUMMARY = new DataType("com.google.height.summary", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
    public static final DataType AGGREGATE_HYDRATION = TYPE_HYDRATION;
    @Deprecated
    public static final Set<DataType> AGGREGATE_INPUT_TYPES;
    @RequiresPermission(conditional = true, value = "android.permission.ACCESS_FINE_LOCATION")
    public static final DataType AGGREGATE_LOCATION_BOUNDING_BOX = new DataType("com.google.location.bounding_box", Scopes.FITNESS_LOCATION_READ, Scopes.FITNESS_LOCATION_READ_WRITE, Field.FIELD_LOW_LATITUDE, Field.FIELD_LOW_LONGITUDE, Field.FIELD_HIGH_LATITUDE, Field.FIELD_HIGH_LONGITUDE);
    public static final DataType AGGREGATE_NUTRITION_SUMMARY = new DataType("com.google.nutrition.summary", Scopes.FITNESS_NUTRITION_READ, Scopes.FITNESS_NUTRITION_READ_WRITE, Field.FIELD_NUTRIENTS, Field.FIELD_MEAL_TYPE);
    public static final DataType AGGREGATE_POWER_SUMMARY = new DataType("com.google.power.summary", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
    public static final DataType AGGREGATE_SPEED_SUMMARY = new DataType("com.google.speed.summary", Scopes.FITNESS_LOCATION_READ, Scopes.FITNESS_LOCATION_READ_WRITE, Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
    public static final DataType AGGREGATE_STEP_COUNT_DELTA = TYPE_STEP_COUNT_DELTA;
    public static final DataType AGGREGATE_WEIGHT_SUMMARY = new DataType("com.google.weight.summary", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
    public static final Creator<DataType> CREATOR = new zzl();
    public static final String MIME_TYPE_PREFIX = "vnd.google.fitness.data_type/";
    @Deprecated
    public static final DataType TYPE_ACTIVITY_SAMPLE = new DataType("com.google.activity.sample", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_ACTIVITY, Field.FIELD_CONFIDENCE);
    public static final DataType TYPE_ACTIVITY_SAMPLES = new DataType("com.google.activity.samples", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_ACTIVITY_CONFIDENCE);
    public static final DataType TYPE_ACTIVITY_SEGMENT = new DataType("com.google.activity.segment", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_ACTIVITY);
    public static final DataType TYPE_BASAL_METABOLIC_RATE = new DataType("com.google.calories.bmr", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_CALORIES);
    public static final DataType TYPE_BODY_FAT_PERCENTAGE = new DataType("com.google.body.fat.percentage", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_PERCENTAGE);
    @Deprecated
    public static final DataType TYPE_CALORIES_CONSUMED = new DataType("com.google.calories.consumed", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_CALORIES);
    public static final DataType TYPE_CALORIES_EXPENDED = new DataType("com.google.calories.expended", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_CALORIES);
    public static final DataType TYPE_CYCLING_PEDALING_CADENCE = new DataType("com.google.cycling.pedaling.cadence", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_RPM);
    public static final DataType TYPE_CYCLING_PEDALING_CUMULATIVE = new DataType("com.google.cycling.pedaling.cumulative", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_REVOLUTIONS);
    public static final DataType TYPE_CYCLING_WHEEL_REVOLUTION = new DataType("com.google.cycling.wheel_revolution.cumulative", Scopes.FITNESS_LOCATION_READ, Scopes.FITNESS_LOCATION_READ_WRITE, Field.FIELD_REVOLUTIONS);
    public static final DataType TYPE_CYCLING_WHEEL_RPM = new DataType("com.google.cycling.wheel_revolution.rpm", Scopes.FITNESS_LOCATION_READ, Scopes.FITNESS_LOCATION_READ_WRITE, Field.FIELD_RPM);
    @KeepName
    @RequiresPermission(conditional = true, value = "android.permission.ACCESS_FINE_LOCATION")
    public static final DataType TYPE_DISTANCE_CUMULATIVE = new DataType("com.google.distance.cumulative", Scopes.FITNESS_LOCATION_READ, Scopes.FITNESS_LOCATION_READ_WRITE, Field.FIELD_DISTANCE);
    @RequiresPermission(conditional = true, value = "android.permission.ACCESS_FINE_LOCATION")
    public static final DataType TYPE_DISTANCE_DELTA = new DataType("com.google.distance.delta", Scopes.FITNESS_LOCATION_READ, Scopes.FITNESS_LOCATION_READ_WRITE, Field.FIELD_DISTANCE);
    @RequiresPermission(conditional = true, value = "android.permission.BODY_SENSORS")
    public static final DataType TYPE_HEART_RATE_BPM = new DataType("com.google.heart_rate.bpm", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ, Field.FIELD_BPM);
    public static final DataType TYPE_HEIGHT = new DataType("com.google.height", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_HEIGHT);
    public static final DataType TYPE_HYDRATION = new DataType("com.google.hydration", Scopes.FITNESS_NUTRITION_READ, Scopes.FITNESS_NUTRITION_READ_WRITE, Field.FIELD_VOLUME);
    @RequiresPermission(conditional = true, value = "android.permission.ACCESS_FINE_LOCATION")
    public static final DataType TYPE_LOCATION_SAMPLE = new DataType("com.google.location.sample", Scopes.FITNESS_LOCATION_READ, Scopes.FITNESS_LOCATION_READ_WRITE, Field.FIELD_LATITUDE, Field.FIELD_LONGITUDE, Field.FIELD_ACCURACY, Field.FIELD_ALTITUDE);
    @RequiresPermission(conditional = true, value = "android.permission.ACCESS_FINE_LOCATION")
    public static final DataType TYPE_LOCATION_TRACK = new DataType("com.google.location.track", Scopes.FITNESS_LOCATION_READ, Scopes.FITNESS_LOCATION_READ_WRITE, Field.FIELD_LATITUDE, Field.FIELD_LONGITUDE, Field.FIELD_ACCURACY, Field.FIELD_ALTITUDE);
    public static final DataType TYPE_NUTRITION = new DataType("com.google.nutrition", Scopes.FITNESS_NUTRITION_READ, Scopes.FITNESS_NUTRITION_READ_WRITE, Field.FIELD_NUTRIENTS, Field.FIELD_MEAL_TYPE, Field.FIELD_FOOD_ITEM);
    public static final DataType TYPE_POWER_SAMPLE = new DataType("com.google.power.sample", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_WATTS);
    @RequiresPermission(conditional = true, value = "android.permission.ACCESS_FINE_LOCATION")
    public static final DataType TYPE_SPEED = new DataType("com.google.speed", Scopes.FITNESS_LOCATION_READ, Scopes.FITNESS_LOCATION_READ_WRITE, Field.FIELD_SPEED);
    public static final DataType TYPE_STEP_COUNT_CADENCE = new DataType("com.google.step_count.cadence", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_RPM);
    @KeepName
    public static final DataType TYPE_STEP_COUNT_CUMULATIVE = new DataType("com.google.step_count.cumulative", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_STEPS);
    public static final DataType TYPE_STEP_COUNT_DELTA = new DataType("com.google.step_count.delta", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_STEPS);
    public static final DataType TYPE_WEIGHT = new DataType("com.google.weight", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_WEIGHT);
    public static final DataType TYPE_WORKOUT_EXERCISE = new DataType("com.google.activity.exercise", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_EXERCISE, Field.FIELD_REPETITIONS, Field.FIELD_DURATION, Field.FIELD_RESISTANCE_TYPE, Field.FIELD_RESISTANCE);
    public static final DataType zzhav = new DataType("com.google.step_length", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_STEP_LENGTH);
    public static final DataType zzhaw = new DataType("com.google.internal.goal", Field.zzhcb);
    public static final DataType zzhax = new DataType("com.google.stride_model", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.zzhcc);
    public static final DataType zzhay = new DataType("com.google.floor_change", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.FIELD_ACTIVITY, Field.FIELD_CONFIDENCE, Field.zzhcd, Field.zzhcg);
    public static final DataType zzhaz = new DataType("com.google.accelerometer", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, com.google.android.gms.fitness.data.Field.zza.zzhcr, com.google.android.gms.fitness.data.Field.zza.zzhcs, com.google.android.gms.fitness.data.Field.zza.zzhct);
    public static final DataType zzhba = new DataType("com.google.sensor.events", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.zzhcj, Field.zzhcl, Field.zzhcp);
    public static final DataType zzhbb = new DataType("com.google.sensor.const_rate_events", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.zzhck, Field.zzhcm, Field.zzhcn, Field.zzhco, Field.zzhcp);
    public static final DataType zzhbc = new DataType("com.google.body.waist.circumference", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_CIRCUMFERENCE);
    public static final DataType zzhbd = new DataType("com.google.body.hip.circumference", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_CIRCUMFERENCE);
    public static final DataType zzhbe = new DataType("com.google.floor_change.summary", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, Field.zzhbz, Field.zzhca, Field.zzhce, Field.zzhcf, Field.zzhch, Field.zzhci);
    public static final DataType zzhbf = new DataType("com.google.body.hip.circumference.summary", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
    public static final DataType zzhbg = new DataType("com.google.body.waist.circumference.summary", Scopes.FITNESS_BODY_READ, Scopes.FITNESS_BODY_READ_WRITE, Field.FIELD_AVERAGE, Field.FIELD_MAX, Field.FIELD_MIN);
    private final String name;
    private final int versionCode;
    private final List<Field> zzhbh;
    private final String zzhbi;
    private final String zzhbj;

    public static final class zza {
        public static final DataType zzhbk = new DataType("com.google.internal.session.debug", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, com.google.android.gms.fitness.data.Field.zza.zzhcu);
        public static final DataType zzhbl = new DataType("com.google.internal.session.v2", Scopes.FITNESS_ACTIVITY_READ, Scopes.FITNESS_ACTIVITY_READ_WRITE, com.google.android.gms.fitness.data.Field.zza.zzhcv);
    }

    static {
        ArraySet arraySet = new ArraySet();
        AGGREGATE_INPUT_TYPES = arraySet;
        arraySet.add(TYPE_ACTIVITY_SEGMENT);
        AGGREGATE_INPUT_TYPES.add(TYPE_BASAL_METABOLIC_RATE);
        AGGREGATE_INPUT_TYPES.add(TYPE_BODY_FAT_PERCENTAGE);
        AGGREGATE_INPUT_TYPES.add(zzhbd);
        AGGREGATE_INPUT_TYPES.add(zzhbc);
        AGGREGATE_INPUT_TYPES.add(TYPE_CALORIES_CONSUMED);
        AGGREGATE_INPUT_TYPES.add(TYPE_CALORIES_EXPENDED);
        AGGREGATE_INPUT_TYPES.add(TYPE_DISTANCE_DELTA);
        AGGREGATE_INPUT_TYPES.add(zzhay);
        AGGREGATE_INPUT_TYPES.add(TYPE_LOCATION_SAMPLE);
        AGGREGATE_INPUT_TYPES.add(TYPE_NUTRITION);
        AGGREGATE_INPUT_TYPES.add(TYPE_HYDRATION);
        AGGREGATE_INPUT_TYPES.add(TYPE_HEART_RATE_BPM);
        AGGREGATE_INPUT_TYPES.add(TYPE_POWER_SAMPLE);
        AGGREGATE_INPUT_TYPES.add(TYPE_SPEED);
        AGGREGATE_INPUT_TYPES.add(TYPE_STEP_COUNT_DELTA);
        AGGREGATE_INPUT_TYPES.add(TYPE_WEIGHT);
    }

    DataType(int i, String str, List<Field> list, String str2, String str3) {
        this.versionCode = i;
        this.name = str;
        this.zzhbh = Collections.unmodifiableList(list);
        this.zzhbi = str2;
        this.zzhbj = str3;
    }

    public DataType(String str, String str2, String str3, Field... fieldArr) {
        this(1, str, Arrays.asList(fieldArr), str2, str3);
    }

    private DataType(String str, Field... fieldArr) {
        this(1, str, Arrays.asList(fieldArr), null, null);
    }

    public static List<DataType> getAggregatesForInput(DataType dataType) {
        List list = (List) zza.zzgzt.get(dataType);
        return list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
    }

    public static String getMimeType(DataType dataType) {
        String valueOf = String.valueOf(MIME_TYPE_PREFIX);
        String valueOf2 = String.valueOf(dataType.getName());
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof DataType)) {
                return false;
            }
            DataType dataType = (DataType) obj;
            if (!(this.name.equals(dataType.name) && this.zzhbh.equals(dataType.zzhbh))) {
                return false;
            }
        }
        return true;
    }

    public final List<Field> getFields() {
        return this.zzhbh;
    }

    public final String getName() {
        return this.name;
    }

    public final int hashCode() {
        return this.name.hashCode();
    }

    public final int indexOf(Field field) {
        int indexOf = this.zzhbh.indexOf(field);
        if (indexOf >= 0) {
            return indexOf;
        }
        throw new IllegalArgumentException(String.format("%s not a field of %s", new Object[]{field, this}));
    }

    public final String toString() {
        return String.format("DataType{%s%s}", new Object[]{this.name, this.zzhbh});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 1, getName(), false);
        zzbfp.zzc(parcel, 2, getFields(), false);
        zzbfp.zza(parcel, 3, this.zzhbi, false);
        zzbfp.zza(parcel, 4, this.zzhbj, false);
        zzbfp.zzc(parcel, 1000, this.versionCode);
        zzbfp.zzai(parcel, zze);
    }

    public final String zzaqn() {
        return this.zzhbi;
    }

    public final String zzaqo() {
        return this.zzhbj;
    }

    public final String zzaqp() {
        return this.name.startsWith("com.google.") ? this.name.substring(11) : this.name;
    }
}
