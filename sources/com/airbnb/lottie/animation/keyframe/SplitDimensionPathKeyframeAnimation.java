package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.value.Keyframe;
import java.util.Collections;

public class SplitDimensionPathKeyframeAnimation extends BaseKeyframeAnimation<PointF, PointF> {
    private final PointF point = new PointF();
    private final BaseKeyframeAnimation<Float, Float> xAnimation;
    private final BaseKeyframeAnimation<Float, Float> yAnimation;

    public SplitDimensionPathKeyframeAnimation(BaseKeyframeAnimation<Float, Float> xAnimation2, BaseKeyframeAnimation<Float, Float> yAnimation2) {
        super(Collections.emptyList());
        this.xAnimation = xAnimation2;
        this.yAnimation = yAnimation2;
        setProgress(getProgress());
    }

    public void setProgress(float progress) {
        this.xAnimation.setProgress(progress);
        this.yAnimation.setProgress(progress);
        this.point.set(((Float) this.xAnimation.getValue()).floatValue(), ((Float) this.yAnimation.getValue()).floatValue());
        for (int i = 0; i < this.listeners.size(); i++) {
            ((AnimationListener) this.listeners.get(i)).onValueChanged();
        }
    }

    public PointF getValue() {
        return getValue(null, 0.0f);
    }

    /* access modifiers changed from: 0000 */
    public PointF getValue(Keyframe<PointF> keyframe, float keyframeProgress) {
        return this.point;
    }
}
