package com.iwown.data_link.user_pre;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.iwown.lib_common.PreUtil;
import com.socks.library.KLog;

public class UserConfigDAO {
    public static final String CONDITION = "com.kunekt.healthy.condition";
    public static final String COUNTRY = "com.healthy.healthy.country";
    public static final String ECG_AI_COUNT = "com.iwown.ECG_AI_COUNT";
    public static final String ECG_AI_COUNT_MONTH = "com.iwown.ECG_AI_COUNT_MONTH";
    public static final String Ecg_Mv_Default = "com.iwown.Ecg_Mv_Default";
    public static final String Ecg_Speed_Defaut = "com.iwown.Ecg_Speed_Default";
    public static final String HAS_GUIDE_HOME = "com.kunekt.healthy.HAS_GUIDE_HOME";
    public static final String HAS_GUIDE_HOME_REFRESH = "com.kunekt.healthy.HAS_GUIDE_HOME_REFRESH";
    public static final String HAS_GUIDE_I6hrc = "com.healthy.HAS_GUIDE_I6hrc";
    public static final String HAS_GUIDE_I7a = "com.healthy.HAS_GUIDE_I7a";
    public static final String HAS_GUIDE_SPORT = "com.healthy.healthy.HAS_GUIDE_SPORT";
    public static final String HAS_GUIDE_r1 = "com.healthy.HAS_GUIDE_r1";
    public static final String LANGUAGE = "com.healthy.healthy.language";
    public static final String LAST_UPLOAD_SPORT_TIME = "com.healthy.healthy.LAST_UPLOAD_SPORT_TIME";
    public static final String LATITUDE = "com.kunekt.healthy.latitude";
    public static final String LOCALITY = "com.kunekt.healthy.locality";
    public static final String LONGITUDE = "com.kunekt.healthy.longitude";
    public static final String Last_Req_Weather_Time = "com.healthy.Last_Req_Weather_Time";
    public static final String NEWUID = "com.kunekt.healthy.NEWUID";
    private static final String PREFERENCE_FILE_NAME = "UserConfig_Preferences_5.0";
    public static final String PremissionDialog = "com.iwown.premissionDialog";
    public static final String Sport_Module_City_Name = "com.iwown.Sport_Module_City_Name";
    public static final String TEMPERATURE = "com.kunekt.healthy.temperature";
    public static final String User_Switch_Tab = "com.healthy.User_Switch_Tab";
    public static final String WEATHER_TIME = "com.kunekt.healthy.weather_time";
    public static final String Weather_24h = "com.healthy.Weather_24h";
    public static final String Weather_Go = "com.healthy.weatherGo";
    public static final String Welcome_Text = "com.healthy.Welcome_Text";
    public static final String pm25 = "com.healthy.pm25";
    public static final String weatherLocation = "com.healthy.weatherLocation";
    public static final String weatherTimeOut = "com.healthy.weatherTimeOut";

    public static void readUserConfig(UserConfig userConfig) {
        SharedPreferences dataHolder = PreUtil.getSharedPreferences(PREFERENCE_FILE_NAME, userConfig.getContext());
        KLog.e("readUserConfig", "readUserConfig" + dataHolder.toString());
        userConfig.setLanguage(dataHolder.getString("com.healthy.healthy.language", userConfig.getLanguage()));
        userConfig.setLongitude(dataHolder.getString("com.kunekt.healthy.longitude", userConfig.getLongitude()));
        userConfig.setLatitude(dataHolder.getString("com.kunekt.healthy.latitude", userConfig.getLatitude()));
        userConfig.setLocality(dataHolder.getString("com.kunekt.healthy.locality", userConfig.getLocality()));
        userConfig.setTemperature(dataHolder.getString("com.kunekt.healthy.temperature", userConfig.getTemperature()));
        userConfig.setCondition(dataHolder.getString("com.kunekt.healthy.condition", userConfig.getCondition()));
        userConfig.setWeatherTime(dataHolder.getLong("com.kunekt.healthy.weather_time", userConfig.getWeatherTime()));
        userConfig.setCountry(dataHolder.getString("com.healthy.healthy.country", userConfig.getCountry()));
        userConfig.setLast_up_sport_time(dataHolder.getLong(LAST_UPLOAD_SPORT_TIME, userConfig.getLast_up_sport_time()));
        userConfig.setHasGuideHome(dataHolder.getBoolean(HAS_GUIDE_HOME, userConfig.isHasGuideHome()));
        userConfig.setHasGuideSport(dataHolder.getBoolean(HAS_GUIDE_SPORT, userConfig.isHasGuideSport()));
        userConfig.setHasGuideHomeRefresh(dataHolder.getBoolean(HAS_GUIDE_HOME_REFRESH, userConfig.isHasGuideHomeRefresh()));
        userConfig.setTab(dataHolder.getInt(User_Switch_Tab, userConfig.getTab()));
        userConfig.setWeather24h(dataHolder.getString(Weather_24h, userConfig.getWeather24h()));
        userConfig.setWelcom_text(dataHolder.getString(Welcome_Text, userConfig.getWelcom_text()));
        userConfig.setHasGuideI7A(dataHolder.getBoolean(HAS_GUIDE_I7a, userConfig.isHasGuideI7A()));
        userConfig.setHasGuideI6HRC(dataHolder.getBoolean(HAS_GUIDE_I6hrc, userConfig.isHasGuideI6HRC()));
        userConfig.setHasGuideR1(dataHolder.getBoolean(HAS_GUIDE_r1, userConfig.isHasGuideR1()));
        userConfig.setWeatherGo(dataHolder.getLong(Weather_Go, userConfig.getWeatherGo()));
        userConfig.setWeatherLocation(dataHolder.getString(weatherLocation, userConfig.getWeatherLocation()));
        userConfig.setWeatherTimeout(dataHolder.getLong(weatherTimeOut, userConfig.getWeatherTimeout()));
        userConfig.setEcgCount(dataHolder.getInt(ECG_AI_COUNT, userConfig.getEcgCount()));
        userConfig.setEcgMonth(dataHolder.getString(ECG_AI_COUNT_MONTH, userConfig.getEcgMonth()));
        userConfig.setPm25(dataHolder.getString(pm25, userConfig.getPm25()));
        userConfig.setEcg_Mv_Default(dataHolder.getInt(Ecg_Mv_Default, userConfig.getEcg_Mv_Default()));
        userConfig.setEcg_Speed_Defaut(dataHolder.getBoolean(Ecg_Speed_Defaut, userConfig.isEcg_Speed_Defaut()));
        userConfig.setGoogleCityName(dataHolder.getString(Sport_Module_City_Name, userConfig.getGoogleCityName()));
        userConfig.setPremissionDialog(dataHolder.getBoolean(PremissionDialog, userConfig.isPremissionDialog()));
    }

    public static void saveUserConfig(UserConfig userConfig) {
        Editor editor = PreUtil.getSharedEditor(PREFERENCE_FILE_NAME, userConfig.getContext());
        editor.putString("com.healthy.healthy.language", userConfig.getLanguage());
        editor.putString("com.kunekt.healthy.longitude", userConfig.getLongitude());
        editor.putString("com.kunekt.healthy.latitude", userConfig.getLatitude());
        editor.putString("com.kunekt.healthy.locality", userConfig.getLocality());
        editor.putString("com.kunekt.healthy.temperature", userConfig.getTemperature());
        editor.putString("com.kunekt.healthy.condition", userConfig.getCondition());
        editor.putLong("com.kunekt.healthy.weather_time", userConfig.getWeatherTime());
        editor.putLong(LAST_UPLOAD_SPORT_TIME, userConfig.getLast_up_sport_time());
        editor.putString("com.healthy.healthy.country", userConfig.getCountry());
        editor.putBoolean(HAS_GUIDE_SPORT, userConfig.isHasGuideSport());
        editor.putBoolean(HAS_GUIDE_HOME, userConfig.isHasGuideHome());
        editor.putBoolean(HAS_GUIDE_HOME_REFRESH, userConfig.isHasGuideHomeRefresh());
        editor.putInt(User_Switch_Tab, userConfig.getTab());
        editor.putString(Weather_24h, userConfig.getWeather24h());
        editor.putString(Welcome_Text, userConfig.getWelcom_text());
        editor.putBoolean(HAS_GUIDE_I7a, userConfig.isHasGuideI7A());
        editor.putBoolean(HAS_GUIDE_I6hrc, userConfig.isHasGuideI6HRC());
        editor.putBoolean(HAS_GUIDE_r1, userConfig.isHasGuideR1());
        editor.putLong(Weather_Go, userConfig.getWeatherGo());
        editor.putString(weatherLocation, userConfig.getWeatherLocation());
        editor.putLong(weatherTimeOut, userConfig.getWeatherTimeout());
        editor.putInt(ECG_AI_COUNT, userConfig.getEcgCount());
        editor.putString(ECG_AI_COUNT_MONTH, userConfig.getEcgMonth());
        editor.putString(pm25, userConfig.getPm25());
        editor.putInt(Ecg_Mv_Default, userConfig.getEcg_Mv_Default());
        editor.putBoolean(Ecg_Speed_Defaut, userConfig.isEcg_Speed_Defaut());
        editor.putString(Sport_Module_City_Name, userConfig.getGoogleCityName());
        editor.putBoolean(PremissionDialog, userConfig.isPremissionDialog());
        editor.commit();
    }
}
