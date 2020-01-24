package com.airbnb.lottie.model.layer;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.CallSuper;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.Log;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.animation.content.Content;
import com.airbnb.lottie.animation.content.DrawingContent;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation.AnimationListener;
import com.airbnb.lottie.animation.keyframe.FloatKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.MaskKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TransformKeyframeAnimation;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.KeyPathElement;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.content.Mask.MaskMode;
import com.airbnb.lottie.model.layer.Layer.MatteType;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseLayer implements DrawingContent, AnimationListener, KeyPathElement {
    private static final int SAVE_FLAGS = 19;
    private final Paint addMaskPaint = new Paint(1);
    private final List<BaseKeyframeAnimation<?, ?>> animations = new ArrayList();
    final Matrix boundsMatrix = new Matrix();
    private final Paint clearPaint = new Paint();
    private final Paint contentPaint = new Paint(1);
    private final String drawTraceName;
    final Layer layerModel;
    final LottieDrawable lottieDrawable;
    @Nullable
    private MaskKeyframeAnimation mask;
    private final RectF maskBoundsRect = new RectF();
    private final Matrix matrix = new Matrix();
    private final RectF matteBoundsRect = new RectF();
    @Nullable
    private BaseLayer matteLayer;
    private final Paint mattePaint = new Paint(1);
    @Nullable
    private BaseLayer parentLayer;
    private List<BaseLayer> parentLayers;
    private final Path path = new Path();
    private final RectF rect = new RectF();
    private final Paint subtractMaskPaint = new Paint(1);
    private final RectF tempMaskBoundsRect = new RectF();
    final TransformKeyframeAnimation transform;
    private boolean visible = true;

    /* access modifiers changed from: 0000 */
    public abstract void drawLayer(Canvas canvas, Matrix matrix2, int i);

    @Nullable
    static BaseLayer forModel(Layer layerModel2, LottieDrawable drawable, LottieComposition composition) {
        switch (layerModel2.getLayerType()) {
            case Shape:
                return new ShapeLayer(drawable, layerModel2);
            case PreComp:
                return new CompositionLayer(drawable, layerModel2, composition.getPrecomps(layerModel2.getRefId()), composition);
            case Solid:
                return new SolidLayer(drawable, layerModel2);
            case Image:
                return new ImageLayer(drawable, layerModel2);
            case Null:
                return new NullLayer(drawable, layerModel2);
            case Text:
                return new TextLayer(drawable, layerModel2);
            default:
                L.warn("Unknown layer type " + layerModel2.getLayerType());
                return null;
        }
    }

    BaseLayer(LottieDrawable lottieDrawable2, Layer layerModel2) {
        this.lottieDrawable = lottieDrawable2;
        this.layerModel = layerModel2;
        this.drawTraceName = layerModel2.getName() + "#draw";
        this.clearPaint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        this.addMaskPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        this.subtractMaskPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        if (layerModel2.getMatteType() == MatteType.Invert) {
            this.mattePaint.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
        } else {
            this.mattePaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        }
        this.transform = layerModel2.getTransform().createAnimation();
        this.transform.addListener(this);
        if (layerModel2.getMasks() != null && !layerModel2.getMasks().isEmpty()) {
            this.mask = new MaskKeyframeAnimation(layerModel2.getMasks());
            for (BaseKeyframeAnimation<?, Path> animation : this.mask.getMaskAnimations()) {
                addAnimation(animation);
                animation.addUpdateListener(this);
            }
            for (BaseKeyframeAnimation<Integer, Integer> animation2 : this.mask.getOpacityAnimations()) {
                addAnimation(animation2);
                animation2.addUpdateListener(this);
            }
        }
        setupInOutAnimations();
    }

    public void onValueChanged() {
        invalidateSelf();
    }

    /* access modifiers changed from: 0000 */
    public Layer getLayerModel() {
        return this.layerModel;
    }

    /* access modifiers changed from: 0000 */
    public void setMatteLayer(@Nullable BaseLayer matteLayer2) {
        this.matteLayer = matteLayer2;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasMatteOnThisLayer() {
        return this.matteLayer != null;
    }

    /* access modifiers changed from: 0000 */
    public void setParentLayer(@Nullable BaseLayer parentLayer2) {
        this.parentLayer = parentLayer2;
    }

    private void setupInOutAnimations() {
        if (!this.layerModel.getInOutKeyframes().isEmpty()) {
            final FloatKeyframeAnimation inOutAnimation = new FloatKeyframeAnimation(this.layerModel.getInOutKeyframes());
            inOutAnimation.setIsDiscrete();
            inOutAnimation.addUpdateListener(new AnimationListener() {
                public void onValueChanged() {
                    BaseLayer.this.setVisible(((Float) inOutAnimation.getValue()).floatValue() == 1.0f);
                }
            });
            setVisible(((Float) inOutAnimation.getValue()).floatValue() == 1.0f);
            addAnimation(inOutAnimation);
            return;
        }
        setVisible(true);
    }

    private void invalidateSelf() {
        this.lottieDrawable.invalidateSelf();
    }

    public void addAnimation(BaseKeyframeAnimation<?, ?> newAnimation) {
        this.animations.add(newAnimation);
    }

    @CallSuper
    public void getBounds(RectF outBounds, Matrix parentMatrix) {
        this.boundsMatrix.set(parentMatrix);
        this.boundsMatrix.preConcat(this.transform.getMatrix());
    }

    @SuppressLint({"WrongConstant"})
    public void draw(Canvas canvas, Matrix parentMatrix, int parentAlpha) {
        L.beginSection(this.drawTraceName);
        if (!this.visible) {
            L.endSection(this.drawTraceName);
            return;
        }
        buildParentLayerListIfNeeded();
        L.beginSection("Layer#parentMatrix");
        this.matrix.reset();
        this.matrix.set(parentMatrix);
        for (int i = this.parentLayers.size() - 1; i >= 0; i--) {
            this.matrix.preConcat(((BaseLayer) this.parentLayers.get(i)).transform.getMatrix());
        }
        L.endSection("Layer#parentMatrix");
        int alpha = (int) (((((float) ((Integer) this.transform.getOpacity().getValue()).intValue()) * (((float) parentAlpha) / 255.0f)) / 100.0f) * 255.0f);
        if (hasMatteOnThisLayer() || hasMasksOnThisLayer()) {
            L.beginSection("Layer#computeBounds");
            this.rect.set(0.0f, 0.0f, 0.0f, 0.0f);
            getBounds(this.rect, this.matrix);
            intersectBoundsWithMatte(this.rect, this.matrix);
            this.matrix.preConcat(this.transform.getMatrix());
            intersectBoundsWithMask(this.rect, this.matrix);
            this.rect.set(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight());
            L.endSection("Layer#computeBounds");
            L.beginSection("Layer#saveLayer");
            canvas.saveLayer(this.rect, this.contentPaint, 31);
            L.endSection("Layer#saveLayer");
            clearCanvas(canvas);
            L.beginSection("Layer#drawLayer");
            drawLayer(canvas, this.matrix, alpha);
            L.endSection("Layer#drawLayer");
            if (hasMasksOnThisLayer()) {
                applyMasks(canvas, this.matrix);
            }
            if (hasMatteOnThisLayer()) {
                L.beginSection("Layer#drawMatte");
                L.beginSection("Layer#saveLayer");
                canvas.saveLayer(this.rect, this.mattePaint, 19);
                L.endSection("Layer#saveLayer");
                clearCanvas(canvas);
                this.matteLayer.draw(canvas, parentMatrix, alpha);
                L.beginSection("Layer#restoreLayer");
                canvas.restore();
                L.endSection("Layer#restoreLayer");
                L.endSection("Layer#drawMatte");
            }
            L.beginSection("Layer#restoreLayer");
            canvas.restore();
            L.endSection("Layer#restoreLayer");
            recordRenderTime(L.endSection(this.drawTraceName));
            return;
        }
        this.matrix.preConcat(this.transform.getMatrix());
        L.beginSection("Layer#drawLayer");
        drawLayer(canvas, this.matrix, alpha);
        L.endSection("Layer#drawLayer");
        recordRenderTime(L.endSection(this.drawTraceName));
    }

    private void recordRenderTime(float ms) {
        this.lottieDrawable.getComposition().getPerformanceTracker().recordRenderTime(this.layerModel.getName(), ms);
    }

    private void clearCanvas(Canvas canvas) {
        L.beginSection("Layer#clearLayer");
        canvas.drawRect(this.rect.left - 1.0f, this.rect.top - 1.0f, this.rect.right + 1.0f, 1.0f + this.rect.bottom, this.clearPaint);
        L.endSection("Layer#clearLayer");
    }

    private void intersectBoundsWithMask(RectF rect2, Matrix matrix2) {
        this.maskBoundsRect.set(0.0f, 0.0f, 0.0f, 0.0f);
        if (hasMasksOnThisLayer()) {
            int size = this.mask.getMasks().size();
            int i = 0;
            while (i < size) {
                Mask mask2 = (Mask) this.mask.getMasks().get(i);
                this.path.set((Path) ((BaseKeyframeAnimation) this.mask.getMaskAnimations().get(i)).getValue());
                this.path.transform(matrix2);
                switch (mask2.getMaskMode()) {
                    case MaskModeSubtract:
                    case MaskModeIntersect:
                        return;
                    default:
                        this.path.computeBounds(this.tempMaskBoundsRect, false);
                        if (i == 0) {
                            this.maskBoundsRect.set(this.tempMaskBoundsRect);
                        } else {
                            this.maskBoundsRect.set(Math.min(this.maskBoundsRect.left, this.tempMaskBoundsRect.left), Math.min(this.maskBoundsRect.top, this.tempMaskBoundsRect.top), Math.max(this.maskBoundsRect.right, this.tempMaskBoundsRect.right), Math.max(this.maskBoundsRect.bottom, this.tempMaskBoundsRect.bottom));
                        }
                        i++;
                }
            }
            rect2.set(Math.max(rect2.left, this.maskBoundsRect.left), Math.max(rect2.top, this.maskBoundsRect.top), Math.min(rect2.right, this.maskBoundsRect.right), Math.min(rect2.bottom, this.maskBoundsRect.bottom));
        }
    }

    private void intersectBoundsWithMatte(RectF rect2, Matrix matrix2) {
        if (hasMatteOnThisLayer() && this.layerModel.getMatteType() != MatteType.Invert) {
            this.matteLayer.getBounds(this.matteBoundsRect, matrix2);
            rect2.set(Math.max(rect2.left, this.matteBoundsRect.left), Math.max(rect2.top, this.matteBoundsRect.top), Math.min(rect2.right, this.matteBoundsRect.right), Math.min(rect2.bottom, this.matteBoundsRect.bottom));
        }
    }

    private void applyMasks(Canvas canvas, Matrix matrix2) {
        applyMasks(canvas, matrix2, MaskMode.MaskModeAdd);
        applyMasks(canvas, matrix2, MaskMode.MaskModeIntersect);
        applyMasks(canvas, matrix2, MaskMode.MaskModeSubtract);
    }

    @SuppressLint({"WrongConstant"})
    private void applyMasks(Canvas canvas, Matrix matrix2, MaskMode maskMode) {
        Paint paint;
        switch (maskMode) {
            case MaskModeSubtract:
                paint = this.subtractMaskPaint;
                break;
            case MaskModeIntersect:
                Log.w(L.TAG, "Animation contains intersect masks. They are not supported but will be treated like add masks.");
                break;
        }
        paint = this.addMaskPaint;
        int size = this.mask.getMasks().size();
        boolean hasMask = false;
        int i = 0;
        while (true) {
            if (i < size) {
                if (((Mask) this.mask.getMasks().get(i)).getMaskMode() == maskMode) {
                    hasMask = true;
                } else {
                    i++;
                }
            }
        }
        if (hasMask) {
            L.beginSection("Layer#drawMask");
            L.beginSection("Layer#saveLayer");
            canvas.saveLayer(this.rect, paint, 19);
            L.endSection("Layer#saveLayer");
            clearCanvas(canvas);
            for (int i2 = 0; i2 < size; i2++) {
                if (((Mask) this.mask.getMasks().get(i2)).getMaskMode() == maskMode) {
                    this.path.set((Path) ((BaseKeyframeAnimation) this.mask.getMaskAnimations().get(i2)).getValue());
                    this.path.transform(matrix2);
                    BaseKeyframeAnimation<Integer, Integer> opacityAnimation = (BaseKeyframeAnimation) this.mask.getOpacityAnimations().get(i2);
                    int alpha = this.contentPaint.getAlpha();
                    this.contentPaint.setAlpha((int) (((float) ((Integer) opacityAnimation.getValue()).intValue()) * 2.55f));
                    canvas.drawPath(this.path, this.contentPaint);
                    this.contentPaint.setAlpha(alpha);
                }
            }
            L.beginSection("Layer#restoreLayer");
            canvas.restore();
            L.endSection("Layer#restoreLayer");
            L.endSection("Layer#drawMask");
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean hasMasksOnThisLayer() {
        return this.mask != null && !this.mask.getMaskAnimations().isEmpty();
    }

    /* access modifiers changed from: private */
    public void setVisible(boolean visible2) {
        if (visible2 != this.visible) {
            this.visible = visible2;
            invalidateSelf();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        this.transform.setProgress(progress);
        if (this.layerModel.getTimeStretch() != 0.0f) {
            progress /= this.layerModel.getTimeStretch();
        }
        if (this.matteLayer != null) {
            this.matteLayer.setProgress(progress * this.matteLayer.layerModel.getTimeStretch());
        }
        for (int i = 0; i < this.animations.size(); i++) {
            ((BaseKeyframeAnimation) this.animations.get(i)).setProgress(progress);
        }
    }

    private void buildParentLayerListIfNeeded() {
        if (this.parentLayers == null) {
            if (this.parentLayer == null) {
                this.parentLayers = Collections.emptyList();
                return;
            }
            this.parentLayers = new ArrayList();
            for (BaseLayer layer = this.parentLayer; layer != null; layer = layer.parentLayer) {
                this.parentLayers.add(layer);
            }
        }
    }

    public String getName() {
        return this.layerModel.getName();
    }

    public void setContents(List<Content> list, List<Content> list2) {
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
                resolveChildKeyPath(keyPath, depth + keyPath.incrementDepthBy(getName(), depth), accumulator, currentPartialKeyPath);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void resolveChildKeyPath(KeyPath keyPath, int depth, List<KeyPath> list, KeyPath currentPartialKeyPath) {
    }

    @CallSuper
    public <T> void addValueCallback(T property, @Nullable LottieValueCallback<T> callback) {
        this.transform.applyValueCallback(property, callback);
    }
}
