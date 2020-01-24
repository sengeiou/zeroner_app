package com.iwown.ble_module.zg_ble.data.model.impl;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.model.WelcomeBloodData;
import com.iwown.ble_module.zg_ble.data.model.WelcomeBloodData.BloodPressure;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class WelcomeBloodParse {
    private static WelcomeBloodParse instance = null;
    private WelcomeBloodData bloodData = new WelcomeBloodData();

    private WelcomeBloodParse() {
    }

    public static synchronized WelcomeBloodParse getInstance() {
        WelcomeBloodParse welcomeBloodParse;
        synchronized (WelcomeBloodParse.class) {
            if (instance == null) {
                instance = new WelcomeBloodParse();
            }
            welcomeBloodParse = instance;
        }
        return welcomeBloodParse;
    }

    public void parseWelcome(byte[] datas) {
        try {
            this.bloodData.setBlood(null);
            this.bloodData.setWelcome(new String(Arrays.copyOfRange(datas, 5, ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)) + 5), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            ThrowableExtension.printStackTrace(e);
        }
        if (datas.length >= 18) {
            this.bloodData.setHeight(ByteUtil.byteToInt(datas[16]));
            this.bloodData.setGender(ByteUtil.byteToInt(datas[17]));
            this.bloodData.setTimeZone(ByteUtil.byteToInt(datas[15]));
        }
    }

    public String parseBlood(byte[] datas) {
        if (datas[2] == -127) {
            parseWelcome(datas);
            this.bloodData.setOld8D(1);
        } else {
            this.bloodData.setOld8D(0);
            if (datas.length >= 12) {
                BloodPressure bloodPressure = new BloodPressure();
                bloodPressure.setSrcSbp_LB(ByteUtil.byteToInt(datas[4]));
                bloodPressure.setSrcSbp_HB(ByteUtil.byteToInt(datas[5]));
                bloodPressure.setSrcDbp_LB(ByteUtil.byteToInt(datas[6]));
                bloodPressure.setSrcDbp_HB(ByteUtil.byteToInt(datas[7]));
                bloodPressure.setDstSbp_LB(ByteUtil.byteToInt(datas[8]));
                bloodPressure.setDstSbp_HB(ByteUtil.byteToInt(datas[9]));
                bloodPressure.setDstDbp_LB(ByteUtil.byteToInt(datas[10]));
                bloodPressure.setDstDbp_HB(ByteUtil.byteToInt(datas[11]));
                this.bloodData.setBlood(bloodPressure);
            }
        }
        return JsonTool.toJson(this.bloodData);
    }
}
