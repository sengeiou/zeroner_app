package com.iwown.data_link.fatigue;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.launcher.ARouter;

public class ModuleRouteFatigueService$$ARouter$$Autowired implements ISyringe {
    private SerializationService serializationService;

    public void inject(Object target) {
        this.serializationService = (SerializationService) ARouter.getInstance().navigation(SerializationService.class);
        ((ModuleRouteFatigueService) target).iFatigueService = (IFatigueService) ARouter.getInstance().navigation(IFatigueService.class);
    }
}
