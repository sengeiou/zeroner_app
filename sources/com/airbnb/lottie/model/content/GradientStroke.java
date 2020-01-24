package com.airbnb.lottie.model.content;

import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.GradientStrokeContent;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.ShapeStroke.LineCapType;
import com.airbnb.lottie.model.content.ShapeStroke.LineJoinType;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.List;

public class GradientStroke implements ContentModel {
    private final LineCapType capType;
    @Nullable
    private final AnimatableFloatValue dashOffset;
    private final AnimatablePointValue endPoint;
    private final AnimatableGradientColorValue gradientColor;
    private final GradientType gradientType;
    private final LineJoinType joinType;
    private final List<AnimatableFloatValue> lineDashPattern;
    private final String name;
    private final AnimatableIntegerValue opacity;
    private final AnimatablePointValue startPoint;
    private final AnimatableFloatValue width;

    public GradientStroke(String name2, GradientType gradientType2, AnimatableGradientColorValue gradientColor2, AnimatableIntegerValue opacity2, AnimatablePointValue startPoint2, AnimatablePointValue endPoint2, AnimatableFloatValue width2, LineCapType capType2, LineJoinType joinType2, List<AnimatableFloatValue> lineDashPattern2, @Nullable AnimatableFloatValue dashOffset2) {
        this.name = name2;
        this.gradientType = gradientType2;
        this.gradientColor = gradientColor2;
        this.opacity = opacity2;
        this.startPoint = startPoint2;
        this.endPoint = endPoint2;
        this.width = width2;
        this.capType = capType2;
        this.joinType = joinType2;
        this.lineDashPattern = lineDashPattern2;
        this.dashOffset = dashOffset2;
    }

    public String getName() {
        return this.name;
    }

    public GradientType getGradientType() {
        return this.gradientType;
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

    public AnimatableFloatValue getWidth() {
        return this.width;
    }

    public LineCapType getCapType() {
        return this.capType;
    }

    public LineJoinType getJoinType() {
        return this.joinType;
    }

    public List<AnimatableFloatValue> getLineDashPattern() {
        return this.lineDashPattern;
    }

    @Nullable
    public AnimatableFloatValue getDashOffset() {
        return this.dashOffset;
    }

    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new GradientStrokeContent(drawable, layer, this);
    }
}
