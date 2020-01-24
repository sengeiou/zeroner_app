package com.iwown.sport_module.net;

import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.net.client.BaseClient;
import com.iwown.sport_module.net.client.ChinaClient;
import com.iwown.sport_module.net.client.OverseasClient;

public class NetFactory {
    public static String APP_NAME_FOR_WEATHER = OverseasClient.APP_NAME_FOR_WEATHER;
    public static final int CHINA = 0;
    public static int CheckStatus = 1;
    public static final int OVERSEAS = 1;
    public static int VERSION_INT = OverseasClient.VERSION_INT;

    static class NetFactoryHolder {
        static NetFactory netFactory = new NetFactory();

        NetFactoryHolder() {
        }
    }

    private NetFactory() {
    }

    public static NetFactory getInstance() {
        return NetFactoryHolder.netFactory;
    }

    public BaseClient getClient(MyCallback myCallback) {
        BaseClient mClient = new OverseasClient(myCallback);
        switch (CheckStatus) {
            case 0:
                APP_NAME_FOR_WEATHER = ChinaClient.APP_NAME_FOR_WEATHER;
                VERSION_INT = ChinaClient.VERSION_INT;
                return new ChinaClient(myCallback);
            case 1:
                APP_NAME_FOR_WEATHER = OverseasClient.APP_NAME_FOR_WEATHER;
                VERSION_INT = OverseasClient.VERSION_INT;
                return new OverseasClient(myCallback);
            default:
                return mClient;
        }
    }

    public static String getErrorTips(Throwable e) {
        return "";
    }
}
