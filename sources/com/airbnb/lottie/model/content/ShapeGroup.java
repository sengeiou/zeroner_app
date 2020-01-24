package com.airbnb.lottie.model.content;

import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.model.layer.BaseLayer;
import java.util.Arrays;
import java.util.List;

public class ShapeGroup implements ContentModel {
    private final List<ContentModel> items;
    private final String name;

    public ShapeGroup(String name2, List<ContentModel> items2) {
        this.name = name2;
        this.items = items2;
    }

    public String getName() {
        return this.name;
    }

    public List<ContentModel> getItems() {
        return this.items;
    }

    public Content toContent(LottieDrawable drawable, BaseLayer layer) {
        return new ContentGroup(drawable, layer, this);
    }

    public String toString() {
        return "ShapeGroup{name='" + this.name + "' Shapes: " + Arrays.toString(this.items.toArray()) + '}';
    }
}
