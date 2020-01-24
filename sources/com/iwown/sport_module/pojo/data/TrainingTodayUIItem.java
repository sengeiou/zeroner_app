package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import java.util.ArrayList;
import java.util.List;

public class TrainingTodayUIItem implements MultiItemEntity {
    private List<TodayTraningItem> mTodayTraningItems = new ArrayList();

    public List<TodayTraningItem> getTodayTraningItems() {
        return this.mTodayTraningItems;
    }

    public void setTodayTraningItems(List<TodayTraningItem> todayTraningItems) {
        this.mTodayTraningItems = todayTraningItems;
    }

    public void addItem(TodayTraningItem item) {
        this.mTodayTraningItems.add(item);
    }

    public void addAllItem(List<TodayTraningItem> items) {
        this.mTodayTraningItems.addAll(items);
    }

    public void removeItem(TodayTraningItem item) {
        this.mTodayTraningItems.remove(item);
    }

    public int getItemType() {
        return BaseTraningItem.UI_TYPE_TODAY_INFOS;
    }
}
