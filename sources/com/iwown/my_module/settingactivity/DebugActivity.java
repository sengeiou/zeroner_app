package com.iwown.my_module.settingactivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import com.iwown.data_link.data.GlobalDataUpdater;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.my_module.R;
import com.iwown.my_module.common.BaseActivity;
import com.iwown.my_module.utility.Constants;
import com.iwown.my_module.widget.ShSwitchView.OnSwitchStateChangeListener;
import com.iwown.my_module.widget.ShSwitchViewLayout;

public class DebugActivity extends BaseActivity {
    /* access modifiers changed from: private */
    public Context mContext;
    private ShSwitchViewLayout switchBlelog;
    private TextView textView;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        boolean z;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_module_activity_debug);
        setTitleText("Debug");
        setLeftBackTo();
        this.mContext = this;
        this.switchBlelog = (ShSwitchViewLayout) findViewById(R.id.ble_log);
        this.textView = (TextView) findViewById(R.id.debug_txt);
        String msg = "";
        if (Constants.NEW_API.contains("hwbetaapi")) {
            msg = "hwbetaapi";
        }
        if (Constants.LOG_UPLOAD_API.contains("hwbetaapi")) {
            msg = msg + " - hwbetaapi";
        }
        if (!"".equals(msg)) {
            this.textView.setVisibility(0);
            this.textView.setText(msg);
        } else {
            this.textView.setVisibility(8);
        }
        ShSwitchViewLayout shSwitchViewLayout = this.switchBlelog;
        if (GlobalUserDataFetcher.getBleFlag(this.mContext) == 1) {
            z = true;
        } else {
            z = false;
        }
        shSwitchViewLayout.setCheckboxCheck(z);
        this.switchBlelog.setOnSwitchStateChangeListener(new OnSwitchStateChangeListener() {
            public void onSwitchStateChange(boolean isOn) {
                if (isOn) {
                    GlobalDataUpdater.setBleFlag(DebugActivity.this.mContext, 1);
                    GlobalDataUpdater.setAppFlag(DebugActivity.this.mContext, 1);
                    return;
                }
                GlobalDataUpdater.setBleFlag(DebugActivity.this.mContext, 0);
                GlobalDataUpdater.setAppFlag(DebugActivity.this.mContext, 0);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }
}
