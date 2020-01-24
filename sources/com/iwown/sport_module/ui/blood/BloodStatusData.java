package com.iwown.sport_module.ui.blood;

import java.util.List;

public class BloodStatusData {
    private BloodStatusData1 Data;
    private int ReturnCode;

    public static class BloodStatusData1 {
        public List<ContentBean> BPDataIndex;
        private int Uid;

        public int getUid() {
            return this.Uid;
        }

        public void setUid(int uid) {
            this.Uid = uid;
        }

        public List<ContentBean> getBPDataIndex() {
            return this.BPDataIndex;
        }

        public void setBPDataIndex(List<ContentBean> BPDataIndex2) {
            this.BPDataIndex = BPDataIndex2;
        }
    }

    public static class ContentBean {
        private String Data_from;
        private String Date;

        public String getDate() {
            return this.Date;
        }

        public void setDate(String date) {
            this.Date = date;
        }

        public String getData_from() {
            return this.Data_from;
        }

        public void setData_from(String data_from) {
            this.Data_from = data_from;
        }
    }

    public int getReturnCode() {
        return this.ReturnCode;
    }

    public void setReturnCode(int returnCode) {
        this.ReturnCode = returnCode;
    }

    public BloodStatusData1 getData() {
        return this.Data;
    }

    public void setData(BloodStatusData1 data) {
        this.Data = data;
    }
}
