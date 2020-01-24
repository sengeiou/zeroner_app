package com.airbnb.lottie.model.content;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.TrimPathContent;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.layer.BaseLayer;

public class ShapeTrimPath implements ContentModel {
    private final AnimatableFloatValue end;
    private final String name;
    private final AnimatableFloatValue offset;
    private final AnimatableFloatValue start;
    private final Type type;

    public enum Type {
        Simultaneously,
        Individually;

        public static Type forId(int id) {
            switch (id) {
                case 1:
                    return Simultaneously;
                case 2:
                    return Individually;
                default:
                    throw new IllegalArgumentException("Unknown trim path type " + id);
            }
        }
    }

    public ShapeTrimPath(String name2, Type type2, AnimatableFloatValue start2, AnimatableFloatValue end2, AnimatableFloatValue offset2) {
        this.name = name2;
        this.type = type2;
        this.start = start2;
        this.end = end2;
        this.offset = offset2;
    }

    public String getName() {
        return this.name;
    }

    public Type getType() {
        return this.type;
    }

    public AnimatableFloatValue getEnd() {
        return this.end;
    }

    public AnimatableFloatValue getStart() {
        return this.start;
    }

    public AnimatableFloatValue getOffset() {
        return this.offset;
    }

    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new TrimPathContent(layer, this);
    }

    public String toString() {
        return "Trim Path: {start: " + this.start + ", end: " + this.end + ", offset: " + this.offset + "}";
    }
}
