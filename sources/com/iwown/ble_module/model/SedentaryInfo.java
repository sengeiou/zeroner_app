package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SedentaryInfo {
    public static int CALLBACK_ITEM_COUNT = 3;
    private static final long serialVersionUID = 1;
    private List<Sedentary> mList = new ArrayList();

    public class Sedentary {
        public int duration;
        public int end_hour;
        public int repeat;
        public int start_hour;
        public int work_counts;

        public Sedentary() {
        }

        public int getRepeat() {
            return this.repeat;
        }

        public void setRepeat(int repeat2) {
            this.repeat = repeat2;
        }

        public int getStart_hour() {
            return this.start_hour;
        }

        public void setStart_hour(int start_hour2) {
            this.start_hour = start_hour2;
        }

        public int getEnd_hour() {
            return this.end_hour;
        }

        public void setEnd_hour(int end_hour2) {
            this.end_hour = end_hour2;
        }

        public int getDuration() {
            return this.duration;
        }

        public void setDuration(int duration2) {
            this.duration = duration2;
        }

        public int getWork_counts() {
            return this.work_counts;
        }

        public void setWork_counts(int work_counts2) {
            this.work_counts = work_counts2;
        }
    }

    public List<Sedentary> getList() {
        return this.mList;
    }

    public void setList(List<Sedentary> list) {
        this.mList = list;
    }

    public void parseData(byte[] datas) {
        int i = 0;
        while (i < CALLBACK_ITEM_COUNT) {
            try {
                Sedentary sedentary = new Sedentary();
                sedentary.setRepeat(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 6) + 4, (i * 6) + 5)));
                sedentary.setStart_hour(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 6) + 5, (i * 6) + 6)));
                sedentary.setEnd_hour(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 6) + 6, (i * 6) + 7)));
                sedentary.setDuration(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 6) + 7, (i * 6) + 8)) * 5);
                sedentary.setWork_counts(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 6) + 8, (i * 6) + 10)));
                getList().add(sedentary);
                i++;
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
                MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
                return;
            }
        }
    }
}
