package com.iwown.sport_module.model;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.consts.UserConst;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.utils.AppConfigUtil;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.log.L;
import com.iwown.sport_module.contract.SportHomeContract.SportHomeModel;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.net.exception.ServerException;
import com.iwown.sport_module.net.response.Weather24h0verseasRsp;
import com.iwown.sport_module.net.response.Weather24h0verseasRsp.DataBean;
import com.iwown.sport_module.pojo.Location;
import com.iwown.sport_module.pojo.Weather;
import com.iwown.sport_module.pojo.Weather24hBean;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import java.io.IOException;
import java.util.List;
import okhttp3.FormBody.Builder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.apache.commons.cli.HelpFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.util.Const.TableSchema;
import retrofit2.HttpException;

public class SportHomeModelImpl implements SportHomeModel {
    /* access modifiers changed from: private */
    public String TAG = getClass().getSimpleName();
    /* access modifiers changed from: private */
    public String city;
    MyCallback<ResponseBody> cityCallback = new MyCallback<ResponseBody>() {
        public void onSuccess(ResponseBody body) {
            String str;
            try {
                String json = body.string();
                String[] str2 = Util.getCityFromGoogleJson(json);
                String city = str2[0];
                String firstLocality = str2[1];
                String country = str2[2];
                UserConfig.getInstance().setCountry(country + "");
                UserConfig.getInstance().setGoogleCityName(city);
                if (json != null) {
                    UserConfig.getInstance().setWeatherLocation(json);
                }
                UserConfig.getInstance().save();
                SportHomeModelImpl sportHomeModelImpl = SportHomeModelImpl.this;
                if (city == null) {
                    str = firstLocality;
                } else {
                    str = city;
                }
                sportHomeModelImpl.getWeather(str, SportHomeModelImpl.this.latitude, SportHomeModelImpl.this.longitude, SportHomeModelImpl.this.weatherTime, country, true);
                UserConfig.getInstance().setWeatherTimeout(new DateUtil().getUnixTimestamp() + 86400);
                UserConfig.getInstance().save();
            } catch (IOException e) {
                ThrowableExtension.printStackTrace(e);
                if (SportHomeModelImpl.this.mWeatherCallback != null) {
                    SportHomeModelImpl.this.mWeatherCallback.onFail(1);
                }
            } catch (JSONException e2) {
                ThrowableExtension.printStackTrace(e2);
                if (SportHomeModelImpl.this.mWeatherCallback != null) {
                    SportHomeModelImpl.this.mWeatherCallback.onFail(5);
                }
            } catch (Exception e3) {
                if (SportHomeModelImpl.this.mWeatherCallback != null) {
                    SportHomeModelImpl.this.mWeatherCallback.onFail(1);
                }
            }
        }

        public void onFail(Throwable e) {
            if (e instanceof HttpException) {
                KLog.e("请求城市出错:" + ((HttpException) e).code());
                L.file("请求城市出错" + ((HttpException) e).code(), 4);
            } else if (e instanceof ServerException) {
                KLog.e("请求城市出错:" + ((ServerException) e).code());
                L.file("请求城市出错" + ((ServerException) e).code(), 4);
            } else {
                KLog.e("请求城市出错:" + (!TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : ""));
                L.file("请求城市出错" + e.getMessage(), 4);
            }
            if (SportHomeModelImpl.this.mWeatherCallback != null) {
                SportHomeModelImpl.this.mWeatherCallback.onFail(1);
            }
            ThrowableExtension.printStackTrace(e);
        }
    };
    private String condition;
    /* access modifiers changed from: private */
    public String country;
    private boolean isClient = false;
    /* access modifiers changed from: private */
    public String latitude;
    private String locality;
    /* access modifiers changed from: private */
    public String longitude;
    private Context mContext = null;
    /* access modifiers changed from: private */
    public WeatherCallback mWeatherCallback = null;
    private String temperature;
    MyCallback<ResponseBody> weatherBack = new MyCallback<ResponseBody>() {
        public void onSuccess(ResponseBody body) {
            SportHomeModelImpl.this.saveWeather(false);
            try {
                String answer = body.string();
                KLog.i("weather_answer-->" + answer);
                JSONObject ja = new JSONObject(answer);
                int type = ja.getJSONObject("rc").getInt("c");
                if (type == 0) {
                    if (!AppConfigUtil.isHealthy()) {
                        SportHomeModelImpl.this.handleOverseasWeatherRsp(ja);
                    } else {
                        SportHomeModelImpl.this.handleChinaWeatherRsp(ja);
                    }
                } else if (type == 10) {
                    if (SportHomeModelImpl.this.mWeatherCallback != null) {
                        SportHomeModelImpl.this.mWeatherCallback.onFail(3);
                    }
                } else if (SportHomeModelImpl.this.mWeatherCallback != null) {
                    SportHomeModelImpl.this.mWeatherCallback.onFail(4);
                }
            } catch (JSONException e) {
                SportHomeModelImpl.this.saveWeather(false);
                if (SportHomeModelImpl.this.mWeatherCallback != null) {
                    SportHomeModelImpl.this.mWeatherCallback.onFail(5);
                }
                ThrowableExtension.printStackTrace(e);
            } catch (IOException e2) {
                SportHomeModelImpl.this.saveWeather(false);
                ThrowableExtension.printStackTrace(e2);
                if (SportHomeModelImpl.this.mWeatherCallback != null) {
                    SportHomeModelImpl.this.mWeatherCallback.onFail(2);
                }
            }
        }

        public void onFail(Throwable e) {
            SportHomeModelImpl.this.saveWeather(false);
            if (e instanceof HttpException) {
                KLog.e("获取天气失败:" + ((HttpException) e).code());
                L.file("获取天气失败:" + ((HttpException) e).code(), 4);
            } else if (e instanceof ServerException) {
                KLog.e("获取天气失败:" + ((ServerException) e).code());
                L.file("获取天气失败:" + ((ServerException) e).code(), 4);
            } else {
                KLog.e("获取天气失败:" + (!TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : ""));
                L.file("获取天气失败:" + e.getMessage() + "", 4);
            }
            if (SportHomeModelImpl.this.mWeatherCallback != null) {
                SportHomeModelImpl.this.mWeatherCallback.onFail(2);
            }
            ThrowableExtension.printStackTrace(e);
        }
    };
    MyCallback<Weather24h0verseasRsp> weatherBack24h = new MyCallback<Weather24h0verseasRsp>() {
        public void onSuccess(Weather24h0verseasRsp body) {
            KLog.i("weather_answer-->" + JsonTool.toJson(body));
            SportHomeModelImpl.this.saveWeather(false);
            try {
                SportHomeModelImpl.this.handleOverseasWeather24hRsp(body);
            } catch (JSONException e) {
                ThrowableExtension.printStackTrace(e);
                SportHomeModelImpl.this.mWeatherCallback.onFail(5);
            }
        }

        public void onFail(Throwable e) {
            SportHomeModelImpl.this.saveWeather(false);
            if (e instanceof HttpException) {
                KLog.e("获取天气失败:" + ((HttpException) e).code());
                L.file("获取天气失败:" + ((HttpException) e).code(), 4);
            } else if (e instanceof ServerException) {
                KLog.e("获取天气失败:" + ((ServerException) e).code());
                L.file("获取天气失败:" + ((ServerException) e).code(), 4);
            } else {
                KLog.e("获取天气失败:" + (!TextUtils.isEmpty(e.getMessage()) ? e.getMessage() : ""));
                L.file("获取天气失败:" + e.getMessage() + "", 4);
            }
            if (SportHomeModelImpl.this.mWeatherCallback != null) {
                SportHomeModelImpl.this.mWeatherCallback.onFail(2);
            }
            ThrowableExtension.printStackTrace(e);
        }
    };
    /* access modifiers changed from: private */
    public long weatherTime = 0;

    public interface WeatherCallback {
        void onFail(int i);

        void onSuccess(Weather weather);
    }

    public void setWeatherCallback(WeatherCallback weatherCallback) {
        this.mWeatherCallback = weatherCallback;
    }

    public void getWeather(Context mContext2) {
        startGetWeather(mContext2);
    }

    public void getTopPic(String file_path) {
    }

    public void startGetWeather(Context mContext2) {
        KLog.i("----执行获取天气情况-----");
        this.mContext = mContext2;
        this.isClient = UserConfig.getInstance().isClientWeather();
        saveWeather(this.isClient);
        if (this.isClient || System.currentTimeMillis() - UserConfig.getInstance().getClWeatherTime() <= 60000) {
            KLog.e("weather上次请求的天气还未获取结果，无需再请求" + this.isClient + "/" + UserConfig.getInstance().getClWeatherTime() + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + System.currentTimeMillis());
            L.file("weather上次请求的天气还未获取结果，无需再请求" + this.isClient + "/" + UserConfig.getInstance().getClWeatherTime() + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + System.currentTimeMillis(), 4);
            return;
        }
        KLog.d(this.TAG, "开始获取地理位置--- ");
        getCity(mContext2);
    }

    private void saveLastReqWeatherTime() {
        UserConfig.getInstance().setClWeatherTime(System.currentTimeMillis());
    }

    /* access modifiers changed from: private */
    public void saveWeather(boolean isClient2) {
        UserConfig.getInstance().setClientWeather(isClient2);
    }

    public void getCity(Context mContext2) {
        String str;
        if (ActivityCompat.checkSelfPermission(mContext2, "android.permission.ACCESS_FINE_LOCATION") != 0) {
            if (ActivityCompat.checkSelfPermission(mContext2, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                KLog.i("licl", "没开地理位置权限");
                if (this.mWeatherCallback != null) {
                    this.mWeatherCallback.onFail(6);
                    return;
                }
                return;
            }
        }
        double[] mLocations = Util.getLocationInfo(mContext2);
        if (mLocations == null) {
            KLog.i("============================null== mLocations==============================");
            if (!AppConfigUtil.isHealthy()) {
                if (this.mWeatherCallback != null) {
                    this.mWeatherCallback.onFail(0);
                }
                saveWeather(false);
                return;
            }
            NetFactory.getInstance().getClient(new MyCallback<Location>() {
                public void onSuccess(Location o) {
                    SportHomeModelImpl.this.latitude = o.lat + "";
                    SportHomeModelImpl.this.longitude = o.lng + "";
                    SportHomeModelImpl.this.weatherTime = System.currentTimeMillis();
                    SportHomeModelImpl.this.city = o.city;
                    KLog.e(SportHomeModelImpl.this.TAG, JsonTool.toJson(o));
                    UserConfig.getInstance().setCountry(o.country + "");
                    UserConfig.getInstance().save();
                    SportHomeModelImpl.this.getWeather(SportHomeModelImpl.this.city, SportHomeModelImpl.this.latitude, SportHomeModelImpl.this.longitude, SportHomeModelImpl.this.weatherTime, SportHomeModelImpl.this.country, true);
                }

                public void onFail(Throwable e) {
                    if (SportHomeModelImpl.this.mWeatherCallback != null) {
                        SportHomeModelImpl.this.mWeatherCallback.onFail(0);
                        SportHomeModelImpl.this.saveWeather(false);
                    }
                }
            }).getCity("", "", "");
        } else if (!AppConfigUtil.isHealthy()) {
            KLog.i(this.TAG, "lat/lng--->" + mLocations[0] + "/" + mLocations[1]);
            KLog.i("---------------------------------------------------------------lat/lng--->" + mLocations[0] + "/" + mLocations[1]);
            if (new DateUtil().getUnixTimestamp() > UserConfig.getInstance().getWeatherTimeout()) {
                NetFactory.getInstance().getClient(this.cityCallback).getCity(mLocations[0] + "", mLocations[1] + "", UserConfig.getInstance().getLanguage());
                KLog.i("--------===============----------");
                return;
            }
            try {
                this.latitude = mLocations[0] + "";
                this.longitude = mLocations[1] + "";
                this.weatherTime = System.currentTimeMillis();
                String json = UserConfig.getInstance().getWeatherLocation();
                if (json == null) {
                    KLog.i("==========json null==========");
                    return;
                }
                String[] str2 = Util.getCityFromGoogleJson(json);
                String city2 = str2[0];
                String firstLocality = str2[1];
                String country2 = str2[2];
                UserConfig.getInstance().setCountry(country2 + "");
                UserConfig.getInstance().setGoogleCityName(city2);
                if (json != null) {
                    if (new JSONObject(json).getJSONArray("results").length() > 0) {
                        UserConfig.getInstance().setWeatherLocation(json);
                        UserConfig.getInstance().save();
                    } else {
                        NetFactory.getInstance().getClient(this.cityCallback).getCity(mLocations[0] + "", mLocations[1] + "", UserConfig.getInstance().getLanguage());
                        return;
                    }
                }
                KLog.i("--------===============----------" + json);
                if (city2 == null) {
                    str = firstLocality;
                } else {
                    str = city2;
                }
                getWeather(str, this.latitude, this.longitude, this.weatherTime, country2, true);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                NetFactory.getInstance().getClient(this.cityCallback).getCity(mLocations[0] + "", mLocations[1] + "", UserConfig.getInstance().getLanguage());
            }
        } else {
            NetFactory.getInstance().getClient(new MyCallback<Location>() {
                public void onSuccess(Location o) {
                    SportHomeModelImpl.this.latitude = o.lat + "";
                    SportHomeModelImpl.this.longitude = o.lng + "";
                    SportHomeModelImpl.this.weatherTime = System.currentTimeMillis();
                    SportHomeModelImpl.this.city = o.city;
                    KLog.e(SportHomeModelImpl.this.TAG, JsonTool.toJson(o));
                    UserConfig.getInstance().setCountry(o.country + "");
                    UserConfig.getInstance().save();
                    SportHomeModelImpl.this.getWeather(SportHomeModelImpl.this.city, SportHomeModelImpl.this.latitude, SportHomeModelImpl.this.longitude, SportHomeModelImpl.this.weatherTime, SportHomeModelImpl.this.country, true);
                }

                public void onFail(Throwable e) {
                    if (SportHomeModelImpl.this.mWeatherCallback != null) {
                        SportHomeModelImpl.this.mWeatherCallback.onFail(0);
                    }
                    SportHomeModelImpl.this.saveWeather(false);
                }
            }).getCity("", "", "");
        }
    }

    /* access modifiers changed from: private */
    public void getWeather(String city2, String lat, String lng, long time, String country2, boolean isOk) {
        String str;
        KLog.i("-----------------------" + city2);
        if (TextUtils.isEmpty(city2)) {
            saveWeather(false);
            if (this.mWeatherCallback != null) {
                this.mWeatherCallback.onFail(1);
                return;
            }
            return;
        }
        this.country = country2;
        this.city = city2;
        L.file("dl--" + this.country + "/" + this.city, 4);
        KLog.i("-----------dl--" + this.country + "/" + this.city);
        try {
            KLog.d(this.TAG, "开启请求天气服务器接口");
            String md5 = Util.MD5ToWeather(time);
            String versionName = Util.getVersionName(this.mContext);
            Builder add = new Builder().add(UserConst.UID, String.valueOf(UserConfig.getInstance().getNewUID())).add("lat", lat).add("lon", lng).add("city", city2).add("key", md5).add("timestamp", String.valueOf(time)).add("appversion", AppConfigUtil.APP_NAME_FOR_WEATHER + AppConfigUtil.VERSION);
            String str2 = "country";
            if (country2 == null) {
                str = "";
            } else {
                str = country2;
            }
            RequestBody requestBody = add.add(str2, str).build();
            if (AppConfigUtil.isHealthy()) {
                NetFactory.getInstance().getClient(this.weatherBack).getWeather(requestBody);
                saveWeather(true);
                saveLastReqWeatherTime();
                return;
            }
            NetFactory.getInstance().getClient(this.weatherBack24h).getWeather_24h(UserConfig.getInstance().getNewUID(), lat, lng, city2, md5, String.valueOf(time), AppConfigUtil.APP_NAME_FOR_WEATHER + AppConfigUtil.VERSION, country2);
            saveWeather(true);
            saveLastReqWeatherTime();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    public void handleOverseasWeatherRsp(JSONObject ja) throws JSONException {
        JSONObject ja2 = ja.getJSONObject("condition");
        double temperature2 = ja2.getDouble("temperature");
        String condition2 = ja2.getString("condition");
        UserConfig.getInstance().setLatitude(this.latitude);
        UserConfig.getInstance().setLongitude(this.longitude);
        UserConfig.getInstance().setLocality(this.city);
        UserConfig.getInstance().setTemperature(String.valueOf((int) temperature2));
        UserConfig.getInstance().setCondition(condition2);
        UserConfig.getInstance().setClientWeather(false);
        UserConfig.getInstance().setWeatherTime(this.weatherTime);
        UserConfig.getInstance().setCountry(this.country);
        UserConfig.getInstance().save();
        KLog.d(this.TAG, "weather-->" + temperature2 + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + condition2);
        if (this.mWeatherCallback != null) {
            this.mWeatherCallback.onSuccess(new Weather(temperature2, condition2));
        }
    }

    /* access modifiers changed from: private */
    public void handleChinaWeatherRsp(JSONObject jsonObject) throws JSONException {
        JSONObject data = jsonObject.getJSONObject("data");
        String pm2_5 = "";
        try {
            pm2_5 = data.getJSONObject("aqi").getString("value");
        } catch (JSONException e) {
            ThrowableExtension.printStackTrace(e);
        }
        JSONObject condition2 = data.getJSONObject("condition");
        JSONObject city2 = data.getJSONObject("city");
        String weather_msg = condition2.getString("condition");
        String temperature2 = condition2.getString("temp");
        String string = city2.getString("cityId");
        UserConfig.getInstance().setLatitude(this.latitude);
        UserConfig.getInstance().setLongitude(this.longitude);
        UserConfig.getInstance().setLocality(this.city);
        UserConfig.getInstance().setTemperature(temperature2);
        UserConfig.getInstance().setCondition(weather_msg);
        UserConfig.getInstance().setClientWeather(false);
        UserConfig.getInstance().setWeatherTime(this.weatherTime);
        UserConfig.getInstance().setCountry(this.country);
        UserConfig.getInstance().setPm25(pm2_5);
        UserConfig.getInstance().save();
        city2.getString(TableSchema.COLUMN_NAME);
        Weather waertherBean = new Weather((double) ((int) Double.parseDouble(temperature2)), weather_msg, pm2_5);
        waertherBean.setF_tmp((double) ((int) Util.C2F((double) ((int) Double.parseDouble(temperature2)))));
        KLog.e("weatherBean: " + JsonTool.toJson(waertherBean));
        if (this.mWeatherCallback != null) {
            this.mWeatherCallback.onSuccess(waertherBean);
        }
    }

    /* access modifiers changed from: private */
    public void handleOverseasWeather24hRsp(Weather24h0verseasRsp rsp) throws JSONException {
        double temperature2;
        double F_temp;
        if (rsp != null && rsp.getData() != null && rsp.getData().size() != 0) {
            DataBean dataBean = (DataBean) rsp.getData().get(rsp.getData().size() - 1);
            String cacheWeatherLocal = UserConfig.getInstance().getWeather24h();
            if (!TextUtils.isEmpty(cacheWeatherLocal)) {
                List<Weather24hBean> weather24hBeans = JsonTool.getListJson(cacheWeatherLocal, Weather24hBean.class);
                if (!(weather24hBeans == null || weather24hBeans.size() == 0 || ((Weather24hBean) weather24hBeans.get(weather24hBeans.size() - 1)).getTime_stamp() != ((long) dataBean.getEpochDateTime()))) {
                    KLog.e("这次的请求结果和本地缓存的一样，服务器没有更新");
                    if (this.mWeatherCallback != null) {
                        this.mWeatherCallback.onFail(7);
                        return;
                    }
                    return;
                }
            }
            if (dataBean.getTemperature().getUnit().equals("F")) {
                temperature2 = (double) ((int) Util.F2C(dataBean.getTemperature().getValue()));
                F_temp = (double) ((int) Util.C2F(temperature2));
            } else {
                temperature2 = (double) ((int) dataBean.getTemperature().getValue());
                F_temp = (double) ((int) Util.C2F(temperature2));
            }
            Weather weather = new Weather(temperature2, "");
            weather.setF_tmp(F_temp);
            weather.overseas24Converter(dataBean.getWeatherIcon());
            for (DataBean bean : rsp.getData()) {
                Weather24hBean weather24hBean = new Weather24hBean();
                weather24hBean.isCentigrade = true;
                weather24hBean.temperature = (double) ((int) Util.F2C(bean.getTemperature().getValue()));
                weather24hBean.time_stamp = (long) bean.getEpochDateTime();
                weather24hBean.weather_type = Weather.overseas24WeatherTypeConverter(bean.getWeatherIcon());
                weather.getWeather24hBeans().add(weather24hBean);
            }
            KLog.d(this.TAG, "weather-->" + JsonTool.toJson(weather));
            UserConfig.getInstance().setLatitude(this.latitude);
            UserConfig.getInstance().setLongitude(this.longitude);
            UserConfig.getInstance().setLocality(this.city);
            UserConfig.getInstance().setTemperature(String.valueOf((int) temperature2));
            UserConfig.getInstance().setCondition(this.condition);
            UserConfig.getInstance().setClientWeather(false);
            UserConfig.getInstance().setWeatherTime(this.weatherTime);
            UserConfig.getInstance().setCountry(this.country);
            UserConfig.getInstance().setWeather24h(JsonTool.toJson(weather.getWeather24hBeans()));
            UserConfig.getInstance().save();
            if (this.mWeatherCallback != null) {
                this.mWeatherCallback.onSuccess(weather);
            }
        } else if (this.mWeatherCallback != null) {
            this.mWeatherCallback.onFail(4);
        }
    }
}
