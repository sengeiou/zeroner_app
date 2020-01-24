package com.iwown.sport_module.ui.weight.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.hiflying.smartlink.OnSmartLinkListener;
import com.hiflying.smartlink.SmartLinkedModule;
import com.hiflying.smartlink.v7.MulticastSmartLinker;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.base.ActivityFinish;
import com.iwown.data_link.eventbus.DevicePageSwitch;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import com.iwown.sport_module.ui.weight.S2WifiUtils;
import com.socks.library.KLog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WifiS2ConfigingActivity extends BaseActivity implements OnSmartLinkListener {
    public static String Wifi_Name = "wifi_name";
    public static String Wifi_PWD = "wifi_pwd";
    private boolean mIsConncting;
    private MulticastSmartLinker mSnifferSmartLinker;
    private WifiManager mWifi;
    private BroadcastReceiver mWifiChangedReceiver;
    private String name;
    private String pwd;
    /* access modifiers changed from: private */
    public TextView tvShowStatus;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_wifi_s2_configing);
        setTitleBarBG(getResources().getColor(R.color.s2_wifi_base_color));
        setTitleText(getString(R.string.sport_module_s2_s2_wifi_config));
        setLeftBackTo();
        initView();
        this.mWifi = (WifiManager) getApplicationContext().getSystemService("wifi");
        if (!this.mWifi.isWifiEnabled()) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.device_module_scale_wifi_is_open));
            finish();
            return;
        }
        this.name = getIntent().getStringExtra(Wifi_Name);
        this.pwd = getIntent().getStringExtra(Wifi_PWD);
        if (TextUtils.isEmpty(this.name) || TextUtils.isEmpty(this.pwd)) {
            ToastUtils.showShortToast((CharSequence) getString(R.string.device_module_scale_wifi_setting_error));
            finish();
            return;
        }
        EventBus.getDefault().register(this);
        initAction();
    }

    private void initView() {
        this.tvShowStatus = (TextView) findViewById(R.id.tv_show_status);
    }

    private void initAction() {
        this.mSnifferSmartLinker = MulticastSmartLinker.getInstance();
        this.mSnifferSmartLinker.setOnSmartLinkListener(this);
        this.mWifiChangedReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                NetworkInfo networkInfo = ((ConnectivityManager) WifiS2ConfigingActivity.this.getSystemService("connectivity")).getNetworkInfo(1);
                if (networkInfo == null || !networkInfo.isConnected()) {
                    ToastUtils.showShortToast((CharSequence) WifiS2ConfigingActivity.this.getString(R.string.device_module_scale_wifi_connect_fail));
                    WifiS2ConfigingActivity.this.finish();
                }
            }
        };
        registerReceiver(this.mWifiChangedReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        try {
            this.mSnifferSmartLinker.start(getApplicationContext(), this.pwd, this.name);
            this.mIsConncting = true;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void onLinked(SmartLinkedModule module) {
        KLog.e("onLinked " + module);
        this.tvShowStatus.post(new Runnable() {
            public void run() {
                WifiS2ConfigingActivity.this.tvShowStatus.setText(WifiS2ConfigingActivity.this.getString(R.string.device_module_scale_wifi_connect_right_now));
            }
        });
    }

    public void onCompleted() {
        KLog.e("onCompleted");
        this.tvShowStatus.setText(getString(R.string.device_module_scale_wifi_connect_ok));
        this.mIsConncting = false;
        S2WifiUtils.updateS2WifiConfig(UserConfig.getInstance().getNewUID(), this.name, this.pwd);
        EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Setting));
        EventBus.getDefault().post(new DevicePageSwitch(DevicePageSwitch.Device_Top_Switch_To_Scale));
        EventBus.getDefault().post(new ActivityFinish(WifiInputAcitvity.class.getSimpleName()));
        EventBus.getDefault().post(new ActivityFinish(WifiConfigurationActivity.class.getSimpleName()));
        EventBus.getDefault().post(new ActivityFinish(WifiS2ConfigingActivity.class.getSimpleName()));
        RouteUtils.startAPPMainActivitry();
        finish();
    }

    public void onTimeOut() {
        KLog.e("onTimeOut");
        this.tvShowStatus.setText(getString(R.string.device_module_scale_wifi_connect_timeout));
        this.mIsConncting = false;
        startActivity(new Intent(this, ConfigExceptionActivity.class));
        finish();
    }

    private String getSSid() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService("wifi");
        if (wm != null) {
            WifiInfo wi = wm.getConnectionInfo();
            if (wi != null) {
                String ssid = wi.getSSID();
                if (ssid.length() <= 2 || !ssid.startsWith("\"") || !ssid.endsWith("\"")) {
                    return ssid;
                }
                return ssid.substring(1, ssid.length() - 1);
            }
        }
        return "";
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
        KLog.e("-----");
        if (this.mSnifferSmartLinker != null) {
            this.mSnifferSmartLinker.stop();
            this.mIsConncting = false;
            this.mSnifferSmartLinker.setOnSmartLinkListener(null);
        }
        try {
            unregisterReceiver(this.mWifiChangedReceiver);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }
}
