package com.airbnb.lottie.utils;

import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Build.VERSION;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class BaseLottieAnimator extends ValueAnimator {
    private final Set<AnimatorListener> listeners = new CopyOnWriteArraySet();
    private final Set<AnimatorUpdateListener> updateListeners = new CopyOnWriteArraySet();

    public long getStartDelay() {
        throw new UnsupportedOperationException("LottieAnimator does not support getStartDelay.");
    }

    public void setStartDelay(long startDelay) {
        throw new UnsupportedOperationException("LottieAnimator does not support setStartDelay.");
    }

    public ValueAnimator setDuration(long duration) {
        throw new UnsupportedOperationException("LottieAnimator does not support setDuration.");
    }

    public void setInterpolator(TimeInterpolator value) {
        throw new UnsupportedOperationException("LottieAnimator does not support setInterpolator.");
    }

    public void addUpdateListener(AnimatorUpdateListener listener) {
        this.updateListeners.add(listener);
    }

    public void removeUpdateListener(AnimatorUpdateListener listener) {
        this.updateListeners.remove(listener);
    }

    public void removeAllUpdateListeners() {
        this.updateListeners.clear();
    }

    public void addListener(AnimatorListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(AnimatorListener listener) {
        this.listeners.remove(listener);
    }

    public void removeAllListeners() {
        this.listeners.clear();
    }

    /* access modifiers changed from: 0000 */
    public void notifyStart(boolean isReverse) {
        for (AnimatorListener listener : this.listeners) {
            if (VERSION.SDK_INT >= 26) {
                listener.onAnimationStart(this, isReverse);
            } else {
                listener.onAnimationStart(this);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void notifyRepeat() {
        for (AnimatorListener listener : this.listeners) {
            listener.onAnimationRepeat(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void notifyEnd(boolean isReverse) {
        for (AnimatorListener listener : this.listeners) {
            if (VERSION.SDK_INT >= 26) {
                listener.onAnimationEnd(this, isReverse);
            } else {
                listener.onAnimationEnd(this);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void notifyCancel() {
        for (AnimatorListener listener : this.listeners) {
            listener.onAnimationCancel(this);
        }
    }

    /* access modifiers changed from: 0000 */
    public void notifyUpdate() {
        for (AnimatorUpdateListener listener : this.updateListeners) {
            listener.onAnimationUpdate(this);
        }
    }
}
