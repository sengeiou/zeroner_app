package com.iwown.sport_module.net.constant;

import com.iwown.data_link.BaseNetUrl;

public class ConstantsLive {

    public static class APP_INFO {
        public static String APP_NAME_FOR_WEATHER = "iwownfit_pro";
        public static int VERSION_INT = 3015;
    }

    public static class ChinaBaseUrl {
        public static final String AD_URL = "https://api2.iwown.com/venus/wawaservice/advertise/startpage";
        public static String LOG_UPLOAD_API = "http://api2.iwown.com:7790/";
        public static String LOG_UPLOAD_API_RU = "http://search.iwown.com:7789/";
        public static String NEW_API_1 = "https://api1.iwown.com/venus/";
        public static String NEW_API_2 = "https://api2.iwown.com/venus/";
        public static String NEW_API_3 = "https://api3.iwown.com/venus/";
        public static String NEW_API_RU = "https://search.iwown.com/venus/";
        public static String NGG_Url = BaseNetUrl.API1_ngs_Dev;
        public static String WEATHER_API = "http://192.168.0.60:2020/";
        public static String WEATHER_URL = "https://api3.iwown.com/venus/weatherservice/";

        public static void changeURLRU() {
            NEW_API_1 = NEW_API_RU;
            LOG_UPLOAD_API = LOG_UPLOAD_API_RU;
            WEATHER_API = NEW_API_1;
            WEATHER_URL = WEATHER_API + "weatherservice/";
        }
    }

    public static class OverSeasBaseUrl {
        public static final String AD_URL = (NEW_API + "wawaservice/advertise/startpage");
        public static String GETWAY = BaseNetUrl.Log_P1_Upload_API_AMAZON_PROD;
        public static final String GET_IPS_URL = "http://54.183.62.103:80/ip.json";
        public static final String GOOGLE_LOCATION = "https://maps.googleapis.com/maps/";
        public static String LOG_UPLOAD_API = BaseNetUrl.Log_P1_Upload_API_AMAZON_DEV;
        public static String LOG_UPLOAD_API_RU = "http://search.iwown.com:7789/";
        public static String NEW_API = BaseNetUrl.Base_API_AMAZON_PROD;
        public static String NEW_API_RU = "https://search.iwown.com/venus/";
        public static String NGG_Url = BaseNetUrl.AMAZON_ngs_Dev;
        public static String NGG_Url_RU = BaseNetUrl.Test_ngs_Dev_Ru;
        public static String News_Url = BaseNetUrl.News_Url;
        public static String PHONTO_URL = "http://54.67.86.82:80/iwown/";
        public static String WEATHER_API = NEW_API;
        public static String WEATHER_URL = (WEATHER_API + "weatherservice/");

        public static void changeURLRU() {
            NEW_API = NEW_API_RU;
            LOG_UPLOAD_API = LOG_UPLOAD_API_RU;
            WEATHER_API = NEW_API;
            WEATHER_URL = WEATHER_API + "weatherservice/";
            NGG_Url = NGG_Url_RU;
            GETWAY = LOG_UPLOAD_API_RU;
            News_Url = NEW_API_RU;
        }
    }
}
