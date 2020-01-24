package com.iwown.data_link.consts;

import com.iwown.data_link.BaseNetUrl;

public class ApiConst {
    public static final String ALIYUN_API1_ADDRESS_PROD = "https://api1.iwown.com/venus/";
    public static final String ALIYUN_API2_ADDRESS_PROD = "https://api2.iwown.com/venus/";
    public static final String ALIYUN_API3_ADDRESS_PROD = "https://api3.iwown.com/venus/";
    public static final String ALIYUN_UP_VEDIO = "http://api2.iwown.com:7790/";
    public static final String AMAZON_API_ADDRESS_DEV = "http://hwbetaapi.iwown.com/venus/";
    public static String AMAZON_API_ADDRESS_PROD = BaseNetUrl.Base_API_AMAZON_PROD;
    public static String AMAZON_UP_VEDIO = BaseNetUrl.Log_P1_Upload_API_AMAZON_PROD;
    public static final int DBError = 10001;
    public static final int NETWORK_ERROR = 11000;
    public static final int PasswordNotMatch = 50003;
    public static final String RUSSIA_API_ADDRESS = "https://search.iwown.com/venus/";
    public static final String RUSSIA_UP_VEDIO = "http://search.iwown.com:7789/";
    public static final int SUCCEED = 0;
    public static final int UNKNOW_ERROR = 90000;
    public static final int UserAlreadyExist = 50004;
    public static final int UserAlreadyExistMoreThanOne = 80001;
    public static final int UserNotExist = 50012;
}
