package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.content.GradientColor;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

public class GradientColorKeyframeAnimation extends KeyframeAnimation<GradientColor> {
    private final GradientColor gradientColor;

    public GradientColorKeyframeAnimation(List<Keyframe<GradientColor>> keyframes) {
        int size = 0;
        super(keyframes);
        GradientColor startValue = (GradientColor) ((Keyframe) keyframes.get(0)).startValue;
        if (startValue != null) {
            size = startValue.getSize();
        }
        this.gradientColor = new GradientColor(new float[size], new int[size]);
    }

    /* access modifiers changed from: 0000 */
    public GradientColor getValue(Keyframe<GradientColor> keyframe, float keyframeProgress) {
        this.gradientColor.lerp((GradientColor) keyframe.startValue, (GradientColor) keyframe.endValue, keyframeProgress);
        return this.gradientColor;
    }
}
