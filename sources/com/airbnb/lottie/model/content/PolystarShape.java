package com.airbnb.lottie.model.content;

import android.graphics.PointF;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.PolystarContent;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.layer.BaseLayer;

public class PolystarShape implements ContentModel {
    private final AnimatableFloatValue innerRadius;
    private final AnimatableFloatValue innerRoundedness;
    private final String name;
    private final AnimatableFloatValue outerRadius;
    private final AnimatableFloatValue outerRoundedness;
    private final AnimatableFloatValue points;
    private final AnimatableValue<PointF, PointF> position;
    private final AnimatableFloatValue rotation;
    private final Type type;

    public enum Type {
        Star(1),
        Polygon(2);
        
        private final int value;

        private Type(int value2) {
            this.value = value2;
        }

        public static Type forValue(int value2) {
            Type[] values;
            for (Type type : values()) {
                if (type.value == value2) {
                    return type;
                }
            }
            return null;
        }
    }

    public PolystarShape(String name2, Type type2, AnimatableFloatValue points2, AnimatableValue<PointF, PointF> position2, AnimatableFloatValue rotation2, AnimatableFloatValue innerRadius2, AnimatableFloatValue outerRadius2, AnimatableFloatValue innerRoundedness2, AnimatableFloatValue outerRoundedness2) {
        this.name = name2;
        this.type = type2;
        this.points = points2;
        this.position = position2;
        this.rotation = rotation2;
        this.innerRadius = innerRadius2;
        this.outerRadius = outerRadius2;
        this.innerRoundedness = innerRoundedness2;
        this.outerRoundedness = outerRoundedness2;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public AnimatableFloatValue getPoints() {
        return this.points;
    }

    public AnimatableValue<PointF, PointF> getPosition() {
        return this.position;
    }

    public AnimatableFloatValue getRotation() {
        return this.rotation;
    }

    public AnimatableFloatValue getInnerRadius() {
        return this.innerRadius;
    }

    public AnimatableFloatValue getOuterRadius() {
        return this.outerRadius;
    }

    public AnimatableFloatValue getInnerRoundedness() {
        return this.innerRoundedness;
    }

    public AnimatableFloatValue getOuterRoundedness() {
        return this.outerRoundedness;
    }

    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new PolystarContent(drawable, layer, this);
    }
}
