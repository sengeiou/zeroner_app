package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.ShapeFill;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public class FillContent implements DrawingContent, AnimationListener, KeyPathElementContent {
    private final BaseKeyframeAnimation<Integer, Integer> colorAnimation;
    @Nullable
    private BaseKeyframeAnimation<ColorFilter, ColorFilter> colorFilterAnimation;
    private final LottieDrawable lottieDrawable;
    private final String name;
    private final BaseKeyframeAnimation<Integer, Integer> opacityAnimation;
    private final Paint paint = new Paint(1);
    private final Path path = new Path();
    private final List<PathContent> paths = new ArrayList();

    public FillContent(LottieDrawable lottieDrawable2, BaseLayer layer, ShapeFill fill) {
        this.name = fill.getName();
        this.lottieDrawable = lottieDrawable2;
        if (fill.getColor() == null || fill.getOpacity() == null) {
            this.colorAnimation = null;
            this.opacityAnimation = null;
            return;
        }
        this.path.setFillType(fill.getFillType());
        this.colorAnimation = fill.getColor().createAnimation();
        this.colorAnimation.addUpdateListener(this);
        layer.addAnimation(this.colorAnimation);
        this.opacityAnimation = fill.getOpacity().createAnimation();
        this.opacityAnimation.addUpdateListener(this);
        layer.addAnimation(this.opacityAnimation);
    }

    public void onValueChanged() {
        this.lottieDrawable.invalidateSelf();
    }

    public void setContents(List<Content> list, List<Content> contentsAfter) {
        for (int i = 0; i < contentsAfter.size(); i++) {
            Content content = (Content) contentsAfter.get(i);
            if (content instanceof PathContent) {
                this.paths.add((PathContent) content);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public void draw(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        L.beginSection("FillContent#draw");
        this.paint.setColor(((Integer) this.colorAnimation.getValue()).intValue());
        this.paint.setAlpha(MiscUtils.clamp((int) (((((float) ((Integer) this.opacityAnimation.getValue()).intValue()) * (((float) parentAlpha) / 255.0f)) / 100.0f) * 255.0f), 0, 255));
        if (this.colorFilterAnimation != null) {
            this.paint.setColorFilter((ColorFilter) this.colorFilterAnimation.getValue());
        }
        this.path.reset();
        for (int i = 0; i < this.paths.size(); i++) {
            this.path.addPath(((PathContent) this.paths.get(i)).getPath(), parentMatrix);
        }
        canvas.drawPath(this.path, this.paint);
        L.endSection("FillContent#draw");
    }

    public void getBounds(RectF outBounds, Matrix parentMatrix) {
        this.path.reset();
        for (int i = 0; i < this.paths.size(); i++) {
            this.path.addPath(((PathContent) this.paths.get(i)).getPath(), parentMatrix);
        }
        this.path.computeBounds(outBounds, false);
        outBounds.set(outBounds.left - 1.0f, outBounds.top - 1.0f, outBounds.right + 1.0f, outBounds.bottom + 1.0f);
    }

    public void resolveKeyPath(KeyPath keyPath, int depth, List<KeyPath> accumulator, KeyPath currentPartialKeyPath) {
        MiscUtils.resolveKeyPath(keyPath, depth, accumulator, currentPartialKeyPath, this);
    }

    public <T> void addValueCallback(T property, @Nullable LottieValueCallback<T> callback) {
        if (property == LottieProperty.COLOR) {
            this.colorAnimation.setValueCallback(callback);
        } else if (property == LottieProperty.OPACITY) {
            this.opacityAnimation.setValueCallback(callback);
        } else if (property != LottieProperty.COLOR_FILTER) {
        } else {
            if (callback == null) {
                this.colorFilterAnimation = null;
            } else {
                this.colorFilterAnimation = new ValueCallbackKeyframeAnimation(callback);
            }
        }
    }
}
