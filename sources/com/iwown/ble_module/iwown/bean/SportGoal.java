package com.iwown.ble_module.iwown.bean;

import com.iwown.ble_module.iwown.utils.ByteUtil;
import java.util.ArrayList;
import java.util.Arrays;

public class SportGoal {
    private ArrayList<SportInfo> mList = new ArrayList<>();
    private int week = -1;

    public class SportInfo {
        private int goal = 0;
        private int sport_type = 0;

        public SportInfo() {
        }

        public int getSport_type() {
            return this.sport_type;
        }

        public void setSport_type(int sport_type2) {
            this.sport_type = sport_type2;
        }

        public int getGoal() {
            return this.goal;
        }

        public void setGoal(int goal2) {
            this.goal = goal2;
        }
    }

    public ArrayList<SportInfo> getmList() {
        return this.mList;
    }

    public void setmList(ArrayList<SportInfo> mList2) {
        this.mList = mList2;
    }

    public int getWeek() {
        return this.week;
    }

    public void setWeek(int week2) {
        this.week = week2;
    }

    public void parseData(byte[] datas) {
        this.week = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
        int count = (datas.length - 5) / 3;
        for (int i = 0; i < count; i++) {
            byte[] item_bytes = Arrays.copyOfRange(datas, (i * 3) + 5, (i * 3) + 5 + 3);
            if (item_bytes[0] != 0 || item_bytes[1] != 0 || item_bytes[2] != 0) {
                SportInfo info = new SportInfo();
                info.setGoal(ByteUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 0, 2)));
                info.setSport_type(ByteUtil.bytesToInt(Arrays.copyOfRange(item_bytes, 2, item_bytes.length)));
                this.mList.add(info);
            }
        }
    }
}
