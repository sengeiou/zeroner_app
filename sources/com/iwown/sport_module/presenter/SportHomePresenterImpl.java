package com.iwown.sport_module.presenter;

import android.text.TextUtils;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.json.JsonTool;
import com.iwown.sport_module.Fragment.SportFragment;
import com.iwown.sport_module.base.BasePresenterImpl;
import com.iwown.sport_module.contract.SportHomeContract.SportHomePresenter;
import com.iwown.sport_module.model.SportHomeModelImpl;
import com.iwown.sport_module.model.SportHomeModelImpl.WeatherCallback;
import com.iwown.sport_module.pojo.Weather;
import com.iwown.sport_module.pojo.Weather24hBean;
import com.iwown.sport_module.util.Util;
import com.socks.library.KLog;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SportHomePresenterImpl extends BasePresenterImpl<SportFragment, SportHomeModelImpl> implements SportHomePresenter, WeatherCallback {
    public SportHomePresenterImpl(SportFragment view) {
        super(view);
        setMyModel(new SportHomeModelImpl());
        ((SportHomeModelImpl) getMyModel()).setWeatherCallback(this);
    }

    public void doWeather() {
        if (getView() != null) {
            ((SportHomeModelImpl) getMyModel()).getWeather(((SportFragment) getView()).getActivity().getApplicationContext());
        }
    }

    public void setTopPic(String file_path) {
    }

    public void onSuccess(Weather weather) {
        ((SportFragment) getView()).refreshWeatherView(weather);
    }

    public void onFail(int code) {
        KLog.e("获取天气失败-->" + code);
        String weather24 = UserConfig.getInstance().getWeather24h();
        if (!TextUtils.isEmpty(weather24)) {
            List<Weather24hBean> weather24hBeans = JsonTool.getListJson(weather24, Weather24hBean.class);
            Collections.sort(weather24hBeans, new Comparator<Weather24hBean>() {
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
            KLog.e("找到本地缓存天气：" + JsonTool.toJson(weather24hBeans));
            boolean has_found = false;
            Weather weather = null;
            int i = 0;
            while (true) {
                if (i >= weather24hBeans.size()) {
                    break;
                }
                Weather24hBean weather24hBean = (Weather24hBean) weather24hBeans.get(i);
                Weather24hBean next = (Weather24hBean) weather24hBeans.get(i + 1 >= weather24hBeans.size() ? i : i + 1);
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
                    weather.getWeather24hBeans().addAll(weather24hBeans);
                    Weather.weather_type_num = weather24hBean.weather_type;
                    weather.setWeatherDrawAsFirmwareWeahterType();
                }
            }
            if (!has_found) {
                if (getView() != null) {
                    ((SportFragment) getView()).refreshWeatherView(new Weather(code));
                }
            } else if (getView() != null) {
                ((SportFragment) getView()).refreshWeatherView(weather);
            }
        }
    }
}
