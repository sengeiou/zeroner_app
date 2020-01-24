package com.iwown.ble_module.zg_ble.data.alarm_clock;

import android.support.v4.app.NotificationCompat;
import com.alibaba.json.annotation.JSONField;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.common.base.Ascii;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.ble_module.utils.JsonTool;
import com.iwown.ble_module.zg_ble.utils.ByteUtil;
import com.iwown.device_module.device_alarm_schedule.activity.schedule.AddSchedulePresenter;
import com.socks.library.KLog;
import com.tencent.tinker.android.dx.instruction.Opcodes;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZGAlarmClockScheduleHandler {
    private static ResponseClock_ScheduleData responseClock_scheduleData = new ResponseClock_ScheduleData();

    public static class ResponseClock_ScheduleData {
        List<ZGAlarmClockBean> alarmClockBeanList;
        @JSONField(serialize = false)
        int index = 0;
        @JSONField(serialize = false)
        byte[] old_datas = new byte[240];
        List<ZGSchedule> zgScheduleList;

        public List<ZGAlarmClockBean> getAlarmClockBeanList() {
            return this.alarmClockBeanList;
        }

        public void setAlarmClockBeanList(List<ZGAlarmClockBean> alarmClockBeanList2) {
            this.alarmClockBeanList = alarmClockBeanList2;
        }

        public List<ZGSchedule> getZgScheduleList() {
            return this.zgScheduleList;
        }

        public void setZgScheduleList(List<ZGSchedule> zgScheduleList2) {
            this.zgScheduleList = zgScheduleList2;
        }

        public String handleAlarmClock_ScheduleDatas() {
            this.alarmClockBeanList = new ArrayList();
            this.zgScheduleList = new ArrayList();
            byte[] alarm_clock_datas = new byte[80];
            System.arraycopy(this.old_datas, 0, alarm_clock_datas, 0, 80);
            for (int i = 0; i < alarm_clock_datas.length; i += 20) {
                int index2 = i;
                ZGAlarmClockBean zgAlarmClockBean = new ZGAlarmClockBean();
                zgAlarmClockBean.alarmSet = ByteUtil.bytesToInt(Arrays.copyOfRange(alarm_clock_datas, index2, index2 + 1));
                int index3 = index2 + 1;
                zgAlarmClockBean.alarmHour = ByteUtil.bytesToInt(Arrays.copyOfRange(alarm_clock_datas, index3, index3 + 1));
                int index4 = index3 + 1;
                zgAlarmClockBean.alarmMinute = ByteUtil.bytesToInt(Arrays.copyOfRange(alarm_clock_datas, index4, index4 + 1));
                int index5 = index4 + 1;
                zgAlarmClockBean.alarmRingSetting = ByteUtil.bytesToInt(Arrays.copyOfRange(alarm_clock_datas, index5, index5 + 1));
                int index6 = index5 + 1;
                zgAlarmClockBean.alarm_len = ByteUtil.bytesToInt(Arrays.copyOfRange(alarm_clock_datas, index6, index6 + 1));
                int index7 = index6 + 1;
                zgAlarmClockBean.text = new String(Arrays.copyOfRange(alarm_clock_datas, index7, zgAlarmClockBean.alarm_len + index7));
                this.alarmClockBeanList.add(zgAlarmClockBean);
            }
            byte[] shedule_datas = new byte[Opcodes.AND_LONG];
            int i1 = this.old_datas.length - 80;
            if (i1 > 160) {
                i1 = Opcodes.AND_LONG;
            }
            System.arraycopy(this.old_datas, 80, shedule_datas, 0, i1);
            for (int i2 = 0; i2 < shedule_datas.length; i2 += 40) {
                int index8 = i2;
                ZGSchedule zgSchedule = new ZGSchedule();
                zgSchedule.scheduler_action = ByteUtil.bytesToInt(Arrays.copyOfRange(shedule_datas, index8, index8 + 1));
                int index9 = index8 + 1;
                zgSchedule.scheduler_year = ByteUtil.bytesToInt(Arrays.copyOfRange(shedule_datas, index9, index9 + 2));
                int index10 = index9 + 2;
                zgSchedule.scheduler_month = ByteUtil.bytesToInt(Arrays.copyOfRange(shedule_datas, index10, index10 + 1));
                int index11 = index10 + 1;
                zgSchedule.scheduler_day = ByteUtil.bytesToInt(Arrays.copyOfRange(shedule_datas, index11, index11 + 1));
                int index12 = index11 + 1;
                zgSchedule.scheduler_hour = ByteUtil.bytesToInt(Arrays.copyOfRange(shedule_datas, index12, index12 + 1));
                int index13 = index12 + 1;
                zgSchedule.scheduler_minute = ByteUtil.bytesToInt(Arrays.copyOfRange(shedule_datas, index13, index13 + 1));
                int index14 = index13 + 1;
                zgSchedule.scheringSetting = ByteUtil.bytesToInt(Arrays.copyOfRange(shedule_datas, index14, index14 + 1));
                int index15 = index14 + 1;
                zgSchedule.scheduler_len = ByteUtil.bytesToInt(Arrays.copyOfRange(shedule_datas, index15, index15 + 1));
                int index16 = index15 + 1;
                zgSchedule.text = new String(Arrays.copyOfRange(shedule_datas, index16, zgSchedule.scheduler_len + index16));
                this.zgScheduleList.add(zgSchedule);
            }
            this.old_datas = new byte[240];
            this.index = 0;
            return JsonTool.toJson(this);
        }
    }

    public static class ZGAlarmClockBean {
        public int alarmHour;
        public int alarmMinute;
        public int alarmRingSetting = 1;
        public int alarmSet;
        public int alarm_len;
        public String text = NotificationCompat.CATEGORY_ALARM;

        public int getAlarmSet() {
            if (this.alarmSet == 0) {
                return this.alarmSet;
            }
            String binary_str = "1" + Integer.toBinaryString(this.alarmSet);
            int i = Integer.parseInt(binary_str, 2);
            KLog.d("binary_str " + binary_str + "  int " + i);
            return i;
        }

        public String getText() {
            String real_text = this.text;
            try {
                byte[] bytes = this.text.getBytes("utf-8");
                if (bytes.length > 15) {
                    KLog.e("alarm text length >15 use defaut");
                    byte[] bytes1 = new byte[15];
                    System.arraycopy(bytes, 0, bytes1, 0, 15);
                    return new String(bytes1);
                }
                real_text = this.text;
                return real_text;
            } catch (UnsupportedEncodingException e) {
                ThrowableExtension.printStackTrace(e);
            }
        }

        public String toString() {
            return "ZGAlarmClockBean{alarmSet=" + this.alarmSet + ", alarmHour=" + this.alarmHour + ", alarmMinute=" + this.alarmMinute + ", alarmRingSetting=" + ByteUtil.bytesToString1(new byte[]{(byte) this.alarmRingSetting}) + ", text='" + this.text + '\'' + '}';
        }
    }

    public static class ZGSchedule {
        public int scheduler_action;
        public int scheduler_day;
        public int scheduler_hour;
        public int scheduler_len;
        public int scheduler_minute;
        public int scheduler_month;
        public int scheduler_year = 2017;
        public int scheringSetting = 1;
        public String text = AddSchedulePresenter.TAG;

        public String getText() {
            String real_text;
            String str = this.text;
            try {
                byte[] bytes = this.text.getBytes("utf-8");
                if (bytes.length > 31) {
                    KLog.e("schedule text length >31 use defaut");
                    byte[] bytes1 = new byte[30];
                    System.arraycopy(bytes, 0, bytes1, 0, 30);
                    return new String(bytes1);
                }
                real_text = this.text;
                return real_text;
            } catch (UnsupportedEncodingException e) {
                ThrowableExtension.printStackTrace(e);
                real_text = AddSchedulePresenter.TAG;
            }
        }
    }

    public static int getMode(int type, int count) {
        byte[] typeArray = ByteUtil.byteToBitArray(type);
        byte[] countArray = ByteUtil.byteToBitArray(count);
        return Integer.parseInt(ByteUtil.bytesToStringFormat(new byte[]{typeArray[5], typeArray[6], typeArray[7], countArray[3], countArray[4], countArray[5], countArray[6], countArray[7]}), 2);
    }

    public static byte[] readAlarmClock() {
        return new byte[]{0};
    }

    public static String parseAlarmClock(byte[] datas) {
        KLog.e("parseAlarmClock " + ByteUtil.bytesToString1(datas) + "  " + datas.length);
        byte[] temp_datas = new byte[16];
        if (datas.length < 20) {
            System.arraycopy(datas, 4, temp_datas, 0, datas.length - 4);
        } else {
            System.arraycopy(datas, 4, temp_datas, 0, 16);
        }
        try {
            System.arraycopy(temp_datas, 0, responseClock_scheduleData.old_datas, responseClock_scheduleData.index, temp_datas.length);
            ResponseClock_ScheduleData responseClock_ScheduleData = responseClock_scheduleData;
            responseClock_ScheduleData.index += temp_datas.length;
            if (datas[2] >= 0 && responseClock_scheduleData.index < 240) {
                return null;
            }
            KLog.e("parseAlarmClock end action " + ByteUtil.bytesToString1(responseClock_scheduleData.old_datas));
            return responseClock_scheduleData.handleAlarmClock_ScheduleDatas();
        } catch (Exception e) {
            ThrowableExtension.printStackTrace(e);
            return null;
        }
    }

    public static byte[] writeAlarmZG(List<ZGAlarmClockBean> zgAlarmClockBeanList) {
        if (zgAlarmClockBeanList == null) {
            KLog.e("zgAlarmClockBeanList  must is not null");
            return new byte[80];
        }
        if (zgAlarmClockBeanList.size() > 4) {
            KLog.e("writeAlarm size must < 4");
            zgAlarmClockBeanList = zgAlarmClockBeanList.subList(0, 4);
        }
        byte[] datas = new byte[80];
        int index = 0;
        for (ZGAlarmClockBean bean : zgAlarmClockBeanList) {
            if (index >= 80) {
                break;
            }
            byte[] bean_datas = new byte[20];
            bean_datas[0] = (byte) bean.getAlarmSet();
            bean_datas[1] = (byte) bean.alarmHour;
            bean_datas[2] = (byte) bean.alarmMinute;
            bean_datas[3] = (byte) bean.alarmRingSetting;
            try {
                byte[] codeText = bean.getText().getBytes("utf-8");
                if (codeText.length > 15) {
                    bean_datas[4] = Ascii.SI;
                } else {
                    bean_datas[4] = (byte) codeText.length;
                }
                System.arraycopy(codeText, 0, bean_datas, 5, bean_datas[4]);
                KLog.d("bean_datas " + ByteUtil.bytesToString1(bean_datas) + " size " + bean_datas.length + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + new String(codeText));
                System.arraycopy(bean_datas, 0, datas, index, 20);
            } catch (UnsupportedEncodingException e) {
                ThrowableExtension.printStackTrace(e);
            }
            index += 20;
        }
        KLog.d("writeAlarmZG size " + index + " datas length " + datas.length);
        return datas;
    }

    public static byte[] writeScheduleZG(List<ZGSchedule> zgScheduleList) {
        if (zgScheduleList == null) {
            KLog.e("zgScheduleList  must is not null");
            return new byte[Opcodes.AND_LONG];
        }
        if (zgScheduleList.size() > 4) {
            KLog.e("writeSchedule size must < 4");
            zgScheduleList = zgScheduleList.subList(0, 4);
        }
        byte[] datas = new byte[Opcodes.AND_LONG];
        int index = 0;
        for (ZGSchedule bean : zgScheduleList) {
            if (index >= 160) {
                break;
            }
            byte[] bean_datas = new byte[40];
            bean_datas[0] = (byte) ByteUtil.loword(bean.scheduler_year);
            bean_datas[1] = (byte) ByteUtil.hiword(bean.scheduler_year);
            bean_datas[2] = (byte) bean.scheduler_action;
            bean_datas[3] = (byte) bean.scheduler_month;
            bean_datas[4] = (byte) bean.scheduler_day;
            bean_datas[5] = (byte) bean.scheduler_hour;
            bean_datas[6] = (byte) bean.scheduler_minute;
            bean_datas[7] = (byte) bean.scheringSetting;
            try {
                byte[] codeText = bean.getText().getBytes("utf-8");
                if (codeText.length > 30) {
                    bean_datas[8] = Ascii.SI;
                } else {
                    bean_datas[8] = (byte) codeText.length;
                }
                System.arraycopy(codeText, 0, bean_datas, 9, bean_datas[8]);
                KLog.d("bean_datas " + ByteUtil.bytesToString1(bean_datas) + " size " + bean_datas.length + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + new String(codeText));
                System.arraycopy(bean_datas, 0, datas, index, 40);
            } catch (UnsupportedEncodingException e) {
                ThrowableExtension.printStackTrace(e);
            }
            index += bean_datas.length;
        }
        KLog.d("writeScheduleZG size " + index + " datas length " + datas.length);
        return datas;
    }
}
