package com.airbnb.lottie.model.content;

import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.RepeaterContent;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.layer.BaseLayer;

public class Repeater implements ContentModel {
    private final AnimatableFloatValue copies;
    private final String name;
    private final AnimatableFloatValue offset;
    private final AnimatableTransform transform;

    public Repeater(String name2, AnimatableFloatValue copies2, AnimatableFloatValue offset2, AnimatableTransform transform2) {
        this.name = name2;
        this.copies = copies2;
        this.offset = offset2;
        this.transform = transform2;
    }

    public String getName() {
        return this.name;
    }

    public AnimatableFloatValue getCopies() {
        return this.copies;
    }

    public AnimatableFloatValue getOffset() {
        return this.offset;
    }

    public AnimatableTransform getTransform() {
        return this.transform;
    }

    @Nullable
    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new RepeaterContent(drawable, layer, this);
    }
}
