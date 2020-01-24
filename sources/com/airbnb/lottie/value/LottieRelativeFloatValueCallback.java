package com.airbnb.lottie.value;

import android.support.annotation.NonNull;
import com.airbnb.lottie.utils.MiscUtils;

public class LottieRelativeFloatValueCallback extends LottieValueCallback<Float> {
    public LottieRelativeFloatValueCallback() {
    }

    public LottieRelativeFloatValueCallback(@NonNull Float staticValue) {
        super(staticValue);
    }

    public Float getValue(LottieFrameInfo<Float> frameInfo) {
        return Float.valueOf(MiscUtils.lerp(((Float) frameInfo.getStartValue()).floatValue(), ((Float) frameInfo.getEndValue()).floatValue(), frameInfo.getInterpolatedKeyframeProgress()) + getOffset(frameInfo).floatValue());
    }

    public Float getOffset(LottieFrameInfo<Float> lottieFrameInfo) {
        if (this.value != null) {
            return (Float) this.value;
        }
        throw new IllegalArgumentException("You must provide a static value in the constructor , call setValue, or override getValue.");
    }
}
