package com.airbnb.lottie.model.content;

import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.StrokeContent;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.List;

public class ShapeStroke implements ContentModel {
    private final LineCapType capType;
    private final AnimatableColorValue color;
    private final LineJoinType joinType;
    private final List<AnimatableFloatValue> lineDashPattern;
    private final String name;
    @Nullable
    private final AnimatableFloatValue offset;
    private final AnimatableIntegerValue opacity;
    private final AnimatableFloatValue width;

    public enum LineCapType {
        Butt,
        Round,
        Unknown;

        public Cap toPaintCap() {
            switch (this) {
                case Butt:
                    return Cap.BUTT;
                case Round:
                    return Cap.ROUND;
                default:
                    return Cap.SQUARE;
            }
        }
    }

    public enum LineJoinType {
        Miter,
        Round,
        Bevel;

        public Join toPaintJoin() {
            switch (this) {
                case Bevel:
                    return Join.BEVEL;
                case Miter:
                    return Join.MITER;
                case Round:
                    return Join.ROUND;
                default:
                    return null;
            }
        }
    }

    public ShapeStroke(String name2, @Nullable AnimatableFloatValue offset2, List<AnimatableFloatValue> lineDashPattern2, AnimatableColorValue color2, AnimatableIntegerValue opacity2, AnimatableFloatValue width2, LineCapType capType2, LineJoinType joinType2) {
        this.name = name2;
        this.offset = offset2;
        this.lineDashPattern = lineDashPattern2;
        this.color = color2;
        this.opacity = opacity2;
        this.width = width2;
        this.capType = capType2;
        this.joinType = joinType2;
    }

    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new StrokeContent(drawable, layer, this);
    }

    public String getName() {
        return this.name;
    }

    public AnimatableColorValue getColor() {
        return this.color;
    }

    public AnimatableIntegerValue getOpacity() {
        return this.opacity;
    }

    public AnimatableFloatValue getWidth() {
        return this.width;
    }

    public List<AnimatableFloatValue> getLineDashPattern() {
        return this.lineDashPattern;
    }

    public AnimatableFloatValue getDashOffset() {
        return this.offset;
    }

    public LineCapType getCapType() {
        return this.capType;
    }

    public LineJoinType getJoinType() {
        return this.joinType;
    }
}
