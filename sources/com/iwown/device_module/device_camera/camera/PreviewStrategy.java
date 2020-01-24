package com.iwown.device_module.device_camera.camera;

import android.hardware.Camera;
import android.media.MediaRecorder;
import android.view.View;
import java.io.IOException;

public interface PreviewStrategy {
    void attach(Camera camera) throws IOException;

    void attach(MediaRecorder mediaRecorder);

    View getWidget();
}
