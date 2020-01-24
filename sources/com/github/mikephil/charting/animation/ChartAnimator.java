package com.github.mikephil.charting.animation;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import androidx.annotation.RequiresApi;
import com.github.mikephil.charting.animation.Easing.EasingFunction;

public class ChartAnimator {
    private AnimatorUpdateListener mListener;
    protected float mPhaseX = 1.0f;
    protected float mPhaseY = 1.0f;

    public ChartAnimator() {
    }

    @RequiresApi(11)
    public ChartAnimator(AnimatorUpdateListener listener) {
        this.mListener = listener;
    }

    @RequiresApi(11)
    private ObjectAnimator xAnimator(int duration, EasingFunction easing) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "phaseX", new float[]{0.0f, 1.0f});
        animatorX.setInterpolator(easing);
        animatorX.setDuration((long) duration);
        return animatorX;
    }

    @RequiresApi(11)
    private ObjectAnimator yAnimator(int duration, EasingFunction easing) {
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "phaseY", new float[]{0.0f, 1.0f});
        animatorY.setInterpolator(easing);
        animatorY.setDuration((long) duration);
        return animatorY;
    }

    @RequiresApi(11)
    public void animateX(int durationMillis) {
        animateX(durationMillis, Easing.Linear);
    }

    @RequiresApi(11)
    public void animateX(int durationMillis, EasingFunction easing) {
        ObjectAnimator animatorX = xAnimator(durationMillis, easing);
        animatorX.addUpdateListener(this.mListener);
        animatorX.start();
    }

    @RequiresApi(11)
    public void animateXY(int durationMillisX, int durationMillisY) {
        animateXY(durationMillisX, durationMillisY, Easing.Linear, Easing.Linear);
    }

    @RequiresApi(11)
    public void animateXY(int durationMillisX, int durationMillisY, EasingFunction easing) {
        ObjectAnimator xAnimator = xAnimator(durationMillisX, easing);
        ObjectAnimator yAnimator = yAnimator(durationMillisY, easing);
        if (durationMillisX > durationMillisY) {
            xAnimator.addUpdateListener(this.mListener);
        } else {
            yAnimator.addUpdateListener(this.mListener);
        }
        xAnimator.start();
        yAnimator.start();
    }

    @RequiresApi(11)
    public void animateXY(int durationMillisX, int durationMillisY, EasingFunction easingX, EasingFunction easingY) {
        ObjectAnimator xAnimator = xAnimator(durationMillisX, easingX);
        ObjectAnimator yAnimator = yAnimator(durationMillisY, easingY);
        if (durationMillisX > durationMillisY) {
            xAnimator.addUpdateListener(this.mListener);
        } else {
            yAnimator.addUpdateListener(this.mListener);
        }
        xAnimator.start();
        yAnimator.start();
    }

    @RequiresApi(11)
    public void animateY(int durationMillis) {
        animateY(durationMillis, Easing.Linear);
    }

    @RequiresApi(11)
    public void animateY(int durationMillis, EasingFunction easing) {
        ObjectAnimator animatorY = yAnimator(durationMillis, easing);
        animatorY.addUpdateListener(this.mListener);
        animatorY.start();
    }

    public float getPhaseY() {
        return this.mPhaseY;
    }

    public void setPhaseY(float phase) {
        if (phase > 1.0f) {
            phase = 1.0f;
        } else if (phase < 0.0f) {
            phase = 0.0f;
        }
        this.mPhaseY = phase;
    }

    public float getPhaseX() {
        return this.mPhaseX;
    }

    public void setPhaseX(float phase) {
        if (phase > 1.0f) {
            phase = 1.0f;
        } else if (phase < 0.0f) {
            phase = 0.0f;
        }
        this.mPhaseX = phase;
    }
}
