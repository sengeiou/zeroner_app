package com.airbnb.lottie;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RawRes;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.JsonReader;
import android.util.SparseArray;
import android.view.View.BaseSavedState;
import com.airbnb.lottie.LottieComposition.Factory;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.LottieValueCallback;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class LottieAnimationView extends AppCompatImageView {
    /* access modifiers changed from: private */
    public static final Map<String, LottieComposition> ASSET_STRONG_REF_CACHE = new HashMap();
    /* access modifiers changed from: private */
    public static final Map<String, WeakReference<LottieComposition>> ASSET_WEAK_REF_CACHE = new HashMap();
    public static final CacheStrategy DEFAULT_CACHE_STRATEGY = CacheStrategy.Weak;
    /* access modifiers changed from: private */
    public static final SparseArray<LottieComposition> RAW_RES_STRONG_REF_CACHE = new SparseArray<>();
    /* access modifiers changed from: private */
    public static final SparseArray<WeakReference<LottieComposition>> RAW_RES_WEAK_REF_CACHE = new SparseArray<>();
    private static final String TAG = LottieAnimationView.class.getSimpleName();
    private String animationName;
    @RawRes
    private int animationResId;
    private boolean autoPlay = false;
    @Nullable
    private LottieComposition composition;
    /* access modifiers changed from: private */
    @Nullable
    public Cancellable compositionLoader;
    private CacheStrategy defaultCacheStrategy;
    private final OnCompositionLoadedListener loadedListener = new OnCompositionLoadedListener() {
        public void onCompositionLoaded(@Nullable LottieComposition composition) {
            if (composition != null) {
                LottieAnimationView.this.setComposition(composition);
            }
            LottieAnimationView.this.compositionLoader = null;
        }
    };
    private final LottieDrawable lottieDrawable = new LottieDrawable();
    private boolean useHardwareLayer = false;
    private boolean wasAnimatingWhenDetached = false;

    public enum CacheStrategy {
        None,
        Weak,
        Strong
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        String animationName;
        int animationResId;
        String imageAssetsFolder;
        boolean isAnimating;
        float progress;
        int repeatCount;
        int repeatMode;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            boolean z = true;
            super(in);
            this.animationName = in.readString();
            this.progress = in.readFloat();
            if (in.readInt() != 1) {
                z = false;
            }
            this.isAnimating = z;
            this.imageAssetsFolder = in.readString();
            this.repeatMode = in.readInt();
            this.repeatCount = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.animationName);
            out.writeFloat(this.progress);
            out.writeInt(this.isAnimating ? 1 : 0);
            out.writeString(this.imageAssetsFolder);
            out.writeInt(this.repeatMode);
            out.writeInt(this.repeatCount);
        }
    }

    public LottieAnimationView(Context context) {
        super(context);
        init(null);
    }

    public LottieAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LottieAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LottieAnimationView);
        this.defaultCacheStrategy = CacheStrategy.values()[ta.getInt(R.styleable.LottieAnimationView_lottie_cacheStrategy, DEFAULT_CACHE_STRATEGY.ordinal())];
        if (!isInEditMode()) {
            boolean hasRawRes = ta.hasValue(R.styleable.LottieAnimationView_lottie_rawRes);
            boolean hasFileName = ta.hasValue(R.styleable.LottieAnimationView_lottie_fileName);
            if (hasRawRes && hasFileName) {
                throw new IllegalArgumentException("lottie_rawRes and lottie_fileName cannot be used at the same time. Please use use only one at once.");
            } else if (hasRawRes) {
                int rawResId = ta.getResourceId(R.styleable.LottieAnimationView_lottie_rawRes, 0);
                if (rawResId != 0) {
                    setAnimation(rawResId);
                }
            } else if (hasFileName) {
                String fileName = ta.getString(R.styleable.LottieAnimationView_lottie_fileName);
                if (fileName != null) {
                    setAnimation(fileName);
                }
            }
        }
        if (ta.getBoolean(R.styleable.LottieAnimationView_lottie_autoPlay, false)) {
            this.lottieDrawable.playAnimation();
            this.autoPlay = true;
        }
        if (ta.getBoolean(R.styleable.LottieAnimationView_lottie_loop, false)) {
            this.lottieDrawable.setRepeatCount(-1);
        }
        if (ta.hasValue(R.styleable.LottieAnimationView_lottie_repeatMode)) {
            setRepeatMode(ta.getInt(R.styleable.LottieAnimationView_lottie_repeatMode, 1));
        }
        if (ta.hasValue(R.styleable.LottieAnimationView_lottie_repeatCount)) {
            setRepeatCount(ta.getInt(R.styleable.LottieAnimationView_lottie_repeatCount, -1));
        }
        setImageAssetsFolder(ta.getString(R.styleable.LottieAnimationView_lottie_imageAssetsFolder));
        setProgress(ta.getFloat(R.styleable.LottieAnimationView_lottie_progress, 0.0f));
        enableMergePathsForKitKatAndAbove(ta.getBoolean(R.styleable.LottieAnimationView_lottie_enableMergePathsForKitKatAndAbove, false));
        if (ta.hasValue(R.styleable.LottieAnimationView_lottie_colorFilter)) {
            SimpleColorFilter filter = new SimpleColorFilter(ta.getColor(R.styleable.LottieAnimationView_lottie_colorFilter, 0));
            addValueCallback(new KeyPath("**"), (T) LottieProperty.COLOR_FILTER, new LottieValueCallback<>(filter));
        }
        if (ta.hasValue(R.styleable.LottieAnimationView_lottie_scale)) {
            this.lottieDrawable.setScale(ta.getFloat(R.styleable.LottieAnimationView_lottie_scale, 1.0f));
        }
        ta.recycle();
        enableOrDisableHardwareLayer();
    }

    public void setImageResource(int resId) {
        recycleBitmaps();
        cancelLoaderTask();
        super.setImageResource(resId);
    }

    public void setImageDrawable(Drawable drawable) {
        setImageDrawable(drawable, true);
    }

    private void setImageDrawable(Drawable drawable, boolean recycle) {
        if (recycle && drawable != this.lottieDrawable) {
            recycleBitmaps();
        }
        cancelLoaderTask();
        super.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap bm) {
        recycleBitmaps();
        cancelLoaderTask();
        super.setImageBitmap(bm);
    }

    public void invalidateDrawable(@NonNull Drawable dr) {
        if (getDrawable() == this.lottieDrawable) {
            super.invalidateDrawable(this.lottieDrawable);
        } else {
            super.invalidateDrawable(dr);
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.animationName = this.animationName;
        ss.animationResId = this.animationResId;
        ss.progress = this.lottieDrawable.getProgress();
        ss.isAnimating = this.lottieDrawable.isAnimating();
        ss.imageAssetsFolder = this.lottieDrawable.getImageAssetsFolder();
        ss.repeatMode = this.lottieDrawable.getRepeatMode();
        ss.repeatCount = this.lottieDrawable.getRepeatCount();
        return ss;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.animationName = ss.animationName;
        if (!TextUtils.isEmpty(this.animationName)) {
            setAnimation(this.animationName);
        }
        this.animationResId = ss.animationResId;
        if (this.animationResId != 0) {
            setAnimation(this.animationResId);
        }
        setProgress(ss.progress);
        if (ss.isAnimating) {
            playAnimation();
        }
        this.lottieDrawable.setImagesAssetsFolder(ss.imageAssetsFolder);
        setRepeatMode(ss.repeatMode);
        setRepeatCount(ss.repeatCount);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.autoPlay && this.wasAnimatingWhenDetached) {
            playAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (isAnimating()) {
            cancelAnimation();
            this.wasAnimatingWhenDetached = true;
        }
        recycleBitmaps();
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: 0000 */
    @VisibleForTesting
    public void recycleBitmaps() {
        if (this.lottieDrawable != null) {
            this.lottieDrawable.recycleBitmaps();
        }
    }

    public void enableMergePathsForKitKatAndAbove(boolean enable) {
        this.lottieDrawable.enableMergePathsForKitKatAndAbove(enable);
    }

    public boolean isMergePathsEnabledForKitKatAndAbove() {
        return this.lottieDrawable.isMergePathsEnabledForKitKatAndAbove();
    }

    @Deprecated
    public void useExperimentalHardwareAcceleration() {
        useHardwareAcceleration(true);
    }

    @Deprecated
    public void useExperimentalHardwareAcceleration(boolean use) {
        useHardwareAcceleration(use);
    }

    public void useHardwareAcceleration() {
        useHardwareAcceleration(true);
    }

    public void useHardwareAcceleration(boolean use) {
        this.useHardwareLayer = use;
        enableOrDisableHardwareLayer();
    }

    public boolean getUseHardwareAcceleration() {
        return this.useHardwareLayer;
    }

    public void setAnimation(@RawRes int animationResId2) {
        setAnimation(animationResId2, this.defaultCacheStrategy);
    }

    public void setAnimation(@RawRes final int animationResId2, final CacheStrategy cacheStrategy) {
        this.animationResId = animationResId2;
        this.animationName = null;
        if (RAW_RES_WEAK_REF_CACHE.indexOfKey(animationResId2) > 0) {
            LottieComposition ref = (LottieComposition) ((WeakReference) RAW_RES_WEAK_REF_CACHE.get(animationResId2)).get();
            if (ref != null) {
                setComposition(ref);
                return;
            }
        } else if (RAW_RES_STRONG_REF_CACHE.indexOfKey(animationResId2) > 0) {
            setComposition((LottieComposition) RAW_RES_STRONG_REF_CACHE.get(animationResId2));
            return;
        }
        clearComposition();
        cancelLoaderTask();
        this.compositionLoader = Factory.fromRawFile(getContext(), animationResId2, new OnCompositionLoadedListener() {
            public void onCompositionLoaded(LottieComposition composition) {
                if (cacheStrategy == CacheStrategy.Strong) {
                    LottieAnimationView.RAW_RES_STRONG_REF_CACHE.put(animationResId2, composition);
                } else if (cacheStrategy == CacheStrategy.Weak) {
                    LottieAnimationView.RAW_RES_WEAK_REF_CACHE.put(animationResId2, new WeakReference(composition));
                }
                LottieAnimationView.this.setComposition(composition);
            }
        });
    }

    public void setAnimation(String animationName2) {
        setAnimation(animationName2, this.defaultCacheStrategy);
    }

    public void setAnimation(final String animationName2, final CacheStrategy cacheStrategy) {
        this.animationName = animationName2;
        this.animationResId = 0;
        if (ASSET_WEAK_REF_CACHE.containsKey(animationName2)) {
            LottieComposition ref = (LottieComposition) ((WeakReference) ASSET_WEAK_REF_CACHE.get(animationName2)).get();
            if (ref != null) {
                setComposition(ref);
                return;
            }
        } else if (ASSET_STRONG_REF_CACHE.containsKey(animationName2)) {
            setComposition((LottieComposition) ASSET_STRONG_REF_CACHE.get(animationName2));
            return;
        }
        clearComposition();
        cancelLoaderTask();
        this.compositionLoader = Factory.fromAssetFileName(getContext(), animationName2, new OnCompositionLoadedListener() {
            public void onCompositionLoaded(LottieComposition composition) {
                if (cacheStrategy == CacheStrategy.Strong) {
                    LottieAnimationView.ASSET_STRONG_REF_CACHE.put(animationName2, composition);
                } else if (cacheStrategy == CacheStrategy.Weak) {
                    LottieAnimationView.ASSET_WEAK_REF_CACHE.put(animationName2, new WeakReference(composition));
                }
                LottieAnimationView.this.setComposition(composition);
            }
        });
    }

    @Deprecated
    public void setAnimation(JSONObject json) {
        setAnimation(new JsonReader(new StringReader(json.toString())));
    }

    public void setAnimationFromJson(String jsonString) {
        setAnimation(new JsonReader(new StringReader(jsonString)));
    }

    public void setAnimation(JsonReader reader) {
        clearComposition();
        cancelLoaderTask();
        this.compositionLoader = Factory.fromJsonReader(reader, this.loadedListener);
    }

    private void cancelLoaderTask() {
        if (this.compositionLoader != null) {
            this.compositionLoader.cancel();
            this.compositionLoader = null;
        }
    }

    public void setComposition(@NonNull LottieComposition composition2) {
        this.lottieDrawable.setCallback(this);
        this.composition = composition2;
        boolean isNewComposition = this.lottieDrawable.setComposition(composition2);
        enableOrDisableHardwareLayer();
        if (getDrawable() != this.lottieDrawable || isNewComposition) {
            setImageDrawable(null);
            setImageDrawable(this.lottieDrawable);
            requestLayout();
        }
    }

    @Nullable
    public LottieComposition getComposition() {
        return this.composition;
    }

    public boolean hasMasks() {
        return this.lottieDrawable.hasMasks();
    }

    public boolean hasMatte() {
        return this.lottieDrawable.hasMatte();
    }

    public void playAnimation() {
        this.lottieDrawable.playAnimation();
        enableOrDisableHardwareLayer();
    }

    public void resumeAnimation() {
        this.lottieDrawable.resumeAnimation();
        enableOrDisableHardwareLayer();
    }

    public void setMinFrame(int startFrame) {
        this.lottieDrawable.setMinFrame(startFrame);
    }

    public float getMinFrame() {
        return this.lottieDrawable.getMinFrame();
    }

    public void setMinProgress(float startProgress) {
        this.lottieDrawable.setMinProgress(startProgress);
    }

    public void setMaxFrame(int endFrame) {
        this.lottieDrawable.setMaxFrame(endFrame);
    }

    public float getMaxFrame() {
        return this.lottieDrawable.getMaxFrame();
    }

    public void setMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) float endProgress) {
        this.lottieDrawable.setMaxProgress(endProgress);
    }

    public void setMinAndMaxFrame(int minFrame, int maxFrame) {
        this.lottieDrawable.setMinAndMaxFrame(minFrame, maxFrame);
    }

    public void setMinAndMaxProgress(@FloatRange(from = 0.0d, to = 1.0d) float minProgress, @FloatRange(from = 0.0d, to = 1.0d) float maxProgress) {
        this.lottieDrawable.setMinAndMaxProgress(minProgress, maxProgress);
    }

    public void reverseAnimationSpeed() {
        this.lottieDrawable.reverseAnimationSpeed();
    }

    public void setSpeed(float speed) {
        this.lottieDrawable.setSpeed(speed);
    }

    public float getSpeed() {
        return this.lottieDrawable.getSpeed();
    }

    public void addAnimatorUpdateListener(AnimatorUpdateListener updateListener) {
        this.lottieDrawable.addAnimatorUpdateListener(updateListener);
    }

    public void removeUpdateListener(AnimatorUpdateListener updateListener) {
        this.lottieDrawable.removeAnimatorUpdateListener(updateListener);
    }

    public void removeAllUpdateListeners() {
        this.lottieDrawable.removeAllUpdateListeners();
    }

    public void addAnimatorListener(AnimatorListener listener) {
        this.lottieDrawable.addAnimatorListener(listener);
    }

    public void removeAnimatorListener(AnimatorListener listener) {
        this.lottieDrawable.removeAnimatorListener(listener);
    }

    public void removeAllAnimatorListeners() {
        this.lottieDrawable.removeAllAnimatorListeners();
    }

    @Deprecated
    public void loop(boolean loop) {
        this.lottieDrawable.setRepeatCount(loop ? -1 : 0);
    }

    public void setRepeatMode(int mode) {
        this.lottieDrawable.setRepeatMode(mode);
    }

    public int getRepeatMode() {
        return this.lottieDrawable.getRepeatMode();
    }

    public void setRepeatCount(int count) {
        this.lottieDrawable.setRepeatCount(count);
    }

    public int getRepeatCount() {
        return this.lottieDrawable.getRepeatCount();
    }

    public boolean isAnimating() {
        return this.lottieDrawable.isAnimating();
    }

    public void setImageAssetsFolder(String imageAssetsFolder) {
        this.lottieDrawable.setImagesAssetsFolder(imageAssetsFolder);
    }

    @Nullable
    public String getImageAssetsFolder() {
        return this.lottieDrawable.getImageAssetsFolder();
    }

    @Nullable
    public Bitmap updateBitmap(String id, @Nullable Bitmap bitmap) {
        return this.lottieDrawable.updateBitmap(id, bitmap);
    }

    public void setImageAssetDelegate(ImageAssetDelegate assetDelegate) {
        this.lottieDrawable.setImageAssetDelegate(assetDelegate);
    }

    public void setFontAssetDelegate(FontAssetDelegate assetDelegate) {
        this.lottieDrawable.setFontAssetDelegate(assetDelegate);
    }

    public void setTextDelegate(TextDelegate textDelegate) {
        this.lottieDrawable.setTextDelegate(textDelegate);
    }

    public List<KeyPath> resolveKeyPath(KeyPath keyPath) {
        return this.lottieDrawable.resolveKeyPath(keyPath);
    }

    public <T> void addValueCallback(KeyPath keyPath, T property, LottieValueCallback<T> callback) {
        this.lottieDrawable.addValueCallback(keyPath, property, callback);
    }

    public <T> void addValueCallback(KeyPath keyPath, T property, final SimpleLottieValueCallback<T> callback) {
        this.lottieDrawable.addValueCallback(keyPath, property, (LottieValueCallback<T>) new LottieValueCallback<T>() {
            public T getValue(LottieFrameInfo<T> frameInfo) {
                return callback.getValue(frameInfo);
            }
        });
    }

    public void setScale(float scale) {
        this.lottieDrawable.setScale(scale);
        if (getDrawable() == this.lottieDrawable) {
            setImageDrawable(null, false);
            setImageDrawable(this.lottieDrawable, false);
        }
    }

    public float getScale() {
        return this.lottieDrawable.getScale();
    }

    public void cancelAnimation() {
        this.lottieDrawable.cancelAnimation();
        enableOrDisableHardwareLayer();
    }

    public void pauseAnimation() {
        this.lottieDrawable.pauseAnimation();
        enableOrDisableHardwareLayer();
    }

    public void setFrame(int frame) {
        this.lottieDrawable.setFrame(frame);
    }

    public int getFrame() {
        return this.lottieDrawable.getFrame();
    }

    public void setProgress(@FloatRange(from = 0.0d, to = 1.0d) float progress) {
        this.lottieDrawable.setProgress(progress);
    }

    @FloatRange(from = 0.0d, to = 1.0d)
    public float getProgress() {
        return this.lottieDrawable.getProgress();
    }

    public long getDuration() {
        if (this.composition != null) {
            return (long) this.composition.getDuration();
        }
        return 0;
    }

    public void setPerformanceTrackingEnabled(boolean enabled) {
        this.lottieDrawable.setPerformanceTrackingEnabled(enabled);
    }

    @Nullable
    public PerformanceTracker getPerformanceTracker() {
        return this.lottieDrawable.getPerformanceTracker();
    }

    private void clearComposition() {
        this.composition = null;
        this.lottieDrawable.clearComposition();
    }

    private void enableOrDisableHardwareLayer() {
        int i = 1;
        if (this.useHardwareLayer && this.lottieDrawable.isAnimating()) {
            i = 2;
        }
        setLayerType(i, null);
    }
}
