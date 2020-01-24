package com.iwown.device_module.device_firmware_upgrade.data;

import com.iwown.device_module.common.sql.TB_FM_download;
import org.litepal.crud.DataSupport;

public class FirmwareUpgradeParamsBiz extends DataSupport {
    private static FirmwareUpgradeParamsBiz firmwareUpgradeParamsBiz;

    public static FirmwareUpgradeParamsBiz getInstance() {
        if (firmwareUpgradeParamsBiz == null) {
            firmwareUpgradeParamsBiz = new FirmwareUpgradeParamsBiz();
        }
        return firmwareUpgradeParamsBiz;
    }

    private FirmwareUpgradeParamsBiz() {
    }

    public boolean queryMacExists(String mac) {
        if (DataSupport.where("mac=? ", mac).find(TB_FM_download.class).size() > 0) {
            return true;
        }
        return false;
    }

    public int uploadMacUrl(TB_FM_download request) {
        return request.updateAll("mac=? and url=? ", request.getMac() + "", request.getUrl());
    }
}
