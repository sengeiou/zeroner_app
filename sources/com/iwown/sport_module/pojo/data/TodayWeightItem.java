package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.data_link.weight.ModuleRouteWeightService;
import com.iwown.data_link.weight.ScaleBodyFat;
import com.iwown.lib_common.views.weightview.WeightShowData;
import com.iwown.sport_module.ui.utils.BaseSportUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodayWeightItem implements MultiItemEntity {
    private double goal_weight;
    public long last_unix_time;
    public float max = 0.0f;
    public final boolean mertric;
    private List<WeightShowData> weightDatas1;

    public double getGoal_weight() {
        return this.goal_weight;
    }

    public List<WeightShowData> getWeightDatas1() {
        return this.weightDatas1;
    }

    public TodayWeightItem() {
        List<ScaleBodyFat> scaleBodyFatList = ModuleRouteWeightService.getInstance().getDatasSizeDay(UserConfig.getInstance().getNewUID(), 30);
        List<WeightShowData> weightDataList = new ArrayList<>();
        int index = 0;
        if (scaleBodyFatList.size() > 7) {
            scaleBodyFatList = scaleBodyFatList.subList(0, 7);
        }
        Collections.reverse(scaleBodyFatList);
        this.mertric = UserConfig.getInstance().isMertric();
        for (ScaleBodyFat scaleBodyFat : scaleBodyFatList) {
            float weight = scaleBodyFat.getWeight();
            if (!this.mertric) {
                weight = BaseSportUtils.getLbsFromKG(weight);
            }
            WeightShowData weightData = new WeightShowData(scaleBodyFat.getRecord_date(), (float) BaseSportUtils.getDouble1(weight));
            weightData.old_index = index;
            weightDataList.add(weightData);
            this.last_unix_time = scaleBodyFat.getRecord_date();
            index++;
            if (weightData.real_weight > this.max) {
                this.max = weightData.real_weight;
            }
        }
        KLog.e("  max " + this.max);
        double double1 = (double) UserConfig.getInstance().getTarget_weight();
        if (!this.mertric) {
            double1 = (double) BaseSportUtils.getLbsFromKG((float) double1);
        }
        if (double1 > ((double) this.max)) {
            this.max = (float) double1;
        }
        KLog.e("  max " + this.max);
        this.goal_weight = BaseSportUtils.getDouble1((float) double1);
        this.weightDatas1 = weightDataList;
    }

    public int getItemType() {
        return BaseTraningItem.UI_TYPE_TODAY_Weight;
    }
}
