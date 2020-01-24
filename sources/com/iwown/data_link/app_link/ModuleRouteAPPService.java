package com.iwown.data_link.app_link;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.socks.library.KLog;

public class ModuleRouteAPPService {
    @Autowired
    IAPPService iappService;

    static class ModuleRouteAPPServiceHolder {
        static ModuleRouteAPPService moduleRouteAPPService = new ModuleRouteAPPService();

        ModuleRouteAPPServiceHolder() {
        }
    }

    public void changeRuURL() {
        KLog.e("  changeRuURL " + this.iappService);
        if (this.iappService != null) {
            this.iappService.changeRuURL();
        }
    }

    public ModuleRouteAPPService() {
        ARouter.getInstance().inject(this);
    }

    public static ModuleRouteAPPService getInstance() {
        return ModuleRouteAPPServiceHolder.moduleRouteAPPService;
    }
}
