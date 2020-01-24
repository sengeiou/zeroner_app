package com.iwown.lib_common.views.fatigueview2;

public class FatigueDataBean2 {
    public String data_from;
    public boolean hightLight;
    public String json_details;
    public FatigueDataBean2 left_data;
    public int max_value;
    public int min_value;
    public FatigueDataBean2 right_data;
    public String tag;

    public FatigueDataBean2(int max_value2, int min_value2, String tag2, String data_from2) {
        this.max_value = max_value2;
        this.min_value = min_value2;
        this.tag = tag2;
        this.data_from = data_from2;
    }

    public float getLeftLimitValue() {
        if (this.left_data == null) {
            return -1.0f;
        }
        return ((((float) (this.left_data.max_value + this.left_data.min_value)) / 2.0f) + (((float) (this.max_value + this.min_value)) / 2.0f)) / 2.0f;
    }

    public float getRightLimitValue() {
        if (this.right_data == null) {
            return -1.0f;
        }
        return ((((float) (this.right_data.max_value + this.right_data.min_value)) / 2.0f) + (((float) (this.max_value + this.min_value)) / 2.0f)) / 2.0f;
    }

    public String toString() {
        return "FatigueDataBean2{max_value=" + this.max_value + ", min_value=" + this.min_value + ", hightLight=" + this.hightLight + ", tag='" + this.tag + '\'' + ", json_details='" + this.json_details + '\'' + '}';
    }
}
