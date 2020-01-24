package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

import android.util.Log;
import com.google.gson.Gson;
import com.iwown.ble_module.model.IndexTable;
import com.iwown.ble_module.model.IndexTable.TableItem;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.Bluetooth.receiver.mtk.MtkDataParsePresenter;
import com.iwown.device_module.common.sql.TB_62_data;
import com.iwown.device_module.common.sql.TB_mtk_statue;
import com.iwown.device_module.common.sql.TB_sum_61_62_64;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class Ble62DataParse {
    public static DateUtil date = new DateUtil();
    private int ctrl;
    private int day;
    private String detail = "";
    private int freq;
    private int hour;
    private List<LongitudeAndLatitude> list;
    private int min;
    private int month;
    private int num;
    private int seq;
    private int year;

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail2) {
        this.detail = detail2;
    }

    public static void parseCtrl0(String result) {
        IndexTable indexTable = (IndexTable) new Gson().fromJson(result, IndexTable.class);
        MtkDataParsePresenter.map62.clear();
        List<TableItem> tableItems = indexTable.getmTableItems();
        DateUtil todayDate = new DateUtil();
        if (tableItems.size() > 0) {
            String from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
            long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
            for (TableItem tableItem : tableItems) {
                if ((tableItem.getYear() == todayDate.getYear() || tableItem.getYear() + 1 == todayDate.getYear()) && tableItem.getStart_index() != tableItem.getEnd_index()) {
                    TB_62_data data_62 = (TB_62_data) DataSupport.where("uid=? and year=? and month=? and day=? and data_from=? ", String.valueOf(uid), String.valueOf(tableItem.getYear()), String.valueOf(tableItem.getMonth()), String.valueOf(tableItem.getDay()), from).order("time desc").findFirst(TB_62_data.class);
                    int startSeq = tableItem.getStart_index();
                    int endSeq = tableItem.getEnd_index();
                    int begins = startSeq;
                    if (data_62 != null) {
                        startSeq = data_62.getSeq();
                    }
                    if (endSeq > 1023 && startSeq < begins) {
                        startSeq += 1024;
                    }
                    KLog.d("testf1shuju", "62需要的总数据: " + tableItem.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + tableItem.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + tableItem.getDay() + "  原始: " + begins + " -- " + endSeq + "  已同步到的: " + startSeq);
                    if (startSeq < endSeq - 1) {
                        DateUtil dateUtil = new DateUtil(tableItem.getYear(), tableItem.getMonth(), tableItem.getDay());
                        TB_mtk_statue mtk_statue = new TB_mtk_statue();
                        mtk_statue.setUid(uid);
                        mtk_statue.setData_from(from);
                        mtk_statue.setType(62);
                        mtk_statue.setYear(tableItem.getYear());
                        mtk_statue.setMonth(tableItem.getMonth());
                        mtk_statue.setDay(tableItem.getDay());
                        mtk_statue.setHas_file(2);
                        mtk_statue.setHas_up(2);
                        mtk_statue.setHas_tb(2);
                        mtk_statue.setDate(dateUtil.getUnixTimestamp());
                        mtk_statue.saveOrUpdate("uid=? and data_from=? and type=? and date=?", uid + "", from, "62", dateUtil.getUnixTimestamp() + "");
                        MtkDataParsePresenter.map62.put(dateUtil.getSyyyyMMddDate(), Integer.valueOf(1));
                        KLog.e("testf1shuju", "62有需要e同步的？？: ");
                        String sendS = ByteUtil.byteArrayToString(new byte[]{(byte) (startSeq & 255), (byte) (startSeq >>> 8), (byte) (endSeq & 255), (byte) (endSeq >>> 8)});
                        TB_sum_61_62_64 sum616264 = (TB_sum_61_62_64) DataSupport.where("date=? and send_cmd=?", dateUtil.getSyyyyMMddDate(), sendS).findFirst(TB_sum_61_62_64.class);
                        if (sum616264 == null) {
                            TB_sum_61_62_64 sum6162642 = new TB_sum_61_62_64();
                            sum6162642.setDate(dateUtil.getSyyyyMMddDate());
                            sum6162642.setDate_time(dateUtil.getUnixTimestamp());
                            sum6162642.setSend_cmd(sendS);
                            sum6162642.setSum(tableItem.getEnd_index() - startSeq);
                            sum6162642.setYear(tableItem.getYear());
                            sum6162642.setMonth(tableItem.getMonth());
                            sum6162642.setDay(tableItem.getDay());
                            sum6162642.setType(ByteUtil.bytesToInt(new byte[]{98}));
                            sum6162642.setType_str("0x62");
                            sum6162642.save();
                        } else {
                            sum616264.setYear(dateUtil.getYear());
                            sum616264.setMonth(dateUtil.getMonth());
                            sum616264.setDay(dateUtil.getDay());
                            sum616264.setDate(dateUtil.getSyyyyMMddDate());
                            sum616264.setDate_time(dateUtil.getUnixTimestamp());
                            sum616264.setSend_cmd(sendS);
                            sum616264.setSum(tableItem.getEnd_index() - startSeq);
                            sum616264.setType(ByteUtil.bytesToInt(new byte[]{98}));
                            sum616264.setType_str("0x62");
                            sum616264.updateAll("date=? and send_cmd=?", dateUtil.getSyyyyMMddDate(), sendS);
                        }
                    }
                }
            }
        }
    }

    public static Ble62DataParse parse(byte[] datas) {
        Ble62DataParse ble62DataParse = new Ble62DataParse();
        List<LongitudeAndLatitude> list2 = new ArrayList<>();
        int ctrl2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
        int seq2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 7));
        int year2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)) + 2000;
        int month2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)) + 1;
        int day2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10)) + 1;
        int hour2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11));
        int min2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12));
        int freq2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
        int num2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 13, 14));
        for (int i = 0; i < num2; i++) {
            GnssData gnssData = new GnssData();
            gnssData.setLongitude_degree(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 14, (i * 14) + 15)));
            gnssData.setLongitude_minute(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 15, (i * 14) + 16)));
            gnssData.setLongitude_second(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 16, (i * 14) + 17)));
            gnssData.setLongitude_preci(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 17, (i * 14) + 18)));
            gnssData.setLongitude_direction(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 18, (i * 14) + 19)));
            gnssData.setLatitude_degree(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 19, (i * 14) + 20)));
            gnssData.setLatitude_minute(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 20, (i * 14) + 21)));
            gnssData.setLatitude_second(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 21, (i * 14) + 22)));
            gnssData.setLatitude_preci(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 22, (i * 14) + 23)));
            gnssData.setLatitude_direction(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 23, (i * 14) + 24)));
            gnssData.setGps_speed(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 24, (i * 14) + 26)));
            gnssData.setAltitude(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, (i * 14) + 26, (i * 14) + 28)));
            if (gnssData.getLongitude_direction() == 0) {
                gnssData.setLongitude_direction(1);
            } else if (gnssData.getLongitude_direction() == 1) {
                gnssData.setLongitude_direction(-1);
            }
            if (gnssData.getLatitude_direction() == 0) {
                gnssData.setLatitude_direction(1);
            } else if (gnssData.getLatitude_direction() == 1) {
                gnssData.setLatitude_direction(-1);
            }
            double longitude = (double) (((float) gnssData.getLongitude_direction()) * (((float) gnssData.getLongitude_degree()) + (((float) gnssData.getLongitude_minute()) / 60.0f) + ((((float) gnssData.getLongitude_second()) + (((float) gnssData.getLongitude_preci()) / 100.0f)) / 3600.0f)));
            double latitude = (double) (((float) gnssData.getLatitude_direction()) * (((float) gnssData.getLatitude_degree()) + (((float) gnssData.getLatitude_minute()) / 60.0f) + ((((float) gnssData.getLatitude_second()) + (((float) gnssData.getLatitude_preci()) / 100.0f)) / 3600.0f)));
            Log.d("testgps", "longitude: " + longitude + "  latitude: " + latitude);
            LongitudeAndLatitude loLa = new LongitudeAndLatitude();
            loLa.setLongitude(longitude);
            loLa.setLatitude(latitude);
            loLa.setGps_speed(gnssData.getGps_speed());
            loLa.setAltitude(gnssData.getAltitude());
            list2.add(loLa);
        }
        String message = new Gson().toJson((Object) list2);
        Log.d("testgps", "解析后数据: " + message);
        ble62DataParse.setCtrl(ctrl2);
        ble62DataParse.setSeq(seq2);
        ble62DataParse.setYear(year2);
        ble62DataParse.setMonth(month2);
        ble62DataParse.setDay(day2);
        ble62DataParse.setHour(hour2);
        ble62DataParse.setMin(min2);
        ble62DataParse.setFreq(freq2);
        ble62DataParse.setNum(num2);
        ble62DataParse.setDetail(message);
        return ble62DataParse;
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

    public int getFreq() {
        return this.freq;
    }

    public void setFreq(int freq2) {
        this.freq = freq2;
    }

    public int getNum() {
        return this.num;
    }

    public void setNum(int num2) {
        this.num = num2;
    }

    public List<LongitudeAndLatitude> getList() {
        return this.list;
    }

    public void setList(List<LongitudeAndLatitude> list2) {
        this.list = list2;
    }

    public String toString() {
        return "Ble62DataParse{ctrl=" + this.ctrl + ", seq=" + this.seq + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", min=" + this.min + ", freq=" + this.freq + ", num=" + this.num + ", list=" + this.list + '}';
    }
}
