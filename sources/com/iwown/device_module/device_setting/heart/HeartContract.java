package com.iwown.device_module.device_setting.heart;

import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.device_setting.heart.bean.AutoHeartStatue;
import com.iwown.device_module.device_setting.heart.bean.HeartGuidanceStatue;

public class HeartContract {

    interface Presenter extends BasePresenter {
        AutoHeartStatue autoHeartStatue();

        HeartGuidanceStatue heartGuidanceStatue();

        void saveAutoHeartStatue(AutoHeartStatue autoHeartStatue);

        void saveHeartGuidance(HeartGuidanceStatue heartGuidanceStatue);

        void writeCommandToDevice();
    }

    interface View {
    }
}
