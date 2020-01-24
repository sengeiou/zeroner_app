package com.iwown.device_module.common.network.data.resp;

public class BandScaleInfo extends RetCode {
    Scale data;

    public class Scale {
        String scaleid;
        long uid;

        public Scale() {
        }

        public long getUid() {
            return this.uid;
        }

        public void setUid(long uid2) {
            this.uid = uid2;
        }

        public String getMac() {
            return this.scaleid;
        }

        public void setMac(String mac) {
            this.scaleid = mac;
        }
    }

    public Scale getData() {
        return this.data;
    }

    public void setData(Scale data2) {
        this.data = data2;
    }
}
