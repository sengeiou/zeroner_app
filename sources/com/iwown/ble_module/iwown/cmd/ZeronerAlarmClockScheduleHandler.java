package com.iwown.ble_module.iwown.cmd;

import android.text.TextUtils;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.iwown.utils.ByteUtil;
import com.iwown.ble_module.iwown.utils.HexUtil;
import com.iwown.ble_module.utils.JsonTool;
import com.socks.library.KLog;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ZeronerAlarmClockScheduleHandler {
    private static final String TAG = "IWOWNAlarmClockScheduleHandler";

    public static class AlarmClockBean {
        public int conf;
        public String data;
        public int hour;
        public int id;
        public int len;
        public int mintue;
        public int type;
    }

    public static class ScheduleBean {
        public int curSetableNum;
        public int maxSetableNum;
        public int perdaySetableNum;
    }

    public static String parseSchedule(byte[] datas) {
        try {
            KLog.d(TAG, "接收到数据：读取日程");
            int curSetableNum = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
            int maxSetableNum = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
            int perdaySetableNum = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
            KLog.d(TAG, "curSetableNum = " + curSetableNum + ",maxSetableNum = " + maxSetableNum + ",perdaySetableNum = " + perdaySetableNum);
            ScheduleBean scheduleBean = new ScheduleBean();
            scheduleBean.curSetableNum = curSetableNum;
            scheduleBean.maxSetableNum = maxSetableNum;
            scheduleBean.perdaySetableNum = perdaySetableNum;
            return JsonTool.toJson(scheduleBean);
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static String parseSetScheduleResponse(byte[] data) {
        int success = ByteUtil.bytesToInt(Arrays.copyOfRange(data, 4, 5));
        Map<String, Integer> map = new HashMap<>();
        map.put("success", Integer.valueOf(success));
        return JsonTool.toJson(map);
    }

    public static byte[] syncHeartRateSegmentData(int type) {
        return new byte[]{(byte) type};
    }

    public static byte[] syncHeartRateHourData(int type) {
        return new byte[]{(byte) type};
    }

    public static String parseAlarmClock(byte[] datas) {
        KLog.e("parseAlarmClock " + ByteUtil.bytesToString1(datas));
        AlarmClockBean alarmClockBean = new AlarmClockBean();
        alarmClockBean.id = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 4, 5));
        alarmClockBean.type = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 5, 6));
        alarmClockBean.conf = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 6, 7));
        alarmClockBean.hour = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 7, 8));
        alarmClockBean.mintue = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 8, 9));
        try {
            alarmClockBean.len = ByteUtil.bytesToInt(Arrays.copyOfRange(datas, 9, 10));
            alarmClockBean.data = ByteUtil.bytesToString(Arrays.copyOfRange(datas, 9, datas.length));
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
        }
        return JsonTool.toJson(alarmClockBean);
    }

    public static byte[] readAlarm(int id) {
        return new byte[]{(byte) id};
    }

    public static byte[] writeAlarm(int id, int weekRepeat, int hour, int minute, String text) {
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
            int num = 0;
            StringBuffer result = new StringBuffer();
            int i = 0;
            while (i < text.length()) {
                String a = text.substring(i, i + 1);
                try {
                    byte[] cf = a.getBytes("utf-8");
                    if (cf.length + num <= 15) {
                        num += cf.length;
                        result.append(a);
                        i++;
                    }
                } catch (UnsupportedEncodingException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
            try {
                byte[] codeText = result.toString().getBytes("utf-8");
                datas.add(Byte.valueOf(HexUtil.int2byte(codeText.length)));
                for (byte b : codeText) {
                    datas.add(Byte.valueOf(b));
                }
            } catch (UnsupportedEncodingException e2) {
                ThrowableExtension.printStackTrace(e2);
            }
        }
        byte[] data = new byte[datas.size()];
        for (int i2 = 0; i2 < datas.size(); i2++) {
            data[i2] = ((Byte) datas.get(i2)).byteValue();
        }
        return data;
    }

    public static byte[] setSchedule(int year, int month, int day, int hour, int minute, String text) {
        ArrayList<Byte> datas = new ArrayList<>();
        datas.add(Byte.valueOf(0));
        datas.add(Byte.valueOf(HexUtil.int2byte(year - 2000)));
        datas.add(Byte.valueOf(HexUtil.int2byte(month - 1)));
        datas.add(Byte.valueOf(HexUtil.int2byte(day - 1)));
        datas.add(Byte.valueOf(HexUtil.int2byte(hour)));
        datas.add(Byte.valueOf(HexUtil.int2byte(minute)));
        datas.add(Byte.valueOf(HexUtil.int2byte(0)));
        datas.add(Byte.valueOf(HexUtil.int2byte(0)));
        if (TextUtils.isEmpty(text) || text.length() < 1) {
            datas.add(Byte.valueOf(0));
        } else {
            int num = 0;
            StringBuffer result = new StringBuffer();
            int i = 0;
            while (i < text.length()) {
                String a = text.substring(i, i + 1);
                try {
                    byte[] cf = a.getBytes("utf-8");
                    if (cf.length + num > 54) {
                        break;
                    }
                    num += cf.length;
                    result.append(a);
                    i++;
                } catch (UnsupportedEncodingException e) {
                    ThrowableExtension.printStackTrace(e);
                }
            }
        }
        try {
            byte[] codeText = text.getBytes("utf-8");
            datas.add(Byte.valueOf(HexUtil.int2byte(codeText.length)));
            KLog.d(TAG, "codeText.length = " + codeText.length);
            for (byte b : codeText) {
                datas.add(Byte.valueOf(b));
            }
        } catch (UnsupportedEncodingException e2) {
            ThrowableExtension.printStackTrace(e2);
        }
        byte[] data = new byte[datas.size()];
        for (int i2 = 0; i2 < datas.size(); i2++) {
            data[i2] = ((Byte) datas.get(i2)).byteValue();
        }
        return data;
    }

    public static byte[] readScheduleInfo() {
        return new byte[]{0};
    }
}
