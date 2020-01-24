package com.google.android.cameraview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.common.primitives.Ints;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class CameraView extends FrameLayout {
    static final /* synthetic */ boolean $assertionsDisabled = (!CameraView.class.desiredAssertionStatus());
    public static final int FACING_BACK = 0;
    public static final int FACING_FRONT = 1;
    public static final int FLASH_AUTO = 3;
    public static final int FLASH_OFF = 0;
    public static final int FLASH_ON = 1;
    public static final int FLASH_RED_EYE = 4;
    public static final int FLASH_TORCH = 2;
    private boolean mAdjustViewBounds;
    private final CallbackBridge mCallbacks;
    private final DisplayOrientationDetector mDisplayOrientationDetector;
    CameraViewImpl mImpl;

    public static abstract class Callback {
        public void onCameraOpened(CameraView cameraView) {
        }

        public void onCameraClosed(CameraView cameraView) {
        }

        public void onPictureTaken(CameraView cameraView, byte[] data) {
        }
    }

    private class CallbackBridge implements Callback {
        private final ArrayList<Callback> mCallbacks = new ArrayList<>();
        private boolean mRequestLayoutOnOpen;

        CallbackBridge() {
        }

        public void add(Callback callback) {
            this.mCallbacks.add(callback);
        }

        public void remove(Callback callback) {
            this.mCallbacks.remove(callback);
        }

        public void onCameraOpened() {
            if (this.mRequestLayoutOnOpen) {
                this.mRequestLayoutOnOpen = false;
                CameraView.this.requestLayout();
            }
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onCameraOpened(CameraView.this);
            }
        }

        public void onCameraClosed() {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onCameraClosed(CameraView.this);
            }
        }

        public void onPictureTaken(byte[] data) {
            Iterator it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onPictureTaken(CameraView.this, data);
            }
        }

        public void reserveRequestLayoutOnOpen() {
            this.mRequestLayoutOnOpen = true;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Facing {
    }

    public @interface Flash {
    }

    protected static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        });
        boolean autoFocus;
        int facing;
        @Flash
        int flash;
        AspectRatio ratio;

        public SavedState(Parcel source, ClassLoader loader) {
            super(source);
            this.facing = source.readInt();
            this.ratio = (AspectRatio) source.readParcelable(loader);
            this.autoFocus = source.readByte() != 0;
            this.flash = source.readInt();
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel out, int flags) {
            int i = 0;
            super.writeToParcel(out, flags);
            out.writeInt(this.facing);
            out.writeParcelable(this.ratio, 0);
            if (this.autoFocus) {
                i = 1;
            }
            out.writeByte((byte) i);
            out.writeInt(this.flash);
        }
    }

    public CameraView(Context context) {
        this(context, null);
    }

    public CameraView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            this.mCallbacks = null;
            this.mDisplayOrientationDetector = null;
            return;
        }
        PreviewImpl preview = createPreviewImpl(context);
        this.mCallbacks = new CallbackBridge();
        if (VERSION.SDK_INT < 21) {
            this.mImpl = new Camera1(this.mCallbacks, preview);
        } else if (VERSION.SDK_INT < 23) {
            this.mImpl = new Camera2(this.mCallbacks, preview, context);
        } else {
            this.mImpl = new Camera2Api23(this.mCallbacks, preview, context);
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CameraView, defStyleAttr, R.style.Widget_CameraView);
        this.mAdjustViewBounds = a.getBoolean(R.styleable.CameraView_android_adjustViewBounds, false);
        setFacing(a.getInt(R.styleable.CameraView_facing, 0));
        String aspectRatio = a.getString(R.styleable.CameraView_aspectRatio);
        if (aspectRatio != null) {
            setAspectRatio(AspectRatio.parse(aspectRatio));
        } else {
            setAspectRatio(Constants.DEFAULT_ASPECT_RATIO);
        }
        setAutoFocus(a.getBoolean(R.styleable.CameraView_autoFocus, true));
        setFlash(a.getInt(R.styleable.CameraView_flash, 3));
        a.recycle();
        this.mDisplayOrientationDetector = new DisplayOrientationDetector(context) {
            public void onDisplayOrientationChanged(int displayOrientation) {
                CameraView.this.mImpl.setDisplayOrientation(displayOrientation);
            }
        };
    }

    @NonNull
    private PreviewImpl createPreviewImpl(Context context) {
        if (VERSION.SDK_INT < 14) {
            return new SurfaceViewPreview(context, this);
        }
        return new TextureViewPreview(context, this);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mDisplayOrientationDetector.enable(ViewCompat.getDisplay(this));
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (!isInEditMode()) {
            this.mDisplayOrientationDetector.disable();
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInEditMode()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (!this.mAdjustViewBounds) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else if (!isCameraOpened()) {
            this.mCallbacks.reserveRequestLayoutOnOpen();
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        } else {
            int widthMode = MeasureSpec.getMode(widthMeasureSpec);
            int heightMode = MeasureSpec.getMode(heightMeasureSpec);
            if (widthMode == 1073741824 && heightMode != 1073741824) {
                AspectRatio ratio = getAspectRatio();
                if ($assertionsDisabled || ratio != null) {
                    int height = (int) (((float) MeasureSpec.getSize(widthMeasureSpec)) * ratio.toFloat());
                    if (heightMode == Integer.MIN_VALUE) {
                        height = Math.min(height, MeasureSpec.getSize(heightMeasureSpec));
                    }
                    super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, Ints.MAX_POWER_OF_TWO));
                } else {
                    throw new AssertionError();
                }
            } else if (widthMode == 1073741824 || heightMode != 1073741824) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else {
                AspectRatio ratio2 = getAspectRatio();
                if ($assertionsDisabled || ratio2 != null) {
                    int width = (int) (((float) MeasureSpec.getSize(heightMeasureSpec)) * ratio2.toFloat());
                    if (widthMode == Integer.MIN_VALUE) {
                        width = Math.min(width, MeasureSpec.getSize(widthMeasureSpec));
                    }
                    super.onMeasure(MeasureSpec.makeMeasureSpec(width, Ints.MAX_POWER_OF_TWO), heightMeasureSpec);
                } else {
                    throw new AssertionError();
                }
            }
        }
        int width2 = getMeasuredWidth();
        int height2 = getMeasuredHeight();
        AspectRatio ratio3 = getAspectRatio();
        if (this.mDisplayOrientationDetector.getLastKnownDisplayOrientation() % 180 == 0) {
            ratio3 = ratio3.inverse();
        }
        if (!$assertionsDisabled && ratio3 == null) {
            throw new AssertionError();
        } else if (height2 < (ratio3.getY() * width2) / ratio3.getX()) {
            this.mImpl.getView().measure(MeasureSpec.makeMeasureSpec(width2, Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec((ratio3.getY() * width2) / ratio3.getX(), Ints.MAX_POWER_OF_TWO));
        } else {
            this.mImpl.getView().measure(MeasureSpec.makeMeasureSpec((ratio3.getX() * height2) / ratio3.getY(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(height2, Ints.MAX_POWER_OF_TWO));
        }
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        state.facing = getFacing();
        state.ratio = getAspectRatio();
        state.autoFocus = getAutoFocus();
        state.flash = getFlash();
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setFacing(ss.facing);
        setAspectRatio(ss.ratio);
        setAutoFocus(ss.autoFocus);
        setFlash(ss.flash);
    }

    public void start() {
        if (!this.mImpl.start()) {
            Parcelable state = onSaveInstanceState();
            this.mImpl = new Camera1(this.mCallbacks, createPreviewImpl(getContext()));
            onRestoreInstanceState(state);
            this.mImpl.start();
        }
    }

    public void stop() {
        this.mImpl.stop();
    }

    public boolean isCameraOpened() {
        return this.mImpl.isCameraOpened();
    }

    public void addCallback(@NonNull Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void removeCallback(@NonNull Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (this.mAdjustViewBounds != adjustViewBounds) {
            this.mAdjustViewBounds = adjustViewBounds;
            requestLayout();
        }
    }

    public boolean getAdjustViewBounds() {
        return this.mAdjustViewBounds;
    }

    public void setFacing(int facing) {
        this.mImpl.setFacing(facing);
    }

    public int getFacing() {
        return this.mImpl.getFacing();
    }

    public Set<AspectRatio> getSupportedAspectRatios() {
        return this.mImpl.getSupportedAspectRatios();
    }

    public void setAspectRatio(@NonNull AspectRatio ratio) {
        if (this.mImpl.setAspectRatio(ratio)) {
            requestLayout();
        }
    }

    @Nullable
    public AspectRatio getAspectRatio() {
        return this.mImpl.getAspectRatio();
    }

    public void setAutoFocus(boolean autoFocus) {
        this.mImpl.setAutoFocus(autoFocus);
    }

    public boolean getAutoFocus() {
        return this.mImpl.getAutoFocus();
    }

    public void setFlash(@Flash int flash) {
        this.mImpl.setFlash(flash);
    }

    @Flash
    public int getFlash() {
        return this.mImpl.getFlash();
    }

    public void takePicture() {
        this.mImpl.takePicture();
    }
}
