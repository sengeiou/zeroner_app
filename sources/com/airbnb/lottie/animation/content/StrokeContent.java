package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.content.ShapeStroke;
import com.airbnb.lottie.model.layer.BaseLayer;
import com.airbnb.lottie.value.LottieValueCallback;

public class StrokeContent extends BaseStrokeContent {
    private final BaseKeyframeAnimation<Integer, Integer> colorAnimation;
    @Nullable
    private BaseKeyframeAnimation<ColorFilter, ColorFilter> colorFilterAnimation;
    private final String name;

    public StrokeContent(LottieDrawable lottieDrawable, BaseLayer layer, ShapeStroke stroke) {
        super(lottieDrawable, layer, stroke.getCapType().toPaintCap(), stroke.getJoinType().toPaintJoin(), stroke.getOpacity(), stroke.getWidth(), stroke.getLineDashPattern(), stroke.getDashOffset());
        this.name = stroke.getName();
        this.colorAnimation = stroke.getColor().createAnimation();
        this.colorAnimation.addUpdateListener(this);
        layer.addAnimation(this.colorAnimation);
    }

    public void draw(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        this.paint.setColor(((Integer) this.colorAnimation.getValue()).intValue());
        super.draw(canvas, parentMatrix, parentAlpha);
    }

    public String getName() {
        return this.name;
    }

    public <T> void addValueCallback(T property, @Nullable LottieValueCallback<T> callback) {
        super.addValueCallback(property, callback);
        if (property == LottieProperty.STROKE_COLOR) {
            this.colorAnimation.setValueCallback(callback);
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
