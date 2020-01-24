package com.iwown.sport_module.gps.location;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.Toast;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationClientOption.GeoLanguage;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.Date;

public class AMapLocationStrategy implements LocationStrategy {
    public static final String TAG = "AMapLocationStrategy";
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public UpdateLocationListener listener;
    private AMapLocationClient mLocationClient = null;
    private final AMapLocationListener mLocationListener = new AMapLocationListener() {
        public void onLocationChanged(AMapLocation location) {
            if (location != null) {
                StringBuilder sb = new StringBuilder();
                if (location.getErrorCode() == 0) {
                    sb.append("高德SDK定位成功\n");
                    sb.append("定位类型: " + location.getLocationType() + "\n");
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("精    度    : " + location.getAccuracy() + "米\n");
                    sb.append("提供者    : " + location.getProvider() + "\n");
                    sb.append("速    度    : " + location.getSpeed() + "米/秒\n");
                    sb.append("角    度    : " + location.getBearing() + "\n");
                    sb.append("星    数    : " + location.getSatellites() + "\n");
                    sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append("省            : " + location.getProvince() + "\n");
                    sb.append("市            : " + location.getCity() + "\n");
                    sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append("区            : " + location.getDistrict() + "\n");
                    sb.append("区域 码   : " + location.getAdCode() + "\n");
                    sb.append("地    址    : " + location.getAddress() + "\n");
                    sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    sb.append("定位时间: " + new DateUtil(new Date(location.getTime())).getY_M_D_H_M_S() + "\n");
                    L.file(sb.toString(), 8);
                    if (AMapLocationStrategy.this.listener != null) {
                        AMapLocationStrategy.this.listener.updateLocationChanged(location, location.getSatellites());
                    }
                } else {
                    KLog.e(AMapLocationStrategy.TAG, "onLocationChanged error，location ：" + new Gson().toJson((Object) location));
                    Toast.makeText(AMapLocationStrategy.this.context, "onLocationChanged error：" + sb.toString(), 0).show();
                    sb.append("经    度    : " + location.getLongitude() + "\n");
                    sb.append("纬    度    : " + location.getLatitude() + "\n");
                    sb.append("定位失败\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                sb.append("***定位质量报告***").append("\n");
                sb.append("* WIFI开关：").append(location.getLocationQualityReport().isWifiAble() ? "开启" : "关闭").append("\n");
                sb.append("* GPS状态：").append(AMapLocationStrategy.this.getGPSStatusString(location.getLocationQualityReport().getGPSStatus())).append("\n");
                sb.append("* GPS星数：").append(location.getLocationQualityReport().getGPSSatellites()).append("\n");
                sb.append("* 网络类型：" + location.getLocationQualityReport().getNetworkType()).append("\n");
                sb.append("* 网络耗时：" + location.getLocationQualityReport().getNetUseTime()).append("\n");
                sb.append("*****************").append("\n");
                sb.append("回调时间: " + new DateUtil().getY_M_D_H_M_S() + "\n");
                sb.toString();
                return;
            }
            Toast.makeText(AMapLocationStrategy.this.context, "定位失败，loc is null", 0).show();
        }
    };
    private AMapLocationClientOption mLocationOption = null;

    public AMapLocationStrategy(Context context2) {
        this.context = context2;
        initLocation();
    }

    public void initLocation() {
        this.mLocationClient = new AMapLocationClient(this.context.getApplicationContext());
        this.mLocationOption = getDefaultOption();
        this.mLocationClient.setLocationOption(this.mLocationOption);
        this.mLocationClient.setLocationListener(this.mLocationListener);
    }

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        mOption.setGpsFirst(false);
        mOption.setHttpTimeOut(30000);
        mOption.setInterval(2000);
        mOption.setNeedAddress(true);
        mOption.setOnceLocation(false);
        mOption.setOnceLocationLatest(false);
        AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);
        mOption.setSensorEnable(false);
        mOption.setWifiScan(true);
        mOption.setLocationCacheEnable(true);
        mOption.setGeoLanguage(GeoLanguage.DEFAULT);
        return mOption;
    }

    @SuppressLint({"NewApi"})
    private Notification buildNotification() {
        NotificationManager manager = (NotificationManager) this.context.getSystemService("notification");
        if (VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("11111", "GpsNotification", 3);
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setSound(null, null);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
        Builder builder = new Builder(this.context, "11111");
        builder.setContentText("正在后台运行").setWhen(System.currentTimeMillis());
        if (VERSION.SDK_INT >= 16) {
            return builder.build();
        }
        return builder.getNotification();
    }

    public void requestLocation() {
        this.mLocationClient.enableBackgroundLocation(11111, buildNotification());
        this.mLocationClient.setLocationOption(this.mLocationOption);
        this.mLocationClient.startLocation();
    }

    public void stopLocation() {
        if (this.mLocationClient != null) {
            this.mLocationClient.stopLocation();
            this.mLocationClient.disableBackgroundLocation(true);
        }
    }

    public void destoryLocation() {
        if (this.mLocationClient != null) {
            this.mLocationClient.onDestroy();
            this.mLocationClient = null;
        }
    }

    public void setListener(UpdateLocationListener listener2) {
        this.listener = listener2;
    }

    /* access modifiers changed from: private */
    public String getGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case 0:
                return "GPS状态正常";
            case 1:
                return "手机中没有GPS Provider，无法进行GPS定位";
            case 2:
                return "GPS关闭，建议开启GPS，提高定位质量";
            case 3:
                return "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
            case 4:
                return "没有GPS定位权限，建议开启gps定位权限";
            default:
                return str;
        }
    }
}
