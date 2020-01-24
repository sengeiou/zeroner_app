package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.iwown.data_link.RouteUtils;
import com.iwown.my_module.output_service.UserInfoService;
import java.util.Map;

public class ARouter$$Group$$user implements IRouteGroup {
    public void loadInto(Map<String, RouteMeta> atlas) {
        atlas.put(RouteUtils.User_Service, RouteMeta.build(RouteType.PROVIDER, UserInfoService.class, RouteUtils.User_Service, "user", null, -1, Integer.MIN_VALUE));
    }
}
