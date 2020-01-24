package com.iwown.ble_module.iwown.cmd;

import android.content.Context;
import android.util.SparseBooleanArray;
import com.google.common.base.Ascii;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.task.DataBean;
import com.iwown.ble_module.iwown.task.ITask;
import com.iwown.ble_module.iwown.task.ZeronerBackgroundThreadManager;
import com.iwown.ble_module.iwown.task.ZeronerBleWriteDataTask;
import com.iwown.ble_module.iwown.utils.HexUtil;
import com.iwown.ble_module.utils.Util;
import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;
import com.tencent.bugly.beta.tinker.TinkerReport;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ZeronerSendBluetoothCmdImpl extends BaseSendBluetoothCmdImpl {
    private static ZeronerSendBluetoothCmdImpl instance;

    private ZeronerSendBluetoothCmdImpl() {
    }

    public static ZeronerSendBluetoothCmdImpl getInstance() {
        if (instance == null) {
            instance = new ZeronerSendBluetoothCmdImpl();
        }
        return instance;
    }

    public byte[] getFirmwareInformation() {
        return writeWristBandData(form_Header(0, 0), null);
    }

    public byte[] setTime() {
        DateUtil date = new DateUtil();
        return writeWristBandDataByte(form_Header(1, 0), new byte[]{(byte) (date.getYear() - 2000), (byte) (date.getMonth() - 1), (byte) (date.getDay() - 1), (byte) date.getHour(), (byte) date.getMinute(), (byte) date.getSecond()});
    }

    public static byte form_Header(int grp, int cmd) {
        return (byte) (((((byte) grp) & Ascii.SI) << 4) | (((byte) cmd) & Ascii.SI));
    }

    public static byte[] writeWristBandData(byte header, ArrayList<byte[]> datas) {
        byte[] commonData = new byte[4];
        commonData[0] = Framer.ENTER_FRAME_PREFIX;
        commonData[1] = -1;
        commonData[2] = header;
        if (datas != null) {
            commonData[3] = (byte) ((byte[]) datas.get(0)).length;
            byte[] data = new byte[((byte[]) datas.get(0)).length];
            for (int i = 0; i < ((byte[]) datas.get(0)).length; i++) {
                data[i] = ((byte[]) datas.get(0))[i];
            }
            return Util.concat(commonData, data);
        }
        commonData[3] = 0;
        return commonData;
    }

    public static byte[] writeWristBandDataByte(byte header, byte[] datas) {
        byte[] commonData = new byte[4];
        commonData[0] = Framer.ENTER_FRAME_PREFIX;
        commonData[1] = -1;
        commonData[2] = header;
        if (datas != null) {
            commonData[3] = (byte) datas.length;
            byte[] data = new byte[datas.length];
            for (int i = 0; i < datas.length; i++) {
                data[i] = datas[i];
            }
            return Util.concat(commonData, data);
        }
        commonData[3] = 0;
        return commonData;
    }

    public byte[] writeWristBandDataByte(byte header, ArrayList<Byte> datas) {
        byte[] commonData = new byte[4];
        commonData[0] = Framer.ENTER_FRAME_PREFIX;
        commonData[1] = -1;
        commonData[2] = header;
        if (datas != null) {
            commonData[3] = (byte) datas.size();
            byte[] data = new byte[datas.size()];
            for (int i = 0; i < datas.size(); i++) {
                data[i] = ((Byte) datas.get(i)).byteValue();
            }
            return Util.concat(commonData, data);
        }
        commonData[3] = 0;
        return commonData;
    }

    public byte[] syncHeartRateHourData(int type) {
        return writeWristBandDataByte(form_Header(5, 3), ZeronerAlarmClockScheduleHandler.syncHeartRateSegmentData(type));
    }

    public byte[] syncHeartRateSegmentData(int type) {
        return writeWristBandDataByte(form_Header(5, 1), ZeronerAlarmClockScheduleHandler.syncHeartRateHourData(type));
    }

    public void writeAlarmClock(Context context, int id, int weekRepeat, int hour, int minute, String text) {
        byte[] data = writeWristBandDataByte(form_Header(1, 4), ZeronerAlarmClockScheduleHandler.writeAlarm(id, weekRepeat, hour, minute, text));
        List<byte[]> writes = new ArrayList<>();
        for (int i = 0; i < data.length; i += 20) {
            if (i + 20 > data.length) {
                writes.add(Arrays.copyOfRange(data, i, data.length));
            } else {
                writes.add(Arrays.copyOfRange(data, i, i + 20));
            }
        }
        DataBean bean = new DataBean();
        bean.setData(writes);
        ZeronerBackgroundThreadManager.getInstance().addWriteData(context, bean);
    }

    public void closeAlarm(int id, Context context) {
        super.closeAlarm(id, context);
        byte[] data = {(byte) id, 0, 0, 0, 0, 0};
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(data);
        byte[] datadd = writeWristBandData(form_Header(1, 4), datas);
        List<byte[]> writes = new ArrayList<>();
        writes.add(datadd);
        DataBean bean = new DataBean();
        bean.setData(writes);
        ZeronerBackgroundThreadManager.getInstance().addWriteData(context, bean);
        KLog.d("closeAlarm");
    }

    public void getAlarmClock(Context context, int id) {
        byte[] bytes1 = writeWristBandDataByte(form_Header(1, 5), ZeronerAlarmClockScheduleHandler.readAlarm(id));
        List<byte[]> writes = new ArrayList<>();
        writes.add(bytes1);
        DataBean bean = new DataBean();
        bean.setData(writes);
        ZeronerBackgroundThreadManager.getInstance().addWriteData(context, bean);
    }

    public void setSchedule(Context context, int year, int month, int day, int hour, int minute, String text) {
        byte[] data = writeWristBandDataByte(form_Header(1, 13), ZeronerAlarmClockScheduleHandler.setSchedule(year, month, day, hour, minute, text));
        List<byte[]> writes = new ArrayList<>();
        for (int i = 0; i < data.length; i += 20) {
            if (i + 20 > data.length) {
                writes.add(Arrays.copyOfRange(data, i, data.length));
            } else {
                writes.add(Arrays.copyOfRange(data, i, i + 20));
            }
        }
        DataBean bean = new DataBean();
        bean.setData(writes);
        ZeronerBackgroundThreadManager.getInstance().addWriteData(context, bean);
    }

    public void closeSchedule(Context context, int year, int month, int day, int hour, int minute) {
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{2, HexUtil.int2byte(year - 2000), HexUtil.int2byte(month - 1), HexUtil.int2byte(day - 1), HexUtil.int2byte(hour), HexUtil.int2byte(minute)});
        byte[] data = writeWristBandData(form_Header(1, 13), datas);
        KLog.d("删除 开始时间测试");
        KLog.d("删除 addTask");
        List<byte[]> writes = new ArrayList<>();
        writes.add(data);
        DataBean bean = new DataBean();
        bean.setData(writes);
        ZeronerBackgroundThreadManager.getInstance().addWriteData(context, bean);
    }

    public void clearAllSchedule(Context context) {
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{1});
        byte[] data = writeWristBandData(form_Header(1, 13), datas);
        List<byte[]> writes = new ArrayList<>();
        writes.add(data);
        DataBean bean = new DataBean();
        bean.setData(writes);
        ZeronerBackgroundThreadManager.getInstance().addWriteData(context, bean);
    }

    public void readScheduleInfo(Context context) {
        byte[] data = writeWristBandDataByte(form_Header(1, 14), ZeronerAlarmClockScheduleHandler.readScheduleInfo());
        List<byte[]> writes = new ArrayList<>();
        writes.add(data);
        DataBean bean = new DataBean();
        bean.setData(writes);
        ZeronerBackgroundThreadManager.getInstance().addWriteData(context, bean);
    }

    public byte[] getTime() {
        return writeWristBandData(form_Header(1, 1), null);
    }

    public byte[] setTime(int year, int month, int day, int hour, int minute, int second, int week) {
        byte[] xval = {(byte) (year - 2000), (byte) (month - 1), (byte) (day - 1), (byte) hour, (byte) minute, (byte) second};
        byte header = form_Header(1, 0);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] getBattery() {
        return writeWristBandData(form_Header(0, 1), null);
    }

    public byte[] setUpgrade() {
        return writeWristBandData(form_Header(0, 3), null);
    }

    public byte[] setUnbind() {
        return writeWristBandData(form_Header(0, 5), null);
    }

    public byte[] setQuietMode(int startHour, int startMin, int endHour, int endMin) {
        if (startHour < 0 || startMin < 0 || endHour < 0 || endMin < 0 || startHour > 23 || endHour > 23 || startMin > 59 || endMin > 59) {
            throw new IllegalArgumentException("argument is out of range");
        }
        byte header = form_Header(0, 6);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{0, 1, HexUtil.int2byte(startHour), HexUtil.int2byte(startMin), HexUtil.int2byte(endHour), HexUtil.int2byte(endMin)});
        return writeWristBandData(header, datas);
    }

    public byte[] setQuietMode(int type) {
        byte header = form_Header(0, 6);
        ArrayList<Byte> datas = new ArrayList<>();
        switch (type) {
            case 0:
                datas.add(Byte.valueOf(0));
                datas.add(Byte.valueOf(2));
                datas.add(Byte.valueOf(0));
                datas.add(Byte.valueOf(0));
                datas.add(Byte.valueOf(0));
                datas.add(Byte.valueOf(0));
                break;
            case 1:
                datas.add(Byte.valueOf(0));
                datas.add(Byte.valueOf(3));
                datas.add(Byte.valueOf(0));
                datas.add(Byte.valueOf(0));
                datas.add(Byte.valueOf(0));
                datas.add(Byte.valueOf(0));
                break;
        }
        return writeWristBandDataByte(header, datas);
    }

    public byte[] clearQuietMode() {
        byte header = form_Header(0, 6);
        ArrayList<Byte> datas = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            datas.add(Byte.valueOf(0));
        }
        return writeWristBandDataByte(header, datas);
    }

    public byte[] getQuietModeInfo() {
        return new byte[]{Framer.ENTER_FRAME_PREFIX, -1, form_Header(0, 6), 1, 1, 1};
    }

    public byte[] setWeather(int unitType, int temperature, int weather, int pm) {
        byte header = form_Header(0, 7);
        byte[] data = new byte[6];
        if (temperature < 0) {
            int temperature2 = temperature | 32768;
            data[0] = (byte) (temperature2 & 255);
            data[1] = (byte) ((temperature2 >> 8) & 255);
        } else {
            data[0] = (byte) (temperature & 255);
            data[1] = (byte) ((temperature >> 8) & 255);
        }
        if (!(unitType == 0 || unitType == 1)) {
            unitType = 0;
        }
        data[2] = (byte) unitType;
        if (weather < 0 || weather > 9) {
            weather = 0;
        }
        data[3] = (byte) weather;
        data[4] = (byte) (pm & 255);
        data[5] = (byte) ((pm >> 8) & 255);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(data);
        return writeWristBandData(header, datas);
    }

    public byte[] setSportGole(ArrayList<Byte> datas) {
        return writeWristBandDataByte(form_Header(1, 11), datas);
    }

    public byte[] getSportGoles(int week) {
        byte header = form_Header(1, 12);
        ArrayList<Byte> datas = new ArrayList<>();
        datas.add(Byte.valueOf((byte) week));
        return writeWristBandDataByte(header, datas);
    }

    public byte[] getSportType() {
        return writeWristBandData(form_Header(1, 10), null);
    }

    public byte[] setBle() {
        byte header = form_Header(1, 2);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{0, 1});
        return writeWristBandData(header, datas);
    }

    public byte[] setWristBandBle(int Peer_HandShake, int Conn_Params_Flag, int Conn_Interval_Min, int Conn_Interval_Max, int Conn_Sla_latency, int Conn_Sup_Timeout) {
        byte header = form_Header(1, 2);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{(byte) Peer_HandShake, (byte) Conn_Params_Flag, (byte) Conn_Interval_Min, (byte) Conn_Interval_Max, (byte) Conn_Sla_latency, (byte) Conn_Sup_Timeout});
        return writeWristBandData(header, datas);
    }

    public byte[] getBle() {
        return writeWristBandData(form_Header(1, 3), null);
    }

    public byte[] setSedentary(int id, int week, int startHour, int endHour, int alertDuration, int workCount) {
        byte[] data = {0, 0, 0, 0, 0, 0, 0};
        byte header = form_Header(1, 6);
        ArrayList<byte[]> sendentary = new ArrayList<>();
        data[0] = (byte) id;
        data[1] = (byte) week;
        data[2] = (byte) startHour;
        data[3] = (byte) endHour;
        if (alertDuration < 5 || alertDuration < 0) {
            data[4] = 1;
        } else {
            data[4] = (byte) Integer.parseInt(Integer.toHexString(alertDuration / 5), 16);
        }
        int high = workCount >> 16;
        data[5] = (byte) (workCount & 65535);
        data[6] = (byte) high;
        sendentary.add(data);
        return writeWristBandData(header, sendentary);
    }

    public byte[] getSedentary() {
        return writeWristBandData(form_Header(1, 7), null);
    }

    public byte[] setWristBandGestureAndLight(SparseBooleanArray array, int backLightStartTime, int backLightEndTime) {
        byte header = form_Header(1, 8);
        ArrayList<Byte> datas = new ArrayList<>();
        if (array.get(0)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(1)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(2)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(3)) {
            datas.add(Byte.valueOf(0));
        } else {
            datas.add(Byte.valueOf(1));
        }
        if (array.get(4)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        datas.add(Byte.valueOf(1));
        datas.add(Byte.valueOf((byte) backLightStartTime));
        datas.add(Byte.valueOf((byte) backLightEndTime));
        if (array.get(5)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(6)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(7)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        return writeWristBandDataByte(header, datas);
    }

    public byte[] setWristBandGestureAndLight(SparseBooleanArray array, int backLightStartTime, int backLightEndTime, int languageType) {
        byte header = form_Header(1, 8);
        ArrayList<Byte> datas = new ArrayList<>();
        if (array.get(0)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(1)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(2)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(3)) {
            datas.add(Byte.valueOf(0));
        } else {
            datas.add(Byte.valueOf(1));
        }
        if (array.get(4)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        datas.add(Byte.valueOf(1));
        datas.add(Byte.valueOf((byte) backLightStartTime));
        datas.add(Byte.valueOf((byte) backLightEndTime));
        if (array.get(5)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (languageType == 0) {
            datas.add(Byte.valueOf(0));
        } else if (languageType == 1) {
            datas.add(Byte.valueOf(1));
        } else if (languageType == 255) {
            datas.add(Byte.valueOf(-1));
        } else if (languageType == 2) {
            datas.add(Byte.valueOf(2));
        } else if (languageType == 3) {
            datas.add(Byte.valueOf(3));
        } else {
            datas.add(Byte.valueOf((byte) (languageType - 1)));
        }
        if (array.get(7)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(8)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        return writeWristBandDataByte(header, datas);
    }

    public byte[] setWristBandGestureAndLight(SparseBooleanArray array, int backLightStartTime, int backLightEndTime, int languageType, int wristLightFuncStartTime, int wristLightFuncEndTime) {
        byte header = form_Header(1, 8);
        ArrayList<Byte> datas = new ArrayList<>();
        if (array.get(0)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(1)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(2)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(3)) {
            datas.add(Byte.valueOf(0));
        } else {
            datas.add(Byte.valueOf(1));
        }
        if (array.get(4)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        datas.add(Byte.valueOf(1));
        datas.add(Byte.valueOf((byte) backLightStartTime));
        datas.add(Byte.valueOf((byte) backLightEndTime));
        if (array.get(5)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (languageType == -1) {
            if (array.get(6)) {
                datas.add(Byte.valueOf(1));
            } else {
                datas.add(Byte.valueOf(0));
            }
        } else if (languageType == 0) {
            datas.add(Byte.valueOf(1));
        } else if (languageType == 1) {
            datas.add(Byte.valueOf(0));
        } else {
            datas.add(Byte.valueOf(-1));
        }
        if (array.get(7)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(8)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(9)) {
            datas.add(Byte.valueOf((byte) wristLightFuncStartTime));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(10)) {
            datas.add(Byte.valueOf((byte) wristLightFuncEndTime));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(11)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(12)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        return writeWristBandDataByte(header, datas);
    }

    public byte[] setWristBandGestureAndLight(SparseBooleanArray array, int backLightStartTime, int backLightEndTime, int languageType, int dataFormat, int wristLightFuncStartTime, int wristLightFuncEndTime) {
        byte header = form_Header(1, 8);
        ArrayList<Byte> datas = new ArrayList<>();
        if (array.get(0)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(1)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(2)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(3)) {
            datas.add(Byte.valueOf(0));
        } else {
            datas.add(Byte.valueOf(1));
        }
        if (array.get(4)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        datas.add(Byte.valueOf(1));
        datas.add(Byte.valueOf((byte) backLightStartTime));
        datas.add(Byte.valueOf((byte) backLightEndTime));
        if (array.get(5)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (languageType == -1) {
            if (array.get(6)) {
                datas.add(Byte.valueOf(1));
            } else {
                datas.add(Byte.valueOf(0));
            }
        } else if (languageType != 255) {
            datas.add(Byte.valueOf((byte) languageType));
        } else {
            datas.add(Byte.valueOf(-1));
        }
        if (array.get(7)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.size() >= 9) {
            datas.add(Byte.valueOf((byte) dataFormat));
        }
        datas.add(Byte.valueOf((byte) wristLightFuncStartTime));
        datas.add(Byte.valueOf((byte) wristLightFuncEndTime));
        if (array.get(9)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(10)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        if (array.get(11)) {
            datas.add(Byte.valueOf(1));
        } else {
            datas.add(Byte.valueOf(0));
        }
        return writeWristBandDataByte(header, datas);
    }

    public byte[] getDeviceStateDate() {
        return writeWristBandData(form_Header(1, 9), null);
    }

    public byte[] setUserProfile(int height, int weight, boolean gender, int age, int steps) {
        byte[] xval = new byte[6];
        xval[0] = (byte) height;
        xval[1] = (byte) weight;
        xval[2] = (byte) (gender ? 0 : 1);
        try {
            xval[3] = (byte) age;
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        int goal_high = (byte) (steps >>> 8);
        xval[4] = (byte) (steps & 255);
        xval[5] = (byte) goal_high;
        byte header = form_Header(2, 0);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] getUserProfile() {
        return writeWristBandData(form_Header(2, 1), null);
    }

    public byte[] setHeartRateWarming(boolean isOn, int high, int low, int timeOut, int interval) {
        byte header = form_Header(2, 3);
        byte[] bytes = new byte[5];
        if (isOn) {
            bytes[0] = 1;
        } else {
            bytes[0] = 0;
        }
        bytes[1] = (byte) high;
        bytes[2] = (byte) low;
        bytes[3] = (byte) timeOut;
        bytes[4] = (byte) interval;
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(bytes);
        return writeWristBandData(header, datas);
    }

    public byte[] getHeartRateWarming() {
        return writeWristBandData(form_Header(2, 4), null);
    }

    public byte[] setWristBandSelfie(boolean flag) {
        byte header = form_Header(4, 0);
        ArrayList<byte[]> datas = new ArrayList<>();
        byte[] code = new byte[1];
        if (flag) {
            code[0] = 1;
        } else {
            code[0] = 0;
        }
        datas.add(code);
        return writeWristBandData(header, datas);
    }

    public byte[] setShakeMode(int type, int shakeModeIndex, int num, ArrayList<Map<String, Integer>> model) {
        byte header = form_Header(4, 1);
        ArrayList<Byte> datas = new ArrayList<>();
        if (type == 0) {
            datas.add(Byte.valueOf(0));
        } else if (type == 1) {
            datas.add(Byte.valueOf(1));
        } else if (type == 2) {
            datas.add(Byte.valueOf(2));
            datas.add(Byte.valueOf((byte) shakeModeIndex));
            datas.add(Byte.valueOf((byte) num));
        } else if (type == 3) {
            datas.add(Byte.valueOf(3));
            for (int i = 0; i < model.size(); i++) {
                Map<String, Integer> map = (Map) model.get(i);
                int index = ((Integer) map.get("index")).intValue();
                int number = ((Integer) map.get("number")).intValue();
                int shokeType = ((Integer) map.get("type")).intValue();
                datas.add(Byte.valueOf((byte) index));
                datas.add(Byte.valueOf((byte) number));
                datas.add(Byte.valueOf((byte) shokeType));
            }
        }
        return writeWristBandDataByte(header, datas);
    }

    public byte[] readDataInfoStored() {
        byte header = form_Header(0, 8);
        byte[] xval = {0};
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] showConnectionTipIcon() {
        byte header = form_Header(1, 2);
        byte[] xval = {0, 1, 1, 1, 0, 1};
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] showExceptionTipIcon(int delayShowTimeMinute) {
        byte header = form_Header(1, 2);
        byte[] xval = new byte[6];
        if (delayShowTimeMinute <= 0) {
            xval[0] = Byte.parseByte("64", 10);
        } else {
            xval[0] = (byte) (Byte.parseByte("-127", 10) | ((byte) delayShowTimeMinute));
        }
        xval[1] = 1;
        xval[2] = 1;
        xval[3] = 1;
        xval[4] = 0;
        xval[5] = 1;
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] getDataAccordingIndex(int dataType, int dataIndex) {
        byte header = form_Header(dataType / 16, dataType % 16);
        int low = dataIndex % 256;
        byte[] xval = {2, (byte) low, (byte) ((dataIndex - low) / 256)};
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] switchFindPhoneFunc(boolean toOpen) {
        byte header = form_Header(4, 14);
        byte[] xval = new byte[4];
        xval[0] = 1;
        xval[1] = 1;
        xval[2] = 1;
        if (toOpen) {
            xval[3] = 1;
        } else {
            xval[3] = 0;
        }
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] setGestureSensitivity(int level) {
        byte header = form_Header(4, 14);
        byte[] xval = {1, 2, 1, (byte) level};
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] readCustomDevSettings() {
        return writeWristBandData(form_Header(4, 15), null);
    }

    public void writeAlertFontLibrary(Context context, int type, String displayName) {
        ArrayList<Byte> datas = new ArrayList<>();
        datas.add(Byte.valueOf((byte) type));
        datas.add(Byte.valueOf(-1));
        try {
            byte[] cc = displayName.getBytes("utf-8");
            if (cc.length > 253) {
                cc = Arrays.copyOfRange(cc, 0, TinkerReport.KEY_LOADED_EXCEPTION_DEX_CHECK);
            }
            for (byte b : cc) {
                datas.add(Byte.valueOf(b));
            }
        } catch (UnsupportedEncodingException e) {
            ThrowableExtension.printStackTrace(e);
        }
        byte[] data = writeWristBandDataByte(form_Header(3, 1), datas);
        List<ITask> tasks = new ArrayList<>();
        for (int i = 0; i < data.length; i += 20) {
            if (i + 20 > data.length) {
                List<byte[]> writes = new ArrayList<>();
                byte[] writeData = Arrays.copyOfRange(data, i, data.length);
                DataBean bean = new DataBean();
                writes.add(writeData);
                bean.setData(writes);
                bean.setFlag(false);
                ZeronerBleWriteDataTask task = new ZeronerBleWriteDataTask(context, bean);
                writes.add(writeData);
                tasks.add(task);
            } else {
                List<byte[]> writes2 = new ArrayList<>();
                byte[] writeData2 = Arrays.copyOfRange(data, i, i + 20);
                DataBean bean2 = new DataBean();
                writes2.add(writeData2);
                bean2.setData(writes2);
                bean2.setFlag(true);
                tasks.add(new ZeronerBleWriteDataTask(context, bean2));
            }
        }
        ZeronerBackgroundThreadManager.getInstance().addAllTask(tasks);
    }

    public byte[] setHeartBeat(int value) {
        byte header = form_Header(0, 9);
        byte[] xval = {(byte) value};
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] setHeartRateParams(int strength, int min, int type) {
        byte header = form_Header(5, 0);
        ArrayList<Byte> datas = new ArrayList<>();
        if (type == 0) {
            datas.add(Byte.valueOf((byte) type));
            datas.add(Byte.valueOf((byte) strength));
            datas.add(Byte.valueOf((byte) min));
            return writeWristBandDataByte(header, datas);
        } else if (type != 1) {
            return null;
        } else {
            datas.add(Byte.valueOf((byte) type));
            return writeWristBandDataByte(header, datas);
        }
    }

    public byte[] setDialydata28(int value, boolean flag, int index) {
        byte header = form_Header(2, 8);
        ArrayList<byte[]> datas = new ArrayList<>();
        if (flag) {
            datas.add(new byte[]{(byte) value});
        } else {
            int goal_low = index % 256;
            datas.add(new byte[]{(byte) value, (byte) goal_low, (byte) ((index - goal_low) / 256)});
        }
        return writeWristBandData(header, datas);
    }

    public byte[] setDialydata29(int value) {
        byte header = form_Header(2, 9);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{(byte) value});
        return writeWristBandData(header, datas);
    }
}
