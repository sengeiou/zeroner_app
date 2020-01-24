package com.iwown.my_module.utility;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtility {
    public static int dip2px(Context paramContext, float paramFloat) {
        return (int) (0.5f + (paramContext.getResources().getDisplayMetrics().density * paramFloat));
    }

    public static int px2dip(Context paramContext, float paramFloat) {
        return (int) (0.5f + (paramFloat / paramContext.getResources().getDisplayMetrics().density));
    }

    public static int sp2px(Context paramContext, float paramFloat) {
        return (int) (0.5f + (paramContext.getResources().getDisplayMetrics().scaledDensity * paramFloat));
    }

    public static String getLocation(Context mContext) {
        String locations = "";
        boolean isHavade = false;
        try {
            LocationManager locationManager = (LocationManager) mContext.getSystemService("location");
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains("gps")) {
                Location location = locationManager.getLastKnownLocation("gps");
                if (location != null) {
                    isHavade = true;
                    locations = location.getLatitude() + "," + location.getLongitude();
                }
            }
            if (isHavade || !providers.contains("network")) {
                return locations;
            }
            Location location2 = locationManager.getLastKnownLocation("network");
            if (location2 != null) {
                return location2.getLatitude() + "," + location2.getLongitude();
            }
            return locations;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return locations;
        }
    }

    public static boolean hasChinese(String source) {
        Matcher m = Pattern.compile("([\\u4E00-\\u9FA5]*+)").matcher(source);
        boolean hasChinese = false;
        while (m.find()) {
            if (!"".equals(m.group(1))) {
                hasChinese = true;
            }
        }
        return hasChinese;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            NetworkInfo mNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static float cmToInFloat(String cm) {
        try {
            return (float) Math.round(Float.parseFloat(cm) / 2.54f);
        } catch (NumberFormatException e) {
            ThrowableExtension.printStackTrace(e);
            return 0.0f;
        }
    }

    public static String cmToFt(String ft) {
        try {
            return new BigDecimal(Double.parseDouble(String.valueOf(ft)) * 0.0328084d).setScale(2, 4).toString();
        } catch (Exception e) {
            return "0";
        }
    }

    public static long cmToIn(double cm) {
        return Math.round(cm / 2.54d);
    }

    public static String ftToCm(String cm) {
        try {
            return String.valueOf(Math.round(Double.parseDouble(String.valueOf(cm)) / 0.0328084d));
        } catch (Exception e) {
            return "0";
        }
    }

    public static int kgToLbs(float kg) {
        return Math.round(new BigDecimal(((double) kg) * 2.206d).floatValue());
    }

    public static String kgToLB(String kg) {
        try {
            return String.valueOf(Math.round(Double.parseDouble(String.valueOf(kg)) / 0.454d));
        } catch (Exception e) {
            return "0";
        }
    }

    public static float lbsToKg(int lbs) {
        return new BigDecimal(((double) lbs) * 0.4532d).setScale(1, 4).floatValue();
    }

    public static String lbToKg(String lb) {
        try {
            return String.valueOf(Math.round(Double.parseDouble(String.valueOf(lb)) * 0.454d));
        } catch (Exception e) {
            return "0";
        }
    }

    public static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static String doubleToString(int accuracy, double num) {
        return String.valueOf(new BigDecimal(num).setScale(accuracy, 4).floatValue());
    }

    public static float doubleToFloat(int accuracy, double num) {
        return new BigDecimal(num).setScale(accuracy, 4).floatValue();
    }

    public static String getClientVersionName(Context context) {
        String versionName = "";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
            return versionName;
        }
    }
}
