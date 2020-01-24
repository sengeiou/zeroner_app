package com.iwown.sport_module;

import android.app.Application;
import android.graphics.Typeface;
import android.text.TextUtils;
import com.iwown.data_link.device.ModuleRouteDeviceInfoService;
import com.iwown.data_link.eventbus.DeviceUpdateWeatherEvent;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.lib_common.toast.Utils;
import com.iwown.sport_module.gps.GaoDeMapHelper;
import com.iwown.sport_module.gps.data.TB_location;
import com.iwown.sport_module.gps.data.TB_location_down;
import com.iwown.sport_module.gps.data.TB_location_history;
import com.iwown.sport_module.gps.data.TB_sport_all_ball;
import com.iwown.sport_module.gps.data.TB_sport_all_gps;
import com.iwown.sport_module.gps.data.TB_sport_all_other;
import com.iwown.sport_module.gps.data.TB_sport_all_swim;
import com.iwown.sport_module.gps.data.TB_sport_correct_gps;
import com.iwown.sport_module.model.SportHomeModelImpl;
import com.iwown.sport_module.model.SportHomeModelImpl.WeatherCallback;
import com.iwown.sport_module.net.constant.ConstantsLive.OverSeasBaseUrl;
import com.iwown.sport_module.pojo.Weather;
import com.iwown.sport_module.pojo.Weather24hBean;
import com.iwown.sport_module.sql.TB_29_history_data_monthly;
import com.iwown.sport_module.sql.TB_DevSupportsByName;
import com.iwown.sport_module.sql.TB_ad_url;
import com.iwown.sport_module.sql.TB_has28Days_monthly;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;
import org.litepal.LitePalDB;

public class SportInitUtils {
    public static boolean has_reminder;
    private static LitePalDB litePalDB;
    public static Typeface mDincond_bold_font;
    public static Typeface mFujiboli_font;
    public Application app;

    static class DeviceInitUtilsHolder {
        static SportInitUtils sportInitUtils = new SportInitUtils();

        DeviceInitUtilsHolder() {
        }
    }

    public static SportInitUtils getInstance() {
        return DeviceInitUtilsHolder.sportInitUtils;
    }

    public void changeURLRU(boolean isRU) {
        if (isRU) {
            OverSeasBaseUrl.changeURLRU();
        }
    }

    public void init(Application application, LitePalDB litePalDB2) {
        this.app = application;
        UserConfig.getInstance(application);
        Util.getLanguageEnv();
        Utils.init(application);
        mDincond_bold_font = Typeface.createFromAsset(application.getAssets(), "DINCOND-BOLD.ttf");
        mFujiboli_font = Typeface.createFromAsset(application.getAssets(), "FUJIBOLI.ttf");
        addTable2DB(litePalDB2);
        has_reminder = true;
        GaoDeMapHelper.getInstance(application);
        EventBus.getDefault().register(this);
    }

    public static void addTable2DB(LitePalDB litePalDB2) {
        List classNames = litePalDB2.getClassNames();
        litePalDB2.addClassName(TB_location.class.getName());
        litePalDB2.addClassName(TB_location_history.class.getName());
        litePalDB2.addClassName(TB_location_down.class.getName());
        litePalDB2.addClassName(TB_29_history_data_monthly.class.getName());
        litePalDB2.addClassName(TB_ad_url.class.getName());
        litePalDB2.addClassName(TB_has28Days_monthly.class.getName());
        litePalDB2.addClassName(TB_DevSupportsByName.class.getName());
        litePalDB2.addClassName(TB_sport_all_gps.class.getName());
        litePalDB2.addClassName(TB_sport_all_ball.class.getName());
        litePalDB2.addClassName(TB_sport_all_other.class.getName());
        litePalDB2.addClassName(TB_sport_correct_gps.class.getName());
        litePalDB2.addClassName(TB_sport_all_swim.class.getName());
        LitePal.use(litePalDB2);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(DeviceUpdateWeatherEvent event) {
        SportHomeModelImpl impl = new SportHomeModelImpl();
        impl.startGetWeather(this.app);
        impl.setWeatherCallback(new WeatherCallback() {
            public void onSuccess(Weather weather) {
                ModuleRouteDeviceInfoService.getInstance().writeWeather((float) weather.getTemp(), Weather.weather_type_num, UserConfig.getInstance().getCountry(), weather.getPm(), JsonTool.toJson(weather.getWeather24hBeans()));
            }

            public void onFail(int code) {
                KLog.e("获取天气失败-->" + code);
                String weather24 = UserConfig.getInstance().getWeather24h();
                if (!TextUtils.isEmpty(weather24)) {
                    ArrayList listJson = JsonTool.getListJson(weather24, Weather24hBean.class);
                    Collections.sort(listJson, new Comparator<Weather24hBean>() {
                        public int compare(Weather24hBean bean, Weather24hBean t1) {
                            long diff = bean.getTime_stamp() - t1.getTime_stamp();
                            if (diff < 0) {
                                return -1;
                            }
                            if (diff == 0) {
                                return 0;
                            }
                            return 1;
                        }
                    });
                    KLog.e("找到本地缓存天气：" + JsonTool.toJson(listJson));
                    boolean has_found = false;
                    Weather weather = null;
                    int i = 0;
                    while (true) {
                        if (i >= listJson.size()) {
                            break;
                        }
                        Weather24hBean weather24hBean = (Weather24hBean) listJson.get(i);
                        Weather24hBean next = (Weather24hBean) listJson.get(i + 1 >= listJson.size() ? i : i + 1);
                        long compare_time = System.currentTimeMillis();
                        if (compare_time < weather24hBean.getTime_stamp() * 1000 || compare_time > next.getTime_stamp() * 1000) {
                            i++;
                        } else {
                            has_found = true;
                            KLog.e("找到一个缓存天气-->" + JsonTool.toJson(weather24hBean));
                            if (!weather24hBean.isCentigrade()) {
                                weather = new Weather((double) ((int) Util.F2C(weather24hBean.getTemperature())), "", weather24hBean.getPm_25() + "");
                                weather.setF_tmp(weather24hBean.getTemperature());
                            } else {
                                weather = new Weather(weather24hBean.getTemperature(), "", weather24hBean.getPm_25() + "");
                                weather.setF_tmp((double) ((int) Util.C2F(weather24hBean.getTemperature())));
                            }
                            weather.getWeather24hBeans().clear();
                            weather.getWeather24hBeans().addAll(listJson);
                            Weather.weather_type_num = weather24hBean.weather_type;
                            weather.setWeatherDrawAsFirmwareWeahterType();
                        }
                    }
                    if (!has_found) {
                        weather = new Weather(code);
                    }
                    ModuleRouteDeviceInfoService.getInstance().writeWeather((float) weather.getTemp(), Weather.weather_type_num, UserConfig.getInstance().getCountry(), weather.getPm(), JsonTool.toJson(weather.getWeather24hBeans()));
                }
            }
        });
    }
}
