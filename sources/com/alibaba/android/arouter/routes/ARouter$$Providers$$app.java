package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.iwown.data_link.RouteUtils;
import com.iwown.lib_common.log.L;
import java.util.Map;
import router.AppModuleHandler;

public class ARouter$$Providers$$app implements IProviderGroup {
    public void loadInto(Map<String, RouteMeta> providers) {
        providers.put("com.iwown.data_link.app_link.IAPPService", RouteMeta.build(RouteType.PROVIDER, AppModuleHandler.class, RouteUtils.APP_Service, L.Bluetooth_App_Path, null, -1, Integer.MIN_VALUE));
    }
}
