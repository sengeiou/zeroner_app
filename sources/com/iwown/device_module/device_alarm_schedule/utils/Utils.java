package com.iwown.device_module.device_alarm_schedule.utils;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.google.gson.Gson;
import com.iwown.device_module.R;
import com.iwown.my_module.utility.Constants;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class Utils {
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    public static ArrayList<Integer> sportList = new ArrayList<Integer>() {
        {
            add(Integer.valueOf(1));
            add(Integer.valueOf(2));
            add(Integer.valueOf(3));
            add(Integer.valueOf(4));
            add(Integer.valueOf(5));
            add(Integer.valueOf(6));
            add(Integer.valueOf(7));
            add(Integer.valueOf(128));
            add(Integer.valueOf(129));
            add(Integer.valueOf(Opcodes.INT_TO_FLOAT));
            add(Integer.valueOf(Opcodes.INT_TO_DOUBLE));
            add(Integer.valueOf(Opcodes.LONG_TO_INT));
            add(Integer.valueOf(Opcodes.LONG_TO_FLOAT));
            add(Integer.valueOf(Opcodes.LONG_TO_DOUBLE));
            add(Integer.valueOf(135));
            add(Integer.valueOf(Opcodes.FLOAT_TO_LONG));
            add(Integer.valueOf(Opcodes.FLOAT_TO_DOUBLE));
            add(Integer.valueOf(Opcodes.DOUBLE_TO_INT));
            add(Integer.valueOf(Opcodes.DOUBLE_TO_LONG));
            add(Integer.valueOf(Opcodes.DOUBLE_TO_FLOAT));
            add(Integer.valueOf(Opcodes.INT_TO_BYTE));
            add(Integer.valueOf(Opcodes.INT_TO_CHAR));
            add(Integer.valueOf(Opcodes.INT_TO_SHORT));
            add(Integer.valueOf(Opcodes.ADD_INT));
            add(Integer.valueOf(Opcodes.SUB_INT));
        }
    };

    public static <T> List<T> jsonToList(String json, Class<T[]> clazz) {
        return Arrays.asList((Object[]) new Gson().fromJson(json, clazz));
    }

    public static <T> T[] jsonToArray(String json, Class<T[]> clazz) {
        return (Object[]) new Gson().fromJson(json, clazz);
    }

    public static String getTimeString_OD(int time) {
        return String.format("%02d", new Object[]{Integer.valueOf(time)});
    }

    public static int dip2px(Context paramContext, float paramFloat) {
        return (int) (0.5f + (paramContext.getResources().getDisplayMetrics().density * paramFloat));
    }

    public static int dpToPx(float paramFloat, Context paramContext) {
        return (int) (0.5f + (paramContext.getResources().getDisplayMetrics().density * paramFloat));
    }

    public static int px2dip(Context paramContext, float paramFloat) {
        return (int) (0.5f + (paramFloat / paramContext.getResources().getDisplayMetrics().density));
    }

    public static int sp2px(Context paramContext, float paramFloat) {
        return (int) (0.5f + (paramContext.getResources().getDisplayMetrics().scaledDensity * paramFloat));
    }

    public static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static String MD5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            char[] charArray = (str + "&key=iwown").toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (byte b : md5Bytes) {
                int val = b & 255;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return "";
        }
    }

    public static HashMap<String, Object> getRequestMap(String service, String query_text) {
        HashMap<String, Object> requestParams = new HashMap<>();
        requestParams.put(NotificationCompat.CATEGORY_SERVICE, service);
        requestParams.put("partner", "iwown");
        requestParams.put("query_text", query_text);
        requestParams.put("chartset", "utf-8");
        requestParams.put("sign_type", "md5");
        requestParams.put("encrypt_type", "base64");
        requestParams.put("format", "json");
        requestParams.put("attach", Constants.APPSYSTEM);
        requestParams.put("sign", MD5(createLinkString(paraFilter(requestParams))).toUpperCase());
        return requestParams;
    }

    public static Map<String, Object> paraFilter(Map<String, Object> sArray) {
        Map<String, Object> result = new HashMap<>();
        if (sArray != null && sArray.size() > 0) {
            for (Entry entry : sArray.entrySet()) {
                String value = (String) entry.getValue();
                String key = (String) entry.getKey();
                if (value != null && !value.equals("") && !key.equalsIgnoreCase("sign")) {
                    result.put(key, value);
                }
            }
        }
        return result;
    }

    public static String createLinkString(Map<String, Object> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        StringBuffer prestr = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) params.get(key);
            if (i == keys.size() - 1) {
                prestr.append(key).append("=").append(value);
            } else {
                prestr.append(key).append("=").append(value).append("&");
            }
        }
        return prestr.toString();
    }

    public static double meterToFoot(double meter) {
        return ((double) Math.round((meter / 0.305d) * 1000.0d)) / 1000.0d;
    }

    public static String cmToIn(String cm) {
        return String.valueOf(Math.round(Double.parseDouble(String.valueOf(cm)) / 2.54d));
    }

    public static float cmToInFloat(String cm) {
        try {
            return (float) Math.round(Float.parseFloat(cm) / 2.54f);
        } catch (NumberFormatException e) {
            ThrowableExtension.printStackTrace(e);
            return 0.0f;
        }
    }

    public static int cmToInInt(String cm) {
        return Math.round(((float) Integer.parseInt(String.valueOf(cm))) / 2.54f);
    }

    public static int cmToIn(float cm) {
        return Math.round(cm / 2.54f);
    }

    public static String kgToLB(String kg) {
        try {
            return String.valueOf(Math.round(Double.parseDouble(String.valueOf(kg)) / 0.454d));
        } catch (Exception e) {
            return "0";
        }
    }

    public static String inToCm(String in) {
        return String.valueOf(Math.round(Double.parseDouble(String.valueOf(in)) * 2.54d));
    }

    public static float inToCmFloat(int in) {
        return ((float) in) * 2.54f;
    }

    public static String lbToKg(String lb) {
        try {
            return String.valueOf(Math.round(Double.parseDouble(String.valueOf(lb)) * 0.454d));
        } catch (Exception e) {
            return "0";
        }
    }

    public static double kmToMile(double m) {
        return m * 0.631d;
    }

    public static double meterToMile(double meter) {
        try {
            return Double.parseDouble(String.valueOf(meter)) / 1609.344d;
        } catch (Exception e) {
            return com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        }
    }

    public static String meterToKM(String meter) {
        try {
            return new DecimalFormat("0.000").format(Double.parseDouble(String.valueOf(meter)) / 1000.0d);
        } catch (Exception e) {
            return "0";
        }
    }

    public static String cmToFt(String ft) {
        try {
            return new BigDecimal(Double.parseDouble(String.valueOf(ft)) * 0.0328084d).setScale(2, 4).toString();
        } catch (Exception e) {
            return "0";
        }
    }

    public static String ftToCm(String cm) {
        try {
            return String.valueOf(Math.round(Double.parseDouble(String.valueOf(cm)) / 0.0328084d));
        } catch (Exception e) {
            return "0";
        }
    }

    public static float getBMR(int age, boolean isBoy) {
        char c;
        if (isBoy) {
            c = 0;
        } else {
            c = 1;
        }
        Map<Integer[], Float[]> map = new HashMap<>();
        map.put(new Integer[]{Integer.valueOf(0), Integer.valueOf(12)}, new Float[]{Float.valueOf(43.0f), Float.valueOf(42.0f)});
        map.put(new Integer[]{Integer.valueOf(13), Integer.valueOf(14)}, new Float[]{Float.valueOf(42.3f), Float.valueOf(40.3f)});
        map.put(new Integer[]{Integer.valueOf(15), Integer.valueOf(16)}, new Float[]{Float.valueOf(41.8f), Float.valueOf(37.9f)});
        map.put(new Integer[]{Integer.valueOf(17), Integer.valueOf(18)}, new Float[]{Float.valueOf(40.8f), Float.valueOf(36.3f)});
        map.put(new Integer[]{Integer.valueOf(19), Integer.valueOf(19)}, new Float[]{Float.valueOf(39.2f), Float.valueOf(35.5f)});
        map.put(new Integer[]{Integer.valueOf(20), Integer.valueOf(24)}, new Float[]{Float.valueOf(38.6f), Float.valueOf(35.3f)});
        map.put(new Integer[]{Integer.valueOf(25), Integer.valueOf(29)}, new Float[]{Float.valueOf(37.5f), Float.valueOf(35.2f)});
        map.put(new Integer[]{Integer.valueOf(30), Integer.valueOf(34)}, new Float[]{Float.valueOf(36.8f), Float.valueOf(35.1f)});
        map.put(new Integer[]{Integer.valueOf(35), Integer.valueOf(39)}, new Float[]{Float.valueOf(36.5f), Float.valueOf(35.0f)});
        map.put(new Integer[]{Integer.valueOf(40), Integer.valueOf(44)}, new Float[]{Float.valueOf(36.3f), Float.valueOf(34.9f)});
        map.put(new Integer[]{Integer.valueOf(45), Integer.valueOf(49)}, new Float[]{Float.valueOf(36.2f), Float.valueOf(34.5f)});
        map.put(new Integer[]{Integer.valueOf(50), Integer.valueOf(54)}, new Float[]{Float.valueOf(35.8f), Float.valueOf(33.9f)});
        map.put(new Integer[]{Integer.valueOf(55), Integer.valueOf(59)}, new Float[]{Float.valueOf(35.4f), Float.valueOf(33.3f)});
        map.put(new Integer[]{Integer.valueOf(60), Integer.valueOf(64)}, new Float[]{Float.valueOf(34.9f), Float.valueOf(32.7f)});
        map.put(new Integer[]{Integer.valueOf(65), Integer.valueOf(69)}, new Float[]{Float.valueOf(34.4f), Float.valueOf(32.2f)});
        map.put(new Integer[]{Integer.valueOf(70), Integer.valueOf(74)}, new Float[]{Float.valueOf(33.8f), Float.valueOf(31.7f)});
        map.put(new Integer[]{Integer.valueOf(75), Integer.valueOf(79)}, new Float[]{Float.valueOf(33.2f), Float.valueOf(31.3f)});
        map.put(new Integer[]{Integer.valueOf(80), Integer.valueOf(120)}, new Float[]{Float.valueOf(33.0f), Float.valueOf(30.9f)});
        for (Entry<Integer[], Float[]> ent : map.entrySet()) {
            if (((Integer[]) ent.getKey())[0].intValue() <= age && ((Integer[]) ent.getKey())[1].intValue() >= age) {
                return ((Float[]) ent.getValue())[c].floatValue();
            }
        }
        return 0.0f;
    }

    @SuppressLint({"UseSparseArrays"})
    public static float getPAL(int strength, boolean isBoy) {
        char c;
        float pal = 0.0f;
        if (isBoy) {
            c = 0;
        } else {
            c = 1;
        }
        Map<Integer, Float[]> palMap = new HashMap<>();
        palMap.put(Integer.valueOf(0), new Float[]{Float.valueOf(1.55f), Float.valueOf(1.56f)});
        palMap.put(Integer.valueOf(1), new Float[]{Float.valueOf(1.78f), Float.valueOf(1.64f)});
        palMap.put(Integer.valueOf(2), new Float[]{Float.valueOf(2.1f), Float.valueOf(1.82f)});
        for (Entry<Integer, Float[]> ent : palMap.entrySet()) {
            if (((Integer) ent.getKey()).intValue() == strength) {
                pal = ((Float[]) ent.getValue())[c].floatValue();
            }
        }
        return pal;
    }

    @SuppressLint({"UseSparseArrays"})
    public static String getRepeat(Context context, int i) {
        Map<Integer, String> repeatMap = new HashMap<>();
        String repeat = null;
        repeatMap.put(Integer.valueOf(1), context.getResources().getString(R.string.device_module_schedule_mon));
        repeatMap.put(Integer.valueOf(2), context.getResources().getString(R.string.device_module_schedule_tue));
        repeatMap.put(Integer.valueOf(3), context.getResources().getString(R.string.device_module_schedule_wes));
        repeatMap.put(Integer.valueOf(4), context.getResources().getString(R.string.device_module_schedule_thur));
        repeatMap.put(Integer.valueOf(5), context.getResources().getString(R.string.device_module_schedule_fir));
        repeatMap.put(Integer.valueOf(6), context.getResources().getString(R.string.device_module_schedule_sat));
        repeatMap.put(Integer.valueOf(7), context.getResources().getString(R.string.device_module_schedule_sun));
        for (Entry<Integer, String> ent : repeatMap.entrySet()) {
            if (((Integer) ent.getKey()).equals(Integer.valueOf(i))) {
                repeat = (String) ent.getValue();
            }
        }
        return repeat;
    }

    @SuppressLint({"UseSparseArrays"})
    public static double getCal(int sportType) {
        double cal = com.github.mikephil.charting.utils.Utils.DOUBLE_EPSILON;
        Map<Integer, Double[]> calMap = new HashMap<>();
        calMap.put(Integer.valueOf(1), new Double[]{Double.valueOf(3.1d), Double.valueOf(3.6d), Double.valueOf(4.0d)});
        calMap.put(Integer.valueOf(2), new Double[]{Double.valueOf(0.156d), Double.valueOf(0.156d), Double.valueOf(0.156d)});
        calMap.put(Integer.valueOf(3), new Double[]{Double.valueOf(0.22285714285714286d), Double.valueOf(0.22285714285714286d), Double.valueOf(0.22285714285714286d)});
        calMap.put(Integer.valueOf(4), new Double[]{Double.valueOf(0.078d), Double.valueOf(0.078d), Double.valueOf(0.078d)});
        calMap.put(Integer.valueOf(5), new Double[]{Double.valueOf(0.39d), Double.valueOf(0.39d), Double.valueOf(0.39d)});
        calMap.put(Integer.valueOf(6), new Double[]{Double.valueOf(0.39d), Double.valueOf(0.39d), Double.valueOf(0.39d)});
        calMap.put(Integer.valueOf(7), new Double[]{Double.valueOf(3.1d), Double.valueOf(3.6d), Double.valueOf(4.0d)});
        calMap.put(Integer.valueOf(128), new Double[]{Double.valueOf(4.5d), Double.valueOf(4.5d), Double.valueOf(4.5d)});
        calMap.put(Integer.valueOf(129), new Double[]{Double.valueOf(4.5d), Double.valueOf(5.9d), Double.valueOf(7.8d)});
        calMap.put(Integer.valueOf(Opcodes.INT_TO_FLOAT), new Double[]{Double.valueOf(6.9d), Double.valueOf(8.5d), Double.valueOf(10.0d)});
        calMap.put(Integer.valueOf(Opcodes.INT_TO_DOUBLE), new Double[]{Double.valueOf(5.9d), Double.valueOf(7.8d), Double.valueOf(10.0d)});
        calMap.put(Integer.valueOf(Opcodes.LONG_TO_INT), new Double[]{Double.valueOf(3.1d), Double.valueOf(4.0d), Double.valueOf(5.0d)});
        calMap.put(Integer.valueOf(Opcodes.LONG_TO_FLOAT), new Double[]{Double.valueOf(4.0d), Double.valueOf(4.0d), Double.valueOf(4.0d)});
        calMap.put(Integer.valueOf(Opcodes.LONG_TO_DOUBLE), new Double[]{Double.valueOf(3.1d), Double.valueOf(3.1d), Double.valueOf(3.1d)});
        calMap.put(Integer.valueOf(135), new Double[]{Double.valueOf(5.0d), Double.valueOf(5.9d), Double.valueOf(7.8d)});
        calMap.put(Integer.valueOf(Opcodes.FLOAT_TO_LONG), new Double[]{Double.valueOf(4.0d), Double.valueOf(5.9d), Double.valueOf(7.8d)});
        calMap.put(Integer.valueOf(Opcodes.FLOAT_TO_DOUBLE), new Double[]{Double.valueOf(9.5d), Double.valueOf(9.5d), Double.valueOf(9.5d)});
        calMap.put(Integer.valueOf(Opcodes.DOUBLE_TO_INT), new Double[]{Double.valueOf(6.9d), Double.valueOf(6.9d), Double.valueOf(6.9d)});
        calMap.put(Integer.valueOf(Opcodes.DOUBLE_TO_LONG), new Double[]{Double.valueOf(7.8d), Double.valueOf(10.9d), Double.valueOf(10.9d)});
        calMap.put(Integer.valueOf(Opcodes.DOUBLE_TO_FLOAT), new Double[]{Double.valueOf(6.0d), Double.valueOf(6.0d), Double.valueOf(6.0d)});
        calMap.put(Integer.valueOf(Opcodes.INT_TO_BYTE), new Double[]{Double.valueOf(3.1d), Double.valueOf(5.9d), Double.valueOf(6.9d)});
        calMap.put(Integer.valueOf(Opcodes.INT_TO_CHAR), new Double[]{Double.valueOf(7.8d), Double.valueOf(7.8d), Double.valueOf(7.8d)});
        calMap.put(Integer.valueOf(Opcodes.INT_TO_SHORT), new Double[]{Double.valueOf(4.7d), Double.valueOf(4.7d), Double.valueOf(4.7d)});
        calMap.put(Integer.valueOf(Opcodes.ADD_INT), new Double[]{Double.valueOf(4.0d), Double.valueOf(4.0d), Double.valueOf(4.0d)});
        calMap.put(Integer.valueOf(Opcodes.SUB_INT), new Double[]{Double.valueOf(5.0d), Double.valueOf(5.0d), Double.valueOf(5.0d)});
        for (Entry<Integer, Double[]> ent : calMap.entrySet()) {
            if (((Integer) ent.getKey()).intValue() == sportType) {
                cal = ((Double[]) ent.getValue())[1].doubleValue();
            }
        }
        return cal;
    }

    public static boolean isTime(int type) {
        for (int s : new int[]{2, 3, 4, 5, 6}) {
            if (s == type) {
                return true;
            }
        }
        return false;
    }

    public static boolean shouldUseY_M_D() {
        String language = Locale.getDefault().getLanguage();
        char c = 65535;
        switch (language.hashCode()) {
            case 3383:
                if (language.equals("ja")) {
                    c = 1;
                    break;
                }
                break;
            case 3428:
                if (language.equals("ko")) {
                    c = 2;
                    break;
                }
                break;
            case 3886:
                if (language.equals("zh")) {
                    c = 0;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    public static String getFloat1String(double num) {
        return new DecimalFormat("#.0").format(num);
    }

    public static float doubleToFloat(int accuracy, double num) {
        return new BigDecimal(num).setScale(accuracy, 4).floatValue();
    }

    public static String doubleToString(int accuracy, double num) {
        return String.valueOf(new BigDecimal(num).setScale(accuracy, 4).floatValue());
    }

    public static double stringToDouble(String numStr) {
        return new BigDecimal(numStr).doubleValue();
    }

    public static int getDaysOfMonth(int year, int month) {
        int[] DAYS_PER_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2) {
            return isLeap(year) ? 29 : 28;
        }
        return DAYS_PER_MONTH[month - 1];
    }

    public static String getRepeatString(Context context, byte weakRepeat) {
        String[] daysOfWeek = context.getResources().getStringArray(R.array.device_module_day_of_week);
        StringBuilder sb = new StringBuilder();
        if (weakRepeat == 255 || weakRepeat == Byte.MAX_VALUE) {
            sb.append(context.getString(R.string.device_module_every_day));
            return sb.toString();
        }
        if ((weakRepeat & 64) != 0) {
            sb.append(daysOfWeek[0]);
            sb.append(",");
        }
        if ((weakRepeat & 32) != 0) {
            sb.append(daysOfWeek[1]);
            sb.append(",");
        }
        if ((weakRepeat & 16) != 0) {
            sb.append(daysOfWeek[2]);
            sb.append(",");
        }
        if ((weakRepeat & 8) != 0) {
            sb.append(daysOfWeek[3]);
            sb.append(",");
        }
        if ((weakRepeat & 4) != 0) {
            sb.append(daysOfWeek[4]);
            sb.append(",");
        }
        if ((weakRepeat & 2) != 0) {
            sb.append(daysOfWeek[5]);
            sb.append(",");
        }
        if ((weakRepeat & 1) != 0) {
            sb.append(daysOfWeek[6]);
            sb.append(",");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public static int kgToLbs(float kg) {
        return Math.round(new BigDecimal(((double) kg) * 2.206d).floatValue());
    }

    public static float lbsToKg(int lbs) {
        return new BigDecimal(((double) lbs) * 0.4532d).setScale(1, 4).floatValue();
    }

    public static boolean isToday(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        if (year == calendar.get(1) && month == calendar.get(2) + 1 && day == calendar.get(5)) {
            return true;
        }
        return false;
    }

    public static String getPhoneInfo(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 16384);
            String model = Build.MODEL;
            String models = Build.BRAND;
            return "手机型号: " + model + "--手机品牌: " + models + " --系统版本: " + VERSION.RELEASE + "--app版本: " + pi.versionName;
        } catch (NameNotFoundException e) {
            ThrowableExtension.printStackTrace(e);
            return "";
        }
    }

    public static boolean isNotificationEnable(Context context) {
        String pkgName = context.getPackageName();
        String flat = Secure.getString(context.getContentResolver(), ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            String[] names = flat.split(":");
            for (String unflattenFromString : names) {
                ComponentName cn2 = ComponentName.unflattenFromString(unflattenFromString);
                if (cn2 != null && TextUtils.equals(pkgName, cn2.getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String MD5ToWeather(long time) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            char[] charArray = ("iwowna47d69d605ce4a039d320d680798ef33" + String.valueOf(time)).toCharArray();
            byte[] byteArray = new byte[charArray.length];
            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }
            byte[] md5Bytes = md5.digest(byteArray);
            StringBuffer hexValue = new StringBuffer();
            for (byte b : md5Bytes) {
                int val = b & 255;
                if (val < 16) {
                    hexValue.append("0");
                }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return "";
        }
    }

    public static void goClient(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
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

    public static int getTimeZoneInt() {
        return Math.round(((float) Calendar.getInstance().getTimeZone().getRawOffset()) / 3600000.0f);
    }

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

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            String hv = Integer.toHexString(b & 255);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
