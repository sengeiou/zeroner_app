package com.google.android.cameraview;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Size;

@TargetApi(23)
class Camera2Api23 extends Camera2 {
    Camera2Api23(Callback callback, PreviewImpl preview, Context context) {
        super(callback, preview, context);
    }

    /* access modifiers changed from: protected */
    public void collectPictureSizes(SizeMap sizes, StreamConfigurationMap map) {
        Size[] highResolutionOutputSizes;
        if (map.getHighResolutionOutputSizes(256) != null) {
            for (Size size : map.getHighResolutionOutputSizes(256)) {
                sizes.add(new Size(size.getWidth(), size.getHeight()));
            }
        }
        if (sizes.isEmpty()) {
            super.collectPictureSizes(sizes, map);
        }
    }
}
