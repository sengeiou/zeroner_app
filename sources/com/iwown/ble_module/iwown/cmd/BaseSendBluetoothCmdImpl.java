package com.iwown.ble_module.iwown.cmd;

import android.content.Context;
import android.util.SparseBooleanArray;
import java.util.ArrayList;
import java.util.Map;

public class BaseSendBluetoothCmdImpl implements ZeronerSendBluetoothCmd {
    protected BaseSendBluetoothCmdImpl() {
    }

    public void writeAlarmClock(Context context, int id, int weekRepeat, int hour, int minute, String text) {
    }

    public void getAlarmClock(Context context, int id) {
    }

    public void setSchedule(Context context, int year, int month, int day, int hour, int minute, String text) {
    }

    public void closeSchedule(Context context, int year, int month, int day, int hour, int minute) {
    }

    public void clearAllSchedule(Context context) {
    }

    public void readScheduleInfo(Context context) {
    }

    public void closeAlarm(int id, Context context) {
    }

    public byte[] getFirmwareInformation() {
        return new byte[0];
    }

    public byte[] getBattery() {
        return new byte[0];
    }

    public byte[] setRestart() {
        return new byte[0];
    }

    public byte[] setUpgrade() {
        return new byte[0];
    }

    public byte[] setUnbind() {
        return new byte[0];
    }

    public byte[] setDoNotDisturb() {
        return new byte[0];
    }

    public byte[] getDoNotDisturb() {
        return new byte[0];
    }

    public byte[] setWeather() {
        return new byte[0];
    }

    public byte[] getSyncDataProgress() {
        return new byte[0];
    }

    public byte[] setHeartBeat() {
        return new byte[0];
    }

    public byte[] setSportType() {
        return new byte[0];
    }

    public byte[] getSportType() {
        return new byte[0];
    }

    public byte[] setTime() {
        return new byte[0];
    }

    public byte[] setTime(int year, int month, int day, int hour, int minute, int second, int week) {
        return new byte[0];
    }

    public byte[] getTime() {
        return new byte[0];
    }

    public byte[] setBle() {
        return new byte[0];
    }

    public byte[] getBle() {
        return new byte[0];
    }

    public byte[] setWristBandBle(int Peer_HandShake, int Conn_Params_Flag, int Conn_Interval_Min, int Conn_Interval_Max, int Conn_Sla_latency, int Conn_Sup_Timeout) {
        return new byte[0];
    }

    public byte[] setDialydata29() {
        return new byte[0];
    }

    public byte[] setHeartBeat(int value) {
        return new byte[0];
    }

    public byte[] setDialydata28(int value, boolean flag, int index) {
        return new byte[0];
    }

    public byte[] setDialydata29(int value) {
        return new byte[0];
    }

    public byte[] setSedentary() {
        return new byte[0];
    }

    public byte[] getSedentary() {
        return new byte[0];
    }

    public byte[] setHardwareFeatures() {
        return new byte[0];
    }

    public byte[] getHardwareFeatures() {
        return new byte[0];
    }

    public byte[] setSportTarget() {
        return new byte[0];
    }

    public byte[] getSportTarget() {
        return new byte[0];
    }

    public byte[] setContacts() {
        return new byte[0];
    }

    public byte[] setUserProfile() {
        return new byte[0];
    }

    public byte[] getUserProfile() {
        return new byte[0];
    }

    public byte[] setHeartRateWarming() {
        return new byte[0];
    }

    public byte[] getHeartRateWarming() {
        return new byte[0];
    }

    public byte[] setGnss() {
        return new byte[0];
    }

    public byte[] getGnss() {
        return new byte[0];
    }

    public byte[] syncSportSegmentData() {
        return new byte[0];
    }

    public byte[] syncDailyData() {
        return new byte[0];
    }

    public byte[] setShakeMode() {
        return new byte[0];
    }

    public byte[] setHeartRateParams(int strength, int min, int type) {
        return new byte[0];
    }

    public byte[] setHeartRateParams() {
        return new byte[0];
    }

    public byte[] syncHeartRateSegmentData(int type) {
        return new byte[0];
    }

    public byte[] syncHeartRateHourData(int type) {
        return new byte[0];
    }

    public byte[] syncECGData() {
        return new byte[0];
    }

    public byte[] syncMinSegmentData() {
        return new byte[0];
    }

    public byte[] syncGpsData() {
        return new byte[0];
    }

    public byte[] setQuietMode(int startHour, int startMin, int endHour, int endMin) {
        return new byte[0];
    }

    public byte[] setQuietMode(int type) {
        return new byte[0];
    }

    public byte[] clearQuietMode() {
        return new byte[0];
    }

    public byte[] getQuietModeInfo() {
        return new byte[0];
    }

    public byte[] setWeather(int unitType, int temperature, int weather, int pm) {
        return new byte[0];
    }

    public byte[] setSportGole(ArrayList<Byte> arrayList) {
        return new byte[0];
    }

    public byte[] getSportGoles(int week) {
        return new byte[0];
    }

    public byte[] setSedentary(int id, int week, int startHour, int endHour, int alertDuration, int workCount) {
        return new byte[0];
    }

    public byte[] setWristBandGestureAndLight(SparseBooleanArray array, int backLightStartTime, int backLightEndTime) {
        return new byte[0];
    }

    public byte[] setWristBandGestureAndLight(SparseBooleanArray array, int backLightStartTime, int backLightEndTime, int languageType) {
        return new byte[0];
    }

    public byte[] setWristBandGestureAndLight(SparseBooleanArray array, int backLightStartTime, int backLightEndTime, int languageType, int wristLightFuncStartTime, int wristLightFuncEndTime) {
        return new byte[0];
    }

    public byte[] getDeviceStateDate() {
        return new byte[0];
    }

    public byte[] setUserProfile(int height, int weight, boolean gender, int age, int steps) {
        return new byte[0];
    }

    public byte[] setHeartRateWarming(boolean isOn, int high, int low, int timeOut, int interval) {
        return new byte[0];
    }

    public byte[] setShakeMode(int type, int shakeModeIndex, int num, ArrayList<Map<String, Integer>> arrayList) {
        return new byte[0];
    }

    public byte[] setWristBandSelfie(boolean flag) {
        return new byte[0];
    }

    public byte[] readDataInfoStored() {
        return new byte[0];
    }

    public byte[] showConnectionTipIcon() {
        return new byte[0];
    }

    public byte[] showExceptionTipIcon(int delayShowTimeMinute) {
        return new byte[0];
    }

    public byte[] getDataAccordingIndex(int dataType, int dataIndex) {
        return new byte[0];
    }

    public byte[] switchFindPhoneFunc(boolean toOpen) {
        return new byte[0];
    }

    public byte[] setGestureSensitivity(int level) {
        return new byte[0];
    }

    public byte[] readCustomDevSettings() {
        return new byte[0];
    }

    public void writeAlertFontLibrary(Context context, int type, String displayName) {
    }
}
