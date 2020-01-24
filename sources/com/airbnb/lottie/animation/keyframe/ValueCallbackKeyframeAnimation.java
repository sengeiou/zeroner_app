package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.Collections;

public class ValueCallbackKeyframeAnimation<K, A> extends BaseKeyframeAnimation<K, A> {
    private final LottieFrameInfo<A> frameInfo = new LottieFrameInfo<>();

    public ValueCallbackKeyframeAnimation(LottieValueCallback<A> valueCallback) {
        super(Collections.emptyList());
        setValueCallback(valueCallback);
    }

    /* access modifiers changed from: 0000 */
    public float getEndProgress() {
        return 1.0f;
    }

    public void notifyListeners() {
        if (this.valueCallback != null) {
            super.notifyListeners();
        }
    }

    public A getValue() {
        return this.valueCallback.getValueInternal(0.0f, 0.0f, null, null, getProgress(), getProgress(), getProgress());
    }

    /* access modifiers changed from: 0000 */
    public A getValue(Keyframe<K> keyframe, float keyframeProgress) {
        return getValue();
    }
}
