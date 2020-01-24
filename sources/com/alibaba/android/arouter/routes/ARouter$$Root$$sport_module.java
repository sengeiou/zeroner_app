package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.alibaba.android.arouter.facade.template.IRouteRoot;
import com.iwown.device_module.device_setting.configure.WristbandModel.DownType;
import java.util.Map;

public class ARouter$$Root$$sport_module implements IRouteRoot {
    public void loadInto(Map<String, Class<? extends IRouteGroup>> routes) {
        routes.put(DownType.SPORT, ARouter$$Group$$sport.class);
    }
}
