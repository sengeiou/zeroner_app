package com.iwown.device_module.common.network.data.req;

import java.util.List;

public class UpSportBallUrl {
    private List<SportGpsUrl> Data;
    private long uid;

    public static class SportGpsUrl {
        private String Data_from;
        private String Hr_data_url;
        private String Start_time;

        public String getData_from() {
            return this.Data_from;
        }

        public void setData_from(String data_from) {
            this.Data_from = data_from;
        }

        public String getStart_time() {
            return this.Start_time;
        }

        public void setStart_time(String start_time) {
            this.Start_time = start_time;
        }

        public String getHr_data_url() {
            return this.Hr_data_url;
        }

        public void setHr_data_url(String hr_data_url) {
            this.Hr_data_url = hr_data_url;
        }
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
    }

    public List<SportGpsUrl> getData() {
        return this.Data;
    }

    public void setData(List<SportGpsUrl> data) {
        this.Data = data;
    }
}
