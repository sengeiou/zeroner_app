package com.iwown.device_module.common.network;

import com.iwown.device_module.common.network.callback.MyCallback;
import com.iwown.device_module.common.network.client.DeviceClient;

public class NetFactory {
    public static String APP_NAME_FOR_WEATHER = DeviceClient.App_Name;
    public static int VERSION_INT = DeviceClient.App_Version;

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

    public DeviceClient getClient(MyCallback myCallback) {
        DeviceClient mClient = new DeviceClient(myCallback);
        APP_NAME_FOR_WEATHER = DeviceClient.App_Name;
        VERSION_INT = DeviceClient.App_Version;
        return mClient;
    }
}
