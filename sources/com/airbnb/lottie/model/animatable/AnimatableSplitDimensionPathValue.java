package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.SplitDimensionPathKeyframeAnimation;

public class AnimatableSplitDimensionPathValue implements AnimatableValue<PointF, PointF> {
    private final AnimatableFloatValue animatableXDimension;
    private final AnimatableFloatValue animatableYDimension;

    public AnimatableSplitDimensionPathValue(AnimatableFloatValue animatableXDimension2, AnimatableFloatValue animatableYDimension2) {
        this.animatableXDimension = animatableXDimension2;
        this.animatableYDimension = animatableYDimension2;
    }

    public BaseKeyframeAnimation<PointF, PointF> createAnimation() {
        return new SplitDimensionPathKeyframeAnimation(this.animatableXDimension.createAnimation(), this.animatableYDimension.createAnimation());
    }
}
