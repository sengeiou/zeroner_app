package com.iwown.device_module.common.sql;

import org.litepal.crud.DataSupport;

public class TB_PUSH_SOFT extends DataSupport {
    private String appName;
    private int day;
    private int flag1;
    private int flag2;
    private int flag3;
    private int flag4;
    private int flag5;
    private int flag6;
    private int flag7;
    private int flag8;
    private int hour;
    private String message;
    private int month;
    private int msgid;
    private String other;
    private String packageName;
    private long times;
    private String uid;
    private int year;

    public long getTimes() {
        return this.times;
    }

    public void setTimes(long times2) {
        this.times = times2;
    }

    public TB_PUSH_SOFT() {
    }

    public TB_PUSH_SOFT(String uid2, String packageName2, String appName2) {
        this.uid = uid2;
        this.packageName = packageName2;
        this.appName = appName2;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid2) {
        this.uid = uid2;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName2) {
        this.packageName = packageName2;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName2) {
        this.appName = appName2;
    }

    public int getFlag1() {
        return this.flag1;
    }

    public void setFlag1(int flag12) {
        this.flag1 = flag12;
    }

    public int getFlag2() {
        return this.flag2;
    }

    public void setFlag2(int flag22) {
        this.flag2 = flag22;
    }

    public int getFlag3() {
        return this.flag3;
    }

    public void setFlag3(int flag32) {
        this.flag3 = flag32;
    }

    public int getFlag4() {
        return this.flag4;
    }

    public void setFlag4(int flag42) {
        this.flag4 = flag42;
    }

    public int getFlag5() {
        return this.flag5;
    }

    public void setFlag5(int flag52) {
        this.flag5 = flag52;
    }

    public int getFlag6() {
        return this.flag6;
    }

    public void setFlag6(int flag62) {
        this.flag6 = flag62;
    }

    public int getFlag7() {
        return this.flag7;
    }

    public void setFlag7(int flag72) {
        this.flag7 = flag72;
    }

    public int getFlag8() {
        return this.flag8;
    }

    public void setFlag8(int flag82) {
        this.flag8 = flag82;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message2) {
        this.message = message2;
    }

    public String getOther() {
        return this.other;
    }

    public void setOther(String other2) {
        this.other = other2;
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

    public void setHour(int hour2) {
        this.hour = hour2;
    }

    public int getHour() {
        return this.hour;
    }

    public void setMsgid(int msgid2) {
        this.msgid = msgid2;
    }

    public int getMsgid() {
        return this.msgid;
    }
}
