package com.google.android.gms.internal;

import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.fitness.data.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzbuz {
    public static final Set<String> zzheg = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"altitude", "duration", "food_item", "meal_type", "repetitions", "resistance", "resistance_type", "debug_session", "google.android.fitness.SessionV2"})));
    private static final zzbuz zzhej = new zzbuz();
    private final Map<String, Map<String, zzbvb>> zzheh;
    private final Map<String, zzbvb> zzhei;

    private zzbuz() {
        HashMap hashMap = new HashMap();
        hashMap.put("latitude", new zzbvb(-90.0d, 90.0d));
        hashMap.put("longitude", new zzbvb(-180.0d, 180.0d));
        hashMap.put("accuracy", new zzbvb(Utils.DOUBLE_EPSILON, 10000.0d));
        hashMap.put("bpm", new zzbvb(Utils.DOUBLE_EPSILON, 1000.0d));
        hashMap.put("altitude", new zzbvb(-100000.0d, 100000.0d));
        hashMap.put("percentage", new zzbvb(Utils.DOUBLE_EPSILON, 100.0d));
        hashMap.put("confidence", new zzbvb(Utils.DOUBLE_EPSILON, 100.0d));
        hashMap.put("duration", new zzbvb(Utils.DOUBLE_EPSILON, 9.223372036854776E18d));
        hashMap.put("height", new zzbvb(Utils.DOUBLE_EPSILON, 3.0d));
        hashMap.put("weight", new zzbvb(Utils.DOUBLE_EPSILON, 1000.0d));
        hashMap.put("speed", new zzbvb(Utils.DOUBLE_EPSILON, 11000.0d));
        this.zzhei = Collections.unmodifiableMap(hashMap);
        HashMap hashMap2 = new HashMap();
        hashMap2.put("com.google.step_count.delta", zzd("steps", new zzbvb(Utils.DOUBLE_EPSILON, 1.0E-8d)));
        hashMap2.put("com.google.calories.consumed", zzd(Field.NUTRIENT_CALORIES, new zzbvb(Utils.DOUBLE_EPSILON, 1.0E-6d)));
        hashMap2.put("com.google.calories.expended", zzd(Field.NUTRIENT_CALORIES, new zzbvb(Utils.DOUBLE_EPSILON, 5.555555555555555E-10d)));
        hashMap2.put("com.google.distance.delta", zzd("distance", new zzbvb(Utils.DOUBLE_EPSILON, 1.0E-7d)));
        this.zzheh = Collections.unmodifiableMap(hashMap2);
    }

    public static zzbuz zzaqs() {
        return zzhej;
    }

    private static <K, V> Map<K, V> zzd(K k, V v) {
        HashMap hashMap = new HashMap();
        hashMap.put(k, v);
        return hashMap;
    }

    /* access modifiers changed from: 0000 */
    public final zzbvb zzhn(String str) {
        return (zzbvb) this.zzhei.get(str);
    }

    /* access modifiers changed from: 0000 */
    public final zzbvb zzz(String str, String str2) {
        Map map = (Map) this.zzheh.get(str);
        if (map != null) {
            return (zzbvb) map.get(str2);
        }
        return null;
    }
}
