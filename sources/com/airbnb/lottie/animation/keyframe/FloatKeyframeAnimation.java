package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

public class FloatKeyframeAnimation extends KeyframeAnimation<Float> {
    public FloatKeyframeAnimation(List<Keyframe<Float>> keyframes) {
        super(keyframes);
    }

    /* access modifiers changed from: 0000 */
    public Float getValue(Keyframe<Float> keyframe, float keyframeProgress) {
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        } else if (this.valueCallback == null) {
            return Float.valueOf(MiscUtils.lerp(((Float) keyframe.startValue).floatValue(), ((Float) keyframe.endValue).floatValue(), keyframeProgress));
        } else {
            return (Float) this.valueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), keyframe.startValue, keyframe.endValue, keyframeProgress, getLinearCurrentKeyframeProgress(), getProgress());
        }
    }
}
