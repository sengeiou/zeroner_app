package com.iwown.sport_module.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iwown.data_link.RouteUtils;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.BaseActivity2;
import com.iwown.sport_module.R;
import com.iwown.sport_module.util.Util;

public class NotificationHintActivity extends BaseActivity2 {
    private Button setBtn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_module_notification_main);
        setTitleText(R.string.device_module_activity_msg_push);
        setLeftBackTo();
        getTitleBar().setBackgroundColor(RunActivitySkin.Sport_Home_Bg_Color_Bottom);
        boolean isOpen = Util.isMsgNotificationEnabled(this);
        this.setBtn = (Button) findViewById(R.id.notification_btn);
        if (AppConfigUtil.isRussia(this) || AppConfigUtil.isIwownFitPro() || AppConfigUtil.isZeronerHealthPro() || AppConfigUtil.isHealthy(this)) {
            this.setBtn.setVisibility(0);
        } else {
            this.setBtn.setVisibility(8);
        }
        this.setBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String url;
                if (AppConfigUtil.isRussia(NotificationHintActivity.this)) {
                    url = "https://search.iwown.com/guide/bracelet/bracelet.html#/";
                } else if (AppConfigUtil.isIwownFitPro()) {
                    url = "https://api4.iwown.com/setting/dist/index.html#/";
                } else if (AppConfigUtil.isZeronerHealthPro()) {
                    url = "https://api4.iwown.com/setting/dist/index.html#/zhp/app";
                } else if (AppConfigUtil.isHealthy()) {
                    url = "https://api2.iwown.com/setting/zhushou/index.html#/china/app";
                } else {
                    url = "";
                }
                ARouter.getInstance().build(RouteUtils.Activity_my_app_background_Activity).withString("url", url).withString("title", "Guide").navigation();
            }
        });
        if (!isOpen) {
            showConfirmDialog();
        }
    }

    @SuppressLint({"NewApi"})
    private void showConfirmDialog() {
        new Builder(this).setMessage(getString(R.string.device_module_notification_push_open)).setTitle(getString(R.string.device_module_notification_push_title)).setIconAttribute(16843605).setCancelable(true).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Util.openNotificationAccess(NotificationHintActivity.this);
            }
        }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).create().show();
    }
}
