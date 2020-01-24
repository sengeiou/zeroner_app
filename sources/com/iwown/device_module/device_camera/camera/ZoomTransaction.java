package com.iwown.device_module.device_camera.camera;

import android.hardware.Camera;
import android.hardware.Camera.OnZoomChangeListener;
import android.hardware.Camera.Parameters;

public final class ZoomTransaction implements OnZoomChangeListener {
    private Camera camera;
    private int level;
    private OnZoomChangeListener onChange = null;
    private Runnable onComplete = null;

    ZoomTransaction(Camera camera2, int level2) {
        this.camera = camera2;
        this.level = level2;
    }

    public ZoomTransaction onComplete(Runnable onComplete2) {
        this.onComplete = onComplete2;
        return this;
    }

    public ZoomTransaction onChange(OnZoomChangeListener onChange2) {
        this.onChange = onChange2;
        return this;
    }

    public void go() {
        Parameters params = this.camera.getParameters();
        if (params.isSmoothZoomSupported()) {
            this.camera.setZoomChangeListener(this);
            this.camera.startSmoothZoom(this.level);
            return;
        }
        params.setZoom(this.level);
        this.camera.setParameters(params);
        onZoomChange(this.level, true, this.camera);
    }

    public void cancel() {
        this.camera.stopSmoothZoom();
    }

    public void onZoomChange(int zoomValue, boolean stopped, Camera camera2) {
        if (this.onChange != null) {
            this.onChange.onZoomChange(zoomValue, stopped, camera2);
        }
        if (stopped && this.onComplete != null) {
            this.onComplete.run();
        }
    }
}
