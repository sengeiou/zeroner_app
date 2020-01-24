package com.iwown.sport_module.net.response;

import com.iwown.data_link.base.RetCode;
import java.util.ArrayList;
import java.util.List;

public class MonthHas28DateCode extends RetCode {
    List<RspInfoModel> content = new ArrayList();

    public static class RspInfoModel {
        private String date;
        private String from;

        public String getDate() {
            return this.date;
        }

        public void setDate(String date2) {
            this.date = date2;
        }

        public String getFrom() {
            return this.from;
        }

        public void setFrom(String from2) {
            this.from = from2;
        }

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof RspInfoModel)) {
                return false;
            }
            if (((RspInfoModel) obj).date == null) {
                return false;
            }
            return ((RspInfoModel) obj).date.equals(this.date);
        }
    }

    public List<RspInfoModel> getContent() {
        return this.content;
    }

    public void setContent(List<RspInfoModel> content2) {
        this.content = content2;
    }
}
