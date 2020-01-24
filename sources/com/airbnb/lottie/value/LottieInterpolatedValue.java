package com.airbnb.lottie.value;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

abstract class LottieInterpolatedValue<T> extends LottieValueCallback<T> {
    private final T endValue;
    private final Interpolator interpolator;
    private final T startValue;

    /* access modifiers changed from: 0000 */
    public abstract T interpolateValue(T t, T t2, float f);

    LottieInterpolatedValue(T startValue2, T endValue2) {
        this(startValue2, endValue2, new LinearInterpolator());
    }

    LottieInterpolatedValue(T startValue2, T endValue2, Interpolator interpolator2) {
        this.startValue = startValue2;
        this.endValue = endValue2;
        this.interpolator = interpolator2;
    }

    public T getValue(LottieFrameInfo<T> frameInfo) {
        return interpolateValue(this.startValue, this.endValue, this.interpolator.getInterpolation(frameInfo.getOverallProgress()));
    }
}
