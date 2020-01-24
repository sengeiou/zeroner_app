package com.iwown.sport_module.net.response;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class CheckAdCode extends RetCode {
    private List<AdData> data;
    private int data_type;

    public class AdData {
        private String expire_date;
        private String redirect;
        private String start_date;
        private String url;

        public AdData() {
        }

        public String getStart_date() {
            return this.start_date;
        }

        public void setStart_date(String start_date2) {
            this.start_date = start_date2;
        }

        public String getUrl() {
            return this.url;
        }

        public void setUrl(String url2) {
            this.url = url2;
        }

        public String getRedirect() {
            return this.redirect;
        }

        public void setRedirect(String redirect2) {
            this.redirect = redirect2;
        }

        public String getExpire_date() {
            return this.expire_date;
        }

        public void setExpire_date(String expire_date2) {
            this.expire_date = expire_date2;
        }
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public List<AdData> getData() {
        return this.data;
    }

    public void setData(List<AdData> data2) {
        this.data = data2;
    }
}
