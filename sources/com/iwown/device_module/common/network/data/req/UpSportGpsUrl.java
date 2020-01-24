package com.iwown.device_module.common.network.data.req;

import java.util.List;

public class UpSportGpsUrl {
    private List<SportGpsUrl> Data;
    private long uid;

    public class SportGpsUrl {
        private String Data_from;
        private String Gps_data_url;
        private String Headset_data_url;
        private String Hr_data_url;
        private int Source_type;
        private String Start_time;

        public SportGpsUrl() {
        }

        public String getData_from() {
            return this.Data_from;
        }

        public void setData_from(String data_from) {
            this.Data_from = data_from;
        }

        public int getSource_type() {
            return this.Source_type;
        }

        public void setSource_type(int source_type) {
            this.Source_type = source_type;
        }

        public String getStart_time() {
            return this.Start_time;
        }

        public void setStart_time(String start_time) {
            this.Start_time = start_time;
        }

        public String getGps_data_url() {
            return this.Gps_data_url;
        }

        public void setGps_data_url(String gps_data_url) {
            this.Gps_data_url = gps_data_url;
        }

        public String getHr_data_url() {
            return this.Hr_data_url;
        }

        public void setHr_data_url(String hr_data_url) {
            this.Hr_data_url = hr_data_url;
        }

        public String getHeadset_data_url() {
            return this.Headset_data_url;
        }

        public void setHeadset_data_url(String headset_data_url) {
            this.Headset_data_url = headset_data_url;
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
