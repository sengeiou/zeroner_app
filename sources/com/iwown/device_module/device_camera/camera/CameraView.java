package com.iwown.device_module.device_camera.camera;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.hardware.camera2.CameraManager;
import android.media.MediaRecorder;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.device_camera.camera.CameraHost.FailureReason;
import com.iwown.device_module.device_camera.camera.CameraHost.RecordingHint;
import com.iwown.lib_common.permissions.PermissionsUtils;
import com.iwown.lib_common.permissions.PermissionsUtils.PermissinCallBack;
import com.socks.library.KLog;
import java.io.IOException;

public class CameraView extends ViewGroup implements AutoFocusCallback {
    static final String TAG = "CWAC-Camera";
    /* access modifiers changed from: private */
    public Camera camera;
    /* access modifiers changed from: private */
    public int cameraId;
    private Context context;
    private int displayOrientation;
    private CameraHost host;
    private boolean inPreview;
    private boolean isAutoFocusing;
    private boolean isDetectingFaces;
    /* access modifiers changed from: private */
    public int lastPictureOrientation;
    private CameraManager mCameraManager;
    /* access modifiers changed from: private */
    public OnOrientationChange onOrientationChange;
    /* access modifiers changed from: private */
    public int outputOrientation;
    /* access modifiers changed from: private */
    public Parameters previewParams;
    private Size previewSize;
    private PreviewStrategy previewStrategy;
    private MediaRecorder recorder;

    private class OnOrientationChange extends OrientationEventListener {
        private boolean isEnabled = false;

        public OnOrientationChange(Context context) {
            super(context);
            disable();
        }

        public void onOrientationChanged(int orientation) {
            if (CameraView.this.camera != null && orientation != -1) {
                int newOutputOrientation = CameraView.this.getCameraPictureRotation(orientation);
                if (newOutputOrientation != CameraView.this.outputOrientation) {
                    CameraView.this.outputOrientation = newOutputOrientation;
                    Parameters params = CameraView.this.camera.getParameters();
                    params.setRotation(CameraView.this.outputOrientation);
                    try {
                        CameraView.this.camera.setParameters(params);
                        CameraView.this.lastPictureOrientation = CameraView.this.outputOrientation;
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "Exception updating camera parameters in orientation change", e);
                    }
                }
            }
        }

        public void enable() {
            this.isEnabled = true;
            super.enable();
        }

        public void disable() {
            this.isEnabled = false;
            super.disable();
        }

        /* access modifiers changed from: 0000 */
        public boolean isEnabled() {
            return this.isEnabled;
        }
    }

    private class PictureTransactionCallback implements PictureCallback {
        PictureTransaction xact = null;

        PictureTransactionCallback(PictureTransaction xact2) {
            this.xact = xact2;
        }

        public void onPictureTaken(byte[] data, Camera camera) {
            camera.setParameters(CameraView.this.previewParams);
            if (data != null) {
                new ImageCleanupTask(CameraView.this.getContext(), data, CameraView.this.cameraId, this.xact).start();
            }
            if (!this.xact.useSingleShotMode()) {
                CameraView.this.startPreview();
            }
        }
    }

    public CameraView(Context context2) {
        super(context2);
        this.camera = null;
        this.inPreview = false;
        this.host = null;
        this.onOrientationChange = null;
        this.displayOrientation = -1;
        this.outputOrientation = -1;
        this.cameraId = -1;
        this.recorder = null;
        this.previewParams = null;
        this.isDetectingFaces = false;
        this.isAutoFocusing = false;
        this.lastPictureOrientation = -1;
        this.context = context2;
        this.onOrientationChange = new OnOrientationChange(context2.getApplicationContext());
        this.mCameraManager = (CameraManager) context2.getSystemService("camera");
    }

    public CameraView(Context context2, AttributeSet attrs) {
        this(context2, attrs, 0);
    }

    public CameraView(Context context2, AttributeSet attrs, int defStyle) {
        super(context2, attrs, defStyle);
        this.camera = null;
        this.inPreview = false;
        this.host = null;
        this.onOrientationChange = null;
        this.displayOrientation = -1;
        this.outputOrientation = -1;
        this.cameraId = -1;
        this.recorder = null;
        this.previewParams = null;
        this.isDetectingFaces = false;
        this.isAutoFocusing = false;
        this.lastPictureOrientation = -1;
        this.onOrientationChange = new OnOrientationChange(context2.getApplicationContext());
        if (context2 instanceof CameraHostProvider) {
            setHost(((CameraHostProvider) context2).getCameraHost());
            return;
        }
        throw new IllegalArgumentException("To use the two- or three-parameter constructors on CameraView, your activity needs to implement the CameraHostProvider interface");
    }

    public CameraHost getHost() {
        return this.host;
    }

    public void setHost(CameraHost host2) {
        this.host = host2;
        if (host2.getDeviceProfile().useTextureView()) {
            this.previewStrategy = new TexturePreviewStrategy(this);
        } else {
            this.previewStrategy = new SurfacePreviewStrategy(this);
        }
    }

    @TargetApi(14)
    public void onResume() {
        addView(this.previewStrategy.getWidget());
        if (this.camera == null) {
            try {
                PermissionsUtils.handleCAMER(getActivity(), new PermissinCallBack() {
                    public void callBackOk() {
                        CameraView.this.cameraId = CameraView.this.getHost().getCameraId();
                        KLog.i("========================cameraId:" + CameraView.this.cameraId);
                        if (CameraView.this.cameraId >= 0) {
                            CameraView.this.camera = Camera.open(CameraView.this.cameraId);
                            if (CameraView.this.getActivity().getRequestedOrientation() != -1) {
                                CameraView.this.onOrientationChange.enable();
                            }
                            CameraView.this.setCameraDisplayOrientation();
                            if (VERSION.SDK_INT >= 14 && (CameraView.this.getHost() instanceof FaceDetectionListener)) {
                                CameraView.this.camera.setFaceDetectionListener((FaceDetectionListener) CameraView.this.getHost());
                                return;
                            }
                            return;
                        }
                        CameraView.this.getHost().onCameraFail(FailureReason.NO_CAMERAS_REPORTED);
                    }

                    public void callBackFial() {
                    }
                });
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                getHost().onCameraFail(FailureReason.UNKNOWN);
            }
        }
    }

    public void onPause() {
        if (this.camera != null) {
            previewDestroyed();
        }
        removeView(this.previewStrategy.getWidget());
        this.onOrientationChange.disable();
        this.lastPictureOrientation = -1;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
        if (width > 0 && height > 0 && this.camera != null) {
            Size newSize = null;
            try {
                if (getHost().getRecordingHint() != RecordingHint.STILL_ONLY) {
                    newSize = getHost().getPreferredPreviewSizeForVideo(getDisplayOrientation(), width, height, this.camera.getParameters(), null);
                }
                if (newSize == null || newSize.width * newSize.height < 65536) {
                    newSize = getHost().getPreviewSize(getDisplayOrientation(), width, height, this.camera.getParameters());
                }
            } catch (Exception e) {
                Log.e(getClass().getSimpleName(), "Could not work with camera parameters?", e);
            }
            if (newSize == null) {
                return;
            }
            if (this.previewSize == null) {
                this.previewSize = newSize;
            } else if (this.previewSize.width != newSize.width || this.previewSize.height != newSize.height) {
                if (this.inPreview) {
                    stopPreview();
                }
                this.previewSize = newSize;
                initPreview(width, height, false);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed && getChildCount() > 0) {
            View child = getChildAt(0);
            int width = r - l;
            int height = b - t;
            int previewWidth = width;
            int previewHeight = height;
            if (this.previewSize != null) {
                if (getDisplayOrientation() == 90 || getDisplayOrientation() == 270) {
                    previewWidth = this.previewSize.height;
                    previewHeight = this.previewSize.width;
                } else {
                    previewWidth = this.previewSize.width;
                    previewHeight = this.previewSize.height;
                }
            }
            boolean useFirstStrategy = width * previewHeight > height * previewWidth;
            boolean useFullBleed = getHost().useFullBleedPreview();
            if ((!useFirstStrategy || useFullBleed) && (useFirstStrategy || !useFullBleed)) {
                int scaledChildHeight = (previewHeight * width) / previewWidth;
                child.layout(0, (height - scaledChildHeight) / 2, width, (height + scaledChildHeight) / 2);
                return;
            }
            int scaledChildWidth = (previewWidth * height) / previewHeight;
            child.layout((width - scaledChildWidth) / 2, 0, (width + scaledChildWidth) / 2, height);
        }
    }

    public int getDisplayOrientation() {
        return this.displayOrientation;
    }

    public void lockToLandscape(boolean enable) {
        if (enable) {
            getActivity().setRequestedOrientation(6);
            this.onOrientationChange.enable();
            return;
        }
        getActivity().setRequestedOrientation(-1);
        this.onOrientationChange.disable();
    }

    public void restartPreview() {
        if (!this.inPreview) {
            startPreview();
        }
    }

    public void takePicture(boolean needBitmap, boolean needByteArray) {
        takePicture(new PictureTransaction(getHost()).needBitmap(needBitmap).needByteArray(needByteArray));
    }

    public void takePicture(final PictureTransaction xact) {
        if (!this.inPreview) {
            throw new IllegalStateException("Preview mode must have started before you can take a picture");
        } else if (this.isAutoFocusing) {
            throw new IllegalStateException("Camera cannot take a picture while auto-focusing");
        } else {
            this.previewParams = this.camera.getParameters();
            Parameters pictureParams = this.camera.getParameters();
            Size pictureSize = xact.host.getPictureSize(xact, pictureParams);
            pictureParams.setPictureSize(pictureSize.width, pictureSize.height);
            pictureParams.setPictureFormat(256);
            if (xact.flashMode != null) {
                pictureParams.setFlashMode(xact.flashMode);
            }
            if (!this.onOrientationChange.isEnabled()) {
                setCameraPictureOrientation(pictureParams);
            }
            this.camera.setParameters(xact.host.adjustPictureParameters(xact, pictureParams));
            xact.cameraView = this;
            postDelayed(new Runnable() {
                public void run() {
                    try {
                        CameraView.this.camera.takePicture(xact, null, new PictureTransactionCallback(xact));
                    } catch (Exception e) {
                        Log.e(getClass().getSimpleName(), "Exception taking a picture", e);
                    }
                }
            }, (long) xact.host.getDeviceProfile().getPictureDelay());
            this.inPreview = false;
        }
    }

    public boolean isRecording() {
        return this.recorder != null;
    }

    public void record() throws Exception {
        if (VERSION.SDK_INT < 11) {
            throw new UnsupportedOperationException("Video recording supported only on API Level 11+");
        } else if (this.displayOrientation == 0 || this.displayOrientation == 180) {
            Parameters pictureParams = this.camera.getParameters();
            setCameraPictureOrientation(pictureParams);
            this.camera.setParameters(pictureParams);
            stopPreview();
            this.camera.unlock();
            try {
                this.recorder = new MediaRecorder();
                this.recorder.setCamera(this.camera);
                getHost().configureRecorderAudio(this.cameraId, this.recorder);
                this.recorder.setVideoSource(1);
                getHost().configureRecorderProfile(this.cameraId, this.recorder);
                getHost().configureRecorderOutput(this.cameraId, this.recorder);
                this.recorder.setOrientationHint(this.outputOrientation);
                this.previewStrategy.attach(this.recorder);
                this.recorder.prepare();
                this.recorder.start();
            } catch (IOException e) {
                this.recorder.release();
                this.recorder = null;
                throw e;
            }
        } else {
            throw new UnsupportedOperationException("Video recording supported only in landscape");
        }
    }

    public void stopRecording() throws IOException {
        if (VERSION.SDK_INT < 11) {
            throw new UnsupportedOperationException("Video recording supported only on API Level 11+");
        }
        MediaRecorder tempRecorder = this.recorder;
        this.recorder = null;
        tempRecorder.stop();
        tempRecorder.release();
        this.camera.reconnect();
        startPreview();
    }

    public void autoFocus() {
        if (this.inPreview) {
            this.camera.autoFocus(this);
            this.isAutoFocusing = true;
        }
    }

    public void cancelAutoFocus() {
        this.camera.cancelAutoFocus();
    }

    public boolean isAutoFocusAvailable() {
        return this.inPreview;
    }

    public void onAutoFocus(boolean success, Camera camera2) {
        this.isAutoFocusing = false;
        if (getHost() instanceof AutoFocusCallback) {
            getHost().onAutoFocus(success, camera2);
        }
    }

    public String getFlashMode() {
        return this.camera.getParameters().getFlashMode();
    }

    public void setFlashMode(String mode) {
        if (this.camera != null) {
            Parameters params = this.camera.getParameters();
            params.setFlashMode(mode);
            this.camera.setParameters(params);
        }
    }

    public ZoomTransaction zoomTo(int level) {
        if (this.camera == null) {
            throw new IllegalStateException("Yes, we have no camera, we have no camera today");
        }
        Parameters params = this.camera.getParameters();
        if (level >= 0 && level <= params.getMaxZoom()) {
            return new ZoomTransaction(this.camera, level);
        }
        throw new IllegalArgumentException(String.format("Invalid zoom level: %d", new Object[]{Integer.valueOf(level)}));
    }

    @TargetApi(14)
    public void startFaceDetection() {
        if (VERSION.SDK_INT >= 14 && this.camera != null && !this.isDetectingFaces && this.camera.getParameters().getMaxNumDetectedFaces() > 0) {
            this.camera.startFaceDetection();
            this.isDetectingFaces = true;
        }
    }

    @TargetApi(14)
    public void stopFaceDetection() {
        if (VERSION.SDK_INT >= 14 && this.camera != null && this.isDetectingFaces) {
            try {
                this.camera.stopFaceDetection();
            } catch (Exception e) {
            }
            this.isDetectingFaces = false;
        }
    }

    public boolean doesZoomReallyWork() {
        boolean z = true;
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(getHost().getCameraId(), info);
        DeviceProfile deviceProfile = getHost().getDeviceProfile();
        if (info.facing != 1) {
            z = false;
        }
        return deviceProfile.doesZoomActuallyWork(z);
    }

    /* access modifiers changed from: 0000 */
    public void previewCreated() {
        if (this.camera != null) {
            try {
                this.previewStrategy.attach(this.camera);
            } catch (IOException e) {
                getHost().handleException(e);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void previewDestroyed() {
        if (this.camera != null) {
            this.camera.setPreviewCallback(null);
            previewStopped();
            this.camera.release();
            this.camera = null;
        }
    }

    /* access modifiers changed from: 0000 */
    public void previewReset(int width, int height) {
        previewStopped();
        initPreview(width, height);
    }

    private void previewStopped() {
        if (this.inPreview) {
            stopPreview();
        }
    }

    public void initPreview(int w, int h) {
        initPreview(w, h, true);
    }

    @TargetApi(14)
    public void initPreview(int w, int h, boolean firstRun) {
        if (this.camera != null) {
            Parameters parameters = this.camera.getParameters();
            parameters.setPreviewSize(this.previewSize.width, this.previewSize.height);
            if (VERSION.SDK_INT >= 14) {
                parameters.setRecordingHint(getHost().getRecordingHint() != RecordingHint.STILL_ONLY);
            }
            requestLayout();
            this.camera.setParameters(getHost().adjustPreviewParameters(parameters));
            startPreview();
        }
    }

    /* access modifiers changed from: private */
    public void startPreview() {
        this.camera.startPreview();
        this.inPreview = true;
        getHost().autoFocusAvailable();
    }

    private void stopPreview() {
        this.inPreview = false;
        getHost().autoFocusUnavailable();
        this.camera.stopPreview();
    }

    /* access modifiers changed from: private */
    public void setCameraDisplayOrientation() {
        CameraInfo info = new CameraInfo();
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        DisplayMetrics dm = new DisplayMetrics();
        Camera.getCameraInfo(this.cameraId, info);
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        switch (rotation) {
            case 0:
                degrees = 0;
                break;
            case 1:
                degrees = 90;
                break;
            case 2:
                degrees = 180;
                break;
            case 3:
                degrees = Constants.LANDSCAPE_270;
                break;
        }
        if (info.facing == 1) {
            this.displayOrientation = (info.orientation + degrees) % 360;
            this.displayOrientation = (360 - this.displayOrientation) % 360;
        } else {
            this.displayOrientation = ((info.orientation - degrees) + 360) % 360;
        }
        boolean wasInPreview = this.inPreview;
        if (this.inPreview) {
            stopPreview();
        }
        this.camera.setDisplayOrientation(this.displayOrientation);
        if (wasInPreview) {
            startPreview();
        }
    }

    private void setCameraPictureOrientation(Parameters params) {
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(this.cameraId, info);
        if (getActivity().getRequestedOrientation() != -1) {
            this.outputOrientation = getCameraPictureRotation(getActivity().getWindowManager().getDefaultDisplay().getOrientation());
        } else if (info.facing == 1) {
            this.outputOrientation = (360 - this.displayOrientation) % 360;
        } else {
            this.outputOrientation = this.displayOrientation;
        }
        if (this.lastPictureOrientation != this.outputOrientation) {
            params.setRotation(this.outputOrientation);
            this.lastPictureOrientation = this.outputOrientation;
        }
    }

    /* access modifiers changed from: private */
    public int getCameraPictureRotation(int orientation) {
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(this.cameraId, info);
        int orientation2 = ((orientation + 45) / 90) * 90;
        if (info.facing == 1) {
            return ((info.orientation - orientation2) + 360) % 360;
        }
        return (info.orientation + orientation2) % 360;
    }

    /* access modifiers changed from: 0000 */
    public Activity getActivity() {
        return (Activity) getContext();
    }
}
