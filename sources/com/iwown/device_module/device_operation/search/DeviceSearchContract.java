package com.iwown.device_module.device_operation.search;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import com.iwown.ble_module.iwown.bean.WristBand;
import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.BaseView;

public interface DeviceSearchContract {

    public interface Presenter extends BasePresenter {
        void connect(Context context, WristBand wristBand);

        void connectStatue(boolean z);

        void disconnect();

        int getSDKTypeByDeviceName(Context context, int i, String str);

        void searchSuccess();

        void startScan(Context context);

        void stopScan(Context context);

        void tryRecycleAnimationDrawable(AnimationDrawable animationDrawable);
    }

    public interface View extends BaseView<Presenter> {
        void bluetoothStatus();

        void connectFail();

        void connectSuccess();

        void searchTimeout();
    }
}
