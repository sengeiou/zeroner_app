package com.airbnb.lottie.animation.keyframe;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.ScaleXY;

public class TransformKeyframeAnimation {
    private final BaseKeyframeAnimation<PointF, PointF> anchorPoint;
    @Nullable
    private final BaseKeyframeAnimation<?, Float> endOpacity;
    private final Matrix matrix = new Matrix();
    private final BaseKeyframeAnimation<Integer, Integer> opacity;
    private final BaseKeyframeAnimation<?, PointF> position;
    private final BaseKeyframeAnimation<Float, Float> rotation;
    private final BaseKeyframeAnimation<ScaleXY, ScaleXY> scale;
    @Nullable
    private final BaseKeyframeAnimation<?, Float> startOpacity;

    public TransformKeyframeAnimation(AnimatableTransform animatableTransform) {
        this.anchorPoint = animatableTransform.getAnchorPoint().createAnimation();
        this.position = animatableTransform.getPosition().createAnimation();
        this.scale = animatableTransform.getScale().createAnimation();
        this.rotation = animatableTransform.getRotation().createAnimation();
        this.opacity = animatableTransform.getOpacity().createAnimation();
        if (animatableTransform.getStartOpacity() != null) {
            this.startOpacity = animatableTransform.getStartOpacity().createAnimation();
        } else {
            this.startOpacity = null;
        }
        if (animatableTransform.getEndOpacity() != null) {
            this.endOpacity = animatableTransform.getEndOpacity().createAnimation();
        } else {
            this.endOpacity = null;
        }
    }

    public void addAnimationsToLayer(BaseLayer layer) {
        layer.addAnimation(this.anchorPoint);
        layer.addAnimation(this.position);
        layer.addAnimation(this.scale);
        layer.addAnimation(this.rotation);
        layer.addAnimation(this.opacity);
        if (this.startOpacity != null) {
            layer.addAnimation(this.startOpacity);
        }
        if (this.endOpacity != null) {
            layer.addAnimation(this.endOpacity);
        }
    }

    public void addListener(AnimationListener listener) {
        this.anchorPoint.addUpdateListener(listener);
        this.position.addUpdateListener(listener);
        this.scale.addUpdateListener(listener);
        this.rotation.addUpdateListener(listener);
        this.opacity.addUpdateListener(listener);
        if (this.startOpacity != null) {
            this.startOpacity.addUpdateListener(listener);
        }
        if (this.endOpacity != null) {
            this.endOpacity.addUpdateListener(listener);
        }
    }

    public void setProgress(float progress) {
        this.anchorPoint.setProgress(progress);
        this.position.setProgress(progress);
        this.scale.setProgress(progress);
        this.rotation.setProgress(progress);
        this.opacity.setProgress(progress);
        if (this.startOpacity != null) {
            this.startOpacity.setProgress(progress);
        }
        if (this.endOpacity != null) {
            this.endOpacity.setProgress(progress);
        }
    }

    public BaseKeyframeAnimation<?, Integer> getOpacity() {
        return this.opacity;
    }

    @Nullable
    public BaseKeyframeAnimation<?, Float> getStartOpacity() {
        return this.startOpacity;
    }

    @Nullable
    public BaseKeyframeAnimation<?, Float> getEndOpacity() {
        return this.endOpacity;
    }

    public Matrix getMatrix() {
        this.matrix.reset();
        PointF position2 = (PointF) this.position.getValue();
        if (!(position2.x == 0.0f && position2.y == 0.0f)) {
            this.matrix.preTranslate(position2.x, position2.y);
        }
        float rotation2 = ((Float) this.rotation.getValue()).floatValue();
        if (rotation2 != 0.0f) {
            this.matrix.preRotate(rotation2);
        }
        ScaleXY scaleTransform = (ScaleXY) this.scale.getValue();
        if (!(scaleTransform.getScaleX() == 1.0f && scaleTransform.getScaleY() == 1.0f)) {
            this.matrix.preScale(scaleTransform.getScaleX(), scaleTransform.getScaleY());
        }
        PointF anchorPoint2 = (PointF) this.anchorPoint.getValue();
        if (!(anchorPoint2.x == 0.0f && anchorPoint2.y == 0.0f)) {
            this.matrix.preTranslate(-anchorPoint2.x, -anchorPoint2.y);
        }
        return this.matrix;
    }

    public Matrix getMatrixForRepeater(float amount) {
        PointF position2 = (PointF) this.position.getValue();
        PointF anchorPoint2 = (PointF) this.anchorPoint.getValue();
        ScaleXY scale2 = (ScaleXY) this.scale.getValue();
        float rotation2 = ((Float) this.rotation.getValue()).floatValue();
        this.matrix.reset();
        this.matrix.preTranslate(position2.x * amount, position2.y * amount);
        this.matrix.preScale((float) Math.pow((double) scale2.getScaleX(), (double) amount), (float) Math.pow((double) scale2.getScaleY(), (double) amount));
        this.matrix.preRotate(rotation2 * amount, anchorPoint2.x, anchorPoint2.y);
        return this.matrix;
    }

    public <T> boolean applyValueCallback(T property, @Nullable LottieValueCallback<T> callback) {
        if (property == LottieProperty.TRANSFORM_ANCHOR_POINT) {
            this.anchorPoint.setValueCallback(callback);
        } else if (property == LottieProperty.TRANSFORM_POSITION) {
            this.position.setValueCallback(callback);
        } else if (property == LottieProperty.TRANSFORM_SCALE) {
            this.scale.setValueCallback(callback);
        } else if (property == LottieProperty.TRANSFORM_ROTATION) {
            this.rotation.setValueCallback(callback);
        } else if (property == LottieProperty.TRANSFORM_OPACITY) {
            this.opacity.setValueCallback(callback);
        } else if (property == LottieProperty.TRANSFORM_START_OPACITY && this.startOpacity != null) {
            this.startOpacity.setValueCallback(callback);
        } else if (property != LottieProperty.TRANSFORM_END_OPACITY || this.endOpacity == null) {
            return false;
        } else {
            this.endOpacity.setValueCallback(callback);
        }
        return true;
    }
}
