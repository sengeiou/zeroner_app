package com.airbnb.lottie.model.content;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.ShapeContent;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.layer.BaseLayer;

public class ShapePath implements ContentModel {
    private final int index;
    private final String name;
    private final AnimatableShapeValue shapePath;

    public ShapePath(String name2, int index2, AnimatableShapeValue shapePath2) {
        this.name = name2;
        this.index = index2;
        this.shapePath = shapePath2;
    }

    public String getName() {
        return this.name;
    }

    public AnimatableShapeValue getShapePath() {
        return this.shapePath;
    }

    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new ShapeContent(drawable, layer, this);
    }

    public String toString() {
        return "ShapePath{name=" + this.name + ", index=" + this.index + '}';
    }
}
