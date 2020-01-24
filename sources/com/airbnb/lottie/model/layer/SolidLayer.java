package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.value.LottieValueCallback;

public class SolidLayer extends BaseLayer {
    @Nullable
    private BaseKeyframeAnimation<ColorFilter, ColorFilter> colorFilterAnimation;
    private final Layer layerModel;
    private final Paint paint = new Paint();
    private final Path path = new Path();
    private final float[] points = new float[8];
    private final RectF rect = new RectF();

    SolidLayer(LottieDrawable lottieDrawable, Layer layerModel2) {
        super(lottieDrawable, layerModel2);
        this.layerModel = layerModel2;
        this.paint.setAlpha(0);
        this.paint.setStyle(Style.FILL);
        this.paint.setColor(layerModel2.getSolidColor());
    }

    public void drawLayer(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        int backgroundAlpha = Color.alpha(this.layerModel.getSolidColor());
        if (backgroundAlpha != 0) {
            int alpha = (int) (((((float) ((Integer) this.transform.getOpacity().getValue()).intValue()) * (((float) backgroundAlpha) / 255.0f)) / 100.0f) * (((float) parentAlpha) / 255.0f) * 255.0f);
            this.paint.setAlpha(alpha);
            if (this.colorFilterAnimation != null) {
                this.paint.setColorFilter((ColorFilter) this.colorFilterAnimation.getValue());
            }
            if (alpha > 0) {
                this.points[0] = 0.0f;
                this.points[1] = 0.0f;
                this.points[2] = (float) this.layerModel.getSolidWidth();
                this.points[3] = 0.0f;
                this.points[4] = (float) this.layerModel.getSolidWidth();
                this.points[5] = (float) this.layerModel.getSolidHeight();
                this.points[6] = 0.0f;
                this.points[7] = (float) this.layerModel.getSolidHeight();
                parentMatrix.mapPoints(this.points);
                this.path.reset();
                this.path.moveTo(this.points[0], this.points[1]);
                this.path.lineTo(this.points[2], this.points[3]);
                this.path.lineTo(this.points[4], this.points[5]);
                this.path.lineTo(this.points[6], this.points[7]);
                this.path.lineTo(this.points[0], this.points[1]);
                this.path.close();
                canvas.drawPath(this.path, this.paint);
            }
        }
    }

    public void getBounds(RectF outBounds, Matrix parentMatrix) {
        super.getBounds(outBounds, parentMatrix);
        this.rect.set(0.0f, 0.0f, (float) this.layerModel.getSolidWidth(), (float) this.layerModel.getSolidHeight());
        this.boundsMatrix.mapRect(this.rect);
        outBounds.set(this.rect);
    }

    public <T> void addValueCallback(T property, @Nullable LottieValueCallback<T> callback) {
        super.addValueCallback(property, callback);
        if (property != LottieProperty.COLOR_FILTER) {
            return;
        }
        if (callback == null) {
            this.colorFilterAnimation = null;
        } else {
            this.colorFilterAnimation = new ValueCallbackKeyframeAnimation(callback);
        }
    }
}
