package com.iwown.ble_module.zg_ble.data.model;

import com.iwown.ble_module.utils.JsonTool;

public class StoredDataInfoDetail {
    private int end_index;
    private int max_range;
    private int start_index;
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public int getStart_index() {
        return this.start_index;
    }

    public void setStart_index(int start_index2) {
        this.start_index = start_index2;
    }

    public int getEnd_index() {
        return this.end_index;
    }

    public void setEnd_index(int end_index2) {
        this.end_index = end_index2;
    }

    public int getMax_range() {
        return this.max_range;
    }

    public void setMax_range(int max_range2) {
        this.max_range = max_range2;
    }

    public String toString() {
        return JsonTool.toJson(this);
    }
}
