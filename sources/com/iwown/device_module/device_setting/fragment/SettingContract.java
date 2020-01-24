package com.iwown.device_module.device_setting.fragment;

import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.BaseView;
import com.iwown.device_module.common.network.data.resp.DeviceSettingRemote;
import com.iwown.device_module.device_setting.configure.DeviceSettingLocal;

public class SettingContract {

    public interface Presenter extends BasePresenter {
        void checkFirmwareVersion();

        void downloadDevicePref();

        int getBatteryValue();

        DeviceSettingLocal localDeviceSetting();

        void saveLocalDeviceSetting(DeviceSettingLocal deviceSettingLocal);

        void unbindDevice();

        void updateVersionOnlyUpgradeSuccess();

        void uploadDevicePref(DeviceSettingRemote deviceSettingRemote);
    }

    public interface View extends BaseView<Presenter> {
        void connectStatue(boolean z);

        void connectingView(int i);

        void keyDownDismissDialog();

        void upDateFirmwareUi(String str, int i, int i2);

        void updateConfigUi(String str);

        void updateFirmwareUpgradeSuccess();
    }
}
