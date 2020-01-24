package com.hiflying.smartlink;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public abstract class AbstractSmartLinkerActivity extends Activity implements OnSmartLinkListener {
    protected static String TAG = "SmartLinkerActivity";
    /* access modifiers changed from: private */
    public boolean mIsConncting = false;
    protected EditText mPasswordEditText;
    protected ISmartLinker mSmartLinker;
    protected EditText mSsidEditText;
    protected Button mStartButton;
    protected Handler mViewHandler = new Handler();
    protected ProgressDialog mWaitingDialog;
    private BroadcastReceiver mWifiChangedReceiver;

    public abstract ISmartLinker setupSmartLinker();

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        R1.initContext(getApplicationContext());
        this.mSmartLinker = setupSmartLinker();
        this.mWaitingDialog = new ProgressDialog(this);
        this.mWaitingDialog.setMessage(getString(R1.string("hiflying_smartlinker_waiting")));
        this.mWaitingDialog.setButton(-2, getString(17039360), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        this.mWaitingDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                AbstractSmartLinkerActivity.this.mSmartLinker.setOnSmartLinkListener(null);
                AbstractSmartLinkerActivity.this.mSmartLinker.stop();
                AbstractSmartLinkerActivity.this.mIsConncting = false;
            }
        });
        setContentView(R1.layout("activity_hiflying_sniffer_smart_linker"));
        this.mSsidEditText = (EditText) findViewById(R1.id("editText_hiflying_smartlinker_ssid"));
        this.mPasswordEditText = (EditText) findViewById(R1.id("editText_hiflying_smartlinker_password"));
        this.mStartButton = (Button) findViewById(R1.id("button_hiflying_smartlinker_start"));
        this.mSsidEditText.setText(getSSid());
        this.mStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!AbstractSmartLinkerActivity.this.mIsConncting) {
                    try {
                        AbstractSmartLinkerActivity.this.mSmartLinker.setOnSmartLinkListener(AbstractSmartLinkerActivity.this);
                        AbstractSmartLinkerActivity.this.mSmartLinker.start(AbstractSmartLinkerActivity.this.getApplicationContext(), AbstractSmartLinkerActivity.this.mPasswordEditText.getText().toString().trim(), AbstractSmartLinkerActivity.this.mSsidEditText.getText().toString().trim());
                        AbstractSmartLinkerActivity.this.mIsConncting = true;
                        AbstractSmartLinkerActivity.this.mWaitingDialog.show();
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }
        });
        this.mWifiChangedReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                NetworkInfo networkInfo = ((ConnectivityManager) AbstractSmartLinkerActivity.this.getSystemService("connectivity")).getNetworkInfo(1);
                if (networkInfo == null || !networkInfo.isConnected()) {
                    AbstractSmartLinkerActivity.this.mSsidEditText.setText(AbstractSmartLinkerActivity.this.getString(R1.string("hiflying_smartlinker_no_wifi_connectivity")));
                    AbstractSmartLinkerActivity.this.mSsidEditText.requestFocus();
                    AbstractSmartLinkerActivity.this.mStartButton.setEnabled(false);
                    if (AbstractSmartLinkerActivity.this.mWaitingDialog.isShowing()) {
                        AbstractSmartLinkerActivity.this.mWaitingDialog.dismiss();
                        return;
                    }
                    return;
                }
                AbstractSmartLinkerActivity.this.mSsidEditText.setText(AbstractSmartLinkerActivity.this.getSSid());
                AbstractSmartLinkerActivity.this.mPasswordEditText.requestFocus();
                AbstractSmartLinkerActivity.this.mStartButton.setEnabled(true);
            }
        };
        registerReceiver(this.mWifiChangedReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.mSmartLinker.setOnSmartLinkListener(null);
        try {
            unregisterReceiver(this.mWifiChangedReceiver);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void onLinked(final SmartLinkedModule module) {
        Log.w(TAG, "onLinked");
        this.mViewHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(AbstractSmartLinkerActivity.this.getApplicationContext(), AbstractSmartLinkerActivity.this.getString(R1.string("hiflying_smartlinker_new_module_found"), new Object[]{module.getMac(), module.getModuleIP()}), 0).show();
            }
        });
    }

    public void onCompleted() {
        Log.w(TAG, "onCompleted");
        this.mViewHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(AbstractSmartLinkerActivity.this.getApplicationContext(), AbstractSmartLinkerActivity.this.getString(R1.string("hiflying_smartlinker_completed")), 0).show();
                AbstractSmartLinkerActivity.this.mWaitingDialog.dismiss();
                AbstractSmartLinkerActivity.this.mIsConncting = false;
            }
        });
    }

    public void onTimeOut() {
        Log.w(TAG, "onTimeOut");
        this.mViewHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(AbstractSmartLinkerActivity.this.getApplicationContext(), AbstractSmartLinkerActivity.this.getString(R1.string("hiflying_smartlinker_timeout")), 0).show();
                AbstractSmartLinkerActivity.this.mWaitingDialog.dismiss();
                AbstractSmartLinkerActivity.this.mIsConncting = false;
            }
        });
    }

    /* access modifiers changed from: private */
    public String getSSid() {
        WifiManager wm = (WifiManager) getSystemService("wifi");
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
}
