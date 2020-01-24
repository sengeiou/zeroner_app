package com.iwown.device_module.device_setting.wifi_scale;

import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.device_setting.wifi_scale.bean.WifiScaleSetting;

public class WifiScaleSettingContract {

    public interface Presenter extends BasePresenter {
        void saveWifiScaleStatue(WifiScaleSetting wifiScaleSetting);

        WifiScaleSetting wifiScaleSettingStatue();
    }

    public interface View {
        void saveStatueSuccess();
    }
}
