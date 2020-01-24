package com.google.android.cameraview;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

class SurfaceViewPreview extends PreviewImpl {
    final SurfaceView mSurfaceView;

    SurfaceViewPreview(Context context, ViewGroup parent) {
        this.mSurfaceView = (SurfaceView) View.inflate(context, R.layout.surface_view, parent).findViewById(R.id.surface_view);
        SurfaceHolder holder = this.mSurfaceView.getHolder();
        holder.setType(3);
        holder.addCallback(new Callback() {
            public void surfaceCreated(SurfaceHolder h) {
            }

            public void surfaceChanged(SurfaceHolder h, int format, int width, int height) {
                SurfaceViewPreview.this.setSize(width, height);
                if (!ViewCompat.isInLayout(SurfaceViewPreview.this.mSurfaceView)) {
                    SurfaceViewPreview.this.dispatchSurfaceChanged();
                }
            }

            public void surfaceDestroyed(SurfaceHolder h) {
                SurfaceViewPreview.this.setSize(0, 0);
            }
        });
    }

    /* access modifiers changed from: 0000 */
    public Surface getSurface() {
        return getSurfaceHolder().getSurface();
    }

    /* access modifiers changed from: 0000 */
    public SurfaceHolder getSurfaceHolder() {
        return this.mSurfaceView.getHolder();
    }

    /* access modifiers changed from: 0000 */
    public View getView() {
        return this.mSurfaceView;
    }

    /* access modifiers changed from: 0000 */
    public Class getOutputClass() {
        return SurfaceHolder.class;
    }

    /* access modifiers changed from: 0000 */
    public void setDisplayOrientation(int displayOrientation) {
    }

    /* access modifiers changed from: 0000 */
    public boolean isReady() {
        return (getWidth() == 0 || getHeight() == 0) ? false : true;
    }
}
