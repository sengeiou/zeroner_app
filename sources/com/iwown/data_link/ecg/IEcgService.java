package com.iwown.data_link.ecg;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.iwown.lib_common.date.DateUtil;
import java.util.List;

public interface IEcgService extends IProvider {
    void braceletToView(long j, String str);

    int checkHasDataByUid(long j, DateUtil dateUtil);

    List<Integer> ecgChartViewDataByTime(long j, String str, long j2);

    EcgViewDataBean ecgViewDataByTime(long j, String str, long j2);

    List<EcgViewDataBean> ecgViewDataFromDB(long j, String str, long j2);

    List<EcgViewDataBean> ecgViewDataFromDbByUid(long j);

    List<EcgUploadNet> getUnUploadedData(long j);

    void loadEcgCalendarView(long j, DateUtil dateUtil);

    void saveEcgData(EcgViewDataBean ecgViewDataBean);

    void saveEcgNote(EcgViewDataBean ecgViewDataBean);

    void upDateEcgUrl(long j, String str, String str2, String str3);

    void updateDataAlreadyUploaded(List<EcgUploadNet> list);

    void updateEcgAIResult(long j, String str, String str2, List<String> list, int i);

    void updateEcgByData(long j, String str, String str2, String str3);
}
