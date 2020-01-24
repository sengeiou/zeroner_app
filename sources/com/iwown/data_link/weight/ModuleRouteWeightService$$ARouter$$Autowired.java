package com.iwown.data_link.weight;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.launcher.ARouter;

public class ModuleRouteWeightService$$ARouter$$Autowired implements ISyringe {
    private SerializationService serializationService;

    public void inject(Object target) {
        this.serializationService = (SerializationService) ARouter.getInstance().navigation(SerializationService.class);
        ((ModuleRouteWeightService) target).iWeightService = (IWeightService) ARouter.getInstance().navigation(IWeightService.class);
    }
}
