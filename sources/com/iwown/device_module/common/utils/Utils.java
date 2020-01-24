package com.iwown.device_module.common.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

public class Utils {
    public static String getFromAssets(Context context, String fileName) {
        try {
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(fileName)));
            String str = "";
            String Result = "";
            while (true) {
                String line = bufReader.readLine();
                if (line == null) {
                    return Result;
                }
                Result = Result + line;
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static int getClientVersionCode(Context context) {
        int versionCode = -1;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
            return versionCode;
        }
    }

    public static double[] getLocationInfo(Context mContext) {
        double[] gps = new double[3];
        LocationManager locationManager = (LocationManager) mContext.getSystemService("location");
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains("gps")) {
            Location location = locationManager.getLastKnownLocation("gps");
            if (location != null) {
                gps[0] = location.getLatitude();
                gps[1] = location.getLongitude();
                gps[2] = location.getAltitude();
                return gps;
            }
        }
        if (providers.contains("network")) {
            Location location2 = locationManager.getLastKnownLocation("network");
            if (location2 != null) {
                gps[0] = location2.getLatitude();
                gps[1] = location2.getLongitude();
                gps[2] = location2.getAltitude();
                return gps;
            }
        }
        return null;
    }

    public static String doubleToString(int accuracy, double num) {
        return String.valueOf(new BigDecimal(num).setScale(accuracy, 4).floatValue());
    }

    public static int getTimeZoneInt() {
        return Math.round(((float) Calendar.getInstance().getTimeZone().getRawOffset()) / 3600000.0f);
    }

    public static int getTimeZoneInt(float lat) {
        return Math.round(lat / 15.0f);
    }
}
