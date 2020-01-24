package com.airbnb.lottie.model.content;

import android.graphics.Path.FillType;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.FillContent;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.layer.BaseLayer;

public class ShapeFill implements ContentModel {
    @Nullable
    private final AnimatableColorValue color;
    private final boolean fillEnabled;
    private final FillType fillType;
    private final String name;
    @Nullable
    private final AnimatableIntegerValue opacity;

    public ShapeFill(String name2, boolean fillEnabled2, FillType fillType2, @Nullable AnimatableColorValue color2, @Nullable AnimatableIntegerValue opacity2) {
        this.name = name2;
        this.fillEnabled = fillEnabled2;
        this.fillType = fillType2;
        this.color = color2;
        this.opacity = opacity2;
    }

    public String getName() {
        return this.name;
    }

    @Nullable
    public AnimatableColorValue getColor() {
        return this.color;
    }

    @Nullable
    public AnimatableIntegerValue getOpacity() {
        return this.opacity;
    }

    public FillType getFillType() {
        return this.fillType;
    }

    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new FillContent(drawable, layer, this);
    }

    public String toString() {
        return "ShapeFill{color=, fillEnabled=" + this.fillEnabled + '}';
    }
}
