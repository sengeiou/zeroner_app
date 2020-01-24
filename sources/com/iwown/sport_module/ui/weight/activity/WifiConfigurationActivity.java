package com.iwown.sport_module.ui.weight.activity;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iwown.data_link.base.ActivityFinish;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.ui.weight.QuestionActivity;
import com.iwown.sport_module.ui.weight.S2WifiAlert;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WifiConfigurationActivity extends BaseActivity implements OnClickListener {
    private Button btConfig;
    private InputMethodManager imm;
    private ImageView ivCenterCircle;
    private WifiManager mWifi;
    private S2WifiAlert s2WifiAlert;
    private TextView tvConfigError;
    private TextView tvTitle;

    public interface CallBack {
        void ok();

        void onError();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_wifi_configuration);
        setTitleBarBG(getResources().getColor(R.color.s2_wifi_base_color));
        initView();
        setTitleText(getString(R.string.sport_module_s2_s2_wifi_config));
        setLeftBackTo();
        this.mWifi = (WifiManager) getApplicationContext().getSystemService("wifi");
        if (!this.mWifi.isWifiEnabled()) {
            ToastUtils.showLongToast((CharSequence) getString(R.string.device_module_scale_wifi_is_open));
            finish();
            return;
        }
        EventBus.getDefault().register(this);
        Glide.with((FragmentActivity) this).load(Integer.valueOf(R.drawable.s2_wifi)).asGif().placeholder(R.drawable.s2_wifi).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.ivCenterCircle);
        initAction();
    }

    private void initView() {
        this.tvTitle = (TextView) findViewById(R.id.tv_title);
        this.ivCenterCircle = (ImageView) findViewById(R.id.iv_center_circle);
        this.btConfig = (Button) findViewById(R.id.bt_config);
        this.tvConfigError = (TextView) findViewById(R.id.tv_config_error);
        this.tvConfigError.setOnClickListener(this);
        this.btConfig.setOnClickListener(this);
    }

    private void initAction() {
        this.imm = (InputMethodManager) getSystemService("input_method");
    }

    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.bt_config) {
            if (this.s2WifiAlert == null) {
                this.s2WifiAlert = new S2WifiAlert(this);
                this.s2WifiAlert.setCallBack(new CallBack() {
                    public void ok() {
                        WifiConfigurationActivity.this.startActivity(new Intent(WifiConfigurationActivity.this, WifiInputAcitvity.class));
                    }

                    public void onError() {
                        Intent intent = new Intent(WifiConfigurationActivity.this, QuestionActivity.class);
                        intent.putExtra("key_title", WifiConfigurationActivity.this.getString(R.string.device_module_scale_screen_error));
                        WifiConfigurationActivity.this.startActivity(intent);
                    }
                });
            }
            this.s2WifiAlert.show();
        } else if (i == R.id.tv_config_error) {
            Intent intent = new Intent(this, QuestionActivity.class);
            intent.putExtra("key_title", getString(R.string.device_module_scale_wifi_statue));
            startActivity(intent);
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
