package com.iwown.data_link.sleep_data;

import com.iwown.data_link.base.RetCode;
import com.iwown.lib_common.date.DateUtil;
import java.util.List;

public class SleepStatusData extends RetCode {
    private List<ContentBean> content;

    public static class ContentBean {
        private String data_from;
        private int feel_type;
        private int score;
        private int sleep_time_min;
        private int time;
        private long uid;

        public int getSleep_time_min() {
            return this.sleep_time_min;
        }

        public void setSleep_time_min(int sleep_time_min2) {
            this.sleep_time_min = sleep_time_min2;
        }

        public int getScore() {
            return this.score;
        }

        public void setScore(int score2) {
            this.score = score2;
        }

        public long getUid() {
            return this.uid;
        }

        public void setUid(long uid2) {
            this.uid = uid2;
        }

        public int getTime() {
            return this.time;
        }

        public void setTime(int time2) {
            this.time = time2;
        }

        public String getData_from() {
            return this.data_from;
        }

        public void setData_from(String data_from2) {
            this.data_from = data_from2;
        }

        public int getFeel_type() {
            return this.feel_type;
        }

        public void setFeel_type(int feel_type2) {
            this.feel_type = feel_type2;
        }

        public String toString() {
            return "ContentBean{uid=" + this.uid + ", time=" + new DateUtil((long) this.time, true).getY_M_D() + ", data_from='" + this.data_from + '\'' + ", feel_type=" + this.feel_type + ", score=" + this.score + ", sleep_time_min=" + this.sleep_time_min + '}';
        }
    }

    public List<ContentBean> getContent() {
        return this.content;
    }

    public void setContent(List<ContentBean> content2) {
        this.content = content2;
    }

    public String toString() {
        return "SleepStatusData{content=" + this.content + '}';
    }
}
