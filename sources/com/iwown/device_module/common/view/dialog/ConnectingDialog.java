package com.iwown.device_module.common.view.dialog;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager.LayoutParams;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieComposition.Factory;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.iwown.device_module.R;

public class ConnectingDialog extends AlertDialog {
    private Context context;
    /* access modifiers changed from: private */
    public LottieAnimationView img;

    public ConnectingDialog(@NonNull Context context2) {
        super(context2, R.style.device_module_pre_dialog);
        this.context = context2;
    }

    protected ConnectingDialog(@NonNull Context context2, int themeResId) {
        super(context2, themeResId);
    }

    protected ConnectingDialog(@NonNull Context context2, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context2, cancelable, cancelListener);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_search_connect_dialog_layout);
        initView();
    }

    private void initView() {
        this.img = (LottieAnimationView) findViewById(R.id.airbnb_animation);
        Factory.fromAssetFileName(this.context, "connect.json", new OnCompositionLoadedListener() {
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                ConnectingDialog.this.img.setComposition(composition);
                ConnectingDialog.this.img.setImageAssetsFolder("aribnb_connect/");
                ConnectingDialog.this.img.setRepeatMode(-1);
                ConnectingDialog.this.img.loop(true);
                ConnectingDialog.this.img.useHardwareAcceleration(true);
                ConnectingDialog.this.img.enableMergePathsForKitKatAndAbove(true);
            }
        });
    }

    public void show() {
        super.show();
        LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = 80;
        layoutParams.width = -1;
        layoutParams.height = -1;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
        this.img.playAnimation();
    }

    public void dismiss() {
        super.dismiss();
        if (this.img != null) {
            this.img.cancelAnimation();
        }
    }
}
