package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.alibaba.android.arouter.facade.template.IRouteRoot;
import com.iwown.lib_common.log.L;
import java.util.Map;

public class ARouter$$Root$$app implements IRouteRoot {
    public void loadInto(Map<String, Class<? extends IRouteGroup>> routes) {
        routes.put(L.Bluetooth_App_Path, ARouter$$Group$$app.class);
    }
}
