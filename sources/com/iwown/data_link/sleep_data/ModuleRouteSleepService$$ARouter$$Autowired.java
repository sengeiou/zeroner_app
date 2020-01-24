package com.iwown.data_link.sleep_data;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.launcher.ARouter;

public class ModuleRouteSleepService$$ARouter$$Autowired implements ISyringe {
    private SerializationService serializationService;

    public void inject(Object target) {
        this.serializationService = (SerializationService) ARouter.getInstance().navigation(SerializationService.class);
        ((ModuleRouteSleepService) target).iSleepService = (ISleepService) ARouter.getInstance().navigation(ISleepService.class);
    }
}
