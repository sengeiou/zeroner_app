package com.iwown.data_link.af;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import java.util.Map;

public class ModuleRouterRRIService {
    @Autowired
    IRRIService service;

    private static class ModuleRouteEcgServiceHolder {
        static ModuleRouterRRIService moduleRouterRRIService = new ModuleRouterRRIService();

        private ModuleRouteEcgServiceHolder() {
        }
    }

    private ModuleRouterRRIService() {
        ARouter.getInstance().inject(this);
    }

    public boolean hasRRIData(long uid) {
        if (this.service != null) {
            return this.service.hasRRIData(uid);
        }
        return false;
    }

    public Map<String, AfContenBean> getCalendarMap(long uid, String dataFrom) {
        if (this.service != null) {
            return this.service.getCalendarMap(uid, dataFrom);
        }
        return null;
    }

    public String getRRIDataFrom(long uid, String dataFrom) {
        if (this.service != null) {
            return this.service.getRRIDataFrom(uid, dataFrom);
        }
        return null;
    }

    public String getRRIHasDataFrom(long uid, String date, String dataFrom) {
        if (this.service != null) {
            return this.service.getRRIHasDataFrom(uid, date, dataFrom);
        }
        return null;
    }

    public static ModuleRouterRRIService getInstance() {
        return ModuleRouteEcgServiceHolder.moduleRouterRRIService;
    }
}
