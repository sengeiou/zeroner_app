package com.iwown.device_module.device_guide.a_interface;

import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieComposition.Factory;
import com.airbnb.lottie.OnCompositionLoadedListener;

public class LottieAnimController implements IGuideAnimController {
    /* access modifiers changed from: private */
    public int count = -1;
    /* access modifiers changed from: private */
    public String lottie_images_path;
    private String lottie_json_name;
    LottieAnimationView mLottieAnimationView;

    public LottieAnimController(LottieAnimationView lottieAnimationView) {
        this.mLottieAnimationView = lottieAnimationView;
    }

    public LottieAnimController setLottiedAssetsPath(String path) {
        this.lottie_images_path = path;
        return this;
    }

    public LottieAnimController setLottiedJsonName(String jsonName) {
        this.lottie_json_name = jsonName;
        return this;
    }

    public LottieAnimController setRepeatCount(int count2) {
        this.count = count2;
        return this;
    }

    public void init() {
        Factory.fromAssetFileName(this.mLottieAnimationView.getContext(), this.lottie_json_name, new OnCompositionLoadedListener() {
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                LottieAnimController.this.mLottieAnimationView.setComposition(composition);
                LottieAnimController.this.mLottieAnimationView.setImageAssetsFolder(LottieAnimController.this.lottie_images_path);
                LottieAnimController.this.mLottieAnimationView.setRepeatMode(-1);
                LottieAnimController.this.mLottieAnimationView.setRepeatCount(LottieAnimController.this.count);
                LottieAnimController.this.mLottieAnimationView.useHardwareAcceleration(true);
                LottieAnimController.this.mLottieAnimationView.enableMergePathsForKitKatAndAbove(true);
            }
        });
    }

    public void start() {
        this.mLottieAnimationView.cancelAnimation();
        this.mLottieAnimationView.playAnimation();
    }

    public void stop() {
        if (this.mLottieAnimationView.isAnimating()) {
            this.mLottieAnimationView.pauseAnimation();
        }
    }

    public void cancel() {
        if (this.mLottieAnimationView != null) {
            this.mLottieAnimationView.cancelAnimation();
            this.mLottieAnimationView.clearAnimation();
        }
    }
}
