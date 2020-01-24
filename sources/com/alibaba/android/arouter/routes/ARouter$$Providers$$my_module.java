package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IProviderGroup;
import com.iwown.data_link.RouteUtils;
import com.iwown.my_module.output_service.UserInfoService;
import java.util.Map;

public class ARouter$$Providers$$my_module implements IProviderGroup {
    public void loadInto(Map<String, RouteMeta> providers) {
        providers.put("com.iwown.data_link.user_pre.IUserInfoService", RouteMeta.build(RouteType.PROVIDER, UserInfoService.class, RouteUtils.User_Service, "user", null, -1, Integer.MIN_VALUE));
    }
}
