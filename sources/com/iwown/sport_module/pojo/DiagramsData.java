package com.iwown.sport_module.pojo;

import com.iwown.sport_module.view.run.DlineDataBean;
import java.util.ArrayList;
import java.util.List;

public class DiagramsData {
    private int avg_rate;
    private List<DlineDataBean> mPaceDataBeans = new ArrayList();
    private List<DlineDataBean> mRateDataBeans = new ArrayList();
    private float maxY_pace;
    private int maxY_rate;
    private int max_rate;
    private float minY_pace;
    private float pace;

    public int getAvg_rate() {
        return this.avg_rate;
    }

    public void setAvg_rate(int avg_rate2) {
        this.avg_rate = avg_rate2;
    }

    public int getMax_rate() {
        return this.max_rate;
    }

    public void setMax_rate(int max_rate2) {
        this.max_rate = max_rate2;
    }

    public List<DlineDataBean> getRateDataBeans() {
        return this.mRateDataBeans;
    }

    public void setRateDataBeans(List<DlineDataBean> rateDataBeans) {
        this.mRateDataBeans = rateDataBeans;
    }

    public List<DlineDataBean> getPaceDataBeans() {
        return this.mPaceDataBeans;
    }

    public void setPaceDataBeans(List<DlineDataBean> paceDataBeans) {
        this.mPaceDataBeans = paceDataBeans;
    }

    public int getMaxY_rate() {
        return this.maxY_rate;
    }

    public void setMaxY_rate(int maxY_rate2) {
        this.maxY_rate = maxY_rate2;
    }

    public float getMaxY_pace() {
        return this.maxY_pace;
    }

    public void setMaxY_pace(float maxY_pace2) {
        this.maxY_pace = maxY_pace2;
    }

    public float getMinY_pace() {
        return this.minY_pace;
    }

    public void setMinY_pace(float minY_pace2) {
        this.minY_pace = minY_pace2;
    }

    public float getPace() {
        return this.pace;
    }

    public void setPace(float pace2) {
        this.pace = pace2;
    }
}
