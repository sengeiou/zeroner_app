package com.iwown.sport_module.pojo;

import android.text.TextUtils;
import com.iwown.sport_module.R;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class Weather {
    public static final int ERROR_CITY_NOT_SUPPORT_WEATHER_QUERY = 3;
    public static final int ERROR_IWOWN_SERVER_REQUEST_FAIL = 4;
    public static final int ERROR_NO_CITY_INFO = 1;
    public static final int ERROR_NO_LATLNG = 0;
    public static final int ERROR_NO_LOCATION_PERMISSION = 6;
    public static final int ERROR_SAME_RESPONSE_WITH_LAST = 7;
    public static final int ERROR_WEATHER_JsonParse = 5;
    public static final int ERROR_WEATHER_REQUEST_FAIL = 2;
    public static final int INVALID_TEMPERATURE = -1000;
    public static final String INVALID_WEATHER = "invalid_weather";
    public static final int NO_ERROR = -1;
    public static int weather_type_num = -1;
    private double F_tmp = -1000.0d;
    private int error = -1;
    private int icon_res;
    private List<Weather24hBean> mWeather24hBeans = new ArrayList();
    private List<Weather7DayBean> mWeather7DayBeans = new ArrayList();
    private String pm = "";
    private double temp = -1000.0d;
    private String type = INVALID_WEATHER;

    public double getF_tmp() {
        return this.F_tmp;
    }

    public void setF_tmp(double f_tmp) {
        this.F_tmp = f_tmp;
    }

    public String getPm() {
        return this.pm;
    }

    public void setPm(String pm2) {
        this.pm = pm2;
    }

    public List<Weather24hBean> getWeather24hBeans() {
        return this.mWeather24hBeans;
    }

    public void setWeather24hBeans(List<Weather24hBean> weather24hBeans) {
        this.mWeather24hBeans = weather24hBeans;
    }

    public List<Weather7DayBean> getWeather7DayBeans() {
        return this.mWeather7DayBeans;
    }

    public void setWeather7DayBeans(List<Weather7DayBean> weather7DayBeans) {
        this.mWeather7DayBeans = weather7DayBeans;
    }

    public int getIcon_res() {
        return this.icon_res;
    }

    public Weather(double temp2, String type2, String pm2) {
        this.temp = temp2;
        this.type = type2;
        this.pm = pm2;
        this.icon_res = getWeatherDraw(type2);
    }

    public Weather(double temp2, String type2, int error2) {
        this.temp = temp2;
        this.type = type2;
        this.error = error2;
    }

    public Weather(double temp2, String type2) {
        this.temp = temp2;
        this.type = type2;
        this.icon_res = getWeatherDraw(type2);
    }

    public Weather(int error2) {
        this.error = error2;
        this.icon_res = getWeatherDraw(null);
    }

    public double getTemp() {
        return this.temp;
    }

    public void setTemp(double temp2) {
        this.temp = temp2;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public int getError() {
        return this.error;
    }

    public void setError(int error2) {
        this.error = error2;
    }

    public void setWeatherDrawAsFirmwareWeahterType() {
        KLog.i("------------setWeatherDrawAsFirmwareWeahterType--------------" + weather_type_num);
        if (weather_type_num == -1) {
            this.icon_res = R.mipmap.unknown_weather3x;
        } else if (weather_type_num == 0) {
            this.icon_res = R.mipmap.sunny_3x;
        } else if (weather_type_num == 1) {
            this.icon_res = R.mipmap.cloudy_3x;
        } else if (weather_type_num == 2) {
            this.icon_res = R.mipmap.partly_cloudy3x;
        } else if (weather_type_num == 3) {
            this.icon_res = R.mipmap.light_rain3x;
        } else if (weather_type_num == 4) {
            this.icon_res = R.mipmap.rain_3x;
        } else if (weather_type_num == 5) {
            this.icon_res = R.mipmap.heavy_rain3x;
        } else if (weather_type_num == 6) {
            this.icon_res = R.mipmap.shower_3x;
        } else if (weather_type_num == 7) {
            this.icon_res = R.mipmap.snow_3x;
        } else if (weather_type_num == 8) {
            this.icon_res = R.mipmap.fog_3x;
        } else if (weather_type_num == 9) {
            this.icon_res = R.mipmap.sandstorms_3x;
        } else if (weather_type_num == 10) {
            this.icon_res = R.mipmap.turns_cloudy_3x;
        } else if (weather_type_num == 11) {
            this.icon_res = R.mipmap.thunderstorm_3x;
        } else {
            this.icon_res = R.mipmap.unknown_weather3x;
        }
    }

    public int getWeatherDraw(String condition) {
        if (TextUtils.isEmpty(condition)) {
            weather_type_num = -1;
            return R.mipmap.unknown_weather3x;
        } else if (condition.contains("晴")) {
            weather_type_num = 0;
            return R.mipmap.sunny_3x;
        } else if (condition.contains("云")) {
            weather_type_num = 1;
            return R.mipmap.cloudy_3x;
        } else if (condition.contains("阴")) {
            weather_type_num = 2;
            return R.mipmap.partly_cloudy3x;
        } else if (condition.contains("雨") || condition.contains("雷")) {
            if (condition.contains("小雨")) {
                weather_type_num = 3;
                return R.mipmap.light_rain3x;
            } else if (condition.contains("中雨")) {
                weather_type_num = 4;
                return R.mipmap.rain_3x;
            } else if (condition.contains("大雨")) {
                weather_type_num = 5;
                return R.mipmap.heavy_rain3x;
            } else if (condition.contains("阵雨")) {
                weather_type_num = 6;
                return R.mipmap.shower_3x;
            } else {
                weather_type_num = 4;
                return R.mipmap.rain_3x;
            }
        } else if (condition.contains("雪") || condition.contains("冰")) {
            weather_type_num = 7;
            return R.mipmap.snow_3x;
        } else if (condition.contains("雾") || condition.contains("霾")) {
            weather_type_num = 8;
            return R.mipmap.fog_3x;
        } else if (condition.contains("沙") || condition.contains("尘")) {
            weather_type_num = 9;
            return R.mipmap.sandstorms_3x;
        } else {
            weather_type_num = 1;
            return R.mipmap.cloudy_3x;
        }
    }

    public void overseas24Converter(int weatherIcon) {
        if (weatherIcon == 1 || weatherIcon == 2 || weatherIcon == 30 || weatherIcon == 33 || weatherIcon == 34) {
            weather_type_num = 0;
            this.icon_res = R.mipmap.sunny_3x;
        } else if (weatherIcon == 6 || weatherIcon == 7 || weatherIcon == 35 || weatherIcon == 36 || weatherIcon == 38 || weatherIcon == 40) {
            weather_type_num = 1;
            this.icon_res = R.mipmap.cloudy_3x;
        } else if (weatherIcon == 8 || weatherIcon == 32) {
            weather_type_num = 2;
            this.icon_res = R.mipmap.partly_cloudy3x;
        } else if (weatherIcon == 41 || weatherIcon == 42) {
            weather_type_num = 3;
            this.icon_res = R.mipmap.light_rain3x;
        } else if (weatherIcon == 18) {
            weather_type_num = 4;
            this.icon_res = R.mipmap.rain_3x;
        } else if (weatherIcon == 16 || weatherIcon == 17) {
            weather_type_num = 5;
            this.icon_res = R.mipmap.heavy_rain3x;
        } else if (weatherIcon == 12 || weatherIcon == 13 || weatherIcon == 14 || weatherIcon == 39) {
            weather_type_num = 6;
            this.icon_res = R.mipmap.shower_3x;
        } else if (weatherIcon == 11) {
            weather_type_num = 8;
            this.icon_res = R.mipmap.fog_3x;
        } else if (weatherIcon == 37) {
            weather_type_num = 9;
            this.icon_res = R.mipmap.sandstorms_3x;
        } else if (weatherIcon == 3 || weatherIcon == 4 || weatherIcon == 5) {
            weather_type_num = 10;
            this.icon_res = R.mipmap.turns_cloudy_3x;
        } else if (weatherIcon == 15) {
            weather_type_num = 11;
            this.icon_res = R.mipmap.thunderstorm_3x;
        } else if (weatherIcon == 19 || weatherIcon == 20 || weatherIcon == 21 || weatherIcon == 22 || weatherIcon == 23 || weatherIcon == 24 || weatherIcon == 25 || weatherIcon == 26 || weatherIcon == 29 || weatherIcon == 31 || weatherIcon == 43 || weatherIcon == 44) {
            weather_type_num = 7;
            this.icon_res = R.mipmap.snow_3x;
        } else {
            weather_type_num = -1;
            this.icon_res = R.mipmap.unknown_weather3x;
        }
    }

    public static int overseas24WeatherTypeConverter(int weatherIcon) {
        if (weatherIcon == 1 || weatherIcon == 2 || weatherIcon == 30 || weatherIcon == 33 || weatherIcon == 34) {
            return 0;
        }
        if (weatherIcon == 6 || weatherIcon == 7 || weatherIcon == 35 || weatherIcon == 36 || weatherIcon == 38 || weatherIcon == 40) {
            return 1;
        }
        if (weatherIcon == 8 || weatherIcon == 32) {
            return 2;
        }
        if (weatherIcon == 41 || weatherIcon == 42) {
            return 3;
        }
        if (weatherIcon == 18) {
            return 4;
        }
        if (weatherIcon == 16 || weatherIcon == 17) {
            return 5;
        }
        if (weatherIcon == 12 || weatherIcon == 13 || weatherIcon == 14 || weatherIcon == 39) {
            return 6;
        }
        if (weatherIcon == 11) {
            return 8;
        }
        if (weatherIcon == 37) {
            return 9;
        }
        if (weatherIcon == 3 || weatherIcon == 4 || weatherIcon == 5) {
            return 10;
        }
        if (weatherIcon == 15) {
            return 11;
        }
        if (weatherIcon == 19 || weatherIcon == 20 || weatherIcon == 21 || weatherIcon == 22 || weatherIcon == 23 || weatherIcon == 24 || weatherIcon == 25 || weatherIcon == 26 || weatherIcon == 29 || weatherIcon == 31 || weatherIcon == 43 || weatherIcon == 44) {
            return 7;
        }
        return -1;
    }
}
