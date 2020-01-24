package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

import com.google.gson.Gson;
import com.iwown.ble_module.model.IndexTable;
import com.iwown.ble_module.model.IndexTable.TableItem;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.TB_64_index_table;
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

public class Ble64DataParse extends Result {
    public static DateUtil date = new DateUtil();
    private int day;
    private int hour;
    private List<Integer> list;
    private int min;
    private int month;
    private int second;
    private int seq;
    private int year;

    public static void parseCtrl0(String result) {
        String from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        List<TableItem> tableItems = ((IndexTable) new Gson().fromJson(result, IndexTable.class)).getmTableItems();
        DateUtil todayDate = new DateUtil();
        if (tableItems.size() > 0) {
            for (TableItem tableItem : tableItems) {
                if ((tableItem.getYear() == todayDate.getYear() || tableItem.getYear() + 1 == todayDate.getYear()) && tableItem.getStart_index() != tableItem.getEnd_index()) {
                    int startSeq = tableItem.getStart_index();
                    int endSeq = tableItem.getEnd_index();
                    int begins = startSeq;
                    DateUtil d = new DateUtil(tableItem.getYear(), tableItem.getMonth(), tableItem.getDay(), tableItem.getHour(), tableItem.getMin(), tableItem.getSecond());
                    TB_64_index_table index_table = (TB_64_index_table) DataSupport.where("uid=? and date=? and data_from=?", String.valueOf(uid), d.getY_M_D_H_M_S(), from).findFirst(TB_64_index_table.class);
                    if (index_table != null) {
                        startSeq = index_table.getSync_seq();
                    }
                    if (endSeq > 1279 && startSeq < begins) {
                        startSeq += 1280;
                    }
                    KLog.d("testf1shuju", "64需要的总数据: " + tableItem.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + tableItem.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + tableItem.getDay() + "  原始: " + begins + " -- " + endSeq + "  已同步到的: " + startSeq);
                    if (startSeq < endSeq - 1) {
                        KLog.e("testf1shuju", "64有需要e同步的？？: ");
                        byte[] b1 = {(byte) (startSeq & 255), (byte) (startSeq >>> 8), (byte) (endSeq & 255), (byte) (endSeq >>> 8)};
                        DateUtil dateUtil = new DateUtil(tableItem.getYear(), tableItem.getMonth(), tableItem.getDay());
                        String sendS = ByteUtil.byteArrayToString(b1);
                        TB_sum_61_62_64 sum616264 = (TB_sum_61_62_64) DataSupport.where("date=? and send_cmd=? and type_str=?", dateUtil.getSyyyyMMddDate(), sendS, "0x64").findFirst(TB_sum_61_62_64.class);
                        if (sum616264 == null) {
                            TB_sum_61_62_64 sum6162642 = new TB_sum_61_62_64();
                            sum6162642.setDate(dateUtil.getSyyyyMMddDate());
                            sum6162642.setDate_time(dateUtil.getUnixTimestamp());
                            sum6162642.setSend_cmd(sendS);
                            sum6162642.setSum(tableItem.getEnd_index() - startSeq);
                            sum6162642.setYear(tableItem.getYear());
                            sum6162642.setMonth(tableItem.getMonth());
                            sum6162642.setDay(tableItem.getDay());
                            TB_sum_61_62_64 tB_sum_61_62_64 = sum6162642;
                            tB_sum_61_62_64.setType(ByteUtil.bytesToInt(new byte[]{100}));
                            sum6162642.setType_str("0x64");
                            sum6162642.save();
                        } else {
                            sum616264.setYear(dateUtil.getYear());
                            sum616264.setMonth(dateUtil.getMonth());
                            sum616264.setDay(dateUtil.getDay());
                            sum616264.setDate(dateUtil.getSyyyyMMddDate());
                            sum616264.setDate_time(dateUtil.getUnixTimestamp());
                            sum616264.setSend_cmd(sendS);
                            sum616264.setSum(tableItem.getEnd_index() - startSeq);
                            TB_sum_61_62_64 tB_sum_61_62_642 = sum616264;
                            tB_sum_61_62_642.setType(ByteUtil.bytesToInt(new byte[]{100}));
                            sum616264.setType_str("0x64");
                            sum616264.updateAll("date=? and send_cmd=?", dateUtil.getSyyyyMMddDate(), sendS);
                        }
                        if (index_table == null) {
                            index_table = new TB_64_index_table();
                        }
                        int mfend = tableItem.getEnd_index();
                        if (mfend > 1279) {
                            mfend -= 1280;
                        }
                        index_table.setUid(uid);
                        index_table.setData_from(from);
                        index_table.setData_ymd(d.getSyyyyMMddDate());
                        index_table.setSeq_start(tableItem.getStart_index());
                        index_table.setSeq_end(mfend);
                        index_table.setSync_seq(startSeq);
                        index_table.setDate(d.getY_M_D_H_M_S());
                        index_table.setUnixTime(d.getUnixTimestamp());
                        index_table.saveOrUpdate("uid=? and data_from =? and date=?", String.valueOf(uid), from, d.getY_M_D_H_M_S());
                    }
                }
            }
        }
    }

    public static Ble64DataParse parse(byte[] data) {
        Ble64DataParse cmd64 = new Ble64DataParse();
        List<Integer> list2 = new ArrayList<>();
        int seq2 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 5, 7));
        int year2 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 7, 8)) + 2000;
        int month2 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 8, 9)) + 1;
        int day2 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 9, 10)) + 1;
        int hour2 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 10, 11)) + 1;
        int min2 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 11, 12));
        int second2 = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 12, 13));
        for (int i = 0; i < 60; i++) {
            if (data.length >= (i * 2) + 15) {
                list2.add(Integer.valueOf(ByteUtil.bytesToInt(Arrays.copyOfRange(data, (i * 2) + 13, (i * 2) + 15))));
            }
        }
        cmd64.setSeq(seq2);
        cmd64.setYear(year2);
        cmd64.setMonth(month2);
        cmd64.setDay(day2);
        cmd64.setHour(hour2);
        cmd64.setMin(min2);
        cmd64.setSecond(second2);
        cmd64.setList(list2);
        return cmd64;
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

    public int getSecond() {
        return this.second;
    }

    public void setSecond(int second2) {
        this.second = second2;
    }

    public List<Integer> getList() {
        return this.list;
    }

    public void setList(List<Integer> list2) {
        this.list = list2;
    }
}
