package com.iwown.sport_module.ui.weight;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import com.iwown.data_link.base.RetCode;
import com.iwown.data_link.eventbus.HealthDataEventBus;
import com.iwown.data_link.weight.MacBandS2Bean;
import com.iwown.data_link.weight.ModuleRouteWeightService;
import com.iwown.device_module.R;
import com.iwown.device_module.common.network.utils.ToastUtil;
import com.iwown.lib_common.network.NetworkUtils;
import com.iwown.sport_module.net.NetFactory;
import com.iwown.sport_module.net.callback.MyCallback;
import com.iwown.sport_module.ui.weight.activity.BindMacOKActivity;
import com.iwown.sport_module.zxing.activity.EventCaptureFinsh;
import org.greenrobot.eventbus.EventBus;

public class S2WifiUtils {
    public static void banS2MAc(final long uid, final String result, final Activity activity) {
        String regex = "^([A-F0-9]){12}$";
        if (!TextUtils.isEmpty(result) && result.matches(regex)) {
            MacBandS2Bean macBandS2Bean = new MacBandS2Bean();
            macBandS2Bean.uid = uid;
            macBandS2Bean.scaleid = result;
            NetFactory.getInstance().getClient(new MyCallback<RetCode>() {
                public void onSuccess(RetCode retCode) {
                    if (retCode.getRetCode() == 0) {
                        S2WifiUtils.updateMac(uid, result);
                        activity.startActivity(new Intent(activity, BindMacOKActivity.class));
                        HealthDataEventBus.updateHealthWeightEvent();
                        EventBus.getDefault().post(new EventCaptureFinsh());
                        activity.finish();
                    }
                }

                public void onFail(Throwable e) {
                    if (!NetworkUtils.isNetworkAvailable()) {
                        ToastUtil.showToast(activity.getString(R.string.network_error));
                    }
                }
            }).bindWifiScale(macBandS2Bean);
        }
    }

    public static void updateS2WifiConfig(long uid, String config_wifi_name, String config_wifi_pwd) {
        ModuleRouteWeightService.getInstance().saveS2WifiConfig(uid, config_wifi_name, config_wifi_pwd);
    }

    public static void updateMac(long uid, String mac) {
        ModuleRouteWeightService.getInstance().updateMac(uid, mac);
    }
}
