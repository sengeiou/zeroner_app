package com.iwown.device_module.device_long_sit.pb;

import android.content.Context;
import com.iwown.ble_module.proto.cmd.ProtoBufSendBluetoothCmdImpl;
import com.iwown.ble_module.proto.task.BackgroundThreadManager;
import com.iwown.device_module.common.utils.ContextUtil;
import com.socks.library.KLog;
import org.apache.commons.cli.HelpFormatter;

public class PbLongSit {
    private static int LUNCH_BREAK_END = 14;
    private static int LUNCH_BREAK_START = 12;
    private static byte STATE_INCLUDE_LUNCH_BREAK = 6;
    private static byte STATE_INCLUDE_LUNCH_BREAK_START_LESS_12 = 8;
    private static byte STATE_INCLUDE_LUNCH_BREAK_START_MORE_14 = 7;
    private static byte STATE_IN_LUNCH_BREAK = 2;
    private static byte STATE_NOT_OVER_DAY = 0;
    private static byte STATE_OUT_LUNCH_BREAK = 1;
    private static byte STATE_OVER_DAY = 16;
    private static byte STATE_OVER_LUNCH_BREAK_BOTH = 5;
    private static byte STATE_OVER_LUNCH_BREAK_END = 4;
    private static byte STATE_OVER_LUNCH_BREAK_START = 3;
    public static final String TAG = "PbLongSit";
    private static int WEEK_REPEAT_REQUEST = 58;
    public static PbLongSit instance;

    private PbLongSit() {
    }

    public static PbLongSit getInstance() {
        if (instance == null) {
            instance = new PbLongSit();
        }
        return instance;
    }

    public void writeSedentaryIfLunchBreak(int startHour, int endHour, byte state, boolean lunchBreakOpen, int repeat) {
        if (lunchBreakOpen) {
            KLog.e(TAG, "打开lunch_break开关");
            if (state == 1) {
                writeSedentaryAccordingApi(startHour, endHour, 0, repeat);
                KLog.e(TAG, "时间段：" + startHour + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + endHour);
            } else if (state == 6) {
                writeSedentaryAccordingApi(startHour, LUNCH_BREAK_START, 0, repeat);
                writeSedentaryAccordingApi(LUNCH_BREAK_END, endHour, 1, repeat);
                KLog.e(TAG, "时间段1：" + startHour + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + LUNCH_BREAK_START);
                KLog.e(TAG, "时间段2：" + LUNCH_BREAK_END + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + endHour);
            } else if (state == 4) {
                writeSedentaryAccordingApi(LUNCH_BREAK_END, endHour, 0, repeat);
                KLog.e(TAG, "时间段：" + LUNCH_BREAK_END + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + endHour);
            } else if (state == 3) {
                writeSedentaryAccordingApi(startHour, LUNCH_BREAK_START, 0, repeat);
                KLog.e(TAG, "时间段：" + startHour + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + LUNCH_BREAK_START);
            } else if (state == 24) {
                writeSedentaryAccordingApi(startHour, LUNCH_BREAK_START, 0, repeat);
                writeSedentaryAccordingApi(LUNCH_BREAK_END, endHour, 1, repeat);
                KLog.e(TAG, "时间段1：" + startHour + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + LUNCH_BREAK_START);
                KLog.e(TAG, "时间段2：" + LUNCH_BREAK_END + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + endHour);
            }
        } else {
            KLog.e(TAG, "没有打开lunch_break开关");
            writeSedentaryAccordingApi(startHour, endHour, 0, repeat);
            KLog.e(TAG, "时间段：" + startHour + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + endHour);
        }
    }

    public byte judgeWhatState(int startHour, int endHour) {
        byte stateByte = 0;
        if (endHour > startHour) {
            if (endHour <= LUNCH_BREAK_START || startHour >= LUNCH_BREAK_END) {
                stateByte = (byte) (STATE_NOT_OVER_DAY | STATE_OUT_LUNCH_BREAK);
            }
            if (endHour > LUNCH_BREAK_END && startHour < LUNCH_BREAK_START) {
                stateByte = (byte) (STATE_NOT_OVER_DAY | STATE_INCLUDE_LUNCH_BREAK);
            }
            if (endHour <= LUNCH_BREAK_END && startHour >= LUNCH_BREAK_START) {
                stateByte = (byte) (STATE_NOT_OVER_DAY | STATE_IN_LUNCH_BREAK);
            }
            if (endHour > LUNCH_BREAK_END && startHour >= LUNCH_BREAK_START && startHour < LUNCH_BREAK_END) {
                stateByte = (byte) (STATE_NOT_OVER_DAY | STATE_OVER_LUNCH_BREAK_END);
            }
            if (endHour > LUNCH_BREAK_START && endHour <= LUNCH_BREAK_END && startHour < LUNCH_BREAK_START) {
                stateByte = (byte) (STATE_NOT_OVER_DAY | STATE_OVER_LUNCH_BREAK_START);
            }
        } else {
            if (endHour == startHour) {
                stateByte = (byte) (STATE_OVER_DAY | STATE_INCLUDE_LUNCH_BREAK);
            }
            if (startHour <= LUNCH_BREAK_START) {
                stateByte = (byte) (STATE_OVER_DAY | STATE_INCLUDE_LUNCH_BREAK_START_LESS_12);
            }
            if (startHour > LUNCH_BREAK_END && endHour >= LUNCH_BREAK_END) {
                stateByte = (byte) (STATE_OVER_DAY | STATE_INCLUDE_LUNCH_BREAK_START_MORE_14);
            }
            if (startHour > LUNCH_BREAK_START && startHour < LUNCH_BREAK_END && endHour <= LUNCH_BREAK_START) {
                stateByte = (byte) (STATE_OVER_DAY | STATE_OVER_LUNCH_BREAK_START);
            }
            if (startHour > LUNCH_BREAK_START && startHour < LUNCH_BREAK_END && endHour > LUNCH_BREAK_START) {
                stateByte = (byte) (STATE_OVER_DAY | STATE_OVER_LUNCH_BREAK_BOTH);
            }
            if (startHour >= LUNCH_BREAK_END && endHour <= LUNCH_BREAK_START) {
                stateByte = (byte) (STATE_OVER_DAY | STATE_OUT_LUNCH_BREAK);
            }
            if (startHour >= LUNCH_BREAK_END && endHour > LUNCH_BREAK_START) {
                stateByte = (byte) (STATE_OVER_DAY | STATE_OVER_LUNCH_BREAK_END);
            }
        }
        KLog.e(TAG, Byte.valueOf(stateByte));
        return stateByte;
    }

    public void writeSedentaryAccordingApi(int startHour, int endHour, int index, int repeat) {
        writeDataToPedomater(ContextUtil.app, startHour, endHour, index, repeat);
    }

    /* access modifiers changed from: protected */
    public void writeDataToPedomater(Context context, int startHour, int endHour, int index, int repeat) {
        BackgroundThreadManager.getInstance().addWriteData(ContextUtil.app, ProtoBufSendBluetoothCmdImpl.getInstance().setSedentariness(true, index, repeat, startHour, endHour, 60, 50));
    }
}
