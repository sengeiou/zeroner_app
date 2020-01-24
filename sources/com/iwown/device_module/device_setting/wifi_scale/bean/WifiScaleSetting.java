package com.iwown.device_module.device_setting.wifi_scale.bean;

public class WifiScaleSetting {
    private boolean automaticArchive = true;
    private int unArchivedData;
    private int unitSwitch = 2;

    public int getUnArchivedData() {
        return this.unArchivedData;
    }

    public void setUnArchivedData(int unArchivedData2) {
        this.unArchivedData = unArchivedData2;
    }

    public int getUnitSwitch() {
        return this.unitSwitch;
    }

    public void setUnitSwitch(int unitSwitch2) {
        this.unitSwitch = unitSwitch2;
    }

    public boolean isAutomaticArchive() {
        return this.automaticArchive;
    }

    public void setAutomaticArchive(boolean automaticArchive2) {
        this.automaticArchive = automaticArchive2;
    }
}
