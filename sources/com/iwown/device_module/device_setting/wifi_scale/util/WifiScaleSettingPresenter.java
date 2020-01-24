package com.iwown.device_module.device_setting.wifi_scale.util;

import android.content.Context;
import android.text.TextUtils;
import com.iwown.device_module.common.BaseActionUtils.WifiScaleAction;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.JsonUtils;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.device_module.device_setting.wifi_scale.WifiScaleSettingContract.Presenter;
import com.iwown.device_module.device_setting.wifi_scale.WifiScaleSettingContract.View;
import com.iwown.device_module.device_setting.wifi_scale.bean.WifiScaleSetting;

public class WifiScaleSettingPresenter implements Presenter {
    View view;

    public WifiScaleSettingPresenter() {
    }

    public WifiScaleSettingPresenter(View view2) {
        this.view = view2;
    }

    public WifiScaleSetting wifiScaleSettingStatue() {
        String content = PrefUtil.getString(ContextUtil.app, WifiScaleAction.Wifi_Scale_Setting_Action);
        if (content == null || TextUtils.isEmpty(content)) {
            return new WifiScaleSetting();
        }
        return (WifiScaleSetting) JsonUtils.fromJson(content, WifiScaleSetting.class);
    }

    public void saveWifiScaleStatue(WifiScaleSetting setting) {
        if (setting != null) {
            PrefUtil.save((Context) ContextUtil.app, WifiScaleAction.Wifi_Scale_Setting_Action, JsonUtils.toJson(setting));
            if (this.view != null) {
                this.view.saveStatueSuccess();
            }
        }
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }
}
