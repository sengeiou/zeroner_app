package com.iwown.sport_module.ui.weight;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.DensityUtil;
import com.iwown.lib_common.dialog.NewAbsCustomDialog;
import com.iwown.sport_module.R;
import com.iwown.sport_module.ui.weight.activity.WifiConfigurationActivity.CallBack;
import com.socks.library.KLog;

public class S2WifiAlert extends NewAbsCustomDialog implements OnClickListener {
    private Button bt_config_error;
    private Button bt_ok;
    private CallBack callBack;
    private ImageView iv_center_circle;

    public S2WifiAlert(Context context) {
        super(context);
    }

    public S2WifiAlert(Context context, boolean isOutCancel) {
        super(context, isOutCancel);
    }

    public int getLayoutResID() {
        return R.layout.sport_module_dialog_s2_wifi;
    }

    public void initView() {
        try {
            getWindow().getDecorView().setPadding(DensityUtil.dip2px(getContext(), 40.0f), 0, DensityUtil.dip2px(getContext(), 40.0f), 0);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            KLog.e("dialog_heart " + e.getMessage());
        }
        this.bt_ok = (Button) findViewById(R.id.bt_ok);
        this.bt_config_error = (Button) findViewById(R.id.bt_config_error);
        this.bt_config_error = (Button) findViewById(R.id.bt_config_error);
        this.iv_center_circle = (ImageView) findViewById(R.id.iv_center_circle);
        Glide.with(getContext()).load(Integer.valueOf(R.drawable.s2_press)).asGif().placeholder(R.drawable.s2_press).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.iv_center_circle);
        this.bt_ok.setOnClickListener(this);
        this.bt_config_error.setOnClickListener(this);
    }

    public void initData() {
    }

    public void initListener() {
    }

    public void setCallBack(CallBack callBack2) {
        this.callBack = callBack2;
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.bt_ok) {
            dismiss();
            this.callBack.ok();
        } else if (i == R.id.bt_config_error) {
            dismiss();
            this.callBack.onError();
        }
    }
}
