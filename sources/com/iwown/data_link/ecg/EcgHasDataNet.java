package com.iwown.data_link.ecg;

import com.iwown.data_link.base.NggReturnCode;
import java.util.List;

public class EcgHasDataNet extends NggReturnCode {
    private List<EcgHasData> Data;

    public static class EcgHasData {
        private String Data_from;
        private String Date;
        private long unixTime;

        public long getUnixTime() {
            return this.unixTime;
        }

        public void setUnixTime(long unixTime2) {
            this.unixTime = unixTime2;
        }

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

    public List<EcgHasData> getData() {
        return this.Data;
    }

    public void setData(List<EcgHasData> data) {
        this.Data = data;
    }
}
