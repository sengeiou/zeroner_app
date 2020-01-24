package com.iwown.device_module.common.sql;

import android.support.annotation.NonNull;
import com.iwown.lib_common.date.DateUtil;
import java.util.Locale;
import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

public class TB_61_data extends DataSupport implements Comparable<TB_61_data> {
    private int automatic;
    private int avg_bpm;
    private int bpm;
    private int bpm_hr;
    private float calorie;
    private String cmd;
    private int ctrl;
    private String data_from;
    private int data_type;
    private int day;
    private int dbp;
    private float distance;
    @Column(ignore = true)
    public float endClo = 0.0f;
    @Column(ignore = true)
    public float endDis = 0.0f;
    @Column(ignore = true)
    public int endMin = 0;
    @Column(ignore = true)
    public int endStep = 0;
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
    private long time;
    private long uid;
    private int year;

    public int getAutomatic() {
        return this.automatic;
    }

    public void setAutomatic(int automatic2) {
        this.automatic = automatic2;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time2) {
        this.time = time2;
    }

    public String getData_from() {
        return this.data_from;
    }

    public void setData_from(String data_from2) {
        this.data_from = data_from2;
    }

    public long getUid() {
        return this.uid;
    }

    public void setUid(long uid2) {
        this.uid = uid2;
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

    public String getCmd() {
        return this.cmd;
    }

    public void setCmd(String cmd2) {
        this.cmd = cmd2.toUpperCase(Locale.US);
    }

    public String toString() {
        return "TB_61_data{uid=" + this.uid + ", data_from='" + this.data_from + '\'' + ", ctrl=" + this.ctrl + ", seq=" + this.seq + ", year=" + this.year + ", month=" + this.month + ", day=" + this.day + ", hour=" + this.hour + ", min=" + this.min + ", data_type=" + this.data_type + ", sport_type=" + this.sport_type + ", calorie=" + this.calorie + ", step=" + this.step + ", distance=" + this.distance + ", state_type=" + this.state_type + ", reserve=" + this.reserve + ", automatic=" + this.automatic + ", min_bpm=" + this.min_bpm + ", max_bpm=" + this.max_bpm + ", avg_bpm=" + this.avg_bpm + ", level=" + this.level + ", sdnn=" + this.sdnn + ", lf=" + this.lf + ", hf=" + this.hf + ", lf_hf=" + this.lf_hf + ", bpm_hr=" + this.bpm_hr + ", sbp=" + this.sbp + ", dbp=" + this.dbp + ", bpm=" + this.bpm + ", time=" + this.time + ", cmd='" + this.cmd + '\'' + '}';
    }

    public int compareTo(@NonNull TB_61_data o) {
        return (int) (new DateUtil(getYear(), getMonth(), getDay(), getHour(), getMin()).getUnixTimestamp() - new DateUtil(o.getYear(), o.getMonth(), o.getDay(), o.getHour(), o.getMin()).getUnixTimestamp());
    }

    public boolean equals(Object obj) {
        boolean z;
        if (obj == null || !(obj instanceof TB_61_data)) {
            return false;
        }
        if (this.uid != ((TB_61_data) obj).getUid() || ((TB_61_data) obj).getData_from() == null || ((TB_61_data) obj).getCmd() == null || !((TB_61_data) obj).getData_from().equals(this.data_from) || !((TB_61_data) obj).getCmd().equals(this.cmd)) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }
}
