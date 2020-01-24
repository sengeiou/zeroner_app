package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

public class TextKeyframeAnimation extends KeyframeAnimation<DocumentData> {
    public TextKeyframeAnimation(List<Keyframe<DocumentData>> keyframes) {
        super(keyframes);
    }

    /* access modifiers changed from: 0000 */
    public DocumentData getValue(Keyframe<DocumentData> keyframe, float keyframeProgress) {
        return (DocumentData) keyframe.startValue;
    }
}
