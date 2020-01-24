package com.iwown.device_module.device_camera.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.media.MediaActionSound;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.Log;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.device_camera.camera.CameraHost.FailureReason;
import com.iwown.device_module.device_camera.camera.CameraHost.RecordingHint;
import com.socks.library.KLog;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SimpleCameraHost implements CameraHost {
    private static final String[] SCAN_TYPES = {"image/jpeg"};
    /* access modifiers changed from: private */
    public int cameraId = -1;
    private Context ctxt = null;
    /* access modifiers changed from: private */
    public boolean mirrorFFC = false;
    /* access modifiers changed from: private */
    public File photoDirectory = null;
    /* access modifiers changed from: private */
    public DeviceProfile profile = null;
    /* access modifiers changed from: private */
    public RecordingHint recordingHint = null;
    /* access modifiers changed from: private */
    public boolean scanSavedImage = true;
    /* access modifiers changed from: private */
    public boolean useFrontFacingCamera = false;
    /* access modifiers changed from: private */
    public boolean useFullBleedPreview = true;
    /* access modifiers changed from: private */
    public boolean useSingleShotMode = false;
    /* access modifiers changed from: private */
    public File videoDirectory = null;

    public static class Builder {
        private SimpleCameraHost host;

        public Builder(Context ctxt) {
            this(new SimpleCameraHost(ctxt));
        }

        public Builder(SimpleCameraHost host2) {
            this.host = null;
            this.host = host2;
        }

        public SimpleCameraHost build() {
            return this.host;
        }

        public Builder cameraId(int cameraId) {
            this.host.cameraId = cameraId;
            return this;
        }

        public Builder deviceProfile(DeviceProfile profile) {
            this.host.profile = profile;
            return this;
        }

        public Builder mirrorFFC(boolean mirrorFFC) {
            this.host.mirrorFFC = mirrorFFC;
            return this;
        }

        public Builder photoDirectory(File photoDirectory) {
            this.host.photoDirectory = photoDirectory;
            return this;
        }

        public Builder recordingHint(RecordingHint recordingHint) {
            this.host.recordingHint = recordingHint;
            return this;
        }

        public Builder scanSavedImage(boolean scanSavedImage) {
            this.host.scanSavedImage = scanSavedImage;
            return this;
        }

        public Builder useFrontFacingCamera(boolean useFrontFacingCamera) {
            this.host.useFrontFacingCamera = useFrontFacingCamera;
            return this;
        }

        public Builder useFullBleedPreview(boolean useFullBleedPreview) {
            this.host.useFullBleedPreview = useFullBleedPreview;
            return this;
        }

        public Builder useSingleShotMode(boolean useSingleShotMode) {
            this.host.useSingleShotMode = useSingleShotMode;
            return this;
        }

        public Builder videoDirectory(File videoDirectory) {
            this.host.videoDirectory = videoDirectory;
            return this;
        }
    }

    public SimpleCameraHost(Context _ctxt) {
        this.ctxt = _ctxt.getApplicationContext();
    }

    public Parameters adjustPictureParameters(PictureTransaction xact, Parameters parameters) {
        return parameters;
    }

    public Parameters adjustPreviewParameters(Parameters parameters) {
        return parameters;
    }

    public void configureRecorderAudio(int cameraId2, MediaRecorder recorder) {
        recorder.setAudioSource(5);
    }

    public void configureRecorderOutput(int cameraId2, MediaRecorder recorder) {
        recorder.setOutputFile(getVideoPath().getAbsolutePath());
    }

    @TargetApi(11)
    public void configureRecorderProfile(int cameraId2, MediaRecorder recorder) {
        if (VERSION.SDK_INT < 11 || CamcorderProfile.hasProfile(cameraId2, 1)) {
            recorder.setProfile(CamcorderProfile.get(cameraId2, 1));
        } else if (VERSION.SDK_INT < 11 || !CamcorderProfile.hasProfile(cameraId2, 0)) {
            throw new IllegalStateException("cannot find valid CamcorderProfile");
        } else {
            recorder.setProfile(CamcorderProfile.get(cameraId2, 0));
        }
    }

    public int getCameraId() {
        if (this.cameraId == -1) {
            initCameraId();
        }
        return this.cameraId;
    }

    private void initCameraId() {
        int count = Camera.getNumberOfCameras();
        int result = -1;
        if (count > 0) {
            result = 0;
            CameraInfo info = new CameraInfo();
            KLog.i("==============================" + JsonUtils.toJson(info));
            int i = 0;
            while (true) {
                if (i >= count) {
                    break;
                }
                Camera.getCameraInfo(i, info);
                KLog.i("===========facing=============" + info.facing + "====" + useFrontFacingCamera());
                if (info.facing != 0 || useFrontFacingCamera()) {
                    if (info.facing == 1 && useFrontFacingCamera()) {
                        result = i;
                        break;
                    }
                    i++;
                } else {
                    result = i;
                    break;
                }
            }
        }
        this.cameraId = result;
    }

    public DeviceProfile getDeviceProfile() {
        if (this.profile == null) {
            initDeviceProfile(this.ctxt);
        }
        return this.profile;
    }

    private void initDeviceProfile(Context ctxt2) {
        this.profile = DeviceProfile.getInstance(ctxt2);
    }

    public Size getPictureSize(PictureTransaction xact, Parameters parameters) {
        return CameraUtils.getLargestPictureSize(this, parameters);
    }

    public Size getPreviewSize(int displayOrientation, int width, int height, Parameters parameters) {
        return CameraUtils.getBestAspectPreviewSize(displayOrientation, width, height, parameters);
    }

    @TargetApi(11)
    public Size getPreferredPreviewSizeForVideo(int displayOrientation, int width, int height, Parameters parameters, Size deviceHint) {
        if (deviceHint != null) {
            return deviceHint;
        }
        if (VERSION.SDK_INT >= 11) {
            return parameters.getPreferredPreviewSizeForVideo();
        }
        return null;
    }

    public ShutterCallback getShutterCallback() {
        return null;
    }

    public void handleException(Exception e) {
        Log.e(getClass().getSimpleName(), "Exception in setPreviewDisplay()", e);
    }

    public boolean mirrorFFC() {
        return this.mirrorFFC;
    }

    public void saveImage(PictureTransaction xact, Bitmap bitmap) {
    }

    public void saveImage(PictureTransaction xact, byte[] image) {
        File photo = getPhotoPath();
        if (photo.exists()) {
            photo.delete();
        }
        try {
            FileOutputStream fos = new FileOutputStream(photo.getPath());
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(image);
            bos.flush();
            fos.getFD().sync();
            bos.close();
            if (scanSavedImage()) {
                MediaScannerConnection.scanFile(this.ctxt, new String[]{photo.getPath()}, SCAN_TYPES, null);
            }
        } catch (IOException e) {
            handleException(e);
        }
    }

    @TargetApi(16)
    public void onAutoFocus(boolean success, Camera camera) {
        if (success && VERSION.SDK_INT >= 16) {
            new MediaActionSound().play(1);
        }
    }

    public boolean useSingleShotMode() {
        return this.useSingleShotMode;
    }

    public void autoFocusAvailable() {
    }

    public void autoFocusUnavailable() {
    }

    public RecordingHint getRecordingHint() {
        if (this.recordingHint == null) {
            initRecordingHint();
        }
        return this.recordingHint;
    }

    private void initRecordingHint() {
        this.recordingHint = this.profile.getDefaultRecordingHint();
        if (this.recordingHint == RecordingHint.NONE) {
            this.recordingHint = RecordingHint.ANY;
        }
    }

    public void onCameraFail(FailureReason reason) {
        Log.e("CWAC-Camera", String.format("Camera access failed: %d", new Object[]{Integer.valueOf(reason.value)}));
    }

    public boolean useFullBleedPreview() {
        return this.useFullBleedPreview;
    }

    public float maxPictureCleanupHeapUsage() {
        return 1.0f;
    }

    /* access modifiers changed from: protected */
    public File getPhotoPath() {
        File dir = getPhotoDirectory();
        dir.mkdirs();
        return new File(dir, getPhotoFilename());
    }

    /* access modifiers changed from: protected */
    public File getPhotoDirectory() {
        if (this.photoDirectory == null) {
            initPhotoDirectory();
        }
        return this.photoDirectory;
    }

    private void initPhotoDirectory() {
        this.photoDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }

    /* access modifiers changed from: protected */
    public String getPhotoFilename() {
        return "Photo_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".jpg";
    }

    /* access modifiers changed from: protected */
    public File getVideoPath() {
        File dir = getVideoDirectory();
        dir.mkdirs();
        return new File(dir, getVideoFilename());
    }

    /* access modifiers changed from: protected */
    public File getVideoDirectory() {
        if (this.videoDirectory == null) {
            initVideoDirectory();
        }
        return this.videoDirectory;
    }

    private void initVideoDirectory() {
        this.videoDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    }

    /* access modifiers changed from: protected */
    public String getVideoFilename() {
        return "Video_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date()) + ".mp4";
    }

    /* access modifiers changed from: protected */
    public boolean useFrontFacingCamera() {
        return this.useFrontFacingCamera;
    }

    /* access modifiers changed from: protected */
    public boolean scanSavedImage() {
        return this.scanSavedImage;
    }
}
