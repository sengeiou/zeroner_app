package com.airbnb.lottie.animation.keyframe;

import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseKeyframeAnimation<K, A> {
    @Nullable
    private Keyframe<K> cachedKeyframe;
    private boolean isDiscrete = false;
    private final List<? extends Keyframe<K>> keyframes;
    final List<AnimationListener> listeners = new ArrayList();
    private float progress = 0.0f;
    @Nullable
    protected LottieValueCallback<A> valueCallback;

    public interface AnimationListener {
        void onValueChanged();
    }

    /* access modifiers changed from: 0000 */
    public abstract A getValue(Keyframe<K> keyframe, float f);

    BaseKeyframeAnimation(List<? extends Keyframe<K>> keyframes2) {
        this.keyframes = keyframes2;
    }

    public void setIsDiscrete() {
        this.isDiscrete = true;
    }

    public void addUpdateListener(AnimationListener listener) {
        this.listeners.add(listener);
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float progress2) {
        if (progress2 < getStartDelayProgress()) {
            progress2 = getStartDelayProgress();
        } else if (progress2 > getEndProgress()) {
            progress2 = getEndProgress();
        }
        if (progress2 != this.progress) {
            this.progress = progress2;
            notifyListeners();
        }
    }

    public void notifyListeners() {
        for (int i = 0; i < this.listeners.size(); i++) {
            ((AnimationListener) this.listeners.get(i)).onValueChanged();
        }
    }

    private Keyframe<K> getCurrentKeyframe() {
        if (this.cachedKeyframe != null && this.cachedKeyframe.containsProgress(this.progress)) {
            return this.cachedKeyframe;
        }
        Keyframe<K> keyframe = (Keyframe) this.keyframes.get(this.keyframes.size() - 1);
        if (this.progress < keyframe.getStartProgress()) {
            for (int i = this.keyframes.size() - 1; i >= 0; i--) {
                keyframe = (Keyframe) this.keyframes.get(i);
                if (keyframe.containsProgress(this.progress)) {
                    break;
                }
            }
        }
        this.cachedKeyframe = keyframe;
        return keyframe;
    }

    /* access modifiers changed from: 0000 */
    public float getLinearCurrentKeyframeProgress() {
        if (this.isDiscrete) {
            return 0.0f;
        }
        Keyframe<K> keyframe = getCurrentKeyframe();
        if (!keyframe.isStatic()) {
            return (this.progress - keyframe.getStartProgress()) / (keyframe.getEndProgress() - keyframe.getStartProgress());
        }
        return 0.0f;
    }

    private float getInterpolatedCurrentKeyframeProgress() {
        Keyframe<K> keyframe = getCurrentKeyframe();
        if (keyframe.isStatic()) {
            return 0.0f;
        }
        return keyframe.interpolator.getInterpolation(getLinearCurrentKeyframeProgress());
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    private float getStartDelayProgress() {
        if (this.keyframes.isEmpty()) {
            return 0.0f;
        }
        return ((Keyframe) this.keyframes.get(0)).getStartProgress();
    }

    /* access modifiers changed from: 0000 */
    @FloatRange(from = 0.0d, to = 1.0d)
    public float getEndProgress() {
        if (this.keyframes.isEmpty()) {
            return 1.0f;
        }
        return ((Keyframe) this.keyframes.get(this.keyframes.size() - 1)).getEndProgress();
    }

    public A getValue() {
        return getValue(getCurrentKeyframe(), getInterpolatedCurrentKeyframeProgress());
    }

    public float getProgress() {
        return this.progress;
    }

    public void setValueCallback(@Nullable LottieValueCallback<A> valueCallback2) {
        if (this.valueCallback != null) {
            this.valueCallback.setAnimation(null);
        }
        this.valueCallback = valueCallback2;
        if (valueCallback2 != null) {
            valueCallback2.setAnimation(this);
        }
    }
}
