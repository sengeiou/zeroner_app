package com.iwown.device_module.device_camera.camera;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import java.io.IOException;

class SurfacePreviewStrategy implements PreviewStrategy, Callback {
    private final CameraView cameraView;
    private SurfaceView preview = null;
    private SurfaceHolder previewHolder = null;

    SurfacePreviewStrategy(CameraView cameraView2) {
        this.cameraView = cameraView2;
        this.preview = new SurfaceView(cameraView2.getContext());
        this.previewHolder = this.preview.getHolder();
        this.previewHolder.setType(3);
        this.previewHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.cameraView.previewCreated();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.cameraView.initPreview(width, height);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        holder.removeCallback(this);
        this.cameraView.previewDestroyed();
    }

    public void attach(Camera camera) throws IOException {
        camera.setPreviewDisplay(this.previewHolder);
    }

    public void attach(MediaRecorder recorder) {
        recorder.setPreviewDisplay(this.previewHolder.getSurface());
    }

    public View getWidget() {
        return this.preview;
    }
}
