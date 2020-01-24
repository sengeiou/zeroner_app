package com.iwown.device_module.common.network.data.resp;

public class BloodpressureCode extends ReturnCode {
    private bpPreDown Data;

    public static class bpPreDown {
        private int Measured_dbp;
        private int Measured_sbp;
        private int Standard_dbp_1st;
        private int Standard_dbp_2nd;
        private int Standard_sbp_1st;
        private int Standard_sbp_2nd;
        private long Uid;

        public long getUid() {
            return this.Uid;
        }

        public void setUid(long uid) {
            this.Uid = uid;
        }

        public int getStandard_sbp_1st() {
            return this.Standard_sbp_1st;
        }

        public void setStandard_sbp_1st(int standard_sbp_1st) {
            this.Standard_sbp_1st = standard_sbp_1st;
        }

        public int getStandard_dbp_1st() {
            return this.Standard_dbp_1st;
        }

        public void setStandard_dbp_1st(int standard_dbp_1st) {
            this.Standard_dbp_1st = standard_dbp_1st;
        }

        public int getStandard_sbp_2nd() {
            return this.Standard_sbp_2nd;
        }

        public void setStandard_sbp_2nd(int standard_sbp_2nd) {
            this.Standard_sbp_2nd = standard_sbp_2nd;
        }

        public int getStandard_dbp_2nd() {
            return this.Standard_dbp_2nd;
        }

        public void setStandard_dbp_2nd(int standard_dbp_2nd) {
            this.Standard_dbp_2nd = standard_dbp_2nd;
        }

        public int getMeasured_sbp() {
            return this.Measured_sbp;
        }

        public void setMeasured_sbp(int measured_sbp) {
            this.Measured_sbp = measured_sbp;
        }

        public int getMeasured_dbp() {
            return this.Measured_dbp;
        }

        public void setMeasured_dbp(int measured_dbp) {
            this.Measured_dbp = measured_dbp;
        }
    }

    public bpPreDown getData() {
        return this.Data;
    }

    public void setData(bpPreDown data) {
        this.Data = data;
    }
}
