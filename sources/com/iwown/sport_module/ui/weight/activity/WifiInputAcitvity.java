package com.iwown.sport_module.ui.weight.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.base.ActivityFinish;
import com.iwown.lib_common.toast.ToastUtils;
import com.iwown.lib_common.views.ClearableEditText;
import com.iwown.sport_module.R;
import com.iwown.sport_module.base.BaseActivity;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

@Route(path = "/sport/WifiInputAcitvity")
public class WifiInputAcitvity extends BaseActivity implements OnClickListener {
    private Button btConfig;
    private ClearableEditText cetPwd;
    private WifiInfo connectionInfo;
    private ConnectivityManager connectivityManager;
    private TextView errorMessage;
    private WifiManager mWifi;
    private BroadcastReceiver mWifiChangedReceiver;
    private TextView tvWifiName;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout(R.layout.sport_module_activity_wifi_input_acitvity);
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
        EventBus.getDefault().register(this);
        initWifiName();
    }

    private void initView() {
        this.tvWifiName = (TextView) findViewById(R.id.tv_wifi_name);
        this.cetPwd = (ClearableEditText) findViewById(R.id.cet_pwd);
        this.btConfig = (Button) findViewById(R.id.bt_config);
        this.errorMessage = (TextView) findViewById(R.id.error_message);
        this.tvWifiName = (TextView) findViewById(R.id.tv_wifi_name);
        this.cetPwd = (ClearableEditText) findViewById(R.id.cet_pwd);
        this.btConfig = (Button) findViewById(R.id.bt_config);
        this.errorMessage = (TextView) findViewById(R.id.error_message);
        this.btConfig.setOnClickListener(this);
    }

    private void initWifiName() {
        this.connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        State wifi = this.connectivityManager.getNetworkInfo(1).getState();
        if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
            updateWifiData();
        } else {
            ToastUtils.showShortToast((CharSequence) getString(R.string.device_module_scale_wifi_is_connected));
            finish();
        }
        initAction();
    }

    /* access modifiers changed from: private */
    public void updateWifiData() {
        this.connectionInfo = this.mWifi.getConnectionInfo();
        String ssid = this.connectionInfo.getSSID();
        if (wifiIs5G(ssid)) {
            this.btConfig.setEnabled(false);
            this.btConfig.setText(getString(R.string.device_module_weight_scale_wifi_pls_switch));
            this.btConfig.setBackgroundColor(getResources().getColor(R.color.s2_wifi_base_et_hint_color));
            this.errorMessage.setText(R.string.sport_module_scale_wifi_user_10);
        } else {
            this.btConfig.setText(getString(R.string.device_module_weight_scale_pls_wifi_config));
            this.btConfig.setEnabled(true);
            this.btConfig.setBackgroundColor(getResources().getColor(R.color.s2_wifi_base_color));
            this.errorMessage.setText("");
        }
        if (ssid.length() > 2 && ssid.startsWith("\"") && ssid.endsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        this.tvWifiName.setText(ssid);
    }

    private void initAction() {
        this.mWifiChangedReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectivityManager = (ConnectivityManager) WifiInputAcitvity.this.getSystemService("connectivity");
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
                if (networkInfo == null || !networkInfo.isConnected()) {
                    WifiInputAcitvity.this.finish();
                    return;
                }
                State wifi = connectivityManager.getNetworkInfo(1).getState();
                if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
                    WifiInputAcitvity.this.updateWifiData();
                }
            }
        };
        registerReceiver(this.mWifiChangedReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.cetPwd.setFocusable(true);
        this.cetPwd.requestFocus();
    }

    public void onClick(View view) {
        if (view.getId() == R.id.bt_config) {
            String name = this.tvWifiName.getText().toString().trim();
            String pwd = this.cetPwd.getText().toString().trim();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
                ToastUtils.showShortToast((CharSequence) getString(R.string.device_module_weight_scale_wifi_wifi_error));
                return;
            }
            Intent intent = new Intent(this, WifiS2ConfigingActivity.class);
            intent.putExtra(WifiS2ConfigingActivity.Wifi_Name, name);
            intent.putExtra(WifiS2ConfigingActivity.Wifi_PWD, pwd);
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
        try {
            unregisterReceiver(this.mWifiChangedReceiver);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public boolean wifiIs5G(String tempSsidString) {
        if (tempSsidString != null && tempSsidString.length() > 2) {
            String wifiSsid = tempSsidString.substring(1, tempSsidString.length() - 1);
            for (ScanResult scanResult : this.mWifi.getScanResults()) {
                if (scanResult.SSID.equals(wifiSsid)) {
                    return is5GHz(scanResult.frequency);
                }
            }
        }
        return false;
    }

    public static boolean is24GHz(int freq) {
        return freq > 2400 && freq < 2500;
    }

    public static boolean is5GHz(int freq) {
        return freq > 4900 && freq < 5900;
    }
}
