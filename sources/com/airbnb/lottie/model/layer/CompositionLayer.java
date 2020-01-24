package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.List;

public class CompositionLayer extends BaseLayer {
    @Nullable
    private Boolean hasMasks;
    @Nullable
    private Boolean hasMatte;
    private final List<BaseLayer> layers = new ArrayList();
    private final RectF newClipRect = new RectF();
    private final RectF rect = new RectF();
    @Nullable
    private BaseKeyframeAnimation<Float, Float> timeRemapping;

    public CompositionLayer(LottieDrawable lottieDrawable, Layer layerModel, List<Layer> layerModels, LottieComposition composition) {
        Layer lm;
        super(lottieDrawable, layerModel);
        AnimatableFloatValue timeRemapping2 = layerModel.getTimeRemapping();
        if (timeRemapping2 != null) {
            this.timeRemapping = timeRemapping2.createAnimation();
            addAnimation(this.timeRemapping);
            this.timeRemapping.addUpdateListener(this);
        } else {
            this.timeRemapping = null;
        }
        LongSparseArray<BaseLayer> layerMap = new LongSparseArray<>(composition.getLayers().size());
        BaseLayer mattedLayer = null;
        for (int i = layerModels.size() - 1; i >= 0; i--) {
            BaseLayer layer = BaseLayer.forModel((Layer) layerModels.get(i), lottieDrawable, composition);
            if (layer != null) {
                layerMap.put(layer.getLayerModel().getId(), layer);
                if (mattedLayer == null) {
                    this.layers.add(0, layer);
                    switch (lm.getMatteType()) {
                        case Add:
                        case Invert:
                            mattedLayer = layer;
                            break;
                    }
                } else {
                    mattedLayer.setMatteLayer(layer);
                    mattedLayer = null;
                }
            }
        }
        for (int i2 = 0; i2 < layerMap.size(); i2++) {
            BaseLayer layerView = (BaseLayer) layerMap.get(layerMap.keyAt(i2));
            if (layerView != null) {
                BaseLayer parentLayer = (BaseLayer) layerMap.get(layerView.getLayerModel().getParentId());
                if (parentLayer != null) {
                    layerView.setParentLayer(parentLayer);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void drawLayer(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        L.beginSection("CompositionLayer#draw");
        canvas.save();
        this.newClipRect.set(0.0f, 0.0f, (float) this.layerModel.getPreCompWidth(), (float) this.layerModel.getPreCompHeight());
        parentMatrix.mapRect(this.newClipRect);
        for (int i = this.layers.size() - 1; i >= 0; i--) {
            boolean nonEmptyClip = true;
            if (!this.newClipRect.isEmpty()) {
                nonEmptyClip = canvas.clipRect(this.newClipRect);
            }
            if (nonEmptyClip) {
                ((BaseLayer) this.layers.get(i)).draw(canvas, parentMatrix, parentAlpha);
            }
        }
        canvas.restore();
        L.endSection("CompositionLayer#draw");
    }

    public void getBounds(RectF outBounds, Matrix parentMatrix) {
        super.getBounds(outBounds, parentMatrix);
        this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
        for (int i = this.layers.size() - 1; i >= 0; i--) {
            ((BaseLayer) this.layers.get(i)).getBounds(this.rect, this.boundsMatrix);
            if (outBounds.isEmpty()) {
                outBounds.set(this.rect);
            } else {
                outBounds.set(Math.min(outBounds.left, this.rect.left), Math.min(outBounds.top, this.rect.top), Math.max(outBounds.right, this.rect.right), Math.max(outBounds.bottom, this.rect.bottom));
            }
        }
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        super.setProgress(progress);
        if (this.timeRemapping != null) {
            progress = ((float) ((long) (((Float) this.timeRemapping.getValue()).floatValue() * 1000.0f))) / this.lottieDrawable.getComposition().getDuration();
        }
        if (this.layerModel.getTimeStretch() != 0.0f) {
            progress /= this.layerModel.getTimeStretch();
        }
        float progress2 = progress - this.layerModel.getStartProgress();
        for (int i = this.layers.size() - 1; i >= 0; i--) {
            ((BaseLayer) this.layers.get(i)).setProgress(progress2);
        }
    }

    public boolean hasMasks() {
        if (this.hasMasks == null) {
            for (int i = this.layers.size() - 1; i >= 0; i--) {
                BaseLayer layer = (BaseLayer) this.layers.get(i);
                if (layer instanceof ShapeLayer) {
                    if (layer.hasMasksOnThisLayer()) {
                        this.hasMasks = Boolean.valueOf(true);
                        return true;
                    }
                } else if ((layer instanceof CompositionLayer) && ((CompositionLayer) layer).hasMasks()) {
                    this.hasMasks = Boolean.valueOf(true);
                    return true;
                }
            }
            this.hasMasks = Boolean.valueOf(false);
        }
        return this.hasMasks.booleanValue();
    }

    public boolean hasMatte() {
        if (this.hasMatte == null) {
            if (hasMatteOnThisLayer()) {
                this.hasMatte = Boolean.valueOf(true);
                return true;
            }
            for (int i = this.layers.size() - 1; i >= 0; i--) {
                if (((BaseLayer) this.layers.get(i)).hasMatteOnThisLayer()) {
                    this.hasMatte = Boolean.valueOf(true);
                    return true;
                }
            }
            this.hasMatte = Boolean.valueOf(false);
        }
        return this.hasMatte.booleanValue();
    }

    /* access modifiers changed from: protected */
    public void resolveChildKeyPath(KeyPath keyPath, int depth, List<KeyPath> accumulator, KeyPath currentPartialKeyPath) {
        for (int i = 0; i < this.layers.size(); i++) {
            ((BaseLayer) this.layers.get(i)).resolveKeyPath(keyPath, depth, accumulator, currentPartialKeyPath);
        }
    }

    public <T> void addValueCallback(T property, @Nullable LottieValueCallback<T> callback) {
        super.addValueCallback(property, callback);
        if (property != LottieProperty.TIME_REMAP) {
            return;
        }
        if (callback == null) {
            this.timeRemapping = null;
            return;
        }
        this.timeRemapping = new ValueCallbackKeyframeAnimation(callback);
        addAnimation(this.timeRemapping);
    }
}
