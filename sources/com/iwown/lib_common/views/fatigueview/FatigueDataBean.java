package com.iwown.lib_common.views.fatigueview;

public class FatigueDataBean {
    public FatiguePointBean bottomPoint;
    public FatiguePointBean centerPoint;
    public int end_time_s;
    public int end_value;
    public int start_time_s;
    public int start_value;
    public FatiguePointBean topPoint;
    int x;

    public FatigueDataBean(int start_value2, int end_value2) {
        this.start_value = start_value2;
        this.end_value = end_value2;
    }

    public void calPointData(int viewHeight, int x2, int left_size) {
        this.x = x2;
        this.topPoint = new FatiguePointBean();
        this.topPoint.x = x2;
        this.topPoint.screenX = left_size + x2;
        this.topPoint.value = this.start_value;
        this.topPoint.y = ((((float) this.start_value) * 1.0f) / 150.0f) * ((float) viewHeight);
        this.bottomPoint = new FatiguePointBean();
        this.bottomPoint.x = x2;
        this.bottomPoint.screenX = left_size + x2;
        this.bottomPoint.value = this.end_value;
        this.bottomPoint.y = ((((float) this.end_value) * 1.0f) / 150.0f) * ((float) viewHeight);
        this.centerPoint = new FatiguePointBean();
        this.centerPoint.x = x2;
        this.centerPoint.screenX = left_size + x2;
        this.centerPoint.y = (this.topPoint.y + this.bottomPoint.y) / 2.0f;
    }
}
