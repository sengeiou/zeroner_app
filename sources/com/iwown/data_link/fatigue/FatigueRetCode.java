package com.iwown.data_link.fatigue;

import com.iwown.data_link.base.RetCode;
import java.util.List;

public class FatigueRetCode extends RetCode {
    private FatigueDaily data;
    private int data_type;

    public class FatigueDaily {
        private List<FatigueNet> dailyData;
        private long uid;

        public FatigueDaily() {
        }

        public long getUid() {
            return this.uid;
        }

        public void setUid(long uid2) {
            this.uid = uid2;
        }

        public List<FatigueNet> getDailyData() {
            return this.dailyData;
        }

        public void setDailyData(List<FatigueNet> dailyData2) {
            this.dailyData = dailyData2;
        }

        public String toString() {
            return "FatigueDaily{uid=" + this.uid + ", dailyData=" + this.dailyData + '}';
        }
    }

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public FatigueDaily getData() {
        return this.data;
    }

    public void setData(FatigueDaily data2) {
        this.data = data2;
    }
}
