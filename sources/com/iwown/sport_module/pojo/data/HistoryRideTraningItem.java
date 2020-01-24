package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class HistoryRideTraningItem extends BaseTraningItem implements MultiItemEntity {
    private int bgImgResources = 0;
    private float cal = 0.0f;
    private int count;
    private float distance;
    private int goImgResources;
    private boolean showGoImg;
    private int showType;
    private int time = 0;
    private int titleColor = 0;
    private int titleImgResources = 0;
    private int titleMsgResources;
    private int unitResources;

    public HistoryRideTraningItem(int sport_type, int titleImgResources2, int titleColor2) {
        this.sport_type = sport_type;
        this.titleImgResources = titleImgResources2;
        this.titleColor = titleColor2;
    }

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

    public int getTime() {
        return this.time;
    }

    public void setTime(int time2) {
        this.time = time2;
    }

    public float getCal() {
        return this.cal;
    }

    public void setCal(float cal2) {
        this.cal = cal2;
    }

    public int getItemType() {
        return BaseTraningItem.UI_TYPE_HISTORY_RIDE_Layout;
    }

    public int getTitleImgResources() {
        return this.titleImgResources;
    }

    public void setTitleImgResources(int titleImgResources2) {
        this.titleImgResources = titleImgResources2;
    }

    public int getBgImgResources() {
        return this.bgImgResources;
    }

    public void setBgImgResources(int bgImgResources2) {
        this.bgImgResources = bgImgResources2;
    }

    public boolean isShowGoImg() {
        return this.showGoImg;
    }

    public void setShowGoImg(boolean showGoImg2) {
        this.showGoImg = showGoImg2;
    }

    public int getTitleColor() {
        return this.titleColor;
    }

    public void setTitleColor(int titleColor2) {
        this.titleColor = titleColor2;
    }

    public int getTitleMsgResources() {
        return this.titleMsgResources;
    }

    public void setTitleMsgResources(int titleMsgResources2) {
        this.titleMsgResources = titleMsgResources2;
    }

    public int getUnitResources() {
        return this.unitResources;
    }

    public void setUnitResources(int unitResources2) {
        this.unitResources = unitResources2;
    }

    public int getGoImgResources() {
        return this.goImgResources;
    }

    public void setGoImgResources(int goImgResources2) {
        this.goImgResources = goImgResources2;
    }

    public int getShowType() {
        return this.showType;
    }

    public void setShowType(int showType2) {
        this.showType = showType2;
    }
}
