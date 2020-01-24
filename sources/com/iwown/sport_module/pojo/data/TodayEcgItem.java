package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.data_link.ecg.EcgData;

public class TodayEcgItem implements MultiItemEntity {
    private EcgData ecgData;

    public void setEcgData(EcgData ecgData2) {
        this.ecgData = ecgData2;
    }

    public EcgData getEcgData() {
        return this.ecgData;
    }

    public int getItemType() {
        return BaseTraningItem.UI_TYPE_TODAY_ECG;
    }
}
