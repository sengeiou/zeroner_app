package com.airbnb.lottie.value;

import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;

public class LottieValueCallback<T> {
    @Nullable
    BaseKeyframeAnimation<?, ?> animation;
    private final LottieFrameInfo<T> frameInfo = new LottieFrameInfo<>();
    @Nullable
    protected T value = null;

    public LottieValueCallback() {
    }

    public LottieValueCallback(@Nullable T staticValue) {
        this.value = staticValue;
    }

    public T getValue(LottieFrameInfo<T> lottieFrameInfo) {
        return this.value;
    }

    public final void setValue(@Nullable T value2) {
        this.value = value2;
        if (this.animation != null) {
            this.animation.notifyListeners();
        }
    }

    @RestrictTo({Scope.LIBRARY})
    public final T getValueInternal(float startFrame, float endFrame, T startValue, T endValue, float linearKeyframeProgress, float interpolatedKeyframeProgress, float overallProgress) {
        return getValue(this.frameInfo.set(startFrame, endFrame, startValue, endValue, linearKeyframeProgress, interpolatedKeyframeProgress, overallProgress));
    }

    @RestrictTo({Scope.LIBRARY})
    public final void setAnimation(@Nullable BaseKeyframeAnimation<?, ?> animation2) {
        this.animation = animation2;
    }
}
