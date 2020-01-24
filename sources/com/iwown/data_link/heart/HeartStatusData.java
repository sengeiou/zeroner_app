package com.iwown.data_link.heart;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class HeartStatusData extends RetCode {
    private List<ContentBean> content;

    public static class ContentBean {
        private String data_from;
        private int data_status;
        private long uid;
        private int unix_time;

        public long getUid() {
            return this.uid;
        }

        public void setUid(long uid2) {
            this.uid = uid2;
        }

        public int getData_status() {
            return this.data_status;
        }

        public void setData_status(int data_status2) {
            this.data_status = data_status2;
        }

        public String getData_from() {
            return this.data_from;
        }

        public void setData_from(String data_from2) {
            this.data_from = data_from2;
        }

        public int getUnix_time() {
            return this.unix_time;
        }

        public void setUnix_time(int unix_time2) {
            this.unix_time = unix_time2;
        }

        public String toString() {
            return "ContentBean{uid=" + this.uid + ", data_status=" + this.data_status + ", data_from='" + this.data_from + '\'' + ", unix_time=" + this.unix_time + '}';
        }
    }

    public List<ContentBean> getContent() {
        return this.content;
    }

    public void setContent(List<ContentBean> content2) {
        this.content = content2;
    }

    public String toString() {
        return "HeartStatusData{content=" + this.content + '}';
    }
}
