package com.iwown.device_module.device_vibration;

import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.BaseView;
import com.iwown.device_module.device_vibration.bean.VibrationIvMtk;
import com.iwown.device_module.device_vibration.bean.VibrationPb;
import com.iwown.device_module.device_vibration.bean.VibrationZg;

public class VibrationSettingContract {

    interface VibrationPresenter extends BasePresenter {
        VibrationIvMtk ivVibration();

        VibrationIvMtk mtkVibration();

        VibrationPb pbVibration();

        void saveVibration(int i, int i2, int i3);

        void writeVibrationToDevice();

        VibrationZg zgVibration();
    }

    interface View extends BaseView<VibrationPresenter> {
    }
}
