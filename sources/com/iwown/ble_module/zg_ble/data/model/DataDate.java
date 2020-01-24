package com.iwown.ble_module.zg_ble.data.model;

import com.iwown.ble_module.zg_ble.utils.ByteUtil;

public class DataDate {
    private static DataDate instance = new DataDate();
    private byte[] data = new byte[36];
    private boolean mDataReady = false;

    private DataDate() {
    }

    public static DataDate getInstance() {
        return instance;
    }

    public void setData(byte[] responseData, byte seq) {
        if (seq == -125) {
            if (responseData.length < 4) {
                throw new IllegalArgumentException("last pack to fetch data date should contains 4 bytes");
            }
            for (int i = 0; i < 4; i++) {
                this.data[i + 32] = responseData[i + 4];
            }
            this.mDataReady = true;
        } else if (seq == 1) {
            for (int i2 = 0; i2 < 16; i2++) {
                this.data[i2] = responseData[i2 + 4];
            }
        } else if (seq == 2) {
            for (int i3 = 0; i3 < 16; i3++) {
                this.data[i3 + 16] = responseData[i3 + 4];
            }
        }
    }

    public SevenDayStore getDataDateObject() {
        if (!this.mDataReady) {
            return null;
        }
        SevenDayStore dateStore = new SevenDayStore();
        dateStore.totalDays = this.data[0];
        for (int i = 0; i < dateStore.totalDays; i++) {
            dateStore.storeDateObject.add(getOneDate(this.data[(i * 4) + 8 + 1], this.data[(i * 4) + 8], this.data[(i * 4) + 8 + 2], this.data[(i * 4) + 8 + 3]));
        }
        return dateStore;
    }

    private EveryDayInfo getOneDate(byte bYearHigh, byte bYearLow, byte bMonth, byte bDay) {
        EveryDayInfo dailyDate = new EveryDayInfo();
        dailyDate.year = (long) ByteUtil.bytesToInt(new byte[]{bYearLow, bYearHigh});
        dailyDate.month = bMonth;
        dailyDate.day = bDay;
        return dailyDate;
    }
}
