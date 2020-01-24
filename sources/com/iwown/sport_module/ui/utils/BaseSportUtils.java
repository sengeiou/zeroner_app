package com.iwown.sport_module.ui.utils;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.math.BigDecimal;

public class BaseSportUtils {
    public static String getUI_DataFrom_Month(DateUtil dateUtil) {
        return UserConfig.getInstance().getNewUID() + UserConfig.getInstance().getDevice() + dateUtil.getYyyyMMDate();
    }

    public static double getDouble1(float target_weight) {
        try {
            return new BigDecimal((double) target_weight).setScale(1, 4).doubleValue();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return Utils.DOUBLE_EPSILON;
        }
    }

    public static float getWeightMusclePercent(float muscule, float height, boolean male) {
        if (male) {
            if (height < 160.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(muscule, new float[]{0.0f, 38.4f, 38.5f, 46.5f, 46.6f, 49.5f});
            }
            if (height >= 160.0f && height <= 170.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(muscule, new float[]{0.0f, 43.0f, 43.9f, 52.4f, 52.5f, 57.4f});
            }
            if (height > 170.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(muscule, new float[]{0.0f, 49.3f, 49.4f, 59.4f, 59.5f, 52.4f});
            }
            return 0.0f;
        } else if (height < 150.0f) {
            return WeightDataCalUtils.get3PercentByLimitValues(muscule, new float[]{0.0f, 29.0f, 29.1f, 34.7f, 34.8f, 36.7f});
        } else {
            if (height >= 150.0f && height <= 160.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(muscule, new float[]{0.0f, 32.8f, 32.9f, 37.5f, 37.6f, 39.5f});
            }
            if (height > 160.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(muscule, new float[]{0.0f, 36.4f, 36.5f, 42.5f, 42.6f, 44.5f});
            }
            return 0.0f;
        }
    }

    public static float getWeightFatPercent(float bodyfat, int age, boolean male) {
        KLog.e("  " + age);
        if (male) {
            if (age <= 10 || age == 11) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 6.9f, 7.0f, 25.9f, 26.0f, 31.0f});
            }
            if (age >= 12 && age <= 14) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 6.9f, 7.0f, 24.9f, 25.0f, 30.0f});
            }
            if (age >= 15 && age <= 16) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 7.9f, 8.0f, 23.9f, 24.0f, 29.0f});
            }
            if (age == 17) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 8.9f, 9.0f, 22.9f, 23.0f, 28.0f});
            }
            if (age >= 18 && age <= 39) {
                float value = WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 10.9f, 11.0f, 21.9f, 22.0f, 27.0f});
                KLog.e("getWeightFatPercent " + age + "  " + bodyfat + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + value);
                return value;
            } else if (age >= 40 && age <= 59) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 11.9f, 12.0f, 22.9f, 23.0f, 28.0f});
            } else {
                if (age >= 60) {
                    return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 13.9f, 14.0f, 24.9f, 25.0f, 30.0f});
                }
                return 0.0f;
            }
        } else if (age <= 10) {
            return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 10.9f, 11.0f, 28.9f, 29.0f, 35.0f});
        } else {
            if (age == 11) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 12.9f, 13.0f, 30.9f, 31.0f, 37.0f});
            }
            if (age == 12) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 13.9f, 14.0f, 31.9f, 32.0f, 38.0f});
            }
            if (age == 13) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 14.9f, 15.0f, 33.9f, 34.0f, 40.0f});
            }
            if (age == 14) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 16.9f, 17.0f, 34.9f, 25.0f, 41.0f});
            }
            if (age == 15) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 17.9f, 18.0f, 35.9f, 36.0f, 42.0f});
            }
            if (age == 16) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 18.9f, 19.0f, 36.9f, 37.0f, 43.0f});
            }
            if (age == 17) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 19.9f, 20.0f, 36.9f, 37.0f, 43.0f});
            }
            if (age >= 18 && age <= 39) {
                float value2 = WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 20.9f, 21.0f, 34.9f, 35.0f, 41.0f});
                KLog.e("getWeightFatPercent " + age + "  " + bodyfat + "  " + value2);
                return value2;
            } else if (age >= 40 && age <= 59) {
                return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 21.9f, 22.0f, 35.9f, 36.0f, 42.0f});
            } else {
                if (age >= 60) {
                    return WeightDataCalUtils.get3PercentByLimitValues(bodyfat, new float[]{0.0f, 22.9f, 23.0f, 36.9f, 37.0f, 43.0f});
                }
                return 0.0f;
            }
        }
    }

    public static float getWeightWatherPercent(float water, boolean male) {
        if (male) {
            return WeightDataCalUtils.get3PercentByLimitValues(water, new float[]{0.0f, 54.0f, 55.0f, 65.0f, 66.0f, 80.0f});
        }
        return WeightDataCalUtils.get3PercentByLimitValues(water, new float[]{0.0f, 44.0f, 45.0f, 60.0f, 61.0f, 75.0f});
    }

    public static float getWeightBonePercent(float bone_weight, float weight, boolean male) {
        if (male) {
            if (weight < 60.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(bone_weight, new float[]{0.0f, 1.9f, 2.0f, 3.0f, 3.1f, 3.45f});
            }
            if (weight >= 60.0f && weight < 75.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(bone_weight, new float[]{0.0f, 2.31f, 2.32f, 3.48f, 3.49f, 4.0f});
            }
            if (weight >= 75.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(bone_weight, new float[]{0.0f, 2.55f, 2.56f, 3.84f, 3.85f, 4.41f});
            }
            return 0.0f;
        } else if (weight < 45.0f) {
            return WeightDataCalUtils.get3PercentByLimitValues(bone_weight, new float[]{0.0f, 1.43f, 1.44f, 2.16f, 2.17f, 2.484f});
        } else {
            if (weight >= 45.0f && weight < 60.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(bone_weight, new float[]{0.0f, 1.75f, 1.76f, 2.64f, 2.65f, 3.0f});
            }
            if (weight >= 60.0f) {
                return WeightDataCalUtils.get3PercentByLimitValues(bone_weight, new float[]{0.0f, 1.9f, 2.0f, 3.0f, 3.1f, 3.45f});
            }
            return 0.0f;
        }
    }

    public static float getWeightVisceral_fatPercent(int visceral_fat) {
        return WeightDataCalUtils.get3PercentByLimitValues((float) visceral_fat, new float[]{0.0f, 0.0f, 0.0f, 9.0f, 9.1f, 17.0f});
    }

    public static float getWeightMetabolismPercent(float calorie, boolean male, int age) {
        if (!male) {
            if (age <= 11) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1002.0f, 1003.0f, 1375.0f, 1375.0f, 1492.0f});
            }
            if (age >= 12 && age <= 14) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1138.0f, 1139.0f, 1541.0f, 1542.0f, 1695.0f});
            }
            if (age >= 15 && age <= 17) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1104.0f, 1105.0f, 1495.0f, 1496.0f, 1644.0f});
            }
            if (age >= 18 && age <= 29) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1027.0f, 1028.0f, 1391.0f, 1392.0f, 1530.0f});
            }
            if (age >= 30 && age <= 49) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 993.0f, 994.0f, 1345.0f, 1346.0f, 1480.0f});
            }
            if (age >= 50 && age <= 69) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 942.0f, 943.0f, 1276.0f, 1277.0f, 1403.0f});
            }
            if (age >= 70) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 857.0f, 858.0f, 1161.0f, 1162.0f, 1277.0f});
            }
            return 0.0f;
        } else if (age <= 11) {
            return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1095.0f, 1096.0f, 1485.0f, 1486.0f, 1631.0f});
        } else {
            if (age >= 12 && age <= 14) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1257.0f, 1258.0f, 1702.0f, 1703.0f, 1872.0f});
            }
            if (age >= 15 && age <= 17) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1367.0f, 1368.0f, 1851.0f, 1852.0f, 2036.0f});
            }
            if (age >= 18 && age <= 29) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1316.0f, 1317.0f, 1782.0f, 1783.0f, 1960.0f});
            }
            if (age >= 30 && age <= 49) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1274.0f, 1275.0f, 1725.0f, 1726.0f, 1897.0f});
            }
            if (age >= 50 && age <= 69) {
                return WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1146.0f, 1147.0f, 1552.0f, 1553.0f, 1707.0f});
            }
            if (age >= 70) {
                return (float) ((int) WeightDataCalUtils.get3PercentByLimitValues(calorie, new float[]{0.0f, 1036.0f, 1038.0f, 1403.0f, 1404.0f, 1543.0f}));
            }
            return 0.0f;
        }
    }

    public static float getBMIFatPercent(float bmi) {
        return WeightDataCalUtils.get3PercentByLimitValues(bmi, new float[]{0.0f, 18.4f, 18.5f, 23.9f, 24.0f, 28.0f});
    }

    public static float getLbsFromKG(float weight) {
        return 2.2046225f * weight;
    }
}
