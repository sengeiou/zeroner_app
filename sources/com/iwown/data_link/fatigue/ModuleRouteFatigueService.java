package com.iwown.data_link.fatigue;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iwown.data_link.fatigue.FatigueRetCode.FatigueDaily;
import com.socks.library.KLog;
import java.util.List;

public class ModuleRouteFatigueService {
    @Autowired
    IFatigueService iFatigueService;

    private static class ModuleRouteFatigueServiceHolder {
        static ModuleRouteFatigueService moduleRouteFatigueService = new ModuleRouteFatigueService();

        private ModuleRouteFatigueServiceHolder() {
        }
    }

    private ModuleRouteFatigueService() {
        ARouter.getInstance().inject(this);
    }

    public void saveFatigueDatas(FatigueDaily data) {
        if (this.iFatigueService != null && data != null) {
            this.iFatigueService.saveFatigueDatas(data);
        }
    }

    public void updateFatigueDatas(long uid, String data_from, String time) {
        if (this.iFatigueService != null) {
            this.iFatigueService.updateFatigueDatas(uid, data_from, time);
            KLog.e("修改疲劳度 isupload ");
        }
    }

    public List<FatigueShowData> getFatigueDatas(long uid, String data_from, long endTime, int pageSize) {
        if (this.iFatigueService != null) {
            return this.iFatigueService.getFatigues(uid, data_from, endTime, pageSize);
        }
        return null;
    }

    public FatigueSend getUnUploadFatigueDatas(long newUID, String device) {
        if (this.iFatigueService != null) {
            return this.iFatigueService.getUnUploadFatigueDatas(newUID, device);
        }
        return null;
    }

    public boolean isMTK() {
        if (this.iFatigueService != null) {
            return this.iFatigueService.isMTK();
        }
        return false;
    }

    public static ModuleRouteFatigueService getIsnatnce() {
        return ModuleRouteFatigueServiceHolder.moduleRouteFatigueService;
    }
}
