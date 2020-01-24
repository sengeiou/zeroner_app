package com.iwown.sport_module.zxing.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import java.io.IOException;

public final class CameraManager {
    private static final int MAX_FRAME_HEIGHT = 360;
    private static final int MAX_FRAME_WIDTH = 480;
    private static final int MIN_FRAME_HEIGHT = 240;
    private static final int MIN_FRAME_WIDTH = 240;
    static final int SDK_INT;
    private static final String TAG = CameraManager.class.getSimpleName();
    private static CameraManager cameraManager;
    private final AutoFocusCallback autoFocusCallback;
    private Camera camera;
    private final CameraConfigurationManager configManager;
    private final Context context;
    private Rect framingRect;
    private Rect framingRectInPreview;
    private boolean initialized;
    private Parameters parameter;
    private final PreviewCallback previewCallback;
    private boolean previewing;
    private final boolean useOneShotPreviewCallback;

    static {
        int sdkInt;
        try {
            sdkInt = Integer.parseInt(VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkInt = 10000;
        }
        SDK_INT = sdkInt;
    }

    public static void init(Context context2) {
        if (cameraManager == null) {
            cameraManager = new CameraManager(context2);
        }
    }

    public static CameraManager get() {
        return cameraManager;
    }

    private CameraManager(Context context2) {
        this.context = context2;
        this.configManager = new CameraConfigurationManager(context2);
        this.useOneShotPreviewCallback = Integer.parseInt(VERSION.SDK) > 3;
        this.previewCallback = new PreviewCallback(this.configManager, this.useOneShotPreviewCallback);
        this.autoFocusCallback = new AutoFocusCallback();
    }

    public void openDriver(SurfaceHolder holder) throws IOException {
        if (this.camera == null) {
            this.camera = Camera.open();
            if (this.camera == null) {
                throw new IOException();
            }
            this.camera.setPreviewDisplay(holder);
            if (!this.initialized) {
                this.initialized = true;
                this.configManager.initFromCameraParameters(this.camera);
            }
            this.configManager.setDesiredCameraParameters(this.camera);
        }
    }

    public boolean switchLight(boolean open) {
        this.parameter = this.camera.getParameters();
        if (open) {
            this.parameter.setFlashMode("torch");
            this.camera.setParameters(this.parameter);
            return true;
        }
        this.parameter.setFlashMode("off");
        this.camera.setParameters(this.parameter);
        return false;
    }

    public void closeDriver() {
        if (this.camera != null) {
            this.camera.release();
            this.camera = null;
        }
    }

    public void startPreview() {
        if (this.camera != null && !this.previewing) {
            this.camera.startPreview();
            this.previewing = true;
        }
    }

    public void stopPreview() {
        if (this.camera != null && this.previewing) {
            if (!this.useOneShotPreviewCallback) {
                this.camera.setPreviewCallback(null);
            }
            this.camera.stopPreview();
            this.previewCallback.setHandler(null, 0);
            this.autoFocusCallback.setHandler(null, 0);
            this.previewing = false;
        }
    }

    public void requestPreviewFrame(Handler handler, int message) {
        if (this.camera != null && this.previewing) {
            this.previewCallback.setHandler(handler, message);
            if (this.useOneShotPreviewCallback) {
                this.camera.setOneShotPreviewCallback(this.previewCallback);
            } else {
                this.camera.setPreviewCallback(this.previewCallback);
            }
        }
    }

    public void requestAutoFocus(Handler handler, int message) {
        if (this.camera != null && this.previewing) {
            this.autoFocusCallback.setHandler(handler, message);
            this.camera.autoFocus(this.autoFocusCallback);
        }
    }

    public Rect getFramingRect() {
        Point screenResolution = this.configManager.getScreenResolution();
        if (this.framingRect == null) {
            if (this.camera == null) {
                return null;
            }
            int width = (int) (((double) this.context.getResources().getDisplayMetrics().widthPixels) * 0.5d);
            int height = (int) (((double) width) * 1.06d);
            int leftOffset = (screenResolution.x - width) / 2;
            int topOffset = (screenResolution.y - height) / 4;
            this.framingRect = new Rect(leftOffset, topOffset, leftOffset + width, topOffset + height);
            Log.d(TAG, "Calculated framing rect: " + this.framingRect);
        }
        return this.framingRect;
    }

    public Rect getFramingRectInPreview() {
        if (this.framingRectInPreview == null) {
            Rect rect = new Rect(getFramingRect());
            Point cameraResolution = this.configManager.getCameraResolution();
            Point screenResolution = this.configManager.getScreenResolution();
            rect.left = (rect.left * cameraResolution.y) / screenResolution.x;
            rect.right = (rect.right * cameraResolution.y) / screenResolution.x;
            rect.top = (rect.top * cameraResolution.x) / screenResolution.y;
            rect.bottom = (rect.bottom * cameraResolution.x) / screenResolution.y;
            this.framingRectInPreview = rect;
        }
        return this.framingRectInPreview;
    }

    public PlanarYUVLuminanceSource buildLuminanceSource(byte[] data, int width, int height) {
        Rect rect = getFramingRectInPreview();
        int previewFormat = this.configManager.getPreviewFormat();
        String previewFormatString = this.configManager.getPreviewFormatString();
        switch (previewFormat) {
            case 16:
            case 17:
                return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height());
            default:
                if ("yuv420p".equals(previewFormatString)) {
                    return new PlanarYUVLuminanceSource(data, width, height, rect.left, rect.top, rect.width(), rect.height());
                }
                throw new IllegalArgumentException("Unsupported picture format: " + previewFormat + '/' + previewFormatString);
        }
    }

    public Context getContext() {
        return this.context;
    }
}
