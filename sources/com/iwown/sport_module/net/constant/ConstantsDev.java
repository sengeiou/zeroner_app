package com.iwown.sport_module.net.constant;

import com.iwown.data_link.BaseNetUrl;

public class ConstantsDev {

    public static class APP_INFO {
        public static String APP_NAME_FOR_WEATHER = "iwownfit_pro";
        public static int VERSION_INT = 3015;
    }

    public static class ChinaBaseUrl {
        public static final String AD_URL = (NEW_API_2 + "wawaservice/advertise/startpage");
        public static String LOG_UPLOAD_API = BaseNetUrl.Log_P1_Upload_API_ALIYUN_DEV;
        public static String NEW_API_1 = "http://betaapi.iwown.com:9000/venus/";
        public static String NEW_API_2 = "http://betaapi.iwown.com:9000/venus/";
        public static String NEW_API_3 = "http://betaapi.iwown.com:9000/venus/";
        public static String WEATHER_API = NEW_API_3;
        public static String WEATHER_URL = (WEATHER_API + "weatherservice/");
    }

    public static class OverSeasBaseUrl {
        public static final String AD_URL = (NEW_API + "wawaservice/advertise/startpage");
        public static String GETWAY = BaseNetUrl.Log_P1_Upload_API_AMAZON_PROD;
        public static final String GET_IPS_URL = "http://54.183.62.103:80/ip.json";
        public static final String GOOGLE_LOCATION = "http://maps.google.com/maps/";
        public static String LOG_UPLOAD_API = BaseNetUrl.Log_P1_Upload_API_AMAZON_DEV;
        public static String NEW_API = "http://hwbetaapi.iwown.com:8080/";
        public static String NGG_Url = BaseNetUrl.Test_ngs_Dev;
        public static String PHONTO_URL = "http://54.67.86.82:80/iwown/";
        public static String WEATHER_API = BaseNetUrl.Base_API_AMAZON_PROD;
        public static final String WEATHER_URL = (WEATHER_API + "weatherservice/");
    }
}
