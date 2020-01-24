package com.iwown.device_module.device_setting.wifi_scale.util;

import com.github.mikephil.charting.utils.Utils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public class Cm2FtUtil {
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

    public static int ft2in(int ft) {
        return ft * 12;
    }
}
