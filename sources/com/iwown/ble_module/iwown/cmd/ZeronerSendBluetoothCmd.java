package com.iwown.ble_module.iwown.cmd;

import android.content.Context;
import android.util.SparseBooleanArray;
import java.util.ArrayList;
import java.util.Map;

public interface ZeronerSendBluetoothCmd {
    void clearAllSchedule(Context context);

    byte[] clearQuietMode();

    void closeAlarm(int i, Context context);

    void closeSchedule(Context context, int i, int i2, int i3, int i4, int i5);

    void getAlarmClock(Context context, int i);

    byte[] getBattery();

    byte[] getBle();

    byte[] getDataAccordingIndex(int i, int i2);

    byte[] getDeviceStateDate();

    byte[] getDoNotDisturb();

    byte[] getFirmwareInformation();

    byte[] getGnss();

    byte[] getHardwareFeatures();

    byte[] getHeartRateWarming();

    byte[] getQuietModeInfo();

    byte[] getSedentary();

    byte[] getSportGoles(int i);

    byte[] getSportTarget();

    byte[] getSportType();

    byte[] getSyncDataProgress();

    byte[] getTime();

    byte[] getUserProfile();

    byte[] readCustomDevSettings();

    byte[] readDataInfoStored();

    void readScheduleInfo(Context context);

    byte[] setBle();

    byte[] setContacts();

    byte[] setDialydata28(int i, boolean z, int i2);

    byte[] setDialydata29();

    byte[] setDialydata29(int i);

    byte[] setDoNotDisturb();

    byte[] setGestureSensitivity(int i);

    byte[] setGnss();

    byte[] setHardwareFeatures();

    byte[] setHeartBeat();

    byte[] setHeartBeat(int i);

    byte[] setHeartRateParams();

    byte[] setHeartRateParams(int i, int i2, int i3);

    byte[] setHeartRateWarming();

    byte[] setHeartRateWarming(boolean z, int i, int i2, int i3, int i4);

    byte[] setQuietMode(int i);

    byte[] setQuietMode(int i, int i2, int i3, int i4);

    byte[] setRestart();

    void setSchedule(Context context, int i, int i2, int i3, int i4, int i5, String str);

    byte[] setSedentary();

    byte[] setSedentary(int i, int i2, int i3, int i4, int i5, int i6);

    byte[] setShakeMode();

    byte[] setShakeMode(int i, int i2, int i3, ArrayList<Map<String, Integer>> arrayList);

    byte[] setSportGole(ArrayList<Byte> arrayList);

    byte[] setSportTarget();

    byte[] setSportType();

    byte[] setTime();

    byte[] setTime(int i, int i2, int i3, int i4, int i5, int i6, int i7);

    byte[] setUnbind();

    byte[] setUpgrade();

    byte[] setUserProfile();

    byte[] setUserProfile(int i, int i2, boolean z, int i3, int i4);

    byte[] setWeather();

    byte[] setWeather(int i, int i2, int i3, int i4);

    byte[] setWristBandBle(int i, int i2, int i3, int i4, int i5, int i6);

    byte[] setWristBandGestureAndLight(SparseBooleanArray sparseBooleanArray, int i, int i2);

    byte[] setWristBandGestureAndLight(SparseBooleanArray sparseBooleanArray, int i, int i2, int i3);

    byte[] setWristBandGestureAndLight(SparseBooleanArray sparseBooleanArray, int i, int i2, int i3, int i4, int i5);

    byte[] setWristBandSelfie(boolean z);

    byte[] showConnectionTipIcon();

    byte[] showExceptionTipIcon(int i);

    byte[] switchFindPhoneFunc(boolean z);

    byte[] syncDailyData();

    byte[] syncECGData();

    byte[] syncGpsData();

    byte[] syncHeartRateHourData(int i);

    byte[] syncHeartRateSegmentData(int i);

    byte[] syncMinSegmentData();

    byte[] syncSportSegmentData();

    void writeAlarmClock(Context context, int i, int i2, int i3, int i4, String str);

    void writeAlertFontLibrary(Context context, int i, String str);
}
