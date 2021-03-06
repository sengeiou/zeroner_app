package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;

public class PathKeyframe extends Keyframe<PointF> {
    @Nullable
    private Path path;

    public PathKeyframe(LottieComposition composition, Keyframe<PointF> keyframe) {
        super(composition, keyframe.startValue, keyframe.endValue, keyframe.interpolator, keyframe.startFrame, keyframe.endFrame);
        boolean equals = (this.endValue == null || this.startValue == null || !((PointF) this.startValue).equals(((PointF) this.endValue).x, ((PointF) this.endValue).y)) ? false : true;
        if (this.endValue != null && !equals) {
            this.path = Utils.createPath((PointF) this.startValue, (PointF) this.endValue, keyframe.pathCp1, keyframe.pathCp2);
        }
    }

    /* access modifiers changed from: 0000 */
    @Nullable
    public Path getPath() {
        return this.path;
    }
}
