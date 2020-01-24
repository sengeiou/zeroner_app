package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.ScaleXY;
import java.util.List;

public class ScaleKeyframeAnimation extends KeyframeAnimation<ScaleXY> {
    public ScaleKeyframeAnimation(List<Keyframe<ScaleXY>> keyframes) {
        super(keyframes);
    }

    public ScaleXY getValue(Keyframe<ScaleXY> keyframe, float keyframeProgress) {
        if (keyframe.startValue == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        ScaleXY startTransform = (ScaleXY) keyframe.startValue;
        ScaleXY endTransform = (ScaleXY) keyframe.endValue;
        if (this.valueCallback == null) {
            return new ScaleXY(MiscUtils.lerp(startTransform.getScaleX(), endTransform.getScaleX(), keyframeProgress), MiscUtils.lerp(startTransform.getScaleY(), endTransform.getScaleY(), keyframeProgress));
        }
        return (ScaleXY) this.valueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), startTransform, endTransform, keyframeProgress, getLinearCurrentKeyframeProgress(), getProgress());
    }
}
