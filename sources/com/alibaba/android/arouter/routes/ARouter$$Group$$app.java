package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.iwown.data_link.RouteUtils;
import com.iwown.healthy.MainActivity;
import com.iwown.lib_common.log.L;
import java.util.Map;
import router.AppModuleHandler;

public class ARouter$$Group$$app implements IRouteGroup {
    public void loadInto(Map<String, RouteMeta> atlas) {
        atlas.put(RouteUtils.Activity_app_MainActivity, RouteMeta.build(RouteType.ACTIVITY, MainActivity.class, "/app/mainactivity", L.Bluetooth_App_Path, null, -1, Integer.MIN_VALUE));
        atlas.put(RouteUtils.APP_Service, RouteMeta.build(RouteType.PROVIDER, AppModuleHandler.class, RouteUtils.APP_Service, L.Bluetooth_App_Path, null, -1, Integer.MIN_VALUE));
    }
}
