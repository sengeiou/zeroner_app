package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.data_link.blood.BloodData;

public class TodayBloodItem implements MultiItemEntity {
    private BloodData bloodData;
    public long time;

    public BloodData getBlood() {
        return this.bloodData;
    }

    public int getItemType() {
        return BaseTraningItem.UI_TYPE_TODAY_Blood;
    }
}
