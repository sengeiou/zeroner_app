package com.iwown.my_module.utility;

import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.math.BigDecimal;

public class MeasureTransform {
    public static String cm2Feet(double cm) {
        try {
            return new BigDecimal(cm * 0.0328084d).setScale(2, 4).toString();
        } catch (Exception e) {
            return "0";
        }
    }

    public static float cm2Inch(float cm) {
        try {
            return (float) Math.round(cm / 2.54f);
        } catch (NumberFormatException e) {
            ThrowableExtension.printStackTrace(e);
            return 0.0f;
        }
    }

    public static double cm2Inch(double cm) {
        try {
            return (double) Math.round(cm / 2.54d);
        } catch (NumberFormatException e) {
            ThrowableExtension.printStackTrace(e);
            return Utils.DOUBLE_EPSILON;
        }
    }

    public static float inch2Cm(int in) {
        return ((float) in) * 2.54f;
    }

    public static float kg2Lbs(float kg) {
        return new BigDecimal(((double) kg) * 2.2046226d).floatValue();
    }

    public static float lbs2Kg(float lbs) {
        return lbs * 0.45359f;
    }

    public static double lbs2Kg(double lbs) {
        return lbs * 0.4535900056362152d;
    }

    public static double kg2lb(double kg) {
        return 2.2046226d * kg;
    }
}
