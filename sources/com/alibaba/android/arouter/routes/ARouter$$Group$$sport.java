package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.iwown.data_link.RouteUtils;
import com.iwown.device_module.device_setting.configure.WristbandModel.DownType;
import com.iwown.sport_module.DataDownloadActivity;
import com.iwown.sport_module.MainActivity;
import com.iwown.sport_module.activity.ActiveActivity;
import com.iwown.sport_module.service.DataNetService;
import com.iwown.sport_module.ui.sleep.SleepShowActivity;
import com.iwown.sport_module.ui.weight.activity.BindMacOKActivity;
import com.iwown.sport_module.ui.weight.activity.WifiInputAcitvity;
import com.iwown.sport_module.zxing.activity.CaptureActivity;
import java.util.Map;

public class ARouter$$Group$$sport implements IRouteGroup {
    public void loadInto(Map<String, RouteMeta> atlas) {
        atlas.put(RouteUtils.Actvity_Sport_CaptureActivity, RouteMeta.build(RouteType.ACTIVITY, CaptureActivity.class, "/sport/captureactivity", DownType.SPORT, null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Activity_sport_data_download, RouteMeta.build(RouteType.ACTIVITY, DataDownloadActivity.class, "/sport/datadownloadactivity", DownType.SPORT, null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Actvity_Sport_WifiInputAcitvity, RouteMeta.build(RouteType.ACTIVITY, WifiInputAcitvity.class, "/sport/wifiinputacitvity", DownType.SPORT, null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Actvity_Active, RouteMeta.build(RouteType.ACTIVITY, ActiveActivity.class, RouteUtils.Actvity_Active, DownType.SPORT, null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.SPORT_DATANETService, RouteMeta.build(RouteType.SERVICE, DataNetService.class, RouteUtils.SPORT_DATANETService, DownType.SPORT, null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Actvity_Sleep, RouteMeta.build(RouteType.ACTIVITY, SleepShowActivity.class, RouteUtils.Actvity_Sleep, DownType.SPORT, null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Actvity_Sport_Scale_Wifi_Bind_OK, RouteMeta.build(RouteType.ACTIVITY, BindMacOKActivity.class, RouteUtils.Actvity_Sport_Scale_Wifi_Bind_OK, DownType.SPORT, null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.Actvity_Sport_Main, RouteMeta.build(RouteType.ACTIVITY, MainActivity.class, RouteUtils.Actvity_Sport_Main, DownType.SPORT, null, -1, Integer.MIN_VALUE));
    }
}
