package com.iwown.sport_module.gps.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.log.L;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import com.iwown.sport_module.gps.view.MyWakeLock;
import com.socks.library.KLog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GpsGoogleService extends Service implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_CHECK_SETTINGS = 2;
    /* access modifiers changed from: private */
    public String actionStr = "com.kunket.locSDK.timer1";
    private AlarmManager am;
    int count = 0;
    private ExecutorService executor;
    /* access modifiers changed from: private */
    public boolean isRun;
    private String lastProvider;
    /* access modifiers changed from: private */
    public long lastTime;
    private GoogleApiClient mGoogleApiClient;
    private LocationReceiver mLister = null;
    private LocationRequest mLocationRequest;
    private MyWakeLock myWakeLock = null;
    private PendingIntent pi;
    PendingResult<LocationSettingsResult> result;
    /* access modifiers changed from: private */
    public int sleepTime = 0;

    class LocationReceiver extends BroadcastReceiver {
        LocationReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            KLog.e("no2855testamap actiton_？ " + intent.getAction());
            if (intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                KLog.e("no2855testamap gps 手机进入锁屏状态-> " + intent.getAction());
                L.file("gps 手机进入锁屏状态-> ", 8);
            } else if (intent.getAction().equals(GpsGoogleService.this.actionStr)) {
                if (System.currentTimeMillis() - GpsGoogleService.this.lastTime > 10000) {
                    KLog.e("no2855testamap gps 我叫他点亮频幕的" + intent.getAction());
                    L.file("gps 过久没有定位，强行定位-> ", 8);
                    GpsGoogleService.this.startLocationUpdates();
                }
            } else if (intent.getAction().equals("android.intent.action.SCREEN_ON")) {
                KLog.e("no2855testamap gps 手机进入屏幕亮起-> " + intent.getAction());
                L.file("testgps 手机进入屏幕亮起-> ", 8);
            }
        }
    }

    public void onCreate() {
        super.onCreate();
        Log.d("testamap", "服务onCreate");
        if (this.mGoogleApiClient == null) {
            this.mGoogleApiClient = new Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
        this.isRun = true;
        createLocationRequest();
        this.mGoogleApiClient.connect();
        this.executor = Executors.newSingleThreadExecutor();
        this.executor.execute(new Runnable() {
            /* JADX WARNING: Removed duplicated region for block: B:3:0x0008 A[Catch:{ Exception -> 0x0044 }] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r6 = this;
                L_0x0000:
                    com.iwown.sport_module.gps.service.GpsGoogleService r1 = com.iwown.sport_module.gps.service.GpsGoogleService.this     // Catch:{ Exception -> 0x0044 }
                    boolean r1 = r1.isRun     // Catch:{ Exception -> 0x0044 }
                    if (r1 == 0) goto L_0x0048
                    r2 = 2500(0x9c4, double:1.235E-320)
                    java.lang.Thread.sleep(r2)     // Catch:{ Exception -> 0x0044 }
                    com.iwown.sport_module.gps.service.GpsGoogleService r1 = com.iwown.sport_module.gps.service.GpsGoogleService.this     // Catch:{ Exception -> 0x0044 }
                    int r2 = r1.count     // Catch:{ Exception -> 0x0044 }
                    int r2 = r2 + 1
                    r1.count = r2     // Catch:{ Exception -> 0x0044 }
                    com.iwown.sport_module.gps.service.GpsGoogleService r1 = com.iwown.sport_module.gps.service.GpsGoogleService.this     // Catch:{ Exception -> 0x0044 }
                    int r1 = r1.count     // Catch:{ Exception -> 0x0044 }
                    int r1 = r1 % 3
                    if (r1 != 0) goto L_0x0026
                    java.lang.String r1 = "testMain11111"
                    java.lang.String r2 = "服务还没挂呢 --------->"
                    android.util.Log.d(r1, r2)     // Catch:{ Exception -> 0x0044 }
                L_0x0026:
                    long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Exception -> 0x0044 }
                    r4 = 1000(0x3e8, double:4.94E-321)
                    long r2 = r2 / r4
                    com.iwown.sport_module.gps.service.GpsGoogleService r1 = com.iwown.sport_module.gps.service.GpsGoogleService.this     // Catch:{ Exception -> 0x0044 }
                    long r4 = r1.lastTime     // Catch:{ Exception -> 0x0044 }
                    long r2 = r2 - r4
                    com.iwown.sport_module.gps.service.GpsGoogleService r1 = com.iwown.sport_module.gps.service.GpsGoogleService.this     // Catch:{ Exception -> 0x0044 }
                    int r1 = r1.sleepTime     // Catch:{ Exception -> 0x0044 }
                    int r1 = r1 / 1000
                    int r1 = r1 * 3
                    long r4 = (long) r1
                    int r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                    if (r1 <= 0) goto L_0x0000
                    goto L_0x0000
                L_0x0044:
                    r0 = move-exception
                    com.google.devtools.build.android.desugar.runtime.ThrowableExtension.printStackTrace(r0)
                L_0x0048:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.iwown.sport_module.gps.service.GpsGoogleService.AnonymousClass1.run():void");
            }
        });
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
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.d("no2855服务onStartCommand");
        initData(intent);
        GoogleLocationManger.getInstance().initLocation(this);
        if (GoogleLocationManger.getInstance().getNotify2() != null) {
            startForeground(237, GoogleLocationManger.getInstance().getNotify2());
        }
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
        if (this.mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, (LocationListener) this);
        }
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLocationChanged(Location location) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.lastTime = System.currentTimeMillis();
        Date date1 = new Date(location.getTime());
        df.format(date1);
        Date date = new Date(System.currentTimeMillis());
        df.format(date);
        String provider = location.getProvider().toString();
        KLog.e("no2855-> gps数据: " + JsonTool.toJson(location));
        L.file(location.getLatitude() + " - " + location.getLongitude() + " 系统时间： " + df.format(date) + " gps时间: " + df.format(date1) + " 质量: " + provider, 8);
        if (location.getLongitude() != Utils.DOUBLE_EPSILON || location.getLatitude() != Utils.DOUBLE_EPSILON) {
            this.lastProvider = provider;
            GoogleLocationManger.getInstance().saveLocationChange(location);
        }
    }

    /* access modifiers changed from: protected */
    public void createLocationRequest() {
        this.mLocationRequest = new LocationRequest();
        this.mLocationRequest.setInterval(2000);
        this.mLocationRequest.setFastestInterval(2000);
        this.mLocationRequest.setPriority(100);
        this.result = LocationServices.SettingsApi.checkLocationSettings(this.mGoogleApiClient, new LocationSettingsRequest.Builder().addLocationRequest(this.mLocationRequest).build());
        this.result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            public void onResult(@NonNull LocationSettingsResult result) {
                switch (result.getStatus().getStatusCode()) {
                    case 0:
                        GpsGoogleService.this.startLocationUpdates();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void startLocationUpdates() {
        KLog.d("no2855谷歌地图:startLocationUpdates");
        if (ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
            return;
        }
        if (this.mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(this.mGoogleApiClient, this.mLocationRequest, (LocationListener) this);
            return;
        }
        L.file("谷歌服务:没有连接上", 8);
        this.mGoogleApiClient.connect();
    }

    public void onConnected(@Nullable Bundle bundle) {
        L.file("谷歌服务:已成功连接", 8);
    }

    public void onConnectionSuspended(int i) {
        KLog.d("no2855谷歌服务:onConnectionSuspended");
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        KLog.d("no2855谷歌服务:onConnectionFailed");
        L.file("谷歌服务:onConnectionFailed", 8);
    }
}
