package com.iwown.sport_module.pojo;

import com.iwown.sport_module.view.run.DlineDataBean;
import java.util.List;

public class SwimRateData {
    private int avg_rate;
    private int maxY_rate;
    private int max_rate;
    private List<DlineDataBean> rateDataBeans;

    public int getMaxY_rate() {
        return this.maxY_rate;
    }

    public void setMaxY_rate(int maxY_rate2) {
        this.maxY_rate = maxY_rate2;
    }

    public int getMax_rate() {
        return this.max_rate;
    }

    public void setMax_rate(int max_rate2) {
        this.max_rate = max_rate2;
    }

    public int getAvg_rate() {
        return this.avg_rate;
    }

    public void setAvg_rate(int avg_rate2) {
        this.avg_rate = avg_rate2;
    }

    public List<DlineDataBean> getRateDataBeans() {
        return this.rateDataBeans;
    }

    public void setRateDataBeans(List<DlineDataBean> rateDataBeans2) {
        this.rateDataBeans = rateDataBeans2;
    }
}
