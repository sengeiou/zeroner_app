package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.content.ShapeGroup;
import java.util.Collections;
import java.util.List;

public class ShapeLayer extends BaseLayer {
    private final ContentGroup contentGroup;

    ShapeLayer(LottieDrawable lottieDrawable, Layer layerModel) {
        super(lottieDrawable, layerModel);
        this.contentGroup = new ContentGroup(lottieDrawable, this, new ShapeGroup("__container", layerModel.getShapes()));
        this.contentGroup.setContents(Collections.emptyList(), Collections.emptyList());
    }

    /* access modifiers changed from: 0000 */
    public void drawLayer(@NonNull Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        this.contentGroup.draw(canvas, parentMatrix, parentAlpha);
    }

    public void getBounds(RectF outBounds, Matrix parentMatrix) {
        super.getBounds(outBounds, parentMatrix);
        this.contentGroup.getBounds(outBounds, this.boundsMatrix);
    }

    /* access modifiers changed from: protected */
    public void resolveChildKeyPath(KeyPath keyPath, int depth, List<KeyPath> accumulator, KeyPath currentPartialKeyPath) {
        this.contentGroup.resolveKeyPath(keyPath, depth, accumulator, currentPartialKeyPath);
    }
}
