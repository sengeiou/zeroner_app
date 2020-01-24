package com.iwown.device_module.device_camera.camera;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.io.IOException;

@TargetApi(11)
public class CameraFragment extends Fragment {
    private CameraView cameraView = null;
    private CameraHost host = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.cameraView = new CameraView(getActivity());
        this.cameraView.setHost(getCameraHost());
        return this.cameraView;
    }

    public void onResume() {
        super.onResume();
        this.cameraView.onResume();
    }

    public void onPause() {
        if (isRecording()) {
            try {
                stopRecording();
            } catch (IOException e) {
                Log.e(getClass().getSimpleName(), "Exception stopping recording in onPause()", e);
            }
        }
        this.cameraView.onPause();
        super.onPause();
    }

    /* access modifiers changed from: protected */
    public void setCameraView(CameraView cameraView2) {
        this.cameraView = cameraView2;
    }

    public CameraHost getCameraHost() {
        if (this.host == null) {
            this.host = new SimpleCameraHost(getActivity());
        }
        return this.host;
    }

    public void setCameraHost(CameraHost host2) {
        this.host = host2;
    }

    public void takePicture() {
        takePicture(false, true);
    }

    public void takePicture(boolean needBitmap, boolean needByteArray) {
        this.cameraView.takePicture(needBitmap, needByteArray);
    }

    public void takePicture(PictureTransaction xact) {
        this.cameraView.takePicture(xact);
    }

    public boolean isRecording() {
        if (this.cameraView == null) {
            return false;
        }
        return this.cameraView.isRecording();
    }

    public void record() throws Exception {
        this.cameraView.record();
    }

    public void stopRecording() throws IOException {
        this.cameraView.stopRecording();
    }

    public int getDisplayOrientation() {
        return this.cameraView.getDisplayOrientation();
    }

    public void lockToLandscape(boolean enable) {
        this.cameraView.lockToLandscape(enable);
    }

    public void autoFocus() {
        this.cameraView.autoFocus();
    }

    public void cancelAutoFocus() {
        this.cameraView.cancelAutoFocus();
    }

    public boolean isAutoFocusAvailable() {
        return this.cameraView.isAutoFocusAvailable();
    }

    public void restartPreview() {
        this.cameraView.restartPreview();
    }

    public String getFlashMode() {
        return this.cameraView.getFlashMode();
    }

    public ZoomTransaction zoomTo(int level) {
        return this.cameraView.zoomTo(level);
    }

    public void startFaceDetection() {
        this.cameraView.startFaceDetection();
    }

    public void stopFaceDetection() {
        this.cameraView.stopFaceDetection();
    }

    public boolean doesZoomReallyWork() {
        return this.cameraView.doesZoomReallyWork();
    }

    public void setFlashMode(String mode) {
        this.cameraView.setFlashMode(mode);
    }
}
