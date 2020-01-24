package com.iwown.data_link.sleep_data;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iwown.data_link.sleep_data.SleepStatusData.ContentBean;
import com.iwown.lib_common.date.DateUtil;
import java.util.List;
import java.util.Map;

public class ModuleRouteSleepService {
    @Autowired
    ISleepService iSleepService;

    static class ModuleRouteSleepServiceHolder {
        static ModuleRouteSleepService moduleRouteSleepService = new ModuleRouteSleepService();

        ModuleRouteSleepServiceHolder() {
        }
    }

    public void updateSleepAction(int db_id, List<Integer> actions) {
        if (this.iSleepService != null) {
            this.iSleepService.updateSleepAction(db_id, actions);
        }
    }

    public void updateSleepFeelType(int db_id, int feel_type) {
        if (this.iSleepService != null) {
            this.iSleepService.updateSleepFeelType(db_id, feel_type);
        }
    }

    public void getDaySleepByDataFrom(SleepDataDay sleepDataDay) {
        if (this.iSleepService != null) {
            this.iSleepService.getDaySleepByDataFrom(sleepDataDay);
        }
    }

    private ModuleRouteSleepService() {
        ARouter.getInstance().inject(this);
    }

    public static ModuleRouteSleepService getInstance() {
        return ModuleRouteSleepServiceHolder.moduleRouteSleepService;
    }

    public void getDaySleep(SleepDataDay sleepDataToday) {
        if (this.iSleepService != null) {
            this.iSleepService.getSleepData(sleepDataToday);
        }
    }

    public void saveSleep(SleepDownCode sleepDownCode, boolean isNet) {
        if (this.iSleepService != null) {
            this.iSleepService.saveSleep(sleepDownCode, isNet);
        }
    }

    public void updateFinalSleepUploadStatus(SleepUpNewSend sleepUpNewSend, boolean upload) {
        if (this.iSleepService != null) {
            this.iSleepService.updateFinalSleepUploadStatus(sleepUpNewSend, upload);
        }
    }

    public void updateSleepStatusData(List<ContentBean> content) {
        if (this.iSleepService != null) {
            this.iSleepService.updateSleepStatusData(content);
        }
    }

    public SleepUpNewSend getUnUploadSleepDatas(long newUID) {
        if (this.iSleepService != null) {
            return this.iSleepService.getUnUploadSleepDatas(newUID);
        }
        return null;
    }

    public List<SleepHistoryData> getStartEndSleeps(long uid, String data_from, DateUtil startDate, DateUtil endDate) {
        if (this.iSleepService != null) {
            return this.iSleepService.getStartEndSleeps(uid, data_from, startDate, endDate);
        }
        return null;
    }

    public Map<String, ContentBean> getStatusDatas(long newUID, DateUtil dateUtil, String device) {
        if (this.iSleepService != null) {
            return this.iSleepService.getStatusDatas(newUID, dateUtil, device);
        }
        return null;
    }

    public boolean isExitSleepData(long uid) {
        if (this.iSleepService != null) {
            return this.iSleepService.isExitSleepData(uid);
        }
        return false;
    }
}
