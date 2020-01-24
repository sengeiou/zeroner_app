package com.iwown.device_module.common.sql;

import com.iwown.device_module.common.Bluetooth.receiver.zg.bean.ScheduleType;
import org.litepal.crud.DataSupport;

public class TB_Alarmstatue extends DataSupport implements ScheduleType {
    private int Ac_Conf;
    private int Ac_Hour;
    private int Ac_Idx;
    private int Ac_Minute;
    private String Ac_String;
    private String UID;
    private String date;
    private int id;
    private int openState;
    private String remind;
    private int zg_mode = 1;
    private int zg_number = 6;

    public int getZg_mode() {
        return this.zg_mode;
    }

    public void setZg_mode(int zg_mode2) {
        this.zg_mode = zg_mode2;
    }

    public int getZg_number() {
        return this.zg_number;
    }

    public void setZg_number(int zg_number2) {
        this.zg_number = zg_number2;
    }

    public int getAc_Idx() {
        return this.Ac_Idx;
    }

    public void setAc_Idx(int ac_Idx) {
        this.Ac_Idx = ac_Idx;
    }

    public int getAc_Conf() {
        return this.Ac_Conf;
    }

    public void setAc_Conf(int ac_Conf) {
        this.Ac_Conf = ac_Conf;
    }

    public int getAc_Hour() {
        return this.Ac_Hour;
    }

    public void setAc_Hour(int ac_Hour) {
        this.Ac_Hour = ac_Hour;
    }

    public int getAc_Minute() {
        return this.Ac_Minute;
    }

    public void setAc_Minute(int ac_Minute) {
        this.Ac_Minute = ac_Minute;
    }

    public String getAc_String() {
        return this.Ac_String;
    }

    public void setAc_String(String ac_String) {
        this.Ac_String = ac_String;
    }

    public int getOpenState() {
        return this.openState;
    }

    public void setOpenState(int openState2) {
        this.openState = openState2;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String UID2) {
        this.UID = UID2;
    }

    public String getRemind() {
        return this.remind;
    }

    public void setRemind(String remind2) {
        this.remind = remind2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date2) {
        this.date = date2;
    }

    public String toString() {
        return "TB_Alarmstatue{id=" + this.id + ", Ac_Idx=" + this.Ac_Idx + ", Ac_Conf=" + this.Ac_Conf + ", Ac_Hour=" + this.Ac_Hour + ", Ac_Minute=" + this.Ac_Minute + ", Ac_String='" + this.Ac_String + '\'' + ", openState=" + this.openState + ", date='" + this.date + '\'' + ", zg_mode=" + this.zg_mode + ", zg_number=" + this.zg_number + '}';
    }
}
