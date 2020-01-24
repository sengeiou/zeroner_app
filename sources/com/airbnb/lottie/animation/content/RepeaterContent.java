package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.Repeater;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class RepeaterContent implements DrawingContent, PathContent, GreedyContent, AnimationListener, KeyPathElementContent {
    private ContentGroup contentGroup;
    private final BaseKeyframeAnimation<Float, Float> copies;
    private final BaseLayer layer;
    private final LottieDrawable lottieDrawable;
    private final Matrix matrix = new Matrix();
    private final String name;
    private final BaseKeyframeAnimation<Float, Float> offset;
    private final Path path = new Path();
    private final TransformKeyframeAnimation transform;

    public RepeaterContent(LottieDrawable lottieDrawable2, BaseLayer layer2, Repeater repeater) {
        this.lottieDrawable = lottieDrawable2;
        this.layer = layer2;
        this.name = repeater.getName();
        this.copies = repeater.getCopies().createAnimation();
        layer2.addAnimation(this.copies);
        this.copies.addUpdateListener(this);
        this.offset = repeater.getOffset().createAnimation();
        layer2.addAnimation(this.offset);
        this.offset.addUpdateListener(this);
        this.transform = repeater.getTransform().createAnimation();
        this.transform.addAnimationsToLayer(layer2);
        this.transform.addListener(this);
    }

    public void absorbContent(ListIterator<Content> contentsIter) {
        if (this.contentGroup == null) {
            while (contentsIter.hasPrevious()) {
                if (contentsIter.previous() == this) {
                    break;
                }
            }
            List<Content> contents = new ArrayList<>();
            while (contentsIter.hasPrevious()) {
                contents.add(contentsIter.previous());
                contentsIter.remove();
            }
            Collections.reverse(contents);
            this.contentGroup = new ContentGroup(this.lottieDrawable, this.layer, "Repeater", contents, null);
        }
    }

    public String getName() {
        return this.name;
    }

    public void setContents(List<Content> contentsBefore, List<Content> contentsAfter) {
        this.contentGroup.setContents(contentsBefore, contentsAfter);
    }

    public Path getPath() {
        Path contentPath = this.contentGroup.getPath();
        this.path.reset();
        float copies2 = ((Float) this.copies.getValue()).floatValue();
        float offset2 = ((Float) this.offset.getValue()).floatValue();
        for (int i = ((int) copies2) - 1; i >= 0; i--) {
            this.matrix.set(this.transform.getMatrixForRepeater(((float) i) + offset2));
            this.path.addPath(contentPath, this.matrix);
        }
        return this.path;
    }

    public void draw(Canvas canvas, Matrix parentMatrix, int alpha) {
        float copies2 = ((Float) this.copies.getValue()).floatValue();
        float offset2 = ((Float) this.offset.getValue()).floatValue();
        float startOpacity = ((Float) this.transform.getStartOpacity().getValue()).floatValue() / 100.0f;
        float endOpacity = ((Float) this.transform.getEndOpacity().getValue()).floatValue() / 100.0f;
        for (int i = ((int) copies2) - 1; i >= 0; i--) {
            this.matrix.set(parentMatrix);
            this.matrix.preConcat(this.transform.getMatrixForRepeater(((float) i) + offset2));
            this.contentGroup.draw(canvas, this.matrix, (int) (((float) alpha) * MiscUtils.lerp(startOpacity, endOpacity, ((float) i) / copies2)));
        }
    }

    public void getBounds(RectF outBounds, Matrix parentMatrix) {
        this.contentGroup.getBounds(outBounds, parentMatrix);
    }

    public void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    public void resolveKeyPath(KeyPath keyPath, int depth, List<KeyPath> accumulator, KeyPath currentPartialKeyPath) {
        MiscUtils.resolveKeyPath(keyPath, depth, accumulator, currentPartialKeyPath, this);
    }

    public <T> void addValueCallback(T property, @Nullable LottieValueCallback<T> callback) {
        if (!this.transform.applyValueCallback(property, callback)) {
            if (property == LottieProperty.REPEATER_COPIES) {
                this.copies.setValueCallback(callback);
            } else if (property == LottieProperty.REPEATER_OFFSET) {
                this.offset.setValueCallback(callback);
            }
        }
    }
}
