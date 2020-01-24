package com.airbnb.lottie.value;

import android.view.animation.Interpolator;
import com.airbnb.lottie.utils.MiscUtils;

public class LottieInterpolatedFloatValue extends LottieInterpolatedValue<Float> {
    public LottieInterpolatedFloatValue(Float startValue, Float endValue) {
        super(startValue, endValue);
    }

    public LottieInterpolatedFloatValue(Float startValue, Float endValue, Interpolator interpolator) {
        super(startValue, endValue, interpolator);
    }

    /* access modifiers changed from: 0000 */
    public Float interpolateValue(Float startValue, Float endValue, float progress) {
        return Float.valueOf(MiscUtils.lerp(startValue.floatValue(), endValue.floatValue(), progress));
    }
}
