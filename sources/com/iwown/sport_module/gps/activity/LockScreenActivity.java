package com.iwown.sport_module.gps.activity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RelativeLayout;
import com.iwown.sport_module.R;
import com.iwown.sport_module.gps.data.GoogleGpsEvent;
import com.iwown.sport_module.gps.data.GpsTimeEvent;
import com.iwown.sport_module.gps.service.LocationImpl;
import com.iwown.sport_module.gps.view.WallpaperDrawable;
import com.iwown.sport_module.util.WindowUtil;
import com.iwown.sport_module.view.ScreenMainLayout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LockScreenActivity extends SwipeBackActivity {
    public static LockScreenActivity instance = null;
    RelativeLayout lockBgLy;
    ScreenMainLayout screenMainLayout;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowUtil.setTopWindows(getWindow());
        getWindow().addFlags(4718592);
        setContentView(R.layout.sport_module_lock_screen_main);
        Log.d("testMain", "锁屏界面启动了-----");
        instance = this;
        this.screenMainLayout.setView(LocationImpl.getInstance().getSportType());
        this.screenMainLayout.initData(LocationImpl.getInstance().getSportType());
        this.screenMainLayout.reshView(LocationImpl.getInstance().getGpsMsgData());
    }

    public void startActivity(Intent intent, @Nullable Bundle options) {
        super.startActivity(intent, options);
    }

    public void onBackPressed() {
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GpsTimeEvent event) {
        this.screenMainLayout.changeTime(event.getTime());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(GoogleGpsEvent event) {
        if (event.location != null) {
            Log.d("testMain", "锁屏夜收到广播-->");
            this.screenMainLayout.reshView(event.data);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    private void setWallBackgruond() {
        Drawable d = WallpaperManager.getInstance(this).getDrawable();
        if (d != null) {
            new WallpaperDrawable().setBitmap(d);
            this.lockBgLy.setBackground(d);
        }
    }
}
