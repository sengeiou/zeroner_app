package com.iwown.data_link.ecg;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.iwown.lib_common.date.DateUtil;
import java.util.List;

public class ModuleRouterEcgService {
    @Autowired
    IEcgService service;

    private static class ModuleRouteEcgServiceHolder {
        static ModuleRouterEcgService moduleRouterEcgService = new ModuleRouterEcgService();

        private ModuleRouteEcgServiceHolder() {
        }
    }

    private ModuleRouterEcgService() {
        ARouter.getInstance().inject(this);
    }

    public void braceletToView(long newUID, String device) {
        if (this.service != null) {
            this.service.braceletToView(newUID, device);
        }
    }

    public List<Integer> ecgChartViewDataByTime(long newUID, String device, long time) {
        if (this.service != null) {
            return this.service.ecgChartViewDataByTime(newUID, device, time);
        }
        return null;
    }

    public List<EcgViewDataBean> ecgViewDataFromDB(long newUID, String device, long time) {
        if (this.service != null) {
            return this.service.ecgViewDataFromDB(newUID, device, time);
        }
        return null;
    }

    public EcgViewDataBean ecgDataByTime(long newUID, String device, long time) {
        if (this.service != null) {
            return this.service.ecgViewDataByTime(newUID, device, time);
        }
        return null;
    }

    public void saveEcgNote(EcgViewDataBean bean) {
        if (this.service != null) {
            this.service.saveEcgNote(bean);
        }
    }

    public List<EcgUploadNet> getUnUploadedData(long uid) {
        if (this.service != null) {
            return this.service.getUnUploadedData(uid);
        }
        return null;
    }

    public void updateDataAlreadyUploaded(List<EcgUploadNet> data) {
        if (this.service != null) {
            this.service.updateDataAlreadyUploaded(data);
        }
    }

    public void updateEcgData(long uid, String dataFrom, String date, String data) {
        if (this.service != null) {
            this.service.updateEcgByData(uid, dataFrom, date, data);
        }
    }

    public void updateEcgAiResult(long uid, String data_from, String date, List<String> ai_result, int upLoaded) {
        if (this.service != null) {
            this.service.updateEcgAIResult(uid, data_from, date, ai_result, upLoaded);
        }
    }

    public void saveECGData(EcgViewDataBean dataBean) {
        if (this.service != null) {
            this.service.saveEcgData(dataBean);
        }
    }

    public List<EcgViewDataBean> queryEcgDataByUid(long uid) {
        if (this.service != null) {
            return this.service.ecgViewDataFromDbByUid(uid);
        }
        return null;
    }

    public int checkHasDataByUid(long uid, DateUtil dateUtil) {
        if (this.service != null) {
            return this.service.checkHasDataByUid(uid, dateUtil);
        }
        return 0;
    }

    public static ModuleRouterEcgService getInstance() {
        return ModuleRouteEcgServiceHolder.moduleRouterEcgService;
    }
}
