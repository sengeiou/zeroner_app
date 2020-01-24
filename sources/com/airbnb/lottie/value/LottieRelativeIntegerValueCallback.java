package com.airbnb.lottie.value;

import com.airbnb.lottie.utils.MiscUtils;

public class LottieRelativeIntegerValueCallback extends LottieValueCallback<Integer> {
    public Integer getValue(LottieFrameInfo<Integer> frameInfo) {
        return Integer.valueOf(MiscUtils.lerp(((Integer) frameInfo.getStartValue()).intValue(), ((Integer) frameInfo.getEndValue()).intValue(), frameInfo.getInterpolatedKeyframeProgress()) + getOffset(frameInfo).intValue());
    }

    public Integer getOffset(LottieFrameInfo<Integer> lottieFrameInfo) {
        if (this.value != null) {
            return (Integer) this.value;
        }
        throw new IllegalArgumentException("You must provide a static value in the constructor , call setValue, or override getValue.");
    }
}
