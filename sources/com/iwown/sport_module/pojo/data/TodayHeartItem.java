package com.iwown.sport_module.pojo.data;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.iwown.data_link.heart.HeartShowData;
import com.iwown.data_link.heart.ModuleRouteHeartService;
import com.iwown.data_link.user_pre.UserConfig;
import com.iwown.lib_common.date.DateUtil;
import com.iwown.lib_common.views.heartview.DlineDataBean;
import com.iwown.my_module.utility.Constants.ServiceErrorCode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TodayHeartItem implements MultiItemEntity {
    public List<DlineDataBean> datas;
    public final List<Integer> y_titles;

    public List<DlineDataBean> getDatas() {
        return this.datas;
    }

    public TodayHeartItem() {
        HeartShowData heartShowData = new HeartShowData();
        heartShowData.uid = UserConfig.getInstance().getNewUID();
        heartShowData.data_from = UserConfig.getInstance().getDevice();
        heartShowData.dateUtil = new DateUtil();
        ModuleRouteHeartService.getInstance().getHeartByTime(heartShowData);
        this.y_titles = heartShowData.y_titles;
        if (heartShowData.detail_data == null) {
            this.datas = new ArrayList();
            return;
        }
        new Random();
        List<DlineDataBean> datas2 = new ArrayList<>();
        DateUtil dateUtil1 = new DateUtil();
        dateUtil1.setHour(0);
        dateUtil1.setMinute(0);
        dateUtil1.setSecond(0);
        long unixTimestamp = dateUtil1.getUnixTimestamp();
        for (int i = 0; i < heartShowData.detail_data.size(); i++) {
            datas2.add(new DlineDataBean(((long) (i * ServiceErrorCode.YOU_AND_ME_IS_FRIEND)) + unixTimestamp, ((Integer) heartShowData.detail_data.get(i)).intValue()));
        }
        this.datas = datas2;
    }

    public int getItemType() {
        return BaseTraningItem.UI_TYPE_TODAY_Heart;
    }
}
