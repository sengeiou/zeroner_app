package com.airbnb.lottie.utils;

import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import com.airbnb.lottie.LottieComposition;

public class LottieValueAnimator extends BaseLottieAnimator implements FrameCallback {
    @Nullable
    private LottieComposition composition;
    private float frame = 0.0f;
    @VisibleForTesting
    protected boolean isRunning = false;
    private long lastFrameTimeNs = 0;
    private float maxFrame = 2.14748365E9f;
    private float minFrame = -2.14748365E9f;
    private int repeatCount = 0;
    private float speed = 1.0f;

    public Object getAnimatedValue() {
        return Float.valueOf(getAnimatedValueAbsolute());
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getAnimatedValueAbsolute() {
        if (this.composition == null) {
            return 0.0f;
        }
        return (this.frame - this.composition.getStartFrame()) / (this.composition.getEndFrame() - this.composition.getStartFrame());
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getAnimatedFraction() {
        if (this.composition == null) {
            return 0.0f;
        }
        if (isReversed()) {
            return (getMaxFrame() - this.frame) / (getMaxFrame() - getMinFrame());
        }
        return (this.frame - getMinFrame()) / (getMaxFrame() - getMinFrame());
    }

    public long getDuration() {
        if (this.composition == null) {
            return 0;
        }
        return (long) this.composition.getDuration();
    }

    public float getFrame() {
        return this.frame;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void doFrame(long frameTimeNanos) {
        postFrameCallback();
        if (this.composition != null && isRunning()) {
            long now = System.nanoTime();
            float dFrames = ((float) (now - this.lastFrameTimeNs)) / getFrameDurationNs();
            float f = this.frame;
            if (isReversed()) {
                dFrames = -dFrames;
            }
            this.frame = f + dFrames;
            boolean ended = !MiscUtils.contains(this.frame, getMinFrame(), getMaxFrame());
            this.frame = MiscUtils.clamp(this.frame, getMinFrame(), getMaxFrame());
            this.lastFrameTimeNs = now;
            notifyUpdate();
            if (ended) {
                if (getRepeatCount() == -1 || this.repeatCount < getRepeatCount()) {
                    notifyRepeat();
                    this.repeatCount++;
                    if (getRepeatMode() == 2) {
                        reverseAnimationSpeed();
                    } else {
                        this.frame = isReversed() ? getMaxFrame() : getMinFrame();
                    }
                    this.lastFrameTimeNs = now;
                } else {
                    this.frame = getMaxFrame();
                    notifyEnd(isReversed());
                    removeFrameCallback();
                }
            }
            verifyFrame();
        }
    }

    private float getFrameDurationNs() {
        if (this.composition == null) {
            return Float.MAX_VALUE;
        }
        return (1.0E9f / this.composition.getFrameRate()) / Math.abs(this.speed);
    }

    public void setComposition(LottieComposition composition2) {
        this.composition = composition2;
        setMinAndMaxFrames((int) Math.max(this.minFrame, composition2.getStartFrame()), (int) Math.min(this.maxFrame, composition2.getEndFrame()));
        setFrame((int) this.frame);
        this.lastFrameTimeNs = System.nanoTime();
    }

    public void setFrame(int frame2) {
        if (this.frame != ((float) frame2)) {
            this.frame = MiscUtils.clamp((float) frame2, getMinFrame(), getMaxFrame());
            this.lastFrameTimeNs = System.nanoTime();
            notifyUpdate();
        }
    }

    public void setMinFrame(int minFrame2) {
        setMinAndMaxFrames(minFrame2, (int) this.maxFrame);
    }

    public void setMaxFrame(int maxFrame2) {
        setMinAndMaxFrames((int) this.minFrame, maxFrame2);
    }

    public void setMinAndMaxFrames(int minFrame2, int maxFrame2) {
        this.minFrame = (float) minFrame2;
        this.maxFrame = (float) maxFrame2;
        setFrame((int) MiscUtils.clamp(this.frame, (float) minFrame2, (float) maxFrame2));
    }

    public void reverseAnimationSpeed() {
        setSpeed(-getSpeed());
    }

    public void setSpeed(float speed2) {
        this.speed = speed2;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void playAnimation() {
        notifyStart(isReversed());
        setFrame((int) (isReversed() ? getMaxFrame() : getMinFrame()));
        this.lastFrameTimeNs = System.nanoTime();
        this.repeatCount = 0;
        postFrameCallback();
    }

    public void endAnimation() {
        removeFrameCallback();
        notifyEnd(isReversed());
    }

    public void pauseAnimation() {
        removeFrameCallback();
    }

    public void resumeAnimation() {
        postFrameCallback();
        this.lastFrameTimeNs = System.nanoTime();
        if (isReversed() && getFrame() == getMinFrame()) {
            this.frame = getMaxFrame();
        } else if (!isReversed() && getFrame() == getMaxFrame()) {
            this.frame = getMinFrame();
        }
    }

    public void cancel() {
        notifyCancel();
        removeFrameCallback();
    }

    private boolean isReversed() {
        return this.speed < 0.0f;
    }

    public float getMinFrame() {
        if (this.composition == null) {
            return 0.0f;
        }
        return this.minFrame == -2.14748365E9f ? this.composition.getStartFrame() : this.minFrame;
    }

    public float getMaxFrame() {
        if (this.composition == null) {
            return 0.0f;
        }
        return this.maxFrame == 2.14748365E9f ? this.composition.getEndFrame() : this.maxFrame;
    }

    /* access modifiers changed from: protected */
    public void postFrameCallback() {
        removeFrameCallback();
        Choreographer.getInstance().postFrameCallback(this);
        this.isRunning = true;
    }

    /* access modifiers changed from: protected */
    public void removeFrameCallback() {
        Choreographer.getInstance().removeFrameCallback(this);
        this.isRunning = false;
    }

    private void verifyFrame() {
        if (this.composition != null) {
            if (this.frame < this.minFrame || this.frame > this.maxFrame) {
                throw new IllegalStateException(String.format("Frame must be [%f,%f]. It is %f", new Object[]{Float.valueOf(this.minFrame), Float.valueOf(this.maxFrame), Float.valueOf(this.frame)}));
            }
        }
    }
}
