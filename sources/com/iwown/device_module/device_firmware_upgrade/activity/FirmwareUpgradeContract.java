package com.iwown.device_module.device_firmware_upgrade.activity;

import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.BaseView;
import com.iwown.device_module.common.network.data.resp.FwUpdateReturnDetail;
import com.iwown.device_module.device_firmware_upgrade.dwonloadBiz.CallbackHandler;

public class FirmwareUpgradeContract {

    interface Presenter extends BasePresenter {
        void checkVersion();

        void createFileDir();

        void downloadUpgradle(String str, CallbackHandler callbackHandler);

        void downloadUserInfo();

        FwUpdateReturnDetail fwUpdateDetail();

        boolean isDialog();

        boolean isNordic();

        void saveFwUpdateDetail(FwUpdateReturnDetail fwUpdateReturnDetail);

        void uploadUserInfo();
    }

    interface View extends BaseView<Presenter> {
        void pGoToStep(int i);

        void pUpdateUI(int i, boolean z);
    }
}
