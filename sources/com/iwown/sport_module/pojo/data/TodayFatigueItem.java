package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.data_link.fatigue.FatigueData;
import com.iwown.data_link.fatigue.FatigueShowData;
import com.iwown.data_link.fatigue.ModuleRouteFatigueService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.network.utils.JsonUtils;
import java.util.ArrayList;
import java.util.List;

public class TodayFatigueItem implements MultiItemEntity {
    private FatigueData fatigue;
    public long time;

    public TodayFatigueItem() {
        List<FatigueShowData> fatigueDatas = ModuleRouteFatigueService.getIsnatnce().getFatigueDatas(UserConfig.getInstance().getNewUID(), UserConfig.getInstance().getDev_mac(), new DateUtil().getTimestamp(), 1);
        if (fatigueDatas.size() > 0) {
            if (fatigueDatas.size() > 7) {
                fatigueDatas = fatigueDatas.subList(0, 7);
            }
            FatigueShowData fatigueShowData = (FatigueShowData) fatigueDatas.get(0);
            this.time = fatigueShowData.getTime();
            ArrayList<FatigueData> fatigues = JsonUtils.getListJson(fatigueShowData.getFatigues(), FatigueData.class);
            if (fatigues != null && fatigues.size() > 0) {
                this.fatigue = (FatigueData) fatigues.get(fatigues.size() - 1);
            }
        }
    }

    public FatigueData getFatigue() {
        return this.fatigue;
    }

    public int getItemType() {
        return BaseTraningItem.UI_TYPE_TODAY_Fatigue;
    }
}
