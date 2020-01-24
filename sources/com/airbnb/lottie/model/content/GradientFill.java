package com.airbnb.lottie.model.content;

import android.graphics.Path.FillType;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.GradientFillContent;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.layer.BaseLayer;

public class GradientFill implements ContentModel {
    private final AnimatablePointValue endPoint;
    private final FillType fillType;
    private final AnimatableGradientColorValue gradientColor;
    private final GradientType gradientType;
    @Nullable
    private final AnimatableFloatValue highlightAngle;
    @Nullable
    private final AnimatableFloatValue highlightLength;
    private final String name;
    private final AnimatableIntegerValue opacity;
    private final AnimatablePointValue startPoint;

    public GradientFill(String name2, GradientType gradientType2, FillType fillType2, AnimatableGradientColorValue gradientColor2, AnimatableIntegerValue opacity2, AnimatablePointValue startPoint2, AnimatablePointValue endPoint2, AnimatableFloatValue highlightLength2, AnimatableFloatValue highlightAngle2) {
        this.gradientType = gradientType2;
        this.fillType = fillType2;
        this.gradientColor = gradientColor2;
        this.opacity = opacity2;
        this.startPoint = startPoint2;
        this.endPoint = endPoint2;
        this.name = name2;
        this.highlightLength = highlightLength2;
        this.highlightAngle = highlightAngle2;
    }

    public String getName() {
        return this.name;
    }

    public GradientType getGradientType() {
        return this.gradientType;
    }

    public FillType getFillType() {
        return this.fillType;
    }

    public AnimatableGradientColorValue getGradientColor() {
        return this.gradientColor;
    }

    public AnimatableIntegerValue getOpacity() {
        return this.opacity;
    }

    public AnimatablePointValue getStartPoint() {
        return this.startPoint;
    }

    public AnimatablePointValue getEndPoint() {
        return this.endPoint;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public AnimatableFloatValue getHighlightLength() {
        return this.highlightLength;
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public AnimatableFloatValue getHighlightAngle() {
        return this.highlightAngle;
    }

    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new GradientFillContent(drawable, layer, this);
    }
}
