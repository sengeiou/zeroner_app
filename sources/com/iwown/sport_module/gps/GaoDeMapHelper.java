package com.iwown.sport_module.gps;

import android.content.Context;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.github.mikephil.charting.utils.Utils;
import com.iwown.lib_common.log.L;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.pojo.Location;
import com.socks.library.KLog;

public class GaoDeMapHelper {
    private static GaoDeMapHelper instance = null;
    private Context mContext;

    public static GaoDeMapHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (GaoDeMapHelper.class) {
                if (instance == null) {
                    instance = new GaoDeMapHelper(context);
                }
            }
        }
        return instance;
    }

    public static GaoDeMapHelper getInstance() {
        return instance;
    }

    public GaoDeMapHelper(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public void getOnceLocation(final MyCallback<Location> cityCallback) {
        AMapLocationClient mlocationClient = new AMapLocationClient(this.mContext);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setWifiActiveScan(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
        mlocationClient.setLocationListener(new AMapLocationListener() {
            public void onLocationChanged(AMapLocation aMapLocation) {
                KLog.e(" 高德定位 getLongitude " + aMapLocation.getLongitude() + " getErrorCode  " + aMapLocation.getErrorCode() + "  getErrorInfo " + aMapLocation.getErrorInfo() + "  getLocationDetail " + aMapLocation.getLocationDetail());
                L.file(" 高德定位 getLongitude " + aMapLocation.getLongitude() + " getErrorCode  " + aMapLocation.getErrorCode() + "  getErrorInfo " + aMapLocation.getErrorInfo() + "  getLocationDetail " + aMapLocation.getLocationDetail(), 4);
                if (aMapLocation.getErrorCode() != 0) {
                    if (cityCallback != null) {
                        cityCallback.onFail(null);
                    }
                } else if (aMapLocation.getLatitude() == Utils.DOUBLE_EPSILON && aMapLocation.getLongitude() == Utils.DOUBLE_EPSILON) {
                    if (cityCallback != null) {
                        cityCallback.onFail(null);
                    }
                } else if (cityCallback != null) {
                    Location location = new Location();
                    location.lat = aMapLocation.getLatitude();
                    location.lng = aMapLocation.getLongitude();
                    location.city = aMapLocation.getCity();
                    location.country = aMapLocation.getCountry();
                    KLog.e(aMapLocation.getLatitude() + "  " + aMapLocation.getLongitude() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + aMapLocation.getCity() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + aMapLocation.getCountry());
                    cityCallback.onSuccess(location);
                }
            }
        });
    }
}
