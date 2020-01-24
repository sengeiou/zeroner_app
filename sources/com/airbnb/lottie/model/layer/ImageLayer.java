package com.airbnb.lottie.model.layer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;

public class ImageLayer extends BaseLayer {
    @Nullable
    private BaseKeyframeAnimation<ColorFilter, ColorFilter> colorFilterAnimation;
    private final Rect dst = new Rect();
    private final Paint paint = new Paint(3);
    private final Rect src = new Rect();

    ImageLayer(LottieDrawable lottieDrawable, Layer layerModel) {
        super(lottieDrawable, layerModel);
    }

    public void drawLayer(@NonNull Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            float density = Utils.dpScale();
            this.paint.setAlpha(parentAlpha);
            if (this.colorFilterAnimation != null) {
                this.paint.setColorFilter((ColorFilter) this.colorFilterAnimation.getValue());
            }
            canvas.save();
            canvas.concat(parentMatrix);
            this.src.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
            this.dst.set(0, 0, (int) (((float) bitmap.getWidth()) * density), (int) (((float) bitmap.getHeight()) * density));
            canvas.drawBitmap(bitmap, this.src, this.dst, this.paint);
            canvas.restore();
        }
    }

    public void getBounds(RectF outBounds, Matrix parentMatrix) {
        super.getBounds(outBounds, parentMatrix);
        Bitmap bitmap = getBitmap();
        if (bitmap != null) {
            outBounds.set(outBounds.left, outBounds.top, Math.min(outBounds.right, (float) bitmap.getWidth()), Math.min(outBounds.bottom, (float) bitmap.getHeight()));
            this.boundsMatrix.mapRect(outBounds);
        }
    }

    @Nullable
    private Bitmap getBitmap() {
        return this.lottieDrawable.getImageAsset(this.layerModel.getRefId());
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
