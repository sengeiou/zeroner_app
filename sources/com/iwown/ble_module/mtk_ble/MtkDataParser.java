package com.iwown.ble_module.mtk_ble;

import com.iwown.ble_module.model.AlarmClockBean;
import com.iwown.ble_module.model.DeviceTime;
import com.iwown.ble_module.model.ECG_Data;
import com.iwown.ble_module.model.FMdeviceInfo;
import com.iwown.ble_module.model.GnssMinData;
import com.iwown.ble_module.model.HealthDailyData;
import com.iwown.ble_module.model.HealthMinData;
import com.iwown.ble_module.model.IWBleParams;
import com.iwown.ble_module.model.IWDevSetting;
import com.iwown.ble_module.model.IWHeartWarning;
import com.iwown.ble_module.model.IWUserInfo;
import com.iwown.ble_module.model.IndexTable;
import com.iwown.ble_module.model.KeyModel;
import com.iwown.ble_module.model.Power;
import com.iwown.ble_module.model.ProductInfo;
import com.iwown.ble_module.model.QuietModeInfo;
import com.iwown.ble_module.model.R1HealthMinuteData;
import com.iwown.ble_module.model.ScheduleBean;
import com.iwown.ble_module.model.SedentaryInfo;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.ble_module.utils.JsonTool;
import java.util.Arrays;
import java.util.HashMap;

class MtkDataParser {
    MtkDataParser() {
    }

    public static String parseData(int dataType, byte[] data) {
        String result = "";
        switch (dataType) {
            case 0:
                FMdeviceInfo fMdeviceInfo = new FMdeviceInfo();
                fMdeviceInfo.parseData(data);
                return JsonTool.toJson(fMdeviceInfo);
            case 1:
                Power power = new Power();
                power.parseData(data);
                return JsonTool.toJson(power);
            case 6:
                QuietModeInfo quietModeInfo = new QuietModeInfo();
                quietModeInfo.parseData(data);
                return JsonTool.toJson(quietModeInfo);
            case 11:
                ProductInfo info = new ProductInfo();
                info.cmd = ByteUtil.bytesToString(data);
                return JsonTool.toJson(info);
            case 12:
                ProductInfo info1 = new ProductInfo();
                info1.cmd = ByteUtil.bytesToString(data);
                return JsonTool.toJson(info1);
            case 17:
                DeviceTime deviceTime = new DeviceTime();
                deviceTime.parseData(data);
                return JsonTool.toJson(deviceTime);
            case 19:
                IWBleParams iwBleParams = new IWBleParams();
                iwBleParams.parseData(data);
                return JsonTool.toJson(iwBleParams);
            case 21:
                break;
            case 23:
                SedentaryInfo sedentaryInfo = new SedentaryInfo();
                sedentaryInfo.parseData(data);
                return JsonTool.toJson(sedentaryInfo);
            case 25:
                IWDevSetting iwDevSetting = new IWDevSetting();
                iwDevSetting.parseData(data);
                return JsonTool.toJson(iwDevSetting);
            case 29:
                int success = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
                HashMap hashMap = new HashMap();
                hashMap.put("success", Integer.valueOf(success));
                return JsonTool.toJson(hashMap);
            case 30:
                ScheduleBean scheduleBean = new ScheduleBean();
                scheduleBean.parseData(data);
                return JsonTool.toJson(scheduleBean);
            case 33:
                IWUserInfo iwUserInfo = new IWUserInfo();
                iwUserInfo.parseData(data);
                return JsonTool.toJson(iwUserInfo);
            case 36:
                IWHeartWarning iwHeartWarning = new IWHeartWarning();
                iwHeartWarning.parseData(data);
                String result2 = JsonTool.toJson(iwHeartWarning);
                break;
            case 64:
                KeyModel keyModel = new KeyModel();
                keyModel.parseData(data);
                return JsonTool.toJson(keyModel);
            case 96:
                HealthDailyData healthDailyData = new HealthDailyData();
                healthDailyData.parseData(data);
                return JsonTool.toJson(healthDailyData);
            case 97:
                if (data.length <= 5) {
                    return result;
                }
                int ctrl = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
                if (ctrl == 0) {
                    IndexTable indexTable = new IndexTable();
                    indexTable.parseData(data);
                    return JsonTool.toJson(indexTable);
                } else if (ctrl != 1) {
                    return result;
                } else {
                    HealthMinData healthMinData = new HealthMinData();
                    healthMinData.parseData(data);
                    return JsonTool.toJson(healthMinData);
                }
            case 98:
                if (data.length <= 5) {
                    return result;
                }
                int ctrl1 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
                if (ctrl1 == 0) {
                    IndexTable indexTable2 = new IndexTable();
                    indexTable2.parseData(data);
                    return JsonTool.toJson(indexTable2);
                } else if (ctrl1 != 1) {
                    return result;
                } else {
                    GnssMinData gnssMinData = new GnssMinData();
                    gnssMinData.parseData(data);
                    return JsonTool.toJson(gnssMinData);
                }
            case 100:
                if (data.length <= 5) {
                    return result;
                }
                int ctrl2 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
                if (ctrl2 == 0) {
                    IndexTable indexTable3 = new IndexTable();
                    indexTable3.parseData(data);
                    return JsonTool.toJson(indexTable3);
                } else if (ctrl2 != 1) {
                    return result;
                } else {
                    ECG_Data ecg_data = new ECG_Data();
                    ecg_data.parseData(data);
                    return JsonTool.toJson(ecg_data);
                }
            case 104:
                if (data.length <= 5) {
                    return result;
                }
                int ctrl3 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
                if (ctrl3 == 0) {
                    IndexTable indexTable4 = new IndexTable();
                    indexTable4.parseData(data);
                    return JsonTool.toJson(indexTable4);
                } else if (ctrl3 != 1) {
                    return result;
                } else {
                    R1HealthMinuteData healthMinData2 = new R1HealthMinuteData();
                    healthMinData2.parseData(data);
                    return JsonTool.toJson(healthMinData2);
                }
            default:
                return result;
        }
        AlarmClockBean alarmClockBean = new AlarmClockBean();
        alarmClockBean.parseData(data);
        return JsonTool.toJson(alarmClockBean);
    }
}
