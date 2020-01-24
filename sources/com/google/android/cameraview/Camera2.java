package com.google.android.cameraview;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCaptureSession.CaptureCallback;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraDevice.StateCallback;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureRequest.Builder;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.Image.Plane;
import android.media.ImageReader;
import android.media.ImageReader.OnImageAvailableListener;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Set;
import java.util.SortedSet;

@TargetApi(21)
class Camera2 extends CameraViewImpl {
    private static final SparseIntArray INTERNAL_FACINGS = new SparseIntArray();
    private static final int MAX_PREVIEW_HEIGHT = 1080;
    private static final int MAX_PREVIEW_WIDTH = 1920;
    private static final String TAG = "Camera2";
    private AspectRatio mAspectRatio = Constants.DEFAULT_ASPECT_RATIO;
    private boolean mAutoFocus;
    CameraDevice mCamera;
    private CameraCharacteristics mCameraCharacteristics;
    private final StateCallback mCameraDeviceCallback = new StateCallback() {
        public void onOpened(@NonNull CameraDevice camera) {
            Camera2.this.mCamera = camera;
            Camera2.this.mCallback.onCameraOpened();
            Camera2.this.startCaptureSession();
        }

        public void onClosed(@NonNull CameraDevice camera) {
            Camera2.this.mCallback.onCameraClosed();
        }

        public void onDisconnected(@NonNull CameraDevice camera) {
            Camera2.this.mCamera = null;
        }

        public void onError(@NonNull CameraDevice camera, int error) {
            Log.e(Camera2.TAG, "onError: " + camera.getId() + " (" + error + ")");
            Camera2.this.mCamera = null;
        }
    };
    private String mCameraId;
    private final CameraManager mCameraManager;
    PictureCaptureCallback mCaptureCallback = new PictureCaptureCallback() {
        public void onPrecaptureRequired() {
            Camera2.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, Integer.valueOf(1));
            setState(3);
            try {
                Camera2.this.mCaptureSession.capture(Camera2.this.mPreviewRequestBuilder.build(), this, null);
                Camera2.this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_PRECAPTURE_TRIGGER, Integer.valueOf(0));
            } catch (CameraAccessException e) {
                Log.e(Camera2.TAG, "Failed to run precapture sequence.", e);
            }
        }

        public void onReady() {
            Camera2.this.captureStillPicture();
        }
    };
    CameraCaptureSession mCaptureSession;
    private int mDisplayOrientation;
    private int mFacing;
    private int mFlash;
    private ImageReader mImageReader;
    private final OnImageAvailableListener mOnImageAvailableListener = new OnImageAvailableListener() {
        public void onImageAvailable(ImageReader reader) {
            Image image = reader.acquireNextImage();
            Throwable th = null;
            try {
                Plane[] planes = image.getPlanes();
                if (planes.length > 0) {
                    ByteBuffer buffer = planes[0].getBuffer();
                    byte[] data = new byte[buffer.remaining()];
                    buffer.get(data);
                    Camera2.this.mCallback.onPictureTaken(data);
                }
                if (image == null) {
                    return;
                }
                if (0 != 0) {
                    try {
                        image.close();
                        return;
                    } catch (Throwable th2) {
                        ThrowableExtension.addSuppressed(null, th2);
                        return;
                    }
                } else {
                    image.close();
                    return;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                th = r4;
                th = th4;
            }
            throw th;
            if (image != null) {
                if (th != null) {
                    try {
                        image.close();
                    } catch (Throwable th5) {
                        ThrowableExtension.addSuppressed(th, th5);
                    }
                } else {
                    image.close();
                }
            }
            throw th;
        }
    };
    private final SizeMap mPictureSizes = new SizeMap();
    Builder mPreviewRequestBuilder;
    private final SizeMap mPreviewSizes = new SizeMap();
    private final CameraCaptureSession.StateCallback mSessionCallback = new CameraCaptureSession.StateCallback() {
        public void onConfigured(@NonNull CameraCaptureSession session) {
            if (Camera2.this.mCamera != null) {
                Camera2.this.mCaptureSession = session;
                Camera2.this.updateAutoFocus();
                Camera2.this.updateFlash();
                try {
                    if (Camera2.this.mCaptureSession != null) {
                        Camera2.this.mCaptureSession.setRepeatingRequest(Camera2.this.mPreviewRequestBuilder.build(), Camera2.this.mCaptureCallback, null);
                    }
                } catch (CameraAccessException e) {
                    Log.e(Camera2.TAG, "Failed to start camera preview because it couldn't access camera", e);
                } catch (IllegalStateException e2) {
                    Log.e(Camera2.TAG, "Failed to start camera preview.", e2);
                } catch (Exception e3) {
                    Log.e(Camera2.TAG, "Failed to start camera preview.", e3);
                }
            }
        }

        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
            Log.e(Camera2.TAG, "Failed to configure capture session.");
        }

        public void onClosed(@NonNull CameraCaptureSession session) {
            if (Camera2.this.mCaptureSession != null && Camera2.this.mCaptureSession.equals(session)) {
                Camera2.this.mCaptureSession = null;
            }
        }
    };

    private static abstract class PictureCaptureCallback extends CaptureCallback {
        static final int STATE_CAPTURING = 5;
        static final int STATE_LOCKED = 2;
        static final int STATE_LOCKING = 1;
        static final int STATE_PRECAPTURE = 3;
        static final int STATE_PREVIEW = 0;
        static final int STATE_WAITING = 4;
        private int mState;

        public abstract void onPrecaptureRequired();

        public abstract void onReady();

        PictureCaptureCallback() {
        }

        /* access modifiers changed from: 0000 */
        public void setState(int state) {
            this.mState = state;
        }

        public void onCaptureProgressed(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull CaptureResult partialResult) {
            process(partialResult);
        }

        public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
            process(result);
        }

        private void process(@NonNull CaptureResult result) {
            switch (this.mState) {
                case 1:
                    Integer af = (Integer) result.get(CaptureResult.CONTROL_AF_STATE);
                    if (af == null) {
                        return;
                    }
                    if (af.intValue() == 4 || af.intValue() == 5) {
                        Integer ae = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
                        if (ae == null || ae.intValue() == 2) {
                            setState(5);
                            onReady();
                            return;
                        }
                        setState(2);
                        onPrecaptureRequired();
                        return;
                    }
                    return;
                case 3:
                    Integer ae2 = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
                    if (ae2 == null || ae2.intValue() == 5 || ae2.intValue() == 4 || ae2.intValue() == 2) {
                        setState(4);
                        return;
                    }
                    return;
                case 4:
                    Integer ae3 = (Integer) result.get(CaptureResult.CONTROL_AE_STATE);
                    if (ae3 == null || ae3.intValue() != 5) {
                        setState(5);
                        onReady();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    static {
        INTERNAL_FACINGS.put(0, 1);
        INTERNAL_FACINGS.put(1, 0);
    }

    Camera2(Callback callback, PreviewImpl preview, Context context) {
        super(callback, preview);
        this.mCameraManager = (CameraManager) context.getSystemService("camera");
        this.mPreview.setCallback(new Callback() {
            public void onSurfaceChanged() {
                Camera2.this.startCaptureSession();
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public boolean start() {
        if (!chooseCameraIdByFacing()) {
            return false;
        }
        collectCameraInfo();
        prepareImageReader();
        startOpeningCamera();
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void stop() {
        if (this.mCaptureSession != null) {
            this.mCaptureSession.close();
            this.mCaptureSession = null;
        }
        if (this.mCamera != null) {
            this.mCamera.close();
            this.mCamera = null;
        }
        if (this.mImageReader != null) {
            this.mImageReader.close();
            this.mImageReader = null;
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
        return this.mPreviewSizes.ratios();
    }

    /* access modifiers changed from: 0000 */
    public boolean setAspectRatio(AspectRatio ratio) {
        if (ratio == null || ratio.equals(this.mAspectRatio) || !this.mPreviewSizes.ratios().contains(ratio)) {
            return false;
        }
        this.mAspectRatio = ratio;
        prepareImageReader();
        if (this.mCaptureSession != null) {
            this.mCaptureSession.close();
            this.mCaptureSession = null;
            startCaptureSession();
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public AspectRatio getAspectRatio() {
        return this.mAspectRatio;
    }

    /* access modifiers changed from: 0000 */
    public void setAutoFocus(boolean autoFocus) {
        if (this.mAutoFocus != autoFocus) {
            this.mAutoFocus = autoFocus;
            if (this.mPreviewRequestBuilder != null) {
                updateAutoFocus();
                if (this.mCaptureSession != null) {
                    try {
                        if (this.mCaptureSession != null) {
                            this.mCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
                        }
                    } catch (CameraAccessException e) {
                        this.mAutoFocus = !this.mAutoFocus;
                    } catch (Exception e2) {
                        ThrowableExtension.printStackTrace(e2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean getAutoFocus() {
        return this.mAutoFocus;
    }

    /* access modifiers changed from: 0000 */
    public void setFlash(int flash) {
        if (this.mFlash != flash) {
            int saved = this.mFlash;
            this.mFlash = flash;
            if (this.mPreviewRequestBuilder != null) {
                updateFlash();
                if (this.mCaptureSession != null) {
                    try {
                        this.mCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
                    } catch (CameraAccessException e) {
                        this.mFlash = saved;
                    } catch (Exception e2) {
                        ThrowableExtension.printStackTrace(e2);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int getFlash() {
        return this.mFlash;
    }

    /* access modifiers changed from: 0000 */
    public void takePicture() {
        if (this.mAutoFocus) {
            lockFocus();
        } else {
            captureStillPicture();
        }
    }

    /* access modifiers changed from: 0000 */
    public void setDisplayOrientation(int displayOrientation) {
        this.mDisplayOrientation = displayOrientation;
        this.mPreview.setDisplayOrientation(this.mDisplayOrientation);
    }

    private boolean chooseCameraIdByFacing() {
        try {
            int internalFacing = INTERNAL_FACINGS.get(this.mFacing);
            String[] ids = this.mCameraManager.getCameraIdList();
            if (ids.length == 0) {
                throw new RuntimeException("No camera available.");
            }
            for (String id : ids) {
                CameraCharacteristics characteristics = this.mCameraManager.getCameraCharacteristics(id);
                Integer level = (Integer) characteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
                if (!(level == null || level.intValue() == 2)) {
                    Integer internal = (Integer) characteristics.get(CameraCharacteristics.LENS_FACING);
                    if (internal == null) {
                        throw new NullPointerException("Unexpected state: LENS_FACING null");
                    } else if (internal.intValue() == internalFacing) {
                        this.mCameraId = id;
                        this.mCameraCharacteristics = characteristics;
                        return true;
                    }
                }
            }
            this.mCameraId = ids[0];
            this.mCameraCharacteristics = this.mCameraManager.getCameraCharacteristics(this.mCameraId);
            Integer level2 = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
            if (level2 == null || level2.intValue() == 2) {
                return false;
            }
            Integer internal2 = (Integer) this.mCameraCharacteristics.get(CameraCharacteristics.LENS_FACING);
            if (internal2 == null) {
                throw new NullPointerException("Unexpected state: LENS_FACING null");
            }
            int count = INTERNAL_FACINGS.size();
            for (int i = 0; i < count; i++) {
                if (INTERNAL_FACINGS.valueAt(i) == internal2.intValue()) {
                    this.mFacing = INTERNAL_FACINGS.keyAt(i);
                    return true;
                }
            }
            this.mFacing = 0;
            return true;
        } catch (CameraAccessException e) {
            throw new RuntimeException("Failed to get a list of camera devices", e);
        }
    }

    private void collectCameraInfo() {
        Size[] outputSizes;
        StreamConfigurationMap map = (StreamConfigurationMap) this.mCameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (map == null) {
            throw new IllegalStateException("Failed to get configuration map: " + this.mCameraId);
        }
        this.mPreviewSizes.clear();
        for (Size size : map.getOutputSizes(this.mPreview.getOutputClass())) {
            int width = size.getWidth();
            int height = size.getHeight();
            if (width <= MAX_PREVIEW_WIDTH && height <= MAX_PREVIEW_HEIGHT) {
                this.mPreviewSizes.add(new Size(width, height));
            }
        }
        this.mPictureSizes.clear();
        collectPictureSizes(this.mPictureSizes, map);
        for (AspectRatio ratio : this.mPreviewSizes.ratios()) {
            if (!this.mPictureSizes.ratios().contains(ratio)) {
                this.mPreviewSizes.remove(ratio);
            }
        }
        if (!this.mPreviewSizes.ratios().contains(this.mAspectRatio)) {
            this.mAspectRatio = (AspectRatio) this.mPreviewSizes.ratios().iterator().next();
        }
    }

    /* access modifiers changed from: protected */
    public void collectPictureSizes(SizeMap sizes, StreamConfigurationMap map) {
        Size[] outputSizes;
        for (Size size : map.getOutputSizes(256)) {
            this.mPictureSizes.add(new Size(size.getWidth(), size.getHeight()));
        }
    }

    private void prepareImageReader() {
        if (this.mImageReader != null) {
            this.mImageReader.close();
        }
        Size largest = (Size) this.mPictureSizes.sizes(this.mAspectRatio).last();
        this.mImageReader = ImageReader.newInstance(largest.getWidth(), largest.getHeight(), 256, 2);
        this.mImageReader.setOnImageAvailableListener(this.mOnImageAvailableListener, null);
    }

    private void startOpeningCamera() {
        try {
            this.mCameraManager.openCamera(this.mCameraId, this.mCameraDeviceCallback, null);
        } catch (CameraAccessException e) {
            throw new RuntimeException("Failed to open camera: " + this.mCameraId, e);
        }
    }

    /* access modifiers changed from: 0000 */
    public void startCaptureSession() {
        if (isCameraOpened() && this.mPreview.isReady() && this.mImageReader != null) {
            Size previewSize = chooseOptimalSize();
            this.mPreview.setBufferSize(previewSize.getWidth(), previewSize.getHeight());
            Surface surface = this.mPreview.getSurface();
            try {
                this.mPreviewRequestBuilder = this.mCamera.createCaptureRequest(1);
                this.mPreviewRequestBuilder.addTarget(surface);
                this.mCamera.createCaptureSession(Arrays.asList(new Surface[]{surface, this.mImageReader.getSurface()}), this.mSessionCallback, null);
            } catch (CameraAccessException e) {
                throw new RuntimeException("Failed to start camera session");
            }
        }
    }

    private Size chooseOptimalSize() {
        int surfaceLonger;
        int surfaceShorter;
        int surfaceWidth = this.mPreview.getWidth();
        int surfaceHeight = this.mPreview.getHeight();
        if (surfaceWidth < surfaceHeight) {
            surfaceLonger = surfaceHeight;
            surfaceShorter = surfaceWidth;
        } else {
            surfaceLonger = surfaceWidth;
            surfaceShorter = surfaceHeight;
        }
        SortedSet<Size> candidates = this.mPreviewSizes.sizes(this.mAspectRatio);
        for (Size size : candidates) {
            if (size.getWidth() >= surfaceLonger && size.getHeight() >= surfaceShorter) {
                return size;
            }
        }
        return (Size) candidates.last();
    }

    /* access modifiers changed from: 0000 */
    public void updateAutoFocus() {
        if (this.mAutoFocus) {
            int[] modes = (int[]) this.mCameraCharacteristics.get(CameraCharacteristics.CONTROL_AF_AVAILABLE_MODES);
            if (modes == null || modes.length == 0 || (modes.length == 1 && modes[0] == 0)) {
                this.mAutoFocus = false;
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(0));
                return;
            }
            this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(4));
            return;
        }
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, Integer.valueOf(0));
    }

    /* access modifiers changed from: 0000 */
    public void updateFlash() {
        switch (this.mFlash) {
            case 0:
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
                this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
                return;
            case 1:
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(3));
                this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
                return;
            case 2:
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
                this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(2));
                return;
            case 3:
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(2));
                this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
                return;
            case 4:
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(4));
                this.mPreviewRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
                return;
            default:
                return;
        }
    }

    private void lockFocus() {
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(1));
        try {
            this.mCaptureCallback.setState(1);
            this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
        } catch (CameraAccessException e) {
            Log.e(TAG, "Failed to lock focus.", e);
        }
    }

    /* access modifiers changed from: 0000 */
    public void captureStillPicture() {
        try {
            Builder captureRequestBuilder = this.mCamera.createCaptureRequest(2);
            captureRequestBuilder.addTarget(this.mImageReader.getSurface());
            captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, this.mPreviewRequestBuilder.get(CaptureRequest.CONTROL_AF_MODE));
            switch (this.mFlash) {
                case 0:
                    captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
                    captureRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(0));
                    break;
                case 1:
                    captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(3));
                    break;
                case 2:
                    captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(1));
                    captureRequestBuilder.set(CaptureRequest.FLASH_MODE, Integer.valueOf(2));
                    break;
                case 3:
                    captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(2));
                    break;
                case 4:
                    captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, Integer.valueOf(2));
                    break;
            }
            int sensorOrientation = ((Integer) this.mCameraCharacteristics.get(CameraCharacteristics.SENSOR_ORIENTATION)).intValue();
            captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, Integer.valueOf(((((this.mFacing == 1 ? 1 : -1) * this.mDisplayOrientation) + sensorOrientation) + 360) % 360));
            this.mCaptureSession.stopRepeating();
            this.mCaptureSession.capture(captureRequestBuilder.build(), new CaptureCallback() {
                public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                    Camera2.this.unlockFocus();
                }
            }, null);
        } catch (CameraAccessException e) {
            Log.e(TAG, "Cannot capture a still picture.", e);
        }
    }

    /* access modifiers changed from: 0000 */
    public void unlockFocus() {
        this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(2));
        try {
            if (this.mCaptureSession != null) {
                this.mCaptureSession.capture(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
                updateAutoFocus();
                updateFlash();
                this.mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, Integer.valueOf(0));
                this.mCaptureSession.setRepeatingRequest(this.mPreviewRequestBuilder.build(), this.mCaptureCallback, null);
                this.mCaptureCallback.setState(0);
            }
        } catch (CameraAccessException e) {
            Log.e(TAG, "Failed to restart camera preview.", e);
        } catch (Exception e2) {
            ThrowableExtension.printStackTrace(e2);
        }
    }
}
