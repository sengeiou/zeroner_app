package com.iwown.sport_module.ui.utils;

import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class WeightDataCalUtils {
    private static float percent_1_3 = 0.33333334f;

    static class WeightPercent {
        public float maxValue;
        public float minValue;

        public WeightPercent(float minValue2, float maxValue2) {
            this.minValue = minValue2;
            this.maxValue = maxValue2;
        }
    }

    public static float get3PercentByLimitValues(float bodyfat, float[] values) {
        List<WeightPercent> weightPercents = new ArrayList<>();
        for (int i = 0; i < values.length; i += 2) {
            weightPercents.add(new WeightPercent(values[i], values[i + 1]));
        }
        float sum_p = 0.0f;
        float temp_value = bodyfat;
        for (int i2 = 0; i2 < weightPercents.size(); i2++) {
            float size = ((WeightPercent) weightPercents.get(i2)).maxValue - ((WeightPercent) weightPercents.get(i2)).minValue;
            if (bodyfat <= ((WeightPercent) weightPercents.get(i2)).maxValue) {
                float v = temp_value / size;
                if (v <= 0.0f) {
                    v = 0.0f;
                    KLog.e("temp_value / size <0 exception please check code");
                }
                return sum_p + (percent_1_3 * v);
            }
            sum_p += percent_1_3;
            temp_value -= size;
        }
        return sum_p;
    }
}
