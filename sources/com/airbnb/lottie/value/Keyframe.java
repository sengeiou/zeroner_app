package com.airbnb.lottie.value;

import android.graphics.PointF;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.view.animation.Interpolator;
import com.airbnb.lottie.LottieComposition;

public class Keyframe<T> {
    @Nullable
    private final LottieComposition composition;
    @Nullable
    public Float endFrame;
    private float endProgress;
    @Nullable
    public final T endValue;
    @Nullable
    public final Interpolator interpolator;
    public PointF pathCp1;
    public PointF pathCp2;
    public final float startFrame;
    private float startProgress;
    @Nullable
    public final T startValue;

    public Keyframe(LottieComposition composition2, @Nullable T startValue2, @Nullable T endValue2, @Nullable Interpolator interpolator2, float startFrame2, @Nullable Float endFrame2) {
        this.startProgress = Float.MIN_VALUE;
        this.endProgress = Float.MIN_VALUE;
        this.pathCp1 = null;
        this.pathCp2 = null;
        this.composition = composition2;
        this.startValue = startValue2;
        this.endValue = endValue2;
        this.interpolator = interpolator2;
        this.startFrame = startFrame2;
        this.endFrame = endFrame2;
    }

    public Keyframe(T value) {
        this.startProgress = Float.MIN_VALUE;
        this.endProgress = Float.MIN_VALUE;
        this.pathCp1 = null;
        this.pathCp2 = null;
        this.composition = null;
        this.startValue = value;
        this.endValue = value;
        this.interpolator = null;
        this.startFrame = Float.MIN_VALUE;
        this.endFrame = Float.valueOf(Float.MAX_VALUE);
    }

    public float getStartProgress() {
        if (this.composition == null) {
            return 0.0f;
        }
        if (this.startProgress == Float.MIN_VALUE) {
            this.startProgress = (this.startFrame - this.composition.getStartFrame()) / this.composition.getDurationFrames();
        }
        return this.startProgress;
    }

    public float getEndProgress() {
        if (this.composition == null) {
            return 1.0f;
        }
        if (this.endProgress == Float.MIN_VALUE) {
            if (this.endFrame == null) {
                this.endProgress = 1.0f;
            } else {
                this.endProgress = getStartProgress() + ((this.endFrame.floatValue() - this.startFrame) / this.composition.getDurationFrames());
            }
        }
        return this.endProgress;
    }

    public boolean isStatic() {
        return this.interpolator == null;
    }

    public boolean containsProgress(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        return progress >= getStartProgress() && progress < getEndProgress();
    }

    public String toString() {
        return "Keyframe{startValue=" + this.startValue + ", endValue=" + this.endValue + ", startFrame=" + this.startFrame + ", endFrame=" + this.endFrame + ", interpolator=" + this.interpolator + '}';
    }
}
