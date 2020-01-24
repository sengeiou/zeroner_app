package com.iwown.device_module.common.network.config;

import com.iwown.data_link.BaseNetUrl;

public class BaseNetParams {
    public static String AMAZON_ngs_Dev = BaseNetUrl.AMAZON_ngs_Dev;
    public static String Base_API_AMAZON_PROD = BaseNetUrl.Base_API_AMAZON_PROD;
    public static String Log_P1_Upload_API_AMAZON_PROD = BaseNetUrl.Log_P1_Upload_API_AMAZON_PROD;

    public static class App_Info {
        public static String app_name = "iwownfit_pro";
        public static String app_name_healthy = "healthy";
        public static int version = 3015;
        public static int version_healthy = 4414;
    }

    public static void changeURLRU() {
        Base_API_AMAZON_PROD = "https://search.iwown.com/venus/";
        Log_P1_Upload_API_AMAZON_PROD = "http://search.iwown.com:7789/";
        AMAZON_ngs_Dev = BaseNetUrl.Test_ngs_Dev_Ru;
    }
}
