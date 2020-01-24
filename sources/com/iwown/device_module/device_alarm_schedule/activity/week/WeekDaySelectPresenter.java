package com.iwown.device_module.device_alarm_schedule.activity.week;

import android.content.Context;
import com.iwown.ble_module.utils.ByteUtil;
import com.iwown.device_module.R;
import com.iwown.device_module.device_alarm_schedule.bean.WeekRepeat;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class WeekDaySelectPresenter implements Presenter {
    public static final byte EVERY_DAY = Byte.MAX_VALUE;
    public static final byte NO_REPEAT = 0;
    public static final byte REPEAT = Byte.MIN_VALUE;
    public static final byte WEEKEND = 3;
    public static final byte WEEK_1 = 64;
    public static final byte WEEK_2 = 32;
    public static final byte WEEK_3 = 16;
    public static final byte WEEK_4 = 8;
    public static final byte WEEK_5 = 4;
    public static final byte WEEK_6 = 2;
    public static final byte WEEK_7 = 1;
    public static final byte WORK_DAY = 124;
    View view;

    public WeekDaySelectPresenter(View view2) {
        this.view = view2;
    }

    public List<WeekRepeat> getData(Context context, byte repeat) {
        List<WeekRepeat> data = new ArrayList<>();
        String[] mDayOfWeek = context.getResources().getStringArray(R.array.device_module_day_of_week_complete);
        if ((repeat & 64) != 0) {
            data.add(new WeekRepeat(mDayOfWeek[0], true));
        } else {
            data.add(new WeekRepeat(mDayOfWeek[0], false));
        }
        if ((repeat & 32) != 0) {
            data.add(new WeekRepeat(mDayOfWeek[1], true));
        } else {
            data.add(new WeekRepeat(mDayOfWeek[1], false));
        }
        if ((repeat & 16) != 0) {
            data.add(new WeekRepeat(mDayOfWeek[2], true));
        } else {
            data.add(new WeekRepeat(mDayOfWeek[2], false));
        }
        if ((repeat & 8) != 0) {
            data.add(new WeekRepeat(mDayOfWeek[3], true));
        } else {
            data.add(new WeekRepeat(mDayOfWeek[3], false));
        }
        if ((repeat & 4) != 0) {
            data.add(new WeekRepeat(mDayOfWeek[4], true));
        } else {
            data.add(new WeekRepeat(mDayOfWeek[4], false));
        }
        if ((repeat & 2) != 0) {
            data.add(new WeekRepeat(mDayOfWeek[5], true));
        } else {
            data.add(new WeekRepeat(mDayOfWeek[5], false));
        }
        if ((repeat & 1) != 0) {
            data.add(new WeekRepeat(mDayOfWeek[6], true));
        } else {
            data.add(new WeekRepeat(mDayOfWeek[6], false));
        }
        return data;
    }

    public byte getWeekRepeat(List<WeekRepeat> data) {
        byte mWeekRept = Byte.MIN_VALUE;
        for (int i = 0; i < data.size(); i++) {
            switch (i) {
                case 0:
                    if (!((WeekRepeat) data.get(0)).isCheck()) {
                        break;
                    } else {
                        mWeekRept = (byte) (mWeekRept | 64);
                        break;
                    }
                case 1:
                    if (!((WeekRepeat) data.get(1)).isCheck()) {
                        break;
                    } else {
                        mWeekRept = (byte) (mWeekRept | 32);
                        break;
                    }
                case 2:
                    if (!((WeekRepeat) data.get(2)).isCheck()) {
                        break;
                    } else {
                        mWeekRept = (byte) (mWeekRept | 16);
                        break;
                    }
                case 3:
                    if (!((WeekRepeat) data.get(3)).isCheck()) {
                        break;
                    } else {
                        mWeekRept = (byte) (mWeekRept | 8);
                        break;
                    }
                case 4:
                    if (!((WeekRepeat) data.get(4)).isCheck()) {
                        break;
                    } else {
                        mWeekRept = (byte) (mWeekRept | 4);
                        break;
                    }
                case 5:
                    if (!((WeekRepeat) data.get(5)).isCheck()) {
                        break;
                    } else {
                        mWeekRept = (byte) (mWeekRept | 2);
                        break;
                    }
                case 6:
                    if (!((WeekRepeat) data.get(6)).isCheck()) {
                        break;
                    } else {
                        mWeekRept = (byte) (mWeekRept | 1);
                        break;
                    }
            }
        }
        KLog.i("=======repeat===== ：" + mWeekRept + "=======" + ByteUtil.byteToBit(mWeekRept));
        return mWeekRept;
    }

    public void subscribe() {
    }

    public void unsubscribe() {
    }
}
