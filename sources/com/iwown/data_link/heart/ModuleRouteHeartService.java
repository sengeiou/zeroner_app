package com.iwown.data_link.heart;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.heart.HeartStatusData.ContentBean;
import com.iwown.data_link.heart.heart_sport.HeartDownData1;
import com.iwown.data_link.heart.heart_sport.HeartUpSend;
import com.iwown.lib_common.date.DateUtil;
import java.util.List;
import java.util.Map;

public class ModuleRouteHeartService {
    @Autowired
    IHeartService iHeartService;

    private static class ModuleRouteHeartServiceHolder {
        static ModuleRouteHeartService moduleRouteHeartService = new ModuleRouteHeartService();

        private ModuleRouteHeartServiceHolder() {
        }
    }

    private ModuleRouteHeartService() {
        ARouter.getInstance().inject(this);
    }

    public boolean isExist53SomeDay(long uid, String data_from, DateUtil dateUtil) {
        if (this.iHeartService != null) {
            return this.iHeartService.isExist53SomeDay(uid, data_from, dateUtil);
        }
        return false;
    }

    public boolean isExist53DataByUid(long uid) {
        if (this.iHeartService != null) {
            return this.iHeartService.isExist53DataByUid(uid);
        }
        return false;
    }

    public boolean isExist51SomeSegment(long uid, String data_from, long start, long end) {
        if (this.iHeartService != null) {
            return this.iHeartService.isExist51SomeTimeSegment(uid, data_from, start, end);
        }
        return false;
    }

    public void getHeartByTime(HeartShowData heartShowData) {
        if (this.iHeartService != null) {
            this.iHeartService.getHeartByTime(heartShowData);
        }
    }

    public void saveNetHoursData(List<HeartHoursDownCode> heartHoursDownCodeList) {
        if (this.iHeartService != null) {
            try {
                this.iHeartService.saveNetHoursData(heartHoursDownCodeList);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
    }

    public List<HeartHoursData> getUnUploadHeartDatas(long newUID, String device) {
        if (this.iHeartService != null) {
            return this.iHeartService.getUnUploadHeartDatas(newUID, device);
        }
        return null;
    }

    public HeartData getHeartDataByTime(long newUID, String device, long start, long end, int age) {
        if (this.iHeartService != null) {
            return this.iHeartService.getHeartDataByTime(newUID, device, start, end, age);
        }
        return null;
    }

    public HeartData getSwimDataByTime(long newUID, String device, long start, long end, int age) {
        if (this.iHeartService != null) {
            return this.iHeartService.getSwimDataByTime(newUID, device, start, end, age);
        }
        return null;
    }

    public HeartData getHeartOldDataByTime(long newUID, String device, long start, long end, int age) {
        if (this.iHeartService != null) {
            return this.iHeartService.getHeartOldDataByTime(newUID, device, start, end, age);
        }
        return null;
    }

    public HeartData getHeartDataR1ByTime(long newUID, String device, long start, long end, int age, List<Integer> hrs) {
        if (this.iHeartService != null) {
            return this.iHeartService.getHeartDataByR1Time(newUID, device, start, end, age, hrs);
        }
        return null;
    }

    public void saveHeartStatus(List<ContentBean> content) {
        if (this.iHeartService != null) {
            this.iHeartService.saveHeartStatus(content);
        }
    }

    public Map<String, ContentBean> getStatusDatas(long newUID, String device, DateUtil dateUtil) {
        if (this.iHeartService != null) {
            return this.iHeartService.getStatusDatas(newUID, device, dateUtil);
        }
        return null;
    }

    public void clearHours() {
        if (this.iHeartService != null) {
            this.iHeartService.clearHours();
        }
    }

    public void updateDataUploads(Map<String, HeartUploadBean> unUploadHeartDatas) {
        if (this.iHeartService != null) {
            this.iHeartService.updateDataUploads(unUploadHeartDatas);
        }
    }

    public HeartUpSend getUnUploadHeartSportsDatas(long newUID) {
        if (this.iHeartService != null) {
            return this.iHeartService.getUnUploadHeartSportsDatas(newUID);
        }
        return null;
    }

    public void updateUnUpload1HeartSportDatas(long uid) {
        if (this.iHeartService != null) {
            this.iHeartService.updateUnUpload1HeartSportDatas(uid);
        }
    }

    public void saveHeartSports51(List<HeartDownData1> temp_dates) {
        if (this.iHeartService != null) {
            this.iHeartService.saveHeartSports51(temp_dates);
        }
    }

    public static ModuleRouteHeartService getInstance() {
        return ModuleRouteHeartServiceHolder.moduleRouteHeartService;
    }
}
