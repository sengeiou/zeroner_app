package com.peng.ppscalelibrary.BleManager.Model;

public class BleEnum {
    public static final String BLE_SCALE_TYPE_CE = "CE";
    public static final String BLE_SCALE_TYPE_CF = "CF";

    public enum BleMeasureType {
        BLE_MEASURE_TYPE_BODY,
        BLE_MEASURE_TYPE_BMDJ
    }

    public enum BleSex {
        Male,
        Female
    }

    public enum BleUnit {
        BLE_UNIT_KG,
        BLE_UNIT_LB,
        BLE_UNIT_JIN,
        BLE_UNIT_ST_LB
    }
}
