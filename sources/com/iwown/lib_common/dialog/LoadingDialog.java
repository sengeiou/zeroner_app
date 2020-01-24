package com.iwown.lib_common.dialog;

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
import com.iwown.lib_common.R;

public class LoadingDialog extends AlertDialog {
    private Context context;
    /* access modifiers changed from: private */
    public LottieAnimationView img;
    private TextView message;

    public LoadingDialog(@NonNull Context context2) {
        super(context2, R.style.lib_common_pre_dialog);
        this.context = context2;
    }

    public LoadingDialog(@NonNull Context context2, boolean cancelable) {
        super(context2, cancelable, null);
        this.context = context2;
    }

    public LoadingDialog(@NonNull Context context2, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context2, cancelable, cancelListener);
        this.context = context2;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lib_common_new_view_loading);
        initView();
    }

    private void initView() {
        this.img = (LottieAnimationView) findViewById(R.id.pre_loading);
        this.message = (TextView) findViewById(R.id.toast_msg);
        Factory.fromAssetFileName(this.context, "loading.json", new OnCompositionLoadedListener() {
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                LoadingDialog.this.img.setComposition(composition);
                LoadingDialog.this.img.setImageAssetsFolder("airbnb_loading/");
                LoadingDialog.this.img.setRepeatMode(-1);
                LoadingDialog.this.img.loop(true);
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
