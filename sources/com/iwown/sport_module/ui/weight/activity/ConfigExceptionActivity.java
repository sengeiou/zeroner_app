package com.iwown.sport_module.ui.weight.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;

public class ConfigExceptionActivity extends BaseActivity {
    private Button btAgin;
    private ImageView ivError;
    private TextView tvAction;
    private TextView tvAction1;
    private TextView tvAction2;
    private TextView tvAction3;
    private TextView tvAction4;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_config_exception);
        setTitleBarBG(getResources().getColor(R.color.s2_wifi_base_color));
        setTitleText(getString(R.string.sport_module_s2_wifi_config_error));
        setLeftBackTo();
        initView();
        this.btAgin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ConfigExceptionActivity.this.back();
            }
        });
    }

    private void initView() {
        this.ivError = (ImageView) findViewById(R.id.iv_error);
        this.tvAction = (TextView) findViewById(R.id.tv_action);
        this.tvAction1 = (TextView) findViewById(R.id.tv_action1);
        this.tvAction2 = (TextView) findViewById(R.id.tv_action2);
        this.tvAction3 = (TextView) findViewById(R.id.tv_action3);
        this.tvAction4 = (TextView) findViewById(R.id.tv_action4);
        this.btAgin = (Button) findViewById(R.id.bt_agin);
    }
}
