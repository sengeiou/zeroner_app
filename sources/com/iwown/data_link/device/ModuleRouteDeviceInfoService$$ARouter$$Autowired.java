package com.iwown.data_link.device;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.launcher.ARouter;

public class ModuleRouteDeviceInfoService$$ARouter$$Autowired implements ISyringe {
    private SerializationService serializationService;

    public void inject(Object target) {
        this.serializationService = (SerializationService) ARouter.getInstance().navigation(SerializationService.class);
        ((ModuleRouteDeviceInfoService) target).mDeviceService = (IDeviceService) ARouter.getInstance().navigation(IDeviceService.class);
    }
}
