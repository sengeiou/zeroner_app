package com.iwown.data_link.sleep_data;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.iwown.data_link.sleep_data.SleepStatusData.ContentBean;
import com.iwown.lib_common.date.DateUtil;
import java.util.List;
import java.util.Map;

public interface ISleepService extends IProvider {
    void getDaySleepByDataFrom(SleepDataDay sleepDataDay);

    void getSleepData(SleepDataDay sleepDataDay);

    List<SleepHistoryData> getStartEndSleeps(long j, String str, DateUtil dateUtil, DateUtil dateUtil2);

    Map<String, ContentBean> getStatusDatas(long j, DateUtil dateUtil, String str);

    SleepUpNewSend getUnUploadSleepDatas(long j);

    boolean isExitSleepData(long j);

    void saveSleep(SleepDownCode sleepDownCode, boolean z);

    void updateFinalSleepUploadStatus(SleepUpNewSend sleepUpNewSend, boolean z);

    void updateSleepAction(int i, List<Integer> list);

    void updateSleepFeelType(int i, int i2);

    void updateSleepStatusData(List<ContentBean> list);
}
