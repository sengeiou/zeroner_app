package com.iwown.ble_module.model;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.mtk_ble.MTKBle;
import com.iwown.ble_module.mtk_ble.MTKBleError;
import com.iwown.ble_module.utils.ByteUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndexTable {
    private static int dataType = -1;
    private int ctrl = -1;
    private int item_num = 0;
    private List<TableItem> mTableItems = new ArrayList();

    public class TableItem {
        private int day;
        private int end_index;
        private int hour;
        private int min;
        private int month;
        private int second;
        private int start_index;
        private int year;

        public TableItem() {
        }

        public int getSecond() {
            return this.second;
        }

        public void setSecond(int second2) {
            this.second = second2;
        }

        public int getYear() {
            return this.year;
        }

        public void setYear(int year2) {
            this.year = year2;
        }

        public int getMonth() {
            return this.month;
        }

        public void setMonth(int month2) {
            this.month = month2;
        }

        public int getDay() {
            return this.day;
        }

        public void setDay(int day2) {
            this.day = day2;
        }

        public int getStart_index() {
            return this.start_index;
        }

        public void setStart_index(int start_index2) {
            this.start_index = start_index2;
        }

        public int getEnd_index() {
            return this.end_index;
        }

        public void setEnd_index(int end_index2) {
            this.end_index = end_index2;
        }

        public int getHour() {
            return this.hour;
        }

        public void setHour(int hour2) {
            this.hour = hour2;
        }

        public int getMin() {
            return this.min;
        }

        public void setMin(int min2) {
            this.min = min2;
        }
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType2) {
        dataType = dataType2;
    }

    public int getCtrl() {
        return this.ctrl;
    }

    public void setCtrl(int ctrl2) {
        this.ctrl = ctrl2;
    }

    public int getItem_num() {
        return this.item_num;
    }

    public void setItem_num(int item_num2) {
        this.item_num = item_num2;
    }

    public List<TableItem> getmTableItems() {
        return this.mTableItems;
    }

    public void setmTableItems(List<TableItem> tableItems) {
        this.mTableItems = tableItems;
    }

    public IndexTable parseData(byte[] datas) {
        try {
            setDataType(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 2, 3)));
            setCtrl(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)));
            setItem_num(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6)));
            getmTableItems().clear();
            for (int i = 0; i < getItem_num(); i++) {
                TableItem item = new TableItem();
                int year = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 10) + 6, (i * 10) + 7)) + 2000;
                int month = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 10) + 7, (i * 10) + 8)) + 1;
                int day = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 10) + 8, (i * 10) + 9)) + 1;
                int hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 10) + 9, (i * 10) + 10));
                int min = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 10) + 10, (i * 10) + 11));
                int second = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 10) + 11, (i * 10) + 12));
                int startSeq = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 10) + 12, (i * 10) + 14));
                int endSeq = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 10) + 14, (i * 10) + 16));
                item.setYear(year);
                item.setMonth(month);
                item.setDay(day);
                item.setHour(hour);
                item.setMin(min);
                item.setSecond(second);
                item.setStart_index(startSeq);
                item.setEnd_index(endSeq);
                getmTableItems().add(item);
            }
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            MTKBle.getInstance().onError(MTKBleError.BLE_NOTIFY_DATA_PARSE_ERROR);
        }
        return this;
    }
}
