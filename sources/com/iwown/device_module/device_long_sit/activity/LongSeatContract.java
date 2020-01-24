package com.iwown.device_module.device_long_sit.activity;

import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.BaseView;
import com.iwown.device_module.device_long_sit.bean.LongSitStatue;

public class LongSeatContract {

    interface Presenter extends BasePresenter {
        byte judgeWhatState(int i, int i2);

        LongSitStatue longSeatStatue();

        void saveLongSeatStatue(LongSitStatue longSitStatue);

        void writeSedentaryIfLunchBreak(int i, int i2, byte b, boolean z, int i3, boolean z2);
    }

    interface View extends BaseView<Presenter> {
    }
}
