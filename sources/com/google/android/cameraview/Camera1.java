package com.google.android.cameraview;

import android.annotation.SuppressLint;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Build.VERSION;
import android.support.v4.util.SparseArrayCompat;
import android.view.SurfaceHolder;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicBoolean;

class Camera1 extends CameraViewImpl {
    private static final SparseArrayCompat<String> FLASH_MODES = new SparseArrayCompat<>();
    private static final int INVALID_CAMERA_ID = -1;
    /* access modifiers changed from: private */
    public final AtomicBoolean isPictureCaptureInProgress = new AtomicBoolean(false);
    private AspectRatio mAspectRatio;
    private boolean mAutoFocus;
    Camera mCamera;
    private int mCameraId;
    private final CameraInfo mCameraInfo = new CameraInfo();
    private Parameters mCameraParameters;
    private int mDisplayOrientation;
    private int mFacing;
    private int mFlash;
    private final SizeMap mPictureSizes = new SizeMap();
    private final SizeMap mPreviewSizes = new SizeMap();
    private boolean mShowingPreview;

    static {
        FLASH_MODES.put(0, "off");
        FLASH_MODES.put(1, "on");
        FLASH_MODES.put(2, "torch");
        FLASH_MODES.put(3, "auto");
        FLASH_MODES.put(4, "red-eye");
    }

    Camera1(Callback callback, PreviewImpl preview) {
        super(callback, preview);
        preview.setCallback(new Callback() {
            public void onSurfaceChanged() {
                if (Camera1.this.mCamera != null) {
                    Camera1.this.setUpPreview();
                    Camera1.this.adjustCameraParameters();
                }
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public boolean start() {
        chooseCamera();
        openCamera();
        if (this.mPreview.isReady()) {
            setUpPreview();
        }
        this.mShowingPreview = true;
        this.mCamera.startPreview();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void stop() {
        if (this.mCamera != null) {
            this.mCamera.stopPreview();
        }
        this.mShowingPreview = false;
        releaseCamera();
    }

    /* access modifiers changed from: 0000 */
    @SuppressLint({"NewApi"})
    public void setUpPreview() {
        try {
            if (this.mPreview.getOutputClass() == SurfaceHolder.class) {
                boolean needsToStopPreview = this.mShowingPreview && VERSION.SDK_INT < 14;
                if (needsToStopPreview) {
                    this.mCamera.stopPreview();
                }
                this.mCamera.setPreviewDisplay(this.mPreview.getSurfaceHolder());
                if (needsToStopPreview) {
                    this.mCamera.startPreview();
                    return;
                }
                return;
            }
            this.mCamera.setPreviewTexture((SurfaceTexture) this.mPreview.getSurfaceTexture());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean isCameraOpened() {
        return this.mCamera != null;
    }

    /* access modifiers changed from: 0000 */
    public void setFacing(int facing) {
        if (this.mFacing != facing) {
            this.mFacing = facing;
            if (isCameraOpened()) {
                stop();
                start();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int getFacing() {
        return this.mFacing;
    }

    /* access modifiers changed from: 0000 */
    public Set<AspectRatio> getSupportedAspectRatios() {
        SizeMap idealAspectRatios = this.mPreviewSizes;
        for (AspectRatio aspectRatio : idealAspectRatios.ratios()) {
            if (this.mPictureSizes.sizes(aspectRatio) == null) {
                idealAspectRatios.remove(aspectRatio);
            }
        }
        return idealAspectRatios.ratios();
    }

    /* access modifiers changed from: 0000 */
    public boolean setAspectRatio(AspectRatio ratio) {
        if (this.mAspectRatio == null || !isCameraOpened()) {
            this.mAspectRatio = ratio;
            return true;
        } else if (this.mAspectRatio.equals(ratio)) {
            return false;
        } else {
            if (this.mPreviewSizes.sizes(ratio) == null) {
                throw new UnsupportedOperationException(ratio + " is not supported");
            }
            this.mAspectRatio = ratio;
            adjustCameraParameters();
            return true;
        }
    }

    /* access modifiers changed from: 0000 */
    public AspectRatio getAspectRatio() {
        return this.mAspectRatio;
    }

    /* access modifiers changed from: 0000 */
    public void setAutoFocus(boolean autoFocus) {
        if (this.mAutoFocus != autoFocus && setAutoFocusInternal(autoFocus)) {
            this.mCamera.setParameters(this.mCameraParameters);
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean getAutoFocus() {
        if (!isCameraOpened()) {
            return this.mAutoFocus;
        }
        String focusMode = this.mCameraParameters.getFocusMode();
        return focusMode != null && focusMode.contains("continuous");
    }

    /* access modifiers changed from: 0000 */
    public void setFlash(int flash) {
        if (flash != this.mFlash && setFlashInternal(flash)) {
            this.mCamera.setParameters(this.mCameraParameters);
        }
    }

    /* access modifiers changed from: 0000 */
    public int getFlash() {
        return this.mFlash;
    }

    /* access modifiers changed from: 0000 */
    public void takePicture() {
        if (!isCameraOpened()) {
            throw new IllegalStateException("Camera is not ready. Call start() before takePicture().");
        } else if (getAutoFocus()) {
            this.mCamera.cancelAutoFocus();
            this.mCamera.autoFocus(new AutoFocusCallback() {
                public void onAutoFocus(boolean success, Camera camera) {
                    Camera1.this.takePictureInternal();
                }
            });
        } else {
            takePictureInternal();
        }
    }

    /* access modifiers changed from: 0000 */
    public void takePictureInternal() {
        if (!this.isPictureCaptureInProgress.getAndSet(true)) {
            this.mCamera.takePicture(null, null, null, new PictureCallback() {
                public void onPictureTaken(byte[] data, Camera camera) {
                    Camera1.this.isPictureCaptureInProgress.set(false);
                    Camera1.this.mCallback.onPictureTaken(data);
                    camera.cancelAutoFocus();
                    camera.startPreview();
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public void setDisplayOrientation(int displayOrientation) {
        if (this.mDisplayOrientation != displayOrientation) {
            this.mDisplayOrientation = displayOrientation;
            if (isCameraOpened()) {
                this.mCameraParameters.setRotation(calcCameraRotation(displayOrientation));
                this.mCamera.setParameters(this.mCameraParameters);
                boolean needsToStopPreview = this.mShowingPreview && VERSION.SDK_INT < 14;
                if (needsToStopPreview) {
                    this.mCamera.stopPreview();
                }
                this.mCamera.setDisplayOrientation(calcDisplayOrientation(displayOrientation));
                if (needsToStopPreview) {
                    this.mCamera.startPreview();
                }
            }
        }
    }

    private void chooseCamera() {
        int count = Camera.getNumberOfCameras();
        for (int i = 0; i < count; i++) {
            Camera.getCameraInfo(i, this.mCameraInfo);
            if (this.mCameraInfo.facing == this.mFacing) {
                this.mCameraId = i;
                return;
            }
        }
        this.mCameraId = -1;
    }

    private void openCamera() {
        if (this.mCamera != null) {
            releaseCamera();
        }
        this.mCamera = Camera.open(this.mCameraId);
        this.mCameraParameters = this.mCamera.getParameters();
        this.mPreviewSizes.clear();
        for (Size size : this.mCameraParameters.getSupportedPreviewSizes()) {
            this.mPreviewSizes.add(new Size(size.width, size.height));
        }
        this.mPictureSizes.clear();
        for (Size size2 : this.mCameraParameters.getSupportedPictureSizes()) {
            this.mPictureSizes.add(new Size(size2.width, size2.height));
        }
        if (this.mAspectRatio == null) {
            this.mAspectRatio = Constants.DEFAULT_ASPECT_RATIO;
        }
        adjustCameraParameters();
        this.mCamera.setDisplayOrientation(calcDisplayOrientation(this.mDisplayOrientation));
        this.mCallback.onCameraOpened();
    }

    private AspectRatio chooseAspectRatio() {
        AspectRatio r = null;
        for (AspectRatio ratio : this.mPreviewSizes.ratios()) {
            r = ratio;
            if (ratio.equals(Constants.DEFAULT_ASPECT_RATIO)) {
                return ratio;
            }
        }
        return r;
    }

    /* access modifiers changed from: 0000 */
    public void adjustCameraParameters() {
        SortedSet<Size> sizes = this.mPreviewSizes.sizes(this.mAspectRatio);
        if (sizes == null) {
            this.mAspectRatio = chooseAspectRatio();
            sizes = this.mPreviewSizes.sizes(this.mAspectRatio);
        }
        Size size = chooseOptimalSize(sizes);
        Size pictureSize = (Size) this.mPictureSizes.sizes(this.mAspectRatio).last();
        if (this.mShowingPreview) {
            this.mCamera.stopPreview();
        }
        this.mCameraParameters.setPreviewSize(size.getWidth(), size.getHeight());
        this.mCameraParameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());
        this.mCameraParameters.setRotation(calcCameraRotation(this.mDisplayOrientation));
        setAutoFocusInternal(this.mAutoFocus);
        setFlashInternal(this.mFlash);
        this.mCamera.setParameters(this.mCameraParameters);
        if (this.mShowingPreview) {
            this.mCamera.startPreview();
        }
    }

    private Size chooseOptimalSize(SortedSet<Size> sizes) {
        int desiredWidth;
        int desiredHeight;
        if (!this.mPreview.isReady()) {
            return (Size) sizes.first();
        }
        int surfaceWidth = this.mPreview.getWidth();
        int surfaceHeight = this.mPreview.getHeight();
        if (isLandscape(this.mDisplayOrientation)) {
            desiredWidth = surfaceHeight;
            desiredHeight = surfaceWidth;
        } else {
            desiredWidth = surfaceWidth;
            desiredHeight = surfaceHeight;
        }
        Size result = null;
        for (Size size : sizes) {
            if (desiredWidth <= size.getWidth() && desiredHeight <= size.getHeight()) {
                return size;
            }
            result = size;
        }
        return result;
    }

    private void releaseCamera() {
        if (this.mCamera != null) {
            this.mCamera.release();
            this.mCamera = null;
            this.mCallback.onCameraClosed();
        }
    }

    private int calcDisplayOrientation(int screenOrientationDegrees) {
        if (this.mCameraInfo.facing == 1) {
            return (360 - ((this.mCameraInfo.orientation + screenOrientationDegrees) % 360)) % 360;
        }
        return ((this.mCameraInfo.orientation - screenOrientationDegrees) + 360) % 360;
    }

    private int calcCameraRotation(int screenOrientationDegrees) {
        if (this.mCameraInfo.facing == 1) {
            return (this.mCameraInfo.orientation + screenOrientationDegrees) % 360;
        }
        return ((this.mCameraInfo.orientation + screenOrientationDegrees) + (isLandscape(screenOrientationDegrees) ? 180 : 0)) % 360;
    }

    private boolean isLandscape(int orientationDegrees) {
        return orientationDegrees == 90 || orientationDegrees == 270;
    }

    private boolean setAutoFocusInternal(boolean autoFocus) {
        this.mAutoFocus = autoFocus;
        if (!isCameraOpened()) {
            return false;
        }
        List<String> modes = this.mCameraParameters.getSupportedFocusModes();
        if (autoFocus && modes.contains("continuous-picture")) {
            this.mCameraParameters.setFocusMode("continuous-picture");
        } else if (modes.contains("fixed")) {
            this.mCameraParameters.setFocusMode("fixed");
        } else if (modes.contains("infinity")) {
            this.mCameraParameters.setFocusMode("infinity");
        } else {
            this.mCameraParameters.setFocusMode((String) modes.get(0));
        }
        return true;
    }

    private boolean setFlashInternal(int flash) {
        if (isCameraOpened()) {
            List<String> modes = this.mCameraParameters.getSupportedFlashModes();
            String mode = (String) FLASH_MODES.get(flash);
            if (modes == null || !modes.contains(mode)) {
                String currentMode = (String) FLASH_MODES.get(this.mFlash);
                if (modes != null && modes.contains(currentMode)) {
                    return false;
                }
                this.mCameraParameters.setFlashMode("off");
                this.mFlash = 0;
                return true;
            }
            this.mCameraParameters.setFlashMode(mode);
            this.mFlash = flash;
            return true;
        }
        this.mFlash = flash;
        return false;
    }
}
