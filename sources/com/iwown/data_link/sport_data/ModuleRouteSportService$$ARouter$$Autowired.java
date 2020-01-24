package com.iwown.data_link.sport_data;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iwown.data_link.RouteUtils;

public class ModuleRouteSportService$$ARouter$$Autowired implements ISyringe {
    private SerializationService serializationService;

    public void inject(Object target) {
        this.serializationService = (SerializationService) ARouter.getInstance().navigation(SerializationService.class);
        ((ModuleRouteSportService) target).mSportService = (ISportService) ARouter.getInstance().build(RouteUtils.Device_Sport_Service).navigation();
    }
}
