package com.iwown.data_link.heart;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.iwown.data_link.heart.HeartStatusData.ContentBean;
import com.iwown.data_link.heart.heart_sport.HeartDownData1;
import com.iwown.data_link.heart.heart_sport.HeartUpSend;
import com.iwown.lib_common.date.DateUtil;
import java.util.List;
import java.util.Map;

public interface IHeartService extends IProvider {
    void clearHours();

    void getHeartByTime(HeartShowData heartShowData);

    HeartData getHeartDataByR1Time(long j, String str, long j2, long j3, int i, List<Integer> list);

    HeartData getHeartDataByTime(long j, String str, long j2, long j3, int i);

    HeartData getHeartOldDataByTime(long j, String str, long j2, long j3, int i);

    Map<String, ContentBean> getStatusDatas(long j, String str, DateUtil dateUtil);

    HeartData getSwimDataByTime(long j, String str, long j2, long j3, int i);

    List<HeartHoursData> getUnUploadHeartDatas(long j, String str);

    HeartUpSend getUnUploadHeartSportsDatas(long j);

    boolean isExist51SomeTimeSegment(long j, String str, long j2, long j3);

    boolean isExist53DataByUid(long j);

    boolean isExist53SomeDay(long j, String str, DateUtil dateUtil);

    void saveHeartSports51(List<HeartDownData1> list);

    void saveHeartStatus(List<ContentBean> list);

    void saveNetHoursData(List<HeartHoursDownCode> list) throws Exception;

    void updateDataUploads(Map<String, HeartUploadBean> map);

    void updateUnUpload1HeartSportDatas(long j);
}
