package com.google.android.cameraview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.ViewGroup;

@TargetApi(14)
class TextureViewPreview extends PreviewImpl {
    private int mDisplayOrientation;
    private final TextureView mTextureView;

    TextureViewPreview(Context context, ViewGroup parent) {
        this.mTextureView = (TextureView) View.inflate(context, R.layout.texture_view, parent).findViewById(R.id.texture_view);
        this.mTextureView.setSurfaceTextureListener(new SurfaceTextureListener() {
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                TextureViewPreview.this.setSize(width, height);
                TextureViewPreview.this.configureTransform();
                TextureViewPreview.this.dispatchSurfaceChanged();
            }

            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                TextureViewPreview.this.setSize(width, height);
                TextureViewPreview.this.configureTransform();
                TextureViewPreview.this.dispatchSurfaceChanged();
            }

            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                TextureViewPreview.this.setSize(0, 0);
                return true;
            }

            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(15)
    public void setBufferSize(int width, int height) {
        this.mTextureView.getSurfaceTexture().setDefaultBufferSize(width, height);
    }

    /* access modifiers changed from: 0000 */
    public Surface getSurface() {
        return new Surface(this.mTextureView.getSurfaceTexture());
    }

    /* access modifiers changed from: 0000 */
    public SurfaceTexture getSurfaceTexture() {
        return this.mTextureView.getSurfaceTexture();
    }

    /* access modifiers changed from: 0000 */
    public View getView() {
        return this.mTextureView;
    }

    /* access modifiers changed from: 0000 */
    public Class getOutputClass() {
        return SurfaceTexture.class;
    }

    /* access modifiers changed from: 0000 */
    public void setDisplayOrientation(int displayOrientation) {
        this.mDisplayOrientation = displayOrientation;
        configureTransform();
    }

    /* access modifiers changed from: 0000 */
    public boolean isReady() {
        return this.mTextureView.getSurfaceTexture() != null;
    }

    /* access modifiers changed from: 0000 */
    public void configureTransform() {
        Matrix matrix = new Matrix();
        if (this.mDisplayOrientation % 180 == 90) {
            int width = getWidth();
            int height = getHeight();
            matrix.setPolyToPoly(new float[]{0.0f, 0.0f, (float) width, 0.0f, 0.0f, (float) height, (float) width, (float) height}, 0, this.mDisplayOrientation == 90 ? new float[]{0.0f, (float) height, 0.0f, 0.0f, (float) width, (float) height, (float) width, 0.0f} : new float[]{(float) width, 0.0f, (float) width, (float) height, 0.0f, 0.0f, 0.0f, (float) height}, 0, 4);
        } else if (this.mDisplayOrientation == 180) {
            matrix.postRotate(180.0f, (float) (getWidth() / 2), (float) (getHeight() / 2));
        }
        this.mTextureView.setTransform(matrix);
    }
}
