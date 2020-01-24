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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

public abstract class AbstractSmartLinkerFragment extends Fragment implements OnSmartLinkListener {
    protected static String TAG = "SmartLinkerFragment";
    /* access modifiers changed from: private */
    public Context mAppContext;
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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mAppContext = activity.getApplicationContext();
        R1.initContext(this.mAppContext);
        this.mSmartLinker = setupSmartLinker();
        this.mSmartLinker.setOnSmartLinkListener(this);
        this.mWaitingDialog = new ProgressDialog(activity);
        this.mWaitingDialog.setMessage(getString(R1.string("hiflying_smartlinker_waiting")));
        this.mWaitingDialog.setButton(-2, getString(17039360), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        this.mWaitingDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                AbstractSmartLinkerFragment.this.mSmartLinker.setOnSmartLinkListener(null);
                AbstractSmartLinkerFragment.this.mSmartLinker.stop();
                AbstractSmartLinkerFragment.this.mIsConncting = false;
            }
        });
    }

    public void onDetach() {
        super.onDetach();
        this.mSmartLinker.setOnSmartLinkListener(null);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R1.layout("activity_hiflying_sniffer_smart_linker"), container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mSsidEditText = (EditText) view.findViewById(R1.id("editText_hiflying_smartlinker_ssid"));
        this.mPasswordEditText = (EditText) view.findViewById(R1.id("editText_hiflying_smartlinker_password"));
        this.mStartButton = (Button) view.findViewById(R1.id("button_hiflying_smartlinker_start"));
        this.mSsidEditText.setText(getSSid());
        this.mStartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!AbstractSmartLinkerFragment.this.mIsConncting) {
                    try {
                        AbstractSmartLinkerFragment.this.mSmartLinker.setOnSmartLinkListener(AbstractSmartLinkerFragment.this);
                        AbstractSmartLinkerFragment.this.mSmartLinker.start(AbstractSmartLinkerFragment.this.mAppContext, AbstractSmartLinkerFragment.this.mPasswordEditText.getText().toString().trim(), AbstractSmartLinkerFragment.this.mSsidEditText.getText().toString().trim());
                        AbstractSmartLinkerFragment.this.mIsConncting = true;
                        AbstractSmartLinkerFragment.this.mWaitingDialog.show();
                    } catch (Exception e) {
                        ThrowableExtension.printStackTrace(e);
                    }
                }
            }
        });
        this.mWifiChangedReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                NetworkInfo networkInfo = ((ConnectivityManager) AbstractSmartLinkerFragment.this.mAppContext.getSystemService("connectivity")).getNetworkInfo(1);
                if (networkInfo == null || !networkInfo.isConnected()) {
                    AbstractSmartLinkerFragment.this.mSsidEditText.setText(AbstractSmartLinkerFragment.this.getString(R1.string("hiflying_smartlinker_no_wifi_connectivity")));
                    AbstractSmartLinkerFragment.this.mSsidEditText.requestFocus();
                    AbstractSmartLinkerFragment.this.mStartButton.setEnabled(false);
                    if (AbstractSmartLinkerFragment.this.mWaitingDialog.isShowing()) {
                        AbstractSmartLinkerFragment.this.mWaitingDialog.dismiss();
                        return;
                    }
                    return;
                }
                AbstractSmartLinkerFragment.this.mSsidEditText.setText(AbstractSmartLinkerFragment.this.getSSid());
                AbstractSmartLinkerFragment.this.mPasswordEditText.requestFocus();
                AbstractSmartLinkerFragment.this.mStartButton.setEnabled(true);
            }
        };
        this.mAppContext.registerReceiver(this.mWifiChangedReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    public void onDestroyView() {
        super.onDestroyView();
        try {
            this.mAppContext.unregisterReceiver(this.mWifiChangedReceiver);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    public void onLinked(final SmartLinkedModule module) {
        Log.w(TAG, "onLinked");
        this.mViewHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(AbstractSmartLinkerFragment.this.mAppContext, AbstractSmartLinkerFragment.this.getString(R1.string("hiflying_smartlinker_new_module_found"), module.getMac(), module.getModuleIP()), 0).show();
            }
        });
    }

    public void onCompleted() {
        Log.w(TAG, "onCompleted");
        this.mViewHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(AbstractSmartLinkerFragment.this.mAppContext, AbstractSmartLinkerFragment.this.getString(R1.string("hiflying_smartlinker_completed")), 0).show();
                AbstractSmartLinkerFragment.this.mWaitingDialog.dismiss();
                AbstractSmartLinkerFragment.this.mIsConncting = false;
            }
        });
    }

    public void onTimeOut() {
        Log.w(TAG, "onTimeOut");
        this.mViewHandler.post(new Runnable() {
            public void run() {
                Toast.makeText(AbstractSmartLinkerFragment.this.mAppContext, AbstractSmartLinkerFragment.this.getString(R1.string("hiflying_smartlinker_timeout")), 0).show();
                AbstractSmartLinkerFragment.this.mWaitingDialog.dismiss();
                AbstractSmartLinkerFragment.this.mIsConncting = false;
            }
        });
    }

    /* access modifiers changed from: private */
    public String getSSid() {
        WifiManager wm = (WifiManager) this.mAppContext.getSystemService("wifi");
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
