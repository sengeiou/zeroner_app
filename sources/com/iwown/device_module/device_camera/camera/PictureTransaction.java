package com.iwown.device_module.device_camera.camera;

import android.hardware.Camera.ShutterCallback;

public class PictureTransaction implements ShutterCallback {
    CameraView cameraView = null;
    int displayOrientation = 0;
    String flashMode = null;
    CameraHost host = null;
    boolean mirrorFFC = false;
    boolean needBitmap = false;
    boolean needByteArray = true;
    private Object tag = null;
    boolean useSingleShotMode = false;

    public PictureTransaction(CameraHost host2) {
        this.host = host2;
    }

    public PictureTransaction needBitmap(boolean needBitmap2) {
        this.needBitmap = needBitmap2;
        return this;
    }

    public PictureTransaction needByteArray(boolean needByteArray2) {
        this.needByteArray = needByteArray2;
        return this;
    }

    public Object getTag() {
        return this.tag;
    }

    public PictureTransaction tag(Object tag2) {
        this.tag = tag2;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public boolean useSingleShotMode() {
        return this.useSingleShotMode || this.host.useSingleShotMode();
    }

    /* access modifiers changed from: 0000 */
    public boolean mirrorFFC() {
        return this.mirrorFFC || this.host.mirrorFFC();
    }

    public PictureTransaction useSingleShotMode(boolean useSingleShotMode2) {
        this.useSingleShotMode = useSingleShotMode2;
        return this;
    }

    public PictureTransaction mirrorFFC(boolean mirrorFFC2) {
        this.mirrorFFC = mirrorFFC2;
        return this;
    }

    public PictureTransaction flashMode(String flashMode2) {
        this.flashMode = flashMode2;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public PictureTransaction displayOrientation(int displayOrientation2) {
        this.displayOrientation = displayOrientation2;
        return this;
    }

    public void onShutter() {
        ShutterCallback cb = this.host.getShutterCallback();
        if (cb != null) {
            cb.onShutter();
        }
    }
}
