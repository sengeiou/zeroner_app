package com.iwown.data_link.user_pre;

import android.content.Context;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.github.mikephil.charting.utils.Utils;
import com.iwown.data_link.device.DeviceInfo;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.socks.library.KLog;

public class UserConfig {
    public static UserConfig instance = null;
    private int Ecg_Mv_Default = 2;
    private boolean Ecg_Speed_Defaut;
    private int age = 25;
    public int ble_sdk_type = 0;
    private long clWeatherTime;
    private String condition;
    private String country;
    private String dev_mac = "";
    private String dev_model = "";
    private String device = "";
    private int ecgCount;
    private String ecgMonth;
    private String googleCityName;
    private boolean hasGuideHome = false;
    private boolean hasGuideHomeRefresh = false;
    private boolean hasGuideI6HRC = false;
    private boolean hasGuideI7A = false;
    private boolean hasGuideR1 = false;
    private boolean hasGuideSport = false;
    private double height = Utils.DOUBLE_EPSILON;
    private boolean isCentigrade = true;
    private boolean isClientWeather;
    private boolean isMale = true;
    private boolean isMertric = true;
    private String language = "";
    private long last_up_sport_time = 0;
    private String latitude;
    private String locality;
    private String longitude;
    private Context mContext = null;
    private long newUID = 0;
    private String pm25;
    private boolean premissionDialog;
    private int tab;
    private float target_cal = 0.0f;
    private int target_stand_hours = 12;
    private int target_step = 0;
    private float target_weight = 0.0f;
    private String temperature;
    private int toDaySleepTimeMin;
    private String weather24h = "";
    private long weatherGo;
    private String weatherLocation;
    private long weatherTime = 0;
    private long weatherTimeout;
    private double weight = Utils.DOUBLE_EPSILON;
    private String welcom_text = "";

    public boolean isHasGuideI7A() {
        return this.hasGuideI7A;
    }

    public void setHasGuideI7A(boolean hasGuideI7A2) {
        this.hasGuideI7A = hasGuideI7A2;
    }

    public boolean isHasGuideI6HRC() {
        return this.hasGuideI6HRC;
    }

    public void setHasGuideI6HRC(boolean hasGuideI6HRC2) {
        this.hasGuideI6HRC = hasGuideI6HRC2;
    }

    public boolean isHasGuideR1() {
        return this.hasGuideR1;
    }

    public void setHasGuideR1(boolean hasGuideR12) {
        this.hasGuideR1 = hasGuideR12;
    }

    public String getWelcom_text() {
        return this.welcom_text;
    }

    public void setWelcom_text(String welcom_text2) {
        this.welcom_text = welcom_text2;
    }

    public String getWeather24h() {
        return this.weather24h;
    }

    public void setWeather24h(String weather24h2) {
        this.weather24h = weather24h2;
    }

    public boolean isHasGuideHomeRefresh() {
        return this.hasGuideHomeRefresh;
    }

    public void setHasGuideHomeRefresh(boolean hasGuideHomeRefresh2) {
        this.hasGuideHomeRefresh = hasGuideHomeRefresh2;
    }

    public boolean isHasGuideHome() {
        return this.hasGuideHome;
    }

    public void setHasGuideHome(boolean hasGuideHome2) {
        this.hasGuideHome = hasGuideHome2;
    }

    public boolean isHasGuideSport() {
        return this.hasGuideSport;
    }

    public void setHasGuideSport(boolean hasGuideSport2) {
        this.hasGuideSport = hasGuideSport2;
    }

    public long getLast_up_sport_time() {
        return this.last_up_sport_time;
    }

    public void setLast_up_sport_time(long last_up_sport_time2) {
        this.last_up_sport_time = last_up_sport_time2;
    }

    public boolean isMale() {
        return this.isMale;
    }

    public void setMale(boolean male) {
        this.isMale = male;
    }

    public boolean isMertric() {
        return this.isMertric;
    }

    public void setMertric(boolean mertric) {
        this.isMertric = mertric;
    }

    public boolean isCentigrade() {
        return this.isCentigrade;
    }

    public void setCentigrade(boolean centigrade) {
        this.isCentigrade = centigrade;
    }

    public long getNewUID() {
        return this.newUID;
    }

    public void setNewUID(long newUID2) {
        this.newUID = newUID2;
    }

    public String getDevice() {
        return this.device;
    }

    public void setDevice(String device2) {
        this.device = device2;
    }

    public String getDev_mac() {
        return this.dev_mac;
    }

    public String getDev_model() {
        return this.dev_model;
    }

    public void setDev_model(String dev_model2) {
        this.dev_model = dev_model2;
    }

    public void setDev_mac(String dev_mac2) {
        this.dev_mac = dev_mac2;
    }

    public int getBle_sdk_type() {
        return this.ble_sdk_type;
    }

    public void setBle_sdk_type(int ble_sdk_type2) {
        this.ble_sdk_type = ble_sdk_type2;
    }

    public int getTab() {
        return this.tab;
    }

    public void setTab(int tab2) {
        this.tab = tab2;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age2) {
        this.age = age2;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(float height2) {
        this.height = (double) height2;
    }

    public double getWeight() {
        return this.weight;
    }

    public void setWeight(float weight2) {
        this.weight = (double) weight2;
    }

    public float getTarget_cal() {
        return this.target_cal;
    }

    public void setTarget_cal(float target_cal2) {
        this.target_cal = target_cal2;
    }

    public int getTarget_stand_hours() {
        return this.target_stand_hours;
    }

    public void setTarget_stand_hours(int target_stand_hours2) {
        this.target_stand_hours = target_stand_hours2;
    }

    public int getTarget_step() {
        return this.target_step;
    }

    public void setTarget_step(int target_step2) {
        this.target_step = target_step2;
    }

    public float getTarget_weight() {
        return this.target_weight;
    }

    public void setTarget_weight(float target_weight2) {
        this.target_weight = target_weight2;
    }

    public boolean isPremissionDialog() {
        return this.premissionDialog;
    }

    public void setPremissionDialog(boolean premissionDialog2) {
        this.premissionDialog = premissionDialog2;
    }

    public String getGoogleCityName() {
        return this.googleCityName;
    }

    public void setGoogleCityName(String googleCityName2) {
        this.googleCityName = googleCityName2;
    }

    public int getEcg_Mv_Default() {
        return this.Ecg_Mv_Default;
    }

    public void setEcg_Mv_Default(int ecg_Mv_Default) {
        this.Ecg_Mv_Default = ecg_Mv_Default;
    }

    public boolean isEcg_Speed_Defaut() {
        return this.Ecg_Speed_Defaut;
    }

    public void setEcg_Speed_Defaut(boolean ecg_Speed_Defaut) {
        this.Ecg_Speed_Defaut = ecg_Speed_Defaut;
    }

    public String getEcgMonth() {
        return this.ecgMonth;
    }

    public void setEcgMonth(String ecgMonth2) {
        this.ecgMonth = ecgMonth2;
    }

    public int getEcgCount() {
        return this.ecgCount;
    }

    public void setEcgCount(int ecgCount2) {
        this.ecgCount = ecgCount2;
    }

    public long getWeatherTimeout() {
        return this.weatherTimeout;
    }

    public void setWeatherTimeout(long weatherTimeout2) {
        this.weatherTimeout = weatherTimeout2;
    }

    public String getWeatherLocation() {
        return this.weatherLocation;
    }

    public void setWeatherLocation(String weatherLocation2) {
        this.weatherLocation = weatherLocation2;
    }

    public long getWeatherGo() {
        return this.weatherGo;
    }

    public void setWeatherGo(long weatherGo2) {
        this.weatherGo = weatherGo2;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country2) {
        this.country = country2;
    }

    public void setLongitude(String longitude2) {
        this.longitude = longitude2;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude2) {
        this.latitude = latitude2;
    }

    public String getLocality() {
        return this.locality;
    }

    public void setLocality(String locality2) {
        this.locality = locality2;
    }

    public String getTemperature() {
        return this.temperature;
    }

    public void setTemperature(String temperature2) {
        this.temperature = temperature2;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition2) {
        this.condition = condition2;
    }

    public long getWeatherTime() {
        return this.weatherTime;
    }

    public void setWeatherTime(long weatherTime2) {
        this.weatherTime = weatherTime2;
    }

    public void setClientWeather(boolean clientWeather) {
        this.isClientWeather = clientWeather;
    }

    public long getClWeatherTime() {
        return this.clWeatherTime;
    }

    public void setClWeatherTime(long clWeatherTime2) {
        this.clWeatherTime = clWeatherTime2;
    }

    public boolean isClientWeather() {
        return this.isClientWeather;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language2) {
        this.language = language2;
    }

    public String getPm25() {
        return this.pm25;
    }

    public void setPm25(String pm252) {
        this.pm25 = pm252;
    }

    public Context getContext() {
        return this.mContext;
    }

    private UserConfig(Context context) {
        this.mContext = context;
        UserConfigDAO.readUserConfig(this);
    }

    public static UserConfig getInstance(Context context) {
        if (instance == null) {
            synchronized (UserConfig.class) {
                if (instance == null) {
                    instance = new UserConfig(context);
                }
            }
        }
        return instance;
    }

    public static UserConfig getInstance() {
        return instance;
    }

    public void save() {
        UserConfigDAO.saveUserConfig(this);
    }

    public void read() {
    }

    public void initInfoFromOtherModule() {
        UserInfo userInfo = ModuleRouteUserInfoService.getInstance().getUserInfo(this.mContext);
        this.newUID = userInfo.uid;
        this.target_step = userInfo.target_step;
        this.target_weight = userInfo.target_weight;
        this.target_stand_hours = userInfo.target_stand_hours;
        this.isMertric = userInfo.isMertric;
        this.target_cal = userInfo.target_cal;
        this.isCentigrade = userInfo.isCentigrade;
        this.isMale = userInfo.isMale;
        this.weight = userInfo.weight;
        this.height = userInfo.height;
        this.age = userInfo.getAge();
        DeviceInfo deviceInfo = ModuleRouteDeviceInfoService.getInstance().getDeviceInfo();
        this.device = deviceInfo.dev_name;
        this.dev_mac = deviceInfo.mac;
        this.dev_model = deviceInfo.dev_modle;
        this.ble_sdk_type = deviceInfo.sdk_type;
        KLog.e(" v " + userInfo + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + deviceInfo);
    }

    public int getToDaySleepTimeMin() {
        return this.toDaySleepTimeMin;
    }

    public void setToDaySleepTimeMin(int toDaySleepTimeMin2) {
        this.toDaySleepTimeMin = toDaySleepTimeMin2;
    }
}
