package com.iwown.healthy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.data.GlobalUserDataFetcher;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.WeakHandler;
import com.iwown.my_module.healthy.HealthyLoginActivity;
import com.iwown.my_module.settingactivity.GoalSettingActivity;
import com.iwown.my_module.useractivity.LoginActivity;
import com.iwown.my_module.useractivity.profile.NewProfileGenderActivity;
import com.iwown.my_module.utility.StatusbarHelper;

public class LoadingActivity extends Activity {
    private static final String TAG = "LoadingActivity";
    WeakHandler handler = new WeakHandler((Callback) new Callback() {
        public boolean handleMessage(Message message) {
            return false;
        }
    });
    /* access modifiers changed from: private */
    public boolean isDestory;
    private View view;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        requestWindowFeature(1);
        StatusbarHelper.hideStatusBar(this);
        this.handler.postDelayed(new Runnable() {
            public void run() {
                if (!LoadingActivity.this.isDestory) {
                    Log.i(LoadingActivity.TAG, String.format("login status : %d", new Object[]{Integer.valueOf(GlobalUserDataFetcher.getLoginStatus(LoadingActivity.this))}));
                    int status = GlobalUserDataFetcher.getLoginStatus(LoadingActivity.this);
                    if (status == 3) {
                        RouteUtils.startAPPMainActivitry();
                        LoadingActivity.this.finish();
                        return;
                    }
                    LoadingActivity.this.enter(status);
                }
            }
        }, 1000);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        this.isDestory = true;
    }

    /* access modifiers changed from: 0000 */
    public void enter(int status) {
        switch (status) {
            case 0:
                if (!AppConfigUtil.isHealthy(this)) {
                    startActivity(new Intent(this, LoginActivity.class));
                    break;
                } else {
                    startActivity(new Intent(this, HealthyLoginActivity.class));
                    break;
                }
            case 1:
                Intent intent = new Intent(this, NewProfileGenderActivity.class);
                intent.putExtra(UserConst.PROFILE_VIEW_STATUS, 1);
                startActivity(intent);
                break;
            case 2:
                Intent intent2 = new Intent(this, GoalSettingActivity.class);
                intent2.putExtra(GoalSettingActivity.GOALVIEWSTATUS, 1);
                startActivity(intent2);
                break;
        }
        finish();
    }
}
