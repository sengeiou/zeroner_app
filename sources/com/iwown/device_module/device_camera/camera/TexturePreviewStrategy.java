package com.iwown.device_module.device_camera.camera;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Build.VERSION;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import java.io.IOException;

@TargetApi(14)
class TexturePreviewStrategy implements PreviewStrategy, SurfaceTextureListener {
    private final CameraView cameraView;
    private SurfaceTexture surface = null;
    private TextureView widget = null;

    TexturePreviewStrategy(CameraView cameraView2) {
        this.cameraView = cameraView2;
        this.widget = new TextureView(cameraView2.getContext());
        this.widget.setSurfaceTextureListener(this);
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface2, int width, int height) {
        this.surface = surface2;
        this.cameraView.previewCreated();
        this.cameraView.initPreview(width, height);
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface2, int width, int height) {
        this.cameraView.previewReset(width, height);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface2) {
        this.cameraView.previewDestroyed();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface2) {
    }

    public void attach(Camera camera) throws IOException {
        camera.setPreviewTexture(this.surface);
    }

    public void attach(MediaRecorder recorder) {
        if (VERSION.SDK_INT < 16) {
            throw new IllegalStateException("Cannot use TextureView with MediaRecorder");
        }
    }

    public View getWidget() {
        return this.widget;
    }
}
