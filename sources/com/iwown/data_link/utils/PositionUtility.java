package com.iwown.data_link.utils;

import android.text.TextUtils;
import com.iwown.data_link.sport_data.gps.Gps;
import java.util.Locale;

public class PositionUtility {
    public static final String BAIDU_LBS_TYPE = "bd09ll";
    public static double a = 6378245.0d;
    public static double ee = 0.006693421622965943d;
    public static double pi = 3.141592653589793d;

    public static Gps gps84_To_Gcj02(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            Gps gps = new Gps(lat, lon);
            return gps;
        }
        double dLat = transformLat(lon - 105.0d, lat - 35.0d);
        double dLon = transformLon(lon - 105.0d, lat - 35.0d);
        double radLat = (lat / 180.0d) * pi;
        double magic = Math.sin(radLat);
        double magic2 = 1.0d - ((ee * magic) * magic);
        double sqrtMagic = Math.sqrt(magic2);
        Gps gps2 = new Gps(lat + ((180.0d * dLat) / (((a * (1.0d - ee)) / (magic2 * sqrtMagic)) * pi)), lon + ((180.0d * dLon) / (((a / sqrtMagic) * Math.cos(radLat)) * pi)));
        return gps2;
    }

    public static boolean isInChina() {
        String country = "CN";
        if (TextUtils.isEmpty(country)) {
            country = Locale.getDefault().getCountry();
        }
        if ("CN".equals(country)) {
            return true;
        }
        return false;
    }

    public static Gps gps84_2_Gcj02(double lat, double lon) {
        if (isInChina()) {
            double dLat = transformLat(lon - 105.0d, lat - 35.0d);
            double dLon = transformLon(lon - 105.0d, lat - 35.0d);
            double radLat = (lat / 180.0d) * pi;
            double magic = Math.sin(radLat);
            double magic2 = 1.0d - ((ee * magic) * magic);
            double sqrtMagic = Math.sqrt(magic2);
            Gps gps = new Gps(lat + ((180.0d * dLat) / (((a * (1.0d - ee)) / (magic2 * sqrtMagic)) * pi)), lon + ((180.0d * dLon) / (((a / sqrtMagic) * Math.cos(radLat)) * pi)));
            return gps;
        }
        Gps gps2 = new Gps(lat, lon);
        return gps2;
    }

    public static Gps gcj_To_Gps84(double lat, double lon) {
        Gps gps = transform(lat, lon);
        return new Gps((lat * 2.0d) - gps.getWgLat(), (lon * 2.0d) - gps.getWgLon());
    }

    public static Gps gcj02_To_Bd09(double gg_lat, double gg_lon) {
        double x = gg_lon;
        double y = gg_lat;
        double z = Math.sqrt((x * x) + (y * y)) + (2.0E-5d * Math.sin(pi * y));
        double theta = Math.atan2(y, x) + (3.0E-6d * Math.cos(pi * x));
        return new Gps((Math.sin(theta) * z) + 0.006d, (Math.cos(theta) * z) + 0.0065d);
    }

    public static Gps bd09_To_Gcj02(double bd_lat, double bd_lon) {
        double x = bd_lon - 0.0065d;
        double y = bd_lat - 0.006d;
        double z = Math.sqrt((x * x) + (y * y)) - (2.0E-5d * Math.sin(pi * y));
        double theta = Math.atan2(y, x) - (3.0E-6d * Math.cos(pi * x));
        return new Gps(z * Math.sin(theta), z * Math.cos(theta));
    }

    public static Gps bd09_To_Gps84(double bd_lat, double bd_lon) {
        Gps gcj02 = bd09_To_Gcj02(bd_lat, bd_lon);
        return gcj_To_Gps84(gcj02.getWgLat(), gcj02.getWgLon());
    }

    public static boolean outOfChina(double lat, double lon) {
        if (lon < 72.004d || lon > 137.8347d || lat < 0.8293d || lat > 55.8271d) {
            return true;
        }
        return false;
    }

    public static Gps transform(double lat, double lon) {
        if (outOfChina(lat, lon)) {
            Gps gps = new Gps(lat, lon);
            return gps;
        }
        double dLat = transformLat(lon - 105.0d, lat - 35.0d);
        double dLon = transformLon(lon - 105.0d, lat - 35.0d);
        double radLat = (lat / 180.0d) * pi;
        double magic = Math.sin(radLat);
        double magic2 = 1.0d - ((ee * magic) * magic);
        double sqrtMagic = Math.sqrt(magic2);
        Gps gps2 = new Gps(lat + ((180.0d * dLat) / (((a * (1.0d - ee)) / (magic2 * sqrtMagic)) * pi)), lon + ((180.0d * dLon) / (((a / sqrtMagic) * Math.cos(radLat)) * pi)));
        return gps2;
    }

    public static double transformLat(double x, double y) {
        return -100.0d + (2.0d * x) + (3.0d * y) + (0.2d * y * y) + (0.1d * x * y) + (0.2d * Math.sqrt(Math.abs(x))) + ((((20.0d * Math.sin((6.0d * x) * pi)) + (20.0d * Math.sin((2.0d * x) * pi))) * 2.0d) / 3.0d) + ((((20.0d * Math.sin(pi * y)) + (40.0d * Math.sin((y / 3.0d) * pi))) * 2.0d) / 3.0d) + ((((160.0d * Math.sin((y / 12.0d) * pi)) + (320.0d * Math.sin((pi * y) / 30.0d))) * 2.0d) / 3.0d);
    }

    public static double transformLon(double x, double y) {
        return 300.0d + x + (2.0d * y) + (0.1d * x * x) + (0.1d * x * y) + (0.1d * Math.sqrt(Math.abs(x))) + ((((20.0d * Math.sin((6.0d * x) * pi)) + (20.0d * Math.sin((2.0d * x) * pi))) * 2.0d) / 3.0d) + ((((20.0d * Math.sin(pi * x)) + (40.0d * Math.sin((x / 3.0d) * pi))) * 2.0d) / 3.0d) + ((((150.0d * Math.sin((x / 12.0d) * pi)) + (300.0d * Math.sin((x / 30.0d) * pi))) * 2.0d) / 3.0d);
    }
}
