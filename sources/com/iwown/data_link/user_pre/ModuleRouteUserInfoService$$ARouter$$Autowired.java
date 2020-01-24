package com.iwown.data_link.user_pre;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.launcher.ARouter;

public class ModuleRouteUserInfoService$$ARouter$$Autowired implements ISyringe {
    private SerializationService serializationService;

    public void inject(Object target) {
        this.serializationService = (SerializationService) ARouter.getInstance().navigation(SerializationService.class);
        ((ModuleRouteUserInfoService) target).mUserInfoService = (IUserInfoService) ARouter.getInstance().navigation(IUserInfoService.class);
    }
}
