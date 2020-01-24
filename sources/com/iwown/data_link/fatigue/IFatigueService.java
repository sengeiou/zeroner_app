package com.iwown.data_link.fatigue;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.iwown.data_link.fatigue.FatigueRetCode.FatigueDaily;
import java.util.List;

public interface IFatigueService extends IProvider {
    List<FatigueShowData> getFatigues(long j, String str, long j2, int i);

    FatigueSend getUnUploadFatigueDatas(long j, String str);

    boolean isMTK();

    void saveFatigueDatas(FatigueDaily fatigueDaily);

    void updateFatigueDatas(long j, String str, String str2);
}
