package com.iwown.device_module.device_camera.camera;

import android.graphics.Bitmap;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;

public interface CameraHost extends AutoFocusCallback {

    public enum FailureReason {
        NO_CAMERAS_REPORTED(1),
        UNKNOWN(2);
        
        int value;

        private FailureReason(int value2) {
            this.value = value2;
        }
    }

    public enum RecordingHint {
        STILL_ONLY,
        VIDEO_ONLY,
        ANY,
        RecordingHint,
        NONE
    }

    Parameters adjustPictureParameters(PictureTransaction pictureTransaction, Parameters parameters);

    Parameters adjustPreviewParameters(Parameters parameters);

    void autoFocusAvailable();

    void autoFocusUnavailable();

    void configureRecorderAudio(int i, MediaRecorder mediaRecorder);

    void configureRecorderOutput(int i, MediaRecorder mediaRecorder);

    void configureRecorderProfile(int i, MediaRecorder mediaRecorder);

    int getCameraId();

    DeviceProfile getDeviceProfile();

    Size getPictureSize(PictureTransaction pictureTransaction, Parameters parameters);

    Size getPreferredPreviewSizeForVideo(int i, int i2, int i3, Parameters parameters, Size size);

    Size getPreviewSize(int i, int i2, int i3, Parameters parameters);

    RecordingHint getRecordingHint();

    ShutterCallback getShutterCallback();

    void handleException(Exception exc);

    float maxPictureCleanupHeapUsage();

    boolean mirrorFFC();

    void onCameraFail(FailureReason failureReason);

    void saveImage(PictureTransaction pictureTransaction, Bitmap bitmap);

    void saveImage(PictureTransaction pictureTransaction, byte[] bArr);

    boolean useFullBleedPreview();

    boolean useSingleShotMode();
}
