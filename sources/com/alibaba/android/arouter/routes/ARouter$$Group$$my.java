package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.enums.RouteType;
import com.alibaba.android.arouter.facade.model.RouteMeta;
import com.alibaba.android.arouter.facade.template.IRouteGroup;
import com.iwown.data_link.RouteUtils;
import com.iwown.my_module.settingactivity.CustomWebViewActivity;
import java.util.Map;

public class ARouter$$Group$$my implements IRouteGroup {
    public void loadInto(Map<String, RouteMeta> atlas) {
        atlas.put(RouteUtils.Activity_my_app_background_Activity, RouteMeta.build(RouteType.ACTIVITY, CustomWebViewActivity.class, "/my/appbackgroundactivity", "my", null, -1, Integer.MIN_VALUE));
    }
}
