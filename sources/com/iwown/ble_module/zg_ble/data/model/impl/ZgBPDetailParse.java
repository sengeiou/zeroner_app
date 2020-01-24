package com.iwown.ble_module.zg_ble.data.model.impl;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.data.model.Result;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.ble_module.zg_ble.utils.Util;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZgBPDetailParse extends Result {
    private static ZgBPDetailParse instance;
    private List<String> dataStr = new ArrayList();
    private byte[] mData;

    public class BPData {
        int day;
        int dbp;
        int hour;
        int minute;
        int month;
        int sbp;
        int year;

        public BPData() {
        }

        public int getYear() {
            return this.year;
        }

        public void setYear(int year2) {
            this.year = year2;
        }

        public int getMonth() {
            return this.month;
        }

        public void setMonth(int month2) {
            this.month = month2;
        }

        public int getDay() {
            return this.day;
        }

        public void setDay(int day2) {
            this.day = day2;
        }

        public int getHour() {
            return this.hour;
        }

        public void setHour(int hour2) {
            this.hour = hour2;
        }

        public int getSbp() {
            return this.sbp;
        }

        public void setSbp(int sbp2) {
            this.sbp = sbp2;
        }

        public int getDbp() {
            return this.dbp;
        }

        public void setDbp(int dbp2) {
            this.dbp = dbp2;
        }

        public int getMinute() {
            return this.minute;
        }

        public void setMinute(int minute2) {
            this.minute = minute2;
        }
    }

    public static synchronized ZgBPDetailParse getInstance() {
        ZgBPDetailParse zgBPDetailParse;
        synchronized (ZgBPDetailParse.class) {
            if (instance == null) {
                instance = new ZgBPDetailParse();
            }
            zgBPDetailParse = instance;
        }
        return zgBPDetailParse;
    }

    private ZgBPDetailParse() {
    }

    public String parseBPData() {
        List<BPData> data = new ArrayList<>();
        if (this.mData.length != 80) {
            KLog.e("---bp data length error");
            return null;
        }
        int i = 0;
        while (i < 10) {
            try {
                int year = ByteUtil.bytesToInt(Arrays.copyOfRange(this.mData, i * 8, (i * 8) + 2));
                int month = ByteUtil.bytesToInt(Arrays.copyOfRange(this.mData, (i * 8) + 2, (i * 8) + 3));
                int day = ByteUtil.bytesToInt(Arrays.copyOfRange(this.mData, (i * 8) + 3, (i * 8) + 4));
                int hour = ByteUtil.bytesToInt(Arrays.copyOfRange(this.mData, (i * 8) + 4, (i * 8) + 5));
                int min = ByteUtil.bytesToInt(Arrays.copyOfRange(this.mData, (i * 8) + 5, (i * 8) + 6));
                int sbp = ByteUtil.bytesToInt(Arrays.copyOfRange(this.mData, (i * 8) + 6, (i * 8) + 7));
                int dbp = ByteUtil.bytesToInt(Arrays.copyOfRange(this.mData, (i * 8) + 7, (i * 8) + 8));
                BPData bpData = new BPData();
                bpData.setYear(year);
                bpData.setMonth(month);
                bpData.setDay(day);
                bpData.setHour(hour);
                bpData.setMinute(min);
                bpData.setSbp(sbp);
                bpData.setDbp(dbp);
                if (!(year == 0 || month == 0 || day == 0)) {
                    data.add(bpData);
                }
                i++;
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        if (data.size() <= 0) {
            return null;
        }
        this.mData = new byte[0];
        this.dataStr.clear();
        return JsonTool.toJson(data);
    }

    public boolean isDataOver(byte seq) {
        if (seq < 0) {
            return true;
        }
        return false;
    }

    public void addToArrays(byte[] data) {
        String msg = Util.bytesToString(data);
        if (this.dataStr.contains(msg)) {
            KLog.i("-----contains msg------");
            return;
        }
        this.dataStr.add(msg);
        if (data[2] == 1) {
            this.mData = new byte[0];
            this.dataStr.clear();
            this.dataStr.add(msg);
        }
        this.mData = Util.concat(this.mData, Arrays.copyOfRange(data, 4, data.length));
    }
}
