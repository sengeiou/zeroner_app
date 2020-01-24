package com.iwown.sport_module.net.response;

import com.iwown.data_link.sport_data.ReturnCode;
import java.util.List;

public class Sport28MonthCode extends ReturnCode {
    private Month28 Data;

    public static class Month28 {
        private List<Sport28Index> SportDataIndex;
        private long Uid;

        public long getUid() {
            return this.Uid;
        }

        public void setUid(long uid) {
            this.Uid = uid;
        }

        public List<Sport28Index> getSportDataIndex() {
            return this.SportDataIndex;
        }

        public void setSportDataIndex(List<Sport28Index> sportDataIndex) {
            this.SportDataIndex = sportDataIndex;
        }
    }

    public static class Sport28Index {
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

    public Month28 getData() {
        return this.Data;
    }

    public void setData(Month28 data) {
        this.Data = data;
    }
}
