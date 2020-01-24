package com.iwown.device_module.device_setting.wifi_scale.dialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.device_module.R;
import com.iwown.lib_common.DensityUtil;
import com.socks.library.KLog;

public class S2WifiAlertBelongTo extends AbsCustomDialog implements OnClickListener {
    private Button bt_config_error;
    private Button bt_ok;
    private CallBack callBack;
    private TextView msgText;

    public interface CallBack {
        void ok();

        void onError();
    }

    public S2WifiAlertBelongTo(Context context) {
        super(context);
    }

    public int getLayoutResID() {
        return R.layout.device_module_dialog_s2_wifi_not_belong;
    }

    public void initView() {
        try {
            getWindow().getDecorView().setPadding(DensityUtil.dip2px(getContext(), 50.0f), 0, DensityUtil.dip2px(getContext(), 50.0f), 0);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            KLog.e("dialog_heart " + e.getMessage());
        }
        this.bt_ok = (Button) findViewById(R.id.bt_ok);
        this.bt_config_error = (Button) findViewById(R.id.bt_config_error);
        this.msgText = (TextView) findViewById(R.id.tv_title1);
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
            this.callBack.onError();
        } else if (i == R.id.bt_config_error) {
            dismiss();
            this.callBack.ok();
        }
    }

    public void setOkText(String text) {
        this.bt_ok.setText(text);
    }

    public void setCancelText(String cancelText) {
        this.bt_config_error.setText(cancelText);
    }

    public void msgContentText(String contentText) {
        this.msgText.setText(contentText);
    }
}
