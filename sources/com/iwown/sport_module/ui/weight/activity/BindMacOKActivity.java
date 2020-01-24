package com.iwown.sport_module.ui.weight.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.iwown.data_link.base.ActivityFinish;
import com.iwown.data_link.eventbus.DevicePageSwitch;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.ui.weight.QuestionActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@Route(path = "/sport/sport_bind_ok_activty")
public class BindMacOKActivity extends BaseActivity implements OnClickListener {
    private Button bt2WifiSet;
    private Button btOk;
    private TextView ivBindOk;
    private TextView tv2Guide;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_bind_mac_ok);
        setTitleBarBG(getResources().getColor(R.color.s2_wifi_base_color));
        setTitleText(getString(R.string.sport_module_s2_bind_ok));
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        this.ivBindOk = (TextView) findViewById(R.id.iv_bind_ok);
        this.bt2WifiSet = (Button) findViewById(R.id.bt_2_wifi_set);
        this.tv2Guide = (TextView) findViewById(R.id.tv_2_guide);
        this.btOk = (Button) findViewById(R.id.bt_ok);
        this.tv2Guide.setOnClickListener(this);
        this.ivBindOk.setOnClickListener(this);
        this.bt2WifiSet.setOnClickListener(this);
        this.btOk.setOnClickListener(this);
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_2_guide) {
            startActivity(new Intent(this, QuestionActivity.class));
        } else if (i == R.id.bt_2_wifi_set) {
            startActivity(new Intent(this, WifiConfigurationActivity.class));
        } else if (i == R.id.bt_ok) {
            finish();
            EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Setting));
            EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Top_Switch_To_Scale));
        }
    }

    @Subscribe
    public void onEventMainThread(ActivityFinish activityFinish) {
        if (this.Tag.equals(activityFinish.tag)) {
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
