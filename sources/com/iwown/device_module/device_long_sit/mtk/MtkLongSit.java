package com.iwown.device_module.device_long_sit.mtk;

import android.content.Context;
import com.iwown.ble_module.mtk_ble.cmd.MtkCmdAssembler;
import com.iwown.ble_module.mtk_ble.task.MtkBackgroundThreadManager;
import com.iwown.device_module.common.utils.ContextUtil;
import com.iwown.lib_common.log.L;
import com.socks.library.KLog;
import java.util.ArrayList;
import org.apache.commons.cli.HelpFormatter;

public class MtkLongSit {
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
    private static int WEEK_REPEAT_REQUEST = 58;
    public static MtkLongSit instance;

    private MtkLongSit() {
    }

    public static MtkLongSit getInstance() {
        if (instance == null) {
            instance = new MtkLongSit();
        }
        return instance;
    }

    public void writeSedentaryIfLunchBreak(int startHour, int endHour, byte state, boolean lunchBreakOpen, int repeat) {
        if (lunchBreakOpen) {
            KLog.e(L.TAG, "打开lunch_break开关");
            if (state == 1) {
                writeSedentaryAccordingApi(startHour, endHour, 0, repeat);
                KLog.e(L.TAG, "时间段：" + startHour + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + endHour);
            } else if (state == 6) {
                writeSedentaryAccordingApi(startHour, LUNCH_BREAK_START, 0, repeat);
                writeSedentaryAccordingApi(LUNCH_BREAK_END, endHour, 1, repeat);
                KLog.e(L.TAG, "时间段1：" + startHour + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + LUNCH_BREAK_START);
                KLog.e(L.TAG, "时间段2：" + LUNCH_BREAK_END + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + endHour);
            } else if (state == 4) {
                writeSedentaryAccordingApi(LUNCH_BREAK_END, endHour, 0, repeat);
                KLog.e(L.TAG, "时间段：" + LUNCH_BREAK_END + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + endHour);
            } else if (state == 3) {
                writeSedentaryAccordingApi(startHour, LUNCH_BREAK_START, 0, repeat);
                KLog.e(L.TAG, "时间段：" + startHour + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + LUNCH_BREAK_START);
            }
        } else {
            KLog.e(L.TAG, "没有打开lunch_break开关");
            writeSedentaryAccordingApi(startHour, endHour, 0, repeat);
            KLog.e(L.TAG, "时间段：" + startHour + HelpFormatter.DEFAULT_LONG_OPT_PREFIX + endHour);
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
        KLog.e(L.TAG, Byte.valueOf(stateByte));
        return stateByte;
    }

    public void writeSedentaryAccordingApi(int startHour, int endHour, int index, int repeat) {
        writeDataToPedomater(ContextUtil.app, startHour, endHour, index, repeat);
    }

    /* access modifiers changed from: protected */
    public void writeDataToPedomater(Context context, int startHour, int endHour, int index, int repeat) {
        byte[] data = {0, 0, 0, 0, 0, 0, 0};
        ArrayList<byte[]> sendentary = new ArrayList<>();
        data[0] = (byte) index;
        data[1] = (byte) repeat;
        data[2] = (byte) startHour;
        data[3] = (byte) endHour;
        data[4] = 60;
        sendentary.add(data);
        MtkBackgroundThreadManager.getInstance().addWriteData(context, MtkCmdAssembler.getInstance().setSedentary(data[0], data[1], data[2], data[3], data[4], 50));
    }

    public void writeSendtary(Context context, int startHour, int endHour, int repeat) {
        byte[] data = {0, 0, 0, 0, 0, 0, 0};
        data[0] = 0;
        data[1] = (byte) repeat;
        KLog.d("writeDataToPedomater bb:" + repeat);
        data[2] = (byte) startHour;
        data[3] = (byte) endHour;
        data[4] = 60;
        MtkBackgroundThreadManager.getInstance().addWriteData(context, MtkCmdAssembler.getInstance().setSedentary(data[0], data[1], data[2], data[3], data[4], 50));
    }
}
