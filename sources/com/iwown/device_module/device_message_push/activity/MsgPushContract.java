package com.iwown.device_module.device_message_push.activity;

import com.iwown.device_module.common.BasePresenter;
import com.iwown.device_module.common.BaseView;
import com.iwown.device_module.device_message_push.bean.AppInfo;
import com.iwown.device_module.device_message_push.bean.MessagePushSwitchStatue;
import java.util.List;

public class MsgPushContract {

    interface Presenter extends BasePresenter {
        List<AppInfo> getMainAppList(List<String> list);

        List<String> mainAppPackNames();

        MessagePushSwitchStatue messageSwitchStatue();

        void saveMessageSwitchStatue(MessagePushSwitchStatue messagePushSwitchStatue);

        String[] smsPackageNames();

        void switchMainAppState(String str, boolean z);
    }

    interface View extends BaseView<Presenter> {
    }
}
