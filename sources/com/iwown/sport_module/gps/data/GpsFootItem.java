package com.iwown.sport_module.gps.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class GpsFootItem implements MultiItemEntity {
    public static final int loadAllOk = 2;
    public static final int loadFail = 1;
    public static final int loading = 0;
    private int loadType;

    public GpsFootItem(int loadType2) {
        this.loadType = loadType2;
    }

    public int getItemType() {
        return 2;
    }

    public int getLoadType() {
        return this.loadType;
    }

    public void setLoadType(int loadType2) {
        this.loadType = loadType2;
    }
}
