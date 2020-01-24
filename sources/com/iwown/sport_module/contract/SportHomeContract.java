package com.iwown.sport_module.contract;

import android.content.Context;
import com.iwown.sport_module.pojo.Weather;

public interface SportHomeContract {

    public interface SportHomeModel {
        void getTopPic(String str);

        void getWeather(Context context);
    }

    public interface SportHomePresenter {
        void doWeather();

        void setTopPic(String str);
    }

    public interface SportHomeView {
        void refreshTopPic(String str);

        void refreshWeatherView(Weather weather);
    }
}
