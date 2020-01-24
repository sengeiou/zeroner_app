package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.ModifierContent;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.layer.BaseLayer;

public class AnimatableTransform implements ModifierContent, ContentModel {
    private final AnimatablePathValue anchorPoint;
    @Nullable
    private final AnimatableFloatValue endOpacity;
    private final AnimatableIntegerValue opacity;
    private final AnimatableValue<PointF, PointF> position;
    private final AnimatableFloatValue rotation;
    private final AnimatableScaleValue scale;
    @Nullable
    private final AnimatableFloatValue startOpacity;

    public AnimatableTransform() {
        this(new AnimatablePathValue(), new AnimatablePathValue(), new AnimatableScaleValue(), new AnimatableFloatValue(), new AnimatableIntegerValue(), new AnimatableFloatValue(), new AnimatableFloatValue());
    }

    public AnimatableTransform(AnimatablePathValue anchorPoint2, AnimatableValue<PointF, PointF> position2, AnimatableScaleValue scale2, AnimatableFloatValue rotation2, AnimatableIntegerValue opacity2, @Nullable AnimatableFloatValue startOpacity2, @Nullable AnimatableFloatValue endOpacity2) {
        this.anchorPoint = anchorPoint2;
        this.position = position2;
        this.scale = scale2;
        this.rotation = rotation2;
        this.opacity = opacity2;
        this.startOpacity = startOpacity2;
        this.endOpacity = endOpacity2;
    }

    public AnimatablePathValue getAnchorPoint() {
        return this.anchorPoint;
    }

    public AnimatableValue<PointF, PointF> getPosition() {
        return this.position;
    }

    public AnimatableScaleValue getScale() {
        return this.scale;
    }

    public AnimatableFloatValue getRotation() {
        return this.rotation;
    }

    public AnimatableIntegerValue getOpacity() {
        return this.opacity;
    }

    @Nullable
    public AnimatableFloatValue getStartOpacity() {
        return this.startOpacity;
    }

    @Nullable
    public AnimatableFloatValue getEndOpacity() {
        return this.endOpacity;
    }

    public TransformKeyframeAnimation createAnimation() {
        return new TransformKeyframeAnimation(this);
    }

    @Nullable
    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return null;
    }
}
