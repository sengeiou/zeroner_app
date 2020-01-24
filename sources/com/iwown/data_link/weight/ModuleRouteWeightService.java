package com.iwown.data_link.weight;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import java.util.List;

public class ModuleRouteWeightService {
    @Autowired
    IWeightService iWeightService;

    static class ModuleRouteWeightServiceHolder {
        static ModuleRouteWeightService moduleRouteweightService = new ModuleRouteWeightService();

        ModuleRouteWeightServiceHolder() {
        }
    }

    public String getScaleMac(long newUID) {
        if (this.iWeightService != null) {
            return this.iWeightService.getScaleMac(newUID);
        }
        return null;
    }

    public List<WeightUser> getWeightUsers(long newUID) {
        if (this.iWeightService != null) {
            return this.iWeightService.getWeightUsers(newUID);
        }
        return null;
    }

    public void getNetWeightUsers(long newUID) {
        if (this.iWeightService != null) {
            this.iWeightService.getNetWeightUsers(newUID);
        }
    }

    public int getRawWeightCounts(String mac) {
        if (this.iWeightService != null) {
            return this.iWeightService.getRawWeightCounts(mac);
        }
        return 0;
    }

    private ModuleRouteWeightService() {
        ARouter.getInstance().inject(this);
    }

    public static ModuleRouteWeightService getInstance() {
        return ModuleRouteWeightServiceHolder.moduleRouteweightService;
    }

    public void saveNetWeight(ScaleDataResp scaleDataResp) {
        if (this.iWeightService != null) {
            this.iWeightService.saveNetWeight(scaleDataResp);
        }
    }

    public List<ScaleBodyFat> getDatasSizeDay(long uid, int daySize) {
        if (this.iWeightService != null) {
            return this.iWeightService.getLocalDataSizeDay(uid, daySize);
        }
        return null;
    }

    public void saveS2WifiConfig(long uid, String config_wifi_name, String config_wifi_pwd) {
        if (this.iWeightService != null) {
            this.iWeightService.saveS2WifiConfig(uid, config_wifi_name, config_wifi_pwd);
        }
    }

    public void updateMac(long uid, String mac) {
        if (this.iWeightService != null) {
            this.iWeightService.updateMac(uid, mac);
        }
    }

    public void saveNetRWWeight(WifiScaleRWResp wifiScaleRWResp) {
        if (this.iWeightService != null) {
            this.iWeightService.saveNetRWWeight(wifiScaleRWResp);
        }
    }

    public void getUnUploadWeightDatas(long newUID, String device) {
    }
}
