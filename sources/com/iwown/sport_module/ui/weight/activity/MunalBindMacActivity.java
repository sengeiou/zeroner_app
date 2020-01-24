package com.iwown.sport_module.ui.weight.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.ui.weight.S2WifiUtils;
import com.tapadoo.alerter.Alerter;

public class MunalBindMacActivity extends BaseActivity {
    private Button btBind;
    /* access modifiers changed from: private */
    public EditText etMac;
    private TextView tvTitle;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_munal_bind_mac);
        setLeftBackTo();
        setTitleText(getString(R.string.sport_module_s2_m_bind));
        setTitleBarBG(getResources().getColor(R.color.qr_code_top_bg2));
        initView();
    }

    private void initView() {
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.etMac = (EditText) findViewById(R.id.et_mac);
        this.btBind = (Button) findViewById(R.id.bt_bind);
        this.btBind.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String resultString = MunalBindMacActivity.this.etMac.getText().toString().trim();
                String regex = "^([A-F0-9]){12}$";
                if (TextUtils.isEmpty(resultString) || !resultString.matches(regex)) {
                    Alerter.create(MunalBindMacActivity.this).hideIcon().setDuration(500).setText(MunalBindMacActivity.this.getString(R.string.mac_is_error)).show();
                } else {
                    S2WifiUtils.banS2MAc(UserConfig.getInstance().getNewUID(), resultString, MunalBindMacActivity.this);
                }
            }
        });
    }
}
