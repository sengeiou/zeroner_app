package com.airbnb.lottie.value;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import com.airbnb.lottie.utils.MiscUtils;

public class LottieInterpolatedPointValue extends LottieInterpolatedValue<PointF> {
    private final PointF point = new PointF();

    public LottieInterpolatedPointValue(PointF startValue, PointF endValue) {
        super(startValue, endValue);
    }

    public LottieInterpolatedPointValue(PointF startValue, PointF endValue, Interpolator interpolator) {
        super(startValue, endValue, interpolator);
    }

    /* access modifiers changed from: 0000 */
    public PointF interpolateValue(PointF startValue, PointF endValue, float progress) {
        this.point.set(MiscUtils.lerp(startValue.x, endValue.x, progress), MiscUtils.lerp(startValue.y, endValue.y, progress));
        return this.point;
    }
}
