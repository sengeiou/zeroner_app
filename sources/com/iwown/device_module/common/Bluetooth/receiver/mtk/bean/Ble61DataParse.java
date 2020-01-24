package com.iwown.device_module.common.Bluetooth.receiver.mtk.bean;

import android.util.Log;
import com.google.common.base.Ascii;
import com.google.gson.Gson;
import com.iwown.ble_module.model.IndexTable;
import com.iwown.ble_module.model.IndexTable.TableItem;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.device_module.common.BaseActionUtils.BleAction;
import com.iwown.device_module.common.BaseActionUtils.UserAction;
import com.iwown.device_module.common.sql.TB_61_data;
import com.iwown.device_module.common.sql.TB_f1_index;
import com.iwown.device_module.common.sql.TB_sum_61_62_64;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.device_module.common.utils.PrefUtil;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.litepal.crud.DataSupport;

public class Ble61DataParse {
    public static DateUtil date = new DateUtil();
    private int automaticMin;
    private int avg_bpm;
    private int bpm;
    private int bpm_hr;
    private float calorie;
    private int ctrl;
    private int data_type;
    private int day;
    private int dbp;
    private float distance;
    private int hf;
    private int hour;
    private int level;
    private int lf;
    private int lf_hf;
    private int max_bpm;
    private int min;
    private int min_bpm;
    private int month;
    private int reserve;
    private int sbp;
    private int sdnn;
    private int seq;
    private int sport_type;
    private int state_type;
    private int step;
    private int year;

    public static void parseCtrl0(String result) {
        long uid = PrefUtil.getLong(ContextUtil.app, UserAction.User_Uid);
        String from = PrefUtil.getString(ContextUtil.app, BleAction.Bluetooth_Device_Name);
        IndexTable indexTable = (IndexTable) new Gson().fromJson(result, IndexTable.class);
        Log.e("licl", "获取到61 -:" + new Gson().toJson((Object) indexTable));
        List<TableItem> tableItems = indexTable.getmTableItems();
        DateUtil todayDate = new DateUtil();
        if (tableItems.size() > 0) {
            for (TableItem tableItem : tableItems) {
                if ((tableItem.getYear() == todayDate.getYear() || tableItem.getYear() + 1 == todayDate.getYear()) && tableItem.getStart_index() != tableItem.getEnd_index()) {
                    int mfend = tableItem.getEnd_index();
                    if (mfend > 4095) {
                        mfend -= 4096;
                    }
                    DateUtil dateUtil = new DateUtil(tableItem.getYear(), tableItem.getMonth(), tableItem.getDay());
                    long fTime = dateUtil.getUnixTimestamp();
                    TB_f1_index f1Index = (TB_f1_index) DataSupport.where("uid=? and start_seq=? and end_seq=? and data_from=?", uid + "", tableItem.getStart_index() + "", mfend + "", from).findFirst(TB_f1_index.class);
                    if (dateUtil.getSyyyyMMddDate().equals(todayDate.getSyyyyMMddDate())) {
                        DataSupport.deleteAll(TB_f1_index.class, "uid=? and data_from=? and date=?", uid + "", from, todayDate.getSyyyyMMddDate());
                        if (f1Index == null) {
                            f1Index = new TB_f1_index();
                        }
                        f1Index.setUid(uid);
                        f1Index.setDate(dateUtil.getSyyyyMMddDate());
                        f1Index.setData_from(from);
                        f1Index.setTime(fTime);
                        f1Index.setStart_seq(tableItem.getStart_index());
                        f1Index.setEnd_seq(mfend);
                        f1Index.setOk(0);
                        f1Index.setType("61");
                        f1Index.setHas_file(1);
                        f1Index.setHas_up(1);
                        f1Index.save();
                    }
                    int begins = tableItem.getStart_index();
                    int startSeq = tableItem.getStart_index();
                    if (f1Index == null) {
                        TB_f1_index f1Index2 = new TB_f1_index();
                        f1Index2.setUid(uid);
                        f1Index2.setDate(dateUtil.getSyyyyMMddDate());
                        f1Index2.setData_from(from);
                        f1Index2.setStart_seq(tableItem.getStart_index());
                        f1Index2.setEnd_seq(mfend);
                        f1Index2.setTime(fTime);
                        f1Index2.setOk(0);
                        f1Index2.setHas_up(1);
                        f1Index2.setHas_file(1);
                        f1Index2.setType("61");
                        f1Index2.save();
                    } else if (f1Index.getOk() != 1) {
                        TB_61_data data_61 = (TB_61_data) DataSupport.where("uid=? and year=? and month=? and day=? and data_from=? ", String.valueOf(uid), String.valueOf(tableItem.getYear()), String.valueOf(tableItem.getMonth()), String.valueOf(tableItem.getDay()), from).order("time desc").findFirst(TB_61_data.class);
                        if (data_61 != null) {
                            startSeq = data_61.getSeq();
                        }
                        if (tableItem.getEnd_index() > 4095 && startSeq < begins) {
                            startSeq += 4096;
                        }
                        if (tableItem.getEnd_index() < 4095 && startSeq < begins) {
                            KLog.e("testf1shuju11", "date与seq存在异常" + startSeq + " - " + begins);
                            startSeq = begins;
                        }
                        if (startSeq >= tableItem.getEnd_index() - 1) {
                        }
                    }
                    KLog.e("testf1shuju", "61有需要e同步的: " + tableItem.getYear() + HelpFormatter.DEFAULT_OPT_PREFIX + tableItem.getMonth() + HelpFormatter.DEFAULT_OPT_PREFIX + tableItem.getDay() + "  原始: " + begins + " -- " + tableItem.getEnd_index() + "  已同步到的: " + startSeq);
                    String sendS = ByteUtil.byteArrayToString(new byte[]{(byte) (startSeq & 255), (byte) (startSeq >>> 8), (byte) (tableItem.getEnd_index() & 255), (byte) (tableItem.getEnd_index() >>> 8)});
                    TB_sum_61_62_64 sum616264 = (TB_sum_61_62_64) DataSupport.where("date=? and send_cmd=?", dateUtil.getSyyyyMMddDate(), sendS).findFirst(TB_sum_61_62_64.class);
                    if (sum616264 == null) {
                        TB_sum_61_62_64 sum6162642 = new TB_sum_61_62_64();
                        sum6162642.setDate(dateUtil.getSyyyyMMddDate());
                        sum6162642.setDate_time(dateUtil.getUnixTimestamp());
                        sum6162642.setSend_cmd(sendS);
                        sum6162642.setSum(tableItem.getEnd_index() - startSeq);
                        sum6162642.setYear(dateUtil.getYear());
                        sum6162642.setMonth(dateUtil.getMonth());
                        sum6162642.setDay(dateUtil.getDay());
                        sum6162642.setType(ByteUtil.bytesToInt(new byte[]{97}));
                        sum6162642.setType_str("0x61");
                        sum6162642.save();
                    } else {
                        sum616264.setYear(dateUtil.getYear());
                        sum616264.setMonth(dateUtil.getMonth());
                        sum616264.setDay(dateUtil.getDay());
                        sum616264.setDate(dateUtil.getSyyyyMMddDate());
                        sum616264.setDate_time(dateUtil.getUnixTimestamp());
                        sum616264.setSend_cmd(sendS);
                        sum616264.setSum(tableItem.getEnd_index() - startSeq);
                        sum616264.setType(ByteUtil.bytesToInt(new byte[]{97}));
                        sum616264.setType_str("0x61");
                        sum616264.updateAll("date=? and send_cmd=?", dateUtil.getSyyyyMMddDate(), sendS);
                    }
                }
            }
        }
    }

    private static void saveUpDate() {
    }

    public static Ble61DataParse parse(byte[] datas) {
        Ble61DataParse ble61DataParse = new Ble61DataParse();
        ble61DataParse.setCtrl(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5)));
        ble61DataParse.setSeq(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 7)));
        ble61DataParse.setYear(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8)) + 2000);
        ble61DataParse.setMonth(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9)) + 1);
        ble61DataParse.setDay(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10)) + 1);
        ble61DataParse.setHour(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 10, 11)));
        ble61DataParse.setMin(ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 11, 12)));
        int data_type2 = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 12, 13));
        ble61DataParse.setData_type(data_type2);
        int index = 13;
        byte[] data = ByteUtil.byteToBitArray(data_type2);
        if (data[2] == 1) {
            byte[] pedoData = Arrays.copyOfRange(datas, 13, 23);
            ble61DataParse.setCalorie(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 0, 2))) * 0.1f);
            ble61DataParse.setStep(ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 2, 4)));
            ble61DataParse.setDistance(((float) ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 4, 6))) * 0.1f);
            ble61DataParse.setSport_type(ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 6, 8)));
            int automaticMin2 = ByteUtil.byteToInt((byte) (pedoData[8] >> 4));
            ble61DataParse.setState_type(ByteUtil.byteToInt((byte) (pedoData[8] & Ascii.SI)));
            ble61DataParse.setAutomaticMin(automaticMin2);
            ble61DataParse.setReserve(ByteUtil.bytesToInt(Arrays.copyOfRange(pedoData, 9, 10)));
            index = 13 + 10;
        }
        if (data[7] == 1) {
            byte[] hrData = Arrays.copyOfRange(datas, index, index + 7);
            ble61DataParse.setMin_bpm(ByteUtil.bytesToInt(Arrays.copyOfRange(hrData, 0, 2)));
            ble61DataParse.setMax_bpm(ByteUtil.bytesToInt(Arrays.copyOfRange(hrData, 2, 4)));
            ble61DataParse.setAvg_bpm(ByteUtil.bytesToInt(Arrays.copyOfRange(hrData, 4, 6)));
            ble61DataParse.setLevel(ByteUtil.bytesToInt(Arrays.copyOfRange(hrData, 6, 7)));
            index += 7;
        }
        if (data[6] == 1) {
            byte[] hrvData = Arrays.copyOfRange(datas, index, index + 14);
            ble61DataParse.setSdnn(ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 0, 2)));
            ble61DataParse.setLf(ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 2, 6)));
            ble61DataParse.setHf(ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 6, 10)));
            ble61DataParse.setLf_hf(ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 10, 12)));
            ble61DataParse.setBpm_hr(ByteUtil.bytesToInt(Arrays.copyOfRange(hrvData, 12, 14)));
            index += 14;
        }
        if (data[5] == 1) {
            byte[] bpData = Arrays.copyOfRange(datas, index, index + 6);
            ble61DataParse.setSbp(ByteUtil.bytesToInt(Arrays.copyOfRange(bpData, 0, 2)));
            ble61DataParse.setDbp(ByteUtil.bytesToInt(Arrays.copyOfRange(bpData, 2, 4)));
            ble61DataParse.setBpm(ByteUtil.bytesToInt(Arrays.copyOfRange(bpData, 4, 6)));
            int index2 = index + 6;
        }
        return ble61DataParse;
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

    public float getCalorie() {
        return this.calorie;
    }

    public void setCalorie(float calorie2) {
        this.calorie = calorie2;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int step2) {
        this.step = step2;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public int getState_type() {
        return this.state_type;
    }

    public void setState_type(int state_type2) {
        this.state_type = state_type2;
    }

    public int getReserve() {
        return this.reserve;
    }

    public void setReserve(int reserve2) {
        this.reserve = reserve2;
    }

    public int getMin_bpm() {
        return this.min_bpm;
    }

    public void setMin_bpm(int min_bpm2) {
        this.min_bpm = min_bpm2;
    }

    public int getMax_bpm() {
        return this.max_bpm;
    }

    public void setMax_bpm(int max_bpm2) {
        this.max_bpm = max_bpm2;
    }

    public int getAvg_bpm() {
        return this.avg_bpm;
    }

    public void setAvg_bpm(int avg_bpm2) {
        this.avg_bpm = avg_bpm2;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level2) {
        this.level = level2;
    }

    public int getSdnn() {
        return this.sdnn;
    }

    public void setSdnn(int sdnn2) {
        this.sdnn = sdnn2;
    }

    public int getLf() {
        return this.lf;
    }

    public void setLf(int lf2) {
        this.lf = lf2;
    }

    public int getHf() {
        return this.hf;
    }

    public void setHf(int hf2) {
        this.hf = hf2;
    }

    public int getLf_hf() {
        return this.lf_hf;
    }

    public void setLf_hf(int lf_hf2) {
        this.lf_hf = lf_hf2;
    }

    public int getBpm_hr() {
        return this.bpm_hr;
    }

    public void setBpm_hr(int bpm_hr2) {
        this.bpm_hr = bpm_hr2;
    }

    public int getSbp() {
        return this.sbp;
    }

    public void setSbp(int sbp2) {
        this.sbp = sbp2;
    }

    public int getDbp() {
        return this.dbp;
    }

    public void setDbp(int dbp2) {
        this.dbp = dbp2;
    }

    public int getBpm() {
        return this.bpm;
    }

    public void setBpm(int bpm2) {
        this.bpm = bpm2;
    }

    public int getAutomaticMin() {
        return this.automaticMin;
    }

    public void setAutomaticMin(int automaticMin2) {
        this.automaticMin = automaticMin2;
    }

    public String toString() {
        return "Ble61DataParse{seq=" + this.seq + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", min=" + this.min + ", data_type=" + this.data_type + ", sport_type=" + this.sport_type + ", calorie=" + this.calorie + ", step=" + this.step + ", distance=" + this.distance + ", state_type=" + this.state_type + ", reserve=" + this.reserve + ", min_bpm=" + this.min_bpm + ", max_bpm=" + this.max_bpm + ", avg_bpm=" + this.avg_bpm + ", level=" + this.level + ", sdnn=" + this.sdnn + ", lf=" + this.lf + ", hf=" + this.hf + ", lf_hf=" + this.lf_hf + ", bpm_hr=" + this.bpm_hr + ", sbp=" + this.sbp + ", dbp=" + this.dbp + ", bpm=" + this.bpm + ", automaticMin=" + this.automaticMin + '}';
    }
}
