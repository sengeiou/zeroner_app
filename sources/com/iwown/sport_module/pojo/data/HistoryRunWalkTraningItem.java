package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class HistoryRunWalkTraningItem extends BaseTraningItem implements MultiItemEntity {
    private float cal;
    private int count;
    private float distance;
    private float tims;

    public float getDistance() {
        return this.distance;
    }

    public void setDistance(float distance2) {
        this.distance = distance2;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count2) {
        this.count = count2;
    }

    public float getTims() {
        return this.tims;
    }

    public void setTims(float tims2) {
        this.tims = tims2;
    }

    public float getCal() {
        return this.cal;
    }

    public void setCal(float cal2) {
        this.cal = cal2;
    }

    public int getItemType() {
        return BaseTraningItem.UI_TYPE_HISTORY_2_Layout;
    }
}
