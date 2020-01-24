package com.iwown.sport_module.pojo;

import com.iwown.sport_module.pojo.R1UpdateBean.BaseBean;
import com.iwown.sport_module.view.run.DlineDataBean;
import java.util.ArrayList;
import java.util.List;

public class R1DataBean {
    private int avg_hr;
    private List<DlineDataBean> earthTimeLists;
    private String earth_balance;
    private String earth_time_avg;
    private List<Integer> hrLists;
    private int maxRate;
    private int max_earth_time;
    private int max_hr;
    private int max_vertical;
    private int min_hr;
    private String rate_avg;
    private List<DlineDataBean> skyTimeLists;
    private String sky_time_avg;
    private List<DlineDataBean> speedLists;
    private float speed_avg;
    private float speed_max;
    private float speed_min;
    private List<DlineDataBean> stepRateLists;
    private List<DlineDataBean> verticalLists;
    private String vertical_avg;

    public String getRate_avg() {
        return this.rate_avg;
    }

    public void setRate_avg(String rate_avg2) {
        this.rate_avg = rate_avg2;
    }

    public String getEarth_time_avg() {
        return this.earth_time_avg;
    }

    public void setEarth_time_avg(String earth_time_avg2) {
        this.earth_time_avg = earth_time_avg2;
    }

    public String getSky_time_avg() {
        return this.sky_time_avg;
    }

    public void setSky_time_avg(String sky_time_avg2) {
        this.sky_time_avg = sky_time_avg2;
    }

    public String getVertical_avg() {
        return this.vertical_avg;
    }

    public void setVertical_avg(String vertical_avg2) {
        this.vertical_avg = vertical_avg2;
    }

    public String getEarth_balance() {
        return this.earth_balance;
    }

    public void setEarth_balance(String earth_balance2) {
        this.earth_balance = earth_balance2;
    }

    public int getMaxRate() {
        return this.maxRate;
    }

    public void setMaxRate(int maxRate2) {
        this.maxRate = maxRate2;
    }

    public int getMax_earth_time() {
        return this.max_earth_time;
    }

    public void setMax_earth_time(int max_earth_time2) {
        this.max_earth_time = max_earth_time2;
    }

    public int getMax_vertical() {
        return this.max_vertical;
    }

    public void setMax_vertical(int max_vertical2) {
        this.max_vertical = max_vertical2;
    }

    public List<DlineDataBean> getVerticalLists() {
        return this.verticalLists;
    }

    public void setVerticalLists(List<DlineDataBean> verticalLists2) {
        this.verticalLists = verticalLists2;
    }

    public List<DlineDataBean> getEarthTimeLists() {
        return this.earthTimeLists;
    }

    public void setEarthTimeLists(List<DlineDataBean> earthTimeLists2) {
        this.earthTimeLists = earthTimeLists2;
    }

    public List<DlineDataBean> getStepRateLists() {
        return this.stepRateLists;
    }

    public void setStepRateLists(List<DlineDataBean> stepRateLists2) {
        this.stepRateLists = stepRateLists2;
    }

    public List<DlineDataBean> getSkyTimeLists() {
        return this.skyTimeLists;
    }

    public void setSkyTimeLists(List<DlineDataBean> skyTimeLists2) {
        this.skyTimeLists = skyTimeLists2;
    }

    public float getSpeed_avg() {
        return this.speed_avg;
    }

    public void setSpeed_avg(float speed_avg2) {
        this.speed_avg = speed_avg2;
    }

    public float getSpeed_min() {
        return this.speed_min;
    }

    public void setSpeed_min(float speed_min2) {
        this.speed_min = speed_min2;
    }

    public List<DlineDataBean> getSpeedLists() {
        return this.speedLists;
    }

    public void setSpeedLists(List<DlineDataBean> speedLists2) {
        this.speedLists = speedLists2;
    }

    public float getSpeed_max() {
        return this.speed_max;
    }

    public void setSpeed_max(float speed_max2) {
        this.speed_max = speed_max2;
    }

    public int getAvg_hr() {
        return this.avg_hr;
    }

    public void setAvg_hr(int avg_hr2) {
        this.avg_hr = avg_hr2;
    }

    public int getMax_hr() {
        return this.max_hr;
    }

    public void setMax_hr(int max_hr2) {
        this.max_hr = max_hr2;
    }

    public int getMin_hr() {
        return this.min_hr;
    }

    public void setMin_hr(int min_hr2) {
        this.min_hr = min_hr2;
    }

    public List<Integer> getHrLists() {
        return this.hrLists;
    }

    public void setHrLists(List<Integer> hrLists2) {
        this.hrLists = hrLists2;
    }

    public List<BaseBean> parse(List<DlineDataBean> dlineDataBeans) {
        List<BaseBean> baseBeans = new ArrayList<>();
        for (DlineDataBean bean : dlineDataBeans) {
            BaseBean baseBean = new BaseBean();
            baseBean.setY(bean.value);
            baseBeans.add(baseBean);
        }
        return baseBeans;
    }

    public List<DlineDataBean> parse1(List<BaseBean> r1UpdateBeans) {
        List<DlineDataBean> baseBeans = new ArrayList<>();
        for (BaseBean bean : r1UpdateBeans) {
            DlineDataBean baseBean = new DlineDataBean();
            baseBean.setValue(bean.getY());
            baseBeans.add(baseBean);
        }
        return baseBeans;
    }

    public float parseMax(List<BaseBean> r1UpdateBeans) {
        float max = 0.0f;
        for (BaseBean bean : r1UpdateBeans) {
            max = Math.max(max, bean.getY());
        }
        return max;
    }

    public float parseMin(List<BaseBean> r1UpdateBeans) {
        float min = Float.MAX_VALUE;
        for (BaseBean bean : r1UpdateBeans) {
            if (bean.getY() > 0.0f) {
                min = Math.min(min, bean.getY());
            }
        }
        return min;
    }

    public float parseAvg(List<BaseBean> r1UpdateBeans) {
        int index = 0;
        float value = 0.0f;
        for (BaseBean bean : r1UpdateBeans) {
            if (bean.getY() > 0.0f) {
                value += bean.getY();
                index++;
            }
        }
        if (index > 0) {
            return (1.0f * value) / ((float) index);
        }
        return 0.0f;
    }
}
