package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public class ContentGroup implements DrawingContent, PathContent, AnimationListener, KeyPathElement {
    private final List<Content> contents;
    private final LottieDrawable lottieDrawable;
    private final Matrix matrix;
    private final String name;
    private final Path path;
    @Nullable
    private List<PathContent> pathContents;
    private final RectF rect;
    @Nullable
    private TransformKeyframeAnimation transformAnimation;

    private static List<Content> contentsFromModels(LottieDrawable drawable, BaseLayer layer, List<ContentModel> contentModels) {
        List<Content> contents2 = new ArrayList<>(contentModels.size());
        for (int i = 0; i < contentModels.size(); i++) {
            Content content = ((ContentModel) contentModels.get(i)).toContent(drawable, layer);
            if (content != null) {
                contents2.add(content);
            }
        }
        return contents2;
    }

    @Nullable
    static AnimatableTransform findTransform(List<ContentModel> contentModels) {
        for (int i = 0; i < contentModels.size(); i++) {
            ContentModel contentModel = (ContentModel) contentModels.get(i);
            if (contentModel instanceof AnimatableTransform) {
                return (AnimatableTransform) contentModel;
            }
        }
        return null;
    }

    public ContentGroup(LottieDrawable lottieDrawable2, BaseLayer layer, ShapeGroup shapeGroup) {
        this(lottieDrawable2, layer, shapeGroup.getName(), contentsFromModels(lottieDrawable2, layer, shapeGroup.getItems()), findTransform(shapeGroup.getItems()));
    }

    ContentGroup(LottieDrawable lottieDrawable2, BaseLayer layer, String name2, List<Content> contents2, @Nullable AnimatableTransform transform) {
        this.matrix = new Matrix();
        this.path = new Path();
        this.rect = new RectF();
        this.name = name2;
        this.lottieDrawable = lottieDrawable2;
        this.contents = contents2;
        if (transform != null) {
            this.transformAnimation = transform.createAnimation();
            this.transformAnimation.addAnimationsToLayer(layer);
            this.transformAnimation.addListener(this);
        }
        List<GreedyContent> greedyContents = new ArrayList<>();
        for (int i = contents2.size() - 1; i >= 0; i--) {
            Content content = (Content) contents2.get(i);
            if (content instanceof GreedyContent) {
                greedyContents.add((GreedyContent) content);
            }
        }
        for (int i2 = greedyContents.size() - 1; i2 >= 0; i2--) {
            ((GreedyContent) greedyContents.get(i2)).absorbContent(contents2.listIterator(contents2.size()));
        }
    }

    public void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    public String getName() {
        return this.name;
    }

    public void setContents(List<Content> contentsBefore, List<Content> list) {
        List<Content> myContentsBefore = new ArrayList<>(contentsBefore.size() + this.contents.size());
        myContentsBefore.addAll(contentsBefore);
        for (int i = this.contents.size() - 1; i >= 0; i--) {
            Content content = (Content) this.contents.get(i);
            content.setContents(myContentsBefore, this.contents.subList(0, i));
            myContentsBefore.add(content);
        }
    }

    /* access modifiers changed from: 0000 */
    public List<PathContent> getPathList() {
        if (this.pathContents == null) {
            this.pathContents = new ArrayList();
            for (int i = 0; i < this.contents.size(); i++) {
                Content content = (Content) this.contents.get(i);
                if (content instanceof PathContent) {
                    this.pathContents.add((PathContent) content);
                }
            }
        }
        return this.pathContents;
    }

    /* access modifiers changed from: 0000 */
    public Matrix getTransformationMatrix() {
        if (this.transformAnimation != null) {
            return this.transformAnimation.getMatrix();
        }
        this.matrix.reset();
        return this.matrix;
    }

    public Path getPath() {
        this.matrix.reset();
        if (this.transformAnimation != null) {
            this.matrix.set(this.transformAnimation.getMatrix());
        }
        this.path.reset();
        for (int i = this.contents.size() - 1; i >= 0; i--) {
            Content content = (Content) this.contents.get(i);
            if (content instanceof PathContent) {
                this.path.addPath(((PathContent) content).getPath(), this.matrix);
            }
        }
        return this.path;
    }

    public void draw(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        int alpha;
        this.matrix.set(parentMatrix);
        if (this.transformAnimation != null) {
            this.matrix.preConcat(this.transformAnimation.getMatrix());
            alpha = (int) ((((((float) ((Integer) this.transformAnimation.getOpacity().getValue()).intValue()) / 100.0f) * ((float) parentAlpha)) / 255.0f) * 255.0f);
        } else {
            alpha = parentAlpha;
        }
        for (int i = this.contents.size() - 1; i >= 0; i--) {
            Object content = this.contents.get(i);
            if (content instanceof DrawingContent) {
                ((DrawingContent) content).draw(canvas, this.matrix, alpha);
            }
        }
    }

    public void getBounds(RectF outBounds, Matrix parentMatrix) {
        this.matrix.set(parentMatrix);
        if (this.transformAnimation != null) {
            this.matrix.preConcat(this.transformAnimation.getMatrix());
        }
        this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
        for (int i = this.contents.size() - 1; i >= 0; i--) {
            Content content = (Content) this.contents.get(i);
            if (content instanceof DrawingContent) {
                ((DrawingContent) content).getBounds(this.rect, this.matrix);
                if (outBounds.isEmpty()) {
                    outBounds.set(this.rect);
                } else {
                    outBounds.set(Math.min(outBounds.left, this.rect.left), Math.min(outBounds.top, this.rect.top), Math.max(outBounds.right, this.rect.right), Math.max(outBounds.bottom, this.rect.bottom));
                }
            }
        }
    }

    public void resolveKeyPath(KeyPath keyPath, int depth, List<KeyPath> accumulator, KeyPath currentPartialKeyPath) {
        if (keyPath.matches(getName(), depth)) {
            if (!"__container".equals(getName())) {
                currentPartialKeyPath = currentPartialKeyPath.addKey(getName());
                if (keyPath.fullyResolvesTo(getName(), depth)) {
                    accumulator.add(currentPartialKeyPath.resolve(this));
                }
            }
            if (keyPath.propagateToChildren(getName(), depth)) {
                int newDepth = depth + keyPath.incrementDepthBy(getName(), depth);
                for (int i = 0; i < this.contents.size(); i++) {
                    Content content = (Content) this.contents.get(i);
                    if (content instanceof KeyPathElement) {
                        ((KeyPathElement) content).resolveKeyPath(keyPath, newDepth, accumulator, currentPartialKeyPath);
                    }
                }
            }
        }
    }

    public <T> void addValueCallback(T property, @Nullable LottieValueCallback<T> callback) {
        if (this.transformAnimation != null) {
            this.transformAnimation.applyValueCallback(property, callback);
        }
    }
}
