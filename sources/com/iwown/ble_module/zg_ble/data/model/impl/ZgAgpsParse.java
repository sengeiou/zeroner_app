package com.iwown.ble_module.zg_ble.data.model.impl;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.zg_ble.data.model.ZgAgpsData;
import com.iwown.lib_common.network.utils.JsonUtils;

public class ZgAgpsParse {
    private static ZgAgpsParse instance = null;

    private ZgAgpsParse() {
    }

    public static synchronized ZgAgpsParse getInstance() {
        ZgAgpsParse zgAgpsParse;
        synchronized (ZgAgpsParse.class) {
            if (instance == null) {
                instance = new ZgAgpsParse();
            }
            zgAgpsParse = instance;
        }
        return zgAgpsParse;
    }

    public String parseStart(byte[] datas) {
        ZgAgpsData agpsStart = new ZgAgpsData();
        agpsStart.setCode(0);
        agpsStart.setDataType(ByteUtil.byteToInt(datas[0]));
        if (datas == null || datas.length < 5) {
            return JsonUtils.toJson(agpsStart);
        }
        if (datas[4] == 0) {
            agpsStart.setCode(1);
        } else if (datas[4] == 5) {
            agpsStart.setCode(5);
        } else {
            agpsStart.setCode(datas[4]);
        }
        return JsonUtils.toJson(agpsStart);
    }

    public String parseWrite(byte[] datas) {
        ZgAgpsData agpsData = new ZgAgpsData();
        if (datas == null || datas.length < 2) {
            return "";
        }
        agpsData.setDataType(ByteUtil.byteToInt(datas[0]));
        agpsData.setType(ByteUtil.byteToInt(datas[1]));
        agpsData.setCode(2);
        agpsData.setWriteCode(datas[4]);
        return JsonUtils.toJson(agpsData);
    }

    public String parseCheck(byte[] datas) {
        ZgAgpsData agpsData = new ZgAgpsData();
        agpsData.setType(datas[1]);
        agpsData.setDataType(ByteUtil.byteToInt(datas[0]));
        if (datas != null && datas.length > 5) {
            agpsData.setCode(datas[5]);
        }
        return JsonUtils.toJson(agpsData);
    }

    public String parseCheckStatue(byte[] datas) {
        ZgAgpsData agpsData = new ZgAgpsData();
        agpsData.setDataType(ByteUtil.byteToInt(datas[0]));
        agpsData.setType(2);
        if (datas != null && datas.length > 5) {
            agpsData.setCode(datas[5]);
        }
        return JsonUtils.toJson(agpsData);
    }

    public String parseAgpsPage(byte[] datas) {
        ZgAgpsData agpsData = new ZgAgpsData();
        agpsData.setDataType(ByteUtil.byteToInt(datas[0]));
        agpsData.setCode(datas[1]);
        return JsonUtils.toJson(agpsData);
    }
}
