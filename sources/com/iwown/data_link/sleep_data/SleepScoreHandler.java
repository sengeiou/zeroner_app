package com.iwown.data_link.sleep_data;

import com.iwown.lib_common.date.DateUtil;
import com.socks.library.KLog;

public class SleepScoreHandler {
    public static int calSleepScore(int total_min, int deep_min, long startTime_unix) {
        int score;
        DateUtil dateUtil1 = new DateUtil(startTime_unix, true);
        if (dateUtil1.getHour() < 18 || dateUtil1.getHour() > 23) {
            DateUtil dateUtil_Z = new DateUtil(dateUtil1.getUnixTimestamp(), true);
            dateUtil_Z.setHour(0);
            dateUtil_Z.setMinute(0);
            dateUtil_Z.setSecond(0);
            score = (int) (((float) ((1 - (((int) ((dateUtil1.getUnixTimestamp() - dateUtil_Z.getUnixTimestamp()) / 60)) / 480)) * 20)) + (60.0f * (1.0f - (((float) Math.abs(total_min - 480)) / 480.0f))) + (20.0f * (1.0f - (((float) Math.abs(((int) (((1.0f * ((float) deep_min)) / ((float) total_min)) * 100.0f)) - 25)) / 25.0f))));
        } else {
            int y = total_min;
            int z = (int) (((1.0f * ((float) deep_min)) / ((float) total_min)) * 100.0f);
            KLog.e("0点前 " + y + "  " + z);
            score = (int) (20.0f + (60.0f * (1.0f - (((float) Math.abs(y - 480)) / 480.0f))) + (20.0f * (1.0f - (((float) Math.abs(z - 25)) / 25.0f))));
        }
        KLog.e("睡眠 得分--- " + score);
        if (score < 0) {
            return 0;
        }
        return score;
    }
}
