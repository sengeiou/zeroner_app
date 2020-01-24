package com.iwown.ble_module.mtk_ble.cmd;

import android.content.Context;
import android.support.annotation.Size;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import com.google.common.base.Ascii;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.model.Weather24h;
import com.iwown.ble_module.model.Weather7D;
import com.iwown.ble_module.mtk_ble.task.BleWriteDataTask;
import com.iwown.ble_module.mtk_ble.task.ITask;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.ble_module.utils.Util;
import com.iwown.lib_common.date.DateUtil;
import com.tencent.bugly.beta.tinker.TinkerReport;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MtkCmdAssembler {
    private static MtkCmdAssembler instance;
    private static Context mContext;
    private String TAG = getClass().getSimpleName();

    private MtkCmdAssembler(Context context) {
        mContext = context;
    }

    public static MtkCmdAssembler getInstance(Context context) {
        if (instance == null) {
            synchronized (MtkCmdAssembler.class) {
                if (instance == null) {
                    instance = new MtkCmdAssembler(context);
                }
            }
        }
        return instance;
    }

    public static MtkCmdAssembler getInstance() {
        return instance;
    }

    public byte[] getFirmwareInformation() {
        return writeWristBandData(form_Header(0, 0), null);
    }

    public byte[] getFirmwareProductTime() {
        return writeWristBandData(form_Header(0, 11), null);
    }

    public byte[] getFirmwareProductInfo() {
        return writeWristBandData(form_Header(0, 12), null);
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

    public void writeAlarmClock(Context context, int id, int weekRepeat, int hour, int minute, String text) {
        ArrayList<Byte> datas = new ArrayList<>();
        datas.add(Byte.valueOf((byte) id));
        if (id > 7) {
            datas.add(Byte.valueOf(0));
            datas.add(Byte.valueOf(0));
            datas.add(Byte.valueOf(0));
            datas.add(Byte.valueOf(0));
            datas.add(Byte.valueOf(0));
        } else {
            datas.add(Byte.valueOf(0));
            datas.add(Byte.valueOf((byte) weekRepeat));
            datas.add(Byte.valueOf((byte) hour));
            datas.add(Byte.valueOf((byte) minute));
        }
        if (TextUtils.isEmpty(text) || text.length() < 1) {
            datas.add(Byte.valueOf(0));
        } else {
            byte[] bytes = null;
            byte[] bytes2 = null;
            int byteCount = 0;
            for (int i = 0; i < text.length(); i++) {
                try {
                    bytes = (text.charAt(i) + "").getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                byteCount += bytes.length;
                if (byteCount > 15) {
                    break;
                }
                bytes2 = Util.concat(bytes2, bytes);
            }
            datas.add(Byte.valueOf(Util.int2byte(bytes2.length)));
            for (byte valueOf : bytes2) {
                datas.add(Byte.valueOf(valueOf));
            }
        }
        byte[] data = writeWristBandDataByte(form_Header(1, 4), datas);
        for (int i2 = 0; i2 < data.length; i2 += 20) {
            if (i2 + 20 > data.length) {
                MtkBackgroundThreadManager.getInstance().addWriteData(context, Arrays.copyOfRange(data, i2, data.length));
            } else {
                MtkBackgroundThreadManager.getInstance().addWriteData(context, Arrays.copyOfRange(data, i2, i2 + 20));
            }
        }
    }

    public void closeAlarm(int id, Context context) {
        byte[] data = {(byte) id, 0, 0, 0, 0, 0};
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(data);
        MtkBackgroundThreadManager.getInstance().addWriteData(context, writeWristBandData(form_Header(1, 4), datas));
        Log.d(this.TAG, "closeAlarm");
    }

    public void getAlarmClock(Context context, int id) {
        byte header = form_Header(1, 5);
        ArrayList<Byte> datas = new ArrayList<>();
        datas.add(Byte.valueOf((byte) id));
        MtkBackgroundThreadManager.getInstance().addWriteData(context, writeWristBandDataByte(header, datas));
    }

    public void setSchedule(Context context, int year, int month, int day, int hour, int minute, String text) {
        ArrayList<Byte> datas = new ArrayList<>();
        datas.add(Byte.valueOf(0));
        datas.add(Byte.valueOf(Util.int2byte(year - 2000)));
        datas.add(Byte.valueOf(Util.int2byte(month - 1)));
        datas.add(Byte.valueOf(Util.int2byte(day - 1)));
        datas.add(Byte.valueOf(Util.int2byte(hour)));
        datas.add(Byte.valueOf(Util.int2byte(minute)));
        datas.add(Byte.valueOf(Util.int2byte(0)));
        datas.add(Byte.valueOf(Util.int2byte(0)));
        if (TextUtils.isEmpty(text) || text.length() < 1) {
            datas.add(Byte.valueOf(0));
        } else {
            byte[] bytes = null;
            byte[] bytes2 = null;
            int byteCount = 0;
            for (int i = 0; i < text.length(); i++) {
                try {
                    bytes = (text.charAt(i) + "").getBytes("utf-8");
                } catch (UnsupportedEncodingException e) {
                    ThrowableExtension.printStackTrace(e);
                }
                byteCount += bytes.length;
                if (byteCount > 54) {
                    break;
                }
                bytes2 = Util.concat(bytes2, bytes);
            }
            datas.add(Byte.valueOf(Util.int2byte(bytes2.length)));
            for (byte valueOf : bytes2) {
                datas.add(Byte.valueOf(valueOf));
            }
        }
        byte[] data = writeWristBandDataByte(form_Header(1, 13), datas);
        new ArrayList();
        Log.d(this.TAG, "开始设置日程：text = " + text + ",year = " + year + ",month = " + month + ",day = " + day + ",hour = " + hour + ",minute = " + minute);
        for (int i2 = 0; i2 < data.length; i2 += 20) {
            if (i2 + 20 > data.length) {
                MtkBackgroundThreadManager.getInstance().addWriteData(context, Arrays.copyOfRange(data, i2, data.length));
            } else {
                MtkBackgroundThreadManager.getInstance().addWriteData(context, Arrays.copyOfRange(data, i2, i2 + 20));
            }
        }
    }

    public void closeSchedule(Context context, int year, int month, int day, int hour, int minute) {
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{2, Util.int2byte(year - 2000), Util.int2byte(month - 1), Util.int2byte(day - 1), Util.int2byte(hour), Util.int2byte(minute)});
        byte[] data = writeWristBandData(form_Header(1, 13), datas);
        Log.d(this.TAG, "删除 开始时间测试");
        Log.d(this.TAG, "删除 addTask");
        MtkBackgroundThreadManager.getInstance().addWriteData(context, data);
    }

    public void clearAllSchedule(Context context) {
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{1});
        MtkBackgroundThreadManager.getInstance().addWriteData(context, writeWristBandData(form_Header(1, 13), datas));
    }

    public void readScheduleInfo(Context context) {
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{0});
        MtkBackgroundThreadManager.getInstance().addTask(new BleWriteDataTask(context, writeWristBandData(form_Header(1, 14), datas)));
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

    public byte[] setUnbind() {
        return writeWristBandData(form_Header(0, 5), null);
    }

    public byte[] setQuietMode(int startHour, int startMin, int endHour, int endMin) {
        if (startHour < 0 || startMin < 0 || endHour < 0 || endMin < 0 || startHour > 23 || endHour > 23 || startMin > 59 || endMin > 59) {
            throw new IllegalArgumentException("argument is out of range");
        }
        byte header = form_Header(0, 6);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{0, 1, Util.int2byte(startHour), Util.int2byte(startMin), Util.int2byte(endHour), Util.int2byte(endMin)});
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
        int low = workCount % 256;
        int high = (workCount - low) / 256;
        data[5] = (byte) low;
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
        datas.add(Byte.valueOf((byte) languageType));
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

    public byte[] setRestart() {
        byte header = form_Header(0, 2);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(new byte[]{0});
        return writeWristBandData(header, datas);
    }

    public byte[] getDeviceStateDate() {
        return writeWristBandData(form_Header(1, 9), null);
    }

    public byte[] setUserProfile(int height, int weight, boolean gender, int age, int steps) {
        byte[] xval = new byte[8];
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
        xval[6] = 100;
        xval[7] = 100;
        byte header = form_Header(2, 0);
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(xval);
        return writeWristBandData(header, datas);
    }

    public byte[] setUserProfileBlood(int height, int weight, boolean gender, int age, int steps, int srcSbp, int srcDbp, int dstSbp, int dstDbp) {
        byte[] xval = new byte[12];
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
        xval[6] = 100;
        xval[7] = 100;
        xval[8] = (byte) srcSbp;
        xval[9] = (byte) srcDbp;
        xval[10] = (byte) dstSbp;
        xval[11] = (byte) dstDbp;
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
                BleWriteDataTask task = new BleWriteDataTask(context, Arrays.copyOfRange(data, i, data.length), 4);
                task.setFlag(false);
                tasks.add(task);
            } else {
                BleWriteDataTask task2 = new BleWriteDataTask(context, Arrays.copyOfRange(data, i, i + 20), 4);
                task2.setFlag(false);
                tasks.add(task2);
            }
        }
        MtkBackgroundThreadManager.getInstance().addAllTaskSecond(tasks);
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

    public void getIndexTableAccordingType(int... dataType) {
        for (int i : dataType) {
            byte header = form_Header(i / 16, i % 16);
            ArrayList<byte[]> healthData = new ArrayList<>();
            healthData.add(new byte[]{0});
            MtkBackgroundThreadManager.getInstance().addWriteData(mContext, writeWristBandData(header, healthData));
        }
    }

    public void getDetailDataAsIndex(int year, int mon, int day, int startIndex, int endIndex, int dataType) {
        byte header = form_Header(dataType / 16, dataType % 16);
        ArrayList<byte[]> healthData = new ArrayList<>();
        byte[] data = {1, 0, 0, 0, 0, 0, 0, 0};
        data[1] = (byte) (year - 2000);
        data[2] = (byte) (mon - 1);
        data[3] = (byte) (day - 1);
        int low1 = startIndex % 256;
        int hight1 = (startIndex - low1) / 256;
        data[4] = (byte) low1;
        data[5] = (byte) hight1;
        int low2 = endIndex % 256;
        int hight2 = (endIndex - low2) / 256;
        data[6] = (byte) low2;
        data[7] = (byte) hight2;
        healthData.add(data);
        MtkBackgroundThreadManager.getInstance().addWriteData(mContext, writeWristBandData(header, healthData));
    }

    public void stopSyncDetailData(int... dataType) {
        for (int i : dataType) {
            byte header = form_Header(i / 16, i % 16);
            ArrayList<byte[]> healthData = new ArrayList<>();
            healthData.add(new byte[]{2});
            MtkBackgroundThreadManager.getInstance().addWriteData(mContext, writeWristBandData(header, healthData));
        }
    }

    public void dailyHealthDataSwitch(boolean isOn) {
        byte header = form_Header(6, 0);
        ArrayList<byte[]> healthData = new ArrayList<>();
        healthData.add(isOn ? new byte[]{1} : new byte[]{0});
        MtkBackgroundThreadManager.getInstance().addWriteData(mContext, writeWristBandData(header, healthData));
    }

    public void realTimeLocationDataSwitch(boolean isOn) {
        byte header = form_Header(6, 3);
        ArrayList<byte[]> gnss = new ArrayList<>();
        byte[] data = {0};
        if (isOn) {
            data[0] = 1;
        }
        gnss.add(data);
        MtkBackgroundThreadManager.getInstance().addWriteData(mContext, writeWristBandData(header, gnss));
    }

    public void writeGnssParams(int timeZone, String latitude_str, String longitude_str, int altitude) {
        byte header = form_Header(2, 5);
        byte[] data = Util.concat(new byte[0], new byte[]{(byte) timeZone});
        int lat_str_len = latitude_str.length();
        int long_str_len = longitude_str.length();
        StringBuilder sb = new StringBuilder(latitude_str);
        if (lat_str_len >= 12) {
            sb.substring(0, 11);
            sb.append(0);
        } else {
            for (int i = 0; i < 12 - lat_str_len; i++) {
                sb.append(0);
            }
        }
        String latitude_str2 = sb.toString();
        StringBuilder longStrBuilder = new StringBuilder(longitude_str);
        if (long_str_len >= 12) {
            longStrBuilder.substring(0, 11);
            longStrBuilder.append(0);
        } else {
            for (int i2 = 0; i2 < 12 - long_str_len; i2++) {
                longStrBuilder.append(0);
            }
        }
        try {
            data = Util.concat(Util.concat(data, latitude_str2.getBytes("UTF-8")), longStrBuilder.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            ThrowableExtension.printStackTrace(e);
        }
        byte[] data2 = Util.concat(data, new byte[]{(byte) (altitude % 256), (byte) (altitude / 256)});
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(data2);
        byte[] data1 = writeWristBandData(header, datas);
        int cmd_nums = data1.length % 20 == 0 ? data1.length / 20 : (data1.length / 20) + 1;
        for (int j = 0; j < cmd_nums; j++) {
            MtkBackgroundThreadManager.getInstance().addWriteData(mContext, Arrays.copyOfRange(data1, j * 20, (j + 1) * 20 > data1.length ? data1.length : (j + 1) * 20));
        }
    }

    public void writeEpo() {
        byte header = form_Header(6, 5);
        byte[] data = {1, 1};
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(data);
        MtkBackgroundThreadManager.getInstance().addWriteData(mContext, writeWristBandData(header, datas));
    }

    public void writeP1Target(int steps, int calorie) {
        byte header = form_Header(6, 6);
        byte[] stepBytes = Util.concat(ByteUtil.intToByte(steps, 4), ByteUtil.intToByte(calorie, 4));
        ArrayList<byte[]> datas = new ArrayList<>();
        datas.add(stepBytes);
        MtkBackgroundThreadManager.getInstance().addWriteData(mContext, writeWristBandData(header, datas));
    }

    public byte[] setR1Switch(boolean tpTouch, boolean wearRegonize) {
        byte[] xval = new byte[3];
        xval[0] = 1;
        if (tpTouch) {
            xval[1] = 1;
        } else {
            xval[1] = 0;
        }
        if (wearRegonize) {
            xval[2] = 1;
        } else {
            xval[2] = 0;
        }
        return writeWristBandDataByte(form_Header(0, 14), xval);
    }

    public byte[] getR1Switch() {
        return writeWristBandDataByte(form_Header(0, 14), new byte[]{0});
    }

    public void sendFutureWeather(Context context, int year, int month, int day, int hour, boolean isCentigrade, @Size(max = 24) List<Weather24h> weather24hs, @Size(max = 7) List<Weather7D> weather7DS) {
        byte header = form_Header(0, 15);
        ArrayList<Byte> datas = new ArrayList<>();
        datas.add(Byte.valueOf((byte) (year - 2000)));
        datas.add(Byte.valueOf((byte) (month - 1)));
        datas.add(Byte.valueOf((byte) (day - 1)));
        datas.add(Byte.valueOf((byte) hour));
        if (isCentigrade) {
            datas.add(Byte.valueOf(0));
        } else {
            datas.add(Byte.valueOf(1));
        }
        if (weather24hs != null && weather24hs.size() != 0) {
            for (Weather24h weather24h : weather24hs) {
                int temperature = weather24h.getTemperature();
                if (temperature < 0) {
                    int temperature2 = temperature | 32768;
                    datas.add(Byte.valueOf((byte) (temperature2 & 255)));
                    datas.add(Byte.valueOf((byte) ((temperature2 >> 8) & 255)));
                } else {
                    datas.add(Byte.valueOf((byte) (temperature & 255)));
                    datas.add(Byte.valueOf((byte) ((temperature >> 8) & 255)));
                }
                datas.add(Byte.valueOf((byte) weather24h.getWeather_type()));
                int pm = weather24h.getPm_2_5();
                if (pm == -1) {
                    datas.add(Byte.valueOf(0));
                    datas.add(Byte.valueOf(0));
                } else {
                    datas.add(Byte.valueOf((byte) (pm & 255)));
                    datas.add(Byte.valueOf((byte) ((pm >> 8) & 255)));
                }
            }
            if (!(weather7DS == null || weather7DS.size() == 0)) {
                for (Weather7D weather7D : weather7DS) {
                    int max = weather7D.getMax_temp();
                    int min = weather7D.getMin_temp();
                    if (min < 0) {
                        int min2 = min | 32768;
                        datas.add(Byte.valueOf((byte) (min2 & 255)));
                        datas.add(Byte.valueOf((byte) ((min2 >> 8) & 255)));
                    } else {
                        datas.add(Byte.valueOf((byte) (min & 255)));
                        datas.add(Byte.valueOf((byte) ((min >> 8) & 255)));
                    }
                    if (max < 0) {
                        int max2 = max | 32768;
                        datas.add(Byte.valueOf((byte) (max2 & 255)));
                        datas.add(Byte.valueOf((byte) ((max2 >> 8) & 255)));
                    } else {
                        datas.add(Byte.valueOf((byte) (max & 255)));
                        datas.add(Byte.valueOf((byte) ((max >> 8) & 255)));
                    }
                    datas.add(Byte.valueOf((byte) weather7D.getWeather_type()));
                }
            }
            byte[] data = writeWristBandDataByte(header, datas);
            for (int i = 0; i < data.length; i += 20) {
                if (i + 20 > data.length) {
                    MtkBackgroundThreadManager.getInstance().addWriteData(context, Arrays.copyOfRange(data, i, data.length));
                } else {
                    MtkBackgroundThreadManager.getInstance().addWriteData(context, Arrays.copyOfRange(data, i, i + 20));
                }
            }
        }
    }
}
