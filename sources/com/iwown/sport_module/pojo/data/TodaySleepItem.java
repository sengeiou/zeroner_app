package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import com.iwown.data_link.sleep_data.ModuleRouteSleepService;
import com.iwown.data_link.sleep_data.SleepDataDay;
import com.iwown.data_link.sleep_data.SleepDownData2;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.network.utils.JsonUtils;
import com.iwown.lib_common.views.sleepview.DSleepChartView.SleepData;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

public class TodaySleepItem implements MultiItemEntity {
    private List<SleepData> lists;
    private int total_sleep_time;

    public int getTotal_sleep_time() {
        return this.total_sleep_time;
    }

    public List<SleepData> getLists() {
        return this.lists;
    }

    public TodaySleepItem() {
        int type;
        SleepDataDay sleepDataToday = new SleepDataDay();
        sleepDataToday.uid = UserConfig.getInstance().getNewUID();
        sleepDataToday.data_from = UserConfig.getInstance().getDevice();
        sleepDataToday.time_unix = System.currentTimeMillis() / 1000;
        ModuleRouteSleepService.getInstance().getDaySleep(sleepDataToday);
        KLog.e("no2855-->获取到数据:  " + sleepDataToday);
        if (sleepDataToday == null || sleepDataToday.sleepDownData1 == null) {
            this.lists = new ArrayList();
            return;
        }
        int total_sleep_time2 = 0;
        List<SleepData> sleepTimes = new ArrayList<>();
        if (sleepDataToday.sleepDownData1.getSleep_segment() == null || sleepDataToday.sleepDownData1.getSleep_segment().size() == 0) {
            sleepTimes.add(new SleepData(sleepDataToday.sleepDownData1.getStart_time() * 1000, sleepDataToday.sleepDownData1.getEnd_time() * 1000, 2));
            total_sleep_time2 = (int) ((sleepDataToday.sleepDownData1.getEnd_time() - sleepDataToday.sleepDownData1.getStart_time()) / 60);
        } else {
            long start_time = sleepDataToday.sleepDownData1.getStart_time() * 1000;
            for (SleepDownData2 sleepDownData2 : sleepDataToday.sleepDownData1.getSleep_segment()) {
                long startTime = start_time + ((long) (sleepDownData2.getSt() * 60 * 1000));
                long endTime = start_time + ((long) (sleepDownData2.getEt() * 60 * 1000));
                int type2 = sleepDownData2.getType();
                if (type2 == 3) {
                    type = 3;
                } else if (type2 == 4) {
                    type = 2;
                } else {
                    type = 1;
                }
                sleepTimes.add(new SleepData(startTime, endTime, type));
            }
            try {
                total_sleep_time2 = (int) ((((SleepData) sleepTimes.get(sleepTimes.size() - 1)).endTime - ((SleepData) sleepTimes.get(0)).startTime) / 60000);
            } catch (Exception e) {
                ThrowableExtension.printStackTrace(e);
            }
        }
        KLog.e(" no2855-->睡眠查询 " + total_sleep_time2);
        this.total_sleep_time = total_sleep_time2;
        this.lists = sleepTimes;
        KLog.e(" no2855-->睡眠查询2: " + JsonUtils.toJson(this.lists));
        UserConfig.getInstance().setToDaySleepTimeMin(total_sleep_time2);
    }

    public int getItemType() {
        return BaseTraningItem.UI_TYPE_TODAY_Sleep;
    }

    public String toString() {
        return "TodaySleepItem{total_sleep_time=" + this.total_sleep_time + ", lists=" + this.lists + '}';
    }
}
