package com.iwown.device_module.common.view.dialog;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieComposition.Factory;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.iwown.device_module.R;

public class PreDialog extends AlertDialog {
    private Context context;
    /* access modifiers changed from: private */
    public LottieAnimationView img;
    private TextView message;

    public PreDialog(@NonNull Context context2) {
        super(context2, R.style.device_module_pre_dialog);
        this.context = context2;
    }

    protected PreDialog(@NonNull Context context2, int themeResId) {
        super(context2, themeResId);
    }

    protected PreDialog(@NonNull Context context2, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context2, cancelable, cancelListener);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_module_pre_dialog_layout);
        initView();
    }

    private void initView() {
        this.img = (LottieAnimationView) findViewById(R.id.pre_loading);
        this.message = (TextView) findViewById(R.id.toast_msg);
        Factory.fromAssetFileName(this.context, "loading.json", new OnCompositionLoadedListener() {
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                PreDialog.this.img.setComposition(composition);
                PreDialog.this.img.setImageAssetsFolder("airbnb_loading/");
                PreDialog.this.img.setRepeatMode(-1);
                PreDialog.this.img.loop(true);
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

    public void setMessage(String text) {
        this.message.setText(text);
    }
}
