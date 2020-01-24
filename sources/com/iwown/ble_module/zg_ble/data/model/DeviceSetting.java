package com.iwown.ble_module.zg_ble.data.model;

import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import java.util.Arrays;

public class DeviceSetting {
    private static DeviceSetting instance;
    private int batteryVolume;
    private int calorieOnceday;
    private int caloriesReachRing;
    private int comingCallEnable;
    private int comingCallEndHour;
    private int comingCallRing;
    private int comingCallStartHour;
    private byte[] d1 = new byte[20];
    private byte[] d2 = new byte[20];
    private byte[] d3 = new byte[12];
    private int distanceOnceday;
    private int distanceReachRing;
    private int heartAlarmEnable;
    private int heartRing;
    private int highHeartAlarm;
    private int lowHeartAlarm;
    private int messageEnable;
    private int messageEndHour;
    private int messageRing;
    private int messageStartHour;
    private int quietHeartEnable;
    private int quietHeartEndHour;
    private int quietHeartStartHour;
    private int rollEnable;
    private int rollEndHour;
    private int rollStartHour;
    private int runStride;
    private int sitLongAlarmEnable;
    private int sitlongEndHour;
    private int sitlongRing;
    private int sitlongStartHour;
    private int stepsOnceday;
    private int stepsReachRing;
    private int unitSet;
    private int walkStride;
    private int watchSelect;
    private int weight;

    public static DeviceSetting getInstance() {
        if (instance == null) {
            instance = new DeviceSetting();
        }
        return instance;
    }

    public void init_01_data(byte[] data) {
        this.d1 = data;
        this.unitSet = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
        this.weight = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 5, 6));
        this.walkStride = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 6, 7));
        this.runStride = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 7, 8));
        this.stepsOnceday = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 8, 10));
        this.calorieOnceday = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 10, 12));
        this.distanceOnceday = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 12, 13));
        this.stepsReachRing = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 13, 14));
        this.caloriesReachRing = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 14, 15));
        this.distanceReachRing = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 15, 16));
        this.comingCallEnable = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 16, 17));
        this.comingCallStartHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 17, 18));
        this.comingCallEndHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 18, 19)) + 1;
        this.comingCallRing = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 19, 20));
    }

    public void init_82_data(byte[] data) {
        this.d2 = data;
        this.messageEnable = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
        this.messageStartHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 5, 6));
        this.messageEndHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 6, 7)) + 1;
        this.messageRing = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 7, 8));
        this.quietHeartEnable = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 8, 9));
        this.quietHeartStartHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 9, 10));
        this.quietHeartEndHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 10, 11)) + 1;
        if (this.quietHeartEndHour == 24) {
            this.quietHeartEndHour = 0;
        }
        this.heartAlarmEnable = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 11, 12));
        this.highHeartAlarm = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 12, 13));
        this.lowHeartAlarm = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 13, 14));
        this.heartRing = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 14, 15));
        this.sitLongAlarmEnable = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 15, 16));
        this.sitlongStartHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 16, 17));
        this.sitlongEndHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 17, 18)) + 1;
        if (this.sitlongEndHour == 24) {
            this.sitlongEndHour = 0;
        }
        this.sitlongRing = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 18, 19));
        this.rollEnable = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 19, 20));
    }

    public void init_83_data(byte[] data) {
        if (data.length == 20) {
            for (int i = 0; i < this.d3.length; i++) {
                this.d3[i] = data[i];
            }
            this.d3[2] = -125;
            this.d3[3] = 8;
        } else {
            this.d3 = data;
        }
        this.rollStartHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
        this.rollEndHour = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 5, 6)) + 1;
        this.watchSelect = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 6, 7));
        this.batteryVolume = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 7, 8));
        if (this.rollEndHour == 24) {
            this.rollEndHour = 0;
        }
    }

    public static void setInstance(DeviceSetting instance2) {
        instance = instance2;
    }

    public int getUnitSet() {
        return this.unitSet;
    }

    public void setUnitSet(int unitSet2) {
        this.unitSet = unitSet2;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight2) {
        this.weight = weight2;
    }

    public int getWalkStride() {
        return this.walkStride;
    }

    public void setWalkStride(int walkStride2) {
        this.walkStride = walkStride2;
    }

    public int getRunStride() {
        return this.runStride;
    }

    public void setRunStride(int runStride2) {
        this.runStride = runStride2;
    }

    public int getStepsOnceday() {
        return this.stepsOnceday;
    }

    public void setStepsOnceday(int stepsOnceday2) {
        this.stepsOnceday = stepsOnceday2;
    }

    public int getCalorieOnceday() {
        return this.calorieOnceday;
    }

    public void setCalorieOnceday(int calorieOnceday2) {
        this.calorieOnceday = calorieOnceday2;
    }

    public int getDistanceOnceday() {
        return this.distanceOnceday;
    }

    public void setDistanceOnceday(int distanceOnceday2) {
        this.distanceOnceday = distanceOnceday2;
    }

    public int getComingCallEnable() {
        return this.comingCallEnable;
    }

    public void setComingCallEnable(int comingCallEnable2) {
        this.comingCallEnable = comingCallEnable2;
    }

    public byte[] getD3() {
        return this.d3;
    }

    public void setD3(byte[] d32) {
        this.d3 = d32;
    }

    public int getComingCallStartHour() {
        return this.comingCallStartHour;
    }

    public void setComingCallStartHour(int comingCallStartHour2) {
        this.comingCallStartHour = comingCallStartHour2;
    }

    public int getComingCallEndHour() {
        return this.comingCallEndHour;
    }

    public void setComingCallEndHour(int comingCallEndHour2) {
        this.comingCallEndHour = comingCallEndHour2;
    }

    public int getComingCallRing() {
        return this.comingCallRing;
    }

    public void setComingCallRing(int comingCallRing2) {
        this.comingCallRing = comingCallRing2;
    }

    public int getMessageStartHour() {
        return this.messageStartHour;
    }

    public void setMessageStartHour(int messageStartHour2) {
        this.messageStartHour = messageStartHour2;
    }

    public int getMessageEndHour() {
        return this.messageEndHour;
    }

    public void setMessageEndHour(int messageEndHour2) {
        this.messageEndHour = messageEndHour2;
    }

    public int getMessageRing() {
        return this.messageRing;
    }

    public void setMessageRing(int messageRing2) {
        this.messageRing = messageRing2;
    }

    public int getQuietHeartStartHour() {
        return this.quietHeartStartHour;
    }

    public void setQuietHeartStartHour(int quietHeartStartHour2) {
        this.quietHeartStartHour = quietHeartStartHour2;
    }

    public int getQuietHeartEndHour() {
        return this.quietHeartEndHour;
    }

    public void setQuietHeartEndHour(int quietHeartEndHour2) {
        this.quietHeartEndHour = quietHeartEndHour2;
    }

    public int getHighHeartAlarm() {
        return this.highHeartAlarm;
    }

    public void setHighHeartAlarm(int highHeartAlarm2) {
        this.highHeartAlarm = highHeartAlarm2;
    }

    public int getLowHeartAlarm() {
        return this.lowHeartAlarm;
    }

    public void setLowHeartAlarm(int lowHeartAlarm2) {
        this.lowHeartAlarm = lowHeartAlarm2;
    }

    public int getHeartRing() {
        return this.heartRing;
    }

    public void setHeartRing(int heartRing2) {
        this.heartRing = heartRing2;
    }

    public int getSitlongStartHour() {
        return this.sitlongStartHour;
    }

    public void setSitlongStartHour(int sitlongStartHour2) {
        this.sitlongStartHour = sitlongStartHour2;
    }

    public int getSitlongEndHour() {
        return this.sitlongEndHour;
    }

    public void setSitlongEndHour(int sitlongEndHour2) {
        this.sitlongEndHour = sitlongEndHour2;
    }

    public int getSitlongRing() {
        return this.sitlongRing;
    }

    public void setSitlongRing(int sitlongRing2) {
        this.sitlongRing = sitlongRing2;
    }

    public int getRollStartHour() {
        return this.rollStartHour;
    }

    public void setRollStartHour(int rollStartHour2) {
        this.rollStartHour = rollStartHour2;
    }

    public int getRollEndHour() {
        return this.rollEndHour;
    }

    public void setRollEndHour(int rollEndHour2) {
        this.rollEndHour = rollEndHour2;
    }

    public int getBatteryVolume() {
        return this.batteryVolume;
    }

    public void setBatteryVolume(int batteryVolume2) {
        this.batteryVolume = batteryVolume2;
    }

    public int getWatchSelect() {
        return this.watchSelect;
    }

    public void setWatchSelect(int watchSelect2) {
        this.watchSelect = watchSelect2;
    }

    public byte[] getD1() {
        return this.d1;
    }

    public void setD1(byte[] d12) {
        this.d1 = d12;
    }

    public byte[] getD2() {
        return this.d2;
    }

    public void setD2(byte[] d22) {
        this.d2 = d22;
    }

    public int getStepsReachRing() {
        return this.stepsReachRing;
    }

    public void setStepsReachRing(int stepsReachRing2) {
        this.stepsReachRing = stepsReachRing2;
    }

    public int getCaloriesReachRing() {
        return this.caloriesReachRing;
    }

    public void setCaloriesReachRing(int caloriesReachRing2) {
        this.caloriesReachRing = caloriesReachRing2;
    }

    public int getDistanceReachRing() {
        return this.distanceReachRing;
    }

    public void setDistanceReachRing(int distanceReachRing2) {
        this.distanceReachRing = distanceReachRing2;
    }

    public int getMessageEnable() {
        return this.messageEnable;
    }

    public void setMessageEnable(int messageEnable2) {
        this.messageEnable = messageEnable2;
    }

    public int getQuietHeartEnable() {
        return this.quietHeartEnable;
    }

    public void setQuietHeartEnable(int quietHeartEnable2) {
        this.quietHeartEnable = quietHeartEnable2;
    }

    public int getHeartAlarmEnable() {
        return this.heartAlarmEnable;
    }

    public void setHeartAlarmEnable(int heartAlarmEnable2) {
        this.heartAlarmEnable = heartAlarmEnable2;
    }

    public int getSitLongAlarmEnable() {
        return this.sitLongAlarmEnable;
    }

    public void setSitLongAlarmEnable(int sitLongAlarmEnable2) {
        this.sitLongAlarmEnable = sitLongAlarmEnable2;
    }

    public int getRollEnable() {
        return this.rollEnable;
    }

    public void setRollEnable(int rollEnable2) {
        this.rollEnable = rollEnable2;
    }

    public String toString() {
        return "DeviceSetting{unitSet=" + this.unitSet + ", weight=" + this.weight + ", walkStride=" + this.walkStride + ", runStride=" + this.runStride + ", stepsOnceday=" + this.stepsOnceday + ", calorieOnceday=" + this.calorieOnceday + ", distanceOnceday=" + this.distanceOnceday + ", stepsReachRing=" + this.stepsReachRing + ", caloriesReachRing=" + this.caloriesReachRing + ", distanceReachRing=" + this.distanceReachRing + ", comingCallEnable=" + this.comingCallEnable + ", comingCallStartHour=" + this.comingCallStartHour + ", comingCallEndHour=" + this.comingCallEndHour + ", comingCallRing=" + this.comingCallRing + ", messageEnable=" + this.messageEnable + ", messageStartHour=" + this.messageStartHour + ", messageEndHour=" + this.messageEndHour + ", messageRing=" + this.messageRing + ", quietHeartEnable=" + this.quietHeartEnable + ", quietHeartStartHour=" + this.quietHeartStartHour + ", quietHeartEndHour=" + this.quietHeartEndHour + ", heartAlarmEnable=" + this.heartAlarmEnable + ", highHeartAlarm=" + this.highHeartAlarm + ", lowHeartAlarm=" + this.lowHeartAlarm + ", heartRing=" + this.heartRing + ", sitLongAlarmEnable=" + this.sitLongAlarmEnable + ", sitlongStartHour=" + this.sitlongStartHour + ", sitlongEndHour=" + this.sitlongEndHour + ", sitlongRing=" + this.sitlongRing + ", rollEnable=" + this.rollEnable + ", rollStartHour=" + this.rollStartHour + ", rollEndHour=" + this.rollEndHour + ", watchSelect=" + this.watchSelect + ", batteryVolume=" + this.batteryVolume + ", d1=" + Arrays.toString(this.d1) + ", d2=" + Arrays.toString(this.d2) + ", d3=" + Arrays.toString(this.d3) + '}';
    }
}
