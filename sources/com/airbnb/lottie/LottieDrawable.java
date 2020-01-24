package com.airbnb.lottie;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Build.VERSION;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import com.airbnb.lottie.manager.FontAssetManager;
import com.airbnb.lottie.manager.ImageAssetManager;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.model.layer.CompositionLayer;
import com.airbnb.lottie.parser.LayerParser;
import com.airbnb.lottie.utils.LottieValueAnimator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class LottieDrawable extends Drawable implements Callback, Animatable {
    public static final int INFINITE = -1;
    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static final String TAG = LottieDrawable.class.getSimpleName();
    private int alpha = 255;
    /* access modifiers changed from: private */
    public final LottieValueAnimator animator = new LottieValueAnimator();
    private final Set<ColorFilterData> colorFilterData = new HashSet();
    private LottieComposition composition;
    /* access modifiers changed from: private */
    @Nullable
    public CompositionLayer compositionLayer;
    private boolean enableMergePaths;
    @Nullable
    FontAssetDelegate fontAssetDelegate;
    @Nullable
    private FontAssetManager fontAssetManager;
    @Nullable
    private ImageAssetDelegate imageAssetDelegate;
    @Nullable
    private ImageAssetManager imageAssetManager;
    @Nullable
    private String imageAssetsFolder;
    private final ArrayList<LazyCompositionTask> lazyCompositionTasks = new ArrayList<>();
    private final Matrix matrix = new Matrix();
    private boolean performanceTrackingEnabled;
    private float scale = 1.0f;
    @Nullable
    TextDelegate textDelegate;

    private static class ColorFilterData {
        @Nullable
        final ColorFilter colorFilter;
        @Nullable
        final String contentName;
        final String layerName;

        ColorFilterData(@Nullable String layerName2, @Nullable String contentName2, @Nullable ColorFilter colorFilter2) {
            this.layerName = layerName2;
            this.contentName = contentName2;
            this.colorFilter = colorFilter2;
        }

        public int hashCode() {
            int hashCode = 17;
            if (this.layerName != null) {
                hashCode = this.layerName.hashCode() * 527;
            }
            if (this.contentName != null) {
                return hashCode * 31 * this.contentName.hashCode();
            }
            return hashCode;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ColorFilterData)) {
                return false;
            }
            ColorFilterData other = (ColorFilterData) obj;
            if (hashCode() == other.hashCode() && this.colorFilter == other.colorFilter) {
                return true;
            }
            return false;
        }
    }

    private interface LazyCompositionTask {
        void run(LottieComposition lottieComposition);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    public LottieDrawable() {
        this.animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                if (LottieDrawable.this.compositionLayer != null) {
                    LottieDrawable.this.compositionLayer.setProgress(LottieDrawable.this.animator.getAnimatedValueAbsolute());
                }
            }
        });
    }

    public boolean hasMasks() {
        return this.compositionLayer != null && this.compositionLayer.hasMasks();
    }

    public boolean hasMatte() {
        return this.compositionLayer != null && this.compositionLayer.hasMatte();
    }

    public boolean enableMergePathsForKitKatAndAbove() {
        return this.enableMergePaths;
    }

    public void enableMergePathsForKitKatAndAbove(boolean enable) {
        if (VERSION.SDK_INT < 19) {
            Log.w(TAG, "Merge paths are not supported pre-Kit Kat.");
            return;
        }
        this.enableMergePaths = enable;
        if (this.composition != null) {
            buildCompositionLayer();
        }
    }

    public boolean isMergePathsEnabledForKitKatAndAbove() {
        return this.enableMergePaths;
    }

    public void setImagesAssetsFolder(@Nullable String imageAssetsFolder2) {
        this.imageAssetsFolder = imageAssetsFolder2;
    }

    @Nullable
    public String getImageAssetsFolder() {
        return this.imageAssetsFolder;
    }

    public void recycleBitmaps() {
        if (this.imageAssetManager != null) {
            this.imageAssetManager.recycleBitmaps();
        }
    }

    public boolean setComposition(LottieComposition composition2) {
        if (this.composition == composition2) {
            return false;
        }
        clearComposition();
        this.composition = composition2;
        buildCompositionLayer();
        this.animator.setComposition(composition2);
        setProgress(this.animator.getAnimatedFraction());
        setScale(this.scale);
        updateBounds();
        Iterator<LazyCompositionTask> it = new ArrayList(this.lazyCompositionTasks).iterator();
        while (it.hasNext()) {
            ((LazyCompositionTask) it.next()).run(composition2);
            it.remove();
        }
        this.lazyCompositionTasks.clear();
        composition2.setPerformanceTrackingEnabled(this.performanceTrackingEnabled);
        return true;
    }

    public void setPerformanceTrackingEnabled(boolean enabled) {
        this.performanceTrackingEnabled = enabled;
        if (this.composition != null) {
            this.composition.setPerformanceTrackingEnabled(enabled);
        }
    }

    @Nullable
    public PerformanceTracker getPerformanceTracker() {
        if (this.composition != null) {
            return this.composition.getPerformanceTracker();
        }
        return null;
    }

    private void buildCompositionLayer() {
        this.compositionLayer = new CompositionLayer(this, LayerParser.parse(this.composition), this.composition.getLayers(), this.composition);
    }

    public void clearComposition() {
        recycleBitmaps();
        if (this.animator.isRunning()) {
            this.animator.cancel();
        }
        this.composition = null;
        this.compositionLayer = null;
        this.imageAssetManager = null;
        invalidateSelf();
    }

    public void invalidateSelf() {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void setAlpha(@IntRange(from = 0, to = 255) int alpha2) {
        this.alpha = alpha2;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        Log.w(L.TAG, "Use addColorFilter instead.");
    }

    public int getOpacity() {
        return -3;
    }

    public void draw(@NonNull Canvas canvas) {
        L.beginSection("Drawable#draw");
        if (this.compositionLayer != null) {
            float scale2 = this.scale;
            float extraScale = 1.0f;
            float maxScale = getMaxScale(canvas);
            if (scale2 > maxScale) {
                scale2 = maxScale;
                extraScale = this.scale / scale2;
            }
            if (extraScale > 1.0f) {
                canvas.save();
                float halfWidth = ((float) this.composition.getBounds().width()) / 2.0f;
                float halfHeight = ((float) this.composition.getBounds().height()) / 2.0f;
                float scaledHalfWidth = halfWidth * scale2;
                float scaledHalfHeight = halfHeight * scale2;
                canvas.translate((getScale() * halfWidth) - scaledHalfWidth, (getScale() * halfHeight) - scaledHalfHeight);
                canvas.scale(extraScale, extraScale, scaledHalfWidth, scaledHalfHeight);
            }
            this.matrix.reset();
            this.matrix.preScale(scale2, scale2);
            this.compositionLayer.draw(canvas, this.matrix, this.alpha);
            L.endSection("Drawable#draw");
            if (extraScale > 1.0f) {
                canvas.restore();
            }
        }
    }

    public void start() {
        playAnimation();
    }

    public void stop() {
        endAnimation();
    }

    public boolean isRunning() {
        return isAnimating();
    }

    public void playAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public void run(LottieComposition composition) {
                    LottieDrawable.this.playAnimation();
                }
            });
        } else {
            this.animator.playAnimation();
        }
    }

    public void endAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.endAnimation();
    }

    public void resumeAnimation() {
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public void run(LottieComposition composition) {
                    LottieDrawable.this.resumeAnimation();
                }
            });
        } else {
            this.animator.resumeAnimation();
        }
    }

    public void setMinFrame(int minFrame) {
        this.animator.setMinFrame(minFrame);
    }

    public float getMinFrame() {
        return this.animator.getMinFrame();
    }

    public void setMinProgress(final float minProgress) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public void run(LottieComposition composition) {
                    LottieDrawable.this.setMinProgress(minProgress);
                }
            });
        } else {
            setMinFrame((int) (this.composition.getDurationFrames() * minProgress));
        }
    }

    public void setMaxFrame(int maxFrame) {
        this.animator.setMaxFrame(maxFrame);
    }

    public float getMaxFrame() {
        return this.animator.getMaxFrame();
    }

    public void setMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) final float maxProgress) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public void run(LottieComposition composition) {
                    LottieDrawable.this.setMaxProgress(maxProgress);
                }
            });
        } else {
            setMaxFrame((int) (this.composition.getDurationFrames() * maxProgress));
        }
    }

    public void setMinAndMaxFrame(int minFrame, int maxFrame) {
        this.animator.setMinAndMaxFrames(minFrame, maxFrame);
    }

    public void setMinAndMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) final float minProgress, @FloatRange(from = 0.0d, to = 1.0d) final float maxProgress) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public void run(LottieComposition composition) {
                    LottieDrawable.this.setMinAndMaxProgress(minProgress, maxProgress);
                }
            });
        } else {
            setMinAndMaxFrame((int) (this.composition.getDurationFrames() * minProgress), (int) (this.composition.getDurationFrames() * maxProgress));
        }
    }

    public void reverseAnimationSpeed() {
        this.animator.reverseAnimationSpeed();
    }

    public void setSpeed(float speed) {
        this.animator.setSpeed(speed);
    }

    public float getSpeed() {
        return this.animator.getSpeed();
    }

    public void addAnimatorUpdateListener(AnimatorUpdateListener updateListener) {
        this.animator.addUpdateListener(updateListener);
    }

    public void removeAnimatorUpdateListener(AnimatorUpdateListener updateListener) {
        this.animator.removeUpdateListener(updateListener);
    }

    public void removeAllUpdateListeners() {
        this.animator.removeAllUpdateListeners();
    }

    public void addAnimatorListener(AnimatorListener listener) {
        this.animator.addListener(listener);
    }

    public void removeAnimatorListener(AnimatorListener listener) {
        this.animator.removeListener(listener);
    }

    public void removeAllAnimatorListeners() {
        this.animator.removeAllListeners();
    }

    public void setFrame(final int frame) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public void run(LottieComposition composition) {
                    LottieDrawable.this.setFrame(frame);
                }
            });
        } else {
            this.animator.setFrame(frame);
        }
    }

    public int getFrame() {
        return (int) this.animator.getFrame();
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) final float progress) {
        if (this.composition == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public void run(LottieComposition composition) {
                    LottieDrawable.this.setProgress(progress);
                }
            });
        } else {
            setFrame((int) MiscUtils.lerp(this.composition.getStartFrame(), this.composition.getEndFrame(), progress));
        }
    }

    @Deprecated
    public void loop(boolean loop) {
        this.animator.setRepeatCount(loop ? -1 : 0);
    }

    public void setRepeatMode(int mode) {
        this.animator.setRepeatMode(mode);
    }

    public int getRepeatMode() {
        return this.animator.getRepeatMode();
    }

    public void setRepeatCount(int count) {
        this.animator.setRepeatCount(count);
    }

    public int getRepeatCount() {
        return this.animator.getRepeatCount();
    }

    public boolean isLooping() {
        return this.animator.getRepeatCount() == -1;
    }

    public boolean isAnimating() {
        return this.animator.isRunning();
    }

    public void setScale(float scale2) {
        this.scale = scale2;
        updateBounds();
    }

    public void setImageAssetDelegate(ImageAssetDelegate assetDelegate) {
        this.imageAssetDelegate = assetDelegate;
        if (this.imageAssetManager != null) {
            this.imageAssetManager.setDelegate(assetDelegate);
        }
    }

    public void setFontAssetDelegate(FontAssetDelegate assetDelegate) {
        this.fontAssetDelegate = assetDelegate;
        if (this.fontAssetManager != null) {
            this.fontAssetManager.setDelegate(assetDelegate);
        }
    }

    public void setTextDelegate(TextDelegate textDelegate2) {
        this.textDelegate = textDelegate2;
    }

    @Nullable
    public TextDelegate getTextDelegate() {
        return this.textDelegate;
    }

    public boolean useTextGlyphs() {
        return this.textDelegate == null && this.composition.getCharacters().size() > 0;
    }

    public float getScale() {
        return this.scale;
    }

    public LottieComposition getComposition() {
        return this.composition;
    }

    private void updateBounds() {
        if (this.composition != null) {
            float scale2 = getScale();
            setBounds(0, 0, (int) (((float) this.composition.getBounds().width()) * scale2), (int) (((float) this.composition.getBounds().height()) * scale2));
        }
    }

    public void cancelAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.cancel();
    }

    public void pauseAnimation() {
        this.lazyCompositionTasks.clear();
        this.animator.pauseAnimation();
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.animator.getAnimatedValueAbsolute();
    }

    public int getIntrinsicWidth() {
        if (this.composition == null) {
            return -1;
        }
        return (int) (((float) this.composition.getBounds().width()) * getScale());
    }

    public int getIntrinsicHeight() {
        if (this.composition == null) {
            return -1;
        }
        return (int) (((float) this.composition.getBounds().height()) * getScale());
    }

    public List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        if (this.compositionLayer == null) {
            Log.w(L.TAG, "Cannot resolve KeyPath. Composition is not set yet.");
            return Collections.emptyList();
        }
        List<KeyPath> keyPaths = new ArrayList<>();
        this.compositionLayer.resolveKeyPath(keyPath, 0, keyPaths, new KeyPath(new String[0]));
        return keyPaths;
    }

    public <T> void addValueCallback(final KeyPath keyPath, final T property, final LottieValueCallback<T> callback) {
        boolean invalidate;
        if (this.compositionLayer == null) {
            this.lazyCompositionTasks.add(new LazyCompositionTask() {
                public void run(LottieComposition composition) {
                    LottieDrawable.this.addValueCallback(keyPath, property, callback);
                }
            });
            return;
        }
        if (keyPath.getResolvedElement() != null) {
            keyPath.getResolvedElement().addValueCallback(property, callback);
            invalidate = true;
        } else {
            List<KeyPath> elements = resolveKeyPath(keyPath);
            for (int i = 0; i < elements.size(); i++) {
                ((KeyPath) elements.get(i)).getResolvedElement().addValueCallback(property, callback);
            }
            invalidate = !elements.isEmpty();
        }
        if (invalidate) {
            invalidateSelf();
            if (property == LottieProperty.TIME_REMAP) {
                setProgress(getProgress());
            }
        }
    }

    public <T> void addValueCallback(KeyPath keyPath, T property, final SimpleLottieValueCallback<T> callback) {
        addValueCallback(keyPath, property, (LottieValueCallback<T>) new LottieValueCallback<T>() {
            public T getValue(LottieFrameInfo<T> frameInfo) {
                return callback.getValue(frameInfo);
            }
        });
    }

    @Nullable
    public Bitmap updateBitmap(String id, @Nullable Bitmap bitmap) {
        ImageAssetManager bm = getImageAssetManager();
        if (bm == null) {
            Log.w(L.TAG, "Cannot update bitmap. Most likely the drawable is not added to a View which prevents Lottie from getting a Context.");
            return null;
        }
        Bitmap updateBitmap = bm.updateBitmap(id, bitmap);
        invalidateSelf();
        return updateBitmap;
    }

    @Nullable
    public Bitmap getImageAsset(String id) {
        ImageAssetManager bm = getImageAssetManager();
        if (bm != null) {
            return bm.bitmapForId(id);
        }
        return null;
    }

    private ImageAssetManager getImageAssetManager() {
        if (getCallback() == null) {
            return null;
        }
        if (this.imageAssetManager != null && !this.imageAssetManager.hasSameContext(getContext())) {
            this.imageAssetManager.recycleBitmaps();
            this.imageAssetManager = null;
        }
        if (this.imageAssetManager == null) {
            this.imageAssetManager = new ImageAssetManager(getCallback(), this.imageAssetsFolder, this.imageAssetDelegate, this.composition.getImages());
        }
        return this.imageAssetManager;
    }

    @Nullable
    public Typeface getTypeface(String fontFamily, String style) {
        FontAssetManager assetManager = getFontAssetManager();
        if (assetManager != null) {
            return assetManager.getTypeface(fontFamily, style);
        }
        return null;
    }

    private FontAssetManager getFontAssetManager() {
        if (getCallback() == null) {
            return null;
        }
        if (this.fontAssetManager == null) {
            this.fontAssetManager = new FontAssetManager(getCallback(), this.fontAssetDelegate);
        }
        return this.fontAssetManager;
    }

    @Nullable
    private Context getContext() {
        Callback callback = getCallback();
        if (callback != null && (callback instanceof View)) {
            return ((View) callback).getContext();
        }
        return null;
    }

    public void invalidateDrawable(@NonNull Drawable who) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public void scheduleDrawable(@NonNull Drawable who, @NonNull Runnable what, long when) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    public void unscheduleDrawable(@NonNull Drawable who, @NonNull Runnable what) {
        Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }

    private float getMaxScale(@NonNull Canvas canvas) {
        return Math.min(((float) canvas.getWidth()) / ((float) this.composition.getBounds().width()), ((float) canvas.getHeight()) / ((float) this.composition.getBounds().height()));
    }
}
