package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

public class ColorKeyframeAnimation extends KeyframeAnimation<Integer> {
    public ColorKeyframeAnimation(List<Keyframe<Integer>> keyframes) {
        super(keyframes);
    }

    public Integer getValue(Keyframe<Integer> keyframe, float keyframeProgress) {
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        int startColor = ((Integer) keyframe.startValue).intValue();
        int endColor = ((Integer) keyframe.endValue).intValue();
        if (this.valueCallback == null) {
            return Integer.valueOf(GammaEvaluator.evaluate(keyframeProgress, startColor, endColor));
        }
        return (Integer) this.valueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), Integer.valueOf(startColor), Integer.valueOf(endColor), keyframeProgress, getLinearCurrentKeyframeProgress(), getProgress());
    }
}
