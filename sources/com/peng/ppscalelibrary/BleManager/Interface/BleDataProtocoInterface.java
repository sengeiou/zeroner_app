package com.peng.ppscalelibrary.BleManager.Interface;

import com.peng.ppscalelibrary.BleManager.Model.BleDeviceModel;
import com.peng.ppscalelibrary.BleManager.Model.LFPeopleGeneral;

public interface BleDataProtocoInterface {
    void deviceInfo(BleDeviceModel bleDeviceModel);

    void historyData(boolean z, LFPeopleGeneral lFPeopleGeneral, String str);

    void lockedData(LFPeopleGeneral lFPeopleGeneral, BleDeviceModel bleDeviceModel);

    void progressData(LFPeopleGeneral lFPeopleGeneral);
}
