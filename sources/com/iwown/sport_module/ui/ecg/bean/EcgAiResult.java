package com.iwown.sport_module.ui.ecg.bean;

import java.util.List;

public class EcgAiResult {
    Result data;
    int errorCode;

    public class Result {
        int avgHr;
        int avgQrs;
        int avgQt;
        int avgQtc;
        List<String> diagLabels;
        String isAbnormal;
        int prIntervals;
        long recordId;

        public Result() {
        }

        public long getRecordId() {
            return this.recordId;
        }

        public void setRecordId(long recordId2) {
            this.recordId = recordId2;
        }

        public int getAvgHr() {
            return this.avgHr;
        }

        public void setAvgHr(int avgHr2) {
            this.avgHr = avgHr2;
        }

        public int getAvgQrs() {
            return this.avgQrs;
        }

        public void setAvgQrs(int avgQrs2) {
            this.avgQrs = avgQrs2;
        }

        public int getPrIntervals() {
            return this.prIntervals;
        }

        public void setPrIntervals(int prIntervals2) {
            this.prIntervals = prIntervals2;
        }

        public int getAvgQt() {
            return this.avgQt;
        }

        public void setAvgQt(int avgQt2) {
            this.avgQt = avgQt2;
        }

        public int getAvgQtc() {
            return this.avgQtc;
        }

        public void setAvgQtc(int avgQtc2) {
            this.avgQtc = avgQtc2;
        }

        public List<String> getDiagLabels() {
            return this.diagLabels;
        }

        public void setDiagLabels(List<String> diagLabels2) {
            this.diagLabels = diagLabels2;
        }

        public String getIsAbnormal() {
            return this.isAbnormal;
        }

        public void setIsAbnormal(String isAbnormal2) {
            this.isAbnormal = isAbnormal2;
        }
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode2) {
        this.errorCode = errorCode2;
    }

    public Result getData() {
        return this.data;
    }

    public void setData(Result data2) {
        this.data = data2;
    }
}
