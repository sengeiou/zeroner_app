package com.iwown.data_link.heart;

import com.iwown.lib_common.date.DateUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HeartShowData {
    public String data_from;
    public DateUtil dateUtil;
    public List<Integer> detail_data = new ArrayList();
    public Map<Integer, SportHeart> sportHeartMap = new LinkedHashMap();
    public int sportTimes;
    public long uid;
    public List<Integer> y_titles = new ArrayList();

    public static class SportHeart {
        public int activity;
        public int range_end;
        public int range_start;
        public int type;

        public SportHeart(int type2, int range_start2, int range_end2) {
            this.type = type2;
            this.range_start = range_start2;
            this.range_end = range_end2;
        }

        public String toString() {
            return "SportHeart{type=" + this.type + ", range_start=" + this.range_start + ", range_end=" + this.range_end + ", activity=" + this.activity + '}';
        }
    }

    public String toString() {
        return "HeartShowData{uid=" + this.uid + ", dateUtil=" + this.dateUtil + ", data_from='" + this.data_from + '\'' + ", detail_data=" + this.detail_data + ", sportTimes=" + this.sportTimes + '}';
    }
}
