package com.iwown.sport_module.gps.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.provider.Settings.System;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import com.amap.api.location.APSService;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.lib_common.log.L;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.iwown.sport_module.gps.view.MyWakeLock;
import com.socks.library.KLog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;

public class GpsGoogleNewService extends APSService implements LocationListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    /* access modifiers changed from: private */
    public String actionStr = "com.kunket.locSDK.timer1";
    private AlarmManager am;
    int count = 0;
    private ExecutorService executor;
    /* access modifiers changed from: private */
    public boolean hasGps = false;
    private boolean isRun;
    private String lastProvider;
    /* access modifiers changed from: private */
    public long lastTime;
    LocationManager locationManager;
    private LocationReceiver mLister = null;
    private MyWakeLock myWakeLock = null;
    private PendingIntent pi;
    private int sleepTime = 0;

    class LocationReceiver extends BroadcastReceiver {
        LocationReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            KLog.e("testamap actiton_？ " + intent.getAction());
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                KLog.e("testamap gps 手机进入锁屏状态-> " + intent.getAction());
                L.file("gps 手机进入锁屏状态-> ", 8);
            } else if (intent.getAction().equals(GpsGoogleNewService.this.actionStr)) {
                if (GpsGoogleNewService.this.hasGps && System.currentTimeMillis() - GpsGoogleNewService.this.lastTime > 10000) {
                    KLog.e("testamap gps 我叫他点亮频幕的" + intent.getAction());
                    L.file("gps 过久没有定位，强行定位-> ", 8);
                    GpsGoogleNewService.this.startLocation();
                } else if (!GpsGoogleNewService.this.hasGps && System.currentTimeMillis() - GpsGoogleNewService.this.lastTime > 60000) {
                    GpsGoogleNewService.this.startLocation();
                }
            } else if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                KLog.e("testamap gps 手机进入屏幕亮起-> " + intent.getAction());
                L.file("testgps 手机进入屏幕亮起-> ", 8);
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        KLog.d("2855服务11onCreate");
    }

    private void initData(Intent intent) {
        if (this.myWakeLock == null) {
            this.myWakeLock = new MyWakeLock(this);
            this.myWakeLock.acquireWakeLock();
        }
        if (intent == null || intent.getIntExtra("type", 0) != 1) {
            this.sleepTime = ServiceErrorCode.UPLOAD_FILE_SO_BIG;
        } else {
            this.sleepTime = 2500;
        }
        IntentFilter intentFile = new IntentFilter();
        intentFile.addAction(this.actionStr);
        intentFile.addAction("android.intent.action.SCREEN_OFF");
        intentFile.addAction("android.intent.action.SCREEN_ON");
        Intent intent1 = new Intent();
        intent1.setAction(this.actionStr);
        this.pi = PendingIntent.getBroadcast(this, 0, intent1, 134217728);
        this.am = (AlarmManager) getSystemService(NotificationCompat.CATEGORY_ALARM);
        this.mLister = new LocationReceiver();
        registerReceiver(this.mLister, intentFile);
        this.am.setRepeating(2, 0, 90000, this.pi);
        this.count = 0;
        KLog.e("no2855testamap 这里执行了??-> ");
        this.hasGps = false;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.d("no2855服务111onStartCommand");
        initData(intent);
        GoogleLocationManger.getInstance().initLocation(this);
        if (GoogleLocationManger.getInstance().getNotify2() != null) {
            startForeground(237, GoogleLocationManger.getInstance().getNotify2());
        }
        startLocation();
        this.isRun = true;
        return 1;
    }

    public void onDestroy() {
        super.onDestroy();
        this.isRun = false;
        KLog.d("no2855服务onDestroy");
        if (this.myWakeLock != null) {
            this.myWakeLock.releaseWakeLock();
        }
        if (this.executor != null) {
            this.executor.shutdown();
        }
        if (this.am != null) {
            unregisterReceiver(this.mLister);
            this.am.cancel(this.pi);
        }
        stopForeground(true);
        this.isRun = false;
        if (this.locationManager != null) {
            this.locationManager.removeUpdates(this);
            this.locationManager = null;
        }
    }

    public void onLocationChanged(Location location) {
        if (location != null) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.lastTime = System.currentTimeMillis();
            Date date1 = new Date(location.getTime());
            df.format(date1);
            Date date = new Date(System.currentTimeMillis());
            df.format(date);
            String provider = location.getProvider().toString();
            KLog.e("no2855 gps质量：-> " + location.getLatitude() + "," + location.getLongitude() + " -- " + location.getProvider() + "  ---  " + location.getAccuracy());
            L.file(location.getLatitude() + " - " + location.getLongitude() + " 系统时间： " + df.format(date) + " gps时间: " + df.format(date1) + " 质量: " + location.getProvider().toString(), 8);
            if (!"network".equals(provider) || !"network".equals(this.lastProvider)) {
            }
            if (this.lastProvider == null || !"network".equals(provider) || location.getAccuracy() <= 50.0f) {
                this.lastProvider = provider;
                if ("gps".equals(provider)) {
                    this.hasGps = true;
                    GoogleLocationManger.getInstance().saveLocationChange(location);
                    return;
                }
                return;
            }
            this.lastProvider = provider;
        }
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case 0:
                L.file("gps 状态变更--> 服务区外", 8);
                return;
            case 1:
                L.file("gps 状态变更--> 暂停服务", 8);
                return;
            case 2:
                L.file("gps 状态变更--> 可见", 8);
                return;
            default:
                return;
        }
    }

    public void onProviderEnabled(String provider) {
        L.file("gps 状态变更--> onProviderEnabled", 8);
    }

    public void onProviderDisabled(String provider) {
        L.file("gps 状态变更--> onProviderDisabled", 8);
    }

    /* access modifiers changed from: private */
    public void startLocation() {
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            KLog.e("no2855不是吧写错地方了吗--->>>>");
            if (this.locationManager == null) {
                this.locationManager = (LocationManager) getSystemService("location");
            }
            this.locationManager.getBestProvider(getCriteria(), true);
            Location location = this.locationManager.getLastKnownLocation("passive");
            if (location == null) {
                KLog.d("no2855 gps质量初始化：->location 为空 ");
                L.file("gps去你家大爷-->为什么获取到的数据为空", 8);
                return;
            }
            this.lastTime = System.currentTimeMillis();
            GoogleLocationManger.getInstance().saveFirstLocation(location);
            KLog.d("no2855 gps质量初始化：-> " + location.getLatitude() + "," + location.getLongitude() + " -- " + location.getProvider() + "  ---  " + location.getAccuracy());
            L.file("gps 最近一次数据-> " + location.getLatitude() + " - " + location.getLongitude(), 8);
            this.locationManager.requestLocationUpdates("gps", 2000, 5.0f, this);
            String provider = location.getProvider().toString();
            boolean isTwoLbs = "network".equals(provider) && "network".equals(this.lastProvider);
            if (this.lastProvider == null || !"network".equals(provider) || location.getAccuracy() <= 50.0f) {
                this.lastProvider = provider;
                if (!isTwoLbs && "gps".equals(provider)) {
                    this.hasGps = true;
                    GoogleLocationManger.getInstance().saveLocationChange(location);
                    return;
                }
                return;
            }
            this.lastProvider = provider;
            return;
        }
        KLog.e("no2855这里才是真狠饿的--->>>>");
    }

    public Criteria getCriteria() {
        Criteria c = new Criteria();
        c.setAccuracy(1);
        c.setAltitudeRequired(false);
        c.setBearingRequired(true);
        c.setSpeedRequired(false);
        c.setCostAllowed(true);
        c.setPowerRequirement(3);
        return c;
    }

    private int getSystemBrightness() {
        int systemBrightness = 0;
        try {
            systemBrightness = System.getInt(getContentResolver(), "screen_brightness");
            KLog.e("no2855屏幕亮度--> " + systemBrightness);
            return systemBrightness;
        } catch (SettingNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
            return systemBrightness;
        }
    }
}
