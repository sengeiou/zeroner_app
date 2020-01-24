package com.google.android.cameraview;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;

abstract class PreviewImpl {
    private Callback mCallback;
    private int mHeight;
    private int mWidth;

    interface Callback {
        void onSurfaceChanged();
    }

    /* access modifiers changed from: 0000 */
    public abstract Class getOutputClass();

    /* access modifiers changed from: 0000 */
    public abstract Surface getSurface();

    /* access modifiers changed from: 0000 */
    public abstract View getView();

    /* access modifiers changed from: 0000 */
    public abstract boolean isReady();

    /* access modifiers changed from: 0000 */
    public abstract void setDisplayOrientation(int i);

    PreviewImpl() {
    }

    /* access modifiers changed from: 0000 */
    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    /* access modifiers changed from: protected */
    public void dispatchSurfaceChanged() {
        this.mCallback.onSurfaceChanged();
    }

    /* access modifiers changed from: 0000 */
    public SurfaceHolder getSurfaceHolder() {
        return null;
    }

    /* access modifiers changed from: 0000 */
    public Object getSurfaceTexture() {
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void setBufferSize(int width, int height) {
    }

    /* access modifiers changed from: 0000 */
    public void setSize(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    /* access modifiers changed from: 0000 */
    public int getWidth() {
        return this.mWidth;
    }

    /* access modifiers changed from: 0000 */
    public int getHeight() {
        return this.mHeight;
    }
}
