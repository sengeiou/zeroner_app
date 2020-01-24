package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PathKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.Collections;
import java.util.List;

public class AnimatablePathValue implements AnimatableValue<PointF, PointF> {
    private final List<Keyframe<PointF>> keyframes;

    public AnimatablePathValue() {
        this.keyframes = Collections.singletonList(new Keyframe(new PointF(0.0f, 0.0f)));
    }

    public AnimatablePathValue(List<Keyframe<PointF>> keyframes2) {
        this.keyframes = keyframes2;
    }

    public BaseKeyframeAnimation<PointF, PointF> createAnimation() {
        if (((Keyframe) this.keyframes.get(0)).isStatic()) {
            return new PointKeyframeAnimation(this.keyframes);
        }
        return new PathKeyframeAnimation(this.keyframes);
    }
}
