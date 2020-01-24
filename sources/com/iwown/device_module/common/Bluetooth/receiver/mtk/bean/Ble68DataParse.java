package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

import com.google.gson.Gson;
import com.iwown.ble_module.model.IndexTable;
import com.iwown.ble_module.model.IndexTable.TableItem;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.headset.DataIndex_68;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.litepal.crud.DataSupport;

public class Ble68DataParse {
    public static DateUtil date = new DateUtil();
    private int avg_hr;
    private int calorie;
    private int ctrl;
    private int data_type;
    private int day;
    private int distance;
    private int flight_avg;
    private int flight_max;
    private int flight_min;
    private int hour;
    private int max_hr;
    private int min;
    private int min_hr;
    private int month;
    private int rateOfStride_avg;
    private int rateOfStride_max;
    private int rateOfStride_min;
    private int seconds;
    private int seq;
    private int sport_type;
    private int state_type;
    private int step;
    private int touchDownPower_avg;
    private int touchDownPower_balance;
    private int touchDownPower_max;
    private int touchDownPower_min;
    private int touchDownPower_stop;
    private int touchDown_avg;
    private int touchDown_max;
    private int touchDown_min;
    private int year;

    public static List<DataIndex_68> parseCtrl0(String result) {
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        String data_from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        IndexTable indexTable = (IndexTable) new Gson().fromJson(result, IndexTable.class);
        if (indexTable == null || indexTable.getmTableItems() == null) {
            return null;
        }
        List<DataIndex_68> dataIndexList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.add(6, -60);
        Date benchmarkDate = cal.getTime();
        KLog.e(String.format("---*device to proceed 68:%s, index count:%d", new Object[]{data_from, Integer.valueOf(indexTable.getmTableItems().size())}));
        for (TableItem tableItem : indexTable.getmTableItems()) {
            KLog.e(String.format("----*index date:%d-%d-%d", new Object[]{Integer.valueOf(tableItem.getYear()), Integer.valueOf(tableItem.getMonth()), Integer.valueOf(tableItem.getDay())}));
            Calendar indexCal = Calendar.getInstance();
            indexCal.set(1, tableItem.getYear());
            indexCal.set(2, tableItem.getMonth());
            indexCal.set(5, tableItem.getDay());
            if (!indexCal.getTime().before(benchmarkDate) && tableItem.getStart_index() != tableItem.getEnd_index()) {
                List<DataIndex_68> dbIndexList = DataSupport.where("uid=? and device_name=? and year=? and month=? and day=? and start_idx=? and end_idx=?", String.valueOf(uid), data_from, String.valueOf(tableItem.getYear()), String.valueOf(tableItem.getMonth()), String.valueOf(tableItem.getDay()), String.valueOf(tableItem.getStart_index()), String.valueOf(tableItem.getEnd_index())).find(DataIndex_68.class);
                if (dbIndexList == null || dbIndexList.size() <= 0) {
                    DataIndex_68 dataIndex = new DataIndex_68();
                    dataIndex.setUid(uid);
                    dataIndex.setDevice_name(data_from);
                    dataIndex.setYear(tableItem.getYear());
                    dataIndex.setMonth(tableItem.getMonth());
                    dataIndex.setDay(tableItem.getDay());
                    dataIndex.setStart_idx(tableItem.getStart_index());
                    dataIndex.setEnd_idx(tableItem.getEnd_index());
                    dataIndex.setProcessed(0);
                    dataIndex.setSend_cmd(ByteUtil.byteArrayToString(new byte[]{(byte) (tableItem.getStart_index() & 255), (byte) (tableItem.getStart_index() >>> 8), (byte) (tableItem.getEnd_index() & 255), (byte) (tableItem.getEnd_index() >>> 8)}));
                    dataIndexList.add(dataIndex);
                    dataIndex.saveOrUpdate("uid=? and device_name=? and year=? and month=? and day=? and start_idx=? and end_idx=?", String.valueOf(uid), data_from, String.valueOf(dataIndex.getYear()), String.valueOf(dataIndex.getMonth()), String.valueOf(dataIndex.getDay()), String.valueOf(dataIndex.getStart_idx()), String.valueOf(dataIndex.getEnd_idx()));
                } else {
                    KLog.e("---*index proceed before,continue");
                }
            }
        }
        return dataIndexList;
    }

    public static Ble68DataParse parse(byte[] datas) {
        int index;
        Ble68DataParse ble68DataParse = new Ble68DataParse();
        ble68DataParse.setCtrl(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)));
        ble68DataParse.setSeq(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 7)));
        ble68DataParse.setYear(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)) + 2000);
        ble68DataParse.setMonth(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)) + 1);
        ble68DataParse.setDay(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10)) + 1);
        ble68DataParse.setHour(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11)));
        ble68DataParse.setMin(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12)));
        ble68DataParse.setSeconds(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13)));
        int data_type2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
        ble68DataParse.setData_type(data_type2);
        byte[] data = ByteUtil.byteToBitArray(data_type2);
        if (data[2] == 1 && data.length >= 51) {
            byte[] walkDataBytes = Arrays.copyOfRange(datas, 14, 51);
            ble68DataParse.setSport_type(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 0, 2)));
            ble68DataParse.setState_type(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 2, 3)));
            ble68DataParse.setStep(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 3, 5)));
            ble68DataParse.setDistance(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 5, 7)));
            ble68DataParse.setCalorie(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 7, 9)));
            ble68DataParse.setRateOfStride_min(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 9, 11)));
            ble68DataParse.setRateOfStride_max(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 11, 13)));
            ble68DataParse.setRateOfStride_avg(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 13, 15)));
            ble68DataParse.setFlight_min(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 15, 17)));
            ble68DataParse.setFlight_max(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 17, 19)));
            ble68DataParse.setFlight_avg(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 19, 21)));
            ble68DataParse.setTouchDown_min(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 21, 23)));
            ble68DataParse.setTouchDown_max(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 23, 25)));
            ble68DataParse.setTouchDown_avg(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 25, 27)));
            ble68DataParse.setTouchDownPower_min(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 27, 29)));
            ble68DataParse.setTouchDownPower_max(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 29, 31)));
            ble68DataParse.setTouchDownPower_avg(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 31, 33)));
            ble68DataParse.setTouchDownPower_balance(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 33, 35)));
            ble68DataParse.setTouchDownPower_stop(ByteUtil.bytesToInt(Arrays.copyOfRange(walkDataBytes, 35, 37)));
        }
        if (data[7] == 1) {
            if (data[2] != 1 || datas.length < 51) {
                index = 14;
            } else {
                index = 51;
            }
            byte[] hrDataBytes = Arrays.copyOfRange(datas, index, index + 6);
            int min_hr2 = ByteUtil.bytesToInt(Arrays.copyOfRange(hrDataBytes, 0, 2));
            int max_hr2 = ByteUtil.bytesToInt(Arrays.copyOfRange(hrDataBytes, 2, 4));
            int avg_hr2 = ByteUtil.bytesToInt(Arrays.copyOfRange(hrDataBytes, 4, 6));
            ble68DataParse.setMax_hr(max_hr2);
            ble68DataParse.setAvg_hr(avg_hr2);
            ble68DataParse.setMin_hr(min_hr2);
        }
        return ble68DataParse;
    }

    public int getSeconds() {
        return this.seconds;
    }

    public void setSeconds(int seconds2) {
        this.seconds = seconds2;
    }

    public int getCtrl() {
        return this.ctrl;
    }

    public void setCtrl(int ctrl2) {
        this.ctrl = ctrl2;
    }

    public int getSeq() {
        return this.seq;
    }

    public void setSeq(int seq2) {
        this.seq = seq2;
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

    public int getData_type() {
        return this.data_type;
    }

    public void setData_type(int data_type2) {
        this.data_type = data_type2;
    }

    public int getSport_type() {
        return this.sport_type;
    }

    public void setSport_type(int sport_type2) {
        this.sport_type = sport_type2;
    }

    public int getState_type() {
        return this.state_type;
    }

    public void setState_type(int state_type2) {
        this.state_type = state_type2;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int distance2) {
        this.distance = distance2;
    }

    public int getCalorie() {
        return this.calorie;
    }

    public void setCalorie(int calorie2) {
        this.calorie = calorie2;
    }

    public int getRateOfStride_min() {
        return this.rateOfStride_min;
    }

    public void setRateOfStride_min(int rateOfStride_min2) {
        this.rateOfStride_min = rateOfStride_min2;
    }

    public int getRateOfStride_max() {
        return this.rateOfStride_max;
    }

    public void setRateOfStride_max(int rateOfStride_max2) {
        this.rateOfStride_max = rateOfStride_max2;
    }

    public int getRateOfStride_avg() {
        return this.rateOfStride_avg;
    }

    public void setRateOfStride_avg(int rateOfStride_avg2) {
        this.rateOfStride_avg = rateOfStride_avg2;
    }

    public int getFlight_min() {
        return this.flight_min;
    }

    public void setFlight_min(int flight_min2) {
        this.flight_min = flight_min2;
    }

    public int getFlight_max() {
        return this.flight_max;
    }

    public void setFlight_max(int flight_max2) {
        this.flight_max = flight_max2;
    }

    public int getFlight_avg() {
        return this.flight_avg;
    }

    public void setFlight_avg(int flight_avg2) {
        this.flight_avg = flight_avg2;
    }

    public int getTouchDown_min() {
        return this.touchDown_min;
    }

    public void setTouchDown_min(int touchDown_min2) {
        this.touchDown_min = touchDown_min2;
    }

    public int getTouchDown_max() {
        return this.touchDown_max;
    }

    public void setTouchDown_max(int touchDown_max2) {
        this.touchDown_max = touchDown_max2;
    }

    public int getTouchDown_avg() {
        return this.touchDown_avg;
    }

    public void setTouchDown_avg(int touchDown_avg2) {
        this.touchDown_avg = touchDown_avg2;
    }

    public int getTouchDownPower_min() {
        return this.touchDownPower_min;
    }

    public void setTouchDownPower_min(int touchDownPower_min2) {
        this.touchDownPower_min = touchDownPower_min2;
    }

    public int getTouchDownPower_max() {
        return this.touchDownPower_max;
    }

    public void setTouchDownPower_max(int touchDownPower_max2) {
        this.touchDownPower_max = touchDownPower_max2;
    }

    public int getTouchDownPower_avg() {
        return this.touchDownPower_avg;
    }

    public void setTouchDownPower_avg(int touchDownPower_avg2) {
        this.touchDownPower_avg = touchDownPower_avg2;
    }

    public int getTouchDownPower_balance() {
        return this.touchDownPower_balance;
    }

    public void setTouchDownPower_balance(int touchDownPower_balance2) {
        this.touchDownPower_balance = touchDownPower_balance2;
    }

    public int getTouchDownPower_stop() {
        return this.touchDownPower_stop;
    }

    public void setTouchDownPower_stop(int touchDownPower_stop2) {
        this.touchDownPower_stop = touchDownPower_stop2;
    }

    public int getMin_hr() {
        return this.min_hr;
    }

    public void setMin_hr(int min_hr2) {
        this.min_hr = min_hr2;
    }

    public int getMax_hr() {
        return this.max_hr;
    }

    public void setMax_hr(int max_hr2) {
        this.max_hr = max_hr2;
    }

    public int getAvg_hr() {
        return this.avg_hr;
    }

    public void setAvg_hr(int avg_hr2) {
        this.avg_hr = avg_hr2;
    }

    public static DateUtil getDate() {
        return date;
    }

    public static void setDate(DateUtil date2) {
        date = date2;
    }

    public String toString() {
        return "";
    }
}
