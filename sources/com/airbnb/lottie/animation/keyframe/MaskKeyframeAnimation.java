package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.content.ShapeData;
import java.util.ArrayList;
import java.util.List;

public class MaskKeyframeAnimation {
    private final List<BaseKeyframeAnimation<ShapeData, Path>> maskAnimations;
    private final List<Mask> masks;
    private final List<BaseKeyframeAnimation<Integer, Integer>> opacityAnimations;

    public MaskKeyframeAnimation(List<Mask> masks2) {
        this.masks = masks2;
        this.maskAnimations = new ArrayList(masks2.size());
        this.opacityAnimations = new ArrayList(masks2.size());
        for (int i = 0; i < masks2.size(); i++) {
            this.maskAnimations.add(((Mask) masks2.get(i)).getMaskPath().createAnimation());
            this.opacityAnimations.add(((Mask) masks2.get(i)).getOpacity().createAnimation());
        }
    }

    public List<Mask> getMasks() {
        return this.masks;
    }

    public List<BaseKeyframeAnimation<ShapeData, Path>> getMaskAnimations() {
        return this.maskAnimations;
    }

    public List<BaseKeyframeAnimation<Integer, Integer>> getOpacityAnimations() {
        return this.opacityAnimations;
    }
}
