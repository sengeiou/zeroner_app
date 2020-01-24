package com.iwown.sport_module.util;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.Constants.LogPath;
import com.iwown.data_link.eventbus.DeviceUpdateWeatherEvent;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.ZipUtil;
import com.iwown.lib_common.file.FileIOUtils;
import com.iwown.sport_module.R;
import com.iwown.sport_module.net.NetFactory;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {
    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
    private static String regex = ".*[a-z0-9A-Z]+.*";

    public static void openNotificationAccess(Context context) {
        context.startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
    }

    public static boolean isMsgNotificationEnabled(Context context) {
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

    public static void writeGpsSD(String name, String msg) {
        try {
            String folderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + LogPath.GPS_PATH;
            String real_path = folderPath + name;
            KLog.e("licl", "real_path: " + real_path);
            if (FileIOUtils.writeFileFromString(real_path, msg)) {
                ZipUtil.zip(real_path, folderPath + name.substring(0, name.length() - 4) + ".zip");
            }
        } catch (Exception e1) {
            ThrowableExtension.printStackTrace(e1);
        }
    }

    public static double[] getLocationInfo(Context mContext) {
        final double[] gps = new double[3];
        LocationManager locationManager = (LocationManager) mContext.getSystemService("location");
        List<String> providers = locationManager.getProviders(true);
        KLog.d("位置提供器： " + providers.toString());
        if (providers.contains("gps")) {
            Location location = locationManager.getLastKnownLocation("gps");
            if (location == null) {
                int index = 0;
                while (location == null) {
                    index++;
                    locationManager.requestLocationUpdates("gps", 60000, 1.0f, new LocationListener() {
                        public void onLocationChanged(Location location) {
                            if (location != null) {
                                gps[0] = location.getLatitude();
                                gps[1] = location.getLongitude();
                                gps[2] = location.getAltitude();
                            }
                        }

                        public void onStatusChanged(String provider, int status, Bundle extras) {
                        }

                        public void onProviderEnabled(String provider) {
                        }

                        public void onProviderDisabled(String provider) {
                        }
                    });
                    if (index >= 5) {
                        break;
                    }
                }
            } else {
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
            }
        }
        return gps;
    }

    public static String[] getCityFromGoogleJson(String answer) throws JSONException {
        JSONArray ja = new JSONObject(answer).getJSONArray("results");
        String city = null;
        String firstLocality = "";
        String country = "";
        String level1 = null;
        String level2 = null;
        if (ja.length() > 0) {
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jb1 = ja.getJSONObject(i);
                KLog.d("weather", "address.matches-->" + jb1.getString("formatted_address").matches(regex));
                JSONArray ja2 = jb1.getJSONArray("address_components");
                for (int j = 0; j < ja2.length(); j++) {
                    JSONObject jb3 = ja2.getJSONObject(j);
                    String long_name = jb3.optString("long_name");
                    String short_name = jb3.optString("short_name");
                    if (j == 0) {
                        firstLocality = long_name;
                    }
                    String locality = jb3.getJSONArray("types").getString(0);
                    if ("country".equals(locality)) {
                        country = short_name;
                    }
                    if ("locality".equals(locality)) {
                        city = long_name;
                    } else if ("administrative_area_level_1".equals(locality)) {
                        level1 = long_name;
                    } else if ("administrative_area_level_2".equals(locality)) {
                        level2 = long_name;
                    }
                }
                if (city != null) {
                    break;
                }
            }
            if (city == null) {
                if (level2 != null) {
                    city = level2;
                } else if (level1 != null) {
                    city = level1;
                }
            }
        }
        KLog.d("locality-->" + firstLocality + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + city + " country: " + country);
        return new String[]{city, firstLocality, country};
    }

    public static String celsiusToFah(String temper) {
        String str = null;
        if (TextUtils.isEmpty(temper)) {
            return str;
        }
        try {
            return String.valueOf(((int) (((double) Float.parseFloat(temper.trim())) * 1.8d)) + 32);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return str;
        }
    }

    public static boolean isLocationEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getApplicationContext().getSystemService("location");
        if (locationManager != null) {
            return locationManager.isProviderEnabled("gps");
        }
        return false;
    }

    public static void getLanguageEnv() {
        String language = Locale.getDefault().getLanguage();
        boolean isSp = false;
        char c = 65535;
        switch (language.hashCode()) {
            case 3121:
                if (language.equals("ar")) {
                    c = 10;
                    break;
                }
                break;
            case 3139:
                if (language.equals("be")) {
                    c = 8;
                    break;
                }
                break;
            case 3201:
                if (language.equals("de")) {
                    c = 6;
                    break;
                }
                break;
            case 3241:
                if (language.equals("en")) {
                    c = 0;
                    break;
                }
                break;
            case 3246:
                if (language.equals("es")) {
                    c = 7;
                    break;
                }
                break;
            case 3276:
                if (language.equals("fr")) {
                    c = 4;
                    break;
                }
                break;
            case 3371:
                if (language.equals("it")) {
                    c = 2;
                    break;
                }
                break;
            case 3383:
                if (language.equals("ja")) {
                    c = 3;
                    break;
                }
                break;
            case 3428:
                if (language.equals("ko")) {
                    c = 9;
                    break;
                }
                break;
            case 3508:
                if (language.equals("nb")) {
                    c = 16;
                    break;
                }
                break;
            case 3518:
                if (language.equals("nl")) {
                    c = 12;
                    break;
                }
                break;
            case 3580:
                if (language.equals("pl")) {
                    c = 13;
                    break;
                }
                break;
            case 3588:
                if (language.equals("pt")) {
                    c = 5;
                    break;
                }
                break;
            case 3651:
                if (language.equals("ru")) {
                    c = 17;
                    break;
                }
                break;
            case 3683:
                if (language.equals("sv")) {
                    c = 18;
                    break;
                }
                break;
            case 3700:
                if (language.equals("th")) {
                    c = 14;
                    break;
                }
                break;
            case 3710:
                if (language.equals("tr")) {
                    c = 15;
                    break;
                }
                break;
            case 3768:
                if (language.equals("vn")) {
                    c = 11;
                    break;
                }
                break;
            case 3886:
                if (language.equals("zh")) {
                    c = 1;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 1:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 2:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 3:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 4:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 5:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 6:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 7:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 8:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 9:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 10:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 11:
                UserConfig.getInstance().setLanguage(language);
                break;
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                isSp = true;
                break;
        }
        if (isSp) {
            UserConfig.getInstance().setLanguage(language);
        } else {
            UserConfig.getInstance().setLanguage("en");
        }
        UserConfig.getInstance().save();
    }

    public static String MD5ToWeather(long time) {
        try {
            MessageDigest md5 = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            char[] charArray = (AppConfigUtil.WEATHER_KEY + String.valueOf(time)).toCharArray();
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

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 16384).versionName.replaceAll("\\.", "");
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return String.valueOf(NetFactory.VERSION_INT);
        }
    }

    public static float doubleToFloat(int accuracy, double num) {
        try {
            return new BigDecimal(num).setScale(accuracy, 4).floatValue();
        } catch (NumberFormatException e) {
            return ((float) ((int) (100.0d * num))) / 100.0f;
        }
    }

    public static String doubleToString(int accuracy, double num) {
        return String.valueOf(new BigDecimal(num).setScale(accuracy, 4).floatValue());
    }

    public static double stringToDouble(String numStr) {
        return new BigDecimal(numStr).doubleValue();
    }

    public static double meterToMile(double meter) {
        return meter / 1.609d;
    }

    public static float m2ft(double meter) {
        return (float) (3.2799999713897705d * meter);
    }

    public static double cm2in(double cm) {
        return 0.394d * cm;
    }

    public static double mi2in(double mi) {
        return 63360.0d * mi;
    }

    public static double ft2in(double ft) {
        return 12.0d * ft;
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

    public static String get02dStr(int value) {
        return String.format("%02d", new Object[]{Integer.valueOf(value)});
    }

    public static String getHH_mm_ss_Str(int value) {
        return String.format("%02d", new Object[]{Integer.valueOf(value / 3600)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf((value / 60) % 60)}) + ":" + String.format("%02d", new Object[]{Integer.valueOf(value % 60)});
    }

    public static double kmToMile(double m) {
        return m * 0.631d;
    }

    public static byte[] hexToBytes(String hexStrings) {
        if (hexStrings == null || hexStrings.equals("")) {
            return null;
        }
        String hexString = hexStrings.toLowerCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];
        String hexDigits = "0123456789abcdef";
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            int h = hexDigits.indexOf(hexChars[pos]) << 4;
            int l = hexDigits.indexOf(hexChars[pos + 1]);
            if (h == -1 || l == -1) {
                return null;
            }
            bytes[i] = (byte) (h | l);
        }
        return bytes;
    }

    public static double mileToKm(float m) {
        return ((double) m) / 0.631d;
    }

    @SuppressLint({"UseSparseArrays"})
    public static int getSporyImgOrName(int k, int sportType) {
        Map<Integer, Integer[]> sportMap = new HashMap<>();
        sportMap.put(Integer.valueOf(1), new Integer[]{Integer.valueOf(R.string.sport_module_walking), Integer.valueOf(0), Integer.valueOf(R.mipmap.walk_on3x)});
        sportMap.put(Integer.valueOf(2), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_situp), Integer.valueOf(0), Integer.valueOf(R.mipmap.situps_on3x)});
        sportMap.put(Integer.valueOf(3), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_pushup), Integer.valueOf(0), Integer.valueOf(R.mipmap.pushups_on3x)});
        sportMap.put(Integer.valueOf(4), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_jump), Integer.valueOf(0), Integer.valueOf(R.mipmap.rope_skipping_on3x)});
        sportMap.put(Integer.valueOf(5), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_mountaineering), Integer.valueOf(0), Integer.valueOf(R.mipmap.climbing_on3x)});
        sportMap.put(Integer.valueOf(6), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_pullup), Integer.valueOf(0), Integer.valueOf(R.mipmap.pullups_on3x)});
        sportMap.put(Integer.valueOf(7), new Integer[]{Integer.valueOf(R.string.sport_module_running), Integer.valueOf(0), Integer.valueOf(R.mipmap.run_on3x)});
        sportMap.put(Integer.valueOf(8), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_hi_low), Integer.valueOf(0), Integer.valueOf(R.mipmap.hi_low_on3x)});
        sportMap.put(Integer.valueOf(128), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_badminton), Integer.valueOf(0), Integer.valueOf(R.mipmap.badminton_on3x)});
        sportMap.put(Integer.valueOf(129), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_basketball), Integer.valueOf(0), Integer.valueOf(R.mipmap.basketball_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_FLOAT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_football), Integer.valueOf(0), Integer.valueOf(R.mipmap.football_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_DOUBLE), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_swimming), Integer.valueOf(0), Integer.valueOf(R.mipmap.swimming_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.LONG_TO_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_volleyball), Integer.valueOf(0), Integer.valueOf(R.mipmap.volleyball_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.LONG_TO_FLOAT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_pingpong), Integer.valueOf(0), Integer.valueOf(R.mipmap.table_tennis_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.LONG_TO_DOUBLE), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_bowling), Integer.valueOf(0), Integer.valueOf(R.mipmap.bowling_on3x)});
        sportMap.put(Integer.valueOf(135), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_tennis), Integer.valueOf(0), Integer.valueOf(R.mipmap.tennis_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.FLOAT_TO_LONG), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_cycling), Integer.valueOf(0), Integer.valueOf(R.mipmap.bike_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.FLOAT_TO_DOUBLE), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_skee), Integer.valueOf(0), Integer.valueOf(R.mipmap.ski_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.DOUBLE_TO_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_skate), Integer.valueOf(0), Integer.valueOf(R.mipmap.skate_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.DOUBLE_TO_LONG), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_climbing), Integer.valueOf(0), Integer.valueOf(R.mipmap.rock_climbing_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.DOUBLE_TO_FLOAT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_gymnasium), Integer.valueOf(0), Integer.valueOf(R.mipmap.fitness_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_BYTE), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_dance), Integer.valueOf(0), Integer.valueOf(R.mipmap.dance_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_CHAR), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_slap), Integer.valueOf(0), Integer.valueOf(R.mipmap.plank_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.INT_TO_SHORT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_bodymechanics), Integer.valueOf(0), Integer.valueOf(R.mipmap.aerobics_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.ADD_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_yoga), Integer.valueOf(0), Integer.valueOf(R.mipmap.yoga_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.SUB_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_shuttlecock), Integer.valueOf(0), Integer.valueOf(R.mipmap.shuttlecock_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.MUL_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_ball_game), Integer.valueOf(0), Integer.valueOf(R.mipmap.ball_games_on)});
        sportMap.put(Integer.valueOf(Opcodes.DIV_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_speed_walking), Integer.valueOf(R.mipmap.fast_walk3x), Integer.valueOf(R.mipmap.fast_walk3x)});
        sportMap.put(Integer.valueOf(Opcodes.REM_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_golf), Integer.valueOf(0), Integer.valueOf(R.mipmap.golf_on3x)});
        sportMap.put(Integer.valueOf(Opcodes.AND_INT), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_canoeing), Integer.valueOf(0), Integer.valueOf(R.mipmap.canoeing_on3x)});
        sportMap.put(Integer.valueOf(150), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_bodymechanics), Integer.valueOf(0), Integer.valueOf(R.mipmap.aerobics_on3x)});
        sportMap.put(Integer.valueOf(4096), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_treadmill), Integer.valueOf(0), Integer.valueOf(R.mipmap.treadmill_on3x)});
        sportMap.put(Integer.valueOf(4097), new Integer[]{Integer.valueOf(R.string.sport_module_sport_plan_spinning), Integer.valueOf(0), Integer.valueOf(R.mipmap.spinning_on3x)});
        sportMap.put(Integer.valueOf(255), new Integer[]{Integer.valueOf(R.string.sport_module_sport_other), Integer.valueOf(R.mipmap.others_on3x), Integer.valueOf(R.mipmap.others_on3x)});
        for (Integer integer : sportMap.keySet()) {
            if (integer.intValue() == sportType) {
                return ((Integer[]) sportMap.get(integer))[k].intValue();
            }
        }
        return -1;
    }

    public static int getTimeZoneInt() {
        int zone = Math.round(((float) Calendar.getInstance().getTimeZone().getRawOffset()) / 3600000.0f);
        KLog.e("licl", "zone: " + zone);
        return zone;
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

    public static double F2C(double f) {
        return ((f - 32.0d) * 5.0d) / 9.0d;
    }

    public static double C2F(double c) {
        return ((9.0d * c) / 5.0d) + 32.0d;
    }

    public static void startAWeatherTask() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                EventBus.getDefault().post(new DeviceUpdateWeatherEvent());
            }
        }, 600, 600, TimeUnit.SECONDS);
    }

    public static void startGetSupportsTask() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            public void run() {
                NetFactory.getInstance().getClient(null).getSupportsByName();
            }
        }, 0, 20, TimeUnit.MINUTES);
    }
}
